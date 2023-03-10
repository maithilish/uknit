
## ITest

In this note we broadly explain the design of uKnit. As it's not possible to cover each and every aspect of the code here or in the class documentation, we can use itests gain better understanding of impact of the particualar code block. Just disable the code under question and use error thrown by the itests. The integration tests cover majority of the features.

## Abbrivations

Abbrivations used in this note, uKnit code and documentation.

IMC - Internal Method Call
IM  - Internal Method
CUT - Class Under Test
MUT - Method Under Test

The AST nodes, Expression is shortened as exp and Statment as stmt.

## Pack

Everthing else in uknit revolves around Pack which maps an IVar to an Expression. The var and exp refers LHS and RHS of a statement. In some statements such as names[0] = "foo", the var is null and the LHS names[0] is held by the optional field leftExp. Invoke is subclass of Pack which packs MethodInvocation and SuperMethodInvocation nodes and additionally holds callVar, returnType, When and Verify. 

The visitor adds packs to the list held by the heap in the same order as the stmt and exp appear in the source. Order is also maintained during IM heap merge as patch logic depends on pack order. Use Heap.addPack() method to insert pack to the list as it add listener to the pack to listen for var changes in the pack so that Heap.getVars() can rebuild the var list on changes.

Filtering and search of pack is handled by the Packs, the helper class. 


## IMC Flow

Explain this

  private Foo foo;

    public void callMultiTimes() {
        configure();
        configure();
    }

    private void configure() {
        foo.opt().check(false);
    }
    
IM the arg overrides the parameter. If arg and param name are named same then arg pack is retained and param pack is discarded in merge(). If name differs then again arg pack is retained and param pack var name is set to arg name so that var patch is created. On multicall new arg is created. See ArgParam.java

## Patch

In many situations the exp in a node needs to be replaced by a var name. To generate a test class uKnit may process a MUT multiple times - internal methods such as private or super method may be called one or more times and the ctl flow path in MUT may result processing of MUT. Any direct  modification to node makes it useless for subsequent processing. To avoid modification to the original node, uKnit copies the node and patches its exp with relavent var name. Whenever a patched view of a node is required, a copy of original node is created and patch is applied to the copy. Bottom line is that in no circumstance the node parsed from source is modified.

When an IM is called multiple times, separate Packs (or Invoke) are created for each invoke. The packs for same statement from multiple calls are distint and are not equal, but the exp held by the packs points to same ast node as all IM invokes uses the same source tree. For example, if IM has one statement String name = foo.get() and IM is called twice. On first invoke P1 [var=name, exp=foo.get()] and in second invoke P2 [var=name2, exp=foo.get()] are created. The P1 != P2, but P1.exp == P2.exp as both exps, foo.get(), are one and same instance as it comes from single parsed source. Keep this aspect in mind while tinkering with patch logic.

At present patch is done in three ways

 - Invoke exps in any exp (including another invoke exp) is replaced with corresponding var name. No separate patch is created for this, instead the pack list in heap is searched for the invoke pack and its var name is used to patch the invoke in exp.
 - A var may be renamed in case of name conflict and all depending exp are patched with new name. It is not possible to search for relevant var such renames instead Patch is created and added to patch list of the Pack.
 - IM invoke is packed as exp in a dedicated Pack during visit phase, but the IM invoke exp is replaced with IM's return var name exp on IM merge. To patch any chained IM call the IM invoke exp and relavent retunr var is collected in patches map in Patcher.
 
Details are as below,
 
### Invoke Patch

    public void invokePatchExample() {
        Foo foo;
        foo = configure();

        Foo foo1;
        foo1 = configure();
    }

    private Foo configure() {
        Foo foo = factory.getFoo();
        foo.getOptions().setEnabled(false);
        return foo;
    }

    I0 [var=foo, exp=configure()] -- IM invoke
       I1 [var=foo2, exp=factory.getFoo()]
       I2 [var=options, exp=foo.getOptions()]
          Patch [kind=VAR, rename foo -> foo2]
       P3 [var=--, exp=foo.getOptions().setEnabled(false)]

    I4 [var=foo1, exp=configure()] -- IM invoke
       I5 [var=foo3, exp=factory.getFoo()]
       I6 [var=options2, exp=foo.getOptions()]
          Patch [kind=VAR, rename foo -> foo3]
       P7 [var=--, exp=foo.getOptions().setEnabled(false)]
       
The IMC configure() is invoked twice and the packs are I0 and I4. When copyAndPatch() is called for P3, all the packs before are reverse traversed to find matching pack for its exp part foo.getOptions(). The matching pack is I2 and its var options is patched and copy of P3 exp becomes options.setEnabled(true).

Similarly, when copyAndPatch() is called for P7, all the packs before are reverse traversed to find first matching pack for its exp part foo.getOptions(). The matching pack is I6 and its var options is patched and copy of P7 exp becomes options2.setEnabled(true). Even though I2 also perfectly matches it is never considered as search terminates on the first match and I6 is used for patch. It is important to note that I2 and I6 are different packs and not equal but the exp of both I2 and I6 are one and the same instance as it comes from same MethodDeclaration node of single compilatation unit. In other words, both exp of I2 and I6 point to same instance of MI exp foo.options().

The logic eliminates complexcities associated with patch maintainace, but it depends on ordering of pack and it is important that packs in the list are in same order as they appear in source.

### Var Rename Patch

IM Arg and Parameter name may be same or differ. Pack level var rename patch is required whenever arg and param name differs.

    public String sameAndDiffName(final Foo foo) {
        int index = 10;
        imc(foo, index);
        int index2 = 20;
        return imc(foo, index2);
    }

    private String imc(final Foo foo, final int index) {
        return foo.get(index);
    }

    P1 [var=index, exp=10]
    P2 [var=--, exp=imc(foo, index)]  -- IMC invoke
       I3 [var=orange, exp=foo.get(index)]

    P4 [var=index2, exp=20]
    P5 [var=--, exp=imc(foo, index2)] -- IMC invoke
       I6 [var=kiwi, exp=foo.get(index)]
          Patch [kind=VAR, rename index -> index2]

The arg var in calling method is patched to exps in IM. Above, in the first call imc(foo, index) the arg index is passed to IM but as param name is also index there is no patch for I3. However, in second IM call, P5, arg index2 is passed and all depended exps in IM should be patched to index2 and I6 has patch to replace index to index2 in exp foo.get(index).

Var rename patch is also used whenever there is var name conflict.

    private Factory factory;

    public void varNameConflict() {
        Foo foo = factory.createFoo();
        Foo otherFoo = imc();    
    }

    private Foo imc() {
        Foo foo = factory.createFoo();
        foo.bar();
        return foo;
    }
    
    I1 [var=foo, exp=factory.createFoo()]
       I2 [var=foo2 [oldName=foo], exp=factory.createFoo()]
       I3 [var=bar, exp=foo.bar()]
          Patch [kind=VAR, rename foo -> foo2]
    I4 [var=otherFoo, exp=foo2]

Above, calling method creates an instance of Foo and assign it var named foo. The IM also creates and assign another instance of Foo to var named foo within it scope. But when statements of IM is merged with calling method there is conflict as there are two vars named foo. To resolve the conflict, on merge the IM foo is renamed as foo2 and a patch is added to I3 whose exp used local var foo. With the patch, I3 exp becomes foo2.bar().

In case of renamed vars it is possible to patch exp with the logic used in case of invoke patches, but same is not true in case of arg-param name mismatch where pack level patch is essential. For uniformity in both cases pack level patch is implemented.

While applying var patch the value of the arg is not compared instead it is applied based on arg index. In following pack, the exp becomes foo.format(name4, dept2) even though patch is for name3 to name4. This is because the name and old names fields in the var held by the patch are updated on reassign.

    Invoke Var [name=name4, [name3], type=String, L, Real] Exp [exp=foo.format(name,dept)]
        Patch [kind=VAR, rename dept -> dept2]
        Patch [kind=VAR, rename name3 -> name4]

### IM Patch
 
    class SuperClass {
        public Payload getPayload() {
           return factory.createPayload();
        }
    }

    class SubClass extends SuperClass {    
        public long getStaticMulti() {
           super.getPayload().getJobInfo().getId();
           return super.getPayload().getJobInfo().getId();
        }
    }

       I1 [var=payload3, [payload], exp=factory.createPayload()]
    I2 [var=payload, exp=payload3]  -- IMC call
    I3 [var=jobInfo, exp=super.getPayload().getJobInfo()]
    I4 [var=apple, exp=super.getPayload().getJobInfo().getId()]
       I5 [var=payload4, [payload], exp=factory.createPayload()]
    I6 [var=payload2, exp=payload4]  -- IMC call
    I7 [var=jobInfo2, exp=super.getPayload().getJobInfo()]
    I8 [var=grape, exp=super.getPayload().getJobInfo().getId()]

    [ Patch Map ]
    Patch1 [exp=super.getPayload(), var=payload4]
    Patch2 [exp=super.getPayload(), var=payload3]

Here IM super.getPayload().getJobInfo().getId() is called twice and it is a chained call. The invoke super.getPayload() returns payload3 on first call and payload4 on second. However, in IMC calls I2 and I6 the exp is not set super.getPayload() but its return var payload3 and payload4. Because of this there is no way we can patch super.getPayload() in I3, I4, I7 and I8. To patch these, the patch map is maintained in Patcher class where each IM invoke exp and its return var is collected. For I4 and I4, the Patch2 and for I7 and I8 the Patch1 are applied. Each IM invoke exp is distinct and no two IM invoke exp are equal (hashcode are different), so the super.getPayload() in I3 and I4 gets Patch2 as its exp matches.

### Patch Services

The copyAndPatch() delgates to appropriate PatchService in org.codetab.uknit.core.make.method.patch.service package depending on the node type. For example, if node is MI then node is processed by MethodInvocationSrv. This is done so because each node type has differently named methods to get or set its exp parts. While the MI has getExpression() and arguments(), the InfixExpression has getLeftOperand(), getRightOperand() and getExtendedOperands() to get the exps.

## Java classes for Test

It is convenient to use custom interfaces defined in package private Model.java. Apart from it some of the suitable classes to use in itests - Instant, LocalDate, LocalDateTime, DateTimeFormatter, File, Path, StringBuilder, Locale, IO InputStream, OutputStream (io related classes). Avoid Date as compare may randomly fail.

## Static Calls

Objects returned by statics calls are real, but in internal or super static calls the returned object may be mock if static call returns a mock field, parameter or mock object returned by mock field or parameter.

Any static call either from project packages or external lib can't be initializer. Ex: Integer.of(10) or Statics.getName("foo")  both are not allowed as initializer.

Calls to internal static methods are not static calls and they are treated as regular methods.

## Accessible Objects

Test class don't have access to instances produced and consumed inside a method. 

    public int foo() {
        int[] array = new int[2];
        int foo = array[0];
        return foo;
    }        
        
Here array is created and accessed within the method and test method can't access the array. The foo is returned by the method and is accessible. Whether var is accessible is set with Nature.ACCESSIBLE.

  - Fields, Parameters and return vars, either real or mock, are accessible.
  - The objects returned by accessible mocks are also accessible.
  - The objects returned by accessible real are accessbile only if it is field or parameters or return var. (1st rule takes care of this)

## Anonymous and Lambda

Anon and Lambda are treated in same fashion. For brevity, we just explain the logic of anon, and same applies to lambda.

In when stmts, the anon args are replaced with any(type name) and other args with matcher eq(arg). 

        when(calc.op(6, 3, (a, b) -> a * b)              becomes     when(calc.op(eq(6), eq(3), any(Op.class))).thenReturn(..)
        when(button.add(new new ActionListener() {...})  becomes     when(button.add(any(ActionListener.class)).thenReturn(..)

In verify stmts, if config uknit.anonymous.class.capture is true (default) then in anon args are replaced with ArgumentCapture and other args with matchers.

        verify(calc.op(6, 3, (a, b) -> a * b)              becomes
               ArgumentCaptor<OperationA> captorA = ArgumentCaptor.forClass(Op.class);
               verify(calc).op(eq(6), eq(3), captorA.capture());
        
        verify(button.add(new new ActionListener() {...})  becomes
               ArgumentCaptor<ActionListener> captorA = ArgumentCaptor.forClass(ActionListener.class);
               verify(button.add(captorA.capture());

User may use captures for further testing. In case capture config is false then in anon args are replaced with any(type name) and other args with matchers.

        verify(calc.op(6, 3, (a, b) -> a * b)              becomes               
               verify(calc).op(eq(6), eq(3), any(Op.class);
        verify(button.add(new new ActionListener() {...})  becomes
                verify(button).addActionListener(any(ActionListener.class));

## Standin Fields

The mock fields are injected with @Mock annotations. Apart from these, method may use other fields and standin local vars are created for such fields. In debug log pack listing, the packs for standin vars appear in the end after the return pack.


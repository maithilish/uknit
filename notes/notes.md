## JDK Version

Eclipse 4.17 (2020-09) onwards mandates JDK version 11 or newer. Stand alone Eclipse JDT core is also compiled with 11. If tried with JDK 8 then class file has wrong version 55.0, should be 52.0 is thrown.

The eclipse.jdt version should be 3.26.0 or newer.

## Java Statement vs Expression

An expression is a construct made up of variables, operators, and method invocations that evaluates to a single value. 

A statement forms a complete unit of execution. 

  - expression statements - assignment expressions, ++, --, Method Invocation, creation expressions can be made into a statement by terminating the expression with a semicolon (;).
  - declaration statements - declares a variable.
  - control flow statements

A block is a group of zero or more statements between balanced braces and can be used anywhere a single statement is allowed. 

## Abbreviations

IMC 	- internal method call.
MI 		- method invocation.
SMI 	- super method invocation.

## Cast Change type

The varExp is found via cast exp and its left var is modified to cast type. However, if expression is Mi, the ExpReturnType in its invoker is not modified.

## MethodInvocation

for	date.toString().charAt(0), mi.getExpression() returns date.toString() and mi.getName() returns the method name: charAt.

for	date.toString(), date and toString.
	
## IVar Fields

IVar boolean fields and their characteristics.

### mock

Indicates whether var is mock or real. By default, any type for which config **uknit.createInstance** is not defined are mocks, otherwise it is real. However, a mock may evolve into real based on certain conditions. For example, if type is primitive, array type or unmodifiable then mock field is set to false.

### created

Indicates whether var is created and it is set to true if,

 - node is of creation type such as StringLiteral, NumberLiteral, InfixExpression etc., See Nodes.creationNodes array for full list of types.
 - infer var is not stageable
    - var is real
    - if method invocation is static call.
 - local var
    - initializer is creation
    - Anon or Lambda
    - method invocation is static call
    - top name of method invocation is real. In track = tracks.stream()....; if tracks is real then track is created.
 - return var
    - var initializer is creation

### enable

The IVar.enable field controls whether var is part of output. If it is true, then var definition statement is generated in test class or method, otherwise var doesn't find place in the output.

By default all types of vars are enabled when created by DefaultVar constructor. Except Field var, all other retain this state till test method statements are generated.  As the last step in MethodMaker.process() the VarEnabler.updateVarEnableState() method is called to disable the unused vars. The VarEnabler.checkEnableState() throws CriticalError if any var is disabled before that. This is to ensure that any update to enable field is centralized in one place.

For infer, local, return vars and parameters, setEnable() and isEnable() are used only in VarEnabler and VarEnablers class.

For Field var, setEnable() and isEnable() are used in many locations as detailed below.

    setEnable()
	fields are disabled so that mock field is not injected if, 
		static, primitive type, unmodifiable and vdf has initializer.
	
	returnProcessor
		if var is field and not mock then enable. Real field is disabled, if returned enabled it.

    isEnable()
	FieldMaker.addFieldDeclsToTestClz()
		add fields to body - isEnable() filters the disabled fields.
		
	GetterSetters.isMockInjected()
		if field is mock - isEnable() filters the disabled field.
		
	InvokeProcessor.stageVerify()
		if callVar is mock and enabled, stage verify - isEnable() filters disabled field.

The method generation classes VarStmt and ReturnStmt uses isEnable() to filter and output enabled vars.

### enforce

If any var that doesn't fit the above enable/disable logic then use enforce field to override or hardcode the enable/disable state. Take for example the enhanceForStatement, for(String key: list), even though var key is not used by when etc., we force enable it so var key is defined in test method which is useful to tester to add an item to list. Likewise we can also use it hide any enabled field.

### deepstub

Indicates that MethodInvocation is a chain call and mock should be created with property RETURNS_DEEP_STUBS. 

## Setters

The setter local var may hide the field as uKnit doesn't check field name for duplicate while generating local vars. See - clz.Pojo.java

    private Date bar;
    public void setBar(final Date bar) {
        this.bar = bar;
    }

## Stepins

Assertions on contents of objects or collections.

## Patches

In some cases the expression in a node needs to be replaced by a var name. To generate a test class uKnit may process a Method Declaration  multiple times - private (or even) method may be called one or more times internally, ctl flow path may result in multiple test methods. Any direct modification to node makes it invalid for any subsequent processing. To avoid modification to the original node, uKnit stages Patch which is simply maps exp to a name in a node. Whenever a patched view of a node is required or in test generation phase, a copy of original node is created and patch is applied to the copy.

In JDT only the node that is directly parsed from the source is resolvable and copy is unresoveable. To resolve uKnit uses the original node which is used to create the copy. The Visitor visits the original source AST nodes and they are resolvable while the nodes created with ASTNode.copySubtree() are not resolvable.

### MethodInvocation and Infer Vars Patch

Infer vars are patched to expressions. 

        sb.append(file.getName())
        
For file.getName() infer var apple is created and used to generate the when(file.getName()).thenReturn(apple). For sb.append(..) node, a patch is created mapping exp file.getName() to var apple with expIndex as 0. While generating the when stmt patch is applied and when(sb.append(apple)).thenReturn(stringBuilder) is generated. While generating statements, when, verify and initializer, the ExpPatcher.copyAndPatch() method creates patched copy of the original node and use it to generate statements.

In MI visit patches are created for MI by calling patchProcessor.stageMiPatches() which calls patcher.stageInferPatch() and patcher.stageInternalPatch().

  - The stageInferPatch() gets Invoke for MI and its args (only for arg that are MI). If any MI's parent is MI, not static call, not real returning real (String.toLowerCase()) then invoke is patchable.

  - The stageInternalPatch() stages patch for internal method call when calling arg name is different from parameter. For example, if calling arg is inferVar apple and parameter of internal method is fruit, then fruit.pie() becomes apple.pie() which IM.

Next MI is processed by calling invokeProcessor.process() which applies any existing patches to MI and patchedMI is used to get name etc., For other things original MI is used.

### SuperMethodInvocation Patch

Patch to replace entire super method invocation with return var. Ex: return super.foo(bar); if SMI returns var named orange then stmt becomes return orange;

Similarly, there are patchers for ClassInstanceCreation, ArrayCreation, ReturnStatement, InfixExpression and Assignment - They all call stageInferPatch() and stageInternalPatch().

## Initializer Patch

In addition to stageInferPatch() and stageInternalPatch() the ReturnStatement all call stageInitializerPatch(). Ex: return new Date();

## Injected Field and Local Create

	public class MetricsServer {
		
		private Server server;
		
		public void start() {
			server = factory.createServer(port);
		}
		
		public void stop(){
			server.stop();
		}
	}
	
Even though there is no @Inject for field server, uknit injects mock and also creates a local var server in test method. Checkstyle marks it with Hides a field message. Tester has to step-in and decide what to retain as stop() method uses the injected mock.

We can strictly inject mocks only for the @Inject fields, but then for stop() test we have inject a server mock forcibly.

This is also happens with POJO fields.

## Super Class

### Parse Super Class CU

First CUT is parsed. Next for each TypeDecl in CUT CompilationUnit (cu) the super class name and package are collected into a map.  Then for each super class name the source is searched (file content search) in package dir and details are collected in CuCache. Finally, for each entry in CuCache if cu not exists then new cu is parsed.


## IMC Arg Param

When call arg and internal method parameter are named same then second local var is created which acts as parameter inside the internal call. Deduplicate such vars. When names are different then in VarProcessor.stageLocalVars() local var parameter is not staged instead arg is used as var.

if names are same use the arg as param in IM else stage patch.

## Class isAssignableFrom

isAssignableFrom() determines if the class or interface of calling Class object is either the same as, or is a superclass or super interface of, the parameter's class or interface.

Otherwise, whether parameter is assignable to caller class. The ArrayList (parameter) is assignable to the List (caller) but not otherwise.

        List.class.isAssignableFrom(ArrayList.class)  // true
        ArrayList.class.isAssignableFrom(List.class)  // false
        

Class from variables:

        List<String> cowArrayList = new CopyOnWriteArrayList<>();
        cowArrayList.getClass() // CopyOnWriteArrayList

        List<String> arrayList = new ArrayList<>();
        arrayList.getClass() // ArrayList

        ArrayList<Date> dateArrayList = new ArrayList<>();
        dateArrayList.getClass() // ArrayList

        List<String> nullList = null;
        arrayList.getClass() // NullPointerException
	
Class is obtained from the object held by variable and it not from the variable type.

        cowArrayList.getClass().isAssignableFrom(arrayList.getClass()) // false
        arrayList.getClass().isAssignableFrom(cowArrayList.getClass()) // false
        arrayList.getClass().isAssignableFrom(dateArrayList.getClass()) // true (erased type parameter is ignored)
        dateArrayList.getClass().isAssignableFrom(arrayList.getClass()) // true
	
In above, the class of object held by var is used from comparision, not the var type. Both the cowArrayList and arrayList are List and we can assign one to another but isAssignableFrom returns false.

        // can assign List to List
        arrayList = cowArrayList;  // allowed

        // can't assign ArrayList to CopyOnWriteArrayList
        cowArrayList.getClass().isAssignableFrom(arrayList.getClass()) // false

Class from types.

        System.out.println(List.class); // interface java.util.List

Class of the Type is obtained from it.

        // can assign CopyOnWriteArrayList to List
        List.class.isAssignableFrom(cowArrayList.getClass())); // true

        // can't assign List to CopyOnWriteArrayList
        cowArrayList.getClass().isAssignableFrom(List.class)); // false

        // CopyOnWriteArrayList implements List but not extends ArrayList
        // can't assign CopyOnWriteArrayList to ArrayList
        ArrayList.class.isAssignableFrom(cowArrayList.getClass())); // false
        
        // can't assign ArrayList to CopyOnWriteArrayList
        cowArrayList.getClass().isAssignableFrom(ArrayList.class)); // false
	

	

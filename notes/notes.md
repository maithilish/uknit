## JDK Version

Eclipse 4.17 (2020-09) onwards mandates JDK version 11 or newer. Stand alone Eclipse JDT core is also compiled with 11. If tried with JDK 8 then class file has wrong version 55.0, should be 52.0 is thrown.

The eclipse.jdt version should be 3.26.0 or newer.

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

## Infer Var Patch

Infer vars are patched to expressions but direct modification to src node affects
the subsequent internal calls (private calls). Instead, keep the source node unmodified and create patch for replacement during visit and collect patches in heap. Defer actual patching to statement generate phase.

While generating statements, when, verify and initializer, the ExpPatcher.copyAndPatch() method creates copy of the source node and patch its infer expressions and copy is used to generate statements.

All visit processing happens on source AST nodes as they are resolvable while the nodes created with ASTNode.copySubtree() are not.

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

## IMC Arg Param

When call arg and internal method parameter are named same then second local var is created which acts as parameter inside the internal call. Deduplicate such vars. When names are different then in VarProcessor.stageLocalVars() local var parameter is not staged instead arg is used as var.

if names are same use the arg as param in IM else stage patch.

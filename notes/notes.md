## JDK Version

Eclipse 4.17 (2020-09) onwards mandates JDK version 11 or newer. Stand alone Eclipse JDT core is also compiled with 11. If tried with JDK 8 then class file has wrong version 55.0, should be 52.0 is thrown.

The eclipse.jdt version should be 3.26.0 or newer.

## Abbreviations

IMC 	- internal method call.
MI 		- method invocation.
SMI 	- super method invocation.


## Cast Change type

The varExp is found via cast exp and its left var is modified to cast type. However, 
if expression is Mi, the ExpReturnType in its invoker is not modified.   

## MethodInvocation

for	date.toString().charAt(0), mi.getExpression() returns date.toString() and mi.getName() returns the method name: charAt.

for	date.toString(), date and toString.
	
## Hidden Var

By default, vars are not hidden. It is used while generating the statements.

  - Lambda or Annon expected var is hidden and assert statement is not generated.
  
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

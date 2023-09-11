
## Purpose of uKnit

To reduce typing but not to reduce effort to test.

## Local var hides field

	private ScriptEngine jsEngine;
	
	public void initScriptEngine() {
		jsEngine = seManager.getEngineByName("JavaScript");
	}

For the above code, the test method is generated as below:

	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInitScriptEngine() {    
		ScriptEngine jsEngine = Mockito.mock(ScriptEngine.class);
		when(seManager.getEngineByName("JavaScript")).thenReturn(jsEngine);
	}	

In setup mockito injects mock for the field jsEngine. In the test method uKnit creates another mock for jsEngine as `seManager.getEngineByName(...)` returns an object that has to assigned to jsEngine field. 

User has couple of options here

 - if no other test method uses the field then delete the field so that mockito doesn't inserts the mock before hand. 
 - otherwise, rename the local jsEngine var something like jsEngine2.


## Var Forward References 

Sometimes you may see errors where vars are referred before it is defined. This happens when vars are initialized by calling internal methods such as private or super methods. 

    WebClient webClient = getWebClient();

    	emits

    WebClient webClient = webClientPlum;
    WebClient webClientPlum = Mockito.mock(WebClient.class);

It is so because var webClient is added to source and then the private method getWebClient() creates and adds the webClient renaming it as webClientPlum. Just cut the error line and paste it after the var definition.

## Collection Insert

uKnit adds single object to collections such as List or Map etc., This feature works as long as variables are of base interface type. For example, the ArrayList are referred using List interface.

    public String getReturn(final List<String> names) {
        return names.get(0);
    }

uKnit emits

    @Test
    public void testGetReturn() {
        List<String> names = new ArrayList<>();
        String apple = "Foo";
        names.add(apple);
		 ...

If param **names** is defined as ArrayList<String> instead of List<String> then item is not added. In case, if you code to specific type instead of interface then you have to add list of access methods of specific type in uknit.properties file. For ArrayList this would be

	uknit.createInstance.ArrayList=new ArrayList<>();
	uknit.collection.access.java.util.ArrayList=get,remove
	
## Ctl Path and Code Coverage

uKnit generates multiple test methods based on ctl flow paths. Consider the following,

	if (fetchDocument && persist) {
	  ...
	}
	
uKnit generates two tests one for if path and another for else path. For complete test coverage, we need one more else test where both operands are false and you have to add the missing test manually.

## Why there is no thenThrow statements in Test Methods That test Try Catch Paths

In test methods that tests the try catch path, no thenThrow statements are created instead regular when or verify statements are created. Developer has to modify appropriate when or verify statements to throw exception.

Mockito expects doThrow statements for void methods. At present uKnit generates verify for such items. Developer has to step in and change it.

For catch path tests, uKnit simply call the the method under test and doesn't generate junit assertThrows() statement.

## Infinite Loop

The culprit usually is the while(foo.hasNext()) or similar construct. Add second thenReturn(false) to break the loop.

        when(indexer.hasNext()).thenReturn(peach).thenReturn(false);
        
## How to Test Abstract Class

uKnit generates normal test class for abstract CUT and test run throws 

        Cannot instantiate @InjectMocks field named 'baseProcessor'! Cause: the type 'BaseProcessor' is an abstract class.

To test this, add a static nested class in the test class that extends the abstract class and add unimplemented methods in it. Next change the CUT (var with @ InjectMocks) to static nested class type as shown below,

        @InjectMocks
        private TestBaseProcessor baseProcessor;
    
        static class TestBaseProcessor extends BaseProcessor {
                @Override
                public void process() {            
                }
        }
    
## Source exp appears in tests

The conditional exp in arg appears in test instead of value.

	boolean flag = true;
	int code = 2;
	foo.appendObj(flag ? code > 1 : code > 2);

uKnit can't evaluate the value of the exps. For the source code `flag ? code > 1 : code > 2`,	the verify is `verify(foo, times(2)).appendObj(flag ? code > 1 : code > 2);` instead of `verify(foo, times(2)).appendObj(true);`

Consider the code

        foo.appendString(str);
        foo.appendString(String.valueOf(str));
        
it results in two verifies even though both evaluates to same value.

        verify(foo).appendString(str);
        verify(foo).appendString(String.valueOf(str));

The verifies should be modified

        verify(foo, times(2)).appendString(str);
 
		 
	
 

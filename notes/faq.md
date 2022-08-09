
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
 
 
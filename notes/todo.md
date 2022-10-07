# TODO

## Optional

Optional is empty at present. It should be initialized with value. See: itest.optional.OptionalLists

When Optional contains list then Optional.get() in arg is not patched and NoSuchElement exception is thrown. Also, list is not defined. See: itest.optional.OptionalOps.

## Ctl Flow

add never() to trailing statements in main block after exception is thrown.
write tests - throw exception in catch block.

### Ctl Flow for Arg Validation

Arg Validations in step.parse.IndexerFactory throws Exceptions. For coverage separate test is required for this. Refactor control flow paths to handle this.

	validState((itemNames.indexOf("fact") == itemNames.size() - 1),
                "fact should be last item of the list");

### Ctl Flow Missing Test Method

May be else path of, if (!lgs.containsKey(linkGroup)) is missing in LocatorGroupFactory, is missing in /step/src/main/java/org/codetab/scoopi/step/process/LocatorGroupFactory.java. Needs investigation.

## Static Calls

Static call in new object creation creates inferVar apple but not replace in call. See: itest.statics.StaticCalls.

## Factory

Factory test creates object of same type and tests with assertSame(). Explore better way to test created object. See: itest.create.Factory.

## IMC

Internal call returns separate mock for each call instead of single mock. See: itest.internal.MultiCall.

Chaining multiple mocks is ok as long as thenReturn returns only the necessary mocks based on path, otherwise results in error. For example, if jobInfo2 is in catch block but test path is try then we have to return jobInfo and jobInfo3 and not jobInfo2.

         when(payload.getJobInfo()).thenReturn(jobInfo).thenReturn(jobInfo2).thenReturn(jobInfo3);

See: org.codetab.scoopi.step.Step.handover() method for refactor and test this. In else test it is proper!

## Same method call in Try and Catch blocks

When same method is invoked in try as well as in catch then verify never statement is generated for that method. Should generate verify times statement. See: itest.invoke.TryCatch.

## This in calls

The keyword this should be replaced with CUT. See cluster.hz.JobStore.close() method.

## Catch Multi Exception

Configurize whether to generate separate test method for each exception in catch clause or single test.

## Verify or VerifyZeroInteraction

Explore replacing multiple verify with single VerifyZeroInteraction when there is no interaction.

## Pseudo Getter

For methods similar to getter, uKnit wrongly calls setter method. See: itest.getset.detect.PseudoGetter.

## Stubbing final/private/equals()/hashCode

The org.mockito.exceptions.misusing.MissingMethodInvocationException: this error might show up because:
You stub either of: final/private/equals()/hashCode() methods.
   Those methods *cannot* be stubbed/verified.
   Mocking methods declared on non-public parent classes is not supported.

Fix: drop when statements for final/private/equals()/hashCode().

## Array

Tests are not proper when,
array is casted, test is not proper. See: jtest.array.ArrayType.java
array var with []. See: jtest.array.ArrayVarDim.java

## Creations

Generate initialization for exp.new creation. See: jtest.cic.ExpNewCreation.java

## Conditional

Enable ctl flow logic for missing test branch. See: jtest.conditional.ConditionalExp.java

## Continue and Break

Ignore statements after continue and break.

        public void foo(final List<String> list, final Log log) {
          for (String name : list) {
            if (name.equals("hello")) {
                break;  // or continue;
            }
            log.debug(name);
          }
        }

## Cast 

Return cast test is not proper. See: jtest.cast.CastInReturn.java

        

## Reorganise the old entries

Clean and reorganise the below old entries:

## Inner classes and interfaces

Test classes or interfaces nested in another class. 
For Inner classes and nested static classes see https://www.programiz.com/java-programming/nested-inner-class

## Lambda Functions

Test lambda functions. see org.codetab.scoopi.step.webdriver.DriverFactory.

## add test

	public StringBuilder argArgMethodInvoke(final StringBuilder s1,
            final File src, final File dest) {
        StringBuilder sb = s1.append(src.renameTo(dest.getAbsoluteFile()));
        return sb;
    }

    // assignments - expCarry test
    public String createString() {
        String a = new String("foo");
        String b = a;
        String c = b;
        String d = c;
        return d;
    }

	public int discardAssigment() {
        int[] array = new int[1];
        array[1] = 10;

        array[0] = array[1];
        array[0] = 3;
        int foo = array[0];
        return foo;
    }
    
    
Write Test for - list.stream().toArray(String[]::new)
    
## var args ...    
        
## matcher

	verify(pets).add(new Dog(apple)); to verify(pets).add(any(Dog.class));
	
see invoke.MiCreateInstanceIT
	
## Real returns real
	    
    public String notOk() {
        String path = "foo";
        return path.toLowerCase();
    }
    	    
## StringLiteral passed to private method is initialized to foo

see FileAppender init() method - String filePath = getPluginField("file");

Field creation in constructor - mock should not be injected 

    see org.codetab.scoopi.metrics.serialize.GaugeTest, creates mock for value and
    formatter
    
	public class Gauge implements Metric {
    
    private Map<String, Object> value;  
    private DateTimeFormatter formatter;

    public Gauge() {
        formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("H:m:s"))                
                .toFormatter();
    }

## POJO fields - mock should not be injected for gauges  
 
    public class Metrics {
    
    private Map<String, Gauge> gauges;

    public Map<String, Gauge> getGauges() {
        return gauges;
    }

    public void setGauges(final Map<String, Gauge> gauges) {
        this.gauges = gauges;
    }
    
## Public fields POJO

try to improve test for org.codetab.scoopi.metrics.serialize.Meter and Timer
    
## discard previous assignment

don't generate test for abstract classes

## junit 4 and 5 profiles

Fix errors by comparing with corrected tests in scoopi 

	plugin/converter/DateFormatter
	plugin/converter/DateRoller
	metrics/SystemStat	
	
## Try to Improve
		
	metrics/serialize/Serializer - Builder pattern
	metrics/server/MetricsServlet - Nested class test
	metrics/server/MetricsServlet - used static fields
	
## Fix error generating test

	metrics/server/MetricsServletListener - refers nested class in another class

## Qualified Names Var

Var accessed through qualified name should be spy instead of mock and when 
on such method invocation should be name part of qName. However, at present this is 
not supported. See QNames qNameAsCallMockObj() test as an example.

## Initialized Mocks

	private Map<String, Date> cache = new HashMap<>();

    public Date contains(final String key) {
        return cache.get(key);
    }

If map is user configured as mock, whether to treat initialized the mock as 
mock or real. Now, it is treated as mock. If treated as real then make it 
configurable.

## Multiple Tests for Branches

See notes in tech-doc folder. For test cases refer cluster.hz.JobStore, step.mediator.StateFliper

## Abstract Class

	Don't generate test class
	
## QNames

Create infer var, return and assert. See QNameIT.

	public String qNameInCast(final Point point) {
        return (String) point.attchment;
    }	

## Field and Parameter Same Name

Rename parameter name with index if field exists with same name. See superclass.MultiGetMock.getMulti(final Payload payload) method.
   
    
On the other hand for the following validation in step.parse.PrefixProcessor the coverage goes through without separate test. Analyze the reasons.

	notNull(value, "value must not be null");

## Improve Generated Tests

/step/src/test/java/org/codetab/scoopi/step/parse/QueryVarSubstitutorTest.java	

## Exceptions Thrown by MI

When exceptions are thrown by MI such as by when statement, add throws Exception to test method declaration.

    @Test
    public void testPostInitialize() {   
        when(htmlUnitFactory.createUrl(cherry)).thenReturn(url);

The MI createUrl throws MalformedException so change test method declaration to 
	
    @Test
    public void testPostInitialize() throws Exception {   

## Mock added to List	

When real list stream contains mocks try to create when for that. See org.codetab.scoopi.step.parse.htmlunit.QueryAnalyzer.getQueryElements() 

## Inserts

Improve inserts into collection got from super. See itest insert.SuperGetIT.

ForEach in two IMC with two loop vars with same name (key) wrongly renames second key as keyApple. See itest insert.ForEachMapsInternal.

## Treatment of super private fields.

Analyze whether to add super private field as @Mock in test class. See: itest insert.SuperGetIT for example.


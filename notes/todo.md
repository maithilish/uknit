
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

## Pojo fields - mock should not be injected for gauges  
 
    public class Metrics {
    
    private Map<String, Gauge> gauges;

    public Map<String, Gauge> getGauges() {
        return gauges;
    }

    public void setGauges(final Map<String, Gauge> gauges) {
        this.gauges = gauges;
    }
    
## Public fields Pojo 

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

## This in calls

The keyword this should be replaced with SUT. See cluster.hz.JobStore.close() method.

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
    
## Arg Validation and Control Flow

Arg Validations in step.parse.IndexerFactory throws Exceptions. For coverage separate test is required for this. Refactor control flow paths to handle this.

	validState((itemNames.indexOf("fact") == itemNames.size() - 1),
                "fact should be last item of the list");
    
On the other hand for the following validation in step.parse.PrefixProcessor the coverage goes through without separate test. Analyze the reasons.

	notNull(value, "value must not be null");

## Improve Generated Tests

/step/src/test/java/org/codetab/scoopi/step/parse/QueryVarSubstitutorTest.java	

## Ctl Flow

Complex if generates redundant tests - ifElseIfPlusIf
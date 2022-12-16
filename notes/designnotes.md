When and Verify are held by Invoke not Pack.

Abbrivations

Abbrivations used in uKnit code and documentation.

IMC - Internal Method Call
IM  - Internal Method
CUT - Class Under Test
MUT - Method Under Test


## Patch

Invoke Patch is used to patch invoke to infer var. It is created by post visit processor after creating infer vars. The patches are held by respectve Invoke pack.


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

## Java classes for Test

It is convenient to use custom interfaces defined in package private Model.java. Apart from it some of the suitable classes to use in itests - Instant, LocalDate, LocalDateTime, DateTimeFormatter, File, StringBuilder, Locale. Avoid Date as compare may randomly fail.

## Static Calls

Objects returned by statics calls are real, but in internal or super static calls the returned object may be mock if static call returns a mock field, parameter or mock object returned by mock field or parameter.




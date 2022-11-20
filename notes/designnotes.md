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

Some of the suitable classes to use in itests - Instant, LocalDate, Locale 
Date compare may fail randomly.

## Cast Change type

The varExp is found via cast exp and its left var is modified to cast type. However, 
if expression is Mi, the ExpReturnType in its invoker is not modified.   

## Hidden Var

By default, vars are not hidden. It is used while generating the statements.

  - Lambda or Annon expected var is hidden and assert statement is not generated.
  
## Setters

The setter local var may hide the field as uKnit doesn't check field name for duplicate while generating local vars. See - clz.Pojo.java

	private Date bar;
    public void setBar(final Date bar) {
        this.bar = bar;
    }  
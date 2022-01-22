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
    
## Stepins

Assertions on contents of objects or collections.

## Infer Var Patch

Infer vars are patched to expressions but direct modification to src node affects
the subsequent internal calls (private calls). Instead, keep the source node unmodified and create patch for replacement during visit and collect patches in heap. Defer actual patching to statement generate phase.

While generating statements, when, verify and initializer, the ExpPatcher.copyAndPatch() method creates copy of the source node and patch its infer expressions and copy is used to generate statements.

All visit processing happens on source AST nodes as they are resolvable while the nodes created with ASTNode.copySubtree() are not.

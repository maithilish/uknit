# AST Notes

## ASTNode

AnonymousClassDeclaration, BodyDeclaration, CatchClause, Comment, CompilationUnit, Dimension, Expression, ImportDeclaration, MemberRef, MemberValuePair, MethodRef, MethodRefParameter, Modifier, ModuleDeclaration, ModuleDirective, ModuleModifier, PackageDeclaration, Statement, TagElement, TextElement, Type, TypeParameter, VariableDeclaration

## Statement

The sequence of execution of a program is controlled by statements, which are executed for their effect and do not have values. 

AssertStatement, Block, BreakStatement, ConstructorInvocation, ContinueStatement, DoStatement, EmptyStatement, EnhancedForStatement, ExpressionStatement, ForStatement, IfStatement, LabeledStatement, ReturnStatement, SuperConstructorInvocation, SwitchCase, SwitchStatement, SynchronizedStatement, ThrowStatement, TryStatement, TypeDeclarationStatement, VariableDeclarationStatement, WhileStatement, YieldStatement

Expression Statements: Certain kinds of expressions may be used as statements by following them with semicolons. Assignment, MethodInvocation, ClassInstanceCreationExpression, pre and post increment and decrement.

## Expression

Much of the work in a program is done by evaluating expressions, either for their side effects, such as assignments to variables, or for their values, which can be used as arguments or operands in larger expressions, or to affect the execution sequence in statements, or both. 

When an expression in a program is evaluated (executed), the result denotes one of three things: variable,  value or nothing (the expression is said to be void). A method declared void can be used only as an expression statement, because every other context in which an expression can appear requires the expression to denote something. An expression statement that is a method invocation may also invoke a method that produces a result; in this case the value returned by the method is quietly discarded. 

Annotation, Assignment, CastExpression, ClassInstanceCreation, ConditionalExpression, InstanceofExpression, LambdaExpression,  MethodReference, Name, ParenthesizedExpression, PatternInstanceofExpression, SwitchExpression, TextBlock, ThisExpression, VariableDeclarationExpression

ArrayAccess, ArrayCreation, ArrayInitializer.
MethodInvocation, SuperMethodInvocation.
FieldAccess, SuperFieldAccess.
InfixExpression, PostfixExpression, PrefixExpression.
CharacterLiteral, NullLiteral, BooleanLiteral, NumberLiteral, StringLiteral, TypeLiteral.

## Primary Expression

        Primary:
            PrimaryNoNewArray
            ArrayCreationExpression
    
        PrimaryNoNewArray:
            Literal
            Type . class
            void . class
            this
            ClassName . this
            ( Expression )
            ClassInstanceCreationExpression
            FieldAccess
            MethodInvocation
            ArrayAccess

Primary expressions include most of the simplest kinds of expressions, from which all others are constructed: literals, class literals, field accesses, method invocations, and array accesses. A parenthesized expression is also treated syntactically as a primary expression. 

## MI



## Exp for MI, Return, Infer

ArrayCreation (with new), ArrayInitializer (no new), ArrayAccess, ClassInstanceCreation, ConditionalExpression, CastExpression, FieldAccess,


## AST Nodes used by uKnit

ArrayAccess
ArrayCreation
ArrayInitializer
ArrayType
BreakStatement, ContinueStatement
CastExpression
CatchClause
CompilationUnit
ConditionalExpression
CreationReference
Dimension
DoStatement, WhileStatement
EnhancedForStatement
ExpressionStatement
FieldAccess
FieldDeclaration

## No Impact AST Nodes 

AnnotationTypeDeclaration
AnnotationTypeMemberDeclaration
AssertStatement
Block
BlockComment
ConstructorInvocation
CompilationUnit
EmptyStatement
ExportsDirective
EmptyStatement
ModuleDeclaration
ModuleModifier
Javadoc
LabeledStatement
LineComment
MarkerAnnotation
NormalAnnotation
PackageDeclaration
ImportDeclaration

## Nodes

ForStatement
IfStatement

InfixExpression
Initializer
InstanceofExpression
IntersectionType

MemberRef
MemberValuePair
MethodRef
MethodRefParameter
MethodDeclaration
Modifier

NameQualifiedType

OpensDirective

ParameterizedType
ParenthesizedExpression
PatternInstanceofExpression
PostfixExpression
PrefixExpression
ProvidesDirective
PrimitiveType
QualifiedName
QualifiedType
ModuleQualifiedName
RequiresDirective
RecordDeclaration
ReturnStatement
SimpleName
SimpleType
SingleMemberAnnotation
SingleVariableDeclaration

SuperConstructorInvocation
SuperFieldAccess
SuperMethodInvocation
SuperMethodReference
SwitchCase
SwitchExpression
SwitchStatement
SynchronizedStatement
TagElement
TextBlock
TextElement
ThisExpression
ThrowStatement
TryStatement
TypeDeclaration
TypeDeclarationStatement
TypeLiteral
TypeMethodReference
TypeParameter
UnionType
UsesDirective
VariableDeclarationExpression
VariableDeclarationStatement
VariableDeclarationFragment
WildcardType
YieldStatement

## For Review

AnonymousClassDeclaration
BooleanLiteral, CharacterLiteral, NullLiteral, NumberLiteral, StringLiteral
Assignment
CatchClause
ContinueStatement
BreakStatement
Extra Dimensions in SingleVariableDeclaration, VariableDeclarationFragment
EnumConstantDeclaration
EnumDeclaration
ExpressionMethodReference
LambdaExpression

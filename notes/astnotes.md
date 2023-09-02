# AST Notes

## ASTNode

BodyDeclaration, CatchClause, Comment, CompilationUnit, ImportDeclaration, MemberRef, MemberValuePair, MethodRef, MethodRefParameter, Modifier, ModuleDeclaration, ModuleDirective, ModuleModifier, PackageDeclaration,  TagElement, TextElement,  TypeParameter.

uKnit test method deals with: Statement, Expression, Type, VariableDeclaration, Dimension, AnonymousClassDeclaration.

## Statement

The sequence of execution of a program is controlled by statements, which are executed for their effect and do not have values. 

AssertStatement, Block, BreakStatement, ConstructorInvocation, ContinueStatement, DoStatement, EmptyStatement, EnhancedForStatement, ExpressionStatement, ForStatement, IfStatement, LabeledStatement, ReturnStatement, SuperConstructorInvocation, SwitchCase, SwitchStatement, SynchronizedStatement, ThrowStatement, TryStatement, TypeDeclarationStatement, VariableDeclarationStatement, WhileStatement, YieldStatement

Expression Statements: Certain kinds of expressions may be used as statements by following them with semicolons. Assignment, MethodInvocation, ClassInstanceCreationExpression, pre and post increment and decrement.

## Expression

Much of the work in a program is done by evaluating expressions, either for their side effects, such as assignments to variables, or for their values, which can be used as arguments or operands in larger expressions, or to affect the execution sequence in statements, or both. 

When an expression in a program is evaluated (executed), the result denotes one of three things: variable,  value or nothing (the expression is said to be void). A method declared void can be used only as an expression statement, because every other context in which an expression can appear requires the expression to denote something. An expression statement that is a method invocation may also invoke a method that produces a result; in this case the value returned by the method is quietly discarded. 

Annotation, Assignment, CastExpression, ClassInstanceCreation, ConditionalExpression, InstanceofExpression, LambdaExpression,  MethodReference, Name, ParenthesizedExpression, PatternInstanceofExpression, SwitchExpression, TextBlock, ThisExpression, VariableDeclarationExpression

## Expression Type Hierarchy

Sub classes of Expression in JDT version 3.28.0. The items marked with -- are not used by uKnit till now.

        Expression
            Annotation 
                MarkerAnnotation    --
                NormalAnnotation    --
                SingleMemberAnnotation    --
            ArrayAccess
            ArrayCreation
            ArrayInitializer
            Assignment
            BooleanLiteral
            CaseDefaultExpression    --
            CastExpression
            CharacterLiteral
            ClassInstanceCreation
            ConditionalExpression
            FieldAccess
            InfixExpression
            InstanceofExpression
            LambdaExpression
            MethodInvocation
            MethodReference
                CreationReference    --
                ExpressionMethodReference
                SuperMethodReference    --
                TypeMethodReference    --
            Name
                ModuleQualifiedName    --
                QualifiedName
                SimpleName
            NullLiteral
            NumberLiteral
            ParenthesizedExpression
            Pattern
                GuardedPattern    --
                NullPattern    --
                TypePattern    --
            PatternInstanceofExpression    --
            PostfixExpression
            PrefixExpression
            StringLiteral
            SuperFieldAccess    --
            SuperMethodInvocation
            SwitchExpression    --
            TextBlock    --
            ThisExpression
            TypeLiteral
            VariableDeclarationExpression    --

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

## Expression Returns Value or Assignable to Var

ArrayAccess, ArrayCreation (with new), ArrayInitializer (no new).
MethodInvocation, SuperMethodInvocation.
FieldAccess, SuperFieldAccess.
InfixExpression, PostfixExpression, PrefixExpression.
CharacterLiteral, NullLiteral, BooleanLiteral, NumberLiteral, StringLiteral, TypeLiteral.
ClassInstanceCreation, 
ConditionalExpression
ReturnStatement
ThisExpression
TypeLiteral (Type.class.cast())

TODO - Cross check org.codetab.uknit.itest.exp.value.InstanceOf with list of expression and arrive final list.

## Expression No Returns Value nor Assignable to Var

Assignment, ArrayType (in ArrayDecl or cast), CastExpression, 

## Var

VariableDeclarationStatement, VariableDeclarationFragment
VariableDeclarationExpression (for stmt)
SingleVariableDeclaration (enhanced for, catch)
Extra Dimensions in SingleVariableDeclaration, VariableDeclarationFragment

## Anonymous and Lambda

LambdaExpression, AnonymousClassDeclaration
MethodReference: CreationReference, ExpressionMethodReference, SuperMethodReference, TypeMethodReference

## Generics

TypeParameter, ParameterizedType, IntersectionType, WildcardType

## Other AST Nodes used by uKnit

CompilationUnit, TypeDeclaration, TypeDeclarationStatement, EnumConstantDeclaration, EnumDeclaration
MethodDeclaration, FieldDeclaration, Modifier, NameQualifiedType, PrimitiveType, QualifiedName, QualifiedType
SimpleName, SimpleType, 
DoStatement, WhileStatement, ForStatement, EnhancedForStatement, IfStatement, BreakStatement, ContinueStatement, YieldStatement
SwitchCase, SwitchExpression, SwitchStatement
Dimension
ExpressionStatement, 
TryStatement, CatchClause, ThrowStatement, UnionType ( Ex | Ex)
TextBlock

## Not used AST Nodes 

InstanceofExpression, PatternInstanceofExpression
AnnotationTypeDeclaration, AnnotationTypeMemberDeclaration, MarkerAnnotation
NormalAnnotation, MemberValuePair,SingleMemberAnnotation
Block, EmptyStatement, LabeledStatement, EmptyStatement, AssertStatement
ModuleDeclaration, ModuleModifier, ExportsDirective, OpensDirective, ProvidesDirective, ModuleQualifiedName,
RequiresDirective, UsesDirective
PackageDeclaration, ImportDeclaration, Initializer
RecordDeclaration,
ConstructorInvocation, ParenthesizedExpression, SuperConstructorInvocation,
SynchronizedStatement

## Doc, Comment

Javadoc, LineComment, MethodRef, MemberRef, BlockComment, MethodRefParameter, TagElement, TextElement

## Infer

Creations

        internalCall(true, new String(""), "foo");

ExpressionMethodReference

        private <T> void setWriter(final BiConsumer<Logger, T> consumer,
            final T object, final Logger logger) {
        consumer.accept(logger, object);
        }

        public void setLogWriter(final PrintWriter writer,
            final Logger logger) {
        setWriter(Logger::setLogWriter, writer, logger);
        }

## FieldAccess vs QualifiedName

foo.id is QName and (foo).id is FieldAccess.

An expression like "foo.bar" can be represented either as a qualified name (QualifiedName) or as a field access expression (FieldAccess) containing simple names. Either is acceptable, and there is no way to choose between them without information about what the names resolve to (ASTParser may return either).





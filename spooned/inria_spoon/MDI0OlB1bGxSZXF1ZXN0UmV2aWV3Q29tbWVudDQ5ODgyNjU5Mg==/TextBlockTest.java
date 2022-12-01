[CompilationUnitImpl][CtPackageDeclarationImpl]package spoon.test.textBlocks;
[CtImportImpl]import spoon.reflect.code.CtInvocation;
[CtImportImpl]import spoon.reflect.code.CtStatement;
[CtImportImpl]import spoon.reflect.code.CtLiteral;
[CtImportImpl]import spoon.Launcher;
[CtUnresolvedImport]import static org.junit.Assert.assertEquals;
[CtUnresolvedImport]import org.junit.Test;
[CtImportImpl]import spoon.reflect.code.CtTry;
[CtImportImpl]import spoon.reflect.declaration.CtClass;
[CtImportImpl]import spoon.reflect.declaration.CtMethod;
[CtClassImpl]public class TextBlockTest {
    [CtMethodImpl]private [CtTypeReferenceImpl]spoon.Launcher setUpTest() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]spoon.Launcher launcher = [CtConstructorCallImpl]new [CtTypeReferenceImpl]spoon.Launcher();
        [CtInvocationImpl][CtVariableReadImpl]launcher.addInputResource([CtLiteralImpl]"./src/test/resources/textBlock/TextBlockTestClass.java");
        [CtInvocationImpl][CtVariableReadImpl]launcher.run();
        [CtReturnImpl]return [CtVariableReadImpl]launcher;
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testTextBlock() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]spoon.Launcher launcher = [CtInvocationImpl]setUpTest();
        [CtLocalVariableImpl][CtTypeReferenceImpl]spoon.reflect.declaration.CtClass<[CtWildcardReferenceImpl]?> allstmt = [CtInvocationImpl](([CtTypeReferenceImpl]spoon.reflect.declaration.CtClass<[CtWildcardReferenceImpl]?>) ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]launcher.getFactory().Type().get([CtLiteralImpl]"textBlock.TextBlockTestClass")));
        [CtLocalVariableImpl][CtTypeReferenceImpl]spoon.reflect.declaration.CtMethod<[CtWildcardReferenceImpl]?> m1 = [CtInvocationImpl][CtVariableReadImpl]allstmt.getMethod([CtLiteralImpl]"m1");
        [CtLocalVariableImpl][CtTypeReferenceImpl]spoon.reflect.code.CtStatement stmt1 = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]m1.getBody().getStatement([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]spoon.reflect.code.CtLiteral l1 = [CtInvocationImpl](([CtTypeReferenceImpl]spoon.reflect.code.CtLiteral) ([CtInvocationImpl][CtVariableReadImpl]stmt1.getDirectChildren().get([CtLiteralImpl]1)));
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]l1.getValue(), [CtLiteralImpl]"<html>\n    <body>\n        <p>Hello, world</p>\n    </body>\n</html>\n");
        [CtLocalVariableImpl][CtTypeReferenceImpl]spoon.reflect.code.CtStatement stmt2 = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]m1.getBody().getStatement([CtLiteralImpl]1);
        [CtLocalVariableImpl][CtTypeReferenceImpl]spoon.reflect.code.CtLiteral l2 = [CtInvocationImpl](([CtTypeReferenceImpl]spoon.reflect.code.CtLiteral) ([CtInvocationImpl][CtVariableReadImpl]stmt2.getDirectChildren().get([CtLiteralImpl]1)));
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]l2.getValue(), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"SELECT \"EMP_ID\", \"LAST_NAME\" FROM \"EMPLOYEE_TB\"\n" + [CtLiteralImpl]"WHERE \"CITY\" = \'INDIANAPOLIS\'\n") + [CtLiteralImpl]"ORDER BY \"EMP_ID\", \"LAST_NAME\";\n");
        [CtLocalVariableImpl][CtTypeReferenceImpl]spoon.reflect.code.CtTry stmt4 = [CtInvocationImpl](([CtTypeReferenceImpl]spoon.reflect.code.CtTry) ([CtInvocationImpl][CtVariableReadImpl]m1.getBody().getStatement([CtLiteralImpl]3)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]spoon.reflect.code.CtStatement stmt5 = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]stmt4.getBody().getStatement([CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]spoon.reflect.code.CtInvocation inv = [CtInvocationImpl](([CtTypeReferenceImpl]spoon.reflect.code.CtInvocation) ([CtInvocationImpl][CtVariableReadImpl]stmt5.getDirectChildren().get([CtLiteralImpl]1)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]spoon.reflect.code.CtLiteral l3 = [CtInvocationImpl](([CtTypeReferenceImpl]spoon.reflect.code.CtLiteral) ([CtInvocationImpl][CtVariableReadImpl]inv.getArguments().get([CtLiteralImpl]0)));
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]l3.getValue(), [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"function hello() {\n" + [CtLiteralImpl]"    print(\'\"Hello, world\"\');\n") + [CtLiteralImpl]"}\n") + [CtLiteralImpl]"\n") + [CtLiteralImpl]"hello();\n") + [CtLiteralImpl]"");
    }
}
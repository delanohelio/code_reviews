[CompilationUnitImpl][CtPackageDeclarationImpl]package de.bonndan.nivio.util;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtImportImpl]import java.net.URL;
[CtImportImpl]import java.net.MalformedURLException;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertTrue;
[CtImportImpl]import java.nio.file.Paths;
[CtImportImpl]import java.io.File;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertEquals;
[CtImportImpl]import org.apache.commons.lang3.SystemUtils;
[CtClassImpl]class URLHelperTest {
    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void test_getURL() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String root = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtLiteralImpl]"").toAbsolutePath().toString();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URL url = [CtInvocationImpl][CtTypeAccessImpl]de.bonndan.nivio.util.URLHelper.getURL([CtBinaryOperatorImpl][CtVariableReadImpl]root + [CtLiteralImpl]"/src/test/resources/example/example_templates.yml");
        [CtIfImpl]if ([CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.SystemUtils.[CtFieldReferenceImpl]IS_OS_WINDOWS) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]root = [CtBinaryOperatorImpl][CtLiteralImpl]"/" + [CtInvocationImpl][CtVariableReadImpl]root.replace([CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator, [CtLiteralImpl]"/");
        }
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]url != [CtLiteralImpl]null;
        [CtInvocationImpl]assertEquals([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"file:" + [CtVariableReadImpl]root) + [CtLiteralImpl]"/src/test/resources/example/example_templates.yml", [CtInvocationImpl][CtVariableReadImpl]url.toString());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void test_getURL_relativePath() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String root = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtLiteralImpl]"").toAbsolutePath().toString();
        [CtIfImpl]if ([CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.SystemUtils.[CtFieldReferenceImpl]IS_OS_WINDOWS) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]root = [CtBinaryOperatorImpl][CtLiteralImpl]"/" + [CtInvocationImpl][CtVariableReadImpl]root.replace([CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator, [CtLiteralImpl]"/");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URL url = [CtInvocationImpl][CtTypeAccessImpl]de.bonndan.nivio.util.URLHelper.getURL([CtLiteralImpl]"src/test/resources/example/example_templates.yml");
        [CtAssertImpl]assert [CtBinaryOperatorImpl][CtVariableReadImpl]url != [CtLiteralImpl]null;
        [CtInvocationImpl]assertEquals([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"file:" + [CtVariableReadImpl]root) + [CtLiteralImpl]"/src/test/resources/example/example_templates.yml", [CtInvocationImpl][CtVariableReadImpl]url.toString());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void test_splitQuery() throws [CtTypeReferenceImpl]java.net.MalformedURLException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URL url = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtLiteralImpl]"http://192.168.99.100:8080?namespace=default&groupLabel=release");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> actual = [CtInvocationImpl][CtTypeAccessImpl]de.bonndan.nivio.util.URLHelper.splitQuery([CtVariableReadImpl]url);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtVariableReadImpl]actual.size());
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]actual.containsKey([CtLiteralImpl]"namespace"));
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]actual.containsKey([CtLiteralImpl]"groupLabel"));
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"default", [CtInvocationImpl][CtVariableReadImpl]actual.get([CtLiteralImpl]"namespace"));
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"release", [CtInvocationImpl][CtVariableReadImpl]actual.get([CtLiteralImpl]"groupLabel"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void test_splitQuery_withoutQuery() throws [CtTypeReferenceImpl]java.net.MalformedURLException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URL url = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtLiteralImpl]"http://192.168.99.100:8080");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> actual = [CtInvocationImpl][CtTypeAccessImpl]de.bonndan.nivio.util.URLHelper.splitQuery([CtVariableReadImpl]url);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]actual.size());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void test_splitQuery_badQuery() throws [CtTypeReferenceImpl]java.net.MalformedURLException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URL url = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtLiteralImpl]"http://192.168.99.100:8080?foo=");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> actual = [CtInvocationImpl][CtTypeAccessImpl]de.bonndan.nivio.util.URLHelper.splitQuery([CtVariableReadImpl]url);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]0, [CtInvocationImpl][CtVariableReadImpl]actual.size());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void test_combine() throws [CtTypeReferenceImpl]java.net.MalformedURLException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String root = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtLiteralImpl]"").toAbsolutePath().toString();
        [CtIfImpl]if ([CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.SystemUtils.[CtFieldReferenceImpl]IS_OS_WINDOWS) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]root = [CtBinaryOperatorImpl][CtLiteralImpl]"/" + [CtInvocationImpl][CtVariableReadImpl]root.replace([CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator, [CtLiteralImpl]"/");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URL baseUrl = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"file:" + [CtVariableReadImpl]root) + [CtLiteralImpl]"/src/test/resources/example/");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String part = [CtLiteralImpl]"./services/wordpress.yml";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String actual = [CtInvocationImpl][CtTypeAccessImpl]de.bonndan.nivio.util.URLHelper.combine([CtVariableReadImpl]baseUrl, [CtVariableReadImpl]part);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String expected = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"file:" + [CtVariableReadImpl]root) + [CtLiteralImpl]"/src/test/resources/example/services/wordpress.yml";
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]expected, [CtVariableReadImpl]actual);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void test_combine_absolute_part() throws [CtTypeReferenceImpl]java.net.MalformedURLException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String root = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtLiteralImpl]"").toAbsolutePath().toString();
        [CtIfImpl]if ([CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.SystemUtils.[CtFieldReferenceImpl]IS_OS_WINDOWS) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]root = [CtBinaryOperatorImpl][CtLiteralImpl]"/" + [CtInvocationImpl][CtVariableReadImpl]root.replace([CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator, [CtLiteralImpl]"/");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URL baseUrl = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"file:" + [CtVariableReadImpl]root) + [CtLiteralImpl]"/src/test/resources/example/");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String part = [CtLiteralImpl]"http://192.168.99.100:8080";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String actual = [CtInvocationImpl][CtTypeAccessImpl]de.bonndan.nivio.util.URLHelper.combine([CtVariableReadImpl]baseUrl, [CtVariableReadImpl]part);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String expected = [CtLiteralImpl]"http://192.168.99.100:8080";
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]expected, [CtVariableReadImpl]actual);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void test_combine_absolute_part2() throws [CtTypeReferenceImpl]java.net.MalformedURLException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String root = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.nio.file.Paths.get([CtLiteralImpl]"").toAbsolutePath().toString();
        [CtIfImpl]if ([CtFieldReadImpl][CtTypeAccessImpl]org.apache.commons.lang3.SystemUtils.[CtFieldReferenceImpl]IS_OS_WINDOWS) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]root = [CtBinaryOperatorImpl][CtLiteralImpl]"/" + [CtInvocationImpl][CtVariableReadImpl]root.replace([CtFieldReadImpl][CtTypeAccessImpl]java.io.File.[CtFieldReferenceImpl]separator, [CtLiteralImpl]"/");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.net.URL baseUrl = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"file:" + [CtVariableReadImpl]root) + [CtLiteralImpl]"/src/test/resources/example/");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String part = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"file:" + [CtVariableReadImpl]root) + [CtLiteralImpl]"/src/test/resources/example/example_templates.yml";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String actual = [CtInvocationImpl][CtTypeAccessImpl]de.bonndan.nivio.util.URLHelper.combine([CtVariableReadImpl]baseUrl, [CtVariableReadImpl]part);
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]part, [CtVariableReadImpl]actual);
    }
}
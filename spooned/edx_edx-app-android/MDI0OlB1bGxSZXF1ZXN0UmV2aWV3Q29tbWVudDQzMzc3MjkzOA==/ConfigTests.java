[CompilationUnitImpl][CtPackageDeclarationImpl]package org.edx.mobile.test;
[CtUnresolvedImport]import com.google.gson.JsonObject;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import static org.junit.Assert.assertNotNull;
[CtUnresolvedImport]import org.junit.runner.RunWith;
[CtUnresolvedImport]import org.junit.runners.Parameterized.Parameters;
[CtUnresolvedImport]import static org.junit.Assert.assertTrue;
[CtUnresolvedImport]import static org.junit.Assert.assertFalse;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import org.edx.mobile.util.Config;
[CtUnresolvedImport]import com.google.gson.JsonArray;
[CtUnresolvedImport]import static org.junit.Assert.assertNull;
[CtUnresolvedImport]import static org.junit.Assert.assertEquals;
[CtUnresolvedImport]import com.google.gson.JsonPrimitive;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.junit.runners.Parameterized;
[CtClassImpl][CtJavaDocImpl]/**
 * Created by aleffert on 2/6/15.
 */
public class ConfigTests extends [CtTypeReferenceImpl]org.edx.mobile.test.BaseTestCase {
    [CtFieldImpl][CtCommentImpl]// TODO - should we place constant at a central place?
    [CtCommentImpl]/* Config keys */
    private static final [CtTypeReferenceImpl]java.lang.String DISCOVERY = [CtLiteralImpl]"DISCOVERY";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String COURSE_DISCOVERY = [CtLiteralImpl]"COURSE";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SOCIAL_SHARING = [CtLiteralImpl]"SOCIAL_SHARING";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ZERO_RATING = [CtLiteralImpl]"ZERO_RATING";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String FACEBOOK = [CtLiteralImpl]"FACEBOOK";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String GOOGLE = [CtLiteralImpl]"GOOGLE";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String NEW_RELIC = [CtLiteralImpl]"NEW_RELIC";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SEGMENT_IO = [CtLiteralImpl]"SEGMENT_IO";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String WHITE_LIST_OF_DOMAINS = [CtLiteralImpl]"WHITE_LIST_OF_DOMAINS";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BRANCH = [CtLiteralImpl]"BRANCH";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ENABLED = [CtLiteralImpl]"ENABLED";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DISABLED_CARRIERS = [CtLiteralImpl]"DISABLED_CARRIERS";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CARRIERS = [CtLiteralImpl]"CARRIERS";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String BASE_URL = [CtLiteralImpl]"BASE_URL";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String EXPLORE_SUBJECTS_URL = [CtLiteralImpl]"EXPLORE_SUBJECTS_URL";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String TYPE = [CtLiteralImpl]"TYPE";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DETAIL_TEMPLATE = [CtLiteralImpl]"DETAIL_TEMPLATE";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String FACEBOOK_APP_ID = [CtLiteralImpl]"FACEBOOK_APP_ID";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String KEY = [CtLiteralImpl]"KEY";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SECRET = [CtLiteralImpl]"SECRET";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String KITS = [CtLiteralImpl]"KITS";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String CRASHLYTICS = [CtLiteralImpl]"CRASHLYTICS";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ANSWERS = [CtLiteralImpl]"ANSWERS";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String NEW_RELIC_KEY = [CtLiteralImpl]"NEW_RELIC_KEY";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SEGMENT_IO_WRITE_KEY = [CtLiteralImpl]"SEGMENT_IO_WRITE_KEY";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String DOMAINS = [CtLiteralImpl]"DOMAINS";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PARSE = [CtLiteralImpl]"PARSE";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PARSE_ENABLED = [CtLiteralImpl]"NOTIFICATIONS_ENABLED";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PARSE_APPLICATION_ID = [CtLiteralImpl]"APPLICATION_ID";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String PARSE_CLIENT_KEY = [CtLiteralImpl]"CLIENT_KEY";

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testZeroRatingNoConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getZeroRatingConfig().isEnabled());
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getZeroRatingConfig().getCarriers().size(), [CtLiteralImpl]0);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testZeroRatingEmptyConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject socialConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.ZERO_RATING, [CtVariableReadImpl]socialConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getZeroRatingConfig().isEnabled());
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getZeroRatingConfig().getCarriers().size(), [CtLiteralImpl]0);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testZeroRatingConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject zeroRatingConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]zeroRatingConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.ENABLED, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtLiteralImpl]true));
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.ZERO_RATING, [CtVariableReadImpl]zeroRatingConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> carrierList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String>();
        [CtInvocationImpl][CtVariableReadImpl]carrierList.add([CtLiteralImpl]"12345");
        [CtInvocationImpl][CtVariableReadImpl]carrierList.add([CtLiteralImpl]"foo");
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonArray carriers = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonArray();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String carrier : [CtVariableReadImpl]carrierList) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]carriers.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtVariableReadImpl]carrier));
        }
        [CtInvocationImpl][CtVariableReadImpl]zeroRatingConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.CARRIERS, [CtVariableReadImpl]carriers);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> domainList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]domainList.add([CtLiteralImpl]"domain1");
        [CtInvocationImpl][CtVariableReadImpl]domainList.add([CtLiteralImpl]"domain2");
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonArray domains = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonArray();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String domain : [CtVariableReadImpl]domainList) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]domains.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtVariableReadImpl]domain));
        }
        [CtInvocationImpl][CtVariableReadImpl]zeroRatingConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.WHITE_LIST_OF_DOMAINS, [CtVariableReadImpl]domains);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getZeroRatingConfig().isEnabled());
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]carrierList, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getZeroRatingConfig().getCarriers());
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]domainList, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getZeroRatingConfig().getWhiteListedDomains());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testEnrollmentNoConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertNotNull([CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getCourseDiscoveryConfig());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getProgramDiscoveryConfig());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testEnrollmentEmptyConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject discoveryConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.DISCOVERY, [CtVariableReadImpl]discoveryConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject courseDiscoveryConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]discoveryConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.COURSE_DISCOVERY, [CtVariableReadImpl]courseDiscoveryConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getCourseDiscoveryConfig().isDiscoveryEnabled());
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getCourseDiscoveryConfig().isWebviewDiscoveryEnabled());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getCourseDiscoveryConfig().getBaseUrl());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getCourseDiscoveryConfig().getInfoUrlTemplate());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]java.lang.IllegalArgumentException.class)
    public [CtTypeReferenceImpl]void testEnrollmentInvalidType() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject discoveryConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.DISCOVERY, [CtVariableReadImpl]discoveryConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject courseDiscoveryConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]discoveryConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.COURSE_DISCOVERY, [CtVariableReadImpl]courseDiscoveryConfig);
        [CtInvocationImpl][CtVariableReadImpl]courseDiscoveryConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.TYPE, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtLiteralImpl]"invalid type"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getCourseDiscoveryConfig().isDiscoveryEnabled());
    }

    [CtClassImpl][CtAnnotationImpl]@org.junit.runner.RunWith([CtFieldReadImpl]org.junit.runners.Parameterized.class)
    public static class EnrollmentConfigTests {
        [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String course_enrollment_type;

        [CtFieldImpl]private [CtTypeReferenceImpl]boolean expected;

        [CtConstructorImpl]public EnrollmentConfigTests([CtParameterImpl][CtTypeReferenceImpl]java.lang.String course_enrollment_type, [CtParameterImpl][CtTypeReferenceImpl]boolean expected) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.course_enrollment_type = [CtVariableReadImpl]course_enrollment_type;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.expected = [CtVariableReadImpl]expected;
        }

        [CtMethodImpl][CtAnnotationImpl]@org.junit.runners.Parameterized.Parameters(name = [CtLiteralImpl]"{index}: willUseWebview({0})={1}")
        public static [CtTypeReferenceImpl]java.lang.Iterable<[CtArrayTypeReferenceImpl]java.lang.Object[]> data() [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[][]{ [CtNewArrayImpl]new java.lang.Object[]{ [CtLiteralImpl]"webview", [CtLiteralImpl]true }, [CtNewArrayImpl]new java.lang.Object[]{ [CtLiteralImpl]"WEBVIEW", [CtLiteralImpl]true }, [CtNewArrayImpl]new java.lang.Object[]{ [CtLiteralImpl]"native", [CtLiteralImpl]false }, [CtNewArrayImpl]new java.lang.Object[]{ [CtLiteralImpl]"NATIVE", [CtLiteralImpl]false } });
        }

        [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
        public [CtTypeReferenceImpl]void testEnrollmentConfig() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject discoveryConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
            [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.DISCOVERY, [CtVariableReadImpl]discoveryConfig);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject courseDiscoveryConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
            [CtInvocationImpl][CtVariableReadImpl]discoveryConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.COURSE_DISCOVERY, [CtVariableReadImpl]courseDiscoveryConfig);
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject webviewConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
            [CtInvocationImpl][CtVariableReadImpl]courseDiscoveryConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.TYPE, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtFieldReadImpl]course_enrollment_type));
            [CtInvocationImpl][CtVariableReadImpl]webviewConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.BASE_URL, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtLiteralImpl]"fake-url"));
            [CtInvocationImpl][CtVariableReadImpl]webviewConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.DETAIL_TEMPLATE, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtLiteralImpl]"fake-url-template"));
            [CtInvocationImpl][CtVariableReadImpl]courseDiscoveryConfig.add([CtLiteralImpl]"WEBVIEW", [CtVariableReadImpl]webviewConfig);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
            [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getCourseDiscoveryConfig().isDiscoveryEnabled());
            [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getCourseDiscoveryConfig().isWebviewDiscoveryEnabled(), [CtFieldReadImpl]expected);
            [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getCourseDiscoveryConfig().getBaseUrl(), [CtLiteralImpl]"fake-url");
            [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getCourseDiscoveryConfig().getInfoUrlTemplate(), [CtLiteralImpl]"fake-url-template");
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testEnrollmentConfig_withExploreSubjectsEnabled() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject discoveryConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.DISCOVERY, [CtVariableReadImpl]discoveryConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject courseDiscoveryConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]discoveryConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.COURSE_DISCOVERY, [CtVariableReadImpl]courseDiscoveryConfig);
        [CtInvocationImpl][CtVariableReadImpl]courseDiscoveryConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.TYPE, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtLiteralImpl]"invalid type"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject webviewConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]courseDiscoveryConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.TYPE, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtLiteralImpl]"WEBVIEW"));
        [CtInvocationImpl][CtVariableReadImpl]webviewConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.BASE_URL, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtLiteralImpl]"fake-url"));
        [CtInvocationImpl][CtVariableReadImpl]webviewConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.EXPLORE_SUBJECTS_URL, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtLiteralImpl]"explore-subjects-url"));
        [CtInvocationImpl][CtVariableReadImpl]webviewConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.DETAIL_TEMPLATE, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtLiteralImpl]"fake-url-template"));
        [CtInvocationImpl][CtVariableReadImpl]courseDiscoveryConfig.add([CtLiteralImpl]"WEBVIEW", [CtVariableReadImpl]webviewConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getCourseDiscoveryConfig().isDiscoveryEnabled());
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getCourseDiscoveryConfig().isWebviewDiscoveryEnabled());
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getCourseDiscoveryConfig().getBaseUrl(), [CtLiteralImpl]"fake-url");
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getDiscoveryConfig().getCourseDiscoveryConfig().getInfoUrlTemplate(), [CtLiteralImpl]"fake-url-template");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testFacebookNoConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getFacebookConfig().isEnabled());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getFacebookConfig().getFacebookAppId());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testFacebookEmptyConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject fbConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.FACEBOOK, [CtVariableReadImpl]fbConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getFacebookConfig().isEnabled());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getFacebookConfig().getFacebookAppId());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testFacebookConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String appId = [CtLiteralImpl]"fake-app-id";
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject fbConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]fbConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.ENABLED, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtLiteralImpl]true));
        [CtInvocationImpl][CtVariableReadImpl]fbConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.FACEBOOK_APP_ID, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtVariableReadImpl]appId));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.FACEBOOK, [CtVariableReadImpl]fbConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getFacebookConfig().isEnabled());
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]appId, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getFacebookConfig().getFacebookAppId());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGoogleNoConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getGoogleConfig().isEnabled());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGoogleEmptyConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject googleConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.GOOGLE, [CtVariableReadImpl]googleConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getGoogleConfig().isEnabled());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGoogleConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject googleConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]googleConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.ENABLED, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtLiteralImpl]true));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.GOOGLE, [CtVariableReadImpl]googleConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getGoogleConfig().isEnabled());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testBranchNoConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getBranchConfig().isEnabled());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getBranchConfig().getKey());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getBranchConfig().getSecret());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testBranchEmptyConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject branchConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.BRANCH, [CtVariableReadImpl]branchConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getBranchConfig().isEnabled());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getBranchConfig().getKey());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getBranchConfig().getSecret());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testBranchConfig() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String key = [CtLiteralImpl]"fake-key";
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String secret = [CtLiteralImpl]"fake-secret";
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject branchConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]branchConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.ENABLED, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtLiteralImpl]true));
        [CtInvocationImpl][CtVariableReadImpl]branchConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.KEY, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtVariableReadImpl]key));
        [CtInvocationImpl][CtVariableReadImpl]branchConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.SECRET, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtVariableReadImpl]secret));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.BRANCH, [CtVariableReadImpl]branchConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getBranchConfig().isEnabled());
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]key, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getBranchConfig().getKey());
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]secret, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getBranchConfig().getSecret());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testNewRelicNoConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getNewRelicConfig().isEnabled());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getNewRelicConfig().getNewRelicKey());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testNewRelicEmptyConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject fabricConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.NEW_RELIC, [CtVariableReadImpl]fabricConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getNewRelicConfig().isEnabled());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getNewRelicConfig().getNewRelicKey());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testNewRelicConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key = [CtLiteralImpl]"fake-key";
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject newRelicConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]newRelicConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.ENABLED, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtLiteralImpl]true));
        [CtInvocationImpl][CtVariableReadImpl]newRelicConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.NEW_RELIC_KEY, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtVariableReadImpl]key));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.NEW_RELIC, [CtVariableReadImpl]newRelicConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getNewRelicConfig().isEnabled());
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]key, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getNewRelicConfig().getNewRelicKey());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testSegmentNoConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getSegmentConfig().isEnabled());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getSegmentConfig().getSegmentWriteKey());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testSegmentEmptyConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject segmentConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.SEGMENT_IO, [CtVariableReadImpl]segmentConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getSegmentConfig().isEnabled());
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getSegmentConfig().getSegmentWriteKey());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testSegmentConfig() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key = [CtLiteralImpl]"fake-key";
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject segmentConfig = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]segmentConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.ENABLED, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtLiteralImpl]true));
        [CtInvocationImpl][CtVariableReadImpl]segmentConfig.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.SEGMENT_IO_WRITE_KEY, [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonPrimitive([CtVariableReadImpl]key));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.google.gson.JsonObject configBase = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.google.gson.JsonObject();
        [CtInvocationImpl][CtVariableReadImpl]configBase.add([CtFieldReadImpl]org.edx.mobile.test.ConfigTests.SEGMENT_IO, [CtVariableReadImpl]segmentConfig);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.edx.mobile.util.Config config = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.edx.mobile.util.Config([CtVariableReadImpl]configBase);
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getSegmentConfig().isEnabled());
        [CtInvocationImpl]Assert.assertEquals([CtVariableReadImpl]key, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]config.getSegmentConfig().getSegmentWriteKey());
    }
}
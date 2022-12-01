[CompilationUnitImpl][CtPackageDeclarationImpl]package gov.cms.dpc.api;
[CtUnresolvedImport]import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
[CtUnresolvedImport]import org.apache.http.client.methods.HttpPost;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import gov.cms.dpc.fhir.validations.dropwizard.InjectingConstraintValidatorFactory;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.hapi.ctx.DefaultProfileValidationSupport;
[CtUnresolvedImport]import ca.uhn.fhir.rest.client.api.ServerValidationModeEnum;
[CtUnresolvedImport]import gov.cms.dpc.fhir.dropwizard.handlers.exceptions.DefaultFHIRExceptionHandler;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.model.*;
[CtUnresolvedImport]import gov.cms.dpc.fhir.validations.DPCProfileSupport;
[CtUnresolvedImport]import gov.cms.dpc.fhir.dropwizard.handlers.exceptions.JerseyExceptionHandler;
[CtUnresolvedImport]import ca.uhn.fhir.context.FhirContext;
[CtUnresolvedImport]import gov.cms.dpc.api.auth.OrganizationPrincipal;
[CtUnresolvedImport]import io.dropwizard.testing.junit5.ResourceExtension;
[CtUnresolvedImport]import org.apache.http.client.methods.CloseableHttpResponse;
[CtUnresolvedImport]import gov.cms.dpc.fhir.validations.dropwizard.FHIRValidatorProvider;
[CtUnresolvedImport]import com.typesafe.config.ConfigFactory;
[CtUnresolvedImport]import javax.validation.Validation;
[CtUnresolvedImport]import ca.uhn.fhir.rest.client.api.IGenericClient;
[CtUnresolvedImport]import org.eclipse.jetty.http.HttpStatus;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import gov.cms.dpc.fhir.dropwizard.handlers.exceptions.HAPIExceptionHandler;
[CtUnresolvedImport]import static org.junit.jupiter.api.Assertions.assertEquals;
[CtUnresolvedImport]import ca.uhn.fhir.parser.IParser;
[CtImportImpl]import com.fasterxml.jackson.databind.ObjectMapper;
[CtUnresolvedImport]import gov.cms.dpc.fhir.dropwizard.handlers.exceptions.PersistenceExceptionHandler;
[CtUnresolvedImport]import org.apache.http.impl.client.CloseableHttpClient;
[CtUnresolvedImport]import io.dropwizard.testing.DropwizardTestSupport;
[CtImportImpl]import java.io.InputStream;
[CtUnresolvedImport]import gov.cms.dpc.api.exceptions.JsonParseExceptionMapper;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import javax.validation.Validator;
[CtUnresolvedImport]import gov.cms.dpc.fhir.dropwizard.handlers.BundleHandler;
[CtUnresolvedImport]import gov.cms.dpc.fhir.validations.ProfileValidator;
[CtImportImpl]import java.sql.Date;
[CtUnresolvedImport]import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
[CtUnresolvedImport]import org.hl7.fhir.dstu3.hapi.validation.ValidationSupportChain;
[CtUnresolvedImport]import org.apache.http.client.methods.HttpGet;
[CtUnresolvedImport]import gov.cms.dpc.fhir.configuration.DPCFHIRConfiguration;
[CtUnresolvedImport]import gov.cms.dpc.fhir.dropwizard.handlers.FHIRHandler;
[CtUnresolvedImport]import gov.cms.dpc.fhir.DPCIdentifierSystem;
[CtUnresolvedImport]import org.apache.http.impl.client.HttpClients;
[CtClassImpl]public class APITestHelpers {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ATTRIBUTION_URL = [CtLiteralImpl]"http://localhost:3500/v1";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ORGANIZATION_ID = [CtLiteralImpl]"46ac7ad6-7487-4dd0-baa0-6e2c8cae76a0";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String ATTRIBUTION_TRUNCATE_TASK = [CtLiteralImpl]"http://localhost:9902/tasks/truncate";

    [CtFieldImpl]public static [CtTypeReferenceImpl]java.lang.String BASE_URL = [CtLiteralImpl]"https://dpc.cms.gov/api";

    [CtFieldImpl]public static [CtTypeReferenceImpl]java.lang.String ORGANIZATION_NPI = [CtLiteralImpl]"1111111112";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper mapper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.fasterxml.jackson.databind.ObjectMapper();

    [CtConstructorImpl]private APITestHelpers() [CtBlockImpl]{
        [CtCommentImpl]// Not used
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]gov.cms.dpc.api.auth.OrganizationPrincipal makeOrganizationPrincipal() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Organization org = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Organization();
        [CtInvocationImpl][CtVariableReadImpl]org.setId([CtFieldReadImpl]gov.cms.dpc.api.APITestHelpers.ORGANIZATION_ID);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.dpc.api.auth.OrganizationPrincipal([CtVariableReadImpl]org);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]gov.cms.dpc.api.auth.OrganizationPrincipal makeOrganizationPrincipal([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Organization org = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Organization();
        [CtInvocationImpl][CtVariableReadImpl]org.setId([CtVariableReadImpl]id);
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.dpc.api.auth.OrganizationPrincipal([CtVariableReadImpl]org);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]ca.uhn.fhir.rest.client.api.IGenericClient buildAttributionClient([CtParameterImpl][CtTypeReferenceImpl]ca.uhn.fhir.context.FhirContext ctx) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctx.getRestfulClientFactory().setServerValidationMode([CtTypeAccessImpl]ServerValidationModeEnum.NEVER);
        [CtLocalVariableImpl][CtTypeReferenceImpl]ca.uhn.fhir.rest.client.api.IGenericClient client = [CtInvocationImpl][CtVariableReadImpl]ctx.newRestfulGenericClient([CtFieldReadImpl]gov.cms.dpc.api.APITestHelpers.ATTRIBUTION_URL);
        [CtLocalVariableImpl][CtCommentImpl]// Disable logging for tests
        [CtTypeReferenceImpl]ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor loggingInterceptor = [CtConstructorCallImpl]new [CtTypeReferenceImpl]ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor();
        [CtInvocationImpl][CtVariableReadImpl]loggingInterceptor.setLogRequestSummary([CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]loggingInterceptor.setLogResponseSummary([CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]client.registerInterceptor([CtVariableReadImpl]loggingInterceptor);
        [CtReturnImpl]return [CtVariableReadImpl]client;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void setupPractitionerTest([CtParameterImpl][CtTypeReferenceImpl]ca.uhn.fhir.rest.client.api.IGenericClient client, [CtParameterImpl][CtTypeReferenceImpl]ca.uhn.fhir.parser.IParser parser) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream inputStream = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]gov.cms.dpc.api.APITestHelpers.class.getClassLoader().getResourceAsStream([CtLiteralImpl]"provider_bundle.json")) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]Parameters providerParameters = [CtInvocationImpl](([CtTypeReferenceImpl]Parameters) ([CtVariableReadImpl]parser.parseResource([CtVariableReadImpl]inputStream)));
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]client.operation().onType([CtFieldReadImpl]gov.cms.dpc.api.Practitioner.class).named([CtLiteralImpl]"submit").withParameters([CtVariableReadImpl]providerParameters).encodedJson().execute();
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void setupPatientTest([CtParameterImpl][CtTypeReferenceImpl]ca.uhn.fhir.rest.client.api.IGenericClient client, [CtParameterImpl][CtTypeReferenceImpl]ca.uhn.fhir.parser.IParser parser) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]java.io.InputStream inputStream = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]gov.cms.dpc.api.APITestHelpers.class.getClassLoader().getResourceAsStream([CtLiteralImpl]"patient_bundle.json")) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]Parameters patientParameters = [CtInvocationImpl](([CtTypeReferenceImpl]Parameters) ([CtVariableReadImpl]parser.parseResource([CtVariableReadImpl]inputStream)));
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]client.operation().onType([CtFieldReadImpl]gov.cms.dpc.api.Patient.class).named([CtLiteralImpl]"submit").withParameters([CtVariableReadImpl]patientParameters).encodedJson().execute();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Build Dropwizard test instance with a specific subset of Resources and Providers
     *
     * @param ctx
     * 		- {@link FhirContext} context to use
     * @param resources
     * 		- {@link List} of resources to add to test instance
     * @param providers
     * 		- {@link List} of providers to add to test instance
     * @param validation
     * 		- {@code true} enable custom validation. {@code false} Disable custom validation
     * @return - {@link ResourceExtension}
     */
    public static [CtTypeReferenceImpl]io.dropwizard.testing.junit5.ResourceExtension buildResourceExtension([CtParameterImpl][CtTypeReferenceImpl]ca.uhn.fhir.context.FhirContext ctx, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> resources, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.Object> providers, [CtParameterImpl][CtTypeReferenceImpl]boolean validation) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]gov.cms.dpc.fhir.dropwizard.handlers.FHIRHandler fhirHandler = [CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.dpc.fhir.dropwizard.handlers.FHIRHandler([CtVariableReadImpl]ctx);
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]var builder = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]io.dropwizard.testing.junit5.ResourceExtension.builder().setRegisterDefaultExceptionMappers([CtLiteralImpl]false).setTestContainerFactory([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory()).addProvider([CtVariableReadImpl]fhirHandler).addProvider([CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.dpc.fhir.dropwizard.handlers.BundleHandler([CtVariableReadImpl]fhirHandler)).addProvider([CtFieldReadImpl]gov.cms.dpc.fhir.dropwizard.handlers.exceptions.JerseyExceptionHandler.class).addProvider([CtFieldReadImpl]gov.cms.dpc.fhir.dropwizard.handlers.exceptions.PersistenceExceptionHandler.class).addProvider([CtFieldReadImpl]gov.cms.dpc.fhir.dropwizard.handlers.exceptions.HAPIExceptionHandler.class).addProvider([CtFieldReadImpl]gov.cms.dpc.fhir.dropwizard.handlers.exceptions.DefaultFHIRExceptionHandler.class).addProvider([CtFieldReadImpl]gov.cms.dpc.api.exceptions.JsonParseExceptionMapper.class);
        [CtIfImpl][CtCommentImpl]// Optionally enable validation
        if ([CtVariableReadImpl]validation) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Validation config
            final [CtTypeReferenceImpl][CtTypeReferenceImpl]gov.cms.dpc.fhir.configuration.DPCFHIRConfiguration.FHIRValidationConfiguration config = [CtConstructorCallImpl]new [CtTypeReferenceImpl][CtTypeReferenceImpl]gov.cms.dpc.fhir.configuration.DPCFHIRConfiguration.FHIRValidationConfiguration();
            [CtInvocationImpl][CtVariableReadImpl]config.setEnabled([CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]config.setSchematronValidation([CtLiteralImpl]true);
            [CtInvocationImpl][CtVariableReadImpl]config.setSchemaValidation([CtLiteralImpl]true);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]gov.cms.dpc.fhir.validations.DPCProfileSupport dpcModule = [CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.dpc.fhir.validations.DPCProfileSupport([CtVariableReadImpl]ctx);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.hl7.fhir.dstu3.hapi.validation.ValidationSupportChain support = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.hapi.validation.ValidationSupportChain([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hl7.fhir.dstu3.hapi.ctx.DefaultProfileValidationSupport(), [CtVariableReadImpl]dpcModule);
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]gov.cms.dpc.fhir.validations.dropwizard.InjectingConstraintValidatorFactory constraintFactory = [CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.dpc.fhir.validations.dropwizard.InjectingConstraintValidatorFactory([CtInvocationImpl][CtTypeAccessImpl]java.util.Set.of([CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.dpc.fhir.validations.ProfileValidator([CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]gov.cms.dpc.fhir.validations.dropwizard.FHIRValidatorProvider([CtVariableReadImpl]ctx, [CtVariableReadImpl]config, [CtVariableReadImpl]support).get())));
            [CtInvocationImpl][CtVariableReadImpl]builder.setValidator([CtInvocationImpl]gov.cms.dpc.api.APITestHelpers.provideValidator([CtVariableReadImpl]constraintFactory));
        }
        [CtInvocationImpl][CtVariableReadImpl]resources.forEach([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]builder::addResource);
        [CtInvocationImpl][CtVariableReadImpl]providers.forEach([CtExecutableReferenceExpressionImpl][CtVariableReadImpl]builder::addProvider);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]builder.build();
    }

    [CtMethodImpl]static <[CtTypeParameterImpl]C extends [CtTypeReferenceImpl]io.dropwizard.Configuration> [CtTypeReferenceImpl]void setupApplication([CtParameterImpl][CtTypeReferenceImpl]io.dropwizard.testing.DropwizardTestSupport<[CtTypeParameterReferenceImpl]C> application) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.typesafe.config.ConfigFactory.invalidateCaches();
        [CtInvocationImpl][CtCommentImpl]// Truncate attribution database
        gov.cms.dpc.api.APITestHelpers.truncateDatabase();
        [CtInvocationImpl][CtVariableReadImpl]application.before();
        [CtInvocationImpl][CtCommentImpl]// Truncate the Auth DB
        [CtInvocationImpl][CtVariableReadImpl]application.getApplication().run([CtLiteralImpl]"db", [CtLiteralImpl]"drop-all", [CtLiteralImpl]"--confirm-delete-everything", [CtLiteralImpl]"ci.application.conf");
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]application.getApplication().run([CtLiteralImpl]"db", [CtLiteralImpl]"migrate", [CtLiteralImpl]"ci.application.conf");
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]void truncateDatabase() throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.impl.client.CloseableHttpClient client = [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.impl.client.HttpClients.createDefault()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.http.client.methods.HttpPost post = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpPost([CtFieldReadImpl]gov.cms.dpc.api.APITestHelpers.ATTRIBUTION_TRUNCATE_TASK);
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.CloseableHttpResponse execute = [CtInvocationImpl][CtVariableReadImpl]client.execute([CtVariableReadImpl]post)) [CtBlockImpl]{
                [CtInvocationImpl]assertEquals([CtTypeAccessImpl]HttpStatus.OK_200, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]execute.getStatusLine().getStatusCode(), [CtLiteralImpl]"Should have truncated database");
            }
        }
    }

    [CtMethodImpl]static <[CtTypeParameterImpl]C extends [CtTypeReferenceImpl]io.dropwizard.Configuration> [CtTypeReferenceImpl]void checkHealth([CtParameterImpl][CtTypeReferenceImpl]io.dropwizard.testing.DropwizardTestSupport<[CtTypeParameterReferenceImpl]C> application) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// URI of the API Service Healthcheck
        final [CtTypeReferenceImpl]java.lang.String healthURI = [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"http://localhost:%s/healthcheck", [CtInvocationImpl][CtVariableReadImpl]application.getAdminPort());
        [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.impl.client.CloseableHttpClient client = [CtInvocationImpl][CtTypeAccessImpl]org.apache.http.impl.client.HttpClients.createDefault()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet healthCheck = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.http.client.methods.HttpGet([CtVariableReadImpl]healthURI);
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.http.client.methods.CloseableHttpResponse execute = [CtInvocationImpl][CtVariableReadImpl]client.execute([CtVariableReadImpl]healthCheck)) [CtBlockImpl]{
                [CtInvocationImpl]assertEquals([CtTypeAccessImpl]HttpStatus.OK_200, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]execute.getStatusLine().getStatusCode(), [CtLiteralImpl]"Should be healthy");
            }
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]javax.validation.Validator provideValidator([CtParameterImpl][CtTypeReferenceImpl]gov.cms.dpc.fhir.validations.dropwizard.InjectingConstraintValidatorFactory factory) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]javax.validation.Validation.byDefaultProvider().configure().constraintValidatorFactory([CtVariableReadImpl]factory).buildValidatorFactory().getValidator();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]gov.cms.dpc.api.Practitioner createPractitionerResource([CtParameterImpl][CtTypeReferenceImpl]java.lang.String NPI, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String orgID) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]Practitioner practitioner = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Practitioner();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]practitioner.addIdentifier().setValue([CtVariableReadImpl]NPI).setSystem([CtInvocationImpl][CtTypeAccessImpl]DPCIdentifierSystem.NPPES.getSystem());
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]practitioner.addName().setFamily([CtLiteralImpl]"Practitioner").addGiven([CtLiteralImpl]"Test");
        [CtLocalVariableImpl][CtCommentImpl]// Meta data which includes the Org we're using
        final [CtTypeReferenceImpl]Meta meta = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Meta();
        [CtInvocationImpl][CtVariableReadImpl]meta.addTag([CtInvocationImpl][CtTypeAccessImpl]DPCIdentifierSystem.DPC.getSystem(), [CtVariableReadImpl]orgID, [CtLiteralImpl]"OrganizationID");
        [CtInvocationImpl][CtVariableReadImpl]practitioner.setMeta([CtVariableReadImpl]meta);
        [CtReturnImpl]return [CtVariableReadImpl]practitioner;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]gov.cms.dpc.api.Patient createPatientResource([CtParameterImpl][CtTypeReferenceImpl]java.lang.String MBI, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String organizationID) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]Patient patient = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Patient();
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]patient.addIdentifier().setSystem([CtInvocationImpl][CtTypeAccessImpl]DPCIdentifierSystem.MBI.getSystem()).setValue([CtVariableReadImpl]MBI);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]patient.addName().setFamily([CtLiteralImpl]"Patient").addGiven([CtLiteralImpl]"Test");
        [CtInvocationImpl][CtVariableReadImpl]patient.setBirthDate([CtInvocationImpl][CtTypeAccessImpl]java.sql.Date.valueOf([CtLiteralImpl]"1990-01-01"));
        [CtInvocationImpl][CtVariableReadImpl]patient.setGender([CtTypeAccessImpl]Enumerations.AdministrativeGender.OTHER);
        [CtInvocationImpl][CtVariableReadImpl]patient.setManagingOrganization([CtConstructorCallImpl]new [CtTypeReferenceImpl]Reference([CtConstructorCallImpl]new [CtTypeReferenceImpl]IdType([CtLiteralImpl]"Organization", [CtVariableReadImpl]organizationID)));
        [CtReturnImpl]return [CtVariableReadImpl]patient;
    }
}
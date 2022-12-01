[CompilationUnitImpl][CtPackageDeclarationImpl]package org.smartregister.service;
[CtUnresolvedImport]import org.smartregister.sync.SaveANMLocationTask;
[CtUnresolvedImport]import org.smartregister.view.activity.DrishtiApplication;
[CtImportImpl]import java.util.LinkedHashMap;
[CtUnresolvedImport]import org.robolectric.util.ReflectionHelpers;
[CtUnresolvedImport]import org.smartregister.domain.jsonmapping.LoginResponseData;
[CtUnresolvedImport]import org.smartregister.util.Session;
[CtUnresolvedImport]import org.smartregister.domain.LoginResponse;
[CtUnresolvedImport]import org.smartregister.CoreLibrary;
[CtImportImpl]import java.util.UUID;
[CtUnresolvedImport]import static org.mockito.Mockito.doReturn;
[CtUnresolvedImport]import org.smartregister.SyncConfiguration;
[CtUnresolvedImport]import org.smartregister.util.AssetHandler;
[CtUnresolvedImport]import org.smartregister.sync.SaveANMTeamTask;
[CtUnresolvedImport]import org.smartregister.repository.Repository;
[CtUnresolvedImport]import org.smartregister.domain.TimeStatus;
[CtImportImpl]import java.util.TimeZone;
[CtUnresolvedImport]import org.smartregister.repository.AllSettings;
[CtUnresolvedImport]import static org.mockito.Mockito.mock;
[CtUnresolvedImport]import org.apache.commons.codec.digest.Md5Crypt;
[CtUnresolvedImport]import org.powermock.reflect.Whitebox;
[CtUnresolvedImport]import org.mockito.Mock;
[CtUnresolvedImport]import org.smartregister.repository.AllSharedPreferences;
[CtUnresolvedImport]import static org.junit.Assert.assertFalse;
[CtUnresolvedImport]import static org.mockito.Mockito.spy;
[CtUnresolvedImport]import static org.mockito.Mockito.verifyZeroInteractions;
[CtUnresolvedImport]import org.smartregister.domain.jsonmapping.User;
[CtUnresolvedImport]import org.smartregister.domain.jsonmapping.Time;
[CtUnresolvedImport]import org.mockito.junit.MockitoJUnit;
[CtUnresolvedImport]import static org.smartregister.AllConstants.ENGLISH_LOCALE;
[CtUnresolvedImport]import org.smartregister.BaseUnitTest;
[CtUnresolvedImport]import org.powermock.api.mockito.PowerMockito;
[CtUnresolvedImport]import org.junit.Before;
[CtImportImpl]import java.util.Calendar;
[CtUnresolvedImport]import static org.junit.Assert.assertNotNull;
[CtUnresolvedImport]import static org.junit.Assert.assertTrue;
[CtUnresolvedImport]import static org.mockito.Mockito.verify;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import static org.mockito.Mockito.when;
[CtUnresolvedImport]import org.smartregister.sync.SaveUserInfoTask;
[CtImportImpl]import java.security.KeyStore;
[CtImportImpl]import java.security.KeyStoreSpi;
[CtImportImpl]import java.util.Date;
[CtUnresolvedImport]import org.smartregister.DristhiConfiguration;
[CtUnresolvedImport]import org.mockito.Mockito;
[CtUnresolvedImport]import org.smartregister.repository.AllEligibleCouples;
[CtUnresolvedImport]import org.junit.Rule;
[CtUnresolvedImport]import static org.mockito.Mockito.never;
[CtUnresolvedImport]import static org.junit.Assert.assertNull;
[CtUnresolvedImport]import static org.smartregister.AllConstants.KANNADA_LOCALE;
[CtUnresolvedImport]import org.smartregister.repository.AllAlerts;
[CtUnresolvedImport]import static org.junit.Assert.assertEquals;
[CtUnresolvedImport]import org.mockito.junit.MockitoRule;
[CtClassImpl]public class UserServiceTest extends [CtTypeReferenceImpl]org.smartregister.BaseUnitTest {
    [CtFieldImpl][CtAnnotationImpl]@org.junit.Rule
    public [CtTypeReferenceImpl]org.mockito.junit.MockitoRule mockitoRule = [CtInvocationImpl][CtTypeAccessImpl]org.mockito.junit.MockitoJUnit.rule();

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]org.smartregister.repository.Repository repository;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]org.smartregister.repository.AllSettings allSettings;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]org.smartregister.repository.AllSharedPreferences allSharedPreferences;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]org.smartregister.repository.AllAlerts allAlerts;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]org.smartregister.repository.AllEligibleCouples allEligibleCouples;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]org.smartregister.util.Session session;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]org.smartregister.service.HTTPAgent httpAgent;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]org.smartregister.DristhiConfiguration configuration;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]org.smartregister.sync.SaveANMLocationTask saveANMLocationTask;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]org.smartregister.sync.SaveUserInfoTask saveUserInfoTask;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]org.smartregister.sync.SaveANMTeamTask saveANMTeamTask;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]java.security.KeyStore keyStore;

    [CtFieldImpl][CtAnnotationImpl]@org.mockito.Mock
    private [CtTypeReferenceImpl]java.security.KeyStoreSpi keyStoreSpi;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.smartregister.service.UserService userService;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String userInfoJSON;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.LoginResponseData loginResponseData;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void setUp() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtInvocationImpl][CtTypeAccessImpl]org.smartregister.view.activity.DrishtiApplication.getInstance(), [CtLiteralImpl]"repository", [CtFieldReadImpl]repository);
        [CtAssignmentImpl][CtFieldWriteImpl]userService = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.smartregister.service.UserService([CtFieldReadImpl]allSettings, [CtFieldReadImpl]allSharedPreferences, [CtFieldReadImpl]httpAgent, [CtFieldReadImpl]session, [CtFieldReadImpl]configuration, [CtFieldReadImpl]saveANMLocationTask, [CtFieldReadImpl]saveUserInfoTask, [CtFieldReadImpl]saveANMTeamTask);
        [CtAssignmentImpl][CtFieldWriteImpl]userInfoJSON = [CtLiteralImpl]"{\"locations\":{\"locationsHierarchy\":{\"map\":{\"cd4ed528-87cd-42ee-a175-5e7089521ebd\":{\"id\":\"cd4ed528-87cd-42ee-a175-5e7089521ebd\",\"label\":\"Pakistan\",\"node\":{\"locationId\":\"cd4ed528-87cd-42ee-a175-5e7089521ebd\",\"name\":\"Pakistan\",\"tags\":[\"Country\"],\"voided\":false},\"children\":{\"461f2be7-c95d-433c-b1d7-c68f272409d7\":{\"id\":\"461f2be7-c95d-433c-b1d7-c68f272409d7\",\"label\":\"Sindh\",\"node\":{\"locationId\":\"461f2be7-c95d-433c-b1d7-c68f272409d7\",\"name\":\"Sindh\",\"parentLocation\":{\"locationId\":\"cd4ed528-87cd-42ee-a175-5e7089521ebd\",\"name\":\"Pakistan\",\"voided\":false},\"tags\":[\"Province\"],\"voided\":false},\"children\":{\"a529e2fc-6f0d-4e60-a5df-789fe17cca48\":{\"id\":\"a529e2fc-6f0d-4e60-a5df-789fe17cca48\",\"label\":\"Karachi\",\"node\":{\"locationId\":\"a529e2fc-6f0d-4e60-a5df-789fe17cca48\",\"name\":\"Karachi\",\"parentLocation\":{\"locationId\":\"461f2be7-c95d-433c-b1d7-c68f272409d7\",\"name\":\"Sindh\",\"parentLocation\":{\"locationId\":\"cd4ed528-87cd-42ee-a175-5e7089521ebd\",\"name\":\"Pakistan\",\"voided\":false},\"voided\":false},\"tags\":[\"City\"],\"voided\":false},\"children\":{\"60c21502-fec1-40f5-b77d-6df3f92771ce\":{\"id\":\"60c21502-fec1-40f5-b77d-6df3f92771ce\",\"label\":\"Baldia\",\"node\":{\"locationId\":\"60c21502-fec1-40f5-b77d-6df3f92771ce\",\"name\":\"Baldia\",\"parentLocation\":{\"locationId\":\"a529e2fc-6f0d-4e60-a5df-789fe17cca48\",\"name\":\"Karachi\",\"parentLocation\":{\"locationId\":\"461f2be7-c95d-433c-b1d7-c68f272409d7\",\"name\":\"Sindh\",\"voided\":false},\"voided\":false},\"tags\":[\"Town\"],\"attributes\":{\"at1\":\"atttt1\"},\"voided\":false},\"parent\":\"a529e2fc-6f0d-4e60-a5df-789fe17cca48\"}},\"parent\":\"461f2be7-c95d-433c-b1d7-c68f272409d7\"}},\"parent\":\"cd4ed528-87cd-42ee-a175-5e7089521ebd\"}}}},\"parentChildren\":{\"cd4ed528-87cd-42ee-a175-5e7089521ebd\":[\"461f2be7-c95d-433c-b1d7-c68f272409d7\"],\"461f2be7-c95d-433c-b1d7-c68f272409d7\":[\"a529e2fc-6f0d-4e60-a5df-789fe17cca48\"],\"a529e2fc-6f0d-4e60-a5df-789fe17cca48\":[\"60c21502-fec1-40f5-b77d-6df3f92771ce\"]}}},\"user\":{\"username\":\"demotest\",\"roles\":[\"Provider\",\"Authenticated\",\"Thrive Member\"],\"permissions\":[\"Delete Reports\",\"Remove Allergies\",\"Edit Cohorts\",\"View Unpublished Forms\",\"Patient Dashboard - View Patient Summary\",\"Add Relationships\",\"Edit Problems\",\"Remove Problems\",\"Patient Dashboard - View Forms Section\",\"Manage Report Designs\",\"Add Patient Programs\",\"Delete Orders\",\"Manage Identifier Types\",\"Manage Person Attribute Types\",\"Add Patient Identifiers\",\"View Visit Types\",\"View Patients\",\"Delete Concept Proposals\",\"View Identifier Types\",\"Delete Encounters\",\"View Global Properties\",\"Edit Visits\",\"View Concept Map Types\",\"Add Users\",\"Delete Cohorts\",\"Manage Scheduled Report Tasks\",\"View Concepts\",\"Edit Concept Proposals\",\"Add Visits\",\"Edit Patient Programs\",\"Manage Concept Datatypes\",\"Manage Indicator Definitions\",\"View Concept Proposals\",\"Add Allergies\",\"Edit Allergies\",\"Delete Observations\",\"View Roles\",\"Manage Address Templates\",\"Configure Visits\",\"Manage Data Set Definitions\",\"View Concept Sources\",\"Patient Dashboard - View Regimen Section\",\"View Calculations\",\"Manage Encounter Roles\",\"Delete People\",\"Edit Report Objects\",\"View People\",\"Manage Concept Sources\",\"View Orders\",\"Manage Concept Map Types\",\"Delete Patient Programs\",\"Add Problems\",\"Edit People\",\"Manage Locations\",\"View Patient Programs\",\"View Field Types\",\"View Relationship Types\",\"Manage Visit Attribute Types\",\"Manage Order Types\",\"Manage TeamLocation Attribute Types\",\"Form Entry\",\"View Encounter Types\",\"View Encounter Roles\",\"Manage Programs\",\"Edit Reports\",\"View TeamLocation Attribute Types\",\"View Order Types\",\"Manage Relationship Types\",\"Manage Providers\",\"Manage Reports\",\"Manage Concept Classes\",\"Add Concept Proposals\",\"View Patient Identifiers\",\"View Privileges\",\"View Data Entry Statistics\",\"Patient Dashboard - View Graphs Section\",\"Manage Tokens\",\"Add Reports\",\"View Forms\",\"View Administration Functions\",\"Manage Relationships\",\"View Observations\",\"View Team\",\"Add Observations\",\"View Member\",\"View Report Objects\",\"Edit Relationships\",\"View Relationships\",\"Manage Scheduler\",\"View Allergies\",\"View Concept Reference Terms\",\"View Encounters\",\"Edit Patient Identifiers\",\"Edit Observations\",\"Delete Patients\",\"Delete Patient Identifiers\",\"View Person Attribute Types\",\"Add Encounters\",\"View Problems\",\"Manage FormEntry XSN\",\"View Visits\",\"Edit Team\",\"Manage Field Types\",\"Patient Dashboard - View Encounters Section\",\"Add Team\",\"Add Cohorts\",\"Add Patients\",\"Patient Dashboard - View Demographics Section\",\"Manage Concepts\",\"View Rule Definitions\",\"Add Orders\",\"Manage Visit Types\",\"Patient Dashboard - View Visits Section\",\"View Locations\",\"Manage Forms\",\"Edit Encounters\",\"Delete Relationships\",\"Manage Concept Reference Terms\",\"Add Report Objects\",\"Manage Alerts\",\"View Users\",\"Edit Patients\",\"Manage Concept Stop Words\",\"View Concept Classes\",\"View Patient Cohorts\",\"View Visit Attribute Types\",\"Manage TeamLocation Tags\",\"Manage Encounter Types\",\"View Concept Datatypes\",\"View Navigation Menu\",\"Delete Visits\",\"Add People\",\"Edit Orders\",\"Manage Concept Name tags\",\"Run Reports\",\"View Providers\",\"Patient Dashboard - View Overview Section\",\"Manage Cohort Definitions\",\"View Reports\",\"View Programs\",\"Delete Report Objects\",\"Manage Report Definitions\"],\"baseEntityId\":\"6637559e-ebf9-480a-9731-c47e16e95716\",\"preferredName\":\"Demo test User\",\"voided\":false},\"time\":{\"time\":\"2018-03-02 10:17:51\",\"timeZone\":\"Africa/Harare\"},\"team\":{\"identifier\":\"12345678\",\"person\":{\"gender\":\"F\",\"display\":\"MOH ZEIR Demo\",\"resourceVersion\":\"1.11\",\"dead\":false,\"uuid\":\"12481a02-9a78-4c45-9ead-ddf24d14b19d\",\"birthdateEstimated\":false,\"deathdateEstimated\":false,\"attributes\":[],\"voided\":false,\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/person/12481a02-9a78-4c45-9ead-ddf24d14b19d\"},{\"rel\":\"full\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/person/12481a02-9a78-4c45-9ead-ddf24d14b19d?v\\u003dfull\"}],\"preferredName\":{\"display\":\"MOH ZEIR Demo\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/person/12481a02-9a78-4c45-9ead-ddf24d14b19d/name/4ab4a8b9-3723-44a8-8733-815ee6d05ef7\"}],\"uuid\":\"4ab4a8b9-3723-44a8-8733-815ee6d05ef7\"}},\"teamMemberId\":1.0,\"patients\":[],\"resourceVersion\":\"1.8\",\"location\":[{\"parentLocation\":{\"display\":\"Fort Jameson\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/25089a50-0cf0-47e8-8bfe-fecabed92530\"}],\"uuid\":\"25089a50-0cf0-47e8-8bfe-fecabed92530\"},\"display\":\"Happy Kids Clinic\",\"resourceVersion\":\"1.9\",\"uuid\":\"42abc582-6658-488b-922e-7be500c070f3\",\"tags\":[{\"display\":\"Health Centre Urban\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/locationtag/86c5e41b-08f0-495d-9130-170556c05041\"}],\"uuid\":\"86c5e41b-08f0-495d-9130-170556c05041\"},{\"display\":\"Health Facility\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/locationtag/4d9fce9d-c83a-46aa-b1d9-121da6176758\"}],\"uuid\":\"4d9fce9d-c83a-46aa-b1d9-121da6176758\"}],\"name\":\"Happy Kids Clinic\",\"retired\":false,\"attributes\":[{\"display\":\"dhis_ou_id: k2SgIKwkSh1\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/42abc582-6658-488b-922e-7be500c070f3/attribute/01ec1f7c-e061-4f37-9d2c-ce1c7fe99c36\"}],\"uuid\":\"01ec1f7c-e061-4f37-9d2c-ce1c7fe99c36\"}],\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/42abc582-6658-488b-922e-7be500c070f3\"},{\"rel\":\"full\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/42abc582-6658-488b-922e-7be500c070f3?v\\u003dfull\"}],\"childLocations\":[{\"display\":\"Happy Kids Clinic: Zone 1\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/42b88545-7ebb-4e11-8d1a-3d3a924c8af4\"}],\"uuid\":\"42b88545-7ebb-4e11-8d1a-3d3a924c8af4\"},{\"display\":\"Happy Kids Clinic: Zone 2\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/8a40cd7e-b8d4-4c6e-b88f-a77272fec630\"}],\"uuid\":\"8a40cd7e-b8d4-4c6e-b88f-a77272fec630\"},{\"display\":\"Happy Kids Clinic: Zone 3\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/5e79ae00-5a69-4814-aace-30e4717f823a\"}],\"uuid\":\"5e79ae00-5a69-4814-aace-30e4717f823a\"},{\"display\":\"Happy Kids Clinic: Zone 4\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/e79ff5bc-b6ff-46bc-9bbf-0cedc7d6c4c7\"}],\"uuid\":\"e79ff5bc-b6ff-46bc-9bbf-0cedc7d6c4c7\"}]}],\"team\":{\"teamName\":\"Demo\",\"dateCreated\":\"2017-04-06T09:21:39.000+0200\",\"display\":\"Demo\",\"resourceVersion\":\"1.8\",\"location\":{\"parentLocation\":{\"display\":\"Fort Jameson\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/25089a50-0cf0-47e8-8bfe-fecabed92530\"}],\"uuid\":\"25089a50-0cf0-47e8-8bfe-fecabed92530\"},\"display\":\"Happy Kids Clinic\",\"resourceVersion\":\"1.9\",\"uuid\":\"42abc582-6658-488b-922e-7be500c070f3\",\"tags\":[{\"display\":\"Health Centre Urban\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/locationtag/86c5e41b-08f0-495d-9130-170556c05041\"}],\"uuid\":\"86c5e41b-08f0-495d-9130-170556c05041\"},{\"display\":\"Health Facility\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/locationtag/4d9fce9d-c83a-46aa-b1d9-121da6176758\"}],\"uuid\":\"4d9fce9d-c83a-46aa-b1d9-121da6176758\"}],\"name\":\"Happy Kids Clinic\",\"retired\":false,\"attributes\":[{\"display\":\"dhis_ou_id: k2SgIKwkSh1\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/42abc582-6658-488b-922e-7be500c070f3/attribute/01ec1f7c-e061-4f37-9d2c-ce1c7fe99c36\"}],\"uuid\":\"01ec1f7c-e061-4f37-9d2c-ce1c7fe99c36\"}],\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/42abc582-6658-488b-922e-7be500c070f3\"},{\"rel\":\"full\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/42abc582-6658-488b-922e-7be500c070f3?v\\u003dfull\"}],\"childLocations\":[{\"display\":\"Happy Kids Clinic: Zone 1\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/42b88545-7ebb-4e11-8d1a-3d3a924c8af4\"}],\"uuid\":\"42b88545-7ebb-4e11-8d1a-3d3a924c8af4\"},{\"display\":\"Happy Kids Clinic: Zone 2\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/8a40cd7e-b8d4-4c6e-b88f-a77272fec630\"}],\"uuid\":\"8a40cd7e-b8d4-4c6e-b88f-a77272fec630\"},{\"display\":\"Happy Kids Clinic: Zone 3\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/5e79ae00-5a69-4814-aace-30e4717f823a\"}],\"uuid\":\"5e79ae00-5a69-4814-aace-30e4717f823a\"},{\"display\":\"Happy Kids Clinic: Zone 4\",\"links\":[{\"rel\":\"self\",\"uri\":\"http://openmrs.zeir-stage.smartregister.org/openmrs/ws/rest/v1/location/e79ff5bc-b6ff-46bc-9bbf-0cedc7d6c4c7\"}],\"uuid\":\"e79ff5bc-b6ff-46bc-9bbf-0cedc7d6c4c7\"}]},\"teamIdentifier\":\"Demo\",\"uuid\":\"7bfb4bb3-2689-404c-a5d4-f5cbe1aea9c4\"},\"isTeamLead\":true,\"uuid\":\"6ea953fb-46a2-4415-ae53-299ce909894b\"}}";
        [CtAssignmentImpl][CtFieldWriteImpl]loginResponseData = [CtInvocationImpl][CtTypeAccessImpl]org.smartregister.util.AssetHandler.jsonStringToJava([CtFieldReadImpl]userInfoJSON, [CtFieldReadImpl]org.smartregister.domain.jsonmapping.LoginResponseData.class);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldUseHttpAgentToDoRemoteLoginCheck() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.LoginResponseData userInfo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.LoginResponseData();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.User userObject = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.User();
        [CtInvocationImpl][CtVariableReadImpl]userObject.setUsername([CtLiteralImpl]"user");
        [CtInvocationImpl][CtVariableReadImpl]userObject.setPassword([CtLiteralImpl]"password Y");
        [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]userInfo.user = [CtVariableReadImpl]userObject;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.smartregister.domain.LoginResponse loginResponse = [CtInvocationImpl][CtTypeAccessImpl]LoginResponse.SUCCESS.withPayload([CtVariableReadImpl]userInfo);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]configuration.dristhiBaseURL()).thenReturn([CtLiteralImpl]"http://dristhi_base_url");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String httpAuthenticateUrl = [CtLiteralImpl]"http://dristhi_base_url/security/authenticate";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String user = [CtLiteralImpl]"user";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String password = [CtLiteralImpl]"password Y";
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]httpAgent.urlCanBeAccessWithGivenCredentials([CtVariableReadImpl]httpAuthenticateUrl, [CtVariableReadImpl]user, [CtVariableReadImpl]password)).thenReturn([CtVariableReadImpl]loginResponse);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchRegisteredANM()).thenReturn([CtLiteralImpl]"user");
        [CtInvocationImpl][CtFieldReadImpl]userService.isValidRemoteLogin([CtVariableReadImpl]user, [CtVariableReadImpl]password);
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]httpAgent).urlCanBeAccessWithGivenCredentials([CtVariableReadImpl]httpAuthenticateUrl, [CtVariableReadImpl]user, [CtVariableReadImpl]password);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldGetANMLocation() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]configuration.dristhiBaseURL()).thenReturn([CtLiteralImpl]"http://opensrp_base_url");
        [CtInvocationImpl][CtFieldReadImpl]userService.getLocationInformation();
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]httpAgent).fetch([CtLiteralImpl]"http://opensrp_base_url/teamLocation/teamLocation-tree");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldSaveUserInformationRemoteLoginIsSuccessful() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.User user = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.User();
        [CtInvocationImpl][CtVariableReadImpl]user.setPreferredName([CtLiteralImpl]"Test");
        [CtInvocationImpl][CtFieldReadImpl]userService.saveUserInfo([CtVariableReadImpl]user);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String userInfoString = [CtInvocationImpl][CtTypeAccessImpl]org.smartregister.util.AssetHandler.javaToJsonString([CtVariableReadImpl]user);
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]saveUserInfoTask).save([CtVariableReadImpl]userInfoString);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldSaveANMLocation() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.util.LocationTree locationTree = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.util.LocationTree();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.Location location = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.Location();
        [CtInvocationImpl][CtVariableReadImpl]location.setName([CtLiteralImpl]"test location");
        [CtInvocationImpl][CtVariableReadImpl]location.setLocationId([CtLiteralImpl]"Test location ID");
        [CtInvocationImpl][CtVariableReadImpl]locationTree.addLocation([CtVariableReadImpl]location);
        [CtInvocationImpl][CtFieldReadImpl]userService.saveAnmLocation([CtVariableReadImpl]locationTree);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String locationString = [CtInvocationImpl][CtTypeAccessImpl]org.smartregister.util.AssetHandler.javaToJsonString([CtVariableReadImpl]locationTree);
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]saveANMLocationTask).save([CtVariableReadImpl]locationString);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldConsiderALocalLoginValid() [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// When Username Matches Registered User And Password Matches The One In DB
        [CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchRegisteredANM()).thenReturn([CtLiteralImpl]"ANM X");
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]repository.canUseThisPassword([CtLiteralImpl]"password Z")).thenReturn([CtLiteralImpl]true);
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtFieldReadImpl]userService.isValidLocalLogin([CtLiteralImpl]"ANM X", [CtLiteralImpl]"password Z"));
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSharedPreferences).fetchRegisteredANM();
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]repository).canUseThisPassword([CtLiteralImpl]"password Z");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldConsiderALocalLoginInvalidWhenRegisteredUserDoesNotMatch() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchRegisteredANM()).thenReturn([CtLiteralImpl]"ANM X");
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtFieldReadImpl]userService.isValidLocalLogin([CtLiteralImpl]"SOME OTHER ANM", [CtLiteralImpl]"password"));
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSharedPreferences).fetchRegisteredANM();
        [CtInvocationImpl]Mockito.verifyZeroInteractions([CtFieldReadImpl]repository);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldConsiderALocalLoginInvalidWhenRegisteredUserMatchesButNotThePassword() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchRegisteredANM()).thenReturn([CtLiteralImpl]"ANM X");
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]repository.canUseThisPassword([CtLiteralImpl]"password Z")).thenReturn([CtLiteralImpl]false);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtFieldReadImpl]userService.isValidLocalLogin([CtLiteralImpl]"ANM X", [CtLiteralImpl]"password Z"));
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSharedPreferences).fetchRegisteredANM();
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]repository).canUseThisPassword([CtLiteralImpl]"password Z");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldRegisterANewUser() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]configuration.getDrishtiApplication()).thenReturn([CtNewClassImpl]new [CtTypeReferenceImpl]org.smartregister.view.activity.DrishtiApplication()[CtClassImpl] {
            [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
            public [CtTypeReferenceImpl]void logoutCurrentUser() [CtBlockImpl]{
                [CtCommentImpl]// Nothing to cleanup
            }
        });
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.LoginResponseData userInfo = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.LoginResponseData();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.User user = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.User();
        [CtInvocationImpl][CtVariableReadImpl]user.setUsername([CtLiteralImpl]"user X");
        [CtInvocationImpl][CtVariableReadImpl]user.setPassword([CtLiteralImpl]"password Y");
        [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]userInfo.user = [CtVariableReadImpl]user;
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchRegisteredANM()).thenReturn([CtLiteralImpl]"user X");
        [CtInvocationImpl][CtFieldReadImpl]userService.remoteLogin([CtLiteralImpl]"user X", [CtLiteralImpl]"password Y", [CtVariableReadImpl]userInfo);
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSettings).registerANM([CtLiteralImpl]"user X", [CtLiteralImpl]"password Y");
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]session).setPassword([CtLiteralImpl]"password Y");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldDeleteDataAndSettingsWhenLogoutHappens() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.smartregister.SyncConfiguration syncConfiguration = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]org.smartregister.SyncConfiguration.class);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.doReturn([CtLiteralImpl]false).when([CtVariableReadImpl]syncConfiguration).clearDataOnNewTeamLogin();
        [CtInvocationImpl][CtTypeAccessImpl]org.robolectric.util.ReflectionHelpers.setField([CtInvocationImpl][CtTypeAccessImpl]org.smartregister.CoreLibrary.getInstance(), [CtLiteralImpl]"syncConfiguration", [CtVariableReadImpl]syncConfiguration);
        [CtInvocationImpl][CtFieldReadImpl]userService.logout();
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]repository).deleteRepository();
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]repository).deleteRepository();
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]repository).deleteRepository();
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSettings).savePreviousFetchIndex([CtLiteralImpl]"0");
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSettings).registerANM([CtLiteralImpl]"", [CtLiteralImpl]"");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldSwitchLanguageToKannada() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchLanguagePreference()).thenReturn([CtTypeAccessImpl]org.smartregister.AllConstants.ENGLISH_LOCALE);
        [CtInvocationImpl][CtFieldReadImpl]userService.switchLanguagePreference();
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSharedPreferences).saveLanguagePreference([CtTypeAccessImpl]org.smartregister.AllConstants.KANNADA_LOCALE);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldSwitchLanguageToEnglish() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchLanguagePreference()).thenReturn([CtTypeAccessImpl]org.smartregister.AllConstants.KANNADA_LOCALE);
        [CtInvocationImpl][CtFieldReadImpl]userService.switchLanguagePreference();
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSharedPreferences).saveLanguagePreference([CtTypeAccessImpl]org.smartregister.AllConstants.ENGLISH_LOCALE);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldGetUserDataFromJSON() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.User user = [CtInvocationImpl][CtFieldReadImpl]userService.getUserData([CtFieldReadImpl]loginResponseData);
        [CtInvocationImpl]Assert.assertNotNull([CtVariableReadImpl]user);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"demotest", [CtInvocationImpl][CtVariableReadImpl]user.getUsername());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"Demo test User", [CtInvocationImpl][CtVariableReadImpl]user.getPreferredName());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void shouldGetUserLocationFromJSON() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.util.LocationTree locationTree = [CtInvocationImpl][CtFieldReadImpl]userService.getUserLocation([CtFieldReadImpl]loginResponseData);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.LinkedHashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.util.TreeNode<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.Location>> mapLocation = [CtInvocationImpl][CtVariableReadImpl]locationTree.getLocationsHierarchy();
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"Pakistan", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mapLocation.values().iterator().next().getLabel());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGetServerTimeZoneForExtractsTimeZoneFromResponse() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]loginResponseData = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]org.smartregister.domain.jsonmapping.LoginResponseData.class);
        [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]loginResponseData.time = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.Time([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date(), [CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getTimeZone([CtLiteralImpl]"Africa/Nairobi"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.TimeZone timezone = [CtInvocationImpl][CtTypeAccessImpl]org.smartregister.service.UserService.getServerTimeZone([CtFieldReadImpl]loginResponseData);
        [CtInvocationImpl]Assert.assertNotNull([CtVariableReadImpl]timezone);
        [CtLocalVariableImpl][CtTypeReferenceImpl]long millisecondsPerHour = [CtBinaryOperatorImpl][CtLiteralImpl]3600 * [CtLiteralImpl]1000;
        [CtInvocationImpl]Assert.assertEquals([CtBinaryOperatorImpl][CtLiteralImpl]3 * [CtVariableReadImpl]millisecondsPerHour, [CtInvocationImpl][CtVariableReadImpl]timezone.getRawOffset());
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"Africa/Nairobi", [CtInvocationImpl][CtVariableReadImpl]timezone.getID());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testValidateDeviceTimeForNullServerTimeZoneReturnsError() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]loginResponseData = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]org.smartregister.domain.jsonmapping.LoginResponseData.class);
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]TimeStatus.ERROR, [CtInvocationImpl][CtFieldReadImpl]userService.validateDeviceTime([CtFieldReadImpl]loginResponseData, [CtLiteralImpl]3600));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testValidateDeviceTimeForDifferentTimeZoneServerTimeZoneReturnsMismatch() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]loginResponseData = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]org.smartregister.domain.jsonmapping.LoginResponseData.class);
        [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]loginResponseData.time = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.Time([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date(), [CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getTimeZone([CtLiteralImpl]"Africa/Nairobi"));
        [CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.setDefault([CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getTimeZone([CtLiteralImpl]"GMT"));
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]TimeStatus.TIMEZONE_MISMATCH, [CtInvocationImpl][CtFieldReadImpl]userService.validateDeviceTime([CtFieldReadImpl]loginResponseData, [CtBinaryOperatorImpl][CtLiteralImpl]3600 * [CtLiteralImpl]1000));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testValidateDeviceTimeForDifferentTimeReturnsMismatch() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]loginResponseData = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]org.smartregister.domain.jsonmapping.LoginResponseData.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Calendar calendar = [CtInvocationImpl][CtTypeAccessImpl]java.util.Calendar.getInstance();
        [CtInvocationImpl][CtVariableReadImpl]calendar.add([CtFieldReadImpl][CtTypeAccessImpl]java.util.Calendar.[CtFieldReferenceImpl]MINUTE, [CtUnaryOperatorImpl]-[CtLiteralImpl]30);
        [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]loginResponseData.time = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.Time([CtInvocationImpl][CtVariableReadImpl]calendar.getTime(), [CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getTimeZone([CtLiteralImpl]"Africa/Nairobi"));
        [CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.setDefault([CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getTimeZone([CtLiteralImpl]"Africa/Nairobi"));
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]TimeStatus.TIME_MISMATCH, [CtInvocationImpl][CtFieldReadImpl]userService.validateDeviceTime([CtFieldReadImpl]loginResponseData, [CtBinaryOperatorImpl][CtLiteralImpl]15 * [CtLiteralImpl]1000));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testValidateDeviceTimeSameTimeTimeAndTimeZone() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]loginResponseData = [CtInvocationImpl]Mockito.mock([CtFieldReadImpl]org.smartregister.domain.jsonmapping.LoginResponseData.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Calendar calendar = [CtInvocationImpl][CtTypeAccessImpl]java.util.Calendar.getInstance();
        [CtAssignmentImpl][CtFieldWriteImpl][CtFieldReadImpl][CtFieldReferenceImpl]loginResponseData.time = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.smartregister.domain.jsonmapping.Time([CtInvocationImpl][CtVariableReadImpl]calendar.getTime(), [CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getTimeZone([CtLiteralImpl]"Africa/Nairobi"));
        [CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.setDefault([CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getTimeZone([CtLiteralImpl]"Africa/Nairobi"));
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]TimeStatus.OK, [CtInvocationImpl][CtFieldReadImpl]userService.validateDeviceTime([CtFieldReadImpl]loginResponseData, [CtBinaryOperatorImpl][CtLiteralImpl]15 * [CtLiteralImpl]1000));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testValidateStoredServerTimeZoneForNullServerTimeZoneReturnsError() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchServerTimeZone()).thenReturn([CtLiteralImpl]null);
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]TimeStatus.ERROR, [CtInvocationImpl][CtFieldReadImpl]userService.validateStoredServerTimeZone());
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSharedPreferences).saveForceRemoteLogin([CtLiteralImpl]true);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testValidateStoredServerTimeZoneForDifferentTimeZoneServerTimeZoneReturnsMismatch() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchServerTimeZone()).thenReturn([CtLiteralImpl]"Africa/Nairobi");
        [CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.setDefault([CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getTimeZone([CtLiteralImpl]"GMT"));
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]TimeStatus.TIMEZONE_MISMATCH, [CtInvocationImpl][CtFieldReadImpl]userService.validateStoredServerTimeZone());
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSharedPreferences).saveForceRemoteLogin([CtLiteralImpl]true);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testValidateStoredServerTimeZoneForSameTimeTimeAndTimeZone() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchServerTimeZone()).thenReturn([CtLiteralImpl]"Africa/Nairobi");
        [CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.setDefault([CtInvocationImpl][CtTypeAccessImpl]java.util.TimeZone.getTimeZone([CtLiteralImpl]"Africa/Nairobi"));
        [CtInvocationImpl]Assert.assertEquals([CtTypeAccessImpl]TimeStatus.OK, [CtInvocationImpl][CtFieldReadImpl]userService.validateStoredServerTimeZone());
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSharedPreferences, [CtInvocationImpl]Mockito.never()).saveForceRemoteLogin([CtLiteralImpl]true);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testIsUserInValidGroupForNullUserAndPassword() [CtBlockImpl]{
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtFieldReadImpl]userService.isUserInValidGroup([CtLiteralImpl]null, [CtLiteralImpl]null));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testIsUserInValidGroupForValidUserAndPassword() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]userService, [CtLiteralImpl]"keyStore", [CtFieldReadImpl]keyStore);
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]keyStore, [CtLiteralImpl]"initialized", [CtLiteralImpl]true);
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]keyStore, [CtLiteralImpl]"keyStoreSpi", [CtFieldReadImpl]keyStoreSpi);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String user = [CtLiteralImpl]"johndoe";
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]keyStore.containsAlias([CtVariableReadImpl]user)).thenReturn([CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.security.KeyStore.PrivateKeyEntry privateKeyEntry = [CtInvocationImpl][CtTypeAccessImpl]org.powermock.api.mockito.PowerMockito.mock([CtFieldReadImpl]java.security.KeyStore.PrivateKeyEntry.class);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]keyStore.getEntry([CtVariableReadImpl]user, [CtLiteralImpl]null)).thenReturn([CtVariableReadImpl]privateKeyEntry);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String password = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID().toString();
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchEncryptedPassword([CtVariableReadImpl]user)).thenReturn([CtVariableReadImpl]password);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchEncryptedGroupId([CtVariableReadImpl]user)).thenReturn([CtVariableReadImpl]password);
        [CtAssignmentImpl][CtFieldWriteImpl]userService = [CtInvocationImpl]Mockito.spy([CtFieldReadImpl]userService);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]Mockito.doReturn([CtVariableReadImpl]password).when([CtFieldReadImpl]userService).decryptString([CtVariableReadImpl]privateKeyEntry, [CtVariableReadImpl]password);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]repository.canUseThisPassword([CtVariableReadImpl]password)).thenReturn([CtLiteralImpl]true);
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtFieldReadImpl]userService.isUserInValidGroup([CtVariableReadImpl]user, [CtVariableReadImpl]password));
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSharedPreferences).fetchEncryptedPassword([CtVariableReadImpl]user);
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSharedPreferences).fetchEncryptedGroupId([CtVariableReadImpl]user);
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]repository).canUseThisPassword([CtVariableReadImpl]password);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testIsUserInValidGroupShouldReturnFalseOnError() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]userService, [CtLiteralImpl]"keyStore", [CtFieldReadImpl]keyStore);
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]keyStore, [CtLiteralImpl]"initialized", [CtLiteralImpl]true);
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]keyStore, [CtLiteralImpl]"keyStoreSpi", [CtFieldReadImpl]keyStoreSpi);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String user = [CtLiteralImpl]"johndoe";
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]keyStore.containsAlias([CtVariableReadImpl]user)).thenReturn([CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.security.KeyStore.PrivateKeyEntry privateKeyEntry = [CtInvocationImpl][CtTypeAccessImpl]org.powermock.api.mockito.PowerMockito.mock([CtFieldReadImpl]java.security.KeyStore.PrivateKeyEntry.class);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]keyStore.getEntry([CtVariableReadImpl]user, [CtLiteralImpl]null)).thenReturn([CtVariableReadImpl]privateKeyEntry);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String password = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID().toString();
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchEncryptedPassword([CtVariableReadImpl]user)).thenReturn([CtVariableReadImpl]password);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchEncryptedGroupId([CtVariableReadImpl]user)).thenReturn([CtVariableReadImpl]password);
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtFieldReadImpl]userService.isUserInValidGroup([CtVariableReadImpl]user, [CtVariableReadImpl]password));
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSharedPreferences).fetchEncryptedPassword([CtVariableReadImpl]user);
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]allSharedPreferences, [CtInvocationImpl]Mockito.never()).fetchEncryptedGroupId([CtVariableReadImpl]user);
        [CtInvocationImpl][CtInvocationImpl]Mockito.verify([CtFieldReadImpl]repository, [CtInvocationImpl]Mockito.never()).canUseThisPassword([CtVariableReadImpl]password);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGetGroupIdShShouldReturnNullOnError() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]userService, [CtLiteralImpl]"keyStore", [CtFieldReadImpl]keyStore);
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]keyStore, [CtLiteralImpl]"initialized", [CtLiteralImpl]true);
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]keyStore, [CtLiteralImpl]"keyStoreSpi", [CtFieldReadImpl]keyStoreSpi);
        [CtInvocationImpl]Assert.assertNull([CtInvocationImpl][CtFieldReadImpl]userService.getGroupId([CtLiteralImpl]"johndoe"));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testGetGroupIdShouldReturnGroupId() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]userService = [CtInvocationImpl]Mockito.spy([CtFieldReadImpl]userService);
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]userService, [CtLiteralImpl]"keyStore", [CtFieldReadImpl]keyStore);
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]keyStore, [CtLiteralImpl]"initialized", [CtLiteralImpl]true);
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]keyStore, [CtLiteralImpl]"keyStoreSpi", [CtFieldReadImpl]keyStoreSpi);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String password = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID().toString();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String user = [CtLiteralImpl]"johndoe";
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]keyStore.containsAlias([CtVariableReadImpl]user)).thenReturn([CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.security.KeyStore.PrivateKeyEntry privateKeyEntry = [CtInvocationImpl][CtTypeAccessImpl]org.powermock.api.mockito.PowerMockito.mock([CtFieldReadImpl]java.security.KeyStore.PrivateKeyEntry.class);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]keyStore.getEntry([CtVariableReadImpl]user, [CtLiteralImpl]null)).thenReturn([CtVariableReadImpl]privateKeyEntry);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchEncryptedGroupId([CtVariableReadImpl]user)).thenReturn([CtVariableReadImpl]password);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]Mockito.doReturn([CtLiteralImpl]"pass123").when([CtFieldReadImpl]userService).decryptString([CtVariableReadImpl]privateKeyEntry, [CtVariableReadImpl]password);
        [CtInvocationImpl]Assert.assertEquals([CtLiteralImpl]"pass123", [CtInvocationImpl][CtFieldReadImpl]userService.getGroupId([CtVariableReadImpl]user));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testIsUserInPioneerGroupShouldReturnTrueForPioneerUser() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]userService = [CtInvocationImpl]Mockito.spy([CtFieldReadImpl]userService);
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]userService, [CtLiteralImpl]"keyStore", [CtFieldReadImpl]keyStore);
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]keyStore, [CtLiteralImpl]"initialized", [CtLiteralImpl]true);
        [CtInvocationImpl][CtTypeAccessImpl]org.powermock.reflect.Whitebox.setInternalState([CtFieldReadImpl]keyStore, [CtLiteralImpl]"keyStoreSpi", [CtFieldReadImpl]keyStoreSpi);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String password = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID().toString();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String user = [CtLiteralImpl]"johndoe";
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]keyStore.containsAlias([CtVariableReadImpl]user)).thenReturn([CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.security.KeyStore.PrivateKeyEntry privateKeyEntry = [CtInvocationImpl][CtTypeAccessImpl]org.powermock.api.mockito.PowerMockito.mock([CtFieldReadImpl]java.security.KeyStore.PrivateKeyEntry.class);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]keyStore.getEntry([CtVariableReadImpl]user, [CtLiteralImpl]null)).thenReturn([CtVariableReadImpl]privateKeyEntry);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchEncryptedGroupId([CtVariableReadImpl]user)).thenReturn([CtVariableReadImpl]password);
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchPioneerUser()).thenReturn([CtVariableReadImpl]user);
        [CtInvocationImpl]Assert.assertTrue([CtInvocationImpl][CtFieldReadImpl]userService.isUserInPioneerGroup([CtVariableReadImpl]user));
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testIsUserInPioneerGroupShouldReturnFalseForOthers() throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]Mockito.when([CtInvocationImpl][CtFieldReadImpl]allSharedPreferences.fetchPioneerUser()).thenReturn([CtLiteralImpl]"user");
        [CtInvocationImpl]Assert.assertFalse([CtInvocationImpl][CtFieldReadImpl]userService.isUserInPioneerGroup([CtLiteralImpl]"john"));
    }
}
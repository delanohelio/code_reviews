[CompilationUnitImpl][CtPackageDeclarationImpl]package pro.taskana.rest;
[CtUnresolvedImport]import static org.assertj.core.api.Assertions.assertThat;
[CtUnresolvedImport]import org.springframework.core.ParameterizedTypeReference;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import org.springframework.http.HttpMethod;
[CtUnresolvedImport]import org.springframework.beans.factory.annotation.Autowired;
[CtUnresolvedImport]import pro.taskana.TaskanaSpringBootTest;
[CtUnresolvedImport]import org.junit.jupiter.api.Test;
[CtUnresolvedImport]import org.springframework.http.HttpStatus;
[CtUnresolvedImport]import org.springframework.web.client.HttpClientErrorException;
[CtUnresolvedImport]import pro.taskana.rest.resource.AccessIdResource;
[CtUnresolvedImport]import org.junit.jupiter.api.BeforeAll;
[CtUnresolvedImport]import org.springframework.http.ResponseEntity;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import pro.taskana.RestHelper;
[CtUnresolvedImport]import org.springframework.web.client.RestTemplate;
[CtClassImpl][CtAnnotationImpl]@pro.taskana.TaskanaSpringBootTest
class AccessIdControllerIntTest {
    [CtFieldImpl]private static [CtTypeReferenceImpl]org.springframework.web.client.RestTemplate template;

    [CtFieldImpl][CtAnnotationImpl]@org.springframework.beans.factory.annotation.Autowired
    [CtTypeReferenceImpl]pro.taskana.RestHelper restHelper;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.BeforeAll
    static [CtTypeReferenceImpl]void init() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]pro.taskana.rest.AccessIdControllerIntTest.template = [CtInvocationImpl][CtTypeAccessImpl]pro.taskana.RestHelper.getRestTemplate();
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void testQueryGroupsByDn() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]pro.taskana.rest.resource.AccessIdResource>> response = [CtInvocationImpl][CtFieldReadImpl]pro.taskana.rest.AccessIdControllerIntTest.template.exchange([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]restHelper.toUrl([CtTypeAccessImpl]Mapping.URL_ACCESSID) + [CtLiteralImpl]"?search-for=cn=developersgroup,ou=groups,o=taskanatest", [CtTypeAccessImpl]HttpMethod.GET, [CtInvocationImpl][CtFieldReadImpl]restHelper.defaultRequest(), [CtInvocationImpl][CtTypeAccessImpl]org.springframework.core.ParameterizedTypeReference.forType([CtFieldReadImpl]java.util.List.class));
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getBody().size()).isEqualTo([CtLiteralImpl]1);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void testQueryGroupsByCn() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]pro.taskana.rest.resource.AccessIdResource>> response = [CtInvocationImpl][CtFieldReadImpl]pro.taskana.rest.AccessIdControllerIntTest.template.exchange([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]restHelper.toUrl([CtTypeAccessImpl]Mapping.URL_ACCESSID) + [CtLiteralImpl]"?search-for=developer", [CtTypeAccessImpl]HttpMethod.GET, [CtInvocationImpl][CtFieldReadImpl]restHelper.defaultRequest(), [CtInvocationImpl][CtTypeAccessImpl]org.springframework.core.ParameterizedTypeReference.forType([CtFieldReadImpl]java.util.List.class));
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]response.getBody().size()).isEqualTo([CtLiteralImpl]1);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void testGetMatches() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.http.ResponseEntity<[CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]pro.taskana.rest.resource.AccessIdResource>> response = [CtInvocationImpl][CtFieldReadImpl]pro.taskana.rest.AccessIdControllerIntTest.template.exchange([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]restHelper.toUrl([CtTypeAccessImpl]Mapping.URL_ACCESSID) + [CtLiteralImpl]"?search-for=ali", [CtTypeAccessImpl]HttpMethod.GET, [CtInvocationImpl][CtFieldReadImpl]restHelper.defaultRequest(), [CtInvocationImpl][CtTypeAccessImpl]org.springframework.core.ParameterizedTypeReference.forType([CtFieldReadImpl]pro.taskana.rest.AccessIdControllerIntTest.AccessIdListResource.class));
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]pro.taskana.rest.resource.AccessIdResource> body = [CtInvocationImpl][CtVariableReadImpl]response.getBody();
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtVariableReadImpl]body).isNotNull();
        [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]body.size()).isEqualTo([CtLiteralImpl]3);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]assertThat([CtVariableReadImpl]body).extracting([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]AccessIdResource::getName).containsExactlyInAnyOrder([CtLiteralImpl]"Tralisch, Thea", [CtLiteralImpl]"Bert, Ali", [CtLiteralImpl]"Mente, Ali");
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.jupiter.api.Test
    [CtTypeReferenceImpl]void testBadRequestWhenSearchForIsTooShort() [CtBlockImpl]{
        [CtTryImpl]try [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]pro.taskana.rest.AccessIdControllerIntTest.template.exchange([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]restHelper.toUrl([CtTypeAccessImpl]Mapping.URL_ACCESSID) + [CtLiteralImpl]"?search-for=al", [CtTypeAccessImpl]HttpMethod.GET, [CtInvocationImpl][CtFieldReadImpl]restHelper.defaultRequest(), [CtInvocationImpl][CtTypeAccessImpl]org.springframework.core.ParameterizedTypeReference.forType([CtFieldReadImpl]java.util.List.class));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.web.client.HttpClientErrorException e) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl]assertThat([CtTypeAccessImpl]HttpStatus.BAD_REQUEST).isEqualTo([CtInvocationImpl][CtVariableReadImpl]e.getStatusCode());
            [CtInvocationImpl][CtInvocationImpl]assertThat([CtInvocationImpl][CtVariableReadImpl]e.getResponseBodyAsString()).containsSequence([CtLiteralImpl]"Minimum searchFor length =");
        }
    }

    [CtClassImpl]static class AccessIdListResource extends [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]pro.taskana.rest.resource.AccessIdResource> {
        [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtLiteralImpl]1L;
    }
}
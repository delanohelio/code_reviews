[CompilationUnitImpl][CtPackageDeclarationImpl]package com.redhat.pantheon.servlet.assembly;
[CtImportImpl]import java.util.*;
[CtUnresolvedImport]import org.osgi.framework.Constants;
[CtUnresolvedImport]import static com.redhat.pantheon.servlet.ServletUtils.paramValueAsLocale;
[CtUnresolvedImport]import com.redhat.pantheon.model.ProductVersion;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import org.apache.sling.api.SlingHttpServletRequest;
[CtUnresolvedImport]import org.osgi.service.component.annotations.Component;
[CtUnresolvedImport]import com.redhat.pantheon.html.Html;
[CtUnresolvedImport]import com.redhat.pantheon.model.assembly.Assembly;
[CtUnresolvedImport]import com.redhat.pantheon.servlet.ServletUtils;
[CtUnresolvedImport]import javax.jcr.RepositoryException;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import javax.annotation.Nonnull;
[CtUnresolvedImport]import com.google.common.base.Charsets;
[CtUnresolvedImport]import com.redhat.pantheon.model.assembly.AssemblyVersion;
[CtUnresolvedImport]import javax.servlet.Servlet;
[CtUnresolvedImport]import org.apache.sling.servlets.annotations.SlingServletPaths;
[CtUnresolvedImport]import org.apache.sling.api.resource.Resource;
[CtUnresolvedImport]import static com.redhat.pantheon.servlet.ServletUtils.paramValue;
[CtUnresolvedImport]import static com.redhat.pantheon.conf.GlobalConfig.DEFAULT_MODULE_LOCALE;
[CtUnresolvedImport]import com.redhat.pantheon.model.api.FileResource;
[CtUnresolvedImport]import com.redhat.pantheon.model.assembly.AssemblyMetadata;
[CtUnresolvedImport]import static com.redhat.pantheon.model.assembly.AssemblyVariant.DEFAULT_VARIANT_NAME;
[CtUnresolvedImport]import static javax.servlet.http.HttpServletResponse.SC_OK;
[CtUnresolvedImport]import org.jetbrains.annotations.NotNull;
[CtUnresolvedImport]import com.redhat.pantheon.servlet.AbstractJsonSingleQueryServlet;
[CtClassImpl][CtJavaDocImpl]/**
 * Get operation to render a Released Assembly data in JSON format.
 * Only two parameters are expected in the Get request:
 * 1. locale - Optional; indicates the locale that the assembly content is in, defaulted to en-US
 * 2. assembly_id - indicates the uuid string which uniquely identifies an assembly
 *
 * The url to GET a request from the server is /api/module
 * Example: <server_url>/api/assembly?locale=en-us&module_id=xyz&variant=abc
 * The said url is accessible outside of the system without any authentication.
 *
 * @author A.P. Rajjshekhar
 */
[CtCommentImpl]// /api/module.json?module_id=${moduleUuid}&locale=${localeId}&variant=${variantName}";
[CtAnnotationImpl]@org.osgi.service.component.annotations.Component(service = [CtFieldReadImpl]javax.servlet.Servlet.class, property = [CtNewArrayImpl]{ [CtBinaryOperatorImpl][CtFieldReadImpl]org.osgi.framework.Constants.SERVICE_DESCRIPTION + [CtLiteralImpl]"=Servlet to facilitate GET operation which accepts locale and assembly uuid to output module data", [CtBinaryOperatorImpl][CtFieldReadImpl]org.osgi.framework.Constants.SERVICE_VENDOR + [CtLiteralImpl]"=Red Hat Content Tooling team" })
[CtAnnotationImpl]@org.apache.sling.servlets.annotations.SlingServletPaths([CtLiteralImpl]"/api/assembly")
public class AssemblyJsonServlet extends [CtTypeReferenceImpl]com.redhat.pantheon.servlet.AbstractJsonSingleQueryServlet {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String PRODUCT_VERSION = [CtLiteralImpl]"product_version";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String PRODUCT_NAME = [CtLiteralImpl]"product_name";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String PRODUCT_LINK = [CtLiteralImpl]"product_link";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String VANITY_URL_FRAGMENT = [CtLiteralImpl]"vanity_url_fragment";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SEARCH_KEYWORDS = [CtLiteralImpl]"search_keywords";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String VIEW_URI = [CtLiteralImpl]"view_uri";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String PORTAL_URL = [CtLiteralImpl]"PORTAL_URL";

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.slf4j.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]com.redhat.pantheon.servlet.assembly.AssemblyJsonServlet.class);

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.lang.String getQuery([CtParameterImpl][CtTypeReferenceImpl]org.apache.sling.api.SlingHttpServletRequest request) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// Get the query parameter(s)
        [CtTypeReferenceImpl]java.lang.String uuidParam = [CtInvocationImpl]com.redhat.pantheon.servlet.ServletUtils.paramValue([CtVariableReadImpl]request, [CtLiteralImpl]"assembly_id", [CtLiteralImpl]"");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder query = [CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtLiteralImpl]"select * from [pant:assembly] as assembly WHERE assembly.[jcr:uuid] = '").append([CtVariableReadImpl]uuidParam).append([CtLiteralImpl]"'");
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]query.toString();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]boolean isValidResource([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]org.apache.sling.api.SlingHttpServletRequest request, [CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]org.apache.sling.api.resource.Resource resource) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Locale locale = [CtInvocationImpl]com.redhat.pantheon.servlet.ServletUtils.paramValueAsLocale([CtVariableReadImpl]request, [CtLiteralImpl]"locale", [CtTypeAccessImpl]GlobalConfig.DEFAULT_MODULE_LOCALE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String variantName = [CtInvocationImpl]com.redhat.pantheon.servlet.ServletUtils.paramValue([CtVariableReadImpl]request, [CtLiteralImpl]"variant", [CtTypeAccessImpl]DEFAULT_VARIANT_NAME);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.redhat.pantheon.model.assembly.Assembly assembly = [CtInvocationImpl][CtVariableReadImpl]resource.adaptTo([CtFieldReadImpl]com.redhat.pantheon.model.assembly.Assembly.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.redhat.pantheon.model.assembly.AssemblyVersion> releasedRevision = [CtInvocationImpl][CtVariableReadImpl]assembly.getReleasedVersion([CtVariableReadImpl]locale, [CtVariableReadImpl]variantName);
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]releasedRevision.isPresent();
    }

    [CtMethodImpl][CtCommentImpl]// ToDo: Refactor map based to builder pattern based POJO backed response entity
    [CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> resourceToMap([CtParameterImpl][CtAnnotationImpl]@javax.annotation.Nonnull
    [CtTypeReferenceImpl]org.apache.sling.api.SlingHttpServletRequest request, [CtParameterImpl][CtAnnotationImpl]@org.jetbrains.annotations.NotNull
    [CtTypeReferenceImpl]org.apache.sling.api.resource.Resource resource) throws [CtTypeReferenceImpl]javax.jcr.RepositoryException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.redhat.pantheon.model.assembly.Assembly assembly = [CtInvocationImpl][CtVariableReadImpl]resource.adaptTo([CtFieldReadImpl]com.redhat.pantheon.model.assembly.Assembly.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Locale locale = [CtInvocationImpl]com.redhat.pantheon.servlet.ServletUtils.paramValueAsLocale([CtVariableReadImpl]request, [CtLiteralImpl]"locale", [CtTypeAccessImpl]GlobalConfig.DEFAULT_MODULE_LOCALE);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String variantName = [CtInvocationImpl]com.redhat.pantheon.servlet.ServletUtils.paramValue([CtVariableReadImpl]request, [CtLiteralImpl]"variant", [CtTypeAccessImpl]DEFAULT_VARIANT_NAME);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.redhat.pantheon.model.assembly.AssemblyMetadata> releasedMetadata = [CtInvocationImpl][CtVariableReadImpl]assembly.getReleasedMetadata([CtVariableReadImpl]locale, [CtVariableReadImpl]variantName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.redhat.pantheon.model.api.FileResource> releasedContent = [CtInvocationImpl][CtVariableReadImpl]assembly.getReleasedContent([CtVariableReadImpl]locale, [CtVariableReadImpl]variantName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.redhat.pantheon.model.assembly.AssemblyVersion> releasedRevision = [CtInvocationImpl][CtVariableReadImpl]assembly.getReleasedVersion([CtVariableReadImpl]locale, [CtVariableReadImpl]variantName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> assemblyMap = [CtInvocationImpl][CtSuperAccessImpl]super.resourceToMap([CtVariableReadImpl]request, [CtVariableReadImpl]resource);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> assemblyDetails = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]assemblyDetails.put([CtLiteralImpl]"status", [CtTypeAccessImpl]SC_OK);
        [CtInvocationImpl][CtVariableReadImpl]assemblyDetails.put([CtLiteralImpl]"message", [CtLiteralImpl]"Assembly Found");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String resourcePath = [CtInvocationImpl][CtVariableReadImpl]resource.getPath();
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtLiteralImpl]"locale", [CtInvocationImpl][CtTypeAccessImpl]com.redhat.pantheon.servlet.ServletUtils.toLanguageTag([CtVariableReadImpl]locale));
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtLiteralImpl]"revision_id", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedRevision.get().getName());
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtLiteralImpl]"title", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().title().get());
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtLiteralImpl]"headline", [CtConditionalImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().getValueMap().containsKey([CtLiteralImpl]"pant:headline") ? [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().headline().get() : [CtLiteralImpl]"");
        [CtInvocationImpl][CtCommentImpl]// assemblyMap.put("description", releasedMetadata.get().description().get());
        [CtVariableReadImpl]assemblyMap.put([CtLiteralImpl]"content_type", [CtLiteralImpl]"assembly");
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtLiteralImpl]"date_published", [CtConditionalImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().getValueMap().containsKey([CtLiteralImpl]"pant:datePublished") ? [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().datePublished().get().toInstant().toString() : [CtLiteralImpl]"");
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtLiteralImpl]"status", [CtLiteralImpl]"published");
        [CtInvocationImpl][CtCommentImpl]// Assume the path is something like: /content/<something>/my/resource/path
        [CtVariableReadImpl]assemblyMap.put([CtLiteralImpl]"assembly_url_fragment", [CtInvocationImpl][CtVariableReadImpl]resourcePath.substring([CtInvocationImpl][CtLiteralImpl]"/content/repositories/".length()));
        [CtLocalVariableImpl][CtCommentImpl]// Striping out the jcr: from key name
        [CtTypeReferenceImpl]java.lang.String module_uuid = [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]assemblyMap.remove([CtLiteralImpl]"jcr:uuid")));
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtLiteralImpl]"assembly_uuid", [CtVariableReadImpl]module_uuid);
        [CtLocalVariableImpl][CtCommentImpl]// Convert date string to UTC
        [CtTypeReferenceImpl]java.util.Date dateModified = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Date([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getResourceMetadata().getModificationTime());
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtLiteralImpl]"date_modified", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]dateModified.toInstant().toString());
        [CtInvocationImpl][CtCommentImpl]// Return the body content of the module ONLY
        [CtVariableReadImpl]assemblyMap.put([CtLiteralImpl]"body", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.redhat.pantheon.html.Html.parse([CtInvocationImpl][CtTypeAccessImpl]Charsets.UTF_8.name()).andThen([CtInvocationImpl][CtTypeAccessImpl]com.redhat.pantheon.html.Html.getBody()).apply([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedContent.get().jcrContent().get().jcrData().get()));
        [CtInvocationImpl][CtCommentImpl]// Fields that are part of the spec and yet to be implemented
        [CtCommentImpl]// TODO Should either of these be the variant name?
        [CtVariableReadImpl]assemblyMap.put([CtLiteralImpl]"context_url_fragment", [CtLiteralImpl]"");
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtLiteralImpl]"context_id", [CtLiteralImpl]"");
        [CtLocalVariableImpl][CtCommentImpl]// Process productVersion from metadata
        [CtCommentImpl]// Making these arrays - in the future, we will have multi-product, so get the API right the first time
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.Map> productList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtLiteralImpl]"products", [CtVariableReadImpl]productList);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.redhat.pantheon.model.ProductVersion pv = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().productVersion().getReference();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pv != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> productMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtInvocationImpl][CtVariableReadImpl]productList.add([CtVariableReadImpl]productMap);
            [CtInvocationImpl][CtVariableReadImpl]productMap.put([CtFieldReadImpl]com.redhat.pantheon.servlet.assembly.AssemblyJsonServlet.PRODUCT_VERSION, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pv.name().get());
            [CtInvocationImpl][CtVariableReadImpl]productMap.put([CtFieldReadImpl]com.redhat.pantheon.servlet.assembly.AssemblyJsonServlet.PRODUCT_NAME, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]pv.getProduct().name().get());
            [CtInvocationImpl][CtVariableReadImpl]productMap.put([CtFieldReadImpl]com.redhat.pantheon.servlet.assembly.AssemblyJsonServlet.PRODUCT_LINK, [CtLiteralImpl]"https://www.redhat.com/productlinkplaceholder");
        }
        [CtLocalVariableImpl][CtCommentImpl]// Process url_fragment from metadata
        [CtTypeReferenceImpl]java.lang.String urlFragment = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().urlFragment().get() != [CtLiteralImpl]null) ? [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().urlFragment().get() : [CtLiteralImpl]"";
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]urlFragment.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtFieldReadImpl]com.redhat.pantheon.servlet.assembly.AssemblyJsonServlet.VANITY_URL_FRAGMENT, [CtVariableReadImpl]urlFragment);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtFieldReadImpl]com.redhat.pantheon.servlet.assembly.AssemblyJsonServlet.VANITY_URL_FRAGMENT, [CtLiteralImpl]"");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String searchKeywords = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().searchKeywords().get();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]searchKeywords != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]searchKeywords.isEmpty())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtFieldReadImpl]com.redhat.pantheon.servlet.assembly.AssemblyJsonServlet.SEARCH_KEYWORDS, [CtInvocationImpl][CtVariableReadImpl]searchKeywords.split([CtLiteralImpl]", *"));
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtFieldReadImpl]com.redhat.pantheon.servlet.assembly.AssemblyJsonServlet.SEARCH_KEYWORDS, [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{  });
        }
        [CtIfImpl][CtCommentImpl]// Process view_uri
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getenv([CtFieldReadImpl]com.redhat.pantheon.servlet.assembly.AssemblyJsonServlet.PORTAL_URL) != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String view_uri = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]java.lang.System.getenv([CtFieldReadImpl]com.redhat.pantheon.servlet.assembly.AssemblyJsonServlet.PORTAL_URL) + [CtLiteralImpl]"/topics/") + [CtInvocationImpl][CtTypeAccessImpl]com.redhat.pantheon.servlet.ServletUtils.toLanguageTag([CtVariableReadImpl]locale)) + [CtLiteralImpl]"/") + [CtVariableReadImpl]module_uuid;
            [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtFieldReadImpl]com.redhat.pantheon.servlet.assembly.AssemblyJsonServlet.VIEW_URI, [CtVariableReadImpl]view_uri);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]assemblyMap.put([CtFieldReadImpl]com.redhat.pantheon.servlet.assembly.AssemblyJsonServlet.VIEW_URI, [CtLiteralImpl]"");
        }
        [CtInvocationImpl][CtCommentImpl]// remove unnecessary fields from the map
        [CtVariableReadImpl]assemblyMap.remove([CtLiteralImpl]"jcr:lastModified");
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.remove([CtLiteralImpl]"jcr:lastModifiedBy");
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.remove([CtLiteralImpl]"jcr:createdBy");
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.remove([CtLiteralImpl]"jcr:created");
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.remove([CtLiteralImpl]"sling:resourceType");
        [CtInvocationImpl][CtVariableReadImpl]assemblyMap.remove([CtLiteralImpl]"jcr:primaryType");
        [CtInvocationImpl][CtCommentImpl]// Adding moduleMap to a parent moduleDetails map
        [CtVariableReadImpl]assemblyDetails.put([CtLiteralImpl]"assembly", [CtVariableReadImpl]assemblyMap);
        [CtReturnImpl]return [CtVariableReadImpl]assemblyDetails;
    }
}
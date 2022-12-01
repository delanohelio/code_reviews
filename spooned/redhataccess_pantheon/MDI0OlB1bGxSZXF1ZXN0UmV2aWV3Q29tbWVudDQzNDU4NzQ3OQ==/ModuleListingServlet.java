[CompilationUnitImpl][CtPackageDeclarationImpl]package com.redhat.pantheon.servlet;
[CtUnresolvedImport]import com.redhat.pantheon.model.module.HashableFileResource;
[CtUnresolvedImport]import org.apache.sling.api.resource.ResourceResolver;
[CtUnresolvedImport]import org.osgi.framework.Constants;
[CtUnresolvedImport]import com.redhat.pantheon.model.module.Module;
[CtImportImpl]import static java.util.stream.Collectors.toList;
[CtUnresolvedImport]import com.redhat.pantheon.jcr.JcrQueryHelper;
[CtImportImpl]import org.slf4j.Logger;
[CtUnresolvedImport]import static com.google.common.base.Strings.isNullOrEmpty;
[CtUnresolvedImport]import org.apache.sling.api.SlingHttpServletRequest;
[CtUnresolvedImport]import com.google.common.base.Strings;
[CtUnresolvedImport]import org.osgi.service.component.annotations.Component;
[CtUnresolvedImport]import static com.redhat.pantheon.model.api.util.ResourceTraversal.traverseFrom;
[CtUnresolvedImport]import javax.jcr.RepositoryException;
[CtUnresolvedImport]import com.redhat.pantheon.model.module.Metadata;
[CtUnresolvedImport]import com.redhat.pantheon.model.workspace.ModuleVariantDefinition;
[CtImportImpl]import org.apache.commons.lang3.StringUtils;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.stream.Stream;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtUnresolvedImport]import static com.redhat.pantheon.model.module.ModuleVariant.DEFAULT_VARIANT_NAME;
[CtUnresolvedImport]import org.apache.jackrabbit.JcrConstants;
[CtUnresolvedImport]import javax.jcr.query.Query;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import javax.servlet.Servlet;
[CtUnresolvedImport]import org.apache.sling.servlets.annotations.SlingServletPaths;
[CtUnresolvedImport]import org.apache.sling.api.resource.Resource;
[CtUnresolvedImport]import static com.redhat.pantheon.servlet.ServletUtils.paramValue;
[CtUnresolvedImport]import com.redhat.pantheon.model.module.ModuleLocale;
[CtImportImpl]import java.text.SimpleDateFormat;
[CtImportImpl]import org.apache.commons.lang3.ArrayUtils;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import static com.google.common.collect.Lists.newArrayListWithCapacity;
[CtImportImpl]import java.util.Arrays;
[CtClassImpl][CtJavaDocImpl]/**
 * Created by ben on 4/18/19.
 */
[CtAnnotationImpl]@org.osgi.service.component.annotations.Component(service = [CtFieldReadImpl]javax.servlet.Servlet.class, property = [CtNewArrayImpl]{ [CtBinaryOperatorImpl][CtFieldReadImpl]org.osgi.framework.Constants.SERVICE_DESCRIPTION + [CtLiteralImpl]"=Servlet which provides initial module listing and search functionality", [CtBinaryOperatorImpl][CtFieldReadImpl]org.osgi.framework.Constants.SERVICE_VENDOR + [CtLiteralImpl]"=Red Hat Content Tooling team" })
[CtAnnotationImpl]@org.apache.sling.servlets.annotations.SlingServletPaths([CtLiteralImpl]"/pantheon/internal/modules.json")
public class ModuleListingServlet extends [CtTypeReferenceImpl]com.redhat.pantheon.servlet.AbstractJsonQueryServlet {
    [CtFieldImpl]private final [CtTypeReferenceImpl]org.slf4j.Logger log = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]com.redhat.pantheon.servlet.ModuleListingServlet.class);

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.lang.String getQueryLanguage() [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// While XPATH is deprecated in JCR 2.0, it's still supported (and recommended) in apache oak
        return [CtFieldReadImpl]javax.jcr.query.Query.XPATH;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.lang.String getQuery([CtParameterImpl][CtTypeReferenceImpl]org.apache.sling.api.SlingHttpServletRequest request) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String searchParam = [CtInvocationImpl]com.redhat.pantheon.servlet.ServletUtils.paramValue([CtVariableReadImpl]request, [CtLiteralImpl]"search", [CtLiteralImpl]"");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String keyParam = [CtInvocationImpl]com.redhat.pantheon.servlet.ServletUtils.paramValue([CtVariableReadImpl]request, [CtLiteralImpl]"key");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String directionParam = [CtInvocationImpl]com.redhat.pantheon.servlet.ServletUtils.paramValue([CtVariableReadImpl]request, [CtLiteralImpl]"direction");
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] productIds = [CtInvocationImpl][CtVariableReadImpl]request.getParameterValues([CtLiteralImpl]"product");
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] productVersionIds = [CtInvocationImpl][CtVariableReadImpl]request.getParameterValues([CtLiteralImpl]"productversion");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String type = [CtInvocationImpl]com.redhat.pantheon.servlet.ServletUtils.paramValue([CtVariableReadImpl]request, [CtLiteralImpl]"type");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]keyParam == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]keyParam.contains([CtLiteralImpl]"Uploaded")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]keyParam = [CtLiteralImpl]"pant:dateUploaded";
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]keyParam.contains([CtLiteralImpl]"Title")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]keyParam = [CtLiteralImpl]"jcr:title";
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]keyParam.contains([CtLiteralImpl]"Published")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]keyParam = [CtLiteralImpl]"pant:datePublished";
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]keyParam.contains([CtLiteralImpl]"Module")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]keyParam = [CtLiteralImpl]"pant:moduleType";
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]keyParam.contains([CtLiteralImpl]"Updated")) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]keyParam = [CtFieldReadImpl]org.apache.jackrabbit.JcrConstants.JCR_LASTMODIFIED;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtLiteralImpl]"desc".equals([CtVariableReadImpl]directionParam)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]directionParam = [CtLiteralImpl]"descending";
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]directionParam = [CtLiteralImpl]"ascending";
        }
        [CtTryImpl][CtCommentImpl]// Add all product revisions resolved from product ids
        try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]productVersionIds = [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.ArrayUtils.addAll([CtVariableReadImpl]productVersionIds, [CtInvocationImpl]resolveProductVersions([CtInvocationImpl][CtVariableReadImpl]request.getResourceResolver(), [CtVariableReadImpl]productIds));
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]javax.jcr.RepositoryException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.RuntimeException([CtVariableReadImpl]e);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder queryBuilder = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder().append([CtLiteralImpl]"/jcr:root/content/(repositories | modules)//element(*, pant:module)");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.StringBuilder> queryFilters = [CtInvocationImpl]newArrayListWithCapacity([CtLiteralImpl]4);
        [CtIfImpl][CtCommentImpl]// only filter by text if provided
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]searchParam.length() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder textFilter = [CtInvocationImpl][CtCommentImpl]// .append("or jcr:like(*/*/source/@jcr:created,'%" + searchParam + "%') ")
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder().append([CtLiteralImpl]"(").append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"jcr:like(*/*/*/*/metadata/@jcr:title,'%" + [CtVariableReadImpl]searchParam) + [CtLiteralImpl]"%') ").append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"or jcr:like(*/*/*/*/metadata/@jcr:description,'%" + [CtVariableReadImpl]searchParam) + [CtLiteralImpl]"%')").append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"or jcr:like(*/*/*/*/cached_html/jcr:content/@jcr:data,'%" + [CtVariableReadImpl]searchParam) + [CtLiteralImpl]"%')").append([CtLiteralImpl]")");
            [CtInvocationImpl][CtVariableReadImpl]queryFilters.add([CtVariableReadImpl]textFilter);
        }
        [CtIfImpl][CtCommentImpl]// product version filter
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]productVersionIds != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]productVersionIds.length > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder productVersionCondition = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> conditions = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.stream([CtVariableReadImpl]productVersionIds).map([CtLambdaImpl]([CtParameterImpl]java.lang.String id) -> [CtBlockImpl]{
                [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"*/*/*/*/metadata/@productVersion = '" + [CtVariableReadImpl]id) + [CtLiteralImpl]"'";
            }).collect([CtInvocationImpl]java.util.stream.Collectors.toList());
            [CtInvocationImpl][CtVariableReadImpl]productVersionCondition.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"(" + [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.join([CtVariableReadImpl]conditions, [CtLiteralImpl]" or ")) + [CtLiteralImpl]")");
            [CtInvocationImpl][CtVariableReadImpl]queryFilters.add([CtVariableReadImpl]productVersionCondition);
        }
        [CtIfImpl][CtCommentImpl]// Module type filter
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Strings.isNullOrEmpty([CtVariableReadImpl]type)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder moduleTypeCondition = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder().append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"*/*/*/*/metadata/@pant:moduleType = '" + [CtVariableReadImpl]type) + [CtLiteralImpl]"'");
            [CtInvocationImpl][CtVariableReadImpl]queryFilters.add([CtVariableReadImpl]moduleTypeCondition);
        }
        [CtIfImpl][CtCommentImpl]// join all the available conditions
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]queryFilters.size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]queryBuilder.append([CtLiteralImpl]"[").append([CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.join([CtVariableReadImpl]queryFilters, [CtLiteralImpl]" and ")).append([CtLiteralImpl]"]");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl]isNullOrEmpty([CtVariableReadImpl]keyParam)) && [CtUnaryOperatorImpl](![CtInvocationImpl]isNullOrEmpty([CtVariableReadImpl]directionParam))) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// TODO: add condictional for order by
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]queryBuilder.append([CtLiteralImpl]" order by */*/*/*/metadata/@").append([CtVariableReadImpl]keyParam).append([CtLiteralImpl]" ").append([CtVariableReadImpl]directionParam);
        }
        [CtInvocationImpl][CtFieldReadImpl]log.info([CtBinaryOperatorImpl][CtLiteralImpl]"Executing module query: " + [CtInvocationImpl][CtVariableReadImpl]queryBuilder.toString());
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]queryBuilder.toString();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Auxiliary method to resolve product version ids from a single product id.
     *
     * @param resourceResolver
     * 		The resource resolver to use to resolve the product version ids
     * @param productIds
     * 		The collection of product ids to resolve
     * @return An array containing all product version ids which belong to any of the products which
    ids has been passed in the parameters
     * @throws RepositoryException
     * 		If there is a problem doing the resolution
     */
    private [CtArrayTypeReferenceImpl]java.lang.String[] resolveProductVersions([CtParameterImpl][CtTypeReferenceImpl]org.apache.sling.api.resource.ResourceResolver resourceResolver, [CtParameterImpl][CtArrayTypeReferenceImpl]java.lang.String[] productIds) throws [CtTypeReferenceImpl]javax.jcr.RepositoryException [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]productIds == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]productIds.length == [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{  };
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.redhat.pantheon.jcr.JcrQueryHelper queryHelper = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.redhat.pantheon.jcr.JcrQueryHelper([CtVariableReadImpl]resourceResolver);
        [CtLocalVariableImpl][CtCommentImpl]// product conditions
        [CtTypeReferenceImpl]java.lang.String productCondition = [CtLiteralImpl]"";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> conditions = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.stream([CtVariableReadImpl]productIds).map([CtLambdaImpl]([CtParameterImpl]java.lang.String id) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"product.[jcr:uuid] = '" + [CtVariableReadImpl]id) + [CtLiteralImpl]"'").collect([CtInvocationImpl]java.util.stream.Collectors.toList());
        [CtAssignmentImpl][CtVariableWriteImpl]productCondition = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"AND (" + [CtInvocationImpl][CtTypeAccessImpl]org.apache.commons.lang3.StringUtils.join([CtVariableReadImpl]conditions, [CtLiteralImpl]" OR ")) + [CtLiteralImpl]") ";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder query = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder().append([CtLiteralImpl]"SELECT pv.* from [pant:productVersion] AS pv ").append([CtLiteralImpl]"INNER JOIN [pant:product] AS product ON ISDESCENDANTNODE(pv, product) ").append([CtLiteralImpl]"WHERE ISDESCENDANTNODE(product, '/content/products') ").append([CtVariableReadImpl]productCondition);
        [CtLocalVariableImpl][CtCommentImpl]// TODO Right now this queries for everything, complex queries with lots of products might not scale
        [CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]org.apache.sling.api.resource.Resource> results = [CtInvocationImpl][CtVariableReadImpl]queryHelper.query([CtInvocationImpl][CtVariableReadImpl]query.toString());
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]results.map([CtLambdaImpl]([CtParameterImpl] resource) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]resource.getValueMap().get([CtLiteralImpl]"jcr:uuid")).collect([CtInvocationImpl]java.util.stream.Collectors.toList()).toArray([CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{  });
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> resourceToMap([CtParameterImpl][CtTypeReferenceImpl]org.apache.sling.api.resource.Resource resource) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Module module = [CtInvocationImpl][CtVariableReadImpl]resource.adaptTo([CtFieldReadImpl]java.lang.Module.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String variantName = [CtFieldReadImpl]DEFAULT_VARIANT_NAME;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]com.redhat.pantheon.model.workspace.ModuleVariantDefinition> mvd = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]module.getWorkspace().moduleVariantDefinitions().get().getVariants();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]mvd != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]variantName = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mvd.findFirst().get().getName();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.redhat.pantheon.model.module.Metadata> draftMetadata = [CtInvocationImpl][CtVariableReadImpl]module.getDraftMetadata([CtTypeAccessImpl]com.redhat.pantheon.servlet.DEFAULT_MODULE_LOCALE, [CtVariableReadImpl]variantName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.redhat.pantheon.model.module.Metadata> releasedMetadata = [CtInvocationImpl][CtVariableReadImpl]module.getReleasedMetadata([CtTypeAccessImpl]com.redhat.pantheon.servlet.DEFAULT_MODULE_LOCALE, [CtVariableReadImpl]variantName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]com.redhat.pantheon.model.module.HashableFileResource> sourceFile = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]traverseFrom([CtVariableReadImpl]module).toChild([CtLambdaImpl]([CtParameterImpl] m) -> [CtInvocationImpl][CtVariableReadImpl]m.moduleLocale([CtTypeAccessImpl]com.redhat.pantheon.servlet.DEFAULT_MODULE_LOCALE)).toChild([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]ModuleLocale::source).toChild([CtLambdaImpl]([CtParameterImpl] sourceContent) -> [CtConditionalImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceContent.draft().isPresent() ? [CtInvocationImpl][CtVariableReadImpl]sourceContent.draft() : [CtInvocationImpl][CtVariableReadImpl]sourceContent.released()).getAsOptional();
        [CtLocalVariableImpl][CtCommentImpl]// TODO Need some DTOs to convert to maps
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> m = [CtInvocationImpl][CtSuperAccessImpl]super.resourceToMap([CtVariableReadImpl]resource);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String resourcePath = [CtInvocationImpl][CtVariableReadImpl]resource.getPath();
        [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"name", [CtInvocationImpl][CtVariableReadImpl]resource.getName());
        [CtLocalVariableImpl][CtCommentImpl]// TODO need to provide both released and draft to the api caller
        [CtTypeReferenceImpl]java.text.SimpleDateFormat sdf = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.text.SimpleDateFormat([CtLiteralImpl]"MMM dd, yyyy HH:mm");
        [CtIfImpl][CtCommentImpl]// logic for file name is present in ModuleVersionUpload.java
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]draftMetadata.isPresent() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]draftMetadata.get().moduleType().get() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"moduleType", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]draftMetadata.get().moduleType().get());
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.isPresent() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().moduleType().get() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"moduleType", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().moduleType().get());
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"moduleType", [CtLiteralImpl]"-");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]sourceFile.isPresent() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceFile.get().created().get() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"pant:dateUploaded", [CtInvocationImpl][CtVariableReadImpl]sdf.format([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sourceFile.get().created().get().getTime()));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.isPresent() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().dateUploaded().get() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"pant:dateUploaded", [CtInvocationImpl][CtVariableReadImpl]sdf.format([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().dateUploaded().get().getTime()));
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"pant:dateUploaded", [CtLiteralImpl]"-");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.isPresent() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().datePublished().get() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"pant:publishedDate", [CtInvocationImpl][CtVariableReadImpl]sdf.format([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().datePublished().get().getTime()));
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"pant:publishedDate", [CtLiteralImpl]"-");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]draftMetadata.isPresent() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]draftMetadata.get().title().get() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"jcr:title", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]draftMetadata.get().title().get());
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.isPresent() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().title().get() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"jcr:title", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().title().get());
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"jcr:title", [CtLiteralImpl]"-");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]draftMetadata.isPresent() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]draftMetadata.get().description().get() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"jcr:description", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]draftMetadata.get().description().get());
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.isPresent() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().description().get() != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"jcr:description", [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]releasedMetadata.get().description().get());
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"jcr:description", [CtLiteralImpl]"-");
        }
        [CtInvocationImpl][CtCommentImpl]// Assume the path is something like: /content/<something>/my/resource/path
        [CtVariableReadImpl]m.put([CtLiteralImpl]"pant:transientPath", [CtInvocationImpl][CtVariableReadImpl]resourcePath.substring([CtInvocationImpl][CtLiteralImpl]"/content/".length()));
        [CtLocalVariableImpl][CtCommentImpl]// Example path: /content/repositories/ben_2019-04-11_16-15-15/shared/attributes.module.adoc
        [CtArrayTypeReferenceImpl]java.lang.String[] fragments = [CtInvocationImpl][CtVariableReadImpl]resourcePath.split([CtLiteralImpl]"/");
        [CtInvocationImpl][CtCommentImpl]// Example fragments: ["", "content", "repositories", "ben_2019-04-11_16-15-15", "shared", "attributes.module.adoc"]
        [CtVariableReadImpl]m.put([CtLiteralImpl]"pant:transientSource", [CtArrayReadImpl][CtVariableReadImpl]fragments[[CtLiteralImpl]2]);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtLiteralImpl]"modules".equals([CtArrayReadImpl][CtVariableReadImpl]fragments[[CtLiteralImpl]2])) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"pant:transientSourceName", [CtArrayReadImpl][CtVariableReadImpl]fragments[[CtLiteralImpl]3]);
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]variantName.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.put([CtLiteralImpl]"variant", [CtVariableReadImpl]variantName);
        }
        [CtInvocationImpl][CtFieldReadImpl]log.trace([CtInvocationImpl][CtVariableReadImpl]m.toString());
        [CtReturnImpl]return [CtVariableReadImpl]m;
    }
}
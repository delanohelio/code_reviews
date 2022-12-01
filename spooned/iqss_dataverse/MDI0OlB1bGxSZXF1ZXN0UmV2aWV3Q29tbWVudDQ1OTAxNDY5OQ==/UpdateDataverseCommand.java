[CompilationUnitImpl][CtPackageDeclarationImpl]package edu.harvard.iq.dataverse.engine.command.impl;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DataverseFieldTypeInputLevel;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.RequiredPermissions;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.DatasetFieldType;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.search.IndexResponse;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.Dataset;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.DataverseRequest;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.batch.util.LoggingUtil;
[CtUnresolvedImport]import javax.persistence.TypedQuery;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.CommandContext;
[CtUnresolvedImport]import org.apache.solr.client.solrj.SolrServerException;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.authorization.Permission;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.AbstractCommand;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.Dataverse.DataverseType;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.engine.command.exception.CommandException;
[CtUnresolvedImport]import edu.harvard.iq.dataverse.Dataverse;
[CtImportImpl]import java.util.concurrent.Future;
[CtClassImpl][CtJavaDocImpl]/**
 * Update an existing dataverse.
 *
 * @author michael
 */
[CtAnnotationImpl]@edu.harvard.iq.dataverse.engine.command.RequiredPermissions([CtFieldReadImpl]edu.harvard.iq.dataverse.authorization.Permission.EditDataverse)
public class UpdateDataverseCommand extends [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.AbstractCommand<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse> {
    [CtFieldImpl]private final [CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse editedDv;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldType> facetList;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse> featuredDataverseList;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DataverseFieldTypeInputLevel> inputLevelList;

    [CtConstructorImpl]public UpdateDataverseCommand([CtParameterImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse editedDv, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldType> facetList, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse> featuredDataverseList, [CtParameterImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.DataverseRequest aRequest, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.DataverseFieldTypeInputLevel> inputLevelList) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]aRequest, [CtVariableReadImpl]editedDv);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.editedDv = [CtVariableReadImpl]editedDv;
        [CtIfImpl][CtCommentImpl]// add update template uses this command but does not
        [CtCommentImpl]// update facet list or featured dataverses
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]facetList != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.facetList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]facetList);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.facetList = [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]featuredDataverseList != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.featuredDataverseList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]featuredDataverseList);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.featuredDataverseList = [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]inputLevelList != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inputLevelList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtVariableReadImpl]inputLevelList);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inputLevelList = [CtLiteralImpl]null;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse execute([CtParameterImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.CommandContext ctxt) throws [CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.exception.CommandException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse.DataverseType oldDvType = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctxt.dataverses().find([CtInvocationImpl][CtFieldReadImpl]editedDv.getId()).getDataverseType();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oldDvAlias = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctxt.dataverses().find([CtInvocationImpl][CtFieldReadImpl]editedDv.getId()).getAlias();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String oldDvName = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctxt.dataverses().find([CtInvocationImpl][CtFieldReadImpl]editedDv.getId()).getName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse result = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctxt.dataverses().save([CtFieldReadImpl]editedDv);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]facetList != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctxt.facets().deleteFacetsFor([CtVariableReadImpl]result);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DatasetFieldType df : [CtFieldReadImpl]facetList) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctxt.facets().create([CtUnaryOperatorImpl][CtVariableWriteImpl]i++, [CtInvocationImpl][CtVariableReadImpl]df.getId(), [CtInvocationImpl][CtVariableReadImpl]result.getId());
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]featuredDataverseList != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctxt.featuredDataverses().deleteFeaturedDataversesFor([CtVariableReadImpl]result);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object obj : [CtFieldReadImpl]featuredDataverseList) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse dv = [CtVariableReadImpl](([CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse) (obj));
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctxt.featuredDataverses().create([CtUnaryOperatorImpl][CtVariableWriteImpl]i++, [CtInvocationImpl][CtVariableReadImpl]dv.getId(), [CtInvocationImpl][CtVariableReadImpl]result.getId());
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]inputLevelList != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctxt.fieldTypeInputLevels().deleteFacetsFor([CtVariableReadImpl]result);
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.DataverseFieldTypeInputLevel obj : [CtFieldReadImpl]inputLevelList) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctxt.fieldTypeInputLevels().create([CtVariableReadImpl]obj);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean onSuccess([CtParameterImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.engine.command.CommandContext ctxt, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object r) [CtBlockImpl]{
        [CtTryImpl][CtCommentImpl]// first kick of async index of datasets
        [CtCommentImpl]// TODO: is this actually needed? Is there a better way to handle
        try [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse result = [CtVariableReadImpl](([CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse) (r));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataset> datasets = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctxt.datasets().findByOwnerId([CtInvocationImpl][CtVariableReadImpl]result.getId());
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctxt.index().asyncIndexDatasetList([CtVariableReadImpl]datasets, [CtLiteralImpl]true);
        }[CtCatchImpl] catch ([CtTypeReferenceImpl]java.io.IOException | [CtTypeReferenceImpl]org.apache.solr.client.solrj.SolrServerException e) [CtBlockImpl]{
            [CtCommentImpl]// these datasets are being indexed asynchrounously, so not sure how to handle errors here
        }
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ctxt.dataverses().index([CtVariableReadImpl](([CtTypeReferenceImpl]edu.harvard.iq.dataverse.Dataverse) (r)));
    }
}
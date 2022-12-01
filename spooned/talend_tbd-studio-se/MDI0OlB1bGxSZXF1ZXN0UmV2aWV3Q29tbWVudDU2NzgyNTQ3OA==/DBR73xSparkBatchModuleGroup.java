[CompilationUnitImpl][CtPackageDeclarationImpl]package org.talend.hadoop.distribution.dbr73x.modulegroup;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.talend.hadoop.distribution.condition.BasicExpression;
[CtUnresolvedImport]import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
[CtUnresolvedImport]import org.talend.hadoop.distribution.constants.SparkBatchConstant;
[CtUnresolvedImport]import org.talend.hadoop.distribution.DistributionModuleGroup;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import org.talend.hadoop.distribution.condition.ComponentCondition;
[CtUnresolvedImport]import org.talend.hadoop.distribution.condition.EqualityOperator;
[CtUnresolvedImport]import org.talend.hadoop.distribution.dbr73x.DBR73xConstant;
[CtClassImpl]public class DBR73xSparkBatchModuleGroup {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.talend.hadoop.distribution.condition.ComponentCondition condition = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.condition.SimpleComponentCondition([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.condition.BasicExpression([CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, [CtFieldReadImpl]org.talend.hadoop.distribution.condition.EqualityOperator.EQ, [CtLiteralImpl]"false"));[CtCommentImpl]// $NON-NLS-1$


    [CtMethodImpl]public static [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup> getModuleGroups() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup> hs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtInvocationImpl][CtVariableReadImpl]hs.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup([CtInvocationImpl][CtTypeAccessImpl]DBR73xConstant.HIVEONSPARK_LIB_MRREQUIRED_DBR73X.getModuleName(), [CtLiteralImpl]true, [CtFieldReadImpl]org.talend.hadoop.distribution.dbr73x.modulegroup.DBR73xSparkBatchModuleGroup.condition));
        [CtInvocationImpl][CtVariableReadImpl]hs.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup([CtInvocationImpl][CtTypeAccessImpl]DBR73xConstant.SPARK_LIB_MRREQUIRED_DBR73X.getModuleName(), [CtLiteralImpl]true, [CtFieldReadImpl]org.talend.hadoop.distribution.dbr73x.modulegroup.DBR73xSparkBatchModuleGroup.condition));
        [CtInvocationImpl][CtVariableReadImpl]hs.add([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup([CtInvocationImpl][CtTypeAccessImpl]DBR73xConstant.BIGDATA_LAUNCHER_LIB_DBR73X.getModuleName(), [CtLiteralImpl]true, [CtFieldReadImpl]org.talend.hadoop.distribution.dbr73x.modulegroup.DBR73xSparkBatchModuleGroup.condition));
        [CtReturnImpl]return [CtVariableReadImpl]hs;
    }
}
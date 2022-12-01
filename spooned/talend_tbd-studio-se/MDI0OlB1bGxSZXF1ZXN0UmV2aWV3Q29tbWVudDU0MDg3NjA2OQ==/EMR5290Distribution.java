[CompilationUnitImpl][CtPackageDeclarationImpl]package org.talend.hadoop.distribution.emr5290;
[CtUnresolvedImport]import org.talend.hadoop.distribution.utils.ModuleGroupsUtils;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HiveModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.node.spark.EMR5290SparkDynamoDBNodeModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HiveOnSparkModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.component.HBaseComponent;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingSqlRowHiveNodeModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.ESparkVersion;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HBaseModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingKinesisNodeModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290SqoopModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingFlumeNodeModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290GraphFramesNodeModuleGroup;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import org.talend.hadoop.distribution.component.HCatalogComponent;
[CtImportImpl]import java.util.HashSet;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingS3NodeModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.spark.SparkClassPathUtils;
[CtUnresolvedImport]import org.talend.hadoop.distribution.component.MRComponent;
[CtUnresolvedImport]import org.talend.hadoop.distribution.DistributionModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.AbstractDistribution;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290SparkBatchParquetNodeModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.EHadoopVersion;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.talend.hadoop.distribution.condition.ComponentCondition;
[CtUnresolvedImport]import org.talend.hadoop.distribution.component.SparkStreamingComponent;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290WebHDFSModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.constants.HDFSConstant;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HCatalogModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.constants.MRConstant;
[CtUnresolvedImport]import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;
[CtUnresolvedImport]import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.spark.DynamicSparkNodeModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.component.SqoopComponent;
[CtUnresolvedImport]import org.talend.hadoop.distribution.component.HDFSComponent;
[CtUnresolvedImport]import org.talend.hadoop.distribution.constants.emr.IAmazonEMRDistribution;
[CtUnresolvedImport]import org.talend.hadoop.distribution.constants.SparkBatchConstant;
[CtUnresolvedImport]import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
[CtUnresolvedImport]import org.talend.hadoop.distribution.component.SparkBatchComponent;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290SparkBatchS3NodeModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.ComponentType;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290SparkBatchModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290SparkBatchAzureNodeModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingKafkaAssemblyModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.NodeComponentTypeBean;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr.EMRDistribution;
[CtUnresolvedImport]import org.talend.hadoop.distribution.condition.common.SparkBatchLinkedNodeCondition;
[CtUnresolvedImport]import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HDFSModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.component.HiveComponent;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290SparkStreamingModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingParquetNodeModuleGroup;
[CtUnresolvedImport]import org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290SparkBatchSqlRowHiveNodeModuleGroup;
[CtClassImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"nls")
public class EMR5290Distribution extends [CtTypeReferenceImpl]org.talend.hadoop.distribution.emr.EMRDistribution implements [CtTypeReferenceImpl]org.talend.hadoop.distribution.component.HBaseComponent , [CtTypeReferenceImpl]org.talend.hadoop.distribution.component.HDFSComponent , [CtTypeReferenceImpl]org.talend.hadoop.distribution.component.MRComponent , [CtTypeReferenceImpl]org.talend.hadoop.distribution.component.HCatalogComponent , [CtTypeReferenceImpl]org.talend.hadoop.distribution.component.HiveComponent , [CtTypeReferenceImpl]org.talend.hadoop.distribution.component.SqoopComponent , [CtTypeReferenceImpl]org.talend.hadoop.distribution.constants.emr.IAmazonEMRDistribution , [CtTypeReferenceImpl]org.talend.hadoop.distribution.component.HiveOnSparkComponent , [CtTypeReferenceImpl]org.talend.hadoop.distribution.component.SparkBatchComponent , [CtTypeReferenceImpl]org.talend.hadoop.distribution.component.SparkStreamingComponent {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String VERSION = [CtLiteralImpl]"EMR_5_29_0";[CtCommentImpl]// $NON-NLS-1$


    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String VERSION_DISPLAY = [CtLiteralImpl]"EMR 5.29.0 (Hadoop 2.8.5)";[CtCommentImpl]// $NON-NLS-1$


    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String SQOOP_emr5290_DISPLAY = [CtLiteralImpl]"EMR 5.29.0 (Sqoop 1.4.7)";[CtCommentImpl]// $NON-NLS-1$


    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String HIVE_emr5290_DISPLAY = [CtLiteralImpl]"EMR 5.29.0 (Hive 2.3.6)";[CtCommentImpl]// $NON-NLS-1$


    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String YARN_APPLICATION_CLASSPATH = [CtLiteralImpl]"$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,/usr/lib/hadoop-lzo/lib/*,/usr/share/aws/emr/emrfs/conf, /usr/share/aws/emr/emrfs/lib/*,/usr/share/aws/emr/emrfs/auxlib/*,/usr/share/aws/emr/lib/*,/usr/share/aws/emr/ddb/lib/emr-ddb-hadoop.jar, /usr/share/aws/emr/goodies/lib/emr-hadoop-goodies.jar,/usr/share/aws/emr/kinesis/lib/emr-kinesis-hadoop.jar,/usr/lib/spark/yarn/lib/datanucleus-api-jdo.jar,/usr/lib/spark/yarn/lib/datanucleus-core.jar,/usr/lib/spark/yarn/lib/datanucleus-rdbms.jar,/usr/share/aws/emr/cloudwatch-sink/lib/*";[CtCommentImpl]// $NON-NLS-1$


    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.lang.String SPARK_MODULE_GROUP_NAME = [CtLiteralImpl]"SPARK2-LIB-EMR_5_29_0_LATEST";[CtCommentImpl]// $NON-NLS-1$


    [CtFieldImpl]protected [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.talend.hadoop.distribution.ComponentType, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup>> moduleGroups;

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup>> nodeModuleGroups;

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.talend.hadoop.distribution.ComponentType, [CtTypeReferenceImpl]org.talend.hadoop.distribution.condition.ComponentCondition> displayConditions;

    [CtFieldImpl]protected [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.talend.hadoop.distribution.ComponentType, [CtTypeReferenceImpl]java.lang.String> customVersionDisplayNames;

    [CtConstructorImpl]public EMR5290Distribution() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]displayConditions = [CtInvocationImpl]buildDisplayConditions();
        [CtAssignmentImpl][CtFieldWriteImpl]customVersionDisplayNames = [CtInvocationImpl]buildCustomVersionDisplayNames();
        [CtAssignmentImpl][CtFieldWriteImpl]moduleGroups = [CtInvocationImpl]buildModuleGroups();
        [CtAssignmentImpl][CtFieldWriteImpl]nodeModuleGroups = [CtInvocationImpl]buildNodeModuleGroups([CtInvocationImpl]getDistribution(), [CtInvocationImpl]getVersion());
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.talend.hadoop.distribution.ComponentType, [CtTypeReferenceImpl]org.talend.hadoop.distribution.condition.ComponentCondition> buildDisplayConditions() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.talend.hadoop.distribution.ComponentType, [CtTypeReferenceImpl]java.lang.String> buildCustomVersionDisplayNames() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.talend.hadoop.distribution.ComponentType, [CtTypeReferenceImpl]java.lang.String> result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtTypeAccessImpl]ComponentType.HIVE, [CtFieldReadImpl]org.talend.hadoop.distribution.emr5290.EMR5290Distribution.HIVE_emr5290_DISPLAY);
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtTypeAccessImpl]ComponentType.SQOOP, [CtFieldReadImpl]org.talend.hadoop.distribution.emr5290.EMR5290Distribution.SQOOP_emr5290_DISPLAY);
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.talend.hadoop.distribution.ComponentType, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup>> buildModuleGroups() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.talend.hadoop.distribution.ComponentType, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup>> result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtTypeAccessImpl]ComponentType.HCATALOG, [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HCatalogModuleGroup.getModuleGroups());
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtTypeAccessImpl]ComponentType.HDFS, [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HDFSModuleGroup.getModuleGroups());
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtTypeAccessImpl]ComponentType.HIVE, [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HiveModuleGroup.getModuleGroups());
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtTypeAccessImpl]ComponentType.HIVEONSPARK, [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HiveOnSparkModuleGroup.getModuleGroups());
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtTypeAccessImpl]ComponentType.SQOOP, [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290SqoopModuleGroup.getModuleGroups());
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtTypeAccessImpl]ComponentType.HBASE, [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290HBaseModuleGroup.getModuleGroups());
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtTypeAccessImpl]ComponentType.SPARKBATCH, [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290SparkBatchModuleGroup.getModuleGroups());
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtTypeAccessImpl]ComponentType.SPARKSTREAMING, [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290SparkStreamingModuleGroup.getModuleGroups());
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup>> buildNodeModuleGroups([CtParameterImpl][CtTypeReferenceImpl]java.lang.String distribution, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String version) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean, [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup>> result = [CtInvocationImpl][CtSuperAccessImpl]super.buildNodeModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version);
        [CtLocalVariableImpl][CtCommentImpl]// WebHDFS
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup> webHDFSNodeModuleGroups = [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.EMR5290WebHDFSModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String hdfsComponent : [CtFieldReadImpl]org.talend.hadoop.distribution.constants.HDFSConstant.HDFS_COMPONENTS) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.HDFS, [CtVariableReadImpl]hdfsComponent), [CtVariableReadImpl]webHDFSNodeModuleGroups);
        }
        [CtInvocationImpl][CtCommentImpl]// Spark Batch Parquet nodes
        [CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKBATCH, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkBatchConstant.PARQUET_INPUT_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290SparkBatchParquetNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKBATCH, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkBatchConstant.PARQUET_OUTPUT_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290SparkBatchParquetNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtCommentImpl]// Spark Batch tSQLRow nodes
        [CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKBATCH, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkBatchConstant.SPARK_SQL_ROW_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290SparkBatchSqlRowHiveNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtCommentImpl]// Spark Batch S3 nodes
        [CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKBATCH, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkBatchConstant.S3_CONFIGURATION_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290SparkBatchS3NodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtCommentImpl]// Spark Batch DQ matching
        [CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKBATCH, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkBatchConstant.MATCH_PREDICT_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290GraphFramesNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtLocalVariableImpl][CtCommentImpl]// DynamoDB nodes ...
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup> dynamoDBNodeModuleGroups = [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.spark.EMR5290SparkDynamoDBNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version, [CtLiteralImpl]"USE_EXISTING_CONNECTION == 'false'");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup> dynamoDBConfigurationModuleGroups = [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.spark.EMR5290SparkDynamoDBNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version, [CtLiteralImpl]null);
        [CtInvocationImpl][CtCommentImpl]// ... in Spark batch
        [CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKBATCH, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkBatchConstant.DYNAMODB_INPUT_COMPONENT), [CtVariableReadImpl]dynamoDBNodeModuleGroups);
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKBATCH, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkBatchConstant.DYNAMODB_OUTPUT_COMPONENT), [CtVariableReadImpl]dynamoDBNodeModuleGroups);
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKBATCH, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkBatchConstant.DYNAMODB_CONFIGURATION_COMPONENT), [CtVariableReadImpl]dynamoDBConfigurationModuleGroups);
        [CtInvocationImpl][CtCommentImpl]// ... in Spark streaming
        [CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.DYNAMODB_INPUT_COMPONENT), [CtVariableReadImpl]dynamoDBNodeModuleGroups);
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.DYNAMODB_OUTPUT_COMPONENT), [CtVariableReadImpl]dynamoDBNodeModuleGroups);
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.DYNAMODB_CONFIGURATION_COMPONENT), [CtVariableReadImpl]dynamoDBConfigurationModuleGroups);
        [CtInvocationImpl][CtCommentImpl]// Spark Streaming Parquet nodes
        [CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.PARQUET_INPUT_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingParquetNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.PARQUET_OUTPUT_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingParquetNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.PARQUET_STREAM_INPUT_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingParquetNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtCommentImpl]// Spark Streaming tSQLRow nodes
        [CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.SPARK_SQL_ROW_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingSqlRowHiveNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtCommentImpl]// Spark Streaming S3 nodes
        [CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.S3_CONFIGURATION_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingS3NodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtCommentImpl]// Spark Streaming Kinesis nodes
        [CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.KINESIS_INPUT_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingKinesisNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.KINESIS_INPUT_AVRO_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingKinesisNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.KINESIS_OUTPUT_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingKinesisNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtCommentImpl]// Spark Streaming Kafka nodes
        [CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.KAFKA_INPUT_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.KAFKA_AVRO_INPUT_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.KAFKA_OUTPUT_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingKafkaAssemblyModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtCommentImpl]// Spark Streaming Flume nodes
        [CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.FLUME_INPUT_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingFlumeNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.FLUME_OUTPUT_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkstreaming.EMR5290SparkStreamingFlumeNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtCommentImpl]// Azure
        [CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKBATCH, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkBatchConstant.AZURE_CONFIGURATION_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290SparkBatchAzureNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtInvocationImpl][CtVariableReadImpl]result.put([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtFieldReadImpl]org.talend.hadoop.distribution.ComponentType.SPARKSTREAMING, [CtFieldReadImpl]org.talend.hadoop.distribution.constants.SparkStreamingConstant.AZURE_CONFIGURATION_COMPONENT), [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.emr5290.modulegroup.node.sparkbatch.EMR5290SparkBatchAzureNodeModuleGroup.getModuleGroups([CtVariableReadImpl]distribution, [CtVariableReadImpl]version));
        [CtReturnImpl]return [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getDistribution() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]DISTRIBUTION_NAME;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getDistributionName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]DISTRIBUTION_DISPLAY_NAME;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getVersion() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]org.talend.hadoop.distribution.emr5290.EMR5290Distribution.VERSION;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getVersionName([CtParameterImpl][CtTypeReferenceImpl]org.talend.hadoop.distribution.ComponentType componentType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String customVersionName = [CtInvocationImpl][CtFieldReadImpl]customVersionDisplayNames.get([CtVariableReadImpl]componentType);
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]customVersionName != [CtLiteralImpl]null ? [CtVariableReadImpl]customVersionName : [CtFieldReadImpl]org.talend.hadoop.distribution.emr5290.EMR5290Distribution.VERSION_DISPLAY;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.talend.hadoop.distribution.EHadoopVersion getHadoopVersion() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]org.talend.hadoop.distribution.EHadoopVersion.HADOOP_2;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportKerberos() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup> getModuleGroups([CtParameterImpl][CtTypeReferenceImpl]org.talend.hadoop.distribution.ComponentType componentType) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]moduleGroups.get([CtVariableReadImpl]componentType);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.DistributionModuleGroup> getModuleGroups([CtParameterImpl][CtTypeReferenceImpl]org.talend.hadoop.distribution.ComponentType componentType, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String componentName) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]nodeModuleGroups.get([CtConstructorCallImpl]new [CtTypeReferenceImpl]org.talend.hadoop.distribution.NodeComponentTypeBean([CtVariableReadImpl]componentType, [CtVariableReadImpl]componentName));
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportUseDatanodeHostname() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportCrossPlatformSubmission() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportSequenceFileShortType() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getYarnApplicationClasspath() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]org.talend.hadoop.distribution.emr5290.EMR5290Distribution.YARN_APPLICATION_CLASSPATH;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String generateSparkJarsPaths([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> commandLineJarsPaths) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.talend.hadoop.distribution.spark.SparkClassPathUtils.generateSparkJarsPaths([CtVariableReadImpl]commandLineJarsPaths, [CtFieldReadImpl]org.talend.hadoop.distribution.emr5290.EMR5290Distribution.SPARK_MODULE_GROUP_NAME);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportNewHBaseAPI() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportHBaseForHive() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportImpersonation() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportEmbeddedMode() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportStandaloneMode() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportHive1() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportHive2() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportTezForHive() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportSSL() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportORCFormat() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportAvroFormat() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportParquetFormat() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportStoreAsParquet() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.ESparkVersion> getSparkVersions() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]org.talend.hadoop.distribution.ESparkVersion> version = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtInvocationImpl][CtVariableReadImpl]version.add([CtTypeAccessImpl]ESparkVersion.SPARK_2_4);
        [CtReturnImpl]return [CtVariableReadImpl]version;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean isExecutedThroughSparkJobServer() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.talend.hadoop.distribution.condition.ComponentCondition getDisplayCondition([CtParameterImpl][CtTypeReferenceImpl]org.talend.hadoop.distribution.ComponentType componentType) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]displayConditions.get([CtVariableReadImpl]componentType);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportOldImportMode() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doJavaAPISupportStorePasswordInFile() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doJavaAPISqoopImportSupportDeleteTargetDir() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doJavaAPISqoopImportAllTablesSupportExcludeTable() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportS3() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportS3V4() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportParquetOutput() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportSparkStandaloneMode() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportSparkYarnClientMode() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportDynamicMemoryAllocation() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportCheckpointing() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportBackpressure() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportHDFSEncryption() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion getSparkStreamingKafkaVersion([CtParameterImpl][CtTypeReferenceImpl]org.talend.hadoop.distribution.ESparkVersion version) [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion.KAFKA_0_10;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doImportDynamoDBDependencies() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportAzureBlobStorage() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportAvroDeflateProperties() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean useOldAWSAPI() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]short orderingWeight() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]10;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean useS3AProperties() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportAssumeRole() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportExtendedAssumeRole() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean doSupportEMRFS() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]true;
    }
}
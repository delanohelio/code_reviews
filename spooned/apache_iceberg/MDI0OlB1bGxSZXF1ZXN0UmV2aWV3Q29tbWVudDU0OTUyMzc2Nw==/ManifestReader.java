[CompilationUnitImpl][CtCommentImpl]/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package org.apache.iceberg;
[CtUnresolvedImport]import org.apache.iceberg.exceptions.RuntimeIOException;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import org.apache.iceberg.io.CloseableIterator;
[CtUnresolvedImport]import org.apache.iceberg.types.Types;
[CtUnresolvedImport]import static org.apache.iceberg.expressions.Expressions.alwaysTrue;
[CtUnresolvedImport]import org.apache.iceberg.avro.AvroIterable;
[CtUnresolvedImport]import org.apache.iceberg.expressions.InclusiveMetricsEvaluator;
[CtUnresolvedImport]import org.apache.iceberg.io.CloseableIterable;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.apache.iceberg.io.CloseableGroup;
[CtUnresolvedImport]import org.apache.iceberg.avro.Avro;
[CtUnresolvedImport]import org.apache.iceberg.relocated.com.google.common.collect.ImmutableList;
[CtUnresolvedImport]import org.apache.iceberg.expressions.Evaluator;
[CtUnresolvedImport]import org.apache.iceberg.relocated.com.google.common.collect.Sets;
[CtUnresolvedImport]import org.apache.iceberg.relocated.com.google.common.collect.Lists;
[CtUnresolvedImport]import org.apache.iceberg.expressions.Expression;
[CtImportImpl]import java.util.Collection;
[CtUnresolvedImport]import org.apache.iceberg.relocated.com.google.common.base.Preconditions;
[CtUnresolvedImport]import org.apache.iceberg.expressions.Projections;
[CtUnresolvedImport]import org.apache.iceberg.io.InputFile;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import org.apache.iceberg.expressions.Expressions;
[CtClassImpl][CtJavaDocImpl]/**
 * Base reader for data and delete manifest files.
 *
 * @param <F>
 * 		The Java class of files returned by this reader.
 */
public class ManifestReader<[CtTypeParameterImpl]F extends [CtTypeReferenceImpl]org.apache.iceberg.ContentFile<[CtTypeParameterReferenceImpl]F>> extends [CtTypeReferenceImpl]org.apache.iceberg.io.CloseableGroup implements [CtTypeReferenceImpl]org.apache.iceberg.io.CloseableIterable<[CtTypeParameterReferenceImpl]F> {
    [CtFieldImpl]static final [CtTypeReferenceImpl]org.apache.iceberg.relocated.com.google.common.collect.ImmutableList<[CtTypeReferenceImpl]java.lang.String> ALL_COLUMNS = [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.relocated.com.google.common.collect.ImmutableList.of([CtLiteralImpl]"*");

    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> STATS_COLUMNS = [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.relocated.com.google.common.collect.Sets.newHashSet([CtLiteralImpl]"value_counts", [CtLiteralImpl]"null_value_counts", [CtLiteralImpl]"nan_value_counts", [CtLiteralImpl]"lower_bounds", [CtLiteralImpl]"upper_bounds", [CtLiteralImpl]"record_count");

    [CtEnumImpl]protected enum FileType {

        [CtEnumValueImpl]DATA_FILES([CtInvocationImpl][CtFieldReadImpl]org.apache.iceberg.GenericDataFile.class.getName()),
        [CtEnumValueImpl]DELETE_FILES([CtInvocationImpl][CtFieldReadImpl]org.apache.iceberg.GenericDeleteFile.class.getName());
        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String fileClass;

        [CtConstructorImpl]FileType([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fileClass) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.fileClass = [CtVariableReadImpl]fileClass;
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]java.lang.String fileClass() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]fileClass;
        }
    }

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.iceberg.io.InputFile file;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.iceberg.InheritableMetadata inheritableMetadata;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.iceberg.ManifestReader.FileType content;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> metadata;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.iceberg.PartitionSpec spec;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.apache.iceberg.Schema fileSchema;

    [CtFieldImpl][CtCommentImpl]// updated by configuration methods
    private [CtTypeReferenceImpl]org.apache.iceberg.expressions.Expression partFilter = [CtInvocationImpl]org.apache.iceberg.ManifestReader.alwaysTrue();

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.iceberg.expressions.Expression rowFilter = [CtInvocationImpl]org.apache.iceberg.ManifestReader.alwaysTrue();

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.iceberg.Schema fileProjection = [CtLiteralImpl]null;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> columns = [CtLiteralImpl]null;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean caseSensitive = [CtLiteralImpl]true;

    [CtFieldImpl][CtCommentImpl]// lazily initialized
    private [CtTypeReferenceImpl]org.apache.iceberg.expressions.Evaluator lazyEvaluator = [CtLiteralImpl]null;

    [CtFieldImpl]private [CtTypeReferenceImpl]org.apache.iceberg.expressions.InclusiveMetricsEvaluator lazyMetricsEvaluator = [CtLiteralImpl]null;

    [CtConstructorImpl]protected ManifestReader([CtParameterImpl][CtTypeReferenceImpl]org.apache.iceberg.io.InputFile file, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]org.apache.iceberg.PartitionSpec> specsById, [CtParameterImpl][CtTypeReferenceImpl]org.apache.iceberg.InheritableMetadata inheritableMetadata, [CtParameterImpl][CtTypeReferenceImpl]org.apache.iceberg.ManifestReader.FileType content) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.file = [CtVariableReadImpl]file;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.inheritableMetadata = [CtVariableReadImpl]inheritableMetadata;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.content = [CtVariableReadImpl]content;
        [CtTryImpl]try [CtBlockImpl]{
            [CtTryWithResourceImpl]try ([CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.iceberg.avro.AvroIterable<[CtTypeReferenceImpl]org.apache.iceberg.ManifestEntry<[CtTypeParameterReferenceImpl]F>> headerReader = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.avro.Avro.read([CtVariableReadImpl]file).project([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.ManifestEntry.getSchema([CtInvocationImpl][CtTypeAccessImpl]Types.StructType.of()).select([CtLiteralImpl]"status")).build()) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.metadata = [CtInvocationImpl][CtVariableReadImpl]headerReader.getMetadata();
            }
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.iceberg.exceptions.RuntimeIOException([CtVariableReadImpl]e);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int specId = [CtFieldReadImpl]TableMetadata.INITIAL_SPEC_ID;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String specProperty = [CtInvocationImpl][CtFieldReadImpl]metadata.get([CtLiteralImpl]"partition-spec-id");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]specProperty != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]specId = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.parseInt([CtVariableReadImpl]specProperty);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]specsById != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.spec = [CtInvocationImpl][CtVariableReadImpl]specsById.get([CtVariableReadImpl]specId);
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.iceberg.Schema schema = [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.SchemaParser.fromJson([CtInvocationImpl][CtFieldReadImpl]metadata.get([CtLiteralImpl]"schema"));
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.spec = [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.PartitionSpecParser.fromJsonFields([CtVariableReadImpl]schema, [CtVariableReadImpl]specId, [CtInvocationImpl][CtFieldReadImpl]metadata.get([CtLiteralImpl]"partition-spec"));
        }
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.fileSchema = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.iceberg.Schema([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.DataFile.getType([CtInvocationImpl][CtFieldReadImpl]spec.partitionType()).fields());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isDeleteManifestReader() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl]content == [CtFieldReadImpl][CtTypeAccessImpl]org.apache.iceberg.ManifestReader.FileType.[CtFieldReferenceImpl]DELETE_FILES;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.iceberg.io.InputFile file() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]file;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.iceberg.Schema schema() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]fileSchema;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.iceberg.PartitionSpec spec() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]spec;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.iceberg.ManifestReader<[CtTypeParameterReferenceImpl]F> select([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> newColumns) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.relocated.com.google.common.base.Preconditions.checkState([CtBinaryOperatorImpl][CtFieldReadImpl]fileProjection == [CtLiteralImpl]null, [CtLiteralImpl]"Cannot select columns using both select(String...) and project(Schema)");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.columns = [CtVariableReadImpl]newColumns;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.iceberg.ManifestReader<[CtTypeParameterReferenceImpl]F> project([CtParameterImpl][CtTypeReferenceImpl]org.apache.iceberg.Schema newFileProjection) [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.relocated.com.google.common.base.Preconditions.checkState([CtBinaryOperatorImpl][CtFieldReadImpl]columns == [CtLiteralImpl]null, [CtLiteralImpl]"Cannot select columns using both select(String...) and project(Schema)");
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.fileProjection = [CtVariableReadImpl]newFileProjection;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.iceberg.ManifestReader<[CtTypeParameterReferenceImpl]F> filterPartitions([CtParameterImpl][CtTypeReferenceImpl]org.apache.iceberg.expressions.Expression expr) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.partFilter = [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.expressions.Expressions.and([CtFieldReadImpl]partFilter, [CtVariableReadImpl]expr);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.iceberg.ManifestReader<[CtTypeParameterReferenceImpl]F> filterRows([CtParameterImpl][CtTypeReferenceImpl]org.apache.iceberg.expressions.Expression expr) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.rowFilter = [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.expressions.Expressions.and([CtFieldReadImpl]rowFilter, [CtVariableReadImpl]expr);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]org.apache.iceberg.ManifestReader<[CtTypeParameterReferenceImpl]F> caseSensitive([CtParameterImpl][CtTypeReferenceImpl]boolean isCaseSensitive) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.caseSensitive = [CtVariableReadImpl]isCaseSensitive;
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtTypeReferenceImpl]org.apache.iceberg.io.CloseableIterable<[CtTypeReferenceImpl]org.apache.iceberg.ManifestEntry<[CtTypeParameterReferenceImpl]F>> entries() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]rowFilter != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]rowFilter != [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.expressions.Expressions.alwaysTrue())) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtFieldReadImpl]partFilter != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]partFilter != [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.expressions.Expressions.alwaysTrue()))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.iceberg.expressions.Evaluator evaluator = [CtInvocationImpl]evaluator();
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.iceberg.expressions.InclusiveMetricsEvaluator metricsEvaluator = [CtInvocationImpl]metricsEvaluator();
            [CtLocalVariableImpl][CtCommentImpl]// ensure stats columns are present for metrics evaluation
            [CtTypeReferenceImpl]boolean requireStatsProjection = [CtInvocationImpl]org.apache.iceberg.ManifestReader.requireStatsProjection([CtFieldReadImpl]rowFilter, [CtFieldReadImpl]columns);
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> projectColumns = [CtConditionalImpl]([CtVariableReadImpl]requireStatsProjection) ? [CtInvocationImpl]org.apache.iceberg.ManifestReader.withStatsColumns([CtFieldReadImpl]columns) : [CtFieldReadImpl]columns;
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.io.CloseableIterable.filter([CtInvocationImpl]open([CtInvocationImpl]org.apache.iceberg.ManifestReader.projection([CtFieldReadImpl]fileSchema, [CtFieldReadImpl]fileProjection, [CtVariableReadImpl]projectColumns, [CtFieldReadImpl]caseSensitive)), [CtLambdaImpl]([CtParameterImpl] entry) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]entry != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]evaluator.eval([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.file().partition())) && [CtInvocationImpl][CtVariableReadImpl]metricsEvaluator.eval([CtInvocationImpl][CtVariableReadImpl]entry.file()));
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]open([CtInvocationImpl]org.apache.iceberg.ManifestReader.projection([CtFieldReadImpl]fileSchema, [CtFieldReadImpl]fileProjection, [CtFieldReadImpl]columns, [CtFieldReadImpl]caseSensitive));
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.iceberg.io.CloseableIterable<[CtTypeReferenceImpl]org.apache.iceberg.ManifestEntry<[CtTypeParameterReferenceImpl]F>> open([CtParameterImpl][CtTypeReferenceImpl]org.apache.iceberg.Schema projection) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.iceberg.FileFormat format = [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.FileFormat.fromFileName([CtInvocationImpl][CtFieldReadImpl]file.location());
        [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.relocated.com.google.common.base.Preconditions.checkArgument([CtBinaryOperatorImpl][CtVariableReadImpl]format != [CtLiteralImpl]null, [CtLiteralImpl]"Unable to determine format of manifest: %s", [CtFieldReadImpl]file);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.apache.iceberg.types.Types.NestedField> fields = [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.relocated.com.google.common.collect.Lists.newArrayList();
        [CtInvocationImpl][CtVariableReadImpl]fields.addAll([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]projection.asStruct().fields());
        [CtInvocationImpl][CtVariableReadImpl]fields.add([CtTypeAccessImpl]MetadataColumns.ROW_POSITION);
        [CtSwitchImpl]switch ([CtVariableReadImpl]format) {
            [CtCaseImpl]case [CtFieldReadImpl]AVRO :
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.iceberg.avro.AvroIterable<[CtTypeReferenceImpl]org.apache.iceberg.ManifestEntry<[CtTypeParameterReferenceImpl]F>> reader = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.avro.Avro.read([CtFieldReadImpl]file).project([CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.ManifestEntry.wrapFileSchema([CtInvocationImpl][CtTypeAccessImpl]Types.StructType.of([CtVariableReadImpl]fields))).rename([CtLiteralImpl]"manifest_entry", [CtInvocationImpl][CtFieldReadImpl]org.apache.iceberg.GenericManifestEntry.class.getName()).rename([CtLiteralImpl]"partition", [CtInvocationImpl][CtFieldReadImpl]org.apache.iceberg.PartitionData.class.getName()).rename([CtLiteralImpl]"r102", [CtInvocationImpl][CtFieldReadImpl]org.apache.iceberg.PartitionData.class.getName()).rename([CtLiteralImpl]"data_file", [CtInvocationImpl][CtFieldReadImpl]content.fileClass()).rename([CtLiteralImpl]"r2", [CtInvocationImpl][CtFieldReadImpl]content.fileClass()).classLoader([CtInvocationImpl][CtFieldReadImpl]org.apache.iceberg.GenericManifestEntry.class.getClassLoader()).reuseContainers().build();
                [CtInvocationImpl]addCloseable([CtVariableReadImpl]reader);
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.io.CloseableIterable.transform([CtVariableReadImpl]reader, [CtExecutableReferenceExpressionImpl][CtFieldReadImpl]inheritableMetadata::apply);
            [CtCaseImpl]default :
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException([CtBinaryOperatorImpl][CtLiteralImpl]"Invalid format for manifest file: " + [CtVariableReadImpl]format);
        }
    }

    [CtMethodImpl][CtTypeReferenceImpl]org.apache.iceberg.io.CloseableIterable<[CtTypeReferenceImpl]org.apache.iceberg.ManifestEntry<[CtTypeParameterReferenceImpl]F>> liveEntries() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.io.CloseableIterable.filter([CtInvocationImpl]entries(), [CtLambdaImpl]([CtParameterImpl] entry) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]entry != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]entry.status() != [CtVariableReadImpl]ManifestEntry.Status.DELETED));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return an Iterator of DataFile. Makes defensive copies of files before returning
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]org.apache.iceberg.io.CloseableIterator<[CtTypeParameterReferenceImpl]F> iterator() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]org.apache.iceberg.ManifestReader.dropStats([CtFieldReadImpl]rowFilter, [CtFieldReadImpl]columns)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.io.CloseableIterable.transform([CtInvocationImpl]liveEntries(), [CtLambdaImpl]([CtParameterImpl] e) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.file().copyWithoutStats()).iterator();
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.io.CloseableIterable.transform([CtInvocationImpl]liveEntries(), [CtLambdaImpl]([CtParameterImpl] e) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.file().copy()).iterator();
        }
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.apache.iceberg.Schema projection([CtParameterImpl][CtTypeReferenceImpl]org.apache.iceberg.Schema schema, [CtParameterImpl][CtTypeReferenceImpl]org.apache.iceberg.Schema project, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> columns, [CtParameterImpl][CtTypeReferenceImpl]boolean caseSensitive) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]columns != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]caseSensitive) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]schema.select([CtVariableReadImpl]columns);
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]schema.caseInsensitiveSelect([CtVariableReadImpl]columns);
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]project != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]project;
        }
        [CtReturnImpl]return [CtVariableReadImpl]schema;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.iceberg.expressions.Evaluator evaluator() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]lazyEvaluator == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.iceberg.expressions.Expression projected = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.expressions.Projections.inclusive([CtFieldReadImpl]spec, [CtFieldReadImpl]caseSensitive).project([CtFieldReadImpl]rowFilter);
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.apache.iceberg.expressions.Expression finalPartFilter = [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.expressions.Expressions.and([CtVariableReadImpl]projected, [CtFieldReadImpl]partFilter);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]finalPartFilter != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.lazyEvaluator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.iceberg.expressions.Evaluator([CtInvocationImpl][CtFieldReadImpl]spec.partitionType(), [CtVariableReadImpl]finalPartFilter, [CtFieldReadImpl]caseSensitive);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.lazyEvaluator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.iceberg.expressions.Evaluator([CtInvocationImpl][CtFieldReadImpl]spec.partitionType(), [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.expressions.Expressions.alwaysTrue(), [CtFieldReadImpl]caseSensitive);
            }
        }
        [CtReturnImpl]return [CtFieldReadImpl]lazyEvaluator;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.apache.iceberg.expressions.InclusiveMetricsEvaluator metricsEvaluator() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]lazyMetricsEvaluator == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]rowFilter != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.lazyMetricsEvaluator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.iceberg.expressions.InclusiveMetricsEvaluator([CtInvocationImpl][CtFieldReadImpl]spec.schema(), [CtFieldReadImpl]rowFilter, [CtFieldReadImpl]caseSensitive);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.lazyMetricsEvaluator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.apache.iceberg.expressions.InclusiveMetricsEvaluator([CtInvocationImpl][CtFieldReadImpl]spec.schema(), [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.expressions.Expressions.alwaysTrue(), [CtFieldReadImpl]caseSensitive);
            }
        }
        [CtReturnImpl]return [CtFieldReadImpl]lazyMetricsEvaluator;
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]boolean requireStatsProjection([CtParameterImpl][CtTypeReferenceImpl]org.apache.iceberg.expressions.Expression rowFilter, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> columns) [CtBlockImpl]{
        [CtReturnImpl][CtCommentImpl]// Make sure we have all stats columns for metrics evaluator
        return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]rowFilter != [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.expressions.Expressions.alwaysTrue()) && [CtBinaryOperatorImpl]([CtVariableReadImpl]columns != [CtLiteralImpl]null)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]columns.containsAll([CtFieldReadImpl][CtTypeAccessImpl]org.apache.iceberg.ManifestReader.[CtFieldReferenceImpl]ALL_COLUMNS))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]columns.containsAll([CtFieldReadImpl]org.apache.iceberg.ManifestReader.STATS_COLUMNS));
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]boolean dropStats([CtParameterImpl][CtTypeReferenceImpl]org.apache.iceberg.expressions.Expression rowFilter, [CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]java.lang.String> columns) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// Make sure we only drop all stats if we had projected all stats
        [CtCommentImpl]// We do not drop stats even if we had partially added some stats columns, except for record_count column.
        [CtCommentImpl]// Since we don't want to keep stats map which could be huge in size just because we select record_count, which
        [CtCommentImpl]// is a primitive type.
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]rowFilter != [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.expressions.Expressions.alwaysTrue()) && [CtBinaryOperatorImpl]([CtVariableReadImpl]columns != [CtLiteralImpl]null)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]columns.containsAll([CtFieldReadImpl][CtTypeAccessImpl]org.apache.iceberg.ManifestReader.[CtFieldReferenceImpl]ALL_COLUMNS))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> interaction = [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.relocated.com.google.common.collect.Sets.intersection([CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.relocated.com.google.common.collect.Sets.newHashSet([CtVariableReadImpl]columns), [CtFieldReadImpl]org.apache.iceberg.ManifestReader.STATS_COLUMNS);
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]interaction.isEmpty() || [CtInvocationImpl][CtVariableReadImpl]interaction.equals([CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.relocated.com.google.common.collect.Sets.newHashSet([CtLiteralImpl]"record_count"));
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]static [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> withStatsColumns([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> columns) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]columns.containsAll([CtFieldReadImpl][CtTypeAccessImpl]org.apache.iceberg.ManifestReader.[CtFieldReferenceImpl]ALL_COLUMNS)) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]columns;
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> projectColumns = [CtInvocationImpl][CtTypeAccessImpl]org.apache.iceberg.relocated.com.google.common.collect.Lists.newArrayList([CtVariableReadImpl]columns);
            [CtInvocationImpl][CtVariableReadImpl]projectColumns.addAll([CtFieldReadImpl]org.apache.iceberg.ManifestReader.STATS_COLUMNS);[CtCommentImpl]// order doesn't matter

            [CtReturnImpl]return [CtVariableReadImpl]projectColumns;
        }
    }
}
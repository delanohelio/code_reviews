[CompilationUnitImpl][CtPackageDeclarationImpl]package de.bonndan.nivio.input;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.net.MalformedURLException;
[CtUnresolvedImport]import de.bonndan.nivio.input.dto.ItemDescription;
[CtUnresolvedImport]import de.bonndan.nivio.model.LandscapeImpl;
[CtUnresolvedImport]import org.springframework.beans.PropertyAccessorFactory;
[CtUnresolvedImport]import org.springframework.beans.PropertyAccessor;
[CtImportImpl]import java.net.URL;
[CtUnresolvedImport]import de.bonndan.nivio.input.dto.LandscapeDescription;
[CtUnresolvedImport]import org.springframework.beans.NotWritablePropertyException;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import org.springframework.util.StringUtils;
[CtClassImpl][CtJavaDocImpl]/**
 * Inspects item description labels for keys starting with "nivio" and tries to set the corresponding values to fields.
 */
public class LabelToFieldProcessor {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String NIVIO_LABEL_PREFIX = [CtLiteralImpl]"nivio.";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String COLLECTION_DELIMITER = [CtLiteralImpl]",";

    [CtFieldImpl]private final [CtTypeReferenceImpl]de.bonndan.nivio.input.ProcessLog logger;

    [CtConstructorImpl]public LabelToFieldProcessor([CtParameterImpl][CtTypeReferenceImpl]de.bonndan.nivio.input.ProcessLog logger) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.logger = [CtVariableReadImpl]logger;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void process([CtParameterImpl][CtTypeReferenceImpl]de.bonndan.nivio.input.dto.LandscapeDescription input, [CtParameterImpl][CtTypeReferenceImpl]de.bonndan.nivio.model.LandscapeImpl landscape) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]input.getItemDescriptions().all().forEach([CtLambdaImpl]([CtParameterImpl] item) -> [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]item.getLabels().entrySet().stream().filter([CtLambdaImpl]([CtParameterImpl] entry) -> [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getKey().toLowerCase().startsWith([CtFieldReadImpl][CtFieldReferenceImpl]NIVIO_LABEL_PREFIX)).forEach([CtLambdaImpl]([CtParameterImpl] entry) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String field = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getKey().substring([CtInvocationImpl][CtVariableReadImpl]LabelToFieldProcessor.NIVIO_LABEL_PREFIX.length());
                [CtInvocationImpl]setValue([CtVariableReadImpl]item, [CtVariableReadImpl]field, [CtInvocationImpl][CtVariableReadImpl]entry.getValue());
            });
        });
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void setValue([CtParameterImpl][CtTypeReferenceImpl]de.bonndan.nivio.input.dto.ItemDescription item, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String name, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String value) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.isEmpty([CtVariableReadImpl]value)) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.springframework.beans.PropertyAccessor myAccessor = [CtInvocationImpl][CtTypeAccessImpl]org.springframework.beans.PropertyAccessorFactory.forBeanPropertyAccess([CtVariableReadImpl]item);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class<[CtWildcardReferenceImpl]?> propertyType = [CtInvocationImpl][CtVariableReadImpl]myAccessor.getPropertyType([CtVariableReadImpl]name);
        [CtTryImpl]try [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]propertyType != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]propertyType.isAssignableFrom([CtFieldReadImpl]java.util.List.class)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] o = [CtInvocationImpl]de.bonndan.nivio.input.LabelToFieldProcessor.getParts([CtVariableReadImpl]value);
                [CtInvocationImpl][CtVariableReadImpl]myAccessor.setPropertyValue([CtVariableReadImpl]name, [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]o));
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]propertyType != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]propertyType.isAssignableFrom([CtFieldReadImpl]java.util.Set.class)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] o = [CtInvocationImpl]de.bonndan.nivio.input.LabelToFieldProcessor.getParts([CtVariableReadImpl]value);
                [CtInvocationImpl][CtVariableReadImpl]myAccessor.setPropertyValue([CtVariableReadImpl]name, [CtInvocationImpl][CtTypeAccessImpl]java.util.Set.of([CtVariableReadImpl]o));
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]propertyType != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]propertyType.isAssignableFrom([CtFieldReadImpl]java.util.Map.class)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] o = [CtInvocationImpl]de.bonndan.nivio.input.LabelToFieldProcessor.getParts([CtVariableReadImpl]value);
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map propertyValue = [CtInvocationImpl](([CtTypeReferenceImpl]java.util.Map) ([CtVariableReadImpl]myAccessor.getPropertyValue([CtVariableReadImpl]name)));
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl][CtVariableReadImpl]o.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]ItemDescription.LINKS_FIELD.equals([CtVariableReadImpl]name)) [CtBlockImpl]{
                        [CtTryImpl]try [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]propertyValue.put([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtLiteralImpl]1), [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.net.URL([CtArrayReadImpl][CtVariableReadImpl]o[[CtVariableReadImpl]i]));
                        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.net.MalformedURLException e) [CtBlockImpl]{
                            [CtInvocationImpl][CtFieldReadImpl]logger.warn([CtBinaryOperatorImpl][CtLiteralImpl]"Failed to parse link " + [CtArrayReadImpl][CtVariableReadImpl]o[[CtVariableReadImpl]i]);
                        }
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]propertyValue.put([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtBinaryOperatorImpl][CtVariableReadImpl]i + [CtLiteralImpl]1), [CtArrayReadImpl][CtVariableReadImpl]o[[CtVariableReadImpl]i]);
                    }
                }
                [CtReturnImpl]return;
            }
            [CtInvocationImpl][CtVariableReadImpl]myAccessor.setPropertyValue([CtVariableReadImpl]name, [CtInvocationImpl][CtVariableReadImpl]value.trim());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]org.springframework.beans.NotWritablePropertyException e) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]logger.warn([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Failed to write field '" + [CtVariableReadImpl]name) + [CtLiteralImpl]"' via label");
        }
    }

    [CtMethodImpl]private static [CtArrayTypeReferenceImpl]java.lang.String[] getParts([CtParameterImpl][CtTypeReferenceImpl]java.lang.String value) [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] split = [CtInvocationImpl][CtTypeAccessImpl]org.springframework.util.StringUtils.split([CtVariableReadImpl]value, [CtFieldReadImpl]de.bonndan.nivio.input.LabelToFieldProcessor.COLLECTION_DELIMITER);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]split == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[]{ [CtInvocationImpl][CtVariableReadImpl]value.trim() };
        }
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.stream([CtVariableReadImpl]split).map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.String::trim).toArray([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl][CtArrayTypeReferenceImpl]java.lang.String[]::new);
    }
}
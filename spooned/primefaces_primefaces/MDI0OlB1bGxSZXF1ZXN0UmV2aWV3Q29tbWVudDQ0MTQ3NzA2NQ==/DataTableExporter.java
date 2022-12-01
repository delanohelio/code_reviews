[CompilationUnitImpl][CtCommentImpl]/* The MIT License

Copyright (c) 2009-2020 PrimeTek

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
[CtPackageDeclarationImpl]package org.primefaces.component.datatable.export;
[CtUnresolvedImport]import javax.faces.component.visit.VisitResult;
[CtImportImpl]import java.util.HashMap;
[CtUnresolvedImport]import javax.faces.component.*;
[CtUnresolvedImport]import javax.faces.component.visit.VisitCallback;
[CtUnresolvedImport]import org.primefaces.model.LazyDataModel;
[CtUnresolvedImport]import javax.faces.component.visit.VisitContext;
[CtUnresolvedImport]import org.primefaces.component.export.Exporter;
[CtUnresolvedImport]import javax.el.MethodExpression;
[CtUnresolvedImport]import javax.faces.component.html.HtmlCommandLink;
[CtUnresolvedImport]import javax.servlet.http.HttpServletRequest;
[CtUnresolvedImport]import javax.faces.context.FacesContext;
[CtUnresolvedImport]import org.primefaces.component.celleditor.CellEditor;
[CtUnresolvedImport]import javax.faces.component.html.HtmlGraphicImage;
[CtUnresolvedImport]import org.primefaces.component.datatable.DataTable;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Collections;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import javax.faces.context.ExternalContext;
[CtUnresolvedImport]import org.primefaces.component.export.ExportConfiguration;
[CtUnresolvedImport]import org.primefaces.component.overlaypanel.OverlayPanel;
[CtUnresolvedImport]import org.primefaces.util.Constants;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import javax.faces.convert.Converter;
[CtImportImpl]import java.lang.reflect.Array;
[CtUnresolvedImport]import org.primefaces.util.ComponentUtils;
[CtUnresolvedImport]import javax.faces.FacesException;
[CtImportImpl]import java.util.Collection;
[CtImportImpl]import java.util.Map;
[CtImportImpl]import java.util.Arrays;
[CtClassImpl]public abstract class DataTableExporter implements [CtTypeReferenceImpl]org.primefaces.component.export.Exporter<[CtTypeReferenceImpl]org.primefaces.component.datatable.DataTable> {
    [CtEnumImpl]protected enum ColumnType {

        [CtEnumValueImpl]HEADER([CtLiteralImpl]"header"),
        [CtEnumValueImpl]FOOTER([CtLiteralImpl]"footer");
        [CtFieldImpl]private final [CtTypeReferenceImpl]java.lang.String facet;

        [CtConstructorImpl]ColumnType([CtParameterImpl][CtTypeReferenceImpl]java.lang.String facet) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.facet = [CtVariableReadImpl]facet;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String facet() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]facet;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]java.lang.String toString() [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]facet;
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]UIColumn> getColumnsToExport([CtParameterImpl][CtTypeReferenceImpl]UIData table) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]table.getChildren().stream().filter([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]org.primefaces.component.datatable.export.UIColumn.class::isInstance).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]org.primefaces.component.datatable.export.UIColumn.class::cast).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]boolean hasColumnFooter([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]UIColumn> columns) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]columns.stream().anyMatch([CtLambdaImpl]([CtParameterImpl] c) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]c.getFooter() != [CtLiteralImpl]null);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]java.lang.String exportColumnByFunction([CtParameterImpl][CtTypeReferenceImpl]javax.faces.context.FacesContext context, [CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.api.UIColumn column) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]javax.el.MethodExpression exportFunction = [CtInvocationImpl][CtVariableReadImpl]column.getExportFunction();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]exportFunction != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtVariableReadImpl]exportFunction.invoke([CtInvocationImpl][CtVariableReadImpl]context.getELContext(), [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.Object[]{ [CtVariableReadImpl]column })));
        }
        [CtReturnImpl]return [CtFieldReadImpl]org.primefaces.util.Constants.EMPTY_STRING;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String exportValue([CtParameterImpl][CtTypeReferenceImpl]javax.faces.context.FacesContext context, [CtParameterImpl][CtTypeReferenceImpl]UIComponent component) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]component instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]javax.faces.component.html.HtmlCommandLink) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// support for PrimeFaces and standard HtmlCommandLink
            [CtTypeReferenceImpl]javax.faces.component.html.HtmlCommandLink link = [CtVariableReadImpl](([CtTypeReferenceImpl]javax.faces.component.html.HtmlCommandLink) (component));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object value = [CtInvocationImpl][CtVariableReadImpl]link.getValue();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.String.valueOf([CtVariableReadImpl]value);
            } else [CtBlockImpl]{
                [CtForEachImpl][CtCommentImpl]// export first value holder
                for ([CtLocalVariableImpl][CtTypeReferenceImpl]UIComponent child : [CtInvocationImpl][CtVariableReadImpl]link.getChildren()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]child instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ValueHolder) [CtBlockImpl]{
                        [CtReturnImpl]return [CtInvocationImpl]exportValue([CtVariableReadImpl]context, [CtVariableReadImpl]child);
                    }
                }
                [CtReturnImpl]return [CtFieldReadImpl]org.primefaces.util.Constants.EMPTY_STRING;
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]component instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ValueHolder) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]component instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]EditableValueHolder) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object submittedValue = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]EditableValueHolder) (component)).getSubmittedValue();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]submittedValue != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]submittedValue.toString();
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]ValueHolder valueHolder = [CtVariableReadImpl](([CtTypeReferenceImpl]ValueHolder) (component));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object value = [CtInvocationImpl][CtVariableReadImpl]valueHolder.getValue();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtFieldReadImpl]org.primefaces.util.Constants.EMPTY_STRING;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]javax.faces.convert.Converter converter = [CtInvocationImpl][CtVariableReadImpl]valueHolder.getConverter();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]converter == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Class valueType = [CtInvocationImpl][CtVariableReadImpl]value.getClass();
                [CtAssignmentImpl][CtVariableWriteImpl]converter = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getApplication().createConverter([CtVariableReadImpl]valueType);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]converter != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]component instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]UISelectMany) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder builder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List collection = [CtLiteralImpl]null;
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]java.util.List) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]collection = [CtVariableReadImpl](([CtTypeReferenceImpl]java.util.List) (value));
                    } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]value.getClass().isArray()) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]collection = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtVariableReadImpl]value);
                    } else [CtBlockImpl]{
                        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.faces.FacesException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Value of " + [CtInvocationImpl][CtVariableReadImpl]component.getClientId([CtVariableReadImpl]context)) + [CtLiteralImpl]" must be a List or an Array.");
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int collectionSize = [CtInvocationImpl][CtVariableReadImpl]collection.size();
                    [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]collectionSize; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object object = [CtInvocationImpl][CtVariableReadImpl]collection.get([CtVariableReadImpl]i);
                        [CtInvocationImpl][CtVariableReadImpl]builder.append([CtInvocationImpl][CtVariableReadImpl]converter.getAsString([CtVariableReadImpl]context, [CtVariableReadImpl]component, [CtVariableReadImpl]object));
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtBinaryOperatorImpl]([CtVariableReadImpl]collectionSize - [CtLiteralImpl]1)) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]builder.append([CtLiteralImpl]",");
                        }
                    }
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String valuesAsString = [CtInvocationImpl][CtVariableReadImpl]builder.toString();
                    [CtInvocationImpl][CtVariableReadImpl]builder.setLength([CtLiteralImpl]0);
                    [CtReturnImpl]return [CtVariableReadImpl]valuesAsString;
                } else [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]converter.getAsString([CtVariableReadImpl]context, [CtVariableReadImpl]component, [CtVariableReadImpl]value);
                }
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]value.toString();
            }
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]component instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.primefaces.component.celleditor.CellEditor) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]exportValue([CtVariableReadImpl]context, [CtInvocationImpl][CtVariableReadImpl]component.getFacet([CtLiteralImpl]"output"));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]component instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]javax.faces.component.html.HtmlGraphicImage) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.lang.String) ([CtInvocationImpl][CtVariableReadImpl]component.getAttributes().get([CtLiteralImpl]"alt")));
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]component instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.primefaces.component.overlaypanel.OverlayPanel) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]org.primefaces.util.Constants.EMPTY_STRING;
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// This would get the plain texts on UIInstructions when using Facelets
            [CtTypeReferenceImpl]java.lang.String value = [CtInvocationImpl][CtVariableReadImpl]component.toString();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]value != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]value.trim();
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtFieldReadImpl]org.primefaces.util.Constants.EMPTY_STRING;
            }
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void exportPageOnly([CtParameterImpl][CtTypeReferenceImpl]javax.faces.context.FacesContext context, [CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.datatable.DataTable table, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object document) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int first = [CtInvocationImpl][CtVariableReadImpl]table.getFirst();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int rows = [CtInvocationImpl][CtVariableReadImpl]table.getRows();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]rows == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]rows = [CtInvocationImpl][CtVariableReadImpl]table.getRowCount();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int rowsToExport = [CtBinaryOperatorImpl][CtVariableReadImpl]first + [CtVariableReadImpl]rows;
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int rowIndex = [CtVariableReadImpl]first; [CtBinaryOperatorImpl][CtVariableReadImpl]rowIndex < [CtVariableReadImpl]rowsToExport; [CtUnaryOperatorImpl][CtVariableWriteImpl]rowIndex++) [CtBlockImpl]{
            [CtInvocationImpl]exportRow([CtVariableReadImpl]table, [CtVariableReadImpl]document, [CtVariableReadImpl]rowIndex);
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void exportAll([CtParameterImpl][CtTypeReferenceImpl]javax.faces.context.FacesContext context, [CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.datatable.DataTable table, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object document) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int first = [CtInvocationImpl][CtVariableReadImpl]table.getFirst();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int rowCount = [CtInvocationImpl][CtVariableReadImpl]table.getRowCount();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int rows = [CtInvocationImpl][CtVariableReadImpl]table.getRows();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean lazy = [CtInvocationImpl][CtVariableReadImpl]table.isLazy();
        [CtIfImpl]if ([CtVariableReadImpl]lazy) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.primefaces.model.LazyDataModel<[CtWildcardReferenceImpl]?> lazyDataModel = [CtInvocationImpl](([CtTypeReferenceImpl]org.primefaces.model.LazyDataModel<[CtWildcardReferenceImpl]?>) ([CtVariableReadImpl]table.getValue()));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtWildcardReferenceImpl]?> wrappedData = [CtInvocationImpl][CtVariableReadImpl]lazyDataModel.getWrappedData();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]rowCount > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]table.setFirst([CtLiteralImpl]0);
                [CtInvocationImpl][CtVariableReadImpl]table.setRows([CtVariableReadImpl]rowCount);
                [CtInvocationImpl][CtVariableReadImpl]table.clearLazyCache();
                [CtInvocationImpl][CtVariableReadImpl]table.loadLazyData();
            }
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int rowIndex = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]rowIndex < [CtVariableReadImpl]rowCount; [CtUnaryOperatorImpl][CtVariableWriteImpl]rowIndex++) [CtBlockImpl]{
                [CtInvocationImpl]exportRow([CtVariableReadImpl]table, [CtVariableReadImpl]document, [CtVariableReadImpl]rowIndex);
            }
            [CtInvocationImpl][CtCommentImpl]// restore
            [CtVariableReadImpl]table.setFirst([CtVariableReadImpl]first);
            [CtInvocationImpl][CtVariableReadImpl]table.setRows([CtVariableReadImpl]rows);
            [CtInvocationImpl][CtVariableReadImpl]table.setRowIndex([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
            [CtInvocationImpl][CtVariableReadImpl]table.clearLazyCache();
            [CtInvocationImpl][CtVariableReadImpl]lazyDataModel.setWrappedData([CtVariableReadImpl]wrappedData);
            [CtInvocationImpl][CtVariableReadImpl]lazyDataModel.setPageSize([CtVariableReadImpl]rows);
            [CtInvocationImpl][CtVariableReadImpl]lazyDataModel.setRowIndex([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
        } else [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int rowIndex = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]rowIndex < [CtVariableReadImpl]rowCount; [CtUnaryOperatorImpl][CtVariableWriteImpl]rowIndex++) [CtBlockImpl]{
                [CtInvocationImpl]exportRow([CtVariableReadImpl]table, [CtVariableReadImpl]document, [CtVariableReadImpl]rowIndex);
            }
            [CtInvocationImpl][CtCommentImpl]// restore
            [CtVariableReadImpl]table.setFirst([CtVariableReadImpl]first);
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void exportRow([CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.datatable.DataTable table, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object document, [CtParameterImpl][CtTypeReferenceImpl]int rowIndex) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]table.setRowIndex([CtVariableReadImpl]rowIndex);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]table.isRowAvailable()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl]preRowExport([CtVariableReadImpl]table, [CtVariableReadImpl]document);
        [CtInvocationImpl]exportCells([CtVariableReadImpl]table, [CtVariableReadImpl]document);
        [CtInvocationImpl]postRowExport([CtVariableReadImpl]table, [CtVariableReadImpl]document);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void exportRow([CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.datatable.DataTable table, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object document) [CtBlockImpl]{
        [CtInvocationImpl]preRowExport([CtVariableReadImpl]table, [CtVariableReadImpl]document);
        [CtInvocationImpl]exportCells([CtVariableReadImpl]table, [CtVariableReadImpl]document);
        [CtInvocationImpl]postRowExport([CtVariableReadImpl]table, [CtVariableReadImpl]document);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void exportSelectionOnly([CtParameterImpl][CtTypeReferenceImpl]javax.faces.context.FacesContext context, [CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.datatable.DataTable table, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object document) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object selection = [CtInvocationImpl][CtVariableReadImpl]table.getSelection();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String var = [CtInvocationImpl][CtVariableReadImpl]table.getVar();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]selection != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> requestMap = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]context.getExternalContext().getRequestMap();
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]selection.getClass().isArray()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int size = [CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Array.getLength([CtVariableReadImpl]selection);
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]size; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]requestMap.put([CtVariableReadImpl]var, [CtInvocationImpl][CtTypeAccessImpl]java.lang.reflect.Array.get([CtVariableReadImpl]selection, [CtVariableReadImpl]i));
                    [CtInvocationImpl]exportRow([CtVariableReadImpl]table, [CtVariableReadImpl]document);
                }
            } else [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]java.util.Collection.class.isAssignableFrom([CtInvocationImpl][CtVariableReadImpl]selection.getClass())) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object obj : [CtVariableReadImpl](([CtTypeReferenceImpl]java.util.Collection) (selection))) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]requestMap.put([CtVariableReadImpl]var, [CtVariableReadImpl]obj);
                    [CtInvocationImpl]exportRow([CtVariableReadImpl]table, [CtVariableReadImpl]document);
                }
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]requestMap.put([CtVariableReadImpl]var, [CtVariableReadImpl]selection);
                [CtInvocationImpl]exportCells([CtVariableReadImpl]table, [CtVariableReadImpl]document);
            }
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void preExport([CtParameterImpl][CtTypeReferenceImpl]javax.faces.context.FacesContext context, [CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.export.ExportConfiguration config) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtCommentImpl]// NOOP
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void postExport([CtParameterImpl][CtTypeReferenceImpl]javax.faces.context.FacesContext context, [CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.export.ExportConfiguration config) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtCommentImpl]// NOOP
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void preRowExport([CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.datatable.DataTable table, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object document) [CtBlockImpl]{
        [CtCommentImpl]// NOOP
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void postRowExport([CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.datatable.DataTable table, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object document) [CtBlockImpl]{
        [CtCommentImpl]// NOOP
    }

    [CtMethodImpl]protected abstract [CtTypeReferenceImpl]void exportCells([CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.datatable.DataTable table, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object document);

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void export([CtParameterImpl][CtTypeReferenceImpl]javax.faces.context.FacesContext context, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.primefaces.component.datatable.DataTable> tables, [CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.export.ExportConfiguration config) throws [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtInvocationImpl]preExport([CtVariableReadImpl]context, [CtVariableReadImpl]config);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int index = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.primefaces.component.datatable.DataTable table : [CtVariableReadImpl]tables) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.primefaces.component.datatable.export.DataTableExporter.DataTableVisitCallBack visitCallback = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.primefaces.component.datatable.export.DataTableExporter.DataTableVisitCallBack([CtVariableReadImpl]table, [CtVariableReadImpl]config, [CtVariableReadImpl]index);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int nbTables = [CtInvocationImpl][CtVariableReadImpl]visitCallback.invoke([CtVariableReadImpl]context);
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]index += [CtVariableReadImpl]nbTables;
        }
        [CtInvocationImpl]postExport([CtVariableReadImpl]context, [CtVariableReadImpl]config);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Export datatable
     *
     * @param facesContext
     * 		faces context
     * @param table
     * 		datatable to export
     * @param config
     * 		export configuration
     * @param index
     * 		datatable current index during export process
     * @throws IOException
     */
    protected abstract [CtTypeReferenceImpl]void doExport([CtParameterImpl][CtTypeReferenceImpl]javax.faces.context.FacesContext facesContext, [CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.datatable.DataTable table, [CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.export.ExportConfiguration config, [CtParameterImpl][CtTypeReferenceImpl]int index) throws [CtTypeReferenceImpl]java.io.IOException;

    [CtClassImpl]private class DataTableVisitCallBack implements [CtTypeReferenceImpl]javax.faces.component.visit.VisitCallback {
        [CtFieldImpl]private [CtTypeReferenceImpl]org.primefaces.component.export.ExportConfiguration config;

        [CtFieldImpl]private [CtTypeReferenceImpl]org.primefaces.component.datatable.DataTable target;

        [CtFieldImpl]private [CtTypeReferenceImpl]int index = [CtLiteralImpl]0;

        [CtFieldImpl]private [CtTypeReferenceImpl]int counter = [CtLiteralImpl]0;

        [CtConstructorImpl]public DataTableVisitCallBack([CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.datatable.DataTable target, [CtParameterImpl][CtTypeReferenceImpl]org.primefaces.component.export.ExportConfiguration config, [CtParameterImpl][CtTypeReferenceImpl]int index) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.target = [CtVariableReadImpl]target;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.config = [CtVariableReadImpl]config;
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.index = [CtVariableReadImpl]index;
        }

        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        public [CtTypeReferenceImpl]javax.faces.component.visit.VisitResult visit([CtParameterImpl][CtTypeReferenceImpl]javax.faces.component.visit.VisitContext context, [CtParameterImpl][CtTypeReferenceImpl]UIComponent component) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]target == [CtVariableReadImpl]component) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl]doExport([CtInvocationImpl][CtVariableReadImpl]context.getFacesContext(), [CtFieldReadImpl]target, [CtFieldReadImpl]config, [CtFieldReadImpl]index);
                    [CtUnaryOperatorImpl][CtFieldWriteImpl]index++;
                    [CtUnaryOperatorImpl][CtFieldWriteImpl]counter++;
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.io.IOException e) [CtBlockImpl]{
                    [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]javax.faces.FacesException([CtVariableReadImpl]e);
                }
            }
            [CtReturnImpl]return [CtFieldReadImpl]javax.faces.component.visit.VisitResult.ACCEPT;
        }

        [CtMethodImpl][CtJavaDocImpl]/**
         * Returns number of tables exported
         *
         * @param context
         * 		faces context
         * @return number of tables exported
         */
        public [CtTypeReferenceImpl]int invoke([CtParameterImpl][CtTypeReferenceImpl]javax.faces.context.FacesContext context) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]org.primefaces.util.ComponentUtils.invokeOnClosestIteratorParent([CtFieldReadImpl]target, [CtLambdaImpl]([CtParameterImpl] p) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]javax.faces.component.visit.VisitContext visitContext = [CtInvocationImpl][CtTypeAccessImpl]javax.faces.component.visit.VisitContext.createVisitContext([CtVariableReadImpl]context);
                [CtInvocationImpl][CtVariableReadImpl]p.visitTree([CtVariableReadImpl]visitContext, [CtThisAccessImpl]this);
            }, [CtLiteralImpl]true);
            [CtReturnImpl]return [CtFieldReadImpl]counter;
        }
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void setResponseHeader([CtParameterImpl][CtTypeReferenceImpl]javax.faces.context.ExternalContext externalContext, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String contentDisposition) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]externalContext.setResponseHeader([CtLiteralImpl]"Expires", [CtLiteralImpl]"0");
        [CtInvocationImpl][CtVariableReadImpl]externalContext.setResponseHeader([CtLiteralImpl]"Cache-Control", [CtLiteralImpl]"must-revalidate, post-check=0, pre-check=0");
        [CtInvocationImpl][CtVariableReadImpl]externalContext.setResponseHeader([CtLiteralImpl]"Pragma", [CtLiteralImpl]"public");
        [CtInvocationImpl][CtVariableReadImpl]externalContext.setResponseHeader([CtLiteralImpl]"Content-disposition", [CtVariableReadImpl]contentDisposition);
    }

    [CtMethodImpl]protected [CtTypeReferenceImpl]void addResponseCookie([CtParameterImpl][CtTypeReferenceImpl]javax.faces.context.ExternalContext externalContext) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean secure = [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]javax.servlet.http.HttpServletRequest) ([CtVariableReadImpl]externalContext.getRequest())).isSecure();
        [CtIfImpl]if ([CtVariableReadImpl]secure) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> map = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>();
            [CtInvocationImpl][CtVariableReadImpl]map.put([CtLiteralImpl]"secure", [CtVariableReadImpl]secure);
            [CtInvocationImpl][CtVariableReadImpl]externalContext.addResponseCookie([CtTypeAccessImpl]Constants.DOWNLOAD_COOKIE, [CtLiteralImpl]"true", [CtVariableReadImpl]map);
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]externalContext.addResponseCookie([CtTypeAccessImpl]Constants.DOWNLOAD_COOKIE, [CtLiteralImpl]"true", [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object>emptyMap());
        }
    }
}
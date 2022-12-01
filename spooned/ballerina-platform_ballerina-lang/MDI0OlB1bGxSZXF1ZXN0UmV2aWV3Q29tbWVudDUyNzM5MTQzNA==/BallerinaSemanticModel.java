[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.

WSO2 Inc. licenses this file to you under the Apache License,
Version 2.0 (the "License"); you may not use this file except
in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
[CtPackageDeclarationImpl]package io.ballerina.compiler.api.impl;
[CtUnresolvedImport]import io.ballerina.compiler.api.ModuleID;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import io.ballerina.compiler.api.impl.symbols.BallerinaTypeReferenceTypeSymbol;
[CtUnresolvedImport]import io.ballerina.compiler.api.symbols.Symbol;
[CtUnresolvedImport]import io.ballerina.tools.diagnostics.Location;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.semantics.model.symbols.BTypeSymbol;
[CtUnresolvedImport]import org.wso2.ballerinalang.util.Flags;
[CtUnresolvedImport]import io.ballerina.compiler.api.symbols.TypeSymbol;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.tree.BLangCompilationUnit;
[CtUnresolvedImport]import org.ballerinalang.model.symbols.SymbolKind;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.semantics.analyzer.SymbolResolver;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.semantics.model.symbols.BAnnotationSymbol;
[CtUnresolvedImport]import io.ballerina.tools.text.LineRange;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Collections;
[CtUnresolvedImport]import io.ballerina.compiler.api.impl.symbols.TypesFactory;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.tree.BLangPackage;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.diagnostic.BLangDiagnosticLocation;
[CtUnresolvedImport]import static org.ballerinalang.model.symbols.SymbolOrigin.SOURCE;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.semantics.model.SymbolEnv;
[CtImportImpl]import java.util.Optional;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.semantics.model.symbols.BSymbol;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.semantics.model.symbols.BPackageSymbol;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.semantics.model.symbols.Symbols;
[CtUnresolvedImport]import io.ballerina.tools.diagnostics.Diagnostic;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.util.Name;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.tree.BLangNode;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.semantics.model.Scope;
[CtUnresolvedImport]import io.ballerina.compiler.api.SemanticModel;
[CtUnresolvedImport]import static org.ballerinalang.model.symbols.SymbolOrigin.COMPILED_SOURCE;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import io.ballerina.tools.text.LinePosition;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.semantics.model.SymbolTable;
[CtUnresolvedImport]import org.wso2.ballerinalang.compiler.util.CompilerContext;
[CtClassImpl][CtJavaDocImpl]/**
 * Semantic model representation of a given syntax tree.
 *
 * @since 2.0.0
 */
public class BallerinaSemanticModel implements [CtTypeReferenceImpl]io.ballerina.compiler.api.SemanticModel {
    [CtFieldImpl]private final [CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.tree.BLangPackage bLangPackage;

    [CtFieldImpl]private final [CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.util.CompilerContext compilerContext;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.ballerina.compiler.api.impl.EnvironmentResolver envResolver;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.ballerina.compiler.api.impl.SymbolFactory symbolFactory;

    [CtFieldImpl]private final [CtTypeReferenceImpl]io.ballerina.compiler.api.impl.symbols.TypesFactory typesFactory;

    [CtConstructorImpl]public BallerinaSemanticModel([CtParameterImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.tree.BLangPackage bLangPackage, [CtParameterImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.util.CompilerContext context) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.compilerContext = [CtVariableReadImpl]context;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.bLangPackage = [CtVariableReadImpl]bLangPackage;
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.SymbolTable symbolTable = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.ballerinalang.compiler.semantics.model.SymbolTable.getInstance([CtVariableReadImpl]context);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.SymbolEnv pkgEnv = [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]symbolTable.pkgEnvMap.get([CtFieldReadImpl][CtVariableReadImpl]bLangPackage.symbol);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.envResolver = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.ballerina.compiler.api.impl.EnvironmentResolver([CtVariableReadImpl]pkgEnv);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.symbolFactory = [CtInvocationImpl][CtTypeAccessImpl]io.ballerina.compiler.api.impl.SymbolFactory.getInstance([CtVariableReadImpl]context);
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.typesFactory = [CtInvocationImpl][CtTypeAccessImpl]io.ballerina.compiler.api.impl.symbols.TypesFactory.getInstance([CtVariableReadImpl]context);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.ballerina.compiler.api.symbols.Symbol> visibleSymbols([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fileName, [CtParameterImpl][CtTypeReferenceImpl]io.ballerina.tools.text.LinePosition linePosition) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.ballerina.compiler.api.symbols.Symbol> compiledSymbols = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.analyzer.SymbolResolver symbolResolver = [CtInvocationImpl][CtTypeAccessImpl]org.wso2.ballerinalang.compiler.semantics.analyzer.SymbolResolver.getInstance([CtFieldReadImpl][CtThisAccessImpl]this.compilerContext);
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.tree.BLangCompilationUnit compilationUnit = [CtInvocationImpl]getCompilationUnit([CtVariableReadImpl]fileName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.util.Name, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.Scope.ScopeEntry>> scopeSymbols = [CtInvocationImpl][CtVariableReadImpl]symbolResolver.getAllVisibleInScopeSymbols([CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.envResolver.lookUp([CtVariableReadImpl]compilationUnit, [CtVariableReadImpl]linePosition));
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.ballerina.tools.diagnostics.Location cursorPos = [CtConstructorCallImpl]new [CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.diagnostic.BLangDiagnosticLocation([CtFieldReadImpl][CtVariableReadImpl]compilationUnit.name, [CtInvocationImpl][CtVariableReadImpl]linePosition.line(), [CtInvocationImpl][CtVariableReadImpl]linePosition.line(), [CtInvocationImpl][CtVariableReadImpl]linePosition.offset(), [CtInvocationImpl][CtVariableReadImpl]linePosition.offset());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.util.Name, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.Scope.ScopeEntry>> entry : [CtInvocationImpl][CtVariableReadImpl]scopeSymbols.entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.util.Name name = [CtInvocationImpl][CtVariableReadImpl]entry.getKey();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.Scope.ScopeEntry> scopeEntries = [CtInvocationImpl][CtVariableReadImpl]entry.getValue();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.Scope.ScopeEntry scopeEntry : [CtVariableReadImpl]scopeEntries) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.symbols.BSymbol symbol = [CtFieldReadImpl][CtVariableReadImpl]scopeEntry.symbol;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]hasCursorPosPassedSymbolPos([CtVariableReadImpl]symbol, [CtVariableReadImpl]cursorPos) || [CtInvocationImpl]isImportedSymbol([CtVariableReadImpl]symbol)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]compiledSymbols.add([CtInvocationImpl][CtFieldReadImpl]symbolFactory.getBCompiledSymbol([CtVariableReadImpl]symbol, [CtInvocationImpl][CtVariableReadImpl]name.getValue()));
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]compiledSymbols;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.ballerina.compiler.api.symbols.Symbol> symbol([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fileName, [CtParameterImpl][CtTypeReferenceImpl]io.ballerina.tools.text.LinePosition position) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.tree.BLangCompilationUnit compilationUnit = [CtInvocationImpl]getCompilationUnit([CtVariableReadImpl]fileName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.ballerina.compiler.api.impl.SymbolFinder symbolFinder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.ballerina.compiler.api.impl.SymbolFinder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.symbols.BSymbol symbolAtCursor = [CtInvocationImpl][CtVariableReadImpl]symbolFinder.lookup([CtVariableReadImpl]compilationUnit, [CtVariableReadImpl]position);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]symbolAtCursor == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]isTypeSymbol([CtVariableReadImpl]symbolAtCursor) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtTypeAccessImpl]io.ballerina.compiler.api.impl.PositionUtil.withinBlock([CtVariableReadImpl]position, [CtFieldReadImpl][CtVariableReadImpl]symbolAtCursor.pos))) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.ballerina.compiler.api.ModuleID moduleID = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.ballerina.compiler.api.impl.BallerinaModuleID([CtFieldReadImpl][CtVariableReadImpl]symbolAtCursor.pkgID);
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.of([CtConstructorCallImpl]new [CtTypeReferenceImpl]io.ballerina.compiler.api.impl.symbols.BallerinaTypeReferenceTypeSymbol([CtFieldReadImpl][CtThisAccessImpl]this.compilerContext, [CtVariableReadImpl]moduleID, [CtFieldReadImpl][CtVariableReadImpl]symbolAtCursor.type, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]symbolAtCursor.getName().getValue()));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtFieldReadImpl]symbolFactory.getBCompiledSymbol([CtVariableReadImpl]symbolAtCursor, [CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]symbolAtCursor.name.value));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.ballerina.compiler.api.symbols.Symbol> moduleLevelSymbols() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.ballerina.compiler.api.symbols.Symbol> compiledSymbols = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.util.Name, [CtTypeReferenceImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.Scope.ScopeEntry> e : [CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]bLangPackage.symbol.scope.entries.entrySet()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.util.Name key = [CtInvocationImpl][CtVariableReadImpl]e.getKey();
            [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.Scope.ScopeEntry value = [CtInvocationImpl][CtVariableReadImpl]e.getValue();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtFieldReadImpl][CtVariableReadImpl]value.symbol.origin == [CtFieldReadImpl]SOURCE) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]compiledSymbols.add([CtInvocationImpl][CtFieldReadImpl]symbolFactory.getBCompiledSymbol([CtFieldReadImpl][CtVariableReadImpl]value.symbol, [CtFieldReadImpl][CtVariableReadImpl]key.value));
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]compiledSymbols;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.ballerina.tools.diagnostics.Location> references([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fileName, [CtParameterImpl][CtTypeReferenceImpl]io.ballerina.tools.text.LinePosition position) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.tree.BLangCompilationUnit compilationUnit = [CtInvocationImpl]getCompilationUnit([CtVariableReadImpl]fileName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.ballerina.compiler.api.impl.SymbolFinder symbolFinder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.ballerina.compiler.api.impl.SymbolFinder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.symbols.BSymbol symbolAtCursor = [CtInvocationImpl][CtVariableReadImpl]symbolFinder.lookup([CtVariableReadImpl]compilationUnit, [CtVariableReadImpl]position);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]symbolAtCursor == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableList([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.tree.BLangNode node = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]io.ballerina.compiler.api.impl.NodeFinder().lookupEnclosingContainer([CtFieldReadImpl][CtThisAccessImpl]this.bLangPackage, [CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]symbolAtCursor.pos.lineRange());
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.ballerina.compiler.api.impl.ReferenceFinder refFinder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.ballerina.compiler.api.impl.ReferenceFinder();
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]refFinder.findReferences([CtVariableReadImpl]node, [CtVariableReadImpl]symbolAtCursor);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Optional<[CtTypeReferenceImpl]io.ballerina.compiler.api.symbols.TypeSymbol> getType([CtParameterImpl][CtTypeReferenceImpl]java.lang.String fileName, [CtParameterImpl][CtTypeReferenceImpl]io.ballerina.tools.text.LineRange range) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.tree.BLangCompilationUnit compilationUnit = [CtInvocationImpl]getCompilationUnit([CtVariableReadImpl]fileName);
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.ballerina.compiler.api.impl.NodeFinder nodeFinder = [CtConstructorCallImpl]new [CtTypeReferenceImpl]io.ballerina.compiler.api.impl.NodeFinder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.tree.BLangNode node = [CtInvocationImpl][CtVariableReadImpl]nodeFinder.lookup([CtVariableReadImpl]compilationUnit, [CtVariableReadImpl]range);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]node == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.empty();
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Optional.ofNullable([CtInvocationImpl][CtFieldReadImpl]typesFactory.getTypeDescriptor([CtFieldReadImpl][CtVariableReadImpl]node.type));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.ballerina.tools.diagnostics.Diagnostic> diagnostics([CtParameterImpl][CtTypeReferenceImpl]io.ballerina.tools.text.LineRange range) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.ballerina.tools.diagnostics.Diagnostic> allDiagnostics = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.bLangPackage.getDiagnostics();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.ballerina.tools.diagnostics.Diagnostic> filteredDiagnostics = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]io.ballerina.tools.diagnostics.Diagnostic diagnostic : [CtVariableReadImpl]allDiagnostics) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]io.ballerina.tools.text.LineRange lineRange = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]diagnostic.location().lineRange();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]lineRange.filePath().equals([CtInvocationImpl][CtVariableReadImpl]range.filePath()) && [CtInvocationImpl]withinRange([CtVariableReadImpl]lineRange, [CtVariableReadImpl]range)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]filteredDiagnostics.add([CtVariableReadImpl]diagnostic);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]filteredDiagnostics;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * {@inheritDoc }
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]io.ballerina.tools.diagnostics.Diagnostic> diagnostics() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.bLangPackage.getDiagnostics();
    }

    [CtMethodImpl][CtCommentImpl]// Private helper methods for the public APIs above.
    private [CtTypeReferenceImpl]boolean hasCursorPosPassedSymbolPos([CtParameterImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.symbols.BSymbol symbol, [CtParameterImpl][CtTypeReferenceImpl]io.ballerina.tools.diagnostics.Location cursorPos) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]symbol.origin != [CtFieldReadImpl]SOURCE) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]symbol.owner.getKind() == [CtFieldReadImpl]org.ballerinalang.model.symbols.SymbolKind.PACKAGE) || [CtInvocationImpl][CtTypeAccessImpl]org.wso2.ballerinalang.compiler.semantics.model.symbols.Symbols.isFlagOn([CtFieldReadImpl][CtVariableReadImpl]symbol.flags, [CtTypeAccessImpl]Flags.WORKER)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]bLangPackage.packageID.equals([CtFieldReadImpl][CtVariableReadImpl]symbol.pkgID)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtCommentImpl]// These checks whether the cursor position has passed the symbol position or not
        [CtTypeReferenceImpl]io.ballerina.tools.text.LinePosition cursorPosStartLine = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cursorPos.lineRange().startLine();
        [CtLocalVariableImpl][CtTypeReferenceImpl]io.ballerina.tools.text.LinePosition symbolStartLine = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtVariableReadImpl]symbol.pos.lineRange().startLine();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]cursorPosStartLine.line() < [CtInvocationImpl][CtVariableReadImpl]symbolStartLine.line()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]cursorPosStartLine.line() > [CtInvocationImpl][CtVariableReadImpl]symbolStartLine.line()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]cursorPosStartLine.offset() > [CtInvocationImpl][CtVariableReadImpl]symbolStartLine.offset();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isImportedSymbol([CtParameterImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.symbols.BSymbol symbol) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]symbol.origin == [CtFieldReadImpl]COMPILED_SOURCE) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]org.wso2.ballerinalang.compiler.semantics.model.symbols.Symbols.isFlagOn([CtFieldReadImpl][CtVariableReadImpl]symbol.flags, [CtTypeAccessImpl]Flags.PUBLIC) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]symbol.getKind() == [CtFieldReadImpl]org.ballerinalang.model.symbols.SymbolKind.PACKAGE));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.tree.BLangCompilationUnit getCompilationUnit([CtParameterImpl][CtTypeReferenceImpl]java.lang.String srcFile) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl][CtFieldReadImpl][CtFieldReferenceImpl]bLangPackage.compUnits.stream().filter([CtLambdaImpl]([CtParameterImpl] unit) -> [CtInvocationImpl][CtVariableReadImpl]unit.name.equals([CtVariableReadImpl]srcFile)).findFirst().get();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean isTypeSymbol([CtParameterImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.symbols.BSymbol symbol) [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]symbol instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.symbols.BTypeSymbol) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]symbol instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.symbols.BPackageSymbol))) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]symbol instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]org.wso2.ballerinalang.compiler.semantics.model.symbols.BAnnotationSymbol));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]boolean withinRange([CtParameterImpl][CtTypeReferenceImpl]io.ballerina.tools.text.LineRange range, [CtParameterImpl][CtTypeReferenceImpl]io.ballerina.tools.text.LineRange specifiedRange) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int startLine = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]range.startLine().line();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int startOffset = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]range.startLine().offset();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int specifiedStartLine = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]specifiedRange.startLine().line();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int specifiedEndLine = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]specifiedRange.endLine().line();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int specifiedStartOffset = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]specifiedRange.startLine().offset();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int specifiedEndOffset = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]specifiedRange.endLine().offset();
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]startLine >= [CtVariableReadImpl]specifiedStartLine) && [CtBinaryOperatorImpl]([CtVariableReadImpl]startLine <= [CtVariableReadImpl]specifiedEndLine)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]startOffset >= [CtVariableReadImpl]specifiedStartOffset)) && [CtBinaryOperatorImpl]([CtVariableReadImpl]startOffset <= [CtVariableReadImpl]specifiedEndOffset);
    }
}
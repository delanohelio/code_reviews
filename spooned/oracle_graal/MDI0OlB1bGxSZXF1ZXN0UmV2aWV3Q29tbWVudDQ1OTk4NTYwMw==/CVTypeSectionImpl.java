[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2020, 2020, Oracle and/or its affiliates. All rights reserved.
Copyright (c) 2020, 2020, Red Hat Inc. All rights reserved.
DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

This code is free software; you can redistribute it and/or modify it
under the terms of the GNU General Public License version 2 only, as
published by the Free Software Foundation.  Oracle designates this
particular file as subject to the "Classpath" exception as provided
by Oracle in the LICENSE file that accompanied this code.

This code is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
version 2 for more details (a copy is included in the LICENSE file that
accompanied this code).

You should have received a copy of the GNU General Public License version
2 along with this work; if not, write to the Free Software Foundation,
Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.

Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
or visit www.oracle.com if you need additional information or have any
questions.
 */
[CtPackageDeclarationImpl]package com.oracle.objectfile.pecoff.cv;
[CtUnresolvedImport]import static com.oracle.objectfile.pecoff.cv.CVConstants.CV_SYMBOL_SECTION_NAME;
[CtUnresolvedImport]import com.oracle.objectfile.LayoutDecisionMap;
[CtImportImpl]import java.util.Set;
[CtImportImpl]import java.util.HashMap;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import static com.oracle.objectfile.pecoff.cv.CVConstants.CV_TYPE_SECTION_NAME;
[CtUnresolvedImport]import com.oracle.objectfile.ObjectFile;
[CtUnresolvedImport]import static com.oracle.objectfile.pecoff.cv.CVConstants.CV_SIGNATURE_C13;
[CtUnresolvedImport]import com.oracle.objectfile.BuildDependency;
[CtUnresolvedImport]import org.graalvm.compiler.debug.DebugContext;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.Map;
[CtUnresolvedImport]import com.oracle.objectfile.LayoutDecision;
[CtUnresolvedImport]import com.oracle.objectfile.pecoff.PECoffObjectFile;
[CtImportImpl]import java.util.Collections;
[CtClassImpl]public final class CVTypeSectionImpl extends [CtTypeReferenceImpl]com.oracle.objectfile.pecoff.cv.CVSectionImpl {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]int CV_RECORD_INITIAL_CAPACITY = [CtLiteralImpl]200;

    [CtFieldImpl]private [CtTypeReferenceImpl]int sequenceCounter = [CtLiteralImpl]0x1000;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]com.oracle.objectfile.pecoff.cv.CVTypeRecord> cvRecords = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtFieldReadImpl]com.oracle.objectfile.pecoff.cv.CVTypeSectionImpl.CV_RECORD_INITIAL_CAPACITY);

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]com.oracle.objectfile.pecoff.cv.CVTypeRecord> typeMap = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtConstructorImpl]CVTypeSectionImpl() [CtBlockImpl]{
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.String getSectionName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]com.oracle.objectfile.pecoff.cv.CVConstants.CV_TYPE_SECTION_NAME;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void createContent([CtParameterImpl][CtTypeReferenceImpl]org.graalvm.compiler.debug.DebugContext debugContext) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int pos = [CtLiteralImpl]0;
        [CtInvocationImpl]enableLog([CtVariableReadImpl]debugContext);
        [CtInvocationImpl]log([CtVariableReadImpl]debugContext, [CtLiteralImpl]"CVTypeSectionImpl.createContent() adding records");
        [CtInvocationImpl]addRecords();
        [CtInvocationImpl]log([CtVariableReadImpl]debugContext, [CtLiteralImpl]"CVTypeSectionImpl.createContent() start");
        [CtAssignmentImpl][CtVariableWriteImpl]pos = [CtInvocationImpl][CtTypeAccessImpl]com.oracle.objectfile.pecoff.cv.CVUtil.putInt([CtTypeAccessImpl]com.oracle.objectfile.pecoff.cv.CVConstants.CV_SIGNATURE_C13, [CtLiteralImpl]null, [CtVariableReadImpl]pos);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.oracle.objectfile.pecoff.cv.CVTypeRecord record : [CtFieldReadImpl]cvRecords) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]pos = [CtInvocationImpl][CtVariableReadImpl]record.computeFullSize([CtVariableReadImpl]pos);
        }
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] buffer = [CtNewArrayImpl]new [CtTypeReferenceImpl]byte[[CtVariableReadImpl]pos];
        [CtInvocationImpl][CtSuperAccessImpl]super.setContent([CtVariableReadImpl]buffer);
        [CtInvocationImpl]log([CtVariableReadImpl]debugContext, [CtLiteralImpl]"CVTypeSectionImpl.createContent() end");
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void writeContent([CtParameterImpl][CtTypeReferenceImpl]org.graalvm.compiler.debug.DebugContext debugContext) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int pos = [CtLiteralImpl]0;
        [CtInvocationImpl]enableLog([CtVariableReadImpl]debugContext);
        [CtInvocationImpl]log([CtVariableReadImpl]debugContext, [CtLiteralImpl]"CVTypeSectionImpl.writeContent() start");
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] buffer = [CtInvocationImpl]getContent();
        [CtInvocationImpl]verboseLog([CtVariableReadImpl]debugContext, [CtLiteralImpl]"  [0x%08x] CV_SIGNATURE_C13", [CtVariableReadImpl]pos);
        [CtAssignmentImpl][CtVariableWriteImpl]pos = [CtInvocationImpl][CtTypeAccessImpl]com.oracle.objectfile.pecoff.cv.CVUtil.putInt([CtTypeAccessImpl]com.oracle.objectfile.pecoff.cv.CVConstants.CV_SIGNATURE_C13, [CtVariableReadImpl]buffer, [CtVariableReadImpl]pos);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]com.oracle.objectfile.pecoff.cv.CVTypeRecord record : [CtFieldReadImpl]cvRecords) [CtBlockImpl]{
            [CtInvocationImpl]verboseLog([CtVariableReadImpl]debugContext, [CtLiteralImpl]"  [0x%08x] 0x%06x %s", [CtVariableReadImpl]pos, [CtInvocationImpl][CtVariableReadImpl]record.getSequenceNumber(), [CtInvocationImpl][CtVariableReadImpl]record.toString());
            [CtAssignmentImpl][CtVariableWriteImpl]pos = [CtInvocationImpl][CtVariableReadImpl]record.computeFullContents([CtVariableReadImpl]buffer, [CtVariableReadImpl]pos);
        }
        [CtInvocationImpl]verboseLog([CtVariableReadImpl]debugContext, [CtLiteralImpl]"CVTypeSectionImpl.writeContent() end");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add all relevant type records to the type section.
     */
    private [CtTypeReferenceImpl]void addRecords() [CtBlockImpl]{
        [CtCommentImpl]/* if an external PDB file is generated, add CVTypeServer2Record */
        [CtCommentImpl]/* for each class, add all members, types, etc */
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.oracle.objectfile.pecoff.cv.CVTypeRecord> getRecords() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableList([CtFieldReadImpl]cvRecords);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a record to the type section if it has a unique hash.
     *
     * @param <T>
     * 		type of new record.
     * @param newRecord
     * 		record to add
     * @return newRecord if the hash is unique, or the existing record already in the type table
     */
    <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.oracle.objectfile.pecoff.cv.CVTypeRecord> [CtTypeParameterReferenceImpl]T addRecord([CtParameterImpl][CtTypeParameterReferenceImpl]T newRecord) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]addOrReference([CtVariableReadImpl]newRecord);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a record (that has a unique hashcode) to the type section.
     *
     * @param r
     * 		the record to add
     */
    private [CtTypeReferenceImpl]void addUniqueRecord([CtParameterImpl][CtTypeReferenceImpl]com.oracle.objectfile.pecoff.cv.CVTypeRecord r) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]cvRecords.add([CtVariableReadImpl]r);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.oracle.objectfile.BuildDependency> getDependencies([CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.oracle.objectfile.ObjectFile.Element, [CtTypeReferenceImpl]com.oracle.objectfile.LayoutDecisionMap> decisions) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]com.oracle.objectfile.BuildDependency> deps = [CtInvocationImpl][CtSuperAccessImpl]super.getDependencies([CtVariableReadImpl]decisions);
        [CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]com.oracle.objectfile.pecoff.PECoffObjectFile.PECoffSection targetSection = [CtInvocationImpl](([CtTypeReferenceImpl][CtTypeReferenceImpl]com.oracle.objectfile.pecoff.PECoffObjectFile.PECoffSection) ([CtInvocationImpl][CtInvocationImpl]getElement().getOwner().elementForName([CtTypeAccessImpl]com.oracle.objectfile.pecoff.cv.CVConstants.CV_SYMBOL_SECTION_NAME)));
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.oracle.objectfile.LayoutDecision ourContent = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]decisions.get([CtInvocationImpl]getElement()).getDecision([CtTypeAccessImpl]LayoutDecision.Kind.CONTENT);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.oracle.objectfile.LayoutDecision ourSize = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]decisions.get([CtInvocationImpl]getElement()).getDecision([CtTypeAccessImpl]LayoutDecision.Kind.SIZE);
        [CtInvocationImpl][CtCommentImpl]/* Make our content depend on the codeview symbol section. */
        [CtVariableReadImpl]deps.add([CtInvocationImpl][CtTypeAccessImpl]com.oracle.objectfile.BuildDependency.createOrGet([CtVariableReadImpl]ourContent, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]decisions.get([CtVariableReadImpl]targetSection).getDecision([CtTypeAccessImpl]LayoutDecision.Kind.CONTENT)));
        [CtInvocationImpl][CtCommentImpl]/* Make our size depend on our content. */
        [CtVariableReadImpl]deps.add([CtInvocationImpl][CtTypeAccessImpl]com.oracle.objectfile.BuildDependency.createOrGet([CtVariableReadImpl]ourSize, [CtVariableReadImpl]ourContent));
        [CtReturnImpl]return [CtVariableReadImpl]deps;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Return either the caller-created instance or a matching existing instance. Every entry in
     * typeMap is a T, because it is ONLY this function which inserts entries (of type T).
     *
     * @param <T>
     * 		type of new record
     * @param newRecord
     * 		record to add if an existing record with same hash hasn't already been added
     * @return the record (if previously unseen) or old record
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    <[CtTypeParameterImpl]T extends [CtTypeReferenceImpl]com.oracle.objectfile.pecoff.cv.CVTypeRecord> [CtTypeParameterReferenceImpl]T addOrReference([CtParameterImpl][CtTypeParameterReferenceImpl]T newRecord) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeParameterReferenceImpl]T record;
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]int hashCode = [CtInvocationImpl][CtVariableReadImpl]newRecord.hashCode();
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]typeMap.containsKey([CtVariableReadImpl]hashCode)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]record = [CtInvocationImpl](([CtTypeParameterReferenceImpl]T) ([CtFieldReadImpl]typeMap.get([CtVariableReadImpl]hashCode)));
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]newRecord.setSequenceNumber([CtUnaryOperatorImpl][CtFieldWriteImpl]sequenceCounter++);
            [CtInvocationImpl][CtFieldReadImpl]typeMap.put([CtVariableReadImpl]hashCode, [CtVariableReadImpl]newRecord);
            [CtInvocationImpl]addUniqueRecord([CtVariableReadImpl]newRecord);
            [CtAssignmentImpl][CtVariableWriteImpl]record = [CtVariableReadImpl]newRecord;
        }
        [CtReturnImpl]return [CtVariableReadImpl]record;
    }
}
[CompilationUnitImpl][CtJavaDocImpl]/**
 * Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
[CtPackageDeclarationImpl]package com.powsybl.psse.model;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import com.univocity.parsers.annotations.Parsed;
[CtUnresolvedImport]import com.powsybl.psse.model.PsseConstants.PsseVersion;
[CtClassImpl][CtJavaDocImpl]/**
 *
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at rte-france.com>
 */
public class PsseCaseIdentification {
    [CtFieldImpl][CtAnnotationImpl]@com.univocity.parsers.annotations.Parsed
    private [CtTypeReferenceImpl]int ic = [CtLiteralImpl]0;

    [CtFieldImpl][CtAnnotationImpl]@com.univocity.parsers.annotations.Parsed
    private [CtTypeReferenceImpl]double sbase = [CtLiteralImpl]100;

    [CtFieldImpl][CtAnnotationImpl]@com.univocity.parsers.annotations.Parsed
    private [CtTypeReferenceImpl]int rev = [CtLiteralImpl]33;

    [CtFieldImpl][CtAnnotationImpl]@com.univocity.parsers.annotations.Parsed
    private [CtTypeReferenceImpl]double xfrrat = [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Double.[CtFieldReferenceImpl]NaN;

    [CtFieldImpl][CtAnnotationImpl]@com.univocity.parsers.annotations.Parsed
    private [CtTypeReferenceImpl]double nxfrat = [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Double.[CtFieldReferenceImpl]NaN;

    [CtFieldImpl][CtAnnotationImpl]@com.univocity.parsers.annotations.Parsed
    private [CtTypeReferenceImpl]double basfrq = [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Double.[CtFieldReferenceImpl]NaN;

    [CtFieldImpl][CtAnnotationImpl]@com.univocity.parsers.annotations.Parsed(defaultNullRead = [CtLiteralImpl]"")
    private [CtTypeReferenceImpl]java.lang.String title1;

    [CtFieldImpl][CtAnnotationImpl]@com.univocity.parsers.annotations.Parsed(defaultNullRead = [CtLiteralImpl]"")
    private [CtTypeReferenceImpl]java.lang.String title2;

    [CtMethodImpl]public [CtTypeReferenceImpl]int getIc() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]ic;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setIc([CtParameterImpl][CtTypeReferenceImpl]int ic) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.ic = [CtVariableReadImpl]ic;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getSbase() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]sbase;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setSbase([CtParameterImpl][CtTypeReferenceImpl]double sbase) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.sbase = [CtVariableReadImpl]sbase;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getRev() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]rev;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setRev([CtParameterImpl][CtTypeReferenceImpl]int rev) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.rev = [CtVariableReadImpl]rev;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getXfrrat() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]xfrrat;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setXfrrat([CtParameterImpl][CtTypeReferenceImpl]double xfrrat) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.xfrrat = [CtVariableReadImpl]xfrrat;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getNxfrat() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]nxfrat;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setNxfrat([CtParameterImpl][CtTypeReferenceImpl]double nxfrat) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.nxfrat = [CtVariableReadImpl]nxfrat;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getBasfrq() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]basfrq;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setBasfrq([CtParameterImpl][CtTypeReferenceImpl]double basfrq) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.basfrq = [CtVariableReadImpl]basfrq;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getTitle1() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]title1;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setTitle1([CtParameterImpl][CtTypeReferenceImpl]java.lang.String title1) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.title1 = [CtVariableReadImpl]title1;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getTitle2() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]title2;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setTitle2([CtParameterImpl][CtTypeReferenceImpl]java.lang.String title2) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.title2 = [CtVariableReadImpl]title2;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void validate() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]ic == [CtLiteralImpl]1) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.powsybl.psse.model.PsseException([CtLiteralImpl]"Incremental load of data option (IC = 1) is not supported");
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.powsybl.psse.model.PsseConstants.PsseVersion.supportedVersions().contains([CtFieldReadImpl]rev)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String msgSupported = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]com.powsybl.psse.model.PsseConstants.PsseVersion.supportedVersions().stream().map([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]java.lang.String::valueOf).sorted().collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.joining([CtLiteralImpl]", "));
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.powsybl.psse.model.PsseException([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Version " + [CtFieldReadImpl]rev) + [CtLiteralImpl]" not supported. Supported versions are: ") + [CtVariableReadImpl]msgSupported);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]sbase <= [CtLiteralImpl]0.0) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.powsybl.psse.model.PsseException([CtBinaryOperatorImpl][CtLiteralImpl]"Unexpected System MVA base " + [CtFieldReadImpl]sbase);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]basfrq <= [CtLiteralImpl]0.0) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.powsybl.psse.model.PsseException([CtBinaryOperatorImpl][CtLiteralImpl]"Unexpected System base frequency " + [CtFieldReadImpl]basfrq);
        }
    }
}
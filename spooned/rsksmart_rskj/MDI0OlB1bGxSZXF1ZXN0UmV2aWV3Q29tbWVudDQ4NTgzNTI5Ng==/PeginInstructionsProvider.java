[CompilationUnitImpl][CtPackageDeclarationImpl]package co.rsk.peg.pegininstructions;
[CtUnresolvedImport]import co.rsk.bitcoinj.core.BtcTransaction;
[CtUnresolvedImport]import co.rsk.peg.utils.BtcTransactionFormatUtils;
[CtImportImpl]import org.slf4j.Logger;
[CtImportImpl]import org.slf4j.LoggerFactory;
[CtClassImpl]public class PeginInstructionsProvider {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]org.slf4j.Logger logger = [CtInvocationImpl][CtTypeAccessImpl]org.slf4j.LoggerFactory.getLogger([CtFieldReadImpl]co.rsk.peg.pegininstructions.PeginInstructionsProvider.class);

    [CtMethodImpl]public [CtTypeReferenceImpl]co.rsk.peg.pegininstructions.PeginInstructionsBase buildPeginInstructions([CtParameterImpl][CtTypeReferenceImpl]co.rsk.bitcoinj.core.BtcTransaction btcTx) throws [CtTypeReferenceImpl]java.lang.Exception [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]co.rsk.peg.pegininstructions.PeginInstructionsBase peginInstructions;
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]byte[] opReturnOutput = [CtInvocationImpl][CtTypeAccessImpl]co.rsk.peg.utils.BtcTransactionFormatUtils.extractOpReturnData([CtVariableReadImpl]btcTx);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]opReturnOutput == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]opReturnOutput.length == [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String message = [CtLiteralImpl]"Empty OP_RETURN data found";
            [CtInvocationImpl][CtFieldReadImpl]co.rsk.peg.pegininstructions.PeginInstructionsProvider.logger.debug([CtLiteralImpl]"[getOpReturnOutput] {}", [CtVariableReadImpl]message);
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]co.rsk.peg.pegininstructions.PeginInstructionsException([CtVariableReadImpl]message);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int protocolVersion = [CtInvocationImpl][CtTypeAccessImpl]co.rsk.peg.pegininstructions.PeginInstructionsBase.extractProtocolVersion([CtVariableReadImpl]opReturnOutput);
        [CtSwitchImpl]switch ([CtVariableReadImpl]protocolVersion) {
            [CtCaseImpl]case [CtLiteralImpl]1 :
                [CtLocalVariableImpl][CtTypeReferenceImpl]co.rsk.peg.pegininstructions.PeginInstructionsVersion1 peginInstructionsVersion1 = [CtConstructorCallImpl]new [CtTypeReferenceImpl]co.rsk.peg.pegininstructions.PeginInstructionsVersion1([CtInvocationImpl][CtVariableReadImpl]btcTx.getParams());
                [CtInvocationImpl][CtVariableReadImpl]peginInstructionsVersion1.parse([CtVariableReadImpl]opReturnOutput);
                [CtAssignmentImpl][CtVariableWriteImpl]peginInstructions = [CtVariableReadImpl]peginInstructionsVersion1;
                [CtBreakImpl]break;
            [CtCaseImpl]default :
                [CtInvocationImpl][CtFieldReadImpl]co.rsk.peg.pegininstructions.PeginInstructionsProvider.logger.debug([CtLiteralImpl]"[buildPeginInstructions] Invalid protocol version given");
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]co.rsk.peg.pegininstructions.PeginInstructionsException([CtLiteralImpl]"Invalid protocol version");
        }
        [CtReturnImpl]return [CtVariableReadImpl]peginInstructions;
    }
}
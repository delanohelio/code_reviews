[CompilationUnitImpl][CtCommentImpl]/* Copyright ConsenSys AG.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.

SPDX-License-Identifier: Apache-2.0
 */
[CtPackageDeclarationImpl]package org.hyperledger.besu.consensus.common.bft;
[CtUnresolvedImport]import org.hyperledger.besu.ethereum.mainnet.ProtocolSpecBuilder;
[CtUnresolvedImport]import org.hyperledger.besu.config.IbftConfigOptions;
[CtUnresolvedImport]import static org.hyperledger.besu.consensus.common.bft.BftBlockHeaderValidationRulesetFactory.bftBlockHeaderValidator;
[CtUnresolvedImport]import org.hyperledger.besu.ethereum.mainnet.ProtocolScheduleBuilder;
[CtUnresolvedImport]import org.hyperledger.besu.config.GenesisConfigOptions;
[CtUnresolvedImport]import org.hyperledger.besu.ethereum.mainnet.ProtocolSchedule;
[CtUnresolvedImport]import org.hyperledger.besu.ethereum.MainnetBlockValidator;
[CtUnresolvedImport]import org.hyperledger.besu.ethereum.core.Address;
[CtUnresolvedImport]import org.hyperledger.besu.ethereum.mainnet.MainnetBlockBodyValidator;
[CtUnresolvedImport]import org.hyperledger.besu.ethereum.mainnet.MainnetBlockImporter;
[CtImportImpl]import java.math.BigInteger;
[CtUnresolvedImport]import org.hyperledger.besu.ethereum.core.Wei;
[CtUnresolvedImport]import org.hyperledger.besu.ethereum.core.PrivacyParameters;
[CtClassImpl][CtJavaDocImpl]/**
 * Defines the protocol behaviours for a blockchain using a BFT consensus mechanism.
 */
public class BftProtocolSchedule {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]java.math.BigInteger DEFAULT_CHAIN_ID = [CtFieldReadImpl][CtTypeAccessImpl]java.math.BigInteger.[CtFieldReferenceImpl]ONE;

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.hyperledger.besu.ethereum.mainnet.ProtocolSchedule create([CtParameterImpl]final [CtTypeReferenceImpl]org.hyperledger.besu.config.GenesisConfigOptions config, [CtParameterImpl]final [CtTypeReferenceImpl]org.hyperledger.besu.ethereum.core.PrivacyParameters privacyParameters, [CtParameterImpl]final [CtTypeReferenceImpl]boolean isRevertReasonEnabled) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]org.hyperledger.besu.ethereum.mainnet.ProtocolScheduleBuilder([CtVariableReadImpl]config, [CtFieldReadImpl]org.hyperledger.besu.consensus.common.bft.BftProtocolSchedule.DEFAULT_CHAIN_ID, [CtLambdaImpl]([CtParameterImpl] builder) -> [CtInvocationImpl]applyBftChanges([CtInvocationImpl][CtVariableReadImpl]config.getIbft2ConfigOptions(), [CtVariableReadImpl]builder), [CtVariableReadImpl]privacyParameters, [CtVariableReadImpl]isRevertReasonEnabled, [CtInvocationImpl][CtVariableReadImpl]config.isQuorum()).createProtocolSchedule();
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.hyperledger.besu.ethereum.mainnet.ProtocolSchedule create([CtParameterImpl]final [CtTypeReferenceImpl]org.hyperledger.besu.config.GenesisConfigOptions config, [CtParameterImpl]final [CtTypeReferenceImpl]boolean isRevertReasonEnabled) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.hyperledger.besu.consensus.common.bft.BftProtocolSchedule.create([CtVariableReadImpl]config, [CtTypeAccessImpl]PrivacyParameters.DEFAULT, [CtVariableReadImpl]isRevertReasonEnabled);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]org.hyperledger.besu.ethereum.mainnet.ProtocolSchedule create([CtParameterImpl]final [CtTypeReferenceImpl]org.hyperledger.besu.config.GenesisConfigOptions config) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]org.hyperledger.besu.consensus.common.bft.BftProtocolSchedule.create([CtVariableReadImpl]config, [CtTypeAccessImpl]PrivacyParameters.DEFAULT, [CtLiteralImpl]false);
    }

    [CtMethodImpl]private static [CtTypeReferenceImpl]org.hyperledger.besu.ethereum.mainnet.ProtocolSpecBuilder applyBftChanges([CtParameterImpl]final [CtTypeReferenceImpl]org.hyperledger.besu.config.IbftConfigOptions configOptions, [CtParameterImpl]final [CtTypeReferenceImpl]org.hyperledger.besu.ethereum.mainnet.ProtocolSpecBuilder builder) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]configOptions.getEpochLength() <= [CtLiteralImpl]0) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Epoch length in config must be greater than zero");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]configOptions.getBlockRewardWei().signum() < [CtLiteralImpl]0) [CtBlockImpl]{
            [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Bft Block reward in config cannot be negative");
        }
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]builder.blockHeaderValidatorBuilder([CtInvocationImpl]org.hyperledger.besu.consensus.common.bft.BftBlockHeaderValidationRulesetFactory.bftBlockHeaderValidator([CtInvocationImpl][CtVariableReadImpl]configOptions.getBlockPeriodSeconds())).ommerHeaderValidatorBuilder([CtInvocationImpl]org.hyperledger.besu.consensus.common.bft.BftBlockHeaderValidationRulesetFactory.bftBlockHeaderValidator([CtInvocationImpl][CtVariableReadImpl]configOptions.getBlockPeriodSeconds())).blockBodyValidatorBuilder([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]org.hyperledger.besu.ethereum.mainnet.MainnetBlockBodyValidator::new).blockValidatorBuilder([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]org.hyperledger.besu.ethereum.MainnetBlockValidator::new).blockImporterBuilder([CtExecutableReferenceExpressionImpl][CtTypeAccessImpl]org.hyperledger.besu.ethereum.mainnet.MainnetBlockImporter::new).difficultyCalculator([CtLambdaImpl]([CtParameterImpl] time,[CtParameterImpl] parent,[CtParameterImpl] protocolContext) -> [CtVariableReadImpl]BigInteger.ONE).blockReward([CtInvocationImpl][CtTypeAccessImpl]org.hyperledger.besu.ethereum.core.Wei.of([CtInvocationImpl][CtVariableReadImpl]configOptions.getBlockRewardWei())).skipZeroBlockRewards([CtLiteralImpl]true).blockHeaderFunctions([CtInvocationImpl][CtTypeAccessImpl]org.hyperledger.besu.consensus.common.bft.BftBlockHeaderFunctions.forOnChainBlock());
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]configOptions.getMiningBeneficiary().isPresent()) [CtBlockImpl]{
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]org.hyperledger.besu.ethereum.core.Address miningBeneficiary;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// Precalculate beneficiary to ensure string is valid now, rather than on lambda execution.
                [CtVariableWriteImpl]miningBeneficiary = [CtInvocationImpl][CtTypeAccessImpl]org.hyperledger.besu.ethereum.core.Address.fromHexString([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]configOptions.getMiningBeneficiary().get());
            }[CtCatchImpl] catch ([CtCatchVariableImpl]final [CtTypeReferenceImpl]java.lang.IllegalArgumentException e) [CtBlockImpl]{
                [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.IllegalArgumentException([CtLiteralImpl]"Mining beneficiary in config is not a valid ethereum address", [CtVariableReadImpl]e);
            }
            [CtInvocationImpl][CtVariableReadImpl]builder.miningBeneficiaryCalculator([CtLambdaImpl]([CtParameterImpl] header) -> [CtVariableReadImpl]miningBeneficiary);
        }
        [CtReturnImpl]return [CtVariableReadImpl]builder;
    }
}
[CompilationUnitImpl][CtPackageDeclarationImpl]package net.ripe.db.whois.update.handler.validator.poem;
[CtUnresolvedImport]import net.ripe.db.whois.common.rpsl.ObjectType;
[CtUnresolvedImport]import net.ripe.db.whois.common.rpsl.AttributeType;
[CtUnresolvedImport]import static net.ripe.db.whois.common.domain.CIString.ciString;
[CtUnresolvedImport]import net.ripe.db.whois.update.domain.PreparedUpdate;
[CtUnresolvedImport]import net.ripe.db.whois.update.handler.validator.BusinessRuleValidator;
[CtUnresolvedImport]import net.ripe.db.whois.update.domain.UpdateMessages;
[CtUnresolvedImport]import net.ripe.db.whois.common.rpsl.RpslAttribute;
[CtUnresolvedImport]import org.springframework.stereotype.Component;
[CtUnresolvedImport]import net.ripe.db.whois.update.domain.UpdateContext;
[CtUnresolvedImport]import com.google.common.collect.ImmutableList;
[CtUnresolvedImport]import net.ripe.db.whois.update.domain.Action;
[CtUnresolvedImport]import net.ripe.db.whois.common.domain.CIString;
[CtImportImpl]import java.util.List;
[CtClassImpl][CtAnnotationImpl]@org.springframework.stereotype.Component
public class PoeticFormHasOnlyDbmMaintainerValidator implements [CtTypeReferenceImpl]net.ripe.db.whois.update.handler.validator.BusinessRuleValidator {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.google.common.collect.ImmutableList<[CtTypeReferenceImpl]net.ripe.db.whois.update.domain.Action> ACTIONS = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of([CtTypeAccessImpl]Action.CREATE, [CtTypeAccessImpl]Action.MODIFY);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]com.google.common.collect.ImmutableList<[CtTypeReferenceImpl]net.ripe.db.whois.common.rpsl.ObjectType> TYPES = [CtInvocationImpl][CtTypeAccessImpl]com.google.common.collect.ImmutableList.of([CtTypeAccessImpl]ObjectType.POETIC_FORM);

    [CtFieldImpl]private static final [CtTypeReferenceImpl]net.ripe.db.whois.common.domain.CIString POETC_FORM_MAINTAINER = [CtInvocationImpl]ciString([CtLiteralImpl]"RIPE-DBM-MNT");

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void validate([CtParameterImpl]final [CtTypeReferenceImpl]net.ripe.db.whois.update.domain.PreparedUpdate update, [CtParameterImpl]final [CtTypeReferenceImpl]net.ripe.db.whois.update.domain.UpdateContext updateContext) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]net.ripe.db.whois.common.rpsl.RpslAttribute> mntByAttribute = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]update.getUpdatedObject().findAttributes([CtTypeAccessImpl]AttributeType.MNT_BY);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]mntByAttribute.size() != [CtLiteralImpl]1) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mntByAttribute.get([CtLiteralImpl]0).getCleanValue().equals([CtFieldReadImpl]net.ripe.db.whois.update.handler.validator.poem.PoeticFormHasOnlyDbmMaintainerValidator.POETC_FORM_MAINTAINER))) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]updateContext.addMessage([CtVariableReadImpl]update, [CtInvocationImpl][CtVariableReadImpl]mntByAttribute.get([CtLiteralImpl]0), [CtInvocationImpl][CtTypeAccessImpl]net.ripe.db.whois.update.domain.UpdateMessages.poeticFormRequiresDbmMaintainer());
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.common.collect.ImmutableList<[CtTypeReferenceImpl]net.ripe.db.whois.update.domain.Action> getActions() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]net.ripe.db.whois.update.handler.validator.poem.PoeticFormHasOnlyDbmMaintainerValidator.ACTIONS;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]com.google.common.collect.ImmutableList<[CtTypeReferenceImpl]net.ripe.db.whois.common.rpsl.ObjectType> getTypes() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]net.ripe.db.whois.update.handler.validator.poem.PoeticFormHasOnlyDbmMaintainerValidator.TYPES;
    }
}
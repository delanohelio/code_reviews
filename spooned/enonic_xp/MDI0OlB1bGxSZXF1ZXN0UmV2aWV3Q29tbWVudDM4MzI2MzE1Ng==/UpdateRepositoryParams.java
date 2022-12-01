[CompilationUnitImpl][CtPackageDeclarationImpl]package com.enonic.xp.repository;
[CtImportImpl]import java.util.function.Consumer;
[CtUnresolvedImport]import com.google.common.base.Preconditions;
[CtUnresolvedImport]import com.google.common.annotations.Beta;
[CtClassImpl][CtAnnotationImpl]@com.google.common.annotations.Beta
public final class UpdateRepositoryParams {
    [CtFieldImpl]private final [CtTypeReferenceImpl]com.enonic.xp.repository.RepositoryId repositoryId;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.function.Consumer<[CtTypeReferenceImpl]com.enonic.xp.repository.EditableRepository> editor;

    [CtConstructorImpl]private UpdateRepositoryParams([CtParameterImpl]final [CtTypeReferenceImpl]com.enonic.xp.repository.UpdateRepositoryParams.Builder builder) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.repositoryId = [CtFieldReadImpl][CtVariableReadImpl]builder.repositoryId;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.editor = [CtFieldReadImpl][CtVariableReadImpl]builder.editor;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.function.Consumer<[CtTypeReferenceImpl]com.enonic.xp.repository.EditableRepository> getEditor() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]editor;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.enonic.xp.repository.RepositoryId getRepositoryId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]repositoryId;
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]com.enonic.xp.repository.UpdateRepositoryParams.Builder create() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.enonic.xp.repository.UpdateRepositoryParams.Builder();
    }

    [CtClassImpl]public static final class Builder {
        [CtFieldImpl]private [CtTypeReferenceImpl]com.enonic.xp.repository.RepositoryId repositoryId;

        [CtFieldImpl]private [CtTypeReferenceImpl]java.util.function.Consumer<[CtTypeReferenceImpl]com.enonic.xp.repository.EditableRepository> editor;

        [CtConstructorImpl]private Builder() [CtBlockImpl]{
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.enonic.xp.repository.UpdateRepositoryParams.Builder repositoryId([CtParameterImpl]final [CtTypeReferenceImpl]com.enonic.xp.repository.RepositoryId repositoryId) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.repositoryId = [CtVariableReadImpl]repositoryId;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.enonic.xp.repository.UpdateRepositoryParams.Builder editor([CtParameterImpl]final [CtTypeReferenceImpl]java.util.function.Consumer<[CtTypeReferenceImpl]com.enonic.xp.repository.EditableRepository> editor) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.editor = [CtVariableReadImpl]editor;
            [CtReturnImpl]return [CtThisAccessImpl]this;
        }

        [CtMethodImpl]private [CtTypeReferenceImpl]void validate() [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]com.google.common.base.Preconditions.checkNotNull([CtFieldReadImpl]repositoryId, [CtLiteralImpl]"repositoryId cannot be null");
        }

        [CtMethodImpl]public [CtTypeReferenceImpl]com.enonic.xp.repository.UpdateRepositoryParams build() [CtBlockImpl]{
            [CtInvocationImpl]validate();
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.enonic.xp.repository.UpdateRepositoryParams([CtThisAccessImpl]this);
        }
    }
}
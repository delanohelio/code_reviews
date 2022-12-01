[CompilationUnitImpl][CtPackageDeclarationImpl]package com.box.sdk;
[CtImportImpl]import java.net.URL;
[CtUnresolvedImport]import com.eclipsesource.json.JsonObject;
[CtImportImpl]import java.util.Iterator;
[CtClassImpl]class BoxCollaborationIterator implements [CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl][CtTypeReferenceImpl]com.box.sdk.BoxCollaboration.Info> {
    [CtFieldImpl]private static final [CtTypeReferenceImpl]long LIMIT = [CtLiteralImpl]100;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.box.sdk.BoxAPIConnection api;

    [CtFieldImpl]private final [CtTypeReferenceImpl]com.box.sdk.JSONIterator jsonIterator;

    [CtConstructorImpl]BoxCollaborationIterator([CtParameterImpl][CtTypeReferenceImpl]com.box.sdk.BoxAPIConnection api, [CtParameterImpl][CtTypeReferenceImpl]java.net.URL url) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.api = [CtVariableReadImpl]api;
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.jsonIterator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.box.sdk.JSONIterator([CtVariableReadImpl]api, [CtVariableReadImpl]url, [CtFieldReadImpl]com.box.sdk.BoxCollaborationIterator.LIMIT);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean hasNext() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.jsonIterator.hasNext();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]BoxCollaboration.Info next() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.eclipsesource.json.JsonObject nextJSONObject = [CtInvocationImpl][CtFieldReadImpl][CtThisAccessImpl]this.jsonIterator.next();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String id = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nextJSONObject.get([CtLiteralImpl]"id").asString();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.box.sdk.BoxCollaboration collaboration = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.box.sdk.BoxCollaboration([CtFieldReadImpl][CtThisAccessImpl]this.api, [CtVariableReadImpl]id);
        [CtReturnImpl]return [CtConstructorCallImpl][CtVariableReadImpl]collaboration.new [CtTypeReferenceImpl]com.box.sdk.Info([CtVariableReadImpl]nextJSONObject);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void remove() [CtBlockImpl]{
        [CtThrowImpl]throw [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.UnsupportedOperationException();
    }
}
[CompilationUnitImpl][CtPackageDeclarationImpl]package com.alibaba.csp.sentinel.adapter.dubbo.origin;
[CtUnresolvedImport]import com.alibaba.dubbo.rpc.Invoker;
[CtUnresolvedImport]import com.alibaba.csp.sentinel.context.Context;
[CtUnresolvedImport]import com.alibaba.dubbo.rpc.Invocation;
[CtInterfaceImpl][CtJavaDocImpl]/**
 * Customized handler parser in Dubbo provider filter. {@link Context#getOrigin()}
 *
 * @author tc
 * @date 2020/6/10
 */
public interface DubboOrigin {
    [CtMethodImpl][CtJavaDocImpl]/**
     * Handle the handler parser.
     *
     * @param invoker
     * 		Dubbo invoker
     * @param invocation
     * 		Dubbo invocation
     * @return handler result
     */
    [CtTypeReferenceImpl]java.lang.String handler([CtParameterImpl][CtTypeReferenceImpl]com.alibaba.dubbo.rpc.Invoker<[CtWildcardReferenceImpl]?> invoker, [CtParameterImpl][CtTypeReferenceImpl]com.alibaba.dubbo.rpc.Invocation invocation);
}
[CompilationUnitImpl][CtPackageDeclarationImpl]package com.vaadin.flow.component;
[CtUnresolvedImport]import com.vaadin.flow.component.polymertemplate.TemplateParser.TemplateData;
[CtUnresolvedImport]import com.vaadin.flow.templatemodel.TemplateModel;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import com.vaadin.flow.function.DeploymentConfiguration;
[CtUnresolvedImport]import net.jcip.annotations.NotThreadSafe;
[CtUnresolvedImport]import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
[CtUnresolvedImport]import org.mockito.Mockito;
[CtUnresolvedImport]import org.junit.After;
[CtUnresolvedImport]import com.vaadin.flow.dom.Element;
[CtUnresolvedImport]import com.vaadin.flow.server.VaadinService;
[CtUnresolvedImport]import static org.junit.Assert.assertEquals;
[CtUnresolvedImport]import org.jsoup.Jsoup;
[CtUnresolvedImport]import org.junit.Before;
[CtClassImpl][CtAnnotationImpl]@net.jcip.annotations.NotThreadSafe
public class CompositeTest {
    [CtClassImpl][CtAnnotationImpl]@com.vaadin.flow.component.Tag([CtLiteralImpl]"div")
    public static class MyTemplate extends [CtTypeReferenceImpl]com.vaadin.flow.component.polymertemplate.PolymerTemplate<[CtTypeReferenceImpl]com.vaadin.flow.templatemodel.TemplateModel> {
        [CtConstructorImpl]public MyTemplate() [CtBlockImpl]{
            [CtInvocationImpl]super([CtLambdaImpl]([CtParameterImpl] clazz,[CtParameterImpl] tag,[CtParameterImpl] service) -> [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vaadin.flow.component.polymertemplate.TemplateParser.TemplateData([CtLiteralImpl]"", [CtInvocationImpl][CtTypeAccessImpl]org.jsoup.Jsoup.parse([CtLiteralImpl]"<dom-module id='div'></dom-module>")));
        }
    }

    [CtClassImpl]public static class KeyNotifierComposite extends [CtTypeReferenceImpl]com.vaadin.flow.component.Composite<[CtTypeReferenceImpl]com.vaadin.flow.component.CompositeTest.MyTemplate> implements [CtTypeReferenceImpl]com.vaadin.flow.component.KeyNotifier {
        [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
        protected [CtTypeReferenceImpl]com.vaadin.flow.component.CompositeTest.MyTemplate initContent() [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]com.vaadin.flow.component.CompositeTest.MyTemplate template = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vaadin.flow.component.CompositeTest.MyTemplate();
            [CtInvocationImpl]addKeyUpListener([CtTypeAccessImpl]Key.ENTER, [CtLambdaImpl]([CtParameterImpl] event) -> [CtBlockImpl]{
            }, [CtTypeAccessImpl]KeyModifier.CONTROL);
            [CtReturnImpl]return [CtVariableReadImpl]template;
        }
    }

    [CtFieldImpl]private [CtTypeReferenceImpl]com.vaadin.flow.server.VaadinService service;

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Before
    public [CtTypeReferenceImpl]void setup() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]service = [CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.mock([CtFieldReadImpl]com.vaadin.flow.server.VaadinService.class);
        [CtInvocationImpl][CtTypeAccessImpl]com.vaadin.flow.server.VaadinService.setCurrent([CtFieldReadImpl]service);
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.vaadin.flow.function.DeploymentConfiguration configuration = [CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.mock([CtFieldReadImpl]com.vaadin.flow.function.DeploymentConfiguration.class);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]org.mockito.Mockito.when([CtInvocationImpl][CtFieldReadImpl]service.getDeploymentConfiguration()).thenReturn([CtVariableReadImpl]configuration);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.After
    public [CtTypeReferenceImpl]void tearDown() [CtBlockImpl]{
        [CtInvocationImpl][CtTypeAccessImpl]com.vaadin.flow.server.VaadinService.setCurrent([CtLiteralImpl]null);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test(expected = [CtFieldReadImpl]java.lang.IllegalStateException.class)
    public [CtTypeReferenceImpl]void getContent_compositeIsKeyNotifier() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.vaadin.flow.component.CompositeTest.KeyNotifierComposite composite = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vaadin.flow.component.CompositeTest.KeyNotifierComposite();
        [CtInvocationImpl][CtVariableReadImpl]composite.getContent();
    }

    [CtMethodImpl][CtCommentImpl]/* This is just a test for #1181. */
    [CtCommentImpl]// @Ignore("Failing after adding connect client generators")
    [CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void templateInsideComposite_compositeCanBeAdded() [CtBlockImpl]{
        [CtClassImpl]class MyComponent extends [CtTypeReferenceImpl]com.vaadin.flow.component.Composite<[CtTypeReferenceImpl]com.vaadin.flow.component.CompositeTest.MyTemplate> {}
        [CtLocalVariableImpl][CtTypeReferenceImpl]MyComponent component = [CtConstructorCallImpl]new [CtTypeReferenceImpl]MyComponent();
        [CtLocalVariableImpl][CtTypeReferenceImpl]com.vaadin.flow.component.UI ui = [CtConstructorCallImpl]new [CtTypeReferenceImpl]com.vaadin.flow.component.UI();
        [CtInvocationImpl][CtCommentImpl]// Doesn't throw any exception
        [CtVariableReadImpl]ui.add([CtVariableReadImpl]component);
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]void assertElementChildren([CtParameterImpl][CtTypeReferenceImpl]com.vaadin.flow.dom.Element parent, [CtParameterImpl]com.vaadin.flow.dom.Element... expected) [CtBlockImpl]{
        [CtInvocationImpl]Assert.assertEquals([CtFieldReadImpl][CtVariableReadImpl]expected.length, [CtInvocationImpl][CtVariableReadImpl]parent.getChildCount());
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtInvocationImpl][CtVariableReadImpl]parent.getChildCount(); [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtInvocationImpl]Assert.assertEquals([CtArrayReadImpl][CtVariableReadImpl]expected[[CtVariableReadImpl]i], [CtInvocationImpl][CtVariableReadImpl]parent.getChild([CtVariableReadImpl]i));
        }
    }
}
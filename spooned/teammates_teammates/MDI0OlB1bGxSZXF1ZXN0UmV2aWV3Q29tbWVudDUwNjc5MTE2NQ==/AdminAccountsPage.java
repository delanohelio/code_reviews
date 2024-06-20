[CompilationUnitImpl][CtPackageDeclarationImpl]package teammates.e2e.pageobjects;
[CtUnresolvedImport]import org.openqa.selenium.By;
[CtUnresolvedImport]import static org.junit.Assert.assertEquals;
[CtUnresolvedImport]import org.openqa.selenium.WebElement;
[CtUnresolvedImport]import static org.junit.Assert.fail;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import teammates.common.datatransfer.attributes.AccountAttributes;
[CtUnresolvedImport]import org.openqa.selenium.support.FindBy;
[CtClassImpl][CtJavaDocImpl]/**
 * Page Object Model for the admin accounts page.
 */
public class AdminAccountsPage extends [CtTypeReferenceImpl]teammates.e2e.pageobjects.AppPage {
    [CtFieldImpl][CtAnnotationImpl]@org.openqa.selenium.support.FindBy(id = [CtLiteralImpl]"account-google-id")
    private [CtTypeReferenceImpl]org.openqa.selenium.WebElement accountId;

    [CtFieldImpl][CtAnnotationImpl]@org.openqa.selenium.support.FindBy(id = [CtLiteralImpl]"account-name")
    private [CtTypeReferenceImpl]org.openqa.selenium.WebElement accountName;

    [CtFieldImpl][CtAnnotationImpl]@org.openqa.selenium.support.FindBy(id = [CtLiteralImpl]"account-email")
    private [CtTypeReferenceImpl]org.openqa.selenium.WebElement accountEmail;

    [CtFieldImpl][CtAnnotationImpl]@org.openqa.selenium.support.FindBy(id = [CtLiteralImpl]"account-institute")
    private [CtTypeReferenceImpl]org.openqa.selenium.WebElement accountInstitute;

    [CtFieldImpl][CtAnnotationImpl]@org.openqa.selenium.support.FindBy(id = [CtLiteralImpl]"account-is-instructor")
    private [CtTypeReferenceImpl]org.openqa.selenium.WebElement accountIsInstructor;

    [CtFieldImpl][CtAnnotationImpl]@org.openqa.selenium.support.FindBy(id = [CtLiteralImpl]"instructor-table")
    private [CtTypeReferenceImpl]org.openqa.selenium.WebElement instructorTable;

    [CtFieldImpl][CtAnnotationImpl]@org.openqa.selenium.support.FindBy(id = [CtLiteralImpl]"student-table")
    private [CtTypeReferenceImpl]org.openqa.selenium.WebElement studentTable;

    [CtFieldImpl][CtAnnotationImpl]@org.openqa.selenium.support.FindBy(id = [CtLiteralImpl]"btn-downgrade-account")
    private [CtTypeReferenceImpl]org.openqa.selenium.WebElement downgradeAccountButton;

    [CtFieldImpl][CtAnnotationImpl]@org.openqa.selenium.support.FindBy(id = [CtLiteralImpl]"btn-delete-account")
    private [CtTypeReferenceImpl]org.openqa.selenium.WebElement deleteAccountButton;

    [CtConstructorImpl]public AdminAccountsPage([CtParameterImpl][CtTypeReferenceImpl]teammates.e2e.pageobjects.Browser browser) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]browser);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]boolean containsExpectedPageContents() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getPageSource().contains([CtLiteralImpl]"Account Details");
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void verifyAccountDetails([CtParameterImpl][CtTypeReferenceImpl]teammates.common.datatransfer.attributes.AccountAttributes account) [CtBlockImpl]{
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]account.getGoogleId(), [CtInvocationImpl][CtFieldReadImpl]accountId.getText());
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]account.getName(), [CtInvocationImpl][CtFieldReadImpl]accountName.getText());
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]account.getEmail(), [CtInvocationImpl][CtFieldReadImpl]accountEmail.getText());
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]account.getInstitute(), [CtInvocationImpl][CtFieldReadImpl]accountInstitute.getText());
        [CtInvocationImpl]Assert.assertEquals([CtInvocationImpl][CtVariableReadImpl]account.isInstructor(), [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.parseBoolean([CtInvocationImpl][CtFieldReadImpl]accountIsInstructor.getText()));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void clickRemoveInstructorFromCourse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openqa.selenium.WebElement> instructorRows = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]instructorTable.findElement([CtInvocationImpl][CtTypeAccessImpl]org.openqa.selenium.By.tagName([CtLiteralImpl]"tbody")).findElements([CtInvocationImpl][CtTypeAccessImpl]org.openqa.selenium.By.tagName([CtLiteralImpl]"tr"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openqa.selenium.WebElement deleteButton = [CtLiteralImpl]null;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openqa.selenium.WebElement instructorRow : [CtVariableReadImpl]instructorRows) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openqa.selenium.WebElement> cells = [CtInvocationImpl][CtVariableReadImpl]instructorRow.findElements([CtInvocationImpl][CtTypeAccessImpl]org.openqa.selenium.By.tagName([CtLiteralImpl]"td"));
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cells.get([CtLiteralImpl]0).getText().startsWith([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"[" + [CtVariableReadImpl]courseId) + [CtLiteralImpl]"]")) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]deleteButton = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cells.get([CtLiteralImpl]1).findElement([CtInvocationImpl][CtTypeAccessImpl]org.openqa.selenium.By.className([CtLiteralImpl]"btn-danger"));
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]deleteButton == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"Instructor to be deleted is not found");
        }
        [CtInvocationImpl]click([CtVariableReadImpl]deleteButton);
        [CtInvocationImpl]waitForPageToLoad([CtLiteralImpl]true);
        [CtInvocationImpl]verifyStatusMessage([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Instructor is successfully deleted from course \"" + [CtVariableReadImpl]courseId) + [CtLiteralImpl]"\"");
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void clickRemoveStudentFromCourse([CtParameterImpl][CtTypeReferenceImpl]java.lang.String courseId) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openqa.selenium.WebElement> instructorRows = [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]studentTable.findElement([CtInvocationImpl][CtTypeAccessImpl]org.openqa.selenium.By.tagName([CtLiteralImpl]"tbody")).findElements([CtInvocationImpl][CtTypeAccessImpl]org.openqa.selenium.By.tagName([CtLiteralImpl]"tr"));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.openqa.selenium.WebElement deleteButton = [CtLiteralImpl]null;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]org.openqa.selenium.WebElement instructorRow : [CtVariableReadImpl]instructorRows) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]org.openqa.selenium.WebElement> cells = [CtInvocationImpl][CtVariableReadImpl]instructorRow.findElements([CtInvocationImpl][CtTypeAccessImpl]org.openqa.selenium.By.tagName([CtLiteralImpl]"td"));
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cells.get([CtLiteralImpl]0).getText().startsWith([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"[" + [CtVariableReadImpl]courseId) + [CtLiteralImpl]"]")) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]deleteButton = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cells.get([CtLiteralImpl]1).findElement([CtInvocationImpl][CtTypeAccessImpl]org.openqa.selenium.By.className([CtLiteralImpl]"btn-danger"));
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]deleteButton == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]Assert.fail([CtLiteralImpl]"Student to be deleted is not found");
        }
        [CtInvocationImpl]click([CtVariableReadImpl]deleteButton);
        [CtInvocationImpl]waitForPageToLoad([CtLiteralImpl]true);
        [CtInvocationImpl]verifyStatusMessage([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Student is successfully deleted from course \"" + [CtVariableReadImpl]courseId) + [CtLiteralImpl]"\"");
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void clickDowngradeAccount() [CtBlockImpl]{
        [CtInvocationImpl]click([CtFieldReadImpl]downgradeAccountButton);
        [CtInvocationImpl]waitForPageToLoad([CtLiteralImpl]true);
        [CtInvocationImpl]verifyStatusMessage([CtLiteralImpl]"Instructor account is successfully downgraded to student.");
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void clickDeleteAccount([CtParameterImpl][CtTypeReferenceImpl]java.lang.String googleId) [CtBlockImpl]{
        [CtInvocationImpl]click([CtFieldReadImpl]deleteAccountButton);
        [CtInvocationImpl]waitForPageToLoad([CtLiteralImpl]true);
        [CtInvocationImpl]verifyStatusMessage([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Account \"" + [CtVariableReadImpl]googleId) + [CtLiteralImpl]"\" is successfully deleted.");
        [CtInvocationImpl]changePageType([CtFieldReadImpl]teammates.e2e.pageobjects.AdminSearchPage.class);
    }
}
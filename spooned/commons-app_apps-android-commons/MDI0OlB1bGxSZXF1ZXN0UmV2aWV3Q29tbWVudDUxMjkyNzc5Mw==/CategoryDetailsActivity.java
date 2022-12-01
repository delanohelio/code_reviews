[CompilationUnitImpl][CtPackageDeclarationImpl]package fr.free.nrw.commons.category;
[CtUnresolvedImport]import android.content.Context;
[CtUnresolvedImport]import android.os.Bundle;
[CtUnresolvedImport]import butterknife.ButterKnife;
[CtUnresolvedImport]import fr.free.nrw.commons.theme.NavigationBaseActivity;
[CtImportImpl]import java.util.ArrayList;
[CtUnresolvedImport]import android.net.Uri;
[CtUnresolvedImport]import android.widget.FrameLayout;
[CtUnresolvedImport]import androidx.viewpager.widget.ViewPager;
[CtUnresolvedImport]import fr.free.nrw.commons.R;
[CtUnresolvedImport]import android.content.Intent;
[CtUnresolvedImport]import android.view.MenuItem;
[CtUnresolvedImport]import butterknife.BindView;
[CtUnresolvedImport]import fr.free.nrw.commons.Media;
[CtUnresolvedImport]import android.view.MenuInflater;
[CtUnresolvedImport]import fr.free.nrw.commons.explore.ViewPagerAdapter;
[CtUnresolvedImport]import fr.free.nrw.commons.Utils;
[CtUnresolvedImport]import fr.free.nrw.commons.explore.categories.parent.ParentCategoriesFragment;
[CtUnresolvedImport]import android.view.Menu;
[CtUnresolvedImport]import androidx.fragment.app.Fragment;
[CtUnresolvedImport]import fr.free.nrw.commons.media.MediaDetailPagerFragment;
[CtUnresolvedImport]import android.view.View;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import fr.free.nrw.commons.explore.categories.sub.SubCategoriesFragment;
[CtUnresolvedImport]import androidx.fragment.app.FragmentManager;
[CtUnresolvedImport]import com.google.android.material.tabs.TabLayout;
[CtUnresolvedImport]import fr.free.nrw.commons.explore.categories.media.CategoriesMediaFragment;
[CtUnresolvedImport]import org.wikipedia.page.PageTitle;
[CtClassImpl][CtJavaDocImpl]/**
 * This activity displays details of a particular category
 * Its generic and simply takes the name of category name in its start intent to load all images, subcategories in
 * a particular category on wikimedia commons.
 */
public class CategoryDetailsActivity extends [CtTypeReferenceImpl]fr.free.nrw.commons.theme.NavigationBaseActivity implements [CtTypeReferenceImpl][CtTypeReferenceImpl]fr.free.nrw.commons.media.MediaDetailPagerFragment.MediaDetailProvider , [CtTypeReferenceImpl]fr.free.nrw.commons.category.CategoryImagesCallback {
    [CtFieldImpl]private [CtTypeReferenceImpl]androidx.fragment.app.FragmentManager supportFragmentManager;

    [CtFieldImpl]private [CtTypeReferenceImpl]fr.free.nrw.commons.explore.categories.media.CategoriesMediaFragment categoriesMediaFragment;

    [CtFieldImpl]private [CtTypeReferenceImpl]fr.free.nrw.commons.media.MediaDetailPagerFragment mediaDetails;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String categoryName;

    [CtFieldImpl][CtAnnotationImpl]@butterknife.BindView([CtFieldReadImpl]R.id.mediaContainer)
    [CtTypeReferenceImpl]android.widget.FrameLayout mediaContainer;

    [CtFieldImpl][CtAnnotationImpl]@butterknife.BindView([CtFieldReadImpl]R.id.tab_layout)
    [CtTypeReferenceImpl]com.google.android.material.tabs.TabLayout tabLayout;

    [CtFieldImpl][CtAnnotationImpl]@butterknife.BindView([CtFieldReadImpl]R.id.viewPager)
    [CtTypeReferenceImpl]androidx.viewpager.widget.ViewPager viewPager;

    [CtFieldImpl][CtTypeReferenceImpl]fr.free.nrw.commons.explore.ViewPagerAdapter viewPagerAdapter;

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    protected [CtTypeReferenceImpl]void onCreate([CtParameterImpl][CtTypeReferenceImpl]android.os.Bundle savedInstanceState) [CtBlockImpl]{
        [CtInvocationImpl][CtSuperAccessImpl]super.onCreate([CtVariableReadImpl]savedInstanceState);
        [CtInvocationImpl]setContentView([CtTypeAccessImpl]R.layout.activity_category_details);
        [CtInvocationImpl][CtTypeAccessImpl]butterknife.ButterKnife.bind([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtFieldWriteImpl]supportFragmentManager = [CtInvocationImpl]getSupportFragmentManager();
        [CtAssignmentImpl][CtFieldWriteImpl]viewPagerAdapter = [CtConstructorCallImpl]new [CtTypeReferenceImpl]fr.free.nrw.commons.explore.ViewPagerAdapter([CtInvocationImpl]getSupportFragmentManager());
        [CtInvocationImpl][CtFieldReadImpl]viewPager.setAdapter([CtFieldReadImpl]viewPagerAdapter);
        [CtInvocationImpl][CtFieldReadImpl]viewPager.setOffscreenPageLimit([CtLiteralImpl]2);
        [CtInvocationImpl][CtFieldReadImpl]tabLayout.setupWithViewPager([CtFieldReadImpl]viewPager);
        [CtInvocationImpl]setTabs();
        [CtInvocationImpl]setPageTitle();
        [CtInvocationImpl]initDrawer();
        [CtInvocationImpl]forceInitBackButton();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This activity contains 3 tabs and a viewpager. This method is used to set the titles of tab,
     * Set the fragments according to the tab selected in the viewPager.
     */
    private [CtTypeReferenceImpl]void setTabs() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]androidx.fragment.app.Fragment> fragmentList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> titleList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtAssignmentImpl][CtFieldWriteImpl]categoriesMediaFragment = [CtConstructorCallImpl]new [CtTypeReferenceImpl]fr.free.nrw.commons.explore.categories.media.CategoriesMediaFragment();
        [CtLocalVariableImpl][CtTypeReferenceImpl]fr.free.nrw.commons.explore.categories.sub.SubCategoriesFragment subCategoryListFragment = [CtConstructorCallImpl]new [CtTypeReferenceImpl]fr.free.nrw.commons.explore.categories.sub.SubCategoriesFragment();
        [CtLocalVariableImpl][CtTypeReferenceImpl]fr.free.nrw.commons.explore.categories.parent.ParentCategoriesFragment parentCategoriesFragment = [CtConstructorCallImpl]new [CtTypeReferenceImpl]fr.free.nrw.commons.explore.categories.parent.ParentCategoriesFragment();
        [CtAssignmentImpl][CtFieldWriteImpl]categoryName = [CtInvocationImpl][CtInvocationImpl]getIntent().getStringExtra([CtLiteralImpl]"categoryName");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]getIntent() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]categoryName != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]android.os.Bundle arguments = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.os.Bundle();
            [CtInvocationImpl][CtVariableReadImpl]arguments.putString([CtLiteralImpl]"categoryName", [CtFieldReadImpl]categoryName);
            [CtInvocationImpl][CtFieldReadImpl]categoriesMediaFragment.setArguments([CtVariableReadImpl]arguments);
            [CtInvocationImpl][CtVariableReadImpl]subCategoryListFragment.setArguments([CtVariableReadImpl]arguments);
            [CtInvocationImpl][CtVariableReadImpl]parentCategoriesFragment.setArguments([CtVariableReadImpl]arguments);
        }
        [CtInvocationImpl][CtVariableReadImpl]fragmentList.add([CtFieldReadImpl]categoriesMediaFragment);
        [CtInvocationImpl][CtVariableReadImpl]titleList.add([CtLiteralImpl]"MEDIA");
        [CtInvocationImpl][CtVariableReadImpl]fragmentList.add([CtVariableReadImpl]subCategoryListFragment);
        [CtInvocationImpl][CtVariableReadImpl]titleList.add([CtLiteralImpl]"SUBCATEGORIES");
        [CtInvocationImpl][CtVariableReadImpl]fragmentList.add([CtVariableReadImpl]parentCategoriesFragment);
        [CtInvocationImpl][CtVariableReadImpl]titleList.add([CtLiteralImpl]"PARENT CATEGORIES");
        [CtInvocationImpl][CtFieldReadImpl]viewPagerAdapter.setTabData([CtVariableReadImpl]fragmentList, [CtVariableReadImpl]titleList);
        [CtInvocationImpl][CtFieldReadImpl]viewPagerAdapter.notifyDataSetChanged();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the passed categoryName from the intents and displays it as the page title
     */
    private [CtTypeReferenceImpl]void setPageTitle() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]getIntent() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getIntent().getStringExtra([CtLiteralImpl]"categoryName") != [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtInvocationImpl]setTitle([CtInvocationImpl][CtInvocationImpl]getIntent().getStringExtra([CtLiteralImpl]"categoryName"));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method is called onClick of media inside category details (CategoryImageListFragment).
     */
    public [CtTypeReferenceImpl]void onMediaClicked([CtParameterImpl][CtTypeReferenceImpl]int position) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]tabLayout.setVisibility([CtTypeAccessImpl]View.GONE);
        [CtInvocationImpl][CtFieldReadImpl]viewPager.setVisibility([CtTypeAccessImpl]View.GONE);
        [CtInvocationImpl][CtFieldReadImpl]mediaContainer.setVisibility([CtTypeAccessImpl]View.VISIBLE);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]mediaDetails == [CtLiteralImpl]null) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]mediaDetails.isVisible())) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// set isFeaturedImage true for featured images, to include author field on media detail
            [CtFieldWriteImpl]mediaDetails = [CtConstructorCallImpl]new [CtTypeReferenceImpl]fr.free.nrw.commons.media.MediaDetailPagerFragment([CtLiteralImpl]false, [CtLiteralImpl]true);
            [CtLocalVariableImpl][CtTypeReferenceImpl]androidx.fragment.app.FragmentManager supportFragmentManager = [CtInvocationImpl]getSupportFragmentManager();
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]supportFragmentManager.beginTransaction().replace([CtTypeAccessImpl]R.id.mediaContainer, [CtFieldReadImpl]mediaDetails).addToBackStack([CtLiteralImpl]null).commit();
            [CtInvocationImpl][CtVariableReadImpl]supportFragmentManager.executePendingTransactions();
        }
        [CtInvocationImpl][CtFieldReadImpl]mediaDetails.showImage([CtVariableReadImpl]position);
        [CtInvocationImpl]forceInitBackButton();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Consumers should be simply using this method to use this activity.
     *
     * @param context
     * 		A Context of the application package implementing this class.
     * @param categoryName
     * 		Name of the category for displaying its details
     */
    public static [CtTypeReferenceImpl]void startYourself([CtParameterImpl][CtTypeReferenceImpl]android.content.Context context, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String categoryName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.content.Intent intent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]android.content.Intent([CtVariableReadImpl]context, [CtFieldReadImpl]fr.free.nrw.commons.category.CategoryDetailsActivity.class);
        [CtInvocationImpl][CtVariableReadImpl]intent.putExtra([CtLiteralImpl]"categoryName", [CtVariableReadImpl]categoryName);
        [CtInvocationImpl][CtVariableReadImpl]context.startActivity([CtVariableReadImpl]intent);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method is called mediaDetailPagerFragment. It returns the Media Object at that Index
     *
     * @param i
     * 		It is the index of which media object is to be returned which is same as
     * 		current index of viewPager.
     * @return Media Object
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]fr.free.nrw.commons.Media getMediaAtPosition([CtParameterImpl][CtTypeReferenceImpl]int i) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]categoriesMediaFragment.getMediaAtPosition([CtVariableReadImpl]i);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method is called on from getCount of MediaDetailPagerFragment
     * The viewpager will contain same number of media items as that of media elements in adapter.
     *
     * @return Total Media count in the adapter
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getTotalMediaCount() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]categoriesMediaFragment.getTotalMediaCount();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]java.lang.Integer getContributionStateAt([CtParameterImpl][CtTypeReferenceImpl]int position) [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method inflates the menu in the toolbar
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean onCreateOptionsMenu([CtParameterImpl][CtTypeReferenceImpl]android.view.Menu menu) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]android.view.MenuInflater inflater = [CtInvocationImpl]getMenuInflater();
        [CtInvocationImpl][CtVariableReadImpl]inflater.inflate([CtTypeAccessImpl]R.menu.fragment_category_detail, [CtVariableReadImpl]menu);
        [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.onCreateOptionsMenu([CtVariableReadImpl]menu);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method handles the logic on ItemSelect in toolbar menu
     * Currently only 1 choice is available to open category details page in browser
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean onOptionsItemSelected([CtParameterImpl][CtTypeReferenceImpl]android.view.MenuItem item) [CtBlockImpl]{
        [CtSwitchImpl][CtCommentImpl]// Handle item selection
        switch ([CtInvocationImpl][CtVariableReadImpl]item.getItemId()) {
            [CtCaseImpl]case [CtFieldReadImpl]R.id.menu_browser_current_category :
                [CtLocalVariableImpl][CtTypeReferenceImpl]org.wikipedia.page.PageTitle title = [CtInvocationImpl][CtTypeAccessImpl]fr.free.nrw.commons.Utils.getPageTitle([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"Category:%s", [CtFieldReadImpl]categoryName));
                [CtInvocationImpl][CtTypeAccessImpl]fr.free.nrw.commons.Utils.handleWebUrl([CtThisAccessImpl]this, [CtInvocationImpl][CtTypeAccessImpl]android.net.Uri.parse([CtInvocationImpl][CtVariableReadImpl]title.getCanonicalUri()));
                [CtReturnImpl]return [CtLiteralImpl]true;
            [CtCaseImpl]default :
                [CtReturnImpl]return [CtInvocationImpl][CtSuperAccessImpl]super.onOptionsItemSelected([CtVariableReadImpl]item);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method is called on backPressed of anyFragment in the activity.
     * If condition is called when mediaDetailFragment is opened.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void onBackPressed() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]supportFragmentManager.getBackStackEntryCount() == [CtLiteralImpl]1) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// back to search so show search toolbar and hide navigation toolbar
            [CtFieldReadImpl]tabLayout.setVisibility([CtTypeAccessImpl]View.VISIBLE);
            [CtInvocationImpl][CtFieldReadImpl]viewPager.setVisibility([CtTypeAccessImpl]View.VISIBLE);
            [CtInvocationImpl][CtFieldReadImpl]mediaContainer.setVisibility([CtTypeAccessImpl]View.GONE);
        }
        [CtInvocationImpl][CtSuperAccessImpl]super.onBackPressed();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method is called on success of API call for Images inside a category.
     * The viewpager will notified that number of items have changed.
     */
    [CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]void viewPagerNotifyDataSetChanged() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]mediaDetails != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]mediaDetails.notifyDataSetChanged();
        }
    }
}
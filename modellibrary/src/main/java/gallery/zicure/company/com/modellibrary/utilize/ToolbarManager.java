package gallery.zicure.company.com.modellibrary.utilize;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


/**
 * Created by 4GRYZ52 on 3/26/2017.
 */

public class ToolbarManager {
    private AppCompatActivity activity = null;
    private Toolbar toolbarMenu = null;
    private TextView textTitle = null;
    private Drawable menuToggle = null;

    public ToolbarManager(AppCompatActivity activity){
        this.activity = activity;
    }

    public void setToolbar(Toolbar toolbarMenu, TextView textTitle, Drawable menuToggle, String title){
        this.toolbarMenu = toolbarMenu;
        this.textTitle = textTitle;
        this.menuToggle = menuToggle;

        toolbarMenu.setFitsSystemWindows(true);
        setTitle(title);
        setIndicator();
        enableHomeButton();
    }

    private void setTitle(String title){
        if (title != null){
            textTitle.setText(title);
            toolbarMenu.setTitle("");
            activity.setSupportActionBar(toolbarMenu);
        }else{
            activity.setSupportActionBar(toolbarMenu);
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void setIndicator(){
        if (menuToggle != null){
            activity.getSupportActionBar().setHomeAsUpIndicator(menuToggle);
        }
    }

    private void enableHomeButton(){
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private int getActionBarHeight(){
        int actionBarHeight = 0;
        final TypedArray styleAttributes = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        actionBarHeight = (int) styleAttributes.getDimension(0,0);
        styleAttributes.recycle();

        return actionBarHeight;
    }

    private int getStatusBarHeight(){
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0){
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void setAppbarLayout(AppBarLayout layout){
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) layout.getLayoutParams();
        params.height = getActionBarHeight() + getStatusBarHeight() + 10;
        layout.setLayoutParams(params);
    }
}

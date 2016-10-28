package com.leeves.h.gankio.ui.base;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.leeves.h.gankio.R;
import com.leeves.h.gankio.ui.ContentFragment;

/**
 * Function：侧边栏功能
 * Created by h on 2016/10/25.
 *
 * @author Leeves
 */

public abstract class NavigationViewActivity extends ToolbarActivity {

    private DrawerLayout mDrawer;
    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    private Toolbar mToolbar;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNavigationView();
    }

    /**
     * 当打开侧边栏时，点返回键关闭侧边栏
     */
    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }
        //        else if (getFragmentManager().getBackStackEntryCount() == 0) {
//            super.onBackPressed();
//        }
    }

    /**
     * Function: 在主activity,两次退出程序
     *
     * @param keyCode
     * @param event
     * @return null
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN
                && getFragmentManager().getBackStackEntryCount() == 0
                && !mDrawer.isDrawerOpen(GravityCompat.START)
                ) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast toast = Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 左上角Menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && isCanBack()) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == android.R.id.home && isMaterialMenu()) {
            mDrawer.openDrawer(GravityCompat.START);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    private void initNavigationView() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //设置侧边栏里面Menu点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                String categoryName = "干货大本营";
                //判断点击Menu，并打对应的Frgament，设置toolbar标题和侧边栏选中颜色
                if (id == R.id.nav_cover) {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {
                    Bundle bundle = new Bundle();
                    ContentFragment fragment = new ContentFragment();
                    switch (id) {
                        case R.id.nav_android:
                            categoryName = "Android";
                            break;
                        case R.id.nav_ios:
                            categoryName = "iOS";
                            break;
                        case R.id.nav_frontEnd:
                            categoryName = "前端";
                            break;
                        case R.id.nav_expand:
                            categoryName = "拓展资源";
                            break;
                        case R.id.nav_video:
                            categoryName = "休息视频";
                            break;
//                        case R.id.nav_girls:
//                            categoryName = "福利";
//                            break;
                    }
                    bundle.putString(ContentFragment.CATEGORYNAME, categoryName);
                    fragment.setArguments(bundle);
                    mFragmentManager = getFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.fl_category, fragment).addToBackStack(null).commit();
                }
                mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawer.closeDrawer(GravityCompat.START);
                mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
                mToolbar.setTitle(categoryName);
                setSupportActionBar(mToolbar);
                return true;
            }
        });
    }
}

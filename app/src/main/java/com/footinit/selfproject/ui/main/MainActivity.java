package com.footinit.selfproject.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.footinit.selfproject.BuildConfig;
import com.footinit.selfproject.R;
import com.footinit.selfproject.ui.base.BaseActivity;
import com.footinit.selfproject.ui.custom.RoundedImageView;
import com.footinit.selfproject.ui.login.LoginActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abhijit on 16-11-2017.
 */


/*
* TODO
* 1. Login with Google(use email) Oauth
* 2. Feed Activity
* 3. Refresh Icon
* 4. Pull to Refresh
* 5. Item On click- BlogDetailsActivity
* */

public class MainActivity extends BaseActivity implements MainMvpView {


    @Inject
    MainMvpPresenter<MainMvpView> presenter;

    @Inject
    MainPagerAdapter adapter;

    @BindView(R.id.tv_app_version)
    TextView tvAppVersion;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.main_view_pager)
    ViewPager pager;

    @BindView(R.id.main_tab_layout)
    TabLayout tabLayout;

    private TextView tvUserName, tvUserEmail;

    private RoundedImageView ivProfilePic;

    ActionBarDrawerToggle drawerToggle;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        setUp();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void updateAppVersion() {
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        tvAppVersion.setText(version);
    }

    @Override
    public void updateUserName(String name) {
        tvUserName.setText(name);
    }

    @Override
    public void updateUserEmail(String email) {
        tvUserEmail.setText(email);
    }

    @Override
    public void updateUserProfilePicture(String picPath) {
        //TODO
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this));
        finish();
    }

    @Override
    public void openFeedActivity() {
        //TODO
        showMessage("openFeedActivity()");
    }

    @Override
    public void closeNavigationDrawer() {
        drawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public void unlockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void lockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    protected void setUp() {
        setSupportActionBar(toolbar);

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        setUpNavMenu();
        presenter.onNavMenuCreated();
        setUpViewPager();
    }

    private void setUpViewPager() {
        adapter.setCount(2);

        pager.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.blog));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.open_source));

        pager.setOffscreenPageLimit(tabLayout.getTabCount());

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpNavMenu() {
        View headerLayout = navigationView.getHeaderView(0);

        tvUserName = headerLayout.findViewById(R.id.tv_name);
        tvUserEmail = headerLayout.findViewById(R.id.tv_email);
        ivProfilePic = (RoundedImageView) headerLayout.findViewById(R.id.iv_profile_picture);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);

                switch (item.getItemId()) {
                    case R.id.nav_item_feed:
                        presenter.onDrawerOptionFeedClicked();
                        return true;
                    case R.id.nav_item_logout:
                        presenter.onDrawerOptionLogoutClicked();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }

        switch (item.getItemId()) {
            case R.id.action_cut:
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}

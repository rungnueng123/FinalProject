package com.android.orc.ocrapplication.dashboard;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.adapter.PagerAdapter;
import com.android.orc.ocrapplication.callback.FragmentListener;
import com.android.orc.ocrapplication.camera.CameraActivity;
import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.login.LoginActivity;
import com.android.orc.ocrapplication.result.HomeResultItemActivity;
import com.facebook.AccessToken;


public class DashBoardActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, View.OnClickListener, FragmentListener {


    //This is our viewPager
    private ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    MenuItem prevMenuItem;
    FloatingActionButton floatingCameraButton;
    FavoriteFragment favoriteFragment;
    HomeFragment homeFragment;
    MapFragment mapFragment;
    //    Button logout_facebook;
    String guest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        guest = getIntent().getStringExtra("login_guest");
        initInstance();

    }


    private void initInstance() {

        //Login Facebook
        if (guest == null) {
            if (AccessToken.getCurrentAccessToken() == null) {
                goLoginScreen();
            }
        }

//        logout_facebook = findViewById(R.id.logout_facebook);

        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_favorite:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_home:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_map:
                                viewPager.setCurrentItem(2);
                        }
                        return false;
                    }
                });
        floatingCameraButton = findViewById(R.id.dashboard_camera);
        floatingCameraButton.setOnClickListener(this);

        viewPager.addOnPageChangeListener(this);
        setupViewPager(viewPager);

        bottomNavigationView.setSelectedItemId(R.id.action_home);

//        logout_facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v == logout_facebook){
//                    LoginManager.getInstance().logOut();
//                    goLoginScreen();
//                }
//            }
//        });

    }

    private void goLoginScreen() {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        favoriteFragment = new FavoriteFragment();
        homeFragment = new HomeFragment();
        mapFragment = new MapFragment();
        adapter.addFragment(favoriteFragment);
        adapter.addFragment(homeFragment);
        adapter.addFragment(mapFragment);
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        } else {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }

        bottomNavigationView.getMenu().getItem(position).setChecked(true);
        prevMenuItem = bottomNavigationView.getMenu().getItem(position);


    }

    @Override
    public void onPageScrollStateChanged(int state) {

//       viewPager.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                return true;
//            }
//        });
//

    }

    @Override
    public void onClick(View v) {
        if (v == floatingCameraButton) {
            Intent intent = new Intent(DashBoardActivity.this,
                    CameraActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onMenuItemClick(MenuDao dao) {
        Intent intent = new Intent(getBaseContext(), HomeResultItemActivity.class);

        intent.putExtra("dao", dao);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        DashBoardActivity.super.onBackPressed();
                    }
                }).create().show();
    }


}

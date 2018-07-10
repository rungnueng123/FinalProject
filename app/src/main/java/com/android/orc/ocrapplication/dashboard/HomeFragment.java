package com.android.orc.ocrapplication.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.adapter.HomeAdapter;
import com.android.orc.ocrapplication.callback.FragmentListener;
import com.android.orc.ocrapplication.callback.RecyclerViewClickListener;
import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.login.LoginActivity;
import com.android.orc.ocrapplication.manager.HttpManager;
import com.android.orc.ocrapplication.manager.MenuListManager;
import com.arlib.floatingsearchview.FloatingSearchView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by j.poobest on 9/24/2017 AD.
 */

public class HomeFragment extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    MenuListManager menuListManager;


    DrawerLayout mDrawer;

    public HomeFragment() {
        super();
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        menuListManager = new MenuListManager();

    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // set floatingView


        recyclerView = rootView.findViewById(R.id.recycler_view_dashboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerViewClickListener listener = (view, position) -> {

            MenuDao dao = menuListManager.getDao().get(position);
            FragmentListener fragmentListener = (FragmentListener) getActivity();
            fragmentListener.onMenuItemClick(dao);

        };

        adapter = new HomeAdapter(getContext(), listener);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadData();

    }

    public void loadData() {
        Call<List<MenuDao>> call = HttpManager.getInstance().getService().sortbyRating("rating", 30);
        call.enqueue(new Callback<List<MenuDao>>() {
            @Override
            public void onResponse(Call<List<MenuDao>> call, Response<List<MenuDao>> response) {

                if (response.isSuccessful()) {
                    List<MenuDao> dao = response.body();
                    //ดึง dao
                    menuListManager.setDao(dao);
                    adapter.setDao(dao);
                    adapter.notifyDataSetChanged();

                } else {
                    try {
                        Toast.makeText(getContext(),
                                response.errorBody().string(),
                                Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MenuDao>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                break;
            case 1:
                break;
        }

        return super.onContextItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }


    private void attachSearchViewActivityDrawer(FloatingSearchView mSearchView) {
        mSearchView.attachNavigationDrawerToMenuButton(mDrawer);
    }


    private void goLoginScreen() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        mDrawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();

        if (id == R.id.logout_facebook) {
            // Handle the camera action
        }
        return true;

    }

    @Override
    public void onClick(View v) {


    }
}

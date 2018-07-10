package com.android.orc.ocrapplication.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.adapter.FavoriteListAdapter;
import com.android.orc.ocrapplication.dao.FavoriteItemDao;
import com.android.orc.ocrapplication.dao.FavoriteListItem;
import com.android.orc.ocrapplication.description.DescriptionActivity;
import com.android.orc.ocrapplication.model.ItemClickCallback;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by j.poobest on 9/24/2017 AD.
 */

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<FavoriteListItem> listResult;
    private FavoriteListAdapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public FavoriteFragment() {
        super();
    }

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
//        args.putParcelable("objectFB",stringFacebook);
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
        View rootView = inflater.inflate(R.layout.fragment_favorite_dashboard, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        //set firebase recyclerview
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("favorite");

        listResult = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recycler_view_review);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        ItemClickCallback listener = (view, position) -> {
            Intent intent = new Intent(getActivity(), DescriptionActivity.class);
            FavoriteItemDao favoriteItemDao = new FavoriteItemDao();
            favoriteItemDao.nameThai = listResult.get(position).getNameThai();
            favoriteItemDao.description = listResult.get(position).getDescription();
            favoriteItemDao.ingredient = listResult.get(position).getIngredient();
            favoriteItemDao.imgUrl = listResult.get(position).getImgUrl();
            intent.putExtra("favoriteItemDao",favoriteItemDao);
            startActivity(intent);
        };

        updateList();
        adapter = new FavoriteListAdapter(getContext(),listResult,listener);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 0:
                break;
            case 1:
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void updateList() {

        Profile profile = Profile.getCurrentProfile();
        myRef.orderByChild("facebookName")
                .equalTo(constructWelcomeMessage(profile))
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listResult.removeAll(listResult);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FavoriteListItem list = snapshot.getValue(FavoriteListItem.class);
                        listResult.add(list);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String constructWelcomeMessage(Profile profile) {
        StringBuffer stringBuffer = new StringBuffer();
        if (profile != null) {
            stringBuffer.append(profile.getName());
        }
        return stringBuffer.toString();
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

}

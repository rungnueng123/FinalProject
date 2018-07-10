package com.android.orc.ocrapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.callback.RecyclerViewClickListener;
import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.holder.MenuItemHolder;
import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by j.poobest on 16/12/2017 AD.
 */

public class ResultListAdapter extends RecyclerView.Adapter<MenuItemHolder> {

    Context context;
    List<MenuDao> dao;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private RecyclerViewClickListener mListener;

    public ResultListAdapter(Context context, RecyclerViewClickListener listener) {
        this.context = context;
        this.mListener = listener;
    }

    public void setDao(List<MenuDao> dao) {
        this.dao = dao;
    }

    @Override
    public MenuItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);

//        int height = parent.getMeasuredHeight() / 3;
//        view.setMinimumHeight(height);

        return new MenuItemHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemHolder holder, int position) {
        MenuDao item = dao.get(position);
        holder.getMenuName().setText(item.getName());
        if (item.getQuantityRating() == null){
            holder.getMaterialRatingBar().setNumStars(0);
        }else {
            holder.getMaterialRatingBar().setNumStars(item.getRating().intValue());
        }

        holder.getMenuNameThai().setText(item.getNameThai());

        Glide.with(context)
                .load(item.getImgUrl())
                .into(holder.getMenuImage());

        holder.getStar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: check
                // status
                holder.getStar().setImageResource(R.drawable.ic_toggle_star_24);

                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("favorite");

                addFavoriteList(position);
            }
        });

    }

    private void addFavoriteList(int position) {
        Profile profile = Profile.getCurrentProfile();
        String id = myRef.push().getKey();
        myRef.child(dao.get(position).getName() + " " + constructWelcomeMessage(profile)).child("name").setValue(dao.get(position).getName());
        myRef.child(dao.get(position).getName() + " " + constructWelcomeMessage(profile)).child("nameThai").setValue(dao.get(position).getNameThai());
        myRef.child(dao.get(position).getName() + " " + constructWelcomeMessage(profile)).child("description").setValue(dao.get(position).getDescription());
        myRef.child(dao.get(position).getName() + " " + constructWelcomeMessage(profile)).child("ingredient").setValue(dao.get(position).getIngredient());
        myRef.child(dao.get(position).getName() + " " + constructWelcomeMessage(profile)).child("imgUrl").setValue(dao.get(position).getImgUrl());
        myRef.child(dao.get(position).getName() + " " + constructWelcomeMessage(profile)).child("facebookName").setValue(constructWelcomeMessage(profile));
        myRef.child(dao.get(position).getName() + " " + constructWelcomeMessage(profile)).child("star").setValue("true");
    }

    private String constructWelcomeMessage(Profile profile) {
        StringBuffer stringBuffer = new StringBuffer();
        if (profile != null) {
            stringBuffer.append(profile.getName());
        }
        return stringBuffer.toString();

    }

    @Override
    public int getItemCount() {

        if (dao == null) {
            return 0;
        }
        if (dao.size() == 0) {
            return 0;
        }
        return dao.size();
    }
}

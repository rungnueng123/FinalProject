package com.android.orc.ocrapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dao.FavoriteListItem;
import com.android.orc.ocrapplication.holder.FavoriteHolder;
import com.android.orc.ocrapplication.model.ItemClickCallback;
import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteHolder> {

    private ItemClickCallback mListener;
    private List<FavoriteListItem> list;
    Context context;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    String key;

    public FavoriteListAdapter(Context context, List<FavoriteListItem> list, ItemClickCallback listener) {
        this.context = context;
        this.list = list;
        this.mListener = listener;
    }


    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_menu_item, parent, false);
        return new FavoriteHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
        FavoriteListItem favoriteListItem = list.get(position);

        holder.getMenuName().setText(favoriteListItem.name);
        holder.getMenuNameThai().setText(favoriteListItem.nameThai);

        holder.getStar().setImageResource(R.drawable.ic_toggle_star_24);

        holder.getStar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getStar().setImageResource(R.drawable.ic_toggle_star_outline_24);
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("favorite");
                myRef.orderByChild("nameThai")
                        .equalTo(favoriteListItem.nameThai)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    key = childSnapshot.getKey();
                                    removeFirebase();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });

        Glide.with(context)
                .load(favoriteListItem.getImgUrl())
                .into(holder.getMenuImage());
    }

    private void removeFirebase() {

        Query query = myRef.child(key);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dataSnapshot.getRef().setValue(null);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

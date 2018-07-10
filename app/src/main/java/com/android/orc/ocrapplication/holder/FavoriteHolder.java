package com.android.orc.ocrapplication.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.model.ItemClickCallback;

public class FavoriteHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    private ItemClickCallback mListener;
    ImageView menuImage;
    TextView menuName;
    TextView menuNameThai;
    ImageView star;

    public TextView getMenuNameThai() {
        return menuNameThai;
    }

    public ItemClickCallback getmListener() {
        return mListener;
    }

    public ImageView getMenuImage() {
        return menuImage;
    }

    public TextView getMenuName() {
        return menuName;
    }

    public ImageView getStar() {
        return star;
    }

    public FavoriteHolder(View itemView, ItemClickCallback listener) {
        super(itemView);

        mListener = listener;
        itemView.setOnClickListener(this);

        menuImage = itemView.findViewById(R.id.menu_image);
        menuName = itemView.findViewById(R.id.menu_name);
        menuNameThai = itemView.findViewById(R.id.menu_item_namethai);
        star = itemView.findViewById(R.id.star);
    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v, getAdapterPosition());
    }
}

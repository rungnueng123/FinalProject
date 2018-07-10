package com.android.orc.ocrapplication.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.callback.RecyclerViewClickListener;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;


/**
 * Created by j.poobest on 19/3/2018 AD.
 */

public class MenuListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView menuImage;
    TextView menuName;
    ImageView star;
    TextView nameThai;
    MaterialRatingBar materialRatingBar;

    public MaterialRatingBar getMaterialRatingBar() {
        return materialRatingBar;
    }

    public TextView getNameThai() {
        return nameThai;
    }

    private RecyclerViewClickListener mListener;

    public ImageView getMenuImage() {
        return menuImage;
    }

    public TextView getMenuName() {
        return menuName;
    }

    public ImageView getStar(){
        return star;
    }

    public MenuListHolder(View itemView, RecyclerViewClickListener listener) {

        super(itemView);
        mListener = listener;
        menuImage = itemView.findViewById(R.id.menu_image);
        menuName = itemView.findViewById(R.id.menu_name);
        nameThai = itemView.findViewById(R.id.menu_item_namethai);
        star = itemView.findViewById(R.id.star);
        materialRatingBar = itemView.findViewById(R.id.menu_item_rating);
        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        mListener.onClick(view, getLayoutPosition());
    }
}

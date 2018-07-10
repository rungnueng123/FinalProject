package com.android.orc.ocrapplication.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.orc.ocrapplication.R;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;


public class ReviewHolder extends RecyclerView.ViewHolder {

    TextView tvUserName;
    TextView tvDate;
    MaterialRatingBar ratingBar;
    TextView tvComment;

    public TextView getTvUserName() {
        return tvUserName;
    }

    public TextView getTvDate() {
        return tvDate;
    }

    public MaterialRatingBar getRatingBar() {
        return ratingBar;
    }

    public TextView getTvComment() {
        return tvComment;
    }

    public ReviewHolder(View itemView) {
        super(itemView);
        tvUserName = itemView.findViewById(R.id.comment_item_name);
        tvDate = itemView.findViewById(R.id.comment_item_date);
        tvComment = itemView.findViewById(R.id.comment_item_text);
        ratingBar = itemView.findViewById(R.id.comment_item_rating);
    }
}



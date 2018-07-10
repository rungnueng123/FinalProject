package com.android.orc.ocrapplication.callback;

import com.android.orc.ocrapplication.dao.CommentDao;

public interface RatingListener {
    void onRating(CommentDao rating, String request);
}

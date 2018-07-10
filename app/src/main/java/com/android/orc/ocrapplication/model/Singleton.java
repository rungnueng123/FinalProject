package com.android.orc.ocrapplication.model;

import android.content.Context;

/**
 * Created by j.poobest on 9/20/2017 AD.
 */

public class Singleton {

    private static Singleton instance;

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    private Context mContext;

    private Singleton() {
        mContext = Contextor.getInstance().getContext();
    }
}

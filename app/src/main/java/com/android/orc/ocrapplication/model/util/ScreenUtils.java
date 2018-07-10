package com.android.orc.ocrapplication.model.util;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.android.orc.ocrapplication.model.Contextor;


/**
 * Created by j.poobest on 9/20/2017 AD.
 */

public class ScreenUtils {

    private static ScreenUtils instance;

    public static ScreenUtils getInstance() {
        if (instance == null) {
            instance = new ScreenUtils();
        }
        return instance;
    }

    private Context mContext;

    private ScreenUtils() {
        mContext = Contextor.getInstance().getContext();
    }


    public int getScreenWidth() {
        WindowManager wm = (WindowManager)
                mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public int getScreenHeight() {
        WindowManager wm = (WindowManager)
                mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

}

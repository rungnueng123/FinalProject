package com.android.orc.ocrapplication.manager;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;


import com.android.orc.ocrapplication.dao.MenuDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j.poobest on 20/3/2018 AD.
 */

public class MenuListManager {

    private Context mContext;
    private List<MenuDao> dao;
//    MenuItemDao daoSaveState;

    public MenuListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public List<MenuDao> getDao() {
        return dao;
    }

    public void setDao(List<MenuDao> dao) {
        this.dao = dao;
    }


    public Bundle onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("dao", (ArrayList<? extends Parcelable>) dao);
        return null;


    }

    public void onRestoreInstanceState(Bundle saveInstanceState) {
        dao = saveInstanceState.getParcelableArrayList("dao");
    }
}

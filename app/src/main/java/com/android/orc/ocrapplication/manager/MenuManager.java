package com.android.orc.ocrapplication.manager;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import com.android.orc.ocrapplication.dao.MenuDao;

import java.util.List;

public class MenuManager {

    private Context mContext;
    private List<MenuDao> dao;
//    MenuItemDao daoSaveState;

    public MenuManager() {
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
        bundle.putParcelable("dao", (Parcelable) dao);
        return null;


    }

    public void onRestoreInstanceState(Bundle saveInstanceState) {
        dao = saveInstanceState.getParcelable("dao");
    }
}

package com.android.orc.ocrapplication.description;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dao.FavoriteItemDao;
import com.android.orc.ocrapplication.dao.MenuDao;
import com.bumptech.glide.Glide;

/**
 * Created by RUNGNUENG on 9/10/2560.
 */

public class DescriptionFragment extends Fragment {

    ImageView imgMenu;
    TextView tvNameMenu;
    TextView tvDescription;
    TextView tvIngredient;

    MenuDao dao;
    FavoriteItemDao favoriteItemDao;

    public DescriptionFragment() {
        super();
    }

    public static DescriptionFragment newInstance(String dao) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        args.putString("dao", dao);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);

        favoriteItemDao = getActivity().getIntent().getParcelableExtra("favoriteItemDao");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_description, container, false);

        initInstances(rootView, savedInstanceState);
        return rootView;
    }


    private void init(Bundle savedInstanceState) {
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {

        imgMenu = rootView.findViewById(R.id.image_menu_description);
        tvNameMenu = rootView.findViewById(R.id.text_name_menu_description);
        tvDescription = rootView.findViewById(R.id.text_description_description);
        tvIngredient = rootView.findViewById(R.id.text_ingredient_menu_description);

        if (dao != null) {
            tvNameMenu.setText(dao.getName());
            tvDescription.setText(dao.getDescription());
            tvIngredient.setText(dao.getIngredient());

            Glide.with(DescriptionFragment.this)
                    .load(dao.getImgUrl())
                    .into(imgMenu);
        }else if (favoriteItemDao != null){
            tvNameMenu.setText(favoriteItemDao.nameThai);
            tvDescription.setText(favoriteItemDao.description);
            tvIngredient.setText(favoriteItemDao.ingredient);

            Glide.with(DescriptionFragment.this)
                    .load(favoriteItemDao.imgUrl)
                    .into(imgMenu);
        }
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }
}

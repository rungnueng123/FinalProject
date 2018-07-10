/**
 * Copyright 2017 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.orc.ocrapplication.dialogfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.adapter.ReviewListAdapter;
import com.android.orc.ocrapplication.callback.CommentListener;
import com.android.orc.ocrapplication.callback.RatingListener;
import com.android.orc.ocrapplication.dao.CommentDao;
import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.manager.CommentManager;
import com.android.orc.ocrapplication.manager.HttpManager;


import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Dialog Fragment containing rating form.
 */
public class CommentDialogFragment extends DialogFragment implements View.OnClickListener {

    MaterialRatingBar mRatingBar;
    EditText mRatingText;
    Button btnSubmit;
    Button btnCancel;
    CommentManager commentManager;

    String nameThai;
    CommentListener commentListener;

    public static CommentDialogFragment newInstance(String nameThai) {
        CommentDialogFragment commentDialogFragment = new CommentDialogFragment();

        Bundle args = new Bundle();
        args.putString("nameThai", nameThai);
        commentDialogFragment.setArguments(args);

        return commentDialogFragment;

    }

    public static final String TAG = "RatingDialog";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nameThai = getArguments().getString("nameThai");
        Toast.makeText(getContext(), nameThai, Toast.LENGTH_LONG).show();
        init();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_rating, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void init() {
        commentManager = new CommentManager();
    }

    private void initInstances(View rootView) {
        mRatingBar = rootView.findViewById(R.id.menu_form_rating);
        mRatingText = rootView.findViewById(R.id.menu_form_text);
        btnSubmit = rootView.findViewById(R.id.menu_form_button);
        btnCancel = rootView.findViewById(R.id.menu_form_cancel);


        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof CommentListener) {
            commentListener = (CommentListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

    }


    @Override
    public void onClick(View v) {
        if (v == btnSubmit) {
            Fragment fragment = getParentFragment();
//            Toast.makeText(getContext(), "Please Comment this menu", Toast.LENGTH_LONG).show();

            CommentDao comment = new CommentDao("Guest",
                    mRatingBar.getRating(),
                    mRatingText.getText().toString());

            if (comment.getRating() == null || comment.getComment() == null) {
                Toast.makeText(getContext(), "PLEASE add rating and comment", Toast.LENGTH_LONG).show();
            }else {
                commentListener = (CommentListener) fragment;
                commentListener.onSubmitComment(nameThai, comment);
                dismiss();

            }

        } else if (v == btnCancel) {
            dismiss();
        }


    }
}




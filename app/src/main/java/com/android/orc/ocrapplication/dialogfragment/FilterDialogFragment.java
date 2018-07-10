package com.android.orc.ocrapplication.dialogfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.android.orc.ocrapplication.R;

public class FilterDialogFragment extends DialogFragment implements View.OnClickListener {


    Spinner mCategorySpinner;
    Spinner mCitySpinner;
    Spinner mSortSpinner;
    Spinner mPriceSpinner;
    Button btnSearch;
    Button btnCancel;


    public static final String TAG = "FilterDialog";


    interface FilterListener {

        void onFilter(Filters filters);

    }

    private View rootView;


    private FilterListener mFilterListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_filters, container, false);

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View mRootView) {
        mCategorySpinner = rootView.findViewById(R.id.spinner_category);
        mSortSpinner = rootView.findViewById(R.id.spinner_sort);
        btnSearch = rootView.findViewById(R.id.button_search);
        btnCancel = rootView.findViewById(R.id.button_cancel);

        btnSearch.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btnSearch) {
            onSearchClicked();
        } else if (v == btnCancel) {
            onCancelClicked();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FilterListener) {
            mFilterListener = (FilterListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void onSearchClicked() {
        if (mFilterListener != null) {
            mFilterListener.onFilter(getFilters());
        }

        dismiss();
    }

    public void onCancelClicked() {
        dismiss();
    }

    @Nullable
    private String getSelectedCategory() {
        String selected = (String) mCategorySpinner.getSelectedItem();
        if (getString(R.string.value_any_category).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }




    public void resetFilters() {
        if (rootView != null) {
            mCategorySpinner.setSelection(0);
            mCitySpinner.setSelection(0);
            mPriceSpinner.setSelection(0);
            mSortSpinner.setSelection(0);
        }
    }

    public Filters getFilters() {
        Filters filters = new Filters();

        if (rootView != null) {
        }
        return filters;
    }

}

package com.android.orc.ocrapplication.result.menuocr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.callback.ResultOcrFragmentListener;
import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.result.ocrresult.OrcDescriptionActivity;

public class ResultOcrActivity extends AppCompatActivity implements ResultOcrFragmentListener {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_ocr);

        toolbar = findViewById(R.id.result_ocr_toolbar);
        toolbar.setTitle("Result Menu");
        setSupportActionBar(toolbar);

        String request = getIntent().getStringExtra("stringRequest");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_result_ocr, ResultOcrFragment.newInstance(request))
                    .commit();
        }
    }

    @Override
    public void onMenuItemClick(MenuDao dao) {
        Intent intent = new Intent(getBaseContext(), OrcDescriptionActivity.class);

        intent.putExtra("dao", dao);
        startActivity(intent);
    }
}

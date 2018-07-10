package com.android.orc.ocrapplication.description;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.orc.ocrapplication.R;


public class DescriptionActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        toolbar = findViewById(R.id.description_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String dao = intent.getStringExtra("dao");
        Toast.makeText(this, "DES activity", Toast.LENGTH_LONG).show();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_description, DescriptionFragment.newInstance(dao))
                    .commit();
        }
    }

}

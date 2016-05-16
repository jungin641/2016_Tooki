package com.tendom.tooki.settingpage;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.tendom.tooki.R;

/**
 * Created by JUNGIN on 2015-07-10.
 */
public class AppInfoActivity_appinfo extends AppCompatActivity {
        Button btn_back;
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_license_appinfo);
                final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
             //   setSupportActionBar(toolbar);
               // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                CollapsingToolbarLayout collapsingToolbar =
                        (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                btn_back = (Button)findViewById(R.id.btn_back);
                btn_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                finish();
                        }
                });


        }

}

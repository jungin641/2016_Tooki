package com.tendom.tooki.settingpage;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tendom.tooki.R;


/**
 * Created by JUNGIN on 2015-07-10.
 */

public class AppInfoActivity_Delete3 extends AppCompatActivity {
    TextView textview_exit;
    ImageView btn_back;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_delete3);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //   setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        textview_exit = (TextView)findViewById(R.id.textview_exit);
        textview_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

    }


}
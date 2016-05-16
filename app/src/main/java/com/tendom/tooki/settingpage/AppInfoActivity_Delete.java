package com.tendom.tooki.settingpage;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.tendom.tooki.R;


/**
 * Created by JUNGIN on 2015-07-10.
 */
public class AppInfoActivity_Delete extends AppCompatActivity {
    Button button_delete_next;
    ImageView btn_back;
    RadioGroup radioGroup_delete;
    int delete_reason=0;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_delete);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //   setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        radioGroup_delete = (RadioGroup) findViewById(R.id.radiogroup_delete);
        radioGroup_delete.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_delete_1:
                        delete_reason=1;
                        break;
                    case R.id.radio_delete_2:
                        delete_reason=2;
                        break;
                    case R.id.radio_delete_3:
                        delete_reason=3;
                        break;
                    case R.id.radio_delete_4:
                        delete_reason=4;
                        break;
                    case R.id.radio_delete_5:
                        delete_reason=5;
                        break;
                }
            }
        });
        btn_back = (ImageView)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_delete_next = (Button)findViewById(R.id.button_delete_next);
        button_delete_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delete_reason!=0) {
                    Intent intent = new Intent(getApplicationContext(), AppInfoActivity_Delete2.class);
                    intent.putExtra("delete_reason", delete_reason);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }


}
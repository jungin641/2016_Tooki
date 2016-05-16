package com.tendom.tooki.settingpage;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tendom.tooki.R;


/**
 * Created by JUNGIN on 2015-07-10.
 */
public class AppInfoActivity_inventors extends AppCompatActivity {
       // LinearLayout animation_linear;
        ImageView btn_back;

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_setting_inventors);
                final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                //   setSupportActionBar(toolbar);
                // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                CollapsingToolbarLayout collapsingToolbar =
                        (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

                btn_back = (ImageView)findViewById(R.id.btn_back);
                btn_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                finish();
                        }
                });
               /*
                animation_linear =  (LinearLayout)findViewById(R.id.inventors_linear);


                Animation anim = AnimationUtils.loadAnimation
                        (getApplicationContext(), // 현재화면의 제어권자
                                R.anim.translate_anim);   // 에니메이션 설정 파일
                animation_linear.startAnimation(anim);
                */

        }


}
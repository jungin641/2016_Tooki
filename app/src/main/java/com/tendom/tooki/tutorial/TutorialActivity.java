package com.tendom.tooki.tutorial;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.tendom.tooki.R;


/**
 * Created by OZ5 on 2015-11-22.
 */
public class TutorialActivity extends AppCompatActivity {
    private ViewPager mPager;

    @Override
    public void onCreate(Bundle saverdInstanceState)
    {
        super.onCreate(saverdInstanceState);
        setContentView(R.layout.tutorial_activity);
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(new PagerAdapterClass(getApplicationContext()));

/*

        ImageView btn_close1,btn_close2;
        //정인 : 튜토리얼 종료하고 어플 시작버튼
        btn_close1 = (ImageView)findViewById(R.id.btn_tutorial_1);
        btn_close2 = (ImageView)findViewById(R.id.btn_tutorial_2);

        btn_close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
   /*
        //정인 : 서비스이용약관 보기
        license_1 = (LinearLayout)findViewById(R.id.license_1);
        license_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AppInfoActivity_service.class);
                startActivity(intent);
                finish();
            }
        });
        //정인 : 위치정보이용약관 보기
        license_2 = (LinearLayout)findViewById(R.id.license_2);
        license_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AppInfoActivity_gps.class);
                startActivity(intent);
                finish();
            }
        });
        //정인 : 개인정보 이용약관 보기
        license_3 = (LinearLayout)findViewById(R.id.license_3);
        license_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AppInfoActivity_info.class);
                startActivity(intent);
                finish();
            }
        });

        tutorial_checkbox_1 = (CheckBox)findViewById(R.id.tutorial_checkbox_1);
        tutorial_checkbox_2 = (CheckBox)findViewById(R.id.tutorial_checkbox_2);
        tutorial_checkbox_3 = (CheckBox)findViewById(R.id.tutorial_checkbox_3);

        //정인 : 만약 개인정보 서비스 이용약관 체크되었으면
        if(tutorial_checkbox_1.isChecked()&&tutorial_checkbox_3.isChecked()){
            //버튼 활성화
            Button_StartApp.setClickable(true);
        }
        //아니면
        else
        {
            //버튼 비활성화
            Button_StartApp.setClickable(false);
        }

*/
    }
}

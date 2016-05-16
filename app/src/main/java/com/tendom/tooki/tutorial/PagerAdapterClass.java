package com.tendom.tooki.tutorial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tendom.tooki.R;
import com.tendom.tooki.mainpage.MainActivity;
import com.tendom.tooki.network.ApplicationController;
import com.tendom.tooki.network.ServerInterface;
import com.tendom.tooki.settingpage.AppInfoActivity_gps;
import com.tendom.tooki.settingpage.AppInfoActivity_info;
import com.tendom.tooki.settingpage.AppInfoActivity_service;
import com.tendom.tooki.uuid.MyUUID;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by OZ5 on 2015-11-22.
 */
public class PagerAdapterClass extends PagerAdapter {
    Button Button_StartApp;
    LinearLayout license_1, license_2,license_3;
    CheckBox tutorial_checkbox_1,tutorial_checkbox_2,tutorial_checkbox_3;

    ImageView btn_close1,btn_close2;

    private Context context;

    private ServerInterface api;

        private LayoutInflater mInflater;

        public PagerAdapterClass(Context c){
            super();
            mInflater = LayoutInflater.from(c);
            context = c;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(View pager, int position) {

            api = ApplicationController.getInstance().getServerInterface();
            View v = mInflater.inflate(R.layout.tutorial_page_1, null);
            if(position==0){
                v = mInflater.inflate(R.layout.tutorial_page_1, null);

/*
                //정인 : 튜토리얼 종료하고 어플 시작버튼
                btn_close1 = (ImageView)v.findViewById(R.id.btn_tutorial_1);
                btn_close1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent().setClass(context,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

*/
            }
            else if(position==1){

                v = mInflater.inflate(R.layout.tutorial_page_2, null);
                /*
                btn_close2 = (ImageView)v.findViewById(R.id.btn_tutorial_2);
                btn_close2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent().setClass(context,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
*/
            }else{
                v = mInflater.inflate(R.layout.tutorial_page_3, null);

                //정인 : 튜토리얼 종료하고 어플 시작버튼
                Button_StartApp = (Button)v.findViewById(R.id.button_startApp);
                Button_StartApp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String _uuid  = new MyUUID().getUUID(context);

                        api.JoinUUID(_uuid, new Callback<MyUUID>() {
                            @Override
                            public void success(MyUUID myUUID, Response response) {
                                Log.v("uuid", myUUID.uuid);
                                Intent intent = new Intent().setClass(context,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.v("uuid", "fail");
                                ((Activity) context).finish();
                            }
                        });
                    }
                });

                //정인 : 서비스이용약관 보기
                license_1 = (LinearLayout)v.findViewById(R.id.license_1);
                license_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent().setClass(context,AppInfoActivity_service.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                //정인 : 위치정보이용약관 보기
                license_2 = (LinearLayout)v.findViewById(R.id.license_2);
                license_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent().setClass(context,AppInfoActivity_gps.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                //정인 : 개인정보 이용약관 보기
                license_3 = (LinearLayout)v.findViewById(R.id.license_3);
                license_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent().setClass(context,AppInfoActivity_info.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

                tutorial_checkbox_1 = (CheckBox)v.findViewById(R.id.tutorial_checkbox_1);
                tutorial_checkbox_2 = (CheckBox)v.findViewById(R.id.tutorial_checkbox_2);
                tutorial_checkbox_3 = (CheckBox)v.findViewById(R.id.tutorial_checkbox_3);


                tutorial_checkbox_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(tutorial_checkbox_1.isChecked()&&tutorial_checkbox_2.isChecked()&&tutorial_checkbox_3.isChecked()){
                            Button_StartApp.setClickable(true);
                        }
                        else {
                            Button_StartApp.setClickable(false);
                        }
                    }
                });
                tutorial_checkbox_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(tutorial_checkbox_1.isChecked()&&tutorial_checkbox_2.isChecked()&&tutorial_checkbox_3.isChecked()){
                            Button_StartApp.setClickable(true);
                        }
                        else {
                            Button_StartApp.setClickable(false);
                        }
                    }
                });
                tutorial_checkbox_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(tutorial_checkbox_1.isChecked()&&tutorial_checkbox_2.isChecked()&&tutorial_checkbox_3.isChecked()){
                            Button_StartApp.setClickable(true);
                        }
                        else {
                            Button_StartApp.setClickable(false);
                        }
                    }
                });
            }

            ((ViewPager)pager).addView(v, 0);

            return v;
        }

        @Override
        public void destroyItem(View pager, int position, Object view) {
            ((ViewPager)pager).removeView((View)view);
        }

        @Override
        public boolean isViewFromObject(View pager, Object obj) {
            return pager == obj;
        }

        @Override public void restoreState(Parcelable arg0, ClassLoader arg1) {}
        @Override public Parcelable saveState() { return null; }
        @Override public void startUpdate(View arg0) {}
        @Override public void finishUpdate(View arg0) {}


    }


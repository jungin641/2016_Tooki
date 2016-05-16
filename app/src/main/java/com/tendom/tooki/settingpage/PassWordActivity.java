package com.tendom.tooki.settingpage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tendom.tooki.R;


public class PassWordActivity extends Activity implements View.OnClickListener {
    TextView PWButton1, PWButton2, PWButton3, PWButton4,PWButton5, PWButton6, PWButton7, PWButton8, PWButton9,PWButton0;
    ImageView Erasebtn,exitBtn;
    ImageView pass_image1,pass_image2,pass_image3,pass_image4;

    int count=0;
    StringBuffer password = new StringBuffer("");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_word);

        PWButton1 = (TextView)findViewById(R.id.PWButton1);  PWButton2 = (TextView)findViewById(R.id.PWButton2);
        PWButton3 = (TextView)findViewById(R.id.PWButton3);  PWButton4 = (TextView)findViewById(R.id.PWButton4);
        PWButton5 = (TextView)findViewById(R.id.PWButton5);  PWButton6 = (TextView)findViewById(R.id.PWButton6);
        PWButton7 = (TextView)findViewById(R.id.PWButton7);  PWButton8 = (TextView)findViewById(R.id.PWButton8);
        PWButton9 = (TextView)findViewById(R.id.PWButton9);  PWButton0 = (TextView)findViewById(R.id.PWButton0);
        Erasebtn = (ImageView)findViewById(R.id.Erasebtn); exitBtn = (ImageView)findViewById(R.id.exitBtn);
       pass_image1=(ImageView)findViewById(R.id.pass_image1); pass_image2=(ImageView)findViewById(R.id.pass_image2);
       pass_image3=(ImageView)findViewById(R.id.pass_image3); pass_image4=(ImageView)findViewById(R.id.pass_image4);

        PWButton1.setOnClickListener(this); PWButton2.setOnClickListener(this);
        PWButton3.setOnClickListener(this); PWButton4.setOnClickListener(this);
        PWButton5.setOnClickListener(this); PWButton6.setOnClickListener(this);
        PWButton7.setOnClickListener(this); PWButton8.setOnClickListener(this);
        PWButton9.setOnClickListener(this); PWButton0.setOnClickListener(this);
        Erasebtn.setOnClickListener(this);   exitBtn.setOnClickListener(this);


    }

    public void onClick(View v){


        if(v == PWButton1){   password.append("1");  count++; }
        else if(v == PWButton2){   password.append("2");  count++; }  else if(v == PWButton3){   password.append("3");  count++; }
        else if(v == PWButton4){   password.append("4");  count++; }  else if(v == PWButton5){   password.append("5");  count++; }
        else if(v == PWButton6){   password.append("6");  count++; }  else if(v == PWButton7){   password.append("7");  count++; }
        else if(v == PWButton8){   password.append("8");  count++; }  else if(v == PWButton9){   password.append("9");  count++; }
        else if(v == PWButton0){   password.append("0");  count++; }

        else if(v == Erasebtn){ password.deleteCharAt(count-1); count--;}

        if(v==exitBtn)
            startActivity(new Intent(PassWordActivity.this, SettingActivity.class));

        //count만큼 imageview 바꿔주기
        if(count == 1)
            pass_image1.setImageResource(R.drawable.password_circle);
        if(count == 2 )  pass_image2.setImageResource(R.drawable.password_circle);
        if(count == 3)  pass_image3.setImageResource(R.drawable.password_circle);
        if(count == 4 ) {
            pass_image4.setImageResource(R.drawable.password_circle);
     //  count가 4가 되어서 비밀번호 4자리가 입력되면 설정창으로 다시 돌아가기
            finish();
        }

    }

}

package com.tendom.tooki.settingpage;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tendom.tooki.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by JUNGIN on 2015-07-10.
 */
public class AppInfoActivity_info extends AppCompatActivity {
        Button btn_back;
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_license_info);
                final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                btn_back = (Button)findViewById(R.id.btn_back);
                btn_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                finish();
                        }
                });

                TextView helloTxt = (TextView) findViewById(R.id.textview_license_info);
                helloTxt.setText(readTxt());

                helloTxt.setMovementMethod(new ScrollingMovementMethod());
        }


        ;

        private String readTxt() {
                String data = null;
                InputStream inputStream = getResources().openRawResource(R.raw.license_info);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                int i;
                try {
                        i = inputStream.read();
                        while (i != -1) {
                                byteArrayOutputStream.write(i);
                                i = inputStream.read();
                        }

                        data = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
                        inputStream.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return data;
        }
}
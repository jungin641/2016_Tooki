package com.tendom.tooki.settingpage;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tendom.tooki.R;
import com.tendom.tooki.commentpage.Question;
import com.tendom.tooki.network.ApplicationController;
import com.tendom.tooki.network.ServerInterface;
import com.tendom.tooki.uuid.MyUUID;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by JUNGIN on 2015-07-10.
 */
public class AppInfoActivity_Delete2 extends AppCompatActivity {
    Button button_delete;
    ImageView btn_back;
    ServerInterface api;
    String _uuid;
    Question question;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_delete2);
        api = ApplicationController.getInstance().getServerInterface();
        _uuid  = new MyUUID().getUUID(getApplicationContext());
        question = new Question();
        Intent m_intent = new Intent(this.getIntent());
        final int delete_reason = m_intent.getIntExtra("delete_reason", 0);
        question.setUuid(delete_reason+"");
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
        button_delete = (Button)findViewById(R.id.button_delete_delete);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.OutUUID(_uuid, question, new Callback<Question>() {
                    @Override
                    public void success(Question question, Response response) {
                        Log.v("signout", question.getUuid());
                        Intent intent = new Intent(getApplicationContext(), AppInfoActivity_Delete3.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.v("signout", String.valueOf(error));
                    }
                });
            }
        });

    }


}
package com.tendom.tooki.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tendom.tooki.mainpage.MainActivity;
import com.tendom.tooki.network.ApplicationController;
import com.tendom.tooki.network.ServerInterface;
import com.tendom.tooki.tutorial.TutorialActivity;
import com.tendom.tooki.uuid.MyUUID;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Woong on 2015-07-01.
 */
public class SplashActivity extends Activity {

    String _uuid;
    private ServerInterface api;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _uuid  = new MyUUID().getUUID(getApplicationContext());
        api = ApplicationController.getInstance().getServerInterface();

        api.check_uuid(_uuid, new Callback<MyUUID>() {
            @Override
            public void success(MyUUID myUUID, Response response) {
                if (myUUID.uuid.equals("no")) {
                    Intent intent = new Intent(getApplicationContext(),TutorialActivity.class);
                    intent.putExtra("uuid", _uuid);
                    startActivity(intent);
                    finish();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                finish();
            }
        });
    }
}

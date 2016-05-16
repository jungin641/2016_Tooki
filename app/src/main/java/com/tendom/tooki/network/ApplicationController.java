package com.tendom.tooki.network;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class ApplicationController extends Application {

    private static ApplicationController instance;
    public static ApplicationController getInstance() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationController.instance = this;
    }

    private ServerInterface api;
    public ServerInterface getServerInterface() {
        api=buildServerInterface();
        return api;
    }
    private String endpoint;

    public ServerInterface buildServerInterface() {
        synchronized (ApplicationController.class) {
            if (api == null) {
                endpoint = String.format("http://175.158.15.101:3000");

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
                RestAdapter.Builder builder = new RestAdapter.Builder();
                builder.setConverter(new GsonConverter(gson));
                builder.setEndpoint(endpoint);

                RestAdapter adapter = builder.build();
                api = adapter.create(ServerInterface.class);
            }
        }
        return api;
    }
}

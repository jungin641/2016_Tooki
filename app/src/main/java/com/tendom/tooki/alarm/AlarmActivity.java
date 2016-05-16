package com.tendom.tooki.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.tendom.tooki.R;
import com.tendom.tooki.commentpage.CommentActivity;
import com.tendom.tooki.network.ApplicationController;
import com.tendom.tooki.network.ServerInterface;
import com.tendom.tooki.uuid.MyUUID;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by OZ5 on 2016-01-03.
 */
public class AlarmActivity extends AppCompatActivity {
    Button btn_back;
    ListView mlistView;
    AlarmCustomAdapter mAdapter;
    String uuid;



    public ArrayList<AlarmContent> cList = new ArrayList<AlarmContent>();


    final ArrayList<AlarmContent> abList = new ArrayList<AlarmContent>();
    private ServerInterface api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        uuid = new MyUUID().getUUID(getApplicationContext());
        mlistView = (ListView) findViewById(R.id.alarm_listView);

        //final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        api = ApplicationController.getInstance().getServerInterface();

        api.View_Alarm(uuid, new Callback<ArrayList<AlarmContent>>() {
            @Override
            public void success(ArrayList<AlarmContent> alarmContents, Response response) {
                cList = alarmContents;
                mAdapter = new AlarmCustomAdapter(getApplicationContext(), R.layout.alarm_row, cList);
                mAdapter.setSource(alarmContents);
                mlistView.setAdapter(mAdapter);
            }
            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(findViewById(R.id.alarm_listView), "나의 활동 내역이 없습니다.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                intent.putExtra("card_id", mAdapter.getItem(position).getId_card());
                //intent.putExtra("card_answer", mAdapter.getItem(position).getYes_NO());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home :
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}

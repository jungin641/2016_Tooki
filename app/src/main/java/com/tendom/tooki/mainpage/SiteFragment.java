package com.tendom.tooki.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tendom.tooki.GPS.GpsInfo;
import com.tendom.tooki.R;
import com.tendom.tooki.commentpage.CommentActivity;
import com.tendom.tooki.commentpage.Question;
import com.tendom.tooki.network.ApplicationController;
import com.tendom.tooki.network.ServerInterface;
import com.tendom.tooki.uuid.MyUUID;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Woong on 2015-11-08.
 */
public class SiteFragment extends Fragment {



    @Nullable
    ListView mlistView;
    CustomAdapter mAdapter;
    ArrayList<Question> abList = new ArrayList<Question>();
    private GpsInfo gps;
    private ServerInterface api;

    double lat;
    double lng;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_list, container, false);

        //TODO: 서버 연결해서 리스트 요청


        gps = new GpsInfo(getContext());

        if(gps.isGetLocation())
        {
            lat = gps.getLatitude();
            lng = gps.getLongitude();
        }
        else gps.showSettingsAlert();

        Toast.makeText(getActivity(),"위도 : " + lat + "경도 :" + lng,Toast.LENGTH_LONG  ).show();
        Log.v("woong - 위도",String.valueOf(lat));
            Log.v("woong - 경도",String.valueOf(lng));



        api = ApplicationController.getInstance().getServerInterface();

        api.Call_Site_Question(lat, lng, new Callback<ArrayList<Question>>() {
            @Override
            public void success(ArrayList<Question> questions, Response response) {

                mAdapter.setSource(questions);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


        mlistView = (ListView) view.findViewById(R.id.fragment_view);
        mAdapter = new CustomAdapter(getActivity(), R.layout.custom_item, abList);
        mlistView.setAdapter(mAdapter);

        MyUUID myUUID = new MyUUID();
        final String uuid = myUUID.getUUID(getActivity());

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("card_id", mAdapter.getItem(position).getId_card());
                intent.putExtra("card_answer", mAdapter.getItem(position).getYes_NO());
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}

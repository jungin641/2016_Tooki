package com.tendom.tooki.logpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
 * Created by Woong on 2015-07-10.
 */
public class MyQuestionFragment extends Fragment {
    @Nullable
    ListView mlistView;
    CustomAdapter_Log mAdapter;
    String _uuid;

    final ArrayList<Question> abList = new ArrayList<Question>();
    private ServerInterface api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        MyUUID myUUID = new MyUUID();
        _uuid = myUUID.getUUID(getActivity());

        api = ApplicationController.getInstance().getServerInterface();

        api.Call_My_Question(_uuid, new Callback<ArrayList<Question>>() {
            @Override
            public void success(ArrayList<Question> question, Response response) {
                mAdapter.setSource(question);
            }
            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(view, "글이 없습니다.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        mlistView = (ListView) view.findViewById(R.id.fragment_view);
        mAdapter = new CustomAdapter_Log(getActivity(), 0, abList);
        mlistView.setAdapter(mAdapter);

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
}

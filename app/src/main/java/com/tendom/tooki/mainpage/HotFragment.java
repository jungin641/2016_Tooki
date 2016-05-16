/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tendom.tooki.mainpage;

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

public class HotFragment extends Fragment {
    @Nullable
    ListView mlistView;
    CustomAdapter mAdapter;
    final ArrayList<Question> abList = new ArrayList<Question>();

    private ServerInterface api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        MyUUID myUUID = new MyUUID();
        final String uuid = myUUID.getUUID(getActivity());

        api = ApplicationController.getInstance().getServerInterface();
        api.Call_Hot_Question(uuid, new Callback<ArrayList<Question>>() {
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
        mAdapter = new CustomAdapter(getActivity(), R.layout.custom_item, abList);
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

        ((MainActivity)getActivity()).setFragmentRefreshListener_Hot(new MainActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                api.Call_Hot_Question(uuid, new Callback<ArrayList<Question>>() {
                    @Override
                    public void success(ArrayList<Question> question, Response response) {
                        mAdapter.setSource(question);
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Snackbar.make(view, "글이 없습니다.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    }
                });
            }
        });

        return view;
    }
}

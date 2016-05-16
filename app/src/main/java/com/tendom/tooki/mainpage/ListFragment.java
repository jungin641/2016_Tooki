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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daimajia.swipe.util.Attributes;
import com.tendom.tooki.R;
import com.tendom.tooki.commentpage.CommentActivity;
import com.tendom.tooki.commentpage.Question;
import com.tendom.tooki.network.ApplicationController;
import com.tendom.tooki.network.ServerInterface;
import com.tendom.tooki.uuid.MyUUID;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ListFragment extends Fragment {
    @Nullable
    ListView mlistView; //질문카드 띄울 리스트뷰
    CustomAdapter mAdapter; //질문카드 붙이는 어댑터
    ArrayList<Question> abList = new ArrayList<Question>(); //질문카드 가져올 ArrayList

    private ServerInterface api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //listview 띄우는 xml로 뷰를 inflate
        final View view = inflater.inflate(R.layout.fragment_list, container, false);

        //UUID가져옴
        MyUUID myUUID = new MyUUID();
        final String uuid = myUUID.getUUID(getActivity());

        //서버인터페이스에서 글 가져옴
        api = ApplicationController.getInstance().getServerInterface();
        api.Call_latest_Question(uuid, new Callback<ArrayList<Question>>() {
            @Override
            public void success(ArrayList<Question> question, Response response) {
                //어댑터에 붙임
                mAdapter.setSource(question);
            }
            @Override
            public void failure(RetrofitError error) {
                Log.v("api", String.valueOf(error));
                Snackbar.make(view, "글이 없습니다.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });
        //리스트뷰 생성
        mlistView = (ListView) view.findViewById(R.id.fragment_view);
        // 어댑터 생성
        //ListviewAdapter로 바꿔주고
        mAdapter = new CustomAdapter(getActivity(), R.layout.before_item_change, abList);
        //리스트뷰에 어댑터 내용들 넣음
        mlistView.setAdapter(mAdapter);
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("온클릭", "돼라");
                Intent intent = new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("card_id", mAdapter.getItem(position).getId_card());
                intent.putExtra("card_answer", mAdapter.getItem(position).getYes_NO());
                startActivity(intent);
            }
        });

    // 플로팅버튼 눌렀을 때 동작할 refresh listener
        ((MainActivity)getActivity()).setFragmentRefreshListener_List(new MainActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                api.Call_latest_Question(uuid, new Callback<ArrayList<Question>>() {
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

        ((MainActivity)getActivity()).setFragmentRefreshListener_new(new MainActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                api.Call_latest_Question(uuid, new Callback<ArrayList<Question>>() {
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

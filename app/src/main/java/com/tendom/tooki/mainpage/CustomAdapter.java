package com.tendom.tooki.mainpage;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.tendom.tooki.R;
import com.tendom.tooki.commentpage.Question;
import com.tendom.tooki.network.ApplicationController;
import com.tendom.tooki.network.ServerInterface;
import com.tendom.tooki.uuid.MyUUID;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Woong on 2015-07-08.
 */
public class CustomAdapter extends ArrayAdapter<Question> {

    private static final int TYPE_YES = 0;
    private static final int TYPE_NO = 1;
    private static final int TYPE_NONE = 2;

    private static SwipeLayout.DragEdge DefaultDragEdge = SwipeLayout.DragEdge.Bottom;
    public ArrayList<Question> items;
    int resource;
    Context _context;
    private ServerInterface api;

    public CustomAdapter(Context context, int resource, ArrayList<Question> items) {
        super(context, resource, items);
        _context = context;
        this.resource = resource;
        this.items = items;
    }
    public CustomAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void setSource(ArrayList<Question> questions) {
        this.items = questions;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Question question = items.get(position);
        if(question.getAnswer()=="yes"){
            return TYPE_YES;
        }
        else if(question.getAnswer()=="no"){
            return TYPE_NO;
        }
        else{
            return TYPE_NONE;
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Question getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getSwipeLayoutResourceId(int position) {
        return R.id.container;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Question question = items.get(position);
        MyUUID myUUID = new MyUUID();
        final String uuid = myUUID.getUUID(getContext());
        question.setUuid(uuid);

        api = ApplicationController.getInstance().getServerInterface();

        int rowtype = getItemViewType(position);

        switch(rowtype){
            case TYPE_YES:
                convertView = LayoutInflater.from(_context).inflate(R.layout.yes_custom_item, null);

                LinearLayout yesLinearLayout = (LinearLayout)convertView.findViewById(R.id.yes_item_layout);

                TextView questionCard_y_yes = (TextView) convertView.findViewById(R.id.questionCard);


                TextView NumOfYes_y_yes = (TextView) convertView.findViewById(R.id.NumOfYes);
                TextView NumOfNo_y_yes = (TextView) convertView.findViewById(R.id.NumOfNo);
                TextView NumOfComment_y_yes = (TextView) convertView.findViewById(R.id.NumOfComment);

                questionCard_y_yes.setText(question.getContect());

                NumOfYes_y_yes.setText((question.getNum_Yes()+1)+"");

                NumOfNo_y_yes.setText((question.getNum_No())+"");
                NumOfComment_y_yes.setText(question.getNum_Comment()+"");


                break;

            case TYPE_NO:
                convertView = LayoutInflater.from(_context).inflate(R.layout.no_custom_item, null);

                LinearLayout noLinearLayout = (LinearLayout)convertView.findViewById(R.id.no_item_layout);


                //텍스트 값 지정
                TextView questionCard_n_no = (TextView) convertView.findViewById(R.id.questionCard);
                TextView NumOfYes_n_no = (TextView) convertView.findViewById(R.id.NumOfYes);
                TextView NumOfNo_n_no = (TextView) convertView.findViewById(R.id.NumOfNo);
                TextView NumOfComment_n_no = (TextView) convertView.findViewById(R.id.NumOfComment);

                questionCard_n_no.setText(question.getContect());

                NumOfYes_n_no.setText(question.getNum_Yes()+"");

                NumOfNo_n_no.setText((question.getNum_No()+1)+"");

                NumOfComment_n_no.setText(question.getNum_Comment()+"");

                break;

            case TYPE_NONE:
                convertView = LayoutInflater.from(_context).inflate(R.layout.before_item_change, null);


                FixedSwipeLayout swipeLayout_view = (FixedSwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));

                //텍스트 값 지정
                TextView questionCard = (TextView) convertView.findViewById(R.id.questionCard);
                TextView questionCard_y = (TextView) convertView.findViewById(R.id.questionCard_y);
                TextView questionCard_n = (TextView) convertView.findViewById(R.id.questionCard_n);

                TextView datetime_card = (TextView) convertView.findViewById(R.id.textView_date);

                TextView NumOfYes_y = (TextView) convertView.findViewById(R.id.NumOfYes_y);
                TextView NumOfYes_n = (TextView) convertView.findViewById(R.id.NumOfYes_n);

                TextView NumOfNo_y = (TextView) convertView.findViewById(R.id.NumOfNo_y);
                TextView NumOfNo_n = (TextView) convertView.findViewById(R.id.NumOfNo_n);

                TextView NumOfComment_y = (TextView) convertView.findViewById(R.id.NumOfComment_y);
                TextView NumOfComment_n = (TextView) convertView.findViewById(R.id.NumOfComment_n);

                questionCard.setText(question.getContect());
                questionCard_y.setText(question.getContect());
                questionCard_n.setText(question.getContect());

                Date date = new Date(question.getRegdate().getTime());
                datetime_card.setText((new SimpleDateFormat("yyyy.MM.dd").format(date)).toString());

                NumOfYes_y.setText((question.getNum_Yes()+1)+"");
                NumOfYes_n.setText(question.getNum_Yes()+"");

                NumOfNo_y.setText((question.getNum_No())+"");
                NumOfNo_n.setText((question.getNum_No()+1)+ "");

                NumOfComment_y.setText(question.getNum_Comment() + "");
                NumOfComment_n.setText(question.getNum_Comment()+"");

                swipeLayout_view.setShowMode(SwipeLayout.ShowMode.PullOut);

                swipeLayout_view.addDrag(SwipeLayout.DragEdge.Left, swipeLayout_view.findViewById(R.id.container_y));
                swipeLayout_view.addDrag(SwipeLayout.DragEdge.Right, swipeLayout_view.findViewById(R.id.container_n));

                swipeLayout_view.addRevealListener(R.id.container_y, new SwipeLayout.OnRevealListener() {
                    @Override
                    public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
                        DefaultDragEdge = edge;

                    }
                });
                swipeLayout_view.addRevealListener(R.id.container_n, new SwipeLayout.OnRevealListener() {
                    @Override
                    public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
                        DefaultDragEdge = edge;

                    }
                });

                final FixedSwipeLayout finalSwipeLayout_view = swipeLayout_view;
                final View finalConvertView = convertView;

                swipeLayout_view.addSwipeListener(new SwipeLayout.SwipeListener() {
                    @Override
                    public void onStartOpen(SwipeLayout swipeLayout) {

                    }
                    @Override
                    public void onOpen(SwipeLayout swipeLayout) {
                        finalSwipeLayout_view.setSwipeEnabled(false);

                        if(DefaultDragEdge.equals(SwipeLayout.DragEdge.Left)){
                            question.setAnswer("yes");

                        } else if(DefaultDragEdge.equals(SwipeLayout.DragEdge.Right)){
                            question.setAnswer("no");
                        }else{
                            return;
                        }
                        Log.v("InputSelect", question.getUuid()+"  "+question.getAnswer());
                        api.InputSelect(question.getId_card(), question, new Callback<Question>() {
                            @Override
                            public void success(Question question, Response response) {
                                Log.v("api", "success select " + question.getId_card() + " " + question.getYes_NO());
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.v("api", String.valueOf(error));
                                Snackbar.make(finalConvertView, "카드 등록에 실패했습니다", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            }
                        });
                    }

                    @Override
                    public void onStartClose(SwipeLayout swipeLayout) {

                    }

                    @Override
                    public void onClose(SwipeLayout swipeLayout) {

                    }

                    @Override
                    public void onUpdate(SwipeLayout swipeLayout, int i, int i1) {

                    }

                    @Override
                    public void onHandRelease(SwipeLayout swipeLayout, float v, float v1) {

                    }
                });
                break;
        }

        return convertView;
    }

    public void add(Question _msg) {
        this.items.add(0, _msg);
        this.notifyDataSetChanged();
    }

    public void add_top(Question _msg) {
        this.items.add(items.size(), _msg);
        this.notifyDataSetChanged();
    }
    public void remove(int _position) {
        items.remove(_position);
    }

}

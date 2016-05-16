package com.tendom.tooki.logpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tendom.tooki.R;
import com.tendom.tooki.commentpage.Question;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Woong on 2015-07-08.
 */
public class CustomAdapter_Log extends ArrayAdapter<Question> {


    public ArrayList<Question> items;
    Context _context;
    int resource;


    public CustomAdapter_Log(Context context, int resource, ArrayList<Question> items) {
        super(context, resource, items);
        _context = context;
        this.resource = resource;
        this.items = items;
    }


    public void setSource(ArrayList<Question> questions) {
        this.items = questions;
        this.notifyDataSetChanged();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        Question q = items.get(position);
        q.answer = items.get(position).answer;


        if (q != null) {

            if(q.answer != null && resource!=0) {

                if (q.answer.equals("yes")) {
                    v = LayoutInflater.from(_context).inflate(R.layout.yes_custom_item, null);
                }
                else if (q.answer.equals("no")) {
                    v = LayoutInflater.from(_context).inflate(R.layout.no_custom_item, null);
                }

                TextView Question_Card = (TextView) v.findViewById(R.id.questionCard);
                TextView Yes_Num = (TextView) v.findViewById(R.id.NumOfYes);
                TextView No_Num = (TextView) v.findViewById(R.id.NumOfNo);
                TextView Comment_Num = (TextView) v.findViewById(R.id.NumOfComment);


                if (Question_Card != null) {
                    Question_Card.setText(q.getContect());
                }
                if (Yes_Num != null) {
                    Yes_Num.setText(Integer.toString(q.getNum_Yes()));
                }
                if (No_Num != null) {
                    No_Num.setText(Integer.toString(q.getNum_No()));
                }
                if (Comment_Num != null) {
                    Comment_Num.setText(Integer.toString(q.getNum_Comment()));
                }

            }
            else {
                v = LayoutInflater.from(_context).inflate(R.layout.before_item, null);

                TextView Question_Card = (TextView) v.findViewById(R.id.questionCard);
                TextView datetime_card = (TextView) v.findViewById(R.id.textView);

                if (Question_Card != null) {
                    Question_Card.setText(q.getContect());
                }
                if (datetime_card != null) {
                    Date date = new Date(q.getRegdate().getTime());
                    datetime_card.setText((new SimpleDateFormat("yyyy.MM.dd").format(date)).toString());
                }

            }

        }
        return v;
    }

    public void add(Question _msg) {
        this.items.add(0, _msg);
        this.notifyDataSetChanged();
    }

    public void remove(int _position) {
        items.remove(_position);
    }
}

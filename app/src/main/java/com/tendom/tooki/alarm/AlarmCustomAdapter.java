package com.tendom.tooki.alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tendom.tooki.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by OZ5 on 2016-01-03.
 */
public class AlarmCustomAdapter extends ArrayAdapter<AlarmContent> {

    Context context;
    private int layoutResourceId;
    private ArrayList<AlarmContent> cList;
    public String current_text;

    public AlarmCustomAdapter(Context context, int layoutResourceId, ArrayList<AlarmContent> items)
    {
        super(context,layoutResourceId,items);
        this.cList = items;
        this.context = context;
        this.layoutResourceId =layoutResourceId;
    }

    public void setSource(ArrayList<AlarmContent> items) {
        this.cList = items;
        this.notifyDataSetChanged();
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row==null) {
            LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = mLayoutInflater.inflate(layoutResourceId, parent, false);
        }

        ImageView OXimage = (ImageView) row.findViewById(R.id.OXCmtimage);
        TextView AlarmText = (TextView) row.findViewById(R.id.Alarm_text);
        TextView AlarmDateText = (TextView) row.findViewById(R.id.textview_alarm_date);

        AlarmContent alarm = cList.get(position);

        if(alarm != null)
        {
            if(alarm.alarm == alarm.YES)
            {
                //TODO : 알람 YES
                OXimage.setImageResource(R.drawable.ic_yes);
                current_text = "누군가 \"" + alarm.question + "\" 에 반응했습니다. ";
            }
            else if (alarm.alarm == alarm.COMMENT)
            {

                //TODO : 댓글 이미지

                OXimage.setImageResource(R.drawable.ic_comment_big);
                current_text = "누군가 \"" + alarm.question + "\" 에 댓글을 등록했습니다. ";
            }
            else if (alarm.alarm == alarm.NO)
            {
                //TODO : 알람 NO
                OXimage.setImageResource(R.drawable.ic_no);
                current_text = "누군가 \"" + alarm.question + "\" 에 반응했습니다. ";
            }

            else {
               // OXimage.setImageResource(R.drawable.ic_back);
               // current_text = "diįddwdd"+alarm.alarm;
            }
            //OXimage.setImageResource(R.drawable.o_image);
            //comment.comment = CommentText.getText().toString();
            AlarmText.setText(current_text);
            Date date = new Date(alarm.getRegdate().getTime());
            AlarmDateText.setText((new SimpleDateFormat("yyyy.MM.dd").format(date)).toString());

        }

        return row;
    }

}

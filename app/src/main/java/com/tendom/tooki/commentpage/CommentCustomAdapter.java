package com.tendom.tooki.commentpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tendom.tooki.R;

import java.util.ArrayList;

/**
 * Created by Woong on 2015-07-10.
 */
public class CommentCustomAdapter extends ArrayAdapter<Comment> {
    Context _context;
    private int layoutResourceId;
    private ArrayList<Comment> cList;

    public CommentCustomAdapter(Context context, int layoutResourceId, ArrayList<Comment> items) {
        super(context, layoutResourceId, items);
        this.cList = items;
        this.layoutResourceId = layoutResourceId;
        _context = context;
    }

    public void add(Comment _msg) {
        this.cList.add(0, _msg);
        this.notifyDataSetChanged();
    }

    public void setSource(ArrayList<Comment> items) {
        this.cList = items;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row==null) {
            LayoutInflater mLayoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = mLayoutInflater.inflate(layoutResourceId, parent, false);
        }

        ImageView OXimage = (ImageView) row.findViewById(R.id.OXimage);
        TextView CommentText = (TextView) row.findViewById(R.id.CommentText);
        TextView CommentlikeText = (TextView) row.findViewById(R.id.CommentLikeText);

        Comment comment = cList.get(position);

        if(comment != null)
        {
            if(comment.getAnswer()!= null){
                if(comment.getAnswer().equals("yes"))
                {
                    OXimage.setImageResource(R.drawable.ic_yes);
                }
                else if (comment.getAnswer().equals("no"))
                {
                    OXimage.setImageResource(R.drawable.ic_no);
                }
                else{
                }
            }
            CommentText.setText(comment.getComment());
            CommentlikeText.setText("추천 "+ comment.getComment_like());
        }

        return row;
    }
}
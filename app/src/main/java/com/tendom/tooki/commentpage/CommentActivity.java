package com.tendom.tooki.commentpage;

/**
 * Created by Woong on 2015-07-01.
 */

import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tendom.tooki.R;
import com.tendom.tooki.network.ApplicationController;
import com.tendom.tooki.network.ServerInterface;
import com.tendom.tooki.uuid.MyUUID;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CommentActivity extends AppCompatActivity {

    EditText input_value_comment; // Edittext에 입력되는 변수
    Button input_button_comment,share_button, btn_back;
    Intent m_intent;
    ListView mlistView;
    LinearLayout content_page;
    TextView CommentlikeText;

    public ArrayList<Comment> cList = new ArrayList<Comment>();
    public CommentCustomAdapter customAdapter;

    private ServerInterface api;
    public String uuid="";
    Question mQuestion = new Question();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        input_button_comment= (Button)findViewById(R.id.input_button_comment);
        input_value_comment = (EditText)findViewById(R.id.input_comment);
        content_page = (LinearLayout)findViewById(R.id.content_page);
        mlistView = (ListView) findViewById(R.id.listView_comment);

        share_button=(Button)findViewById(R.id.share_button);
        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.listView_comment), "링크가 복사되었습니다.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                final ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboardManager.setText("www.sopt.org");      }
        });

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        m_intent = new Intent(this.getIntent());
        final int card_id = m_intent.getIntExtra("card_id", 0);
        String card_answer = m_intent.getStringExtra("card_answer");
        if(card_answer==null){
            card_answer="";
        }
        final double site_x = 0;
        final double site_y = 0;

        mQuestion.setAnswer(card_answer);
        if(card_answer.equals("yes")) {
            content_page.setBackgroundResource(R.drawable.yes_select);
        }else if(card_answer.equals("no")){
            content_page.setBackgroundResource(R.drawable.no_select);
        }else{
        }

        api = ApplicationController.getInstance().getServerInterface();
        uuid = new MyUUID().getUUID(getApplicationContext());

        api.OutputSelect(card_id, new Callback<Question>() {
            @Override
            public void success(Question question, Response response) {
                mQuestion = new Question(uuid, question.getContect(), question.getNum_Yes(), question.getNum_No(), question.getNum_Comment(), site_x, site_y);
                mQuestion.setId_card(question.getId_card());

                TextView NumOfYes = (TextView) findViewById(R.id.NumOfYes);// yes에 들어갈 text 지정
                NumOfYes.setText(Integer.toString(mQuestion.getNum_Yes()));

                TextView NumOfNo = (TextView) findViewById(R.id.NumOfNo);// NO에 들어갈 text 지정
                NumOfNo.setText(Integer.toString(mQuestion.getNum_No()));

                TextView NumOfComment = (TextView) findViewById(R.id.NumOfComment);// 댓글수에 들어갈 text 지정
                NumOfComment.setText(Integer.toString(mQuestion.getNum_Comment()));

                TextView questionCard = (TextView) findViewById(R.id.questionCard);//질문카드 내용 지정
                questionCard.setText(mQuestion.getContect());



                    api.View_Comment(mQuestion.getId_card(), new Callback<ArrayList<Comment>>() {
                        @Override
                        public void success(ArrayList<Comment> comments, Response response) {
                            cList = comments;
                            customAdapter = new CommentCustomAdapter(getApplicationContext(), R.layout.row, cList);
                            customAdapter.setSource(comments);
                            mlistView.setAdapter(customAdapter);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Snackbar.make(findViewById(R.id.listView_comment), "댓글이 없습니다.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        }

                    });

            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(findViewById(R.id.listView_comment), "카드 정보를 찾지 못했습니다.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }

        });
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                if(customAdapter.getItem(position).getClick_one()==0) {
                    api.LikeComment(card_id, customAdapter.getItem(position), new Callback<Comment>() {
                        @Override
                        public void success(Comment comment, Response response) {
                            Log.v("like", "success");
                            CommentlikeText = (TextView) view.findViewById(R.id.CommentLikeText);
                            customAdapter.getItem(position).setComment_like(comment.getComment_like());
                            CommentlikeText.setText("추천 " + comment.getComment_like());
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.v("like", "fail " + error);
                        }
                    });
                    customAdapter.getItem(position).setClick_one(1);
                }else{
                    return;
                }
            }
        });
        //final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String finalCard_answer = card_answer;
        input_button_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("comment", uuid);
                Log.v("comment", String.valueOf(mQuestion.getId_card()));
                Comment mComment = new Comment(input_value_comment.getText().toString(), finalCard_answer, uuid);
            if(!input_value_comment.getText().toString().equals("")) {

                api.SendComment(mQuestion.getId_card(), mComment, new Callback<Comment>() {
                    @Override
                    public void success(Comment comment, Response response) {
                        cList.add(comment);
                        customAdapter = new CommentCustomAdapter(getApplicationContext(), R.layout.row, cList);
                        mlistView.setAdapter(customAdapter);
                        TextView NumOfComment = (TextView) findViewById(R.id.NumOfComment);// 댓글수에 들어갈 text 지정
                        mQuestion.setCount_comment((mQuestion.getNum_Comment() + 1));
                        NumOfComment.setText(Integer.toString(mQuestion.getNum_Comment()));
                        input_value_comment.setText("");
                        Snackbar.make(findViewById(R.id.listView_comment), "댓글등록을 성공했습니다.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        input_value_comment.setText("");
                        Snackbar.make(findViewById(R.id.listView_comment), "댓글등록을 실패했습니다.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    }

                });
            }
            else{
                Snackbar.make(findViewById(R.id.listView_comment), "댓글을 입력해주세요.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
            }
        });

    }

    //툴바 뒤로가기 버튼
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    };
}
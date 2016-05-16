package com.tendom.tooki.commentpage;

import java.sql.Timestamp;

/**
 * Created by Woong on 2015-07-03.
 */
public class Question {

    public int id_card;
    public String uuid;
    public String question;
    public int count_yes;
    public int count_no;
    public int count_comment;
    public int card_select; //카드 yes no 선택여부
    public Timestamp regdate;

    public double site_x;
    public double site_y;


    public String answer;

    public String getAnswer() {
        return answer;
    }

    public void setCount_comment(int count_comment) {
        this.count_comment = count_comment;
    }

    public Timestamp getRegdate() {
        return regdate;
    }

    public Question(String uuid, String Contect, int num_Yes, int num_No, int num_Comment, double site_x, double site_y) {
        this.uuid = uuid;
        this.question = Contect;
        this.count_comment = num_Comment;
        this.count_no = num_No;
        this.count_yes = num_Yes;
        this.site_x = site_x;
        this.site_y = site_y;
    }

    public Question()
    {}

    public void setUuid(String _uuid) {this.uuid = _uuid;}

    public void setAnswer(String _answer) { this.answer = _answer;}

    public int getCard_select(){ return card_select;} //카드 yes no 선택여부 get
    public void setCard_select(int selectedNum){this.card_select = selectedNum;} //카드 yes no 선택여부 set

    public int getId_card() {return id_card; }

    public String getUuid() {
        return uuid;
    }

    public String getContect() {
        return question;
    }

    public int getNum_Yes() {
        return count_yes;
    }

    public int getNum_No() {
        return count_no;
    }

    public int getNum_Comment() {
        return count_comment;
    }

    public double getSite_x() {
        return site_x;
    }

    public double getSite_y() {
        return site_y;
    }

    public String getYes_NO() {return answer;}

    public void setYes_NO(String yes_no)
    {
        this.answer = yes_no;
    }

    public void setId_card(int id_card) {this.id_card = id_card;}

}


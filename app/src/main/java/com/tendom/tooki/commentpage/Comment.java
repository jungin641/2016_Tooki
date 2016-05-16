package com.tendom.tooki.commentpage;

/**
 * Created by Woong on 2015-07-10.
 */
public class Comment {
    public String uuid;
    public String comment;
    public String answer;
    public int comment_like;
    public int click_one;

    public Comment(String comment, String answer, String uuid) {
        this.uuid = uuid;
        this.comment = comment;
        this.answer = answer;
    }

    public int getClick_one() {
        return click_one;
    }

    public void setClick_one(int click_one) {
        this.click_one = click_one;
    }

    public void setComment_like(int comment_like) {
        this.comment_like = comment_like;
    }

    public int getComment_like() {return comment_like;}

    public String getuuid() {return uuid;}

    public String getComment() {
        return comment;
    }

    public String getAnswer() {
        return answer;
    }
}
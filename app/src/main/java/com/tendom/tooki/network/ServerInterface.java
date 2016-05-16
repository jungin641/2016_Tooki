package com.tendom.tooki.network;


import com.tendom.tooki.alarm.AlarmContent;
import com.tendom.tooki.commentpage.Comment;
import com.tendom.tooki.commentpage.Question;
import com.tendom.tooki.uuid.MyUUID;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ServerInterface {

    // UUID 가 기존에 등록 되어 있는지 확인
    @GET("/users/{uuid}")
    public void check_uuid(@Path("uuid") String uuid, Callback<MyUUID> callback);
    // 회원 가입
    @POST("/users/{uuid}")
    public void JoinUUID(@Path("uuid") String uuid, Callback<MyUUID> callback);
    // 회원 탈퇴
    @POST("/users/{uuid}/signout")
    public void OutUUID(@Path("uuid") String uuid, @Body Question question, Callback<Question> callback);

    // 내가 쓴 글
    @GET("/users/{uuid}/write")
    public void Call_My_Question(@Path("uuid") String uuid, Callback<ArrayList<Question>> callback);
    // 내가 선택한 글
    @GET("/users/{uuid}/select")
    public void Call_My_Key(@Path("uuid") String uuid, Callback<ArrayList<Question>> callback);
    // 내가 댓글단 글
    @GET("/users/{uuid}/comment")
    public void Call_My_Comment(@Path("uuid") String uuid, Callback<ArrayList<Question>> callback);

    // 알림 목록 받아오기
    @GET("/users/{uuid}/alarm")
    public void  View_Alarm(@Path("uuid") String uuid, Callback<ArrayList<AlarmContent>> callback);



    // 최근에 등록된 카드를 받아오는것 (질문에 답하지 않은 카드)
    @GET("/cards/latest/{uuid}")
    public void Call_latest_Question(@Path("uuid") String uuid, Callback<ArrayList<Question>> callback);
    // 지금 위치를 보내주어서 근처에 등록된 카드를 받아오는것(질문에 답하지 않은 카드)
    @GET("/cards/nearby")
    public void Call_Site_Question(double latitude, double longitude, Callback<ArrayList<Question>> callback);
    // 인기 있는 카드를 받아오는것 (질문에 답하지 않은카드)
    @GET("/cards/hottest/{uuid}")
    public void Call_Hot_Question(@Path("uuid") String uuid, Callback<ArrayList<Question>> callback);

    // 댓글 열람
    @GET("/cards/{id-card}/comment")
    public void View_Comment(@Path("id-card") int id_card, Callback<ArrayList<Comment>> callback);
    // 댓글 등록
    @POST("/cards/{id-card}/comment")
    public void SendComment(@Path("id-card") int id_card, @Body Comment comment, Callback<Comment> callback);
    @POST("/cards/{id-card}/comment_like")
    public void LikeComment(@Path("id-card") int id_card, @Body Comment comment, Callback<Comment> callback);

    // 선택한 카드 보기
    @GET("/cards/{id-card}/selected")
    public void OutputSelect(@Path("id-card") int id_card, Callback<Question> callback);
    // 카드 등록
    @POST("/cards")
    public void SendQuestion(@Body Question question, Callback<Question> callback);
    // 카드 선택
    @POST("/cards/{id-card}/select")
    public void InputSelect(@Path("id-card") int id_card, @Body Question question, Callback<Question> callback);



}

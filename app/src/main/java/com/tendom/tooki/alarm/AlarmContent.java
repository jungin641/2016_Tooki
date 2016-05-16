package com.tendom.tooki.alarm;


import java.sql.Timestamp;

/**
 * Created by OZ5 on 2016-01-03.
 */
public class AlarmContent {

    final  int  YES =1, NO = 2, COMMENT = 3;

    public int alarm;
    public String question;
    public Timestamp regdate;
    public String uuid;
    public int id_card;

    public AlarmContent()
    {

    }

    public int getId_card() {
        return id_card;
    }

    public Timestamp getRegdate() {
        return regdate;
    }
}

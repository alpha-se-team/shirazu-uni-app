package com.example.shiraz_uni_app.Event;

public class Event {

    private String mContext ;
    private String mDate ;

    public Event(String context, String date) {
        this.mContext = context;
        this.mDate = date;
    }

    public String getContext() {
        return mContext;
    }

    public void setContext(String context) {
        this.mContext = context;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }
}
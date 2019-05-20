package com.example.shiraz_uni_app.Event;

public class Event {

    private String mContext ;
    private String mDate ;
    private int mId;
    private String mImageAddress;

    public Event(String context, String date, int id) {
        this.mId = id;
        this.mContext = context;
        this.mDate = date;
    }


    public String getmImageAddress() {
        return mImageAddress;
    }

    public void setmImageAddress(String mImageAddress) {
        this.mImageAddress = mImageAddress;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
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
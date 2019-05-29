package com.example.shiraz_uni_app.Event;

import java.util.Date;

public class Event {

    private String mContext ;
    private Date mDate ;
    private int mId;
    private String mImageAddress;
    private boolean mSaved;
    private String mTitle;

    public Event(String context, Date date, int id, String title) {
        this.mId = id;
        this.mContext = context;
        this.mDate = date;
        this.mSaved = false;
        this.mTitle = title;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public boolean ismSaved() { return mSaved; }

    public void setmSaved(boolean mSaved) { this.mSaved = mSaved; }

    public String getmImageAddress() {
        return mImageAddress;
    }

    public void setmImageAddress(String mImageAddress) {
        this.mImageAddress = mImageAddress;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) { this.mId = mId; }

    public String getContext() {
        return mContext;
    }

    public void setContext(String context) {
        this.mContext = context;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }
}
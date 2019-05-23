package com.example.shiraz_uni_app.Internet;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Observable;

import saman.zamani.persiandate.PersianDate;

public class AccountModel extends Observable {

    private int mTraffic;
    private int mDays;

    public int getmTraffic() {
        return mTraffic;
    }

    public int getmDays() {
        return mDays;
    }

    public void setmTraffic(int mTraffic) {
        this.mTraffic = mTraffic;
    }

    public void setmDays(int mDays) {
        this.mDays = mDays;
    }

    public void getDataApi(String mToken) {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectUser = new JSONObject();
        try {
            jsonObject.put("token", mToken);
            jsonObjectUser.put("user", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post("") //TODO : server ok kone
                .addJSONObjectBody(jsonObjectUser) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONObject user = response.getJSONObject("user");

                            setmTraffic(user.getInt("traffic"));
                            setmDays(user.getInt("day"));

                            setChanged();
                            notifyObservers();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("shirin", "on response exception");
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                        setmTraffic(9);
                        setmDays(2);

                        Log.i("shirin" , mDays + " " + mTraffic);
                        setChanged();
                        notifyObservers();

                    }
                });
    }

    public String setDate(){
        String res = "";
        Date date = new Date();
        PersianDate persianDate = new PersianDate(date);
        res += persianDate.dayName() + " ";
        res += persianDate.getShDay() + " ";
        res += persianDate.monthName() + " ";
        res += persianDate.getShYear() + " ";
        return res;
    }

}
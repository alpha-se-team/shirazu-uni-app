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
    private int mChargeAmountPerMonth;
    private String mRechargeDate;
    private String mExpirationDate;


    public void setmRechargeDate(String mRechargeDate) {
        this.mRechargeDate = mRechargeDate;
    }

    public String getmRechargeDate() {
        return mRechargeDate;
    }

    public void setmExpirationDate(String mExpirationDate) {
        this.mExpirationDate = mExpirationDate;
    }

    public String getmExpirationDate() {
        return mExpirationDate;
    }

    public void setmChargeAmountPerMonth(int mChargeAmountPerMonth) {
        this.mChargeAmountPerMonth = mChargeAmountPerMonth;
    }

    public int getmChargeAmountPerMonth() {
        return mChargeAmountPerMonth;
    }


    public int getmTraffic() {
        return mTraffic;
    }

    public void setmTraffic(int mTraffic) {
        this.mTraffic = mTraffic;
    }

    public int getmDays() {
        return mDays;
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

        Log.i("shirin" , jsonObjectUser.toString());


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
                            setmChargeAmountPerMonth(user.getInt("chargeAmount"));
                            setmExpirationDate(user.getString("expirationDate"));
                            setmRechargeDate(user.getString("rechargeDate"));
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
                        setmRechargeDate("1/2/3");
                        setmExpirationDate("01/01/00");
                        setmChargeAmountPerMonth(15);

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
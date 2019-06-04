package com.example.shiraz_uni_app.Internet;

import android.util.Log;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Observable;

import okhttp3.Response;
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

    public void getDataApi(String token){

        Log.i("shirin" , "get data api");
        AndroidNetworking.get("") //todo get url from server
                .addHeaders("Authorization", "Bearer "+ token)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("shirin" , "response ");
                        try {
                            JSONObject user = response.getJSONObject("account");

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
                        Log.i("shirin" , "on error " + error.getErrorDetail());

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
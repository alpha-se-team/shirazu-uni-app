package com.example.shiraz_uni_app.Internet.Account;

import android.util.Log;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.anychart.editor.Step;
import com.orhanobut.hawk.Hawk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Observable;

import okhttp3.Response;

import saman.zamani.persiandate.PersianDate;

public class AccountModel extends Observable {

    private int mAmountConsumed;
    private int mPlanId;
    private int mPlanTotalBW;
    private String mPlantitle;
    private String mPlaneDesc;
    private boolean getPlanIdData;
    private boolean getNameData;
    private String mFullName;
    private String mStdNum;

    public String getmFullName() {
        return mFullName;
    }

    public String getmStdNum() {
        return mStdNum;
    }

    public void setmFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public void setmStdNum(String mStdNum) {
        this.mStdNum = mStdNum;
    }

    public boolean isGetPlanIdData() {
        return getPlanIdData;
    }

    public boolean isGetNameData() {
        return getNameData;
    }

    public void setGetPlanIdData(boolean getPlanIdData) {
        this.getPlanIdData = getPlanIdData;
    }

    public void setGetNameData(boolean getNameData) {
        this.getNameData = getNameData;
    }

    private Date date = new Date();
    private PersianDate persianDate = new PersianDate(date);


    public int getmAmountConsumed() {
        return mAmountConsumed;
    }

    public void setmPlanTotalBW(int mPlanTotalBW) {
        this.mPlanTotalBW = mPlanTotalBW;
    }

    public void setmPlantitle(String mPlantitle) {
        this.mPlantitle = mPlantitle;
    }

    public void setmPlaneDesc(String mPlaneDesc) {
        this.mPlaneDesc = mPlaneDesc;
    }

    public int getmPlanTotalBW() {
        return mPlanTotalBW;
    }

    public String getmPlantitle() {
        return mPlantitle;
    }

    public String getmPlaneDesc() {
        return mPlaneDesc;
    }

    public void setmAmountConsumed(int mAmountConsumed) {
        this.mAmountConsumed = mAmountConsumed;
    }

    public void setmPlanId(int mPlanId) {
        this.mPlanId = mPlanId;
    }

    public int getmPlanId() {
        return mPlanId;
    }


    public void mProfileReadApi(String mUserName){
        AndroidNetworking.get("https://young-castle-19921.herokuapp.com/apiv1/profile/" + mUserName + "/")
                .addHeaders("")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject mProfile = response.getJSONObject("profile");
                            setmAmountConsumed(mProfile.getInt("amount_consumed"));
                            setmPlanId(mProfile.getInt("plan_id"));

                            mPlanReadApi();

                        } catch (JSONException e) {
                            setGetPlanIdData(false);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        setGetPlanIdData(false);
                    }
                });
    }

    private void mPlanReadApi() {
        AndroidNetworking.get("https://young-castle-19921.herokuapp.com/apiv1/plan/" + getmPlanId() + "/")
                .addHeaders("")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject mPlan = response.getJSONObject("plan");
                            setmPlanTotalBW(mPlan.getInt("total_bandwidth"));
                            setmPlantitle(mPlan.getString("title"));
                            setmPlaneDesc(mPlan.getString("description"));
                            setGetPlanIdData(true);
                            setChanged();
                            notifyObservers();


                        } catch (JSONException e) {
                            setGetPlanIdData(false);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        setGetPlanIdData(false);
                    }
                });
    }

    public void mGetNameStudentNumApi() {
        Log.i("shirin" , "get name called");
        AndroidNetworking.get("https://young-castle-19921.herokuapp.com/apiv1/user/")
                .addHeaders("Authorization", "Bearer "+ Hawk.get("token"))
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONObject user = response.getJSONObject("user");
                            setmFullName(user.getString("first_name") + " " + user.getString("last_name"));
                            setmStdNum(user.getString("student_id"));

                            setGetNameData(true);
                            setChanged();
                            notifyObservers();

                        } catch (JSONException e) {
                            setGetNameData(false);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        setGetNameData(false);
                    }
                });
    }

    public String getDate(){
        String res = "";
        res += persianDate.dayName() + " ";
        res += persianDate.getShDay() + " ";
        res += persianDate.monthName() + " ";
        res += persianDate.getShYear() + " ";
        return res;
    }

    public int setRemainingTime(){
        int today = persianDate.getShDay();
        int thisMonth = persianDate.getShMonth();

        if (thisMonth >6){
            return 30 - today;
        }

        else {
            return 31 - today;
        }
    }

    public String getmRechargeDate() {
        int mNextMonth = persianDate.getShMonth() + 1;
        int mYear = persianDate.getShYear();
        if (mNextMonth == 13){
            mNextMonth = 1;
            mYear += 1;
        }
        return mYear + "/" + mNextMonth + "/1" ;
    }

    public String getmExpirationDate() {
        return "1401/6/17";
    }

    public int mSetMonthLength(){
        return persianDate.getMonthLength();
    }


}
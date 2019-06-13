package com.example.shiraz_uni_app.Internet.ChangePassword;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

public class ChangePasswordModel extends Observable {

    boolean mSuccess;

    public boolean ismSuccess() {
        return mSuccess;
    }

    public void setmSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }


    public void changePassword(String mNewPassword) {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectUser = new JSONObject();
        String token = Hawk.get("token");

        try {

            jsonObject.put("password", mNewPassword);

            jsonObjectUser.put("user" , jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("fogetpasstest", "json sent : " + jsonObjectUser.toString() + "token ; " + token);


        AndroidNetworking.patch("https://young-castle-19921.herokuapp.com/apiv1/user/")
                .addHeaders("Authorization", "Bearer " + token)
                .addJSONObjectBody(jsonObjectUser)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("fogetpasstest", "on response" + response.toString());
                        setmSuccess(true);
                        setChanged();
                        notifyObservers();
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.i("fogetpasstest", "on error" + error.getErrorDetail());
                        setmSuccess(false);
                        setChanged();
                        notifyObservers();
                    }
                });
    }
}

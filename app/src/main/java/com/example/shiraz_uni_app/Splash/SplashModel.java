package com.example.shiraz_uni_app.Splash;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

public class SplashModel extends Observable {

   private boolean mSuccess;

    public void checkToken(String token) {

        Log.d("Amirerfan", "login called");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("")
            .addJSONObjectBody(jsonObject) // posting json
            .setTag("test")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("shirin", "on response");
                    try {
                        setmSuccess(response.getBoolean("success")) ;

                        Log.d("shirin", mSuccess + "");

                        setChanged();
                        notifyObservers();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("shirin", "on response exception");
                    }
                }

                @Override
                public void onError(ANError error) {

                    if (error.getErrorCode() == 404) {
                        Log.i("amirerfan", "onError: 404");
                    }
                    //dialog.cancel();
                }
            });
    }


    public void setmSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public boolean ismSuccess() {
        return mSuccess;
    }
}

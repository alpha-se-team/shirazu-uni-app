package com.example.shiraz_uni_app.Splash;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

import okhttp3.Response;

public class SplashModel extends Observable {

   private boolean mSuccess;

    public void checkToken(String token){
        Log.i("shirin" , "check token api");
        AndroidNetworking.get("https://young-castle-19921.herokuapp.com/apiv1/user/")
                .addHeaders("Authorization", "Bearer "+ token)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsOkHttpResponse(new OkHttpResponseListener() {

                    @Override
                    public void onResponse(Response response) {
                        setmSuccess(true);
                        setChanged();
                        notifyObservers();
                    }

                    @Override
                    public void onError(ANError error) {
                        setmSuccess(false);
                        // handle error
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

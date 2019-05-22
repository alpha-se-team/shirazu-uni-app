package com.example.shiraz_uni_app.ForgetPassword;

import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

public class ForgetPasswordModel extends Observable {

    private boolean mSuccess;
    private String mEmailOrPhoneNumber;

    private void loginApiCallEmail(JSONObject jsonObject) {
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
                            setmSuccess(response.getBoolean("success")); //or hamijori true bedim behesh

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
                        Log.i("shirin" , "error");
                        mSuccess = true;
                        Log.i("shirin" , "success" + mSuccess);
                        setChanged();
                        notifyObservers();
                    }
                });


    }

    private void loginApiCallPhoneNumber(JSONObject jsonObject) {
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
                            setmSuccess(response.getBoolean("success"));

                            setChanged();
                            notifyObservers();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("shirin", "on response exception");
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                        setmSuccess(false);
                        if (error.getErrorCode() == 404) {
                            Log.i("amirerfan", "onError: 404");
                        }
                        mSuccess = false;
                        Log.i("shirin" , "success" + mSuccess);
                        setChanged();
                        notifyObservers();
                        //dialog.cancel();
                    }
                });
        //for test
        mSuccess = false;
    }

    public void forgetPasswordEmail(String mEmail) {
        Log.i("shirin" , "femail");
        setEmailOrPhoneNumber("email");
        Log.d("Amirerfan", "login called");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", mEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        loginApiCallEmail(jsonObject);
    }

    public void forgetPasswordPhoneNumber(String mPhoneNumber) {

        setEmailOrPhoneNumber("phone number");
        Log.d("Amirerfan", "login called");
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("phoneNumber", mPhoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        loginApiCallPhoneNumber(jsonObject);
    }

    public boolean ismSuccess() {
        return mSuccess;
    }

    public void setmSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public String getmEmailOrPhoneNumber() {
        return mEmailOrPhoneNumber;
    }

    public void setEmailOrPhoneNumber(String mEmailOrPhoneNumber) {
        this.mEmailOrPhoneNumber = mEmailOrPhoneNumber;
    }


}

package com.example.shiraz_uni_app.Login;

import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Observable;

public class LoginModel extends Observable{

    private String mToken;
    private boolean mValidLogin;
    private boolean mSuccess;
    private boolean mConnectionStatus;

    public void login(String mUsername, String mPassword) {

        Log.d("Amirerfan", "login called");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", mUsername);
            jsonObject.put("password", mPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        loginApiCall(jsonObject);

    }

    private void loginApiCall(JSONObject jsonObject) {
        AndroidNetworking.post("https://young-castle-19921.herokuapp.com/apiv1/users/login/")
                .addJSONObjectBody(jsonObject) // posting json
                .addBodyParameter("username", "amirerfan")
                .addBodyParameter("password", "amirerfan")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("login called s");
                            mSuccess = response.getBoolean("success");
                            setValidLogin(mSuccess);

                            if (mSuccess) {
                                setToken(response.getString("token"));
                                System.out.println(getToken());
                            }

                            setChanged();
                            notifyObservers();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("shirin", "on response exception");
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        System.out.println("login called s");
                        if (error.getErrorCode() == 201) {
                            Log.i("amirerfan", "onError: 404");
                        }
                        //dialog.cancel();
                    }
                });
    }


    public boolean isValidLogin() {
        return mValidLogin;
    }

    public void setValidLogin(boolean mValidLogin) {
        this.mValidLogin = mValidLogin;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        this.mToken = token;
    }
}

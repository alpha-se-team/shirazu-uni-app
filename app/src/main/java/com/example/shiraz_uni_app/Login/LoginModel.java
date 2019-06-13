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

    public void login(String mUsername, String mPassword) {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectUser = new JSONObject();
        try {
            jsonObject.put("username", mUsername);
            jsonObject.put("password", mPassword);

            jsonObjectUser.put("user" , jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        loginApiCall(jsonObjectUser);

    }

    private void loginApiCall(JSONObject jsonObject) {
        AndroidNetworking.post("https://young-castle-19921.herokuapp.com/apiv1/users/login/")
            .addJSONObjectBody(jsonObject) // posting json
            .setTag("test")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    setValidLogin(true);
                    try {
                        Log.i("shirin" , response.toString());
                        JSONObject user = response.getJSONObject("user");
                        setToken(user.getString("token"));

                        setChanged();
                        notifyObservers();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(ANError error) {

                    setValidLogin(false);

                    setChanged();
                    notifyObservers();
                    if (error.getErrorCode() == 404) {
                    }

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

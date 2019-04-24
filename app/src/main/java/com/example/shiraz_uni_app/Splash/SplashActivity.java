package com.example.shiraz_uni_app.Splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.example.shiraz_uni_app.Login.LoginActivity;
import com.example.shiraz_uni_app.MainActivity;
import com.example.shiraz_uni_app.R;
import com.orhanobut.hawk.Hawk;

import java.util.Observable;
import java.util.Observer;

public class SplashActivity extends Activity implements Observer {

    private boolean mConnectionStatus;
    private SplashModel mSplashModel;
    private String token;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mSplashModel = new SplashModel();
        mSplashModel.addObserver(this);

        AndroidNetworking.initialize(getApplicationContext());

        Hawk.init(SplashActivity.this).build();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                getState();
            }
        }, 2500);

    }

    @Override
    public void update(Observable o, Object arg) {

        if (mSplashModel.ismSuccess()) {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }
        finish();
        startActivity(intent);
    }

    private void getState(){

        Toast.makeText(SplashActivity.this, "2.5", Toast.LENGTH_SHORT).show();
        mConnectionStatus = MainActivity.checkInternetConnection(this);
        token = Hawk.get("token");

        if(mConnectionStatus){
            if(token != null){
                mSplashModel.checkToken(token);
            }else{
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        }else{
            Toast.makeText(SplashActivity.this, "no internet", Toast.LENGTH_SHORT).show();
            // TODO: 2019-04-20 pop up no internet
        }
    }

}

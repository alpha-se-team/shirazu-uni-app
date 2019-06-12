package com.example.shiraz_uni_app.Splash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.example.shiraz_uni_app.Internet.Account.AccountActivity;
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
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mSplashModel = new SplashModel();
        mSplashModel.addObserver(this);

        AndroidNetworking.initialize(getApplicationContext());
        Hawk.init(SplashActivity.this).build();
        //getState();

        tryToInter();
    }



    private void tryToInter() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Log.i("shirintest2" , "sag");
                getState();
            }
        }, 5000);
    }

    @Override
    public void update(Observable o, Object arg) {

        Log.i("shirin" , "update called");
        if (mSplashModel.ismSuccess()) {
            Log.i("shirin" , "update1");

            intent = new Intent(SplashActivity.this, AccountActivity.class);

        } else {
            Log.i("shirin" , "update2");
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }
        finish();
        startActivity(intent);
    }

    private void getState(){

        mConnectionStatus = MainActivity.checkInternetConnection(this);
        token = Hawk.get("token");
        Log.i("shirin" , "get state called");
        if(mConnectionStatus){
            if(token != null){
                Log.i("shirin" , "token not null  " + token);
                mSplashModel.checkToken(token);
            }else{
                Log.i("shirin" , "else");
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);

            }
        }else{

            final AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this); //the current class
            View dialogView = getLayoutInflater().inflate(R.layout.no_internet_connection_dialog,null);
            TextView close = dialogView.findViewById(R.id.close);
            builder.setView(dialogView) ;
            final AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                    tryToInter();
                }
            });

            dialog.setCanceledOnTouchOutside(false);
        }
    }
}

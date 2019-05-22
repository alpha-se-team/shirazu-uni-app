package com.example.shiraz_uni_app.Login;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.example.shiraz_uni_app.ForgetPassword.ForgetPassword;
import com.example.shiraz_uni_app.MainActivity;
import com.example.shiraz_uni_app.R;
import com.example.shiraz_uni_app.Splash.SplashActivity;

import com.orhanobut.hawk.Hawk;

import java.util.Observable;
import java.util.Observer;

public class LoginActivity extends AppCompatActivity implements Observer, View.OnClickListener {

    private LoginModel mModel;

    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginButton;

    private TextView mForgetPassword;

    private boolean mConnectionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AndroidNetworking.initialize(getApplicationContext());
        Hawk.init(LoginActivity.this).build();

        mModel = new LoginModel();
        mModel.addObserver(this);

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.login);
        mForgetPassword = findViewById(R.id.forget_password);

        mForgetPassword.setClickable(true);
        mForgetPassword.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        Log.i("shirin" , mModel.isValidLogin() + " valid login");
        if (mModel.isValidLogin()){
            // TODO: 2019-04-17 : create an intent
            Toast.makeText(this, "Internet account page", Toast.LENGTH_SHORT).show();
            Hawk.put("token" ,mModel.getToken());
        } else {
            // TODO: 2019-04-17 : unsuccessful login with wrong username or password

        }
    }

    @Override
    public void onClick(View v) {
        Log.i("amirerfan", "onClick: log in button has been clicked");

        switch (v.getId()){
            case (R.id.login):
                Log.i("shirin" , "login clicked");

                mConnectionStatus = MainActivity.checkInternetConnection(this);

                if(mConnectionStatus)
                    mModel.login(mUsername.getText().toString(), mPassword.getText().toString());
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this); //the current class
                    View dialogView = getLayoutInflater().inflate(R.layout.no_internet_connection_dialog, null);
                    TextView close = dialogView.findViewById(R.id.close);
                    builder.setView(dialogView);
                    final AlertDialog dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                }
                break;
            case (R.id.forget_password):
                Intent intent = new Intent(LoginActivity.this , ForgetPassword.class);
                startActivity(intent);
                break;
        }

    }
}

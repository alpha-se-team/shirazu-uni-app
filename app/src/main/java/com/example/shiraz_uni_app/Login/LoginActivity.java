package com.example.shiraz_uni_app.Login;

import android.content.DialogInterface;
import android.graphics.ColorSpace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.example.shiraz_uni_app.MainActivity;
import com.example.shiraz_uni_app.R;
import com.orhanobut.hawk.Hawk;

import java.util.Observable;
import java.util.Observer;

public class LoginActivity extends AppCompatActivity implements Observer, View.OnClickListener {

    private LoginModel mModel;

    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginButton;

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
        mLoginButton = findViewById(R.id.login_button);

        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        
        if (mModel.isValidLogin()){
            // TODO: 2019-04-17 : create an intent
            Hawk.put("token" ,mModel.getToken());
        } else {
            // TODO: 2019-04-17 : unsuccessful login with wrong username or password
        }

    }

    @Override
    public void onClick(View v) {
        Log.i("amirerfan", "onClick: log in button has been clicked");

        switch (v.getId()){
            case (R.id.login_button):
                mConnectionStatus = MainActivity.checkInternetConnection(this);

                if(mConnectionStatus)
                    mModel.login(mUsername.getText().toString(), mPassword.getText().toString());
                else
                    // TODO: 2019-04-17 : show no internet dialog

                break;
        }

    }
}

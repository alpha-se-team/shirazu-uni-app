package com.example.shiraz_uni_app.Login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.androidnetworking.AndroidNetworking;
import com.example.shiraz_uni_app.Event.AllEvents.EventsActivity;
import com.example.shiraz_uni_app.ForgetPassword.ForgetPasswordActivity;
import com.example.shiraz_uni_app.Internet.Account.AccountActivity;
import com.example.shiraz_uni_app.Internet.ChangePassword.ChangePasswordActivity;
import com.example.shiraz_uni_app.MainActivity;
import com.example.shiraz_uni_app.R;
import com.orhanobut.hawk.Hawk;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class LoginActivity extends AppCompatActivity implements Observer, View.OnClickListener {

    private LoginModel mModel;
    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginButton;
    private TextView mEventsText;
    private TextView mForgetPassword;
    private ImageView mEventsImage;
    private TextView mValidLogin;
    private ProgressDialog progressDialog;
    private boolean mConnectionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AndroidNetworking.initialize(getApplicationContext());
        Hawk.init(LoginActivity.this).build();

        try {
            Hawk.put("Saved", Hawk.get("Saved"));
        }catch (Exception e) {
            Hawk.put("Saved", new ArrayList<Integer>());
        }

        mModel = new LoginModel();
        mModel.addObserver(this);

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.login);
        mForgetPassword = findViewById(R.id.forget_password);
        mEventsText = findViewById(R.id.go_to_events_text_view);
        mEventsImage = findViewById(R.id.go_to_events_image_view);
        mValidLogin = findViewById(R.id.failed_attempt_dialog);

        mForgetPassword.setClickable(true);
        mForgetPassword.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        mEventsText.setOnClickListener(this);
        mEventsImage.setOnClickListener(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        progressDialog.cancel();

        if (mModel.isValidLogin()){
            Intent mAccount = new Intent(LoginActivity.this , AccountActivity.class);
            Hawk.put("token" ,mModel.getToken());
            Hawk.put("user_name" , mUsername.getText().toString());
            Hawk.put("password" , mPassword.getText().toString());
            finish();
            startActivity(mAccount);
        } else {
            mValidLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case (R.id.login):

                mConnectionStatus = MainActivity.checkInternetConnection(this);

                if(mConnectionStatus){
                    progressDialog = new ProgressDialog(LoginActivity.this , R.style.MyAlertDialogStyle);
                    progressDialog.setMessage("Please wait ...");
                    progressDialog.show();
                    mModel.login(mUsername.getText().toString(), mPassword.getText().toString());
                }

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
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);

                startActivity(intent);
                break;

            case (R.id.go_to_events_image_view):
                Intent mEventIntent = new Intent(LoginActivity.this, EventsActivity.class);
                startActivity(mEventIntent);
                break;

            case (R.id.go_to_events_text_view):
                Intent mEventIntent2 = new Intent(LoginActivity.this, EventsActivity.class);
                startActivity(mEventIntent2);
                break;
        }

    }
}

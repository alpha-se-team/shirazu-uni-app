package com.example.shiraz_uni_app.ForgetPassword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiraz_uni_app.Login.LoginActivity;
import com.example.shiraz_uni_app.R;

import java.util.Observable;
import java.util.Observer;

public class ForgetPassword extends AppCompatActivity implements View.OnClickListener , Observer {

    private EditText mEditTextEmil;
    private EditText mEditTextPhoneNumber;
    private TextView back;
    private Button mConfirm;
    private  ForgetPasswordModel mForgetPasswordModel;
    String mEmail;
    String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mEditTextEmil = findViewById(R.id.email);
        mEditTextPhoneNumber = findViewById(R.id.phone);
        back = findViewById(R.id.goToStart);
        mConfirm = findViewById(R.id.send);

        mForgetPasswordModel = new ForgetPasswordModel();
        mForgetPasswordModel.addObserver(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case (R.id.send):
                mEmail = mEditTextEmil.getText().toString();
                mPhoneNumber = mEditTextPhoneNumber.getText().toString();

                if (mEmail.length() == 0 && mPhoneNumber.length() != 0){
                    mForgetPasswordModel.forgetPasswordEmail(mEmail);
                }
                else if (mEmail.length() != 0 && mPhoneNumber.length() == 0){
                    mForgetPasswordModel.forgetPasswordPhoneNumber(mPhoneNumber);
                }

                else if (mEmail.length() != 0 && mPhoneNumber.length() != 0){
                    // TODO: 2019-04-24 :
                }

                else {
                    Toast.makeText(ForgetPassword.this, "please enter your email or your phone number", Toast.LENGTH_SHORT).show();
                }

                break;
            case (R.id.goToStart):
                Intent intent = new Intent(ForgetPassword.this , LoginActivity.class);
                finish();
                startActivity(intent);
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (mForgetPasswordModel.ismSuccess()){
            Toast.makeText(ForgetPassword.this,
                    "your password has been send to the" + mForgetPasswordModel.getmEmailOrPhoneNumber()
                    + "you been entered", Toast.LENGTH_SHORT).show();
        }
    }
}

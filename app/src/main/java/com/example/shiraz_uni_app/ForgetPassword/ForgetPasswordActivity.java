package com.example.shiraz_uni_app.ForgetPassword;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiraz_uni_app.Login.LoginActivity;
import com.example.shiraz_uni_app.MainActivity;
import com.example.shiraz_uni_app.R;

import java.util.Observable;
import java.util.Observer;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener , Observer {

    private EditText mEditTextEmil;
    private EditText mEditTextPhoneNumber;
    private TextView mBack;
    private Button mConfirm;
    private ImageView mBackButton;
    private  ForgetPasswordModel mForgetPasswordModel;
    String mEmail;
    String mPhoneNumber;
    boolean mConnectionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mEditTextEmil = findViewById(R.id.email);
        mEditTextPhoneNumber = findViewById(R.id.phone);
        mBack = findViewById(R.id.go_to_login_text_view);
        mBack.setOnClickListener(this);
        mConfirm = findViewById(R.id.send);
        mConfirm.setOnClickListener(this);
        mBackButton = findViewById(R.id.go_to_login_image_view);
        mBackButton.setOnClickListener(this);

        mForgetPasswordModel = new ForgetPasswordModel();
        mForgetPasswordModel.addObserver(this);

    }

    @Override
    public void onClick(View v) {

        mConnectionStatus = MainActivity.checkInternetConnection(ForgetPasswordActivity.this);
        switch (v.getId()){
            case (R.id.send):
                if (mConnectionStatus){
                    Log.i("shirin" , "send btn clicked");
                    mEmail = mEditTextEmil.getText().toString();
                    mPhoneNumber = mEditTextPhoneNumber.getText().toString();

                    if (mEmail.length() == 0 && mPhoneNumber.length() != 0){
                        mForgetPasswordModel.forgetPasswordPhoneNumber(mPhoneNumber);
                        Log.i("shirin" , "phone");
                    }

                    else if (mEmail.length() != 0 && mPhoneNumber.length() == 0){
                        mForgetPasswordModel.forgetPasswordEmail(mEmail);
                        Log.i("shirin" , "email");
                    }

                    else if (mEmail.length() != 0 ){
                        mForgetPasswordModel.forgetPasswordEmail(mEmail);
                        Log.i("shirin" , "both");

                    }

                    else {
                        Toast.makeText(ForgetPasswordActivity.this, "please enter your email or your phone number", Toast.LENGTH_LONG).show();
                    }
                }else{

                    final AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPasswordActivity.this); //the current class
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
                        }
                    });

                    dialog.setCanceledOnTouchOutside(false);
                }

                break;
            case (R.id.go_to_login_text_view):
                finish();
                break;

            case (R.id.go_to_login_image_view):
                finish();
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Intent intent;

        if (mForgetPasswordModel.ismSuccess()){
            Toast.makeText(ForgetPasswordActivity.this,
                    "your password has been send to the" + mForgetPasswordModel.getmEmailOrPhoneNumber()
                    + "you been entered", Toast.LENGTH_LONG).show();

            intent = new Intent(ForgetPasswordActivity.this , LoginActivity.class);

            finish();
            //startActivity(intent);

        }else {
            Toast.makeText(ForgetPasswordActivity.this, "ooops , try again!", Toast.LENGTH_LONG).show();
        }
    }
}

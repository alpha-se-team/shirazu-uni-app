package com.example.shiraz_uni_app.Internet.ChangePassword;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiraz_uni_app.Internet.Account.AccountActivity;
import com.example.shiraz_uni_app.Internet.ShowDialog;
import com.example.shiraz_uni_app.MainActivity;
import com.example.shiraz_uni_app.R;
import com.orhanobut.hawk.Hawk;

import java.util.Observable;
import java.util.Observer;

public class ChangePasswordActivity extends AppCompatActivity implements Observer, View.OnClickListener {

    private ImageView mBack;

    EditText mOldPasswordEditText;
    String mOldPassword;
    EditText mNewPasswordEditText1;
    String mNewPassword;
    EditText mConfirmNewPasswordEditText;
    String mConfirmNewPassword;
    Button mConfirmButton;
    ChangePasswordModel mModel;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mBack = findViewById(R.id.change_password_back_Button);
        mBack.setOnClickListener(this);

        mOldPasswordEditText = findViewById(R.id.old_password_edit_text);
        mNewPasswordEditText1 = findViewById(R.id.new_password_edit_text);
        mConfirmNewPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        mConfirmButton = findViewById(R.id.confirm_button);

        mModel = new ChangePasswordModel();
        mModel.addObserver(this);


        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOldPassword = mOldPasswordEditText.getText().toString();
                mNewPassword = mNewPasswordEditText1.getText().toString();
                mConfirmNewPassword = mConfirmNewPasswordEditText.getText().toString();
                TextView textView = findViewById(R.id.failed_attempt_dialog);
                if (MainActivity.checkInternetConnection(ChangePasswordActivity.this)){

                    if (! mOldPassword.equals(Hawk.get("password"))){
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("رمزعبور قبلی درست نمیباشد");
                    }

                    else if (mNewPassword.length() < 8){
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("طول رمزعبور حداقل باید 8 کاراکتر باشد");
                    }

                    else if (! mNewPassword.equals(mConfirmNewPassword)){
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("رمزعبور جدید مطابقت ندارد");
                    }

                    else {
                        textView.setVisibility(View.INVISIBLE);
                        progressDialog = new ProgressDialog(ChangePasswordActivity.this , R.style.MyAlertDialogStyle);
                        progressDialog.setMessage("Please wait ...");
                        progressDialog.show();
                        mModel.changePassword( mNewPassword);
                    }
                }

                else { final AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
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

            }
        });

    }

    @Override
    public void update(Observable observable, Object o) {
        progressDialog.cancel();
        if (mModel.ismSuccess()){
            ShowDialog.changePasswordDialog(this);
        }

        else {
            ShowDialog.wrongPasswordDialog(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_password_back_Button:
                finish();
        }
    }
}

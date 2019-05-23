package com.example.shiraz_uni_app.Internet;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiraz_uni_app.Login.LoginActivity;
import com.example.shiraz_uni_app.MainActivity;
import com.example.shiraz_uni_app.R;
import com.orhanobut.hawk.Hawk;

import java.util.Observable;
import java.util.Observer;

public class AccountActivity extends AppCompatActivity implements Observer {

    String mToken;
    AccountModel mModel;
    ImageView mMenuImageView;
    TextView mDateTextView;
    String date;
    TextView mRemainingDaysTextView;
    int mRemainingDays;
    com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar mRemainingDaysProgressBar;
    TextView mRemainingTrafficTextView;
    int mRemainingTraffic;
    com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar mRemainingTrafficProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mModel = new AccountModel();
        mModel.addObserver(this);

        mMenuImageView = findViewById(R.id.menu_icon);
        mDateTextView = findViewById(R.id.date_view);
        mRemainingDaysTextView = findViewById(R.id.remaining_days_text_view);
        mRemainingDaysProgressBar = findViewById(R.id.remaining_days_progress_bar);
        mRemainingTrafficTextView = findViewById(R.id.remaining_traffic_text_view);
        mRemainingTrafficProgressBar = findViewById(R.id.remaining_traffic_progress_bar);

        mToken = Hawk.get("token");
        if (mToken != null){

            if (MainActivity.checkInternetConnection(AccountActivity.this) ){
                mModel.getDataApi(mToken);
                mSetCurrentDate();
            }

            else{
                final AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
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

        else { //token paride , bayad az aval login kone
            Intent intent = new Intent(AccountActivity.this , LoginActivity.class);
            Toast.makeText(this, "error , plz login again", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }



    }

    @Override
    public void update(Observable o, Object arg) {
        mGetData();
    }

    public void mSetData(){
        mRemainingDaysTextView.setText(mRemainingDays + " روز ");
        mRemainingDaysProgressBar.setMax(30);
        mRemainingDaysProgressBar.setProgress(mRemainingDays);

        mRemainingTrafficTextView.setText(mRemainingTraffic + " گیگابایت ");
        mRemainingTrafficProgressBar.setMax(12);
        mRemainingTrafficProgressBar.setProgress(mRemainingTraffic);

        mSetCurrentDate();
    }
    public void mGetData(){
        mRemainingTraffic = mModel.getmTraffic();
        mRemainingDays = mModel.getmDays();
        mSetData();

    }

    public void mSetCurrentDate(){
        //todo : date ro hesab kon
        date = mModel.setDate();
        mDateTextView.setText(date);

    }




}

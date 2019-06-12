package com.example.shiraz_uni_app.Internet.Account;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.anychart.ui.contextmenu.Item;
import com.example.shiraz_uni_app.Internet.BuyTrafficActivity;
import com.example.shiraz_uni_app.Internet.ChangePassword.ChangePasswordActivity;
import com.example.shiraz_uni_app.Internet.ConnectionReportActivity;
import com.example.shiraz_uni_app.Login.LoginActivity;
import com.example.shiraz_uni_app.MainActivity;
import com.example.shiraz_uni_app.R;
import com.orhanobut.hawk.Hawk;

import java.util.Observable;
import java.util.Observer;

public class AccountActivity extends AppCompatActivity implements Observer, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private String mUserName;
    private AccountModel mModel;
    private ImageView mMenuImageView;
    private TextView mDateTextView;
    private String date;
    private TextView mRemainingDaysTextView;
    private int mRemainingDays;
    private RoundCornerProgressBar mRemainingDaysProgressBar;
    private TextView mRemainingTrafficTextView;
    private int mRemainingTraffic;
    private TextView mChargeAmountPerMonthTextView;
    private int mChargeAmountPerMonth;
    private TextView mRechargeDateTextView;
    private String mRechargeDate;
    private TextView mExpirationDateTextView;
    private String mExpirationDate;
    private DrawerLayout mAccountInfoDrawerLayout;
    private NavigationView navigationView;
    private LinearLayout mLogOut;
    private TextView mLogOutTextView;
    private ImageView mLogOutImageView;
    private ImageView mNavigation;
    private Thread thread;
    private RoundCornerProgressBar mRemainingTrafficProgressBar;

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
        mChargeAmountPerMonthTextView = findViewById(R.id.charge_amount_per_month);
        mRechargeDateTextView = findViewById(R.id.recharge_date);
        mExpirationDateTextView = findViewById(R.id.expiration_date);
        mAccountInfoDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        mNavigation = findViewById(R.id.menu_icon);
        mNavigation.setOnClickListener(this);

        mLogOutTextView = findViewById(R.id.logout_text_view);
        mLogOutImageView = findViewById(R.id.logout_image_view);
        mLogOutImageView.setClickable(false);
        mLogOutTextView.setClickable(false);

        mLogOut = findViewById(R.id.logout_layout);
        mLogOut.setOnClickListener(this);

        /*mMenuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAccountInfoDrawerLayout.openDrawer(GravityCompat.END);
                // we use the attribute GravityCompat.END to open the navigation bar from right to left

            }
        }); */



        mUserName = Hawk.get("user_name");

        if (mUserName != null){

            if (MainActivity.checkInternetConnection(AccountActivity.this) ){
                mModel.mProfileReadApi(mUserName);
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

        else {
            Intent intent = new Intent(AccountActivity.this , LoginActivity.class);
            startActivity(intent);
            finish();
        }

        updateData();

    }

    private void updateData() {

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!thread.isInterrupted()) {
                        Thread.sleep(60000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (MainActivity.checkInternetConnection(AccountActivity.this)){
                                    mModel.mProfileReadApi(mUserName);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        mGetData();
    }

    public void mSetData(){
        mDateTextView.setText(date);

        mRemainingDaysTextView.setText(mRemainingDays + " روز ");
        mRemainingDaysProgressBar.setMax(mModel.mSetMonthLength());
        mRemainingDaysProgressBar.setProgress(mRemainingDays);

        mRemainingTrafficTextView.setText(mRemainingTraffic + " گیگابایت ");
        mRemainingTrafficProgressBar.setMax(mChargeAmountPerMonth);
        mRemainingTrafficProgressBar.setProgress(mRemainingTraffic);

        mExpirationDateTextView.setText(mExpirationDate);
        mRechargeDateTextView.setText(mRechargeDate);
        mChargeAmountPerMonthTextView.setText(mChargeAmountPerMonth + " گیگابایت ");


    }
    public void mGetData(){
        mRemainingTraffic = mModel.getmPlanTotalBW() - mModel.getmAmountConsumed();
        mRemainingDays = mModel.setRemainingTime();
        mExpirationDate = mModel.getmExpirationDate();
        mRechargeDate = mModel.getmRechargeDate();
        mChargeAmountPerMonth = mModel.getmPlanTotalBW();
        date = mModel.getDate();

        mSetData();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_buy_traffic:
                Intent mBuyTraffic = new Intent(AccountActivity.this, BuyTrafficActivity.class);
                startActivity(mBuyTraffic);
                break;

            case R.id.nav_change_password:
                Intent mChangePassword = new Intent(AccountActivity.this, ChangePasswordActivity.class);
                startActivity(mChangePassword);
                break;

            case R.id.nav_connections:
                Intent mConnection = new Intent(AccountActivity.this, ConnectionReportActivity.class);
                startActivity(mConnection);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.logout_layout:
                Hawk.delete("token");
                Intent mLogin = new Intent (AccountActivity.this, LoginActivity.class);
                finish();
                startActivity(mLogin);
                break;

            case R.id.menu_icon:
                mAccountInfoDrawerLayout.openDrawer(Gravity.END);
        }
    }
}

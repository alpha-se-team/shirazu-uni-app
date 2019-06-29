package com.example.shiraz_uni_app.Event.SingleEvent;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.ablanco.zoomy.Zoomy;
import com.example.shiraz_uni_app.Event.AllEvents.EventsActivity;
import com.example.shiraz_uni_app.Event.Event;
import com.example.shiraz_uni_app.Login.LoginActivity;
import com.example.shiraz_uni_app.MainActivity;
import com.example.shiraz_uni_app.R;
import com.example.shiraz_uni_app.Utility.JalaliCalendar;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class SingleEventActivity extends AppCompatActivity implements View.OnClickListener, Observer {

    private int mId = 0;
    private String mContext = "";
    private Date mDate;
    private Bitmap mImageAddress;
    private ImageButton add_remove;
    private Event mEvent;
    private boolean mSaved;
    private String mJsonEvent;
    private String mTitle;
    private ImageView mEventPhoto;
    private ImageView mBack;
    private TextView mEventTitle;
    private TextView mEventText;
    private TextView mEventDate;
    private ArrayList<Integer> mSavedEventsId = new ArrayList<>();
    private ProgressDialog progressDialog;
    private Bitmap mPhotoBitmap;
    private SingleEventModel mModel = new SingleEventModel();
    private boolean mConnectionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mJsonEvent = extras.getString("event");
        }
        mEvent = new Gson().fromJson(mJsonEvent, Event.class);

        mSavedEventsId = Hawk.get("Saved");

        mModel.addObserver(this);

        mTitle = mEvent.getmTitle();
        mId = mEvent.getmId();
        mSaved = mSavedEventsId.contains(mId);
        mContext = mEvent.getContext();
        mImageAddress = mEvent.getmImageAddress();
        mDate = mEvent.getDate();

        mConnectionStatus = MainActivity.checkInternetConnection(this);

        if (mConnectionStatus) {
            progressDialog = new ProgressDialog(SingleEventActivity.this, R.style.MyAlertDialogStyle);
            progressDialog.setMessage("لطفا صبر کنید ...");
            progressDialog.show();
            mModel.getEventImage(mId);
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(SingleEventActivity.this); //the current class
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
        if (mImageAddress == null){
            mModel.getEventImage(mId);
        }else{
            System.out.println("image is here5 :)");
            progressDialog.cancel();
            mEventPhoto.setImageBitmap(mImageAddress);
        }
        add_remove = findViewById(R.id.add_remove);
        add_remove.setImageResource(mSaved ? R.drawable.remove_animation : R.drawable.add_animation);
        add_remove.setOnClickListener(this);
        mEventTitle = findViewById(R.id.event_title);
        mEventTitle.setText(mTitle);
        mEventPhoto = findViewById(R.id.event_pic);
        mBack = findViewById(R.id.go_back_button);
        mBack.setOnClickListener(this);
        mEventText = findViewById(R.id.event_text);
        mEventText.setText(mContext);
        mEventDate = findViewById(R.id.event_date);

        JalaliCalendar.YearMonthDate yearMonthDate = JalaliCalendar.gregorianToJalali(new JalaliCalendar.YearMonthDate(
                mDate.getYear(),
                mDate.getMonth(),
                mDate.getDay()
        ));

        mEventDate.setText("تاریخ برگزاری: " + yearMonthDate.toString());

        /*Zoom Feature*/
        ImageView event_view = findViewById(R.id.event_pic);
        // event_view.setImageResource(R.drawable.event_pic);
        Zoomy.Builder builder = new Zoomy.Builder(this).target(event_view);
        builder.register();

    }

    public void addFavorite(View view, Event mEvent){

        if(!mSavedEventsId.contains(mEvent.getmId())){
            mSavedEventsId.add(mEvent.getmId());
            System.out.println("this event saved!");
            add_remove.setImageResource(R.drawable.add_animation);
            ((AnimatedVectorDrawable) add_remove.getDrawable()).start();
        } else{
            mSavedEventsId.remove(mSavedEventsId.indexOf(mEvent.getmId()));
            System.out.println("this event deleted!");
            add_remove.setImageResource(R.drawable.remove_animation);
            ((AnimatedVectorDrawable) add_remove.getDrawable()).start();
        }
        Hawk.put("Saved", mSavedEventsId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.go_back_button):
                finish();
                break;

            case (R.id.add_remove):
                addFavorite(v, mEvent);
                break;
        }
    }
    private Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    @Override
    public void update(Observable o, Object arg) {
        progressDialog.cancel();
        String mPicString = mModel.getmImage();
        mPhotoBitmap = decodeBase64(mPicString);
        mEventPhoto.setImageBitmap(mPhotoBitmap);

    }
}

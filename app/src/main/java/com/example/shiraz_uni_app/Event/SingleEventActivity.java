package com.example.shiraz_uni_app.Event;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.ablanco.zoomy.Zoomy;
import com.example.shiraz_uni_app.R;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import java.util.ArrayList;
import java.util.Date;

public class SingleEventActivity extends AppCompatActivity implements View.OnClickListener {

    private int mId = 0;
    private String mContext = "";
    private Date mDate;
    private String mImageAddress;
    private boolean add_state = false; // add or remove event from favorites ... default= not added yet
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            mJsonEvent = extras.getString("event");
        }
        mEvent = new Gson().fromJson(mJsonEvent, Event.class);

        mSavedEventsId = Hawk.get("Saved");

        mTitle = mEvent.getmTitle();
        mId = mEvent.getmId();
        mSaved = mSavedEventsId.contains(mId);
        mContext = mEvent.getContext();
        mImageAddress = mEvent.getmImageAddress();
        mDate = mEvent.getDate();

        add_remove = findViewById(R.id.add_remove);
        add_remove.setImageResource(mSaved ? R.drawable.remove_animation : R.drawable.add_animation);
        System.out.println("saved: "+ String.valueOf(mSaved));
        add_remove.setOnClickListener(this);
        mEventTitle = findViewById(R.id.event_title);
        mEventTitle.setText(mTitle);
        mEventPhoto = findViewById(R.id.event_pic);
//        mEventPhoto.setImageURI(Uri.parse(mImageAddress));
        mBack = findViewById(R.id.go_back_buttun);
        mBack.setOnClickListener(this);
        mEventText = findViewById(R.id.event_text);
        mEventText.setText(mContext);
        mEventDate = findViewById(R.id.event_date);
        mEventDate.setText(mDate.toString());

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
            case (R.id.go_back_buttun):
                finish();
                break;

            case (R.id.add_remove):
                addFavorite(v, mEvent);
                break;
        }
    }
}

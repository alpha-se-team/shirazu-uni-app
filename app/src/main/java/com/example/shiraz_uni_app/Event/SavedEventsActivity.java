package com.example.shiraz_uni_app.Event;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shiraz_uni_app.R;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class SavedEventsActivity extends AppCompatActivity implements View.OnClickListener, Observer {
    private ImageView mback ;
    private RecyclerView mRecyclerView ;
    private RecyclerView.Adapter mAdapter ;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView mBack;
    private ImageView mSavedEvents;
    private TextView mNoEventText;
    private SavedEventsModel mModel = new SavedEventsModel();
    private static ArrayList<Event> mEvents = new ArrayList<>();
    private Boolean mThereIsSavedEvents;
    private ArrayList<Integer> mSavedEventsId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);

        mback = findViewById(R.id.back_button);
        mback.setOnClickListener(this);
        mNoEventText = findViewById(R.id.no_events);

        mSavedEventsId = Hawk.get("Saved");
        mThereIsSavedEvents = mSavedEventsId.size() > 0 ? true : false;
        if(mThereIsSavedEvents){
            mNoEventText.setText("در حال بارگزاری");
        }else{
            mNoEventText.setText("رویدادی وجود ندارد");
        }

        mModel.addObserver(this);
        mModel.getEvents();

        mRecyclerView = findViewById(R.id.saved_event);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SavedEventsAdapter(mEvents);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.back_button):
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("resume");
        mModel.getEvents();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (SavedEventsAdapter.mEvents.size() > 0){
            mNoEventText.setVisibility(View.GONE);
        }else{
            mNoEventText.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    public static void addEvent(Event mEvent) {
        mEvents.add(mEvent);
    }

    public static void clearEvent(){
        mEvents.clear();
    }
}

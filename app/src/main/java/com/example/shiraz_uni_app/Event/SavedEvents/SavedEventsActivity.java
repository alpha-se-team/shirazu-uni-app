package com.example.shiraz_uni_app.Event.SavedEvents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shiraz_uni_app.Event.Event;
import com.example.shiraz_uni_app.R;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class SavedEventsActivity extends AppCompatActivity implements View.OnClickListener, Observer {
    private RecyclerView mRecyclerView ;
    private static RecyclerView.Adapter mAdapter ;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView mBack;
    private static TextView mNoEventText;
    private Boolean mThereIsSavedEvents;
    private SavedEventsModel mModel = new SavedEventsModel();
    private static ArrayList<Event> mEvents = new ArrayList<>();
    private ArrayList<Integer> mSavedEventsId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);

        mBack = findViewById(R.id.back_button);
        mBack.setOnClickListener(this);

        mModel.addObserver(this);

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
        mModel.getEvents();
        mAdapter.notifyDataSetChanged();

        mNoEventText = findViewById(R.id.no_events);
        mNoEventText.setVisibility(View.VISIBLE);

        mSavedEventsId = Hawk.get("Saved");
        mThereIsSavedEvents = mSavedEventsId.size() > 0 ? true : false;
        if(mThereIsSavedEvents){
            mNoEventText.setText("در حال بارگزاری");
        }else{
            mNoEventText.setText("رویدادی وجود ندارد");
        }

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

    public static void addEvent(Event mEvent){
        mEvents.add(mEvent);
    }

    public static void clearEvent(){
        mEvents.clear();
    }

    public static void noEvent() {
        mNoEventText.setVisibility(View.VISIBLE);
        mNoEventText.setText("رویدادی وجود ندارد");
    }

    public static void changeDataSet() {
        mAdapter.notifyDataSetChanged();
    }
}

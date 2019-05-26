package com.example.shiraz_uni_app.Event;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.shiraz_uni_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class EventsActivity extends AppCompatActivity implements View.OnClickListener, Observer {
    private RecyclerView mRecyclerView ;
    private RecyclerView.Adapter mAdapter ;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView mBack;
    private ImageView mSavedEvents;
    private ImageButton mAddOrRemove;
    private boolean favorite_state = false;
    private EventsModel mModel = new EventsModel();

    private static ArrayList<Event> mEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
        View myLayout = LayoutInflater.from(this).inflate(R.layout.activity_event_item,null);

        mModel.addObserver(this);
        mModel.getEvents();

        mBack = findViewById(R.id.event_back);
        mBack.setOnClickListener(this);
        mAddOrRemove = findViewById(R.id.event_add_image_button);

        mRecyclerView = findViewById(R.id.all_events);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new EventsAdapter(mEvents);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //mSavedEvents = findViewById(R.id.saved_event);
        //mSavedEvents.setOnClickListener(this);

    }

    public ArrayList<Event> getEvents() {
        return mEvents;
    }

    public static void addEvent(Event mEvent) {
        mEvents.add(mEvent);
    }

    public static void clearEvent(){
        mEvents.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.saved_event):
                Intent mSavedEventIntent = new Intent(EventsActivity.this , SavedEventsActivity.class);
                startActivity(mSavedEventIntent);
                break;

            case (R.id.event_back):
                finish();
                break;
        }
    }
    public void addFavorite(View view){
        if(!favorite_state){
            favorite_state = !favorite_state;
            mAddOrRemove.setImageResource(R.drawable.add_animation);
            ((AnimatedVectorDrawable) mAddOrRemove.getDrawable()).start();
        }
        else if (favorite_state) {
            favorite_state = !favorite_state;
            mAddOrRemove.setImageResource(R.drawable.remove_animation);
            ((AnimatedVectorDrawable) mAddOrRemove.getDrawable()).start();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        mAdapter.notifyDataSetChanged();
    }

}


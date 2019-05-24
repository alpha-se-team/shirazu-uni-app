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

import com.example.shiraz_uni_app.R;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView ;
    private RecyclerView.Adapter mAdapter ;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button mBack;
    private ImageButton mSavedEvents;
    private ImageButton mAddOrRemove;
    private boolean favorite_state = false;

    private ArrayList<Event> mEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);

        View myLayout = LayoutInflater.from(this).inflate(R.layout.activity_event_item,null);

        mBack = findViewById(R.id.back);
        mAddOrRemove = findViewById(R.id.event_add_image_button);

        mEvents.add(new Event("111111\nthis is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4", 1 ) ) ;
        mEvents.add(new Event("222222\nthis is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4", 2 ) ) ;
        mEvents.add(new Event("333333\nthis is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4", 3 ) ) ;
        mEvents.add(new Event("444444\nthis is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4", 4 ) ) ;
        mEvents.add(new Event("555555\nthis is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4", 5 ) ) ;

        mRecyclerView = findViewById(R.id.all_events);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new EventsAdapter(mEvents);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //mSavedEvents = findViewById(R.id.saved_event);
        //mSavedEvents.setOnClickListener(this);

    }

    public ArrayList<Event> getmEvents() {
        return mEvents;
    }

    public void setmEvents(ArrayList<Event> mEvents) {
        this.mEvents = mEvents;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.saved_event):
                Intent mSavedEventIntent = new Intent(EventsActivity.this , SavedEventsActivity.class);
                startActivity(mSavedEventIntent);
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
}

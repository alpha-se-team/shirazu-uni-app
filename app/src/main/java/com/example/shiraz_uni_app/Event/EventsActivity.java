package com.example.shiraz_uni_app.Event;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.shiraz_uni_app.R;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView ;
    private RecyclerView.Adapter mAdapter ;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button mBack;
    private ArrayList<Event> mEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);

        mBack = findViewById(R.id.back);

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


    }

    public ArrayList<Event> getmEvents() {
        return mEvents;
    }

    public void setmEvents(ArrayList<Event> mEvents) {
        this.mEvents = mEvents;
    }
}

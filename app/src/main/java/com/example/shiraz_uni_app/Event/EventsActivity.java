package com.example.shiraz_uni_app.Event;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.shiraz_uni_app.R;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView ;
    private RecyclerView.Adapter mAdapter ;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);

        mBack = findViewById(R.id.back);

        ArrayList<Event> mEvents = new ArrayList<>();
        mEvents.add(new Event("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;
        mEvents.add(new Event("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;
        mEvents.add(new Event("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;
        mEvents.add(new Event("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;
        mEvents.add(new Event("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;

        mRecyclerView = findViewById(R.id.all_events);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new EventsAdapter(mEvents);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}

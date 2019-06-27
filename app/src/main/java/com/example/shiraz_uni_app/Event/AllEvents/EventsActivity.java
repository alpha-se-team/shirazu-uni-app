package com.example.shiraz_uni_app.Event.AllEvents;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shiraz_uni_app.Event.Event;
import com.example.shiraz_uni_app.Event.SavedEvents.SavedEventsActivity;
import com.example.shiraz_uni_app.Login.LoginActivity;
import com.example.shiraz_uni_app.MainActivity;
import com.example.shiraz_uni_app.R;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class EventsActivity extends AppCompatActivity implements View.OnClickListener, Observer {
    private RecyclerView mRecyclerView ;
    private RecyclerView.Adapter mAdapter ;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView mBack;
    private ImageView mSavedEvents;
    private boolean mConnectionStatus;
    private ProgressDialog progressDialog;
    private EventsModel mModel = new EventsModel();
    private static ArrayList<Event> mEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
        View myLayout = LayoutInflater.from(this).inflate(R.layout.activity_event_item,null);
        mConnectionStatus = MainActivity.checkInternetConnection(this);

        if(mConnectionStatus) {
            progressDialog = new ProgressDialog(EventsActivity.this, R.style.MyAlertDialogStyle);
            progressDialog.setMessage("لطفا صبر کنید ...");
            progressDialog.show();
        }else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(EventsActivity.this); //the current class
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

        mModel.addObserver(this);
        mModel.getEvents();


//        else {
//            final AlertDialog.Builder builder = new AlertDialog.Builder(EventsActivity.this); //the current class
//            View dialogView = getLayoutInflater().inflate(R.layout.no_internet_connection_dialog, null);
//            TextView close = dialogView.findViewById(R.id.close);
//            builder.setView(dialogView);
//            final AlertDialog dialog = builder.create();
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialog.show();
//            close.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.cancel();
//                }
//            });
//        }

        mBack = findViewById(R.id.event_back);
        mBack.setOnClickListener(this);
        mSavedEvents = findViewById(R.id.saved_events_image_view);
        mSavedEvents.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.all_events);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new EventsAdapter(mEvents);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
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

            case (R.id.saved_events_image_view):
                Intent mSavedEvents = new Intent(EventsActivity.this, SavedEventsActivity.class);
                startActivity(mSavedEvents);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("hello this means that events have been downloaded");
        progressDialog.cancel();
        mAdapter.notifyDataSetChanged();
    }

}


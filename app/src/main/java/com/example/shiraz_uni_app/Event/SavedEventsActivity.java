package com.example.shiraz_uni_app.Event;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shiraz_uni_app.R;

public class SavedEventsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mback ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);
        mback = findViewById(R.id.savaed_events_back_button);
        mback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.savaed_events_back_button):
                Intent intent = new Intent( SavedEventsActivity.this , EventsActivity.class);
                startActivity(intent);
                break;
        }
    }
}

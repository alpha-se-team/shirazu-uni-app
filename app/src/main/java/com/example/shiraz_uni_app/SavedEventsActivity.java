package com.example.shiraz_uni_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SavedEventsActivity extends AppCompatActivity {
    private Button mback ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);
        mback = findViewById(R.id.savaed_events_back_button);
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( SavedEventsActivity.this , AllEvents.class);
                startActivity(intent);
            }
        });
    }
}

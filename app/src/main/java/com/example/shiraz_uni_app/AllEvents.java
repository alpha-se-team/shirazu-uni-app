package com.example.shiraz_uni_app;

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

import java.util.ArrayList;


public class AllEvents extends AppCompatActivity {
    private RecyclerView recyclerView ;
    private RecyclerView.Adapter adapter ;
    private RecyclerView.LayoutManager layoutManager;
    private Button mback ;
    private ImageButton mSavedEvents ;
    ImageButton add_remove ;
    boolean add_state = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
        mback = findViewById(R.id.events_back_button);
        mSavedEvents = findViewById(R.id.saved_events_image_button);
        View myLayout = LayoutInflater.from(this).inflate(R.layout.activity_event_item,null);
        add_remove =myLayout.findViewById(R.id.event_add_image_button);
        ArrayList<com.example.shiraz_uni_app.Event.Example> examples = new ArrayList<>();
        examples.add(new com.example.shiraz_uni_app.Event.Example("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;
        examples.add(new com.example.shiraz_uni_app.Event.Example("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;
        examples.add(new com.example.shiraz_uni_app.Event.Example("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;
        examples.add(new com.example.shiraz_uni_app.Event.Example("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;
        examples.add(new com.example.shiraz_uni_app.Event.Example("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;

        recyclerView = findViewById(R.id.all_events);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ExampleAdapter(examples);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mSavedEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntenr = new Intent(AllEvents.this , SavedEventsActivity.class);
                startActivity(mIntenr);
            }
        });


    }
    public void addFavorite(View view){
        if(!add_state){
            add_state = !add_state;
            add_remove.setImageResource(R.drawable.add_animation);
            ((AnimatedVectorDrawable) add_remove.getDrawable()).start();
        }
        else if (add_state) {
            add_state = !add_state;
            add_remove.setImageResource(R.drawable.remove_animation);
            ((AnimatedVectorDrawable) add_remove.getDrawable()).start();
        }
    }
}

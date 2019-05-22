package com.example.shiraz_uni_app.Event;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.shiraz_uni_app.R;

import java.util.ArrayList;


public class AllEvents extends AppCompatActivity {
    private RecyclerView recyclerView ;
    private RecyclerView.Adapter adapter ;
    private RecyclerView.LayoutManager layoutManager;
    Button back ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
        back = findViewById(R.id.back);
        ArrayList<Example> examples = new ArrayList<>();
        examples.add(new Example("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;
        examples.add(new Example("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;
        examples.add(new Example("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;
        examples.add(new Example("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;
        examples.add(new Example("this is line 1 \nthis is line 2" ,"تاریخ برگزاری:1397/2/4" ) ) ;

        recyclerView = findViewById(R.id.all_events);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ExampleAdapter(examples);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}

package com.example.shiraz_uni_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<com.example.shiraz_uni_app.Event.Example> mExample ;
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mycontext ;
        public TextView mydate ;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mycontext = itemView.findViewById(R.id.event_context);
            mydate = itemView.findViewById(R.id.event_date);

        }
    }
    public ExampleAdapter (ArrayList<com.example.shiraz_uni_app.Event.Example> exampleList){
        mExample = exampleList ;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_event_item, parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return  evh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        com.example.shiraz_uni_app.Event.Example currentItem = mExample.get(i);
        exampleViewHolder.mycontext.setText(currentItem.getContext());
        exampleViewHolder.mydate.setText(currentItem.getDate());
    }

    @Override
    public int getItemCount() {
        return mExample.size();
    }
}

package com.example.shiraz_uni_app.Event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shiraz_uni_app.R;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {
    private ArrayList<Event> mEvents ;

    public static class EventViewHolder extends RecyclerView.ViewHolder{
        public TextView mContext ;
        public TextView mDate ;
        public EventViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.findViewById(R.id.context);
            mDate = itemView.findViewById(R.id.date);

        }
    }
    public EventsAdapter(ArrayList<Event> eventList){
        mEvents = eventList ;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_event_item, parent,false);
        EventViewHolder evh = new EventViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, final int i) {
        Event currentItem = mEvents.get(i);
        eventViewHolder.mContext.setText(currentItem.getContext());
        eventViewHolder.mDate.setText(currentItem.getDate());

        eventViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mEventIntent = new Intent(v.getContext(), SingleEvent.class);
                mEventIntent.putExtra("id", mEvents.get(i).getmId());
                mEventIntent.putExtra("context", mEvents.get(i).getContext());
                mEventIntent.putExtra("date", mEvents.get(i).getDate());
                mEventIntent.putExtra("image_address", mEvents.get(i).getmImageAddress());
                v.getContext().startActivity(mEventIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }
}

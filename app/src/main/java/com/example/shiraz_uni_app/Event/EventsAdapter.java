package com.example.shiraz_uni_app.Event;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.findViewById(R.id.context);
            mDate = itemView.findViewById(R.id.date);

        }
    }
    public EventsAdapter(ArrayList<Event> eventList){
        mEvents = eventList ;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_event_item, parent,false);
        EventViewHolder evh = new EventViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {
        Event currentItem = mEvents.get(i);
        eventViewHolder.mContext.setText(currentItem.getContext());
        eventViewHolder.mDate.setText(currentItem.getDate());
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }
}

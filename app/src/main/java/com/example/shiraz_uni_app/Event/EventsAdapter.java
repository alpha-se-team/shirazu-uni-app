package com.example.shiraz_uni_app.Event;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.shiraz_uni_app.R;
import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {
    private ArrayList<Event> mEvents ;
    private int lastPosition = -1;

    public class EventViewHolder extends RecyclerView.ViewHolder{
        public TextView mContext;
        public TextView mDate;
        public ImageButton mSaved;

        public EventViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.findViewById(R.id.event_context);
            mDate = itemView.findViewById(R.id.event_date);
            mSaved = itemView.findViewById(R.id.event_add_image_button);

            mSaved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2019-05-24 set on click listener
                }
            });

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
                Intent mEventIntent = new Intent(v.getContext(), SingleEventActivity.class);
                mEventIntent.putExtra("id", mEvents.get(i).getmId());
                mEventIntent.putExtra("context", mEvents.get(i).getContext());
                mEventIntent.putExtra("date", mEvents.get(i).getDate());
                mEventIntent.putExtra("image_address", mEvents.get(i).getmImageAddress());
                v.getContext().startActivity(mEventIntent);
            }
        });
        setAnimation(eventViewHolder.itemView, i);

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }
}

package com.example.shiraz_uni_app.Event.AllEvents;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shiraz_uni_app.Event.Event;
import com.example.shiraz_uni_app.Event.SingleEvent.SingleEventActivity;
import com.example.shiraz_uni_app.R;
import com.example.shiraz_uni_app.Utility.JalaliCalendar;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {
    private ArrayList<Event> mEvents ;
    private int lastPosition = -1;
    private boolean favorite_state;
    private ImageButton mAddOrRemove;
    private ArrayList<Integer> mSavedEventsId = new ArrayList<>();


    public class EventViewHolder extends RecyclerView.ViewHolder{
        public TextView mContext;
        public TextView mDate;
        public ImageButton mSaveButton;

        public EventViewHolder(final View itemView) {
            super(itemView);
            mContext = itemView.findViewById(R.id.event_context);
            mDate = itemView.findViewById(R.id.event_date);
            mSaveButton = itemView.findViewById(R.id.event_add_image_button);

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

        mSavedEventsId = Hawk.get("Saved");

        Event currentItem = mEvents.get(i);
        eventViewHolder.mContext.setText(currentItem.getContext());
        System.out.println(JalaliCalendar.gregorianToJalali(new JalaliCalendar.YearMonthDate(currentItem.getDate().getYear(),
                                                                                                currentItem.getDate().getMonth(),
                                                                                                    currentItem.getDate().getDay())));

        eventViewHolder.mDate.setText(currentItem.getDate().toString());
        eventViewHolder.mSaveButton.setImageResource(mSavedEventsId.contains(mEvents.get(i).getmId()) ?
                                                                            R.drawable.remove_animation :
                                                                                R.drawable.add_animation);

        eventViewHolder.mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEvents.get(i).setmSaved(!mEvents.get(i).ismSaved());
                addFavorite(v, mEvents.get(i));
            }
        });

        eventViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mEventIntent = new Intent(v.getContext(), SingleEventActivity.class);
                mEventIntent.putExtra("event", new Gson().toJson(mEvents.get(i)));
                v.getContext().startActivity(mEventIntent);
            }
        });
        setAnimation(eventViewHolder.itemView, i);

    }

    private void setAnimation(View viewToAnimate, int position) {
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

    public void addFavorite(View view, Event event){
        mAddOrRemove = view.findViewById(R.id.event_add_image_button);

        try {
            mSavedEventsId = Hawk.get("Saved");
        }catch (Exception e){
            System.out.println("there is no Saved key in Hawk dictionary");
        }

        favorite_state = mSavedEventsId.contains(event.getmId());

        if(!favorite_state){
            System.out.println("add");
            mSavedEventsId.add(event.getmId());
            Hawk.put("Saved", mSavedEventsId);
            mAddOrRemove.setImageResource(R.drawable.add_animation);
            ((AnimatedVectorDrawable) mAddOrRemove.getDrawable()).start();
        }
        else {
            mSavedEventsId.remove(mSavedEventsId.indexOf(event.getmId()));
            Hawk.put("Saved", mSavedEventsId);
            System.out.println("remove");
            mAddOrRemove.setImageResource(R.drawable.remove_animation);
            ((AnimatedVectorDrawable) mAddOrRemove.getDrawable()).start();
        }
    }
}

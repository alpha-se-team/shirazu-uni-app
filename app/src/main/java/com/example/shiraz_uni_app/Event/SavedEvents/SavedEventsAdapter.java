package com.example.shiraz_uni_app.Event.SavedEvents;

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
import com.example.shiraz_uni_app.Utility.Animations;
import com.example.shiraz_uni_app.Utility.JalaliCalendar;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import java.util.ArrayList;

public class SavedEventsAdapter extends RecyclerView.Adapter<SavedEventsAdapter.EventViewHolder> {
    public static ArrayList<Event> mEvents;
    private int lastPosition = -1;
    private boolean favorite_state;
    private ImageButton mAddOrRemove;
    private ArrayList<Integer> mSavedEventsId = new ArrayList<>();

    public class EventViewHolder extends RecyclerView.ViewHolder {
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

    public SavedEventsAdapter(ArrayList<Event> eventList) {
        mEvents = eventList;
    }

    @Override
    public SavedEventsAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_event_item, parent, false);
        SavedEventsAdapter.EventViewHolder evh = new SavedEventsAdapter.EventViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final SavedEventsAdapter.EventViewHolder savedEventViewHolder, final int i) {
        mSavedEventsId = Hawk.get("Saved");

        Event currentItem = mEvents.get(i);
        savedEventViewHolder.mContext.setText(currentItem.getContext());

        JalaliCalendar.YearMonthDate yearMonthDate = JalaliCalendar.gregorianToJalali(new JalaliCalendar.YearMonthDate(
                currentItem.getDate().getYear(),
                currentItem.getDate().getMonth(),
                currentItem.getDate().getDay()
        ));

        savedEventViewHolder.mDate.setText("تاریخ برگزاری: " + yearMonthDate.toString());

        savedEventViewHolder.mSaveButton.setImageResource(mSavedEventsId.contains(mEvents.get(i).getmId()) ?
                R.drawable.remove_animation :
                R.drawable.add_animation);

        savedEventViewHolder.mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEvents.get(i).setmSaved(!mEvents.get(i).ismSaved());
                addFavorite(v, mEvents.get(i), savedEventViewHolder.itemView);
            }
        });

        savedEventViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mEventIntent = new Intent(v.getContext(), SingleEventActivity.class);
                mEventIntent.putExtra("event", new Gson().toJson(mEvents.get(i)));
                v.getContext().startActivity(mEventIntent);
            }
        });
        setAnimation(savedEventViewHolder.itemView, i);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public void addFavorite(View view, Event event, View itemView){
        mAddOrRemove = view.findViewById(R.id.event_add_image_button);

        try {
            mSavedEventsId = Hawk.get("Saved");
        }catch (Exception e){
            System.out.println("there is no Saved key in Hawk dictionary");
        }

        favorite_state = mSavedEventsId.contains(event.getmId());

        if(!favorite_state){
            mSavedEventsId.add(event.getmId());
            mAddOrRemove.setImageResource(R.drawable.add_animation);
            ((AnimatedVectorDrawable) mAddOrRemove.getDrawable()).start();
        }
        else {
            System.out.println("Animation");
            Animation mFade = Animations.fade(itemView);
            itemView.startAnimation(mFade);

            mSavedEventsId.remove(mSavedEventsId.indexOf(event.getmId()));
            mEvents.remove(mEvents.indexOf(event));
            mAddOrRemove.setImageResource(R.drawable.remove_animation);
            ((AnimatedVectorDrawable) mAddOrRemove.getDrawable()).start();
        }
        Hawk.put("Saved", mSavedEventsId);
        if(mSavedEventsId.size() == 0) {
            SavedEventsActivity.noEvent();
        }
        else {
            SavedEventsActivity.changeDataSet();
        }
    }
}

package com.example.shiraz_uni_app.Event.SavedEvents;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.shiraz_uni_app.Event.Event;
import com.example.shiraz_uni_app.Event.SavedEvents.SavedEventsActivity;
import com.example.shiraz_uni_app.Utility.JalaliCalendar;
import com.orhanobut.hawk.Hawk;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

import static java.lang.Integer.getInteger;

public class SavedEventsModel extends Observable {

    private ArrayList<Integer> mSavedEvents = new ArrayList<>();
    private JSONObject mEventJsonObject;

    public void getEvents(){
        mSavedEvents = Hawk.get("Saved");
        SavedEventsActivity.clearEvent();
        getEventsApiCall(mSavedEvents);
    }

    private void getEventsApiCall(ArrayList<Integer> mSavedEvents) {
        for (int i=0; i<mSavedEvents.size(); i++)
            AndroidNetworking.get("https://young-castle-19921.herokuapp.com/apiv1/event/" + mSavedEvents.get(i) + '/')
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                mEventJsonObject = response.getJSONObject("event");

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                                int year = Integer.valueOf(simpleDateFormat.parse(mEventJsonObject.getString("created_at")).toString().split(" ")[5]);
                                int month = JalaliCalendar.monthOfYear(simpleDateFormat.parse(mEventJsonObject.getString("created_at")).toString().split(" ")[1]);
                                int day = Integer.valueOf(simpleDateFormat.parse(mEventJsonObject.getString("created_at")).toString().split(" ")[2]);

                                Date date = new Date(year, month, day);

                                SavedEventsActivity.addEvent(new Event(mEventJsonObject.getString("text"),
                                        date,
                                        mEventJsonObject.getInt("id"),
                                        mEventJsonObject.getString("title")));


                                setChanged();
                                notifyObservers();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            if (error.getErrorCode() == 404) {

                            }
                        }
                    });
    }


}

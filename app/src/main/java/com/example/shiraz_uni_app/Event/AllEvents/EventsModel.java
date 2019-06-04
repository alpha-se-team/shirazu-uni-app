package com.example.shiraz_uni_app.Event.AllEvents;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.shiraz_uni_app.Event.AllEvents.EventsActivity;
import com.example.shiraz_uni_app.Event.Event;
import com.example.shiraz_uni_app.Utility.JalaliCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;

public class EventsModel extends Observable {

    private JSONArray mEventsJsonArray;

    void getEvents(){

        JSONObject jsonObject = new JSONObject();
        eventApiCall(jsonObject);
    }

    private void eventApiCall (final JSONObject jsonObject){

        AndroidNetworking.get("https://young-castle-19921.herokuapp.com/apiv1/event/all/")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            mEventsJsonArray = response.getJSONArray("events");
                            EventsActivity.clearEvent();

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                            for(int i=0; i<mEventsJsonArray.length(); i++) {
                                JSONObject mEachEvent = mEventsJsonArray.getJSONObject(i);

                                System.out.println(response);

                                int year = Integer.valueOf(simpleDateFormat.parse(mEachEvent.getString("created_at")).toString().split(" ")[5]);
                                int month = JalaliCalendar.monthOfYear(simpleDateFormat.parse(mEachEvent.getString("created_at")).toString().split(" ")[1]);
                                int day = Integer.valueOf(simpleDateFormat.parse(mEachEvent.getString("created_at")).toString().split(" ")[2]);

                                Date date = new Date(year, month, day);

                                EventsActivity.addEvent( new Event( mEachEvent.getString("text"),
                                                                    date,
                                                                    mEachEvent.getInt("id"),
                                                                    mEachEvent.getString("title")));
                            }

                        setChanged();
                        notifyObservers();
                        } catch (JSONException e) {

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

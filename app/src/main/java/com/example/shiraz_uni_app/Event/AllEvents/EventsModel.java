package com.example.shiraz_uni_app.Event.AllEvents;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.shiraz_uni_app.Event.AllEvents.EventsActivity;
import com.example.shiraz_uni_app.Event.Event;

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

                            System.out.println(simpleDateFormat.parse("2019-05-26T16:41:18.244169Z").getMonth());
                            for(int i=0; i<mEventsJsonArray.length(); i++) {
                                JSONObject mEachEvent = mEventsJsonArray.getJSONObject(i);
                                Date date = new Date(simpleDateFormat.parse(mEachEvent.getString("created_at")).getYear(),
                                                        simpleDateFormat.parse(mEachEvent.getString("created_at")).getMonth(),
                                                            simpleDateFormat.parse(mEachEvent.getString("created_at")).getDay());

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

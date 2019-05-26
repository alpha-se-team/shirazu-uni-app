package com.example.shiraz_uni_app.Event;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

                            for(int i=0; i<mEventsJsonArray.length(); i++) {
                                JSONObject mEachEvent = mEventsJsonArray.getJSONObject(i);
                                EventsActivity.addEvent( new Event( mEachEvent.getString("text"),
                                                                    mEachEvent.getString("created_at"),
                                                                    mEachEvent.getInt("id")));
                            }

                        setChanged();
                        notifyObservers();
                        } catch (JSONException e) {

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

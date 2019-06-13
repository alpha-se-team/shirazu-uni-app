package com.example.shiraz_uni_app.Event.SingleEvent;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Observable;

public class SingleEventModel extends Observable {

    private String mImage;

    public void getEventImage(int mId){
        JSONObject jsonObject = new JSONObject();
        getEventImageApiCall(jsonObject, mId);

    }

    private void getEventImageApiCall (JSONObject jsonObject, int mId) {

        AndroidNetworking.get("https://young-castle-19921.herokuapp.com/apiv1/event/"+mId+"/image/")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject mEventImageJsonObject = response.getJSONObject("img");
                            mImage = mEventImageJsonObject.getString("img");
                            System.out.println(mImage.length());
                            setChanged();
                            notifyObservers();
                        } catch (JSONException e) {

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        System.out.println("error");
                        if (error.getErrorCode() == 404) {

                        }
                    }
                });
    }

    public String getmImage() {
        return mImage;
    }
}

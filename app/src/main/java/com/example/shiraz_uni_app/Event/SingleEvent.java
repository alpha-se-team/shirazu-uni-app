package com.example.shiraz_uni_app.Event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.ablanco.zoomy.Zoomy;
import com.example.shiraz_uni_app.R;

public class SingleEvent extends AppCompatActivity {

    private int mId = 0;
    private String mContext = "";
    private String mDate = "";
    private String mImageAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            mId = extras.getInt("id");
            mContext = extras.getString("context");
            mDate = extras.getString("date");
            mImageAddress =  extras.getString("image_address");
        }

        Log.i("id", "id: " + Integer.toString(mId) + "\ncontext: " + mContext);
        /*Zoom Feature*/
        ImageView event_view = (ImageView) findViewById(R.id.event_pic);
        // event_view.setImageResource(R.drawable.event_pic);
        Zoomy.Builder builder = new Zoomy.Builder(this).target(event_view);
        builder.register();
    }
}

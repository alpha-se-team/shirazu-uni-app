package com.example.shiraz_uni_app.Event;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ablanco.zoomy.Zoomy;
import com.example.shiraz_uni_app.R;

public class SingleEvent extends AppCompatActivity {

    boolean add_state = false; // add or remove event from favorites ... default= not added yet
    ImageButton add_remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);

        add_remove = (ImageButton) findViewById(R.id.add_remove);

        /*Zoom Feature*/
        ImageView event_view = (ImageView) findViewById(R.id.event_pic);
        // event_view.setImageResource(R.drawable.event_pic);
        Zoomy.Builder builder = new Zoomy.Builder(this).target(event_view);
        builder.register();

    }

    public void addFavorite(View view){
        if(!add_state){
            add_state = !add_state;
            add_remove.setImageResource(R.drawable.add_animation);
            ((AnimatedVectorDrawable) add_remove.getDrawable()).start();
        }
        else if (add_state) {
            add_state = !add_state;
            add_remove.setImageResource(R.drawable.remove_animation);
            ((AnimatedVectorDrawable) add_remove.getDrawable()).start();
        }
    }
}

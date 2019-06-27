package com.example.shiraz_uni_app.Utility;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.shiraz_uni_app.R;

public class Animations {

    public static Animation fade(View view){
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade);
        return animation;
    }

}

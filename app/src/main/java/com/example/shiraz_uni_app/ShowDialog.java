package com.example.shiraz_uni_app;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class ShowDialog {

    // SuccessDialog(Activity.this,"خرید شما با موفقیت انجام شد")
    // SuccessDialog(Activity.this,"ارتباط با موفقیت قطع شد")

    public void NoInternetDialog(Activity activity){

        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_internet_connection_dialog);

       // TextView mText = dialog.findViewById(R.id.textno);
       // mText.setText(message);

        TextView close =  dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    public void SuccessDialog(Activity activity, String message){

        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.success_dialog);

        TextView mText = dialog.findViewById(R.id.success_text);
        mText.setText(message);

        TextView close =  dialog.findViewById(R.id.close_text_view);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }
}

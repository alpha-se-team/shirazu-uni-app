package com.example.shiraz_uni_app;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.LinearLayout;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class BuyTrafficActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_traffic);

        final LinearLayout payment_datail = findViewById(R.id.payment_detail);
        payment_datail.setVisibility(View.GONE);

        MaterialSpinner traffic_selector = findViewById(R.id.traffic_selector);

        traffic_selector.setItems(
                "1 گیگابایت - 20000 ریال" ,
                "2 گیگابایت - 50000 ریال" ,
                "5 گیگابایت - 100000 ریال"
        );

        traffic_selector.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                payment_datail.setVisibility(View.VISIBLE);
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}

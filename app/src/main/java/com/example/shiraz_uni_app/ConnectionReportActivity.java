package com.example.shiraz_uni_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.data.Set;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConnectionReportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_report);


        final AnyChartView connection_report_chart = findViewById(R.id.connection_report_chart);
        connection_report_chart.setVisibility(View.GONE);
        MaterialSpinner month_selector = findViewById(R.id.month_selector);

        month_selector.setItems(
                "فروردین",
                "اردیبهشت",
                "خرداد",
                "تیر",
                "مرداد",
                "شهریور",
                "مهر",
                "آبان",
                "آذر",
                "دی",
                "بهمن",
                "اسفند"
        );

        month_selector.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                getChart(connection_report_chart);
                connection_report_chart.setVisibility(View.VISIBLE);
            }
        });

    }


    public void getChart(AnyChartView chartView) {
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair().yLabel(true);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("میزان ترافیک مصرف شده");

        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> usageData = new ArrayList<>();

        // add Chart Data
        Random rand = new Random();
        for (int day_of_month = 1; day_of_month <= 31; day_of_month++) {
            int download = rand.nextInt(6);
            int upload = rand.nextInt(4);
            usageData.add(new CustomDataEntry(Integer.toString(day_of_month), download, upload));
        }

        Set set = Set.instantiate();
        set.data(usageData);
        Mapping downUsageMapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping upUsageMapping = set.mapAs("{ x: 'x', value: 'value2' }");

        Line download = cartesian.line(downUsageMapping);
        download.name("دانلود");
        download.color("blue");
        download.hovered().markers().enabled(true);
        download.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        download.tooltip()
                .position("left")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);


        Line upload = cartesian.line(upUsageMapping);
        upload.name("آپلود");
        upload.color("red");
        upload.hovered().markers().enabled(true);
        upload.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        upload.tooltip()
                .position("left")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        chartView.setChart(cartesian);
    }


    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }

    }


}
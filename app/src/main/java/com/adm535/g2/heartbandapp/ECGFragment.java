package com.adm535.g2.heartbandapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;

public class ECGFragment extends Fragment {

    private XYPlot plot;
    private Button locButton;
    private TextView bpmText;

    public ECGFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ecg, container, false);
        plot = v.findViewById(R.id.plot);
        locButton = v.findViewById(R.id.location_button);
        bpmText = v.findViewById(R.id.bpm_number);

        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LocationActivity.class);
                startActivity(intent);
            }
        });

        final Number[] domainLabels = {1, 2, 3, 6, 7, 8, 9, 10, 13, 14, 16, 18};
        Number[] series1Numbers = {1, 4, 2, 8, 4, 16, 8, 32, 16, 64};
        Number[] series2Numbers = {5, 2, 10, 5, 20, 10, 40, 20, 80, 40};

        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series 1");
        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series 2");

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);
        LineAndPointFormatter series2Format = new LineAndPointFormatter(Color.CYAN, Color.MAGENTA, Color.YELLOW, null);
        series1Format.setLegendIconEnabled(false);
        series2Format.setLegendIconEnabled(false);

        series2Format.getLinePaint().setPathEffect(new DashPathEffect(new float[]{
                PixelUtils.dpToPix(20),
                PixelUtils.dpToPix(15)
        }, 0));

        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        series2Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        plot.addSeries(series1, series1Format);
        plot.addSeries(series2, series2Format);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object o, @NonNull StringBuffer stringBuffer, @NonNull FieldPosition fieldPosition) {
                int i = Math.round(((Number) o).floatValue());
                return stringBuffer.append(domainLabels[i]);
            }

            @Override
            public Object parseObject(String s, @NonNull ParsePosition parsePosition) {
                return null;
            }
        });
        return v;
    }
}

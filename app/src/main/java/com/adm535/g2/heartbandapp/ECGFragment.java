package com.adm535.g2.heartbandapp;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidplot.util.Redrawer;
import com.androidplot.xy.AdvancedLineAndPointRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.lang.ref.WeakReference;
import java.util.Arrays;

public class ECGFragment extends Fragment {

    private XYPlot plot;
    private Button locButton;
    private TextView bpmText;

    private Redrawer redrawer;

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
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                startActivity(intent);
            }
        });

        ECGModel ecgSeries = new ECGModel(100, 50);

        // add a new series' to the xyplot:

        MyFadeFormatter formatter = new MyFadeFormatter(2000);
        formatter.setLegendIconEnabled(false);
        plot.addSeries(ecgSeries, formatter);
        plot.setRangeBoundaries(0, 10, BoundaryMode.FIXED.FIXED);
        plot.setDomainBoundaries(0, 100, BoundaryMode.FIXED);

        // reduce the number of range labels
        plot.setLinesPerRangeLabel(3);


        // start generating ecg data in the background:
        ecgSeries.start(new WeakReference<>(plot.getRenderer(AdvancedLineAndPointRenderer.class)));

        // set a redraw rate of 30hz and start immediately:
        redrawer = new Redrawer(plot, 30, true);
        return v;
    }

    /////////////////////////////////////////////////
    public static class MyFadeFormatter extends AdvancedLineAndPointRenderer.Formatter {

        private int trailSize;

        public MyFadeFormatter(int trailSize) {
            this.trailSize = trailSize;
        }

        @Override
        public Paint getLinePaint(int thisIndex, int latestIndex, int seriesSize) {

            // offset from the latest index:
            int offset;

            if (thisIndex > latestIndex) {

                offset = latestIndex + (seriesSize - thisIndex);

            } else {

                offset = latestIndex - thisIndex;

            }

            float scale = 255f / trailSize;
            int alpha = (int) (255 - (offset * scale));
            getLinePaint().setAlpha(alpha > 0 ? alpha : 0);
            return getLinePaint();
        }

    }

    ////////////////////////////////////////////////
    public static class ECGModel implements XYSeries {

        private final Number[] data;
        private final long delayMs;
        private final int blipInteral;
        private final Thread thread;
        private boolean keepRunning;
        private int latestIndex;

        private WeakReference<AdvancedLineAndPointRenderer> rendererRef;
        public ECGModel(int size, int updateFreqHz) {

            data = new Number[size];

            for (int i = 0; i < data.length; i++) {
                data[i] = 0;
            }


            // translate hz into delay (ms):
            delayMs = 1000 / updateFreqHz;


            // add 7 "blips" into the signal:
            blipInteral = size / 7;

            thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        while (keepRunning) {
                            if (latestIndex >= data.length) {
                                latestIndex = 0;
                            }


                            // generate some random data:
                            if (latestIndex % blipInteral == 0) {

                                // insert a "blip" to simulate a heartbeat:
                                data[latestIndex] = (Math.random() * 10) + 3;

                            } else {

                                // insert a random sample:
                                data[latestIndex] = Math.random() * 2;

                            }


                            if (latestIndex < data.length - 1) {

                                // null out the point immediately following i, to disable
                                // connecting i and i+1 with a line:
                                data[latestIndex + 1] = null;
                            }


                            if (rendererRef.get() != null) {
                                rendererRef.get().setLatestIndex(latestIndex);
                                Thread.sleep(delayMs);
                            } else {
                                keepRunning = false;
                            }
                            latestIndex++;
                        }
                    } catch (InterruptedException e) {
                        keepRunning = false;
                    }
                }
            });
        }


        public void start(final WeakReference<AdvancedLineAndPointRenderer> rendererRef) {
            this.rendererRef = rendererRef;
            keepRunning = true;
            thread.start();
        }


        @Override
        public int size() {
            return data.length;
        }


        @Override
        public Number getX(int index) {
            return index;
        }


        @Override
        public Number getY(int index) {
            return data[index];
        }


        @Override
        public String getTitle() {
            return "Signal";
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        redrawer.finish();
    }
}

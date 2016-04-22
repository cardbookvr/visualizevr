package com.cardbookvr.visualizevr;

import android.media.audiofx.Visualizer;

import com.google.vrtoolkit.cardboard.CardboardView;

/**
 * Created by Schoen and Jonathan on 4/22/2016.
 */
public class VisualizerBox {
    static final String TAG = "VisualizerBox";

    public Visualization activeViz;

    Visualizer visualizer;
    public static int captureSize;
    public static byte[] audioBytes;


    public VisualizerBox(final CardboardView cardboardView) {
        visualizer = new Visualizer(0);
        captureSize = Visualizer.getCaptureSizeRange()[0];
        visualizer.setCaptureSize(captureSize);

        // capture audio data
        Visualizer.OnDataCaptureListener captureListener = new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
                audioBytes = bytes;
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
            }
        };

        // Visualizer.OnDataCaptureListener captureListener = ...
        visualizer.setDataCaptureListener(captureListener, Visualizer.getMaxCaptureRate(), true, true);
        visualizer.setEnabled(true);
    }

    public void setup() {
        if(activeViz != null)
            activeViz.setup();
    }

    public void preDraw() {
        if(activeViz != null)
            activeViz.preDraw();
    }

    public void postDraw() {
        if(activeViz != null)
            activeViz.postDraw();
    }
}

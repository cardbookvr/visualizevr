package com.cardbookvr.visualizevr;

import com.google.vrtoolkit.cardboard.CardboardView;

/**
 * Created by Schoen and Jonathan on 4/22/2016.
 */
public class VisualizerBox {
    static final String TAG = "VisualizerBox";

    public Visualization activeViz;

    public VisualizerBox(final CardboardView cardboardView){
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

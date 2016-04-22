package com.cardbookvr.visualizevr;

import android.os.Bundle;

import com.cardbookvr.renderbox.IRenderBox;
import com.cardbookvr.renderbox.RenderBox;
import com.cardbookvr.renderbox.Transform;
import com.cardbookvr.renderbox.components.Cube;
import com.cardbookvr.visualizevr.visualizations.FFTVisualization;
import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;

public class MainActivity extends CardboardActivity implements IRenderBox {
    private static final String TAG = "MainActivity";
    CardboardView cardboardView;

    VisualizerBox visualizerBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardboardView = (CardboardView) findViewById(R.id.cardboard_view);
        cardboardView.setRenderer(new RenderBox(this, this));
        setCardboardView(cardboardView);

        visualizerBox = new VisualizerBox(cardboardView);
        //visualizerBox.activeViz = new GeometricVisualization(visualizerBox);
        //visualizerBox.activeViz = new WaveformVisualization(visualizerBox);
        visualizerBox.activeViz = new FFTVisualization(visualizerBox);
    }

    @Override
    public void setup() {
        new Transform()
                .setLocalPosition(0,0,-7)
                .setLocalRotation(45,60,0)
                .addComponent(new Cube(true));
        visualizerBox.setup();
    }

    @Override
    public void preDraw() {
        visualizerBox.preDraw();
    }

    @Override
    public void postDraw() {
        visualizerBox.postDraw();
    }
}

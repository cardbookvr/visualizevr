package com.cardbookvr.visualizevr;

import android.os.Bundle;

import com.cardbookvr.renderbox.IRenderBox;
import com.cardbookvr.renderbox.RenderBox;
import com.cardbookvr.renderbox.Time;
import com.cardbookvr.renderbox.Transform;
import com.cardbookvr.renderbox.components.Cube;
import com.cardbookvr.visualizevr.visualizations.FFTVisualization;
import com.cardbookvr.visualizevr.visualizations.GeometricVisualization;
import com.cardbookvr.visualizevr.visualizations.WaveformVisualization;
import com.google.vr.sdk.base.GvrActivity;
import com.google.vr.sdk.base.GvrView;

import java.util.Random;

public class MainActivity extends GvrActivity implements IRenderBox {
    private static final String TAG = "MainActivity";
    GvrView gvrView;

    VisualizerBox visualizerBox;

    float timeToChange = 0f;
    final float CHANGE_DELAY = 3f;
    final Random rand = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gvrView = (GvrView) findViewById(R.id.gvr_view);
        gvrView.setRenderer(new RenderBox(this, this));
        setGvrView(gvrView);

        visualizerBox = new VisualizerBox(gvrView);
        visualizerBox.visualizations.add(new GeometricVisualization(visualizerBox));
        visualizerBox.visualizations.add(new WaveformVisualization(visualizerBox));
        visualizerBox.visualizations.add(new FFTVisualization(visualizerBox));
    }

    @Override
    public void setup() {
        new Transform()
                .setLocalPosition(0,0,-7)
                .setLocalRotation(45,60,0)
                .addComponent(new Cube(true));
        visualizerBox.setup();
        RenderBox.mainCamera.trailsMode = true;

        for (Visualization viz : visualizerBox.visualizations) {
            viz.activate(false);
        }
    }

    @Override
    public void preDraw() {
        if (Time.getTime() > timeToChange) {
            int idx = rand.nextInt(visualizerBox.visualizations.size() );
            Visualization viz = visualizerBox.visualizations.get(idx);
            viz.activate(!viz.active);
            timeToChange += CHANGE_DELAY;
        }

        visualizerBox.preDraw();
    }

    @Override
    public void postDraw() {
        visualizerBox.postDraw();
    }
}

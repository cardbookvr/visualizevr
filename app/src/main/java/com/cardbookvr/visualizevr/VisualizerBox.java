package com.cardbookvr.visualizevr;

import android.media.audiofx.Visualizer;
import android.opengl.GLES20;

import com.cardbookvr.renderbox.RenderBox;
import com.google.vrtoolkit.cardboard.CardboardView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Schoen and Jonathan on 4/22/2016.
 */
public class VisualizerBox {
    static final String TAG = "VisualizerBox";

    public Visualization activeViz;

    Visualizer visualizer;
    public static int captureSize;
    public static byte[] audioBytes;
    public static int audioTexture = -1;

    public VisualizerBox(final CardboardView cardboardView) {
        visualizer = new Visualizer(0);
        captureSize = Visualizer.getCaptureSizeRange()[0];
        visualizer.setCaptureSize(captureSize);

        // capture audio data
        Visualizer.OnDataCaptureListener captureListener = new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
                audioBytes = bytes;
                loadTexture(cardboardView, audioTexture, bytes);
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
        audioTexture = genTexture();
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

    public static int genTexture() {
        final int[] textureHandle = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);
        RenderBox.checkGLError("VisualizerBox GenTexture");
        if (textureHandle[0] != 0) {
            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        }
        if (textureHandle[0] == 0) {
            throw new RuntimeException("Error loading texture.");
        }
        return textureHandle[0];
    }

    public static void loadTexture(CardboardView cardboardView, final int textureId, byte[] bytes) {
        if (textureId < 0)
            return;
        final ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length * 4);
        final int length = bytes.length;
        buffer.order(ByteOrder.nativeOrder());
        buffer.put(bytes);
        buffer.position(0);
        cardboardView.queueEvent(new Runnable() {
            @Override
            public void run() {
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
                GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0,
                        GLES20.GL_LUMINANCE, length, 1, 0,
                        GLES20.GL_LUMINANCE,
                        GLES20.GL_UNSIGNED_BYTE, buffer);
            }
        });
    }

}

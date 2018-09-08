package com.example.app_shimeji;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    public static final int Max_FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    public Boolean runing;
    public static Canvas canvas;

    public void setRuning(boolean runing)
    {
        this.runing=runing;

    }

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;


    }

    @Override
    public void run() {
        long startTime;
        long timeMillis = 1000 / Max_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long tagetTime = 1000 / Max_FPS;

        while (runing) {
            startTime = System.nanoTime();

            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = tagetTime - timeMillis;
            try {
                if (waitTime > 0) {
                    this.sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == Max_FPS) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;

                if (BuildConfig.DEBUG) {
                    Log.d("averageFPS", "averageFPS---" + averageFPS);
                }

            }

        }
    }
}

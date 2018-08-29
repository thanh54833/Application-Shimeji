package com.example.examplegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    private SceneManeger maneger;


    public GamePanel(Context context) {

        super(context);
        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT=context;


        thread = new MainThread(getHolder(), this);

        maneger=new SceneManeger();

        setFocusable(true);

    }



    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        thread = new MainThread(getHolder(), this);
        thread.setRuning(true);
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (true) {
            try {
                thread.setRuning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        maneger.recieveTouch(event);
        return true;
        //return super.onTouchEvent(event);
    }

    public void update() {

        maneger.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        maneger.draw(canvas);
    }



}

package com.example.app_shimeji;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private SceneManeger maneger;

    private GameplayScene gs;

    public GamePanel(Context context) {

        super(context);
        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT=context;


        thread = new MainThread(getHolder(), this);

        maneger=new SceneManeger();

        //add...

        gs= new GameplayScene();

        /*final Handler mHandler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                mHandler.postDelayed(this, 200);
                gs.incrementY();
            }
        };
        mHandler.postDelayed(r, 200);*/

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

    private int record=0;
    private Point playpoint;
    private RectPlayer player;


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        maneger.recieveTouch(event);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                Log.d("thanh","thanh :"+(record++));

                gs.setY();

                break;
            case MotionEvent.ACTION_MOVE:

                gs.reset();

                Log.d("thanh","reset !");

                break;

        }
        return true;
        //return super.onTouchEvent(event);
    }

    public void update() {

        maneger.update();

        gs.update();

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        maneger.draw(canvas);

        gs.draw(canvas);
    }



}

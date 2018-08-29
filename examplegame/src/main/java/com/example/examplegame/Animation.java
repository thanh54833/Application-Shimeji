package com.example.examplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Animation {
    private Bitmap[] frame;
    private int frameIndex;
    private boolean isPlaying=false;
    private long lastFrame;
    private float frameTime;


    public boolean isPlaying(){
        return isPlaying;
    }

    public void play(){
        isPlaying=true;
        frameIndex=0;
        lastFrame=System.currentTimeMillis();

    }
    public void stop(){
        isPlaying=false;
    }
    public Animation(Bitmap[] frame,float animTime)
    {

        this.frame=frame;
        frameIndex=0;
        frameTime=animTime/frame.length;
        lastFrame=System.currentTimeMillis();
    }

    public void draw(Canvas canvas, Rect destination){
        if(!isPlaying)
        {
            return;
        }
        canvas.drawBitmap(frame[frameIndex],null,destination,new Paint());
    }

    public void update(){
        if(!isPlaying)
        {
            return;
        }

        if(System.currentTimeMillis()-lastFrame>frameTime*1000){
            frameIndex++;
            frameIndex=frameIndex>=frame.length?0:frameIndex;
            lastFrame=System.currentTimeMillis();
        }
    }



 }

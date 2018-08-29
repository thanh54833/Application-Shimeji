package com.example.examplegame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class RectPlayer implements gameObject {


    private Rect rectangle;
    private int color;


    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;


    public Rect getRectangle(){
        return rectangle;
    }

    public RectPlayer(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf=new BitmapFactory();
        Bitmap idleimg=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),)


    }

    @Override
    public void draw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(color);

        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {

    }

    public void update(Point point) {
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);
    }

}

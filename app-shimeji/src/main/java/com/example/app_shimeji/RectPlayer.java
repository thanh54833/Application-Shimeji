package com.example.app_shimeji;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class RectPlayer implements GameObject {


    private Rect rectangle;
    private int color;


    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;

    /*private Rect rectangle;
    private Paint paint;

    // add ...
    int x = 50;
    int y = 50;
    int sideLength = 200;

    // create a rectangle that we'll draw later
    rectangle = new Rect(x, y, sideLength, sideLength);

    // create the Paint and set its color
    paint = new Paint();
        paint.setColor(Color.GRAY);
        canvas.drawRect(rectangle, paint);*/


    public Rect getRectangle(){
        return rectangle;
    }

    public RectPlayer(Rect rectangle, int color) {

        this.rectangle = rectangle;
        this.color = color;

    }

    @Override
    public void draw(Canvas canvas) {

        Paint paint = new Paint();

        paint.setColor(Color.BLACK);

        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {

    }

    public void update(Point point) {
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);

    }

}

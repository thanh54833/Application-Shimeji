package com.example.examplegame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public  class Obstacle implements gameObject {

    private Rect rectangle;
    private Rect rectangle2;
    private int color;


    public void incrementY(float y){
        rectangle.top+=y;
        rectangle.bottom+=y;
        rectangle2.top+=y;
        rectangle2.bottom+=y;

    }


    public  Obstacle(int rectHeight,int color,int startX,int startY,int playerGap){
        this.color=color;

        rectangle=new Rect(0,startY,startX,startY+rectHeight);
        rectangle2=new Rect(startX+playerGap,startY,Constants.SCREEN_HEIGHT,startY+rectHeight);

    }

    public  Rect getRectangle(){
        return rectangle;
    }
    public boolean playerCollide(RectPlayer player)
    {

        //vesrion 1...
        /*if(rectangle.contains(player.getRectangle().left,player.getRectangle().top)
                ||rectangle.contains(player.getRectangle().right,player.getRectangle().top)
                ||rectangle.contains(player.getRectangle().left,player.getRectangle().bottom)
                ||rectangle.contains(player.getRectangle().right,player.getRectangle().bottom)
                )
        {

            return true;

        }
        return false;*/

        return Rect.intersects(rectangle,player.getRectangle())||Rect.intersects(rectangle2,player.getRectangle());


    }


    @Override
    public void draw(Canvas canvas) {
        Paint paint=new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
        canvas.drawRect(rectangle2,paint);


    }

    @Override
    public void update() {

    }
}

package com.example.app_shimeji;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public  class Obstacle implements GameObject {

    private Rect rectangle;
    private Rect rectangle2;

    /*private Rect rectangle11;
    private Rect rectangle22;*/

    private int color;


    public void incrementY(float y){

        /*rectangle.top+=y;
        rectangle.bottom+=y;

        rectangle2.top+=y;
        rectangle2.bottom+=y;*/

        rectangle.left-=y;
        rectangle.right-=y;

        rectangle2.left-=y;
        rectangle2.right-=y;

        /*rectangle11.left-=y;
        rectangle11.right-=y;

        rectangle22.left-=y;
        rectangle22.right-=y;*/


    }

    public  Obstacle(int rectHeight,int color,int startX,int startY,int playerGap){

        this.color=color;

        //rectangle=new Rect(0,startY,startX,startY+rectHeight);
        //rectangle2=new Rect(startX+100,startY,Constants.SCREEN_HEIGHT,startY+rectHeight);

        rectangle=new Rect(getScreenWidth()-40,0,getScreenWidth(),100);

        rectangle2=new Rect(getScreenWidth()-40,200,getScreenWidth(),400);


        /*rectangle11=new Rect(getScreenWidth()/2-40,0,getScreenWidth()/2,100);

        rectangle22=new Rect(getScreenWidth()/2-40,200,getScreenWidth()/2,400);*/


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

        return Rect.intersects(rectangle,player.getRectangle())||Rect.intersects(rectangle2,player.getRectangle());//||Rect.intersects(rectangle11,player.getRectangle())||Rect.intersects(rectangle22,player.getRectangle());

    }


    @Override
    public void draw(Canvas canvas) {


        Paint paint=new Paint();
        paint.setColor(color);

        canvas.drawRect(rectangle,paint);
        canvas.drawRect(rectangle2,paint);
        // cut ...
        /*canvas.drawRect(rectangle11,paint);
        canvas.drawRect(rectangle22,paint);*/

    }

    @Override
    public void update() {

    }


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }



}

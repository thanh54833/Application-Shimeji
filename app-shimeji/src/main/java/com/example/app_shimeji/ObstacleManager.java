package com.example.app_shimeji;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

public class ObstacleManager {


    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private long startTime;
    private long initTime;
    private int score=0;
    //add ...
    private int record=0;



    public ObstacleManager(int playerGap,int obstacleGap,int obstacleHeight,int color)
    {
        this.playerGap=playerGap;

        this.obstacleGap=obstacleGap;

        this.obstacleHeight=obstacleHeight;

        this.color=color;

        startTime=initTime=System.currentTimeMillis();

        obstacles=new ArrayList<>();

        populateObstacles();


    }

    private void populateObstacles(){

        int currY=-(5*Constants.SCREEN_WIDTH/4);

        while (currY<0){

            int xStart=(int)(Math.random()*(Constants.SCREEN_HEIGHT-playerGap));

            obstacles.add(new Obstacle(obstacleHeight,color,xStart,currY,playerGap));

            currY+=obstacleHeight+obstacleGap;

        }

        if(BuildConfig.DEBUG){
            Log.d("currY","+currY ="+currY);
        }


    }


    public void update(){

        int elapsedTime=(int)(System.currentTimeMillis()-startTime);
        startTime=System.currentTimeMillis();
        float speed=(float)(Math.sqrt(1+(startTime-initTime)/2000.0))*Constants.SCREEN_HEIGHT/10000.0f;

        for(Obstacle ob: obstacles){

            ob.incrementY(speed*elapsedTime);

        }

        /*if(obstacles.get(obstacles.size()-1).getRectangle().right>=Constants.SCREEN_WIDTH){

            int xStart=(int)(Math.random()*(Constants.SCREEN_WIDTH-playerGap));

            //xStart=10;
            //playerGap=100;

            obstacles.add(0,new Obstacle(obstacleHeight,color,xStart,obstacles.get(0).getRectangle().top-obstacleHeight-obstacleGap,playerGap));
            Log.d("Obstacle","obstacleHeight :"+obstacleHeight+"  - obstacleHeight :"+obstacleHeight+" - "+color+" - xStart : "+xStart+" - obstacles.get(0).getRectangle() :"+(obstacles.get(0).getRectangle().top-obstacleHeight-obstacleGap)+" - playerGap :"+playerGap);
            obstacles.remove(obstacles.size()-1);

            score++;
        }*/

        if(obstacles.get(obstacles.size()-1).getRectangle().left<=getScreenWidth()/2){

            if(record==0) {

                record=1;
                int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
                //xStart=10;
                //playerGap=100;
                obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));

                Log.d("Obstacle", "obstacleHeight :" + obstacleHeight + "  - obstacleHeight :" + obstacleHeight + " - " + color + " - xStart : " + xStart + " - obstacles.get(0).getRectangle() :" + (obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap) + " - playerGap :" + playerGap);

            }
        }

        if(obstacles.get(obstacles.size()-1).getRectangle().left<=0){

            record=0;
            obstacles.remove(obstacles.size()-1);
            score++;
        }

        Log.d("obstacles","obstacles :"+obstacles.get(obstacles.size()-1).getRectangle().left+" - getScreenWidth :"+getScreenWidth()/2);

    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    public void draw(Canvas canvas){

        //gs.draw(canvas);

        for(Obstacle ob:obstacles){
            ob.draw(canvas);
        }

        Paint paint=new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);

        canvas.drawText(""+score,50,50+paint.descent()-paint.ascent(),paint);

    }


    public boolean playerCollide(RectPlayer player){
        for(Obstacle ob:obstacles){
            if(ob.playerCollide(player))
            {
                return true;
            }
        }
        return false;
    }


}

package com.example.examplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Config;
import android.view.MotionEvent;

import static android.graphics.Color.*;

public class GameplayScene implements Scene {

    private RectPlayer player;
    private Point playpoint;
    private ObstacleManager obstacleManager;

    private boolean movingPlayer;
    private boolean gameOver=false;

    private long gameOverTime;

    private Rect r=new Rect();


    public GameplayScene() {


        player=new RectPlayer(new Rect(150,150,200,200), rgb(255,0,0));

        playpoint=new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);

        player.update(playpoint);

        obstacleManager=new ObstacleManager(200,350,75, WHITE);

    }
    public void reset(){

        playpoint=new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);

        player.update(playpoint);

        obstacleManager=new ObstacleManager(200,350,75, WHITE);

        movingPlayer=false;

    }


    @Override
    public void update() {

        if(!gameOver) {

            player.update(playpoint);
            obstacleManager.update();
            if(obstacleManager.playerCollide(player))
            {
                gameOver=true;
                gameOverTime=System.currentTimeMillis();
            }

        }

    }


    @Override
    public void draw(Canvas canvas) {

        //canvas.drawColor(Color.BLUE);

        Paint paints = new Paint();
        paints.setColor(Color.BLUE);

        Rect rectangle=new Rect(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);

        canvas.drawRect(rectangle, paints);


        player.draw(canvas);

        obstacleManager.draw(canvas);

        if(gameOver){

            Paint paint=new Paint();

            paint.setTextSize(100);

            paint.setColor(MAGENTA);

            drawCenterText(canvas ,paint,"Game Over ...");

        }

    }

    @Override
    public void terminate() {

        SceneManeger.ACTIVE_SCENE=0;
    }

   @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver&&player.getRectangle().contains((int)event.getX(),(int)event.getY()))
                {
                    movingPlayer=true;
                }
                if(gameOver&&System.currentTimeMillis()-gameOverTime>=2000){
                    gameOver=false;
                    reset();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!gameOver&&movingPlayer)
                {
                    playpoint.set((int)event.getX(),(int)event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer=false;
                break;
        }

    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);

    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}

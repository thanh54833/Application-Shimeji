package com.example.test_commit;

/**
 * Created by ThanhHang on 8/1/2017.
 */

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;

    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder)  {
        this.gameSurface= gameSurface;
        this.surfaceHolder= surfaceHolder;
    }

    @Override
    public void run()  {
        long startTime = System.nanoTime();

        while(running)  {
            Canvas canvas= null;
            try {
                // Lấy ra đối tượng Canvas và khóa nó lại.
                canvas = this.surfaceHolder.lockCanvas();


                // Đồng bộ hóa.
                synchronized (canvas)  {
                    this.gameSurface.update();

                    this.gameSurface.draw(canvas);
                }
            }catch(Exception e)  {
                // Không làm gì
            } finally {
                if(canvas!= null)  {
                    // Mở khóa cho Canvas.
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            long now = System.nanoTime() ;
            // Thời gian cập nhập lại giao diện Game
            // (Đổi từ Nanosecond ra milisecond).
            long waitTime = (now - startTime)/100000;
            if(waitTime < 10)  {
                waitTime= 10; // Millisecond.
            }

            System.out.print(" Wait Time="+ waitTime);

            try {
                // Ngừng chương trình một chút.
                this.sleep(200);
            } catch(InterruptedException e)  {

            }
            startTime = System.nanoTime();
            System.out.print(".");
        }
    }

    public void setRunning(boolean running)  {
        this.running= running;
    }
}
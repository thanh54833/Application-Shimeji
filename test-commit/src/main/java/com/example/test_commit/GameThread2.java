package com.example.test_commit;

/**
 * Created by ThanhHang on 8/6/2017.
 */

import android.graphics.Canvas;
        import android.view.SurfaceHolder;

public class GameThread2 extends Thread {

    private boolean running;

    public GameThread2()  {

    }
    @Override
    public void run()  {




        floatingmove fm=new floatingmove();

        while(running)  {
            try {
                // Ngừng chương trình một chút.
                this.sleep(200);
            } catch(InterruptedException e)  {

            }

        }
    }

    public void setRunning(boolean running)  {
        this.running= running;
    }
}
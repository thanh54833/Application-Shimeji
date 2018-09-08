package com.example.test_commit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;



    private final List<ChibiCharacter> chibiList = new ArrayList<ChibiCharacter>();
    private final List<Explosion> explosionList = new ArrayList<Explosion>();

    private static final int MAX_STREAMS=100;
    private int soundIdExplosion;
    private int soundIdBackground;

    private boolean soundPoolLoaded;
    private SoundPool soundPool;

    public GameSurface(Context context)  {
        super(context);

        // Đảm bảo Game Surface có thể focus để điều khiển các sự kiện.
        this.setFocusable(true);

        // Sét đặt các sự kiện liên quan tới Game.
        this.getHolder().addCallback(this);

        this.initSoundPool();
    }

    private void initSoundPool()  {
        // Với phiên bản Android API >= 21
        if (Build.VERSION.SDK_INT >= 21 ) {

            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder= new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        // Với phiên bản Android API < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }


        // Sự kiện SoundPool đã tải lên bộ nhớ thành công.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPoolLoaded = true;

                // Phát nhạc nền
                playSoundBackground();
            }
        });

        // Tải file nhạc tiếng nổ (background.mp3) vào SoundPool.
        this.soundIdBackground= this.soundPool.load(this.getContext(), R.raw.background,1);

        // Tải file nhạc tiếng nổ (explosion.wav) vào SoundPool.
        this.soundIdExplosion = this.soundPool.load(this.getContext(), R.raw.explosion,1);


    }

    public void playSoundExplosion()  {
        if(this.soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn =  0.8f;

            // Phát âm thanh explosion.wav
            int streamId = this.soundPool.play(this.soundIdExplosion,leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    public void playSoundBackground()  {
        if(this.soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn =  0.8f;

            // Phát âm thanh background.mp3
            int streamId = this.soundPool.play(this.soundIdBackground,leftVolumn, rightVolumn, 1, -1, 1f);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            int x=  (int)event.getX();
            int y = (int)event.getY();

            Iterator<ChibiCharacter> iterator= this.chibiList.iterator();

            // Kiểm tra xem có click vào nhân vật nào không.
            while(iterator.hasNext()) {
                ChibiCharacter chibi = iterator.next();
                if( chibi.getX() < x && x < chibi.getX() + chibi.getWidth()
                        && chibi.getY() < y && y < chibi.getY()+ chibi.getHeight())  {

                    // Loại bỏ nhân vật Game hiện tại ra khỏi iterator và list.
                    iterator.remove();

                    // Tạo mới một đối tượng Explosion.
                    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.boom);
                    Explosion explosion = new Explosion(this, bitmap,chibi.getX(),chibi.getY());
                    this.explosionList.add(explosion);


                }
            }

            for(ChibiCharacter chibi: chibiList) {
                int movingVectorX =x-  chibi.getX() ;
                int movingVectorY =y-  chibi.getY() ;
                chibi.setMovingVector(movingVectorX, movingVectorY);
            }
            return true;
        }
        return false;
    }

    public void update()  {

        for(ChibiCharacter chibi: chibiList) {
            chibi.update();
        }
        for(Explosion explosion: this.explosionList)  {
            explosion.update();
        }

        Iterator<Explosion> iterator= this.explosionList.iterator();
        while(iterator.hasNext())  {
            Explosion explosion = iterator.next();

            if(explosion.isFinish()) {
                // Nếu explosion đã hoàn thành, loại nó ra khỏi iterator & list.
                // (Loại bỏ phần tử hiện thời ra khỏi bộ lặp).
                iterator.remove();
                continue;
            }
        }
    }
    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

        for(ChibiCharacter chibi: chibiList){

            chibi.draw(canvas);

        }

        for(Explosion explosion: this.explosionList){

            explosion.draw(canvas);

        }

    }
    // Thi hành phương thức của interface SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap chibi = BitmapFactory.decodeResource(this.getResources(),R.drawable.serge);

        Bitmap chibiBitmap1 = Bitmap.createScaledBitmap(chibi, 550, 800, true);

        ChibiCharacter chibi1 = new ChibiCharacter(this,chibiBitmap1,100,50);
        Bitmap chibiBitmap2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chipioi);
        ChibiCharacter chibi2 = new ChibiCharacter(this,chibiBitmap2,300,150);
        this.chibiList.add(chibi1);
        this.chibiList.add(chibi2);
        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Thi hành phương thức của interface SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    // Thi hành phương thức của interface SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Luồng cha, cần phải tạm dừng chờ GameThread kết thúc.
                this.gameThread.join();

            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }


    }




}
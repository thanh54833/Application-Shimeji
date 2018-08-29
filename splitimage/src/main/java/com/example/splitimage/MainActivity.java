package com.example.splitimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private final List<ChibiCharacter> chibiList = new ArrayList<ChibiCharacter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.setContentView(new GameSurface(this));

        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chibi1);

        //resize image ...
        Bitmap chibimap=scaleBitmap(chibiBitmap1,100,100);

        ChibiCharacter chibi1 = new ChibiCharacter(chibimap);


        ImageView im1=(ImageView)findViewById(R.id.image1);
        ImageView im2=(ImageView)findViewById(R.id.image2);
        ImageView im3=(ImageView)findViewById(R.id.image3);

        Bitmap bitmap[]=chibi1.getMoveBitmaps(1);

        im1.setImageBitmap(bitmap[0]);
        im2.setImageBitmap(bitmap[1]);
        im3.setImageBitmap(bitmap[2]);

        Toast.makeText(getApplicationContext(),"--"+chibi1.getMoveBitmaps(1).length,Toast.LENGTH_LONG).show();

    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int wantedWidth, int wantedHeight) {
        Bitmap output = Bitmap.createBitmap(wantedWidth, wantedHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Matrix m = new Matrix();
        m.setScale((float) wantedWidth / bitmap.getWidth(), (float) wantedHeight / bitmap.getHeight());
        canvas.drawBitmap(bitmap, m, new Paint());
        return output;
    }





}

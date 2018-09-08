package com.example.app_shimeji;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
public class view extends View {


        private Rect rectangle;
        private Paint paint;

        public view(Context context) {
            super(context);
            int x = 50;
            int y = 50;
            int sideLength = 200;

            // create a rectangle that we'll draw later
            rectangle = new Rect(x, y, sideLength, sideLength);

            // create the Paint and set its color
            paint = new Paint();
            paint.setColor(Color.GRAY);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.BLUE);
            canvas.drawRect(rectangle, paint);
        }



}

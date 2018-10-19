package com.example.app_shimeji;

import android.graphics.Bitmap;

public abstract class Object {

    protected Bitmap image;
    protected final int rowCount;
    protected final int colCount;
    protected final int WIDTH;
    protected final int HEIGHT;
    protected final int width;
    protected final int height;

    public Object(Bitmap image, int rowCount, int colCount)  {

        this.image = image;
        this.rowCount= rowCount;
        this.colCount= colCount;

        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();

        this.width = this.WIDTH/ colCount;
        this.height= this.HEIGHT/ rowCount;
    }


    protected Bitmap createSubImageAt(int row, int col)  {
        Bitmap subImage = Bitmap.createBitmap(image, col* width, row* height ,width,height);
        return subImage;
    }

}
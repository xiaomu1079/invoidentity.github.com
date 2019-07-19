package com.example.invo.plantidentification.ui.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import java.io.IOException;

/**
 * Created by INvo
 * on 2019-07-11.
 */
public class PhotoProcess {


    public Bitmap mBitmap;


    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public void handle_photo(Bitmap bitmap,String path) {
        //调整宽高比例，便于imageView控件显示
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//返回图片宽高信息
        BitmapFactory.decodeFile(path, options);
        //让图片小于1024
        double radio = Math.max(options.outWidth * 1.0d / 1024f, options.outHeight * 1.0d / 1024f);
        options.inSampleSize = (int) Math.ceil(radio);//向上取整倍数
        options.inJustDecodeBounds = false;//显示图片
        bitmap = BitmapFactory.decodeFile(path, options);
        //调整角度，防止图片在某些手机（如三星）显示时歪斜
        if (path != null) {
            int degree = getPicRotate(path);
            Matrix m = new Matrix();
            m.setRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        }
        mBitmap = bitmap;
    }

    public int getPicRotate(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
}

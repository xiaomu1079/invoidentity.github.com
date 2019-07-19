package com.example.invo.plantidentification.ui.util;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by INvo
 * on 2019-07-11.
 */
public class BitmapToByte {
    private byte[] mArrays;
    private Bitmap mBitmap;

    public void bitmapToByte(Bitmap bitmap) {
        //质量压缩图片转换为字节
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);//此时并不是压缩，而是把压缩的数据存入bytearrayOutputStream
        int quality = 100;
        while (byteArrayOutputStream.toByteArray().length / 1024 > 4000) {//小于4M
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        mArrays = byteArrayOutputStream.toByteArray();
        mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public byte[] getArrays() {
        return this.mArrays;
    }
}

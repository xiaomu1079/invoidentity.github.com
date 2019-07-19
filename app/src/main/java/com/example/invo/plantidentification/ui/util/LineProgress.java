package com.example.invo.plantidentification.ui.util;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.invo.plantidentification.BuildConfig;

/**
 * Created by INvo
 * on 2019-07-11.
 */
public class LineProgress {
    private TranslateAnimation lineAnimation;

    public void lineProgress(ImageView imageView,View line) {
        int height = imageView.getHeight();
        if (BuildConfig.DEBUG) Log.d("LineProgress", "imageView:" + imageView);
        if (BuildConfig.DEBUG)
            Log.d("LineProgress", "imageView.getHeight():" + imageView.getHeight());
        line.setVisibility(View.VISIBLE);
        double time = height / 0.5;
        lineAnimation = new TranslateAnimation(0, 0, 0, height);
        lineAnimation.start();
        lineAnimation.setDuration((int) time);
        lineAnimation.setRepeatCount(Animation.INFINITE);//无限循环
        line.startAnimation(lineAnimation);
    }

    public TranslateAnimation getLineAnimation() {
        return this.lineAnimation;
    }
}

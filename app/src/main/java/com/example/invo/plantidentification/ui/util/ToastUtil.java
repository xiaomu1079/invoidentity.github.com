package com.example.invo.plantidentification.ui.util;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * Created by INvo
 * on 2019-07-10.
 */
public class ToastUtil {
    private static Toast mToast;
    final static String myMsg = "Error!!!";

    public static void showToast(Context ctx, @Nullable String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}

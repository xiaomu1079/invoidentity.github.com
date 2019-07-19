package com.example.invo.plantidentification.ui.util;

import android.content.Intent;

/**
 * Created by INvo
 * on 2019-07-10.
 */
public class DataUtil {
    private Intent mData;

    public void setData(Intent data) {
        mData = data;
    }

    public Intent getData() {
        return this.mData;
    }
}

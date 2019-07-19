package com.example.invo.plantidentification.ui.util;

import com.baidu.aip.imageclassify.AipImageClassify;

/**
 * Created by INvo
 * on 2019-07-11.
 */
public class Classify {

    private AipImageClassify mClient;
    public void detected() {
        AipImageClassify aipImageClassify = new AipImageClassify(Constants.APP_ID, Constants.API_KEY, Constants.SECRET_KEY);
        aipImageClassify.setConnectionTimeoutInMillis(2000);
        aipImageClassify.setSocketTimeoutInMillis(6000);
        mClient = aipImageClassify;
    }

    public AipImageClassify getClient() {
        return this.mClient;
    }
}


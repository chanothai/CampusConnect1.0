package com.company.zicure.survey.utilize;

import android.os.Handler;

import com.company.zicure.survey.interfaces.LaunchCallback;

/**
 * Created by 4GRYZ52 on 1/19/2017.
 */

public class NextzyUtil {
    public static void launch(final LaunchCallback callback){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (callback != null){
                    callback.onLaunchCallback();
                }
            }
        });
    }
}

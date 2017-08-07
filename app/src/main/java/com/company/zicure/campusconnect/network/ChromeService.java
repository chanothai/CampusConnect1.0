package com.company.zicure.campusconnect.network;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;

import com.company.zicure.campusconnect.R;

/**
 * Created by 4GRYZ52 on 10/21/2016.
 */
public class ChromeService{
    //Chrome tab service
    private CustomTabsServiceConnection customTabsServiceConnection = null;
    private CustomTabsSession customTabsSession = null;
    private static ChromeService me;

    private static final Uri URI = Uri.parse("http://hungary.ar-bro.net:8082/debug.html");
    private Context context = null;

    public ChromeService(Context context){
        this.context = context;
    }

    public static ChromeService getInstance(Context context){
        if (me == null){
            me = new ChromeService(context);
        }
        return me;
    }

    public boolean connectCustomTabsService(){
        String chromePackageName = "com.android.chrome";
        try {
            customTabsServiceConnection = new CustomTabsServiceConnection() {
                @Override
                public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                    createCustomTabsSession(client);
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
            CustomTabsClient.bindCustomTabsService(context, chromePackageName, customTabsServiceConnection);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void createCustomTabsSession(CustomTabsClient customTabsClient){
        //Initialize
        customTabsClient.warmup(0);
        customTabsSession = customTabsClient.newSession(new CustomTabsCallback() {
            @Override
            public void onNavigationEvent(int navigationEvent, Bundle extras) {

            }
        });

        customTabsSession.mayLaunchUrl(URI, null, null);
    }

    public CustomTabsSession getCustomTabsSession(){
        if (customTabsSession != null){
            return customTabsSession;
        }
        return null;
    }

    public void destroyService(){
        if (customTabsServiceConnection != null){
            context.unbindService(customTabsServiceConnection);
        }
    }

    public void openChromeCustomTabs(){
        try{
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder(customTabsSession);
            builder.setShowTitle(true);
            builder.setToolbarColor(Color.parseColor("#8cc34c"));
            builder.setStartAnimations(context, R.anim.anim_slide_in_top, R.anim.anim_slide_out_top);
            builder.setExitAnimations(context,R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_bottom);
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl((Activity) context, URI);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

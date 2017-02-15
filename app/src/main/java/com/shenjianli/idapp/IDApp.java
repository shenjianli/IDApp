package com.shenjianli.idapp;

import android.app.Application;

import com.shenjianli.shenlib.LibApp;
import com.shenjianli.shenlib.net.NetClient;

/**
 * Created by edianzu on 2017/2/15.
 */
public class IDApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        LibApp.getLibInstance().setMobileContext(this);
        LibApp.getLibInstance().setLogEnable(true);
        LibApp.getLibInstance().setServerBaseUrl(Constant.SERVER);
        NetClient.retrofit();
    }
}

package com.shenjianli.idapp;

import android.app.Application;

import com.shen.netclient.NetClient;
import com.shen.netclient.engine.NetClientLib;


/**
 * Created by edianzu on 2017/2/15.
 */
public class IDApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        NetClientLib.getLibInstance().setMobileContext(this);
        NetClientLib.getLibInstance().setLogEnable(true);
        NetClientLib.getLibInstance().setServerBaseUrl(Constant.SERVER);
        NetClient.retrofit();
    }
}

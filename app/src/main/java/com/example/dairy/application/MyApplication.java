package com.example.dairy.application;

import android.app.Application;

import org.xutils.x;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化xuttils3.0
        x.Ext.init(this);
        //是否输出debug日志
        x.Ext.setDebug(true);
    }
}

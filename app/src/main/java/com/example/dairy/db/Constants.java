package com.example.dairy.db;

import android.os.Environment;

import java.io.File;


public class Constants {
    //数据库路径
    public static final String DBPATH  = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "Diary/Db";

    //数据库名称
    public static final String DBNAME = "Diary.db";

    //数据库版本
    public static final int DBVERSION = 1;
}

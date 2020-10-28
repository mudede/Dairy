package com.example.dairy.db;

import android.content.Context;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;


/**
 * 数据库控制器
 */
public class DbHelper {

    private static DbHelper helper;

    private DbHelper() {
    }

    public static DbHelper getInstance() {
        if (helper == null) {
            synchronized (DbHelper.class) {
                if (helper == null) {
                    helper = new DbHelper();
                }
            }
        }
        return helper;
    }

    /**
     * 获取DbUtils
     *
     * @param context
     * @param listener
     * @return
     */
    public DbManager getDbManager(Context context, DbManager.DbUpgradeListener listener) {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName(Constants.DBNAME)
                .setDbDir(new File(Constants.DBPATH))
                .setDbVersion(Constants.DBVERSION)
                .setDbUpgradeListener(listener);
        return x.getDb(daoConfig);
    }

}

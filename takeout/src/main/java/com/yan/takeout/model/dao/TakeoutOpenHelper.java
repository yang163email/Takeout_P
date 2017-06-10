package com.yan.takeout.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yan.takeout.model.net.User;

import java.sql.SQLException;

/**
 * Created by 楠GG on 2017/6/6.
 */

public class TakeoutOpenHelper extends OrmLiteSqliteOpenHelper {

    public TakeoutOpenHelper(Context context) {
        super(context, "takeout.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建数据库执行,只是新创建才执行
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, ReceiptAddress.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //数据库升级时才执行
        try {
            TableUtils.createTable(connectionSource, ReceiptAddress.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

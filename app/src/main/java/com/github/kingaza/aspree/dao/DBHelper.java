package com.github.kingaza.aspree.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.kingaza.aspree.bean.Country;
import com.github.kingaza.aspree.bean.State;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by abu on 2015/5/17.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper{

    private static String TAG = "DBHelper";

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "aSpree.db";

    private Map<String, Dao> daos = new HashMap<String, Dao>();

    private static DBHelper instance;

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper(context);
                    Log.i(TAG, "DB Helper created for database " + DATABASE_NAME);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, State.class);
            TableUtils.createTable(connectionSource, Country.class);
            Log.i(TAG, "tables created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, State.class, true);
            TableUtils.dropTable(connectionSource, Country.class, true);
            onCreate(database, connectionSource);
            Log.i(TAG, "database is updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  synchronized Dao getDao (Class cls) throws SQLException{
        Dao dao = null;
        String classname = cls.getSimpleName();
        if (daos.containsKey(classname)) {
            dao = daos.get(classname);
        }
        if (dao == null) {
            dao = super.getDao(cls);
            daos.put(classname, dao);
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}

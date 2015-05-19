package com.github.kingaza.aspree.dao;

import android.content.Context;

import com.github.kingaza.aspree.bean.State;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by abu on 2015/5/19.
 */
public class StateDao {

    private Context context;
    private Dao<State, Integer> stateDaoOpe;
    private DBHelper dbHelper;

    public StateDao(Context context) {
        this.context = context;
        try {
            dbHelper = DBHelper.getInstance(context);
            stateDaoOpe = dbHelper.getDao(State.class);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add (State state) {
        try {
            stateDaoOpe.create(state);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

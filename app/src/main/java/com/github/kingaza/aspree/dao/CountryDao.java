package com.github.kingaza.aspree.dao;

import android.content.Context;

import com.github.kingaza.aspree.bean.Country;
import com.github.kingaza.aspree.bean.State;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by abu on 2015/5/19.
 */
public class CountryDao {

    private Context context;
    private Dao<Country, Integer> countryDaoOpe;
    private DBHelper dbHelper;

    public CountryDao(Context context) {
        this.context = context;
        try {
            dbHelper = DBHelper.getInstance(context);
            countryDaoOpe = dbHelper.getDao(Country.class);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void add (Country country) {
        try {
            countryDaoOpe.create(country);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

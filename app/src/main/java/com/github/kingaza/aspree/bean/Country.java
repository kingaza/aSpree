package com.github.kingaza.aspree.bean;

/**
 * Created by abu on 2015/5/1.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "country")
public class Country {

    @DatabaseField(id = true, generatedId = false)
    public int id;

    @DatabaseField
    public String iso_name;

    @DatabaseField
    public String iso;

    @DatabaseField
    public String iso3;

    @DatabaseField
    public String name;

    @DatabaseField
    public int numcode;

    public List<State> states;

    public Country() {}
}

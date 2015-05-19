package com.github.kingaza.aspree.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by abu on 2015/5/1.
 */

@DatabaseTable(tableName = "state")
public class State {

    @DatabaseField(id = true, generatedId = false)
    int id;

    @DatabaseField
    String name;

    @DatabaseField
    String abbr;

    @DatabaseField
    int country_id;

    public State() {}
}

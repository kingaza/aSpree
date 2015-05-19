package com.github.kingaza.aspree.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by abu on 2015/4/28.
 */

@DatabaseTable(tableName = "taxon")
public class Taxon {

    @DatabaseField(id = true, generatedId = false)
    int id;

    @DatabaseField
    String name;

    @DatabaseField
    String pretty_name;

    @DatabaseField
    String permalink;

    @DatabaseField
    int parent_id;

    @DatabaseField
    int taxonomy_id;

}

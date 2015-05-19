package com.github.kingaza.aspree.bean;

/**
 * Created by abu on 2015/4/28.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;
import java.util.ArrayList;

@DatabaseTable(tableName = "taxonomy")
public class Taxonomy {

    @DatabaseField(id = true, generatedId = false)
    public int id;

    @DatabaseField
    public String name;

    public class Root {
        public int id;
        public String name;
        public String pretty_name;
        public String permalink;
        public int parent_id;
        public int taxonomy_id;
        public List<Taxon> taxons;
    }

    public Root root;

    public List<String> getTaxonNames() {
        List<String> list = new ArrayList<String>();
        for (Taxon taxon : root.taxons) {
            list.add(taxon.name);
        }
        return list;
    }

}

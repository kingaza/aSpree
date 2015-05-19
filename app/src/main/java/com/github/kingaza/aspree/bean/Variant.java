package com.github.kingaza.aspree.bean;

/**
 * Created by abu on 2015/5/11.
 */

import java.util.List;

public class Variant {
    public int id;
    public String name;
    public String sku;
    public String price;
    public String display_price;
    public String weight;
    public String height;
    public String width;
    public String depth;
    public boolean is_master;
    public String slug;
    public String description;
    public boolean track_inventory;
    public String cost_price;
    public String permalink;
    public String options_text;
    public boolean in_stock;
    public List<OptionValue> option_values;
    public List<Image> images;
    public List<StockItem> stock_items;
}

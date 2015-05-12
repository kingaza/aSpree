package com.github.kingaza.aspree.protocol;


import java.util.List;
/**
 * Created by abu on 2015/5/12.
 */
public class Product {

    public int id;
    public String name;
    String description;
    String price;
    String display_price;
    String available_on;
    String slug;
    String meta_description;
    String meta_keywords;
    int shipping_category_id;
    List<Integer> taxon_ids;
    int total_on_hand;
    boolean has_variants;
    Variant master;
    List<Variant> variants;
    List<OptionType> option_types;
    List<ProductProperty> product_properties;
    List<Classification> classifications;

}

package com.github.kingaza.aspree.model;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Headers;

import com.github.kingaza.aspree.SpreeConst;
import com.github.kingaza.aspree.bean.Taxonomy;
import com.github.kingaza.aspree.bean.Taxonomies;

/**
 * Created by abu on 2015/4/26.
 */
public class TaxonomyModel {

    interface ITaxonomy {

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json",
                "X-Spree-Token: " + SpreeConst.SPREE_TOKEN
        })
        @GET("/taxonomies")
        void taxonomies(Callback<Taxonomies> callback);

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json",
                "X-Spree-Token: " + SpreeConst.SPREE_TOKEN
        })
        @GET("/taxonomies/{id}")
        void taxonomy(@Path("id") int id, Callback<Taxonomy> callback);
    }

    public static void getTaxonomies(Callback<Taxonomies> callback) {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(SpreeConst.API_URL).build();
        ITaxonomy spree = restAdapter.create(ITaxonomy.class);
        spree.taxonomies(callback);
    }

    public static void getTaxonomy(int id, Callback<Taxonomy> callback) {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(SpreeConst.API_URL).build();
        ITaxonomy spree = restAdapter.create(ITaxonomy.class);
        spree.taxonomy(id, callback);
    }
}

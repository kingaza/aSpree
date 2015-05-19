package com.github.kingaza.aspree.model;

import java.util.List;

import com.github.kingaza.aspree.SpreeConst;
import com.github.kingaza.aspree.bean.Pagination;
import com.github.kingaza.aspree.bean.Variant;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by abu on 2015/5/11.
 */


public class VariantModel {

    public class Variants extends Pagination {
        public List<Variant> variants;
    }

    interface IVariant {

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json",
                "X-Spree-Token: " + SpreeConst.SPREE_TOKEN
        })
        @GET("/variants")
        void variants(Callback<Variants> callback);

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json",
                "X-Spree-Token: " + SpreeConst.SPREE_TOKEN
        })
        @GET("/variants")
        void variantsInPage(@Query("page") int page, Callback<Variants> callback);

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json",
                "X-Spree-Token: " + SpreeConst.SPREE_TOKEN
        })
        @GET("/variants/{id}")
        void variant(@Path("id") int id, Callback<Variant> callback);
    }

    public static void getVariants(Callback<Variants> callback) {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(SpreeConst.API_URL).build();
        IVariant spree = restAdapter.create(IVariant.class);
        spree.variants(callback);
    }

    public static void getVariantsInPage(int page, Callback<Variants> callback) {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(SpreeConst.API_URL).build();
        IVariant spree = restAdapter.create(IVariant.class);
        spree.variantsInPage(page, callback);
    }

    public static void getCountry(int id, Callback<Variant> callback) {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(SpreeConst.API_URL).build();
        IVariant spree = restAdapter.create(IVariant.class);
        spree.variant(id, callback);
    }
}

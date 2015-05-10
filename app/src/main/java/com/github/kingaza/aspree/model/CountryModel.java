package com.github.kingaza.aspree.model;

import com.github.kingaza.aspree.SpreeConst;
import com.github.kingaza.aspree.protocol.Country;
import com.github.kingaza.aspree.protocol.Pagination;
import com.github.kingaza.aspree.protocol.Taxonomies;
import com.github.kingaza.aspree.protocol.Taxonomy;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by abu on 2015/5/1.
 */
public class CountryModel {

    interface ICountry {

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json",
                "X-Spree-Token: " + SpreeConst.SPREE_TOKEN
        })
        @GET("/countries")
        void countries(Callback<Countries> callback);

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json",
                "X-Spree-Token: " + SpreeConst.SPREE_TOKEN
        })
        @GET("/countries")
        void countriesInPage(@Query("page") int page, Callback<Countries> callback);

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json",
                "X-Spree-Token: " + SpreeConst.SPREE_TOKEN
        })
        @GET("/countries/{id}")
        void country(@Path("id") int id, Callback<Country> callback);
    }

    public class Countries extends Pagination {
        public List<Country> countries;
    };

    public static void getCountries(Callback<Countries> callback) {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(SpreeConst.API_URL).build();
        ICountry spree = restAdapter.create(ICountry.class);
        spree.countries(callback);
    }

    public static void getCountriesInPage(int page, Callback<Countries> callback) {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(SpreeConst.API_URL).build();
        ICountry spree = restAdapter.create(ICountry.class);
        spree.countriesInPage(page, callback);
    }

    public static void getCountry(int id, Callback<Country> callback) {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(SpreeConst.API_URL).build();
        ICountry spree = restAdapter.create(ICountry.class);
        spree.country(id, callback);
    }
}

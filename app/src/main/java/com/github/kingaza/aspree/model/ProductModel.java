package com.github.kingaza.aspree.model;

import com.github.kingaza.aspree.SpreeConst;
import com.github.kingaza.aspree.protocol.Pagination;
import com.github.kingaza.aspree.protocol.Product;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by abu on 2015/5/12.
 */
public class ProductModel {
    public class Products extends Pagination {
        public List<Product> products;
    }

    interface IProduct {

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json",
                "X-Spree-Token: " + SpreeConst.SPREE_TOKEN
        })
        @GET("/products")
        void products(Callback<Products> callback);

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json",
                "X-Spree-Token: " + SpreeConst.SPREE_TOKEN
        })
        @GET("/products")
        void productsInPage(@Query("page") int page, Callback<Products> callback);

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json",
                "X-Spree-Token: " + SpreeConst.SPREE_TOKEN
        })
        @GET("/products/{id}")
        void product(@Path("id") int id, Callback<Product> callback);
    }

    public static void getProducts(Callback<Products> callback) {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(SpreeConst.API_URL).build();
        IProduct spree = restAdapter.create(IProduct.class);
        spree.products(callback);
    }

    public static void getProductsInPage(int page, Callback<Products> callback) {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(SpreeConst.API_URL).build();
        IProduct spree = restAdapter.create(IProduct.class);
        spree.productsInPage(page, callback);
    }

    public static void getProduct(int id, Callback<Product> callback) {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(SpreeConst.API_URL).build();
        IProduct spree = restAdapter.create(IProduct.class);
        spree.product(id, callback);
    }
}

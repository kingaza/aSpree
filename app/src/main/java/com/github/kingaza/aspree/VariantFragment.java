package com.github.kingaza.aspree;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.kingaza.aspree.model.VariantModel;
import com.github.kingaza.aspree.bean.Variant;
import com.github.kingaza.aspree.bean.Pagination;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by abu on 2015/5/12.
 */
public class VariantFragment extends ListFragment {

    private static final String TAG = "VariantFragment";

    SwipyRefreshLayout mSwipyRefreshLayout;
    private List<String> mVariantNameList = new ArrayList<String>();
    private ArrayAdapter<String> mAdapter;

    private Pagination mPagination = new Pagination();

    Callback<VariantModel.Variants> mCallback = new Callback<VariantModel.Variants>() {
        @Override
        public void success(VariantModel.Variants variants, Response response) {
            mPagination = (Pagination) variants;
            Log.i(TAG, "Got page " + mPagination.current_page + "/" + mPagination.pages);
            for (Variant variant : variants.variants) {
                mVariantNameList.add(variant.name);
            }
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void failure(RetrofitError error) {
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_variant, container, false);
        mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {

                if (direction == SwipyRefreshLayoutDirection.BOTTOM &&
                        mPagination.current_page < mPagination.pages) {
                    VariantModel.getVariantsInPage(mPagination.current_page + 1, mCallback);
                }

                mSwipyRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        VariantModel.getVariants(mCallback);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, mVariantNameList);
        setListAdapter(mAdapter);
    }
}

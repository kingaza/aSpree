package com.github.kingaza.aspree;

import java.util.List;
import java.util.ArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.kingaza.aspree.bean.State;
import com.github.kingaza.aspree.dao.CountryDao;
import com.github.kingaza.aspree.dao.DBHelper;
import com.github.kingaza.aspree.dao.StateDao;
import com.github.kingaza.aspree.model.CountryModel;
import com.github.kingaza.aspree.bean.Country;
import com.github.kingaza.aspree.bean.Pagination;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by abu on 2015/5/1.
 */
public class CountryFragment extends ListFragment {

    private static final String TAG = "CountryFragment";

    SwipyRefreshLayout mSwipyRefreshLayout;
    private List<String> mCountryNameList = new ArrayList<String>();
    private ArrayAdapter<String> mAdapter;

    private Pagination mPagination = new Pagination();

    Callback<CountryModel.Countries> mCallback = new Callback<CountryModel.Countries>() {
        @Override
        public void success(CountryModel.Countries countries, Response response) {

            mPagination = (Pagination) countries;
            Log.i(TAG, "Got page " + mPagination.current_page + "/" + mPagination.pages);
            for (Country country : countries.countries) {
                mCountryNameList.add(country.name);
                CountryDao dao = new CountryDao(getActivity());
                dao.add(country);
            }
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void failure(RetrofitError error) {
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);
        mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {

                if (direction == SwipyRefreshLayoutDirection.BOTTOM &&
                        mPagination.current_page < mPagination.pages) {
                    CountryModel.getCountriesInPage(mPagination.current_page+1, mCallback);
                }

                mSwipyRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CountryModel.getCountries(mCallback);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, mCountryNameList);
        setListAdapter(mAdapter);
    }


}

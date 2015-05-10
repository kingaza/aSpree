package com.github.kingaza.aspree;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Build;
import android.util.Log;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import com.github.kingaza.aspree.model.TaxonomyModel;
import com.github.kingaza.aspree.protocol.Taxonomies;
import com.github.kingaza.aspree.protocol.Taxonomy;
import com.github.kingaza.aspree.view.PinnedSectionListView;
import com.github.kingaza.aspree.view.PinnedSectionListView.PinnedSectionListAdapter;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by abu on 2015/4/28.
 */

public class TaxonomyFragment extends ListFragment {

    private static String TAG = "TaxonomyFragment";

    static class SimpleAdapter extends ArrayAdapter<Item>
            implements PinnedSectionListAdapter {

        private static final int[] COLORS = new int[]{
                R.color.green_light,
                R.color.orange_light,
                R.color.blue_light,
                R.color.red_light};

        public SimpleAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
        }

        public void retrieveFromTaxonomy(Taxonomies taxonomies) {
            clear();
            int sectionPosition = 0, listPosition = 0;
            int sectionsNumber = taxonomies.taxonomies.size();
            prepareSections(sectionsNumber);
            for (Taxonomy taxonomy : taxonomies.taxonomies) {
                Item section = new Item(Item.SECTION, taxonomy.name);
                section.sectionPosition = sectionPosition;
                section.listPosition = listPosition++;
                onSectionAdded(section, sectionPosition);
                add(section);
                for (String s : taxonomy.getTaxonNames()) {
                    Item item = new Item(Item.ITEM, s);
                    item.sectionPosition = sectionPosition;
                    item.listPosition = listPosition++;
                    add(item);
                }
                sectionPosition++;
            }
        }

        protected void prepareSections(int sectionsNumber) {
        }

        protected void onSectionAdded(Item section, int sectionPosition) {
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTextColor(Color.DKGRAY);
            view.setTag("" + position);
            Item item = getItem(position);
            if (item.type == Item.SECTION) {
                view.setBackgroundColor(
                        parent.getResources().getColor(
                                COLORS[item.sectionPosition % COLORS.length]));
            }
            return view;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).type;
        }

        @Override
        public boolean isItemViewTypePinned(int viewType) {
            return viewType == Item.SECTION;
        }

    }

    static class FastScrollAdapter extends SimpleAdapter implements SectionIndexer {

        private Item[] sections;

        public FastScrollAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
        }

        @Override
        protected void prepareSections(int sectionsNumber) {
            sections = new Item[sectionsNumber];
        }

        @Override
        protected void onSectionAdded(Item section, int sectionPosition) {
            sections[sectionPosition] = section;
        }

        @Override
        public Item[] getSections() {
            return sections;
        }

        @Override
        public int getPositionForSection(int section) {
            if (section >= sections.length) {
                section = sections.length - 1;
            }
            return sections[section].listPosition;
        }

        @Override
        public int getSectionForPosition(int position) {
            if (position >= getCount()) {
                position = getCount() - 1;
            }
            return getItem(position).sectionPosition;
        }

    }

    static class Item {

        public static final int ITEM = 0;
        public static final int SECTION = 1;

        public final int type;
        public final String text;

        public int sectionPosition;
        public int listPosition;

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

    }

    private boolean hasHeaderAndFooter;
    private boolean isFastScroll;
    private boolean addPadding;
    private boolean isShadowVisible = true;

    private Taxonomies mTaxonomies = new Taxonomies();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            isFastScroll = savedInstanceState.getBoolean("isFastScroll");
            addPadding = savedInstanceState.getBoolean("addPadding");
            isShadowVisible = savedInstanceState.getBoolean("isShadowVisible");
            hasHeaderAndFooter = savedInstanceState.getBoolean("hasHeaderAndFooter");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_taxonomy, container, false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_taxonomy, menu);
        menu.getItem(0).setChecked(isFastScroll);
        menu.getItem(1).setChecked(addPadding);
        menu.getItem(2).setChecked(isShadowVisible);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_fastscroll:
                isFastScroll = !isFastScroll;
                item.setChecked(isFastScroll);
                initializeAdapter();
                updateView();
                break;
            case R.id.action_addpadding:
                addPadding = !addPadding;
                item.setChecked(addPadding);
                initializePadding();
                break;
            case R.id.action_showShadow:
                isShadowVisible = !isShadowVisible;
                item.setChecked(isShadowVisible);
                ((PinnedSectionListView)getListView()).setShadowVisible(isShadowVisible);
                break;
            case R.id.action_showHeaderAndFooter:
                hasHeaderAndFooter = !hasHeaderAndFooter;
                item.setChecked(hasHeaderAndFooter);
                initializeHeaderAndFooter();
                updateView();
                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFastScroll", isFastScroll);
        outState.putBoolean("addPadding", addPadding);
        outState.putBoolean("isShadowVisible", isShadowVisible);
        outState.putBoolean("hasHeaderAndFooter", hasHeaderAndFooter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeHeaderAndFooter();
        initializeAdapter();
        initializePadding();

        Callback<Taxonomies> taxonomiesCallback = new Callback<Taxonomies>() {
            @Override
            public void success(Taxonomies taxonomies, Response response) {
                Log.d(TAG, "Got " + taxonomies.taxonomies.size() + " taxonomies");
                mTaxonomies = taxonomies;
                updateView();
            }

            @Override
            public void failure(RetrofitError error) {
            }
        };
        TaxonomyModel.getTaxonomies(taxonomiesCallback);
    }

    private void initializePadding() {
        float density = getResources().getDisplayMetrics().density;
        int padding = addPadding ? (int) (16 * density) : 0;
        getListView().setPadding(padding, padding, padding, padding);
    }

    private void initializeHeaderAndFooter() {
        setListAdapter(null);
        if (hasHeaderAndFooter) {
            ListView list = getListView();

            LayoutInflater inflater = LayoutInflater.from(this.getActivity());
            TextView header1 = (TextView) inflater.inflate(
                    android.R.layout.simple_list_item_1, list, false);
            header1.setText("First header");
            list.addHeaderView(header1);

            TextView header2 = (TextView) inflater.inflate(
                    android.R.layout.simple_list_item_1, list, false);
            header2.setText("Second header");
            list.addHeaderView(header2);

            TextView footer = (TextView) inflater.inflate(
                    android.R.layout.simple_list_item_1, list, false);
            footer.setText("Single footer");
            list.addFooterView(footer);
        }
        initializeAdapter();
    }

    private void updateView() {
        SimpleAdapter adapter = (SimpleAdapter) getListAdapter();
        adapter.retrieveFromTaxonomy(mTaxonomies);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("NewApi")
    private void initializeAdapter() {
        getListView().setFastScrollEnabled(isFastScroll);
        if (isFastScroll) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                getListView().setFastScrollAlwaysVisible(true);
            }
            setListAdapter(new FastScrollAdapter(this.getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1));
        } else {
            setListAdapter(new SimpleAdapter(this.getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1));
        }
    }
}

package edu.njupt.xfilter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chen on 2018/1/19.
 */

public class FilterSetting extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar topBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.list)
    ListView mListView;
    @BindView(R.id.text_count)
    TextView mCount;

    private SimpleAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_setting);
        ButterKnife.bind(this);
        initTopBar();
        initTabs();
        initList();
    }

    private void initTabs() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.web_black_list));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.key_word));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if (pos == 0) {
                    adapter.initData(getResources().getStringArray(R.array.black_list));
                } else {
                    adapter.initData(getResources().getStringArray(R.array.key_word));
                }
                mCount.setText(getString(R.string.count, adapter.getCount()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initTopBar() {
        topBar.setTitle(R.string.site_detector);
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initList() {
        adapter = new SimpleAdapter();
        adapter.initData(getResources().getStringArray(R.array.black_list));
        mListView.setAdapter(adapter);
        mCount.setText(getString(R.string.count, adapter.getCount()));
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, FilterSetting.class);
        context.startActivity(starter);
    }

    class SimpleAdapter extends BaseAdapter {

        private List<String> dataList = new ArrayList<>();
        private List<String> webList = new ArrayList<>();
        private List<String> keywordList = new ArrayList<>();

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public String getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ItemViewHolder holder;

            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.list_item, null);
                holder = new ItemViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ItemViewHolder) view.getTag();
            }

            holder.mContent.setText(getItem(i));
            return view;
        }

        void initData(String[] data) {
            dataList.clear();
            dataList.addAll(Arrays.asList(data));
            notifyDataSetChanged();
        }

        void addKeyWord(String word) {
            keywordList.add(word);
            dataList.addAll(0, keywordList);
            notifyDataSetChanged();
        }

        void addUrl(String url) {
            webList.add(url);
            dataList.addAll(0, webList);
            notifyDataSetChanged();
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_content)
        TextView mContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

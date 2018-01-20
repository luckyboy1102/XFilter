package edu.njupt.xfilter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

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
                    adapter.addUrl();
                } else {
                    adapter.addKeyWord();
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
        topBar.addRightImageButton(R.drawable.ic_add_black_24dp, R.id.right_button)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
    }

    private void initList() {
        adapter = new SimpleAdapter();
        adapter.addUrl();
        mListView.setAdapter(adapter);
        mCount.setText(getString(R.string.count, adapter.getCount()));
    }

    private void showInputDialog() {
        final String tabText = tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText().toString();
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(this);
        builder.setTitle(getString(R.string.add, tabText));
        builder.setPlaceholder(getString(R.string.placeholder, tabText));
        builder.addAction(R.string.cancel, new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        });
        builder.addAction(R.string.ok, new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                String text = builder.getEditText().getText().toString();
                int selectedPos = tabLayout.getSelectedTabPosition();
                if (!TextUtils.isEmpty(text)) {
                    if (selectedPos == 0) {
                        adapter.addUrl(text);
                    } else {
                        adapter.addKeyWord(text);
                    }
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, FilterSetting.class);
        context.startActivity(starter);
    }

    class SimpleAdapter extends BaseAdapter {

        private List<String> dataList = new ArrayList<>();
        private List<String> webList = new ArrayList<>();
        private List<String> keywordList = new ArrayList<>();

        SimpleAdapter() {
            webList.addAll(getStringList(R.array.black_list));
            keywordList.addAll(getStringList(R.array.key_word));
        }

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

            holder.mContent.setText(Html.fromHtml(getItem(i)));
            return view;
        }

        List<String> getStringList(int res) {
            return Arrays.asList(getResources().getStringArray(res));
        }

        void addKeyWord(String... word) {
            keywordList.addAll(0, Arrays.asList(word));
            addToData(keywordList);
        }

        void addUrl(String... url) {
            webList.addAll(0, Arrays.asList(url));
            addToData(webList);
        }

        private void addToData(List<String> list) {
            dataList.clear();
            dataList.addAll(list);
            notifyDataSetChanged();
            mCount.setText(getString(R.string.count, adapter.getCount()));
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

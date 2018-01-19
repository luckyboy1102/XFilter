package edu.njupt.xfilter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBar;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chen on 2018/1/19.
 */

public class CheckerResult extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar topBar;
    @BindView(R.id.layout_type)
    ViewGroup mLayoutType;
    @BindView(R.id.layout_result)
    ViewGroup mLayoutResult;
    @BindView(R.id.text_url)
    TextView mUrl;
    @BindView(R.id.text_name)
    TextView mName;
    @BindView(R.id.text_type)
    TextView mType;
    @BindView(R.id.text_result)
    TextView mResult;

    private CheckerInfo info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        info = (CheckerInfo) getIntent().getSerializableExtra("info");
        setContentView(R.layout.activity_check_result);
        ButterKnife.bind(this);
        initTopBar();
        showResult();
    }

    private void showResult() {
        if (info.checkType == CheckerInfo.TYPE_BLACK_LIST) {
            mLayoutResult.setVisibility(View.GONE);
        } else {
            mLayoutType.setVisibility(View.GONE);
        }

        mUrl.setText(info.url);
        mName.setText(info.name);
        mType.setText(info.type);
        mResult.setText(info.result);
    }

    private void initTopBar() {
        topBar.setTitle(info.checkType == CheckerInfo.TYPE_BLACK_LIST ?
                R.string.black_list : R.string.content_detect);

        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void start(Context context, CheckerInfo checkerInfo) {
        Intent starter = new Intent(context, CheckerResult.class);
        starter.putExtra("info", checkerInfo);
        context.startActivity(starter);
    }

    static class CheckerInfo implements Serializable {

        static final int TYPE_BLACK_LIST = 0;
        static final int TYPE_CONTENT = 1;

        String url;
        String name;
        String type;
        int level;
        String result;

        int checkType;
    }
}

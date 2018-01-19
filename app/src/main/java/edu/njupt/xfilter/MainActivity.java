package edu.njupt.xfilter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chen on 2018/1/10.
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar topBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        topBar.setTitle(R.string.app_name);
    }

    @OnClick({R.id.button_browser, R.id.button_detector})
    void itemClick(View view) {
        switch (view.getId()) {
            case R.id.button_browser:
                BrowserActivity.start(this);
                break;
            case R.id.button_detector:
                SiteChecker.start(this);
                break;
            default:
                break;
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }
}

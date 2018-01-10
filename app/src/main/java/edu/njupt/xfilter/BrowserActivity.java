package edu.njupt.xfilter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Chen on 2018/1/10.
 */

public class BrowserActivity extends AppCompatActivity {

    @BindView(R.id.edit_url)
    EditText mUrl;
    @BindView(R.id.webview)
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }

    @OnClick(R.id.button_go)
    void safari() {
        String url = mUrl.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            if (!url.startsWith("http://") || !url.startsWith("https://")) {
                url = "http://" + url;
            }
            webView.loadUrl(url);
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, BrowserActivity.class);
        context.startActivity(starter);
    }
}

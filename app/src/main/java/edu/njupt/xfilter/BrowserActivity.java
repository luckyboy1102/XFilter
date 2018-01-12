package edu.njupt.xfilter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * Created by Chen on 2018/1/10.
 */

public class BrowserActivity extends AppCompatActivity {

    @BindView(R.id.edit_url)
    EditText mUrl;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.topbar)
    QMUITopBar topBar;

    boolean filterOn = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        initTopBar();
        initWebView();
    }

    private void initTopBar() {
        topBar.setTitle(R.string.app_name);
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        topBar.addRightTextButton(R.string.filter_on, R.id.right_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterOn = !filterOn;
                ((Button) v).setText(filterOn ? R.string.filter_off : R.string.filter_on);
            }
        });
    }

    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
    }

    @OnEditorAction(R.id.edit_url)
    boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
            safari();
            return true;
        }
        return false;
    }

    @OnClick(R.id.button_go)
    void safari() {
        String url = mUrl.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            if (!url.startsWith("http://") || !url.startsWith("https://")) {
                url = "http://" + url;
            }
            webView.loadUrl(url);
            hideKeyboard();
        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, BrowserActivity.class);
        context.startActivity(starter);
    }
}

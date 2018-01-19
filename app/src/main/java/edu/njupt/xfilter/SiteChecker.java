package edu.njupt.xfilter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Chen on 2018/1/19.
 */

public class SiteChecker extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar topBar;
    @BindView(R.id.text_hint)
    TextView mHint;
    @BindView(R.id.group)
    RadioGroup mGroup;
    @BindView(R.id.edit_url)
    EditText mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_checker);
        ButterKnife.bind(this);
        initTopBar();
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

    @OnCheckedChanged({R.id.radio_1, R.id.radio_2})
    void onCheckChange(RadioButton view, boolean checked) {
        if (checked) {
            mHint.setText(view.getId() == R.id.radio_1 ?
                    R.string.hint_black_list : R.string.hint_content_detect);
        }
    }

    @OnClick(R.id.next)
    void goInput() {
        CheckerInput.start(this,
                mGroup.getCheckedRadioButtonId() == R.id.radio_1 ?
                        CheckerResult.CheckerInfo.TYPE_BLACK_LIST :
                        CheckerResult.CheckerInfo.TYPE_CONTENT,
                mUrl.getText().toString());
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SiteChecker.class);
        context.startActivity(starter);
    }
}

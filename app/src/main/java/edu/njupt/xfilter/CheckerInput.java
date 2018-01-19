package edu.njupt.xfilter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chen on 2018/1/19.
 */

public class CheckerInput extends AppCompatActivity {

    @BindView(R.id.edit_name)
    EditText mName;
    @BindView(R.id.edit_type)
    EditText mType;
    @BindView(R.id.edit_level)
    EditText mLevel;
    @BindView(R.id.edit_result)
    EditText mResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checker_input);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.next)
    void showResult() {
        CheckerResult.CheckerInfo info = new CheckerResult.CheckerInfo();
        info.name = mName.getText().toString();
        info.type = mType.getText().toString();
        info.level = Integer.valueOf(mLevel.getText().toString());
        info.result = mResult.getText().toString();
        info.checkType = getIntent().getIntExtra("type", CheckerResult.CheckerInfo.TYPE_BLACK_LIST);
        info.url = getIntent().getStringExtra("url");

        CheckerResult.start(this, info);
    }

    public static void start(Context context, int type, String url) {
        Intent starter = new Intent(context, CheckerInput.class);
        starter.putExtra("type", type);
        starter.putExtra("url", url);
        context.startActivity(starter);
    }
}

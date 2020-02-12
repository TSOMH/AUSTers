package swle.xyz.austers.activity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import swle.xyz.austers.R;

public class MyDynamicActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dynamic);
    }

    @Override
    public void initView() {
        setToolbar(toolbar,"我的帖子");
    }

    @Override
    public void initEvent() {

    }
}

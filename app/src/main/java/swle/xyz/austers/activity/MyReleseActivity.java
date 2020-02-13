package swle.xyz.austers.activity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import swle.xyz.austers.R;

public class MyReleseActivity extends BaseActivity {


    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_relese);
        initView();
        initEvent();
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar_my_release_activity);
        setToolbar(toolbar,"我的发布");
    }

    @Override
    public void initEvent() {

    }
}

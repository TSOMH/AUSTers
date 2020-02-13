package swle.xyz.austers.activity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import swle.xyz.austers.R;

public class AcountBoundActivity extends BaseActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_bound);
        initView();
        initEvent();
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar_account_bound_activity);
        setToolbar(toolbar,"账号绑定");
    }

    @Override
    public void initEvent() {

    }
}

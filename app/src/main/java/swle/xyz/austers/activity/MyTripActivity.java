package swle.xyz.austers.activity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import swle.xyz.austers.R;

public class MyTripActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trip);

        initView();
        initEvent();
    }

    @Override
    public void initEvent() {
        setToolbar(toolbar,"我的行程");
    }
    @Override
    public void initView(){
        toolbar = findViewById(R.id.toolbar_my_trip_activity);
    }
}

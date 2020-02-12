package swle.xyz.austers.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import swle.xyz.austers.R;

public class PersonalInfoActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);


        initView();
        initEvent();
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar_personal_info_activity);
        setToolbar(toolbar,"个人资料");
    }

    @Override
    public void initEvent() {

    }
}

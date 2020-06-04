package swle.xyz.austers.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import swle.xyz.austers.R;
import swle.xyz.austers.httputil.JWXT;

public class ClassScheduleActivity extends BaseActivity {

    Button button_get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_schedule);
        initView();
        initEvent();
    }

    @Override
    public void initEvent() {
        button_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JWXT jwxt = new JWXT("2018304008","122274");
            }
        });
    }

    @Override
    public void initView() {
        button_get = findViewById(R.id.button_get);
    }
}
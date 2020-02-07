package swle.xyz.austers.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import swle.xyz.austers.R;

public class SettingsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button_identity_authenticate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        initEvent();
    }
    private void initView(){

        toolbar = findViewById(R.id.toolbar_settingsActivity);
        setSupportActionBar(toolbar); //将toolbar设置为当前activity的操作栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayShowTitleEnabled(false);//隐藏toolbar默认显示的label

        button_identity_authenticate = findViewById(R.id.button_identity_authenticate);
    }

    private void initEvent(){

        button_identity_authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,IdentityAuthenticateActivity.class);
                startActivity(intent);
            }
        });
    }
}

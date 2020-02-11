package swle.xyz.austers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import swle.xyz.austers.R;

public class SettingsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button_identity_authenticate;
    private Button button_about;
    private Button button_account_bound;
    private Button button_exit_account;
    private Button button_user_information;

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
        button_about = findViewById(R.id.button_about);
        button_account_bound = findViewById(R.id.button_account_bound);
        button_exit_account = findViewById(R.id.button_exit_account);
        button_user_information = findViewById(R.id.button_user_information);

    }

    private void initEvent(){

        button_identity_authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,IdentityAuthenticateActivity.class);
                startActivity(intent);
            }
        });



        button_account_bound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button_user_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        button_exit_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}

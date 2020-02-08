package swle.xyz.austers.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import swle.xyz.austers.R;

public class AboutActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button_version;
    private Button button_oslicenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        intiEvent();
    }
    private void initView(){
        button_version = findViewById(R.id.button_version);
        button_oslicenses = findViewById(R.id.button_oos_licenses);

        toolbar = findViewById(R.id.toolbar_about_activity);
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

    }
    private void intiEvent(){
        button_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this,"当前已是最新版本",Toast.LENGTH_SHORT).show();
            }
        });

        button_oslicenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this,OSLicensesActivity.class);
                startActivity(intent);
            }
        });

    }
}

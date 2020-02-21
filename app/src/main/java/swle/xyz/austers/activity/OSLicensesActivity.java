package swle.xyz.austers.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.zzhoujay.richtext.RichText;

import swle.xyz.austers.R;

public class OSLicensesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textView;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oslicenses);
        initView();
        initEvent();
    }

    private void initView() {


        toolbar = findViewById(R.id.toolbar_oslicenses_activity);
        setSupportActionBar(toolbar); //将toolbar设置为当前activity的操作栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        getSupportActionBar().setTitle("开源项目");
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayShowTitleEnabled(true);//隐藏toolbar默认显示的label

        textView = findViewById(R.id.textView5);
    }

    private void initEvent() {
        text="* [glide](https://github.com/bumptech/glide)\n" +
                "* [okhttp](https://github.com/square/okhttp)\n" +
                "* [glide-transformations](https://github.com/wasabeef/glide-transformations)\n" +
                "* [RichText](https://github.com/zzhoujay/RichText)\n"+
                "* [MaterialEditText](https://github.com/rengwuxian/MaterialEditText)";
        RichText.fromMarkdown(text).into(textView);
    }
}


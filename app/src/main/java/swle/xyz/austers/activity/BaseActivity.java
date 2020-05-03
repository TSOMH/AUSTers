package swle.xyz.austers.activity;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public abstract class BaseActivity extends AppCompatActivity {


    /**
     * 初始化UI
     */
    public abstract void initView();


    /**
     * 初始化事件
     */
    public abstract void initEvent();

    /**
     * 设置toolbar,代替系统的actionbar
     * @param toolbar 此toolbar需在initView里绑定id
     * @param string toolbar标题
     */
    public void setToolbar(Toolbar toolbar,String string){
        setSupportActionBar(toolbar); //将toolbar设置为当前activity的操作栏
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        getSupportActionBar().setTitle(string);//设置toolbar标题
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayShowTitleEnabled(true);//隐藏toolbar默认显示的label
    }
}

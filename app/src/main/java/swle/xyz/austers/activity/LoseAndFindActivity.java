package swle.xyz.austers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.scalified.fab.ActionButton;

import swle.xyz.austers.R;

public class LoseAndFindActivity extends BaseActivity {

    Toolbar toolbar;
    ActionButton actionButton;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose_and_find);
        initView();
        initEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        getMenuInflater().inflate(R.menu.loseandfind_search, menu);
        //找到searchView
//        MenuItem searchItem = menu.findItem(R.id.searchView);

//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setIconified(true);//一开始处于展开状态
        searchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
        searchView.setIconifiedByDefault(true);//默认为true在框内，设置false则在框外
        searchView.setSubmitButtonEnabled(true);//显示提交按钮
        searchView.setQueryHint("请输入丢失物品种类");//设置默认无内容时的文字提示
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void initView() {
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar); //将toolbar设置为当前activity的操作栏
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//
//            }
//        });
//        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
//        getSupportActionBar().setDisplayShowTitleEnabled(false);//隐藏toolbar默认显示的label

        searchView = findViewById(R.id.searchView);
        searchView.setIconified(true);
        actionButton = findViewById(R.id.action_button);
        actionButton.setButtonColor(getResources().getColor(R.color.bottomicon));
        actionButton.setButtonColorPressed(getResources().getColor(R.color.bottomiconPrimary));
        actionButton.setShadowYOffset(3.0f);
        actionButton.setImageResource(R.drawable.ic_add);

    }

    @Override
    public void initEvent() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoseAndFindActivity.this,IssueLostActivity.class);
                startActivity(intent);
            }
        });
    }
}
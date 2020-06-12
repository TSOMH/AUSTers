package swle.xyz.austers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import swle.xyz.austers.R;
import swle.xyz.austers.adapter.LostAndFoundAdapter;
import swle.xyz.austers.bean.Objects;
import swle.xyz.austers.callback.ResponseCallBack;
import swle.xyz.austers.http.LostAndFoundHttpUtil;

public class LoseAndFindActivity extends BaseActivity {

    Toolbar toolbar;
    FloatingActionButton actionButton;
    SearchView searchView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose_and_find);
        handler = new Handler();
        initView();
        initEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

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
//        toolbar = findViewById(R.id.toolbar_issue_objects);
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
        actionButton = findViewById(R.id.button_add);


        recyclerView = findViewById(R.id.recyclerview_lostAndFound);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(myDataset);
//        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public void initEvent() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu=new PopupMenu(LoseAndFindActivity.this,view);//1.实例化PopupMenu
                getMenuInflater().inflate(R.menu.lost_and_found_menu,popupMenu.getMenu());//2.加载Menu资源
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    Intent intent = new Intent(LoseAndFindActivity.this, IssueObjectsActivity.class);
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.add_lost:
                                intent.putExtra("lostOrFound","lost");
                                startActivity(intent);
                                return true;
                            case R.id.add_found:
                                intent.putExtra("lostOrFound","found");
                                startActivity(intent);
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                final Objects objects = new Objects();
                objects.setKind(query);
                LostAndFoundHttpUtil.query(objects, new ResponseCallBack() {
                    @Override
                    public void failure() {

                    }

                    @Override
                    public void success(int code, String message, final Object data) {
                        final List<Objects> objectsList;
                        objectsList = (List<Objects>) data;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (objectsList.size() == 0){
                                    Toast.makeText(LoseAndFindActivity.this,"搜索结果为空，请重试",Toast.LENGTH_LONG).show();
                                }else {
                                    mAdapter = new LostAndFoundAdapter(objectsList,getApplicationContext());
                                    recyclerView.setAdapter(mAdapter);
                                    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
                                }
                            }
                        });
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
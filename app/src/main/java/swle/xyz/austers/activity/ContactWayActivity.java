package swle.xyz.austers.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import swle.xyz.austers.R;
import swle.xyz.austers.adapter.MyGridViewAdapter;
import swle.xyz.austers.bean.User;
import swle.xyz.austers.callback.ResponseCallBack;
import swle.xyz.austers.http.ContactWayHttpUtil;

public class ContactWayActivity extends BaseActivity {
    private Toolbar toolbar;
    private GridView gridView;
    private List<User> users0 = new ArrayList<>();
    private Handler handler;
    private AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_way);
        handler = new Handler();
        initView();
        initEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_view, menu);

        //找到searchView
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setIconified(false);//一开始处于展开状态
        searchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
        searchView.setIconifiedByDefault(true);//默认为true在框内，设置false则在框外
        searchView.setSubmitButtonEnabled(true);//显示提交按钮
        searchView.setQueryHint("教师，机构名称");//设置默认无内容时的文字提示
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                ContactWayHttpUtil.query(query, new ContactWayResultCallBack() {
//                    @Override
//                    public void success(int status_code, final List<User> users) {
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                users0 = users;
//                                if (users.size() == 0){
//                                    gridView.setAdapter(new MyGridViewAdapter(ContactWayActivity.this,users));
//                                    builder = new AlertDialog.Builder(ContactWayActivity.this);
//                                    builder.setMessage("搜索结果为空，请重试");
//                                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                        }
//                                    });
//                                    final AlertDialog dialog = builder.create();
//                                    dialog.show();
//                                }else {
//                                    gridView.setAdapter(new MyGridViewAdapter(ContactWayActivity.this,users));
//                                }
//
//                            }
//                        });
//
//
//                    }
//
//                    @Override
//                    public void failure(int status_code) {
//
//                    }
//                });

                ContactWayHttpUtil.query(query, new ResponseCallBack() {
                    @Override
                    public void failure() {

                    }

                    @Override
                    public void success(int code, String message, Object data) {
                        if (code == 1){
                            final List<User> users = (List<User>) data;
                            handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (users.size() == 0){
                                    gridView.setAdapter(new MyGridViewAdapter(ContactWayActivity.this,users));
                                    builder = new AlertDialog.Builder(ContactWayActivity.this);
                                    builder.setMessage("搜索结果为空，请重试");
                                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    final AlertDialog dialog = builder.create();
                                    dialog.show();
                                }else {
                                    gridView.setAdapter(new MyGridViewAdapter(ContactWayActivity.this,users));
                                }

                            }
                        });
                        }
                    }
                });
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

        toolbar = findViewById(R.id.toolbar_contact_way_activity);
        setSupportActionBar(toolbar); //将toolbar设置为当前activity的操作栏
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayShowTitleEnabled(false);//隐藏toolbar默认显示的label

        gridView = findViewById(R.id.gridView);




    }

    @Override
    public void initEvent() {
    }
}

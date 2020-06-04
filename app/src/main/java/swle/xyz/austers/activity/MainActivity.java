package swle.xyz.austers.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import swle.xyz.austers.BuildConfig;
import swle.xyz.austers.R;
import swle.xyz.austers.fragment.DiscoveryFragment;
import swle.xyz.austers.fragment.FirstFragment;
import swle.xyz.austers.fragment.MessageFragment;
import swle.xyz.austers.fragment.MineFragment;

public class MainActivity extends BaseActivity {

    private FirstFragment firstFragment;
    private DiscoveryFragment discoveryFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;
    private Fragment isFragment;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main2);
        initFragment(savedInstanceState);
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initEvent();
    }

    public void initFragment(Bundle savedInstanceState){
        if (savedInstanceState == null){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if (firstFragment == null) {
                firstFragment = new FirstFragment();
            }
            isFragment = firstFragment;
            ft.replace(R.id.container_2,firstFragment).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            resetToDefaultIcon();
            switch (item.getItemId()) {
                case R.id.firstFragment:
                    if (firstFragment == null) {
                        firstFragment = new FirstFragment();
                    }
                    item.setIcon(R.drawable.ic_home);
                    switchContent(isFragment, firstFragment);
                    return true;
                case R.id.discoveryFragment:
                    if (discoveryFragment == null) {
                        discoveryFragment = new DiscoveryFragment();
                    }
                    item.setIcon(R.drawable.ic_search);
                    switchContent(isFragment, discoveryFragment);
                    return true;
                case R.id.messageFragment:
                    if (messageFragment == null) {
                        messageFragment = new MessageFragment();
                    }
                    item.setIcon(R.drawable.ic_message);
                    switchContent(isFragment, messageFragment);
                    return true;
                case R.id.mineFragment:
                    if (mineFragment == null) {
                        mineFragment = new MineFragment();
                    }

                    switchContent(isFragment,mineFragment);
                    item.setIcon(R.drawable.ic_mine);
                    return true;
            }
            return false;
        }

    };

    private void resetToDefaultIcon() {
        MenuItem home = bottomNavigationView.getMenu().findItem(R.id.firstFragment);
        MenuItem search = bottomNavigationView.getMenu().findItem(R.id.discoveryFragment);
        MenuItem message = bottomNavigationView.getMenu().findItem(R.id.messageFragment);
        MenuItem mine = bottomNavigationView.getMenu().findItem(R.id.mineFragment);
        home.setIcon(R.drawable.ic_home_unchecked);
        search.setIcon(R.drawable.ic_search_unchecked);
        message.setIcon(R.drawable.ic_message_unchecked);
        mine.setIcon(R.drawable.ic_mine_unchecked);
    }


    public void switchContent(Fragment from, Fragment to) {
        if (isFragment != to) {
            isFragment = to;
            FragmentManager fm = getSupportFragmentManager();
            //添加渐隐渐现的动画
            FragmentTransaction ft = fm.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                ft.hide(from).add(R.id.container_2, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                ft.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //启动一个意图,回到桌面
            Intent backHome = new Intent(Intent.ACTION_MAIN);
            backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backHome.addCategory(Intent.CATEGORY_HOME);
            startActivity(backHome);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        checkAppVersion();
    }

    public void checkAppVersion(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://116.62.106.237:8080/update")
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = Objects.requireNonNull(response.body()).string();
                Gson gson = new Gson();
                new Version_info();
                Version_info version_info;
                version_info = gson.fromJson(json,Version_info.class);
                System.out.println(version_info.versionCode+"");
                System.out.println(BuildConfig.VERSION_CODE+"");
                if (version_info.versionCode > BuildConfig.VERSION_CODE){
                    AlertDialog.Builder builder = null;
                    builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("检查到有新版本,是否更新？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent= new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse("http://116.62.106.237:8080/austers.apk");
                            intent.setData(content_url);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    Looper.prepare();
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    Looper.loop();
                }

            }
        });
    }

    private static class Version_info{
        public String downloadUrl;
        public int versionCode;
        public String versionDes;
        public String versionName;
    }

}

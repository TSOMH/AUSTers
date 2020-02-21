package swle.xyz.austers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import swle.xyz.austers.R;
import swle.xyz.austers.fragment.DiscoveryFragment;
import swle.xyz.austers.fragment.FirstFragment;
import swle.xyz.austers.fragment.MessageFragment;
import swle.xyz.austers.fragment.MineFragment;

public class Main2Activity extends BaseActivity {

    private FirstFragment firstFragment;
    private DiscoveryFragment discoveryFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;
    private Fragment isFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main2);
        initFragment(savedInstanceState);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
            switch (item.getItemId()) {
                case R.id.firstFragment:
                    if (firstFragment == null) {
                        firstFragment = new FirstFragment();
                    }
                    switchContent(isFragment, firstFragment);
                    return true;
                case R.id.discoveryFragment:
                    if (discoveryFragment == null) {
                        discoveryFragment = new DiscoveryFragment();
                    }
                    switchContent(isFragment, discoveryFragment);
                    return true;
                case R.id.messageFragment:
                    if (messageFragment == null) {
                        messageFragment = new MessageFragment();
                    }
                    switchContent(isFragment, messageFragment);
                    return true;
                case R.id.mineFragment:
                    if (mineFragment == null) {
                        mineFragment = new MineFragment();
                    }
                    switchContent(isFragment,mineFragment);
                    return true;
            }
            return false;
        }

    };


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


    }
}

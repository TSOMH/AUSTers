package swle.xyz.austers.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import swle.xyz.austers.R;
import swle.xyz.austers.fragment.DiscoveryFragment;
import swle.xyz.austers.fragment.FirstFragment;
import swle.xyz.austers.fragment.MessageFragment;
import swle.xyz.austers.fragment.MineFragment;

public class Main2Activity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FirstFragment firstFragment;
    private DiscoveryFragment discoveryFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;

    private Fragment isFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initFragment(savedInstanceState);
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);

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


}

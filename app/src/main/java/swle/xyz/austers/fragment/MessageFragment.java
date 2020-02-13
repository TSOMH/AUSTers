package swle.xyz.austers.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import swle.xyz.austers.R;

public class MessageFragment extends Fragment {

    static final int NUM_ITEMS = 2;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private String[] strings = new String[]{"系统通知","私信"};

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment, container, false);
        fragmentList.add(new SystemMessageFragment());
        fragmentList.add(new PersonalMessageFragment());
        initView(view);
        return view;
    }

    private void initView(View view){
        TabLayout tab_layout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.viewpager_message);

        MyAdapter fragmentAdapter = new  MyAdapter(getChildFragmentManager());//此处必须为getChildFragmentManger()
        viewPager.setAdapter(fragmentAdapter);
        tab_layout.setupWithViewPager(viewPager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    private class MyAdapter extends FragmentPagerAdapter {
        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }

        /**
         * 避免fragment销毁
         * @param container
         * @param position
         * @param object
         */

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
        }
    }


}

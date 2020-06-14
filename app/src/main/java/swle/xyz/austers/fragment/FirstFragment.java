package swle.xyz.austers.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import swle.xyz.austers.R;
import swle.xyz.austers.activity.CarPoolActivity;
import swle.xyz.austers.activity.ClassScheduleActivity;
import swle.xyz.austers.activity.ContactWayActivity;
import swle.xyz.austers.activity.FleasMarketActivity;
import swle.xyz.austers.activity.GradeActivity;
import swle.xyz.austers.activity.LoseAndFindActivity;
import swle.xyz.austers.activity.NewsActivity;
import swle.xyz.austers.activity.VirusActivity;
import swle.xyz.austers.adapter.BannerAdapter;
import swle.xyz.austers.myclass.CurrentUser;
import swle.xyz.austers.myclass.IsStudentOnClickListener;
import swle.xyz.austers.room.UserDao;
import swle.xyz.austers.room.UserDataBase;
import swle.xyz.austers.room.UserRoom;
import swle.xyz.austers.viewmodel.FirstViewModel;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class FirstFragment extends Fragment {

    private ViewPager viewPager;
    private FirstViewModel mViewModel;
    private View view;
    private LinearLayout linearLayout;

    private ImageButton button_score;
    private ImageButton button_contact_way;
    private ImageButton button_car_pool;
    private ImageButton button_news;
    private ImageButton button_virus;
    private ImageButton button_class_schedule;
    private ImageButton button_lost_and_found;
    ImageButton button_fleas_market;

    private static FirstFragment newInstance() {
        return new FirstFragment();
    }
    private List<ImageView> imageViewList;
    private String[] url = new String[3];
    private int oldPosition = 0;
    private BannerAdapter adapter;
    private Handler handler;
    CurrentUser currentUser = CurrentUser.getInstance();
    private UserDataBase userDataBase;
    private UserDao userDao;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.first_fragment, container,false);
        userDataBase = UserRoom.getInstance(getContext());
        userDao = userDataBase.getUserDao();
        initView(view);
        initBanner(view);
        initEvent();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handler = new Handler();
        handler.postDelayed(new TimerRunnable(),5000);

        mViewModel = ViewModelProviders.of(this).get(FirstViewModel.class);
        // TODO: Use the ViewModel
    }
    private void initView(View view){
        button_score=view.findViewById(R.id.imageButtonScore);
        button_contact_way = view.findViewById(R.id.imageButtonContactWay);
        button_car_pool = view.findViewById(R.id.imageButtonCarPool);
        button_news = view.findViewById(R.id.imageButtonNews);
        button_virus = view.findViewById(R.id.imageButton_virus);
        button_class_schedule = view.findViewById(R.id.imageButtonClassSchedule);
        button_lost_and_found = view.findViewById(R.id.imageButton_lost_and_found);
        button_fleas_market = view.findViewById(R.id.imageButtonFleaMarket);
        viewPager = view.findViewById(R.id.bannerviewpager);
        linearLayout = view.findViewById(R.id.linearLayout);//dot所在布局
    }


    private void initEvent(){
        button_class_schedule.setOnClickListener(new IsStudentOnClickListener(getContext()) {
            @Override
            public void OnClick(View view) {
                Intent intent = new Intent(getActivity(), ClassScheduleActivity.class);
                startActivity(intent);
            }
        });
        button_score.setOnClickListener(new IsStudentOnClickListener(getContext()) {
            @Override
            public void OnClick(View view) {
                Intent intent = new Intent(getActivity(), GradeActivity.class);
                startActivity(intent);
            }
        });

        button_contact_way.setOnClickListener(new IsStudentOnClickListener(getContext()) {
            @Override
            public void OnClick(View view) {
                Intent intent = new Intent(getActivity(), ContactWayActivity.class);
                startActivity(intent);
            }
        });
        button_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });

        button_car_pool.setOnClickListener(new IsStudentOnClickListener(getContext()) {
            @Override
            public void OnClick(View view) {
                Intent intent = new Intent(getActivity(), CarPoolActivity.class);
                startActivity(intent);
            }
        });
        button_virus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VirusActivity.class);
                startActivity(intent);
            }
        });

        button_lost_and_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoseAndFindActivity.class);
                startActivity(intent);
            }
        });

        button_fleas_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FleasMarketActivity.class);
                startActivity(intent);
            }
        });
    }



    private void initBanner(View view){

        MultiTransformation<Bitmap> multi = new MultiTransformation<>(
//                new BlurTransformation(25),
                new RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.ALL));
        url[0] = "https://hbimg.huabanimg.com/7090efe43ccd38111112603d00eab5747fb37fd91e7e3-RqsMAQ_fw658/format/webp";
        url[1] = "https://hbimg.huabanimg.com/be49768a4d7ec60c9508ce8a0043f5e7fc7e353d47bb8-C5RsLe_fw658/format/webp";
        url[2] = "https://hbimg.huabanimg.com/09a1e59c3b4b98e03e9cefe2a65e4f0111a9efc618edb-oKz8Cc_fw658/format/webp";
        imageViewList = new ArrayList<>();

        //生成背景图
        if (DayChangedListener()){
            new Thread(){
                @Override
                public void run(){
                    Glide.get(requireActivity()).clearDiskCache();
                }
            }.start();
        }
        for (int i = 0;i < 10000;i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this)
                    .load(url[i%3])
                    .apply(bitmapTransform(multi))
                    .into(imageView);
            imageViewList.add(imageView);
        }


        //生成指示器
        for (int i = 0;i<url.length;i++){
            ImageView dot = new ImageView(getActivity());
            if (i == 0){
                dot.setImageResource(R.drawable.banner_checked);
            }else {
                dot.setImageResource(R.drawable.banner_unchecked);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40,20);
            params.setMargins(2,0,2,0);
            linearLayout.addView(dot,params);
        }

        adapter = new BannerAdapter(imageViewList);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(url.length*100);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                ImageView current_indicator,old_indicator;//imageView为获取的当前指示器，imageView1为旧的指示器

                current_indicator = (ImageView) linearLayout.getChildAt(position%url.length);//获取当前的指示点
                old_indicator = (ImageView) linearLayout.getChildAt(oldPosition%url.length);//获取旧的指示点

                current_indicator.setImageResource(R.drawable.banner_checked);
                old_indicator.setImageResource(R.drawable.banner_unchecked);

                oldPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    //设定轮播时间

    private class TimerRunnable implements Runnable{
        @Override
        public void run() {
            int curItem = viewPager.getCurrentItem();
            viewPager.setCurrentItem(curItem+1);
            if (handler!=null){
                handler.postDelayed(this,5000);
            }
        }
    }

    //日期变化监听
    private Boolean DayChangedListener(){
        SharedPreferences sp = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_MONTH);
        int yesterday = sp.getInt("yesterday",0);
        editor.putInt("yesterday",yesterday);
        editor.apply();

        if (yesterday != 0){ //不是第一次使用本应用
            if (yesterday == today){
                editor.commit();
                return false;
            }else {
                yesterday = today;
                editor.putInt("yesterday",yesterday);
                editor.commit();
                Glide.get(requireActivity()).clearMemory();
                return true;
            }
        }else { //第一次使用本应用
            editor.putInt("yesterday",today);
            editor.apply();
            editor.commit();
            return false;
        }
    }
}


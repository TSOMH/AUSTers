package swle.xyz.austers.fragment;

import androidx.annotation.NonNull;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import swle.xyz.austers.activity.BannerFirstViewActivity;
import swle.xyz.austers.activity.BannerScondViewActivity;
import swle.xyz.austers.activity.BannerThirdViewActivity;
import swle.xyz.austers.activity.MainActivity;
import swle.xyz.austers.myinterface.RegisterTrigger;
import swle.xyz.austers.viewmodel.FirstViewModel;
import swle.xyz.austers.R;
import swle.xyz.austers.viewpager.ClickableViewPager;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class FirstFragment extends Fragment {

    private FirstViewModel mViewModel;
    private View view;
    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;
    private ViewPager banner;
//    private ClickableViewPager banner;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;


    private ClickableViewPager viewpager;

    private FrameLayout fl;
    public BottomNavigationView bnv;




    public String[] imageurl = new String[]{
            "https://api.dujin.org/bing/1920.php",
            "https://api.dujin.org/bing/1920.php",
            "https://api.dujin.org/bing/1920.php"
    };


    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.first_fragment, container,false);

        bnv=view.findViewById(R.id.bottomNavigationView);


        fl = view.findViewById(R.id.root);
        setView();
        return view;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FirstViewModel.class);
        // TODO: Use the ViewModel
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item = banner.getCurrentItem();
                if(item==0){
                    Log.d(TAG,"111");
                }
            }
        });



            banner.setOnTouchListener(new View.OnTouchListener(){
                @Override
                  public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        int item = banner.getCurrentItem();
                            if (item == 0) {
//                                Intent intent = new Intent(getActivity(), BannerFirstViewActivity.class);
//                                startActivity(intent);
                                fl.removeAllViewsInLayout();
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.root, new BannerFirstViewFragment(),null)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (item == 1) {
                                Intent intent = new Intent(getActivity(), BannerScondViewActivity.class);
                                startActivity(intent);
                            } else if (item == 2) {
                                Intent intent = new Intent(getActivity(), BannerThirdViewActivity.class);
                                startActivity(intent);
                            }
                    }
                    return false;
            }
            });
    }








    private void setView() {
          banner=view.findViewById(R.id.bannerview);

        MultiTransformation<android.graphics.Bitmap> multi = new MultiTransformation<>(
//                new BlurTransformation(25),
                new RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.ALL));





        //        //显示的图片
        images = new ArrayList<>();
        for (int i = 0; i < imageurl.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            imageView.setBackgroundResource(imageIds[i]);
            Glide.with(this)
                    .load(imageurl[i])
                    .apply(bitmapTransform(multi))
                    .into(imageView);

            images.add(imageView);
        }

        //显示的小点
        dots = new ArrayList<>();
//        for(int i=0;i<imageurl.length;i++){
//            dots.add(view.findViewById(R.id.dot_0));
//        }
//
        dots.add(view.findViewById(R.id.dot_0));
        dots.add(view.findViewById(R.id.dot_1));
        dots.add(view.findViewById(R.id.dot_2));
//


//        dots.add(view.findViewById(R.id.dot_3));
//        dots.add(view.findViewById(R.id.dot_4));

//        title = view.findViewById(R.id.textView);
//        title.setText(titles[0]);

        adapter = new ViewPagerAdapter();
        banner.setAdapter(adapter);
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Nullable
            @Override
            public void onPageSelected(int position) {
//                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.banner_checked);
                dots.get(oldPosition).setBackgroundResource(R.drawable.banner_unchecked);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }


    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//          super.destroyItem(container, position, object);
//          view.removeView(view.getChildAt(position));
//          view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }

    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                5,
                5,
                TimeUnit.SECONDS);
    }


    /**
     * 图片轮播任务
     *
     * @author liuyazhuang
     */
    private class ViewPageTask implements Runnable {

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageurl.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            banner.setCurrentItem(currentItem);
        }

        ;
    };

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }


    }


}


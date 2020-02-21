package swle.xyz.austers.fragment;

import android.content.Intent;
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
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import swle.xyz.austers.R;
import swle.xyz.austers.activity.ScoreActivity;
import swle.xyz.austers.adapter.BannerAdapter;
import swle.xyz.austers.viewmodel.FirstViewModel;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class FirstFragment extends Fragment {

    private ViewPager viewPager;
    private FirstViewModel mViewModel;
    private View view;
    private LinearLayout linearLayout;
    private ImageButton button_score;
    private static FirstFragment newInstance() {
        return new FirstFragment();
    }
    private List<ImageView> imageViewList;
    private String[] url = new String[3];
    private int oldPosition = 0;
    private int currentItem = 0;
    private BannerAdapter adapter;
    private Handler handler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.first_fragment, container,false);

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
        viewPager = view.findViewById(R.id.bannerviewpager);
        linearLayout = view.findViewById(R.id.linearLayout);//dot所在布局

    }
    private void initEvent(){
        button_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScoreActivity.class);
                startActivity(intent);
            }
        });
    }



    private void initBanner(View view){

        MultiTransformation<Bitmap> multi = new MultiTransformation<>(
//                new BlurTransformation(25),
                new RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.ALL));


        url[0] = "https://api.dujin.org/bing/1920.php";
        url[1] = "https://api.dujin.org/bing/1920.php";
        url[2] = "https://api.dujin.org/bing/1920.php";
        imageViewList = new ArrayList<>();

        /**
         * 生成背景图
         */
        for (int i = 0;i < 10000;i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this)
                    .load(url[i%3])
                    .apply(bitmapTransform(multi))
                    .into(imageView);
            imageViewList.add(imageView);
        }

        /**
         * 生成指示器
         */
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
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

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
}


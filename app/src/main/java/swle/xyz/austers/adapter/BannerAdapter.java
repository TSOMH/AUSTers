package swle.xyz.austers.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import swle.xyz.austers.activity.BannerFirstViewActivity;

/**
*Created by TSOMH on 2020/2/20$
*Description:
*
*/
public class BannerAdapter extends PagerAdapter   {

    private List<ImageView> imagesList;

    public BannerAdapter(List<ImageView> images) {
        this.imagesList = images;
    }
    @Override
    public int getCount() {
        return 1000;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, final int position) {
//        container.addView(imagesList.get(position));

        try {
            container.addView(imagesList.get(position%imagesList.size()));
            imagesList.get(position%imagesList.size()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BannerFirstViewActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }catch (Exception ignored){

        }
        return imagesList.get(position%imagesList.size());
    }

    @Override
    public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
//        container.removeView(imagesList.get(position));
    }

}

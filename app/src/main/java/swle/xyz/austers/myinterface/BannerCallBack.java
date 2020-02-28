package swle.xyz.austers.myinterface;

import android.widget.ImageView;

import java.util.List;

/**
 * Created by TSOMH on 2020/2/29$
 * Description:
 */
public interface BannerCallBack {
    void changed(List<ImageView> imageViewList);
    void unChanged(List<ImageView> imageViewList);
}

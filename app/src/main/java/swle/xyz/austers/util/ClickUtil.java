package swle.xyz.austers.util;
/**
*Created by TSOMH on 2020/2/13$
*Description:
*
*/
public class ClickUtil{

    // 两次点击按钮之间的点击间隔不能少于1000 毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    // 返回 true 为点击，false 为快速点击
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}

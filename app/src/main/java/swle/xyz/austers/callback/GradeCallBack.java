package swle.xyz.austers.callback;

/**
 * Created by TSOMH on 2020/6/2$
 * Description:
 */
public interface GradeCallBack {
    void failure(int status_code);
    void success(String[][] score);
    void noResult();
}

package swle.xyz.austers.callback;

/**
 * Created by TSOMH on 2020/5/3$
 * Description:
 */
public interface GetVcodeResultCallBack {
    void success(int status_code,int vcode);
    void failure(int status_code,int vcode);
}

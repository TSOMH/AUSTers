package swle.xyz.austers.callback;

/**
 * Created by TSOMH on 2020/6/10$
 * Description:
 */
public interface ResponseCallBack {
    void failure();
    void success(int code,String message,Object data);
}

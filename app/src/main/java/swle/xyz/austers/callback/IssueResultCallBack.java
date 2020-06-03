package swle.xyz.austers.callback;

/**
 * Created by TSOMH on 2020/5/30$
 * Description:
 */
public interface IssueResultCallBack {
    void success(int status_code);
    void failure(int status_code);
}

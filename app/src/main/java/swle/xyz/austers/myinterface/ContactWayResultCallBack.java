package swle.xyz.austers.myinterface;

import java.util.List;

import swle.xyz.austers.bean.User;

/**
 * Created by TSOMH on 2020/5/22$
 * Description:
 */
public interface ContactWayResultCallBack {
    void success(int status_code,List<User> users);
    void failure(int status_code);
}

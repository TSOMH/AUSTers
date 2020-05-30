package swle.xyz.austers.myinterface;

import java.util.List;

import swle.xyz.austers.bean.Trip;

/**
 * Created by TSOMH on 2020/5/25$
 * Description:
 */
public interface QueryOtherTripResultCallBack {
    void success(List<Trip> trips);
    void failure(int status_code);
}

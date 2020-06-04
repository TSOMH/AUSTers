package swle.xyz.austers.dialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import swle.xyz.austers.callback.IssueResultCallBack;
import swle.xyz.austers.httputil.OkHttpUtil;

/**
*Created by TSOMH on 2020/5/28$
*Description:
*
*/
public class IssueTripDialogfragment extends DialogFragment {

    private String starting;
    private String destination;
    private String initiator;
    private int seat_left;
    private int year;
    private int month;
    private int day;
    private int hour;

    private Context context;


    private final String[] items = {"1","2","3","4"};

    public IssueTripDialogfragment(String starting, String destination, String initiator, int year,
                                   int month, int day, int hour, Context context){
        this.starting = starting;
        this.destination = destination;
        this.initiator = initiator;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.context = context;

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){


        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("请选择车辆剩余座位(您已乘坐)");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                seat_left = which+1;
            }
        });
        builder.setPositiveButton("立即发起", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                OkHttpUtil.issueTrip(initiator, starting, destination, seat_left, year, month, day, hour, new IssueResultCallBack() {
                    @Override
                    public void success(int status_code) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("发起成功");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                            }
                        });
                        Looper.prepare();
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        Looper.loop();

                    }
                    @Override
                    public void failure(int status_code) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("发起失败，请重试");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                        Looper.prepare();
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        Looper.loop();

                    }
                });


            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}

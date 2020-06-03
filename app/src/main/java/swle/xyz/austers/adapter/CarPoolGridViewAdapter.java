package swle.xyz.austers.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

import swle.xyz.austers.R;
import swle.xyz.austers.bean.Trip;
import swle.xyz.austers.callback.GetInTripCallBack;
import swle.xyz.austers.util.OkHttpUtil;

/**
*Created by TSOMH on 2020/5/24$
*Description:
*
*/
public class CarPoolGridViewAdapter extends BaseAdapter {

    private List<Trip> trips;
    private Context context;
    private LayoutInflater layoutInflater;
    private AlertDialog.Builder builder = null;
    private AlertDialog.Builder builder1 = null;
    private AlertDialog.Builder builder2 = null;
    private Handler handler;

    SharedPreferences.Editor editor;
    SharedPreferences trip_info;





    public CarPoolGridViewAdapter(Context context,List<Trip> trips,SharedPreferences trip_info,SharedPreferences.Editor editor){
        this.trips = trips;
        this.context = context;
        this.editor = editor;
        this.trip_info = trip_info;
        layoutInflater = LayoutInflater.from(context);
        handler = new Handler();

    }


    @Override
    public int getCount() {
        return trips.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        int id;
        TextView starting;
        TextView destnatination;
        TextView time;
        TextView seat_left;
        TextView initiator;
        Button button_get_in;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;


        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.tripinfocardviewitem,null);
            viewHolder = new ViewHolder();
//            viewHolder.button = convertView.findViewById(R.id.button);
            viewHolder.starting = convertView.findViewById(R.id.textView35);
            viewHolder.destnatination = convertView.findViewById(R.id.textView37);
            viewHolder.time = convertView.findViewById(R.id.textView66);
            viewHolder.seat_left = convertView.findViewById(R.id.textView38);
            viewHolder.initiator = convertView.findViewById(R.id.textView40);
            viewHolder.button_get_in = convertView.findViewById(R.id.button_get_in);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.starting.setText(trips.get(position).getStarting());
        viewHolder.destnatination.setText(trips.get(position).getDestination());
        viewHolder.time.setText(trips.get(position).getMonth()+"月"+trips.get(position).getDay()+"日"+trips.get(position).getHour()+":00");
        viewHolder.seat_left.setText("剩余座位："+trips.get(position).getSeat_left());
        viewHolder.initiator.setText(trips.get(position).getInitiator());
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.button_get_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean is_got_in = trip_info.getBoolean(trips.get(position).getId()+"",false);
                if (is_got_in){
//                    Toast.makeText(context,"请不要重复乘坐！",Toast.LENGTH_LONG).show();
                    builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage("请不要重复乘坐！");
                    builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    final AlertDialog dialog = builder1.create();
                    dialog.show();

                }else {
                    builder = new AlertDialog.Builder(context);
                    builder.setMessage("是否确认乘坐？");

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            final int s_left = trips.get(position).getSeat_left();
                            System.out.println("id="+trips.get(position).getId());
                            if (s_left > 0){
                                OkHttpUtil.getInTrip(trips.get(position).getId(), new GetInTripCallBack() {
                                    @Override
                                    public void success(int status) {
                                        //位置减1
                                        trips.get(position).setSeat_left(s_left-1);
                                        editor.putBoolean(trips.get(position).getId()+"",true);
                                        editor.apply();
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                finalViewHolder.seat_left.setText("剩余座位："+(s_left-1));
                                                if (s_left == 1){
                                                    finalViewHolder.button_get_in.setEnabled(false);
                                                }
                                            }
                                        });
                                        builder2 = new AlertDialog.Builder(context);
                                        builder2.setMessage("乘坐成功！");
                                        builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                        Looper.prepare();
                                        final AlertDialog dialog = builder2.create();
                                        dialog.show();
                                        Looper.loop();
                                        finalViewHolder.button_get_in.setEnabled(false);

                                    }
                                    @Override
                                    public void failure(int status) {
                                    }
                                });
                            }
                        }
                    });
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        return convertView;
    }
}

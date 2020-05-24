package swle.xyz.austers.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import swle.xyz.austers.R;
import swle.xyz.austers.bean.Trip;

/**
*Created by TSOMH on 2020/5/24$
*Description:
*
*/
public class CarPoolGridViewAdapter extends BaseAdapter   {

    private List<Trip> trips = new ArrayList<>();
    private Context context = null;
    private LayoutInflater layoutInflater;
    private AlertDialog.Builder builder = null;
    private int size;


    public CarPoolGridViewAdapter(Context context,List<Trip> trips){
        this.size = trips.size();
        this.trips = trips;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return size;
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
        TextView starting;
        TextView destnatination;
        TextView time;
        TextView seat_left;
        TextView initiator;
        Button button_get_in;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.tripinfocardviewitem,null);
            viewHolder = new ViewHolder();
//            viewHolder.button = convertView.findViewById(R.id.button);
            viewHolder.starting = convertView.findViewById(R.id.textView35);
            viewHolder.destnatination = convertView.findViewById(R.id.textView37);
            viewHolder.time = convertView.findViewById(R.id.textView39);
            viewHolder.seat_left = convertView.findViewById(R.id.textView38);
            viewHolder.initiator = convertView.findViewById(R.id.textView40);
            viewHolder.button_get_in = convertView.findViewById(R.id.button_get_in);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.starting.setText(trips.get(position).getStarting());
        viewHolder.destnatination.setText(trips.get(position).getDestination());
        viewHolder.time.setText("日期："+trips.get(position).getMonth()+"月"+trips.get(position).getDay()+"日");
        viewHolder.seat_left.setText("剩余座位："+trips.get(position).getSeat_left());
        viewHolder.initiator.setText("发起人："+trips.get(position).getInitiator());
        viewHolder.button_get_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(context);
                builder.setMessage("搜索结果为空，请重试");
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
//        viewHolder.QQ.setText("QQ:"+users.get(position).getQq());
        return convertView;
    }
}

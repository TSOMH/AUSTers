package swle.xyz.austers.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import swle.xyz.austers.R;
import swle.xyz.austers.bean.User;


/**
*Created by TSOMH on 2020/5/23$
*Description:
*
*/
public class MyGridViewAdapter extends BaseAdapter   {
    int size;
    private Context context = null;
    private LayoutInflater layoutInflater;
    List<User> users = new ArrayList<>();
    public MyGridViewAdapter(Context context,List<User> users){
        this.size = users.size();
        this.users = users;
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
        public TextView name;
        public TextView organization;
        public TextView phonenumber;
        private TextView QQ;
        private Button button;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.contactifocardviewitem,null);
            viewHolder = new ViewHolder();
//            viewHolder.button = convertView.findViewById(R.id.button);
            viewHolder.name = convertView.findViewById(R.id.textView36);
            viewHolder.organization = convertView.findViewById(R.id.textView42);
            viewHolder.phonenumber = convertView.findViewById(R.id.textView41);
//            viewHolder.QQ = convertView.findViewById(R.id.textView43);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        viewHolder.name.setText(users.get(position).getName());
        viewHolder.organization.setText("学院/机构："+users.get(position).getOrganization());
        viewHolder.phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigInteger b = new BigInteger(users.get(position).getPhonenumber());
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+b.longValue()));//跳转到拨号界面，同时传递电话号码
                context.startActivity(dialIntent);
            }
        });
        viewHolder.phonenumber.setText("电话："+users.get(position).getPhonenumber());
//        viewHolder.QQ.setText("QQ:"+users.get(position).getQq());
        return convertView;
    }
}

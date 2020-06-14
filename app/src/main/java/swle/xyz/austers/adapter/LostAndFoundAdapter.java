package swle.xyz.austers.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;

import swle.xyz.austers.R;
import swle.xyz.austers.bean.Objects;

/**
*Created by TSOMH on 2020/6/12$
*Description:
*
*/
public class LostAndFoundAdapter extends RecyclerView.Adapter<LostAndFoundAdapter.MyViewHolder> {
   private List<Objects> objectsList;
   Context context;

   // Provide a reference to the views for each data item
   // Complex data items may need more than one view per item, and
   // you provide access to all the views for a data item in a view holder
   public static class MyViewHolder extends RecyclerView.ViewHolder {
      // each data item is just a string in this case
      public TextView textView_kind;
      public TextView textView_info;
      public TextView textView_contact_way;
      public TextView textView_date;
      public ImageView imageView;
      public MyViewHolder(View v) {
         super(v);
         textView_kind = v.findViewById(R.id.textView_kind);
         textView_info = v.findViewById(R.id.textView_info);
         textView_contact_way = v.findViewById(R.id.textView_contact_way);
         textView_date = v.findViewById(R.id.textView_date_object);
         imageView = v.findViewById(R.id.imageView5);

      }
   }

   // Provide a suitable constructor (depends on the kind of dataset)
   public LostAndFoundAdapter(List<Objects> objectsList, Context context) {
      this.objectsList = objectsList;
      this.context = context;
   }

   // Create new views (invoked by the layout manager)
   @NotNull
   @Override
   public LostAndFoundAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
      // create a new view
      View v = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.object, parent, false);
      return new MyViewHolder(v);
   }

   // Replace the contents of a view (invoked by the layout manager)
   @SuppressLint("SetTextI18n")
   @Override
   public void onBindViewHolder(MyViewHolder holder, int position) {
      // - get element from your dataset at this position
      // - replace the contents of the view with that element
      holder.textView_kind.setText("种类："+objectsList.get(position).getKind());
      holder.textView_info.setText("信息："+objectsList.get(position).getInfo());
      holder.textView_contact_way.setText("联系方式："+objectsList.get(position).getCurrentHost());
      SimpleDateFormat sdf = new SimpleDateFormat( " MM-dd-yyyy HH:mm:ss " );
//      String s = sdf.format();
      holder.textView_date.setText("时间："+objectsList.get(position).getLostOrFoundDate());
      Glide.with(context)
              .load(objectsList.get(position).getImgaddress())
              .placeholder(R.drawable.ic_placeholder)
              .into(holder.imageView);

   }

   // Return the size of your dataset (invoked by the layout manager)
   @Override
   public int getItemCount() {
      return objectsList.size();
   }
}

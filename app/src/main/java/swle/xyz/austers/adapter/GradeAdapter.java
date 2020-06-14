package swle.xyz.austers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import swle.xyz.austers.R;
import swle.xyz.austers.bean.Grade;

/**
*Created by TSOMH on 2020/6/2$
*Description:
*
*/
public class GradeAdapter extends BaseAdapter {

   List<Grade> grades;
   Context context;
   LayoutInflater layoutInflater;


   public GradeAdapter(List<Grade> grades,Context context){
      layoutInflater = LayoutInflater.from(context);
      this.grades = grades;
   }
   @Override
   public int getCount() {
      return grades.size();
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
      TextView textView_course;
      TextView textView_score;
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder viewHolder;
      if (convertView == null){
         convertView = layoutInflater.inflate(R.layout.grade_item,null);
         viewHolder = new ViewHolder();
         viewHolder.textView_course = convertView.findViewById(R.id.textView_course);
         viewHolder.textView_score = convertView.findViewById(R.id.textView_score);
         convertView.setTag(viewHolder);
      }else {
         viewHolder = (ViewHolder) convertView.getTag();
      }
      viewHolder.textView_course.setText(grades.get(position).getCourse());
      viewHolder.textView_score.setText(grades.get(position).getScore());

      return convertView;
   }
}

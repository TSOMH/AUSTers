package swle.xyz.austers.myclass;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

import swle.xyz.austers.activity.IdentityAuthenticateActivity;
import swle.xyz.austers.room.User;
import swle.xyz.austers.room.UserDao;
import swle.xyz.austers.room.UserDataBase;
import swle.xyz.austers.room.UserRoom;

/**
*Created by TSOMH on 2020/6/2
*Description:
*
*/
public abstract class IsStudentOnClickListener implements View.OnClickListener {
   Context context;
   private UserDao userDao;
   CurrentUser currentUser = CurrentUser.getInstance();
   private AlertDialog.Builder builder = null;


   public abstract void OnClick(View view);
   public IsStudentOnClickListener(Context context){
      this.context = context;
   }

   @Override
   public void onClick(final View v) {
      UserDataBase userDataBase = UserRoom.getInstance(context);
      userDao = userDataBase.getUserDao();
      IsStudent(new IsStudent() {
         @Override
         public void is() {
            OnClick(v);
         }

         @Override
         public void not() {
            builder = new AlertDialog.Builder(context);
            builder.setTitle("提示");
            builder.setMessage("请进行学生身份验证");
            builder.setPositiveButton("去验证", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                  Intent intent = new Intent(context, IdentityAuthenticateActivity.class);
                  context.startActivity(intent);
               }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {

               }
            });
            Looper.prepare();
            builder.show();
            Looper.loop();
         }
      });
   }

   interface IsStudent{
      void is();
      void not();
   }

   void IsStudent(final IsStudent isStudent){
      new Thread(new Runnable() {
         @Override
         public void run() {
            List<User> users = userDao.getAll();
            if (users.size()>0){
               User user = userDao.queryUser(currentUser.phonenumber);
               if (user.isIs_student()){
                  isStudent.is();
               }else {
                  isStudent.not();
               }
            }else {
               isStudent.not();
            }
         }
      }).start();
   }
}

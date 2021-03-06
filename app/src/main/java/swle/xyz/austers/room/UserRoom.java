package swle.xyz.austers.room;

import android.content.Context;

import androidx.room.Room;

/**
*Created by TSOMH on 2020/5/30$
*Description:
 * 单例模式
 * 双检锁/双重校验锁（DCL，即 double-checked locking）
*/
public class UserRoom{
   private volatile static UserDataBase userDataBase = null;
   private UserRoom () {}

   public static UserDataBase getInstance(Context context) {
      if (userDataBase == null) {
         synchronized (UserDataBase.class) {
            if (userDataBase == null) {
               userDataBase= Room.databaseBuilder(context,UserDataBase.class,"user_info").build();
            }
         }
      }
      return userDataBase;
   }
}

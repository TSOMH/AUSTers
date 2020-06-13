package swle.xyz.austers.myclass;

/**
*Created by TSOMH on 2020/5/31$
*Description:
*
*/
public class CurrentUser{

   public String phonenumber;
   public String token;
   private static volatile CurrentUser currentUser = null;


   private CurrentUser(){
   }
   public static CurrentUser getInstance(){
      if (currentUser == null) {
         synchronized (CurrentUser.class) {
            if (currentUser == null) {
               currentUser = new CurrentUser();
            }
         }
      }
      return currentUser;
   }

}

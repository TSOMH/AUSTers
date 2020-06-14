package swle.xyz.austers.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import swle.xyz.austers.bean.ResponseBean;
import swle.xyz.austers.bean.User;
import swle.xyz.austers.callback.ResponseCallBack;
import swle.xyz.austers.myclass.CurrentUser;

/**
*Created by TSOMH on 2020/6/14
*Description:
*
*/
public class ContactWayHttpUtil{

   private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
   private static ResponseBean responseBean = new ResponseBean(0,null,null);
//      static final String url = "http://10.0.2.2:8081";
   static final String url = "https://swle.top:8081";



   public static void query(String name, final ResponseCallBack callBack){
      CurrentUser currentUser = CurrentUser.getInstance();
      User user = new User(name);
      final Gson gson = new Gson();
      String json = gson.toJson(user);
      System.out.println(json);
      RequestBody requestBody = RequestBody.create(json,JSON);
      OkHttpClient okHttpClient = new OkHttpClient.Builder()
              .connectTimeout(5, TimeUnit.SECONDS)
              .build();
      final Request request = new Request.Builder()
              .url(url+"/contact_way/query")
              .addHeader("Authorization",currentUser.token)
              .post(requestBody)
              .build();
      Call call = okHttpClient.newCall(request);
      call.enqueue(new Callback() {
         @Override
         public void onFailure(@NotNull Call call, @NotNull IOException e) {
            e.printStackTrace();
            callBack.failure();
            System.out.println("查询失败");
         }

         @Override
         public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            String json = Objects.requireNonNull(response.body()).string();
            System.out.println(json);
            Gson gson = new Gson();
            responseBean = gson.fromJson(json,ResponseBean.class);
            System.out.println(responseBean.getData().toString());
            List<User> users = gson.fromJson(responseBean.getData().toString(),new TypeToken<List<User>>(){}.getType());
            callBack.success(1,responseBean.getMsg(),users);
         }
      });
   }
}

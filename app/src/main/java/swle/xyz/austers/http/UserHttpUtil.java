package swle.xyz.austers.http;

import android.util.Log;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
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

/**
*Created by TSOMH on 2020/6/10$
*Description:
*
*/
public class UserHttpUtil{
   private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
   private static ResponseBean responseBean = new ResponseBean(0,null,null);

//   static final String test_url = "http://10.0.2.2:8081";
   static final String url = "https://swle.top:8081";

   public static void getAuthCode(String phonenumber,final ResponseCallBack responseCallBack){

      User user = new User(null,null,null,phonenumber);

      Gson gson = new Gson();
      String json = gson.toJson(user);
      System.out.println(json);
      RequestBody requestBody = RequestBody.create(json,JSON);
      OkHttpClient okHttpClient = new OkHttpClient.Builder()
              .connectTimeout(5,TimeUnit.SECONDS)
              .build();
      final Request request = new Request.Builder()
              .url(url+"/users/getsms")
              .post(requestBody)
              .build();

      Call call = okHttpClient.newCall(request);
      call.enqueue(new Callback() {
         @Override
         public void onFailure(@NotNull Call call, @NotNull IOException e) {
            Log.d("signin","failure");
            responseCallBack.failure();
         }
         @Override
         public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            String json = Objects.requireNonNull(response.body()).string();
            System.out.println("json:"+json);
            Gson gson1 = new Gson();
            responseBean = gson1.fromJson(json, ResponseBean.class);
            responseCallBack.success(responseBean.getCode(),responseBean.getMsg(),responseBean.getData());

         }
      });
   }


   public static void SignIn(String phonenumber, String password, final ResponseCallBack responseCallBack){
      User user = new User(null,password,null,phonenumber);
      Gson gson = new Gson();
      String json = gson.toJson(user);
      RequestBody requestBody = RequestBody.create(json,JSON);
      OkHttpClient okHttpClient = new OkHttpClient.Builder()
              .connectTimeout(5,TimeUnit.SECONDS)
              .build();
      Request request =new Request.Builder()
              .url(url+"/users/signin")
              .post(requestBody)
              .build();
      Call call = okHttpClient.newCall(request);
      call.enqueue(new Callback() {
         @Override
         public void onFailure(@NotNull Call call, @NotNull IOException e) {
            responseCallBack.failure();
         }

         @Override
         public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            String json = Objects.requireNonNull(response.body()).string();
            Gson gson1 = new Gson();
            responseBean = gson1.fromJson(json, ResponseBean.class);
            responseCallBack.success(responseBean.getCode(),responseBean.getMsg(),responseBean.getData());
         }
      });
   }
   public static void Login(String phonenumber, String password, final ResponseCallBack responseCallBack){




      User user = new User(null,password,null,phonenumber);

      Gson gson = new Gson();

      String json = gson.toJson(user);  //json字符串
      RequestBody body = RequestBody.create(json,JSON);
      OkHttpClient okHttpClient = new OkHttpClient.Builder()
              .connectTimeout(3, TimeUnit.SECONDS)
              .build();
      Request request = new Request.Builder()
              .url(url+"/users/login")
              .post(body)
              .build();

      Call call = okHttpClient.newCall(request);
      call.enqueue(new Callback() {

         @Override
         public void onFailure(@NotNull Call call, @NotNull IOException e) {
            responseCallBack.failure();
            e.printStackTrace();
         }

         @Override
         public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            String json = Objects.requireNonNull(response.body()).string();
            System.out.println(json);
            Gson gson1 = new Gson();
            responseBean = gson1.fromJson(json,ResponseBean.class);
            responseCallBack.success(responseBean.getCode(),responseBean.getMsg(),responseBean.getData());
         }
      });
   }
}

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
import swle.xyz.austers.bean.Trip;
import swle.xyz.austers.callback.ResponseCallBack;
import swle.xyz.austers.myclass.CurrentUser;

/**
*Created by TSOMH on 2020/6/13
*Description:
*
*/
public class TripHttpUtil{


   private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
   private static ResponseBean responseBean = new ResponseBean(0,null,null);
   //   static final String test_url = "http://10.0.2.2:8081";
   static final String url = "https://swle.top:8081";

   public static void issueTrip(String initiator, String starting, String destination, int seat_left,
                                int year, int month, int day, int hour, final ResponseCallBack callBack){
      CurrentUser currentUser = CurrentUser.getInstance();
      Trip trip = new Trip(initiator,starting,destination,seat_left,year,month,day,hour);
      Gson gson =new Gson();
      String json = gson.toJson(trip);
      System.out.println(json);
      RequestBody requestBody = RequestBody.create(json,JSON);
      OkHttpClient okHttpClient = new OkHttpClient.Builder()
              .connectTimeout(5, TimeUnit.SECONDS)
              .build();
      final Request request = new Request.Builder()
              .addHeader("Authorization",currentUser.token)
              .url(url+"/trips/issue_trip")
              .post(requestBody)
              .build();
      Call call = okHttpClient.newCall(request);
      call.enqueue(new Callback() {
         @Override
         public void onFailure(@NotNull Call call, @NotNull IOException e) {
            callBack.failure();
         }

         @Override
         public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            String json = Objects.requireNonNull(response.body()).string();
            System.out.println("json="+json);
            Gson gson = new Gson();
            responseBean = gson.fromJson(json,ResponseBean.class);
            if (responseBean.getCode()==1){
               callBack.success(1,responseBean.getMsg(),null);
            }else if(responseBean.getCode()==-1){
               callBack.success(-1,responseBean.getMsg(),null);
            }
         }
      });
   }

   public static void queryOtherTrip(String initiator,String starting, String destination,int seat_left, int year, int month, int day,int hour,
                                     final ResponseCallBack callBack){
      CurrentUser currentUser = CurrentUser.getInstance();
      Trip trip = new Trip(initiator,starting,destination,seat_left,year,month,day,hour);
      Gson gson =new Gson();
      String json = gson.toJson(trip);
      System.out.println("request:"+json);
      RequestBody requestBody = RequestBody.create(json,JSON);
      OkHttpClient okHttpClient = new OkHttpClient.Builder()
              .connectTimeout(5,TimeUnit.SECONDS)
              .build();
      final Request request = new Request.Builder()
              .url(url+"/trips/query_other_trip")
              .addHeader("Authorization",currentUser.token)
              .post(requestBody)
              .build();
      Call call = okHttpClient.newCall(request);
      call.enqueue(new Callback() {
         @Override
         public void onFailure(@NotNull Call call, @NotNull IOException e) {
            callBack.failure();
         }

         @Override
         public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            String json = response.body().string();
            System.out.println(json);
            Gson gson = new Gson();
            ResponseBean responseBean = gson.fromJson(json,ResponseBean.class);
            List<Trip> trips = gson.fromJson(responseBean.getData().toString().trim(),new TypeToken<List<Trip>>(){}.getType());
            callBack.success(1,responseBean.getMsg(),trips);
         }
      });

   }
   public static void getInTrip(int id, final ResponseCallBack callBack){
      CurrentUser currentUser = CurrentUser.getInstance();
      String json = "{\"id\":"+id+"}";
      RequestBody requestBody = RequestBody.create(json,JSON);
      OkHttpClient okHttpClient = new OkHttpClient.Builder()
              .connectTimeout(5,TimeUnit.SECONDS)
              .build();
      final Request request = new Request.Builder()
              .url(url+"/trips/get_in_trip")
              .addHeader("Authorization",currentUser.token)
              .post(requestBody)
              .build();
      Call call = okHttpClient.newCall(request);
      call.enqueue(new Callback() {
         @Override
         public void onFailure(@NotNull Call call, @NotNull IOException e) {
            callBack.failure();
         }

         @Override
         public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            String json = Objects.requireNonNull(response.body()).string();
            System.out.println("乘坐结果："+json);
            Gson gson = new Gson();
            ResponseBean responseBean = gson.fromJson(json,ResponseBean.class);
            if (responseBean.getCode()==1){
               callBack.success(1,responseBean.getMsg(),responseBean.getData());
            }else {
               callBack.success(-1,responseBean.getMsg(),responseBean.getData());
            }
         }
      });
   }

}

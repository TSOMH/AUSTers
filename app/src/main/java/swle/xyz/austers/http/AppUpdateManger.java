package swle.xyz.austers.http;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import swle.xyz.austers.BuildConfig;

/**
*Created by TSOMH on 2020/5/15
*Description:
*
*/
public  class AppUpdateManger{


    public static void check(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://116.62.106.237:8080/update")
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = Objects.requireNonNull(response.body()).string();
                Gson gson = new Gson();
                new Version_info();
                Version_info version_info;
                version_info = gson.fromJson(json,Version_info.class);
                System.out.println(version_info.versionCode+"");
                System.out.println(BuildConfig.VERSION_CODE+"");
                if (version_info.versionCode > BuildConfig.VERSION_CODE){

                }

            }
        });
    }

    private static class Version_info{
        public String downloadUrl;
        public int versionCode;
        public String versionDes;
        public String versionName;
    }


}

package swle.xyz.austers.http;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import swle.xyz.austers.bean.Objects;

/**
*Created by TSOMH on 2020/6/11$
*Description:
*
*/
public class LostAndFoundHttpUtil {

    static final String test_url = "http://10.0.2.2:8081";

    private static final MediaType mediaType = MediaType.parse("img/jpeg;charset=utf-8");
    public static void lost(File file, Objects objects){
        OkHttpClient client = new OkHttpClient.Builder()

                .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(objects);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("objects",json)//请求参数，和服务端约定好
                .addFormDataPart("file",file.getName()+".jpeg",RequestBody.create(file,mediaType))
                .build();
        Request request = new Request.Builder()
                .url(test_url+"/objects/lost")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                System.out.println("失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });
    }
}

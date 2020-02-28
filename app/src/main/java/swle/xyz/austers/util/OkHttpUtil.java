package swle.xyz.austers.util;

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
import swle.xyz.austers.myclass.User;
import swle.xyz.austers.myinterface.LoginResultCallBack;

/**
*Created by TSOMH on 2020/2/27$
*Description:
*
*/
public class OkHttpUtil {

    private static LoginResult loginResult = new LoginResult();
    private static final MediaType JSON=MediaType.parse("application/json;charset=utf-8");


    public static void Login(String user_id, String password,final LoginResultCallBack loginResultCallBack){

        User user = new User();
        user.setStudnet_id(user_id);
        user.setPassword(password);

        Gson gson = new Gson();

        String json = gson.toJson(user);  //json字符串
//        System.out.println(json);
        RequestBody body = RequestBody.create(json,JSON);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .build();
        String url = "http://116.62.106.237:8080/austers/login";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                loginResultCallBack.failure(404);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = Objects.requireNonNull(response.body()).string();
                System.out.println(json);
                Gson gson1 = new Gson();
                loginResult =gson1.fromJson(json, LoginResult.class);
                loginResultCallBack.success(loginResult.result);
            }


        });
    }

    private static class LoginResult{
        private int result=0;
    }


}

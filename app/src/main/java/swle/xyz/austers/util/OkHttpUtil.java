package swle.xyz.austers.util;

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
import swle.xyz.austers.myclass.User;
import swle.xyz.austers.myinterface.GetVcodeResultCallBack;
import swle.xyz.austers.myinterface.LoginResultCallBack;
import swle.xyz.austers.myinterface.SignInResultCallBack;

/**
*Created by TSOMH on 2020/2/27$
*Description:
*
*/
public class OkHttpUtil {


    private static final MediaType JSON=MediaType.parse("application/json;charset=utf-8");

    private static SignInResult signInResult = new SignInResult();
    private static LoginResult loginResult = new LoginResult();
    private static GetVcodeResult getVcodeResult = new GetVcodeResult();

    public static void Login(String student_id, String password,final LoginResultCallBack loginResultCallBack){




        User user = new User(student_id,password,null,null);

        Gson gson = new Gson();

        String json = gson.toJson(user);  //json字符串
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

    public static void SignIn(String phonenumber, String password, final SignInResultCallBack signInResultCallBack){
        User user = new User(null,password,null,phonenumber);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        RequestBody requestBody = RequestBody.create(json,JSON);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3,TimeUnit.SECONDS)
                .build();
        String url = "http://116.62.106.237:8080/austers/signin";
        Request request =new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                signInResultCallBack.failure(404);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = Objects.requireNonNull(response.body()).string();
                Gson gson1 = new Gson();
                signInResult = gson1.fromJson(json,SignInResult.class);
                signInResultCallBack.success(signInResult.result);
            }
        });
    }

    public static void getAuthCode(String phonenumber,final GetVcodeResultCallBack getVcodeResultCallBack){

        User user = new User(null,null,null,phonenumber);

        Gson gson = new Gson();
        String json = gson.toJson(user);
        System.out.println(json);
        RequestBody requestBody = RequestBody.create(json,JSON);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5,TimeUnit.SECONDS)
                .build();
        String url = "http://116.62.106.237:8080/austers/getcode";
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("signin","failure");
                getVcodeResultCallBack.failure(0,0);
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = Objects.requireNonNull(response.body()).string();
                System.out.println("json:"+json);
                Gson gson1 = new Gson();
                getVcodeResult = gson1.fromJson(json,GetVcodeResult.class);
                getVcodeResultCallBack.success(getVcodeResult.status_code,getVcodeResult.vcode);

            }
        });
    }

    private static class LoginResult{
        private int result=0;
    }

    private static class SignInResult{
        private int result = 0;
    }
    private static class GetVcodeResult{
        public int getStatus_code() {
            return status_code;
        }

        public void setStatus_code(int status_code) {
            this.status_code = status_code;
        }

        private int status_code = 0;

        public int getVcode() {
            return vcode;
        }

        public void setVcode(int vcode) {
            this.vcode = vcode;
        }

        private int vcode = 0;
    }

}

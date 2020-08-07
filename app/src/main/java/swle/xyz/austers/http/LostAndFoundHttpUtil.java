package swle.xyz.austers.http;

        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;

        import org.jetbrains.annotations.NotNull;

        import java.io.File;
        import java.io.IOException;
        import java.util.List;
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
        import swle.xyz.austers.bean.ResponseBean;
        import swle.xyz.austers.callback.ResponseCallBack;
        import swle.xyz.austers.myclass.CurrentUser;

/**
 *Created by TSOMH on 2020/6/11$
 *Description:
 *
 */
public class LostAndFoundHttpUtil {

    //    static final String url = "http://10.0.2.2:8081";
    static final String url = "https://swle.top:8081";
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    private static final MediaType mediaType = MediaType.parse("img/jpeg;charset=utf-8");
    public static void lost(File file, Objects objects, final ResponseCallBack callBack){
        CurrentUser currentUser = CurrentUser.getInstance();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(objects);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("objects",json)//请求参数，和服务端约定好
                .addFormDataPart("file",objects.getCurrentHost()+
                        "_"+System.currentTimeMillis()+".jpeg",RequestBody.create(file,mediaType))
                .build();
        System.out.println(currentUser.token);
        Request request = new Request.Builder()
                .url(url+"/objects/lost")
                .addHeader("Authorization",currentUser.token)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                System.out.println("lsot失败");
                callBack.failure();

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = java.util.Objects.requireNonNull(response.body()).string();
                System.out.println("lost--"+json);
                Gson gson1 = new Gson();
                ResponseBean responseBean = gson1.fromJson(json,ResponseBean.class);
                if (responseBean.getCode() == 1){
                    callBack.success(1,"发布成功",null);
                }else if (responseBean.getCode() == -1){
                    callBack.success(-1,"发布失败，请重试",null);
                }
            }
        });
    }

    public static void query(final Objects objects, final ResponseCallBack callBack){
        CurrentUser currentUser = CurrentUser.getInstance();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(objects);
        RequestBody body = RequestBody.create(json,JSON);
        Request request = new Request.Builder()
                .post(body)
                .addHeader("Authorization",currentUser.token)
                .url(url+"/objects/query")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.failure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = java.util.Objects.requireNonNull(response.body()).string();
                System.out.println(json);
                Gson gson = new Gson();
                ResponseBean responseBean = gson.fromJson(json,ResponseBean.class);
                List<Objects> objectsList = gson.fromJson(responseBean.getData().toString().trim(),new TypeToken<List<Objects>>(){}.getType());
                callBack.success(1,"查询成功",objectsList);
            }
        });
    }
}

package swle.xyz.austers.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import swle.xyz.austers.R;
import swle.xyz.austers.myclass.SHA1;


public class ScoreActivity extends AppCompatActivity {

    private EditText editText_username;
    private EditText editText_password;
    private Button button_login;

    public String string_yan;
    public String html1,html2;
    private String password_encrypted;
    String url="http://jwgl.aust.edu.cn/eams/login.action";

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    //生产okHttp客户端
    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                    cookieStore.put(httpUrl.host(), list);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                    List<Cookie> cookies = cookieStore.get(httpUrl.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            .build();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        initView();
        initEvent();

    }

    private void initView(){
        editText_username=findViewById(R.id.editText_intranet_user);
        editText_password=findViewById(R.id.editText_intranet_password);
        button_login=findViewById(R.id.button_login);


    }

    private void initEvent() {





        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //获取"盐"
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        ResponseBody responseBody = response.body();
                        html1 = responseBody.string();
                        getYan();
//                        password_encrypted = new SHA1().encode(string_yan+editText_password.getText().toString());
                        password_encrypted = new SHA1().encode(string_yan+"122274");
//                        System.out.println(string_yan);
//                        System.out.println(password_encrypted);

                        requests2();


//                        //提交密码部分
//                        RequestBody body = new FormBody.Builder()
//                                .add("username","2018304008")
//                                .add("password",password_encrypted)
//                                .add("encodedPassword","")
//                                .add("session_locale","zh_CN")
//                                .build();
//                        Request request1 = new Request.Builder()
//                                .url(url)
//                                .post(body)
//                                .build();
//                        Call call1 = okHttpClient.newCall(request1);
//                        call1.enqueue(new Callback() {
//                            @Override
//                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                                Log.d(" "," onFailure");
//                            }
//
//                            @Override
//                            public void onResponse(@NotNull Call call, @NotNull Response response1) throws IOException {
//
//                                ResponseBody responseBody = response1.body();
//                                html2 = responseBody.string();
//                                System.out.println(html2);
//
//                            }
//                        });


                    }
                });




            }
        });
    }

    public void requests2(){


        //提交密码部分
        RequestBody body = new FormBody.Builder()
                .add("username","2018304008")
                .add("password",password_encrypted)
                .add("encodedPassword","")
                .add("session_locale","zh_CN")
                .build();
        Request request1 = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call1 = okHttpClient.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(" "," onFailure");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response1) throws IOException {

                ResponseBody responseBody = response1.body();
                html2 = responseBody.string();
//                System.out.println(html2);
                Log.d("html2",html2);

            }
        });

    }

    // 初始化Python环境
    public void initPython(){
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }

    //调用python代码
    String callPythonCode(){
        Python py = Python.getInstance();
        PyObject obj1 = py.getModule("getStringOfPlaintext").callAttr("get_http",url);
        return obj1.toJava(String.class);
    }



    public void getYan(){
        String regFormat = "\\s*|\t|\r|\n";
        String regTag = "<[^>]*>";
        String result;
        result = html1.replaceAll(regFormat,"").replaceAll(regTag,"");
        string_yan = result.substring(result.indexOf("CryptoJS.SHA1('")+15,result.indexOf("'+form['password'].value)"));

    }


}

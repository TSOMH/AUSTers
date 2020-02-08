package swle.xyz.austers.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.jetbrains.annotations.NotNull;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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


public class IdentityAuthenticateActivity extends AppCompatActivity {

    private EditText editText_student_number;
    private EditText editText_password;
    private Button button_login;
    private String student_numeber;

    public String string_yan;
    private String name;
    public String html1,html2;
    private String password_encrypted;
    private Toolbar toolbar;
    private int count = 0;
    String url_login = "http://jwgl.aust.edu.cn/eams/login.action";
    String url_home = "http://jwgl.aust.edu.cn/eams/home!submenus.action?menu.id=&_=1581064462212";
    String url_detail = "http://jwgl.aust.edu.cn/eams/stdDetail.action";

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    //生成okHttp客户端
    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(@NotNull HttpUrl httpUrl, List<Cookie> list) {
                    cookieStore.put(httpUrl.host(), list);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                    List<Cookie> cookies = cookieStore.get(httpUrl.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_authenticate);
        initView();
        initEvent();

    }

    private void initView(){
        editText_student_number=findViewById(R.id.editText_student_number);
        editText_password=findViewById(R.id.editText_intranet_password);
        button_login=findViewById(R.id.button_identity);
        toolbar = findViewById(R.id.toolbar_identity_authenticate);


        setSupportActionBar(toolbar); //将toolbar设置为当前activity的操作栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        getSupportActionBar().setTitle("身份验证");
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayShowTitleEnabled(true);//隐藏toolbar默认显示的label


    }

    private void initEvent() {
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                student_numeber = editText_student_number.getText().toString();

                //获取"盐"
                Request request = new Request.Builder()
                        .url(url_login)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.d("request1","failure");

                        Looper.prepare();
                        Toast.makeText(IdentityAuthenticateActivity.this, "连接失败，请检查是否开启vpn或使用校园网连接!", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        ResponseBody responseBody = response.body();
                        html1 = responseBody.string();
                        getYan();
//                        password_encrypted = new SHA1().encode(string_yan+editText_password.getText().toString());
                        password_encrypted = new SHA1().encode(string_yan+editText_password.getText().toString());
//                        System.out.println(string_yan);
//                        System.out.println(password_encrypted);
                        request2();
                    }
                });
            }
        });
    }

    public void request2(){
        //提交密码部分
        count += 1;
        RequestBody body = new FormBody.Builder()
                .add("username",student_numeber)
                .add("password",password_encrypted)
                .add("encodedPassword","")
                .add("session_locale","zh_CN")
                .build();
        Request request2 = new Request.Builder()
                .url(url_login)
                .post(body)
                .build();
        Call call2 = okHttpClient.newCall(request2);
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                ResponseBody responseBody = response.body();
                html2 = responseBody.string();
                String regFormat = "\\s*|\t|\r|\n";
                String regTag = "<[^>]*>";
                String result;
                result = html2.replaceAll(regFormat,"").replaceAll(regTag,"");
                if(result.contains("密码错误")){
                    Log.d("requests2"," onFailure");
                    Log.d("html2",html2);
                    Looper.prepare();
                    Toast.makeText(IdentityAuthenticateActivity.this, "密码错误!", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }else {
                    Log.d("request2","successful");
//                    System.out.println(html2);
                    request3();

                    name = getName();
                    System.out.println(name+"同学，你好!");

                    System.out.println("response2_codee:"+response.code());
                    Looper.prepare();
                    Toast.makeText(IdentityAuthenticateActivity.this, name+"同学，你好!", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });

    }

    public void request3(){

        Request request3 = new Request.Builder()
                .url(url_detail)
                .build();
        Call call3 = okHttpClient.newCall(request3);
        call3.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                ResponseBody responseBody = response.body();
                String html3 = responseBody.string();
                System.out.println(html3);
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
        PyObject obj1 = py.getModule("getStringOfPlaintext").callAttr("get_http",url_login);
        return obj1.toJava(String.class);
    }


    public void getYan(){
        String regFormat = "\\s*|\t|\r|\n";
        String regTag = "<[^>]*>";
        String result;
        result = html1.replaceAll(regFormat,"").replaceAll(regTag,"");
        string_yan = result.substring(result.indexOf("CryptoJS.SHA1('")+15,result.indexOf("'+form['password'].value)"));

    }



    public String getName(){
        String regFormat = "\\s*|\t|\r|\n";
        String regTag = "<[^>]*>";
        String result;
        result = html2.replaceAll(regFormat,"").replaceAll(regTag,"");

        return result.substring(result.indexOf("退出")+2,result.indexOf("("+student_numeber));
    }


}

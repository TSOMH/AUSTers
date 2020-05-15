package swle.xyz.austers.myclass;

import android.os.SystemClock;
import android.util.Log;

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

public class IdentityAuthenticate {


    private String result;

    private String string_yan;
    private String html1,html2;

    private String student_name;

    private String student_number;
    private String password;


    private String password_encrypted;

    String url_login = "http://jwgl.aust.edu.cn/eams/login.action";
    String url_home = "http://jwgl.aust.edu.cn/eams/home!submenus.action?menu.id=&_=1581064462212";
    String url_detail = "http://jwgl.aust.edu.cn/eams/stdDetail.action";

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    //生成okHttp客户端
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
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
            .connectTimeout(2, TimeUnit.SECONDS)//设置连接超时时间
            .build();

    public IdentityAuthenticate(String student_number,String password){

        setStudentNumber(student_number);
        setPassword(password);


        Request request = new Request.Builder()
                .url(url_login)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                setResult("连接内网失败");
                System.out.println(getPassword()+"连接内网失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
//                html1 = responseBody.string();
                html1 = response.body().string();
//                Log.d("html1:",html1);
                setYan();
                System.out.println("string_yan="+string_yan);
                password_encrypted = new SHA1().encode(string_yan+getPassword());
                SystemClock.sleep(2200);
                request2();
            }
        });

    }

    private void setPassword(String spassword) {

        password =spassword;
    }
    private String getPassword(){
        return password;
    }

    private void setStudentNumber(String number) {
        student_number = number;
    }

    private String getStudentNumber(){
        return student_number;
    }


    private void request2(){
        RequestBody body = new FormBody.Builder()
                .add("username",student_number)
                .add("password",password_encrypted)
                .add("encodedPassword","")
                .add("session_locale","zh_CN")
                .build();
        Request request2 = new Request.Builder()
                .url(url_login)
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Cache-Control","no-cache")
                .addHeader("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                .addHeader("Connection","keep-alive")
                .addHeader("Content-Length","107")
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .addHeader("Pragma","no-cache")
                .addHeader("Upgrade-Insecure-Requests","1")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0")
                .post(body)
                .build();
        Call call2 = okHttpClient.newCall(request2);
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                setResult("连接内网失败");
                System.out.println("shibai");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                html2 = responseBody.string();
                Log.d("html2:",html2);
                System.out.println("chengong");
                String regFormat = "\\s*|\t|\r|\n";
                String regTag = "<[^>]*>";
                html2 = html2.replaceAll(regFormat,"").replaceAll(regTag,"");
                if (html2.contains("密码错误")){
                    setResult("密码错误");
                    System.out.println(getPassword()+":密码错误");
                }else if (html2.contains("账户不存在")){
                    setResult("账户不存在");
                    System.out.println(getStudentNumber()+":账户不存在");
                }else if (html2.contains("我的账户")){
                    setResult("登陆成功");
                    System.out.println(getPassword()+":登陆成功");
                    setStudentName(html2.substring(html2.indexOf("退出")+2,html2.indexOf("("+getStudentNumber())));
                }
            }
        });
    }

    public String getResult(){
        return result;
    }

    private void setResult(String string){
        result = string;
    }

    private void setYan(){
        String regFormat = "\\s*|\t|\r|\n";
        String regTag = "<[^>]*>";
        String result;
        result = html1.replaceAll(regFormat,"").replaceAll(regTag,"");
        string_yan = result.substring(result.indexOf("CryptoJS.SHA1('")+15,result.indexOf("'+form['password'].value)"));

    }

    private void setStudentName(String name){
        student_name = name;
    }

    public String getStudent_name(){
        return student_name;
    }


}

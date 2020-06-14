package swle.xyz.austers.http;

import android.os.SystemClock;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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
import swle.xyz.austers.callback.BasicInfoCallBack;
import swle.xyz.austers.callback.ClassScheduleCallBack;
import swle.xyz.austers.callback.GradeCallBack;
import swle.xyz.austers.myclass.SHA1;

/**
*Created by TSOMH on 2020/5/31
*Description:
*
*/
public class JWXT {


   String url_login = "http://jwgl.aust.edu.cn/eams/login.action";
//   String url_home = "http://jwgl.aust.edu.cn/eams/home!submenus.action?menu.id=&_=1581064462212";
   String url_detail = "http://jwgl.aust.edu.cn/eams/stdDetail.action";
   String url_class_schedule = "http://jwgl.aust.edu.cn/eams/courseTableForStd!courseTable.action";
   String url_grade = "http://jwgl.aust.edu.cn/eams/teach/grade/course/person!search.action";
   String student_id;
   String password;
   String semesterId;
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
           .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
           .build();

   public JWXT(String student_id, String password){
      this.student_id = student_id;
      this.password = password;
   }





   public void getName(BasicInfoCallBack basicInfoCallBack){
      getLoginWeb(basicInfoCallBack,null,null);
   }
   public void getClassSchedule(ClassScheduleCallBack classScheduleCallBack){
      getLoginWeb(null,classScheduleCallBack,null);
   }
   public void getGrade(GradeCallBack gradeCallBack,String semesterId){
      getLoginWeb(null,null,gradeCallBack);
      this.semesterId = semesterId;
   }






   //内部方法
   void getLoginWeb(final BasicInfoCallBack basicInfoCallBack,final ClassScheduleCallBack classScheduleCallBack,
                     final GradeCallBack gradeCallBack ){

      Request request = new Request.Builder()
              .url(url_login)
              .build();
      Call call = okHttpClient.newCall(request);
      call.enqueue(new Callback() {
         @Override
         public void onFailure(@NotNull Call call, @NotNull IOException e) {
            System.out.println("连接内网失败");

            if (classScheduleCallBack!= null){
            }else if (basicInfoCallBack != null){
               basicInfoCallBack.failure(-1);
            }else {
               gradeCallBack.failure(-1);
            }
         }

         @Override
         public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            String html = Objects.requireNonNull(response.body()).string();
            String password_encrypted = SHA1.encode(getSalt(html)+password);
            SystemClock.sleep(1000);
            if (classScheduleCallBack!= null){
               Login(null,classScheduleCallBack,null,password_encrypted);
            }else if (basicInfoCallBack != null){
               Login(basicInfoCallBack,null,null,password_encrypted);
            }else {
               Login(null,null,gradeCallBack,password_encrypted);
            }

         }
      });


   }


   //内部方法
   void Login(final BasicInfoCallBack basicInfoCallBack,
              final ClassScheduleCallBack classScheduleCallBack,
              final GradeCallBack gradeCallBack,
              String password_encrypted){
      RequestBody body = new FormBody.Builder()
              .add("username",student_id)
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
            //网络错误

            if (classScheduleCallBack != null){

            }else if (basicInfoCallBack != null){
               basicInfoCallBack.failure(-1);
            }else {
               gradeCallBack.failure(-1);
            }
         }

         @Override
         public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            String html = Objects.requireNonNull(response.body()).string();
            String regFormat = "\\s*|\t|\r|\n";
            String regTag = "<[^>]*>";
            html = html.replaceAll(regFormat,"").replaceAll(regTag,"");
            if (html.contains("密码错误")){
               //密码错误
               if (classScheduleCallBack != null){

               }else if (basicInfoCallBack != null){
                  basicInfoCallBack.failure(-2);
               }
            }else if (html.contains("账户不存在")){
               if (classScheduleCallBack != null){

               }else if (basicInfoCallBack != null){
                  basicInfoCallBack.failure(-3);
               }else {
                  gradeCallBack.failure(-3);
               }
            }else if (html.contains("我的账户")){
               if (classScheduleCallBack!=null){
                  getClassScheduleWeb(classScheduleCallBack);
               }else if (basicInfoCallBack != null){
                  getBasicInfoWeb(basicInfoCallBack);
               }else {
                  getGradeWeb(gradeCallBack);
                  System.out.println("登陆成功！！！！！1");
               }

            }

         }
      });
   }

   //内部方法
   void getBasicInfoWeb(final BasicInfoCallBack callBack){
       Request request = new Request.Builder()
               .url(url_detail)
               .build();
       okHttpClient.newCall(request).enqueue(new Callback() {
          @Override
          public void onFailure(@NotNull Call call, @NotNull IOException e) {

          }

          @Override
          public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
             String html = Objects.requireNonNull(response.body()).string();
             Document document = Jsoup.parse(html);
//             Elements table = document.getElementsByClass("infoTable");
//             Document document1 = Jsoup.parse(table.toString());
             Elements trs =document.select("table").select("tr");

             String[] basic_info = new String[26];
             int x = 0;
             for (int i = 1; i < trs.size();i++ ){
                Elements tds = trs.get(i).select("td");
                for (int j = 1; j < tds.size(); j+=2){
                   Log.d("--",tds.get(j).text());
                   basic_info[x] = tds.get(j).text();
                   x++;
                }
             }
             callBack.success(basic_info);
          }
       });
   }


   void getClassScheduleWeb(ClassScheduleCallBack classScheduleCallBack){
      RequestBody body = new FormBody.Builder()
              .add("ignoreHead","1")
              .add("setting.kind","std")
              .add("startWeek","")
              .add("project.id","1")
              .add("semester.id","81")
              .add("ids","55210")
              .build();
      final Request request = new Request.Builder()
              .url(url_class_schedule)
              .post(body)
              .build();
      Call call = okHttpClient.newCall(request);
      call.enqueue(new Callback() {
         @Override
         public void onFailure(@NotNull Call call, @NotNull IOException e) {

         }

         @Override
         public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            String html = response.body().string();
            System.out.println(html+"--------------html结束");
            Document document = Jsoup.parse(html);
            Element table = document.getElementById("manualArrangeCourseTable");
            System.out.println(table.text());
            Element element = table.child(1);
            System.out.println(element.toString());
            Elements trs = element.select("tr");

            System.out.println("以上是table");
//            System.out.println(tr.toString());
            System.out.println("trs的大小="+trs.size());
            for (int i = 0; i < trs.size(); i++){
               Element tr = trs.get(i);
               Elements tds = tr.select("td");
               System.out.println("tds的大小="+tds.size());
               for (int j = 0; j < tds.size(); j++){
                  Element td = tds.get(j);
                  System.out.println(td.text());
               }
            }
//            for (Element ){
//
//            }
         }
      });
   }

   void getGradeWeb(final GradeCallBack gradeCallBack){

      HashMap<String,String> params = new HashMap<>();
      params.put("semesterId",semesterId);
      params.put("projectType","");
      HttpUrl.Builder builder = Objects.requireNonNull(HttpUrl.parse(url_grade)).newBuilder();
      builder.setQueryParameter("semesterId",params.get("semesterId"));
//              .setQueryParameter("projectType","");
      final Request request = new Request.Builder()
              .url(builder.build())
              .get()
              .build();
      Call call = okHttpClient.newCall(request);
      call.enqueue(new Callback() {
         @Override
         public void onFailure(@NotNull Call call, @NotNull IOException e) {

         }

         @Override
         public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            String html = Objects.requireNonNull(response.body()).string();
            System.out.println(html);
            System.out.println("=========");
            Document document = Jsoup.parse(html);
            Elements trs = document.select("tr");//tr用来确认行数
            System.out.println("tr的大小"+trs.size());
            Elements ths = document.select("th");//th用来确认列数
            System.out.println("ths的大小"+ths.size());
            String[][] grade = new String[trs.size()][ths.size()];
            if (trs.size() == 2){
               gradeCallBack.noResult();
            }
            for (int i = 0; i < trs.size(); i++){ // i=1是因为第一行为列表头
               if (i==0 && ths.size() > 0){//表头
                  for (int j = 0;j < ths.size();j++){
                     grade[i][j] = ths.get(j).text();
                     System.out.println("第"+j+"此添加");
                  }
               }else {//表体
                  Elements tds = trs.get(i).select("td");//每一行的具体内容
                  System.out.println("tds的大小"+tds.size());
                  System.out.println("行的大小为"+tds.size());
                  for (int j = 0; j < tds.size(); j++){
                     System.out.println(tds.get(j).text());
                     if (tds.get(j).text()!=null && tds.size() > 0){
                        grade[i][j] = tds.get(j).text();
                        System.out.println("第"+j+"此添加");
                     }
                  }
               }


            }
            gradeCallBack.success(grade);
         }

      });
   }


   String getSalt(String html){
      System.out.println();
      String regFormat = "\\s*|\t|\r|\n";
      String regTag = "<[^>]*>";
      String result;
      result = html.replaceAll(regFormat,"").replaceAll(regTag,"");
      return result.substring(result.indexOf("CryptoJS.SHA1('")+15,result.indexOf("'+form['password'].value);returntrue;"));
   }
}

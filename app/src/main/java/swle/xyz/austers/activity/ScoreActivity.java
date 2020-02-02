package swle.xyz.austers.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import swle.xyz.austers.R;


public class ScoreActivity extends AppCompatActivity {

    private EditText editText_username;
    private EditText editText_password;
    private Button button_login;
    private String psw;
    private static final MediaType CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded");
    private String sha1="var CryptoJS=CryptoJS||function(e,m){var p={},j=p.lib={},l=function(){},f=j.Base={extend:function(a){l.prototype=this;var c=new l;a&&c.mixIn(a);c.hasOwnProperty(\"init\")||(c.init=function(){c.$super.init.apply(this,arguments)});c.init.prototype=c;c.$super=this;return c},create:function(){var a=this.extend();a.init.apply(a,arguments);return a},init:function(){},mixIn:function(a){for(var c in a)a.hasOwnProperty(c)&&(this[c]=a[c]);a.hasOwnProperty(\"toString\")&&(this.toString=a.toString)},clone:function(){return this.init.prototype.extend(this)}},\n" +
            "n=j.WordArray=f.extend({init:function(a,c){a=this.words=a||[];this.sigBytes=c!=m?c:4*a.length},toString:function(a){return(a||h).stringify(this)},concat:function(a){var c=this.words,q=a.words,d=this.sigBytes;a=a.sigBytes;this.clamp();if(d%4)for(var b=0;b<a;b++)c[d+b>>>2]|=(q[b>>>2]>>>24-8*(b%4)&255)<<24-8*((d+b)%4);else if(65535<q.length)for(b=0;b<a;b+=4)c[d+b>>>2]=q[b>>>2];else c.push.apply(c,q);this.sigBytes+=a;return this},clamp:function(){var a=this.words,c=this.sigBytes;a[c>>>2]&=4294967295<<\n" +
            "32-8*(c%4);a.length=e.ceil(c/4)},clone:function(){var a=f.clone.call(this);a.words=this.words.slice(0);return a},random:function(a){for(var c=[],b=0;b<a;b+=4)c.push(4294967296*e.random()|0);return new n.init(c,a)}}),b=p.enc={},h=b.Hex={stringify:function(a){var c=a.words;a=a.sigBytes;for(var b=[],d=0;d<a;d++){var f=c[d>>>2]>>>24-8*(d%4)&255;b.push((f>>>4).toString(16));b.push((f&15).toString(16))}return b.join(\"\")},parse:function(a){for(var c=a.length,b=[],d=0;d<c;d+=2)b[d>>>3]|=parseInt(a.substr(d,\n" +
            "2),16)<<24-4*(d%8);return new n.init(b,c/2)}},g=b.Latin1={stringify:function(a){var c=a.words;a=a.sigBytes;for(var b=[],d=0;d<a;d++)b.push(String.fromCharCode(c[d>>>2]>>>24-8*(d%4)&255));return b.join(\"\")},parse:function(a){for(var c=a.length,b=[],d=0;d<c;d++)b[d>>>2]|=(a.charCodeAt(d)&255)<<24-8*(d%4);return new n.init(b,c)}},r=b.Utf8={stringify:function(a){try{return decodeURIComponent(escape(g.stringify(a)))}catch(c){throw Error(\"Malformed UTF-8 data\");}},parse:function(a){return g.parse(unescape(encodeURIComponent(a)))}},\n" +
            "k=j.BufferedBlockAlgorithm=f.extend({reset:function(){this._data=new n.init;this._nDataBytes=0},_append:function(a){\"string\"==typeof a&&(a=r.parse(a));this._data.concat(a);this._nDataBytes+=a.sigBytes},_process:function(a){var c=this._data,b=c.words,d=c.sigBytes,f=this.blockSize,h=d/(4*f),h=a?e.ceil(h):e.max((h|0)-this._minBufferSize,0);a=h*f;d=e.min(4*a,d);if(a){for(var g=0;g<a;g+=f)this._doProcessBlock(b,g);g=b.splice(0,a);c.sigBytes-=d}return new n.init(g,d)},clone:function(){var a=f.clone.call(this);\n" +
            "a._data=this._data.clone();return a},_minBufferSize:0});j.Hasher=k.extend({cfg:f.extend(),init:function(a){this.cfg=this.cfg.extend(a);this.reset()},reset:function(){k.reset.call(this);this._doReset()},update:function(a){this._append(a);this._process();return this},finalize:function(a){a&&this._append(a);return this._doFinalize()},blockSize:16,_createHelper:function(a){return function(c,b){return(new a.init(b)).finalize(c)}},_createHmacHelper:function(a){return function(b,f){return(new s.HMAC.init(a,\n" +
            "f)).finalize(b)}}});var s=p.algo={};return p}(Math);\n" +
            "(function(){var e=CryptoJS,m=e.lib,p=m.WordArray,j=m.Hasher,l=[],m=e.algo.SHA1=j.extend({_doReset:function(){this._hash=new p.init([1732584193,4023233417,2562383102,271733878,3285377520])},_doProcessBlock:function(f,n){for(var b=this._hash.words,h=b[0],g=b[1],e=b[2],k=b[3],j=b[4],a=0;80>a;a++){if(16>a)l[a]=f[n+a]|0;else{var c=l[a-3]^l[a-8]^l[a-14]^l[a-16];l[a]=c<<1|c>>>31}c=(h<<5|h>>>27)+j+l[a];c=20>a?c+((g&e|~g&k)+1518500249):40>a?c+((g^e^k)+1859775393):60>a?c+((g&e|g&k|e&k)-1894007588):c+((g^e^\n" +
            "k)-899497514);j=k;k=e;e=g<<30|g>>>2;g=h;h=c}b[0]=b[0]+h|0;b[1]=b[1]+g|0;b[2]=b[2]+e|0;b[3]=b[3]+k|0;b[4]=b[4]+j|0},_doFinalize:function(){var f=this._data,e=f.words,b=8*this._nDataBytes,h=8*f.sigBytes;e[h>>>5]|=128<<24-h%32;e[(h+64>>>9<<4)+14]=Math.floor(b/4294967296);e[(h+64>>>9<<4)+15]=b;f.sigBytes=4*e.length;this._process();return this._hash},clone:function(){var e=j.clone.call(this);e._hash=this._hash.clone();return e}});e.SHA1=j._createHelper(m);e.HmacSHA1=j._createHmacHelper(m)})();\n" +
            "\n";

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
                InputStream is=null;
                InputStreamReader isr = null;

                try {
                    is = this.getClass().getResourceAsStream("sha1.js");
                    if(is==null){
                        Log.d("  ","is is null");
                    }else {
                        Log.d(" ","is !=null");
                    }
                    if (is != null) {
                        isr =new InputStreamReader(is,"UTF-8");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
                ScriptEngine scriptEngine = scriptEngineManager
                        .getEngineByName("JavaScript");
                try {
//                    if(isr==null){
//                        Log.d(" ","isr==null");
//                    }else{
//                        Log.d(" ","isr!=null");
//                    }


//                    scriptEngine.eval(new InputStreamReader(this.getClass()
//                            .getResourceAsStream("sha1.js")));
                    scriptEngine.eval(isr);

                    Log.d("input", "failue");

                } catch (ScriptException e) {
                    e.printStackTrace();
                }
                try {
                    String st="bcffc828-5615-4344-ab23-f39c483d8bbf-";
                    Object t = scriptEngine
                            .eval("CryptoJS.SHA1('st+editText_password.getText().toString()').toString();");
                    psw=t.toString();
                    Log.d("miwen",psw);
                } catch (ScriptException e) {
                    e.printStackTrace();
                }

                OkHttpClient client = new OkHttpClient.Builder()
                        .cookieJar(new CookieJar() {
                            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                            @Override
                            public void saveFromResponse(HttpUrl httpUrl, List<Cookie> cookies) {
                                //添加cookie
                                cookieStore.put(httpUrl.host(), cookies);
                                Log.d("cookie", "save successful");
//                                //保存请求回来的cookie登陆和查成绩要用到
//                                cookies.sCookieList = cookies;
//                                //保存cookie的字符串
//                                Constant.sCookie = Constant.sCookieList.toString();
                            }

                            @Override
                            public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                                List<Cookie> cookies = cookieStore.get(httpUrl.host());
                                return cookies != null ? cookies : new ArrayList<Cookie>();
                            }
                        })
                        .build();
                RequestBody requestBody = new FormBody.Builder()
                        .add("username", editText_username.getText().toString())
                        .add("password", psw)
                        .add("encodedPassword", "")
                        .add("session_locale", "zh_CN")
                        .build();
                Request request = new Request.Builder()
                        .url("http://jwgl.aust.edu.cn/eams/login.action")
                        .post(requestBody)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("Callback", "Faliure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("Response", response.body().string());
                    }

                });
                Log.d("StatusCode", call.toString());


//
//                Request request1 = new Request.Builder()
//                        .url("http://jwgl.aust.edu.cn/eams/home.action")
//                        .addHeader("")
//                        .build();
//                Call call1 = client.newCall(request1);
//                call1.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                    }
//                });


            }
        });


    }



}

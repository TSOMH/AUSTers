package swle.xyz.austers.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import swle.xyz.austers.R;
import swle.xyz.austers.bean.CoronaVirus;

public class VirusActivity extends BaseActivity {

    private Toolbar toolbar;
    TextView textView_currentConfirmedCount;
    TextView textView_currentConfirmedIncr;
    TextView textView_confirmedCount;
    TextView textView_confirmedIncr;
    TextView textView_suspectedCount;
    TextView textView_suspectedIncr;
    TextView textView_curedCount;
    TextView textView_curedIncr;
    TextView textView_deadCount;
    TextView textView_deadIncr;
    TextView textView_seriousCount;
    TextView textView_seriousIncr;
    TextView textView_currentConfirmedCount_global;
    TextView textView_confirmedCount_global;
    TextView textView_curedCount_global;
    TextView textView_deadCount_global;
    TextView textView_currentConfirmedIncr_global;
    TextView textView_confirmedIncr_global;
    TextView textView_curedIncr_global;
    TextView textView_deadIncr_global;
    TextView textView_updateTime;
    TextView textView_updateTime2;
    SwipeRefreshLayout swipeRefreshLayout;

    Handler handler;

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virus);
        handler = new Handler();
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar_virus_activity);
        setSupportActionBar(toolbar); //将toolbar设置为当前activity的操作栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayShowTitleEnabled(false);//隐藏toolbar默认显示的label
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        textView_currentConfirmedCount = findViewById(R.id.textView59);
        textView_currentConfirmedIncr = findViewById(R.id.textView63);
        textView_confirmedCount = findViewById(R.id.textView71);
        textView_confirmedIncr = findViewById(R.id.textView75);
        textView_suspectedCount = findViewById(R.id.textView72);
        textView_suspectedIncr = findViewById(R.id.textView76);
        textView_curedCount = findViewById(R.id.textView73);
        textView_curedIncr = findViewById(R.id.textView77);
        textView_deadCount = findViewById(R.id.textView74);
        textView_deadIncr = findViewById(R.id.textView78);
        textView_seriousCount = findViewById(R.id.textView60);
        textView_seriousIncr = findViewById(R.id.textView64);
        textView_currentConfirmedCount_global = findViewById(R.id.textView82);
        textView_confirmedCount_global = findViewById(R.id.textView85);
        textView_curedCount_global = findViewById(R.id.textView88);
        textView_deadCount_global = findViewById(R.id.textView91);
        textView_currentConfirmedIncr_global = findViewById(R.id.textView83);
        textView_confirmedIncr_global = findViewById(R.id.textView86);
        textView_curedIncr_global = findViewById(R.id.textView89);
        textView_deadIncr_global = findViewById(R.id.textView92);
        textView_updateTime = findViewById(R.id.textView54);
        textView_updateTime2 = findViewById(R.id.textView80);
    }

    @Override
    public void initEvent() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.aust));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    void initData(){
        Request request = new Request.Builder()
                .url("https://lab.isaaclin.cn/nCoV/api/overall?latest=true")
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String json = Objects.requireNonNull(response.body()).string();


                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        CoronaVirus coronaVirus = gson.fromJson(json,CoronaVirus.class);
                        List<CoronaVirus.Results> results= coronaVirus.getResults();
                        System.out.println(json);
                        for (int i = 0; i < results.size();i++){
                            textView_currentConfirmedCount.setText(results.get(i).getCurrentConfirmedCount()+"");
                            textView_currentConfirmedIncr.setText("较昨日"+results.get(i).getCurrentConfirmedIncr());
                            textView_confirmedCount.setText(results.get(i).getConfirmedCount()+"");
                            textView_confirmedIncr.setText("较昨日"+results.get(i).getConfirmedIncr());
                            textView_suspectedCount.setText(results.get(i).getSuspectedCount()+"");
                            textView_suspectedIncr.setText("较昨日"+results.get(i).getSuspectedIncr());
                            textView_curedCount.setText(results.get(i).getCuredCount()+"");
                            textView_curedIncr.setText("较昨日"+results.get(i).getCuredIncr());
                            textView_deadCount.setText(results.get(i).getDeadCount()+"");
                            textView_deadIncr.setText("较昨日"+results.get(i).getDeadIncr());
                            textView_seriousCount.setText(results.get(i).getSeriousCount()+"");
                            textView_seriousIncr.setText("较昨日"+results.get(i).getSeriousIncr());
                            CoronaVirus.Results.GlobalStatistics globalStatistics = results.get(i).getGlobalStatistics();

                            textView_currentConfirmedCount_global.setText(globalStatistics.getCurrentConfirmedCount()+"");
                            textView_confirmedCount_global.setText(globalStatistics.getConfirmedCount()+"");
                            textView_curedCount_global.setText(globalStatistics.getCuredCount()+"");
                            textView_deadCount_global.setText(globalStatistics.getDeadCount()+"");
                            textView_currentConfirmedIncr_global.setText("较昨日"+globalStatistics.getCurrentConfirmedIncr());
                            textView_confirmedIncr_global.setText("较昨日"+globalStatistics.getConfirmedIncr());
                            textView_curedIncr_global.setText("较昨日"+globalStatistics.getCuredIncr());
                            textView_deadIncr_global.setText("较昨日"+globalStatistics.getDeadIncr());
                            textView_updateTime.setText("数据更新时间:"+TimeStamp2Date(results.get(i).getUpdateTime()+"","yyyy-MM-dd HH:mm:ss"));
                            textView_updateTime2.setText("数据更新时间:"+TimeStamp2Date(results.get(i).getUpdateTime()+"","yyyy-MM-dd HH:mm:ss"));
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
    public static String TimeStamp2Date(String timestampString, String formats) {
        if (TextUtils.isEmpty(formats))
            formats = "yyyy-MM-dd HH:mm:ss";
        Long timestamp = Long.parseLong(timestampString);
        return new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
    }
}

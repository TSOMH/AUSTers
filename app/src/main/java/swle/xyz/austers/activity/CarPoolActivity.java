package swle.xyz.austers.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import swle.xyz.austers.R;
import swle.xyz.austers.adapter.CarPoolGridViewAdapter;
import swle.xyz.austers.bean.Trip;
import swle.xyz.austers.dialogfragment.DatePickerFragment;
import swle.xyz.austers.dialogfragment.IssueTripDialogfragment;
import swle.xyz.austers.dialogfragment.TimePickerFragment;
import swle.xyz.austers.callback.QueryOtherTripResultCallBack;
import swle.xyz.austers.util.OkHttpUtil;

public class CarPoolActivity extends BaseActivity  {
    Calendar calendar = Calendar.getInstance();
    private Button button_query;
    private Button button_issue;

    private TextView textView_date;
    private TextView textView_hour;

    private Toolbar toolbar;

    private Spinner spinner_starting;
    private Spinner spinner_destnitation;

    private ImageButton imageButton_reverse;

    private GridView gridView;

    private Handler handler;

    private AlertDialog.Builder builder = null;


    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH)+1;
    private int day = calendar.get(Calendar.DAY_OF_MONTH);
    private int hour = calendar.get(Calendar.HOUR_OF_DAY);

    private List<Trip> trips0=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_pool);

        handler = new Handler();
        initView();
        initEvent();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar_car_pool_activity);
        setSupportActionBar(toolbar); //将toolbar设置为当前activity的操作栏
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayShowTitleEnabled(false);//隐藏toolbar默认显示的label



        imageButton_reverse = findViewById(R.id.imageButton_reverse);
        spinner_starting = findViewById(R.id.spinner_starting);
        spinner_destnitation = findViewById(R.id.spinner_destination);
        button_query= findViewById(R.id.button_query_car_pool_activity);
        button_issue = findViewById(R.id.button_issue);
        textView_date = findViewById(R.id.textView_date);
        textView_hour = findViewById(R.id.textView_hour);
        String date = calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日";
        textView_date.setText(date);
        textView_hour.setText(calendar.get(Calendar.HOUR_OF_DAY)+":00");
        gridView = findViewById(R.id.gridView_car_pool_activity);
    }

    @Override
    public void initEvent() {

        final SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
        final SharedPreferences.Editor editor = loginInfo.edit();//获取Editor
        imageButton_reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int start = spinner_starting.getSelectedItemPosition();
                int destnitation = spinner_destnitation.getSelectedItemPosition();
                System.out.println(start+"  "+destnitation);
                spinner_starting.setSelection(destnitation,true);
                spinner_destnitation.setSelection(start,true);
            }
        });


        textView_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setStyle(DialogFragment.STYLE_NO_TITLE,R.style.CustomDatePickerDialogTheme);
                datePickerFragment.show(getSupportFragmentManager(),"dataPicker");
            }
        });

        textView_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(),"timePicker");
            }
        });
        button_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String starting = spinner_starting.getSelectedItem().toString();
                String destination = spinner_destnitation.getSelectedItem().toString();
                String initiator = loginInfo.getString("current_user",null);

                IssueTripDialogfragment issueTripDialogfragment = new IssueTripDialogfragment(starting,destination,initiator,year,month,day,hour,CarPoolActivity.this);
                issueTripDialogfragment.show(getSupportFragmentManager(),"tag");





            }
        });
        button_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String starting = spinner_starting.getSelectedItem().toString();
                String destination = spinner_destnitation.getSelectedItem().toString();
                String initiator = loginInfo.getString("current_user",null);
                OkHttpUtil.queryOtherTrip(initiator, starting, destination, 0, year, month, day, hour, new QueryOtherTripResultCallBack() {
                    @Override
                    public void success(final List<Trip> trips) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                trips0 = trips;
                                if (trips0.size() > 0){
                                    gridView.setAdapter(new CarPoolGridViewAdapter(CarPoolActivity.this,trips0,loginInfo,editor));
                                }else {
                                    builder = new AlertDialog.Builder(CarPoolActivity.this);
                                    builder.setMessage("搜索结果为空，请重试");
                                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    final AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }
                        });
                    }

                    @Override
                    public void failure(int status_code) {

                    }
                });
            }
        });
    }
    public void setDate(int year, int month, int day){
        textView_date.setText(year+"年"+month+"月"+day+"日");

        this.year = year;
        this.month = month;
        this.day = day;

    }
    public void setTime(int hour){
        textView_hour .setText(hour+":00");
        this.hour = hour;
    }

}

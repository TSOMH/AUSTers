package swle.xyz.austers.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;

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

public class CarPoolActivity extends BaseActivity  {
    Calendar calendar = Calendar.getInstance();
    private Button button_query;
    private Button button_date;
    private Toolbar toolbar;
    private Spinner spinner_starting;
    private Spinner spinner_destnitation;
    private ImageButton imageButton_reverse;
    private GridView gridView;
    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH)+1;
    private int day = calendar.get(Calendar.DAY_OF_MONTH);
    private List<Trip> trips=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_pool);
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
        button_date = findViewById(R.id.button_date);
        String date = calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日";
        button_date.setText(date);
        gridView = findViewById(R.id.gridView_car_pool_activity);
    }

    @Override
    public void initEvent() {
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

        button_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setStyle(DialogFragment.STYLE_NO_TITLE,R.style.CustomDatePickerDialogTheme);
                datePickerFragment.show(getSupportFragmentManager(),"dataPicker");
            }
        });
        button_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String starting = spinner_starting.getSelectedItem().toString();
                String destination = spinner_destnitation.getSelectedItem().toString();
                trips.add(new Trip("小明","南门","淮南东站",3,2020,6,1));
                trips.add(new Trip("小红","西门","淮南东站",3,2020,6,1));
                trips.add(new Trip("小蓝","北门","淮南东站",3,2020,6,1));
                gridView.setAdapter(new CarPoolGridViewAdapter(CarPoolActivity.this,trips));

            }
        });
    }
    public void setDate(int year, int month, int day){
        button_date.setText(year+"年"+month+"月"+day+"日");
        this.year = year;
        this.month = month;
        this.day = day;
    }

}

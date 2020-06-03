package swle.xyz.austers.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import swle.xyz.austers.R;
import swle.xyz.austers.adapter.GradeAdapter;
import swle.xyz.austers.callback.GradeCallBack;
import swle.xyz.austers.myclass.CurrentUser;
import swle.xyz.austers.myclass.Grade;
import swle.xyz.austers.room.User;
import swle.xyz.austers.room.UserDao;
import swle.xyz.austers.room.UserDataBase;
import swle.xyz.austers.room.UserRoom;
import swle.xyz.austers.util.JWXT;

public class GradeActivity extends BaseActivity {

    Toolbar toolbar;
    GridView gridView;
    Spinner spinner;
    Button button;
    UserDataBase userDataBase;
    UserDao userDao;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        handler = new Handler();
        userDataBase = UserRoom.getInstance(getApplicationContext());
        userDao = userDataBase.getUserDao();
        initView();
        initEvent();
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar_grade_activity);
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


        gridView = findViewById(R.id.gridView_grade);
        spinner = findViewById(R.id.spinner_grade);
        button = findViewById(R.id.button_search_grade);
    }

    @Override
    public void initEvent() {


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (spinner.getSelectedItemPosition()){
                    case 0:
                        getGrade("9");
                        break;
                    case 1:
                        getGrade("28");
                        break;
                    case 2:
                        getGrade("10");
                        break;
                    case 3:
                        getGrade("29");
                        break;
                    case 4:
                        getGrade("60");
                        break;
                    case 5:
                        getGrade("61");
                        break;
                    case 6:
                        getGrade("80");
                        break;
                    case 7:
                        getGrade("81");
                        break;
                }

            }
        });
    }

    void getGrade(final String semesterId){
        final CurrentUser currentUser = CurrentUser.getInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = userDao.queryUser(currentUser.phonenumber);
                JWXT jwxt = new JWXT(user.getStudent_id(),user.getPassword_jwxt());
                jwxt.getGrade(new GradeCallBack() {
                    @Override
                    public void failure(int status_code) {

                    }
                    @Override
                    public void success(String[][] score) {

                        final List<Grade> grades = new ArrayList<>();
                        for (int i = 0; i< score.length;i++){
                            String[] row = score[i];
                            Grade grade = new Grade();
                            for (int j = 3; j < row.length; j+=3){
                                if (j == 3){
                                    grade.setCourse(row[j]);
                                }else {
                                    grade.setScore(row[j]);
                                }
                            }
                            grades.add(grade);
                        }
                        if (grades.size() > 0){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    gridView.setAdapter(new GradeAdapter(grades,getApplicationContext()));
                                }
                            });

                        }else {
                            System.out.println("结果为空");
                        }
                    }
                },semesterId);
            }
        }).start();
    }
}

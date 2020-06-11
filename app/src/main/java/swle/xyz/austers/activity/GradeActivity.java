package swle.xyz.austers.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
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
import swle.xyz.austers.http.JWXT;

public class GradeActivity extends BaseActivity {

    Toolbar toolbar;
    GridView gridView;
    Spinner spinner;
    Button button;
    UserDataBase userDataBase;
    UserDao userDao;
    Handler handler;
    AlertDialog.Builder builder = null;
    AlertDialog dialog = null;

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
                builder = new AlertDialog.Builder(GradeActivity.this);
                builder.setTitle("提示");
                builder.setMessage("正在查询，请稍后");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog = builder.create();
                dialog.show();
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
                        dialog.setMessage("查询失败，请重试！");
                    }
                    @Override
                    public void success(String[][] score) {

                        System.out.println("score的长度为"+score.length);
                        final List<Grade> grades = new ArrayList<>();
                        for (int i = 1; i< score.length;i++){
                            String[] row = score[i];
                            Grade grade = new Grade();

                            for (int j = 3; j < row.length; j++){
                                if (j == 3){
                                    grade.setCourse(row[j]);
                                }else if (j == row.length-2){
                                    grade.setScore(row[j]);
                                }
                            }
                            grades.add(grade);
                        }
                        if (grades.size() > 1){
                            System.out.println("科目数量="+grades.size());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    gridView.setAdapter(new GradeAdapter(grades,getApplicationContext()));
                                    dialog.dismiss();
                                }
                            });
                        }
                    }

                    @Override
                    public void noResult() {
                        dialog.setMessage("查询结果为空");
                    }
                },semesterId);
            }
        }).start();
    }
}

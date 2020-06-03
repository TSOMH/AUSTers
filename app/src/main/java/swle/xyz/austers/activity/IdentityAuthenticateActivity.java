package swle.xyz.austers.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import swle.xyz.austers.R;
import swle.xyz.austers.callback.BasicInfoCallBack;
import swle.xyz.austers.room.User;
import swle.xyz.austers.room.UserDao;
import swle.xyz.austers.room.UserDataBase;
import swle.xyz.austers.room.UserRoom;
import swle.xyz.austers.util.JWXT;


public class IdentityAuthenticateActivity extends AppCompatActivity {

    private EditText editText_student_number;
    private EditText editText_password;
    private Button button_login;

    private AlertDialog.Builder builder = null;

    UserDataBase userDataBase;
    UserDao userDao;

    SharedPreferences loginInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_authenticate);
        initView();
        initEvent();
        loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
        userDataBase = UserRoom.getInstance(getApplicationContext());
        userDao = userDataBase.getUserDao();

    }

    private void initView(){
        editText_student_number=findViewById(R.id.editText_student_number);
        editText_password=findViewById(R.id.editText_intranet_password);
        button_login=findViewById(R.id.button_identity);
        Toolbar toolbar = findViewById(R.id.toolbar_identity_authenticate);

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


    }

    private void initEvent(){
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(IdentityAuthenticateActivity.this);
                builder.setMessage("验证中");
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
                new Thread(){
                    @Override
                    public void run(){
                        if(!editText_password.getText().toString().equals("") &&
                                !editText_student_number.getText().toString().equals("")){
                            JWXT jwxt = new JWXT(editText_student_number.getText().toString(),
                                    editText_password.getText().toString());
                            jwxt.getName(new BasicInfoCallBack() {
                                @Override
                                public void failure(int status_code) {
                                    switch (status_code){
                                        case -1:
                                            dialog.setMessage("请检查是否开启VPN或使用校园网连接");
                                            break;
                                        case -2:
                                            dialog.setMessage("密码错误，请重试");
                                            break;
                                        case -3:
                                            dialog.setMessage("账户不存在");
                                            break;
                                    }
                                }

                                @Override
                                public void getBasicInfo(String[] info) {
                                    String phonenumber = loginInfo.getString("current_user","");
                                    User user = new User();
                                    user.setStudent_id(editText_student_number.getText().toString());
                                    user.setPhonenumber(phonenumber);
                                    user.setStudent_id(info[0]);
                                    user.setOrganization(info[9]);
                                    user.setTrue_name(info[1]);
                                    user.setMajor(info[10]);
                                    user.setIs_student(true);
                                    user.setPassword_jwxt(editText_password.getText().toString());
                                    updateUser(user);
                                    dialog.setMessage(info[1]+"同学,你好!");

                                }
                            });
                        }else {
                            dialog.setMessage("请检查用户名和密码是否为空");
                        }
                    }
                }.start();
            }
        });
    }

    void updateUser(final User user){
        new Thread(new Runnable() {
            @Override
            public void run() {
                userDao.InsertUser(user);
            }
        }).start();
    }
}

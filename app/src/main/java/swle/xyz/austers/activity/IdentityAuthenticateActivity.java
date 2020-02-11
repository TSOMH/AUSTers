package swle.xyz.austers.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import swle.xyz.austers.R;
import swle.xyz.austers.myclass.IdentityAuthenticate;


public class IdentityAuthenticateActivity extends AppCompatActivity {

    private EditText editText_student_number;
    private EditText editText_password;
    private Button button_login;

    private AlertDialog.Builder builder = null;

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
        Toolbar toolbar = findViewById(R.id.toolbar_identity_authenticate);

        setSupportActionBar(toolbar); //将toolbar设置为当前activity的操作栏
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
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
                        if(!editText_password.getText().toString().equals("") && !editText_student_number.getText().toString().equals("")){
                            IdentityAuthenticate ia = new IdentityAuthenticate(editText_student_number.getText().toString(),editText_password.getText().toString());
                            SystemClock.sleep(1200);
                            String result = ia.getResult();
                            String name = ia.getStudent_name();
                            if (result == null){
                                dialog.setMessage("连接错误");
                            }else {
                                switch (result) {
                                    case "连接内网失败":
                                        dialog.setMessage("请检查是否开启VPN或使用校园网连接");
                                        break;
                                    case "密码错误":
                                        dialog.setMessage("密码错误，请重试");
                                        break;
                                    case "账户不存在":
                                        dialog.setMessage("账户不存在");
                                        break;
                                    case "登陆成功":
                                        dialog.setMessage(name+"同学,你好!");
                                        break;
                                }
                            }
                        }else {
                            dialog.setMessage("请检查用户名和密码是否为空");
                        }
                    }
                }.start();
            }
        });
    }
}

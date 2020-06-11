package swle.xyz.austers.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

import swle.xyz.austers.R;
import swle.xyz.austers.callback.ResponseCallBack;
import swle.xyz.austers.http.UserHttpUtil;
import swle.xyz.austers.myclass.CurrentUser;
import swle.xyz.austers.myclass.OnMultiClickListener;
import swle.xyz.austers.room.User;
import swle.xyz.austers.room.UserDao;
import swle.xyz.austers.room.UserDataBase;
import swle.xyz.austers.room.UserRoom;


public class LogInActivity extends BaseActivity {
    private Button button_log_in;
    private MaterialEditText user_id;
    private MaterialEditText user_password;
    private Button button_sign_in;
    private Button button_forgot_password;


    SharedPreferences loginInfo = null;
    SharedPreferences.Editor editor = null;

    CurrentUser currentUser;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in);
        currentUser = CurrentUser.getInstance();
        initView();
        initEvent();
        initLoginInfo();
    }

    @Override
    public void initView() {
        button_log_in = findViewById(R.id.button_log_in);
        button_sign_in = findViewById(R.id.button_sign_in);
        user_id = findViewById(R.id.user_id);


        user_password =findViewById(R.id.user_password);

        button_forgot_password = findViewById(R.id.button_forgot_password);
    }

    private void initLoginInfo(){
        loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
        editor = loginInfo.edit();//获取Editor
        if (loginInfo.getString("current_user",null) == null ||
                loginInfo.getString("current_password",null) == null){
            editor.putString("current_user","");
            editor.putString("current_password","");
            editor.apply();
        }
        user_id.setText(loginInfo.getString("current_user",null));
        user_password.setText(loginInfo.getString("current_password",null));
    }

    @Override
    public void initEvent() {

        Intent intent = getIntent();
        if (!Objects.equals(intent.getStringExtra("phonenumber"), "")){
            user_id.setText(intent.getStringExtra("phonenumber"));
            user_password.setText(intent.getStringExtra("password"));
        }




        button_log_in.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {//禁止频繁点击


                final String phonenumber = Objects.requireNonNull(user_id.getText()).toString();
                final String password = Objects.requireNonNull(user_password.getText()).toString();

                if (!phonenumber.equals("") && !password.equals("")){
                    UserHttpUtil.Login(phonenumber, password, new ResponseCallBack() {
                        @Override
                        public void failure() {
                            Looper.prepare();
                            Toast.makeText(LogInActivity.this,"网络错误，请重试",Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }

                        @Override
                        public void success(int code, String message, Object data) {
                            switch (code){
                                case 1:
                                    editor.putString("current_user",phonenumber);
                                    editor.putString("current_password",password);
                                    editor.apply();
                                    InsertOneUser(phonenumber,password, (String) data);

                                    currentUser.phonenumber = phonenumber;
                                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); //禁止回跳SignInActivity
                                    intent.setClass(LogInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    break;
                                case -1:
                                    Looper.prepare();
                                    Toast.makeText(LogInActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                    break;
                                case -2:
                                    Looper.prepare();
                                    Toast.makeText(LogInActivity.this,"密码错误,请重新登录",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                    break;
                            }
                        }
                    });
                }else {
                    Looper.prepare();
                    Toast.makeText(LogInActivity.this,"请检查用户名和密码是否为空",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
//                if (!phonenumber.equals("") && !password.equals("")){
//                    OkHttpUtil.Login(phonenumber, password, new LoginResultCallBack() { //生成LoginResultCallBack接口，目的是拿到OkHttpUtil
//                                                                               //的Response返回内容
//
//                        @Override
//                        public void success(int result) {
//                            Log.d("code",""+result);
//                            switch (result){
//                                case 1:
//                                    editor.putString("current_user",phonenumber);
//                                    editor.putString("current_password",password);
//                                    editor.apply();
//                                    InsertOneUser(phonenumber,password);
//                                    currentUser.phonenumber = phonenumber;
//                                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); //禁止回跳SignInActivity
//                                    intent.setClass(LogInActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    break;
//                                case -1:
//                                    Looper.prepare();
//                                    Toast.makeText(LogInActivity.this,"密码错误,请重新登录",Toast.LENGTH_SHORT).show();
//                                    Looper.loop();
//                                    break;
//                                case -2:
//                                    Looper.prepare();
//                                    Toast.makeText(LogInActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
//                                    Looper.loop();
//                                    break;
//                                case -3:
//                                    Looper.prepare();
//                                    Toast.makeText(LogInActivity.this,"服务器异常,请重试",Toast.LENGTH_SHORT).show();
//                                    Looper.loop();
//                                    break;
//                            }
//                        }
//
//                        @Override
//                        public void failure(int result) {
//                            Log.d("code",""+ result);
//                        }
//                    });
//
//                }else {
//                    Toast.makeText(LogInActivity.this,"请检查用户名和密码是否为空",Toast.LENGTH_LONG).show();
//                }

            }
        });

        button_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SignInDialogFragment fragment = new SignInDialogFragment();
//                fragment.show(getSupportFragmentManager(),null);

                Intent intent = new Intent(LogInActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        button_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    void InsertOneUser(String phonenumber, String password, final String token){

        UserDataBase userDataBase = UserRoom.getInstance(getApplicationContext());
        final UserDao userDao = userDataBase.getUserDao();

        User user = userDao.queryUser(phonenumber);
        if (user == null){
            System.out.println("yonghukong");
            user = new User();
            user.setPhonenumber(phonenumber);
            user.setPassword(password);
            user.setToken(token);
            final User finalUser = user;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    userDao.InsertUser(finalUser);
                }
            }).start();
        }else {
            System.out.println("yonghubukong");
            final User finalUser1 = user;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    finalUser1.setToken(token);
                    userDao.updateUser(finalUser1);
                }
            }).start();

        }
    }

}

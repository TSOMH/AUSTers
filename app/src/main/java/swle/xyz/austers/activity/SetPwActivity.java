package swle.xyz.austers.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import swle.xyz.austers.R;
import swle.xyz.austers.callback.SignInResultCallBack;
import swle.xyz.austers.room.User;
import swle.xyz.austers.room.UserDao;
import swle.xyz.austers.room.UserDataBase;
import swle.xyz.austers.room.UserRoom;
import swle.xyz.austers.httputil.OkHttpUtil;

public class SetPwActivity extends BaseActivity {

    private MaterialEditText editText_pw;
    private MaterialEditText editText_pw_again;
    private Button button;

    UserDataBase userDataBase;
    UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pw);
        userDataBase = UserRoom.getInstance(getApplicationContext());
        userDao = userDataBase.getUserDao();
        initView();
        initEvent();
    }

    @Override
    public void initView() {
        editText_pw = findViewById(R.id.editText_password_in_set_pw_activity);
        editText_pw_again = findViewById(R.id.editText_password_again_in_set_pw_activity);
        button = findViewById(R.id.button_next_step_in_set_pw_activity);
    }

    @Override
    public void initEvent() {
        editText_pw_again.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText_pw_again.getText().toString().equals(editText_pw.getText().toString())
                        && editText_pw.getText().toString().length() >= 6){
                    button.setBackground(getResources().getDrawable(R.drawable.button_gray_blue_selector));
                    button.setEnabled(true);
                }else {
                    button.setEnabled(false);
                    button.setBackgroundColor(Color.parseColor("#DCDCDC"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = getIntent();
                final String phonenumber = intent.getStringExtra("phonenumber");
                final String password = editText_pw_again.getText().toString();
                OkHttpUtil.SignIn(phonenumber, password, new SignInResultCallBack() {
                    @Override
                    public void success(int result) {
                        switch (result){
                            case 1:
                                Looper.prepare();
                                Toast.makeText(SetPwActivity.this,"注册成功!",Toast.LENGTH_LONG).show();


                                User user = new User();
                                user.setPhonenumber(phonenumber);
                                user.setPassword(password);
                                System.out.println("密码为"+password);
                                insertOne(user);
                                Intent intent1 = new Intent(SetPwActivity.this,LogInActivity.class);
                                intent1.putExtra("phonenumber",phonenumber);
                                intent1.putExtra("password",password);
                                startActivity(intent1);
                                Looper.loop();
                                break;
                            case -3:
                                Looper.prepare();
                                Toast.makeText(SetPwActivity.this,"注册失败!请重试",Toast.LENGTH_LONG).show();
                                Looper.loop();
                                break;
                        }
                    }

                    @Override
                    public void failure(int result) {

                    }
                });
            }
        });
    }


    private void insertOne(final User user){
        new Thread(new Runnable() {
            @Override
            public void run() {
                userDao.InsertUser(user);
            }
        }).start();
    }
}

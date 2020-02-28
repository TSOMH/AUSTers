package swle.xyz.austers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

import swle.xyz.austers.R;
import swle.xyz.austers.myclass.OnMultiClickListener;
import swle.xyz.austers.myinterface.LoginResultCallBack;
import swle.xyz.austers.util.OkHttpUtil;


public class SignInActivity extends BaseActivity {
    private Button button_sign_in;
    private MaterialEditText user_id;
    private MaterialEditText user_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signin);
        initView();
        initEvent();
    }

    @Override
    public void initView() {
        button_sign_in = findViewById(R.id.button_sign_in);
        user_id = findViewById(R.id.user_id);
        user_password =findViewById(R.id.user_password);
    }

    @Override
    public void initEvent() {
        button_sign_in.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {//禁止频繁点击


                String id = Objects.requireNonNull(user_id.getText()).toString();
                String password = Objects.requireNonNull(user_password.getText()).toString();
                if (!id.equals("") && !password.equals("")){
                    OkHttpUtil.Login(id, password, new LoginResultCallBack() { //生成LoginResultCallBack接口，目的是拿到OkHttpUtil
                                                                               //的Response返回内容
                        @Override
                        public void success(int result) {
                            Log.d("code",""+result);
                            switch (result){
                                case 1:
                                    Intent intent = new Intent(SignInActivity.this, Main2Activity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); //禁止回跳SignInActivity
                                    intent.setClass(SignInActivity.this,Main2Activity.class);
                                    startActivity(intent);
                                    break;
                                case -1:
                                    Looper.prepare();
                                    Toast.makeText(SignInActivity.this,"密码错误,请重新登录",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                    break;
                                case -2:
                                    Looper.prepare();
                                    Toast.makeText(SignInActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                    break;
                                case -3:
                                    Looper.prepare();
                                    Toast.makeText(SignInActivity.this,"服务器异常",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                    break;
                            }
                        }

                        @Override
                        public void failure(int result) {
                            Log.d("code",""+ result);
                        }
                    });

                }else {
                    Toast.makeText(SignInActivity.this,"请检查用户名和密码是否为空",Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}

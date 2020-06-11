package swle.xyz.austers.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import swle.xyz.austers.R;
import swle.xyz.austers.callback.ResponseCallBack;
import swle.xyz.austers.http.UserHttpUtil;
import swle.xyz.austers.myclass.OnMultiClickListener;

public class SignInActivity extends BaseActivity {

    Map<String , String> result_map= new HashMap<>();

    private int t = 60;
    private int vcode = 0;
    private Handler handler = new Handler();

    private MaterialEditText editText_phonenumber;
    private MaterialEditText editText_code;
    private Button button_getCode;
    private Button button_next_step;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        initView();
        initEvent();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void initView() {
        editText_phonenumber = findViewById(R.id.editText_phonenumber_in_sign_in_activity);
        editText_code = findViewById(R.id.code_in_sign_in_activity);
        button_getCode = findViewById(R.id.button_get_code_in_sign_in_activity);
        button_next_step = findViewById(R.id.button_next_step_in_sign_in_activity);
    }

    @Override
    public void initEvent() {
        final String[] vcode = {""};
        editText_phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Objects.requireNonNull(editText_phonenumber.getText()).toString().length() == 11 ){
                    button_getCode.setEnabled(true);
//                    button_getCode.setBackground(getResources().getDrawable(R.drawable.button_gray_blue_selector));
                }else {
                    button_getCode.setEnabled(false);
//                    button_getCode.setBackgroundColor(Color.parseColor("#DCDCDC"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText_code.getText().toString().length() == 6){
                    button_next_step.setEnabled(true);
//                    button_next_step.setBackgroundColor(Color.parseColor("#2861A4"));
                    button_next_step.setBackground(getResources().getDrawable(R.drawable.button_gray_blue_selector));
                }else {
                    button_next_step.setEnabled(false);
                    button_next_step.setBackgroundColor(Color.parseColor("#DCDCDC"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        button_getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//                OkHttpUtil.getAuthCode(editText_phonenumber.getText().toString(), new GetVcodeResultCallBack() {
//                    @Override
//                    public void success(int status_code, int vcode) {
//                        System.out.println("status_code"+status_code);
//                        System.out.println("vcode"+vcode);
//                        if (status_code == -1){
//                            code[0] = vcode;
//                            new Thread(new CountDownTimer()).start();
//                        }else if (status_code == 1){
//                            Looper.prepare();
//                            Toast.makeText(SignInActivity.this,"此账号已被注册！",Toast.LENGTH_LONG).show();
//                            Looper.loop();
//                        }
//                    }
//
//                    @Override
//                    public void failure(int status_code, int vcode) {
//                        Toast.makeText(SignInActivity.this,"获取验证码失败，请重试",Toast.LENGTH_LONG).show();
//                    }
//                });

                UserHttpUtil.getAuthCode(Objects.requireNonNull(editText_phonenumber.getText()).toString(), new ResponseCallBack() {
                    @Override
                    public void failure() {
                        Looper.prepare();
                        Toast.makeText(SignInActivity.this,"获取验证码失败，请重试",Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }

                    @Override
                    public void success(int code, String message, Object data) {
                        switch (code){
                            case 1:
                                vcode[0] = data.toString();
                                new Thread(new CountDownTimer()).start();
                                break;
                            case -1:
                                Looper.prepare();
                                Toast.makeText(SignInActivity.this,"获取验证码失败，请重试", Toast.LENGTH_LONG).show();
                                Looper.loop();
                                break;
                            case -2:
                                Looper.prepare();
                                Toast.makeText(SignInActivity.this,"此账号已被注册", Toast.LENGTH_LONG).show();
                                Looper.loop();
                                break;
                        }
                    }
                });



            }
        });


        button_next_step.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                //                System.out.println(result[0]);
                String vcode1 = vcode[0]+"";
                System.out.println("vcode"+vcode1);
                System.out.println(editText_code.getText().toString());
                if (vcode1.equals(Objects.requireNonNull(editText_code.getText()).toString())){

                    Intent intent = new Intent(SignInActivity.this,SetPwActivity.class);
                    intent.putExtra("phonenumber",editText_phonenumber.getText().toString()+"");
                    startActivity(intent);
                }else {
                    Toast.makeText(SignInActivity.this,"验证码错误，请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    class CountDownTimer implements Runnable{

        @Override
        public void run() {
            while (t > 0){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        button_getCode.setEnabled(false);
                        button_getCode.setText(t+"秒后重新获取");
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                t--;
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    button_getCode.setEnabled(true);
                    button_getCode.setText("获取验证码");
                }
            });
            t = 60;
        }
    }



}



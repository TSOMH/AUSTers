package swle.xyz.austers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import swle.xyz.austers.R;
import swle.xyz.austers.myclass.OnMultiClickListener;


public class SignInActivity extends BaseActivity {
    Button button_sign_in;



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
    }

    @Override
    public void initEvent() {
        button_sign_in.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                Intent intent = new Intent(SignInActivity.this, Main2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); //禁止回跳SignInActivity
                intent.setClass(SignInActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });
    }
}

package swle.xyz.austers.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.sl.utakephoto.exception.TakeException;
import com.sl.utakephoto.manager.ITakePhotoResult;
import com.sl.utakephoto.manager.UTakePhoto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import swle.xyz.austers.R;
import swle.xyz.austers.callback.ResponseCallBack;
import swle.xyz.austers.http.LostAndFoundHttpUtil;
import swle.xyz.austers.myclass.CurrentUser;

public class IssueObjectsActivity extends BaseActivity {

    Toolbar toolbar;
    EditText editText_kind;
    EditText editText_info;
    EditText editText_date;
    ImageButton imageButton;
    Button button_confirm;
    AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_objects);
        initView();
        initEvent();
    }

    @Override
    public void initView() {

        editText_kind = findViewById(R.id.editTextText_lost_kind);
        editText_info = findViewById(R.id.editTextText_lost_info);
        editText_date = findViewById(R.id.editTextDate);
        imageButton = findViewById(R.id.imageButtonAddLostImg);
        button_confirm = findViewById(R.id.button_issue_lost);
        toolbar = findViewById(R.id.toolbar_issue_objects);
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

    @Override
    public void initEvent() {
        final Intent intent = getIntent();
        final File[] imgFile = new File[1];
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                UTakePhoto.with(IssueObjectsActivity.this).openAlbum(intent).build(new ITakePhotoResult() {
                    @Override
                    public void takeSuccess(List<Uri> uriList) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(IssueObjectsActivity.this.getContentResolver(), uriList.get(0));
                            Glide.with(IssueObjectsActivity.this)
                                    .load(bitmap)
                                    .placeholder(R.drawable.userpicture)
                                    .into(imageButton);
                            try {
                                final File file = convertBitmapToFile(bitmap);
                                imgFile[0] = file;
                            }catch (Exception ignored){
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void takeFailure(TakeException ex) {

                    }

                    @Override
                    public void takeCancel() {

                    }
                });
            }
        });
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentUser currentUser = CurrentUser.getInstance();

                final swle.xyz.austers.bean.Objects objects = new swle.xyz.austers.bean.Objects();
                objects.setKind(editText_kind.getText().toString());
                objects.setInfo(editText_info.getText().toString());
                objects.setCurrentHost(currentUser.phonenumber);
                objects.setLostOrFoundDate(editText_date.getText().toString());
                System.out.println(editText_date.getText().toString());
                String s = intent.getStringExtra("lostOrFound");
                assert s != null;
                if (s.equals("lost")){
                    objects.setTag(1);
                }else {
                    objects.setTag(0);
                }
                builder = new AlertDialog.Builder(IssueObjectsActivity.this);
                builder.setMessage("正在提交，请稍后");
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();


                //上传图片
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LostAndFoundHttpUtil.lost(imgFile[0], objects, new ResponseCallBack() {
                            @Override
                            public void failure() {
                                Looper.prepare();
                                Toast.makeText(IssueObjectsActivity.this,"网络错误，请重试",Toast.LENGTH_LONG).show();
                                Looper.loop();
                            }

                            @Override
                            public void success(int code, String message, Object data) {
                                switch (code){
                                    case 1:
                                        Looper.prepare();
                                        dialog.setMessage("提交成功");
                                        Looper.loop();
                                        break;
                                    case -1:
                                        Looper.prepare();
                                        dialog.setMessage("提交失败，请重试");
                                        Looper.loop();
                                        break;
                                    default:
                                        Looper.prepare();
                                        dialog.setMessage("网络错误，请重试");
                                        Looper.loop();
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }

    // convert bitmap to file
    public File convertBitmapToFile(Bitmap bitmap) {
        File f = null;
        try {
            // create a file to write bitmap data
            f = new File(IssueObjectsActivity.this.getCacheDir(), "portrait");
            f.createNewFile();

            // convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            // write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {

        }
        return f;
    }
}
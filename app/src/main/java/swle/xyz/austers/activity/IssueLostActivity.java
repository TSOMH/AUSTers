package swle.xyz.austers.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.sl.utakephoto.exception.TakeException;
import com.sl.utakephoto.manager.ITakePhotoResult;
import com.sl.utakephoto.manager.UTakePhoto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import swle.xyz.austers.R;
import swle.xyz.austers.http.LostAndFoundHttpUtil;

public class IssueLostActivity extends BaseActivity {

    Toolbar toolbar;
    TextView textView_kind;
    TextView textView_info;
    ImageButton imageButton;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_lost);
        initView();
        initEvent();
    }

    @Override
    public void initView() {
        imageButton = findViewById(R.id.imageButtonAddLostImg);
        button = findViewById(R.id.button_issue_lost);
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar); //将toolbar设置为当前activity的操作栏
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//
//            }
//        });
//        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
//        getSupportActionBar().setDisplayShowTitleEnabled(false);//隐藏toolbar默认显示的label
    }

    @Override
    public void initEvent() {
        final File[] imgFile = new File[1];
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                UTakePhoto.with(IssueLostActivity.this).openAlbum(intent).build(new ITakePhotoResult() {
                    @Override
                    public void takeSuccess(List<Uri> uriList) {



                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(IssueLostActivity.this.getContentResolver(), uriList.get(0));
                            Glide.with(IssueLostActivity.this)
                                    .load(bitmap)
                                    .placeholder(R.drawable.userpicture)
                                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final swle.xyz.austers.bean.Objects objects = new swle.xyz.austers.bean.Objects();
                objects.setKind("123");
                objects.setInfo("123");
                //上传图片
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LostAndFoundHttpUtil.lost(imgFile[0],objects);
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
            f = new File(IssueLostActivity.this.getCacheDir(), "portrait");
            f.createNewFile();

            // convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
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
package swle.xyz.austers.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.sl.utakephoto.exception.TakeException;
import com.sl.utakephoto.manager.ITakePhotoResult;
import com.sl.utakephoto.manager.UTakePhoto;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import swle.xyz.austers.R;
import swle.xyz.austers.activity.MyReleseActivity;
import swle.xyz.austers.activity.MyTripActivity;
import swle.xyz.austers.activity.SettingsActivity;
import swle.xyz.austers.myclass.CurrentUser;
import swle.xyz.austers.room.UserDao;
import swle.xyz.austers.room.UserDataBase;
import swle.xyz.austers.room.UserRoom;
import swle.xyz.austers.viewmodel.MineViewModel;

public class MineFragment extends Fragment {

    private Button button_my_trip;
    private Button button_my_release;
    private Button button_my_dynamic;
    private Button button_setting;
    private TextView textView_name;
    private TextView textView_organization;
    ImageView imageView;

    UserDataBase userDataBase;
    UserDao userDao;
    CurrentUser currentUser;
    MyHandler handler;

    public static MineFragment newInstance() {
        return new MineFragment();
    }
    static final String test_url = "http://10.0.2.2:8081";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, container, false);
        currentUser = CurrentUser.getInstance();

        userDataBase = UserRoom.getInstance(getContext());
        userDao = userDataBase.getUserDao();

        initView(view);
        handler = new MyHandler(textView_name,textView_organization);
        initEvent();

        setInfo();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MineViewModel mViewModel = ViewModelProviders.of(this).get(MineViewModel.class);
        // TODO: Use the ViewModel
    }




    private void initEvent(){



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                UTakePhoto.with(requireActivity()).openAlbum(intent).build(new ITakePhotoResult() {
                    @Override
                    public void takeSuccess(List<Uri> uriList) {



                        Bitmap bitmap = null;
                        try {
//                            RequestOptions options = new RequestOptions()
//                                    .placeholder(R.drawable.userpicture)//图片加载出来前，显示的图片
//                                    .fallback( R.drawable.userpicture) //url为空的时候,显示的图片
//                                    .error(R.drawable.userpicture);//图片加载失败后，显示的图片

                            bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uriList.get(0));
                            Glide.with(requireActivity())
                                    .load(bitmap)
                                    .placeholder(R.drawable.userpicture)
                                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                    .into(imageView);
                            try {
                                File PICTURES = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                                File imageFileDirctory = new File(PICTURES + "/accountimg");
                                if (imageFileDirctory.exists()) {
                                    File imageFile = new File(PICTURES + "/accountimg"+ "/" +currentUser.phonenumber+".jpeg");
                                    FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 30, fileOutputStream);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } else if (imageFileDirctory.mkdir()) {//如果该文件夹不存在，则新建
                                    //new一个文件
                                    File imageFile = new File(PICTURES + "/accountimg" + "/" + currentUser.phonenumber+".jpeg");
                                    //通过流将图片写入
                                    FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 30, fileOutputStream);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                }
                                //上传图片
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        File PICTURES = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                                        File imageFile = new File(PICTURES + "/accountimg"+ "/" +currentUser.phonenumber+".jpeg");
                                        uploadfile(imageFile);
                                    }
                                }).start();
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

        button_my_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyTripActivity.class);
                startActivity(intent);
            }
        });

        button_my_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyReleseActivity.class);
                startActivity(intent);
            }
        });

//        button_my_dynamic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MyDynamicActivity.class);
//                startActivity(intent);
//            }
//        });
        button_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initView(View view){
        button_my_trip = view.findViewById(R.id.button_my_trip);
        button_my_release = view.findViewById(R.id.button_my_release);
//        button_my_dynamic = view.findViewById(R.id.button_my_dynamic);
        button_setting = view.findViewById(R.id.button_setting);
        textView_name = view.findViewById(R.id.textView_true_name);
        textView_organization = view.findViewById(R.id.textView_organization);
        imageView = view.findViewById(R.id.imageView_user_image);

        File PICTURES = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File img = new File(PICTURES + "/accountimg"+"/"+currentUser.phonenumber+".jpeg");
        if (img.exists()){
            Glide.with(requireActivity())
                    .load(img)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(imageView);
        }else {
            Glide.with(requireActivity())
                    .load(R.drawable.userpicture)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(imageView);
        }




    }
    void setInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //真实姓名
                Message message = Message.obtain();
                message.what = 1;
                message.obj = userDao.queryTrueName(currentUser.phonenumber);


                //学院
                Message message1 = Message.obtain();
                message1.what = 2;
                message1.obj = userDao.queryOrganization(currentUser.phonenumber);
                handler.sendMessage(message);
                handler.sendMessage(message1);
            }
        }).start();

    }

    static class MyHandler extends Handler {


        TextView textView_name;
        TextView textView_organization;


        MyHandler(TextView textView_name,TextView textView_organization){
            this.textView_name = textView_name;
            this.textView_organization = textView_organization;
        }

        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            // 根据不同线程发送过来的消息，执行不同的UI操作
            // 根据 Message对象的what属性 标识不同的消息
            switch (msg.what) {
                case 1:
                    if (msg.obj != null){
                        textView_name.setText(msg.obj.toString());
                    }

                    break;
                case 2:
                    if (msg.obj != null){
                        textView_organization.setText(msg.obj.toString());
                    }

                    break;
            }
        }
    }


    void uploadfile(File file){
        MediaType mediaType = MediaType.parse("img/jpeg; charset=utf-8");
        OkHttpClient client = new OkHttpClient.Builder()

                .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("filename","123.jpg")//请求参数，和服务端约定好
                .addFormDataPart("file",file.getName(),RequestBody.create(file,mediaType))
                .build();
        Request request = new Request.Builder()
                .url(test_url+"/upload_file/img")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                System.out.println("失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });
    }
}


package com.example.physical_examination_app.common;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.physical_examination_app.R;
import com.example.physical_examination_app.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyAvatarActivity extends AppCompatActivity {

    private ImageView iconAlbum;
    private ImageView userAvatar;
    private ImageView iconBack;

    private PopupWindow pop;

    private TextView avatarCamera;
    private TextView avatarAlbum;
    private TextView popDismiss;

    private Activity activity;

    private CustomListener customListener = new CustomListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_avatar);

        activity = this;

        getViews();
        getAvatar();
        setListener();
        albumClickListener();

    }

    //从后台获取头像
    private void getAvatar() {

        String avatarName = null;
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        if(pre.getString("login_role","").equals("student")){
            avatarName = pre.getString("sno","") + ".jpg";
        }else avatarName = pre.getString("ano","") + ".jpg";

        RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).fallback(R.mipmap.app_icon)
                .error(R.drawable.error).diskCacheStrategy(DiskCacheStrategy.NONE).circleCrop();
        Glide.with(activity).load(Utils.AVATAR_PATH + avatarName)
                .thumbnail(Glide.with(activity).load(R.drawable.loading))
                .apply(requestOptions).into(userAvatar);

    }

    //获取视图控件
    private void getViews() {
        iconAlbum = findViewById(R.id.icon_album);
        userAvatar = findViewById(R.id.user_avatar);
        iconBack = findViewById(R.id.icon_back);
    }

    //设置监听器
    private void setListener() {
        iconBack.setOnClickListener(customListener);
    }

    //相册的点击事件
    private void albumClickListener() {
        iconAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置弹出窗口，并赋值给view
                View popView = getLayoutInflater().inflate(R.layout.album_popup,null);
                //设置弹出窗口的位置
                pop = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, 450,true);
                //设置弹出窗口的动画
                pop.setAnimationStyle(R.style.popwin_anim_style);
                pop.showAsDropDown(getWindow().getDecorView());

                //获取弹出窗口的视图控件并且设置监听器
                getPopupWindowViewsAndSetListener(popView);
                //设置透明阴影
                final WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.4f;
                getWindow().setAttributes(lp);

                //设置弹出窗口的消失事件
                pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
            }
        });
    }

    //获取弹出窗口的视图控件并且设置监听器
    private void getPopupWindowViewsAndSetListener(View v){

        //获取视图控件元素
        avatarCamera = v.findViewById(R.id.avatar_camera);
        avatarAlbum = v.findViewById(R.id.avatar_album);
        popDismiss = v.findViewById(R.id.pop_dismiss);

        //同时设置点击和触摸监听器
        avatarCamera.setOnClickListener(customListener);
        avatarCamera.setOnTouchListener(customListener);
        avatarAlbum.setOnClickListener(customListener);
        avatarAlbum.setOnTouchListener(customListener);
        popDismiss.setOnClickListener(customListener);
        popDismiss.setOnTouchListener(customListener);

    }

    //自定义监听事件
    class CustomListener implements View.OnClickListener,View.OnTouchListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.icon_back:
                    finish();
                    break;
                case R.id.avatar_camera:
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,2);
                    pop.dismiss();
                    break;
                case R.id.avatar_album:
                    Intent albumIntent = new Intent(Intent.ACTION_PICK,null);
                    albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                    startActivityForResult(albumIntent,3);
                    pop.dismiss();
                    break;
                case R.id.pop_dismiss:
                    pop.dismiss();
                    break;
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()){
                case R.id.avatar_camera:
                    touchUpAndDownEvent(avatarCamera,event);
                    break;
                case R.id.avatar_album:
                    touchUpAndDownEvent(avatarAlbum,event);
                    break;
                case R.id.pop_dismiss:
                    touchUpAndDownEvent(popDismiss,event);
                    break;
            }
            //需要返回false才能同时触发点击和触摸两个监听器
            return false;
        }

    }

    //activity跳转的返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String username = null;
        SharedPreferences pre = getSharedPreferences("userInfo",MODE_PRIVATE);
        if(pre.getString("login_role","").equals("student"))
            username = pre.getString("sno","");
        else username = pre.getString("ano","");

        if(requestCode == 2){
            if(data !=null){
                if(data.getExtras() == null){ }
                else {
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");

                    avatarStore(username, bitmap);
                    RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true)
                            .fallback(R.mipmap.app_icon).error(R.drawable.error)
                            .diskCacheStrategy(DiskCacheStrategy.NONE).circleCrop();
                    Glide.with(activity).load(bitmap).thumbnail(Glide.with(activity).load(R.drawable.loading))
                            .apply(requestOptions).into(userAvatar);
                    sendPicture(username);
                }
            }
        }else if(requestCode == 3){
           if(data != null){

               Uri uri = data.getData();
               Bitmap bitmap = null;
               try {
                   bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                   avatarStore(username, bitmap);
                   RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true)
                           .fallback(R.mipmap.app_icon).error(R.drawable.error)
                           .diskCacheStrategy(DiskCacheStrategy.NONE).circleCrop();
                   Glide.with(activity).load(bitmap).thumbnail(Glide.with(activity).load(R.drawable.loading))
                           .apply(requestOptions).into(userAvatar);
               } catch (IOException e) {
                   e.printStackTrace();
               }

               sendPicture(username);

           }
        }

    }

    //头像的存储
    private void avatarStore(String username, Bitmap avatarBitmap){

        String path = getFilesDir().getAbsolutePath() + "/avatar/" + username +".jpg";
        File file = new File(path);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }

        //通过输出流向图片中写入内容
        Bitmap bitmap = avatarBitmap;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);

            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(out.toByteArray());
            out.flush();
            out.close();
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //头像的发送
    private void sendPicture(String username){
        String path = getFilesDir().getAbsolutePath()+"/avatar/" + username + ".jpg";
        String url = "http://"+ Utils.ip + ":8080/" + Utils.project + "/studentController/getPicture";

        File file = new File(path);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file",file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"),file)).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    //设置触摸的按下和抬起时背景的颜色
    private void touchUpAndDownEvent(View view,MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                view.setBackgroundColor(Color.parseColor("#C0C0C0"));
                break;
            case MotionEvent.ACTION_UP:
                view.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
    }

}
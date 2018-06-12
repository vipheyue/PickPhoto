package com.vipheyue.pickphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.vipheyue.pickphotolib.PhotoCallBackManager;
import com.vipheyue.pickphotolib.PickPhotoActivity;
import com.vipheyue.pickphotolib.PickPhotoCallBack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv_path = findViewById(R.id.tv_path);
        final ImageView imageView_show = findViewById(R.id.imageView_show);


        PhotoCallBackManager.callBack = new PickPhotoCallBack() {
            @Override
            public void onSuccess(Intent data, String filePath) {

                Bitmap bmp = BitmapFactory.decodeFile(filePath);//原图
                imageView_show.setImageBitmap(bmp);
                tv_path.setText(filePath);
            }

            @Override
            public void onfail() {

            }
        };

        Intent intent = new Intent(this, PickPhotoActivity.class);

//        intent.putExtra(PickPhotoActivity.REQUEST_IMAGE_TYPE, PickPhotoActivity.REQUEST_IMAGE_PICK);//选图
        intent.putExtra(PickPhotoActivity.REQUEST_IMAGE_TYPE, PickPhotoActivity.REQUEST_IMAGE_CAPTURE);//拍照
//        intent.putExtra(PickPhotoActivity.LIB_CONTROL_PREMISSION, false);//自己控制请求权限
        startActivity(intent);
    }
}

package com.vipheyue.pickphotolib;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class PickPhotoActivity extends AppCompatActivity {
    public static String REQUEST_IMAGE_TYPE = "REQUEST_IMAGE_TYPE";
    public static String LIB_CONTROL_PREMISSION = "libControlPremission";
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_IMAGE_PICK = 2;
    private String avatar_pic_hand_path = "";
    private Intent resultData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        boolean libControlPremission = getIntent().getBooleanExtra(LIB_CONTROL_PREMISSION, true);
        if (libControlPremission) {
            if (requestMiss()) {
                dealRequest();
            } else {
                finish();
            }
        } else {
            dealRequest();
        }
    }

    private boolean requestMiss() {
        //权限处理
        Boolean havePremission = true;
        String[] requestPermissonArray = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        ArrayList<String> noPermissonArray = new ArrayList<>();
        for (String premission : requestPermissonArray) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, premission) != PackageManager.PERMISSION_GRANTED) {
                noPermissonArray.add(premission);
                havePremission = false;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && noPermissonArray.size() > 0) {
            requestPermissions((String[]) noPermissonArray.toArray(new String[noPermissonArray.size()]), 0);
        }
        return havePremission;
    }

    private void dealRequest() {
        int intExtra = getIntent().getIntExtra(REQUEST_IMAGE_TYPE, 1);
        switch (intExtra) {
            case REQUEST_IMAGE_CAPTURE:
                openCamera();

                break;
            case REQUEST_IMAGE_PICK:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
                break;

            default:
                openCamera();
        }
    }


    private void openCamera() {
        Uri imageUri;//相机拍照图片保存地址

        // 创建File对象，用于存储拍照后的图片
        File outputImage = new File(getExternalCacheDir(), UUID.randomUUID().toString() + ".jpg");

        avatar_pic_hand_path = outputImage.getAbsolutePath();
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
            //参数二:fileprovider绝对路径 com.dyb.testcamerademo：项目包名
            imageUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", outputImage);
//            imageUri = FileProvider.getUriForFile(this, "com.vipheyue.pickphoto" + ".fileprovider", outputImage);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        resultData = data;
        if (resultCode != Activity.RESULT_OK) {
            PhotoCallBackManager.callBack.onfail();
            finish();
        }
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            avatar_pic_hand_path = getRealPathFromURI(data.getData());

            String filePath = getCacheDir() + UUID.randomUUID().toString() + ".jpg";//注意内部数据可能其他APP访问不到
            new MyAsyncTask().execute(filePath, avatar_pic_hand_path);//旋转角度

//            PhotoCallBackManager.callBack.onSuccess(data, avatar_pic_hand_path);
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            String filePath = getCacheDir() + UUID.randomUUID().toString() + ".jpg";//注意内部数据可能其他APP访问不到
            new MyAsyncTask().execute(filePath, avatar_pic_hand_path);
//            PhotoCallBackManager.callBack.onSuccess(data, avatar_pic_hand_path);
//            finish();
        }

    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(contentURI, null, null, null, null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public class MyAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            return ImageUtils.rotateBitmap(args[0], args[1]);
        }

        protected void onPostExecute(String result) {
            PhotoCallBackManager.callBack.onSuccess(resultData, result);
            finish();
        }
    }
}
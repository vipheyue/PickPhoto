package com.vipheyue.pickphoto

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.vipheyue.pickphotolib.PhotoCallBackManager
import com.vipheyue.pickphotolib.PickPhotoActivity
import com.vipheyue.pickphotolib.PickPhotoCallBack
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        PhotoCallBackManager.callBack = object : PickPhotoCallBack {
            override fun onSuccess(data: Intent?, filePath: String) {
                tv_path.setText(filePath)
            }

            override fun onfail() {

            }
        }
        var intent = Intent(this, PickPhotoActivity::class.java)
        intent.putExtra(PickPhotoActivity.REQUEST_IMAGE_TYPE, PickPhotoActivity.REQUEST_IMAGE_PICK)//选图
//        intent.putExtra(PickPhotoActivity.REQUEST_IMAGE_TYPE, PickPhotoActivity.REQUEST_IMAGE_CAPTURE)//拍照
        startActivity(intent)
    }
}

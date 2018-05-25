# 原生请求相机 选图 lib
适配5.0动态权限 +7.0 文件路径问题


使用

```

        PhotoCallBackManager.callBack = object : PickPhotoCallBack {
            override fun onSuccess(data: Intent?, filePath: String) {

            }

            override fun onfail() {

            }
        }
        var intent = Intent(this, PickPhotoActivity::class.java)
        intent.putExtra(PickPhotoActivity.REQUEST_IMAGE_TYPE, PickPhotoActivity.REQUEST_IMAGE_PICK)//选图
//        intent.putExtra(PickPhotoActivity.REQUEST_IMAGE_TYPE, PickPhotoActivity.REQUEST_IMAGE_CAPTURE)//拍照
        startActivity(intent)
```
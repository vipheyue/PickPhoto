# 原生请求相机 选图 lib
适配5.0动态权限 +7.0 文件路径问题



# 配置

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}




dependencies {
	        implementation 'com.github.vipheyue:PickPhoto:V1.3'
	}



在清单文件中添加  项目包名
	    <provider
                android:name="android.support.v4.content.FileProvider"
                android:authorities="项目包名.fileprovider"
                android:exported="false"
                android:grantUriPermissions="true">
                <meta-data
                    android:name="android.support.FILE_PROVIDER_PATHS"
                    android:resource="@xml/file_paths" />
            </provider>

使用

```


        PhotoCallBackManager.callBack = new PickPhotoCallBack() {
            @Override
            public void onSuccess(Intent data, String filePath) {
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
```
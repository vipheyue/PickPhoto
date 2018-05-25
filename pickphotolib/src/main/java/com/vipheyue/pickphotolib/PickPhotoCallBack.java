package com.vipheyue.pickphotolib;

import android.content.Intent;

public interface PickPhotoCallBack {
    void onSuccess(Intent data, String filePath);

    void onfail();

}

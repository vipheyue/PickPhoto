package com.vipheyue.pickphotolib;

public class PhotoCallBackManager {
    public static PickPhotoCallBack callBack = null;

    public static PickPhotoCallBack getCallBack() {
        return callBack;
    }

    public static void setCallBack(PickPhotoCallBack callBack) {
        PhotoCallBackManager.callBack = callBack;
    }
}

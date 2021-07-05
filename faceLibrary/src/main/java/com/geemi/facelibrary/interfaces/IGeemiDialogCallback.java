package com.geemi.facelibrary.interfaces;

public  interface IGeemiDialogCallback {
    void onProgress(int tag,String msg);
    void onFinish(int tag, String msg);
}

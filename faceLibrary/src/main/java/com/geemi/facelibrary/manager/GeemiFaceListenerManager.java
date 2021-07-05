package com.geemi.facelibrary.manager;

import android.content.Context;

import com.geemi.facelibrary.interfaces.IGeemiFaceCallback;

public class GeemiFaceListenerManager {
    private IGeemiFaceCallback iGeemiFaceCallback;

    public GeemiFaceListenerManager() {
    }
    private static final GeemiFaceListenerManager manager = new GeemiFaceListenerManager();

    public static GeemiFaceListenerManager getInstance() {
        return manager;
    }

    public void setiGeemiFaceCallback(IGeemiFaceCallback iGeemiFaceCallback) {
        this.iGeemiFaceCallback = iGeemiFaceCallback;
    }
    //人脸回调成功
    public void geemiFaceConmpletData(String str, int faceTag) {
        if (iGeemiFaceCallback != null) {
            iGeemiFaceCallback.onGeemiFaceConmpletion(str,faceTag);
        }
    }

    //人脸回调失败
    public void geemiFaceErrorData(String string,int tag){
        if (iGeemiFaceCallback != null){
            iGeemiFaceCallback.onError(tag,string);
        }
    }

}

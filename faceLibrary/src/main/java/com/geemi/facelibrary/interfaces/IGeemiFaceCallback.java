package com.geemi.facelibrary.interfaces;

import com.baidu.idl.face.platform.FaceStatusNewEnum;
import com.baidu.idl.face.platform.model.ImageInfo;
import com.geemi.facelibrary.http.IGeemiCallback;

import java.util.HashMap;

public interface IGeemiFaceCallback extends IGeemiCallback {
    void onGeemiFaceConmpletion(String bmpBase64, int faceTag);
}

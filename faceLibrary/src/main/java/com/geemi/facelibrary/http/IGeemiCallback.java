package com.geemi.facelibrary.http;

import com.geemi.facelibrary.interfaces.IGeemiDialogCallback;

public interface IGeemiCallback extends IGeemiDialogCallback {
    void onError(int tag, String error);
    void onComplete(int tag, String json);
}

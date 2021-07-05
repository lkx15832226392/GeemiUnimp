package com.geemi.facelibrary.http;

import java.util.Map;

import okhttp3.RequestBody;

public interface IGeemiHttpRequest {
    void getData(int tag, String method, String url, Map<String, String> params, IGeemiCallback onListenter, boolean isProgress);
    void getData(int tag, String method, String url, Map<String, String> params, IGeemiCallback onListenter);
    void getData(int tag, String method, String url, RequestBody requestBody, IGeemiCallback onListenter);
}

package com.geemi.facelibrary.http;

import android.annotation.SuppressLint;
import android.util.Log;

import com.geemi.facelibrary.interfaces.ApiService;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeemiHttpImpl implements IGeemiHttpRequest {
    public static String Method_GET = "get";
    public static String Method_POST = "post";
//    private static AES mAes;

    @Override
    public void getData(int tag, String method, String url, Map<String, String> params, IGeemiCallback onListenter, boolean isProgress) {
        KLog.i("params==="+params.toString());
        if (Method_GET.equals(method)) {
            methodGetData(tag, url, params, onListenter,isProgress);
        } else if (Method_POST.equals(method)) {
            methodPostData(tag, url, params, onListenter,isProgress);
        }
    }

    @Override
    public void getData(final int tag, String method, String url, Map<String, String> params, final IGeemiCallback onListenter) {
//        params.put("version", MyApplication.versionName);
        KLog.i("params==="+params.toString());
        if (Method_GET.equals(method)) {
            methodGetData(tag, url, params, onListenter,true);
        } else if (Method_POST.equals(method)) {
            methodPostData(tag, url, params, onListenter,true);
        }
    }

    @Override
    public void getData(int tag, String method, String url, RequestBody requestBody, IGeemiCallback onListenter) {

        if (Method_GET.equals(method)) {
//            methodGetData(tag, url, requestBody, onListenter,true);
        } else if (Method_POST.equals(method)) {
            methodPostDatas(tag, url, requestBody, onListenter,true);
        }
    }

    @SuppressLint("CheckResult")
    private void methodGetData(final int tag, String url, Map<String, String> params, final IGeemiCallback onListenter, final boolean isProgress) {
        KLog.i("url====="+url);
        RetrofitClient.getInstance().create(ApiService.class)
                .executeGet(url, params)
//                    .compose(RxUtils.bindToLifecycle(lifecycle)) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(disposable -> {
                    if (isProgress){
                        onListenter.onProgress(tag, "");
                    }
                })
                .subscribe((Consumer<ResponseBody>) response -> {
                    String data = response.string();
//                        KLog.i("onComplete=======" + tag + "=======" +data);
//                        MyUtil.deleteUser(data);
                    onListenter.onComplete(tag, data);
                }, (Consumer<ResponseThrowable>) throwable -> {
                    KLog.i("onError=======" + throwable.message+"===="+throwable.code);
                    //关闭对话框
                    onListenter.onError(tag, throwable.message);
                    onListenter.onFinish(tag, "");
                }, () -> {
                    //关闭对话框
                    onListenter.onFinish(tag, "");
                    //请求刷新完成收回
                });
    }

    @SuppressLint("CheckResult")
    private void methodPostData(final int tag, String url, Map<String, String> params, final IGeemiCallback onListenter, final boolean isProgress) {
//        params.put("version", BaseModuleInit.getVersionName());
//        params.put("requestType","app");
        KLog.i("url====="+url);
        RetrofitClient.getInstance().create(ApiService.class)
                .executePost(url, params)
//                    .compose(RxUtils.bindToLifecycle(lifecycle)) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(disposable -> {
                    if (isProgress){
                        onListenter.onProgress(tag, "");
                    }
                })
                .subscribe((Consumer<ResponseBody>) response -> {
                    String data = response.string();
//                        KLog.i("onComplete=======" + tag + "=======" +data);
//                        MyUtil.deleteUser(data);
                    onListenter.onComplete(tag, data);
                }, (Consumer<ResponseThrowable>) throwable -> {
                    KLog.i("onError=======" + throwable.message+"===="+throwable.code);
                    //关闭对话框
                    onListenter.onError(tag, throwable.message);
                    onListenter.onFinish(tag, "");
                }, () -> {
                    //关闭对话框
                    onListenter.onFinish(tag, "");
                    //请求刷新完成收回
                });
    }


    @SuppressLint("CheckResult")
    private void methodPostDatas(final int tag, String url, RequestBody requestBody, final IGeemiCallback onListenter, final boolean isProgress) {
        RetrofitClient.getInstance().create(ApiService.class)
                .executePost(requestBody)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe((Consumer<Disposable>) disposable -> {
                    if (isProgress){
                        onListenter.onProgress(tag, "");
                    }
                })
                .subscribe((Consumer<ResponseBody>) response -> {
                    String data = response.string();
                    onListenter.onComplete(tag, data);
                }, (Consumer<ResponseThrowable>) throwable -> {
                    KLog.i("onError=======" + throwable.message);
                    //关闭对话框
                    onListenter.onError(tag, throwable.message);
                    onListenter.onFinish(tag, "");
                }, (Action) () -> {
                    //关闭对话框
                    onListenter.onFinish(tag, "");
                    //请求刷新完成收回
                });
    }



}

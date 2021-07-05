package com.geemi.facelibrary.ui.client.common;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.geemi.facelibrary.interfaces.IResultDataCallbace;
import com.geemi.facelibrary.utils.MyUtil;
import com.just.agentweb.AgentWeb;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by 2017/5/14.
 */

public class AndroidInterface {

    private Handler deliver = new Handler(Looper.getMainLooper());
    private AgentWeb agent;
    private Context context;
    private IResultDataCallbace resultDataListenter;

    public AndroidInterface(AgentWeb agent, Context context, IResultDataCallbace resultDataListenter) {
        this.agent = agent;
        this.context = context;
        this.resultDataListenter = resultDataListenter;
    }
 
    public AndroidInterface(AgentWeb agent, Context context) {
        this.agent = agent;
        this.context = context;
    }

    @JavascriptInterface
    public void commonButtonEvent(final String msg) {
        Log.i("Info", "main Thread:" + Thread.currentThread());
        KLog.i("commonButtonEvent====="+msg);
//        ToastUtils.showLong(msg);
        MyUtil.setEventBusData(6, msg);
    }

}

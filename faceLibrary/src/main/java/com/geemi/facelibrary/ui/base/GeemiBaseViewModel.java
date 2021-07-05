package com.geemi.facelibrary.ui.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.geemi.facelibrary.http.IGeemiCallback;

import java.util.HashMap;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.KLog;

public class GeemiBaseViewModel extends BaseViewModel implements IGeemiCallback {
    public GeemiBaseViewModel(@NonNull Application application) {
        super(application);
    }

    public GeemiBaseViewModel.UIChangObservable uc = new GeemiBaseViewModel.UIChangObservable();

    public class UIChangObservable {
        //加载
        public ObservableBoolean onProgress = new ObservableBoolean(false);
        //加载结束
        public ObservableBoolean onFinish = new ObservableBoolean(false);
        //加载失败
        public ObservableField<String> onError = new ObservableField<>();

        public ObservableField<Map<Integer, Object>> onComplete = new ObservableField<>();
    }

    @Override
    public void onError(int tag, String error) {
        KLog.i("tag====" + tag + "======error=====" + error);
        uc.onError.set("");
        uc.onError.set(error+",编号:"+tag);
    }

    @Override
    public void onComplete(int tag, String json) {
        Map map = new HashMap();
        map.put(tag, json);
        uc.onComplete.set(map);
    }

    @Override
    public void onProgress(int tag, String msg) {
        uc.onProgress.set(!uc.onProgress.get());
    }

    @Override
    public void onFinish(int tag, String msg) {
        uc.onFinish.set(!uc.onFinish.get());
    }
}

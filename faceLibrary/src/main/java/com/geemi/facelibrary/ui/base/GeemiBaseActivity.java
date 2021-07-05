package com.geemi.facelibrary.ui.base;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;

import com.allen.library.SuperTextView;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.http.IGeemiCallback;
import com.geemi.facelibrary.interfaces.IGeemiDialogCallback;
import com.geemi.facelibrary.model.Const;
import com.geemi.facelibrary.utils.DialogUtil;
import com.geemi.facelibrary.utils.MyUtil;
import com.geemi.facelibrary.utils.shared.SharedPreferencesUtil;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class GeemiBaseActivity extends GeemiSupportActivity implements IGeemiCallback, IGeemiDialogCallback {
    public static boolean isLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(99);
        initImmersionBar();
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        //设置共同沉浸式样式
        ImmersionBar.with(this).navigationBarColor(R.color.blue_sharder_toobar)
                .statusBarColor(R.color.blue_sharder_toobar)
                .fitsSystemWindows(true)
                .init();
    }

    // 请求权限
    public void requestPermissions(int requestCode) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                ArrayList<String> requestPerssionArr = new ArrayList<>();
                int hasCamrea = checkSelfPermission(Manifest.permission.CAMERA);
                if (hasCamrea != PackageManager.PERMISSION_GRANTED) {
                    requestPerssionArr.add(Manifest.permission.CAMERA);
                }
                int hasSdcardRead = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                if (hasSdcardRead != PackageManager.PERMISSION_GRANTED) {
                    requestPerssionArr.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                }

                int hasSdcardWrite = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (hasSdcardWrite != PackageManager.PERMISSION_GRANTED) {
                    requestPerssionArr.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                // 是否应该显示权限请求
                if (requestPerssionArr.size() >= 1) {
                    String[] requestArray = new String[requestPerssionArr.size()];
                    for (int i = 0; i < requestArray.length; i++) {
                        requestArray[i] = requestPerssionArr.get(i);
                    }
                    requestPermissions(requestArray, requestCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        boolean flag = false;
        for (int i = 0; i < permissions.length; i++) {
            if (PackageManager.PERMISSION_GRANTED == grantResults[i]) {
                flag = true;
            }
        }
    }

    /**
     * 自定义Toast
     * @param message toast 消息
     */
    public void showCustomToast(String message) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View inflate = layoutInflater.inflate(R.layout.setting_toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout));
        TextView toastTxt = (TextView) inflate.findViewById(R.id.toast_txt);
        toastTxt.setText(message);
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(inflate);
        toast.show();
    }

    @Override
    public void onError(int tag, String error) {
        KLog.i("tag==="+tag+"=====error===="+error);
        ToastUtils.showLong(error);
        DialogUtil.dismiss();
    }

    @Override
    public void onComplete(int tag, String json) {
//        onComplete(tag,json);
    }

    @Override
    public void onProgress(int tag, String msg) {
        DialogUtil.showProgress(this);
    }

    @Override
    public void onFinish(int tag, String msg) {
        DialogUtil.dismiss();
    }



    protected void setToolBarNav(SuperTextView toolbar, @DrawableRes int resId, final Activity activity, Fragment fragment){
        initToolbarNav(toolbar,resId,activity,fragment);
    }

    /**
     * 设置toolbar
     * @param toolbar
     * @param resId
     * @param activity
     */
    protected void initToolbarNav(SuperTextView toolbar, @DrawableRes int resId , Activity activity, Fragment fragment) {
        if (resId != 0){
            toolbar.setLeftIcon(resId);
        }
        ImmersionBar.setTitleBar(this, toolbar);
        toolbar.setLeftImageViewClickListener(v -> {
            if (fragment.getClass().getSimpleName().contains("GeemiHomeFragment")){
                //打开侧滑菜单
                MyUtil.setEventBusData(88888,"leftClick");
            }else {
                //此处暂时有崩溃问题 未解决
                onBackPressed();
                MyUtil.closeInput(activity);
                Const.GEEMIFACE_IMAGE = "face_image";
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        isLogin = SharedPreferencesUtil.getBoolean(this, Const.IS_LOGIN, false);
        DialogUtil.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销(一般是在Activity或Fragment的onDestory中进行)
        if (EventBus.getDefault().isRegistered(this)) {
            KLog.i("===注销EventBus");
            EventBus.getDefault().unregister(this);
        }
    }
}

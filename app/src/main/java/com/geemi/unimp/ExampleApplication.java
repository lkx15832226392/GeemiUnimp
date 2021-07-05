package com.geemi.unimp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.baidu.idl.face.platform.listener.IInitCallback;
import com.geemi.facelibrary.manager.GeemiFaceManager;
import com.geemi.facelibrary.model.Const;
import com.tencent.bugly.Bugly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.dcloud.feature.sdk.DCSDKInitConfig;
import io.dcloud.feature.sdk.DCUniMPSDK;
import io.dcloud.feature.sdk.MenuActionSheetItem;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ExampleApplication extends Application {
    // 动作活体条目集合
    public static List<LivenessTypeEnum> livenessList = new ArrayList<>();
    // 活体随机开关
    public static boolean isLivenessRandom = true;
    // 语音播报开关
    public static boolean isOpenSound = true;
    // 活体检测开关
    public static boolean isActionLive = true;
    // 质量等级（0：正常、1：宽松、2：严格、3：自定义）
    public static int qualityLevel = Const.QUALITY_HIGH;

    private static Map<String, Activity> destroyMap = new HashMap<>();

//    public static GeemiDB geemiDB;


    /**
     * 添加到销毁队列
     * @param activity 要销毁的activity
     */
    public static void addDestroyActivity(Activity activity, String activityName) {
        destroyMap.put(activityName, activity);
    }

    /**
     * 销毁指定Activity
     */
    public static void destroyActivity(String activityName) {
        Set<String> keySet = destroyMap.keySet();
        for (String key : keySet) {
            destroyMap.get(key).finish();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        geemiDB = GeemiDB.getInstance(this);
        initUnimp();
        initFaceLicense();
        initBuyLy();

    }

    private void initUnimp() {
        //初始化 uni小程序SDK ----start----------
        MenuActionSheetItem item = new MenuActionSheetItem("关于", "gy");
        MenuActionSheetItem item1 = new MenuActionSheetItem("获取当前页面url", "hqdqym");
        MenuActionSheetItem item2 = new MenuActionSheetItem("跳转到宿主原生测试页面", "gotoTestPage");
        List<MenuActionSheetItem> sheetItems = new ArrayList<>();
        sheetItems.add(item);
        sheetItems.add(item1);
        sheetItems.add(item2);
        DCSDKInitConfig config = new DCSDKInitConfig.Builder()
                .setCapsule(true)
                .setMenuDefFontSize("16px")
                .setMenuDefFontColor("#ff00ff")
                .setMenuDefFontWeight("normal")
                .setMenuActionSheetItems(sheetItems)
                .setEnableBackground(true)//开启后台运行
                .build();
        DCUniMPSDK.getInstance().initialize(this, config, b -> Log.i("unimp","onInitFinished----"+b));
        //初始化 uni小程序SDK ----end----------
    }

    private void initBuyLy() {
        Bugly.init(getApplicationContext(), "2f7cf988df", true);
    }

    //初始化人脸参数
    private void initFaceLicense() {
        ExampleApplication.livenessList.add(LivenessTypeEnum.Eye);
        GeemiFaceManager.getInstance()
                .setApplication(this)
                .setQulityLevel(qualityLevel)
                .setIsLivenessRandom(false)
                .setLivenessList(ExampleApplication.livenessList)
                .setIsOpenSound(false)
                .setSecType(FaceEnvironment.VALUE_SEC_TYPE)
//                .setLicenseID("geemiFace-face-android")
//                .setLicenseFileName("idl-license.face-android")
//                .setCallBack(new IInitCallback() {
//                    @Override
//                    public void initSuccess() {
//                        ToastUtils.showShort("初始化成功");
//                        Log.e("lkx", "初始化成功");
//                    }
//
//                    @Override
//                    public void initFailure(int i, String s) {
//                        ToastUtils.showShort("初始化失败 = " + i + " " + s);
//                        Log.e("lkx", "初始化失败 = " + i + " " + s);
//                    }
//                })

                .init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }
}

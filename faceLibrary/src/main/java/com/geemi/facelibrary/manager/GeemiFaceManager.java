package com.geemi.facelibrary.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.baidu.idl.face.platform.listener.IInitCallback;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.http.GeemiHttpImpl;
import com.geemi.facelibrary.model.QualityConfig;
import com.kongzue.dialog.v2.DialogSettings;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.goldze.mvvmhabit.crash.CaocConfig;
import me.goldze.mvvmhabit.crash.DefaultErrorActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.external.ExternalAdaptInfo;
import me.jessyan.autosize.onAdaptListener;
import me.jessyan.autosize.utils.LogUtils;

import static com.kongzue.dialog.v2.DialogSettings.STYLE_IOS;
import static com.kongzue.dialog.v2.DialogSettings.THEME_LIGHT;

public class GeemiFaceManager {
    public static List<Activity> activities = new ArrayList<>();
    private Application instance = null;
    private static GeemiHttpImpl httpModel = null;
    public static Context mContext;
    //质量等级（0：正常、1：宽松、2：严格、3：自定义）
    private int qulityLevel = 1;
    //活体动作集合
    private List<LivenessTypeEnum> livenessList = new ArrayList<>();
    //设置动作活体是否随机
    private boolean isLivenessRandom;
    //设置开启提示音
    private boolean isOpenSound;
    //加密类型，0：Base64加密，上传时image_sec传false；1：百度加密文件加密，上传时image_sec传true
    private int secType;
    // 申请License取得的APPID geemiFace-face-android
    private String licenseID;
    // assets目录下License文件名 idl-license.face-android
    private String licenseFileName;
    private IInitCallback callback;

    private static GeemiFaceManager mInstance;
    private Application application;

    private GeemiFaceManager() {
    }

    public static synchronized GeemiFaceManager getInstance() {
        if (mInstance == null) {
            mInstance = new GeemiFaceManager();
        }
        return mInstance;
    }

    public static GeemiHttpImpl getHttpModel() {
        if (httpModel == null) {
            httpModel = new GeemiHttpImpl();
        }
        return httpModel;
    }

    public void init(Context mContext) {
        this.mContext = mContext;
        init();
    }



    public GeemiFaceManager setLicenseID(String licenseID){
        this.licenseID = licenseID;
        return this;
    }

    public GeemiFaceManager setLicenseFileName(String licenseFileName){
        this.licenseFileName = licenseFileName;
        return this;
    }

    public GeemiFaceManager setCallBack(IInitCallback callback){
        this.callback = callback;
        return this;
    }

        public GeemiFaceManager setApplication(Application application) {
            this.instance = application;
            return this;
        }

    public GeemiFaceManager setQulityLevel(int qulityLevel){
        this.qulityLevel = qulityLevel;
        return this;
    }

    public GeemiFaceManager setLivenessList(List<LivenessTypeEnum> livenessList){
        this.livenessList = livenessList;
        return this;
    }

    public GeemiFaceManager setIsLivenessRandom(boolean isLivenessRandom){
        this.isLivenessRandom = isLivenessRandom;
        return this;
    }

    public GeemiFaceManager setIsOpenSound(boolean isOpenSound){
        this.isOpenSound = isOpenSound;
        return this;
    }
    public GeemiFaceManager setSecType(int secType){
        this.secType = secType;
        return this;
    }


    public boolean init() {
        livenessList.add(LivenessTypeEnum.Eye);
        Utils.init(mContext);
        //是否开启日志打印
        KLog.init(true);
        //配置全局异常崩溃操作
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.ic_launcher) //错误图标
//                .restartActivity(LoginActivity.class) //重新启动后的activity
                //.errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
                //.eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
        dialogStyle();
        configUnits(instance);
        FileDownloader.setup(mContext);
        arouterInit(instance);
//        LibraryInitOCR.initOCR(mContext);
        return setFaceConfig(qulityLevel,livenessList,isLivenessRandom,isOpenSound,secType,licenseID,licenseFileName,callback);
    }

    //设置dialog样式
    private void dialogStyle() {
//        iOS 风格对应 DialogSettings.STYLE_IOS
        DialogSettings.use_blur = false;//设置是否启用模糊
        //决定等待框、提示框以及iOS风格的对话框的模糊背景透明度（50-255）
//        DialogSettings.blur_alpha = 200;
        DialogSettings.style = STYLE_IOS;
        DialogSettings.tip_theme = THEME_LIGHT;
    }

    //初始化路由器
    private void arouterInit(Application application) {
        //初始化阿里路由框架
//        if (BuildConfig.DEBUG) {
//        }
        //开启打印日志
        ARouter.openLog();//打印日志
        ARouter.openDebug();//开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(application); // 尽可能早，推荐在Application中初始化
    }


    /**
     * 参数配置方法
     * @return
     */
    private static boolean setFaceConfig(int qulityLevel, List<LivenessTypeEnum> livenessList, boolean isLivenessRandom, boolean isOpenSound, int secType,String licenseID, String licenseFileName,final IInitCallback callback) {
        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        // 根据质量等级获取相应的质量值（注：第二个参数要与质量等级的set方法参数一致）
        QualityConfigManager manager = QualityConfigManager.getInstance();
        manager.readQualityFile(mContext, qulityLevel);
        QualityConfig qualityConfig = manager.getConfig();
        if (qualityConfig == null) {
            return false;
        }
        // 设置模糊度阈值
        config.setBlurnessValue(qualityConfig.getBlur());
        // 设置最小光照阈值（范围0-255）
        config.setBrightnessValue(qualityConfig.getMinIllum());
        // 设置最大光照阈值（范围0-255）
        config.setBrightnessMaxValue(qualityConfig.getMaxIllum());
        // 设置左眼遮挡阈值
        config.setOcclusionLeftEyeValue(qualityConfig.getLeftEyeOcclusion());
        // 设置右眼遮挡阈值
        config.setOcclusionRightEyeValue(qualityConfig.getRightEyeOcclusion());
        // 设置鼻子遮挡阈值
        config.setOcclusionNoseValue(qualityConfig.getNoseOcclusion());
        // 设置嘴巴遮挡阈值
        config.setOcclusionMouthValue(qualityConfig.getMouseOcclusion());
        // 设置左脸颊遮挡阈值
        config.setOcclusionLeftContourValue(qualityConfig.getLeftContourOcclusion());
        // 设置右脸颊遮挡阈值
        config.setOcclusionRightContourValue(qualityConfig.getRightContourOcclusion());
        // 设置下巴遮挡阈值
        config.setOcclusionChinValue(qualityConfig.getChinOcclusion());
        // 设置人脸姿态角阈值
        config.setHeadPitchValue(qualityConfig.getPitch());
        config.setHeadYawValue(qualityConfig.getYaw());
        config.setHeadRollValue(qualityConfig.getRoll());
        // 设置可检测的最小人脸阈值
        config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        // 设置可检测到人脸的阈值
        config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        // 设置闭眼阈值
        config.setEyeClosedValue(FaceEnvironment.VALUE_CLOSE_EYES);
        // 设置图片缓存数量
        config.setCacheImageNum(FaceEnvironment.VALUE_CACHE_IMAGE_NUM);
        // 设置活体动作，通过设置list，LivenessTypeEunm.Eye, LivenessTypeEunm.Mouth,
        // LivenessTypeEunm.HeadUp, LivenessTypeEunm.HeadDown, LivenessTypeEunm.HeadLeft,
        // LivenessTypeEunm.HeadRight
        config.setLivenessTypeList(livenessList);
        // 设置动作活体是否随机
        config.setLivenessRandom(isLivenessRandom);
        // 设置开启提示音
        config.setSound(isOpenSound);
        // 原图缩放系数
        config.setScale(FaceEnvironment.VALUE_SCALE);
        // 抠图宽高的设定，为了保证好的抠图效果，建议高宽比是4：3
        config.setCropHeight(FaceEnvironment.VALUE_CROP_HEIGHT);
        config.setCropWidth(FaceEnvironment.VALUE_CROP_WIDTH);
        // 抠图人脸框与背景比例
        config.setEnlargeRatio(FaceEnvironment.VALUE_CROP_ENLARGERATIO);
        // 加密类型，0：Base64加密，上传时image_sec传false；1：百度加密文件加密，上传时image_sec传true
        config.setSecType(secType);
        // 检测超时设置
        config.setTimeDetectModule(FaceEnvironment.TIME_DETECT_MODULE);
        // 检测框远近比率
        config.setFaceFarRatio(FaceEnvironment.VALUE_FAR_RATIO);
        config.setFaceClosedRatio(FaceEnvironment.VALUE_CLOSED_RATIO);
        FaceSDKManager.getInstance().setFaceConfig(config);

        FaceSDKManager.getInstance().initialize(mContext, licenseID, licenseFileName, callback);

        return false;
    }


   //初始化AndroidAutoSize
    private void configUnits(Application application) {
        AutoSizeConfig.getInstance()
                //屏幕适配监听器
                .setOnAdaptListener(new onAdaptListener() {
                    @Override
                    public void onAdaptBefore(Object target, Activity activity) {
                        //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
                        //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
//                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
//                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
                    }

                    @Override
                    public void onAdaptAfter(Object target, Activity activity) {
                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
                    }
                });

        AutoSize.initCompatMultiProcess(application);
        customAdaptForExternal();
    }

    private void customAdaptForExternal() {
        AutoSizeConfig.getInstance().getExternalAdaptManager()
                .addExternalAdaptInfoOfActivity(DefaultErrorActivity.class, new ExternalAdaptInfo(true, 400))
        ;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void clearActivitys() {
        if (activities != null && activities.size() > 0) {
            for (Activity activity : activities) {
                activity.finish();
            }
        }
    }

}

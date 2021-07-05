package com.geemi.facelibrary.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;

import com.baidu.idl.face.platform.utils.Base64Utils;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.manager.GeemiFaceManager;
import com.geemi.facelibrary.model.MessageEventbus;
import com.google.gson.Gson;
import com.kongzue.dialog.v2.CustomDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.zkteco.android.biometric.core.device.ParameterHelper;
import com.zkteco.android.biometric.core.device.TransportType;
import com.zkteco.android.biometric.core.utils.LogHelper;
import com.zkteco.android.biometric.module.idcard.IDCardReader;
import com.zkteco.android.biometric.module.idcard.IDCardReaderFactory;
import com.zkteco.android.biometric.module.idcard.exception.IDCardReaderException;
import com.znht.iodev2.PowerCtl;
import com.zyyoona7.wheel.WheelView;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class MyUtil {
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    public static SoundPool sp;
    public static Map<Integer, Integer> suondMap;

    //弹出软键盘
    public static void showInput(final View et, Activity context) {
        et.requestFocus();
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    //关闭软键盘
    @SuppressLint("NewApi")
    public static void closeInput(Activity context) {
        if (context.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager im = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(Objects.requireNonNull(context.getCurrentFocus()).getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //注册点击监听
    public static void setViewsOnClick(View.OnClickListener onClickListener,
                                       View... views) {
        for (View view : views) {
            if (view != null)
                view.setOnClickListener(onClickListener);
        }
    }

    /**
     * 批量隐藏控件
     *
     * @param v
     */
    public static void listEndView(View... v) {
        for (View view : v) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 批量显示控件
     *
     * @param v
     */
    public static void listStartVoew(View... v) {
        for (View view : v) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void setEventBusData(int tag, Object object) {
        MessageEventbus messageWrap = new MessageEventbus();
        messageWrap.setMessageId(tag);
        messageWrap.setObject(object);
        EventBus.getDefault().postSticky(messageWrap);
    }

    /**
     * 配置wheelView
     *
     * @param wheelView
     */
    @SuppressLint("Range")
    public static void setWheelView(WheelView wheelView, Context context) {
        //设置滚动声音资源文件
        wheelView.setSoundEffectResource(R.raw.button_choose);
        //开启滚动声音
        wheelView.setSoundEffect(true);
        //滚动声音音量
        wheelView.setPlayVolume(1.0f);
        //是否循环滚动
        wheelView.setCyclic(false);
        //可见条数
        wheelView.setVisibleItems(7);
        //设置3D圆弧系数
        wheelView.setCurvedArcDirectionFactor(10.f);
        wheelView.setNormalItemTextColor(context.getResources().getColor(R.color.setting_textcolor1));
        wheelView.setSelectedItemTextColor(context.getResources().getColor(R.color.setting2_color));
        //设置字体
        wheelView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD), true);
    }

    /**
     * dp转px
     *
     * @param dpVal dp 值
     * @return px
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, Resources.getSystem().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    public static <E> E readFromAssets(Context activity, String jsonStr, Class<E> clazz) {
        Gson gson = new Gson();
        E ben;
        try {
            InputStream is = activity.getAssets().open(jsonStr);//此处为要加载的json文件名称
            String text = readTextFromSDcard(is);
            ben = gson.fromJson(text, clazz);
            return ben;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //将传入的is一行一行解析读取出来出来
    private static String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder buffer = new StringBuilder();
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        return buffer.toString();//把读取的数据返回
    }

    public static int getResId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "mipmap", paramContext.getPackageName());
    }


    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64Utils.decode(base64Data, Base64Utils.NO_WRAP);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    //配置自定义Dialog宽度填充全屏
    public static void dialogWindowManager(CustomDialog customDialog) {
        Window window = customDialog.getAlertDialog().getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);   // 注意，没有这一行对话框是没法填充屏幕的
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;    // 设置位置为屏幕底部
        window.setAttributes(lp);
    }

    //初始化声音池
    public static SoundPool getInstance(Context context) {
        GeemiFaceManager.mContext = context;
//		sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        if (sp == null) {
            sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        }
        sp.load(context, R.raw.msg, 1);

        return sp;

    }

    //播放声音池声音
    public static void play(int soundID) {
        sp.play(soundID, 1, 1, 0, 0, 1);
    }


    /**
     * 打开模块
     */
    public static IDCardReader open(PowerCtl powerCtl) {
        IDCardReader idCardReader = null;
        try {
            //打开二代证供电
            powerCtl = new PowerCtl();
            powerCtl.identity_uhf_power(1);
            powerCtl.identity_ctl(1);
            // Define output log level
            LogHelper.setLevel(Log.VERBOSE);
            // Start fingerprint sensor
            Map idrparams = new HashMap();
            //设置串口和波特率
            idrparams.put(ParameterHelper.PARAM_SERIAL_SERIALNAME, "/dev/ttysWK2");
            idrparams.put(ParameterHelper.PARAM_SERIAL_BAUDRATE, 115200);
            //打开二代证模块
            idCardReader = IDCardReaderFactory.createIDCardReader(GeemiFaceManager.mContext, TransportType.SERIALPORT, idrparams);
            if (idCardReader != null) {
                //打开模块
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                idCardReader.open(0);
                KLog.i("====NFC模块打开成功");
            }
        } catch (IDCardReaderException e) {
            //打开模块失败给出提示
//            ToastUtils.showLong("模块打开失败");
            KLog.i("====" + e.getMessage() + "====" + e.getInternalErrorCode());
            return null;
        }
        return idCardReader;
    }

    /***
     * 关闭模块
     */
    public static boolean close(PowerCtl powerCtl, IDCardReader idCardReader) {
        try {
            //说明：300ms的延时是为防止线程操作异常，开发时视实际情况
            Thread.sleep(300);
            idCardReader.close(0);
            if (powerCtl != null) {
                powerCtl.identity_uhf_power(0);
                powerCtl.identity_ctl(0);
            }
            KLog.i("====NFC模块关闭成功");
        } catch (Exception e) {
            ToastUtils.showLong("模块关闭异常失败");
            KLog.i("====" + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 根据身份证号 计算出年龄
     * @param sfzjh
     * @return
     */
    public static String evaluate(String sfzjh) {

        if (sfzjh == null || "".equals(sfzjh)) {
            return "身份证件号有误,无法计算年龄";
        }

        if (sfzjh.length() != 15 && sfzjh.length() != 18) {
            return "身份证件号有误,无法计算年龄";
        }

        String age = "身份证件号有误,无法计算年龄";

//        CheckSFZ sf = new CheckSFZ(); // 调用校验身份证的函数，若身份证完全正确，此处可以不需要
//        sfzjh = sf.evaluate(sfzjh);
//
//        if(sfzjh != "身份证件号有误"){

        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        int year = Integer.parseInt(sfzjh.substring(6, 10));
        int month = Integer.parseInt(sfzjh.substring(10, 12));
        int day = Integer.parseInt(sfzjh.substring(12, 14));

//        if ((month < monthNow) || (month == monthNow && day <= dayNow)) {
//            age = String.valueOf(yearNow - year);
//        } else {
//            age = String.valueOf(yearNow - year - 1);
//        }
        age = String.valueOf(yearNow - year);

//        }

        return age;
    }
    public static Bitmap ImageToBitmap(String path,Context context) {
        Bitmap map;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = (int) 7.5; /*图片长宽方向缩小倍数*/
            options.inJustDecodeBounds = false;
            File file = new File(path);
            Uri uri = Uri.fromFile(file);
            map = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
            return map;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            KLog.i("eeeeee===="+e.getMessage());
        }
        return null;
    }

    //选择图片模式
    public static void pictureMode(boolean isMode, boolean enableCrop, int maxSelectNum, Fragment fragment, int resultCode){
        if (isMode){//相机模式
            PictureSelector.create(fragment)
                    .openCamera(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                    .enableCrop(enableCrop)// 是否裁剪
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(true)// 是否圆形裁剪
                    .openClickSound(true)// 是否开启点击声音
                    .isCamera(false)// 是否显示拍照按钮
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
                    .compress(false)// 是否压缩
                    .forResult(resultCode);//结果回调onActivityResult code
        }else{//相册模式
            PictureSelector.create(fragment)
                    .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
                    .isCamera(false)// 是否显示拍照按钮
                    .enableCrop(enableCrop)// 是否裁剪
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(true)// 是否圆形裁剪
                    .openClickSound(true)// 是否开启点击声音
                    .compress(false)// 是否压缩
                    .forResult(resultCode);//结果回调onActivityResult code
        }
    }


    //选择图片模式
    public static void pictureMode(boolean isMode, boolean enableCrop, int maxSelectNum, Activity activity, int resultCode){
        if (isMode){//相机模式
            PictureSelector.create(activity)
                    .openCamera(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                    .enableCrop(enableCrop)// 是否裁剪
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(true)// 是否圆形裁剪
                    .openClickSound(true)// 是否开启点击声音
                    .isCamera(false)// 是否显示拍照按钮
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
                    .compress(true)// 是否压缩
                    .forResult(resultCode);//结果回调onActivityResult code
        }else{//相册模式
            PictureSelector.create(activity)
                    .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
                    .isCamera(false)// 是否显示拍照按钮
                    .enableCrop(enableCrop)// 是否裁剪
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(true)// 是否圆形裁剪
                    .openClickSound(true)// 是否开启点击声音
                    .compress(true)// 是否压缩
                    .forResult(resultCode);//结果回调onActivityResult code
        }
    }

    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}

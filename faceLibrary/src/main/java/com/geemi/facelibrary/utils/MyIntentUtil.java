package com.geemi.facelibrary.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


import com.alibaba.android.arouter.launcher.ARouter;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.model.Const;
import com.geemi.facelibrary.ui.activity.GeemiContainerActivity;

import static android.app.Activity.RESULT_OK;


/**
 * 跳转工具类
 */
public class MyIntentUtil {
    public static final String FRAGMENT = "fragment";
    public static final String BUNDLE = "bundle";
    public static long PERFECT_MILLS = 618;
    public static final int MINI_RADIUS = 0;
    @SuppressLint("NewApi")
    public static void setStartContainerActivity(Activity activity, String canonicalName, Bundle bundle, Class<?> cls, View view) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra(FRAGMENT, canonicalName);
        if (bundle != null) {
//            if (!bundle.getBoolean("isToolbar"))
//            bundle.putBoolean("isToolbar",true);
            intent.putExtra(MyIntentUtil.BUNDLE, bundle);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (view != null){
                activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, view, "images") .toBundle());
            }else{
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.activity_up, R.anim.activity_push_no_anim);
            }
        } else {
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.activity_up, R.anim.activity_push_no_anim);
        }


//        if (view != null){
//            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, view, "images") .toBundle());
//        }else{
//            activity.startActivity(intent);
//            activity.overridePendingTransition(R.anim.activity_up, R.anim.activity_push_no_anim);
//        }
    }

    @SuppressLint("NewApi")
    public static void setAnimContainerActivity(final Activity thisActivity, String canonicalName, final Bundle bundle, Class<?> cls, final View triggerView) {
        final Intent intent = new Intent(thisActivity, cls);
        intent.putExtra(Const.KEY_INTENT_LEVEL_SAVE, canonicalName);
        if (bundle != null) {
            intent.putExtra(MyIntentUtil.BUNDLE, bundle);
        }
        int[] location = new int[2];
        triggerView.getLocationInWindow(location);
        final int cx = location[0] + triggerView.getWidth() / 2;
        final int cy = location[1] + triggerView.getHeight() / 2;
        final ImageView view = new ImageView(thisActivity);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setImageResource(R.color.blue_sharder_toobar);
        final ViewGroup decorView = (ViewGroup) thisActivity.getWindow().getDecorView();
        int w = decorView.getWidth();
        int h = decorView.getHeight();
        decorView.addView(view, w, h);

        // 计算中心点至view边界的最大距离
        int maxW = Math.max(cx, w - cx);
        int maxH = Math.max(cy, h - cy);
        final int finalRadius = (int) Math.sqrt(maxW * maxW + maxH * maxH) + 1;
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        int maxRadius = (int) Math.sqrt(w * w + h * h) + 1;
        // 若使用默认时长，则需要根据水波扩散的距离来计算实际时间
        if (PERFECT_MILLS == PERFECT_MILLS) {
            // 算出实际边距与最大边距的比率
            double rate = 1d * finalRadius / maxRadius;
            // 水波扩散的距离与扩散时间成正比
            PERFECT_MILLS = (long) (PERFECT_MILLS * rate);
        }
        final long finalDuration = PERFECT_MILLS;
        anim.setDuration(finalDuration);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                thisActivity.startActivity(intent);
                thisActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                // 默认显示返回至当前Activity的动画.
                final Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, finalRadius, 0);
                anim.setDuration(finalDuration);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        try {
                            decorView.removeView(view);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                triggerView.postDelayed(anim::start, 1000);
            }
        });
        anim.start();
    }



    /**
     *fragment跳转
     * @param data
     * @return
     */
    public static Fragment initFromIntent(Intent data) {
        if (data == null) {
            throw new RuntimeException("you must provide a page info to display");
        }
        try {
            String fragmentName = data.getStringExtra(FRAGMENT);
            if (fragmentName == null || "".equals(fragmentName)) {
                throw new IllegalArgumentException("can not find page fragmentName");
            }
            Class<?> fragmentClass = Class.forName(fragmentName);
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            Bundle args = data.getBundleExtra(BUNDLE);
            if (args != null) {
                fragment.setArguments(args);
            }
            return fragment;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("fragment initialization failed!");
    }

    /**
     * Bundle跳转
     * @param context
     * @param className
     * @param bundle
     */
    public static void startActivityForResult(Activity context, Class className, Bundle bundle) {
        Intent toIntent = new Intent(context, className);
        if (bundle != null) {
            toIntent.putExtras(bundle);
        }
        context.startActivity(toIntent);
        context.overridePendingTransition(R.anim.activity_up,
                R.anim.activity_push_no_anim);
    }


    /**
     * 目标跳转
     * @param title 目标显示title
     * @param router 目标地址
     * @param view 是否需要显示动画的view
    //     * @param isToolbar 是否显示toolbar
     * @param rightRes 是否显示right图标或文字
     */
    public static void jumpTag(Activity context, String title, String router, View view, String rightRes){
        Bundle mBundle = new Bundle();
        mBundle.putString(GeemiContainerActivity.TITLE, title);
        mBundle.putBoolean(GeemiContainerActivity.ISTOOLBAR, true);
        mBundle.putString(GeemiContainerActivity.RIGHTRES,rightRes);
        if (ARouter.getInstance().build(router).navigation() == null)
            return;

        Fragment fragment = (Fragment) ARouter.getInstance().build(router).navigation();
        setStartContainerActivity(context, fragment.getClass().getCanonicalName(),mBundle, GeemiContainerActivity.class,view);
    }

    public static void jumpTag(Activity context, String title, String router, View view, Boolean isTitleCLick){
        Bundle mBundle = new Bundle();
        mBundle.putString(GeemiContainerActivity.TITLE, title);
        mBundle.putBoolean(GeemiContainerActivity.ISTOOLBAR, true);
        mBundle.putBoolean(GeemiContainerActivity.ISTITLECLICK,isTitleCLick);
        if (ARouter.getInstance().build(router).navigation() == null)
            return;
        Fragment fragment = (Fragment) ARouter.getInstance().build(router).navigation();
        setStartContainerActivity(context, fragment.getClass().getCanonicalName(),mBundle, GeemiContainerActivity.class,view);
    }


    public static void jumpTag(Activity context, String title, String router, View view){
        Bundle mBundle = new Bundle();
        mBundle.putString(GeemiContainerActivity.TITLE, title);
        mBundle.putBoolean(GeemiContainerActivity.ISTOOLBAR, true);
        mBundle.putBoolean(GeemiContainerActivity.ISTITLECLICK,false);
        if (ARouter.getInstance().build(router).navigation() == null)
            return;
        Fragment fragment = (Fragment) ARouter.getInstance().build(router).navigation();
        setStartContainerActivity(context, fragment.getClass().getCanonicalName(),mBundle, GeemiContainerActivity.class,view);
    }

    public static void jumpTag(Activity context, String title, Fragment fragment, View view, String rightRes){
        Bundle mBundle = new Bundle();
        mBundle.putString(GeemiContainerActivity.TITLE, title);
        mBundle.putBoolean(GeemiContainerActivity.ISTOOLBAR, true);
        mBundle.putString(GeemiContainerActivity.RIGHTRES,rightRes);
        if (fragment == null)
            return;
        setStartContainerActivity(context, fragment.getClass().getCanonicalName(),mBundle, GeemiContainerActivity.class,view);
    }

    public static void jumpTag(Activity context, String title, Fragment fragment, View view, String rightRes, Bundle mBundle){
        mBundle.putString(GeemiContainerActivity.TITLE, title);
        mBundle.putBoolean(GeemiContainerActivity.ISTOOLBAR, true);
        mBundle.putString(GeemiContainerActivity.RIGHTRES,rightRes);
        if (fragment == null)
            return;
        setStartContainerActivity(context, fragment.getClass().getCanonicalName(),mBundle, GeemiContainerActivity.class,view);
    }

    //是否修改toolbar背景颜色
    public static void jumpTag(Activity context, String title, String router, View view, String rightRes, String colorId){
        Bundle mBundle = new Bundle();
        mBundle.putString(GeemiContainerActivity.TITLE, title);
        mBundle.putBoolean(GeemiContainerActivity.ISTOOLBAR, true);
        mBundle.putString(GeemiContainerActivity.RIGHTRES,rightRes);
        mBundle.putString(GeemiContainerActivity.TOOLBARCOLOR,colorId);
        if (ARouter.getInstance().build(router).navigation() == null)
            return;
        Fragment fragment = (Fragment) ARouter.getInstance().build(router).navigation();
        setStartContainerActivity(context, fragment.getClass().getCanonicalName(),mBundle, GeemiContainerActivity.class,view);
    }
    //toolbar是否显示
    public static void jumpTag(Activity context, String title, String router, View view, String rightRes, Boolean isToolbar){
        Bundle mBundle = new Bundle();
        mBundle.putString(GeemiContainerActivity.TITLE, title);
        mBundle.putBoolean(GeemiContainerActivity.ISTOOLBAR, isToolbar);
        mBundle.putString(GeemiContainerActivity.RIGHTRES,rightRes);
        if (ARouter.getInstance().build(router).navigation() == null)
            return;
        Fragment fragment = (Fragment) ARouter.getInstance().build(router).navigation();
        setStartContainerActivity(context, fragment.getClass().getCanonicalName(),mBundle, GeemiContainerActivity.class,view);
    }

    public static void jumpTag(Activity context, String title, String router, View view, String rightRes, Boolean isToolbar, Bundle mBundle){
        mBundle.putString(GeemiContainerActivity.TITLE, title);
        mBundle.putBoolean(GeemiContainerActivity.ISTOOLBAR, isToolbar);
        mBundle.putString(GeemiContainerActivity.RIGHTRES,rightRes);
        if (ARouter.getInstance().build(router).navigation() == null)
            return;
        Fragment fragment = (Fragment) ARouter.getInstance().build(router).navigation();
        setStartContainerActivity(context, fragment.getClass().getCanonicalName(),mBundle, GeemiContainerActivity.class,view);
    }



    //动画跳转
    public static void jumpTag(Activity context, String title, String router, View view, String rightRes, Boolean isToolbar, Boolean isAnim){
        Bundle mBundle = new Bundle();
        mBundle.putString(GeemiContainerActivity.TITLE, title);
        mBundle.putBoolean(GeemiContainerActivity.ISTOOLBAR, isToolbar);
        mBundle.putString(GeemiContainerActivity.RIGHTRES,rightRes);
        if (ARouter.getInstance().build(router).navigation() == null)
            return;
        Fragment fragment = (Fragment) ARouter.getInstance().build(router).navigation();
        if (isAnim){
            setAnimContainerActivity(context, fragment.getClass().getCanonicalName(),mBundle, GeemiContainerActivity.class,view);
            return;
        }
        setStartContainerActivity(context, fragment.getClass().getCanonicalName(),mBundle, GeemiContainerActivity.class,view);
    }

    /**
     * 动画带参数跳转
     * @param context
     * @param title
     * @param router
     * @param view
     * @param rightRes
     * @param isToolbar
     * @param isAnim 是否开启动画
     */
    public static void jumpTag(Activity context, String title, String router, Bundle mBundle, View view, String rightRes, Boolean isToolbar, Boolean isAnim){
        mBundle.putString(GeemiContainerActivity.TITLE, title);
        mBundle.putBoolean(GeemiContainerActivity.ISTOOLBAR, isToolbar);
        mBundle.putString(GeemiContainerActivity.RIGHTRES,rightRes);
        Fragment fragment = (Fragment) ARouter.getInstance().build(router).navigation();
        if (isAnim){
            setAnimContainerActivity(context, fragment.getClass().getCanonicalName(),mBundle, GeemiContainerActivity.class,view);
            return;
        }
        setStartContainerActivity(context, fragment.getClass().getCanonicalName(),mBundle, GeemiContainerActivity.class,view);
    }


    /**
     * 参数跳转Intent
     *
     * @param
     */
    public static void backWithData(Bundle bundle, Activity intent) {
        Intent data = new Intent();
        data.putExtras(bundle);
        intent.setResult(RESULT_OK, data);
        intent.finish();
    }

}

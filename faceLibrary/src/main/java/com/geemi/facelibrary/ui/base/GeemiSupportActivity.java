package com.geemi.facelibrary.ui.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.geemi.facelibrary.R;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OSUtils;
import com.tianma.netdetector.lib.NetStateChangeObserver;
import com.tianma.netdetector.lib.NetStateChangeReceiver;
import com.tianma.netdetector.lib.NetworkType;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import me.goldze.mvvmhabit.utils.KLog;
import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.SupportHelper;
import me.yokeyword.fragmentation.anim.FragmentAnimator;


/**
 * 展示自定制的MySupportActivity，不继承SupportActivity
 * Created by YoKey on 17/6/24.
 */

public class GeemiSupportActivity extends RxAppCompatActivity implements ISupportActivity, NetStateChangeObserver {
    final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);
    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return mDelegate;
    }

    /**
     * Perform some extra transactions.
     * 额外的事务：自定义Tag，添加SharedElement动画，操作非回退栈Fragment
     */
    @Override
    public ExtraTransaction extraTransaction() {
        return mDelegate.extraTransaction();
    }
    private static final int READ_CONTACTS_CODE = 10;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        mDelegate.onDestroy();

        //注销(一般是在Activity或Fragment的onDestory中进行)
        if (EventBus.getDefault().isRegistered(this)) {
            KLog.i("====注销EventBus");
            EventBus.getDefault().unregister(this);
        }
        if (needRegisterNetworkChangeObserver()) {
            NetStateChangeReceiver.unregisterObserver(this);
        }

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (needRegisterNetworkChangeObserver()) {
//            NetStateChangeReceiver.unregisterObserver(this);
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 非必加
        // 如果你的app可以横竖屏切换，适配了华为emui3系列系统手机，并且navigationBarWithEMUI3Enable为true，
        // 请在onResume方法里添加这句代码（同时满足这三个条件才需要加上代码哦：1、横竖屏可以切换；2、华为emui3系列系统手机；3、navigationBarWithEMUI3Enable为true）
        // 否则请忽略
        if (OSUtils.isEMUI3_x() && isImmersionBarEnabled()) {
            ImmersionBar.with(this).init();
        }

        if (needRegisterNetworkChangeObserver()) {
            NetStateChangeReceiver.registerObserver(this);
        }
    }

    /**
     * Note： return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    /**
     * 不建议复写该方法,请使用 {@link #onBackPressedSupport} 代替
     */
    @Override
    final public void onBackPressed() {
        mDelegate.onBackPressed();
    }

    /**
     * 该方法回调时机为,Activity回退栈内Fragment的数量 小于等于1 时,默认finish Activity
     * 请尽量复写该方法,避免复写onBackPress(),以保证SupportFragment内的onBackPressedSupport()回退事件正常执行
     */
    @Override
    public void onBackPressedSupport() {
        mDelegate.onBackPressedSupport();
    }

    /**
     * 获取设置的全局动画 copy
     *
     * @return FragmentAnimator
     */
    @Override
    public FragmentAnimator getFragmentAnimator() {
        return mDelegate.getFragmentAnimator();
    }

    /**
     * Set all fragments animation.
     * 设置Fragment内的全局动画
     */
    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    /**
     * Set all fragments animation.
     * 构建Fragment转场动画
     * <p/>
     * 如果是在Activity内实现,则构建的是Activity内所有Fragment的转场动画,
     * 如果是在Fragment内实现,则构建的是该Fragment的转场动画,此时优先级 > Activity的onCreateFragmentAnimator()
     *
     * @return FragmentAnimator对象
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return mDelegate.onCreateFragmentAnimator();
    }

    /**
     * Causes the Runnable r to be added to the action queue.
     * <p>
     * The runnable will be run after all the previous action has been run.
     * <p>
     * 前面的事务全部执行后 执行该Action
     */
    @Override
    public void post(Runnable runnable) {
        mDelegate.post(runnable);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isImmersionBarEnabled()) {
            // 非必加
            // 如果你的app可以横竖屏切换，适配了4.4或者华为emui3.1系统手机，并且navigationBarWithKitkatEnable为true，
            // 请务必在onConfigurationChanged方法里添加如下代码（同时满足这三个条件才需要加上代码哦：1、横竖屏可以切换；2、android4.4或者华为emui3.1系统手机；3、navigationBarWithKitkatEnable为true）
            // 否则请忽略ImmersionBar.with(this).fullScreen(true).transparentNavigationBar().init();
            ImmersionBar.with(this)
                    .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题
                    .fullScreen(false)      //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
                    .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                    .navigationBarColor(R.color.background)
//                    .transparentNavigationBar()
                    .init();
        }
        if (newConfig.fontScale != 1) { //fontScale不为1，需要强制设置为1
            getResources();//控制字体大小不随系统字体大小的改变而改变
        }
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources.getConfiguration().fontScale != 1) { //fontScale不为1，需要强制设置为1
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置成默认值，即fontScale为1
            resources.updateConfiguration(newConfig, resources.getDisplayMetrics());
        }
        return resources;
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        ImmersionBar.with(this)
//                .statusBarColor(R.color.colorAccent)//设置导航栏背景颜色
                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题
                .fullScreen(false)      //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .navigationBarColor(R.color.background)
//                .transparentNavigationBar()
//                .navigationBarAlpha(0.1f)  //导航栏透明度，不写默认0.0F
//                .navigationBarDarkIcon(false) //导航栏图标是深色，不写默认为亮色
                .init();
    }
    /****************************************以下为可选方法(Optional methods)******************************************************/

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }
    // 选择性拓展其他方法

    public void loadRootFragment(int containerId, @NonNull ISupportFragment toFragment) {
        mDelegate.loadRootFragment(containerId, toFragment);
    }

    public void start(ISupportFragment toFragment) {
        mDelegate.start(toFragment);
    }

    /**
     * @param launchMode Same as Activity's LaunchMode.
     */
    public void start(ISupportFragment toFragment, @ISupportFragment.LaunchMode int launchMode) {
        mDelegate.start(toFragment, launchMode);
    }

    /**
     *
     * @see #popTo(Class, boolean)
     * +
     * @see #start(ISupportFragment)
     */
    public void startWithPopTo(ISupportFragment toFragment, Class<?> targetFragmentClass, boolean includeTargetFragment) {
        mDelegate.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment);
    }

    /**
     * Pop the fragment.
     */
    public void pop() {
        mDelegate.pop();
    }

    /**
     * Pop the last fragment transition from the manager's fragment
     * back stack.
     */
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment);
    }

    /**
     * If you want to begin another FragmentTransaction immediately after popTo(), use this method.
     * 如果你想在出栈后, 立刻进行FragmentTransaction操作，请使用该方法
     */
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable);
    }

    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim);
    }

    /**
     * 得到位于栈顶Fragment
     */
    public ISupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getSupportFragmentManager());
    }

    /**
     * 获取栈内的fragment对象
     */
    public <T extends ISupportFragment> T findFragment(Class<T> fragmentClass) {
        return SupportHelper.findFragment(getSupportFragmentManager(), fragmentClass);
    }

    /**
     * 是否需要注册网络变化的Observer,如果不需要监听网络变化,则返回false;否则返回true.默认返回false
     */
    protected boolean needRegisterNetworkChangeObserver() {
        return false;
    }

    @Override
    public void onNetDisconnected() {

    }

    @Override
    public void onNetConnected(NetworkType networkType) {

    }


}

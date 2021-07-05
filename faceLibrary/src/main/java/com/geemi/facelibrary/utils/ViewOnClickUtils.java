package com.geemi.facelibrary.utils;

import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 点击误差处理
 */
public class ViewOnClickUtils {
    private static final int MIN_DELAY_TIME = 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    /**
     * 判断是否快速点击
     * @return
     */
    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }else{
            ToastUtils.showShort("点那么快干嘛~~~");
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    /**
     * 判断是否快速点击
     * @param time 设置时间
     * @return
     */
    public static boolean isFastClick(int time) {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= time) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}

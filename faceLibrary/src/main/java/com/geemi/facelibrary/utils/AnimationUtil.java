package com.geemi.facelibrary.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;


public class AnimationUtil {

    /**
     * 开始播放模式选择动画
     *
     * @param view       目标View
     * @param isSelected 是否选择
     */
    public static void startModeSelectAnimation(View view, boolean isSelected) {
        RotateAnimation rotate;
        if (isSelected) {
            rotate = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            rotate = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        rotate.setFillAfter(true);
        rotate.setDuration(200);
        view.startAnimation(rotate);
    }

    /**
     * 弹簧尼阻动画效果
     * @param animationView 需要显示动画的view
     * @param stiffness  刚度(劲度/弹性)，刚度越大，形变产生的里也就越大，体现在效果上就是运动越快
     * @param dampingRatio 阻尼系数，系数越大，动画停止的越快。从理论上讲分为三种情况 Overdamped过阻尼（ζ > 1）、Critically damped临界阻尼(ζ = 1)、Underdamped欠阻尼状态(0<ζ <1)。
     */
    public static void springAnimation(View animationView, float stiffness, float dampingRatio){
        //尼阻弹簧动画
        SpringAnimation signUpBtnAnimY = new SpringAnimation(animationView, SpringAnimation.TRANSLATION_Y,0);
        signUpBtnAnimY.getSpring().setStiffness(stiffness);
        signUpBtnAnimY.getSpring().setDampingRatio(dampingRatio);
        signUpBtnAnimY.setStartVelocity(10000);
        signUpBtnAnimY.start();

    }

    /**
     * 背景毛玻璃动画
     * @param view
     * @param bitmap
     */
    public static void startSwitchBackgroundAnim(ImageView view, Bitmap bitmap) {
        Drawable oldDrawable = view.getDrawable();
        Drawable oldBitmapDrawable ;
        TransitionDrawable oldTransitionDrawable = null;
        if (oldDrawable instanceof TransitionDrawable) {
            oldTransitionDrawable = (TransitionDrawable) oldDrawable;
            oldBitmapDrawable = oldTransitionDrawable.findDrawableByLayerId(oldTransitionDrawable.getId(1));
        } else if (oldDrawable instanceof BitmapDrawable) {
            oldBitmapDrawable = oldDrawable;
        } else {
            oldBitmapDrawable = new ColorDrawable(0xffc2c2c2);
        }

        if (oldTransitionDrawable == null) {
            oldTransitionDrawable = new TransitionDrawable(new Drawable[]{oldBitmapDrawable, new BitmapDrawable(bitmap)});
            oldTransitionDrawable.setId(0, 0);
            oldTransitionDrawable.setId(1, 1);
            oldTransitionDrawable.setCrossFadeEnabled(true);
            view.setImageDrawable(oldTransitionDrawable);
        } else {
            oldTransitionDrawable.setDrawableByLayerId(oldTransitionDrawable.getId(0), oldBitmapDrawable);
            oldTransitionDrawable.setDrawableByLayerId(oldTransitionDrawable.getId(1), new BitmapDrawable(bitmap));
        }
        oldTransitionDrawable.startTransition(1000);
    }



    public enum AnimationState{
        STATE_SHOW,
        STATE_HIDDEN
    }
    /**
     * 渐隐渐现动画
     * @param view 需要实现动画的对象
     * @param state 需要实现的状态
     * @param duration 动画实现的时长（ms）
     */
    public static void showAndHiddenAnimation(final View view, AnimationState state, long duration){
        float start = 0f;
        float end = 0f;
        if(state == AnimationState.STATE_SHOW){
            end = 1f;
            view.setVisibility(View.VISIBLE);
        } else
        if(state == AnimationState.STATE_HIDDEN){
            start = 1f;
            view.setVisibility(View.INVISIBLE);
        }
        AlphaAnimation animation = new AlphaAnimation(start, end);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }
        });
        view.setAnimation(animation);
        animation.start();
    }

    /**
     * 旋转动画
     * @param context
     * @param anim
     * @param view
     * @return
     */
    public static void setAnimation(Context context, int anim, final View view){
        final Animation operatingAnim = AnimationUtils.loadAnimation(context, anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        view.startAnimation(operatingAnim);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                operatingAnim.cancel();
                view.clearAnimation();
            }
        }, 1000);
    }

///////////////////////////////////////////////////////////底部弹出动画相关start//////////////////////////////////////////////////////
    /**
     * 关闭动画
     */
    public static void startDownAnim(int time, int bottomlaydistance, int locationDistance, View llBottomLay, View bottomViewIcon) {
        try {
            getBottomLayAnim(time, bottomlaydistance,llBottomLay).start();
            getLocationAnim(time, locationDistance,bottomViewIcon).start();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    /**
     * 弹出动画
     */
    public static void startUpAnim(int downTime, int upTime, int ll_bottomlayHight, View llBottomLay, View bottomViewIcon) {
        //上下弹出动画
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator animatorDown = getBottomLayAnim(downTime, ll_bottomlayHight,llBottomLay);
        ObjectAnimator animatorUp = getBottomLayAnim(upTime, 0,llBottomLay);
        animSet.play(animatorUp).after(animatorDown);
        animSet.start();
        if (bottomViewIcon != null){
            startLocationDownUpAnim(downTime, upTime,ll_bottomlayHight,bottomViewIcon,llBottomLay);
        }
        //弹簧动画
        springAnimation(llBottomLay, SpringForce.STIFFNESS_VERY_LOW, SpringForce.DAMPING_RATIO_LOW_BOUNCY);
    }


    /**
     * 定位图标上下动画
     */
    private static void startLocationDownUpAnim(int downTime, int upTime, int ll_bottomlayHight, View overtBottomIcon, View llBottomLay) {
        int v_locationHight = -(ll_bottomlayHight -  MyUtil.dp2px( 110));//该数值只要大于实际控件高度隐藏就没问题
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator animatorDown = getLocationAnim(downTime, 0,overtBottomIcon);
        ObjectAnimator animatorUp = getLocationAnim(upTime, v_locationHight,overtBottomIcon);
        animSet.play(animatorUp).after(
                animatorDown);
        animSet.start();
    }


    /**
     * @描述 创建layout动画, 设置过程监听，设置动画时间
     */
    private static ObjectAnimator getBottomLayAnim(int time, int distance, View llBottomLay) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(llBottomLay, "TranslationY", distance);
        if (animator == null) {
            return null;
        }
        animator.setDuration(time);
        return animator;
    }

    /**
     * @描述 创建图标View动画,设置动画时间
     */
    private static ObjectAnimator getLocationAnim(int time, int distance, View overtBottomIcon) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(overtBottomIcon, "TranslationY", distance);
        if (animator == null) {
            return null;
        }
        animator.setDuration(time);
        return animator;
    }
    ///////////////////////////////////////////////////////////底部弹出动画相关end//////////////////////////////////////////////////////
}

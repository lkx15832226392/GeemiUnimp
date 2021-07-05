package com.geemi.facelibrary.utils;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.geemi.facelibrary.R;
import com.kongzue.dialog.v2.Notification;
import com.kongzue.dialog.v2.TipDialog;
import com.kongzue.dialog.v2.WaitDialog;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import static com.kongzue.dialog.v2.Notification.TYPE_NORMAL;
import static com.kongzue.dialog.v2.TipDialog.TYPE_ERROR;

public class DialogUtil {

    /**
     * 自定义等待加载
     * @param bounceLoadingView
     */
//    public static void loadingView(BounceLoadingView bounceLoadingView){
//        bounceLoadingView.addBitmap(R.mipmap.v04);
//        bounceLoadingView.addBitmap(R.mipmap.v05);
//        bounceLoadingView.addBitmap(R.mipmap.v06);
//        bounceLoadingView.addBitmap(R.mipmap.v07);
//        bounceLoadingView.addBitmap(R.mipmap.v08);
////        bounceLoadingView.addBitmap(R.mipmap.v09);
//        bounceLoadingView.setShadowColor(Color.LTGRAY);
//        bounceLoadingView.setDuration(700);
//        bounceLoadingView.start();
//    }

    /**
     * 加载中
     *
     * @param context
     */
    public static void showProgress(Context context) {
        WaitDialog.show(context, "载入中...");
    }

    /**
     * 自定义加载
     * @param context
     */
    public static void showViewProgress(Context context){
        View customView = LayoutInflater.from(context).inflate(R.layout.prodialog, null);
        Glide.with(context).load(R.drawable.base_loading_gif).into((ImageView) customView.findViewById(R.id.img_loading));
        WaitDialog.show(context, "",customView).setCanCancel(true);
//        loadingPopup = (LoadingPopupView) new XPopup.Builder(context)
//                .dismissOnBackPressed(false)
//                .asLoading("加载中")
//                .show();
//        loadingPopup.dismiss();
    }

    /**
     * 关闭加载
     */
    public static void dismiss(){
        WaitDialog.dismiss();
    }

    /**
     * 自定义文字加载中
     *
     * @param context
     * @param msg
     */
    public static void showTextProgress(Context context, String msg) {
        WaitDialog.show(context, msg);
    }

    /**
     * 加载关闭
     *
     * @param
     */
    public static void showhideProgress() {
        WaitDialog.dismiss();
    }

    /**
     * 加载成功
     *
     * @param context
     */
    public static void showSuccee(Context context, String msg) {
        TipDialog.show(context, msg, TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_FINISH);
    }

    /**
     * 警告提示
     *
     * @param context
     * @param msg
     */
    public static void showWarning(Context context, String msg) {
        TipDialog.show(context, msg, TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_WARNING);
    }

    /**
     * 错误提示
     *
     * @param context
     * @param msg
     */
    public static void showError(Context context, String msg) {
        TipDialog.show(context, msg, TipDialog.SHOW_TIME_LONG, TYPE_ERROR);
    }

    public static void Tshow(Context context, String msg){
        NotificationShow(context,msg,false);
    }
    public static void Tshow(Context context,String title, String msg,boolean isPlayer){
        NotificationShow(context,title,msg,isPlayer);
    }

    public static void Tshow(Context context, String msg,boolean isPlayer){
        NotificationShow(context,msg,isPlayer);
    }
    private static void NotificationShow(Context context, String msg, boolean isPlayer) {
        Notification.show(context, 1, R.mipmap.ic_launcher, context.getString(R.string.app_name), msg, Notification.SHOW_TIME_SHORT, TYPE_NORMAL);
        if (isPlayer){
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.selecter_car);
            mediaPlayer.start();
        }
    }

    private static void NotificationShow(Context context,String title,String msg, boolean isPlayer) {
        Notification.show(context, 1, R.mipmap.ic_launcher, title, msg, Notification.SHOW_TIME_SHORT, TYPE_NORMAL);
        if (isPlayer){
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.selecter_car);
            mediaPlayer.start();
        }
    }

    public static void NotificationShow(Context context, String msg, final OnMyClickListener onMyClickListener) {
        Notification.show(context, 2, R.mipmap.ic_launcher, context.getString(R.string.app_name), msg, Notification.SHOW_TIME_LONG, TYPE_NORMAL)
                .setOnNotificationClickListener(new Notification.OnNotificationClickListener() {
                    @Override
                    public void OnClick(int id) {
                        onMyClickListener.OnClick(id);
                    }
                });
    }


    public interface OnMyClickListener {
        void OnClick(int id);
    }
}

package com.geemi.facelibrary.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;

import androidx.annotation.Nullable;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.model.ARModel;
import com.geemi.facelibrary.model.DictItemsModel;
import com.geemi.facelibrary.utils.GeemiFileUtils;
import com.geemi.facelibrary.utils.UnZipUtils;
import com.geemi.facelibrary.utils.ViewOnClickUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.http.DownLoadManager;
import me.goldze.mvvmhabit.http.download.ProgressCallBack;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.ResponseBody;

public class ARDownAdapter extends BaseQuickAdapter<ARModel.DataDTO.RecordsDTO, BaseViewHolder> {
    RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.ic_launcher)//图片加载出来前，显示的图片
            .fallback(R.mipmap.ic_launcher) //url为空的时候,显示的图片
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.mipmap.ic_launcher);//图片加载失败后，显示的图片
    private String progresss = "下载";
    private ARModel.DataDTO.RecordsDTO item;
    public ARDownAdapter(int layoutResId) {
        super(layoutResId);
    }
    private SuperTextView superTextView;
    @SuppressLint("CheckResult")
    @Override
    protected void convert(BaseViewHolder helper, ARModel.DataDTO.RecordsDTO item) {
        this.item = item;
         superTextView = helper.getView(R.id.item_supText);
        superTextView.setLeftString(item.getTitle());
        superTextView.setLeftBottomString(item.getSizeStr());

        Glide.with(mContext).load(item.getThumbnail()).apply(options).into(superTextView.getLeftIconIV());

        if (GeemiFileUtils.fileIsExists(GeemiFileUtils.getFilePath(mContext, "ar") + "/" +item.getFileName() + ".zip")) {
            System.out.println("======================path:"+GeemiFileUtils.getFilePath(mContext, "ar") + "/" +item.getFileName() + ".zip");
            superTextView.setRightString("已下载");
        } else {
            superTextView.setRightString("下载");
            superTextView.setRightTvClickListener(() -> {
                if (ViewOnClickUtils.isFastClick()){
                    return;
                }
                downLoadARData();
            });
        }
    }

    private void downLoadARData() {
//        String destFileDir = mContext.getCacheDir().getPath();  //文件存放的路径

        String destFileName = GeemiFileUtils.getFilePath(mContext, "ar") + "/" +
                item.getFileName() + ".zip";//文件存放的名称
        KLog.i("destFileName===="+destFileName+"========="+item.getZipUrl());
        FileDownloader.getImpl().create(item.getZipUrl())
                .setPath(destFileName)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        superTextView.setRightString(soFarBytes/1024/1024+"MB / "+totalBytes/1024/1024+"MB");
                        KLog.i("======下载中===="+soFarBytes+"===="+totalBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        ToastUtils.showLong("下载完成");
                        new Thread(() -> {
                            try {
                                UnZipUtils.UnZipFolder(GeemiFileUtils.getFilePath(mContext, "ar") + "/" + item.getFileName() + ".zip", GeemiFileUtils.getFilePath(mContext, "ar"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        ToastUtils.showLong("下载失败，"+e.getMessage());
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }




}

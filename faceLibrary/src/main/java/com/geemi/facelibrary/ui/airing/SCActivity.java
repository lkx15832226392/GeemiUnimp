package com.geemi.facelibrary.ui.airing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.geemi.facelibrary.R;
import com.geemi.facelibrary.adapter.ScAdapter;
import com.geemi.facelibrary.databinding.ActivitySCBinding;
import com.geemi.facelibrary.http.GeemiHttpImpl;
import com.geemi.facelibrary.manager.GeemiFaceManager;
import com.geemi.facelibrary.model.ScListBean;
import com.geemi.facelibrary.router.RouterUrl;
import com.geemi.facelibrary.ui.base.GeemiBaseActivity;
import com.geemi.facelibrary.utils.JsonUtils;
import com.geemi.facelibrary.utils.SipClientDemo;
import com.geemi.facelibrary.utils.ViewOnClickUtils;
import com.geemi.facelibrary.utils.shared.SharedPreferencesUtil;
import com.github.faucamp.simplertmp.RtmpHandler;
import com.gyf.immersionbar.ImmersionBar;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import net.ossrs.yasea.SrsCameraView;
import net.ossrs.yasea.SrsEncodeHandler;
import net.ossrs.yasea.SrsPublisher;
import net.ossrs.yasea.SrsRecordHandler;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static com.geemi.facelibrary.model.Const.KEY_INTENT_PROJECT;


public class SCActivity extends GeemiBaseActivity implements RtmpHandler.RtmpListener,
        SrsRecordHandler.SrsRecordListener, SrsEncodeHandler.SrsEncodeListener{
    private ActivitySCBinding activitySCBinding;
    private static final String TAG = "Yasea";
    public final static int RC_CAMERA = 100;
//    private String rtmpUrl = "rtmp://101.201.196.155:9785/live/666777";
//    private String recPath = Environment.getExternalStorageDirectory().getPath() + "/test.mp4";
    private SrsPublisher mPublisher;
    private boolean isPermissionGranted = false;
    private ScAdapter scAdapter;
    private Map<Integer, Boolean> statues = new HashMap<>();
    private SipClientDemo sipClientDemo;
    private String url;
    private ScListBean scListBean;
    private String projectId;

    private int mWidth = 640;
    private int mHeight = 480;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySCBinding = DataBindingUtil.setContentView(this, R.layout.activity_s_c);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.face_bj)
                .init();
        if (getIntent().getStringExtra(KEY_INTENT_PROJECT) == null) {
            ToastUtils.showLong("项目ID获取失败请重试！");
            finish();
            return;
        }
        projectId = getIntent().getStringExtra(KEY_INTENT_PROJECT);
        KLog.i("projectId===="+projectId);

        initPublisher();
        requestPermission();
        initLinearView();
    }

    @SuppressLint("NewApi")
    private void initLinearView() {
        activitySCBinding.textFaceTitle.setLeftImageViewClickListener(imageView -> {
           if (ViewOnClickUtils.isFastClick()){
               return;
           }
            finish();
        });

        activitySCBinding.btnStart.setOnClickListener(v -> {
            if (ViewOnClickUtils.isFastClick()) {
                return;
            }
            if (TextUtils.equals("开始广播", activitySCBinding.btnStart.getText().toString().trim())) {
                boolean isSelect = false;
                for (Map.Entry<Integer, Boolean> integerBooleanEntry : statues.entrySet()) {
                    if (integerBooleanEntry.getValue()) {
                        isSelect = true;
                    }
                }
                if (isSelect) {
                    publish();
                } else {
                    ToastUtils.showShort("没有选择广播设备");
                }
            } else {
                mPublisher.stopPublish();
                mPublisher.stopRecord();
                scListBean.getResult().forEach(n ->{
                    new Thread(() -> {
                        sipClientDemo.stop(n.getStn());
                    }).start();
                });
                activitySCBinding.btnStart.setText("开始广播");
            }
        });
    }

    public void getRadio() {
        Map<String, String> params = new HashMap<>();
        params.put("projectId", projectId);
        GeemiFaceManager.getHttpModel().getData(1008, GeemiHttpImpl.Method_POST, RouterUrl.GETRADIO, params, this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onComplete(int tag, String json) {
        KLog.i("tag===" + tag + "=====json====" + json);
        scListBean = JsonUtils.getArrJson(json, ScListBean.class);
        if (scListBean.getResult().size() == 0 && scListBean.getResult() == null){
            ToastUtils.showLong("数据获取失败，请重试！");
            finish();
            return;
        }
        initView();
        scListBean.getResult().forEach(n ->{
            new Thread(() -> {
                sipClientDemo.changeVolume(n.getStn(), 50);
            }).start();
        });
    }


    private void initView() {
        sipClientDemo = new SipClientDemo();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        activitySCBinding.rvSc.addItemDecoration(new RecycleGridDivider(30, R.color.transparent));
        activitySCBinding.rvSc.setLayoutManager(layoutManager);
        scAdapter = new ScAdapter(R.layout.item_sc_new);
        //默认提供5种方法（渐显ALPHAIN 、缩放SCALEIN 、从下到上SLIDEIN_BOTTOM ，从左到右SLIDEIN_LEFT 、从右到左SLIDEIN_RIGHT ）
        scAdapter.setNotDoAnimationCount(1);
//        functionListAdapter.openLoadAnimation();
        scAdapter.setNewData(scListBean.getResult());
        activitySCBinding.rvSc.setAdapter(scAdapter);
        scAdapter.setVolListener((item, vol) -> new Thread(() -> {
            sipClientDemo.changeVolume(item.getStn(), vol);
            SharedPreferencesUtil.putInt(this, item.getStn(), vol);
        }).start());
        scAdapter.setCheckListener((pos, isChecked) -> statues.put(pos, isChecked));
        scAdapter.setOnItemClickListener((adapter, view, position) -> {
            CheckBox cb = view.findViewById(R.id.cb_sc);
            KLog.e("==== " + !cb.isChecked());
            cb.setChecked(!cb.isChecked());
        });
        activitySCBinding.twinklingRefreshLayout.setEnableLoadmore(false);
        activitySCBinding.twinklingRefreshLayout.setEnableOverScroll(false);
        activitySCBinding.twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                getRadio();
                activitySCBinding.twinklingRefreshLayout.finishRefreshing();
            }

            @Override
            public void onFinishRefresh() {
                super.onFinishRefresh();
            }
        });

    }

    private void initPublisher() {
        mPublisher = new SrsPublisher(activitySCBinding.srcameraView);
        mPublisher.setEncodeHandler(new SrsEncodeHandler(this));
        mPublisher.setRtmpHandler(new RtmpHandler(this));
        mPublisher.setRecordHandler(new SrsRecordHandler(this));
        mPublisher.setPreviewResolution(mWidth, mHeight);
        mPublisher.setOutputResolution(mHeight, mWidth); // 这里要和preview反过来
        mPublisher.setVideoHDMode();
        mPublisher.setSendAudioOnly(true);
//        mPublisher.startCamera();
        activitySCBinding.srcameraView.setCameraCallbacksHandler(new SrsCameraView.CameraCallbacksHandler() {
            @Override
            public void onCameraParameters(Camera.Parameters params) {
            }
        });
    }


    private void requestPermission() {
        //1. 检查是否已经有该权限
        if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)) {
            //2. 权限没有开启，请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_CAMERA);
        } else {
            //权限已经开启，做相应事情
            isPermissionGranted = true;
            activitySCBinding.twinklingRefreshLayout.startRefresh();

        }
    }

    //3. 接收申请成功或者失败回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限被用户同意,做相应的事情
                isPermissionGranted = true;
                getRadio();
            } else {
                //权限被用户拒绝，做相应的事情
                finish();
            }
        }
    }


    private void publish() {
        url = "rtmp://39.107.190.158:9785/hls/" + projectId;
        KLog.i("url====="+url);
        mPublisher.startPublish(url);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPublisher.getCamera() == null && isPermissionGranted) {
            //if the camera was busy and available again
//            mPublisher.startCamera();
            mPublisher.startAudio();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPublisher.resumeRecord();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPublisher != null) {
            mPublisher.pauseRecord();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPublisher.stopPublish();
        mPublisher.stopRecord();
    }

    private static String getRandomAlphaString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private static String getRandomAlphaDigitString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private void handleException(Exception e) {
        try {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            mPublisher.stopPublish();
            mPublisher.stopRecord();

        } catch (Exception e1) {
        }
    }

    @Override
    public void onRtmpConnecting(String msg) {
        activitySCBinding.btnStart.setText("连接中请稍后。。");
    }

    @Override
    public void onRtmpConnected(String msg) {
        Toast.makeText(getApplicationContext(), "开始广播", Toast.LENGTH_SHORT).show();
        for (Map.Entry<Integer, Boolean> integerBooleanEntry : statues.entrySet()) {
            if (integerBooleanEntry.getValue()) {
                new Thread(() -> {
                    sipClientDemo.play(scListBean.getResult().get(integerBooleanEntry.getKey()).getStn(), url);
                }).start();
            }
        }
        activitySCBinding.btnStart.setText("停止广播");
    }

    @Override
    public void onRtmpVideoStreaming() {
    }

    @Override
    public void onRtmpAudioStreaming() {
    }

    @Override
    public void onRtmpStopped() {
//        Toast.makeText(getApplicationContext(), "Stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRtmpDisconnected() {
        Toast.makeText(getApplicationContext(), "关闭广播", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRtmpVideoFpsChanged(double fps) {
        Log.i(TAG, String.format("Output Fps: %f", fps));
    }

    @Override
    public void onRtmpVideoBitrateChanged(double bitrate) {
        int rate = (int) bitrate;
        if (rate / 1000 > 0) {
            Log.i(TAG, String.format("Video bitrate: %f kbps", bitrate / 1000));
        } else {
            Log.i(TAG, String.format("Video bitrate: %d bps", rate));
        }
    }

    @Override
    public void onRtmpAudioBitrateChanged(double bitrate) {
        int rate = (int) bitrate;
        if (rate / 1000 > 0) {
            Log.i(TAG, String.format("Audio bitrate: %f kbps", bitrate / 1000));
        } else {
            Log.i(TAG, String.format("Audio bitrate: %d bps", rate));
        }
    }

    @Override
    public void onRtmpSocketException(SocketException e) {
        handleException(e);
    }

    @Override
    public void onRtmpIOException(IOException e) {
        handleException(e);
    }

    @Override
    public void onRtmpIllegalArgumentException(IllegalArgumentException e) {
        handleException(e);
    }

    @Override
    public void onRtmpIllegalStateException(IllegalStateException e) {
        handleException(e);
    }

    // Implementation of SrsRecordHandler.

    @Override
    public void onRecordPause() {
        Toast.makeText(getApplicationContext(), "Record paused", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordResume() {
        Toast.makeText(getApplicationContext(), "Record resumed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordStarted(String msg) {
        Toast.makeText(getApplicationContext(), "Recording file: " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordFinished(String msg) {
        Toast.makeText(getApplicationContext(), "MP4 file saved: " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordIOException(IOException e) {
        handleException(e);
    }

    @Override
    public void onRecordIllegalArgumentException(IllegalArgumentException e) {
        handleException(e);
    }

    // Implementation of SrsEncodeHandler.

    @Override
    public void onNetworkWeak() {
        Toast.makeText(getApplicationContext(), "Network weak", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkResume() {
        Toast.makeText(getApplicationContext(), "Network resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEncodeIllegalArgumentException(IllegalArgumentException e) {
        handleException(e);
    }

}
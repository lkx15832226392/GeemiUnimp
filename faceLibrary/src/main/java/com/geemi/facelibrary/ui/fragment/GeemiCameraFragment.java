//package com.geemi.facelibrary.ui.fragment;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.graphics.Rect;
//import android.hardware.Camera;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//
//import com.alibaba.android.arouter.facade.annotation.Route;
//import com.geemi.facelibrary.R;
//import com.geemi.facelibrary.databinding.FragmentCameraBinding;
//import com.geemi.facelibrary.router.RouterFragmentPath;
//import com.geemi.facelibrary.ui.base.GeemiBaseFragment;
//import com.geemi.facelibrary.ui.scan.ScanCameraManager;
//import com.geemi.facelibrary.ui.scan.ScanViewfinderView;
//import com.geemi.facelibrary.utils.MyUtil;
//import com.gyf.immersionbar.ImmersionBar;
//
//import me.goldze.mvvmhabit.BR;
//import me.goldze.mvvmhabit.utils.KLog;
//
//@Route(path = RouterFragmentPath.GeemiRouterPath.PAGER_SIMPLECAMERA)
//public class GeemiCameraFragment extends GeemiBaseFragment<FragmentCameraBinding,GeemiCameraViewModel> {
//    private SurfaceHolder surfaceHolder;
//    private ScanCameraManager cameraManager;
//    private Camera mCamera;
//    private boolean hasSurface;
//
//    @Override
//    protected void initView() {
//        //设置共同沉浸式样式
//        ImmersionBar.with(this).navigationBarColor(R.color.blue_sharder)
//                .statusBarColor(R.color.blue_sharder)
//                .init();
//        baseActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
//        Window window = baseActivity.getWindow();
//
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        // 1. 初始化lib库
//        LibraryInitOCR.initOCR(baseActivity);
//        // 2. 初始化解码器
//        LibraryInitOCR.initDecode(baseActivity, handler, true);
//        //请求相机权限, 实际开中, 请先申请了权限再转跳到扫描界面.
//        surfaceHolder = binding.cameraSv.getHolder();
//        //设置共同沉浸式样式
//        ImmersionBar.with(this).navigationBarColor(R.color.blue_sharder)
//                .statusBarColor(R.color.blue_sharder)
//                .init();
//    }
//
//    @Override
//    protected void initLinearView() {
//        binding.btCancel.setOnClickListener(v -> {
//            baseActivity.finish();
//        });
//    }
//
//    @Override
//    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return R.layout.fragment_camera;
//    }
//
//    @Override
//    public int initVariableId() {
//        return BR.viewModule;
//    }
//
//    @Override
//    public void initData() {
//        super.initData();
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        cameraManager = new ScanCameraManager();
//
//        if (hasSurface) {
//            // activity在paused时但不会stopped,因此surface仍旧存在；
//            // surfaceCreated()不会调用，因此在这里初始化camera
//            initCamera(surfaceHolder);
//        } else {
//            // 重置callback，等待surfaceCreated()来初始化camera
//            surfaceHolder.addCallback(surfaceHolderCallback);
//        }
//    }
//
//
//    private void initCamera(SurfaceHolder surfaceHolder) {
//        if (surfaceHolder == null) {
//            throw new IllegalStateException("No SurfaceHolder provided");
//        }
//
//        if (cameraManager.isOpen()) {
//            return;
//        }
//
//        try {
//            // 打开Camera硬件设备
//            cameraManager.openDriver(surfaceHolder, baseActivity);
//            // 创建一个handler来打开预览，并抛出一个运行时异常
//            cameraManager.startPreview(previewCallback);
//
//            Camera camera = cameraManager.getCamera();
//            Camera.Size size = camera.getParameters().getPreviewSize();
//            binding.cameraFinderView.initFinder(size.width, size.height, handler);
//        } catch (Exception ioe) {
//            Log.d("zk", ioe.toString());
//        }
//    }
//
//    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
//        public void onPreviewFrame(final byte[] data, final Camera camera) {
//
//            Camera.Parameters parameters = camera.getParameters();
//            //Rect rect  =  new Rect(368, 144, 1549, 936);
//
//            Rect rect  =  binding.cameraFinderView.getViewfinder(camera);
//
//            //3. 传递相机数据,请求解码
//            LibraryInitOCR.decode(rect, parameters.getPreviewSize().width, parameters.getPreviewSize().height, data);
//        }
//    };
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        cameraManager.stopPreview();
//        cameraManager.closeDriver();
//        if (!hasSurface) {
//            surfaceHolder.removeCallback(surfaceHolderCallback);
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (cameraManager != null){
//            cameraManager.closeDriver();
//        }
//    }
//
//    @SuppressLint("HandlerLeak")
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            //4. 接收解码回调
//            Log.i("ocr", "Handler: " + msg.what);
//            if (msg.what == LibraryInitOCR.DECODE_SUCCESS) {
//                Log.i("ocr", "成功: " + msg.obj.toString());
//                Intent result = (Intent) msg.obj;
//                String ocrReulst = result.getStringExtra("OCRResult");
//                KLog.i("ocrReulst====="+ocrReulst);
//                MyUtil.setEventBusData(8001, ocrReulst);
//                baseActivity.onBackPressed();
////                Toast.makeText(context, "解析成功: " + ocrReulst, Toast.LENGTH_LONG).show();
//            }
//        }
//    };
//
//
//    private SurfaceHolder.Callback surfaceHolderCallback = new SurfaceHolder.Callback() {
//        @Override
//        public void surfaceCreated(SurfaceHolder holder) {
//            if (!hasSurface) {
//                hasSurface = true;
//                initCamera(holder);
//            }
//        }
//
//        @Override
//        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//        }
//
//        @Override
//        public void surfaceDestroyed(SurfaceHolder holder) {
//            hasSurface = false;
//        }
//    };
//
//}

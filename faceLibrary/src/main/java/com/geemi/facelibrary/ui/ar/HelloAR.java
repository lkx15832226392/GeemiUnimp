package com.geemi.facelibrary.ui.ar;//================================================================================================================================
//
// Copyright (c) 2015-2020 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
// EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
// and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================


import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.geemi.facelibrary.utils.GeemiFileUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import cn.easyar.Buffer;
import cn.easyar.CameraDevice;
import cn.easyar.CameraDeviceFocusMode;
import cn.easyar.CameraDevicePreference;
import cn.easyar.CameraDeviceSelector;
import cn.easyar.CameraDeviceType;
import cn.easyar.CameraParameters;
import cn.easyar.DelayedCallbackScheduler;
import cn.easyar.FeedbackFrameFork;
import cn.easyar.FrameFilterResult;
import cn.easyar.FunctorOfVoidFromTargetAndBool;
import cn.easyar.Image;
import cn.easyar.ImageTarget;
import cn.easyar.ImageTracker;
import cn.easyar.ImageTrackerResult;
import cn.easyar.InputFrame;
import cn.easyar.InputFrameFork;
import cn.easyar.InputFrameThrottler;
import cn.easyar.InputFrameToFeedbackFrameAdapter;
import cn.easyar.InputFrameToOutputFrameAdapter;
import cn.easyar.Matrix44F;
import cn.easyar.OutputFrame;
import cn.easyar.OutputFrameBuffer;
import cn.easyar.OutputFrameFork;
import cn.easyar.OutputFrameJoin;
import cn.easyar.StorageType;
import cn.easyar.Target;
import cn.easyar.TargetInstance;
import cn.easyar.TargetStatus;
import cn.easyar.Vec2F;
import cn.easyar.Vec2I;

public class HelloAR {
    private DelayedCallbackScheduler scheduler;
    private CameraDevice camera;
    private ArrayList<ImageTracker> trackers;
    private BGRenderer bgRenderer;
    private ArrayList<VideoRenderer> video_renderers;
    private VideoRenderer current_video_renderer;
    private int tracked_target = 0;
    private int active_target = 0;
    private ARVideo video = null;

    private InputFrameThrottler throttler;
    private FeedbackFrameFork feedbackFrameFork;
    private InputFrameToOutputFrameAdapter i2OAdapter;
    private InputFrameFork inputFrameFork;
    private OutputFrameJoin join;
    private OutputFrameBuffer oFrameBuffer;
    private InputFrameToFeedbackFrameAdapter i2FAdapter;
    private OutputFrameFork outputFrameFork;
    private int previousInputFrameIndex = -1;
    private byte[] imageBytes = null;
    private Context mContext;
    private List<String> fileList = new ArrayList<>();
    private List<ARBean> Ars = new ArrayList<>();

    public HelloAR(Context mContext) {
        scheduler = new DelayedCallbackScheduler();
        trackers = new ArrayList<ImageTracker>();
        this.mContext = mContext;
//        try {
//            UnZipUtils.UnZipFolder(GeemiFileUtils.getFilePath(mContext, "ar") + "/arg.zip", GeemiFileUtils.getFilePath(mContext, "ar"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        fileList = GeemiFileUtils.getAllFiles(GeemiFileUtils.getFilePath(mContext, "ar"));
        Ars = GeemiFileUtils.getAllFiles(GeemiFileUtils.getFilePath(mContext, "ar"));


//        L.e("FILE  LIST  SIZE  === " + Ars.toString());
    }

    private void loadFromImage(ImageTracker tracker, String path, String name, int storageType) {
        ImageTarget target = ImageTarget.createFromImageFile(path, storageType, name, "", "", 1.0f);
        if (target == null) {
            Log.e("HelloAR", "target create failed or key is not correct");
            return;
        }
        tracker.loadTarget(target, scheduler, new FunctorOfVoidFromTargetAndBool() {
            @Override
            public void invoke(Target target, boolean status) {
                Log.i("HelloAR", String.format("load target (%b): %s (%d)", status, target.name(), target.runtimeID()));
            }
        });
        target.dispose();
    }

    public void recreate_context() {
        if (active_target != 0) {
            video.onLost();
            video.dispose();
            video = null;
            tracked_target = 0;
            active_target = 0;
        }
        if (bgRenderer != null) {
            bgRenderer.dispose();
            bgRenderer = null;
        }
        if (video_renderers != null) {
            for (VideoRenderer video_renderer : video_renderers) {
                video_renderer.dispose();
            }
            video_renderers = null;
        }
        current_video_renderer = null;
        previousInputFrameIndex = -1;
        bgRenderer = new BGRenderer();
        video_renderers = new ArrayList<VideoRenderer>();
        for (int k = 0; k < Ars.size(); k += 1) {
            VideoRenderer video_renderer = new VideoRenderer();
            video_renderers.add(video_renderer);
        }
    }

    public void initialize() {
        recreate_context();

        camera = CameraDeviceSelector.createCameraDevice(CameraDevicePreference.PreferObjectSensing);
        throttler = InputFrameThrottler.create();
        inputFrameFork = InputFrameFork.create(2);
        join = OutputFrameJoin.create(2);
        oFrameBuffer = OutputFrameBuffer.create();
        i2OAdapter = InputFrameToOutputFrameAdapter.create();
        i2FAdapter = InputFrameToFeedbackFrameAdapter.create();
        outputFrameFork = OutputFrameFork.create(2);

        boolean status = true;
        status &= camera.openWithPreferredType(CameraDeviceType.Back);
        camera.setSize(new Vec2I(1280, 960));
        camera.setFocusMode(CameraDeviceFocusMode.Continousauto);
        if (!status) {
            return;
        }
        ImageTracker tracker = ImageTracker.create();
//        loadFromImage(tracker, Environment.getExternalStorageDirectory().getAbsolutePath()
//                + File.separator + "", "argame", StorageType.Absolute);

//        for (int i = 0; i < fileList.size(); i++) {
//            loadFromImage(tracker, GeemiFileUtils.getFilePath(mContext, "ar")
//                    + "/" + fileList.get(i) + ".jpg", fileList.get(i), StorageType.Absolute);
//
//            L.e("TAGER ===== " + GeemiFileUtils.getFilePath(mContext, "ar")
//                    + "/" + fileList.get(i) + ".jpg"  +   " /////// " +  fileList.get(i));
//        }

//        for (int i = 0; i < jsonArray.length(); i++) {
//            loadFromImage(tracker, jsonArray.get(i), fileList.get(i), StorageType.Absolute);
//
//            L.e("TAGER ===== " + GeemiFileUtils.getFilePath(mContext, "ar")
//                    + "/" + fileList.get(i) + ".jpg"  +   " /////// " +  fileList.get(i));
//        }

        for (ARBean ar : Ars) {
            loadFromImage(tracker,ar.getPath(),ar.getName(),StorageType.Absolute);
        }
        trackers.add(tracker);
        feedbackFrameFork = FeedbackFrameFork.create(trackers.size());

        camera.inputFrameSource().connect(throttler.input());
        throttler.output().connect(inputFrameFork.input());
        inputFrameFork.output(0).connect(i2OAdapter.input());
        i2OAdapter.output().connect(join.input(0));

        inputFrameFork.output(1).connect(i2FAdapter.input());
        i2FAdapter.output().connect(feedbackFrameFork.input());
        int k = 0;
        int trackerBufferRequirement = 0;
        for (ImageTracker _tracker : trackers) {
            feedbackFrameFork.output(k).connect(_tracker.feedbackFrameSink());
            _tracker.outputFrameSource().connect(join.input(k + 1));
            trackerBufferRequirement += _tracker.bufferRequirement();
            k++;
        }

        join.output().connect(outputFrameFork.input());
        outputFrameFork.output(0).connect(oFrameBuffer.input());
        outputFrameFork.output(1).connect(i2FAdapter.sideInput());
        oFrameBuffer.signalOutput().connect(throttler.signalInput());

        //CameraDevice and rendering each require an additional buffer
        camera.setBufferCapacity(throttler.bufferRequirement() + i2FAdapter.bufferRequirement() + oFrameBuffer.bufferRequirement() + trackerBufferRequirement + 2);
    }

    public void dispose() {
        if (video != null) {
            video.dispose();
            video = null;
        }
        tracked_target = 0;
        active_target = 0;

        for (ImageTracker tracker : trackers) {
            tracker.dispose();
        }
        trackers.clear();
        if (video_renderers != null) {
            for (VideoRenderer video_renderer : video_renderers) {
                video_renderer.dispose();
            }
            video_renderers = null;
        }
        current_video_renderer = null;
        if (bgRenderer != null) {
            bgRenderer = null;
        }
        if (camera != null) {
            camera.dispose();
            camera = null;
        }
        if (scheduler != null) {
            scheduler.dispose();
            scheduler = null;
        }
    }

    public boolean start() {
        boolean status = true;
        if (camera != null) {
            status &= camera.start();
        } else {
            status = false;
        }
        for (ImageTracker tracker : trackers) {
            status &= tracker.start();
        }
        return status;
    }

    public void stop() {
        if (camera != null) {
            camera.stop();
        }
        for (ImageTracker tracker : trackers) {
            tracker.stop();
        }
    }

    public void render(int width, int height, int screenRotation) {
        while (scheduler.runOne()) {
        }

        GLES20.glViewport(0, 0, width, height);
        GLES20.glClearColor(0.f, 0.f, 0.f, 1.f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        OutputFrame oframe = oFrameBuffer.peek();
        if (oframe == null) {
            return;
        }
        InputFrame iframe = oframe.inputFrame();
        if (iframe == null) {
            oframe.dispose();
            return;
        }
        CameraParameters cameraParameters = iframe.cameraParameters();
        if (cameraParameters == null) {
            oframe.dispose();
            iframe.dispose();
            return;
        }
        float viewport_aspect_ratio = (float) width / (float) height;
        Matrix44F imageProjection = cameraParameters.imageProjection(viewport_aspect_ratio, screenRotation, true, false);
        Image image = iframe.image();

        try {
            if (iframe.index() != previousInputFrameIndex) {
                Buffer buffer = image.buffer();
                try {
                    if ((imageBytes == null) || (imageBytes.length != buffer.size())) {
                        imageBytes = new byte[buffer.size()];
                    }
                    buffer.copyToByteArray(imageBytes);
                    bgRenderer.upload(image.format(), image.width(), image.height(), ByteBuffer.wrap(imageBytes));
                } finally {
                    buffer.dispose();
                }
                previousInputFrameIndex = iframe.index();
            }
            bgRenderer.render(imageProjection);

            Matrix44F projectionMatrix = cameraParameters.projection(0.01f, 1000.f, viewport_aspect_ratio, screenRotation, true, false);
            for (FrameFilterResult oResult : oframe.results()) {
                if (oResult instanceof ImageTrackerResult) {
                    ImageTrackerResult result = (ImageTrackerResult) oResult;
                    ArrayList<TargetInstance> targetInstances = result.targetInstances();
                    for (TargetInstance targetInstance : targetInstances) {
                        if (targetInstance.status() == TargetStatus.Tracked) {
                            Target target = targetInstance.target();
                            int id = target.runtimeID();
                            if (active_target != 0 && active_target != id) {
                                video.onLost();
                                video.dispose();
                                video = null;
                                tracked_target = 0;
                                active_target = 0;
                            }
                            if (tracked_target == 0) {
                                if (video == null && video_renderers.size() > 0) {
                                    String target_name = target.name();
                                    for (int i = 0; i < Ars.size(); i++) {
                                        if (target_name.equals(Ars.get(i).getName()) && video_renderers.get(i).texId() != 0) {
                                            video = new ARVideo();
                                            String imagePath = Ars.get(i).getPath();
                                            String videoPath = imagePath.substring(0,imagePath.length() - 4) + ".mp4";
                                            video.openVideoFile(videoPath, video_renderers.get(i).texId(), scheduler);
                                            current_video_renderer = video_renderers.get(i);
                                        }
                                    }
//                                    if (target_name.equals("video") && video_renderers.get(0).texId() != 0) {
//
//                                    } else if (target_name.equals("namecard") && video_renderers.get(1).texId() != 0) {
//                                        video = new ARVideo();
//                                        video.openTransparentVideoFile("transparentvideo.mp4", video_renderers.get(1).texId(), scheduler);
//                                        current_video_renderer = video_renderers.get(1);
//                                    } else if (target_name.equals("idback") && video_renderers.get(2).texId() != 0) {
//                                        video = new ARVideo();
////                                        video.openStreamingVideo("https://sightpvideo-cdn.sightp.com/sdkvideo/EasyARSDKShow201520.mp4", video_renderers.get(2).texId(), scheduler);
//                                        video.openStreamingVideo("https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218114723HDu3hhxqIT.mp4", video_renderers.get(2).texId(), scheduler);
//                                        current_video_renderer = video_renderers.get(2);
//                                    }
                                }
                                if (video != null) {
                                    video.onFound();
                                    tracked_target = id;
                                    active_target = id;
                                }
                            }
                            ImageTarget imagetarget = target instanceof ImageTarget ? (ImageTarget) (target) : null;
                            if (imagetarget != null) {
                                Vec2F scale = new Vec2F(imagetarget.scale(), imagetarget.scale() / imagetarget.aspectRatio());
                                if (current_video_renderer != null) {
                                    video.update();
                                    if (video.isRenderTextureAvailable()) {
                                        current_video_renderer.render(projectionMatrix, targetInstance.pose(), scale);
                                    }
                                }
                            }
                            target.dispose();
                        }
                        targetInstance.dispose();
                    }
                    if (targetInstances.size() == 0) {
                        if (tracked_target != 0) {
                            video.onLost();
                            tracked_target = 0;
                        }
                    }
                }
                if (oResult != null) {
                    oResult.dispose();
                }
            }
        } finally {
            iframe.dispose();
            oframe.dispose();
            if (cameraParameters != null) {
                cameraParameters.dispose();
            }
            image.dispose();
        }
    }


}

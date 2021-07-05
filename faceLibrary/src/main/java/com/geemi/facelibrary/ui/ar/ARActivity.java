//================================================================================================================================
//
// Copyright (c) 2015-2020 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
// EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
// and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================

package com.geemi.facelibrary.ui.ar;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.model.Const;
import com.geemi.facelibrary.ui.base.GeemiBaseActivity;
import com.geemi.facelibrary.utils.MyIntentUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import cn.easyar.CameraDevice;
import cn.easyar.Engine;
import cn.easyar.ImageTracker;
import cn.easyar.VideoPlayer;
import me.goldze.mvvmhabit.utils.KLog;

import static com.geemi.facelibrary.model.Const.KEY_INTENT_PROJECT;


public class ARActivity extends GeemiBaseActivity {
    /*
     * Steps to create the key for this sample:
     *  1. login www.easyar.com
     *  2. create app with
     *      Name: HelloARVideo
     *      Package Name: cn.easyar.samples.helloarvideo
     *  3. find the created item in the list and show key
     *  4. set key string bellow
     */

    private static String key = "Tv8CRErsGlhSis9LMiHeZkl54yQ3jw6hZtIqP37NNG9K3TJyftAlPzGcaSg7h2AoP4thXXrPf35k03MxKdMwbn/bI1Zuxxh5KYRgMSnSOH5u0CJ4eJxrRnCcM2hl2j14QtoiPzHlDDEpyDBvYt8/aXica0Yp3T5wZss/dH/Hc0AnnCFxaso3cnnTIj8x5XNqYtA1cnzNczEp0zB+KeN9P2bRNWhn2yI/MeVzbm7QIngl9zx8bNsFb2rdOnRl2XMxKc00c3jbf15n0SR5WdsycmzQOGli0T8/J5wieGXNNDNZ2zJyedo4c2ycfT942z9ubpAef2HbMmlfzDB+YNc/eimSc25u0CJ4Je0kb23fMnhfzDB+YNc/eimSc25u0CJ4Je0hfHnNNE573yV0atIcfHucfT942z9ubpAccn/XPnNfzDB+YNc/eimSc25u0CJ4Jfo0c3jbAm1qyjh8Z/MwbSmSc25u0CJ4Jf0QWV/MMH5g1z96KeN9P27GIXR52wV0ZtsCaWrTIT8x0CRxZ5JzdHjyPn5q0nMnbd89bm7DfWYp3CRzb9I0VG/NcydQnDJyZpA2eG7TODNs2zRwYtgwfm7Nc0AnnCd8edcwc3/NcydQnDJyZtMkc2LKKD9WknNtZ98le2TMPG4phAo/atA1b2TXNT9WknNwZNokcW7NcydQnCJ4Zc00M0LTMHpu6iN8aNU4c2ycfT942z9ubpAScWTLNU9u3T56ZdcldGTQczEpzTRzeNt/T27dPm9v1z96KZJzbm7QIngl8TN3bt0lSXnfMnZi0DY/J5wieGXNNDNYyyN7at00SXnfMnZi0DY/J5wieGXNNDNYzjBveNsCbWrKOHxn8zBtKZJzbm7QIngl8z5pYtE/SXnfMnZi0DY/J5wieGXNNDNP2z9ubu0hfH/XMHFG3yE/J5wieGXNNDNI/xVJed8ydmLQNj9WknN4c844b27qOHBu7SV8Zs5zJ2XLPXEnnDhuR9EyfGeca3tq0iJ4dpIqP2nLP3ln2xh5eJxrRimcDDEpyDBvYt8/aXica0Yp3T5wZss/dH/Hc0AnnCFxaso3cnnTIj8x5XN0ZM1zQCecPHJvyz14eJxrRinNNHN4239UZt82eF/MMH5g1z96KZJzbm7QIngl/T1yftoDeGjRNnNiyjhyZZx9P3jbP25ukAN4aNEjeWLQNj8nnCJ4Zc00M0TcO3hoygVvat06dGXZczEpzTRzeNt/Tn7MN3xo2wVvat06dGXZczEpzTRzeNt/TnvfI25u7SF8f9cwcUbfIT8nnCJ4Zc00M0bRJXRk0AVvat06dGXZczEpzTRzeNt/WW7QInhYzjBpYt89UGrOczEpzTRzeNt/Xkr6BW9q3Tp0ZdlzQCecNGV71yN4X9c8eFjKMHB7nGtzftI9MSnXIlFk3TBxKYQ3fGfNNGBWw8Eg0s9wKs03U2xUpuWyOrqf739KiM5Pe3+BM20d7OdlNYTZIdm7tuuuyst1lKv0rpSY7tjLWcHFZBkyT++be6PxuZZZuMngc+I0wYKNbfXz9pjRlmy1nzJGfmwwkRKByv76iERcby7Ye3VvJMkzZfYC1I9skvmMI3oL22m5AiS9uHXk1VPQeEf3J4sXJqSh7VwBKU5wcYa8S2KhIX6xL1tqhvWHy+Ca9uEGw2tosQggdD4m4b4kriCTHXNr+qGArjfXq/rXj0obl/77gDo1ddyjCC6fM1OLfu9a9emDQS4QZ36nS9H4TBTMPOTZX1Ca9BeTimz7PAG/SggSHwu+UR0=";
    private GLView glView;
    private String projectId;

    private SuperTextView superTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        projectId = getIntent().getStringExtra(KEY_INTENT_PROJECT);
        KLog.i("projectId===="+projectId);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        superTextView = findViewById(R.id.hand_ar);
        superTextView.setLeftImageViewClickListener(imageView -> {
            finish();
        });
        superTextView.setRightImageViewClickListener(imageView -> {
            if (glView != null) {
                ((ViewGroup) findViewById(R.id.preview)).removeView(glView);
            }
            if (!TextUtils.isEmpty(projectId)) {
                Bundle mBundle = new Bundle();
                mBundle.putString(Const.KEY_INTENT_PROJECT, projectId);
                MyIntentUtil.startActivityForResult(this, DlListActivity.class, mBundle);
                finish();
            }
        });

        if (!Engine.initialize(this, key)) {
            Log.e("HelloAR", "Initialization Failed.");
            Toast.makeText(ARActivity.this, Engine.errorMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        if (!CameraDevice.isAvailable()) {
            Log.e("HelloAR", "Initialization Failed.111");
            Toast.makeText(ARActivity.this, "CameraDevice not available.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!ImageTracker.isAvailable()) {
            Log.e("HelloAR", "Initialization Failed.222");
            Toast.makeText(ARActivity.this, "ImageTracker not available.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!VideoPlayer.isAvailable()) {
            Log.e("HelloAR", "Initialization Failed.333");
            Toast.makeText(ARActivity.this, "VideoPlayer not available.", Toast.LENGTH_LONG).show();
            return;
        }

        glView = new GLView(this);

        requestCameraPermission(new PermissionCallback() {
            @Override
            public void onSuccess() {
                ((ViewGroup) findViewById(R.id.preview)).addView(glView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

            @Override
            public void onFailure() {
            }
        });
    }

    private interface PermissionCallback {
        void onSuccess();

        void onFailure();
    }

    private HashMap<Integer, PermissionCallback> permissionCallbacks = new HashMap<Integer, PermissionCallback>();
    private int permissionRequestCodeSerial = 0;

    @TargetApi(23)
    private void requestCameraPermission(PermissionCallback callback) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                int requestCode = permissionRequestCodeSerial;
                permissionRequestCodeSerial += 1;
                permissionCallbacks.put(requestCode, callback);
                requestPermissions(new String[]{Manifest.permission.CAMERA}, requestCode);
            } else {
                callback.onSuccess();
            }
        } else {
            callback.onSuccess();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissionCallbacks.containsKey(requestCode)) {
            PermissionCallback callback = permissionCallbacks.get(requestCode);
            permissionCallbacks.remove(requestCode);
            boolean executed = false;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    executed = true;
                    callback.onFailure();
                }
            }
            if (!executed) {
                callback.onSuccess();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (glView != null) {
            ((ViewGroup) findViewById(R.id.preview)).removeView(glView);
            ((ViewGroup) findViewById(R.id.preview)).addView(glView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            glView.onResume();
        }
    }

    @Override
    protected void onPause() {
        if (glView != null) {
            glView.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

package com.geemi.facelibrary.faceui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.idl.face.platform.FaceStatusNewEnum;
import com.baidu.idl.face.platform.model.ImageInfo;
import com.geemi.facelibrary.http.GeemiHttpImpl;
import com.geemi.facelibrary.manager.GeemiFaceManager;
import com.geemi.facelibrary.model.Const;
import com.geemi.facelibrary.model.PersonInfoByidCardModel;
import com.geemi.facelibrary.model.ResultModel;
import com.geemi.facelibrary.router.RouterFragmentPath;
import com.geemi.facelibrary.router.RouterUrl;
import com.geemi.facelibrary.utils.JsonUtils;
import com.geemi.facelibrary.utils.MyIntentUtil;
import com.geemi.facelibrary.utils.MyUtil;
import com.geemi.facelibrary.widget.TimeoutDialog;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnCancelListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static com.geemi.facelibrary.model.Const.GEEMIFACE_IMAGE;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_CONTRAST;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_GATHER;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_MANAGER;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_SING;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_TITLE;
import static com.geemi.facelibrary.model.Const.KEY_INTENT_LEVEL_SAVE;
import static com.geemi.facelibrary.model.Const.KEY_INTENT_PROJECT;

public class FaceLivenessExpActivity extends FaceLivenessActivity implements
        TimeoutDialog.OnTimeoutDialogClickListener {
    private int faceTag = -1;

    private TimeoutDialog mTimeoutDialog;

    private String projectId, IdCard;

    private BasePopupView basePopupView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int stringExtra = getIntent().getIntExtra(KEY_INTENT_LEVEL_SAVE, faceTag);
        KLog.i("stringExtra====" + stringExtra);
        projectId = getIntent().getStringExtra(KEY_INTENT_PROJECT);
//        // ?????????????????????
//        ExampleApplication.addDestroyActivity(FaceLivenessExpActivity.this,
//                "FaceLivenessExpActivity");
    }


    @Override
    public void onLivenessCompletion(FaceStatusNewEnum status, String message,
                                     HashMap<String, ImageInfo> base64ImageCropMap,
                                     HashMap<String, ImageInfo> base64ImageSrcMap, int currentLivenessCount) {
        super.onLivenessCompletion(status, message, base64ImageCropMap, base64ImageSrcMap, currentLivenessCount);

        if (status == FaceStatusNewEnum.OK && mIsCompletion) {
            // ??????????????????
            getBestImage(base64ImageCropMap, base64ImageSrcMap);
        } else if (status == FaceStatusNewEnum.DetectRemindCodeTimeout) {
            if (mViewBg != null) {
                mViewBg.setVisibility(View.VISIBLE);
            }
            showMessageDialog();
        }
    }

    /**
     * ??????????????????
     *
     * @param imageCropMap ????????????
     * @param imageSrcMap  ????????????
     */
    private void getBestImage(HashMap<String, ImageInfo> imageCropMap, HashMap<String, ImageInfo> imageSrcMap) {
        String bmpStr = null;
        // ???????????????????????????????????????????????????????????????????????????????????????????????????
        if (imageCropMap != null && imageCropMap.size() > 0) {
            List<Map.Entry<String, ImageInfo>> list1 = new ArrayList<>(imageCropMap.entrySet());
            Collections.sort(list1, (o1, o2) -> {
                String[] key1 = o1.getKey().split("_");
                String score1 = key1[2];
                String[] key2 = o2.getKey().split("_");
                String score2 = key2[2];
                // ????????????
                return Float.valueOf(score2).compareTo(Float.valueOf(score1));
            });
            bmpStr = list1.get(0).getValue().getBase64();

//            KLog.i("list1====="+bmpStr);
//            boolean testImage = saveImg(MyUtil.base64ToBitmap(bmpStr), "testImage", this);
//            KLog.i("testImage====="+testImage);


            // ???????????????????????????????????????base64
//            int secType = mFaceConfig.getSecType();
//            String base64;
//            if (secType == 0) {
//                base64 = list1.get(0).getValue().getBase64();
//            } else {
//                base64 = list1.get(0).getValue().getSecBase64();
//            }
        }

        // ???????????????????????????????????????????????????????????????????????????????????????????????????
        if (imageSrcMap != null && imageSrcMap.size() > 0) {
            List<Map.Entry<String, ImageInfo>> list2 = new ArrayList<>(imageSrcMap.entrySet());
            Collections.sort(list2, (o1, o2) -> {
                String[] key1 = o1.getKey().split("_");
                String score1 = key1[2];
                String[] key2 = o2.getKey().split("_");
                String score2 = key2[2];
                // ????????????
                return Float.valueOf(score2).compareTo(Float.valueOf(score1));
            });
//            bmpStr = list2.get(0).getValue().getBase64();

            // ???????????????????????????????????????base64
//            int secType = mFaceConfig.getSecType();
//            String base64;
//            if (secType == 0) {
//                base64 = mBmpStr;
//            } else {
//                base64 = list2.get(0).getValue().getBase64();
//            }
            Const.GEEMIFACE_IMAGE = bmpStr;
            int intExtra = getIntent().getIntExtra(KEY_INTENT_LEVEL_SAVE, faceTag);

            switch (intExtra) {
                case GEEMIFACE_TAGCODE_GATHER://???????????? ?????????
                    break;
                case GEEMIFACE_TAGCODE_CONTRAST://????????????
                    comparisonFace(Const.GEEMIFACE_IMAGE);
                    KLog.i("======??????????????????" + projectId);
                    break;
                case GEEMIFACE_TAGCODE_SING://????????????
                    KLog.i("======??????????????????" + projectId);
                    clockFace(GEEMIFACE_IMAGE);
                    break;
                case GEEMIFACE_TAGCODE_MANAGER:
                    IdCard = getIntent().getStringExtra("IdCard");
                    KLog.i("======???????????????????????????" + projectId + "===IdCard==" + IdCard);
                    faceCheck(GEEMIFACE_IMAGE);
//                    loginOntDialog();
//                    MyUtil.setEventBusData(99999, "?????????????????????");
//                    finish();
                    break;
            }
        }
    }


    public static boolean saveImg(Bitmap bitmap, String name, Context context) {
        try {
            String sdcardPath = System.getenv("EXTERNAL_STORAGE");      //??????sd?????????
            String dir = sdcardPath + "/????????????/";                    //???????????????????????????
            File file = new File(dir);                                 //???File?????????
            if (!file.exists()) {                                     //???????????????  ???mkdirs()??????????????????
                file.mkdirs();
            }
            Log.i("SaveImg", "file uri==>" + dir);
            File mFile = new File(dir + name);                        //???????????????????????????
            if (mFile.exists()) {
                Toast.makeText(context, "??????????????????!", Toast.LENGTH_SHORT).show();
                return false;
            }

            FileOutputStream outputStream = new FileOutputStream(mFile);     //???????????????
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);  //compress?????????outputStream
            Uri uri = Uri.fromFile(mFile);                                  //???????????????uri
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)); //???????????????????????????????????????????????????????????????????????????
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void loginOntDialog(String msg) {
        basePopupView = new XPopup.Builder(this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .isDestroyOnDismiss(true)
                .asConfirm("??????", "??????????????????(" + msg + ")????????????????????????",
                        "??????", "??????",
                        () -> {
                            if (mViewBg != null) {
                                mViewBg.setVisibility(View.GONE);
                            }
                            onPause();
                            onResume();
                        }, () -> {
                            Const.GEEMIFACE_IMAGE = "face_image";
                            finish();
                        }, false);
        basePopupView.show();
    }


    //????????????
    public void comparisonFace(String images) {
        KLog.i("projectId====" + projectId);
        Map<String, String> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("images", images);
        GeemiFaceManager.getHttpModel().getData(1001, GeemiHttpImpl.Method_POST, RouterUrl.COMPARISONFACE, params, this);
    }


    //??????????????????
    public void clockFace(String images) {
        Map<String, String> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("images", images);
        GeemiFaceManager.getHttpModel().getData(1002, GeemiHttpImpl.Method_POST, RouterUrl.CLOCKFACE, params, this);
    }

    //??????????????????
    public void faceCheck(String images) {
        Map<String, String> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("image", images);
        params.put("idCard", IdCard);
        KLog.i("projectId=====" + projectId + "===IdCard======" + IdCard);
        GeemiFaceManager.getHttpModel().getData(1003, GeemiHttpImpl.Method_POST, RouterUrl.FACECHECK, params, this);
    }


    private void showMessageDialog() {
        mTimeoutDialog = new TimeoutDialog(this);
        mTimeoutDialog.setDialogListener(this);
        mTimeoutDialog.setCanceledOnTouchOutside(false);
        mTimeoutDialog.setCancelable(false);
        mTimeoutDialog.show();
        onPause();
    }

    @Override
    public void onComplete(int tag, String json) {
        super.onComplete(tag, json);
        KLog.i("tag===" + tag + "=====json====" + json);
        switch (tag) {
            case 1001:
                PersonInfoByidCardModel personInfoByidCardModel = JsonUtils.getArrJson(json, PersonInfoByidCardModel.class);
                if (personInfoByidCardModel.isSuccess()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(GEEMIFACE_TITLE, getIntent().getStringExtra(GEEMIFACE_TITLE));
                    bundle.putInt(KEY_INTENT_LEVEL_SAVE, getIntent().getIntExtra(KEY_INTENT_LEVEL_SAVE, faceTag));
                    bundle.putSerializable("personInfo", personInfoByidCardModel.getResult());
                    MyIntentUtil.jumpTag(this, "????????????", RouterFragmentPath.GeemiRouterPath.PAGER_CLOCKPUNCH,null, "",true,bundle);
//                    MyIntentUtil.startActivityForResult(this, GeemiFaceExpActivity.class, bundle);
                    finish();
                    GeemiFaceManager.getInstance().clearActivitys();
                    return;
                }
                ToastUtils.showLong(personInfoByidCardModel.getMessage());
                break;
            case 1002:
                PersonInfoByidCardModel clockFaceDataModel = JsonUtils.getArrJson(json, PersonInfoByidCardModel.class);
                if (clockFaceDataModel.isSuccess()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(GEEMIFACE_TITLE, getIntent().getStringExtra(GEEMIFACE_TITLE));
                    bundle.putInt(KEY_INTENT_LEVEL_SAVE, getIntent().getIntExtra(KEY_INTENT_LEVEL_SAVE, faceTag));
                    bundle.putSerializable("personInfo", clockFaceDataModel.getResult());
//                    bundle.putString(KEY_INTENT_PERSONID, getIntent().getStringExtra(KEY_INTENT_PERSONID));
//                    MyIntentUtil.startActivityForResult(this, GeemiFaceExpActivity.class, bundle);
                    MyIntentUtil.jumpTag(this, "????????????", RouterFragmentPath.GeemiRouterPath.PAGER_CLOCKPUNCH,null, "",true,bundle);
                }
//                GEEMIFACE_IMAGE = "face_image";
                finish();
                ToastUtils.showLong(clockFaceDataModel.getMessage());
                break;
            case 1003:
                ResultModel resultModel = JsonUtils.getArrJson(json, ResultModel.class);
                ToastUtils.showLong(resultModel.getMessage());
                if (!resultModel.isSuccess() || resultModel.getCode() != 200) {
                    loginOntDialog(resultModel.getMessage());
                    return;
                }
                MyUtil.setEventBusData(99999, "?????????????????????");
                finish();
                break;

        }
    }

    @Override
    public void finish() {
        super.finish();

    }

    @Override
    public void onRecollect() {
        if (mTimeoutDialog != null) {
            mTimeoutDialog.dismiss();
        }
        if (mViewBg != null) {
            mViewBg.setVisibility(View.GONE);
        }
        onResume();
    }

    @Override
    public void onReturn() {
        if (mTimeoutDialog != null) {
            mTimeoutDialog.dismiss();
        }
        Const.GEEMIFACE_IMAGE = "face_image";
        finish();
    }
}

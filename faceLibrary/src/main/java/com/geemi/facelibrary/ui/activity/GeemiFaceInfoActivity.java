//package com.geemi.facelibrary.ui.activity;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.hardware.Camera;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//
//import androidx.annotation.Nullable;
//import androidx.databinding.DataBindingUtil;
//import androidx.dynamicanimation.animation.SpringForce;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.geemi.facelibrary.R;
//import com.geemi.facelibrary.adapter.FaceInfoManagerListAdapter;
//import com.geemi.facelibrary.databinding.ActivityFaceexpBinding;
//import com.geemi.facelibrary.databinding.DialogFaceinfoUnitBinding;
//import com.geemi.facelibrary.faceui.FaceLivenessExpActivity;
//import com.geemi.facelibrary.http.GeemiHttpImpl;
//import com.geemi.facelibrary.interfaces.IResultDataCallbace;
//import com.geemi.facelibrary.manager.GeemiFaceManager;
//import com.geemi.facelibrary.model.AllCompanyModel;
//import com.geemi.facelibrary.model.Const;
//import com.geemi.facelibrary.model.DictItemsModel;
//import com.geemi.facelibrary.model.MessageEventbus;
//import com.geemi.facelibrary.model.PersonInfoByidCardModel;
//import com.geemi.facelibrary.model.ResultModel;
//import com.geemi.facelibrary.model.TempFaceInfoModel;
//import com.geemi.facelibrary.router.RouterUrl;
//import com.geemi.facelibrary.ui.base.GeemiBaseActivity;
//import com.geemi.facelibrary.ui.popup.FaceInfoTypePopupView;
//import com.geemi.facelibrary.utils.AnimationUtil;
//import com.geemi.facelibrary.utils.DialogUtil;
//import com.geemi.facelibrary.utils.JsonUtils;
//import com.geemi.facelibrary.utils.MyIntentUtil;
//import com.geemi.facelibrary.utils.MyUtil;
//import com.geemi.facelibrary.utils.ViewOnClickUtils;
//import com.gyf.immersionbar.ImmersionBar;
//import com.kongzue.dialog.v2.CustomDialog;
//import com.luck.picture.lib.PictureSelector;
//import com.luck.picture.lib.entity.LocalMedia;
//import com.lxj.xpopup.XPopup;
//import com.lxj.xpopup.enums.PopupPosition;
//import com.zkteco.android.IDReader.IDPhotoHelper;
//import com.zkteco.android.IDReader.WLTService;
//import com.zkteco.android.biometric.module.idcard.IDCardReader;
//import com.zkteco.android.biometric.module.idcard.meta.IDCardInfo;
//import com.zkteco.android.biometric.module.idcard.meta.IDPRPCardInfo;
//import com.znht.iodev2.PowerCtl;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import me.goldze.mvvmhabit.utils.KLog;
//import me.goldze.mvvmhabit.utils.ToastUtils;
//
//import static com.geemi.facelibrary.model.Const.GEEMIFACE_CARDKEY;
//import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_MANAGER;
//import static com.geemi.facelibrary.model.Const.GEEMIFACE_TITLE;
//import static com.geemi.facelibrary.model.Const.KEY_CAMERA_FACING;
//import static com.geemi.facelibrary.model.Const.KEY_INTENT_LEVEL_SAVE;
//import static com.geemi.facelibrary.model.Const.KEY_INTENT_PROJECT;
//import static com.geemi.facelibrary.utils.MyUtil.ImageToBitmap;
//import static com.geemi.facelibrary.utils.MyUtil.base64ToBitmap;
//import static com.geemi.facelibrary.utils.MyUtil.bitmapToBase64;
//
///**
// * ????????????
// */
//public class GeemiFaceInfoActivity extends GeemiBaseActivity implements IResultDataCallbace {
//    private ActivityFaceexpBinding activityFaceexpBinding;
//
//    private DialogFaceinfoUnitBinding dialogFaceinfoUnitBinding;
//
//    private int SERVICETEAM = 1;//??????
//
//    private int COMPANY = 2;//??????
//
//    private RequestOptions requestOptions = new RequestOptions()
//            .placeholder(R.mipmap.aaws)
//            .error(R.mipmap.aaws);
//
//    //????????????
//    private int faceTag;
//    //????????????
//    private boolean isCard;
//    //?????????????????????
////    private FaceInfoTypeModel faceInfoTypeModel;
//    //???????????????
//    private TempFaceInfoModel tempFaceInfoModel;
//    //?????????????????????
//    private TempFaceInfoModel.FaceInfoDataDTO faceInfoDataDTOItem;
//    //?????????
//    private FaceInfoManagerListAdapter faceInfoManagerListAdapter;
//    //??????????????????
//    private CustomDialog customDialog;
//    //??????????????????
//    private PowerCtl powerCtl;
//    //???????????????
//    private IDCardReader idCardReader;
//    //????????????
//    private AllCompanyModel allCompanyModel;
//    //????????????
//    private AllCompanyModel allServiceTeam;
//    //????????????
//    private DictItemsModel dictItemsModel;
//    //????????????
//    private PersonInfoByidCardModel personInfoByidCardModel;
//    private boolean bopen = false;
//
//    boolean running = false;
//    //?????????????????????,
//    int retType = 0;
//    final int ID_CN = 1;//?????????????????????
//    final int ID_PRP = 2;//??????????????????
//    final int ID_HK_TW = 3;//??????????????????
//
//    private IDCardInfo idCardInfo = new IDCardInfo();
//
//    private String projectId;//??????ID
//    private String idCard = "";//????????????
//    private String name = "";//??????
//    private String phone = "";//?????????
//    private String age = "";//??????
//    private String jobs = "";//??????
//    private String companyId = "";//??????id
//    private String companyName = "";//????????????
//    private String teamId = "";//??????id
//    private String teamName = "";//????????????
//    private String sex = "";//??????
//    private String address = "";//??????
//    //????????????
//    private String faceImageBase = "";
//    //isteamleader
//    private String isteamleader = "0";
//
//    private String idcardphoto = "";// ????????????
//
//    private String idcardreversephoto = "";//????????????
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        activityFaceexpBinding = DataBindingUtil.setContentView(this, R.layout.activity_faceexp);
//        GeemiFaceManager.getInstance().addActivity(this);
//        ImmersionBar.with(this)
//                .fitsSystemWindows(true)  //???????????????,???????????????????????????
//                .statusBarColor(R.color.face_bj)
//                .init();
//        initView();
//        initData();
//        initLinearView();
//    }
//
//    private void initData() {
//        getAllCompany();
//        getDictItems("");
//    }
//
//    public static final int SCAN_IDCARD_REQUEST = 1;
//
//    @SuppressLint("NewApi")
//    private void initLinearView() {
//        activityFaceexpBinding.textFaceTitle.setLeftImageViewClickListener(imageView -> {
//            if (ViewOnClickUtils.isFastClick()) {
//                return;
//            }
//            Const.GEEMIFACE_IMAGE = "face_image";
//            finish();
//        });
//
//        activityFaceexpBinding.includeFaceexp.superSubmin.setOnClickListener(v -> {
//            if (ViewOnClickUtils.isFastClick()) {
//                return;
//            }
//            submitData();
//        });
//
//        faceInfoManagerListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//            faceInfoClickItemData((TempFaceInfoModel.FaceInfoDataDTO) adapter.getData().get(position));
//        });
//        //??????????????????
//        activityFaceexpBinding.includeFaceexp.faceexpImageRight.setOnClickListener(v -> {
//            if (ViewOnClickUtils.isFastClick()) {
//                return;
//            }
//
//            if (!bopen) {
//                ToastUtils.showLong("??????????????????NFC???????????????!");
//            } else {
//                if (!isCard) {
//                    ToastUtils.showLong("??????NFC?????????????????????!");
//                    return;
//                }
//            }
////            DialogUtil.showProgress(this);
//            intentSkip();
//        });
//
//        activityFaceexpBinding.includeFaceexp.radiogroup.setOnCheckedChangeListener((group, checkedId) -> {
//            if (checkedId == R.id.radio_lw) {
//                isteamleader = "0";
//            } else if (checkedId == R.id.radio_gl) {
//                isteamleader = "1";
//            }
//        });
//
//        //??????
//        activityFaceexpBinding.includeFaceexp.includeCred.linearCredZ.setOnClickListener(v -> {
//            if (ViewOnClickUtils.isFastClick()) {
//                return;
//            }
////            startActivity(new Intent(this, SimpleCameraActivity.class));
////            MyUtil.pictureMode(true, false, 1, this, 8001);
//        });
//        //??????
//        activityFaceexpBinding.includeFaceexp.includeCred.linearCredF.setOnClickListener(v -> {
//            if (ViewOnClickUtils.isFastClick()) {
//                return;
//            }
////            startActivity(new Intent(this, SimpleCameraActivity.class));
////            MyUtil.pictureMode(true, false, 1, this, 8002);
//        });
//    }
//
//    //??????????????????
//    @SuppressLint("NewApi")
//    private void submitData() {
//        faceInfoManagerListAdapter.setNewData(tempFaceInfoModel.getFaceInfoData());
//        activityFaceexpBinding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
//        tempFaceInfoModel.getFaceInfoData().forEach(n -> {
//            switch (n.getFaceInfoId()) {
//                case 1://??????
//                    name = n.getFaceCenterTextString();
//                    break;
//                case 2://??????
//                    age = n.getFaceCenterTextString();
//                    break;
//                case 3://????????????
//                    companyId = n.getCompanyID();
//                    companyName = n.getFaceCenterTextString();
//                    break;
//                case 7://????????????
//                    teamId = n.getTeamId();
//                    teamName = n.getFaceCenterTextString();
//                    break;
//                case 4://??????
//                    if (n.getJobs() == null) {
//                        jobs = "";
//                    } else {
//                        jobs = n.getJobs();
//                    }
//                    break;
//                case 5://?????????
//                    phone = n.getFaceCenterTextString();
//                    break;
//                case 6://????????????
//                    idCard = n.getFaceCenterTextString();
//                    break;
//                case 8://??????
//                    if (n.getFaceCenterTextString().contains("???")) {
//                        sex = "1";
//                    } else {
//                        sex = "2";
//                    }
//                    break;
//                case 9://??????
//                    address = n.getFaceCenterTextString();
//                    break;
//            }
//        });
//        KLog.i("nnnnn====" + projectId + "=====isteamleader====" + isteamleader + "===idCard==" + idCard + "=====sex====" + sex + "====name===" + name + "===phone====" + phone + "===age====" + age + "====jobs===" + jobs + "====address====" + address + "====companyName====" + companyName + "==teamName======" + teamName + "====faceImageBase====" + faceImageBase);
//        KLog.i("====idcardphoto===" + idcardphoto);
//        KLog.i("=====idcardreversephoto=====" + idcardreversephoto);
//        if (projectId.length() == 0) {
//            ToastUtils.showLong("????????????ID");
//        } else if (idCard.length() == 0 || idCard.contains("???????????????")) {
//            ToastUtils.showLong("?????????????????????");
//        } else if (name.length() == 0 || name.contains("???????????????")) {
//            ToastUtils.showLong("????????????");
//        } else if (phone.length() == 0) {
//            ToastUtils.showLong("???????????????");
//        } else if (age.length() == 0) {
//            ToastUtils.showLong("????????????");
//        } else if (jobs.length() == 0 || jobs.contains("?????????")) {
//            ToastUtils.showLong("????????????");
//        } else if (companyName.length() == 0 || companyName.contains("?????????")) {
//            ToastUtils.showLong("????????????");
//        } else if (teamName.length() == 0 || teamName.contains("?????????")) {
//            ToastUtils.showLong("????????????");
//        } else if (faceImageBase.length() == 0 || faceImageBase.contains("face_image")) {
//            ToastUtils.showLong("??????????????????");
//        } else if (sex.contains("0")) {
//            ToastUtils.showLong("??????????????????");
//        } else if (address.length() == 0) {
//            ToastUtils.showLong("??????????????????");
//        } else if (idcardphoto.length() == 0) {
//            ToastUtils.showLong("???????????????????????????");
//        } else if (idcardreversephoto.length() == 0) {
//            ToastUtils.showLong("???????????????????????????");
//        } else {
//            savePerson();
//        }
//    }
//
//
//    @SuppressLint("NewApi")
//    private void initView() {
//        activityFaceexpBinding.includeFaceexp.superSubmin.setText("??????");
//        faceTag = getIntent().getIntExtra(KEY_INTENT_LEVEL_SAVE, faceTag);
//
//        projectId = getIntent().getStringExtra(KEY_INTENT_PROJECT);
//
//        isCard = getIntent().getBooleanExtra(GEEMIFACE_CARDKEY, false);
//        if (!isCard) {
//            ToastUtils.showLong("?????????");
//            //??????????????????????????????????????????
//            MyUtil.getInstance(this);
//            new Thread() {
//                @Override
//                public void run() {
//                    super.run();
//                    idCardReader = MyUtil.open(powerCtl);
//                    if (idCardReader != null) {
//                        bopen = true;
//                    }
//                    mHandler.sendEmptyMessage(201);
//                }
//            }.start();
//        }
//
//        activityFaceexpBinding.includeFaceexp.superSubmin.setEnabled(true);
//        activityFaceexpBinding.textFaceTitle.setCenterTextIsBold(true);
//        //title??????
//        activityFaceexpBinding.textFaceTitle.setCenterString(getIntent().getStringExtra(GEEMIFACE_TITLE));
////        faceInfoTypeModel = MyUtil.readFromAssets(this, "faceInfo_type.json", FaceInfoTypeModel.class);
//
//        tempFaceInfoModel = MyUtil.readFromAssets(this, "faceInfoManager.json", TempFaceInfoModel.class);
//
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
//        activityFaceexpBinding.includeFaceexp.recyclerView.setLayoutManager(layoutManager);
//        faceInfoManagerListAdapter = new FaceInfoManagerListAdapter(R.layout.itme_face_info_temp, this);
//        //????????????5??????????????????ALPHAIN ?????????SCALEIN ???????????????SLIDEIN_BOTTOM ???????????????SLIDEIN_LEFT ???????????????SLIDEIN_RIGHT ???
////        viewModel.orderListAdapter.openLoadAnimation(ALPHAIN);
//        faceInfoManagerListAdapter.setNotDoAnimationCount(1);
//        faceInfoManagerListAdapter.setPreLoadNumber(8);
//        faceInfoManagerListAdapter.setNewData(tempFaceInfoModel.getFaceInfoData());
//        activityFaceexpBinding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
//
////        Glide.with(this).load(tempFaceInfoModel.getFaceThumbnail()).apply(requestOptions).into(activityFaceexpBinding.includeFaceexp.faceexpImageRight);
//
//
//    }
//
//    //?????????Item??????
//    private void faceInfoClickItemData(TempFaceInfoModel.FaceInfoDataDTO o) {
//        faceInfoDataDTOItem = o;
//        KLog.i("faceInfoDataDTOItem====" + faceInfoDataDTOItem.toString());
////        if (ViewOnClickUtils.isFastClick()) {
////            return;
////        }
//        switch (faceInfoDataDTOItem.getFaceInfoId()) {
//            case 3://????????????
//                if (allCompanyModel.getResult().size() == 0) {
//                    ToastUtils.showLong("??????????????????");
//                    return;
//                }
//                dialogDataInfo(COMPANY);
//                break;
//            case 4://??????
//                if (dictItemsModel.getResult().size() == 0) {
//                    ToastUtils.showLong("??????????????????");
//                    return;
//                }
//                popupView();
//                break;
//            case 7://??????
//                if (allServiceTeam == null) {
//                    ToastUtils.showLong("???????????????????????????");
//                    return;
//                }
//                if (allServiceTeam.getResult().size() == 0) {
//                    ToastUtils.showLong("??????????????????");
//                    return;
//                }
//                dialogDataInfo(SERVICETEAM);
//                break;
//        }
//    }
//
//    //????????????dialog
//    private void dialogDataInfo(int tag) {
//        customDialog = CustomDialog.show(this, R.layout.dialog_faceinfo_unit, (dialog, rootView) -> {
//            AnimationUtil.springAnimation(rootView, SpringForce.STIFFNESS_VERY_LOW, SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//            dialogFaceinfoUnitBinding = DataBindingUtil.bind(rootView);
//            initDialogListView(tag);
//        }).setCanCancel(false);
//        MyUtil.dialogWindowManager(customDialog);
//    }
//
//    //    SERVICETEAM = 1;//??????
////    COMPANY = 2;//??????
//    @SuppressLint("NewApi")
//    private void initDialogListView(int tag) {
//        dialogFaceinfoUnitBinding.dialogTextTitle.setRightTextIsBold(true);
//        dialogFaceinfoUnitBinding.dialogTextTitle.setLeftTextIsBold(true);
//        MyUtil.setWheelView(dialogFaceinfoUnitBinding.wheelview, this);
//        //????????????
//        if (tag == COMPANY) {
//            dialogFaceinfoUnitBinding.dialogTextTitle.setCenterString("??????????????????");
//            dialogFaceinfoUnitBinding.wheelview.setData(allCompanyModel.getResult());
//        } else {
//            dialogFaceinfoUnitBinding.dialogTextTitle.setCenterString("??????????????????");
//            dialogFaceinfoUnitBinding.wheelview.setData(allServiceTeam.getResult());
//        }
//        //??????
//        dialogFaceinfoUnitBinding.dialogTextTitle.setLeftTvClickListener(() -> {
//            if (ViewOnClickUtils.isFastClick()) {
//                return;
//            }
//            customDialog.doDismiss();
//        });
////        ??????
//        dialogFaceinfoUnitBinding.dialogTextTitle.setRightTvClickListener(() -> {
//            if (ViewOnClickUtils.isFastClick()) {
//                return;
//            }
//            allCompanyData(tag);
//        });
//
//    }
//
//    //???????????? ????????????
//    @SuppressLint("NewApi")
//    private void allCompanyData(int tag) {
//        AllCompanyModel.ResultDTO resultDTO = (AllCompanyModel.ResultDTO) dialogFaceinfoUnitBinding.wheelview.getSelectedItemData();
////        ToastUtils.showLong("????????????===" + resultDTO.toString());
//        customDialog.doDismiss();
//        if (tag == COMPANY) {//??????
//            tempFaceInfoModel.getFaceInfoData().forEach(n -> {
//                if (n.getFaceInfoId() == 7) {
//                    n.setTeamId("");
//                    n.setFaceCenterTextString("?????????");
//                }
//            });
//            faceInfoDataDTOItem.setCompanyID(resultDTO.getId());
//            //??????????????????
//            getAllServiceTeam(faceInfoDataDTOItem.getCompanyID());
//        } else {
//            faceInfoDataDTOItem.setTeamId(resultDTO.getId());
//        }
//        faceInfoDataDTOItem.setFaceCenterTextString(resultDTO.getName());
//        faceInfoManagerListAdapter.setNewData(tempFaceInfoModel.getFaceInfoData());
//        activityFaceexpBinding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
//        KLog.i("faceInfoDataDTOItem======" + tempFaceInfoModel.getFaceInfoData().toString());
//    }
//
//    //??????????????????
//    private void intentSkip() {
//        Bundle bundle = new Bundle();
//        bundle.putInt(KEY_INTENT_LEVEL_SAVE, GEEMIFACE_TAGCODE_MANAGER);
//        bundle.putString(GEEMIFACE_TITLE, getIntent().getStringExtra(GEEMIFACE_TITLE));
//        bundle.putString(KEY_INTENT_PROJECT, projectId);
//        bundle.putString("IdCard", idCardInfo.getId());
//        new XPopup.Builder(this)
//                .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
//                .asBottomList("?????????", new String[]{"???????????????", "???????????????"},
//                        null, 2,
//                        (position1, text) -> {
//                            if (ViewOnClickUtils.isFastClick()) {
//                                return;
//                            }
//                            if (position1 == 0) {
//                                bundle.putInt(KEY_CAMERA_FACING, Camera.CameraInfo.CAMERA_FACING_FRONT);
//                            } else {
//                                bundle.putInt(KEY_CAMERA_FACING, Camera.CameraInfo.CAMERA_FACING_BACK);
//                            }
//                            MyIntentUtil.startActivityForResult(this, FaceLivenessExpActivity.class, bundle);
//                        })
//                .show();
//    }
//
//    //?????? ?????? ??????
//    private void popupView() {
//        new XPopup.Builder(this)
//                .popupPosition(PopupPosition.Right)//??????
//                .hasStatusBar(false)
//                .dismissOnTouchOutside(false)
//                .enableDrag(true)
//                .asCustom(new FaceInfoTypePopupView(this, dictItemsModel.getResult(), this))
//                .show();
//    }
//
//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @SuppressLint("NewApi")
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 201://
//                    readIdCard();
//                    break;
//                case 202://
//                    tempFaceInfoModel.getFaceInfoData().forEach(n -> {
//                        switch (n.getFaceInfoId()) {
//                            case 1://??????
//                                n.setFaceCenterTextString(idCardInfo.getName());
//                                break;
//                            case 2://??????
//                                n.setFaceCenterTextString(MyUtil.evaluate(idCardInfo.getId()));
//                                break;
//                            case 8://??????
//                                n.setFaceCenterTextString(idCardInfo.getSex());
//                                break;
//                            case 9://??????
//                                n.setFaceCenterTextString(idCardInfo.getAddress());
//                                break;
//                            case 6://?????????
//                                n.setFaceCenterTextString(idCardInfo.getId());
//                                getPersonInfoByIdcard(idCardInfo.getId());
//                                break;
//                        }
//                    });
//                    faceInfoManagerListAdapter.setNewData(tempFaceInfoModel.getFaceInfoData());
//                    activityFaceexpBinding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
//                    MyUtil.listEndView(activityFaceexpBinding.includeFaceexp.linearSfz);
//                    break;
//
//            }
//        }
//    };
//
//    //????????????
//    @SuppressLint("NewApi")
//    private void readIdCard() {
//        if (!bopen) {
//            tempFaceInfoModel.getFaceInfoData().forEach(n -> {
//                if (n.getFaceInfoId() == 1 || n.getFaceInfoId() == 6 || n.getFaceInfoId() == 8) {
//                    n.setFaceIsEnabled(String.valueOf(true));
//                    n.setFaceCenterTextString("");
//                }
//            });
//            faceInfoManagerListAdapter.setNewData(tempFaceInfoModel.getFaceInfoData());
//            activityFaceexpBinding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
//            faceInfoManagerListAdapter.notifyDataSetChanged();
//            ToastUtils.showLong("??????????????????");
//            return;
//        }
//        running = true;
//        //????????????
//        new Thread(readTask).start();
//    }
//
//    //??????????????????
//    private Runnable readTask = () -> {
//
//        while (running) {
//            try {
//                //?????????????????????500ms?????????????????????????????????????????????????????????????????????????????????????????????
//                Thread.sleep(500);
//                //????????????
//                //?????????????????????try?????? ?????????????????????????????????????????????????????????????????????,
//                //???try??????????????????????????????????????????
//                //??????????????????????????????????????????????????????????????????try??????????????????
//                idCardReader.findCard(0);
//                idCardReader.selectCard(0);
//
//                Thread.sleep(50);
//
//                retType = idCardReader.readCardEx(0, 0);
//
//                //????????????
//                if (retType == 1 || retType == 2 || retType == 3) {
//                    //??????UI
//                    runOnUiThread(new Runnable() {
//                        @SuppressLint("NewApi")
//                        @Override
//                        public void run() {
//                            //???????????????
//                            MyUtil.play(1);
//                            switch (retType) {
//                                case 1://?????????????????????
//                                    idCardInfo = idCardReader.getLastIDCardInfo();
//                                    if (idCardInfo != null) {
//                                        isCard = true;
//                                        KLog.i("idCardInfo=====" + idCardInfo.toString());
//                                        //??????
//                                        if (idCardInfo.getPhotolength() > 0) {
//                                            //???????????????????????????bitmap
//                                            byte[] buf = new byte[WLTService.imgLength];
//                                            if (1 == WLTService.wlt2Bmp(idCardInfo.getPhoto(), buf)) {
//                                                activityFaceexpBinding.includeFaceexp.faceexpImageRight.setImageBitmap(IDPhotoHelper.Bgr2Bitmap(buf));
//                                            }
//                                        }
//                                        mHandler.sendEmptyMessage(202);
//                                    }
//                                    break;
//                                case 2://??????????????????
//                                    IDPRPCardInfo idprpCardInfo = idCardReader.getLastPRPIDCardInfo();
//                                    break;
//                                case 3://??????????????????
//                                    IDCardInfo idCardHK = idCardReader.getLastIDCardInfo();
//                                    break;
//                            }
//                        }
//                    });
//                }
//            } catch (Exception e) {
//                //LogHelper.e("errcode:" + e.getErrorCode() + ",internalerrorcode:" + e.getInternalErrorCode());
//                //continue;
//            }
//        }
//    };
//
//
//    //????????????????????????
//    public void getAllCompany() {
//        Map<String, String> params = new HashMap<>();
//        params.put("projectId", projectId);
//        GeemiFaceManager.getHttpModel().getData(1001, GeemiHttpImpl.Method_GET, RouterUrl.GETALLCOMPANY, params, this, false);
//    }
//
//
//    //??????????????????
//    public void getAllServiceTeam(String companyId) {
//        KLog.i("companyId====" + companyId + "===projectId==" + projectId);
//        Map<String, String> params = new HashMap<>();
//        params.put("projectId", projectId);
//        params.put("companyId", companyId);
//        GeemiFaceManager.getHttpModel().getData(1003, GeemiHttpImpl.Method_GET, RouterUrl.GETTALLSERVICETEAM, params, this, false);
//    }
//
//    //????????????????????????
//    public void getDictItems(String itemText) {
//        Map<String, String> params = new HashMap<>();
//        params.put("itemText", itemText);
//        params.put("code", "jobs");
//        GeemiFaceManager.getHttpModel().getData(1002, GeemiHttpImpl.Method_GET, RouterUrl.GETDICTITEMS, params, this, false);
//    }
//
//    //????????????
//    public void savePerson() {
//        Map<String, String> params = new HashMap<>();
//        params.put("projectId", projectId);
//        params.put("idCard", idCard);
//        params.put("name", name);
//        params.put("phone", phone);
//        params.put("age", age);
//        params.put("jobs", jobs);
//        params.put("companyId", companyId);
//        params.put("companyName", companyName);
//        params.put("teamId", teamId);
//        params.put("teamName", teamName);
//        params.put("image", faceImageBase);
//        params.put("sex", sex);
//        params.put("address", address);
//        params.put("isteamleader", isteamleader);
//        params.put("idcardphoto", idcardphoto);
//        params.put("idcardreversephoto", idcardreversephoto);
//        GeemiFaceManager.getHttpModel().getData(1004, GeemiHttpImpl.Method_POST, RouterUrl.SAVEPERSON, params, this);
//    }
//
//    //????????????????????????????????????
//    public void getPersonInfoByIdcard(String idCard) {
//        Map<String, String> params = new HashMap<>();
//        params.put("idCard", idCard);
//        params.put("projectId", projectId);
//        GeemiFaceManager.getHttpModel().getData(1005, GeemiHttpImpl.Method_GET, RouterUrl.GETPERSONINFOBYIDCARD, params, this);
//    }
//
//
//    @SuppressLint("NewApi")
//    @Override
//    public void onComplete(int tag, String json) {
//        super.onComplete(tag, json);
//        KLog.i("tag===" + tag + "=====json====" + json);
//        switch (tag) {
//            case 1001:
//                allCompanyModel = JsonUtils.getArrJson(json, AllCompanyModel.class);
//                if (allCompanyModel.isSuccess()) {
//
//                }
//                break;
//            case 1003:
//                allServiceTeam = JsonUtils.getArrJson(json, AllCompanyModel.class);
//                if (allCompanyModel.isSuccess()) {
//                }
//                break;
//            case 1002:
//                dictItemsModel = JsonUtils.getArrJson(json, DictItemsModel.class);
//                if (dictItemsModel.isSuccess()) {
//                    return;
//                }
//                break;
//            case 1004:
//                ResultModel resultModel = JsonUtils.getArrJson(json, ResultModel.class);
//                ToastUtils.showLong(resultModel.getMessage());
//                if (resultModel.isSuccess()) {
//                    finish();
//                }
//                break;
//            case 1005://?????????????????????
//                personInfoByidCardModel = JsonUtils.getArrJson(json, PersonInfoByidCardModel.class);
//                if (personInfoByidCardModel.isSuccess()) {
//                    if (personInfoByidCardModel.getResult() != null) {
//                        //isteamleader    0??????????????????1???????????????
//                        KLog.i("isteamleader====" + personInfoByidCardModel.getResult().getIsTeamLeader());
//                        if (personInfoByidCardModel.getResult().getIsTeamLeader().contains("0")) {
//                            activityFaceexpBinding.includeFaceexp.radioLw.setChecked(true);
//                        } else {
//                            activityFaceexpBinding.includeFaceexp.radioGl.setChecked(true);
//                        }
//                        tempFaceInfoModel.getFaceInfoData().forEach(n -> {
//                            switch (n.getFaceInfoId()) {
//                                case 1://??????
//                                    n.setFaceCenterTextString(personInfoByidCardModel.getResult().getName());
//                                    break;
//                                case 2://??????
//                                    n.setFaceCenterTextString(String.valueOf(personInfoByidCardModel.getResult().getAge()));
//                                    break;
//                                case 3://????????????
//                                    n.setFaceCenterTextString(personInfoByidCardModel.getResult().getCompany_name());
//                                    n.setCompanyID(personInfoByidCardModel.getResult().getCompany());
//                                    break;
//                                case 7://???????????? serviceteam_name
//                                    n.setFaceCenterTextString(personInfoByidCardModel.getResult().getServiceteam_name());
//                                    n.setTeamId(String.valueOf(personInfoByidCardModel.getResult().getServiceteam_id()));
//                                    break;
//                                case 4://??????
//                                    n.setFaceCenterTextString(personInfoByidCardModel.getResult().getJobsName());
//                                    n.setJobs(personInfoByidCardModel.getResult().getJobs());
//                                    break;
//                                case 5://?????????
//                                    n.setFaceCenterTextString(personInfoByidCardModel.getResult().getPhone());
//                                    break;
//                                case 6://????????????
//                                    n.setFaceCenterTextString(personInfoByidCardModel.getResult().getIdCard());
//                                    break;
//                            }
//                        });
////                        idcardphoto = "";// ????????????
////                        idcardreversephoto = "";//????????????
//                        if (personInfoByidCardModel.getResult().getIdcardReversePhoto() == null) {
//                            ToastUtils.showLong("?????????????????????????????????????????????");
//                        } else {
//                            Glide.with(this)
//                                    .load(personInfoByidCardModel.getResult().getIdcardReversePhoto())
//                                    .apply(requestOptions)
//                                    .into(activityFaceexpBinding.includeFaceexp.includeCred.imageCredF);
//                            idcardreversephoto = personInfoByidCardModel.getResult().getIdcardPhoto().toString();
//                        }
//
//                        if (personInfoByidCardModel.getResult().getIdcardPhoto() == null) {
//                            ToastUtils.showLong("?????????????????????????????????????????????");
//                        } else {
//                            Glide.with(this)
//                                    .load(personInfoByidCardModel.getResult().getIdcardPhoto())
//                                    .apply(requestOptions)
//                                    .into(activityFaceexpBinding.includeFaceexp.includeCred.imageCredZ);
//                            idcardphoto = personInfoByidCardModel.getResult().getIdcardPhoto().toString();
//                        }
//                        //??????????????????
//                        getAllServiceTeam(personInfoByidCardModel.getResult().getCompany());
//                    } else {
//                        ToastUtils.showLong("????????????????????????");
//                        setFaceImageData();
//                    }
//                    Const.GEEMIFACE_IMAGE = "face_image";
//                    faceInfoManagerListAdapter.setNewData(tempFaceInfoModel.getFaceInfoData());
//                    activityFaceexpBinding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
//                    intentSkip();
//                }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        KLog.i("requestCode=====" + requestCode);
////         ?????? LocalMedia ??????????????????path
////         1.media.getPath(); ?????????path
////         2.media.getCutPath();????????????path????????????media.isCut();?????????true
////         3.media.getCompressPath();????????????path????????????media.isCompressed();?????????true
////         ????????????????????????????????????????????????????????????????????????????????????
//        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//        if (selectList != null && selectList.size() != 0) {
//            switch (requestCode) {
//                case 8002://??????
//                    Glide.with(this)
//                            .load(selectList.get(0).getPath())
//                            .apply(requestOptions)
//                            .into(activityFaceexpBinding.includeFaceexp.includeCred.imageCredF);
//
//                    idcardreversephoto = bitmapToBase64(ImageToBitmap(selectList.get(0).getPath(), this));
//                    break;
//                case 8001://??????
//                    Glide.with(this)
//                            .load(selectList.get(0).getPath())
//                            .apply(requestOptions)
//                            .into(activityFaceexpBinding.includeFaceexp.includeCred.imageCredZ);
//                    idcardphoto = bitmapToBase64(ImageToBitmap(selectList.get(0).getPath(), this));
//                    break;
//            }
//        }
//    }
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getMessage(MessageEventbus messageWrap) {
//        KLog.i("messageWrap====" + messageWrap.toString());
//        if (messageWrap.getMessageId() == 99999) {
//            MyUtil.listStartVoew(activityFaceexpBinding.includeFaceexp.superSubmin);
//            MyUtil.listEndView(activityFaceexpBinding.includeFaceexp.linearSfz);
//            faceInfoManagerListAdapter.setNewData(tempFaceInfoModel.getFaceInfoData());
//            activityFaceexpBinding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
//            setFaceImageData();
//        } else if (messageWrap.getMessageId() == 8001) {
//            JSONObject jo = null;
//            try {
//                jo = new JSONObject(messageWrap.getObject().toString());
//                if (jo.opt("type").toString().contains("0")) {//??????
//                    Glide.with(this)
//                            .load(jo.opt("imgPath"))
//                            .apply(requestOptions)
//                            .into(activityFaceexpBinding.includeFaceexp.includeCred.imageCredF);
//                    idcardreversephoto = bitmapToBase64(ImageToBitmap(jo.opt("imgPath").toString(), this));
//                } else {//??????
//                    Glide.with(this)
//                            .load(jo.opt("imgPath"))
//                            .apply(requestOptions)
//                            .into(activityFaceexpBinding.includeFaceexp.includeCred.imageCredZ);
//                    idcardphoto = bitmapToBase64(ImageToBitmap(jo.opt("imgPath").toString(), this));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @SuppressLint("NewApi")
//    @Override
//    public void onResultData(Object o) {
//        DictItemsModel.ResultDTO resultDTO = (DictItemsModel.ResultDTO) o;
////        ToastUtils.showLong("????????????=="+resultDTO.toString());
//        tempFaceInfoModel.getFaceInfoData().forEach(n -> {
//            if (n.getFaceInfoId() == 4) {//??????
//                n.setFaceCenterTextString(resultDTO.getText());
//                n.setJobs(resultDTO.getValue());
//            }
//        });
//        faceInfoManagerListAdapter.setNewData(tempFaceInfoModel.getFaceInfoData());
//        activityFaceexpBinding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
//        setFaceImageData();
//    }
//
//    private void setFaceImageData() {
//        Bitmap bmp = null;
//        if (!Const.GEEMIFACE_IMAGE.contains("face_image")) {
//            faceImageBase = Const.GEEMIFACE_IMAGE;
//            KLog.i("faceImageBase====" + faceImageBase);
//            bmp = base64ToBitmap(faceImageBase);
//            Glide.with(this).load(new BitmapDrawable(bmp)).apply(requestOptions).into(activityFaceexpBinding.includeFaceexp.faceexpImageRight);
//        } else if (personInfoByidCardModel != null && personInfoByidCardModel.getResult() != null) {
//            faceImageBase = personInfoByidCardModel.getResult().getFacePhoto();
//            Glide.with(this).load(personInfoByidCardModel.getResult().getFacePhoto()).apply(requestOptions).into(activityFaceexpBinding.includeFaceexp.faceexpImageRight);
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        DialogUtil.dismiss();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        DialogUtil.dismiss();
//        //????????????
//        //??????(????????????Activity???Fragment???onDestory?????????)
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
//        if (idCardReader != null) {
//            MyUtil.close(powerCtl, idCardReader);
//            running = false;
//            //????????????
//            mHandler.removeCallbacks(readTask);
//            Const.GEEMIFACE_IMAGE = "face_image";
//            finish();
//            KLog.i("idCardReader========");
//        }
//    }
//}

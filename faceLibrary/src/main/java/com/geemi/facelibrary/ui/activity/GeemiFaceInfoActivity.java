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
// * 人员录入
// */
//public class GeemiFaceInfoActivity extends GeemiBaseActivity implements IResultDataCallbace {
//    private ActivityFaceexpBinding activityFaceexpBinding;
//
//    private DialogFaceinfoUnitBinding dialogFaceinfoUnitBinding;
//
//    private int SERVICETEAM = 1;//班组
//
//    private int COMPANY = 2;//单位
//
//    private RequestOptions requestOptions = new RequestOptions()
//            .placeholder(R.mipmap.aaws)
//            .error(R.mipmap.aaws);
//
//    //显示标识
//    private int faceTag;
//    //刷卡标识
//    private boolean isCard;
//    //测试种类数据源
////    private FaceInfoTypeModel faceInfoTypeModel;
//    //测试数据源
//    private TempFaceInfoModel tempFaceInfoModel;
//    //选中的单条数据
//    private TempFaceInfoModel.FaceInfoDataDTO faceInfoDataDTOItem;
//    //适配器
//    private FaceInfoManagerListAdapter faceInfoManagerListAdapter;
//    //底部弹窗对象
//    private CustomDialog customDialog;
//    //模块供电控制
//    private PowerCtl powerCtl;
//    //二代证读头
//    private IDCardReader idCardReader;
//    //单位信息
//    private AllCompanyModel allCompanyModel;
//    //班组信息
//    private AllCompanyModel allServiceTeam;
//    //工种信息
//    private DictItemsModel dictItemsModel;
//    //个人信息
//    private PersonInfoByidCardModel personInfoByidCardModel;
//    private boolean bopen = false;
//
//    boolean running = false;
//    //读到二代证类型,
//    int retType = 0;
//    final int ID_CN = 1;//国内居民身份证
//    final int ID_PRP = 2;//外国人永居证
//    final int ID_HK_TW = 3;//港澳台居住证
//
//    private IDCardInfo idCardInfo = new IDCardInfo();
//
//    private String projectId;//项目ID
//    private String idCard = "";//身份证号
//    private String name = "";//姓名
//    private String phone = "";//手机号
//    private String age = "";//年龄
//    private String jobs = "";//工种
//    private String companyId = "";//单位id
//    private String companyName = "";//单位名称
//    private String teamId = "";//班组id
//    private String teamName = "";//班组名称
//    private String sex = "";//性别
//    private String address = "";//住址
//    //人脸信息
//    private String faceImageBase = "";
//    //isteamleader
//    private String isteamleader = "0";
//
//    private String idcardphoto = "";// 身份证前
//
//    private String idcardreversephoto = "";//身份证后
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        activityFaceexpBinding = DataBindingUtil.setContentView(this, R.layout.activity_faceexp);
//        GeemiFaceManager.getInstance().addActivity(this);
//        ImmersionBar.with(this)
//                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
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
//        //人脸图片点击
//        activityFaceexpBinding.includeFaceexp.faceexpImageRight.setOnClickListener(v -> {
//            if (ViewOnClickUtils.isFastClick()) {
//                return;
//            }
//
//            if (!bopen) {
//                ToastUtils.showLong("此设备不支持NFC读取身份证!");
//            } else {
//                if (!isCard) {
//                    ToastUtils.showLong("请先NFC读取身份证信息!");
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
//        //正面
//        activityFaceexpBinding.includeFaceexp.includeCred.linearCredZ.setOnClickListener(v -> {
//            if (ViewOnClickUtils.isFastClick()) {
//                return;
//            }
////            startActivity(new Intent(this, SimpleCameraActivity.class));
////            MyUtil.pictureMode(true, false, 1, this, 8001);
//        });
//        //反面
//        activityFaceexpBinding.includeFaceexp.includeCred.linearCredF.setOnClickListener(v -> {
//            if (ViewOnClickUtils.isFastClick()) {
//                return;
//            }
////            startActivity(new Intent(this, SimpleCameraActivity.class));
////            MyUtil.pictureMode(true, false, 1, this, 8002);
//        });
//    }
//
//    //提交数据检查
//    @SuppressLint("NewApi")
//    private void submitData() {
//        faceInfoManagerListAdapter.setNewData(tempFaceInfoModel.getFaceInfoData());
//        activityFaceexpBinding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
//        tempFaceInfoModel.getFaceInfoData().forEach(n -> {
//            switch (n.getFaceInfoId()) {
//                case 1://姓名
//                    name = n.getFaceCenterTextString();
//                    break;
//                case 2://年龄
//                    age = n.getFaceCenterTextString();
//                    break;
//                case 3://所属单位
//                    companyId = n.getCompanyID();
//                    companyName = n.getFaceCenterTextString();
//                    break;
//                case 7://所属班组
//                    teamId = n.getTeamId();
//                    teamName = n.getFaceCenterTextString();
//                    break;
//                case 4://工种
//                    if (n.getJobs() == null) {
//                        jobs = "";
//                    } else {
//                        jobs = n.getJobs();
//                    }
//                    break;
//                case 5://手机号
//                    phone = n.getFaceCenterTextString();
//                    break;
//                case 6://身份证号
//                    idCard = n.getFaceCenterTextString();
//                    break;
//                case 8://性别
//                    if (n.getFaceCenterTextString().contains("男")) {
//                        sex = "1";
//                    } else {
//                        sex = "2";
//                    }
//                    break;
//                case 9://住址
//                    address = n.getFaceCenterTextString();
//                    break;
//            }
//        });
//        KLog.i("nnnnn====" + projectId + "=====isteamleader====" + isteamleader + "===idCard==" + idCard + "=====sex====" + sex + "====name===" + name + "===phone====" + phone + "===age====" + age + "====jobs===" + jobs + "====address====" + address + "====companyName====" + companyName + "==teamName======" + teamName + "====faceImageBase====" + faceImageBase);
//        KLog.i("====idcardphoto===" + idcardphoto);
//        KLog.i("=====idcardreversephoto=====" + idcardreversephoto);
//        if (projectId.length() == 0) {
//            ToastUtils.showLong("缺少项目ID");
//        } else if (idCard.length() == 0 || idCard.contains("请刷身份证")) {
//            ToastUtils.showLong("缺少身份证号码");
//        } else if (name.length() == 0 || name.contains("请刷身份证")) {
//            ToastUtils.showLong("缺少名字");
//        } else if (phone.length() == 0) {
//            ToastUtils.showLong("缺少手机号");
//        } else if (age.length() == 0) {
//            ToastUtils.showLong("缺少年龄");
//        } else if (jobs.length() == 0 || jobs.contains("请选择")) {
//            ToastUtils.showLong("缺少工种");
//        } else if (companyName.length() == 0 || companyName.contains("请选择")) {
//            ToastUtils.showLong("缺少单位");
//        } else if (teamName.length() == 0 || teamName.contains("请选择")) {
//            ToastUtils.showLong("缺少班组");
//        } else if (faceImageBase.length() == 0 || faceImageBase.contains("face_image")) {
//            ToastUtils.showLong("缺少人脸信息");
//        } else if (sex.contains("0")) {
//            ToastUtils.showLong("缺少性别信息");
//        } else if (address.length() == 0) {
//            ToastUtils.showLong("缺少地址信息");
//        } else if (idcardphoto.length() == 0) {
//            ToastUtils.showLong("缺少身份证正面信息");
//        } else if (idcardreversephoto.length() == 0) {
//            ToastUtils.showLong("缺少身份证反面信息");
//        } else {
//            savePerson();
//        }
//    }
//
//
//    @SuppressLint("NewApi")
//    private void initView() {
//        activityFaceexpBinding.includeFaceexp.superSubmin.setText("提交");
//        faceTag = getIntent().getIntExtra(KEY_INTENT_LEVEL_SAVE, faceTag);
//
//        projectId = getIntent().getStringExtra(KEY_INTENT_PROJECT);
//
//        isCard = getIntent().getBooleanExtra(GEEMIFACE_CARDKEY, false);
//        if (!isCard) {
//            ToastUtils.showLong("请刷卡");
//            //初始化声音池，用于播放提示音
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
//        //title标题
//        activityFaceexpBinding.textFaceTitle.setCenterString(getIntent().getStringExtra(GEEMIFACE_TITLE));
////        faceInfoTypeModel = MyUtil.readFromAssets(this, "faceInfo_type.json", FaceInfoTypeModel.class);
//
//        tempFaceInfoModel = MyUtil.readFromAssets(this, "faceInfoManager.json", TempFaceInfoModel.class);
//
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
//        activityFaceexpBinding.includeFaceexp.recyclerView.setLayoutManager(layoutManager);
//        faceInfoManagerListAdapter = new FaceInfoManagerListAdapter(R.layout.itme_face_info_temp, this);
//        //默认提供5种方法（渐显ALPHAIN 、缩放SCALEIN 、从下到上SLIDEIN_BOTTOM ，从左到右SLIDEIN_LEFT 、从右到左SLIDEIN_RIGHT ）
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
//    //适配器Item点击
//    private void faceInfoClickItemData(TempFaceInfoModel.FaceInfoDataDTO o) {
//        faceInfoDataDTOItem = o;
//        KLog.i("faceInfoDataDTOItem====" + faceInfoDataDTOItem.toString());
////        if (ViewOnClickUtils.isFastClick()) {
////            return;
////        }
//        switch (faceInfoDataDTOItem.getFaceInfoId()) {
//            case 3://所属单位
//                if (allCompanyModel.getResult().size() == 0) {
//                    ToastUtils.showLong("暂无所属单位");
//                    return;
//                }
//                dialogDataInfo(COMPANY);
//                break;
//            case 4://工种
//                if (dictItemsModel.getResult().size() == 0) {
//                    ToastUtils.showLong("暂无工种信息");
//                    return;
//                }
//                popupView();
//                break;
//            case 7://班组
//                if (allServiceTeam == null) {
//                    ToastUtils.showLong("请先选择所属单位！");
//                    return;
//                }
//                if (allServiceTeam.getResult().size() == 0) {
//                    ToastUtils.showLong("暂无班组信息");
//                    return;
//                }
//                dialogDataInfo(SERVICETEAM);
//                break;
//        }
//    }
//
//    //所属单位dialog
//    private void dialogDataInfo(int tag) {
//        customDialog = CustomDialog.show(this, R.layout.dialog_faceinfo_unit, (dialog, rootView) -> {
//            AnimationUtil.springAnimation(rootView, SpringForce.STIFFNESS_VERY_LOW, SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//            dialogFaceinfoUnitBinding = DataBindingUtil.bind(rootView);
//            initDialogListView(tag);
//        }).setCanCancel(false);
//        MyUtil.dialogWindowManager(customDialog);
//    }
//
//    //    SERVICETEAM = 1;//班组
////    COMPANY = 2;//单位
//    @SuppressLint("NewApi")
//    private void initDialogListView(int tag) {
//        dialogFaceinfoUnitBinding.dialogTextTitle.setRightTextIsBold(true);
//        dialogFaceinfoUnitBinding.dialogTextTitle.setLeftTextIsBold(true);
//        MyUtil.setWheelView(dialogFaceinfoUnitBinding.wheelview, this);
//        //设置数据
//        if (tag == COMPANY) {
//            dialogFaceinfoUnitBinding.dialogTextTitle.setCenterString("选择所属单位");
//            dialogFaceinfoUnitBinding.wheelview.setData(allCompanyModel.getResult());
//        } else {
//            dialogFaceinfoUnitBinding.dialogTextTitle.setCenterString("选择所属班组");
//            dialogFaceinfoUnitBinding.wheelview.setData(allServiceTeam.getResult());
//        }
//        //取消
//        dialogFaceinfoUnitBinding.dialogTextTitle.setLeftTvClickListener(() -> {
//            if (ViewOnClickUtils.isFastClick()) {
//                return;
//            }
//            customDialog.doDismiss();
//        });
////        确定
//        dialogFaceinfoUnitBinding.dialogTextTitle.setRightTvClickListener(() -> {
//            if (ViewOnClickUtils.isFastClick()) {
//                return;
//            }
//            allCompanyData(tag);
//        });
//
//    }
//
//    //设置单位 班组数据
//    @SuppressLint("NewApi")
//    private void allCompanyData(int tag) {
//        AllCompanyModel.ResultDTO resultDTO = (AllCompanyModel.ResultDTO) dialogFaceinfoUnitBinding.wheelview.getSelectedItemData();
////        ToastUtils.showLong("选中数据===" + resultDTO.toString());
//        customDialog.doDismiss();
//        if (tag == COMPANY) {//单位
//            tempFaceInfoModel.getFaceInfoData().forEach(n -> {
//                if (n.getFaceInfoId() == 7) {
//                    n.setTeamId("");
//                    n.setFaceCenterTextString("请选择");
//                }
//            });
//            faceInfoDataDTOItem.setCompanyID(resultDTO.getId());
//            //获取班组信息
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
//    //跳转活体检测
//    private void intentSkip() {
//        Bundle bundle = new Bundle();
//        bundle.putInt(KEY_INTENT_LEVEL_SAVE, GEEMIFACE_TAGCODE_MANAGER);
//        bundle.putString(GEEMIFACE_TITLE, getIntent().getStringExtra(GEEMIFACE_TITLE));
//        bundle.putString(KEY_INTENT_PROJECT, projectId);
//        bundle.putString("IdCard", idCardInfo.getId());
//        new XPopup.Builder(this)
//                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                .asBottomList("摄像头", new String[]{"前置摄像头", "后置摄像头"},
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
//    //工种 班组 选择
//    private void popupView() {
//        new XPopup.Builder(this)
//                .popupPosition(PopupPosition.Right)//右边
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
//                            case 1://姓名
//                                n.setFaceCenterTextString(idCardInfo.getName());
//                                break;
//                            case 2://年龄
//                                n.setFaceCenterTextString(MyUtil.evaluate(idCardInfo.getId()));
//                                break;
//                            case 8://性别
//                                n.setFaceCenterTextString(idCardInfo.getSex());
//                                break;
//                            case 9://住址
//                                n.setFaceCenterTextString(idCardInfo.getAddress());
//                                break;
//                            case 6://身份证
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
//    //读二代证
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
//            ToastUtils.showLong("请先打开模块");
//            return;
//        }
//        running = true;
//        //启动线程
//        new Thread(readTask).start();
//    }
//
//    //读二代证线程
//    private Runnable readTask = () -> {
//
//        while (running) {
//            try {
//                //说明：本程序加500ms延时只做参考，是为了二代证不拿开也能读，开发者可视自身环境而定
//                Thread.sleep(500);
//                //开始读卡
//                //为什么这里还加try呢， 因为同一张卡放在天线处不拿走，多次读会抛出异常,
//                //加try是为了可以对同一张卡多次读取
//                //如果要达到只要卡不拿走就只读一次的话，把这个try去掉就可以了
//                idCardReader.findCard(0);
//                idCardReader.selectCard(0);
//
//                Thread.sleep(50);
//
//                retType = idCardReader.readCardEx(0, 0);
//
//                //读到信息
//                if (retType == 1 || retType == 2 || retType == 3) {
//                    //更新UI
//                    runOnUiThread(new Runnable() {
//                        @SuppressLint("NewApi")
//                        @Override
//                        public void run() {
//                            //播放提示音
//                            MyUtil.play(1);
//                            switch (retType) {
//                                case 1://国内居民身份证
//                                    idCardInfo = idCardReader.getLastIDCardInfo();
//                                    if (idCardInfo != null) {
//                                        isCard = true;
//                                        KLog.i("idCardInfo=====" + idCardInfo.toString());
//                                        //头像
//                                        if (idCardInfo.getPhotolength() > 0) {
//                                            //将原始图像数据转成bitmap
//                                            byte[] buf = new byte[WLTService.imgLength];
//                                            if (1 == WLTService.wlt2Bmp(idCardInfo.getPhoto(), buf)) {
//                                                activityFaceexpBinding.includeFaceexp.faceexpImageRight.setImageBitmap(IDPhotoHelper.Bgr2Bitmap(buf));
//                                            }
//                                        }
//                                        mHandler.sendEmptyMessage(202);
//                                    }
//                                    break;
//                                case 2://外国人永居证
//                                    IDPRPCardInfo idprpCardInfo = idCardReader.getLastPRPIDCardInfo();
//                                    break;
//                                case 3://港澳台居住证
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
//    //查询所有参建单位
//    public void getAllCompany() {
//        Map<String, String> params = new HashMap<>();
//        params.put("projectId", projectId);
//        GeemiFaceManager.getHttpModel().getData(1001, GeemiHttpImpl.Method_GET, RouterUrl.GETALLCOMPANY, params, this, false);
//    }
//
//
//    //查询所有班组
//    public void getAllServiceTeam(String companyId) {
//        KLog.i("companyId====" + companyId + "===projectId==" + projectId);
//        Map<String, String> params = new HashMap<>();
//        params.put("projectId", projectId);
//        params.put("companyId", companyId);
//        GeemiFaceManager.getHttpModel().getData(1003, GeemiHttpImpl.Method_GET, RouterUrl.GETTALLSERVICETEAM, params, this, false);
//    }
//
//    //获取工种字典数据
//    public void getDictItems(String itemText) {
//        Map<String, String> params = new HashMap<>();
//        params.put("itemText", itemText);
//        params.put("code", "jobs");
//        GeemiFaceManager.getHttpModel().getData(1002, GeemiHttpImpl.Method_GET, RouterUrl.GETDICTITEMS, params, this, false);
//    }
//
//    //保存人员
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
//    //根据身份证号查询人员信息
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
//            case 1005://身份证查询信息
//                personInfoByidCardModel = JsonUtils.getArrJson(json, PersonInfoByidCardModel.class);
//                if (personInfoByidCardModel.isSuccess()) {
//                    if (personInfoByidCardModel.getResult() != null) {
//                        //isteamleader    0：劳务人员，1：管理人员
//                        KLog.i("isteamleader====" + personInfoByidCardModel.getResult().getIsTeamLeader());
//                        if (personInfoByidCardModel.getResult().getIsTeamLeader().contains("0")) {
//                            activityFaceexpBinding.includeFaceexp.radioLw.setChecked(true);
//                        } else {
//                            activityFaceexpBinding.includeFaceexp.radioGl.setChecked(true);
//                        }
//                        tempFaceInfoModel.getFaceInfoData().forEach(n -> {
//                            switch (n.getFaceInfoId()) {
//                                case 1://姓名
//                                    n.setFaceCenterTextString(personInfoByidCardModel.getResult().getName());
//                                    break;
//                                case 2://年龄
//                                    n.setFaceCenterTextString(String.valueOf(personInfoByidCardModel.getResult().getAge()));
//                                    break;
//                                case 3://所属单位
//                                    n.setFaceCenterTextString(personInfoByidCardModel.getResult().getCompany_name());
//                                    n.setCompanyID(personInfoByidCardModel.getResult().getCompany());
//                                    break;
//                                case 7://所属班组 serviceteam_name
//                                    n.setFaceCenterTextString(personInfoByidCardModel.getResult().getServiceteam_name());
//                                    n.setTeamId(String.valueOf(personInfoByidCardModel.getResult().getServiceteam_id()));
//                                    break;
//                                case 4://工种
//                                    n.setFaceCenterTextString(personInfoByidCardModel.getResult().getJobsName());
//                                    n.setJobs(personInfoByidCardModel.getResult().getJobs());
//                                    break;
//                                case 5://手机号
//                                    n.setFaceCenterTextString(personInfoByidCardModel.getResult().getPhone());
//                                    break;
//                                case 6://身份证号
//                                    n.setFaceCenterTextString(personInfoByidCardModel.getResult().getIdCard());
//                                    break;
//                            }
//                        });
////                        idcardphoto = "";// 身份证前
////                        idcardreversephoto = "";//身份证后
//                        if (personInfoByidCardModel.getResult().getIdcardReversePhoto() == null) {
//                            ToastUtils.showLong("身份证反面信息为空！请重新录入");
//                        } else {
//                            Glide.with(this)
//                                    .load(personInfoByidCardModel.getResult().getIdcardReversePhoto())
//                                    .apply(requestOptions)
//                                    .into(activityFaceexpBinding.includeFaceexp.includeCred.imageCredF);
//                            idcardreversephoto = personInfoByidCardModel.getResult().getIdcardPhoto().toString();
//                        }
//
//                        if (personInfoByidCardModel.getResult().getIdcardPhoto() == null) {
//                            ToastUtils.showLong("身份证正面信息为空！请重新录入");
//                        } else {
//                            Glide.with(this)
//                                    .load(personInfoByidCardModel.getResult().getIdcardPhoto())
//                                    .apply(requestOptions)
//                                    .into(activityFaceexpBinding.includeFaceexp.includeCred.imageCredZ);
//                            idcardphoto = personInfoByidCardModel.getResult().getIdcardPhoto().toString();
//                        }
//                        //获取班组信息
//                        getAllServiceTeam(personInfoByidCardModel.getResult().getCompany());
//                    } else {
//                        ToastUtils.showLong("暂无此人员信息！");
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
////         例如 LocalMedia 里面返回三种path
////         1.media.getPath(); 为原图path
////         2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
////         3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
////         如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//        if (selectList != null && selectList.size() != 0) {
//            switch (requestCode) {
//                case 8002://反面
//                    Glide.with(this)
//                            .load(selectList.get(0).getPath())
//                            .apply(requestOptions)
//                            .into(activityFaceexpBinding.includeFaceexp.includeCred.imageCredF);
//
//                    idcardreversephoto = bitmapToBase64(ImageToBitmap(selectList.get(0).getPath(), this));
//                    break;
//                case 8001://正面
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
//                if (jo.opt("type").toString().contains("0")) {//反面
//                    Glide.with(this)
//                            .load(jo.opt("imgPath"))
//                            .apply(requestOptions)
//                            .into(activityFaceexpBinding.includeFaceexp.includeCred.imageCredF);
//                    idcardreversephoto = bitmapToBase64(ImageToBitmap(jo.opt("imgPath").toString(), this));
//                } else {//正面
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
////        ToastUtils.showLong("工种回调=="+resultDTO.toString());
//        tempFaceInfoModel.getFaceInfoData().forEach(n -> {
//            if (n.getFaceInfoId() == 4) {//工种
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
//        //解除注册
//        //注销(一般是在Activity或Fragment的onDestory中进行)
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
//        if (idCardReader != null) {
//            MyUtil.close(powerCtl, idCardReader);
//            running = false;
//            //销毁线程
//            mHandler.removeCallbacks(readTask);
//            Const.GEEMIFACE_IMAGE = "face_image";
//            finish();
//            KLog.i("idCardReader========");
//        }
//    }
//}

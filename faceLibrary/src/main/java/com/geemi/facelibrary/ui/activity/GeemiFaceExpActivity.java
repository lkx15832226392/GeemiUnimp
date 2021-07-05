//package com.geemi.facelibrary.ui.activity;
//
//import android.annotation.SuppressLint;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//
//import androidx.databinding.DataBindingUtil;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.geemi.facelibrary.R;
//import com.geemi.facelibrary.adapter.FaceInfoListAdapter;
//import com.geemi.facelibrary.databinding.ActivityFaceexpBinding;
//import com.geemi.facelibrary.faceui.FaceLivenessExpActivity;
//import com.geemi.facelibrary.http.GeemiHttpImpl;
//import com.geemi.facelibrary.manager.GeemiFaceListenerManager;
//import com.geemi.facelibrary.manager.GeemiFaceManager;
//import com.geemi.facelibrary.model.Const;
//import com.geemi.facelibrary.model.PersonInfoByidCardModel;
//import com.geemi.facelibrary.model.TempFaceInfoModel;
//import com.geemi.facelibrary.router.RouterUrl;
//import com.geemi.facelibrary.ui.base.GeemiBaseActivity;
//import com.geemi.facelibrary.utils.JsonUtils;
//import com.geemi.facelibrary.utils.MyIntentUtil;
//import com.geemi.facelibrary.utils.MyUtil;
//import com.geemi.facelibrary.utils.ViewOnClickUtils;
//import com.gyf.immersionbar.ImmersionBar;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import me.goldze.mvvmhabit.utils.KLog;
//import me.goldze.mvvmhabit.utils.ToastUtils;
//
//import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_CONTRAST;
//import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_GATHER;
//import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_SING;
//import static com.geemi.facelibrary.model.Const.GEEMIFACE_TITLE;
//import static com.geemi.facelibrary.model.Const.KEY_INTENT_LEVEL_SAVE;
//import static com.geemi.facelibrary.utils.MyUtil.base64ToBitmap;
//
///**
// * 对比 打卡
// */
//public class GeemiFaceExpActivity extends GeemiBaseActivity {
//    private ActivityFaceexpBinding activityFaceexpBinding;
//    private RequestOptions requestOptions = new RequestOptions()
//            .placeholder(R.mipmap.aaws)
//            .error(R.mipmap.aaws);
//    //人脸信息
//    private String faceImageBase;
//    //显示标识
//    private int faceTag;
//    //测试数据源
//    private TempFaceInfoModel tempFaceInfoModel;
//
//    //适配器
//    private FaceInfoListAdapter faceInfoListAdapter;
//
//    private PersonInfoByidCardModel.ResultDTO resultDTO;
//
//
//    private String CLOCKTYPE_START = "start";//上班打卡
//
//    private String CLOCKTYPE_END = "end";// 下班打卡
//
//    private String CLOCKTYPE_UPDATE = "update";//更新打卡
//
////    private String personId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_faceexp);
//        activityFaceexpBinding = DataBindingUtil.setContentView(this, R.layout.activity_faceexp);
//        ImmersionBar.with(this)
//                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
//                .statusBarColor(R.color.face_bj)
//                .init();
//        Bundle bundle = getIntent().getExtras();
//        resultDTO = (PersonInfoByidCardModel.ResultDTO) bundle.getSerializable("personInfo");
//        KLog.i("persion====" + resultDTO.toString());
//        initView();
//        initData(resultDTO);
//        initLinearView();
//    }
//
//    @SuppressLint("NewApi")
//    private void initData(PersonInfoByidCardModel.ResultDTO personInfoByidCardModel) {
//        if (personInfoByidCardModel != null) {
//            activityFaceexpBinding.includeFaceexp.radiogroup.setEnabled(false);
//            activityFaceexpBinding.includeFaceexp.radioLw.setEnabled(false);
//            activityFaceexpBinding.includeFaceexp.radioGl.setEnabled(false);
//            //isteamleader    0：劳务人员，1：管理人员
//            KLog.i("isteamleader===="+personInfoByidCardModel.getIsTeamLeader());
//            if (personInfoByidCardModel.getIsTeamLeader().contains("0")){
//                activityFaceexpBinding.includeFaceexp.radioLw.setChecked(true);
//                activityFaceexpBinding.includeFaceexp.radioLw.setTextColor(Color.RED);
//            }else {
//                activityFaceexpBinding.includeFaceexp.radioGl.setChecked(true);
//                activityFaceexpBinding.includeFaceexp.radioGl.setTextColor(Color.RED);
//            }
//            tempFaceInfoModel.getFaceInfoData().forEach(n -> {
//                switch (n.getFaceInfoId()) {
//                    case 1://姓名
//                        n.setFaceCenterTextString(personInfoByidCardModel.getName());
//                        break;
//                    case 2://年龄
//                        n.setFaceCenterTextString(String.valueOf(personInfoByidCardModel.getAge()));
//                        break;
//                    case 3://所属单位
//                        n.setFaceCenterTextString(personInfoByidCardModel.getCompany_name());
//                        n.setCompanyID(personInfoByidCardModel.getCompany_id());
//                        break;
//                    case 7://所属班组 serviceteam_name
//                        n.setFaceCenterTextString(personInfoByidCardModel.getServiceteam_name());
//                        n.setTeamId(String.valueOf(personInfoByidCardModel.getServiceteam_id()));
//                        break;
//                    case 4://工种
//                        n.setFaceCenterTextString(personInfoByidCardModel.getJobsName());
//                        n.setJobs(personInfoByidCardModel.getJobs());
//                        break;
//                    case 5://手机号
//                        n.setFaceCenterTextString(personInfoByidCardModel.getPhone());
//                        break;
//                    case 6://身份证号
//                        n.setFaceCenterTextString(personInfoByidCardModel.getIdCard());
//                        break;
//                    case 8://性别
//                        if (personInfoByidCardModel.getSex() != null){
//                            if (personInfoByidCardModel.getSex() == 1){
//                                n.setFaceCenterTextString("男");
//                            }else {
//                                n.setFaceCenterTextString("女");
//                            }
//                        }
//                        break;
//                    case 9://住址
//                        n.setFaceCenterTextString(personInfoByidCardModel.getAddress());
//                        break;
//                }
//            });
//            tempFaceInfoModel.setFaceThumbnail(personInfoByidCardModel.getFacePhoto());
//        }
//
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
//        activityFaceexpBinding.includeFaceexp.recyclerView.setLayoutManager(layoutManager);
//        faceInfoListAdapter = new FaceInfoListAdapter(R.layout.itme_face_info);
//        //默认提供5种方法（渐显ALPHAIN 、缩放SCALEIN 、从下到上SLIDEIN_BOTTOM ，从左到右SLIDEIN_LEFT 、从右到左SLIDEIN_RIGHT ）
////        viewModel.orderListAdapter.openLoadAnimation(ALPHAIN);
//        faceInfoListAdapter.setNotDoAnimationCount(1);
//        faceInfoListAdapter.setPreLoadNumber(8);
//        faceInfoListAdapter.setNewData(tempFaceInfoModel.getFaceInfoData());
//        activityFaceexpBinding.includeFaceexp.recyclerView.setAdapter(faceInfoListAdapter);
//
//        KLog.i("faceImageBase====" + faceImageBase);
//        Bitmap bmp = base64ToBitmap(faceImageBase);
//        Glide.with(this).load(tempFaceInfoModel.getFaceThumbnail()).apply(requestOptions).into(activityFaceexpBinding.includeFaceexp.faceexpImageRight);
//        if (faceTag == GEEMIFACE_TAGCODE_CONTRAST) {
//            Glide.with(this).load(new BitmapDrawable(bmp)).apply(requestOptions).into(activityFaceexpBinding.includeFaceexp.faceexpImageLeft);
//            MyUtil.listStartVoew(activityFaceexpBinding.includeFaceexp.faceexpCardViewLeft);
//            MyUtil.listEndView(activityFaceexpBinding.includeFaceexp.superSubmin);
//        } else if (faceTag == GEEMIFACE_TAGCODE_GATHER) {//录入
//            activityFaceexpBinding.includeFaceexp.superSubmin.setText("重新录入");
//        } else if (faceTag == GEEMIFACE_TAGCODE_SING) {
////            personId = getIntent().getStringExtra(KEY_INTENT_PERSONID);
//            if (personInfoByidCardModel.getClockType().contains(CLOCKTYPE_START)) {
//                activityFaceexpBinding.includeFaceexp.superSubmin.setText("上班打卡");
//            } else if (personInfoByidCardModel.getClockType().contains(CLOCKTYPE_END)) {
//                activityFaceexpBinding.includeFaceexp.superSubmin.setText("下班打卡");
//            } else {
//                activityFaceexpBinding.includeFaceexp.superSubmin.setText("更新打卡");
//            }
//        }
//    }
//
//    private void initLinearView() {
//        activityFaceexpBinding.textFaceTitle.setLeftImageViewClickListener(imageView -> {
//            if (ViewOnClickUtils.isFastClick()) {
//                return;
//            }
//            finish();
//        });
//
//        activityFaceexpBinding.btnHuidiao.setOnClickListener(v -> {
//            GeemiFaceListenerManager.getInstance().geemiFaceConmpletData(getIntent().getStringExtra(GEEMIFACE_TITLE) + "回调成功", 1);
//            finish();
//        });
//
//        activityFaceexpBinding.btnError.setOnClickListener(v -> {
//            GeemiFaceListenerManager.getInstance().geemiFaceErrorData(getIntent().getStringExtra(GEEMIFACE_TITLE) + "回调失败", 2);
//            finish();
//        });
//        activityFaceexpBinding.includeFaceexp.superSubmin.setOnClickListener(v -> {
//            if (ViewOnClickUtils.isFastClick()) {
//                return;
//            }
//            switch (faceTag) {
//                case GEEMIFACE_TAGCODE_GATHER://重新录入
//                    Bundle bundle = new Bundle();
//                    //开始采集
//                    bundle.putInt(KEY_INTENT_LEVEL_SAVE, GEEMIFACE_TAGCODE_GATHER);
//                    bundle.putString(GEEMIFACE_TITLE, "人脸录入");
//                    MyIntentUtil.startActivityForResult(this, FaceLivenessExpActivity.class, bundle);
//                    finish();
//                    break;
//                case GEEMIFACE_TAGCODE_SING://上下班打卡
//                    clockPunch();
//                    break;
//            }
//        });
//    }
//
//    @SuppressLint("NewApi")
//    private void initView() {
//        MyUtil.listEndView(activityFaceexpBinding.includeFaceexp.relativeCred,activityFaceexpBinding.includeFaceexp.linearSfz);
//        faceTag = getIntent().getIntExtra(KEY_INTENT_LEVEL_SAVE, faceTag);
//        faceImageBase = Const.GEEMIFACE_IMAGE;
//        activityFaceexpBinding.includeFaceexp.superSubmin.setEnabled(true);
//        activityFaceexpBinding.textFaceTitle.setCenterTextIsBold(true);
//        //title标题
//        activityFaceexpBinding.textFaceTitle.setCenterString(getIntent().getStringExtra(GEEMIFACE_TITLE));
//        tempFaceInfoModel = MyUtil.readFromAssets(this, "faceInfoManager.json", TempFaceInfoModel.class);
//    }
//
//
//    //打卡
//    public void clockPunch() {
//        Map<String, String> params = new HashMap<>();
//        params.put("projectId",resultDTO.getProject_id());
//        params.put("images", faceImageBase);
//        params.put("personId",resultDTO.getId());
//        params.put("clockType", resultDTO.getClockType());
//        GeemiFaceManager.getHttpModel().getData(1001, GeemiHttpImpl.Method_POST, RouterUrl.CLOCKPUNCH, params, this);
//    }
//
//    //人脸打卡检测
//    public void clockFace() {
//        Map<String, String> params = new HashMap<>();
//        params.put("projectId", resultDTO.getProject_id());
//        params.put("images", faceImageBase);
//        GeemiFaceManager.getHttpModel().getData(1002, GeemiHttpImpl.Method_POST, RouterUrl.CLOCKFACE, params, this);
//    }
//
//    @Override
//    public void onComplete(int tag, String json) {
//        KLog.i("tag===" + tag + "=====json====" + json);
//        switch (tag){
//            case 1001:
////                ResultModel resultModel = JsonUtils.getArrJson(json,ResultModel.class);
//                PersonInfoByidCardModel personInfoByidCardModel = JsonUtils.getArrJson(json,PersonInfoByidCardModel.class);
//                ToastUtils.showLong(personInfoByidCardModel.getMessage());
//                if (personInfoByidCardModel.isSuccess()){
//                    resultDTO = personInfoByidCardModel.getResult();
//                    initData(resultDTO);
//                }
//                break;
////            case 1002:
////                PersonInfoByidCardModel personInfoByidCardModel = JsonUtils.getArrJson(json,PersonInfoByidCardModel.class);
////                if (personInfoByidCardModel.isSuccess()){
////                    initData(personInfoByidCardModel.getResult());
////                    return;
////                }
////                ToastUtils.showLong(personInfoByidCardModel.getMessage());
////                break;
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Const.GEEMIFACE_IMAGE = "face_image";
//    }
//}

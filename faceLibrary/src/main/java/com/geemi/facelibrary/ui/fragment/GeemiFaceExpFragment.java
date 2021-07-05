package com.geemi.facelibrary.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.adapter.FaceInfoListAdapter;
import com.geemi.facelibrary.databinding.FragmentFaceexpBinding;
import com.geemi.facelibrary.faceui.FaceLivenessExpActivity;
import com.geemi.facelibrary.manager.GeemiFaceListenerManager;
import com.geemi.facelibrary.model.Const;
import com.geemi.facelibrary.model.PersonInfoByidCardModel;
import com.geemi.facelibrary.model.TempFaceInfoModel;
import com.geemi.facelibrary.router.RouterFragmentPath;
import com.geemi.facelibrary.ui.base.GeemiBaseFragment;
import com.geemi.facelibrary.utils.JsonUtils;
import com.geemi.facelibrary.utils.MyIntentUtil;
import com.geemi.facelibrary.utils.MyUtil;
import com.geemi.facelibrary.utils.ViewOnClickUtils;


import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_CONTRAST;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_GATHER;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_SING;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_TITLE;
import static com.geemi.facelibrary.model.Const.KEY_INTENT_LEVEL_SAVE;
import static com.geemi.facelibrary.utils.MyUtil.base64ToBitmap;

@Route(path = RouterFragmentPath.GeemiRouterPath.PAGER_CLOCKPUNCH)
public class GeemiFaceExpFragment extends GeemiBaseFragment<FragmentFaceexpBinding,GeemiFaceExpViewModel> {
    //适配器
    private FaceInfoListAdapter faceInfoListAdapter;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_faceexp;
    }

    @Override
    public int initVariableId() {
        return BR.viewModule;
    }

    @Override
    protected void initView() {
        MyUtil.listEndView(binding.includeFaceexp.relativeCred,binding.includeFaceexp.linearSfz);
        viewModel.faceImageBase.set(Const.GEEMIFACE_IMAGE);
        binding.includeFaceexp.superSubmin.setEnabled(true);
        viewModel.tempFaceInfoModel.set(MyUtil.readFromAssets(baseActivity, "faceInfoManager.json", TempFaceInfoModel.class));
    }

    @Override
    protected void initLinearView() {
        binding.includeFaceexp.superSubmin.setOnClickListener(v -> {
            if (ViewOnClickUtils.isFastClick()) {
                return;
            }
            switch (viewModel.faceTag.get()) {
                case GEEMIFACE_TAGCODE_GATHER://重新录入
                    Bundle bundle = new Bundle();
                    //开始采集
                    bundle.putInt(KEY_INTENT_LEVEL_SAVE, GEEMIFACE_TAGCODE_GATHER);
                    bundle.putString(GEEMIFACE_TITLE, "人脸录入");
                    MyIntentUtil.startActivityForResult(baseActivity, FaceLivenessExpActivity.class, bundle);
                    baseActivity.finish();
                    break;
                case GEEMIFACE_TAGCODE_SING://上下班打卡
                    viewModel.clockPunch();
                    break;
            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    public void initData() {
        super.initData();
        if (getArguments().get("personInfo") == null){
            ToastUtils.showLong("数据获取失败");
            getActivity().finish();
            return;
        }
        viewModel.faceTag.set((Integer) getArguments().get(KEY_INTENT_LEVEL_SAVE));
        viewModel.resultDTO.set((PersonInfoByidCardModel.ResultDTO) getArguments().get("personInfo"));

        KLog.i("resultDTO====="+viewModel.resultDTO.get().getId()+"-===key==="+viewModel.faceTag.get());

        if (viewModel.resultDTO.get() != null) {
            binding.includeFaceexp.radiogroup.setEnabled(false);
            binding.includeFaceexp.radioLw.setEnabled(false);
            binding.includeFaceexp.radioGl.setEnabled(false);
            //isteamleader    0：劳务人员，1：管理人员
            KLog.i("isteamleader===="+viewModel.resultDTO.get().getIsTeamLeader());
            if (viewModel.resultDTO.get().getIsTeamLeader().contains("0")){
                binding.includeFaceexp.radioLw.setChecked(true);
                binding.includeFaceexp.radioLw.setTextColor(Color.RED);
            }else {
                binding.includeFaceexp.radioGl.setChecked(true);
                binding.includeFaceexp.radioGl.setTextColor(Color.RED);
            }
            viewModel.tempFaceInfoModel.get().getFaceInfoData().forEach(n -> {
                switch (n.getFaceInfoId()) {
                    case 1://姓名
                        n.setFaceCenterTextString(viewModel.resultDTO.get().getName());
                        break;
                    case 2://年龄
                        n.setFaceCenterTextString(String.valueOf(viewModel.resultDTO.get().getAge()));
                        break;
                    case 3://所属单位
                        n.setFaceCenterTextString(viewModel.resultDTO.get().getCompany_name());
                        n.setCompanyID(viewModel.resultDTO.get().getCompany_id());
                        break;
                    case 7://所属班组 serviceteam_name
                        n.setFaceCenterTextString(viewModel.resultDTO.get().getServiceteam_name());
                        n.setTeamId(String.valueOf(viewModel.resultDTO.get().getServiceteam_id()));
                        break;
                    case 4://工种
                        n.setFaceCenterTextString(viewModel.resultDTO.get().getJobsName());
                        n.setJobs(viewModel.resultDTO.get().getJobs());
                        break;
                    case 5://手机号
                        n.setFaceCenterTextString(viewModel.resultDTO.get().getPhone());
                        break;
                    case 6://身份证号
                        n.setFaceCenterTextString(viewModel.resultDTO.get().getIdCard());
                        break;
                    case 8://性别
                        if (viewModel.resultDTO.get().getSex() != null){
                            if (viewModel.resultDTO.get().getSex() == 1){
                                n.setFaceCenterTextString("男");
                            }else {
                                n.setFaceCenterTextString("女");
                            }
                        }
                        break;
                    case 9://住址
                        n.setFaceCenterTextString(viewModel.resultDTO.get().getAddress());
                        break;
                }
            });
            viewModel.tempFaceInfoModel.get().setFaceThumbnail(viewModel.resultDTO.get().getFacePhoto());
        }

        GridLayoutManager layoutManager = new GridLayoutManager(baseActivity, 1, LinearLayoutManager.VERTICAL, false);
        binding.includeFaceexp.recyclerView.setLayoutManager(layoutManager);
        faceInfoListAdapter = new FaceInfoListAdapter(R.layout.itme_face_info);
        //默认提供5种方法（渐显ALPHAIN 、缩放SCALEIN 、从下到上SLIDEIN_BOTTOM ，从左到右SLIDEIN_LEFT 、从右到左SLIDEIN_RIGHT ）
//        viewModel.orderListAdapter.openLoadAnimation(ALPHAIN);
        faceInfoListAdapter.setNotDoAnimationCount(1);
        faceInfoListAdapter.setPreLoadNumber(8);
        faceInfoListAdapter.setNewData(viewModel.tempFaceInfoModel.get().getFaceInfoData());
        binding.includeFaceexp.recyclerView.setAdapter(faceInfoListAdapter);

        KLog.i("faceImageBase====" + viewModel.faceImageBase.get());
        Bitmap bmp = base64ToBitmap(viewModel.faceImageBase.get());
        Glide.with(this).load(viewModel.tempFaceInfoModel.get().getFaceThumbnail()).apply(viewModel.requestOptions).into(binding.includeFaceexp.faceexpImageRight);
        if (viewModel.faceTag.get() == GEEMIFACE_TAGCODE_CONTRAST) {
            Glide.with(this).load(new BitmapDrawable(bmp)).apply(viewModel.requestOptions).into(binding.includeFaceexp.faceexpImageLeft);
            MyUtil.listStartVoew(binding.includeFaceexp.faceexpCardViewLeft);
            MyUtil.listEndView(binding.includeFaceexp.superSubmin);
        } else if (viewModel.faceTag.get() == GEEMIFACE_TAGCODE_GATHER) {//录入
            binding.includeFaceexp.superSubmin.setText("重新录入");
        } else if (viewModel.faceTag.get() == GEEMIFACE_TAGCODE_SING) {
//            personId = getIntent().getStringExtra(KEY_INTENT_PERSONID);
            if (viewModel.resultDTO.get().getClockType().contains(viewModel.CLOCKTYPE_START.get())) {
                binding.includeFaceexp.superSubmin.setText("上班打卡");
            } else if (viewModel.resultDTO.get().getClockType().contains(viewModel.CLOCKTYPE_END.get())) {
                binding.includeFaceexp.superSubmin.setText("下班打卡");
            } else {
                binding.includeFaceexp.superSubmin.setText("更新打卡");
            }
        }
    }

    @Override
    protected void onComplete(int tag, String json) {
        super.onComplete(tag, json);
        KLog.i("tag===" + tag + "=====json====" + json);
        switch (tag){
            case 1001:
                PersonInfoByidCardModel personInfoByidCardModel = JsonUtils.getArrJson(json,PersonInfoByidCardModel.class);
                ToastUtils.showLong(personInfoByidCardModel.getMessage());
                if (personInfoByidCardModel.isSuccess()){
                    viewModel.resultDTO.set(personInfoByidCardModel.getResult());
                    initData();
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Const.GEEMIFACE_IMAGE = "face_image";
    }

}

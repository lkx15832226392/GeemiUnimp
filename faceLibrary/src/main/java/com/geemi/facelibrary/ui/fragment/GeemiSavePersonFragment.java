package com.geemi.facelibrary.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.adapter.FaceInfoManagerListAdapter;
import com.geemi.facelibrary.databinding.DialogFaceinfoUnitBinding;
import com.geemi.facelibrary.databinding.FragmentSavepersonBinding;
import com.geemi.facelibrary.faceui.FaceLivenessExpActivity;
import com.geemi.facelibrary.interfaces.IResultDataCallbace;
import com.geemi.facelibrary.model.AllCompanyModel;
import com.geemi.facelibrary.model.Const;
import com.geemi.facelibrary.model.DictItemsModel;
import com.geemi.facelibrary.model.MessageEventbus;
import com.geemi.facelibrary.model.PersonInfoByidCardModel;
import com.geemi.facelibrary.model.ResultModel;
import com.geemi.facelibrary.model.TempFaceInfoModel;
import com.geemi.facelibrary.router.RouterFragmentPath;
import com.geemi.facelibrary.ui.base.GeemiBaseFragment;
import com.geemi.facelibrary.ui.popup.FaceInfoTypePopupView;
import com.geemi.facelibrary.utils.AnimationUtil;
import com.geemi.facelibrary.utils.DialogUtil;
import com.geemi.facelibrary.utils.JsonUtils;
import com.geemi.facelibrary.utils.MyIntentUtil;
import com.geemi.facelibrary.utils.MyUtil;
import com.geemi.facelibrary.utils.ViewOnClickUtils;
import com.kongzue.dialog.v2.BottomMenu;
import com.kongzue.dialog.v2.CustomDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupPosition;
import com.zkteco.android.IDReader.IDPhotoHelper;
import com.zkteco.android.IDReader.WLTService;
import com.zkteco.android.biometric.module.idcard.meta.IDCardInfo;
import com.zkteco.android.biometric.module.idcard.meta.IDPRPCardInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static com.geemi.facelibrary.model.Const.GEEMIFACE_CARDKEY;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_MANAGER;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_TITLE;
import static com.geemi.facelibrary.model.Const.KEY_CAMERA_FACING;
import static com.geemi.facelibrary.model.Const.KEY_INTENT_LEVEL_SAVE;
import static com.geemi.facelibrary.model.Const.KEY_INTENT_PROJECT;
import static com.geemi.facelibrary.utils.MyUtil.ImageToBitmap;
import static com.geemi.facelibrary.utils.MyUtil.base64ToBitmap;
import static com.geemi.facelibrary.utils.MyUtil.bitmapToBase64;

@Route(path = RouterFragmentPath.GeemiRouterPath.PAGER_SAVEPERSON)
public class GeemiSavePersonFragment extends GeemiBaseFragment<FragmentSavepersonBinding, GeemiSavePersonViewModel> implements IResultDataCallbace {
    //?????????
    private FaceInfoManagerListAdapter faceInfoManagerListAdapter;
    //??????????????????
    private CustomDialog customDialog;

    private DialogFaceinfoUnitBinding dialogFaceinfoUnitBinding;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_saveperson;
    }

    @Override
    public int initVariableId() {
        return BR.viewModule;
    }

    @Override
    protected void initView() {
        binding.includeFaceexp.superSubmin.setText("??????");
        viewModel.faceTag.set((Integer) getArguments().get(KEY_INTENT_LEVEL_SAVE));
        viewModel.projectId.set((String) getArguments().get(KEY_INTENT_PROJECT));
        viewModel.isCard.set((Boolean) getArguments().get(GEEMIFACE_CARDKEY));
        KLog.i("projectId====" + viewModel.projectId.get() + "====faceTag===" + viewModel.faceTag.get() + "==isCard===" + viewModel.isCard.get());
        if (!viewModel.isCard.get()) {
            ToastUtils.showLong("?????????");
            //??????????????????????????????????????????
            MyUtil.getInstance(baseActivity);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    viewModel.idCardReader.set(MyUtil.open(viewModel.powerCtl.get()));
                    if (viewModel.idCardReader.get() != null) {
                        KLog.i("viewModel.idCardReader====" + viewModel.idCardReader.get());
                        viewModel.bopen.set(true);
                    }
                    mHandler.sendEmptyMessage(201);
                }
            }.start();
        }

        binding.includeFaceexp.superSubmin.setEnabled(true);
//        faceInfoTypeModel = MyUtil.readFromAssets(this, "faceInfo_type.json", FaceInfoTypeModel.class);
        viewModel.tempFaceInfoModel.set(MyUtil.readFromAssets(baseActivity, "faceInfoManager.json", TempFaceInfoModel.class));

        GridLayoutManager layoutManager = new GridLayoutManager(baseActivity, 1, LinearLayoutManager.VERTICAL, false);
        binding.includeFaceexp.recyclerView.setLayoutManager(layoutManager);
        faceInfoManagerListAdapter = new FaceInfoManagerListAdapter(R.layout.itme_face_info_temp, o -> {
            KLog.i("idCard===="+o.toString());
            viewModel.idCard.set(o.toString());
        });
        //????????????5??????????????????ALPHAIN ?????????SCALEIN ???????????????SLIDEIN_BOTTOM ???????????????SLIDEIN_LEFT ???????????????SLIDEIN_RIGHT ???
//        viewModel.orderListAdapter.openLoadAnimation(ALPHAIN);
        faceInfoManagerListAdapter.setNotDoAnimationCount(1);
        faceInfoManagerListAdapter.setPreLoadNumber(8);
        faceInfoManagerListAdapter.setNewData(viewModel.tempFaceInfoModel.get().getFaceInfoData());
        binding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("NewApi")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 201://
                    readIdCard();
                    break;
                case 202://
                    viewModel.tempFaceInfoModel.get().getFaceInfoData().forEach(n -> {
                        switch (n.getFaceInfoId()) {
                            case 1://??????
                                n.setFaceCenterTextString(viewModel.idCardInfo.get().getName());
                                break;
                            case 2://??????
                                n.setFaceCenterTextString(MyUtil.evaluate(viewModel.idCardInfo.get().getId()));
                                break;
                            case 8://??????
                                n.setFaceCenterTextString(viewModel.idCardInfo.get().getSex());
                                break;
                            case 9://??????
                                n.setFaceCenterTextString(viewModel.idCardInfo.get().getAddress());
                                break;
                            case 6://?????????
                                n.setFaceCenterTextString(viewModel.idCardInfo.get().getId());
                                viewModel.getPersonInfoByIdcard(viewModel.idCardInfo.get().getId());
                                break;
                        }
                    });
                    faceInfoManagerListAdapter.setNewData(viewModel.tempFaceInfoModel.get().getFaceInfoData());
                    binding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
                    MyUtil.listEndView(binding.includeFaceexp.linearSfz);
                    break;
            }
        }
    };

    //????????????
    @SuppressLint("NewApi")
    private void readIdCard() {
        if (!viewModel.bopen.get()) {
            viewModel.tempFaceInfoModel.get().getFaceInfoData().forEach(n -> {
                if (n.getFaceInfoId() == 1 || n.getFaceInfoId() == 6 || n.getFaceInfoId() == 8) {
                    n.setFaceIsEnabled(String.valueOf(true));
                    n.setFaceCenterTextString("");
                }
            });
            faceInfoManagerListAdapter.setNewData(viewModel.tempFaceInfoModel.get().getFaceInfoData());
            binding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
            faceInfoManagerListAdapter.notifyDataSetChanged();
            ToastUtils.showLong("??????????????????");
            return;
        }
        viewModel.running.set(true);
        //????????????
        new Thread(readTask).start();
    }

    //??????????????????
    private Runnable readTask = () -> {
        while (viewModel.running.get()) {
            try {
                //?????????????????????500ms?????????????????????????????????????????????????????????????????????????????????????????????
                Thread.sleep(500);
                //????????????
                //?????????????????????try?????? ?????????????????????????????????????????????????????????????????????,
                //???try??????????????????????????????????????????
                //??????????????????????????????????????????????????????????????????try??????????????????
                viewModel.idCardReader.get().findCard(0);
                viewModel.idCardReader.get().selectCard(0);

                Thread.sleep(50);

                viewModel.retType.set(viewModel.idCardReader.get().readCardEx(0, 0));

                //????????????
                if (viewModel.retType.get() == 1 || viewModel.retType.get() == 2 || viewModel.retType.get() == 3) {
                    //??????UI
                    baseActivity.runOnUiThread(() -> {
                        //???????????????
                        MyUtil.play(1);
                        switch (viewModel.retType.get()) {
                            case 1://?????????????????????
                                viewModel.idCardInfo.set(viewModel.idCardReader.get().getLastIDCardInfo());
                                viewModel.idCard.set(viewModel.idCardInfo.get().getId());
                                if (viewModel.idCardInfo.get() != null) {
                                    viewModel.isCard.set(true);
                                    KLog.i("idCardInfo=====" + viewModel.idCardInfo.get().toString());
                                    //??????
                                    if (viewModel.idCardInfo.get().getPhotolength() > 0) {
                                        //???????????????????????????bitmap
                                        byte[] buf = new byte[WLTService.imgLength];
                                        if (1 == WLTService.wlt2Bmp(viewModel.idCardInfo.get().getPhoto(), buf)) {
                                            binding.includeFaceexp.faceexpImageRight.setImageBitmap(IDPhotoHelper.Bgr2Bitmap(buf));
                                        }
                                    }
                                    mHandler.sendEmptyMessage(202);
                                }
                                break;
                            case 2://??????????????????
                                IDPRPCardInfo idprpCardInfo = viewModel.idCardReader.get().getLastPRPIDCardInfo();
                                break;
                            case 3://??????????????????
                                IDCardInfo idCardHK = viewModel.idCardReader.get().getLastIDCardInfo();
                                break;
                        }
                    });
                }
            } catch (Exception e) {
                //LogHelper.e("errcode:" + e.getErrorCode() + ",internalerrorcode:" + e.getInternalErrorCode());
                //continue;
            }
        }
    };


    //?????????Item??????
    private void faceInfoClickItemData(TempFaceInfoModel.FaceInfoDataDTO o) {
        viewModel.faceInfoDataDTOItem.set(o);
        KLog.i("faceInfoDataDTOItem====" + viewModel.faceInfoDataDTOItem.get().toString());
//        if (ViewOnClickUtils.isFastClick()) {
//            return;
//        }
        switch (viewModel.faceInfoDataDTOItem.get().getFaceInfoId()) {
            case 3://????????????
//                KLog.i("allCompanyModel===="+viewModel.allCompanyModel.get().getResult().size());
                if (viewModel.allCompanyModel.get().getResult().size() == 0) {
                    ToastUtils.showLong("??????????????????");
                    return;
                }
                dialogDataInfo(viewModel.COMPANY.get());
                break;
            case 4://??????
                if (viewModel.dictItemsModel.get().getResult().size() == 0) {
                    ToastUtils.showLong("??????????????????");
                    return;
                }
                popupView();
                break;
            case 7://??????
                if (viewModel.allServiceTeam.get().getResult().size() == 0) {
                    ToastUtils.showLong("??????????????????");
                    return;
                }
                dialogDataInfo(viewModel.SERVICETEAM.get());
                break;
        }
    }

    //????????????dialog
    private void dialogDataInfo(int tag) {
        customDialog = CustomDialog.show(baseActivity, R.layout.dialog_faceinfo_unit, (dialog, rootView) -> {
            AnimationUtil.springAnimation(rootView, SpringForce.STIFFNESS_VERY_LOW, SpringForce.DAMPING_RATIO_LOW_BOUNCY);
            dialogFaceinfoUnitBinding = DataBindingUtil.bind(rootView);
            initDialogListView(tag);
        }).setCanCancel(false);
        MyUtil.dialogWindowManager(customDialog);
    }

    //    SERVICETEAM = 1;//??????
//    COMPANY = 2;//??????
    @SuppressLint("NewApi")
    private void initDialogListView(int tag) {
        dialogFaceinfoUnitBinding.dialogTextTitle.setRightTextIsBold(true);
        dialogFaceinfoUnitBinding.dialogTextTitle.setLeftTextIsBold(true);
        MyUtil.setWheelView(dialogFaceinfoUnitBinding.wheelview, baseActivity);
        //????????????
        if (tag == viewModel.COMPANY.get()) {
            KLog.i("????????????=====" + viewModel.allCompanyModel.get().getResult().size());
            dialogFaceinfoUnitBinding.dialogTextTitle.setCenterString("??????????????????");
            dialogFaceinfoUnitBinding.wheelview.setData(viewModel.allCompanyModel.get().getResult());
        } else {
            dialogFaceinfoUnitBinding.dialogTextTitle.setCenterString("??????????????????");
            dialogFaceinfoUnitBinding.wheelview.setData(viewModel.allServiceTeam.get().getResult());
        }
        //??????
        dialogFaceinfoUnitBinding.dialogTextTitle.setLeftTvClickListener(() -> {
            if (ViewOnClickUtils.isFastClick()) {
                return;
            }
            customDialog.doDismiss();
        });
//        ??????
        dialogFaceinfoUnitBinding.dialogTextTitle.setRightTvClickListener(() -> {
            if (ViewOnClickUtils.isFastClick()) {
                return;
            }
            allCompanyData(tag);
        });

    }

    //???????????? ????????????
    @SuppressLint("NewApi")
    private void allCompanyData(int tag) {
        AllCompanyModel.ResultDTO resultDTO = (AllCompanyModel.ResultDTO) dialogFaceinfoUnitBinding.wheelview.getSelectedItemData();
//        ToastUtils.showLong("????????????===" + resultDTO.toString());
        customDialog.doDismiss();
        if (tag == viewModel.COMPANY.get()) {//??????
            viewModel.tempFaceInfoModel.get().getFaceInfoData().forEach(n -> {
                if (n.getFaceInfoId() == 7) {
                    n.setTeamId("");
                    n.setFaceCenterTextString("?????????");
                }
            });
            viewModel.faceInfoDataDTOItem.get().setCompanyID(resultDTO.getId());
            //??????????????????
            viewModel.getAllServiceTeam(viewModel.faceInfoDataDTOItem.get().getCompanyID());
        } else {
            viewModel.faceInfoDataDTOItem.get().setTeamId(resultDTO.getId());
        }
        viewModel.faceInfoDataDTOItem.get().setFaceCenterTextString(resultDTO.getName());
        faceInfoManagerListAdapter.setNewData(viewModel.tempFaceInfoModel.get().getFaceInfoData());
        binding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
        KLog.i("faceInfoDataDTOItem======" + viewModel.tempFaceInfoModel.get().getFaceInfoData().toString());
    }

    //??????????????????
    private void intentSkip() {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_INTENT_LEVEL_SAVE, GEEMIFACE_TAGCODE_MANAGER);
        bundle.putString(GEEMIFACE_TITLE, baseActivity.getIntent().getStringExtra(GEEMIFACE_TITLE));
        bundle.putString(KEY_INTENT_PROJECT, viewModel.projectId.get());
        bundle.putString("IdCard", viewModel.idCard.get());

        List<String> list = new ArrayList<>();
        list.add("???????????????");
        list.add("???????????????");
        BottomMenu.show((AppCompatActivity) getActivity(), list, (text, index) -> {
            if (index == 0) {
                bundle.putInt(KEY_CAMERA_FACING, Camera.CameraInfo.CAMERA_FACING_FRONT);
            } else {
                bundle.putInt(KEY_CAMERA_FACING, Camera.CameraInfo.CAMERA_FACING_BACK);
            }
            MyIntentUtil.startActivityForResult(baseActivity, FaceLivenessExpActivity.class, bundle);
        }, true).setTitle("?????????");
    }

    //?????? ?????? ??????
    private void popupView() {
        new XPopup.Builder(baseActivity)
                .popupPosition(PopupPosition.Right)//??????
                .hasStatusBar(false)
                .dismissOnTouchOutside(false)
                .enableDrag(true)
                .asCustom(new FaceInfoTypePopupView(baseActivity, viewModel.dictItemsModel.get().getResult(), this))
                .show();
    }

    //??????????????????
    @SuppressLint("NewApi")
    private void submitData() {
        faceInfoManagerListAdapter.setNewData(viewModel.tempFaceInfoModel.get().getFaceInfoData());
        binding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
        viewModel.tempFaceInfoModel.get().getFaceInfoData().forEach(n -> {
            switch (n.getFaceInfoId()) {
                case 1://??????
                    viewModel.name.set(n.getFaceCenterTextString());
                    break;
                case 2://??????
                    viewModel.age.set(n.getFaceCenterTextString());
                    break;
                case 3://????????????
                    viewModel.companyId.set(n.getCompanyID());
                    viewModel.companyName.set(n.getFaceCenterTextString());
                    break;
                case 7://????????????
                    viewModel.teamId.set(n.getTeamId());
                    viewModel.teamName.set(n.getFaceCenterTextString());
                    break;
                case 4://??????
                    if (n.getJobs() == null) {
                        viewModel.jobs.set("");
                    } else {
                        viewModel.jobs.set(n.getJobs());
                    }
                    break;
                case 5://?????????
                    viewModel.phone.set(n.getFaceCenterTextString());
                    break;
                case 6://????????????
                    viewModel.idCard.set(n.getFaceCenterTextString());
                    break;
                case 8://??????
                    if (n.getFaceCenterTextString().contains("???")) {
                        viewModel.sex.set("1");
                    } else {
                        viewModel.sex.set("2");
                    }
                    break;
                case 9://??????
                    viewModel.address.set(n.getFaceCenterTextString());
                    break;
            }
        });
        KLog.i("nnnnn====" + viewModel.projectId.get() + "=====isteamleader====" + viewModel.isteamleader.get() + "===idCard==" + viewModel.idCard.get() + "=====sex====" + viewModel.sex.get() + "====name===" + viewModel.name.get() + "===phone====" + viewModel.phone.get() + "===age====" + viewModel.age.get() + "====jobs===" + viewModel.jobs.get() + "====address====" + viewModel.address.get() + "====companyName====" + viewModel.companyName.get() + "==teamName======" + viewModel.teamName.get() + "====faceImageBase====" + viewModel.faceImageBase.get());
        KLog.i("====idcardphoto===" + viewModel.idcardphoto.get());
        KLog.i("=====idcardreversephoto=====" + viewModel.idcardreversephoto.get());
        if (viewModel.projectId.get().length() == 0) {
            ToastUtils.showLong("????????????ID");
        } else if (viewModel.idCard.get().length() == 0 || viewModel.idCard.get().contains("???????????????")) {
            ToastUtils.showLong("?????????????????????");
        } else if (viewModel.name.get().length() == 0 || viewModel.name.get().contains("???????????????")) {
            ToastUtils.showLong("????????????");
        } else if (viewModel.phone.get().length() == 0) {
            ToastUtils.showLong("???????????????");
        } else if (viewModel.age.get().length() == 0) {
            ToastUtils.showLong("????????????");
        } else if (viewModel.jobs.get().length() == 0 || viewModel.jobs.get().contains("?????????")) {
            ToastUtils.showLong("????????????");
        } else if (viewModel.companyName.get().length() == 0 || viewModel.companyName.get().contains("?????????")) {
            ToastUtils.showLong("????????????");
        } else if (viewModel.teamName.get().length() == 0 || viewModel.teamName.get().contains("?????????")) {
            ToastUtils.showLong("????????????");
        } else if (viewModel.faceImageBase.get().length() == 0 || viewModel.faceImageBase.get().contains("face_image")) {
            ToastUtils.showLong("??????????????????");
        } else if (viewModel.sex.get().contains("0")) {
            ToastUtils.showLong("??????????????????");
        } else if (viewModel.address.get().length() == 0) {
            ToastUtils.showLong("??????????????????");
        } else if (viewModel.idcardphoto.get().length() == 0) {
            ToastUtils.showLong("???????????????????????????");
        } else if (viewModel.idcardreversephoto.get().length() == 0) {
            ToastUtils.showLong("???????????????????????????");
        } else {
            viewModel.savePerson();
        }
    }


    @Override
    protected void initLinearView() {
        binding.includeFaceexp.superSubmin.setOnClickListener(v -> {
            if (ViewOnClickUtils.isFastClick()) {
                return;
            }
            submitData();
        });

        faceInfoManagerListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            faceInfoClickItemData((TempFaceInfoModel.FaceInfoDataDTO) adapter.getData().get(position));
        });
        //??????????????????
        binding.includeFaceexp.faceexpImageRight.setOnClickListener(v -> {
            if (ViewOnClickUtils.isFastClick()) {
                return;
            }
            KLog.i("bopen==-===" + viewModel.bopen.get() + "=====" + viewModel.isCard.get());
            if (!viewModel.bopen.get()) {
                ToastUtils.showLong("??????????????????NFC???????????????!");
            }
            if (!viewModel.isCard.get()) {
                ToastUtils.showLong("??????NFC?????????????????????!");
                return;
            }
            if (viewModel.idCard.get().length() == 0){
                ToastUtils.showLong("???????????????????????????");
                return;
            }
            intentSkip();
        });

        binding.includeFaceexp.radiogroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_lw) {
                viewModel.isteamleader.set("0");
            } else if (checkedId == R.id.radio_gl) {
                viewModel.isteamleader.set("1");
            }
        });

        //??????
        binding.includeFaceexp.includeCred.linearCredZ.setOnClickListener(v -> {
            if (ViewOnClickUtils.isFastClick()) {
                return;
            }
//            startActivity(new Intent(this, SimpleCameraActivity.class));
            MyUtil.pictureMode(true, false, 1, this, 8001);
        });
        //??????
        binding.includeFaceexp.includeCred.linearCredF.setOnClickListener(v -> {
            if (ViewOnClickUtils.isFastClick()) {
                return;
            }
//            startActivity(new Intent(this, SimpleCameraActivity.class));
            MyUtil.pictureMode(true, false, 1, this, 8002);
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//         ?????? LocalMedia ??????????????????path
//         1.media.getPath(); ?????????path
//         2.media.getCutPath();????????????path????????????media.isCut();?????????true
//         3.media.getCompressPath();????????????path????????????media.isCompressed();?????????true
//         ????????????????????????????????????????????????????????????????????????????????????
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        KLog.i("requestCode=====" + requestCode + "=====selectList===" + selectList.size());
        if (selectList != null && selectList.size() != 0) {
            switch (requestCode) {
                case 8002://??????
                    Glide.with(this)
                            .load(selectList.get(0).getPath())
                            .apply(viewModel.requestOptions)
                            .into(binding.includeFaceexp.includeCred.imageCredF);
                    viewModel.idcardreversephoto.set(bitmapToBase64(ImageToBitmap(selectList.get(0).getRealPath(), baseActivity)));
                    break;
                case 8001://??????
                    Glide.with(this)
                            .load(selectList.get(0).getPath())
                            .apply(viewModel.requestOptions)
                            .into(binding.includeFaceexp.includeCred.imageCredZ);
                    viewModel.idcardphoto.set(bitmapToBase64(ImageToBitmap(selectList.get(0).getRealPath(), baseActivity)));
                    break;
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(MessageEventbus messageWrap) {
        KLog.i("messageWrap====" + messageWrap.toString());
        if (messageWrap.getMessageId() == 99999) {
            MyUtil.listStartVoew(binding.includeFaceexp.superSubmin);
            MyUtil.listEndView(binding.includeFaceexp.linearSfz);
            faceInfoManagerListAdapter.setNewData(viewModel.tempFaceInfoModel.get().getFaceInfoData());
            binding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
            setFaceImageData();
        } else if (messageWrap.getMessageId() == 8001) {
            JSONObject jo = null;
            try {
                jo = new JSONObject(messageWrap.getObject().toString());
                if (jo.opt("type").toString().contains("0")) {//??????
                    Glide.with(this)
                            .load(jo.opt("imgPath"))
                            .apply(viewModel.requestOptions)
                            .into(binding.includeFaceexp.includeCred.imageCredF);
                    viewModel.idcardreversephoto.set(bitmapToBase64(ImageToBitmap(jo.opt("imgPath").toString(), baseActivity)));
                } else {//??????
                    Glide.with(this)
                            .load(jo.opt("imgPath"))
                            .apply(viewModel.requestOptions)
                            .into(binding.includeFaceexp.includeCred.imageCredZ);
                    viewModel.idcardphoto.set(bitmapToBase64(ImageToBitmap(jo.opt("imgPath").toString(), baseActivity)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void initData() {
        super.initData();
        viewModel.getAllCompany();
    }

    @SuppressLint("NewApi")
    @Override
    public void onResultData(Object o) {
        DictItemsModel.ResultDTO resultDTO = (DictItemsModel.ResultDTO) o;
//        ToastUtils.showLong("????????????=="+resultDTO.toString());
        viewModel.tempFaceInfoModel.get().getFaceInfoData().forEach(n -> {
            if (n.getFaceInfoId() == 4) {//??????
                n.setFaceCenterTextString(resultDTO.getText());
                n.setJobs(resultDTO.getValue());
            }
        });
        faceInfoManagerListAdapter.setNewData(viewModel.tempFaceInfoModel.get().getFaceInfoData());
        binding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onComplete(int tag, String json) {
        super.onComplete(tag, json);
        KLog.i("tag===" + tag + "=====json====" + json);
        switch (tag) {
            case 1001:
                viewModel.allCompanyModel.set(JsonUtils.getArrJson(json, AllCompanyModel.class));
                viewModel.getDictItems("");
                if (viewModel.allCompanyModel.get().isSuccess()) {

                }
                break;
            case 1003:
                viewModel.allServiceTeam.set(JsonUtils.getArrJson(json, AllCompanyModel.class));
                if (viewModel.allServiceTeam.get().isSuccess()) {
                }
                break;
            case 1002:
                viewModel.dictItemsModel.set(JsonUtils.getArrJson(json, DictItemsModel.class));
                if (viewModel.dictItemsModel.get().isSuccess()) {
                    return;
                }
                break;
            case 1004:
                ResultModel resultModel = JsonUtils.getArrJson(json, ResultModel.class);
                ToastUtils.showLong(resultModel.getMessage());
                if (resultModel.isSuccess()) {
                    baseActivity.finish();
                }
                break;
            case 1005://?????????????????????
                viewModel.personInfoByidCardModel.set(JsonUtils.getArrJson(json, PersonInfoByidCardModel.class));
                if (viewModel.personInfoByidCardModel.get().isSuccess()) {
                    if (viewModel.personInfoByidCardModel.get().getResult() != null) {
                        //isteamleader    0??????????????????1???????????????
                        KLog.i("isteamleader====" + viewModel.personInfoByidCardModel.get().getResult().getIsTeamLeader());
                        if (viewModel.personInfoByidCardModel.get().getResult().getIsTeamLeader().contains("0")) {
                            binding.includeFaceexp.radioLw.setChecked(true);
                        } else {
                            binding.includeFaceexp.radioGl.setChecked(true);
                        }
                        viewModel.tempFaceInfoModel.get().getFaceInfoData().forEach(n -> {
                            switch (n.getFaceInfoId()) {
                                case 1://??????
                                    n.setFaceCenterTextString(viewModel.personInfoByidCardModel.get().getResult().getName());
                                    break;
                                case 2://??????
                                    n.setFaceCenterTextString(String.valueOf(viewModel.personInfoByidCardModel.get().getResult().getAge()));
                                    break;
                                case 3://????????????
                                    n.setFaceCenterTextString(viewModel.personInfoByidCardModel.get().getResult().getCompany_name());
                                    n.setCompanyID(viewModel.personInfoByidCardModel.get().getResult().getCompany());
                                    break;
                                case 7://???????????? serviceteam_name
                                    n.setFaceCenterTextString(viewModel.personInfoByidCardModel.get().getResult().getServiceteam_name());
                                    n.setTeamId(String.valueOf(viewModel.personInfoByidCardModel.get().getResult().getServiceteam_id()));
                                    break;
                                case 4://??????
                                    n.setFaceCenterTextString(viewModel.personInfoByidCardModel.get().getResult().getJobsName());
                                    n.setJobs(viewModel.personInfoByidCardModel.get().getResult().getJobs());
                                    break;
                                case 5://?????????
                                    n.setFaceCenterTextString(viewModel.personInfoByidCardModel.get().getResult().getPhone());
                                    break;
                                case 6://????????????
                                    n.setFaceCenterTextString(viewModel.personInfoByidCardModel.get().getResult().getIdCard());
                                    break;
                            }
                        });
//                        idcardphoto = "";// ????????????
//                        idcardreversephoto = "";//????????????
                        if (viewModel.personInfoByidCardModel.get().getResult().getIdcardReversePhoto() == null) {
                            ToastUtils.showLong("?????????????????????????????????????????????");
                        } else {
                            Glide.with(this)
                                    .load(viewModel.personInfoByidCardModel.get().getResult().getIdcardReversePhoto())
                                    .apply(viewModel.requestOptions)
                                    .into(binding.includeFaceexp.includeCred.imageCredF);
                            viewModel.idcardreversephoto.set(viewModel.personInfoByidCardModel.get().getResult().getIdcardPhoto().toString());
                        }

                        if (viewModel.personInfoByidCardModel.get().getResult().getIdcardPhoto() == null) {
                            ToastUtils.showLong("?????????????????????????????????????????????");
                        } else {
                            Glide.with(this)
                                    .load(viewModel.personInfoByidCardModel.get().getResult().getIdcardPhoto())
                                    .apply(viewModel.requestOptions)
                                    .into(binding.includeFaceexp.includeCred.imageCredZ);
                            viewModel.idcardphoto.set(viewModel.personInfoByidCardModel.get().getResult().getIdcardPhoto().toString());
                        }
                        //??????????????????
                        viewModel.getAllServiceTeam(viewModel.personInfoByidCardModel.get().getResult().getCompany());
                    } else {
                        ToastUtils.showLong("????????????????????????");

                    }
                    setFaceImageData();
                    Const.GEEMIFACE_IMAGE = "face_image";
                    faceInfoManagerListAdapter.setNewData(viewModel.tempFaceInfoModel.get().getFaceInfoData());
                    binding.includeFaceexp.recyclerView.setAdapter(faceInfoManagerListAdapter);
                    intentSkip();
                }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setFaceImageData();
    }

    private void setFaceImageData() {
        Bitmap bmp = null;
        if (!Const.GEEMIFACE_IMAGE.contains("face_image")) {
            viewModel.faceImageBase.set(Const.GEEMIFACE_IMAGE);
            KLog.i("faceImageBase====" + viewModel.faceImageBase.get());
            bmp = base64ToBitmap(viewModel.faceImageBase.get());
            Glide.with(this).load(new BitmapDrawable(bmp)).apply(viewModel.requestOptions).into(binding.includeFaceexp.faceexpImageRight);
        } else if (viewModel.personInfoByidCardModel.get() != null && viewModel.personInfoByidCardModel.get().getResult() != null) {
            viewModel.faceImageBase.set(viewModel.personInfoByidCardModel.get().getResult().getFacePhoto());
            Glide.with(this).load(viewModel.personInfoByidCardModel.get().getResult().getFacePhoto()).apply(viewModel.requestOptions).into(binding.includeFaceexp.faceexpImageRight);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        DialogUtil.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtil.dismiss();
        //????????????
        //??????(????????????Activity???Fragment???onDestory?????????)
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (viewModel.idCardReader.get() != null) {
            MyUtil.close(viewModel.powerCtl.get(), viewModel.idCardReader.get());
            viewModel.running.set(false);
            //????????????
            mHandler.removeCallbacks(readTask);
            Const.GEEMIFACE_IMAGE = "face_image";
            baseActivity.finish();
        }
    }
}

package com.geemi.facelibrary.ui.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.bumptech.glide.request.RequestOptions;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.adapter.FaceInfoManagerListAdapter;
import com.geemi.facelibrary.http.GeemiHttpImpl;
import com.geemi.facelibrary.manager.GeemiFaceManager;
import com.geemi.facelibrary.model.AllCompanyModel;
import com.geemi.facelibrary.model.DictItemsModel;
import com.geemi.facelibrary.model.PersonInfoByidCardModel;
import com.geemi.facelibrary.model.TempFaceInfoModel;
import com.geemi.facelibrary.router.RouterUrl;
import com.geemi.facelibrary.ui.base.GeemiBaseViewModel;
import com.kongzue.dialog.v2.CustomDialog;
import com.zkteco.android.biometric.module.idcard.IDCardReader;
import com.zkteco.android.biometric.module.idcard.meta.IDCardInfo;
import com.znht.iodev2.PowerCtl;

import java.util.HashMap;
import java.util.Map;

import me.goldze.mvvmhabit.utils.KLog;

public class GeemiSavePersonViewModel extends GeemiBaseViewModel {
    //班组
    public ObservableField<Integer> SERVICETEAM = new ObservableField<>(1);
    //单位
    public ObservableField<Integer> COMPANY = new ObservableField<>(2);

    public RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.mipmap.aaws)
            .error(R.mipmap.aaws);

    //显示标识
    public ObservableField<Integer> faceTag = new ObservableField<>();

    //刷卡标识
    public ObservableField<Boolean> isCard = new ObservableField<>(false);
    //测试数据源
    public ObservableField<TempFaceInfoModel> tempFaceInfoModel = new ObservableField<>();

    //选中的单条数据
    public ObservableField<TempFaceInfoModel.FaceInfoDataDTO> faceInfoDataDTOItem = new ObservableField<>();

    //模块供电控制
    public ObservableField<PowerCtl> powerCtl = new ObservableField<>();

    //二代证读头
    public ObservableField<IDCardReader> idCardReader = new ObservableField<>();
    //单位信息
    public ObservableField<AllCompanyModel> allCompanyModel = new ObservableField<>();
    //班组信息
    public ObservableField<AllCompanyModel> allServiceTeam = new ObservableField<>();
    //工种信息
    public ObservableField<DictItemsModel> dictItemsModel = new ObservableField<>();
    //个人信息
    public ObservableField<PersonInfoByidCardModel> personInfoByidCardModel = new ObservableField<>();

    public ObservableField<IDCardInfo> idCardInfo = new ObservableField<>();

    public ObservableField<Boolean> bopen = new ObservableField<>(false);

    public ObservableField<Boolean> running = new ObservableField<>(false);

    //读到二代证类型,
    public ObservableField<Integer> retType = new ObservableField<>();

    //国内居民身份证
    public ObservableField<Integer> ID_CN = new ObservableField<>(1);
    //外国人永居证
    public ObservableField<Integer> ID_PRP = new ObservableField<>(2);
    //港澳台居住证
    public ObservableField<Integer> ID_HK_TW = new ObservableField<>(3);

    //项目ID
    public ObservableField<String> projectId = new ObservableField<>("");
    //身份证号
    public ObservableField<String> idCard = new ObservableField<>("");
    //姓名
    public ObservableField<String> name = new ObservableField<>("");
    //手机号
    public ObservableField<String> phone = new ObservableField<>("");
    //年龄
    public ObservableField<String> age = new ObservableField<>("");
    //工种
    public ObservableField<String> jobs = new ObservableField<>("");
    //单位id
    public ObservableField<String> companyId = new ObservableField<>("");
    //单位名称
    public ObservableField<String> companyName = new ObservableField<>("");
    //班组id
    public ObservableField<String> teamId = new ObservableField<>("");
    //班组名称
    public ObservableField<String> teamName = new ObservableField<>("");
    //性别
    public ObservableField<String> sex = new ObservableField<>("");
    //住址
    public ObservableField<String> address = new ObservableField<>("");
    //人脸信息
    public ObservableField<String> faceImageBase = new ObservableField<>("");
    //isteamleader
    public ObservableField<String> isteamleader = new ObservableField<>("0");
    // 身份证前
    public ObservableField<String> idcardphoto = new ObservableField<>("");
    //身份证后
    public ObservableField<String> idcardreversephoto = new ObservableField<>("");

    public GeemiSavePersonViewModel(@NonNull Application application) {
        super(application);
    }

    //查询所有参建单位
    public void getAllCompany() {
        Map<String, String> params = new HashMap<>();
        params.put("projectId", projectId.get());
        GeemiFaceManager.getHttpModel().getData(1001, GeemiHttpImpl.Method_GET, RouterUrl.GETALLCOMPANY, params, this, false);
    }


    //查询所有班组
    public void getAllServiceTeam(String companyId) {
        KLog.i("companyId====" + companyId + "===projectId==" + projectId);
        Map<String, String> params = new HashMap<>();
        params.put("projectId", projectId.get());
        params.put("companyId", companyId);
        GeemiFaceManager.getHttpModel().getData(1003, GeemiHttpImpl.Method_GET, RouterUrl.GETTALLSERVICETEAM, params, this, false);
    }

    //获取工种字典数据
    public void getDictItems(String itemText) {
        Map<String, String> params = new HashMap<>();
        params.put("itemText", itemText);
        params.put("code", "jobs");
        GeemiFaceManager.getHttpModel().getData(1002, GeemiHttpImpl.Method_GET, RouterUrl.GETDICTITEMS, params, this, false);
    }

    //保存人员
    public void savePerson() {
        Map<String, String> params = new HashMap<>();
        params.put("projectId", projectId.get());
        params.put("idCard", idCard.get());
        params.put("name", name.get());
        params.put("phone", phone.get());
        params.put("age", age.get());
        params.put("jobs", jobs.get());
        params.put("companyId", companyId.get());
        params.put("companyName", companyName.get());
        params.put("teamId", teamId.get());
        params.put("teamName", teamName.get());
        params.put("image", faceImageBase.get());
        params.put("sex", sex.get());
        params.put("address", address.get());
        params.put("isteamleader", isteamleader.get());
        params.put("idcardphoto", idcardphoto.get());
        params.put("idcardreversephoto", idcardreversephoto.get());
        GeemiFaceManager.getHttpModel().getData(1004, GeemiHttpImpl.Method_POST, RouterUrl.SAVEPERSON, params, this);
    }

    //根据身份证号查询人员信息
    public void getPersonInfoByIdcard(String idCard) {
        Map<String, String> params = new HashMap<>();
        params.put("idCard", idCard);
        params.put("projectId", projectId.get());
        GeemiFaceManager.getHttpModel().getData(1005, GeemiHttpImpl.Method_GET, RouterUrl.GETPERSONINFOBYIDCARD, params, this);
    }

}

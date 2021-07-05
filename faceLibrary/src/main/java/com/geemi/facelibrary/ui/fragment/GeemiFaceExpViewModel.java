package com.geemi.facelibrary.ui.fragment;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.bumptech.glide.request.RequestOptions;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.adapter.FaceInfoListAdapter;
import com.geemi.facelibrary.http.GeemiHttpImpl;
import com.geemi.facelibrary.manager.GeemiFaceManager;
import com.geemi.facelibrary.model.PersonInfoByidCardModel;
import com.geemi.facelibrary.model.TempFaceInfoModel;
import com.geemi.facelibrary.router.RouterUrl;
import com.geemi.facelibrary.ui.base.GeemiBaseViewModel;

import java.util.HashMap;
import java.util.Map;


public class GeemiFaceExpViewModel extends GeemiBaseViewModel {
    public RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.mipmap.aaws)
            .error(R.mipmap.aaws);

    //人脸信息
    public ObservableField<String> faceImageBase = new ObservableField<>();
    //显示标识
    public ObservableField<Integer> faceTag = new ObservableField<>();
    //测试数据源
    public ObservableField<TempFaceInfoModel> tempFaceInfoModel = new ObservableField<>();

    public ObservableField<PersonInfoByidCardModel.ResultDTO> resultDTO = new ObservableField<>();

    //上班打卡
    public ObservableField<String> CLOCKTYPE_START = new ObservableField<>("start");
    // 下班打卡
    public ObservableField<String> CLOCKTYPE_END = new ObservableField<>( "end");
    //更新打卡
    public ObservableField<String> CLOCKTYPE_UPDATE = new ObservableField<>("update");

    public GeemiFaceExpViewModel(@NonNull Application application) {
        super(application);
    }

    //打卡
    public void clockPunch() {
        Map<String, String> params = new HashMap<>();
        params.put("projectId",resultDTO.get().getProject_id());
        params.put("images", faceImageBase.get());
        params.put("personId",resultDTO.get().getId());
        params.put("clockType", resultDTO.get().getClockType());
        GeemiFaceManager.getHttpModel().getData(1001, GeemiHttpImpl.Method_POST, RouterUrl.CLOCKPUNCH, params, this);
    }
}

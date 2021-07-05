package com.geemi.facelibrary.ui.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.SuperTextView;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.adapter.DrawerPopFaceInfoTypeListAdapter;
import com.geemi.facelibrary.http.GeemiHttpImpl;
import com.geemi.facelibrary.http.IGeemiCallback;
import com.geemi.facelibrary.interfaces.IResultDataCallbace;
import com.geemi.facelibrary.manager.GeemiFaceManager;
import com.geemi.facelibrary.model.DictItemsModel;
import com.geemi.facelibrary.router.RouterUrl;
import com.geemi.facelibrary.utils.JsonUtils;
import com.geemi.facelibrary.utils.ViewOnClickUtils;
import com.lxj.xpopup.core.DrawerPopupView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class FaceInfoTypePopupView extends DrawerPopupView implements IGeemiCallback {
    private DrawerPopFaceInfoTypeListAdapter drawerPopFaceInfoTypeListAdapter;
    private List<DictItemsModel.ResultDTO> dataBen;
    private IResultDataCallbace resultDataListenter;
    private RecyclerView recyclerView;
    private SuperTextView superTextView;
    private EditText edit_searchView;

    public FaceInfoTypePopupView(@NonNull Context context) {
        super(context);
    }

    public FaceInfoTypePopupView(@NonNull Context context, List<?> dataBen, IResultDataCallbace resultDataListenter) {
        super(context);
        this.dataBen = (List<DictItemsModel.ResultDTO>) dataBen;
        this.resultDataListenter = resultDataListenter;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.faceinfo_popup_type;
    }

    @Override
    protected void onShow() {
        super.onShow();
        Log.e("tag", " onShow");
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        dismiss();
        Log.e("tag", " onDismiss");
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        iniView();
        initData();
    }

    @SuppressLint("NewApi")
    private void iniView() {
        edit_searchView = findViewById(R.id.edit_searchView);
        recyclerView = findViewById(R.id.recyclerView);
        superTextView = findViewById(R.id.order_popup_title);
        superTextView.setLeftTextIsBold(true);
        superTextView.setRightTextIsBold(true);
        //取消
        superTextView.setLeftTvClickListener(() -> {
            dismiss();
        });
        //确定
        superTextView.setRightTvClickListener(() -> {
            if (ViewOnClickUtils.isFastClick()){
                return;
            }
            dismiss();
        });

        edit_searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getDictItems(s.toString());
            }
        });
    }

    @SuppressLint("NewApi")
    private void initData() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        drawerPopFaceInfoTypeListAdapter = new DrawerPopFaceInfoTypeListAdapter(R.layout.item_faceinfo_type);
        //默认提供5种方法（渐显ALPHAIN 、缩放SCALEIN 、从下到上SLIDEIN_BOTTOM ，从左到右SLIDEIN_LEFT 、从右到左SLIDEIN_RIGHT ）
        drawerPopFaceInfoTypeListAdapter.setNotDoAnimationCount(1);
        drawerPopFaceInfoTypeListAdapter.openLoadAnimation();
        drawerPopFaceInfoTypeListAdapter.setNewData(dataBen);
        recyclerView.setAdapter(drawerPopFaceInfoTypeListAdapter);
        drawerPopFaceInfoTypeListAdapter.notifyDataSetChanged();
        drawerPopFaceInfoTypeListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            DictItemsModel.ResultDTO resultDTO = (DictItemsModel.ResultDTO) adapter.getData().get(position);
            if (ViewOnClickUtils.isFastClick()){
                return;
            }
            if (resultDataListenter != null){
                resultDataListenter.onResultData(resultDTO);
            }
            dismiss();
        });
    }

    //获取工种字典数据
    public void getDictItems(String itemText) {
        Map<String, String> params = new HashMap<>();
        params.put("itemText", itemText);
        params.put("code", "jobs");
        GeemiFaceManager.getHttpModel().getData(1002, GeemiHttpImpl.Method_GET, RouterUrl.GETDICTITEMS, params, this, false);
    }

    @Override
    public void onError(int tag, String error) {
        KLog.i("tag==="+tag+"=====error===="+error);
    }

    @Override
    public void onComplete(int tag, String json) {
        KLog.i("tag===" + tag + "=====json====" + json);
        DictItemsModel dictItemsModel = JsonUtils.getArrJson(json,DictItemsModel.class);
        if (dictItemsModel.isSuccess()){
            if (dictItemsModel.getResult().size() == 0){
                ToastUtils.showLong(dictItemsModel.getMessage());
            }
            drawerPopFaceInfoTypeListAdapter.setNewData(dictItemsModel.getResult());
            drawerPopFaceInfoTypeListAdapter.notifyDataSetChanged();
            return;
        }
        ToastUtils.showLong(dictItemsModel.getMessage());
    }

    @Override
    public void onProgress(int tag, String msg) {
    }

    @Override
    public void onFinish(int tag, String msg) {
    }
}

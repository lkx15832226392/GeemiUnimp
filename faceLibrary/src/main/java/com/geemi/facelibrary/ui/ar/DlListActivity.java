package com.geemi.facelibrary.ui.ar;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.geemi.facelibrary.R;
import com.geemi.facelibrary.adapter.ARDownAdapter;
import com.geemi.facelibrary.databinding.ActivityDlListBinding;
import com.geemi.facelibrary.http.GeemiHttpImpl;
import com.geemi.facelibrary.manager.GeemiFaceManager;
import com.geemi.facelibrary.model.ARModel;
import com.geemi.facelibrary.ui.base.GeemiBaseActivity;
import com.geemi.facelibrary.utils.JsonUtils;
import com.geemi.facelibrary.utils.ViewOnClickUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.HashMap;
import java.util.Map;

import me.goldze.mvvmhabit.utils.KLog;


public class DlListActivity extends GeemiBaseActivity {
    private ActivityDlListBinding activityDlListBinding;
    private String projectId = "cbd69ee44bfc4e5892f4d6866897171c";

    private ARDownAdapter arDownAdapter;
    private ARModel arModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDlListBinding = DataBindingUtil.setContentView(this, R.layout.activity_dl_list);
//        projectId = getIntent().getStringExtra(KEY_INTENT_PROJECT);
        KLog.i("projectId====" + projectId);
        initView();
        initLinearView();
    }

    private void initView() {
        activityDlListBinding.twinklingRefreshLayout.startRefresh();
        activityDlListBinding.twinklingRefreshLayout.setEnableOverScroll(false);
        activityDlListBinding.twinklingRefreshLayout.setEnableLoadmore(false);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        activityDlListBinding.recyclerView.setLayoutManager(layoutManager);
        arDownAdapter = new ARDownAdapter(R.layout.item_ardown);
        //默认提供5种方法（渐显ALPHAIN 、缩放SCALEIN 、从下到上SLIDEIN_BOTTOM ，从左到右SLIDEIN_LEFT 、从右到左SLIDEIN_RIGHT ）
        arDownAdapter.setNotDoAnimationCount(1);
        arDownAdapter.openLoadAnimation();
        activityDlListBinding.recyclerView.setAdapter(arDownAdapter);

    }

    private void initLinearView() {
        activityDlListBinding.hand.setLeftImageViewClickListener(imageView -> {
            if (ViewOnClickUtils.isFastClick()) {
                return;
            }
            finish();
        });

        activityDlListBinding.twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getARListData();
            }
        });
    }




    //获取AR列表数据
    public void getARListData() {
        Map<String, String> params = new HashMap<>();
//        params.put("projectId", projectId);
        String url = "http://39.107.190.158/smartSiteService/api/arParty/list?projectId=" + projectId;
        GeemiFaceManager.getHttpModel().getData(1001, GeemiHttpImpl.Method_GET, url, params, this, false);
    }

    @Override
    public void onComplete(int tag, String json) {
        KLog.i("tag===" + tag + "=====json====" + json);
        arModel = JsonUtils.getArrJson(json, ARModel.class);
        if (arModel.isSuccess() && arModel.getCode() == 0) {
            arDownAdapter.setNewData(arModel.getData().getRecords());
            arDownAdapter.notifyDataSetChanged();
        }
        activityDlListBinding.twinklingRefreshLayout.finishRefreshing();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}

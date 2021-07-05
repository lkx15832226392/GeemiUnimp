package com.geemi.facelibrary.adapter;

import android.annotation.SuppressLint;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.model.TempFaceInfoModel;

public class FaceInfoListAdapter extends BaseQuickAdapter<TempFaceInfoModel.FaceInfoDataDTO, BaseViewHolder> {
    public FaceInfoListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @SuppressLint("NewApi")
    @Override
    protected void convert(BaseViewHolder helper, TempFaceInfoModel.FaceInfoDataDTO item) {
        SuperTextView superTextView = helper.getView(R.id.item_stpText);
        superTextView.setLeftString(item.getFaceLeftTextString());
        superTextView.setCenterString(item.getFaceCenterTextString());
        superTextView.setRightString(item.getFaceRightTextString());
        superTextView.setCenterTextIsBold(true);
        superTextView.setLeftTextIsBold(true);

    }


}

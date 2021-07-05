package com.geemi.facelibrary.adapter;

import android.graphics.Color;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.model.DictItemsModel;
import com.geemi.facelibrary.model.FaceInfoTypeModel;


public class DrawerPopFaceInfoTypeListAdapter extends BaseQuickAdapter<DictItemsModel.ResultDTO, BaseViewHolder> {

    public DrawerPopFaceInfoTypeListAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, DictItemsModel.ResultDTO item) {
        SuperTextView superTextView = helper.getView(R.id.item_supText);
        superTextView.setLeftString(item.getText());
        superTextView.setLeftTextIsBold(true);
        helper.addOnClickListener(R.id.item_supText);
    }


}

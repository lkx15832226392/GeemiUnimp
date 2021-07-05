package com.geemi.facelibrary.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.interfaces.IResultDataCallbace;
import com.geemi.facelibrary.model.TempFaceInfoModel;
import com.geemi.facelibrary.utils.MyUtil;

import org.w3c.dom.Text;

import me.goldze.mvvmhabit.utils.KLog;

public class FaceInfoManagerListAdapter extends BaseQuickAdapter<TempFaceInfoModel.FaceInfoDataDTO, BaseViewHolder> {

    private IResultDataCallbace iResultDataCallbace;


    public void setiResultDataCallbace(IResultDataCallbace iResultDataCallbace) {
        this.iResultDataCallbace = iResultDataCallbace;
    }

    public FaceInfoManagerListAdapter(int layoutResId, IResultDataCallbace iResultDataCallbace) {
        super(layoutResId);
        this.iResultDataCallbace = iResultDataCallbace;
    }

    public FaceInfoManagerListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void convert(BaseViewHolder helper, TempFaceInfoModel.FaceInfoDataDTO item) {
        EditText editText = helper.getView(R.id.item_edit_text_centent);
        TextView textView = helper.getView(R.id.item_text_centent);
        helper.setText(R.id.item_text_left, item.getFaceLeftTextString());
        editText.setEnabled(Boolean.parseBoolean(item.getFaceIsEnabled()));
        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.abc_spinner_mtrl_am_alpha);

        if (item.getFaceInfoId() == 3 || item.getFaceInfoId() == 7 || item.getFaceInfoId() == 4) {
            textView.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, drawable, null);
            textView.setCompoundDrawablePadding(4);
            helper.setText(R.id.item_text_centent, item.getFaceCenterTextString());
            MyUtil.listStartVoew(textView);
            MyUtil.listEndView(editText);
            helper.addOnClickListener(R.id.item_text_centent);
        }else {
            MyUtil.listStartVoew(editText);
            MyUtil.listEndView(textView);
            helper.setText(R.id.item_edit_text_centent, item.getFaceCenterTextString());
        }
        String digits = "1234567890x";
        if (item.getFaceInfoId() == 5){
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
            editText.setKeyListener(DigitsKeyListener.getInstance(digits));
        }else if (item.getFaceInfoId() == 6){
            editText.setKeyListener(DigitsKeyListener.getInstance(digits));
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
        }else if (item.getFaceInfoId() == 2){
            editText.setKeyListener(DigitsKeyListener.getInstance(digits));
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setFaceCenterTextString(s.toString());
                if (item.getFaceInfoId() == 6 && iResultDataCallbace != null){
                    iResultDataCallbace.onResultData(s.toString());
                }
            }
        });
    }


}

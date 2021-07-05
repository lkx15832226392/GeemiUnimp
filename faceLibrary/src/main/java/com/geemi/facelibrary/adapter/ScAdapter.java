package com.geemi.facelibrary.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.model.DictItemsModel;
import com.geemi.facelibrary.model.ScListBean;

public class ScAdapter extends BaseQuickAdapter<ScListBean.ResultDTO, BaseViewHolder> {
    private VolListener volListener;
    private CheckListener checkListener;
    public ScAdapter(int layoutResId) {
        super(layoutResId);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, ScListBean.ResultDTO item) {
        helper.setText(R.id.tv_sc_name, item.getName());
        TextView tvVol = helper.getView(R.id.tv_vol);
        SeekBar bar = helper.getView(R.id.seekBar);
        CheckBox checkBox = helper.getView(R.id.cb_sc);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO Auto-generated method stub
            checkListener.setCheck(helper.getPosition(), isChecked);
        });
        if(!TextUtils.isEmpty(item.getVolume())){
            bar.setProgress(Integer.parseInt(item.getVolume()));
        }else{
            bar.setProgress(50);
        }
        tvVol.setText("音量："+bar.getProgress());

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvVol.setText("音量：" + i);
                volListener.setVol(item, i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setVolListener(VolListener listener) {
        this.volListener = listener;
    }

    public void setCheckListener(CheckListener checkListener) {
        this.checkListener = checkListener;
    }

    public interface VolListener {
        void setVol(ScListBean.ResultDTO item, int vol);
    }

    public interface CheckListener {
        void setCheck(int pos, boolean isChecked);
    }
}

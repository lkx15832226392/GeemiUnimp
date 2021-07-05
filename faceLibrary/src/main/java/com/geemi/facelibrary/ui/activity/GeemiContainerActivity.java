package com.geemi.facelibrary.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.allen.library.SuperTextView;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.interfaces.IResultDataCallbace;
import com.geemi.facelibrary.model.Const;
import com.geemi.facelibrary.model.MessageEventbus;
import com.geemi.facelibrary.ui.base.GeemiBaseActivity;
import com.geemi.facelibrary.utils.DialogUtil;
import com.geemi.facelibrary.utils.MyIntentUtil;
import com.geemi.facelibrary.utils.MyUtil;
import com.geemi.facelibrary.utils.ViewOnClickUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.tianma.netdetector.lib.NetworkType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

import static com.geemi.facelibrary.utils.MyIntentUtil.BUNDLE;


public class GeemiContainerActivity extends GeemiBaseActivity implements SuperTextView.OnRightTvClickListener {
    public GeemiContainerActivity instance = null;
    public static final String FRAGMENT_TAG = "content_fragment_tag";
    public static final String TITLE = "title";//标题
    public static final String RIGHTRES = "rightRes";//右侧图标是否显示
    public static final String ISTOOLBAR = "isToolbar";//toolbar是否显示
    public static final String ISTITLECLICK = "isTitleClick";//标题是否可点击
    public static final String CONTAINERKEY = "0";
    public static final String TOOLBARCOLOR = "toolbarColor";
    protected WeakReference<Fragment> mFragment;
    private SuperTextView textView;
    private FrameLayout content;
    private Fragment fragment = null;
    private  FragmentManager fm;
    private static IResultDataCallbace iResultDataCallbace;

    public void setiResultDataCallbace(IResultDataCallbace iResultDataCallbace) {
        GeemiContainerActivity.iResultDataCallbace = iResultDataCallbace;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.containeractivity);
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        instance = this;
        textView = findViewById(R.id.super_text);
        content = findViewById(R.id.content);
        initView(savedInstanceState);

    }

    //初始化View
    @SuppressLint("NewApi")
    private void initView(Bundle savedInstanceState) {
        textView.setCenterString(getIntent().getBundleExtra(BUNDLE).getString(TITLE));
        //是否需要修改toolbar背景颜色
        if (!TextUtils.isEmpty(getIntent().getBundleExtra(BUNDLE).getString(TOOLBARCOLOR))){
            textView.setBackgroundColor(Color.parseColor(getIntent().getBundleExtra(BUNDLE).getString(TOOLBARCOLOR)));
        }
        //是否需要显示加载logi
        if (!TextUtils.isEmpty(getIntent().getBundleExtra(BUNDLE).getString(CONTAINERKEY)) && getIntent().getBundleExtra(BUNDLE).getString(CONTAINERKEY).equals("9999")){
//            MyUtil.setEventBusData(CODE_ONE,SPKeyGlobal.SUCCESS_KEY);
        }
        //控制标题是否能点击
        if (getIntent().getBundleExtra(BUNDLE).getBoolean(ISTITLECLICK)){
            textView.setCenterTvClickListener(() -> {
                if (ViewOnClickUtils.isFastClick()){
                    return;
                }
                MyUtil.setEventBusData(80001,textView);
            });
        }
        //控制toolbar右侧图标是否显示 点击
        if (!TextUtils.isEmpty(getIntent().getBundleExtra(BUNDLE).getString(RIGHTRES))){
            if (MyUtil.getResId(this,getIntent().getBundleExtra(BUNDLE).getString(RIGHTRES)) != 0){//显示图标
                textView.setRightIcon(MyUtil.getResId(this,getIntent().getBundleExtra(BUNDLE).getString(RIGHTRES)));
            }else{//显示文字
                textView.setRightString(getIntent().getBundleExtra(BUNDLE).getString(RIGHTRES));
            }
            textView.setRightTvClickListener(this);
        }
        //控制toolbar是否需要显示
        if (!getIntent().getBundleExtra(BUNDLE).getBoolean(ISTOOLBAR)){
            MyUtil.listEndView(textView);
        }
        fm = getSupportFragmentManager();

        if (savedInstanceState != null) {
            fragment = fm.getFragment(savedInstanceState, FRAGMENT_TAG);
            KLog.i("name===="+fragment.getClass().getSimpleName());
        }
       if (fragment == null) {
            fragment = MyIntentUtil.initFromIntent(getIntent());
        }
        FragmentTransaction trans = getSupportFragmentManager()
                .beginTransaction();
        trans.replace(R.id.content, fragment);
        trans.commitAllowingStateLoss();
        mFragment = new WeakReference<>(fragment);

        if (fragment.getClass().getSimpleName().contains("GeemiHomeFragment")){
            setToolBarNav(textView, R.mipmap.ic_menu_white_24dp, this,fragment);
        }else {
            setToolBarNav(textView, R.mipmap.ic_arrow_back_white_24dp, this,fragment);
            textView.setCenterTvDrawableRight(null);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(MessageEventbus messageWrap) {
        KLog.i("messageWrap====="+messageWrap.toString());
        if (messageWrap.getMessageId() == 8002){
            textView.setCenterString(messageWrap.getObject().toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        KLog.i("requestCode=====" + requestCode+"====="+selectList.size());
        /*然后在碎片中调用重写的onActivityResult方法*/
        fragment.onActivityResult(requestCode, resultCode, data);



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, FRAGMENT_TAG, mFragment.get());
    }

    @SuppressLint("WrongConstant")
    @Override
    protected boolean needRegisterNetworkChangeObserver() {
        return true;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onNetConnected(NetworkType networkType) {
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onNetDisconnected() {
        DialogUtil.Tshow(instance,"暂无网络连接");
    }


    @Override
    public void onClickListener() {
        if (iResultDataCallbace != null){
            iResultDataCallbace.onResultData("rightClick");
        }
    }
}

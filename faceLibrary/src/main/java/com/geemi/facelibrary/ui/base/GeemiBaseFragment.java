package com.geemi.facelibrary.ui.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.geemi.facelibrary.utils.DialogUtil;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ContainerActivity;
import me.goldze.mvvmhabit.base.IBaseView;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * lkx 2021-05
 * @param <V>
 * @param <VM>
 */
public abstract class GeemiBaseFragment<V extends ViewDataBinding, VM extends GeemiBaseViewModel> extends RxFragment implements IBaseView {
    protected FragmentActivity baseActivity;
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private MaterialDialog dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = getActivity();
        initParam();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除Messenger注册
        Messenger.getDefault().unregister(viewModel);
        //解除ViewModel生命周期感应
        getLifecycle().removeObserver(viewModel);
        if (viewModel != null) {
            viewModel.removeRxBus();
        }
        if (binding != null) {
            binding.unbind();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding();
        //私有的ViewModel与View的契约事件回调逻辑
//        registorUIChangeLiveDataCallBack();
        //UI初始化
        initView();
        //页面数据初始化方法
        initData();
        //事件初始化
        initLinearView();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        viewModel.registerRxBus();
    }


    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        binding.setVariable(viewModelId, viewModel);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
    }

    private void initViewObservables() {
        viewModel.uc.onProgress.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.sendEmptyMessage(201);
            }
        });
        viewModel.uc.onFinish.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.sendEmptyMessage(202);
            }
        });
        viewModel.uc.onError.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.sendEmptyMessage(203);
            }
        });

        viewModel.uc.onComplete.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.sendEmptyMessage(204);
            }
        });

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("NewApi")
        public void handleMessage(Message msg) {
            DialogUtil.dismiss();
            switch (msg.what) {
                case 201://加载中
                    DialogUtil.showViewProgress(getActivity());
                    break;
                case 202://加载结束
                    DialogUtil.dismiss();
                    break;
                case 203://错误信息
                    ToastUtils.showShort(viewModel.uc.onError.get());
                    break;
                case 204://数据获取成功
                    int code = viewModel.uc.onComplete.get().keySet().hashCode();
                    onComplete(code, (String) viewModel.uc.onComplete.get().get(code));
                    break;
            }
        }
    };

    //接口数据返回
    protected void onComplete(int tag, String json) {

    }

    protected abstract void initView();

    protected abstract void initLinearView();

    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
//    protected void registorUIChangeLiveDataCallBack() {
//        //加载对话框显示
//        viewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String title) {
//                showDialog(title);
//            }
//        });
//        //加载对话框消失
//        viewModel.getUC().getDismissDialogEvent().observe(this, new Observer<Void>() {
//            @Override
//            public void onChanged(@Nullable Void v) {
//                dismissDialog();
//            }
//        });
//        //跳入新页面
//        viewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
//            @Override
//            public void onChanged(@Nullable Map<String, Object> params) {
//                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
//                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
//                startActivity(clz, bundle);
//            }
//        });
//        //跳入ContainerActivity
//        viewModel.getUC().getStartContainerActivityEvent().observe(this, new Observer<Map<String, Object>>() {
//            @Override
//            public void onChanged(@Nullable Map<String, Object> params) {
//                String canonicalName = (String) params.get(ParameterField.CANONICAL_NAME);
//                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
//                startContainerActivity(canonicalName, bundle);
//            }
//        });
//        //关闭界面
//        viewModel.getUC().getFinishEvent().observe(this, new Observer<Void>() {
//            @Override
//            public void onChanged(@Nullable Void v) {
//                getActivity().finish();
//            }
//        });
        //关闭上一层
//        viewModel.getUC().getOnBackPressedEvent().observe(this, (Observer) o -> {
//
//        });
//    }

    public void showDialog(String title) {
        if (dialog != null) {
            dialog.show();
        } else {
            MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(getActivity(), title, true);
            dialog = builder.show();
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getContext(), clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(getContext(), ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }




    /**
     * =====================================================================
     **/

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }

    @Override
    public void initParam() {

    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {
        initViewObservables();
    }


    public boolean isBackPressed() {
        return false;
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
    }
}

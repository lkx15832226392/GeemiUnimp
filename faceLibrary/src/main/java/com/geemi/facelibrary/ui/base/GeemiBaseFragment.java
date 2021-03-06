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
        //??????Messenger??????
        Messenger.getDefault().unregister(viewModel);
        //??????ViewModel??????????????????
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
        //??????????????????Databinding???ViewModel??????
        initViewDataBinding();
        //?????????ViewModel???View???????????????????????????
//        registorUIChangeLiveDataCallBack();
        //UI?????????
        initView();
        //???????????????????????????
        initData();
        //???????????????
        initLinearView();
        //??????????????????????????????????????????ViewModel?????????View??????????????????
        initViewObservable();
        //??????RxBus
        viewModel.registerRxBus();
    }


    /**
     * ????????????
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
                //????????????????????????????????????????????????BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        binding.setVariable(viewModelId, viewModel);
        //???ViewModel??????View?????????????????????
        getLifecycle().addObserver(viewModel);
        //??????RxLifecycle????????????
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
                case 201://?????????
                    DialogUtil.showViewProgress(getActivity());
                    break;
                case 202://????????????
                    DialogUtil.dismiss();
                    break;
                case 203://????????????
                    ToastUtils.showShort(viewModel.uc.onError.get());
                    break;
                case 204://??????????????????
                    int code = viewModel.uc.onComplete.get().keySet().hashCode();
                    onComplete(code, (String) viewModel.uc.onComplete.get().get(code));
                    break;
            }
        }
    };

    //??????????????????
    protected void onComplete(int tag, String json) {

    }

    protected abstract void initView();

    protected abstract void initLinearView();

    /**
     * =====================================================================
     **/
    //??????ViewModel???View?????????UI????????????
//    protected void registorUIChangeLiveDataCallBack() {
//        //?????????????????????
//        viewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String title) {
//                showDialog(title);
//            }
//        });
//        //?????????????????????
//        viewModel.getUC().getDismissDialogEvent().observe(this, new Observer<Void>() {
//            @Override
//            public void onChanged(@Nullable Void v) {
//                dismissDialog();
//            }
//        });
//        //???????????????
//        viewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
//            @Override
//            public void onChanged(@Nullable Map<String, Object> params) {
//                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
//                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
//                startActivity(clz, bundle);
//            }
//        });
//        //??????ContainerActivity
//        viewModel.getUC().getStartContainerActivityEvent().observe(this, new Observer<Map<String, Object>>() {
//            @Override
//            public void onChanged(@Nullable Map<String, Object> params) {
//                String canonicalName = (String) params.get(ParameterField.CANONICAL_NAME);
//                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
//                startContainerActivity(canonicalName, bundle);
//            }
//        });
//        //????????????
//        viewModel.getUC().getFinishEvent().observe(this, new Observer<Void>() {
//            @Override
//            public void onChanged(@Nullable Void v) {
//                getActivity().finish();
//            }
//        });
        //???????????????
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
     * ????????????
     *
     * @param clz ??????????????????Activity???
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getContext(), clz));
    }

    /**
     * ????????????
     *
     * @param clz    ??????????????????Activity???
     * @param bundle ????????????????????????
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * ??????????????????
     *
     * @param canonicalName ????????? : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * ??????????????????
     *
     * @param canonicalName ????????? : Fragment.class.getCanonicalName()
     * @param bundle        ????????????????????????
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

    //????????????
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }

    @Override
    public void initParam() {

    }

    /**
     * ??????????????????
     *
     * @return ??????layout???id
     */
    public abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * ?????????ViewModel???id
     *
     * @return BR???id
     */
    public abstract int initVariableId();

    /**
     * ?????????ViewModel
     *
     * @return ??????BaseViewModel???ViewModel
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
     * ??????ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
    }
}

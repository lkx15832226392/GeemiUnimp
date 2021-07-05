package com.geemi.facelibrary.ui.client;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.allen.library.SuperTextView;
import com.geemi.facelibrary.R;
import com.geemi.facelibrary.faceui.FaceLivenessExpActivity;
import com.geemi.facelibrary.http.GeemiHttpImpl;
import com.geemi.facelibrary.manager.GeemiFaceManager;
import com.geemi.facelibrary.model.Const;
import com.geemi.facelibrary.model.MessageEventbus;
import com.geemi.facelibrary.model.PersonInfoByidCardModel;
import com.geemi.facelibrary.model.TempModel;
import com.geemi.facelibrary.router.RouterFragmentPath;
import com.geemi.facelibrary.router.RouterUrl;
import com.geemi.facelibrary.ui.airing.SCActivity;
import com.geemi.facelibrary.ui.ar.ARActivity;
import com.geemi.facelibrary.ui.base.GeemiBaseActivity;
import com.geemi.facelibrary.ui.client.common.AndroidInterface;
import com.geemi.facelibrary.ui.client.common.FragmentKeyDown;
import com.geemi.facelibrary.ui.client.common.UIController;
import com.geemi.facelibrary.ui.client.widget.WebLayout;
import com.geemi.facelibrary.utils.JsonUtils;
import com.geemi.facelibrary.utils.MyIntentUtil;
import com.geemi.facelibrary.utils.MyUtil;
import com.geemi.facelibrary.utils.ViewOnClickUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IWebLayout;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.MiddlewareWebClientBase;
import com.just.agentweb.PermissionInterceptor;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static com.geemi.facelibrary.model.Const.GEEMIFACE_ARCODE;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_BROADCAST;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_CARDKEY;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_CONTRAST;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_GATHER;
import static com.geemi.facelibrary.model.Const.GEEMIFACE_TAGCODE_SING;
import static com.geemi.facelibrary.model.Const.KEY_INTENT_LEVEL_SAVE;
import static com.geemi.facelibrary.model.Const.KEY_INTENT_PROJECT;
import static com.geemi.facelibrary.utils.MyUtil.ImageToBitmap;
import static com.geemi.facelibrary.utils.MyUtil.bitmapToBase64;

public class CommonWebView extends GeemiBaseActivity implements FragmentKeyDown {
    public static final String URL_KEY = "url_key";
    public static final String TYPE_KEY = "commonWebView";
    protected WeakReference<Fragment> mFragment;

    private ImageView mBackImageView;
    private ImageView mFinishImageView;
    private TextView mTitleTextView;
    protected AgentWeb mAgentWeb;
    private ImageView mMoreImageView;
    private PopupMenu mPopupMenu;
    private SuperTextView text_face_title;

    private RelativeLayout relativeLayout,relative_title;
    private LinearLayout linearLayout;
    /**
     * 用于方便打印测试
     */
    public static final String TAG = FragmentKeyDown.class.getSimpleName();
    private MiddlewareWebClientBase mMiddleWareWebClient;
    private MiddlewareWebChromeBase mMiddleWareWebChrome;

    private String sTag = "h5";
    private String sBitMap;
    private String projectId = "3768bbf67de842688b7f7e817f7a9711";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agentweb);
        linearLayout = findViewById(R.id.linearLayout);
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (getIntent().getStringExtra("sTag") == null){
            if (getIntent().getBundleExtra(MyIntentUtil.BUNDLE).getString("sTag") != null){
                sTag = getIntent().getBundleExtra(MyIntentUtil.BUNDLE).getString("sTag");
            }
        }else {
            sTag = getIntent().getStringExtra("sTag");
        }
        KLog.i("sTag===="+sTag);
        initView();
        initAlweb();
    }

//    @Override
//    protected void initImmersionBar() {
//        super.initImmersionBar();
////        ImmersionBar.with(this).navigationBarColor(R.color.blue_sharder1)
////                .statusBarColor(R.color.blue_sharder1)
////                .fitsSystemWindows(true)
////                .init();
//    }

    private void initAlweb() {
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(linearLayout, -1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
                .useDefaultIndicator(Color.parseColor("#1186d7"))//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
//                .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(mWebChromeClient) //WebChromeClient
//                .setWebLayout(getWebLayout())//下拉回弹View
                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setAgentWebUIController(new UIController(this)) //自定义UI  AgentWeb3.0.0 加入。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .useMiddlewareWebChrome(getMiddlewareWebChrome()) //设置WebChromeClient中间件，支持多个WebChromeClient，AgentWeb 3.0.0 加入。
                .useMiddlewareWebClient(getMiddlewareWebClient()) //设置WebViewClient中间件，支持多个WebViewClient， AgentWeb 3.0.0 加入。
//                .setDownloadListener(mDownloadListener) 4.0.0 删除该API//下载回调
//                .openParallelDownload()// 4.0.0删除该API 打开并行下载 , 默认串行下载。 请通过AgentWebDownloader#Extra实现并行下载
//                .setNotifyIcon(R.drawable.ic_file_download_black_24dp) 4.0.0删除该api //下载通知图标。4.0.0后的版本请通过AgentWebDownloader#Extra修改icon
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .go(getUrl()); //WebView载入该url地址的页面并显示。
        AgentWebConfig.debug();
        addBGChild(mAgentWeb.getWebCreator().getWebParentLayout()); // 得到 AgentWeb 最底层的控件
        if (mAgentWeb != null) {
            //注入对象
            mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(mAgentWeb, this));
        }

        // AgentWeb 4.0 开始，删除该类以及删除相关的API
//        DefaultMsgConfig.DownloadMsgConfig mDownloadMsgConfig = mAgentWeb.getDefaultMsgConfig().getDownloadMsgConfig();
        //  mDownloadMsgConfig.setCancel("放弃");  // 修改下载提示信息，这里可以语言切换

        // AgentWeb 没有把WebView的功能全面覆盖 ，所以某些设置 AgentWeb 没有提供 ， 请从WebView方面入手设置。
        mAgentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_NEVER);

    }

    protected void addBGChild(FrameLayout frameLayout) {
        TextView mTextView = new TextView(frameLayout.getContext());
        mTextView.setText("技术由 Geemi 提供");
        mTextView.setTextSize(16);
        mTextView.setTextColor(Color.parseColor("#727779"));
        frameLayout.setBackgroundColor(Color.parseColor("#272b2d"));
        FrameLayout.LayoutParams mFlp = new FrameLayout.LayoutParams(-2, -2);
        mFlp.gravity = Gravity.CENTER_HORIZONTAL;
        final float scale = frameLayout.getContext().getResources().getDisplayMetrics().density;
        mFlp.topMargin = (int) (15 * scale + 0.5f);
        frameLayout.addView(mTextView, 0, mFlp);
    }

    protected IWebLayout getWebLayout() {
        return new WebLayout(this);
    }

    protected PermissionInterceptor mPermissionInterceptor = new PermissionInterceptor() {

        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */
        @Override
        public boolean intercept(String url, String[] permissions, String action) {
//            Log.i(TAG, "mUrl:" + url + "  permission:" + mGson.toJson(permissions) + " action:" + action);
            return false;
        }
    };

    /**
     * 页面空白，请检查scheme是否加上， scheme://host:port/path?query&query 。
     *
     * @return mUrl
     */
    public String getUrl() {
        String target = "";
        String stringExtra = getIntent().getStringExtra(KEY_INTENT_LEVEL_SAVE);
        KLog.i("webView====url=="+stringExtra);
        if (TextUtils.isEmpty(target = stringExtra)) {
            target = "https://android.myapp.com/myapp/detail.htm?apkName=com.dzrcx.jiaan&ADTAG=mobile";
        }
        return target;
    }

    protected com.just.agentweb.WebChromeClient mWebChromeClient = new com.just.agentweb.WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //  super.onProgressChanged(view, newProgress);
            Log.i(TAG, "onProgressChanged:" + newProgress + "  view:" + view);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mTitleTextView != null && !TextUtils.isEmpty(title)) {
                if (title.length() > 10) {
                    title = title.substring(0, 10).concat("...");
                }
            }
            mTitleTextView.setText(title);
        }
    };
    protected com.just.agentweb.WebViewClient mWebViewClient = new com.just.agentweb.WebViewClient() {

        private HashMap<String, Long> timer = new HashMap<>();

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @SuppressLint("NewApi")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return shouldOverrideUrlLoading(view, request.getUrl() + "");
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
//            Log.i(TAG, "view:" + new Gson().toJson(view.getHitTestResult()));
            Log.i(TAG, "mWebViewClient shouldOverrideUrlLoading:" + url);
            //intent:// scheme的处理 如果返回false ， 则交给 DefaultWebClient 处理 ， 默认会打开该Activity  ， 如果Activity不存在则跳到应用市场上去.  true 表示拦截
            //例如优酷视频播放 ，intent://play?...package=com.youku.phone;end;
            //优酷想唤起自己应用播放该视频 ， 下面拦截地址返回 true  则会在应用内 H5 播放 ，禁止优酷唤起播放该视频， 如果返回 false ， DefaultWebClient  会根据intent 协议处理 该地址 ， 首先匹配该应用存不存在 ，如果存在 ， 唤起该应用播放 ， 如果不存在 ， 则跳到应用市场下载该应用 .
            if (url.startsWith("intent://") && url.contains("com.youku.phone")) {
                return true;
            }

			/*else if (isAlipay(view, mUrl))   //1.2.5开始不用调用该方法了 ，只要引入支付宝sdk即可 ， DefaultWebClient 默认会处理相应url调起支付宝
			    return true;*/
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.i(TAG, "mUrl:" + url + " onPageStarted  target:" + getUrl());
            timer.put(url, System.currentTimeMillis());
            if (url.equals(getUrl())) {
                pageNavigator(View.GONE);
            } else {
                pageNavigator(View.VISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (timer.get(url) != null) {
                long overTime = System.currentTimeMillis();
                Long startTime = timer.get(url);
                Log.i(TAG, "  page mUrl:" + url + "  used time:" + (overTime - startTime));
            }

        }
        /*错误页回调该方法 ， 如果重写了该方法， 上面传入了布局将不会显示 ， 交由开发者实现，注意参数对齐。*/
	   /* public void onMainFrameError(AbsAgentWebUIController agentWebUIController, WebView view, int errorCode, String description, String failingUrl) {

            Log.i(TAG, "AgentWebFragment onMainFrameError");
            agentWebUIController.onMainFrameError(view,errorCode,description,failingUrl);

        }*/

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);

//			Log.i(TAG, "onReceivedHttpError:" + 3 + "  request:" + mGson.toJson(request) + "  errorResponse:" + mGson.toJson(errorResponse));
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
//			Log.i(TAG, "onReceivedError:" + errorCode + "  description:" + description + "  errorResponse:" + failingUrl);
        }
    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(MessageEventbus messageWrap) {
        KLog.i("messageWrap====="+messageWrap.toString());
        TempModel tempModel = JsonUtils.getArrJson((String) messageWrap.getObject(),TempModel.class);
        if (tempModel.getMarkerCode() == -2){
            finish();
            return;
        }
        if (tempModel.getReturnCode() == -1){
            ToastUtils.showLong("您的信息不存在,请联系管理员！");
            finish();
            return;
        }
        if (tempModel.getMarkerCode() == 0 || tempModel.getProjectId().length() == 0 ){
            ToastUtils.showLong("数据获取失败！");
            return;
        }
        projectId = tempModel.getProjectId();//获取项目ID
        Bundle mBundle = new Bundle();
        mBundle.putString(KEY_INTENT_PROJECT, projectId);
        if (tempModel.getMarkerCode() == GEEMIFACE_TAGCODE_CONTRAST){//对比
            MyUtil.pictureMode(true, false, 1, this, 8003);
        }else if (tempModel.getMarkerCode() == GEEMIFACE_TAGCODE_GATHER){//采集
            mBundle.putInt(KEY_INTENT_LEVEL_SAVE, GEEMIFACE_TAGCODE_GATHER);
            mBundle.putBoolean(GEEMIFACE_CARDKEY, false);
            MyIntentUtil.jumpTag(this, "人员录入", RouterFragmentPath.GeemiRouterPath.PAGER_SAVEPERSON, null, "",true,mBundle);
        }else if (tempModel.getMarkerCode() == GEEMIFACE_TAGCODE_SING){//打卡
            mBundle.putInt(KEY_INTENT_LEVEL_SAVE, GEEMIFACE_TAGCODE_SING);
            MyIntentUtil.startActivityForResult(this, FaceLivenessExpActivity.class, mBundle);
        }else if (tempModel.getMarkerCode() == GEEMIFACE_ARCODE){//AR
            MyIntentUtil.startActivityForResult(this, ARActivity.class, mBundle);
        }else if (tempModel.getMarkerCode() == GEEMIFACE_BROADCAST){//广播
            mBundle.putInt(KEY_INTENT_LEVEL_SAVE, GEEMIFACE_BROADCAST);
            MyIntentUtil.startActivityForResult(this, SCActivity.class, mBundle);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        KLog.i("requestCode=====" + requestCode+"====="+selectList.size());
        if (selectList.size() == 0){
            return;
        }
        sBitMap = bitmapToBase64(ImageToBitmap(selectList.get(0).getRealPath(), this));
        comparisonFace();
    }

    //人脸对比
    public void comparisonFace() {
        Map<String, String> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("images", sBitMap);
        GeemiFaceManager.getHttpModel().getData(1001, GeemiHttpImpl.Method_POST, RouterUrl.COMPARISONFACE, params, this);
    }

    @Override
    public void onComplete(int tag, String json) {
        super.onComplete(tag, json);
        KLog.i("tag===" + tag + "=====json====" + json);
        PersonInfoByidCardModel personInfoByidCardModel = JsonUtils.getArrJson(json,PersonInfoByidCardModel.class);
        if (personInfoByidCardModel.isSuccess() && personInfoByidCardModel.getResult() != null ){
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_INTENT_LEVEL_SAVE, GEEMIFACE_TAGCODE_CONTRAST);
            bundle.putSerializable("personInfo",personInfoByidCardModel.getResult());
            MyIntentUtil.jumpTag(this, "人员对比", RouterFragmentPath.GeemiRouterPath.PAGER_CLOCKPUNCH, null, "",true,bundle);
            Const.GEEMIFACE_IMAGE = sBitMap;
            return;
        }
        ToastUtils.showLong(personInfoByidCardModel.getMessage());
    }

    protected void initView() {
        text_face_title = findViewById(R.id.text_face_title);
        relativeLayout = findViewById(R.id.relative_toorbar);
        relative_title = findViewById(R.id.relative_title);
        mBackImageView = findViewById(R.id.iv_back);
        mFinishImageView = findViewById(R.id.iv_finish);
        mTitleTextView = findViewById(R.id.toolbar_title);
        mBackImageView.setOnClickListener(mOnClickListener);
        mFinishImageView.setOnClickListener(mOnClickListener);
        mMoreImageView = findViewById(R.id.iv_more);
        mMoreImageView.setOnClickListener(mOnClickListener);
        text_face_title.setLeftImageViewClickListener(imageView -> {
           if (ViewOnClickUtils.isFastClick()){
               return;
           }
           finish();
        });
        if (sTag.contains("h5")){
            MyUtil.listStartVoew(relativeLayout);
            MyUtil.listEndView(relative_title);
        }else {
            MyUtil.listEndView(relativeLayout);
            MyUtil.listStartVoew(relative_title);
        }
        pageNavigator(View.GONE);
    }


    private void pageNavigator(int tag) {
        mBackImageView.setVisibility(tag);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.iv_back) {// true表示AgentWeb处理了该事件
                if (!mAgentWeb.back()) {
                    finish();
                }
            } else if (i == R.id.iv_finish) {
                finish();

            } else if (i == R.id.iv_more) {
                showPoPup(v);

            }
        }
    };

    /**
     * 打开浏览器
     *
     * @param targetUrl 外部浏览器打开的地址
     */
    private void openBrowser(String targetUrl) {
        if (TextUtils.isEmpty(targetUrl) || targetUrl.startsWith("file://")) {
            Toast.makeText(this, targetUrl + " 该链接无法使用浏览器打开。", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri mUri = Uri.parse(targetUrl);
        intent.setData(mUri);
        startActivity(intent);
    }


    /**
     * 显示更多菜单
     *
     * @param view 菜单依附在该View下面
     */
    private void showPoPup(View view) {
        if (mPopupMenu == null) {
            mPopupMenu = new PopupMenu(this, view);
            mPopupMenu.inflate(R.menu.toolbar_menu);
            mPopupMenu.setOnMenuItemClickListener(mOnMenuItemClickListener);
        }
        mPopupMenu.show();
    }

    /**
     * 菜单事件
     */
    private PopupMenu.OnMenuItemClickListener mOnMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            int i = item.getItemId();
            if (i == R.id.refresh) {
                if (mAgentWeb != null) {
                    mAgentWeb.getUrlLoader().reload(); // 刷新
                }
                return true;
            } else if (i == R.id.copy) {
                if (mAgentWeb != null) {
                    toCopy(CommonWebView.this, mAgentWeb.getWebCreator().getWebView().getUrl());
                }
                return true;
            } else if (i == R.id.default_browser) {
                if (mAgentWeb != null) {
                    openBrowser(mAgentWeb.getWebCreator().getWebView().getUrl());
                }
                return true;
            } else if (i == R.id.default_clean) {
                toCleanWebCache();
                return true;
            } else {
                return false;
            }

        }
    };

    /**
     * 测试错误页的显示
     */
    private void loadErrorWebSite() {
        if (mAgentWeb != null) {
            mAgentWeb.getUrlLoader().loadUrl("http://www.unkownwebsiteblog.me");
        }
    }

    /**
     * 清除 WebView 缓存
     */
    private void toCleanWebCache() {
        if (this.mAgentWeb != null) {
            //清理所有跟WebView相关的缓存 ，数据库， 历史记录 等。
            this.mAgentWeb.clearWebCache();
            Toast.makeText(this, "已清理缓存", Toast.LENGTH_SHORT).show();
            //清空所有 AgentWeb 硬盘缓存，包括 WebView 的缓存 , AgentWeb 下载的图片 ，视频 ，apk 等文件。
//            AgentWebConfig.clearDiskCache(this.getContext());
        }
    }


    /**
     * 复制字符串
     *
     * @param context
     * @param text
     */
    private void toCopy(Context context, String text) {

        ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        mClipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));

    }

    /**
     * MiddlewareWebClientBase 是 AgentWeb 3.0.0 提供一个强大的功能，
     * 如果用户需要使用 AgentWeb 提供的功能， 不想重写 WebClientView方
     * 法覆盖AgentWeb提供的功能，那么 MiddlewareWebClientBase 是一个
     * 不错的选择 。
     *
     * @return
     */
    protected MiddlewareWebClientBase getMiddlewareWebClient() {
        return this.mMiddleWareWebClient = new MiddlewareWebViewClient() {
            /**
             *
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("agentweb")) { // 拦截 url，不执行 DefaultWebClient#shouldOverrideUrlLoading
                    Log.i(TAG, "agentweb scheme ~");
                    return true;
                }

                if (super.shouldOverrideUrlLoading(view, url)) { // 执行 DefaultWebClient#shouldOverrideUrlLoading
                    return true;
                }
                // do you work
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        };
    }

    protected MiddlewareWebChromeBase getMiddlewareWebChrome() {
        return this.mMiddleWareWebChrome = new MiddlewareChromeClient() {
        };
    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();//恢复
        super.onResume();
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause(); //暂停应用内所有WebView ， 调用mWebView.resumeTimers();/mAgentWeb.getWebLifeCycle().onResume(); 恢复。
        super.onPause();
    }

    @Override
    public boolean onFragmentKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        getSupportFragmentManager().putFragment(outState, TYPE_KEY, mFragment.get());
    }

}

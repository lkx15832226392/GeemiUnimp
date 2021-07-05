package com.geemi.facelibrary.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import com.geemi.facelibrary.router.RouterServePath;
import com.geemi.facelibrary.utils.MD5Util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.BuildConfig;
import me.goldze.mvvmhabit.http.cookie.CookieJarImpl;
import me.goldze.mvvmhabit.http.cookie.store.PersistentCookieStore;
import me.goldze.mvvmhabit.http.interceptor.BaseInterceptor;
import me.goldze.mvvmhabit.http.interceptor.logging.Level;
import me.goldze.mvvmhabit.http.interceptor.logging.LoggingInterceptor;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.Utils;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * RetrofitClient封装单例类, 实现网络请求
 * lkx
 */
public class RetrofitClient {
    //超时时间
    private static final int DEFAULT_TIMEOUT = 20;
    //缓存时间
    private static final int CACHE_TIMEOUT = 10 * 1024 * 1024;

    public static final String CUSTOM_REPEAT_REQ_PROTOCOL = "MY_CUSTOM_REPEAT_REQ_PROTOCOL";

    //服务端根路径
    public static String baseUrl = RouterServePath.rootUrl;

    private Context mContext = Utils.getContext();

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    private Cache cache = null;
    private File httpCacheDirectory;

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitClient() {
        this(baseUrl, null);
    }

    //Value 里面保存的是时间，我们实际业务是有用的
    public static Map<String, Long> requestIdsMap = new HashMap<>();

    private RetrofitClient(String url, Map<String, String> headers) {
        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "goldze_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, CACHE_TIMEOUT);
            }
        } catch (Exception e) {
            KLog.e("Could not create http cache", e);
        }
        //2. 请求的拦截处理
        /**
         * 如果你的 token 是空的，就是还没有请求到 token，比如对于登陆请求，是没有 token 的，
         * 只有等到登陆之后才有 token，这时候就不进行附着上 token。另外，如果你的请求中已经带有验证 header 了，
         * 比如你手动设置了一个另外的 token，那么也不需要再附着这一个 token.
         */
        Interceptor mRequestInterceptor = chain -> {
            Request originalRequest = chain.request();

            Request myRequest = originalRequest.newBuilder()
                    .header("Authorization", "no need oauth here")
                    .header("Connection", "Keep-Alive")  //新添加，time-out默认是多少呢？
//                    .header("Content-Encoding", "gzip")  //使用GZIP 压缩内容
                    .build();

            //拦截处理重复的HTTP 请求,类似 防止快速点击按钮去重 可以不去处理了，全局统一处理
            String requestKey = MD5Util.getUpperMD5Str(myRequest.toString());

            if (null == requestIdsMap.get(requestKey)) {
                requestIdsMap.put(requestKey, System.currentTimeMillis());
                Log.e("REPEAT-REQUEST", "注册请求:" + requestKey + " ----  " + Thread.currentThread().getName());
            } else {
                //如果是重复的请求，抛出一个自定义的错误，这个错误大家根据自己的业务定义吧
                Log.i("REPEAT-REQUEST", "重复请求" + requestKey + "  ---重复请求 ----" + Thread.currentThread().getName());
                return new Response.Builder()
                        .protocol(Protocol.get(CUSTOM_REPEAT_REQ_PROTOCOL))
                        .request(myRequest) //multi thread
                        .build();
            }
            Response originalResponse = chain.proceed(myRequest);
            //把统一拦截的header 打印出来
            logRequestHeaders(myRequest);

            return originalResponse.newBuilder().build();
        };

        /**
         * 如果不喜欢系统的Http 的打印方式，可以自己去实现Interceptor 接口
         * 但是统一拦截的header 是无法打印的，因为是在请求发出后统一拦截打印的。
         *
         */
        HttpInterceptor myHttpInterceptor = new HttpInterceptor(requestIdsMap);
        myHttpInterceptor.setLevel(HttpInterceptor.Level.BODY);

        //Retrofit 默认的网络请求执行期（callFactory）就是OkHttpClient
        //这里仍然需要指定是因为参数，行为的设置等
        HttpsUtilss.SSLParams sslParams = HttpsUtilss.getSslSocketFactory();
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)))
//                .cache(cache)
                .addInterceptor(new BaseInterceptor(headers))
//                .addInterceptor(new CacheInterceptor())
                .addInterceptor(myHttpInterceptor)
                .addNetworkInterceptor(mRequestInterceptor)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .addInterceptor(new LoggingInterceptor
                        .Builder()//构建者模式
                        .loggable(BuildConfig.DEBUG) //是否开启日志打印
                        .setLevel(Level.BASIC) //打印的等级
                        .log(Platform.INFO) // 打印类型
                        .request("Request") // request的Tag
                        .response("Response")// Response的Tag
                        .addHeader("log-header", "I am the log request header.") // 添加打印头, 注意 key 和 value 都不能是中文
                        .build()
                )
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
        KLog.i("url==1111111===" + url);

    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    /**
     * /**
     * execute your customer API
     * For example:
     * MyApiService service =
     * RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);
     * <p>
     * RetrofitClient.getInstance(MainActivity.this)
     * .execute(service.lgon("name", "password"), subscriber)
     * * @param subscriber
     */

    public static <T> T execute(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        return null;
    }

    /**
     * 打印全局统一拦截添加的Http Headers
     * <p>
     * 全局拦截的http 没法在配置中直接打印处理，因为先http 请求然后打印然后拦截添加的
     *
     * @param request
     */
    private static void logRequestHeaders(Request request) {
        Log.w("OKhttp ", "  开始打印HTTP请求  Headers \n");
        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                Log.i("OKhttp: " + i + " ", name + ": " + headers.value(i));
            }
        }
        Log.w("OKhttp ", "  打印HTTP请求完成  Headers \n");
    }
}

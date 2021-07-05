package com.geemi.facelibrary.interfaces;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * API接口
 */

public interface ApiService {
//    @GET("action/apiv2/banner?catalog=1")
//    Observable<BaseResponse<DemoEntity>> demoGet();
//
//    @FormUrlEncoded
//    @POST("action/apiv2/banner")
//    Observable<BaseResponse<DemoEntity>> demoPost(@Field("catalog") String catalog);
    //https://www.baidu.com/?tn=02049043_8_pg&ch=2
    @POST("getHomeStationCarList.do")
    Observable<ResponseBody> postQueryJourneyByUsers(@QueryMap Map<String,String> params);
//    @POST("/service/getActivityConfigList.do")
//    Observable<CampaignBen> postCampaign(@QueryMap Map<String, String> params);
//    @POST("/service/getUserInfoFromSkey.do")
//    Observable<UserModel> postUserInfo(@QueryMap Map<String, String> params);

    /**
     * 首页banner
     *
     * @param route
     * @return
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("other")
    Observable<ResponseBody> executePost(@Body RequestBody route);


    @POST
    @FormUrlEncoded
    <T>Observable<ResponseBody> executePost(@Url String var1, @FieldMap Map<String, String> var2);

    @GET
    Observable<ResponseBody> executeGet(@Url String var1, @QueryMap Map<String, String > var2);
}

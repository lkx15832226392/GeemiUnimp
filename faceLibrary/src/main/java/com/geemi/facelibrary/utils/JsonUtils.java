package com.geemi.facelibrary.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dzr on 2017/9/11.
 */

public class JsonUtils {

    public static <E> E getArrJson(String url, Class<E> clazz) {
        // E e;
        Gson gson = new Gson();

        return gson.fromJson(url,clazz);
    }

    public static <T> T getJson(String response, Class<T> cls)
            throws JsonSyntaxException {
        return new Gson().fromJson(response, cls);
    }

    public static <T> T getJson(String response, TypeToken<T> type)
            throws JsonSyntaxException {
        return new Gson().fromJson(response, type.getType());
    }

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }

    /**
     * @param response 需要第一层解析的数据
     * @param params   想要解析第一层数据的哪一节点
     * @throws JSONException
     */
    public static String getDataJson(String response, String params)
            throws JSONException {
        JSONObject jsonObject = new JSONObject(response);

//        return (String) jsonObject.get(params);
        return jsonObject.get(params).toString();
    }

}

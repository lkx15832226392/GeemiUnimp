package com.geemi.facelibrary.utils.shared;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SharedPreferencesUtil {

    private SharedPreferences.Editor editor;
    private static final String SHARED_PREFERENCES_QUALITY_LEVEL_VALUE = "quality_level_value";

    public SharedPreferencesUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_QUALITY_LEVEL_VALUE,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * 存储
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.commit();
    }

    /**
     * 获取保存的数据
     */
    public Object getSharedPreference(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        } else {
            return sharedPreferences.getString(key, null);
        }
    }

    /**
     * 移除某个key值已经对应的值
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否存在
     */
    public Boolean contain(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }


    public static final String mTAG = "lkx_SharedPreferences";
    //文件名称为config
    private static final String PREFERENCE_NAME = "config";
    //可以在此定义常亮，当做key使用
    //版本号
    public static final String APK_VERSION = "APK_VERSION";
    //下载地址
    public static final String APK_DOWNLOAD_URL = "APK_DOWNLOAD_URL";

    private static SharedPreferences sharedPreferences;



    /**
     * 写入Boolean变量至sharedPreferences中
     *
     * @param context 上下文环境
     * @param key     存储节点名称
     * @param value   存储节点的值
     */
    public static void putBoolean(Context context, String key, boolean value) {
        //(存储节点文件名称，读写方式)
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 读取boolean标识从sharedPreferences中
     *
     * @param context 上下文环境
     * @param key     存储节点名称
     * @param value   没有此节点的默认值
     * @return 默认值或者此节点读取到的结果
     */
    public static boolean getBoolean(Context context, String key, boolean value) {
        //(存储节点文件名称,读写方式)
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean(key, value);
    }

    /**
     * 写入String变量至sharedPreferences中
     *
     * @param context 上下文环境
     * @param key     存储节点名称
     * @param value   存储节点的值String
     */
    public static void putString(Context context, String key, String value) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * 读取String标识从sharedPreferences中
     *
     * @param context  上下文环境
     * @param key      存储节点名称
     * @param defValue 没有此节点的默认值
     * @return 返回默认值或者此节点读取到的结果
     */
    public static String getString(Context context, String key, String defValue) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, defValue);
    }

    /**
     * 写入int变量至sharedPreferences中
     *
     * @param context 上下文环境
     * @param key     存储节点名称
     * @param value   存储节点的值String
     */
    public static void putInt(Context context, String key, int value) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putInt(key, value).commit();
    }

    /**
     * 读取int标识从sharedPreferences中
     *
     * @param context  上下文环境
     * @param key      存储节点名称
     * @param defValue 没有此节点的默认值
     * @return 返回默认值或者此节点读取到的结果
     */
    public static int getInt(Context context, String key, int defValue) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getInt(key, defValue);
    }

    /**
     * 从sharedPreferences中移除指定节点
     *
     * @param context 上下文环境
     * @param key     需要移除节点的名称
     */
    public static void remove(Context context, String key) {
        //存储节点文件的名称，读写方式
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().remove(key).commit();
    }


    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }


    public static long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }
}

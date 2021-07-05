package com.geemi.facelibrary.model;

public class Const {

    //是否登录
    public static final String IS_LOGIN = "is_login";

    // 用于sharepreference的key
    public static final String KEY_QUALITY_LEVEL_SAVE = "quality_save";
    //webView传递的URL
    public static final String KEY_INTENT_WEBVIEWKEY = "URL_KEY";

    //用于intente得key
    public static final String KEY_INTENT_LEVEL_SAVE = "intent_save";
    //项目ID
    public static final String KEY_INTENT_PROJECT = "project";
    //人员IDpersonId
    public static final String KEY_INTENT_PERSONID = "personId";
    //人脸前后摄像头调用key
    public static final String KEY_CAMERA_FACING = "camera_facing";

    // 用于Intent传参
    public static final String INTENT_QUALITY_LEVEL = "intent_quality";
    public static final String INTENT_QUALITY_TITLE = "intent_quality_title";
    public static final String INTENT_QUALITY_LEVEL_PARAMS = "intent_quality_params";

    // quality类型：0：正常、1：宽松、2：严格、3：自定义
    public static final int QUALITY_NORMAL = 0;
    public static final int QUALITY_LOW = 1;
    public static final int QUALITY_HIGH = 2;
    public static final int QUALITY_CUSTOM = 3;

    // 页面跳转请求code和回复code
    public static final int REQUEST_QUALITY_CONTROL = 100;
    public static final int RESULT_QUALITY_CONTROL = 101;

    public static final int REQUEST_QUALITY_PARAMS = 102;
    public static final int RESULT_QUALITY_PARAMS = 103;

    // dialog类型
    public static final int DIALOG_SAVE_SAVE_BUTTON = 1;    // 非自定义页面，点击『保存』
    public static final int DIALOG_SAVE_RETURN_BUTTON = 2;  // 非自定义页面，点击『返回』
    public static final int DIALOG_RESET_DEFAULT = 3;       // 非自定义页面，点击『恢复默认』
    public static final int DIALOG_SAVE_CUSTOM_MODIFY = 4;  // 自定义页面，点击『返回』

    //管理员录入
    public static final int GEEMIFACE_TAGCODE_MANAGER = 4;

    //gather 采集
    public static final int GEEMIFACE_TAGCODE_GATHER = 1;
    //对比contrast
    public static final int GEEMIFACE_TAGCODE_CONTRAST = 2;
    //打卡
    public static final int GEEMIFACE_TAGCODE_SING = 3;
    //AR党建
    public static final int GEEMIFACE_ARCODE = 5;
    //广播broadcast
    public static final int GEEMIFACE_BROADCAST = 6;


    //刷卡标识
    public static final String GEEMIFACE_CARDKEY = "key_card";
    //公共表头
    public static final String GEEMIFACE_TITLE = "title";
    //人脸base信息
    public static String GEEMIFACE_IMAGE = "face_image";

}

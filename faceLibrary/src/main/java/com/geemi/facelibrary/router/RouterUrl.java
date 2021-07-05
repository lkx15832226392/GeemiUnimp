package com.geemi.facelibrary.router;


import static com.geemi.facelibrary.router.RouterServePath.rootUrl;

public class RouterUrl {
    private static final String ROUTER_EXP = ".do";

    //登陆
    private static String PAGER_LOGIN = "app/login/v1/login";
    public static String LOGIN = rootUrl + PAGER_LOGIN ;

    ///app/handter/v1/getProjectList 项目列表
    private static String PAGER_GETPROJECTLIST = "app/handter/v1/getProjectList";
    public static String GETPROJECTLIST = rootUrl + PAGER_GETPROJECTLIST;

    //获取项目展示图/app/handter/v1/getProjectPhoto
    private static String PAGER_GETPROJECTPHOTO = "app/handter/v1/getProjectPhoto";
    public static String GETPROJECTPHOTO = rootUrl + PAGER_GETPROJECTPHOTO;

    ///app/handter/v1/getAttendanceInfo 出勤人数统计
    private static String PAGER_GETATTENDANCEINFO = "app/handter/v1/getAttendanceInfo";
    public static String GETATTENDANCEINFO = rootUrl + PAGER_GETATTENDANCEINFO;

    //出勤率趋势图 app/handter/v1/getAttachmentQs
    private static String PAGER_GETATTACHMENTQS = "app/handter/v1/getAttachmentQs";
    public static String GETATTACHMENTQS = rootUrl + PAGER_GETATTACHMENTQS;

    //工种在册分析 /app/handter/v1/getPersonJobs
    private static String PAGER_GETPERSONJOBS = "app/handter/v1/getPersonJobs";
    public static String GETPERSONJOBS = rootUrl + PAGER_GETPERSONJOBS;

    //根据身份证号查询人员信息 /app/handter/v1/getPersonInfoByIdcard
    private static String PAGER_GETPERSONINFOBYIDCARD = "app/handter/v1/getPersonInfoByIdcard";
    public static String GETPERSONINFOBYIDCARD = rootUrl + PAGER_GETPERSONINFOBYIDCARD;

    //查询所有所属单位 /app/handter/v1/getAllCompany
    private static String PAGER_GETALLCOMPANY = "app/handter/v1/getAllCompany";
    public static String GETALLCOMPANY = rootUrl + PAGER_GETALLCOMPANY;
    // 查询所有班组 /app/handter/v1/getAllServiceTeam
    private static String PAGER_GETALLSERVICETEAM = "app/handter/v1/getAllServiceTeam";
    public static String GETTALLSERVICETEAM = rootUrl + PAGER_GETALLSERVICETEAM;

    //人脸比对 /app/handter/v1/comparisonFace
    private static String PAGER_COMPARISONFACE = "app/handter/v1/comparisonFace";
    public static String COMPARISONFACE = rootUrl + PAGER_COMPARISONFACE;

    //人脸录入 /app/handter/v1/recordFace
    private static String PAGER_RECORDFACE = "app/handter/v1/recordFace";
    public static String RECORDFACE = rootUrl + PAGER_RECORDFACE;

    //人员信息 人脸保存 /app/handter/v1/savePerson
    private static String PAGER_SAVEPERSON = "app/handter/v1/savePerson";
    public static String SAVEPERSON = rootUrl + PAGER_SAVEPERSON;

    //打卡 /app/handter/v1/clockPunch
    private static String PAGER_CLOCKPUNCH = "app/handter/v1/clockPunch";
    public static String CLOCKPUNCH = rootUrl + PAGER_CLOCKPUNCH;

    //人脸打卡检测 app/handter/v1/clockFace
    private static String PAGER_CLOCKFACE = "app/handter/v1/clockFace";
    public static String CLOCKFACE = rootUrl + PAGER_CLOCKFACE;

    //获取工种字典数据 app/handter/v1/getDictItems
    private static String PAGER_GETDICTITEMS = "app/handter/v1/getDictItems";
    public static String GETDICTITEMS = rootUrl + PAGER_GETDICTITEMS;

    // 人脸照片录入检验 /app/handter/v1/faceCheck
    private static String PAGER_FACECHECK = "app/handter/v1/faceCheck";
    public static String FACECHECK = rootUrl + PAGER_FACECHECK;

    ///app/workbench/v1/getRadio 广播列表
    private static String PAGER_GETRADIO = "app/workbench/v1/getRadio";
    public static String GETRADIO = rootUrl + PAGER_GETRADIO;


}
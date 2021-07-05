package com.geemi.facelibrary.router;


import com.geemi.facelibrary.utils.shared.ServePathSP;

public class RouterServePath {
    private static String HTTP = "http://";
    private static String PORT = "9987";
    private static String ROUTR = "smartSiteService";//192.168.0.28
    //www.zhjz.cscecee.com
    //正式 39.107.190.158
    public static String BASESERVE = HTTP + "zhjz.cscecee.com" +  "/" + ROUTR + "/";//正式
//    public static String BASESERVE = HTTP + "47.93.97.212" + ":" + PORT + "/" + ROUTR + "/";//测试
//public static String BASESERVE = HTTP + "192.168.0.28" + ":" + PORT + "/" + ROUTR + "/";本地
    public static String rootUrl = getBaseUrl();
//    17733475134 wang123.
    public static final String SERVER_BACKUP = "http://test.xxx.com";
    public static final String SERVER_BACKUP2 = HTTP + "39.107.190.158" +  "/" + ROUTR + "/";
    public static final String SERVER_BACKUP3 = HTTP + "47.93.97.212" + ":" + PORT + "/" + ROUTR + "/";
    public static final String SERVER_BACKUP4 = HTTP + "192.168.0.28" + ":" + PORT + "/" + ROUTR + "/";

    public static String getBaseUrl() {
        if (!ServePathSP.getPublic().getString(ServePathSP.server).isEmpty()) {
            return ServePathSP.getPublic().getString(ServePathSP.server);
        }
        return BASESERVE;
    }


}

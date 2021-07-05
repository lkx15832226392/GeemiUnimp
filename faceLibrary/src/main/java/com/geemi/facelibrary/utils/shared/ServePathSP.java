package com.geemi.facelibrary.utils.shared;


import com.geemi.facelibrary.manager.GeemiFaceManager;

/**
 * Created by Thisfeng on 2017/3/9 0009 21:54
 * 项目中专门用来管理本地用户数据 和公共的数据的 SharedPreferences
 */

public class ServePathSP {

    private static ShareStorage accountStorage;
    private static ShareStorage userStorage;
    private static ShareStorage publicStorage;

    public static final String server = "server";//众享
    public static final String serverOne = "serverOne";//共享
    public static final String serverTWO = "serverTwo";//顺风车
    private static final String accounts = "accounts";
    public static final String user = "user";
    private static final String publicSP = "publicSP";
    public static ShareStorage getAccount() {
        if (accountStorage == null) {
            accountStorage = new ShareStorage(GeemiFaceManager.mContext, accounts);
        }
        return accountStorage;
    }

    public static ShareStorage getUser() {
        if (userStorage == null) {
            userStorage = new ShareStorage(GeemiFaceManager.mContext, user);
        }
        return userStorage;
    }

    public static ShareStorage getPublic() {
        if (publicStorage == null) {
            publicStorage = new ShareStorage(GeemiFaceManager.mContext, publicSP);
        }
        return publicStorage;
    }


    /**
     * 若要清空用户数据
     */
    public static void clearAllUserInfo() {
        ServePathSP.getUser().put(user, null);
//        SP.getUser().put(userInfo, null);
    }

}

package com.geemi.facelibrary.ui.sdialog_view;

import android.app.Activity;
import android.content.Context;

import com.geemi.facelibrary.router.RouterServePath;
import com.geemi.facelibrary.utils.shared.ServePathSP;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;


/**
 * Created by Thisfeng on 2017/3/9 0009 21:53
 */

public class ServerSelectDialog extends BaseServerDialog {
    private int serverType = 0;//0众享 1共享 2顺风车

    public ServerSelectDialog(Activity context) {
        super(context);
    }

    public ServerSelectDialog(Context context, int serverType) {
        super(context);
        this.serverType = serverType;
    }

    /**
     * @param selectedUrl 将地址设置到本地SP文件中
     */
    @Override
    protected void setServerUrl(String selectedUrl) {
        //这个SP中一般存储的是公共的参数包含服务器地址
        KLog.i("selectedUrl=====" + selectedUrl + "======" + serverType);
        if (serverType == 0) {
            ServePathSP.getPublic().put(ServePathSP.server, selectedUrl);
        }
    }

    @Override
    protected String getServerUrl() {
//        if (serverType == 0) {//
//            return RouterServePath.getBroadUrl();
//        } else {
//            return RouterServePath.getOvertUrl();
//        }
        return RouterServePath.getBaseUrl();
    }

    @Override
    protected List<String> defaultServerUrlList() {
        List<String> serverList = new ArrayList<>();
        serverList.add(RouterServePath.SERVER_BACKUP);
        serverList.add(RouterServePath.SERVER_BACKUP2);
        serverList.add(RouterServePath.SERVER_BACKUP3);
        serverList.add(RouterServePath.SERVER_BACKUP4);
        serverList.add(RouterServePath.BASESERVE);
        return serverList;
    }
}
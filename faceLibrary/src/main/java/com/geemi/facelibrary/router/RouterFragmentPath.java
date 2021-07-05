package com.geemi.facelibrary.router;

public class RouterFragmentPath {

    public static class GeemiRouterPath{
        private static final String GEEMIROUTERPATH = "/routerPath";
        //首页
        public static final String PAGER_HOME = GEEMIROUTERPATH + "/geemiHome";
        //登录
        public static final String PAGER_LOGIN = GEEMIROUTERPATH + "/geemiLogin";

        private static final String GEEMIFACELIBRARY = "/geemiFaceLibrary";
        //人员录入savePerson
        public static final String PAGER_SAVEPERSON = GEEMIFACELIBRARY + "/savePerson";
        //打卡 对比 clockPunch
        public static final String PAGER_CLOCKPUNCH = GEEMIFACELIBRARY + "/clockPunch";
        //证件扫描
        public static final String PAGER_SIMPLECAMERA = GEEMIFACELIBRARY + "/simpleCamera";
    }
}

package com.geemi.facelibrary.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import com.geemi.facelibrary.ui.ar.ARBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

public class GeemiFileUtils {

    /**
     * 获取文件下所有jpg文件名称
     *
     * @param fileAbsolutePath
     * @return
     */
    public static List<String> GetVideoFileName(String fileAbsolutePath) {
        List<String> fileList = new ArrayList<>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();

//        L.e("FILE==== SIZE ==== " + subFile.length);

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                // 判断是否为MP4结尾
                if (filename.trim().toLowerCase().endsWith(".jpg")) {
                    String nameStr = filename.substring(0, filename.indexOf("."));
                    fileList.add(nameStr);
                }
            }else if (subFile[iFileLength].isDirectory()){
                File[] subFile1 = subFile[iFileLength].listFiles();
//                L.e("FILE11111==== SIZE ==== " + subFile1.length);
                for (int i = 0; i < subFile1.length; i++) {
                    String filename = subFile1[i].getName();
                    // 判断是否为MP4结尾
                    if (filename.trim().toLowerCase().endsWith(".jpg")) {
                        String nameStr = filename.substring(0, filename.indexOf("."));
                        fileList.add(nameStr);
                    }
                }

            }
        }
        return fileList;
    }

    /**
     * 获取指定目录内所有文件路径
     * @param dirPath 需要查询的文件目录
     */
    public static List<ARBean> getAllFiles(String dirPath) {
        File f = new File(dirPath);
        if (!f.exists()) {//判断路径是否存在
            return null;
        }

        File[] files = f.listFiles();

        if(files==null){//判断权限
            return null;
        }

        List<ARBean> list = new ArrayList<>();
        for (File _file : files) {//遍历目录
            if(_file.isFile() && _file.getName().endsWith(".png")){
                String _name=_file.getName();
                String filePath = _file.getAbsolutePath();//获取文件路径
                String fileName = _file.getName().substring(0,_name.length()-4);//获取文件名
                Log.e("LOGCAT","fileName:"+fileName);
                Log.e("LOGCAT","filePath:"+filePath);

                ARBean bean = new ARBean();
                bean.setName(fileName);
                bean.setPath(filePath);
                list.add(bean);

            } else if(_file.isDirectory()){//查询子目录
               List<ARBean> ars = getAllFiles(_file.getAbsolutePath());
                for (ARBean ar : ars) {
                    list.add(ar);
                }
            } else{
            }
        }
        return list;
    }

    /**
     * 获取文件地址
     *
     * @param context
     * @param dir
     * @return
     */
    public static String getFilePath(Context context, String dir) {
        String directoryPath = "";
        //判断sd卡是否可用
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            directoryPath = context.getExternalFilesDir(dir).getAbsolutePath();
        } else {
            directoryPath = context.getFilesDir() + File.separator + dir;
        }
        KLog.e("FILE  psth ==== " + directoryPath);
        return directoryPath;
    }

    /**
     * 判断本地是否有该文件
     *
     * @param filePath
     * @return
     */
    public static boolean fileIsExists(String filePath) {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

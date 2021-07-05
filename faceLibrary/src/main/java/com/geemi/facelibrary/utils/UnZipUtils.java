package com.geemi.facelibrary.utils;

import android.annotation.SuppressLint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import me.goldze.mvvmhabit.utils.KLog;

public class UnZipUtils {
    public static final String TAG="ZIP";
    public UnZipUtils(){

    }

    /**
     * 解压zip到指定的路径
     * @param zipFileString ZIP的名称
     * @param outPathString 要解压缩路径
     * @throws Exception
     */
    @SuppressLint("NewApi")
    public static void UnZipFolder(String zipFileString, String outPathString) throws Exception {
       ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString), Charset.forName("GBK"));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                //获取部件的文件夹名
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();
            } else {
               KLog.e(outPathString + File.separator + szName);
                File file = new File(outPathString + File.separator + szName);
                if (!file.exists()){
                    KLog.e( "Create the file:" + outPathString + File.separator + szName);
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                // 获取文件的输出流
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // 读取（字节）字节到缓冲区
                while ((len = inZip.read(buffer)) != -1) {
                    // 从缓冲区（0）位置写入（字节）字节
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }


}

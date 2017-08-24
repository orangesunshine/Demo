package com.orange.demo.constance;

import android.os.Environment;

import com.orange.demo.utils.LogUtil;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class Const {
    //requestCode
    public static final int REQUEST_SETTING = 0x0010;

    /**
     * final
     */
    private final String GLIDE_CACHE_DIR = "glide_cache";
    /**
     * static
     */
    public static String GLIDE_CACHE_ROOT;

    /**
     * root
     */

    public static void init() {
        LogUtil.log("getExternalStorageState: " + Environment.getExternalStorageState() +
                ",\ngetDataDirectory: " + Environment.getDataDirectory() +
                ",\ngetRootDirectory: " + Environment.getRootDirectory() +
                ",\ngetDownloadCacheDirectory: " + Environment.getDownloadCacheDirectory() +
                ",\ngetDownloadCacheDirectory: " + Environment.getDownloadCacheDirectory() +
                ",\ngetExternalStorageDirectory: " + Environment.getExternalStorageDirectory() +
                ",\nDIRECTORY_ALARMS: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS) +
                ",\nDIRECTORY_ALARMS: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +
//                ",\nDIRECTORY_ALARMS: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) +
                ",\nDIRECTORY_ALARMS: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +
                ",\nDIRECTORY_ALARMS: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) +
                ",\nDIRECTORY_ALARMS: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) +
                ",\nDIRECTORY_ALARMS: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS) +
                ",\nDIRECTORY_ALARMS: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
                ",\nDIRECTORY_ALARMS: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS) +
                ",\nDIRECTORY_ALARMS: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES)
        );
    }
}

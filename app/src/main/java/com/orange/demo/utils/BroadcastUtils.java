package com.orange.demo.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class BroadcastUtils {

    /**
     * 发送一个通知系统扫描图片的系统广播。
     */
    public static void sendFileScanBroadcast(Context context, String path) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(path));
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    /**
     * 发送一个通知系统扫描图片的系统广播。
     */
    public static void sendDirScanBroadcast(Context context, String path) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_DIR");
        Uri uri = Uri.fromFile(new File(path));
        intent.setData(uri);
        context.sendBroadcast(intent);
    }
}

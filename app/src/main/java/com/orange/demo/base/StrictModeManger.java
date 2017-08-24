package com.orange.demo.base;

import android.annotation.SuppressLint;
import android.os.StrictMode;

import com.orange.demo.utils.LogUtil;


/**
 * Android严苛模式管理。
 * 注意：
 * 		1、开发阶段启用StrictMode，release版本禁用。
 * 		2、严格模式无法监控JNI中的磁盘IO和网络请求。
 * 		3、应用中并非需要解决全部的违例情况，比如有些IO操作必须在主线程中进行
 * @author zhanglei
 * @version 1.0.3
 */
public class StrictModeManger {
	@SuppressLint("NewApi")
	public static void setStrictMode() {
		try {
			StrictMode.setThreadPolicy(
					new StrictMode.ThreadPolicy.Builder()//线程策略检测
							.detectCustomSlowCalls()//检测执行或者潜在执行缓慢的代码
							.detectDiskReads()
							.detectDiskWrites()
							.detectNetwork()
							.penaltyLog()
//							.penaltyDialog()//触发违规时，显示对违规信息对话框。
							.build());
			StrictMode.setVmPolicy(
					new StrictMode.VmPolicy.Builder()//虚拟机策略检测
							.detectActivityLeaks()//检查 Activity 的内存泄露情况
							.detectLeakedSqlLiteObjects()//检查SQLiteCursor或者其他SQLite对象是否被正确关闭
							.detectLeakedClosableObjects()//资源没有正确关闭时提醒
							.penaltyLog()
//							.penaltyDeath()//当触发违规条件时，直接Crash掉当前应用程序。
							.build());
		} catch (Exception e) {
			if(e!=null){
				LogUtil.log(e.getLocalizedMessage());
			}
		}
	}
}

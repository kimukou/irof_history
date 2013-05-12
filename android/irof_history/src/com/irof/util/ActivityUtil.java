package com.irof.util;import java.io.File;import android.app.Activity;import android.content.SharedPreferences.Editor;import android.annotation.SuppressLint;@SuppressLint("NewApi")public class ActivityUtil {	public static void StrictMode11() {		android.os.StrictMode				.setThreadPolicy(new android.os.StrictMode.ThreadPolicy.Builder()						.detectDiskReads().detectDiskWrites().detectNetwork()						.penaltyLog().penaltyFlashScreen().build());		android.os.StrictMode				.setVmPolicy(new android.os.StrictMode.VmPolicy.Builder()						.detectLeakedSqlLiteObjects().penaltyLog()						.penaltyDeath().build());	}	public static void StrictMode09() {		android.os.StrictMode				.setThreadPolicy(new android.os.StrictMode.ThreadPolicy.Builder()						.detectDiskReads().detectDiskWrites().detectNetwork()						.penaltyLog().build());		android.os.StrictMode				.setVmPolicy(new android.os.StrictMode.VmPolicy.Builder()						.detectLeakedSqlLiteObjects().penaltyLog()						.penaltyDeath().build());	}	public static void IgnoreStrictMode() {		android.os.StrictMode				.setThreadPolicy(new android.os.StrictMode.ThreadPolicy.Builder()						.detectAll().penaltyLog().build());	}	public static void overridePendingTransition(Activity activity) {		activity.overridePendingTransition(0, 0);	}	public static File getExternalFilesDir(Activity activity, String type) {		return activity.getExternalFilesDir(type);	}	public static boolean traceTotalSpace(String tag, File dir) {		boolean deleteF = true;		try {			long df = dir.getTotalSpace();			if (df > 0 && df / (1024.0 * 1024.0) < 3.0) {				LogUtil.trace(tag, "(size,sep):" + df / (1024 * 1024) + "," + 3);				deleteF = false;			}		}		catch (Exception e) {		}		return deleteF;	}	public static void apply(Editor e) {		e.apply();	}}
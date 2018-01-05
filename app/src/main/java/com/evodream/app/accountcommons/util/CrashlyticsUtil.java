package com.evodream.app.accountcommons.util;

import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * @author lukaskris
 * @since 2017/12/28
 */


public class CrashlyticsUtil {
	private static boolean isActive = false;

	public static void init(Context context) {
		Fabric.with(context, new Crashlytics());
		isActive = true;
	}

	public static void setUserIdentifier(String userIdentifier, Context context){
		if (!isActive && !AndroidUtil.isDebuggable(context)) init(context.getApplicationContext());
		Crashlytics.setUserIdentifier(userIdentifier);
	}

	static void logException(Throwable e) {
		if (isActive) {
			try {
				Crashlytics.logException(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}

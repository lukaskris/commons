package com.evodream.app.accountcommons.util;

import com.evodream.app.accountcommons.BuildConfig;

/**
 * @author lukaskris
 * @since 2017/12/28
 */


public class ExceptionUtil {
	public static void handleException(Throwable e) {
		if (BuildConfig.DEBUG) {
			e.printStackTrace();
		} else {
			CrashlyticsUtil.logException(e);
		}
	}
}

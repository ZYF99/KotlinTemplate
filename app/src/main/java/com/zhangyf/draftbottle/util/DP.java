package com.zhangyf.draftbottle.util;

import android.graphics.Rect;
import android.util.Log;


public final class DP
{

	private final static boolean IS_OUTPUT_LOG = false;

	public static <T> void p(Class<T> c, String msg) {
		p(c, msg, null);
	}

	public static <T> void p(Class<T> c, String msg, Exception e)
	{
		if(!IS_OUTPUT_LOG) return;

		Log.d(c.getSimpleName(), msg, e);
	}

	public static <T> void p(Class<T> c, String msg, Rect r, Exception e)
	{
		p(c, String.format("%s (left=%d, top=%d, right=%d, bottom=%d)", msg, r.left, r.top, r.right, r.bottom), e);
	}

	public static <T> void pt(Class<T> c, String msg, Exception e)
	{
		p(c, String.format("%s (%d)", msg, System.currentTimeMillis()), e);
	}



	private DP() {}
}

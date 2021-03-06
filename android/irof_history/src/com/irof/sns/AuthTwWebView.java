package com.irof.sns;

import twitter4j.Twitter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class AuthTwWebView extends WebView {

	private static final String	TAG	= "AuthTwWebView";

	private AuthCallback		mCallback;
	private Twitter				mTwitter;

	public AuthTwWebView(Context context) {
		super(context);
		init(context);
	}

	public AuthTwWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public AuthTwWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void init(Context context) {
		WebSettings settings = getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setBuiltInZoomControls(true);
		//addJavascriptInterface(context, "activity");
	}

	public Twitter getTwitter() {
		return mTwitter;
	}

	public void setTwitter(Twitter twitter) {
		mTwitter = twitter;
	}

	private AuthTwTask	mTask	= null;

	public void start(AuthCallback callback, String url) {
		mCallback = callback;
		if (mTask != null) {
			mTask.cancel(true);
			mTask = null;
		}
		mTask = new AuthTwTask();
		mTask.execute(this, url);
	}

	public void end() {
		mCallback.onSuccess(mTwitter);
	}

	interface AuthCallback {
		void onSuccess(Twitter twitter);
	}

}

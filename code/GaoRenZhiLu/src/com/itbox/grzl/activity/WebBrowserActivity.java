package com.itbox.grzl.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.zhaoliewang.grzl.R;

public class WebBrowserActivity extends BaseActivity {
	private ProgressBar pb_load;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_browser);
		setTitle("正在加载...");
		showLeftBackButton();
		pb_load = (ProgressBar) findViewById(R.id.pb_load);
		WebView webView = (WebView) findViewById(R.id.webView);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				pb_load.setVisibility(View.GONE);
				super.onPageFinished(view, url);
			}

		});
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				setTitle(title);
			}
		});
		webView.getSettings().setJavaScriptEnabled(true);
		String url = getIntent().getStringExtra("url");
		if (url == null) {
			webView.loadData(getIntent().getStringExtra("html"), "text/html", "utf-8");
		} else {
			webView.loadUrl(url);
		}
	}
}

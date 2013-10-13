package com.pubci.simple_traveller;

/* Simple Traveller
 * @author Pubudu Gunatilaka
 * @version 1.0
 *   */

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// TODO Auto-generated method stub
		
		view.loadUrl(url);
		
		return true;
	}

}

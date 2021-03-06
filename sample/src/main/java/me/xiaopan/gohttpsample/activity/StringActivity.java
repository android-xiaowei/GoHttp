/*
 * Copyright 2013 Peng fei Pan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.xiaopan.gohttpsample.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

import org.apache.http.HttpResponse;

import me.xiaopan.gohttp.CacheConfig;
import me.xiaopan.gohttp.GoHttp;
import me.xiaopan.gohttp.HttpRequest;
import me.xiaopan.gohttp.HttpRequestFuture;
import me.xiaopan.gohttp.StringHttpResponseHandler;
import me.xiaopan.gohttp.header.ContentType;
import me.xiaopan.gohttpsample.MyActivity;
import me.xiaopan.gohttpsample.R;
import me.xiaopan.gohttpsample.net.Failure;
import me.xiaopan.gohttpsample.util.WebViewManager;

/**
 * 字符串
 */
public class StringActivity extends MyActivity {
	private WebViewManager webViewManager;
    private HttpRequestFuture httpRequestFuture;
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		webViewManager = new WebViewManager((WebView) findViewById(R.id.web1));
		load();
	}
	
	private void load(){
        GoHttp goHttp = GoHttp.with(getBaseContext());
        httpRequestFuture = goHttp.newRequest("http://www.miui.com/forum.php", new StringHttpResponseHandler(), new HttpRequest.Listener<String>() {
            @Override
            public void onStarted(HttpRequest httpRequest) {
                getHintView().loading("MIUI首页");
            }

            @Override
            public void onCompleted(HttpRequest httpRequest, HttpResponse httpResponse, String responseContent, boolean isCache, boolean isContinueCallback) {
                // 显示HTML源代码
                ContentType contentType = ContentType.fromHttpMessage(httpResponse);
                webViewManager.getWebView().loadDataWithBaseURL(null, responseContent, contentType.getMimeType(), contentType.getCharset("UTF-8"), null);
                getHintView().hidden();
            }

            @Override
            public void onFailed(HttpRequest httpRequest, HttpResponse httpResponse, HttpRequest.Failure failure, boolean isCache, boolean isContinueCallback) {
                getHintView().failure(Failure.buildByException(getBaseContext(), failure.getException()), new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        load();
                    }
                });
            }

            @Override
            public void onCanceled(HttpRequest httpRequest) {

            }
        }).progressListener(new HttpRequest.ProgressListener() {
            @Override
            public void onUpdateProgress(HttpRequest httpRequest, long totalLength, long completedLength) {
                getHintView().setProgress((int) totalLength, (int) completedLength);
            }
        }).cacheConfig(new CacheConfig(20 * 1000)).go();
	}

	@Override
	public void onBackPressed() {
		if(webViewManager.getWebView().canGoBack()){
			webViewManager.getWebView().goBack();
		}else{
			super.onBackPressed();
		}
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(httpRequestFuture != null && !httpRequestFuture.isFinished()){
            httpRequestFuture.cancel(true);
        }
    }
}
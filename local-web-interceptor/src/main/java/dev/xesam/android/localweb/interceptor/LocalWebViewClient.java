package dev.xesam.android.localweb.interceptor;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * a helper client
 * Created by xesamguo@gmail.com on 16-4-19.
 */
public class LocalWebViewClient extends WebViewClient {

    private LocalWebInterceptor mLocalWebInterceptor;

    public LocalWebViewClient(@NonNull LocalWebInterceptor localWebInterceptor) {
        mLocalWebInterceptor = localWebInterceptor;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        WebResourceResponse defaultResource = super.shouldInterceptRequest(view, url);
        //shouldInterceptRequest(WebView view, WebResourceRequest request) will delegate to this method,so,check!
        return mLocalWebInterceptor.shouldInterceptRequest(view, url, defaultResource);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        WebResourceResponse defaultResource = super.shouldInterceptRequest(view, request);
        return mLocalWebInterceptor.shouldInterceptRequest(view, request, defaultResource);
    }
}

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
public class LocalResourceWebViewClient extends WebViewClient {

    private LocalResourceInterceptor mLocalResourceInterceptor;

    public LocalResourceWebViewClient(@NonNull LocalResourceInterceptor localResourceInterceptor) {
        mLocalResourceInterceptor = localResourceInterceptor;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        WebResourceResponse defaultResource = super.shouldInterceptRequest(view, url);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return defaultResource;
        } else {
            return mLocalResourceInterceptor.shouldInterceptRequest(view.getContext(), url, defaultResource);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        WebResourceResponse defaultResource = super.shouldInterceptRequest(view, request);
        return mLocalResourceInterceptor.shouldInterceptRequest(view.getContext(), request, defaultResource);
    }
}

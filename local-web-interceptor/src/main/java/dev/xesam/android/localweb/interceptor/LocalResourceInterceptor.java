package dev.xesam.android.localweb.interceptor;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xesamguo@gmail.com on 10/16/15.
 */
public class LocalResourceInterceptor {

    public static boolean DEBUG = false;

    public List<InterceptRule> rules = new LinkedList<>();

    public void addRule(InterceptRule rule) {
        rules.add(rule);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected WebResourceResponse shouldInterceptRequest(WebView webview, Uri uri, WebResourceResponse defaultResource) {
        if (DEBUG) {
            Log.d("intercept", uri.toString());
        }
        if (rules == null || rules.size() == 0) {
            return defaultResource;
        }
        for (InterceptRule rule : rules) {
            WebResourceResponse webResourceResponse = rule.shouldInterceptRequest(webview.getContext(), uri);
            if (webResourceResponse != null) {
                return webResourceResponse;
            }
        }
        return defaultResource;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public WebResourceResponse shouldInterceptRequest(WebView webview, String url, WebResourceResponse defaultResource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return defaultResource;
        } else {
            return shouldInterceptRequest(webview, Uri.parse(url), defaultResource);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WebResourceResponse shouldInterceptRequest(WebView webview, WebResourceRequest request, WebResourceResponse defaultResource) {
        return shouldInterceptRequest(webview, request.getUrl(), defaultResource);
    }
}

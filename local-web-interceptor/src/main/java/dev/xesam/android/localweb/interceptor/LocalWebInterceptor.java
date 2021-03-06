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
public class LocalWebInterceptor {

    public static boolean DEBUG = false;

    public List<LocalWebInterceptRule> mRules = new LinkedList<>();

    public LocalWebInterceptor() {
    }

    public LocalWebInterceptor(LocalWebInterceptRule rule) {
        addRule(rule);
    }

    public LocalWebInterceptor(List<LocalWebInterceptRule> rules) {
        addRules(rules);
    }

    public void addRule(LocalWebInterceptRule rule) {
        mRules.add(rule);
    }

    public void addRules(List<LocalWebInterceptRule> rules) {
        mRules.addAll(rules);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected WebResourceResponse shouldInterceptRequest(WebView webview, LocalWebInterceptRequest localWebInterceptRequest, WebResourceResponse defaultResource) {
        if (DEBUG) {
            Log.d("intercept", localWebInterceptRequest.toString());
        }
        if (mRules == null || mRules.size() == 0) {
            return defaultResource;
        }
        for (LocalWebInterceptRule rule : mRules) {
            WebResourceResponse webResourceResponse = rule.shouldInterceptRequest(webview.getContext(), localWebInterceptRequest);
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
            LocalWebInterceptRequest localWebInterceptRequest = new LocalWebInterceptRequest(Uri.parse(url));
            return shouldInterceptRequest(webview, localWebInterceptRequest, defaultResource);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WebResourceResponse shouldInterceptRequest(WebView webview, WebResourceRequest request, WebResourceResponse defaultResource) {
        LocalWebInterceptRequest localWebInterceptRequest = new LocalWebInterceptRequest(request);
        return shouldInterceptRequest(webview, localWebInterceptRequest, defaultResource);
    }
}

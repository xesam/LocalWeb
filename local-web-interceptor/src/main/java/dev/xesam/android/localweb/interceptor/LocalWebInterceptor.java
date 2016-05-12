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
    protected WebResourceResponse shouldInterceptRequest(WebView webview, LocalWebRequest localWebRequest, WebResourceResponse defaultResource) {
        if (DEBUG) {
            Log.d("intercept", localWebRequest.toString());
        }
        if (mRules == null || mRules.size() == 0) {
            return defaultResource;
        }
        for (LocalWebInterceptRule rule : mRules) {
            WebResourceResponse webResourceResponse = rule.shouldInterceptRequest(webview.getContext(), localWebRequest);
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
            LocalWebRequest localWebRequest = new LocalWebRequest(Uri.parse(url));
            return shouldInterceptRequest(webview, localWebRequest, defaultResource);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WebResourceResponse shouldInterceptRequest(WebView webview, WebResourceRequest request, WebResourceResponse defaultResource) {
        LocalWebRequest localWebRequest = new LocalWebRequest(request);
        return shouldInterceptRequest(webview, localWebRequest, defaultResource);
    }
}

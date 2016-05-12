package dev.xesam.android.localweb.interceptor;

import android.content.Context;
import android.webkit.WebResourceResponse;

/**
 * the rule to intercept resource
 * Created by xesamguo@gmail.com on 16-4-18.
 */
public interface LocalWebInterceptRule {
    WebResourceResponse shouldInterceptRequest(Context context, LocalWebRequest request);
}

package dev.xesam.android.localweb.interceptor;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * a helper class for intercept assets file
 * Created by xesamguo@gmail.com on 16-5-11.
 */
public abstract class AssetInterceptRule implements LocalWebInterceptRule {

    private LocalWebFilter mLocalWebFilter;

    public AssetInterceptRule(LocalWebFilter localWebFilter) {
        this.mLocalWebFilter = localWebFilter;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public WebResourceResponse shouldInterceptRequest(Context context, LocalWebRequest request) {

        if (mLocalWebFilter != null && mLocalWebFilter.filter(request)) {
            return null;
        }

        String path = getAssetPath(context, request);
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        try {
            InputStream inputStream = context.getAssets().open(path);
            if (LocalWebInterceptor.DEBUG) {
                Log.d("intercept hit", request.toString() + " --> " + path);
            }
            return new WebResourceResponse(null, Charset.defaultCharset().name(), inputStream);
        } catch (IOException e) {
            if (LocalWebInterceptor.DEBUG) {
                Log.d("intercept not found", request.toString());
            }
        }
        return null;
    }

    protected abstract String getAssetPath(Context context, LocalWebRequest request);
}

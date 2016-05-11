package dev.xesam.android.localweb.interceptor;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * a helper class for intercept disk file
 * Created by xesamguo@gmail.com on 16-5-11.
 */
public abstract class FileInterceptRule implements LocalWebInterceptRule {

    private LocalWebFilter mLocalWebFilter;

    public FileInterceptRule(LocalWebFilter localWebFilter) {
        this.mLocalWebFilter = localWebFilter;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public WebResourceResponse shouldInterceptRequest(Context context, Uri uri) {

        if (mLocalWebFilter != null && mLocalWebFilter.filter(uri)) {
            return null;
        }

        String absPath = getFilePath(context, uri);
        if (TextUtils.isEmpty(absPath)) {
            return null;
        }
        try {
            InputStream inputStream = new FileInputStream(new File(absPath));
            if (LocalWebInterceptor.DEBUG) {
                Log.d("intercept hit", uri.toString() + " --> " + absPath);
            }
            return new WebResourceResponse(null, Charset.defaultCharset().name(), inputStream);
        } catch (IOException e) {
            if (LocalWebInterceptor.DEBUG) {
                Log.d("intercept not found", uri.toString());
            }
        }
        return null;
    }

    protected abstract String getFilePath(Context context, Uri uri);
}

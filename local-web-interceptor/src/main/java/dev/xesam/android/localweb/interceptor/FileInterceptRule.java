package dev.xesam.android.localweb.interceptor;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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

    public FileInterceptRule() {
        this(null);
    }

    public FileInterceptRule(LocalWebFilter localWebFilter) {
        this.mLocalWebFilter = localWebFilter;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public WebResourceResponse shouldInterceptRequest(Context context, LocalWebInterceptRequest request) {

        if (mLocalWebFilter != null && !mLocalWebFilter.intercept(request)) {
            return null;
        }

        File file = getFile(context, request);
        if (file == null) {
            return null;
        }
        try {
            InputStream inputStream = new FileInputStream(file);
            if (LocalWebInterceptor.DEBUG) {
                Log.d("intercept hit", request.toString() + " --> " + file.getAbsolutePath());
            }
            return new WebResourceResponse(null, Charset.defaultCharset().name(), inputStream);
        } catch (IOException e) {
            if (LocalWebInterceptor.DEBUG) {
                Log.d("intercept not found", request.toString());
            }
        }
        return null;
    }

    protected abstract File getFile(Context context, LocalWebInterceptRequest request);
}

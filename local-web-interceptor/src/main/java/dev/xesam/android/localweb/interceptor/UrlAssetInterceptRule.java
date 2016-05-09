package dev.xesam.android.localweb.interceptor;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xesamguo@gmail.com on 16-4-18.
 */
public class UrlAssetInterceptRule implements InterceptRule {

    public static final String SEP = "/";

    private static final Pattern pattern = Pattern.compile("(?:\\?|&)v=([^=]+)");

    @Nullable
    public static String getV(Uri uri) {
        String query = uri.getQuery();
        if (TextUtils.isEmpty(query)) {
            return null;
        }
        if (!query.startsWith("?")) {
            query = "?" + query;
        }
        Matcher matcher = pattern.matcher(query);
        if (matcher.find()) {
            if (matcher.groupCount() == 1) {
                return matcher.group(1);
            }
        }
        return null;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public WebResourceResponse shouldInterceptRequest(Context context, Uri uri) {
        String host = uri.getHost();
        if (!TextUtils.isEmpty(host)) {
            try {
                String path = uri.getPath();
                String v = getV(uri);
                if (TextUtils.isEmpty(v)) {
                    if (path.startsWith(SEP)) {
                        path = path.substring(1);
                    }
                } else {
                    if (path.startsWith(SEP)) {
                        path = v + path;
                    } else {
                        path = v + File.separator + path;
                    }
                }
                InputStream inputStream = context.getAssets().open(path);
                if (LocalResourceInterceptor.DEBUG) {
                    Log.d("intercept hit", uri.toString() + " --> " + path);
                }
                return new WebResourceResponse(null, Charset.defaultCharset().name(), inputStream);
            } catch (IOException e) {
                if (LocalResourceInterceptor.DEBUG) {
                    Log.d("intercept not found", uri.toString());
                }
            }
        }
        return null;
    }
}

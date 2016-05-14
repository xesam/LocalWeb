package dev.xesam.android.localweb.interceptor;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebInterceptRequest {

    private static final Pattern PATTERN_TAG = Pattern.compile("(?:\\?|&)tag=([^=]+)");

    @Nullable
    public static String getTag(Uri uri) {
        String query = uri.getQuery();
        if (TextUtils.isEmpty(query)) {
            return null;
        }
        if (!query.startsWith("?")) {
            query = "?" + query;
        }
        Matcher matcher = PATTERN_TAG.matcher(query);
        if (matcher.find()) {
            if (matcher.groupCount() == 1) {
                return matcher.group(1);
            }
        }
        return null;
    }

    private String mTag;
    private Uri mUri;

    public LocalWebInterceptRequest(Uri uri) {
        this(getTag(uri), uri);
    }

    public LocalWebInterceptRequest(String version, Uri uri) {
        this.mTag = version;
        this.mUri = uri;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LocalWebInterceptRequest(WebResourceRequest request) {
        this(request.getUrl());
    }

    public String getTag() {
        return mTag;
    }

    public Uri getUrl() {
        return mUri;
    }

    @Override
    public String toString() {
        return "LocalWebInterceptRequest{" +
                "mTag='" + mTag + '\'' +
                ", mUri=" + mUri +
                '}';
    }
}

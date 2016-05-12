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
public class LocalWebRequest {

    private static final Pattern VERSION = Pattern.compile("(?:\\?|&)v=([^=]+)");

    @Nullable
    public static String getVersion(Uri uri) {
        String query = uri.getQuery();
        if (TextUtils.isEmpty(query)) {
            return null;
        }
        if (!query.startsWith("?")) {
            query = "?" + query;
        }
        Matcher matcher = VERSION.matcher(query);
        if (matcher.find()) {
            if (matcher.groupCount() == 1) {
                return matcher.group(1);
            }
        }
        return null;
    }

    private String mVersion;
    private Uri mUri;

    public LocalWebRequest(Uri uri) {
        this(getVersion(uri), uri);
    }

    public LocalWebRequest(String version, Uri uri) {
        this.mVersion = version;
        this.mUri = uri;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LocalWebRequest(WebResourceRequest request) {
        this(request.getUrl());
    }

    public String getVersion() {
        return mVersion;
    }

    public Uri getUrl() {
        return mUri;
    }

    @Override
    public String toString() {
        return "LocalWebRequest{" +
                "mVersion='" + mVersion + '\'' +
                ", mUri=" + mUri +
                '}';
    }
}

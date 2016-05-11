package dev.xesam.android.localweb.interceptor;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xesamguo@gmail.com on 16-4-18.
 */
public class UrlAssetInterceptRule extends AssetInterceptRule {

    public static final String SEP = "/";

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

    @Override
    protected String getAssetPath(Context context, Uri uri) {
        String host = uri.getHost();
        if (!TextUtils.isEmpty(host)) {
            String path = uri.getPath();
            String v = getVersion(uri);
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
            return path;
        }
        return null;
    }
}

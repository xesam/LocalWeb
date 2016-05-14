package dev.xesam.android.localweb.interceptor;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by xesamguo@gmail.com on 16-4-18.
 */
public class UrlAssetInterceptRule extends AssetInterceptRule {

    public static final String SEP = "/";

    public UrlAssetInterceptRule() {
        super(null);
    }

    public UrlAssetInterceptRule(LocalWebFilter localWebFilter) {
        super(localWebFilter);
    }

    @Override
    protected String getAssetPath(Context context, LocalWebInterceptRequest request) {
        Uri uri = request.getUrl();
        String path = uri.getPath();
        String version = request.getTag();
        if (TextUtils.isEmpty(version)) {
            if (path.startsWith(SEP)) {
                path = path.substring(1);
            }
        } else {
            if (path.startsWith(SEP)) {
                path = version + path;
            } else {
                path = version + SEP + path;
            }
        }
        return path;
    }
}

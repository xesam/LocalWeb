package dev.xesam.android.localweb;

import android.content.Context;
import android.net.Uri;

import java.io.File;

import dev.xesam.android.localweb.interceptor.FileInterceptRule;
import dev.xesam.android.localweb.interceptor.LocalWebFilter;
import dev.xesam.android.localweb.interceptor.LocalWebInterceptRequest;

/**
 * Created by xesamguo@gmail.com on 16-5-11.
 */
public class DynamicInterceptRule extends FileInterceptRule {

    private LocalWebManager mLocalWebManager;

    public DynamicInterceptRule(LocalWebManager localWebManager) {
        this(null, localWebManager);
    }

    public DynamicInterceptRule(LocalWebFilter localWebFilter, LocalWebManager localWebManager) {
        super(localWebFilter);
        this.mLocalWebManager = localWebManager;
    }

    @Override
    protected File getFile(Context context, LocalWebInterceptRequest request) {
        Uri uri = request.getUrl();
        String path = uri.getPath();
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return new File(mLocalWebManager.getCacheDir(), request.getTag() + File.separator + path);
    }
}

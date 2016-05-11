package dev.xesam.android.localweb;

import android.content.Context;
import android.net.Uri;

import dev.xesam.android.localweb.interceptor.FileInterceptRule;

/**
 * Created by xesamguo@gmail.com on 16-5-11.
 */
public class DynamicInterceptRule extends FileInterceptRule {

    
    @Override
    protected String getFilePath(Context context, Uri uri) {
        return null;
    }
}

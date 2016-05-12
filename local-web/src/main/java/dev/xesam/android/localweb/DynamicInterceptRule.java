package dev.xesam.android.localweb;

import android.content.Context;

import dev.xesam.android.localweb.interceptor.FileInterceptRule;
import dev.xesam.android.localweb.interceptor.LocalWebInterceptRequest;

/**
 * Created by xesamguo@gmail.com on 16-5-11.
 */
public class DynamicInterceptRule extends FileInterceptRule {

    @Override
    protected String getFilePath(Context context, LocalWebInterceptRequest request) {
        return null;
    }
}

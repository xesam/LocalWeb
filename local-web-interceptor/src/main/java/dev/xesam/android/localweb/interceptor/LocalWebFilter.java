package dev.xesam.android.localweb.interceptor;

import android.net.Uri;

/**
 * Created by xesamguo@gmail.com on 16-5-11.
 */
public interface LocalWebFilter {
    boolean filter(Uri uri);
}

package dev.xesam.android.localweb.interceptor;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xesamguo@gmail.com on 16-4-18.
 */
public class MapAssetInterceptRule implements LocalWebInterceptRule {
    private Map<String, String> mapper = new HashMap<>();

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public WebResourceResponse shouldInterceptRequest(Context context, Uri uri) {
        String key = uri.toString();
        if (mapper.containsKey(key)) {
            String path = mapper.get(key);
            try {
                InputStream inputStream = context.getAssets().open(path);
                if (LocalWebInterceptor.DEBUG) {
                    Log.d("intercept hit", uri.toString() + " --> " + path);
                }
                return new WebResourceResponse(null, Charset.defaultCharset().name(), inputStream);
            } catch (IOException e) {
                if (LocalWebInterceptor.DEBUG) {
                    Log.d("intercept not found", uri.toString());
                }
            }
        }
        return null;
    }

    public void put(String key, String value) {
        mapper.put(key, value);
    }
}

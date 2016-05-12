package dev.xesam.android.localweb.interceptor;

import android.content.Context;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xesamguo@gmail.com on 16-4-18.
 */
public class MapAssetInterceptRule extends AssetInterceptRule {
    private Map<String, String> mapper = new HashMap<>();

    public MapAssetInterceptRule() {
        super(null);
    }

    public MapAssetInterceptRule(LocalWebFilter localWebFilter) {
        super(localWebFilter);
    }

    @Override
    protected String getAssetPath(Context context, LocalWebInterceptRequest request) {
        Uri uri = request.getUrl();
        String key = uri.toString();
        if (mapper.containsKey(key)) {
            return mapper.get(key);
        }
        return null;
    }

    public void put(String key, String value) {
        mapper.put(key, value);
    }
}

package dev.xesam.android.localweb;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebCache {
    public void scan(Context context) {
        File dir = context.getCacheDir();
        String[] files = dir.list();
        for (String file : files) {
            Log.e("LocalWebCache", String.valueOf(file));
        }
    }
}

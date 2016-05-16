package dev.xesam.android.localweb;

import android.content.Context;
import android.content.Intent;

import java.io.File;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebManager {

    public static boolean DEBUG = false;
    private static Context sContext;
    private static LocalWebManager webManager;
    private LocalWebCache localWebCache;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static LocalWebManager getInstance() {
        if (webManager == null) {
            webManager = new LocalWebManager(sContext);
        }
        return webManager;
    }

    private Context mContext;

    private LocalWebManager(Context context) {
        this.mContext = context;
        localWebCache = new LocalWebCache();
    }

    private LocalWebParam createDefaultRequest() {
        return null;
    }

    public void update() {
        update(createDefaultRequest());
    }

    public void update(LocalWebParam request) {
        Intent intent = new Intent(LocalWebHelper.INTENT_ACTION_START_PROCESS);
        intent.setPackage(mContext.getPackageName());
        LocalWebHelper.putRequest(intent, request);
        mContext.startService(intent);
    }

    public void scan() {
        localWebCache.scan(mContext);
    }

    public File getCacheDir() {
        return localWebCache.getCacheDir(mContext);
    }
}

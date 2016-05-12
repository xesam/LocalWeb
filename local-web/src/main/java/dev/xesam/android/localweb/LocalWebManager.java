package dev.xesam.android.localweb;

import android.content.Context;
import android.content.Intent;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebManager {

    public static boolean DEBUG = false;
    private static Context sContext;
    private static LocalWebManager webManager;

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
    }

    private LocalWebRequest createDefaultRequest() {
        return null;
    }

    public void update() {
        update(createDefaultRequest());
    }

    public void update(LocalWebRequest request) {
        Intent intent = new Intent(mContext, LocalWebService.class);
        LocalWebHelper.putRequest(intent, request);
        mContext.startService(intent);
    }
}

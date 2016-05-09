package dev.xesam.android.localweb;

import android.content.Context;
import android.content.Intent;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebManager implements ILocalWebManager {

    public static boolean DEBUG = false;
    private static Context sContext;
    private static ILocalWebManager webManager;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static ILocalWebManager getInstance() {
        if (webManager == null) {
            webManager = new LocalWebManager(sContext);
        }
        return webManager;
    }

    private Context mContext;

    private LocalWebManager(Context context) {
        this.mContext = context;
    }

    private LocalWebRequest createRequest() {
        return null;
    }

    @Override
    public void update() {
        Intent intent = new Intent(mContext, LocalWebService.class);
        LocalWebHelper.putRequest(intent, createRequest());
        mContext.startService(intent);
    }
}

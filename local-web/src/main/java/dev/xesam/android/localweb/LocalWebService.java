package dev.xesam.android.localweb;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebService extends IntentService {

    public static final String TAG = "LocalWebService";
    private LocalWebCache mLocalWebCache;

    public LocalWebService() {
        super(TAG);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LocalWebManager.DEBUG) {
            Log.d(TAG, "onCreate");
        }
        mLocalWebCache = new LocalWebCache();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LocalWebParam request = LocalWebHelper.getRequest(intent);
        onHandleRequest(request);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (LocalWebManager.DEBUG) {
            Log.d(TAG, "onDestroy");
        }
    }

    protected void onHandleRequest(LocalWebParam request) {
        if (LocalWebManager.DEBUG) {
            Log.d(TAG, "handle intent:" + (request == null ? "null" : (request.getTag() + ":" + request.getUrl())));
        }

        if (request == null) {
            return;
        }

        mLocalWebCache.sync(this, request);
        onUpdated(request, new Bundle());
    }

    protected void checkUpdate() {

    }

    protected void onUpdated(LocalWebParam request, Bundle responseReply) {
        LocalWebHelper.broadcastUpdated(this, request, responseReply);
    }
}

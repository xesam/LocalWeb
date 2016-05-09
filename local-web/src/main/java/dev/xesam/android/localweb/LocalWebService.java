package dev.xesam.android.localweb;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LocalWebService(String name) {
        super(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    protected void onUpdated() {

    }
}

package dev.xesam.android.localweb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

/**
 * Created by xesamguo@gmail.com on 16-5-11.
 */
public class LocalWebReceiver extends BroadcastReceiver {

    public static final String ACTION_LOCAL_WEB_UPDATED = "local_web.action.updated";

    private IntentFilter intentFilter;

    public LocalWebReceiver() {
        intentFilter = new IntentFilter(ACTION_LOCAL_WEB_UPDATED);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        switch (action) {
            case ACTION_LOCAL_WEB_UPDATED:
                onReceiveUpdated(context, intent, LocalWebHelper.getRequest(intent), LocalWebHelper.getResponseReply(intent));
                break;
        }
    }

    protected void onReceiveUpdated(Context context, Intent intent, LocalWebParam request, Bundle responseReply) {

    }

    public void register(Context context) {
        context.registerReceiver(this, intentFilter);
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }
}

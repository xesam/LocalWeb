package dev.xesam.android.localweb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebHelper {

    public static final String INTENT_EXTRA_REQUEST = "localweb.extra.request";
    public static final String INTENT_EXTRA_RESPONSE_REPLY = "localweb.extra.response_reply";

    public static void putRequest(Intent data, LocalWebRequest localWebRequest) {
        data.putExtra(INTENT_EXTRA_REQUEST, localWebRequest);
    }

    public static LocalWebRequest getRequest(Intent data) {
        return data.getParcelableExtra(INTENT_EXTRA_REQUEST);
    }

    public static void setRequest(Intent data, LocalWebRequest request) {
        data.putExtra(INTENT_EXTRA_REQUEST, request);
    }

    public static Bundle getResponseReply(Intent data) {
        return data.getBundleExtra(INTENT_EXTRA_REQUEST);
    }

    public static void setResponseReply(Intent data, Bundle responseReply) {
        data.putExtra(INTENT_EXTRA_RESPONSE_REPLY, responseReply);
    }

    public static void broadcastUpdated(Context context, LocalWebRequest request, Bundle responseReply) {
        Intent intent = new Intent(LocalWebReceiver.ACTION_LOCAL_WEB_UPDATED);
        setRequest(intent, request);
        setResponseReply(intent, responseReply);
        context.sendBroadcast(intent);
    }

}

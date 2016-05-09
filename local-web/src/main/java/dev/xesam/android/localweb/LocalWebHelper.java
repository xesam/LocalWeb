package dev.xesam.android.localweb;

import android.content.Intent;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebHelper {

    public static final String INTENT_EXTRA_REQUEST = "localweb.extra.request";

    public static void putRequest(Intent data, LocalWebRequest localWebRequest) {
        data.putExtra(INTENT_EXTRA_REQUEST, localWebRequest);
    }

    public static LocalWebRequest getRequest(Intent data) {
        return data.getParcelableExtra(INTENT_EXTRA_REQUEST);
    }

}

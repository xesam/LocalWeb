package dev.xesam.android.localweb;

/**
 * Created by xesamguo@gmail.com on 16-5-16.
 */
public class LocalWebResp {
    String tag;
    String url;

    public LocalWebResp(String tag, String url) {
        this.tag = tag;
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public String getUrl() {
        return url;
    }
}

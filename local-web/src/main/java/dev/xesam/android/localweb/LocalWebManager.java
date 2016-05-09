package dev.xesam.android.localweb;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebManager implements ILocalWebManager {

    private static ILocalWebManager webManager;

    public static ILocalWebManager getInstance() {
        if (webManager == null) {
            webManager = new LocalWebManager();
        }
        return webManager;
    }

    private LocalWebManager() {

    }

    private LocalWebRequest createRequest() {
        return null;
    }

    @Override
    public void update() {

    }
}

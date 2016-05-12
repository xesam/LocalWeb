package dev.xesam.android.localweb.interceptor;

/**
 * Created by xesamguo@gmail.com on 16-5-11.
 */
public interface LocalWebFilter {
    /**
     * true : intercept
     * false : don't intercept
     */
    boolean intercept(LocalWebInterceptRequest request);
}

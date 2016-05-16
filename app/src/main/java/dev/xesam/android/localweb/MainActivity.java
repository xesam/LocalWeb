package dev.xesam.android.localweb;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.xesam.android.localweb.app.R;
import dev.xesam.android.localweb.interceptor.LocalWebInterceptor;
import dev.xesam.android.localweb.interceptor.LocalWebViewClient;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.web)
    public WebView web;

    LocalWebReceiver localWebReceiver = new LocalWebReceiver() {
        @Override
        protected void onReceiveUpdated(Context context, Intent intent, LocalWebParam request, Bundle responseReply) {
            Log.d("onReceiveUpdated", "get");
            new LocalWebCache(context).scan();
        }
    };

    MockServer mockServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mockServer = new MockServer();
        mockServer.startServer();
        LocalWebManager.init(this);
        LocalWebManager.DEBUG = true;
        localWebReceiver.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mockServer.stopServer();
        localWebReceiver.unregister(this);
    }

    @OnClick(R.id.update)
    public void update() {
        LocalWebParam request = new LocalWebParam();
        request.setUrl(mockServer.getCheckUpdateUrl());
        LocalWebManager.getInstance().update(request);
    }

    @OnClick(R.id.load)
    public void load() {
        LocalWebInterceptor localWebInterceptor = new LocalWebInterceptor(new DynamicInterceptRule(new LocalWebCache(this)));
        web.setWebViewClient(new LocalWebViewClient(localWebInterceptor));
        web.loadUrl("http://xesam.github.io/html/3_0.html?tag=v2");
    }

    public void customIntercept() {
        final LocalWebInterceptor localWebInterceptor = new LocalWebInterceptor();
        web.setWebViewClient(new WebViewClient() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return localWebInterceptor.shouldInterceptRequest(view, url, super.shouldInterceptRequest(view, url));
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return localWebInterceptor.shouldInterceptRequest(view, request, super.shouldInterceptRequest(view, request));
            }
        });
    }
}

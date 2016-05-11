package dev.xesam.android.localweb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.xesam.android.localweb.app.R;
import dev.xesam.android.localweb.interceptor.InterceptRule;
import dev.xesam.android.localweb.interceptor.LocalResourceInterceptor;
import dev.xesam.android.localweb.interceptor.LocalResourceWebViewClient;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.web)
    public WebView web;

    LocalWebReceiver localWebReceiver = new LocalWebReceiver() {
        @Override
        protected void onReceiveUpdated(Context context, Intent intent, LocalWebRequest request, Bundle responseReply) {
            Log.d("onReceiveUpdated", "get");
            new LocalWebCache().scan(context);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LocalWebManager.init(this);
        LocalWebManager.DEBUG = true;
        localWebReceiver.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localWebReceiver.unregister(this);
    }

    @OnClick(R.id.update)
    public void update() {
        LocalWebManager.getInstance().update();
    }

    @OnClick(R.id.load)
    public void load() {
        LocalResourceInterceptor localResourceInterceptor = new LocalResourceInterceptor();
        localResourceInterceptor.addRule(new InterceptRule() {
            @Override
            public WebResourceResponse shouldInterceptRequest(Context context, Uri uri) {
                String path = uri.getPath();
                Log.e("should", path);
                if (path.startsWith("/")) {
                    path = path.substring(1);
                }
                try {
                    InputStream inputStream = new FileInputStream(new File(context.getExternalCacheDir(), "v2/" + path));
                    return new WebResourceResponse(null, Charset.defaultCharset().name(), inputStream);
                } catch (IOException e) {
                    if (LocalResourceInterceptor.DEBUG) {
                        Log.d("intercept not found", uri.toString());
                    }
                }
                return null;
            }
        });
        web.setWebViewClient(new LocalResourceWebViewClient(localResourceInterceptor));
        web.loadUrl("http://192.168.1.159/index.html?v=v1");
    }
}

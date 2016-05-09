package dev.xesam.android.localweb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.xesam.android.localweb.app.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.web)
    public WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.update)
    public void update() {

    }

    @OnClick(R.id.load)
    public void load() {
        Log.e("load", "load");
        web.loadUrl("http://192.168.1.232/v2/index.html");
    }
}

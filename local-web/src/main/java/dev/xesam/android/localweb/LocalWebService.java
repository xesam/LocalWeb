package dev.xesam.android.localweb;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebService extends IntentService {

    public static final String TAG = "LocalWebService";

    public LocalWebService() {
        super(TAG);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LocalWebManager.DEBUG) {
            Log.d(TAG, "onCreate");
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LocalWebRequest request = LocalWebHelper.getRequest(intent);
        onHandleRequest(request);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (LocalWebManager.DEBUG) {
            Log.d(TAG, "onDestroy");
        }
    }

    protected void onHandleRequest(LocalWebRequest request) {
        if (LocalWebManager.DEBUG) {
            Log.d(TAG, "handle intent:" + (request == null ? "null" : (request.getVersion() + ":" + request.getUrl())));
        }

        if (request == null) {
            try {
                Thread.sleep(1000);
                onUpdated(request, new Bundle());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                InputStream in = null;
                OutputStream out = null;
                int connectTimeout = 30 * 1000; // 连接超时:30s
                int readTimeout = 20 * 1000 * 1000; // IO超时:1min
                byte[] buffer = new byte[8 * 1024]; // IO缓冲区:8KB

                URL url = new URL(request.getUrl());
                File file = new File(getCacheDir(), System.currentTimeMillis() + ".zip");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
                conn.connect();

                in = conn.getInputStream();
                out = new FileOutputStream(file);

                for (; ; ) {
                    int bytes = in.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    out.write(buffer, 0, bytes);

                }
                onUpdated(request, new Bundle());
                in.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void onUpdated(LocalWebRequest request, Bundle responseReply) {
        LocalWebHelper.broadcastUpdated(this, request, responseReply);
    }
}

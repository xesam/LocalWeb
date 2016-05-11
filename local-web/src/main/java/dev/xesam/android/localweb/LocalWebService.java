package dev.xesam.android.localweb;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
                File file = new File(getExternalCacheDir(), request.getVersion() + ".zip");
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

                ZipInputStream zis = new ZipInputStream(new FileInputStream(new File(getExternalCacheDir(), request.getVersion() + ".zip")));
                BufferedOutputStream bos = null;
                ZipEntry entry = null;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.isDirectory()) {

                    } else {
                        File target = new File(file.getParent(), entry.getName());
                        if (!target.getParentFile().exists()) {
                            // 创建文件父目录
                            target.getParentFile().mkdirs();
                        }
                        // 写入文件
                        bos = new BufferedOutputStream(new FileOutputStream(target));
                        int read;
                        byte[] buffer2 = new byte[1024 * 10];
                        while ((read = zis.read(buffer2, 0, buffer2.length)) != -1) {
                            bos.write(buffer2, 0, read);
                        }
                        bos.flush();
                    }
                }
                zis.closeEntry();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void onUpdated(LocalWebRequest request, Bundle responseReply) {
        LocalWebHelper.broadcastUpdated(this, request, responseReply);
    }
}

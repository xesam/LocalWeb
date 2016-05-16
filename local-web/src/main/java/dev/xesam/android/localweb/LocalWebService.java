package dev.xesam.android.localweb;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebService extends IntentService {

    public static final String TAG = "LocalWebService";
    private LocalWebCache mLocalWebCache;

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
        mLocalWebCache = new LocalWebCache(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LocalWebParam request = LocalWebHelper.getRequest(intent);
        onHandleRequest(request);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (LocalWebManager.DEBUG) {
            Log.d(TAG, "onDestroy");
        }
    }

    protected void onHandleRequest(LocalWebParam param) {
        if (LocalWebManager.DEBUG) {
            Log.d(TAG, "handle intent:" + (param == null ? "null" : param.toString()));
        }

        if (param == null) {
            return;
        }

        LocalWebResp resp = checkUpdate(param);
        if (resp == null) {
            return;
        }

        Bundle data = syncUpdate(resp);
        onUpdated(param, data);
    }

    protected LocalWebResp checkUpdate(LocalWebParam param) {
        try {
            URL url = new URL(param.getUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            conn.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            return new LocalWebResp(jsonObject.getString("tag"), jsonObject.getString("url"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected Bundle syncUpdate(LocalWebResp resp) {
        mLocalWebCache.syncUpdate(this, resp);
        return null;
    }

    protected void onUpdated(LocalWebParam param, Bundle responseReply) {
        LocalWebHelper.broadcastUpdated(this, param, responseReply);
    }
}

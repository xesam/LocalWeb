package dev.xesam.android.localweb;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebCache {

    public static final int CONNECT_TIME_OUT = 30 * 1000;
    public static final int READ_TIME_OUT = 2 * 60 * 1000;

    File getCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    private File getDestFile(Context context, LocalWebResp resp) {
        return new File(getCacheDir(context), resp.getTag() + ".zip");
    }

    public void scan(Context context) {
        File dir = getCacheDir(context);
        String[] files = dir.list();
        for (String file : files) {
            Log.e("LocalWebCache scan", String.valueOf(file));
        }
    }

    public Bundle syncUpdate(Context context, LocalWebResp resp) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            File dest = getDestFile(context, resp);
            URL url = new URL(resp.getUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setReadTimeout(READ_TIME_OUT);
            conn.connect();
            bis = new BufferedInputStream(conn.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] buffer = new byte[8 * 1024];
            int read;
            while ((read = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
            bis.close();
            bos.close();
            process(new File(dest.getAbsolutePath()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void process(File zipFile) {
        BufferedOutputStream bos = null;
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    File target = new File(zipFile.getParent(), entry.getName());
                    if (!target.getParentFile().exists()) {
                        target.getParentFile().mkdirs();
                    }
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
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

package dev.xesam.android.localweb;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

/**
 * Created by xesamguo@gmail.com on 16-5-13.
 */
public class MockServer extends Thread {
    MockWebServer mockWebServer;

    private static final String CHECK_UPDATE = "/check_update";

    final Dispatcher dispatcher = new Dispatcher() {

        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
            final String path = request.getPath();
            if (path.equals(CHECK_UPDATE)) {
                LocalWebResp localWebResp = new LocalWebResp();
                localWebResp.tag = "v1";
                localWebResp.url = "http://192.168.1.159/v1.zip";
                return new MockResponse().setResponseCode(200).setBody(new Gson().toJson(localWebResp));
            }
            return new MockResponse().setResponseCode(404);
        }
    };

    public MockServer() {
        this.mockWebServer = new MockWebServer();
        mockWebServer.setDispatcher(dispatcher);
    }

    @Override
    public void run() {
        try {
            mockWebServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        start();
    }

    public void stopServer() {
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
        interrupt();
    }

    public String getCheckUpdateUrl() {
        return mockWebServer.url(CHECK_UPDATE).toString();
    }
}

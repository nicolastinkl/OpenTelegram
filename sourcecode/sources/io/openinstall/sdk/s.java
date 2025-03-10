package io.openinstall.sdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.NetworkInterface;
import java.util.Collections;

/* loaded from: classes.dex */
public class s {
    private final Context a;
    private String b;
    private String c;

    public s(Context context) {
        this.a = context;
    }

    private boolean a(String str) {
        return TextUtils.isEmpty(str) || str.equals(fw.j) || str.equals(fw.k);
    }

    private boolean b(String str) {
        return TextUtils.isEmpty(str) || str.equals(fw.l);
    }

    public String a() {
        byte[] hardwareAddress;
        WifiInfo connectionInfo;
        String str = this.b;
        if (str != null) {
            return str;
        }
        String str2 = null;
        try {
            WifiManager wifiManager = (WifiManager) this.a.getSystemService(fw.f);
            if (wifiManager != null && (connectionInfo = wifiManager.getConnectionInfo()) != null) {
                str2 = connectionInfo.getMacAddress();
            }
        } catch (Throwable unused) {
        }
        if (!b(str2)) {
            this.b = str2;
            return str2;
        }
        try {
            str2 = new BufferedReader(new FileReader(new File(fw.h))).readLine();
        } catch (Throwable unused2) {
        }
        if (!b(str2)) {
            this.b = str2;
            return str2;
        }
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (fw.g.equalsIgnoreCase(networkInterface.getName()) && (hardwareAddress = networkInterface.getHardwareAddress()) != null) {
                    StringBuilder sb = new StringBuilder();
                    for (byte b : hardwareAddress) {
                        sb.append(String.format("%02X:", Byte.valueOf(b)));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    str2 = sb.toString();
                }
            }
        } catch (Throwable unused3) {
        }
        if (b(str2)) {
            str2 = "";
        }
        this.b = str2;
        return this.b;
    }

    @SuppressLint({"HardwareIds"})
    public String b() {
        String str = this.c;
        if (str != null) {
            return str;
        }
        String str2 = null;
        if (fz.a(this.a)) {
            TelephonyManager telephonyManager = (TelephonyManager) this.a.getSystemService("phone");
            try {
                str2 = Build.VERSION.SDK_INT >= 26 ? telephonyManager.getImei() : telephonyManager.getDeviceId();
            } catch (Throwable unused) {
            }
        }
        if (a(str2)) {
            str2 = "";
        }
        this.c = str2;
        return this.c;
    }
}

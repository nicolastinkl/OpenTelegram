package com.lxj.xpopup.util;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/* loaded from: classes.dex */
public final class FuckRomUtils {
    private static final String[] ROM_HUAWEI = {"huawei"};
    private static final String[] ROM_VIVO = {"vivo"};
    private static final String[] ROM_XIAOMI = {"xiaomi"};
    private static final String[] ROM_OPPO = {"oppo"};
    private static final String[] ROM_LEECO = {"leeco", "letv"};
    private static final String[] ROM_360 = {"360", "qiku"};
    private static final String[] ROM_ZTE = {"zte"};
    private static final String[] ROM_ONEPLUS = {"oneplus"};
    private static final String[] ROM_NUBIA = {"nubia"};
    private static final String[] ROM_COOLPAD = {"coolpad", "yulong"};
    private static final String[] ROM_LG = {"lg", "lge"};
    private static final String[] ROM_GOOGLE = {"google"};
    private static final String[] ROM_SAMSUNG = {"samsung"};
    private static final String[] ROM_MEIZU = {"meizu"};
    private static final String[] ROM_LENOVO = {"lenovo"};
    private static final String[] ROM_SMARTISAN = {"smartisan"};
    private static final String[] ROM_HTC = {"htc"};
    private static final String[] ROM_SONY = {"sony"};
    private static final String[] ROM_GIONEE = {"gionee", "amigo"};
    private static final String[] ROM_MOTOROLA = {"motorola"};
    private static RomInfo bean = null;

    public static boolean isSamsung() {
        return ROM_SAMSUNG[0].equals(getRomInfo().name);
    }

    public static RomInfo getRomInfo() {
        RomInfo romInfo = bean;
        if (romInfo != null) {
            return romInfo;
        }
        bean = new RomInfo();
        String brand = getBrand();
        String manufacturer = getManufacturer();
        String[] strArr = ROM_HUAWEI;
        if (isRightRom(brand, manufacturer, strArr)) {
            bean.name = strArr[0];
            String romVersion = getRomVersion("ro.build.version.emui");
            String[] split = romVersion.split("_");
            if (split.length > 1) {
                bean.version = split[1];
            } else {
                bean.version = romVersion;
            }
            return bean;
        }
        String[] strArr2 = ROM_VIVO;
        if (isRightRom(brand, manufacturer, strArr2)) {
            bean.name = strArr2[0];
            bean.version = getRomVersion("ro.vivo.os.build.display.id");
            return bean;
        }
        String[] strArr3 = ROM_XIAOMI;
        if (isRightRom(brand, manufacturer, strArr3)) {
            bean.name = strArr3[0];
            bean.version = getRomVersion("ro.build.version.incremental");
            return bean;
        }
        String[] strArr4 = ROM_OPPO;
        if (isRightRom(brand, manufacturer, strArr4)) {
            bean.name = strArr4[0];
            bean.version = getRomVersion("ro.build.version.opporom");
            return bean;
        }
        String[] strArr5 = ROM_LEECO;
        if (isRightRom(brand, manufacturer, strArr5)) {
            bean.name = strArr5[0];
            bean.version = getRomVersion("ro.letv.release.version");
            return bean;
        }
        String[] strArr6 = ROM_360;
        if (isRightRom(brand, manufacturer, strArr6)) {
            bean.name = strArr6[0];
            bean.version = getRomVersion("ro.build.uiversion");
            return bean;
        }
        String[] strArr7 = ROM_ZTE;
        if (isRightRom(brand, manufacturer, strArr7)) {
            bean.name = strArr7[0];
            bean.version = getRomVersion("ro.build.MiFavor_version");
            return bean;
        }
        String[] strArr8 = ROM_ONEPLUS;
        if (isRightRom(brand, manufacturer, strArr8)) {
            bean.name = strArr8[0];
            bean.version = getRomVersion("ro.rom.version");
            return bean;
        }
        String[] strArr9 = ROM_NUBIA;
        if (isRightRom(brand, manufacturer, strArr9)) {
            bean.name = strArr9[0];
            bean.version = getRomVersion("ro.build.rom.id");
            return bean;
        }
        String[] strArr10 = ROM_COOLPAD;
        if (isRightRom(brand, manufacturer, strArr10)) {
            bean.name = strArr10[0];
        } else {
            String[] strArr11 = ROM_LG;
            if (isRightRom(brand, manufacturer, strArr11)) {
                bean.name = strArr11[0];
            } else {
                String[] strArr12 = ROM_GOOGLE;
                if (isRightRom(brand, manufacturer, strArr12)) {
                    bean.name = strArr12[0];
                } else {
                    String[] strArr13 = ROM_SAMSUNG;
                    if (isRightRom(brand, manufacturer, strArr13)) {
                        bean.name = strArr13[0];
                    } else {
                        String[] strArr14 = ROM_MEIZU;
                        if (isRightRom(brand, manufacturer, strArr14)) {
                            bean.name = strArr14[0];
                        } else {
                            String[] strArr15 = ROM_LENOVO;
                            if (isRightRom(brand, manufacturer, strArr15)) {
                                bean.name = strArr15[0];
                            } else {
                                String[] strArr16 = ROM_SMARTISAN;
                                if (isRightRom(brand, manufacturer, strArr16)) {
                                    bean.name = strArr16[0];
                                } else {
                                    String[] strArr17 = ROM_HTC;
                                    if (isRightRom(brand, manufacturer, strArr17)) {
                                        bean.name = strArr17[0];
                                    } else {
                                        String[] strArr18 = ROM_SONY;
                                        if (isRightRom(brand, manufacturer, strArr18)) {
                                            bean.name = strArr18[0];
                                        } else {
                                            String[] strArr19 = ROM_GIONEE;
                                            if (isRightRom(brand, manufacturer, strArr19)) {
                                                bean.name = strArr19[0];
                                            } else {
                                                String[] strArr20 = ROM_MOTOROLA;
                                                if (isRightRom(brand, manufacturer, strArr20)) {
                                                    bean.name = strArr20[0];
                                                } else {
                                                    bean.name = manufacturer;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        bean.version = getRomVersion("");
        return bean;
    }

    private static boolean isRightRom(final String brand, final String manufacturer, final String... names) {
        for (String str : names) {
            if (brand.contains(str) || manufacturer.contains(str)) {
                return true;
            }
        }
        return false;
    }

    private static String getManufacturer() {
        try {
            String str = Build.MANUFACTURER;
            return !TextUtils.isEmpty(str) ? str.toLowerCase() : "unknown";
        } catch (Throwable unused) {
            return "unknown";
        }
    }

    private static String getBrand() {
        try {
            String str = Build.BRAND;
            return !TextUtils.isEmpty(str) ? str.toLowerCase() : "unknown";
        } catch (Throwable unused) {
            return "unknown";
        }
    }

    private static String getRomVersion(final String propertyName) {
        String systemProperty = !TextUtils.isEmpty(propertyName) ? getSystemProperty(propertyName) : "";
        if (TextUtils.isEmpty(systemProperty) || systemProperty.equals("unknown")) {
            try {
                String str = Build.DISPLAY;
                if (!TextUtils.isEmpty(str)) {
                    systemProperty = str.toLowerCase();
                }
            } catch (Throwable unused) {
            }
        }
        return TextUtils.isEmpty(systemProperty) ? "unknown" : systemProperty;
    }

    private static String getSystemProperty(final String name) {
        String systemPropertyByShell = getSystemPropertyByShell(name);
        if (!TextUtils.isEmpty(systemPropertyByShell)) {
            return systemPropertyByShell;
        }
        String systemPropertyByStream = getSystemPropertyByStream(name);
        return (TextUtils.isEmpty(systemPropertyByStream) && Build.VERSION.SDK_INT < 28) ? getSystemPropertyByReflect(name) : systemPropertyByStream;
    }

    private static String getSystemPropertyByShell(final String propName) {
        BufferedReader bufferedReader;
        String readLine;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop " + propName).getInputStream()), 1024);
                try {
                    readLine = bufferedReader.readLine();
                } catch (IOException unused) {
                    bufferedReader2 = bufferedReader;
                    if (bufferedReader2 == null) {
                        return "";
                    }
                    bufferedReader2.close();
                    return "";
                } catch (Throwable th) {
                    th = th;
                    bufferedReader2 = bufferedReader;
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused3) {
            } catch (Throwable th2) {
                th = th2;
            }
            if (readLine != null) {
                try {
                    bufferedReader.close();
                } catch (IOException unused4) {
                }
                return readLine;
            }
            bufferedReader.close();
            return "";
        } catch (IOException unused5) {
            return "";
        }
    }

    private static String getSystemPropertyByStream(final String key) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            return properties.getProperty(key, "");
        } catch (Exception unused) {
            return "";
        }
    }

    private static String getSystemPropertyByReflect(String key) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, key, "");
        } catch (Exception unused) {
            return "";
        }
    }

    public static class RomInfo {
        private String name;
        private String version;

        public String toString() {
            return "RomInfo{name=" + this.name + ", version=" + this.version + "}";
        }
    }
}

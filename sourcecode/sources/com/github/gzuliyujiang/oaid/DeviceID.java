package com.github.gzuliyujiang.oaid;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.media.MediaDrm;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.github.gzuliyujiang.oaid.impl.OAIDFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.UUID;

/* loaded from: classes.dex */
public final class DeviceID {
    private Application application;
    private String clientId;
    private String oaid;
    private boolean tryWidevine;

    private static class Holder {
        static final DeviceID INSTANCE = new DeviceID();
    }

    public static void register(Application application, boolean z, IRegisterCallback iRegisterCallback) {
        if (application == null) {
            if (iRegisterCallback != null) {
                iRegisterCallback.onComplete("", new RuntimeException("application is nulll"));
                return;
            }
            return;
        }
        DeviceID deviceID = Holder.INSTANCE;
        deviceID.application = application;
        deviceID.tryWidevine = z;
        String uniqueID = getUniqueID(application);
        if (!TextUtils.isEmpty(uniqueID)) {
            deviceID.clientId = uniqueID;
            OAIDLog.print("Client id is IMEI/MEID: " + deviceID.clientId);
            if (iRegisterCallback != null) {
                iRegisterCallback.onComplete(uniqueID, null);
                return;
            }
            return;
        }
        getOAIDOrOtherId(application, z, iRegisterCallback);
    }

    private static void getOAIDOrOtherId(final Application application, final boolean z, final IRegisterCallback iRegisterCallback) {
        getOAID(application, new IGetter() { // from class: com.github.gzuliyujiang.oaid.DeviceID.1
            @Override // com.github.gzuliyujiang.oaid.IGetter
            public void onOAIDGetComplete(String str) {
                if (TextUtils.isEmpty(str)) {
                    onOAIDGetError(new OAIDException("OAID is empty"));
                    return;
                }
                DeviceID deviceID = Holder.INSTANCE;
                deviceID.clientId = str;
                deviceID.oaid = str;
                OAIDLog.print("Client id is OAID/AAID: " + str);
            }

            @Override // com.github.gzuliyujiang.oaid.IGetter
            public void onOAIDGetError(Exception exc) {
                DeviceID.getOtherId(exc, application, z, iRegisterCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void getOtherId(Exception exc, Application application, boolean z, IRegisterCallback iRegisterCallback) {
        if (z) {
            String widevineID = getWidevineID();
            if (!TextUtils.isEmpty(widevineID)) {
                Holder.INSTANCE.clientId = widevineID;
                OAIDLog.print("Client id is WidevineID: " + widevineID);
                if (iRegisterCallback != null) {
                    iRegisterCallback.onComplete(widevineID, exc);
                    return;
                }
                return;
            }
        }
        String androidID = getAndroidID(application);
        if (!TextUtils.isEmpty(androidID)) {
            Holder.INSTANCE.clientId = androidID;
            OAIDLog.print("Client id is AndroidID: " + androidID);
            if (iRegisterCallback != null) {
                iRegisterCallback.onComplete(androidID, exc);
                return;
            }
            return;
        }
        String guid = getGUID(application);
        Holder.INSTANCE.clientId = guid;
        OAIDLog.print("Client id is GUID: " + guid);
        if (iRegisterCallback != null) {
            iRegisterCallback.onComplete(guid, exc);
        }
    }

    public static String getClientId() {
        String str = Holder.INSTANCE.clientId;
        return str == null ? "" : str;
    }

    public static String getClientIdMD5() {
        return calculateHash(getClientId(), "MD5");
    }

    public static String getOAID() {
        String str = Holder.INSTANCE.oaid;
        return str == null ? "" : str;
    }

    public static void getOAID(Context context, IGetter iGetter) {
        IOAID create = OAIDFactory.create(context);
        OAIDLog.print("OAID implements class: " + create.getClass().getName());
        create.doGet(iGetter);
    }

    public static String getUniqueID(Context context) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 29) {
            OAIDLog.print("IMEI/MEID not allowed on Android 10+");
            return "";
        }
        if (context == null) {
            return "";
        }
        if (i >= 23 && context.checkSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
            OAIDLog.print("android.permission.READ_PHONE_STATE not granted");
            return "";
        }
        return getIMEI(context);
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    private static String getIMEI(Context context) {
        if (context == null) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            String imei = telephonyManager.getImei();
            return TextUtils.isEmpty(imei) ? telephonyManager.getMeid() : imei;
        } catch (Error e) {
            OAIDLog.print(e);
            return "";
        } catch (Exception e2) {
            OAIDLog.print(e2);
            return "";
        }
    }

    @SuppressLint({"HardwareIds"})
    public static String getAndroidID(Context context) {
        String string;
        return (context == null || (string = Settings.Secure.getString(context.getContentResolver(), "android_id")) == null || "9774d56d682e549c".equals(string)) ? "" : string;
    }

    @Deprecated
    public static String getWidevineID() {
        try {
            byte[] propertyByteArray = new MediaDrm(new UUID(-1301668207276963122L, -6645017420763422227L)).getPropertyByteArray("deviceUniqueId");
            if (propertyByteArray == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (byte b : propertyByteArray) {
                sb.append(String.format("%02x", Byte.valueOf(b)));
            }
            return sb.toString();
        } catch (Throwable th) {
            OAIDLog.print(th);
            return "";
        }
    }

    public static String getPseudoID() {
        StringBuilder sb = new StringBuilder();
        sb.append(Build.BOARD.length() % 10);
        if (Build.VERSION.SDK_INT >= 21) {
            sb.append(Arrays.deepToString(Build.SUPPORTED_ABIS).length() % 10);
        } else {
            sb.append(Build.CPU_ABI.length() % 10);
        }
        sb.append(Build.DEVICE.length() % 10);
        sb.append(Build.DISPLAY.length() % 10);
        sb.append(Build.HOST.length() % 10);
        sb.append(Build.ID.length() % 10);
        sb.append(Build.MANUFACTURER.length() % 10);
        sb.append(Build.BRAND.length() % 10);
        sb.append(Build.MODEL.length() % 10);
        sb.append(Build.PRODUCT.length() % 10);
        sb.append(Build.BOOTLOADER.length() % 10);
        sb.append(Build.HARDWARE.length() % 10);
        sb.append(Build.TAGS.length() % 10);
        sb.append(Build.TYPE.length() % 10);
        sb.append(Build.USER.length() % 10);
        return sb.toString();
    }

    public static String getGUID(Context context) {
        String uuidFromSystemSettings = getUuidFromSystemSettings(context);
        if (TextUtils.isEmpty(uuidFromSystemSettings)) {
            uuidFromSystemSettings = getUuidFromExternalStorage(context);
        }
        if (TextUtils.isEmpty(uuidFromSystemSettings)) {
            uuidFromSystemSettings = getUuidFromSharedPreferences(context);
        }
        if (!TextUtils.isEmpty(uuidFromSystemSettings)) {
            return uuidFromSystemSettings;
        }
        String uuid = UUID.randomUUID().toString();
        OAIDLog.print("Generate uuid by random: " + uuid);
        saveUuidToSharedPreferences(context, uuid);
        saveUuidToSystemSettings(context, uuid);
        saveUuidToExternalStorage(context, uuid);
        return uuid;
    }

    private static String getUuidFromSystemSettings(Context context) {
        if (context == null) {
            return "";
        }
        String string = Settings.System.getString(context.getContentResolver(), "GUID_uuid");
        OAIDLog.print("Get uuid from system settings: " + string);
        return string;
    }

    private static void saveUuidToSystemSettings(Context context, String str) {
        if (context == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < 23 || Settings.System.canWrite(context)) {
            try {
                Settings.System.putString(context.getContentResolver(), "GUID_uuid", str);
                OAIDLog.print("Save uuid to system settings: " + str);
                return;
            } catch (Exception e) {
                OAIDLog.print(e);
                return;
            }
        }
        OAIDLog.print("android.permission.WRITE_SETTINGS not granted");
    }

    private static String getUuidFromExternalStorage(Context context) {
        String str = "";
        if (context == null) {
            return "";
        }
        File guidFile = getGuidFile(context);
        if (guidFile != null) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(guidFile));
                try {
                    str = bufferedReader.readLine();
                    bufferedReader.close();
                } finally {
                }
            } catch (Exception e) {
                OAIDLog.print(e);
            }
        }
        OAIDLog.print("Get uuid from external storage: " + str);
        return str;
    }

    private static void saveUuidToExternalStorage(Context context, String str) {
        if (context == null) {
            return;
        }
        File guidFile = getGuidFile(context);
        if (guidFile == null) {
            OAIDLog.print("UUID file in external storage is null");
            return;
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(guidFile));
            try {
                if (!guidFile.exists()) {
                    guidFile.createNewFile();
                }
                bufferedWriter.write(str);
                bufferedWriter.flush();
                OAIDLog.print("Save uuid to external storage: " + str);
                bufferedWriter.close();
            } finally {
            }
        } catch (Exception e) {
            OAIDLog.print(e);
        }
    }

    private static File getGuidFile(Context context) {
        int i = Build.VERSION.SDK_INT;
        boolean z = true;
        if (i >= 23 && (i >= 30 || context == null || context.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0)) {
            z = false;
        }
        if (z && "mounted".equals(Environment.getExternalStorageState())) {
            return new File(Environment.getExternalStorageDirectory(), "Android/.GUID_uuid");
        }
        return null;
    }

    private static void saveUuidToSharedPreferences(Context context, String str) {
        if (context == null) {
            return;
        }
        context.getSharedPreferences("GUID", 0).edit().putString("uuid", str).apply();
        OAIDLog.print("Save uuid to shared preferences: " + str);
    }

    private static String getUuidFromSharedPreferences(Context context) {
        if (context == null) {
            return "";
        }
        String string = context.getSharedPreferences("GUID", 0).getString("uuid", "");
        OAIDLog.print("Get uuid from shared preferences: " + string);
        return string;
    }

    public static String calculateHash(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            byte[] digest = MessageDigest.getInstance(str2).digest(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", Byte.valueOf(b)));
            }
            return sb.toString();
        } catch (Exception e) {
            OAIDLog.print(e);
            return "";
        }
    }

    private DeviceID() {
    }
}

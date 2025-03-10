package org.telegram.ui;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.util.DisplayMetrics;
import android.widget.Toast;
import java.math.BigDecimal;
import java.util.Arrays;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;

/* loaded from: classes3.dex */
public class AntiAospUtils {

    public interface ScanDeviceListener {
        void onComplete(DeviceScanInfo deviceScanInfo);
    }

    private interface ScanDevicePlanAble {
        boolean isAllScanInfo();

        DeviceScanInfo scanDevice(Context context) throws Exception;
    }

    public static void startScanDeviceInfo(Context context) {
        startScanDeviceInfo(context, null);
    }

    public static void startScanDeviceInfo(Context context, ScanDeviceListener scanDeviceListener) {
        try {
            ScanDevicePlanWrapper scanDevicePlanWrapper = new ScanDevicePlanWrapper(new ScanScreenInfo());
            ScanDevicePlanWrapper scanDevicePlanWrapper2 = new ScanDevicePlanWrapper(new ScanAppInfo());
            ScanDevicePlanWrapper scanDevicePlanWrapper3 = new ScanDevicePlanWrapper(new ScanCpuInfo());
            scanDevicePlanWrapper3.setNextScanDevicePlanWrapper(scanDevicePlanWrapper2);
            scanDevicePlanWrapper2.setNextScanDevicePlanWrapper(scanDevicePlanWrapper);
            DeviceScanInfo scanDevice = scanDevicePlanWrapper3.scanDevice(context);
            if (scanDevice.isFaker() || scanDevice.isBadDevice()) {
                Toast.makeText(context, LocaleController.getString("JMTDisableEmulatorRun", R.string.JMTDisableEmulatorRun), 0).show();
                Process.killProcess(Process.myPid());
                System.exit(0);
            }
            if (scanDeviceListener != null) {
                scanDeviceListener.onComplete(scanDevice);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class ScanDevicePlanWrapper implements ScanDevicePlanAble {
        public ScanDevicePlanWrapper mNextScanDevicePlanWrapper;
        public ScanDevicePlanAble mScanDevicePlan;

        public ScanDevicePlanWrapper(ScanDevicePlanAble scanDevicePlanAble) {
            this.mScanDevicePlan = scanDevicePlanAble;
        }

        public void setNextScanDevicePlanWrapper(ScanDevicePlanWrapper scanDevicePlanWrapper) {
            this.mNextScanDevicePlanWrapper = scanDevicePlanWrapper;
        }

        @Override // org.telegram.ui.AntiAospUtils.ScanDevicePlanAble
        public DeviceScanInfo scanDevice(Context context) throws Exception {
            DeviceScanInfo deviceScanInfo;
            ScanDevicePlanWrapper scanDevicePlanWrapper = this.mNextScanDevicePlanWrapper;
            if (scanDevicePlanWrapper != null) {
                deviceScanInfo = scanDevicePlanWrapper.scanDevice(context);
                if (!isAllScanInfo() && deviceScanInfo.isBadDevice()) {
                    return deviceScanInfo;
                }
            } else {
                deviceScanInfo = null;
            }
            DeviceScanInfo scanDevice = this.mScanDevicePlan.scanDevice(context);
            if (deviceScanInfo != null) {
                scanDevice.setScanInfo(deviceScanInfo.getScanInfo() + "  ||  " + scanDevice.getScanInfo());
                if (deviceScanInfo.isBadDevice()) {
                    scanDevice.setBadDevice(true);
                } else if (deviceScanInfo.isFaker()) {
                    scanDevice.setFaker(true);
                }
            }
            return scanDevice;
        }

        @Override // org.telegram.ui.AntiAospUtils.ScanDevicePlanAble
        public boolean isAllScanInfo() {
            return this.mScanDevicePlan.isAllScanInfo();
        }
    }

    private static class ScanCpuInfo implements ScanDevicePlanAble {
        @Override // org.telegram.ui.AntiAospUtils.ScanDevicePlanAble
        public boolean isAllScanInfo() {
            return false;
        }

        private ScanCpuInfo() {
        }

        @Override // org.telegram.ui.AntiAospUtils.ScanDevicePlanAble
        public DeviceScanInfo scanDevice(Context context) throws Exception {
            if (DeviceUtils.checkDeviceForumX86()) {
                return new DeviceScanInfo("scanCpuInfo：x86 == true", true);
            }
            return new DeviceScanInfo("scanCpuInfo：x86 == false", false);
        }
    }

    private static class ScanAppInfo implements ScanDevicePlanAble {
        private final String[] ALL_SCAN_PACKAGE_INFO;

        @Override // org.telegram.ui.AntiAospUtils.ScanDevicePlanAble
        public boolean isAllScanInfo() {
            return true;
        }

        private ScanAppInfo() {
            this.ALL_SCAN_PACKAGE_INFO = new String[]{"com.android.server.telecom", "com.android.contacts", "com.android.webview", "com.android.settings", "com.android.browser"};
        }

        @Override // org.telegram.ui.AntiAospUtils.ScanDevicePlanAble
        public DeviceScanInfo scanDevice(Context context) throws Exception {
            Exception e;
            String str;
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return new DeviceScanInfo("scanPackageInfo：packageManager == null", false, false);
            }
            String[] strArr = this.ALL_SCAN_PACKAGE_INFO;
            int length = strArr.length;
            int i = 0;
            boolean z = false;
            while (true) {
                boolean z2 = true;
                if (i < length) {
                    String str2 = strArr[i];
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(str2, 1);
                        if (packageInfo != null && (str = packageInfo.applicationInfo.nativeLibraryDir) != null) {
                            try {
                                if (str.contains("x86")) {
                                    return new DeviceScanInfo("scanPackageInfo：" + str2 + ".x86", true);
                                }
                                z = true;
                            } catch (Exception e2) {
                                e = e2;
                                e.printStackTrace();
                                z = z2;
                                i++;
                            }
                        }
                    } catch (Exception e3) {
                        z2 = z;
                        e = e3;
                    }
                    i++;
                } else {
                    return new DeviceScanInfo("scanPackageInfo：scanPackageInfo == null", false, !z);
                }
            }
        }
    }

    private static class ScanScreenInfo implements ScanDevicePlanAble {
        private static final Integer[] SCREEN_WIDTH = {480, 540};
        private static final Integer[] SCREEN_HEIGHT = {800, 960};

        @Override // org.telegram.ui.AntiAospUtils.ScanDevicePlanAble
        public boolean isAllScanInfo() {
            return true;
        }

        private ScanScreenInfo() {
        }

        @Override // org.telegram.ui.AntiAospUtils.ScanDevicePlanAble
        public DeviceScanInfo scanDevice(Context context) throws Exception {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            if (Arrays.asList(SCREEN_HEIGHT).contains(Integer.valueOf(i2)) && Arrays.asList(SCREEN_WIDTH).contains(Integer.valueOf(i))) {
                return new DeviceScanInfo("ScanScreenInfo：badPixel width-" + i + "、height-" + i2 + "-size：" + new BigDecimal(Math.sqrt(Math.pow(i, 2.0d) + Math.pow(i2, 2.0d)) / (displayMetrics.density * 160.0f)).setScale(1, 4).doubleValue(), true);
            }
            return new DeviceScanInfo("ScanScreenInfo：normal《 width-" + i + "、height-" + i2 + " 》", false);
        }
    }

    public static class DeviceScanInfo {
        private boolean isBadDevice;
        private boolean isFaker;
        private String mScanInfo;

        public DeviceScanInfo(String str, boolean z, boolean z2) {
            this.mScanInfo = "";
            this.isBadDevice = false;
            this.isFaker = false;
            this.mScanInfo = str;
            this.isBadDevice = z;
            this.isFaker = z2;
        }

        public DeviceScanInfo(String str, boolean z) {
            this.mScanInfo = "";
            this.isBadDevice = false;
            this.isFaker = false;
            this.mScanInfo = str;
            this.isBadDevice = z;
        }

        public String getScanInfo() {
            return this.mScanInfo;
        }

        public void setScanInfo(String str) {
            this.mScanInfo = str;
        }

        public boolean isBadDevice() {
            return this.isBadDevice;
        }

        public void setBadDevice(boolean z) {
            this.isBadDevice = z;
        }

        public boolean isFaker() {
            return this.isFaker;
        }

        public void setFaker(boolean z) {
            this.isFaker = z;
        }

        public String toString() {
            if (this.isBadDevice) {
                return "BadDevice: " + this.isBadDevice + "。" + this.mScanInfo;
            }
            return "Faker: " + this.isFaker + "，" + this.mScanInfo;
        }
    }
}

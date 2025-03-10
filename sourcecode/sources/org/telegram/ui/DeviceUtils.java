package org.telegram.ui;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;

/* compiled from: AntiAospUtils.java */
/* loaded from: classes3.dex */
class DeviceUtils {
    private static ARCH sArch = ARCH.Unknown;

    /* compiled from: AntiAospUtils.java */
    public enum ARCH {
        Unknown,
        ARM,
        X86,
        MIPS,
        ARM64
    }

    public static synchronized ARCH getMyCpuArch() {
        RandomAccessFile randomAccessFile;
        synchronized (DeviceUtils.class) {
            byte[] bArr = new byte[20];
            File file = new File(Environment.getRootDirectory(), "lib/libc.so");
            if (file.canRead()) {
                RandomAccessFile randomAccessFile2 = null;
                try {
                    try {
                        randomAccessFile = new RandomAccessFile(file, "r");
                        try {
                            randomAccessFile.readFully(bArr);
                            int i = bArr[18] | (bArr[19] << 8);
                            if (i == 3) {
                                sArch = ARCH.X86;
                            } else if (i == 8) {
                                sArch = ARCH.MIPS;
                            } else if (i == 40) {
                                sArch = ARCH.ARM;
                            } else if (i == 183) {
                                sArch = ARCH.ARM64;
                            } else {
                                Log.e("NativeBitmapFactory", "libc.so is unknown arch: " + Integer.toHexString(i));
                            }
                        } catch (Exception e) {
                            e = e;
                            randomAccessFile2 = randomAccessFile;
                            e.printStackTrace();
                            if (randomAccessFile2 != null) {
                                try {
                                    randomAccessFile2.close();
                                } catch (Exception e2) {
                                    e = e2;
                                    e.printStackTrace();
                                    return sArch;
                                }
                            }
                            return sArch;
                        } catch (Throwable th) {
                            th = th;
                            randomAccessFile2 = randomAccessFile;
                            if (randomAccessFile2 != null) {
                                try {
                                    randomAccessFile2.close();
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Exception e4) {
                    e = e4;
                }
                try {
                    randomAccessFile.close();
                } catch (Exception e5) {
                    e = e5;
                    e.printStackTrace();
                    return sArch;
                }
            }
        }
        return sArch;
    }

    public static String get_CPU_ABI() {
        return Build.CPU_ABI;
    }

    public static String get_CPU_ABI2() {
        try {
            Field declaredField = Build.class.getDeclaredField("CPU_ABI2");
            if (declaredField == null) {
                return null;
            }
            Object obj = declaredField.get(null);
            if (obj instanceof String) {
                return (String) obj;
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean supportABI(String str) {
        String _cpu_abi = get_CPU_ABI();
        if (TextUtils.isEmpty(_cpu_abi) || !_cpu_abi.equalsIgnoreCase(str)) {
            return !TextUtils.isEmpty(get_CPU_ABI2()) && _cpu_abi.equalsIgnoreCase(str);
        }
        return true;
    }

    public static boolean supportX86() {
        return supportABI("x86");
    }

    public static boolean isARMSimulatedByX86() {
        return !supportX86() && ARCH.X86.equals(getMyCpuArch());
    }

    public static boolean isRealX86Arch() {
        return supportABI("x86") || ARCH.X86.equals(getMyCpuArch());
    }

    public static boolean checkDeviceForumX86() {
        return isRealX86Arch() || isARMSimulatedByX86() || supportX86();
    }
}

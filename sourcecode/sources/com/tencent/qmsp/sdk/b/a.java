package com.tencent.qmsp.sdk.b;

import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.qmsp.sdk.f.h;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class a {
    private static final byte[] a = {49, 99, -3, 81, 63, 117, 116, -14, 40};
    private static final byte[] b = {4, 85, Byte.MIN_VALUE};
    private static final byte[] c = {4, 85, Byte.MIN_VALUE, 15, AbstractJceStruct.SIMPLE_LIST, 25, 84, -78, 21, 91, -112, 115, 123, AbstractJceStruct.STRUCT_END, 118, -7, 33, 121, -67, 71};
    private static final byte[] d = {-58, -26, -51, -19};
    private static final byte[] e = {-58, -26, -51, -19, 30, -3, -21, -29, 87, 39, 40, AbstractJceStruct.ZERO_TAG, -119, -40, -84, 65};
    private static final b f = b.AES;
    private static ThreadLocal<Integer> g = new ThreadLocal<>();

    /* renamed from: com.tencent.qmsp.sdk.b.a$a, reason: collision with other inner class name */
    static /* synthetic */ class C0031a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b.values().length];
            a = iArr;
            try {
                iArr[b.AES.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[b.NONE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum b {
        UNKNOWN(0, ""),
        AES(1, ""),
        NONE(2, "");

        private short a;
        private String b;

        b(short s, String str) {
            this.a = s;
            this.b = str;
        }

        public static b a(int i) {
            return i != 1 ? i != 2 ? UNKNOWN : NONE : AES;
        }

        public int a() {
            return this.a;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.b;
        }
    }

    private enum c {
        SUCCESS(0, ""),
        OVERLOAD(1, ""),
        FATAL(2, ""),
        CMD_UNKNOWN(3, ""),
        HOST_UNKNOWN(10, ""),
        CONN_ERR(11, ""),
        SEND_ERR(12, ""),
        RECV_ERR(13, ""),
        WRONG_FORMAT(14, ""),
        SYS_ERR(15, ""),
        DECIPHER_ERR(16, ""),
        DECODE_JSON(20, "");

        private int a;
        private String b;

        c(int i, String str) {
            this.a = i;
            this.b = str;
        }

        public int a() {
            return this.a;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.b;
        }
    }

    public static class d {
        public JSONObject a;
        public int b;

        public d(JSONObject jSONObject, int i) {
            this.a = jSONObject;
            this.b = i;
        }
    }

    public static d a(int i, String str, int i2, JSONObject jSONObject) {
        DataInputStream dataInputStream;
        AtomicInteger atomConnTimeOut;
        DataOutputStream dataOutputStream;
        AtomicInteger atomReadTimeOut;
        g.set(Integer.valueOf(c.SUCCESS.a()));
        InetSocketAddress inetSocketAddress = new InetSocketAddress(h.a(a), 33445);
        int i3 = 0;
        while (true) {
            DataOutputStream dataOutputStream2 = null;
            if (i3 >= 1) {
                return new d(null, g.get().intValue());
            }
            Socket socket = new Socket();
            try {
                try {
                    atomConnTimeOut = com.tencent.qmsp.sdk.app.a.getAtomConnTimeOut();
                    socket.connect(inetSocketAddress, atomConnTimeOut.get());
                    try {
                        dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    } catch (IOException unused) {
                        dataOutputStream = null;
                    }
                    try {
                        try {
                            a(i, str, i2, jSONObject, dataOutputStream);
                            atomReadTimeOut = com.tencent.qmsp.sdk.app.a.getAtomReadTimeOut();
                            socket.setSoTimeout(atomReadTimeOut.get());
                            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                            try {
                                d dVar = new d(a(dataInputStream), g.get().intValue());
                                a((Closeable) dataInputStream);
                                a(dataOutputStream);
                                try {
                                    socket.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                                return dVar;
                            } catch (Throwable unused2) {
                                dataOutputStream2 = dataOutputStream;
                                try {
                                    g.set(Integer.valueOf(c.SYS_ERR.a()));
                                    a((Closeable) dataInputStream);
                                    a(dataOutputStream2);
                                    try {
                                        socket.close();
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                    }
                                    i3++;
                                } catch (Throwable th) {
                                    a((Closeable) dataInputStream);
                                    a(dataOutputStream2);
                                    try {
                                        socket.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                    throw th;
                                }
                            }
                        } catch (Throwable unused3) {
                            dataInputStream = null;
                        }
                    } catch (IOException unused4) {
                        d dVar2 = new d(null, c.SEND_ERR.a());
                        a((Closeable) null);
                        a(dataOutputStream);
                        try {
                            socket.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                        return dVar2;
                    }
                } catch (IOException unused5) {
                    d dVar3 = new d(null, c.CONN_ERR.a());
                    a((Closeable) null);
                    a((Closeable) null);
                    try {
                        socket.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                    return dVar3;
                }
            } catch (Throwable unused6) {
                dataInputStream = null;
            }
            i3++;
        }
    }

    private static JSONObject a(b bVar, byte[] bArr) {
        int i = C0031a.a[bVar.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return null;
            }
            return new JSONObject(new String(bArr, Charset.forName("UTF-8")));
        }
        byte[] bArr2 = e;
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, h.a(b));
        Cipher cipher = Cipher.getInstance(h.a(c));
        cipher.init(2, secretKeySpec, new IvParameterSpec(bArr2));
        return new JSONObject(new String(cipher.doFinal(bArr), Charset.forName("UTF-8")));
    }

    private static JSONObject a(DataInputStream dataInputStream) {
        ThreadLocal<Integer> threadLocal;
        c cVar;
        byte[] bArr = new byte[28];
        try {
            dataInputStream.readFully(bArr);
            ByteBuffer wrap = ByteBuffer.wrap(bArr, 18, 10);
            g.set(Integer.valueOf(wrap.getShort()));
            b a2 = b.a(wrap.getShort());
            int i = wrap.getShort();
            int i2 = wrap.getInt();
            dataInputStream.readFully(new byte[i]);
            byte[] bArr2 = new byte[i2];
            dataInputStream.readFully(bArr2);
            if (i2 == 0) {
                return null;
            }
            try {
                return a(a2, bArr2);
            } catch (Exception unused) {
                threadLocal = g;
                cVar = c.DECIPHER_ERR;
                threadLocal.set(Integer.valueOf(cVar.a()));
                return null;
            }
        } catch (IOException unused2) {
            threadLocal = g;
            cVar = c.RECV_ERR;
        }
    }

    private static void a(int i, String str, int i2, JSONObject jSONObject, DataOutputStream dataOutputStream) {
        byte[] bytes;
        dataOutputStream.write(d);
        dataOutputStream.writeInt(i);
        dataOutputStream.writeInt(i2);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeShort(0);
        dataOutputStream.writeShort(0);
        b bVar = f;
        try {
            bytes = a(bVar, jSONObject);
        } catch (Exception unused) {
            bytes = jSONObject.toString().getBytes("UTF-8");
            bVar = b.NONE;
        }
        dataOutputStream.writeShort(bVar.a());
        byte[] bytes2 = str.getBytes("UTF-8");
        dataOutputStream.writeShort(bytes2.length + 3);
        dataOutputStream.writeInt(bytes.length);
        dataOutputStream.write(1);
        dataOutputStream.writeShort(bytes2.length);
        dataOutputStream.write(bytes2);
        dataOutputStream.write(bytes);
        dataOutputStream.flush();
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    private static byte[] a(b bVar, JSONObject jSONObject) {
        byte[] bytes = jSONObject.toString().getBytes("UTF-8");
        int i = C0031a.a[bVar.ordinal()];
        if (i != 1) {
            if (i == 2) {
                return bytes;
            }
            throw new IOException("unsupported");
        }
        byte[] bArr = e;
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, h.a(b));
        Cipher cipher = Cipher.getInstance(h.a(c));
        cipher.init(1, secretKeySpec, new IvParameterSpec(bArr));
        return cipher.doFinal(bytes);
    }
}

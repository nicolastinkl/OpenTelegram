package com.tencent.beacon.base.net.adapter;

import android.text.TextUtils;
import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.NetException;
import com.tencent.beacon.base.net.RequestType;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.beacon.pack.SocketRequestPackage;
import com.tencent.beacon.pack.SocketResponsePackage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.telegram.messenger.LiteMode;

/* compiled from: SocketAdapter.java */
/* loaded from: classes.dex */
public class f extends AbstractNetAdapter {
    private com.tencent.beacon.base.net.a.c<byte[], SocketResponsePackage> a = new com.tencent.beacon.base.net.a.e();
    private com.tencent.beacon.base.net.a.c<JceRequestEntity, SocketRequestPackage> b = new com.tencent.beacon.base.net.a.d();

    private f() {
    }

    public static AbstractNetAdapter a() {
        return new f();
    }

    @Override // com.tencent.beacon.base.net.adapter.AbstractNetAdapter
    public void request(JceRequestEntity jceRequestEntity, Callback<byte[]> callback) {
        String domain = jceRequestEntity.getDomain();
        if (TextUtils.isEmpty(domain)) {
            return;
        }
        String name = jceRequestEntity.getType().name();
        try {
            Socket a = a(domain, jceRequestEntity.getPort());
            StringBuilder sb = new StringBuilder();
            sb.append("send data size: ");
            sb.append(jceRequestEntity.getContent().length);
            com.tencent.beacon.base.util.c.a("SocketAdapter", 0, sb.toString(), new Object[0]);
            a(a, this.b.a(jceRequestEntity));
            byte[] a2 = a(a, jceRequestEntity.getType() == RequestType.EVENT);
            if (a2 != null && a2.length > 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("receivedData: ");
                sb2.append(a2.length);
                com.tencent.beacon.base.util.c.a("SocketAdapter", 1, sb2.toString(), new Object[0]);
                SocketResponsePackage a3 = this.a.a(a2);
                if (a3 == null) {
                    callback.onFailure(new com.tencent.beacon.base.net.d(name, "402", -1, "responsePackage == null"));
                    return;
                }
                com.tencent.beacon.base.util.c.a("SocketAdapter", 2, "socket response code: %s, header: %s, msg: %s", Integer.valueOf(a3.statusCode), a3.header, a3.msg);
                int i = a3.statusCode;
                if (i == 200) {
                    a(callback, name, a3);
                    return;
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append("responsePackage msg: ");
                sb3.append(a3.msg);
                callback.onFailure(new com.tencent.beacon.base.net.d(name, "402", i, sb3.toString()));
                return;
            }
            callback.onFailure(new com.tencent.beacon.base.net.d(name, "402", -1, "receiveData == null"));
        } catch (ConnectException e) {
            callback.onFailure(new com.tencent.beacon.base.net.d(name, "401", -1, " connect time more than 30s", e));
        } catch (SocketTimeoutException e2) {
            callback.onFailure(new com.tencent.beacon.base.net.d(name, "401", -1, " request time more than 30s", e2));
        } catch (Throwable th) {
            com.tencent.beacon.base.util.c.b("SocketAdapter socket request error: %s", th.getMessage());
            com.tencent.beacon.base.util.c.a(th);
            callback.onFailure(new com.tencent.beacon.base.net.d(name, "449", -1, " unknown request error!", th));
        }
    }

    @Override // com.tencent.beacon.base.net.adapter.AbstractNetAdapter
    public void request(com.tencent.beacon.base.net.call.e eVar, Callback<BResponse> callback) {
    }

    private void a(Callback<byte[]> callback, String str, SocketResponsePackage socketResponsePackage) throws NetException {
        String str2 = socketResponsePackage.msg;
        if (str2 == null || !str2.equals("decrypt Data fail!")) {
            callback.onResponse(socketResponsePackage.body);
        } else {
            callback.onFailure(new com.tencent.beacon.base.net.d(str, "405", socketResponsePackage.statusCode, "server encrypt-status error!"));
        }
    }

    private byte[] a(Socket socket, boolean z) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        InputStream inputStream;
        InputStream inputStream2 = null;
        try {
            inputStream = socket.getInputStream();
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (Throwable th) {
                th = th;
                byteArrayOutputStream = null;
            }
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
        }
        try {
            byte[] bArr = new byte[LiteMode.FLAG_AUTOPLAY_GIFS];
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            byteArrayOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (!z) {
                ByteBuffer allocate = ByteBuffer.allocate(byteArray.length - 4);
                allocate.put(byteArray, 2, byteArray.length - 4);
                byteArray = allocate.array();
            }
            byteArrayOutputStream.close();
            inputStream.close();
            com.tencent.beacon.base.util.b.a(inputStream, byteArrayOutputStream);
            return byteArray;
        } catch (Throwable th3) {
            th = th3;
            inputStream2 = inputStream;
            try {
                com.tencent.beacon.base.util.c.a(th);
                com.tencent.beacon.base.util.b.a(inputStream2, byteArrayOutputStream);
                throw th;
            } catch (Throwable th4) {
                com.tencent.beacon.base.util.b.a(inputStream2, byteArrayOutputStream);
                throw th4;
            }
        }
    }

    private void a(Socket socket, SocketRequestPackage socketRequestPackage) throws IOException {
        com.tencent.beacon.pack.b bVar = new com.tencent.beacon.pack.b();
        socketRequestPackage.writeTo(bVar);
        OutputStream outputStream = socket.getOutputStream();
        byte[] b = bVar.b();
        outputStream.write(a(b, b.length));
        outputStream.flush();
    }

    private byte[] a(byte[] bArr, int i) {
        int i2 = i + 4;
        ByteBuffer allocate = ByteBuffer.allocate(i2);
        allocate.order(ByteOrder.BIG_ENDIAN);
        allocate.putShort((short) (i2 & 65535));
        allocate.put(bArr);
        allocate.put(AbstractJceStruct.SIMPLE_LIST);
        allocate.put((byte) 10);
        if (i >= 65532) {
            com.tencent.beacon.base.util.c.b("[Error] send bytes exceed 64kB will failure!", new Object[0]);
        }
        return allocate.array();
    }

    private Socket a(String str, int i) throws IOException {
        com.tencent.beacon.base.util.c.a("SocketAdapter", "create socket domain: %s, port: %d", str, Integer.valueOf(i));
        Socket socket = new Socket(InetAddress.getByName(str).getHostAddress(), i);
        socket.setSoTimeout(30000);
        return socket;
    }
}

package j$.util.stream;

import java.security.AccessController;
import java.security.PrivilegedAction;

/* loaded from: classes2.dex */
abstract class V4 {
    static final boolean a = ((Boolean) AccessController.doPrivileged(new PrivilegedAction() { // from class: j$.util.stream.U4
        @Override // java.security.PrivilegedAction
        public final Object run() {
            boolean z = V4.a;
            return Boolean.valueOf(Boolean.getBoolean("org.openjdk.java.util.stream.tripwire"));
        }
    })).booleanValue();

    static void a(Class cls, String str) {
        throw new UnsupportedOperationException(cls + " tripwire tripped but logging not supported: " + str);
    }
}

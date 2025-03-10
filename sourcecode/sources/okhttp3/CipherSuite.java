package okhttp3;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.telegram.messenger.ImageReceiver;

/* compiled from: CipherSuite.kt */
/* loaded from: classes3.dex */
public final class CipherSuite {
    public static final Companion Companion;
    private static final Map<String, CipherSuite> INSTANCES;
    private static final Comparator<String> ORDER_BY_NAME;
    public static final CipherSuite TLS_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_CHACHA20_POLY1305_SHA256;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384;
    public static final CipherSuite TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256;
    public static final CipherSuite TLS_RSA_WITH_3DES_EDE_CBC_SHA;
    public static final CipherSuite TLS_RSA_WITH_AES_128_CBC_SHA;
    public static final CipherSuite TLS_RSA_WITH_AES_128_GCM_SHA256;
    public static final CipherSuite TLS_RSA_WITH_AES_256_CBC_SHA;
    public static final CipherSuite TLS_RSA_WITH_AES_256_GCM_SHA384;
    private final String javaName;

    public /* synthetic */ CipherSuite(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private CipherSuite(String str) {
        this.javaName = str;
    }

    public final String javaName() {
        return this.javaName;
    }

    public String toString() {
        return this.javaName;
    }

    /* compiled from: CipherSuite.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Comparator<String> getORDER_BY_NAME$okhttp() {
            return CipherSuite.ORDER_BY_NAME;
        }

        public final synchronized CipherSuite forJavaName(String javaName) {
            CipherSuite cipherSuite;
            Intrinsics.checkNotNullParameter(javaName, "javaName");
            cipherSuite = (CipherSuite) CipherSuite.INSTANCES.get(javaName);
            if (cipherSuite == null) {
                cipherSuite = (CipherSuite) CipherSuite.INSTANCES.get(secondaryName(javaName));
                if (cipherSuite == null) {
                    cipherSuite = new CipherSuite(javaName, null);
                }
                CipherSuite.INSTANCES.put(javaName, cipherSuite);
            }
            return cipherSuite;
        }

        private final String secondaryName(String str) {
            if (StringsKt__StringsJVMKt.startsWith$default(str, "TLS_", false, 2, null)) {
                String substring = str.substring(4);
                Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
                return Intrinsics.stringPlus("SSL_", substring);
            }
            if (!StringsKt__StringsJVMKt.startsWith$default(str, "SSL_", false, 2, null)) {
                return str;
            }
            String substring2 = str.substring(4);
            Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String).substring(startIndex)");
            return Intrinsics.stringPlus("TLS_", substring2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final CipherSuite init(String str, int i) {
            CipherSuite cipherSuite = new CipherSuite(str, null);
            CipherSuite.INSTANCES.put(str, cipherSuite);
            return cipherSuite;
        }
    }

    static {
        Companion companion = new Companion(null);
        Companion = companion;
        ORDER_BY_NAME = new Comparator<String>() { // from class: okhttp3.CipherSuite$Companion$ORDER_BY_NAME$1
            @Override // java.util.Comparator
            public int compare(String a, String b) {
                Intrinsics.checkNotNullParameter(a, "a");
                Intrinsics.checkNotNullParameter(b, "b");
                int min = Math.min(a.length(), b.length());
                for (int i = 4; i < min; i++) {
                    char charAt = a.charAt(i);
                    char charAt2 = b.charAt(i);
                    if (charAt != charAt2) {
                        return Intrinsics.compare(charAt, charAt2) < 0 ? -1 : 1;
                    }
                }
                int length = a.length();
                int length2 = b.length();
                if (length != length2) {
                    return length < length2 ? -1 : 1;
                }
                return 0;
            }
        };
        INSTANCES = new LinkedHashMap();
        companion.init("SSL_RSA_WITH_NULL_MD5", 1);
        companion.init("SSL_RSA_WITH_NULL_SHA", 2);
        companion.init("SSL_RSA_EXPORT_WITH_RC4_40_MD5", 3);
        companion.init("SSL_RSA_WITH_RC4_128_MD5", 4);
        companion.init("SSL_RSA_WITH_RC4_128_SHA", 5);
        companion.init("SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", 8);
        companion.init("SSL_RSA_WITH_DES_CBC_SHA", 9);
        TLS_RSA_WITH_3DES_EDE_CBC_SHA = companion.init("SSL_RSA_WITH_3DES_EDE_CBC_SHA", 10);
        companion.init("SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA", 17);
        companion.init("SSL_DHE_DSS_WITH_DES_CBC_SHA", 18);
        companion.init("SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", 19);
        companion.init("SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", 20);
        companion.init("SSL_DHE_RSA_WITH_DES_CBC_SHA", 21);
        companion.init("SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", 22);
        companion.init("SSL_DH_anon_EXPORT_WITH_RC4_40_MD5", 23);
        companion.init("SSL_DH_anon_WITH_RC4_128_MD5", 24);
        companion.init("SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA", 25);
        companion.init("SSL_DH_anon_WITH_DES_CBC_SHA", 26);
        companion.init("SSL_DH_anon_WITH_3DES_EDE_CBC_SHA", 27);
        companion.init("TLS_KRB5_WITH_DES_CBC_SHA", 30);
        companion.init("TLS_KRB5_WITH_3DES_EDE_CBC_SHA", 31);
        companion.init("TLS_KRB5_WITH_RC4_128_SHA", 32);
        companion.init("TLS_KRB5_WITH_DES_CBC_MD5", 34);
        companion.init("TLS_KRB5_WITH_3DES_EDE_CBC_MD5", 35);
        companion.init("TLS_KRB5_WITH_RC4_128_MD5", 36);
        companion.init("TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA", 38);
        companion.init("TLS_KRB5_EXPORT_WITH_RC4_40_SHA", 40);
        companion.init("TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5", 41);
        companion.init("TLS_KRB5_EXPORT_WITH_RC4_40_MD5", 43);
        TLS_RSA_WITH_AES_128_CBC_SHA = companion.init("TLS_RSA_WITH_AES_128_CBC_SHA", 47);
        companion.init("TLS_DHE_DSS_WITH_AES_128_CBC_SHA", 50);
        companion.init("TLS_DHE_RSA_WITH_AES_128_CBC_SHA", 51);
        companion.init("TLS_DH_anon_WITH_AES_128_CBC_SHA", 52);
        TLS_RSA_WITH_AES_256_CBC_SHA = companion.init("TLS_RSA_WITH_AES_256_CBC_SHA", 53);
        companion.init("TLS_DHE_DSS_WITH_AES_256_CBC_SHA", 56);
        companion.init("TLS_DHE_RSA_WITH_AES_256_CBC_SHA", 57);
        companion.init("TLS_DH_anon_WITH_AES_256_CBC_SHA", 58);
        companion.init("TLS_RSA_WITH_NULL_SHA256", 59);
        companion.init("TLS_RSA_WITH_AES_128_CBC_SHA256", 60);
        companion.init("TLS_RSA_WITH_AES_256_CBC_SHA256", 61);
        companion.init("TLS_DHE_DSS_WITH_AES_128_CBC_SHA256", 64);
        companion.init("TLS_RSA_WITH_CAMELLIA_128_CBC_SHA", 65);
        companion.init("TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA", 68);
        companion.init("TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA", 69);
        companion.init("TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", 103);
        companion.init("TLS_DHE_DSS_WITH_AES_256_CBC_SHA256", 106);
        companion.init("TLS_DHE_RSA_WITH_AES_256_CBC_SHA256", 107);
        companion.init("TLS_DH_anon_WITH_AES_128_CBC_SHA256", 108);
        companion.init("TLS_DH_anon_WITH_AES_256_CBC_SHA256", 109);
        companion.init("TLS_RSA_WITH_CAMELLIA_256_CBC_SHA", 132);
        companion.init("TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA", 135);
        companion.init("TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA", 136);
        companion.init("TLS_PSK_WITH_RC4_128_SHA", 138);
        companion.init("TLS_PSK_WITH_3DES_EDE_CBC_SHA", 139);
        companion.init("TLS_PSK_WITH_AES_128_CBC_SHA", 140);
        companion.init("TLS_PSK_WITH_AES_256_CBC_SHA", 141);
        companion.init("TLS_RSA_WITH_SEED_CBC_SHA", ImageReceiver.DEFAULT_CROSSFADE_DURATION);
        TLS_RSA_WITH_AES_128_GCM_SHA256 = companion.init("TLS_RSA_WITH_AES_128_GCM_SHA256", 156);
        TLS_RSA_WITH_AES_256_GCM_SHA384 = companion.init("TLS_RSA_WITH_AES_256_GCM_SHA384", 157);
        companion.init("TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", 158);
        companion.init("TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", 159);
        companion.init("TLS_DHE_DSS_WITH_AES_128_GCM_SHA256", 162);
        companion.init("TLS_DHE_DSS_WITH_AES_256_GCM_SHA384", 163);
        companion.init("TLS_DH_anon_WITH_AES_128_GCM_SHA256", 166);
        companion.init("TLS_DH_anon_WITH_AES_256_GCM_SHA384", 167);
        companion.init("TLS_EMPTY_RENEGOTIATION_INFO_SCSV", 255);
        companion.init("TLS_FALLBACK_SCSV", 22016);
        companion.init("TLS_ECDH_ECDSA_WITH_NULL_SHA", 49153);
        companion.init("TLS_ECDH_ECDSA_WITH_RC4_128_SHA", 49154);
        companion.init("TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA", 49155);
        companion.init("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA", 49156);
        companion.init("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA", 49157);
        companion.init("TLS_ECDHE_ECDSA_WITH_NULL_SHA", 49158);
        companion.init("TLS_ECDHE_ECDSA_WITH_RC4_128_SHA", 49159);
        companion.init("TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", 49160);
        companion.init("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", 49161);
        companion.init("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA", 49162);
        companion.init("TLS_ECDH_RSA_WITH_NULL_SHA", 49163);
        companion.init("TLS_ECDH_RSA_WITH_RC4_128_SHA", 49164);
        companion.init("TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA", 49165);
        companion.init("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA", 49166);
        companion.init("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA", 49167);
        companion.init("TLS_ECDHE_RSA_WITH_NULL_SHA", 49168);
        companion.init("TLS_ECDHE_RSA_WITH_RC4_128_SHA", 49169);
        companion.init("TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", 49170);
        TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA = companion.init("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", 49171);
        TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA = companion.init("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", 49172);
        companion.init("TLS_ECDH_anon_WITH_NULL_SHA", 49173);
        companion.init("TLS_ECDH_anon_WITH_RC4_128_SHA", 49174);
        companion.init("TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA", 49175);
        companion.init("TLS_ECDH_anon_WITH_AES_128_CBC_SHA", 49176);
        companion.init("TLS_ECDH_anon_WITH_AES_256_CBC_SHA", 49177);
        companion.init("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256", 49187);
        companion.init("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384", 49188);
        companion.init("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256", 49189);
        companion.init("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384", 49190);
        companion.init("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", 49191);
        companion.init("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384", 49192);
        companion.init("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256", 49193);
        companion.init("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384", 49194);
        TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256 = companion.init("TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", 49195);
        TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384 = companion.init("TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", 49196);
        companion.init("TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256", 49197);
        companion.init("TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384", 49198);
        TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256 = companion.init("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", 49199);
        TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384 = companion.init("TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", 49200);
        companion.init("TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256", 49201);
        companion.init("TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384", 49202);
        companion.init("TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA", 49205);
        companion.init("TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA", 49206);
        TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256 = companion.init("TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256", 52392);
        TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256 = companion.init("TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256", 52393);
        companion.init("TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256", 52394);
        companion.init("TLS_ECDHE_PSK_WITH_CHACHA20_POLY1305_SHA256", 52396);
        TLS_AES_128_GCM_SHA256 = companion.init("TLS_AES_128_GCM_SHA256", 4865);
        TLS_AES_256_GCM_SHA384 = companion.init("TLS_AES_256_GCM_SHA384", 4866);
        TLS_CHACHA20_POLY1305_SHA256 = companion.init("TLS_CHACHA20_POLY1305_SHA256", 4867);
        companion.init("TLS_AES_128_CCM_SHA256", 4868);
        companion.init("TLS_AES_128_CCM_8_SHA256", 4869);
    }
}

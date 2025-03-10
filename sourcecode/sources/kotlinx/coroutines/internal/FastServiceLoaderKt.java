package kotlinx.coroutines.internal;

import kotlin.Result;
import kotlin.ResultKt;

/* compiled from: FastServiceLoader.kt */
/* loaded from: classes3.dex */
public final class FastServiceLoaderKt {
    private static final boolean ANDROID_DETECTED;

    static {
        Object m164constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            m164constructorimpl = Result.m164constructorimpl(Class.forName("android.os.Build"));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m164constructorimpl = Result.m164constructorimpl(ResultKt.createFailure(th));
        }
        ANDROID_DETECTED = Result.m167isSuccessimpl(m164constructorimpl);
    }

    public static final boolean getANDROID_DETECTED() {
        return ANDROID_DETECTED;
    }
}

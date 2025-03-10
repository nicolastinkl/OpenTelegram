package okhttp3.internal.http;

import okhttp3.Interceptor;

/* compiled from: CallServerInterceptor.kt */
/* loaded from: classes3.dex */
public final class CallServerInterceptor implements Interceptor {
    private final boolean forWebSocket;

    private final boolean shouldIgnoreAndWaitForRealResponse(int i) {
        if (i != 100) {
            if (!(102 <= i && i < 200)) {
                return false;
            }
        }
        return true;
    }

    public CallServerInterceptor(boolean z) {
        this.forWebSocket = z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x0150, code lost:
    
        if (r0 != false) goto L59;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00df A[Catch: IOException -> 0x019f, TryCatch #3 {IOException -> 0x019f, blocks: (B:64:0x00a8, B:66:0x00b1, B:22:0x00b5, B:24:0x00df, B:26:0x00e8, B:27:0x00eb, B:28:0x010f, B:32:0x011a, B:33:0x0139, B:35:0x0147, B:43:0x015d, B:48:0x0170, B:51:0x0193, B:52:0x019d, B:59:0x018b, B:60:0x0166, B:61:0x0152, B:62:0x0129), top: B:63:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0147 A[Catch: IOException -> 0x019f, TryCatch #3 {IOException -> 0x019f, blocks: (B:64:0x00a8, B:66:0x00b1, B:22:0x00b5, B:24:0x00df, B:26:0x00e8, B:27:0x00eb, B:28:0x010f, B:32:0x011a, B:33:0x0139, B:35:0x0147, B:43:0x015d, B:48:0x0170, B:51:0x0193, B:52:0x019d, B:59:0x018b, B:60:0x0166, B:61:0x0152, B:62:0x0129), top: B:63:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0170 A[Catch: IOException -> 0x019f, TryCatch #3 {IOException -> 0x019f, blocks: (B:64:0x00a8, B:66:0x00b1, B:22:0x00b5, B:24:0x00df, B:26:0x00e8, B:27:0x00eb, B:28:0x010f, B:32:0x011a, B:33:0x0139, B:35:0x0147, B:43:0x015d, B:48:0x0170, B:51:0x0193, B:52:0x019d, B:59:0x018b, B:60:0x0166, B:61:0x0152, B:62:0x0129), top: B:63:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0166 A[Catch: IOException -> 0x019f, TryCatch #3 {IOException -> 0x019f, blocks: (B:64:0x00a8, B:66:0x00b1, B:22:0x00b5, B:24:0x00df, B:26:0x00e8, B:27:0x00eb, B:28:0x010f, B:32:0x011a, B:33:0x0139, B:35:0x0147, B:43:0x015d, B:48:0x0170, B:51:0x0193, B:52:0x019d, B:59:0x018b, B:60:0x0166, B:61:0x0152, B:62:0x0129), top: B:63:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00a8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01a8  */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v14, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v16 */
    /* JADX WARN: Type inference failed for: r9v17 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v22, types: [okhttp3.Response$Builder] */
    /* JADX WARN: Type inference failed for: r9v24 */
    /* JADX WARN: Type inference failed for: r9v25 */
    /* JADX WARN: Type inference failed for: r9v26 */
    /* JADX WARN: Type inference failed for: r9v27 */
    @Override // okhttp3.Interceptor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public okhttp3.Response intercept(okhttp3.Interceptor.Chain r14) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 425
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http.CallServerInterceptor.intercept(okhttp3.Interceptor$Chain):okhttp3.Response");
    }
}

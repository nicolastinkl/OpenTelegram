package io.openinstall.sdk;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

/* loaded from: classes.dex */
public class ap extends ao {
    public ap(Context context) {
        super(context);
    }

    private String c(String str) {
        return str + ".mp3";
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0067, code lost:
    
        if (r9.isClosed() == false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0050, code lost:
    
        if (r9.isClosed() == false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0069, code lost:
    
        r9.close();
     */
    @Override // io.openinstall.sdk.ao
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String b(java.lang.String r9) {
        /*
            r8 = this;
            java.lang.String r0 = "title"
            android.content.Context r1 = r8.a
            android.content.ContentResolver r2 = r1.getContentResolver()
            android.net.Uri r3 = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            r1 = 0
            java.lang.String r4 = "_display_name"
            java.lang.String[] r4 = new java.lang.String[]{r4, r0}     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L60
            java.lang.String r5 = "_display_name=?"
            r6 = 1
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L60
            r7 = 0
            java.lang.String r9 = r8.c(r9)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L60
            r6[r7] = r9     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L60
            r7 = 0
            android.database.Cursor r9 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L60
            if (r9 == 0) goto L4a
            int r2 = r9.getCount()     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L48
            if (r2 <= 0) goto L4a
            r9.moveToFirst()     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L48
            int r0 = r9.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L48
            if (r0 < 0) goto L4a
            java.lang.String r0 = r9.getString(r0)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L48
            java.lang.String r0 = io.openinstall.sdk.fv.a(r0)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L48
            boolean r1 = r9.isClosed()
            if (r1 != 0) goto L44
            r9.close()
        L44:
            return r0
        L45:
            r0 = move-exception
            r1 = r9
            goto L54
        L48:
            goto L61
        L4a:
            if (r9 == 0) goto L6c
            boolean r0 = r9.isClosed()
            if (r0 != 0) goto L6c
            goto L69
        L53:
            r0 = move-exception
        L54:
            if (r1 == 0) goto L5f
            boolean r9 = r1.isClosed()
            if (r9 != 0) goto L5f
            r1.close()
        L5f:
            throw r0
        L60:
            r9 = r1
        L61:
            if (r9 == 0) goto L6c
            boolean r0 = r9.isClosed()
            if (r0 != 0) goto L6c
        L69:
            r9.close()
        L6c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.openinstall.sdk.ap.b(java.lang.String):java.lang.String");
    }

    @Override // io.openinstall.sdk.ao
    public boolean b(String str, String str2) {
        if (a(str) != null) {
            return true;
        }
        ContentResolver contentResolver = this.a.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        ContentValues contentValues = new ContentValues();
        contentValues.put("relative_path", "Notifications/Installation");
        contentValues.put("_display_name", c(str));
        contentValues.put("title", fv.c(str2));
        try {
            return contentResolver.insert(uri, contentValues) != null;
        } catch (Exception unused) {
            return false;
        }
    }
}

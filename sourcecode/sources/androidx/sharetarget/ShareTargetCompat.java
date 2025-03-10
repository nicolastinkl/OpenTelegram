package androidx.sharetarget;

/* loaded from: classes.dex */
class ShareTargetCompat {
    final String[] mCategories;
    final String mTargetClass;
    final TargetData[] mTargetData;

    static class TargetData {
        final String mMimeType;

        TargetData(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
            this.mMimeType = str7;
        }
    }

    ShareTargetCompat(TargetData[] targetDataArr, String str, String[] strArr) {
        this.mTargetData = targetDataArr;
        this.mTargetClass = str;
        this.mCategories = strArr;
    }
}

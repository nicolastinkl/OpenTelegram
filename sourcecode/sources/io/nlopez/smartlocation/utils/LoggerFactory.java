package io.nlopez.smartlocation.utils;

import android.util.Log;

/* loaded from: classes.dex */
public class LoggerFactory {
    public static Logger buildLogger(boolean z) {
        return z ? new Blabber() : new Sssht();
    }

    private static class Sssht implements Logger {
        @Override // io.nlopez.smartlocation.utils.Logger
        public void d(String str, Object... objArr) {
        }

        @Override // io.nlopez.smartlocation.utils.Logger
        public void e(String str, Object... objArr) {
        }

        @Override // io.nlopez.smartlocation.utils.Logger
        public void e(Throwable th, String str, Object... objArr) {
        }

        @Override // io.nlopez.smartlocation.utils.Logger
        public void i(String str, Object... objArr) {
        }

        @Override // io.nlopez.smartlocation.utils.Logger
        public void w(String str, Object... objArr) {
        }

        private Sssht() {
        }
    }

    private static class Blabber implements Logger {
        private Blabber() {
        }

        private String getTag() {
            return new Exception().getStackTrace()[3].getMethodName();
        }

        private String formatMessage(String str, Object... objArr) {
            return objArr.length == 0 ? str : String.format(str, objArr);
        }

        @Override // io.nlopez.smartlocation.utils.Logger
        public void d(String str, Object... objArr) {
            Log.d(getTag(), formatMessage(str, objArr));
        }

        @Override // io.nlopez.smartlocation.utils.Logger
        public void i(String str, Object... objArr) {
            Log.i(getTag(), formatMessage(str, objArr));
        }

        @Override // io.nlopez.smartlocation.utils.Logger
        public void w(String str, Object... objArr) {
            Log.w(getTag(), formatMessage(str, objArr));
        }

        @Override // io.nlopez.smartlocation.utils.Logger
        public void e(String str, Object... objArr) {
            Log.e(getTag(), formatMessage(str, objArr));
        }

        @Override // io.nlopez.smartlocation.utils.Logger
        public void e(Throwable th, String str, Object... objArr) {
            Log.e(getTag(), formatMessage(str, objArr), th);
        }
    }
}

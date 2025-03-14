package org.telegram.messenger;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import org.telegram.messenger.LanguageDetector;

/* loaded from: classes3.dex */
public class LanguageDetector {

    public interface ExceptionCallback {
        void run(Exception exc);
    }

    public interface StringCallback {
        void run(String str);
    }

    public static boolean hasSupport() {
        return true;
    }

    public static void detectLanguage(String str, StringCallback stringCallback, ExceptionCallback exceptionCallback) {
        detectLanguage(str, stringCallback, exceptionCallback, false);
    }

    public static void detectLanguage(String str, final StringCallback stringCallback, final ExceptionCallback exceptionCallback, boolean z) {
        if (z) {
            try {
                MlKitContext.zza(ApplicationLoader.applicationContext);
            } catch (IllegalStateException e) {
                if (!z) {
                    detectLanguage(str, stringCallback, exceptionCallback, true);
                    return;
                }
                if (exceptionCallback != null) {
                    exceptionCallback.run(e);
                }
                FileLog.e((Throwable) e, false);
                return;
            } catch (Exception e2) {
                if (exceptionCallback != null) {
                    exceptionCallback.run(e2);
                }
                FileLog.e(e2);
                return;
            } catch (Throwable th) {
                if (exceptionCallback != null) {
                    exceptionCallback.run(null);
                }
                FileLog.e(th, false);
                return;
            }
        }
        LanguageIdentification.getClient().identifyLanguage(str).addOnSuccessListener(new OnSuccessListener() { // from class: org.telegram.messenger.LanguageDetector$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                LanguageDetector.lambda$detectLanguage$0(LanguageDetector.StringCallback.this, (String) obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: org.telegram.messenger.LanguageDetector$$ExternalSyntheticLambda0
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                LanguageDetector.lambda$detectLanguage$1(LanguageDetector.ExceptionCallback.this, exc);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$detectLanguage$0(StringCallback stringCallback, String str) {
        if (stringCallback != null) {
            stringCallback.run(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$detectLanguage$1(ExceptionCallback exceptionCallback, Exception exc) {
        if (exceptionCallback != null) {
            exceptionCallback.run(exc);
        }
    }
}

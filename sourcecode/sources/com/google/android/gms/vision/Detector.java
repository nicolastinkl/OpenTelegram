package com.google.android.gms.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
/* loaded from: classes.dex */
public abstract class Detector<T> {
    private final Object zza = new Object();
    private Processor<T> zzb;

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public interface Processor<T> {
        void release();
    }

    public void release() {
        synchronized (this.zza) {
            Processor<T> processor = this.zzb;
            if (processor != null) {
                processor.release();
                this.zzb = null;
            }
        }
    }
}

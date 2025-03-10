package com.google.android.gms.internal.mlkit_language_id;

import android.content.Context;
import android.content.res.Resources;
import android.os.SystemClock;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;
import com.google.android.gms.common.internal.LibraryVersion;
import com.google.android.gms.internal.mlkit_language_id.zzy$zzad;
import com.google.android.gms.internal.mlkit_language_id.zzy$zzbh;
import com.google.android.gms.tasks.Task;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.Dependency;
import com.google.mlkit.common.sdkinternal.CommonUtils;
import com.google.mlkit.common.sdkinternal.MLTaskExecutor;
import com.google.mlkit.common.sdkinternal.SharedPrefManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.mlkit:language-id@@16.1.1 */
/* loaded from: classes.dex */
public final class zzcv {
    public static final Component<?> zza = Component.builder(zzcv.class).add(Dependency.required(Context.class)).add(Dependency.required(SharedPrefManager.class)).add(Dependency.required(zzb.class)).factory(zzcy.zza).build();
    private static List<String> zzb = null;
    private static boolean zzl = true;
    private final String zzc;
    private final String zzd;
    private final zzb zze;
    private final SharedPrefManager zzf;
    private final Task<String> zzg;
    private final Task<String> zzh;
    private final Map<zzaj, Long> zzi = new HashMap();

    /* compiled from: com.google.mlkit:language-id@@16.1.1 */
    public interface zza {
        zzy$zzad.zza zza();
    }

    /* compiled from: com.google.mlkit:language-id@@16.1.1 */
    public interface zzb {
        void zza(zzy$zzad zzy_zzad);
    }

    private zzcv(Context context, SharedPrefManager sharedPrefManager, zzb zzbVar) {
        new HashMap();
        this.zzc = context.getPackageName();
        this.zzd = CommonUtils.getAppVersion(context);
        this.zzf = sharedPrefManager;
        this.zze = zzbVar;
        this.zzg = MLTaskExecutor.getInstance().scheduleCallable(zzcu.zza);
        MLTaskExecutor mLTaskExecutor = MLTaskExecutor.getInstance();
        sharedPrefManager.getClass();
        this.zzh = mLTaskExecutor.scheduleCallable(zzcx.zza(sharedPrefManager));
    }

    public final void zza(final zzy$zzad.zza zzaVar, final zzaj zzajVar) {
        MLTaskExecutor.workerThreadExecutor().execute(new Runnable(this, zzaVar, zzajVar) { // from class: com.google.android.gms.internal.mlkit_language_id.zzcw
            private final zzcv zza;
            private final zzy$zzad.zza zzb;
            private final zzaj zzc;

            {
                this.zza = this;
                this.zzb = zzaVar;
                this.zzc = zzajVar;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzb(this.zzb, this.zzc);
            }
        });
    }

    public final void zza(zza zzaVar, zzaj zzajVar) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        boolean z = true;
        if (this.zzi.get(zzajVar) != null && elapsedRealtime - this.zzi.get(zzajVar).longValue() <= TimeUnit.SECONDS.toMillis(30L)) {
            z = false;
        }
        if (z) {
            this.zzi.put(zzajVar, Long.valueOf(elapsedRealtime));
            zza(zzaVar.zza(), zzajVar);
        }
    }

    private static synchronized List<String> zzb() {
        synchronized (zzcv.class) {
            List<String> list = zzb;
            if (list != null) {
                return list;
            }
            LocaleListCompat locales = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration());
            zzb = new ArrayList(locales.size());
            for (int i = 0; i < locales.size(); i++) {
                zzb.add(CommonUtils.languageTagFromLocale(locales.get(i)));
            }
            return zzb;
        }
    }

    final /* synthetic */ void zzb(zzy$zzad.zza zzaVar, zzaj zzajVar) {
        String version;
        String mlSdkInstanceId;
        String zza2 = zzaVar.zza().zza();
        if ("NA".equals(zza2) || "".equals(zza2)) {
            zza2 = "NA";
        }
        zzy$zzbh.zza zzb2 = zzy$zzbh.zzb().zza(this.zzc).zzb(this.zzd).zzd(zza2).zza(zzb()).zzb(true);
        if (this.zzg.isSuccessful()) {
            version = this.zzg.getResult();
        } else {
            version = LibraryVersion.getInstance().getVersion("language-id");
        }
        zzy$zzbh.zza zzc = zzb2.zzc(version);
        if (zzl) {
            if (this.zzh.isSuccessful()) {
                mlSdkInstanceId = this.zzh.getResult();
            } else {
                mlSdkInstanceId = this.zzf.getMlSdkInstanceId();
            }
            zzc.zze(mlSdkInstanceId);
        }
        zzaVar.zza(zzajVar).zza(zzc);
        this.zze.zza((zzy$zzad) ((zzeo) zzaVar.zzg()));
    }

    static final /* synthetic */ zzcv zza(ComponentContainer componentContainer) {
        return new zzcv((Context) componentContainer.get(Context.class), (SharedPrefManager) componentContainer.get(SharedPrefManager.class), (zzb) componentContainer.get(zzb.class));
    }
}

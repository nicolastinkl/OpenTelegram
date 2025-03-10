package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: com.google.android.gms:play-services-basement@@18.1.0 */
/* loaded from: classes.dex */
public abstract class BaseGmsClient<T extends IInterface> {
    private static final Feature[] zze = new Feature[0];
    private volatile String zzA;
    zzu zza;
    final Handler zzb;
    protected ConnectionProgressReportCallbacks zzc;
    private int zzf;
    private long zzg;
    private long zzh;
    private int zzi;
    private long zzj;
    private final Context zzl;
    private final GmsClientSupervisor zzn;
    private IGmsServiceBroker zzr;
    private IInterface zzs;
    private zze zzu;
    private final BaseConnectionCallbacks zzw;
    private final BaseOnConnectionFailedListener zzx;
    private final int zzy;
    private final String zzz;
    private volatile String zzk = null;
    private final Object zzp = new Object();
    private final Object zzq = new Object();
    private final ArrayList zzt = new ArrayList();
    private int zzv = 1;
    private ConnectionResult zzB = null;
    private boolean zzC = false;
    private volatile zzj zzD = null;
    protected AtomicInteger zzd = new AtomicInteger(0);

    /* compiled from: com.google.android.gms:play-services-basement@@18.1.0 */
    public interface BaseConnectionCallbacks {
        void onConnected(Bundle bundle);

        void onConnectionSuspended(int i);
    }

    /* compiled from: com.google.android.gms:play-services-basement@@18.1.0 */
    public interface BaseOnConnectionFailedListener {
        void onConnectionFailed(ConnectionResult connectionResult);
    }

    /* compiled from: com.google.android.gms:play-services-basement@@18.1.0 */
    public interface ConnectionProgressReportCallbacks {
        void onReportServiceBinding(ConnectionResult connectionResult);
    }

    /* compiled from: com.google.android.gms:play-services-basement@@18.1.0 */
    protected class LegacyClientCallbackAdapter implements ConnectionProgressReportCallbacks {
        public LegacyClientCallbackAdapter() {
        }

        @Override // com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks
        public final void onReportServiceBinding(ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                BaseGmsClient baseGmsClient = BaseGmsClient.this;
                baseGmsClient.getRemoteService(null, baseGmsClient.getScopes());
            } else if (BaseGmsClient.this.zzx != null) {
                BaseGmsClient.this.zzx.onConnectionFailed(connectionResult);
            }
        }
    }

    /* compiled from: com.google.android.gms:play-services-basement@@18.1.0 */
    public interface SignOutCallbacks {
        void onSignOutComplete();
    }

    static /* bridge */ /* synthetic */ void zzj(BaseGmsClient baseGmsClient, zzj zzjVar) {
        baseGmsClient.zzD = zzjVar;
        if (baseGmsClient.usesClientTelemetry()) {
            ConnectionTelemetryConfiguration connectionTelemetryConfiguration = zzjVar.zzd;
            RootTelemetryConfigManager.getInstance().zza(connectionTelemetryConfiguration == null ? null : connectionTelemetryConfiguration.zza());
        }
    }

    static /* bridge */ /* synthetic */ void zzk(BaseGmsClient baseGmsClient, int i) {
        int i2;
        int i3;
        synchronized (baseGmsClient.zzp) {
            i2 = baseGmsClient.zzv;
        }
        if (i2 == 3) {
            baseGmsClient.zzC = true;
            i3 = 5;
        } else {
            i3 = 4;
        }
        Handler handler = baseGmsClient.zzb;
        handler.sendMessage(handler.obtainMessage(i3, baseGmsClient.zzd.get(), 16));
    }

    static /* bridge */ /* synthetic */ boolean zzn(BaseGmsClient baseGmsClient, int i, int i2, IInterface iInterface) {
        synchronized (baseGmsClient.zzp) {
            if (baseGmsClient.zzv != i) {
                return false;
            }
            baseGmsClient.zzp(i2, iInterface);
            return true;
        }
    }

    static /* bridge */ /* synthetic */ boolean zzo(BaseGmsClient baseGmsClient) {
        if (baseGmsClient.zzC || TextUtils.isEmpty(baseGmsClient.getServiceDescriptor()) || TextUtils.isEmpty(baseGmsClient.getLocalStartServiceAction())) {
            return false;
        }
        try {
            Class.forName(baseGmsClient.getServiceDescriptor());
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void zzp(int i, IInterface iInterface) {
        zzu zzuVar;
        Preconditions.checkArgument((i == 4) == (iInterface != 0));
        synchronized (this.zzp) {
            this.zzv = i;
            this.zzs = iInterface;
            if (i == 1) {
                zze zzeVar = this.zzu;
                if (zzeVar != null) {
                    GmsClientSupervisor gmsClientSupervisor = this.zzn;
                    String zzc = this.zza.zzc();
                    Preconditions.checkNotNull(zzc);
                    gmsClientSupervisor.zzb(zzc, this.zza.zzb(), this.zza.zza(), zzeVar, zze(), this.zza.zzd());
                    this.zzu = null;
                }
            } else if (i == 2 || i == 3) {
                zze zzeVar2 = this.zzu;
                if (zzeVar2 != null && (zzuVar = this.zza) != null) {
                    Log.e("GmsClient", "Calling connect() while still connected, missing disconnect() for " + zzuVar.zzc() + " on " + zzuVar.zzb());
                    GmsClientSupervisor gmsClientSupervisor2 = this.zzn;
                    String zzc2 = this.zza.zzc();
                    Preconditions.checkNotNull(zzc2);
                    gmsClientSupervisor2.zzb(zzc2, this.zza.zzb(), this.zza.zza(), zzeVar2, zze(), this.zza.zzd());
                    this.zzd.incrementAndGet();
                }
                zze zzeVar3 = new zze(this, this.zzd.get());
                this.zzu = zzeVar3;
                zzu zzuVar2 = (this.zzv != 3 || getLocalStartServiceAction() == null) ? new zzu(getStartServicePackage(), getStartServiceAction(), false, GmsClientSupervisor.getDefaultBindFlags(), getUseDynamicLookup()) : new zzu(getContext().getPackageName(), getLocalStartServiceAction(), true, GmsClientSupervisor.getDefaultBindFlags(), false);
                this.zza = zzuVar2;
                if (zzuVar2.zzd() && getMinApkVersion() < 17895000) {
                    throw new IllegalStateException("Internal Error, the minimum apk version of this BaseGmsClient is too low to support dynamic lookup. Start service action: ".concat(String.valueOf(this.zza.zzc())));
                }
                GmsClientSupervisor gmsClientSupervisor3 = this.zzn;
                String zzc3 = this.zza.zzc();
                Preconditions.checkNotNull(zzc3);
                if (!gmsClientSupervisor3.zzc(new zzn(zzc3, this.zza.zzb(), this.zza.zza(), this.zza.zzd()), zzeVar3, zze(), getBindServiceExecutor())) {
                    Log.w("GmsClient", "unable to connect to service: " + this.zza.zzc() + " on " + this.zza.zzb());
                    zzl(16, null, this.zzd.get());
                }
            } else if (i == 4) {
                Preconditions.checkNotNull(iInterface);
                onConnectedLocked(iInterface);
            }
        }
    }

    protected final void checkConnected() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    public void connect(ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        Preconditions.checkNotNull(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        this.zzc = connectionProgressReportCallbacks;
        zzp(2, null);
    }

    protected abstract T createServiceInterface(IBinder iBinder);

    public void disconnect() {
        this.zzd.incrementAndGet();
        synchronized (this.zzt) {
            int size = this.zzt.size();
            for (int i = 0; i < size; i++) {
                ((zzc) this.zzt.get(i)).zzf();
            }
            this.zzt.clear();
        }
        synchronized (this.zzq) {
            this.zzr = null;
        }
        zzp(1, null);
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i;
        IInterface iInterface;
        IGmsServiceBroker iGmsServiceBroker;
        synchronized (this.zzp) {
            i = this.zzv;
            iInterface = this.zzs;
        }
        synchronized (this.zzq) {
            iGmsServiceBroker = this.zzr;
        }
        printWriter.append((CharSequence) str).append("mConnectState=");
        if (i == 1) {
            printWriter.print("DISCONNECTED");
        } else if (i == 2) {
            printWriter.print("REMOTE_CONNECTING");
        } else if (i == 3) {
            printWriter.print("LOCAL_CONNECTING");
        } else if (i == 4) {
            printWriter.print("CONNECTED");
        } else if (i != 5) {
            printWriter.print("UNKNOWN");
        } else {
            printWriter.print("DISCONNECTING");
        }
        printWriter.append(" mService=");
        if (iInterface == null) {
            printWriter.append("null");
        } else {
            printWriter.append((CharSequence) getServiceDescriptor()).append("@").append((CharSequence) Integer.toHexString(System.identityHashCode(iInterface.asBinder())));
        }
        printWriter.append(" mServiceBroker=");
        if (iGmsServiceBroker == null) {
            printWriter.println("null");
        } else {
            printWriter.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(iGmsServiceBroker.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzh > 0) {
            PrintWriter append = printWriter.append((CharSequence) str).append("lastConnectedTime=");
            long j = this.zzh;
            append.println(j + " " + simpleDateFormat.format(new Date(j)));
        }
        if (this.zzg > 0) {
            printWriter.append((CharSequence) str).append("lastSuspendedCause=");
            int i2 = this.zzf;
            if (i2 == 1) {
                printWriter.append("CAUSE_SERVICE_DISCONNECTED");
            } else if (i2 == 2) {
                printWriter.append("CAUSE_NETWORK_LOST");
            } else if (i2 != 3) {
                printWriter.append((CharSequence) String.valueOf(i2));
            } else {
                printWriter.append("CAUSE_DEAD_OBJECT_EXCEPTION");
            }
            PrintWriter append2 = printWriter.append(" lastSuspendedTime=");
            long j2 = this.zzg;
            append2.println(j2 + " " + simpleDateFormat.format(new Date(j2)));
        }
        if (this.zzj > 0) {
            printWriter.append((CharSequence) str).append("lastFailedStatus=").append((CharSequence) CommonStatusCodes.getStatusCodeString(this.zzi));
            PrintWriter append3 = printWriter.append(" lastFailedTime=");
            long j3 = this.zzj;
            append3.println(j3 + " " + simpleDateFormat.format(new Date(j3)));
        }
    }

    protected boolean enableLocalFallback() {
        return false;
    }

    public Account getAccount() {
        return null;
    }

    public Feature[] getApiFeatures() {
        return zze;
    }

    public final Feature[] getAvailableFeatures() {
        zzj zzjVar = this.zzD;
        if (zzjVar == null) {
            return null;
        }
        return zzjVar.zzb;
    }

    protected Executor getBindServiceExecutor() {
        return null;
    }

    public Bundle getConnectionHint() {
        return null;
    }

    public final Context getContext() {
        return this.zzl;
    }

    public String getEndpointPackageName() {
        zzu zzuVar;
        if (!isConnected() || (zzuVar = this.zza) == null) {
            throw new RuntimeException("Failed to connect when checking package");
        }
        return zzuVar.zzb();
    }

    public int getGCoreServiceId() {
        return this.zzy;
    }

    protected Bundle getGetServiceRequestExtraArgs() {
        return new Bundle();
    }

    public String getLastDisconnectMessage() {
        return this.zzk;
    }

    protected String getLocalStartServiceAction() {
        return null;
    }

    public int getMinApkVersion() {
        return GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    public void getRemoteService(IAccountAccessor iAccountAccessor, Set<Scope> set) {
        Bundle getServiceRequestExtraArgs = getGetServiceRequestExtraArgs();
        int i = this.zzy;
        String str = this.zzA;
        int i2 = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        Scope[] scopeArr = GetServiceRequest.zza;
        Bundle bundle = new Bundle();
        Feature[] featureArr = GetServiceRequest.zzb;
        GetServiceRequest getServiceRequest = new GetServiceRequest(6, i, i2, null, null, scopeArr, bundle, null, featureArr, featureArr, true, 0, false, str);
        getServiceRequest.zzf = this.zzl.getPackageName();
        getServiceRequest.zzi = getServiceRequestExtraArgs;
        if (set != null) {
            getServiceRequest.zzh = (Scope[]) set.toArray(new Scope[0]);
        }
        if (requiresSignIn()) {
            Account account = getAccount();
            if (account == null) {
                account = new Account("<<default account>>", "com.google");
            }
            getServiceRequest.zzj = account;
            if (iAccountAccessor != null) {
                getServiceRequest.zzg = iAccountAccessor.asBinder();
            }
        } else if (requiresAccount()) {
            getServiceRequest.zzj = getAccount();
        }
        getServiceRequest.zzk = zze;
        getServiceRequest.zzl = getApiFeatures();
        if (usesClientTelemetry()) {
            getServiceRequest.zzo = true;
        }
        try {
            synchronized (this.zzq) {
                IGmsServiceBroker iGmsServiceBroker = this.zzr;
                if (iGmsServiceBroker != null) {
                    iGmsServiceBroker.getService(new zzd(this, this.zzd.get()), getServiceRequest);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (DeadObjectException e) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            triggerConnectionSuspended(3);
        } catch (RemoteException e2) {
            e = e2;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            onPostInitHandler(8, null, null, this.zzd.get());
        } catch (SecurityException e3) {
            throw e3;
        } catch (RuntimeException e4) {
            e = e4;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            onPostInitHandler(8, null, null, this.zzd.get());
        }
    }

    protected Set<Scope> getScopes() {
        return Collections.emptySet();
    }

    public final T getService() throws DeadObjectException {
        T t;
        synchronized (this.zzp) {
            if (this.zzv == 5) {
                throw new DeadObjectException();
            }
            checkConnected();
            t = (T) this.zzs;
            Preconditions.checkNotNull(t, "Client is connected but service is null");
        }
        return t;
    }

    protected abstract String getServiceDescriptor();

    public Intent getSignInIntent() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    protected abstract String getStartServiceAction();

    protected String getStartServicePackage() {
        return "com.google.android.gms";
    }

    public ConnectionTelemetryConfiguration getTelemetryConfiguration() {
        zzj zzjVar = this.zzD;
        if (zzjVar == null) {
            return null;
        }
        return zzjVar.zzd;
    }

    protected boolean getUseDynamicLookup() {
        return getMinApkVersion() >= 211700000;
    }

    public boolean hasConnectionInfo() {
        return this.zzD != null;
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.zzp) {
            z = this.zzv == 4;
        }
        return z;
    }

    public boolean isConnecting() {
        boolean z;
        synchronized (this.zzp) {
            int i = this.zzv;
            z = true;
            if (i != 2 && i != 3) {
                z = false;
            }
        }
        return z;
    }

    protected void onConnectedLocked(T t) {
        this.zzh = System.currentTimeMillis();
    }

    protected void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzi = connectionResult.getErrorCode();
        this.zzj = System.currentTimeMillis();
    }

    protected void onConnectionSuspended(int i) {
        this.zzf = i;
        this.zzg = System.currentTimeMillis();
    }

    protected void onPostInitHandler(int i, IBinder iBinder, Bundle bundle, int i2) {
        Handler handler = this.zzb;
        handler.sendMessage(handler.obtainMessage(1, i2, -1, new zzf(this, i, iBinder, bundle)));
    }

    public void onUserSignOut(SignOutCallbacks signOutCallbacks) {
        signOutCallbacks.onSignOutComplete();
    }

    public boolean providesSignIn() {
        return false;
    }

    public boolean requiresAccount() {
        return false;
    }

    public boolean requiresGooglePlayServices() {
        return true;
    }

    public boolean requiresSignIn() {
        return false;
    }

    public void setAttributionTag(String str) {
        this.zzA = str;
    }

    public void triggerConnectionSuspended(int i) {
        Handler handler = this.zzb;
        handler.sendMessage(handler.obtainMessage(6, this.zzd.get(), i));
    }

    public boolean usesClientTelemetry() {
        return false;
    }

    protected final String zze() {
        String str = this.zzz;
        return str == null ? this.zzl.getClass().getName() : str;
    }

    protected final void zzl(int i, Bundle bundle, int i2) {
        Handler handler = this.zzb;
        handler.sendMessage(handler.obtainMessage(7, i2, -1, new zzg(this, i, null)));
    }

    public void disconnect(String str) {
        this.zzk = str;
        disconnect();
    }

    protected BaseGmsClient(Context context, Looper looper, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailabilityLight googleApiAvailabilityLight, int i, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener, String str) {
        Preconditions.checkNotNull(context, "Context must not be null");
        this.zzl = context;
        Preconditions.checkNotNull(looper, "Looper must not be null");
        Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzn = gmsClientSupervisor;
        Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.zzb = new zzb(this, looper);
        this.zzy = i;
        this.zzw = baseConnectionCallbacks;
        this.zzx = baseOnConnectionFailedListener;
        this.zzz = str;
    }
}

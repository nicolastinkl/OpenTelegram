package com.android.billingclient.api;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.google.android.gms.internal.play_billing.zzb;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.json.JSONException;

/* compiled from: com.android.billingclient:billing@@5.1.0 */
/* loaded from: classes.dex */
class BillingClientImpl extends BillingClient {
    private volatile int zza;
    private final String zzb;
    private final Handler zzc;
    private volatile zzp zzd;
    private Context zze;
    private volatile com.google.android.gms.internal.play_billing.zze zzf;
    private volatile zzaq zzg;
    private boolean zzh;
    private boolean zzi;
    private int zzj;
    private boolean zzk;
    private boolean zzl;
    private boolean zzm;
    private boolean zzn;
    private boolean zzo;
    private boolean zzp;
    private boolean zzq;
    private boolean zzr;
    private boolean zzs;
    private boolean zzt;
    private boolean zzu;
    private ExecutorService zzv;

    private void initialize(Context context, PurchasesUpdatedListener purchasesUpdatedListener, boolean z, zzc zzcVar) {
        this.zze = context.getApplicationContext();
        if (purchasesUpdatedListener == null) {
            zzb.zzo("BillingClient", "Billing client should have a valid listener but the provided is null.");
        }
        this.zzd = new zzp(this.zze, purchasesUpdatedListener, zzcVar);
        this.zzt = z;
        this.zzu = zzcVar != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Handler zzF() {
        return Looper.myLooper() == null ? this.zzc : new Handler(Looper.myLooper());
    }

    private final BillingResult zzG(final BillingResult billingResult) {
        if (Thread.interrupted()) {
            return billingResult;
        }
        this.zzc.post(new Runnable() { // from class: com.android.billingclient.api.zzah
            @Override // java.lang.Runnable
            public final void run() {
                BillingClientImpl.this.zzE(billingResult);
            }
        });
        return billingResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BillingResult zzH() {
        return (this.zza == 0 || this.zza == 3) ? zzbc.zzm : zzbc.zzj;
    }

    @SuppressLint({"PrivateApi"})
    private static String zzI() {
        try {
            return (String) Class.forName("com.android.billingclient.ktx.BuildConfig").getField("VERSION_NAME").get(null);
        } catch (Exception unused) {
            return "5.1.0";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Future zzJ(Callable callable, long j, final Runnable runnable, Handler handler) {
        if (this.zzv == null) {
            this.zzv = Executors.newFixedThreadPool(zzb.zza, new zzam(this));
        }
        try {
            final Future submit = this.zzv.submit(callable);
            handler.postDelayed(new Runnable() { // from class: com.android.billingclient.api.zzag
                @Override // java.lang.Runnable
                public final void run() {
                    Future future = submit;
                    Runnable runnable2 = runnable;
                    if (future.isDone() || future.isCancelled()) {
                        return;
                    }
                    future.cancel(true);
                    zzb.zzo("BillingClient", "Async task is taking too long, cancel it!");
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            }, (long) (j * 0.95d));
            return submit;
        } catch (Exception e) {
            zzb.zzp("BillingClient", "Async task throws exception!", e);
            return null;
        }
    }

    private final void zzM(String str, final PurchasesResponseListener purchasesResponseListener) {
        if (!isReady()) {
            purchasesResponseListener.onQueryPurchasesResponse(zzbc.zzm, com.google.android.gms.internal.play_billing.zzu.zzl());
            return;
        }
        if (TextUtils.isEmpty(str)) {
            zzb.zzo("BillingClient", "Please provide a valid product type.");
            purchasesResponseListener.onQueryPurchasesResponse(zzbc.zzg, com.google.android.gms.internal.play_billing.zzu.zzl());
        } else if (zzJ(new zzaj(this, str, purchasesResponseListener), 30000L, new Runnable() { // from class: com.android.billingclient.api.zzae
            @Override // java.lang.Runnable
            public final void run() {
                PurchasesResponseListener.this.onQueryPurchasesResponse(zzbc.zzn, com.google.android.gms.internal.play_billing.zzu.zzl());
            }
        }, zzF()) == null) {
            purchasesResponseListener.onQueryPurchasesResponse(zzH(), com.google.android.gms.internal.play_billing.zzu.zzl());
        }
    }

    static /* bridge */ /* synthetic */ zzbj zzi(BillingClientImpl billingClientImpl, String str) {
        zzb.zzn("BillingClient", "Querying owned items, item type: ".concat(String.valueOf(str)));
        ArrayList arrayList = new ArrayList();
        Bundle zzh = zzb.zzh(billingClientImpl.zzm, billingClientImpl.zzt, billingClientImpl.zzb);
        String str2 = null;
        do {
            try {
                Bundle zzj = billingClientImpl.zzm ? billingClientImpl.zzf.zzj(9, billingClientImpl.zze.getPackageName(), str, str2, zzh) : billingClientImpl.zzf.zzi(3, billingClientImpl.zze.getPackageName(), str, str2);
                BillingResult zza = zzbk.zza(zzj, "BillingClient", "getPurchase()");
                if (zza != zzbc.zzl) {
                    return new zzbj(zza, null);
                }
                ArrayList<String> stringArrayList = zzj.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> stringArrayList2 = zzj.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> stringArrayList3 = zzj.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                for (int i = 0; i < stringArrayList2.size(); i++) {
                    String str3 = stringArrayList2.get(i);
                    String str4 = stringArrayList3.get(i);
                    zzb.zzn("BillingClient", "Sku is owned: ".concat(String.valueOf(stringArrayList.get(i))));
                    try {
                        Purchase purchase = new Purchase(str3, str4);
                        if (TextUtils.isEmpty(purchase.getPurchaseToken())) {
                            zzb.zzo("BillingClient", "BUG: empty/null token!");
                        }
                        arrayList.add(purchase);
                    } catch (JSONException e) {
                        zzb.zzp("BillingClient", "Got an exception trying to decode the purchase!", e);
                        return new zzbj(zzbc.zzj, null);
                    }
                }
                str2 = zzj.getString("INAPP_CONTINUATION_TOKEN");
                zzb.zzn("BillingClient", "Continuation token: ".concat(String.valueOf(str2)));
            } catch (Exception e2) {
                zzb.zzp("BillingClient", "Got exception trying to get purchasesm try to reconnect", e2);
                return new zzbj(zzbc.zzm, null);
            }
        } while (!TextUtils.isEmpty(str2));
        return new zzbj(zzbc.zzl, arrayList);
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void consumeAsync(final ConsumeParams consumeParams, final ConsumeResponseListener consumeResponseListener) {
        if (!isReady()) {
            consumeResponseListener.onConsumeResponse(zzbc.zzm, consumeParams.getPurchaseToken());
        } else if (zzJ(new Callable() { // from class: com.android.billingclient.api.zzv
            @Override // java.util.concurrent.Callable
            public final Object call() {
                BillingClientImpl.this.zzl(consumeParams, consumeResponseListener);
                return null;
            }
        }, 30000L, new Runnable() { // from class: com.android.billingclient.api.zzw
            @Override // java.lang.Runnable
            public final void run() {
                ConsumeResponseListener.this.onConsumeResponse(zzbc.zzn, consumeParams.getPurchaseToken());
            }
        }, zzF()) == null) {
            consumeResponseListener.onConsumeResponse(zzH(), consumeParams.getPurchaseToken());
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final boolean isReady() {
        return (this.zza != 2 || this.zzf == null || this.zzg == null) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x03a4 A[Catch: Exception -> 0x03be, CancellationException -> 0x03ca, TimeoutException -> 0x03cc, TRY_LEAVE, TryCatch #4 {CancellationException -> 0x03ca, TimeoutException -> 0x03cc, Exception -> 0x03be, blocks: (B:97:0x036c, B:99:0x037e, B:101:0x03a4), top: B:96:0x036c }] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0325 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0330  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0335  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x02ce  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0316  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x037e A[Catch: Exception -> 0x03be, CancellationException -> 0x03ca, TimeoutException -> 0x03cc, TryCatch #4 {CancellationException -> 0x03ca, TimeoutException -> 0x03cc, Exception -> 0x03be, blocks: (B:97:0x036c, B:99:0x037e, B:101:0x03a4), top: B:96:0x036c }] */
    @Override // com.android.billingclient.api.BillingClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.billingclient.api.BillingResult launchBillingFlow(android.app.Activity r32, final com.android.billingclient.api.BillingFlowParams r33) {
        /*
            Method dump skipped, instructions count: 984
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.BillingClientImpl.launchBillingFlow(android.app.Activity, com.android.billingclient.api.BillingFlowParams):com.android.billingclient.api.BillingResult");
    }

    @Override // com.android.billingclient.api.BillingClient
    public void queryProductDetailsAsync(final QueryProductDetailsParams queryProductDetailsParams, final ProductDetailsResponseListener productDetailsResponseListener) {
        if (!isReady()) {
            productDetailsResponseListener.onProductDetailsResponse(zzbc.zzm, new ArrayList());
            return;
        }
        if (!this.zzs) {
            zzb.zzo("BillingClient", "Querying product details is not supported.");
            productDetailsResponseListener.onProductDetailsResponse(zzbc.zzv, new ArrayList());
        } else if (zzJ(new Callable() { // from class: com.android.billingclient.api.zzt
            @Override // java.util.concurrent.Callable
            public final Object call() {
                BillingClientImpl.this.zzm(queryProductDetailsParams, productDetailsResponseListener);
                return null;
            }
        }, 30000L, new Runnable() { // from class: com.android.billingclient.api.zzu
            @Override // java.lang.Runnable
            public final void run() {
                ProductDetailsResponseListener.this.onProductDetailsResponse(zzbc.zzn, new ArrayList());
            }
        }, zzF()) == null) {
            productDetailsResponseListener.onProductDetailsResponse(zzH(), new ArrayList());
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public void queryPurchasesAsync(QueryPurchasesParams queryPurchasesParams, PurchasesResponseListener purchasesResponseListener) {
        zzM(queryPurchasesParams.zza(), purchasesResponseListener);
    }

    final /* synthetic */ void zzE(BillingResult billingResult) {
        if (this.zzd.zzc() != null) {
            this.zzd.zzc().onPurchasesUpdated(billingResult, null);
        } else {
            this.zzd.zzb();
            zzb.zzo("BillingClient", "No valid listener is set in BroadcastManager");
        }
    }

    final /* synthetic */ Bundle zzc(int i, String str, String str2, BillingFlowParams billingFlowParams, Bundle bundle) throws Exception {
        return this.zzf.zzg(i, this.zze.getPackageName(), str, str2, null, bundle);
    }

    final /* synthetic */ Bundle zzd(String str, String str2) throws Exception {
        return this.zzf.zzf(3, this.zze.getPackageName(), str, str2, null);
    }

    final /* synthetic */ Object zzl(ConsumeParams consumeParams, ConsumeResponseListener consumeResponseListener) throws Exception {
        int zza;
        String str;
        String purchaseToken = consumeParams.getPurchaseToken();
        try {
            zzb.zzn("BillingClient", "Consuming purchase with token: " + purchaseToken);
            if (this.zzm) {
                Bundle zze = this.zzf.zze(9, this.zze.getPackageName(), purchaseToken, zzb.zzd(consumeParams, this.zzm, this.zzb));
                zza = zze.getInt("RESPONSE_CODE");
                str = zzb.zzk(zze, "BillingClient");
            } else {
                zza = this.zzf.zza(3, this.zze.getPackageName(), purchaseToken);
                str = "";
            }
            BillingResult.Builder newBuilder = BillingResult.newBuilder();
            newBuilder.setResponseCode(zza);
            newBuilder.setDebugMessage(str);
            BillingResult build = newBuilder.build();
            if (zza == 0) {
                zzb.zzn("BillingClient", "Successfully consumed purchase.");
                consumeResponseListener.onConsumeResponse(build, purchaseToken);
                return null;
            }
            zzb.zzo("BillingClient", "Error consuming purchase with token. Response code: " + zza);
            consumeResponseListener.onConsumeResponse(build, purchaseToken);
            return null;
        } catch (Exception e) {
            zzb.zzp("BillingClient", "Error consuming purchase!", e);
            consumeResponseListener.onConsumeResponse(zzbc.zzm, purchaseToken);
            return null;
        }
    }

    final /* synthetic */ Object zzm(QueryProductDetailsParams queryProductDetailsParams, ProductDetailsResponseListener productDetailsResponseListener) throws Exception {
        String str;
        ArrayList arrayList = new ArrayList();
        String zzb = queryProductDetailsParams.zzb();
        com.google.android.gms.internal.play_billing.zzu zza = queryProductDetailsParams.zza();
        int size = zza.size();
        int i = 0;
        int i2 = 0;
        while (true) {
            str = "Item is unavailable for purchase.";
            if (i2 >= size) {
                str = "";
                break;
            }
            int i3 = i2 + 20;
            ArrayList arrayList2 = new ArrayList(zza.subList(i2, i3 > size ? size : i3));
            ArrayList<String> arrayList3 = new ArrayList<>();
            int size2 = arrayList2.size();
            for (int i4 = 0; i4 < size2; i4++) {
                arrayList3.add(((QueryProductDetailsParams.Product) arrayList2.get(i4)).zza());
            }
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("ITEM_ID_LIST", arrayList3);
            bundle.putString("playBillingLibraryVersion", this.zzb);
            try {
                Bundle zzl = this.zzf.zzl(17, this.zze.getPackageName(), zzb, bundle, zzb.zzg(this.zzb, arrayList2, null));
                if (zzl == null) {
                    zzb.zzo("BillingClient", "queryProductDetailsAsync got empty product details response.");
                    break;
                }
                if (zzl.containsKey("DETAILS_LIST")) {
                    ArrayList<String> stringArrayList = zzl.getStringArrayList("DETAILS_LIST");
                    if (stringArrayList == null) {
                        zzb.zzo("BillingClient", "queryProductDetailsAsync got null response list");
                        break;
                    }
                    for (int i5 = 0; i5 < stringArrayList.size(); i5++) {
                        try {
                            ProductDetails productDetails = new ProductDetails(stringArrayList.get(i5));
                            zzb.zzn("BillingClient", "Got product details: ".concat(productDetails.toString()));
                            arrayList.add(productDetails);
                        } catch (JSONException e) {
                            zzb.zzp("BillingClient", "Got a JSON exception trying to decode ProductDetails. \n Exception: ", e);
                            str = "Error trying to decode SkuDetails.";
                            i = 6;
                            BillingResult.Builder newBuilder = BillingResult.newBuilder();
                            newBuilder.setResponseCode(i);
                            newBuilder.setDebugMessage(str);
                            productDetailsResponseListener.onProductDetailsResponse(newBuilder.build(), arrayList);
                            return null;
                        }
                    }
                    i2 = i3;
                } else {
                    i = zzb.zzb(zzl, "BillingClient");
                    str = zzb.zzk(zzl, "BillingClient");
                    if (i != 0) {
                        zzb.zzo("BillingClient", "getSkuDetails() failed for queryProductDetailsAsync. Response code: " + i);
                    } else {
                        zzb.zzo("BillingClient", "getSkuDetails() returned a bundle with neither an error nor a product detail list for queryProductDetailsAsync.");
                    }
                }
            } catch (Exception e2) {
                zzb.zzp("BillingClient", "queryProductDetailsAsync got a remote exception (try to reconnect).", e2);
                str = "An internal error occurred.";
            }
        }
        i = 4;
        BillingResult.Builder newBuilder2 = BillingResult.newBuilder();
        newBuilder2.setResponseCode(i);
        newBuilder2.setDebugMessage(str);
        productDetailsResponseListener.onProductDetailsResponse(newBuilder2.build(), arrayList);
        return null;
    }

    private BillingClientImpl(Context context, boolean z, PurchasesUpdatedListener purchasesUpdatedListener, String str, String str2, zzc zzcVar) {
        this.zza = 0;
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzj = 0;
        this.zzb = str;
        initialize(context, purchasesUpdatedListener, z, null);
    }

    BillingClientImpl(String str, boolean z, Context context, zzbf zzbfVar) {
        this.zza = 0;
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzj = 0;
        this.zzb = zzI();
        this.zze = context.getApplicationContext();
        zzb.zzo("BillingClient", "Billing client should have a valid listener but the provided is null.");
        this.zzd = new zzp(this.zze, null);
        this.zzt = z;
    }

    BillingClientImpl(String str, boolean z, Context context, PurchasesUpdatedListener purchasesUpdatedListener, zzc zzcVar) {
        this(context, z, purchasesUpdatedListener, zzI(), null, null);
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void startConnection(BillingClientStateListener billingClientStateListener) {
        ServiceInfo serviceInfo;
        if (isReady()) {
            zzb.zzn("BillingClient", "Service connection is valid. No need to re-initialize.");
            billingClientStateListener.onBillingSetupFinished(zzbc.zzl);
            return;
        }
        if (this.zza == 1) {
            zzb.zzo("BillingClient", "Client is already in the process of connecting to billing service.");
            billingClientStateListener.onBillingSetupFinished(zzbc.zzd);
            return;
        }
        if (this.zza == 3) {
            zzb.zzo("BillingClient", "Client was already closed and can't be reused. Please create another instance.");
            billingClientStateListener.onBillingSetupFinished(zzbc.zzm);
            return;
        }
        this.zza = 1;
        this.zzd.zze();
        zzb.zzn("BillingClient", "Starting in-app billing setup.");
        this.zzg = new zzaq(this, billingClientStateListener, null);
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        List<ResolveInfo> queryIntentServices = this.zze.getPackageManager().queryIntentServices(intent, 0);
        if (queryIntentServices != null && !queryIntentServices.isEmpty() && (serviceInfo = queryIntentServices.get(0).serviceInfo) != null) {
            String str = serviceInfo.packageName;
            String str2 = serviceInfo.name;
            if (!"com.android.vending".equals(str) || str2 == null) {
                zzb.zzo("BillingClient", "The device doesn't have valid Play Store.");
            } else {
                ComponentName componentName = new ComponentName(str, str2);
                Intent intent2 = new Intent(intent);
                intent2.setComponent(componentName);
                intent2.putExtra("playBillingLibraryVersion", this.zzb);
                if (this.zze.bindService(intent2, this.zzg, 1)) {
                    zzb.zzn("BillingClient", "Service was bonded successfully.");
                    return;
                }
                zzb.zzo("BillingClient", "Connection to Billing service is blocked.");
            }
        }
        this.zza = 0;
        zzb.zzn("BillingClient", "Billing service unavailable on device.");
        billingClientStateListener.onBillingSetupFinished(zzbc.zzc);
    }
}

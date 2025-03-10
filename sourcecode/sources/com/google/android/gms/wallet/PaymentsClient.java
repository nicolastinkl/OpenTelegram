package com.google.android.gms.wallet;

import android.content.Context;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wallet.Wallet;

/* compiled from: com.google.android.gms:play-services-wallet@@19.1.0 */
/* loaded from: classes.dex */
public class PaymentsClient extends GoogleApi<Wallet.WalletOptions> {
    public Task<Boolean> isReadyToPay(final IsReadyToPayRequest isReadyToPayRequest) {
        return doRead(TaskApiCall.builder().setMethodKey(23705).run(new RemoteCall() { // from class: com.google.android.gms.wallet.zzag
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                ((com.google.android.gms.internal.wallet.zzac) obj).zzs(IsReadyToPayRequest.this, (TaskCompletionSource) obj2);
            }
        }).build());
    }

    public Task<PaymentData> loadPaymentData(final PaymentDataRequest paymentDataRequest) {
        return doWrite(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.wallet.zzai
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                ((com.google.android.gms.internal.wallet.zzac) obj).zzt(PaymentDataRequest.this, (TaskCompletionSource) obj2);
            }
        }).setFeatures(zzk.zzc).setAutoResolveMissingFeatures(true).setMethodKey(23707).build());
    }

    PaymentsClient(Context context, Wallet.WalletOptions walletOptions) {
        super(context, Wallet.API, walletOptions, GoogleApi.Settings.DEFAULT_SETTINGS);
    }
}

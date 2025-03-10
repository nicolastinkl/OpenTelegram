package org.telegram.messenger;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.util.Consumer;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.google.android.exoplayer2.util.Util;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$InputStorePaymentPurpose;
import org.telegram.tgnet.TLRPC$TL_dataJSON;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputStorePaymentGiftPremium;
import org.telegram.tgnet.TLRPC$TL_payments_assignPlayMarketTransaction;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.ui.PremiumPreviewFragment;

/* loaded from: classes3.dex */
public class BillingController implements PurchasesUpdatedListener, BillingClientStateListener {
    public static ProductDetails PREMIUM_PRODUCT_DETAILS = null;
    public static boolean billingClientEmpty;
    private static BillingController instance;
    private BillingClient billingClient;
    private String lastPremiumToken;
    private String lastPremiumTransaction;
    public static final String PREMIUM_PRODUCT_ID = "telegram_premium";
    public static final QueryProductDetailsParams.Product PREMIUM_PRODUCT = QueryProductDetailsParams.Product.newBuilder().setProductType("subs").setProductId(PREMIUM_PRODUCT_ID).build();
    private Map<String, Consumer<BillingResult>> resultListeners = new HashMap();
    private List<String> requestingTokens = new ArrayList();
    private Map<String, Integer> currencyExpMap = new HashMap();

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onPurchasesUpdated$3(BillingResult billingResult, String str) {
    }

    public static BillingController getInstance() {
        if (instance == null) {
            instance = new BillingController(ApplicationLoader.applicationContext);
        }
        return instance;
    }

    private BillingController(Context context) {
        this.billingClient = BillingClient.newBuilder(context).enablePendingPurchases().setListener(this).build();
    }

    public String getLastPremiumTransaction() {
        return this.lastPremiumTransaction;
    }

    public String getLastPremiumToken() {
        return this.lastPremiumToken;
    }

    public String formatCurrency(long j, String str) {
        return formatCurrency(j, str, getCurrencyExp(str));
    }

    public String formatCurrency(long j, String str, int i) {
        if (str.isEmpty()) {
            return String.valueOf(j);
        }
        Currency currency = Currency.getInstance(str);
        if (currency != null) {
            NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
            currencyInstance.setCurrency(currency);
            return currencyInstance.format(j / Math.pow(10.0d, i));
        }
        return j + " " + str;
    }

    public int getCurrencyExp(String str) {
        Integer num = this.currencyExpMap.get(str);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public void startConnection() {
        if (isReady()) {
            return;
        }
        try {
            InputStream open = ApplicationLoader.applicationContext.getAssets().open("currencies.json");
            parseCurrencies(new JSONObject(new String(Util.toByteArray(open), "UTF-8")));
            open.close();
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (BuildVars.useInvoiceBilling()) {
            return;
        }
        this.billingClient.startConnection(this);
    }

    private void switchToInvoice() {
        if (billingClientEmpty) {
            return;
        }
        billingClientEmpty = true;
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.billingProductDetailsUpdated, new Object[0]);
    }

    private void parseCurrencies(JSONObject jSONObject) {
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            this.currencyExpMap.put(next, Integer.valueOf(jSONObject.optJSONObject(next).optInt("exp")));
        }
    }

    public boolean isReady() {
        return this.billingClient.isReady();
    }

    public void queryProductDetails(List<QueryProductDetailsParams.Product> list, ProductDetailsResponseListener productDetailsResponseListener) {
        if (!isReady()) {
            throw new IllegalStateException("Billing controller should be ready for this call!");
        }
        this.billingClient.queryProductDetailsAsync(QueryProductDetailsParams.newBuilder().setProductList(list).build(), productDetailsResponseListener);
    }

    public void queryPurchases(String str, PurchasesResponseListener purchasesResponseListener) {
        this.billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType(str).build(), purchasesResponseListener);
    }

    public boolean startManageSubscription(Context context, String str) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(String.format("https://play.google.com/store/account/subscriptions?sku=%s&package=%s", str, context.getPackageName()))));
            return true;
        } catch (ActivityNotFoundException unused) {
            return false;
        }
    }

    public void addResultListener(String str, Consumer<BillingResult> consumer) {
        this.resultListeners.put(str, consumer);
    }

    public void launchBillingFlow(Activity activity, AccountInstance accountInstance, TLRPC$InputStorePaymentPurpose tLRPC$InputStorePaymentPurpose, List<BillingFlowParams.ProductDetailsParams> list) {
        launchBillingFlow(activity, accountInstance, tLRPC$InputStorePaymentPurpose, list, null, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void launchBillingFlow(final android.app.Activity r9, final org.telegram.messenger.AccountInstance r10, final org.telegram.tgnet.TLRPC$InputStorePaymentPurpose r11, final java.util.List<com.android.billingclient.api.BillingFlowParams.ProductDetailsParams> r12, final com.android.billingclient.api.BillingFlowParams.SubscriptionUpdateParams r13, boolean r14) {
        /*
            r8 = this;
            boolean r0 = r8.isReady()
            if (r0 == 0) goto L74
            if (r9 != 0) goto La
            goto L74
        La:
            boolean r0 = r11 instanceof org.telegram.tgnet.TLRPC$TL_inputStorePaymentGiftPremium
            if (r0 == 0) goto L22
            if (r14 != 0) goto L22
            org.telegram.messenger.BillingController$$ExternalSyntheticLambda4 r14 = new org.telegram.messenger.BillingController$$ExternalSyntheticLambda4
            r1 = r14
            r2 = r8
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r12
            r7 = r13
            r1.<init>()
            java.lang.String r9 = "inapp"
            r8.queryPurchases(r9, r14)
            return
        L22:
            com.android.billingclient.api.BillingFlowParams$Builder r14 = com.android.billingclient.api.BillingFlowParams.newBuilder()
            com.android.billingclient.api.BillingFlowParams$Builder r14 = r14.setProductDetailsParamsList(r12)
            if (r13 == 0) goto L2f
            r14.setSubscriptionUpdateParams(r13)
        L2f:
            r13 = 0
            com.android.billingclient.api.BillingClient r0 = r8.billingClient     // Catch: java.lang.Exception -> L42
            com.android.billingclient.api.BillingFlowParams r14 = r14.build()     // Catch: java.lang.Exception -> L42
            com.android.billingclient.api.BillingResult r9 = r0.launchBillingFlow(r9, r14)     // Catch: java.lang.Exception -> L42
            int r9 = r9.getResponseCode()     // Catch: java.lang.Exception -> L42
            if (r9 != 0) goto L42
            r9 = 1
            goto L43
        L42:
            r9 = 0
        L43:
            if (r9 == 0) goto L74
            java.util.Iterator r9 = r12.iterator()
        L49:
            boolean r12 = r9.hasNext()
            if (r12 == 0) goto L6d
            java.lang.Object r12 = r9.next()
            com.android.billingclient.api.BillingFlowParams$ProductDetailsParams r12 = (com.android.billingclient.api.BillingFlowParams.ProductDetailsParams) r12
            org.telegram.messenger.UserConfig r14 = r10.getUserConfig()
            r14.billingPaymentPurpose = r11
            org.telegram.messenger.UserConfig r14 = r10.getUserConfig()
            java.util.List<java.lang.String> r14 = r14.awaitBillingProductIds
            com.android.billingclient.api.ProductDetails r12 = r12.zza()
            java.lang.String r12 = r12.getProductId()
            r14.add(r12)
            goto L49
        L6d:
            org.telegram.messenger.UserConfig r9 = r10.getUserConfig()
            r9.saveConfig(r13)
        L74:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.BillingController.launchBillingFlow(android.app.Activity, org.telegram.messenger.AccountInstance, org.telegram.tgnet.TLRPC$InputStorePaymentPurpose, java.util.List, com.android.billingclient.api.BillingFlowParams$SubscriptionUpdateParams, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$launchBillingFlow$2(final Activity activity, final AccountInstance accountInstance, final TLRPC$InputStorePaymentPurpose tLRPC$InputStorePaymentPurpose, final List list, final BillingFlowParams.SubscriptionUpdateParams subscriptionUpdateParams, BillingResult billingResult, List list2) {
        if (billingResult.getResponseCode() == 0) {
            final Runnable runnable = new Runnable() { // from class: org.telegram.messenger.BillingController$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    BillingController.this.lambda$launchBillingFlow$0(activity, accountInstance, tLRPC$InputStorePaymentPurpose, list, subscriptionUpdateParams);
                }
            };
            final AtomicInteger atomicInteger = new AtomicInteger(0);
            final ArrayList arrayList = new ArrayList();
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                Purchase purchase = (Purchase) it.next();
                if (purchase.isAcknowledged()) {
                    Iterator it2 = list.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            final String productId = ((BillingFlowParams.ProductDetailsParams) it2.next()).zza().getProductId();
                            if (purchase.getProducts().contains(productId)) {
                                atomicInteger.incrementAndGet();
                                this.billingClient.consumeAsync(ConsumeParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build(), new ConsumeResponseListener() { // from class: org.telegram.messenger.BillingController$$ExternalSyntheticLambda0
                                    @Override // com.android.billingclient.api.ConsumeResponseListener
                                    public final void onConsumeResponse(BillingResult billingResult2, String str) {
                                        BillingController.lambda$launchBillingFlow$1(arrayList, productId, atomicInteger, runnable, billingResult2, str);
                                    }
                                });
                                break;
                            }
                        }
                    }
                } else {
                    onPurchasesUpdated(BillingResult.newBuilder().setResponseCode(0).build(), Collections.singletonList(purchase));
                    return;
                }
            }
            if (atomicInteger.get() == 0) {
                runnable.run();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$launchBillingFlow$0(Activity activity, AccountInstance accountInstance, TLRPC$InputStorePaymentPurpose tLRPC$InputStorePaymentPurpose, List list, BillingFlowParams.SubscriptionUpdateParams subscriptionUpdateParams) {
        launchBillingFlow(activity, accountInstance, tLRPC$InputStorePaymentPurpose, list, subscriptionUpdateParams, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$launchBillingFlow$1(List list, String str, AtomicInteger atomicInteger, Runnable runnable, BillingResult billingResult, String str2) {
        if (billingResult.getResponseCode() == 0) {
            list.add(str);
            if (atomicInteger.get() == list.size()) {
                runnable.run();
            }
        }
    }

    @Override // com.android.billingclient.api.PurchasesUpdatedListener
    public void onPurchasesUpdated(final BillingResult billingResult, List<Purchase> list) {
        FileLog.d("Billing purchases updated: " + billingResult + ", " + list);
        if (billingResult.getResponseCode() != 0) {
            if (billingResult.getResponseCode() == 1) {
                PremiumPreviewFragment.sentPremiumBuyCanceled();
            }
            for (int i = 0; i < 1; i++) {
                AccountInstance accountInstance = AccountInstance.getInstance(i);
                if (!accountInstance.getUserConfig().awaitBillingProductIds.isEmpty()) {
                    accountInstance.getUserConfig().awaitBillingProductIds.clear();
                    accountInstance.getUserConfig().billingPaymentPurpose = null;
                    accountInstance.getUserConfig().saveConfig(false);
                }
            }
            return;
        }
        if (list == null) {
            return;
        }
        this.lastPremiumTransaction = null;
        for (final Purchase purchase : list) {
            if (purchase.getProducts().contains(PREMIUM_PRODUCT_ID)) {
                this.lastPremiumTransaction = purchase.getOrderId();
                this.lastPremiumToken = purchase.getPurchaseToken();
            }
            if (!this.requestingTokens.contains(purchase.getPurchaseToken())) {
                for (int i2 = 0; i2 < 1; i2++) {
                    final AccountInstance accountInstance2 = AccountInstance.getInstance(i2);
                    if (accountInstance2.getUserConfig().awaitBillingProductIds.containsAll(purchase.getProducts()) && purchase.getPurchaseState() != 2) {
                        if (purchase.getPurchaseState() == 1) {
                            if (!purchase.isAcknowledged()) {
                                this.requestingTokens.add(purchase.getPurchaseToken());
                                final TLRPC$TL_payments_assignPlayMarketTransaction tLRPC$TL_payments_assignPlayMarketTransaction = new TLRPC$TL_payments_assignPlayMarketTransaction();
                                TLRPC$TL_dataJSON tLRPC$TL_dataJSON = new TLRPC$TL_dataJSON();
                                tLRPC$TL_payments_assignPlayMarketTransaction.receipt = tLRPC$TL_dataJSON;
                                tLRPC$TL_dataJSON.data = purchase.getOriginalJson();
                                tLRPC$TL_payments_assignPlayMarketTransaction.purpose = accountInstance2.getUserConfig().billingPaymentPurpose;
                                accountInstance2.getConnectionsManager().sendRequest(tLRPC$TL_payments_assignPlayMarketTransaction, new RequestDelegate() { // from class: org.telegram.messenger.BillingController$$ExternalSyntheticLambda7
                                    @Override // org.telegram.tgnet.RequestDelegate
                                    public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                        BillingController.this.lambda$onPurchasesUpdated$4(accountInstance2, purchase, billingResult, tLRPC$TL_payments_assignPlayMarketTransaction, tLObject, tLRPC$TL_error);
                                    }
                                }, 66);
                            } else {
                                accountInstance2.getUserConfig().awaitBillingProductIds.removeAll(purchase.getProducts());
                                accountInstance2.getUserConfig().saveConfig(false);
                            }
                        } else {
                            accountInstance2.getUserConfig().awaitBillingProductIds.removeAll(purchase.getProducts());
                            accountInstance2.getUserConfig().saveConfig(false);
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPurchasesUpdated$4(AccountInstance accountInstance, Purchase purchase, BillingResult billingResult, TLRPC$TL_payments_assignPlayMarketTransaction tLRPC$TL_payments_assignPlayMarketTransaction, TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        if (tLObject instanceof TLRPC$Updates) {
            accountInstance.getMessagesController().processUpdates((TLRPC$Updates) tLObject, false);
            this.requestingTokens.remove(purchase.getPurchaseToken());
            Iterator<String> it = purchase.getProducts().iterator();
            while (it.hasNext()) {
                Consumer<BillingResult> remove = this.resultListeners.remove(it.next());
                if (remove != null) {
                    remove.accept(billingResult);
                }
            }
            if (tLRPC$TL_payments_assignPlayMarketTransaction.purpose instanceof TLRPC$TL_inputStorePaymentGiftPremium) {
                this.billingClient.consumeAsync(ConsumeParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build(), new ConsumeResponseListener() { // from class: org.telegram.messenger.BillingController$$ExternalSyntheticLambda1
                    @Override // com.android.billingclient.api.ConsumeResponseListener
                    public final void onConsumeResponse(BillingResult billingResult2, String str) {
                        BillingController.lambda$onPurchasesUpdated$3(billingResult2, str);
                    }
                });
            }
        }
        if (tLObject == null && (!ApplicationLoader.isNetworkOnline() || tLRPC$TL_error == null || tLRPC$TL_error.code == -1000)) {
            return;
        }
        accountInstance.getUserConfig().awaitBillingProductIds.removeAll(purchase.getProducts());
        accountInstance.getUserConfig().saveConfig(false);
    }

    @Override // com.android.billingclient.api.BillingClientStateListener
    public void onBillingServiceDisconnected() {
        FileLog.d("Billing service disconnected");
    }

    @Override // com.android.billingclient.api.BillingClientStateListener
    public void onBillingSetupFinished(BillingResult billingResult) {
        FileLog.d("Billing setup finished with result " + billingResult);
        if (billingResult.getResponseCode() == 0) {
            queryProductDetails(Collections.singletonList(PREMIUM_PRODUCT), new ProductDetailsResponseListener() { // from class: org.telegram.messenger.BillingController$$ExternalSyntheticLambda2
                @Override // com.android.billingclient.api.ProductDetailsResponseListener
                public final void onProductDetailsResponse(BillingResult billingResult2, List list) {
                    BillingController.this.lambda$onBillingSetupFinished$6(billingResult2, list);
                }
            });
            queryPurchases("subs", new PurchasesResponseListener() { // from class: org.telegram.messenger.BillingController$$ExternalSyntheticLambda3
                @Override // com.android.billingclient.api.PurchasesResponseListener
                public final void onQueryPurchasesResponse(BillingResult billingResult2, List list) {
                    BillingController.this.onPurchasesUpdated(billingResult2, list);
                }
            });
        } else {
            switchToInvoice();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBillingSetupFinished$6(BillingResult billingResult, List list) {
        FileLog.d("Query product details finished " + billingResult + ", " + list);
        if (billingResult.getResponseCode() == 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ProductDetails productDetails = (ProductDetails) it.next();
                if (productDetails.getProductId().equals(PREMIUM_PRODUCT_ID)) {
                    PREMIUM_PRODUCT_DETAILS = productDetails;
                }
            }
            if (PREMIUM_PRODUCT_DETAILS == null) {
                switchToInvoice();
                return;
            } else {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.BillingController$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        BillingController.lambda$onBillingSetupFinished$5();
                    }
                });
                return;
            }
        }
        switchToInvoice();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onBillingSetupFinished$5() {
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.billingProductDetailsUpdated, new Object[0]);
    }
}

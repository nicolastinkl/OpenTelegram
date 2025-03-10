package org.telegram.messenger.browser;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.regex.Matcher;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.support.customtabs.CustomTabsCallback;
import org.telegram.messenger.support.customtabs.CustomTabsClient;
import org.telegram.messenger.support.customtabs.CustomTabsServiceConnection;
import org.telegram.messenger.support.customtabs.CustomTabsSession;
import org.telegram.messenger.support.customtabsclient.shared.CustomTabsHelper;
import org.telegram.messenger.support.customtabsclient.shared.ServiceConnection;
import org.telegram.messenger.support.customtabsclient.shared.ServiceConnectionCallback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.LaunchActivity;

/* loaded from: classes3.dex */
public class Browser {
    private static WeakReference<Activity> currentCustomTabsActivity;
    private static CustomTabsClient customTabsClient;
    private static String customTabsPackageToBind;
    private static CustomTabsServiceConnection customTabsServiceConnection;
    private static CustomTabsSession customTabsSession;

    private static void setCurrentSession(CustomTabsSession customTabsSession2) {
        new WeakReference(customTabsSession2);
    }

    private static CustomTabsSession getSession() {
        CustomTabsClient customTabsClient2 = customTabsClient;
        if (customTabsClient2 == null) {
            customTabsSession = null;
        } else if (customTabsSession == null) {
            CustomTabsSession newSession = customTabsClient2.newSession(new NavigationCallback());
            customTabsSession = newSession;
            setCurrentSession(newSession);
        }
        return customTabsSession;
    }

    public static void bindCustomTabsService(Activity activity) {
        WeakReference<Activity> weakReference = currentCustomTabsActivity;
        Activity activity2 = weakReference == null ? null : weakReference.get();
        if (activity2 != null && activity2 != activity) {
            unbindCustomTabsService(activity2);
        }
        if (customTabsClient != null) {
            return;
        }
        currentCustomTabsActivity = new WeakReference<>(activity);
        try {
            if (TextUtils.isEmpty(customTabsPackageToBind)) {
                String packageNameToUse = CustomTabsHelper.getPackageNameToUse(activity);
                customTabsPackageToBind = packageNameToUse;
                if (packageNameToUse == null) {
                    return;
                }
            }
            ServiceConnection serviceConnection = new ServiceConnection(new ServiceConnectionCallback() { // from class: org.telegram.messenger.browser.Browser.1
                @Override // org.telegram.messenger.support.customtabsclient.shared.ServiceConnectionCallback
                public void onServiceConnected(CustomTabsClient customTabsClient2) {
                    CustomTabsClient unused = Browser.customTabsClient = customTabsClient2;
                    if (!SharedConfig.customTabs || Browser.customTabsClient == null) {
                        return;
                    }
                    try {
                        Browser.customTabsClient.warmup(0L);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }

                @Override // org.telegram.messenger.support.customtabsclient.shared.ServiceConnectionCallback
                public void onServiceDisconnected() {
                    CustomTabsClient unused = Browser.customTabsClient = null;
                }
            });
            customTabsServiceConnection = serviceConnection;
            if (CustomTabsClient.bindCustomTabsService(activity, customTabsPackageToBind, serviceConnection)) {
                return;
            }
            customTabsServiceConnection = null;
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public static void unbindCustomTabsService(Activity activity) {
        if (customTabsServiceConnection == null) {
            return;
        }
        WeakReference<Activity> weakReference = currentCustomTabsActivity;
        if ((weakReference == null ? null : weakReference.get()) == activity) {
            currentCustomTabsActivity.clear();
        }
        try {
            activity.unbindService(customTabsServiceConnection);
        } catch (Exception unused) {
        }
        customTabsClient = null;
        customTabsSession = null;
    }

    private static class NavigationCallback extends CustomTabsCallback {
        @Override // org.telegram.messenger.support.customtabs.CustomTabsCallback
        public void onNavigationEvent(int i, Bundle bundle) {
        }

        private NavigationCallback() {
        }
    }

    public static void openUrl(Context context, String str) {
        if (str == null) {
            return;
        }
        openUrl(context, Uri.parse(str), true);
    }

    public static void openUrl(Context context, Uri uri) {
        openUrl(context, uri, true);
    }

    public static void openUrl(Context context, String str, boolean z) {
        if (context == null || str == null) {
            return;
        }
        openUrl(context, Uri.parse(str), z);
    }

    public static void openUrl(Context context, Uri uri, boolean z) {
        openUrl(context, uri, z, true);
    }

    public static void openUrl(Context context, String str, boolean z, boolean z2) {
        openUrl(context, Uri.parse(str), z, z2);
    }

    public static boolean isTelegraphUrl(String str, boolean z) {
        return isTelegraphUrl(str, z, false);
    }

    public static boolean isTelegraphUrl(String str, boolean z, boolean z2) {
        if (z) {
            return str.equals("telegra.ph") || str.equals("te.legra.ph") || str.equals("graph.org");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("^(https");
        sb.append(z2 ? "" : "?");
        sb.append("://)?(te\\.?legra\\.ph|graph\\.org)(/.*|$)");
        return str.matches(sb.toString());
    }

    public static String extractUsername(String str) {
        if (str != null && !TextUtils.isEmpty(str)) {
            if (str.startsWith("@")) {
                return str.substring(1);
            }
            if (str.startsWith("t.me/")) {
                return str.substring(5);
            }
            if (str.startsWith("http://t.me/")) {
                return str.substring(12);
            }
            if (str.startsWith("https://t.me/")) {
                return str.substring(13);
            }
            Matcher matcher = LaunchActivity.PREFIX_T_ME_PATTERN.matcher(str);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    public static boolean urlMustNotHaveConfirmation(String str) {
        return isTelegraphUrl(str, false, true) || str.matches("^(https://)?t\\.me/iv\\??(/.*|$)") || str.matches("^(https://)?telegram\\.org/(blog|tour)(/.*|$)") || str.matches("^(https://)?fragment\\.com(/.*|$)");
    }

    public static class Progress {
        private Runnable onCancelListener;

        public void end(boolean z) {
            throw null;
        }

        public void init() {
            throw null;
        }

        public void end() {
            end(false);
        }

        public void cancel() {
            cancel(false);
        }

        public void cancel(boolean z) {
            Runnable runnable = this.onCancelListener;
            if (runnable != null) {
                runnable.run();
            }
            end(z);
        }

        public void onCancel(Runnable runnable) {
            this.onCancelListener = runnable;
        }
    }

    public static void openUrl(Context context, Uri uri, boolean z, boolean z2) {
        openUrl(context, uri, z, z2, false, null);
    }

    public static void openUrl(Context context, Uri uri, boolean z, boolean z2, Progress progress) {
        openUrl(context, uri, z, z2, false, progress);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:39|(2:40|41)|(11:45|46|(5:50|(2:52|53)(1:55)|54|47|48)|56|57|(3:59|(4:62|(2:63|(1:1)(2:65|(3:68|69|70)(1:67)))|71|60)|73)(3:96|(4:99|(2:105|106)(1:103)|104|97)|107)|74|(3:76|(3:79|80|77)|81)|83|84|(2:89|(2:91|92)(2:94|95)))|112|56|57|(0)(0)|74|(0)|83|84|(0)|89|(0)(0)) */
    /* JADX WARN: Can't wrap try/catch for region: R(20:4|(3:142|143|(5:149|150|151|152|(2:154|155)(2:156|157)))|6|7|8|(1:10)(1:139)|11|(12:131|132|133|16|17|(9:19|(1:21)|22|23|(1:25)|26|(1:28)(1:32)|(1:30)|31)|(15:39|40|41|(11:45|46|(5:50|(2:52|53)(1:55)|54|47|48)|56|57|(3:59|(4:62|(2:63|(1:1)(2:65|(3:68|69|70)(1:67)))|71|60)|73)(3:96|(4:99|(2:105|106)(1:103)|104|97)|107)|74|(3:76|(3:79|80|77)|81)|83|84|(2:89|(2:91|92)(2:94|95)))|112|56|57|(0)(0)|74|(0)|83|84|(0)|89|(0)(0))|114|115|(1:117)|118|(2:124|125)(2:122|123))|15|16|17|(0)|(17:34|37|39|40|41|(14:43|45|46|(2:47|48)|56|57|(0)(0)|74|(0)|83|84|(0)|89|(0)(0))|112|56|57|(0)(0)|74|(0)|83|84|(0)|89|(0)(0))|114|115|(0)|118|(1:120)|124|125) */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x032f, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Removed duplicated region for block: B:117:0x033e A[Catch: Exception -> 0x0377, TryCatch #7 {Exception -> 0x0377, blocks: (B:115:0x0337, B:117:0x033e, B:118:0x0350, B:120:0x0361, B:122:0x0365, B:124:0x0373), top: B:114:0x0337 }] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0361 A[Catch: Exception -> 0x0377, TryCatch #7 {Exception -> 0x0377, blocks: (B:115:0x0337, B:117:0x033e, B:118:0x0350, B:120:0x0361, B:122:0x0365, B:124:0x0373), top: B:114:0x0337 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00da A[Catch: Exception -> 0x032f, TryCatch #1 {Exception -> 0x032f, blocks: (B:17:0x00be, B:19:0x00da, B:21:0x0109, B:22:0x010f, B:25:0x0118, B:26:0x0130, B:28:0x0138, B:30:0x0163, B:31:0x0175, B:32:0x014d, B:34:0x018c, B:37:0x0192, B:84:0x029c, B:87:0x02a2, B:89:0x02a8, B:91:0x02b4, B:94:0x02c4), top: B:16:0x00be }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01c5 A[Catch: Exception -> 0x01ef, TryCatch #0 {Exception -> 0x01ef, blocks: (B:48:0x01bf, B:50:0x01c5, B:52:0x01d5), top: B:47:0x01bf }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x02a0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x02b4 A[Catch: Exception -> 0x032f, TryCatch #1 {Exception -> 0x032f, blocks: (B:17:0x00be, B:19:0x00da, B:21:0x0109, B:22:0x010f, B:25:0x0118, B:26:0x0130, B:28:0x0138, B:30:0x0163, B:31:0x0175, B:32:0x014d, B:34:0x018c, B:37:0x0192, B:84:0x029c, B:87:0x02a2, B:89:0x02a8, B:91:0x02b4, B:94:0x02c4), top: B:16:0x00be }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x02c4 A[Catch: Exception -> 0x032f, TRY_LEAVE, TryCatch #1 {Exception -> 0x032f, blocks: (B:17:0x00be, B:19:0x00da, B:21:0x0109, B:22:0x010f, B:25:0x0118, B:26:0x0130, B:28:0x0138, B:30:0x0163, B:31:0x0175, B:32:0x014d, B:34:0x018c, B:37:0x0192, B:84:0x029c, B:87:0x02a2, B:89:0x02a8, B:91:0x02b4, B:94:0x02c4), top: B:16:0x00be }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0228  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void openUrl(final android.content.Context r18, final android.net.Uri r19, final boolean r20, boolean r21, boolean r22, final org.telegram.messenger.browser.Browser.Progress r23) {
        /*
            Method dump skipped, instructions count: 892
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.browser.Browser.openUrl(android.content.Context, android.net.Uri, boolean, boolean, boolean, org.telegram.messenger.browser.Browser$Progress):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$openUrl$1(final Progress progress, final AlertDialog[] alertDialogArr, final int i, final Uri uri, final Context context, final boolean z, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.browser.Browser$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                Browser.lambda$openUrl$0(Browser.Progress.this, alertDialogArr, tLObject, i, uri, context, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void lambda$openUrl$0(org.telegram.messenger.browser.Browser.Progress r2, org.telegram.ui.ActionBar.AlertDialog[] r3, org.telegram.tgnet.TLObject r4, int r5, android.net.Uri r6, android.content.Context r7, boolean r8) {
        /*
            r0 = 0
            if (r2 == 0) goto L7
            r2.end()
            goto Lf
        L7:
            r2 = r3[r0]     // Catch: java.lang.Throwable -> Lc
            r2.dismiss()     // Catch: java.lang.Throwable -> Lc
        Lc:
            r2 = 0
            r3[r0] = r2
        Lf:
            boolean r2 = r4 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaWebPage
            r3 = 1
            if (r2 == 0) goto L37
            org.telegram.tgnet.TLRPC$TL_messageMediaWebPage r4 = (org.telegram.tgnet.TLRPC$TL_messageMediaWebPage) r4
            org.telegram.tgnet.TLRPC$WebPage r2 = r4.webpage
            boolean r1 = r2 instanceof org.telegram.tgnet.TLRPC$TL_webPage
            if (r1 == 0) goto L37
            org.telegram.tgnet.TLRPC$Page r2 = r2.cached_page
            if (r2 == 0) goto L37
            org.telegram.messenger.NotificationCenter r2 = org.telegram.messenger.NotificationCenter.getInstance(r5)
            int r5 = org.telegram.messenger.NotificationCenter.openArticle
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]
            org.telegram.tgnet.TLRPC$WebPage r4 = r4.webpage
            r1[r0] = r4
            java.lang.String r4 = r6.toString()
            r1[r3] = r4
            r2.postNotificationName(r5, r1)
            goto L38
        L37:
            r3 = 0
        L38:
            if (r3 != 0) goto L3d
            openUrl(r7, r6, r8, r0)
        L3d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.browser.Browser.lambda$openUrl$0(org.telegram.messenger.browser.Browser$Progress, org.telegram.ui.ActionBar.AlertDialog[], org.telegram.tgnet.TLObject, int, android.net.Uri, android.content.Context, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$openUrl$3(AlertDialog[] alertDialogArr, final int i) {
        if (alertDialogArr[0] == null) {
            return;
        }
        try {
            alertDialogArr[0].setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.telegram.messenger.browser.Browser$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    Browser.lambda$openUrl$2(i, dialogInterface);
                }
            });
            alertDialogArr[0].show();
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$openUrl$2(int i, DialogInterface dialogInterface) {
        ConnectionsManager.getInstance(UserConfig.selectedAccount).cancelRequest(i, true);
    }

    public static boolean isInternalUrl(String str, boolean[] zArr) {
        return isInternalUri(Uri.parse(str), false, zArr);
    }

    public static boolean isInternalUrl(String str, boolean z, boolean[] zArr) {
        return isInternalUri(Uri.parse(str), z, zArr);
    }

    public static boolean isPassportUrl(String str) {
        String lowerCase;
        if (str == null) {
            return false;
        }
        try {
            lowerCase = str.toLowerCase();
        } catch (Throwable unused) {
        }
        if (lowerCase.startsWith("tg:passport") || lowerCase.startsWith("tg://passport") || lowerCase.startsWith("tg:secureid")) {
            return true;
        }
        if (lowerCase.contains("resolve")) {
            if (lowerCase.contains("domain=telegrampassport")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInternalUri(Uri uri, boolean[] zArr) {
        return isInternalUri(uri, false, zArr);
    }

    public static boolean isInternalUri(Uri uri, boolean z, boolean[] zArr) {
        String str;
        String str2;
        String hostAuthority = AndroidUtilities.getHostAuthority(uri);
        String lowerCase = hostAuthority != null ? hostAuthority.toLowerCase() : "";
        if (MessagesController.getInstance(UserConfig.selectedAccount).authDomains.contains(lowerCase)) {
            if (zArr != null) {
                zArr[0] = true;
            }
            return false;
        }
        Matcher matcher = LaunchActivity.PREFIX_T_ME_PATTERN.matcher(lowerCase);
        if (matcher.find()) {
            StringBuilder sb = new StringBuilder();
            sb.append("https://t.me/");
            sb.append(matcher.group(1));
            if (TextUtils.isEmpty(uri.getPath())) {
                str = "";
            } else {
                str = "/" + uri.getPath();
            }
            sb.append(str);
            if (TextUtils.isEmpty(uri.getQuery())) {
                str2 = "";
            } else {
                str2 = "?" + uri.getQuery();
            }
            sb.append(str2);
            uri = Uri.parse(sb.toString());
            String host = uri.getHost();
            lowerCase = host != null ? host.toLowerCase() : "";
        }
        if ("ton".equals(uri.getScheme())) {
            try {
                List<ResolveInfo> queryIntentActivities = ApplicationLoader.applicationContext.getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", uri), 0);
                if (queryIntentActivities != null) {
                    if (queryIntentActivities.size() >= 1) {
                        return false;
                    }
                }
            } catch (Exception unused) {
            }
            return true;
        }
        if ("tg".equals(uri.getScheme())) {
            return true;
        }
        if ("telegram.dog".equals(lowerCase)) {
            String path = uri.getPath();
            if (path != null && path.length() > 1) {
                if (z) {
                    return true;
                }
                String lowerCase2 = path.substring(1).toLowerCase();
                if (!lowerCase2.startsWith("blog") && !lowerCase2.equals("iv") && !lowerCase2.startsWith("faq") && !lowerCase2.equals("apps") && !lowerCase2.startsWith("s/")) {
                    return true;
                }
                if (zArr != null) {
                    zArr[0] = true;
                }
                return false;
            }
        } else if ("telegram.me".equals(lowerCase) || "t.me".equals(lowerCase)) {
            String path2 = uri.getPath();
            if (path2 != null && path2.length() > 1) {
                if (z) {
                    return true;
                }
                String lowerCase3 = path2.substring(1).toLowerCase();
                if (!lowerCase3.equals("iv") && !lowerCase3.startsWith("s/")) {
                    return true;
                }
                if (zArr != null) {
                    zArr[0] = true;
                }
            }
        } else if (z && (lowerCase.endsWith("telegram.org") || lowerCase.endsWith("telegra.ph") || lowerCase.endsWith("telesco.pe"))) {
            return true;
        }
        return false;
    }

    public static String replaceHostname(Uri uri, String str) {
        String scheme = uri.getScheme();
        String userInfo = uri.getUserInfo();
        int port = uri.getPort();
        String path = uri.getPath();
        String query = uri.getQuery();
        String fragment = uri.getFragment();
        StringBuilder sb = new StringBuilder();
        sb.append(scheme);
        sb.append("://");
        if (userInfo != null) {
            sb.append(userInfo);
            sb.append("@");
        }
        sb.append(str);
        if (port != -1) {
            sb.append(":");
            sb.append(port);
        }
        sb.append(path);
        if (query != null) {
            sb.append("?");
            sb.append(query);
        }
        if (fragment != null) {
            sb.append("#");
            sb.append(fragment);
        }
        return sb.toString();
    }
}

package org.telegram.messenger;

import android.annotation.TargetApi;
import android.content.AsyncQueryHandler;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import java.io.Closeable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.telegram.messenger.NotificationBadge;

/* loaded from: classes3.dex */
public class NotificationBadge {
    private static final List<Class<? extends Badger>> BADGERS;
    private static Badger badger;
    private static ComponentName componentName;
    private static boolean initied;

    public interface Badger {
        void executeBadge(int i);

        List<String> getSupportLaunchers();
    }

    static {
        LinkedList linkedList = new LinkedList();
        BADGERS = linkedList;
        linkedList.add(AdwHomeBadger.class);
        linkedList.add(ApexHomeBadger.class);
        linkedList.add(NewHtcHomeBadger.class);
        linkedList.add(NovaHomeBadger.class);
        linkedList.add(SonyHomeBadger.class);
        linkedList.add(XiaomiHomeBadger.class);
        linkedList.add(AsusHomeBadger.class);
        linkedList.add(HuaweiHomeBadger.class);
        linkedList.add(OPPOHomeBader.class);
        linkedList.add(SamsungHomeBadger.class);
        linkedList.add(ZukHomeBadger.class);
        linkedList.add(VivoHomeBadger.class);
    }

    public static class AdwHomeBadger implements Badger {
        public static final String CLASSNAME = "CNAME";
        public static final String COUNT = "COUNT";
        public static final String INTENT_UPDATE_COUNTER = "org.adw.launcher.counter.SEND";
        public static final String PACKAGENAME = "PNAME";

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public void executeBadge(int i) {
            final Intent intent = new Intent(INTENT_UPDATE_COUNTER);
            intent.putExtra(PACKAGENAME, NotificationBadge.componentName.getPackageName());
            intent.putExtra(CLASSNAME, NotificationBadge.componentName.getClassName());
            intent.putExtra(COUNT, i);
            if (NotificationBadge.canResolveBroadcast(intent)) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationBadge$AdwHomeBadger$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationBadge.AdwHomeBadger.lambda$executeBadge$0(intent);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$executeBadge$0(Intent intent) {
            ApplicationLoader.applicationContext.sendBroadcast(intent);
        }

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public List<String> getSupportLaunchers() {
            return Arrays.asList("org.adw.launcher", "org.adwfreak.launcher");
        }
    }

    public static class ApexHomeBadger implements Badger {
        private static final String CLASS = "class";
        private static final String COUNT = "count";
        private static final String INTENT_UPDATE_COUNTER = "com.anddoes.launcher.COUNTER_CHANGED";
        private static final String PACKAGENAME = "package";

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public void executeBadge(int i) {
            final Intent intent = new Intent(INTENT_UPDATE_COUNTER);
            intent.putExtra(PACKAGENAME, NotificationBadge.componentName.getPackageName());
            intent.putExtra("count", i);
            intent.putExtra(CLASS, NotificationBadge.componentName.getClassName());
            if (NotificationBadge.canResolveBroadcast(intent)) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationBadge$ApexHomeBadger$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationBadge.ApexHomeBadger.lambda$executeBadge$0(intent);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$executeBadge$0(Intent intent) {
            ApplicationLoader.applicationContext.sendBroadcast(intent);
        }

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public List<String> getSupportLaunchers() {
            return Arrays.asList("com.anddoes.launcher");
        }
    }

    public static class AsusHomeBadger implements Badger {
        private static final String INTENT_ACTION = "android.intent.action.BADGE_COUNT_UPDATE";
        private static final String INTENT_EXTRA_ACTIVITY_NAME = "badge_count_class_name";
        private static final String INTENT_EXTRA_BADGE_COUNT = "badge_count";
        private static final String INTENT_EXTRA_PACKAGENAME = "badge_count_package_name";

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public void executeBadge(int i) {
            final Intent intent = new Intent(INTENT_ACTION);
            intent.putExtra(INTENT_EXTRA_BADGE_COUNT, i);
            intent.putExtra(INTENT_EXTRA_PACKAGENAME, NotificationBadge.componentName.getPackageName());
            intent.putExtra(INTENT_EXTRA_ACTIVITY_NAME, NotificationBadge.componentName.getClassName());
            intent.putExtra("badge_vip_count", 0);
            if (NotificationBadge.canResolveBroadcast(intent)) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationBadge$AsusHomeBadger$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationBadge.AsusHomeBadger.lambda$executeBadge$0(intent);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$executeBadge$0(Intent intent) {
            ApplicationLoader.applicationContext.sendBroadcast(intent);
        }

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public List<String> getSupportLaunchers() {
            return Arrays.asList("com.asus.launcher");
        }
    }

    public static class DefaultBadger implements Badger {
        private static final String INTENT_ACTION = "android.intent.action.BADGE_COUNT_UPDATE";
        private static final String INTENT_EXTRA_ACTIVITY_NAME = "badge_count_class_name";
        private static final String INTENT_EXTRA_BADGE_COUNT = "badge_count";
        private static final String INTENT_EXTRA_PACKAGENAME = "badge_count_package_name";

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public void executeBadge(int i) {
            final Intent intent = new Intent(INTENT_ACTION);
            intent.putExtra(INTENT_EXTRA_BADGE_COUNT, i);
            intent.putExtra(INTENT_EXTRA_PACKAGENAME, NotificationBadge.componentName.getPackageName());
            intent.putExtra(INTENT_EXTRA_ACTIVITY_NAME, NotificationBadge.componentName.getClassName());
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationBadge$DefaultBadger$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationBadge.DefaultBadger.lambda$executeBadge$0(intent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$executeBadge$0(Intent intent) {
            try {
                ApplicationLoader.applicationContext.sendBroadcast(intent);
            } catch (Exception unused) {
            }
        }

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public List<String> getSupportLaunchers() {
            return Arrays.asList("fr.neamar.kiss", "com.quaap.launchtime", "com.quaap.launchtime_official");
        }
    }

    public static class HuaweiHomeBadger implements Badger {
        @Override // org.telegram.messenger.NotificationBadge.Badger
        public void executeBadge(int i) {
            final Bundle bundle = new Bundle();
            bundle.putString("package", ApplicationLoader.applicationContext.getPackageName());
            bundle.putString("class", NotificationBadge.componentName.getClassName());
            bundle.putInt("badgenumber", i);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationBadge$HuaweiHomeBadger$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationBadge.HuaweiHomeBadger.lambda$executeBadge$0(bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$executeBadge$0(Bundle bundle) {
            try {
                ApplicationLoader.applicationContext.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", (String) null, bundle);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public List<String> getSupportLaunchers() {
            return Arrays.asList("com.huawei.android.launcher");
        }
    }

    public static class NewHtcHomeBadger implements Badger {
        public static final String COUNT = "count";
        public static final String EXTRA_COMPONENT = "com.htc.launcher.extra.COMPONENT";
        public static final String EXTRA_COUNT = "com.htc.launcher.extra.COUNT";
        public static final String INTENT_SET_NOTIFICATION = "com.htc.launcher.action.SET_NOTIFICATION";
        public static final String INTENT_UPDATE_SHORTCUT = "com.htc.launcher.action.UPDATE_SHORTCUT";
        public static final String PACKAGENAME = "packagename";

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public void executeBadge(int i) {
            final Intent intent = new Intent(INTENT_SET_NOTIFICATION);
            intent.putExtra(EXTRA_COMPONENT, NotificationBadge.componentName.flattenToShortString());
            intent.putExtra(EXTRA_COUNT, i);
            final Intent intent2 = new Intent(INTENT_UPDATE_SHORTCUT);
            intent2.putExtra(PACKAGENAME, NotificationBadge.componentName.getPackageName());
            intent2.putExtra(COUNT, i);
            if (NotificationBadge.canResolveBroadcast(intent) || NotificationBadge.canResolveBroadcast(intent2)) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationBadge$NewHtcHomeBadger$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationBadge.NewHtcHomeBadger.lambda$executeBadge$0(intent, intent2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$executeBadge$0(Intent intent, Intent intent2) {
            ApplicationLoader.applicationContext.sendBroadcast(intent);
            ApplicationLoader.applicationContext.sendBroadcast(intent2);
        }

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public List<String> getSupportLaunchers() {
            return Arrays.asList("com.htc.launcher");
        }
    }

    public static class NovaHomeBadger implements Badger {
        private static final String CONTENT_URI = "content://com.teslacoilsw.notifier/unread_count";
        private static final String COUNT = "count";
        private static final String TAG = "tag";

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public void executeBadge(int i) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TAG, NotificationBadge.componentName.getPackageName() + "/" + NotificationBadge.componentName.getClassName());
            contentValues.put("count", Integer.valueOf(i));
            ApplicationLoader.applicationContext.getContentResolver().insert(Uri.parse(CONTENT_URI), contentValues);
        }

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public List<String> getSupportLaunchers() {
            return Arrays.asList("com.teslacoilsw.launcher");
        }
    }

    public static class OPPOHomeBader implements Badger {
        private static final String INTENT_ACTION = "com.oppo.unsettledevent";
        private static final String INTENT_EXTRA_BADGEUPGRADE_COUNT = "app_badge_count";
        private static final String INTENT_EXTRA_BADGE_COUNT = "number";
        private static final String INTENT_EXTRA_BADGE_UPGRADENUMBER = "upgradeNumber";
        private static final String INTENT_EXTRA_PACKAGENAME = "pakeageName";
        private static final String PROVIDER_CONTENT_URI = "content://com.android.badge/badge";
        private int mCurrentTotalCount = -1;

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public void executeBadge(int i) {
            if (this.mCurrentTotalCount == i) {
                return;
            }
            this.mCurrentTotalCount = i;
            executeBadgeByContentProvider(i);
        }

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public List<String> getSupportLaunchers() {
            return Collections.singletonList("com.oppo.launcher");
        }

        @TargetApi(11)
        private void executeBadgeByContentProvider(int i) {
            try {
                Bundle bundle = new Bundle();
                bundle.putInt(INTENT_EXTRA_BADGEUPGRADE_COUNT, i);
                ApplicationLoader.applicationContext.getContentResolver().call(Uri.parse(PROVIDER_CONTENT_URI), "setAppBadgeCount", (String) null, bundle);
            } catch (Throwable unused) {
            }
        }
    }

    public static class SamsungHomeBadger implements Badger {
        private static final String[] CONTENT_PROJECTION = {"_id", "class"};
        private static final String CONTENT_URI = "content://com.sec.badge/apps?notify=true";
        private static DefaultBadger defaultBadger;

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public void executeBadge(int i) {
            try {
                if (defaultBadger == null) {
                    defaultBadger = new DefaultBadger();
                }
                defaultBadger.executeBadge(i);
            } catch (Exception unused) {
            }
            Uri parse = Uri.parse(CONTENT_URI);
            ContentResolver contentResolver = ApplicationLoader.applicationContext.getContentResolver();
            Cursor cursor = null;
            try {
                cursor = contentResolver.query(parse, CONTENT_PROJECTION, "package=?", new String[]{NotificationBadge.componentName.getPackageName()}, null);
                if (cursor != null) {
                    String className = NotificationBadge.componentName.getClassName();
                    boolean z = false;
                    while (cursor.moveToNext()) {
                        contentResolver.update(parse, getContentValues(NotificationBadge.componentName, i, false), "_id=?", new String[]{String.valueOf(cursor.getInt(0))});
                        if (className.equals(cursor.getString(cursor.getColumnIndex("class")))) {
                            z = true;
                        }
                    }
                    if (!z) {
                        contentResolver.insert(parse, getContentValues(NotificationBadge.componentName, i, true));
                    }
                }
            } finally {
                NotificationBadge.close(cursor);
            }
        }

        private ContentValues getContentValues(ComponentName componentName, int i, boolean z) {
            ContentValues contentValues = new ContentValues();
            if (z) {
                contentValues.put("package", componentName.getPackageName());
                contentValues.put("class", componentName.getClassName());
            }
            contentValues.put("badgecount", Integer.valueOf(i));
            return contentValues;
        }

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public List<String> getSupportLaunchers() {
            return Arrays.asList("com.sec.android.app.launcher", "com.sec.android.app.twlauncher");
        }
    }

    public static class SonyHomeBadger implements Badger {
        private static final String INTENT_ACTION = "com.sonyericsson.home.action.UPDATE_BADGE";
        private static final String INTENT_EXTRA_ACTIVITY_NAME = "com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME";
        private static final String INTENT_EXTRA_MESSAGE = "com.sonyericsson.home.intent.extra.badge.MESSAGE";
        private static final String INTENT_EXTRA_PACKAGE_NAME = "com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME";
        private static final String INTENT_EXTRA_SHOW_MESSAGE = "com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE";
        private static final String PROVIDER_COLUMNS_ACTIVITY_NAME = "activity_name";
        private static final String PROVIDER_COLUMNS_BADGE_COUNT = "badge_count";
        private static final String PROVIDER_COLUMNS_PACKAGE_NAME = "package_name";
        private static final String PROVIDER_CONTENT_URI = "content://com.sonymobile.home.resourceprovider/badge";
        private static final String SONY_HOME_PROVIDER_NAME = "com.sonymobile.home.resourceprovider";
        private static AsyncQueryHandler mQueryHandler;
        private final Uri BADGE_CONTENT_URI = Uri.parse(PROVIDER_CONTENT_URI);

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public void executeBadge(int i) {
            if (sonyBadgeContentProviderExists()) {
                executeBadgeByContentProvider(i);
            } else {
                executeBadgeByBroadcast(i);
            }
        }

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public List<String> getSupportLaunchers() {
            return Arrays.asList("com.sonyericsson.home", "com.sonymobile.home");
        }

        private static void executeBadgeByBroadcast(int i) {
            final Intent intent = new Intent(INTENT_ACTION);
            intent.putExtra(INTENT_EXTRA_PACKAGE_NAME, NotificationBadge.componentName.getPackageName());
            intent.putExtra(INTENT_EXTRA_ACTIVITY_NAME, NotificationBadge.componentName.getClassName());
            intent.putExtra(INTENT_EXTRA_MESSAGE, String.valueOf(i));
            intent.putExtra(INTENT_EXTRA_SHOW_MESSAGE, i > 0);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationBadge$SonyHomeBadger$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationBadge.SonyHomeBadger.lambda$executeBadgeByBroadcast$0(intent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$executeBadgeByBroadcast$0(Intent intent) {
            ApplicationLoader.applicationContext.sendBroadcast(intent);
        }

        private void executeBadgeByContentProvider(int i) {
            if (i < 0) {
                return;
            }
            if (mQueryHandler == null) {
                mQueryHandler = new AsyncQueryHandler(ApplicationLoader.applicationContext.getApplicationContext().getContentResolver()) { // from class: org.telegram.messenger.NotificationBadge.SonyHomeBadger.1
                    @Override // android.content.AsyncQueryHandler, android.os.Handler
                    public void handleMessage(Message message) {
                        try {
                            super.handleMessage(message);
                        } catch (Throwable unused) {
                        }
                    }
                };
            }
            insertBadgeAsync(i, NotificationBadge.componentName.getPackageName(), NotificationBadge.componentName.getClassName());
        }

        private void insertBadgeAsync(int i, String str, String str2) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PROVIDER_COLUMNS_BADGE_COUNT, Integer.valueOf(i));
            contentValues.put(PROVIDER_COLUMNS_PACKAGE_NAME, str);
            contentValues.put(PROVIDER_COLUMNS_ACTIVITY_NAME, str2);
            mQueryHandler.startInsert(0, null, this.BADGE_CONTENT_URI, contentValues);
        }

        private static boolean sonyBadgeContentProviderExists() {
            return ApplicationLoader.applicationContext.getPackageManager().resolveContentProvider(SONY_HOME_PROVIDER_NAME, 0) != null;
        }
    }

    public static class XiaomiHomeBadger implements Badger {
        public static final String EXTRA_UPDATE_APP_COMPONENT_NAME = "android.intent.extra.update_application_component_name";
        public static final String EXTRA_UPDATE_APP_MSG_TEXT = "android.intent.extra.update_application_message_text";
        public static final String INTENT_ACTION = "android.intent.action.APPLICATION_MESSAGE_UPDATE";

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public void executeBadge(int i) {
            try {
                Object newInstance = Class.forName("android.app.MiuiNotification").newInstance();
                Field declaredField = newInstance.getClass().getDeclaredField("messageCount");
                declaredField.setAccessible(true);
                declaredField.set(newInstance, String.valueOf(i == 0 ? "" : Integer.valueOf(i)));
            } catch (Throwable unused) {
                final Intent intent = new Intent(INTENT_ACTION);
                intent.putExtra(EXTRA_UPDATE_APP_COMPONENT_NAME, NotificationBadge.componentName.getPackageName() + "/" + NotificationBadge.componentName.getClassName());
                intent.putExtra(EXTRA_UPDATE_APP_MSG_TEXT, String.valueOf(i != 0 ? Integer.valueOf(i) : ""));
                if (NotificationBadge.canResolveBroadcast(intent)) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationBadge.XiaomiHomeBadger.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ApplicationLoader.applicationContext.sendBroadcast(intent);
                        }
                    });
                }
            }
        }

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public List<String> getSupportLaunchers() {
            return Arrays.asList("com.miui.miuilite", "com.miui.home", "com.miui.miuihome", "com.miui.miuihome2", "com.miui.mihome", "com.miui.mihome2");
        }
    }

    public static class ZukHomeBadger implements Badger {
        private final Uri CONTENT_URI = Uri.parse("content://com.android.badge/badge");

        @Override // org.telegram.messenger.NotificationBadge.Badger
        @TargetApi(11)
        public void executeBadge(int i) {
            final Bundle bundle = new Bundle();
            bundle.putInt("app_badge_count", i);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationBadge$ZukHomeBadger$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationBadge.ZukHomeBadger.this.lambda$executeBadge$0(bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$executeBadge$0(Bundle bundle) {
            try {
                ApplicationLoader.applicationContext.getContentResolver().call(this.CONTENT_URI, "setAppBadgeCount", (String) null, bundle);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public List<String> getSupportLaunchers() {
            return Collections.singletonList("com.zui.launcher");
        }
    }

    public static class VivoHomeBadger implements Badger {
        @Override // org.telegram.messenger.NotificationBadge.Badger
        public void executeBadge(int i) {
            Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            intent.putExtra("packageName", ApplicationLoader.applicationContext.getPackageName());
            intent.putExtra("className", NotificationBadge.componentName.getClassName());
            intent.putExtra("notificationNum", i);
            ApplicationLoader.applicationContext.sendBroadcast(intent);
        }

        @Override // org.telegram.messenger.NotificationBadge.Badger
        public List<String> getSupportLaunchers() {
            return Arrays.asList("com.vivo.launcher");
        }
    }

    public static boolean applyCount(int i) {
        try {
            if (badger == null && !initied) {
                initBadger();
                initied = true;
            }
            Badger badger2 = badger;
            if (badger2 == null) {
                return false;
            }
            badger2.executeBadge(i);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static boolean initBadger() {
        Badger badger2;
        Badger badger3;
        Context context = ApplicationLoader.applicationContext;
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (launchIntentForPackage == null) {
            return false;
        }
        componentName = launchIntentForPackage.getComponent();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT);
        if (resolveActivity != null) {
            String str = resolveActivity.activityInfo.packageName;
            Iterator<Class<? extends Badger>> it = BADGERS.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                try {
                    badger3 = it.next().newInstance();
                } catch (Exception unused) {
                    badger3 = null;
                }
                if (badger3 != null && badger3.getSupportLaunchers().contains(str)) {
                    badger = badger3;
                    break;
                }
            }
            if (badger != null) {
                return true;
            }
        }
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT);
        if (queryIntentActivities != null) {
            for (int i = 0; i < queryIntentActivities.size(); i++) {
                String str2 = queryIntentActivities.get(i).activityInfo.packageName;
                Iterator<Class<? extends Badger>> it2 = BADGERS.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    try {
                        badger2 = it2.next().newInstance();
                    } catch (Exception unused2) {
                        badger2 = null;
                    }
                    if (badger2 != null && badger2.getSupportLaunchers().contains(str2)) {
                        badger = badger2;
                        break;
                    }
                }
                if (badger != null) {
                    break;
                }
            }
        }
        if (badger == null) {
            String str3 = Build.MANUFACTURER;
            if (str3.equalsIgnoreCase("Xiaomi")) {
                badger = new XiaomiHomeBadger();
            } else if (str3.equalsIgnoreCase("ZUK")) {
                badger = new ZukHomeBadger();
            } else if (str3.equalsIgnoreCase("OPPO")) {
                badger = new OPPOHomeBader();
            } else if (str3.equalsIgnoreCase("VIVO")) {
                badger = new VivoHomeBadger();
            } else {
                badger = new DefaultBadger();
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean canResolveBroadcast(Intent intent) {
        List<ResolveInfo> queryBroadcastReceivers = ApplicationLoader.applicationContext.getPackageManager().queryBroadcastReceivers(intent, 0);
        return queryBroadcastReceivers != null && queryBroadcastReceivers.size() > 0;
    }

    public static void close(Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return;
        }
        cursor.close();
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable unused) {
            }
        }
    }
}

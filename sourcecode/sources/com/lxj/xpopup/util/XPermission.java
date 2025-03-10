package com.lxj.xpopup.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.telegram.messenger.CharacterCompat;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
public final class XPermission {
    private static List<String> PERMISSIONS;
    private static XPermission sInstance;
    private static SimpleCallback sSimpleCallback4DrawOverlays;
    private static SimpleCallback sSimpleCallback4WriteSettings;
    private Context context;
    private FullCallback mFullCallback;
    private OnRationaleListener mOnRationaleListener;
    private Set<String> mPermissions;
    private List<String> mPermissionsDenied;
    private List<String> mPermissionsDeniedForever;
    private List<String> mPermissionsGranted;
    private List<String> mPermissionsRequest;
    private SimpleCallback mSimpleCallback;
    private ThemeCallback mThemeCallback;

    public interface FullCallback {
        void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied);

        void onGranted(List<String> permissionsGranted);
    }

    public interface OnRationaleListener {

        public interface ShouldRequest {
        }

        void rationale(ShouldRequest shouldRequest);
    }

    public interface SimpleCallback {
        void onDenied();

        void onGranted();
    }

    public interface ThemeCallback {
        void onActivityCreate(Activity activity);
    }

    public List<String> getPermissions() {
        return getPermissions(this.context.getPackageName());
    }

    public List<String> getPermissions(final String packageName) {
        try {
            String[] strArr = this.context.getPackageManager().getPackageInfo(packageName, LiteMode.FLAG_ANIMATED_EMOJI_CHAT_NOT_PREMIUM).requestedPermissions;
            if (strArr == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(strArr);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private boolean isGranted(final String permission) {
        return Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(this.context, permission) == 0;
    }

    public boolean isGrantedWriteSettings() {
        return Settings.System.canWrite(this.context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(23)
    public void startWriteSettingsActivity(final Activity activity, final int requestCode) {
        Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
        intent.setData(Uri.parse("package:" + this.context.getPackageName()));
        if (!isIntentAvailable(intent)) {
            launchAppDetailsSettings();
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    public boolean isGrantedDrawOverlays() {
        if (Build.VERSION.SDK_INT >= 23) {
            return Settings.canDrawOverlays(this.context);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(23)
    public void startOverlayPermissionActivity(final Activity activity, final int requestCode) {
        Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
        intent.setData(Uri.parse("package:" + this.context.getPackageName()));
        if (!isIntentAvailable(intent)) {
            launchAppDetailsSettings();
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    public void launchAppDetailsSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + this.context.getPackageName()));
        if (isIntentAvailable(intent)) {
            this.context.startActivity(intent.addFlags(268435456));
        }
    }

    public static XPermission create(Context context, final String... permissions) {
        XPermission xPermission = sInstance;
        if (xPermission == null) {
            return new XPermission(context, permissions);
        }
        xPermission.context = context;
        xPermission.prepare(permissions);
        return sInstance;
    }

    private boolean isIntentAvailable(final Intent intent) {
        return this.context.getPackageManager().queryIntentActivities(intent, CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT).size() > 0;
    }

    private XPermission(Context ctx, final String... permissions) {
        sInstance = this;
        this.context = ctx;
        prepare(permissions);
    }

    private void prepare(final String... permissions) {
        this.mPermissions = new LinkedHashSet();
        PERMISSIONS = getPermissions();
        if (permissions == null) {
            return;
        }
        for (String str : permissions) {
            for (String str2 : PermissionConstants.getPermissions(str)) {
                if (PERMISSIONS.contains(str2)) {
                    this.mPermissions.add(str2);
                }
            }
        }
    }

    public XPermission callback(final SimpleCallback callback) {
        this.mSimpleCallback = callback;
        return this;
    }

    public void request() {
        this.mPermissionsGranted = new ArrayList();
        this.mPermissionsRequest = new ArrayList();
        if (Build.VERSION.SDK_INT < 23) {
            this.mPermissionsGranted.addAll(this.mPermissions);
            requestCallback();
            return;
        }
        for (String str : this.mPermissions) {
            if (isGranted(str)) {
                this.mPermissionsGranted.add(str);
            } else {
                this.mPermissionsRequest.add(str);
            }
        }
        if (this.mPermissionsRequest.isEmpty()) {
            requestCallback();
        } else {
            startPermissionActivity();
        }
    }

    private void startPermissionActivity() {
        this.mPermissionsDenied = new ArrayList();
        this.mPermissionsDeniedForever = new ArrayList();
        PermissionActivity.start(this.context, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean rationale(final Activity activity) {
        boolean z = false;
        if (this.mOnRationaleListener != null) {
            Iterator<String> it = this.mPermissionsRequest.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (activity.shouldShowRequestPermissionRationale(it.next())) {
                    getPermissionsStatus(activity);
                    this.mOnRationaleListener.rationale(new OnRationaleListener.ShouldRequest(this) { // from class: com.lxj.xpopup.util.XPermission.1
                    });
                    z = true;
                    break;
                }
            }
            this.mOnRationaleListener = null;
        }
        return z;
    }

    private void getPermissionsStatus(final Activity activity) {
        for (String str : this.mPermissionsRequest) {
            if (isGranted(str)) {
                this.mPermissionsGranted.add(str);
            } else {
                this.mPermissionsDenied.add(str);
                if (!activity.shouldShowRequestPermissionRationale(str)) {
                    this.mPermissionsDeniedForever.add(str);
                }
            }
        }
    }

    private void requestCallback() {
        if (this.mSimpleCallback != null) {
            if (this.mPermissionsRequest.size() == 0 || this.mPermissions.size() == this.mPermissionsGranted.size()) {
                this.mSimpleCallback.onGranted();
            } else if (!this.mPermissionsDenied.isEmpty()) {
                this.mSimpleCallback.onDenied();
            }
            this.mSimpleCallback = null;
        }
        if (this.mFullCallback != null) {
            if (this.mPermissionsRequest.size() == 0 || this.mPermissions.size() == this.mPermissionsGranted.size()) {
                this.mFullCallback.onGranted(this.mPermissionsGranted);
            } else if (!this.mPermissionsDenied.isEmpty()) {
                this.mFullCallback.onDenied(this.mPermissionsDeniedForever, this.mPermissionsDenied);
            }
            this.mFullCallback = null;
        }
        this.mOnRationaleListener = null;
        this.mThemeCallback = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRequestPermissionsResult(final Activity activity) {
        getPermissionsStatus(activity);
        requestCallback();
    }

    public static class PermissionActivity extends Activity {
        public static void start(final Context context, int type) {
            Intent intent = new Intent(context, (Class<?>) PermissionActivity.class);
            intent.addFlags(268435456);
            intent.putExtra("TYPE", type);
            context.startActivity(intent);
        }

        @Override // android.app.Activity
        protected void onCreate(Bundle savedInstanceState) {
            getWindow().addFlags(262672);
            getWindow().getAttributes().alpha = 0.0f;
            int intExtra = getIntent().getIntExtra("TYPE", 1);
            if (intExtra != 1) {
                if (intExtra == 2) {
                    super.onCreate(savedInstanceState);
                    XPermission.sInstance.startWriteSettingsActivity(this, 2);
                    return;
                } else {
                    if (intExtra == 3) {
                        super.onCreate(savedInstanceState);
                        XPermission.sInstance.startOverlayPermissionActivity(this, 3);
                        return;
                    }
                    return;
                }
            }
            if (XPermission.sInstance != null) {
                if (XPermission.sInstance.mThemeCallback != null) {
                    XPermission.sInstance.mThemeCallback.onActivityCreate(this);
                }
                super.onCreate(savedInstanceState);
                if (!XPermission.sInstance.rationale(this)) {
                    if (XPermission.sInstance.mPermissionsRequest != null) {
                        int size = XPermission.sInstance.mPermissionsRequest.size();
                        if (size > 0) {
                            requestPermissions((String[]) XPermission.sInstance.mPermissionsRequest.toArray(new String[size]), 1);
                            return;
                        } else {
                            finish();
                            return;
                        }
                    }
                    return;
                }
                finish();
                return;
            }
            super.onCreate(savedInstanceState);
            Log.e("XPermission", "request permissions failed");
            finish();
        }

        @Override // android.app.Activity
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            XPermission.sInstance.onRequestPermissionsResult(this);
            finish();
        }

        @Override // android.app.Activity, android.view.Window.Callback
        public boolean dispatchTouchEvent(MotionEvent ev) {
            finish();
            return true;
        }

        @Override // android.app.Activity
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == 2) {
                if (XPermission.sSimpleCallback4WriteSettings == null) {
                    return;
                }
                if (XPermission.sInstance.isGrantedWriteSettings()) {
                    XPermission.sSimpleCallback4WriteSettings.onGranted();
                } else {
                    XPermission.sSimpleCallback4WriteSettings.onDenied();
                }
                SimpleCallback unused = XPermission.sSimpleCallback4WriteSettings = null;
            } else if (requestCode == 3) {
                if (XPermission.sSimpleCallback4DrawOverlays == null) {
                    return;
                }
                if (XPermission.sInstance.isGrantedDrawOverlays()) {
                    XPermission.sSimpleCallback4DrawOverlays.onGranted();
                } else {
                    XPermission.sSimpleCallback4DrawOverlays.onDenied();
                }
                SimpleCallback unused2 = XPermission.sSimpleCallback4DrawOverlays = null;
            }
            finish();
        }
    }
}

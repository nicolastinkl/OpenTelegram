package org.telegram.messenger;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupManager;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;

/* loaded from: classes3.dex */
public class BackupAgent extends BackupAgentHelper {
    private static BackupManager backupManager;

    @Override // android.app.backup.BackupAgent
    public void onCreate() {
        addHelper("prefs", new SharedPreferencesBackupHelper(this, "saved_tokens", "saved_tokens_login"));
    }

    public static void requestBackup(Context context) {
        if (backupManager == null) {
            backupManager = new BackupManager(context);
        }
        backupManager.dataChanged();
    }
}

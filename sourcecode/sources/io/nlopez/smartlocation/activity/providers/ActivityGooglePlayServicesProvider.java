package io.nlopez.smartlocation.activity.providers;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionApi;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import io.nlopez.smartlocation.OnActivityUpdatedListener;
import io.nlopez.smartlocation.activity.config.ActivityParams;
import io.nlopez.smartlocation.utils.GooglePlayServicesListener;
import io.nlopez.smartlocation.utils.Logger;
import org.telegram.messenger.XiaomiUtilities;

/* loaded from: classes.dex */
public class ActivityGooglePlayServicesProvider implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {
    private static final String BROADCAST_INTENT_ACTION = ActivityGooglePlayServicesProvider.class.getCanonicalName() + ".DETECTED_ACTIVITY";
    private ActivityParams activityParams;
    private GoogleApiClient client;
    private Context context;
    private final GooglePlayServicesListener googlePlayServicesListener;
    private OnActivityUpdatedListener listener;
    private Logger logger;
    private PendingIntent pendingIntent;
    private boolean shouldStart;

    public ActivityGooglePlayServicesProvider() {
        this(null);
    }

    public ActivityGooglePlayServicesProvider(GooglePlayServicesListener googlePlayServicesListener) {
        this.shouldStart = false;
        new BroadcastReceiver() { // from class: io.nlopez.smartlocation.activity.providers.ActivityGooglePlayServicesProvider.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (ActivityGooglePlayServicesProvider.BROADCAST_INTENT_ACTION.equals(intent.getAction()) && intent.hasExtra("activity")) {
                    ActivityGooglePlayServicesProvider.this.logger.d("sending new activity", new Object[0]);
                    ActivityGooglePlayServicesProvider.this.notifyActivity((DetectedActivity) intent.getParcelableExtra("activity"));
                }
            }
        };
        this.googlePlayServicesListener = googlePlayServicesListener;
    }

    private void startUpdating(ActivityParams activityParams) {
        if (this.client.isConnected()) {
            this.pendingIntent = PendingIntent.getService(this.context, 0, new Intent(this.context, (Class<?>) ActivityRecognitionService.class), 134217728);
            ActivityRecognitionApi activityRecognitionApi = ActivityRecognition.ActivityRecognitionApi;
            throw null;
        }
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public void onConnected(Bundle bundle) {
        this.logger.d("onConnected", new Object[0]);
        if (this.shouldStart) {
            startUpdating(this.activityParams);
        }
        GooglePlayServicesListener googlePlayServicesListener = this.googlePlayServicesListener;
        if (googlePlayServicesListener != null) {
            googlePlayServicesListener.onConnected(bundle);
        }
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public void onConnectionSuspended(int i) {
        this.logger.d("onConnectionSuspended " + i, new Object[0]);
        GooglePlayServicesListener googlePlayServicesListener = this.googlePlayServicesListener;
        if (googlePlayServicesListener != null) {
            googlePlayServicesListener.onConnectionSuspended(i);
        }
    }

    @Override // com.google.android.gms.common.api.internal.OnConnectionFailedListener
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.logger.d("onConnectionFailed", new Object[0]);
        GooglePlayServicesListener googlePlayServicesListener = this.googlePlayServicesListener;
        if (googlePlayServicesListener != null) {
            googlePlayServicesListener.onConnectionFailed(connectionResult);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyActivity(DetectedActivity detectedActivity) {
        OnActivityUpdatedListener onActivityUpdatedListener = this.listener;
        if (onActivityUpdatedListener != null) {
            onActivityUpdatedListener.onActivityUpdated(detectedActivity);
        }
    }

    public static class ActivityRecognitionService extends IntentService {
        public ActivityRecognitionService() {
            super(ActivityRecognitionService.class.getSimpleName());
        }

        @Override // android.app.IntentService
        protected void onHandleIntent(Intent intent) {
            if (ActivityRecognitionResult.hasResult(intent)) {
                DetectedActivity mostProbableActivity = ActivityRecognitionResult.extractResult(intent).getMostProbableActivity();
                Intent intent2 = new Intent(ActivityGooglePlayServicesProvider.BROADCAST_INTENT_ACTION);
                intent2.putExtra("activity", mostProbableActivity);
                sendBroadcast(intent2);
            }
        }
    }

    @Override // com.google.android.gms.common.api.ResultCallback
    public void onResult(Status status) {
        if (status.isSuccess()) {
            this.logger.d("Activity update request successful", new Object[0]);
            return;
        }
        if (status.hasResolution() && (this.context instanceof Activity)) {
            this.logger.w("Unable to register, but we can solve this - will startActivityForResult expecting result code 10002 (if received, please try again)", new Object[0]);
            try {
                status.startResolutionForResult((Activity) this.context, XiaomiUtilities.OP_BLUETOOTH_CHANGE);
                return;
            } catch (IntentSender.SendIntentException e) {
                this.logger.e(e, "problem with startResolutionForResult", new Object[0]);
                return;
            }
        }
        this.logger.e("Registering failed: " + status.getStatusMessage(), new Object[0]);
    }
}

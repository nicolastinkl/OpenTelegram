package io.nlopez.smartlocation.geofencing.providers;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationServices;
import io.nlopez.smartlocation.geofencing.GeofencingStore;
import io.nlopez.smartlocation.utils.GooglePlayServicesListener;
import io.nlopez.smartlocation.utils.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.telegram.messenger.XiaomiUtilities;

/* loaded from: classes.dex */
public class GeofencingGooglePlayServicesProvider implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {
    public static final String BROADCAST_INTENT_ACTION = GeofencingGooglePlayServicesProvider.class.getCanonicalName() + ".GEOFENCE_TRANSITION";
    private GoogleApiClient client;
    private Context context;
    private final List<Geofence> geofencesToAdd;
    private final List<String> geofencesToRemove;
    private GeofencingStore geofencingStore;
    private final GooglePlayServicesListener googlePlayServicesListener;
    private Logger logger;
    private PendingIntent pendingIntent;

    public GeofencingGooglePlayServicesProvider() {
        this(null);
    }

    public GeofencingGooglePlayServicesProvider(GooglePlayServicesListener googlePlayServicesListener) {
        this.geofencesToAdd = Collections.synchronizedList(new ArrayList());
        this.geofencesToRemove = Collections.synchronizedList(new ArrayList());
        new BroadcastReceiver() { // from class: io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (GeofencingGooglePlayServicesProvider.BROADCAST_INTENT_ACTION.equals(intent.getAction()) && intent.hasExtra("geofences")) {
                    GeofencingGooglePlayServicesProvider.this.logger.d("Received geofencing event", new Object[0]);
                    intent.getIntExtra("transition", -1);
                    Iterator<String> it = intent.getStringArrayListExtra("geofences").iterator();
                    if (it.hasNext()) {
                        it.next();
                        GeofencingStore unused = GeofencingGooglePlayServicesProvider.this.geofencingStore;
                        throw null;
                    }
                }
            }
        };
        this.googlePlayServicesListener = googlePlayServicesListener;
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public void onConnected(Bundle bundle) {
        this.logger.d("onConnected", new Object[0]);
        if (this.client.isConnected()) {
            if (this.geofencesToAdd.size() > 0) {
                if (ContextCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                    return;
                }
                LocationServices.GeofencingApi.addGeofences(this.client, this.geofencesToAdd, this.pendingIntent);
                this.geofencesToAdd.clear();
            }
            if (this.geofencesToRemove.size() > 0) {
                LocationServices.GeofencingApi.removeGeofences(this.client, this.geofencesToRemove);
                this.geofencesToRemove.clear();
            }
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

    public static class GeofencingService extends IntentService {
        public GeofencingService() {
            super(GeofencingService.class.getSimpleName());
        }

        @Override // android.app.IntentService
        protected void onHandleIntent(Intent intent) {
            GeofencingEvent fromIntent = GeofencingEvent.fromIntent(intent);
            if (fromIntent == null || fromIntent.hasError()) {
                return;
            }
            int geofenceTransition = fromIntent.getGeofenceTransition();
            Intent intent2 = new Intent(GeofencingGooglePlayServicesProvider.BROADCAST_INTENT_ACTION);
            intent2.putExtra("transition", geofenceTransition);
            intent2.putExtra("location", fromIntent.getTriggeringLocation());
            ArrayList<String> arrayList = new ArrayList<>();
            Iterator<Geofence> it = fromIntent.getTriggeringGeofences().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getRequestId());
            }
            intent2.putStringArrayListExtra("geofences", arrayList);
            sendBroadcast(intent2);
        }
    }

    @Override // com.google.android.gms.common.api.ResultCallback
    public void onResult(Status status) {
        if (status.isSuccess()) {
            this.logger.d("Geofencing update request successful", new Object[0]);
            return;
        }
        if (status.hasResolution() && (this.context instanceof Activity)) {
            this.logger.w("Unable to register, but we can solve this - will startActivityForResult expecting result code 10003 (if received, please try again)", new Object[0]);
            try {
                status.startResolutionForResult((Activity) this.context, XiaomiUtilities.OP_DATA_CONNECT_CHANGE);
                return;
            } catch (IntentSender.SendIntentException e) {
                this.logger.e(e, "problem with startResolutionForResult", new Object[0]);
                return;
            }
        }
        this.logger.e("Registering failed: " + status.getStatusMessage(), new Object[0]);
    }
}

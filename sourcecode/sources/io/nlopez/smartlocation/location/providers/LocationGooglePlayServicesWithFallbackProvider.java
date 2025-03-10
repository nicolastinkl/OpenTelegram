package io.nlopez.smartlocation.location.providers;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.location.LocationProvider;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.utils.GooglePlayServicesListener;
import io.nlopez.smartlocation.utils.Logger;

/* loaded from: classes.dex */
public class LocationGooglePlayServicesWithFallbackProvider implements LocationProvider, GooglePlayServicesListener {
    private Context context;
    private OnLocationUpdatedListener listener;
    private Logger logger;
    private LocationParams params;
    private LocationProvider provider;
    private boolean shouldStart = false;
    private boolean singleUpdate = false;

    @Override // io.nlopez.smartlocation.utils.GooglePlayServicesListener
    public void onConnected(Bundle bundle) {
    }

    public LocationGooglePlayServicesWithFallbackProvider(Context context) {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0) {
            this.provider = new LocationGooglePlayServicesProvider(this);
        } else {
            this.provider = new LocationManagerProvider();
        }
    }

    @Override // io.nlopez.smartlocation.location.LocationProvider
    public void init(Context context, Logger logger) {
        this.logger = logger;
        this.context = context;
        logger.d("Currently selected provider = " + this.provider.getClass().getSimpleName(), new Object[0]);
        this.provider.init(context, logger);
    }

    @Override // io.nlopez.smartlocation.location.LocationProvider
    public void start(OnLocationUpdatedListener onLocationUpdatedListener, LocationParams locationParams, boolean z) {
        this.shouldStart = true;
        this.listener = onLocationUpdatedListener;
        this.params = locationParams;
        this.singleUpdate = z;
        this.provider.start(onLocationUpdatedListener, locationParams, z);
    }

    @Override // io.nlopez.smartlocation.utils.GooglePlayServicesListener
    public void onConnectionSuspended(int i) {
        fallbackToLocationManager();
    }

    @Override // io.nlopez.smartlocation.utils.GooglePlayServicesListener
    public void onConnectionFailed(ConnectionResult connectionResult) {
        fallbackToLocationManager();
    }

    private void fallbackToLocationManager() {
        this.logger.d("FusedLocationProvider not working, falling back and using LocationManager", new Object[0]);
        LocationManagerProvider locationManagerProvider = new LocationManagerProvider();
        this.provider = locationManagerProvider;
        locationManagerProvider.init(this.context, this.logger);
        if (this.shouldStart) {
            this.provider.start(this.listener, this.params, this.singleUpdate);
        }
    }
}

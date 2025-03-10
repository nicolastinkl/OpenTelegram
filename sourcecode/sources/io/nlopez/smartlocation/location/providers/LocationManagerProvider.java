package io.nlopez.smartlocation.location.providers;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import androidx.core.content.ContextCompat;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.location.LocationProvider;
import io.nlopez.smartlocation.location.LocationStore;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.utils.Logger;

/* loaded from: classes.dex */
public class LocationManagerProvider implements LocationProvider, LocationListener {
    private OnLocationUpdatedListener listener;
    private LocationManager locationManager;
    private LocationStore locationStore;
    private Logger logger;
    private Context mContext;

    @Override // android.location.LocationListener
    public void onProviderDisabled(String str) {
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(String str) {
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(String str, int i, Bundle bundle) {
    }

    @Override // io.nlopez.smartlocation.location.LocationProvider
    public void init(Context context, Logger logger) {
        this.locationManager = (LocationManager) context.getSystemService("location");
        this.logger = logger;
        this.mContext = context;
        this.locationStore = new LocationStore(context);
    }

    @Override // io.nlopez.smartlocation.location.LocationProvider
    public void start(OnLocationUpdatedListener onLocationUpdatedListener, LocationParams locationParams, boolean z) {
        this.listener = onLocationUpdatedListener;
        if (onLocationUpdatedListener == null) {
            this.logger.d("Listener is null, you sure about this?", new Object[0]);
        }
        Criteria provider = getProvider(locationParams);
        if (z) {
            if (ContextCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") != 0 && ContextCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                this.logger.i("Permission check failed. Please handle it in your app before setting up location", new Object[0]);
                return;
            } else {
                this.locationManager.requestSingleUpdate(provider, this, Looper.getMainLooper());
                return;
            }
        }
        this.locationManager.requestLocationUpdates(locationParams.getInterval(), locationParams.getDistance(), provider, this, Looper.getMainLooper());
    }

    private Criteria getProvider(LocationParams locationParams) {
        LocationAccuracy accuracy = locationParams.getAccuracy();
        Criteria criteria = new Criteria();
        int i = AnonymousClass1.$SwitchMap$io$nlopez$smartlocation$location$config$LocationAccuracy[accuracy.ordinal()];
        if (i == 1) {
            criteria.setAccuracy(1);
            criteria.setHorizontalAccuracy(3);
            criteria.setVerticalAccuracy(3);
            criteria.setBearingAccuracy(3);
            criteria.setSpeedAccuracy(3);
            criteria.setPowerRequirement(3);
        } else if (i == 2) {
            criteria.setAccuracy(2);
            criteria.setHorizontalAccuracy(2);
            criteria.setVerticalAccuracy(2);
            criteria.setBearingAccuracy(2);
            criteria.setSpeedAccuracy(2);
            criteria.setPowerRequirement(2);
        } else {
            criteria.setAccuracy(2);
            criteria.setHorizontalAccuracy(1);
            criteria.setVerticalAccuracy(1);
            criteria.setBearingAccuracy(1);
            criteria.setSpeedAccuracy(1);
            criteria.setPowerRequirement(1);
        }
        return criteria;
    }

    /* renamed from: io.nlopez.smartlocation.location.providers.LocationManagerProvider$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$nlopez$smartlocation$location$config$LocationAccuracy;

        static {
            int[] iArr = new int[LocationAccuracy.values().length];
            $SwitchMap$io$nlopez$smartlocation$location$config$LocationAccuracy = iArr;
            try {
                iArr[LocationAccuracy.HIGH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$nlopez$smartlocation$location$config$LocationAccuracy[LocationAccuracy.MEDIUM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$nlopez$smartlocation$location$config$LocationAccuracy[LocationAccuracy.LOW.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$nlopez$smartlocation$location$config$LocationAccuracy[LocationAccuracy.LOWEST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(Location location) {
        this.logger.d("onLocationChanged", location);
        OnLocationUpdatedListener onLocationUpdatedListener = this.listener;
        if (onLocationUpdatedListener != null) {
            onLocationUpdatedListener.onLocationUpdated(location);
        }
        if (this.locationStore != null) {
            this.logger.d("Stored in SharedPreferences", new Object[0]);
            this.locationStore.put("LMP", location);
        }
    }
}

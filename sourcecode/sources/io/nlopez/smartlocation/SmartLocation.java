package io.nlopez.smartlocation;

import android.content.Context;
import io.nlopez.smartlocation.location.LocationProvider;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;
import io.nlopez.smartlocation.utils.Logger;
import io.nlopez.smartlocation.utils.LoggerFactory;
import java.util.Map;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class SmartLocation {
    private Context context;
    private Logger logger;
    private boolean preInitialize;

    private SmartLocation(Context context, Logger logger, boolean z) {
        this.context = context;
        this.logger = logger;
        this.preInitialize = z;
    }

    public static SmartLocation with(Context context) {
        return new Builder(context).build();
    }

    public LocationControl location() {
        return location(new LocationGooglePlayServicesWithFallbackProvider(this.context));
    }

    public LocationControl location(LocationProvider locationProvider) {
        return new LocationControl(this, locationProvider);
    }

    public static class Builder {
        private final Context context;
        private boolean loggingEnabled = false;
        private boolean preInitialize = true;

        public Builder(Context context) {
            this.context = context;
        }

        public SmartLocation build() {
            return new SmartLocation(this.context, LoggerFactory.buildLogger(this.loggingEnabled), this.preInitialize);
        }
    }

    public static class LocationControl {
        private static final Map<Context, LocationProvider> MAPPING = new WeakHashMap();
        private LocationProvider provider;
        private LocationParams params = LocationParams.BEST_EFFORT;
        private boolean oneFix = false;

        public LocationControl(SmartLocation smartLocation, LocationProvider locationProvider) {
            Map<Context, LocationProvider> map = MAPPING;
            if (!map.containsKey(smartLocation.context)) {
                map.put(smartLocation.context, locationProvider);
            }
            this.provider = map.get(smartLocation.context);
            if (smartLocation.preInitialize) {
                this.provider.init(smartLocation.context, smartLocation.logger);
            }
        }

        public LocationControl oneFix() {
            this.oneFix = true;
            return this;
        }

        public void start(OnLocationUpdatedListener onLocationUpdatedListener) {
            LocationProvider locationProvider = this.provider;
            if (locationProvider == null) {
                throw new RuntimeException("A provider must be initialized");
            }
            locationProvider.start(onLocationUpdatedListener, this.params, this.oneFix);
        }
    }
}

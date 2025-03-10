package io.nlopez.smartlocation.location;

import android.content.Context;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.utils.Logger;

/* loaded from: classes.dex */
public interface LocationProvider {
    void init(Context context, Logger logger);

    void start(OnLocationUpdatedListener onLocationUpdatedListener, LocationParams locationParams, boolean z);
}

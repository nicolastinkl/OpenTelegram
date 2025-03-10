package io.nlopez.smartlocation.utils;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes.dex */
public interface GooglePlayServicesListener {
    void onConnected(Bundle bundle);

    void onConnectionFailed(ConnectionResult connectionResult);

    void onConnectionSuspended(int i);
}

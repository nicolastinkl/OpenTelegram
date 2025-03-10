package io.nlopez.smartlocation.utils;

/* loaded from: classes.dex */
public interface ServiceConnectionListener {
    void onConnected();

    void onConnectionFailed();

    void onConnectionSuspended();
}

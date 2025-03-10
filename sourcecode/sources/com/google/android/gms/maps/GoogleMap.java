package com.google.android.gms.maps;

import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.HashMap;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
/* loaded from: classes.dex */
public class GoogleMap {
    private final IGoogleMapDelegate zza;
    private UiSettings zzc;

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface CancelableCallback {
        void onCancel();

        void onFinish();
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnCameraMoveListener {
        void onCameraMove();
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnCameraMoveStartedListener {
        void onCameraMoveStarted(int i);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnMapLoadedCallback {
        void onMapLoaded();
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnMarkerClickListener {
        boolean onMarkerClick(Marker marker);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    @Deprecated
    public interface OnMyLocationChangeListener {
        void onMyLocationChange(Location location);
    }

    public GoogleMap(IGoogleMapDelegate iGoogleMapDelegate) {
        new HashMap();
        this.zza = (IGoogleMapDelegate) Preconditions.checkNotNull(iGoogleMapDelegate);
    }

    public final Circle addCircle(CircleOptions circleOptions) {
        try {
            Preconditions.checkNotNull(circleOptions, "CircleOptions must not be null.");
            return new Circle(this.zza.addCircle(circleOptions));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Marker addMarker(MarkerOptions markerOptions) {
        try {
            Preconditions.checkNotNull(markerOptions, "MarkerOptions must not be null.");
            com.google.android.gms.internal.maps.zzaa addMarker = this.zza.addMarker(markerOptions);
            if (addMarker != null) {
                return new Marker(addMarker);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate cameraUpdate) {
        try {
            Preconditions.checkNotNull(cameraUpdate, "CameraUpdate must not be null.");
            this.zza.animateCamera(cameraUpdate.zza());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final CameraPosition getCameraPosition() {
        try {
            return this.zza.getCameraPosition();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMaxZoomLevel() {
        try {
            return this.zza.getMaxZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Projection getProjection() {
        try {
            return new Projection(this.zza.getProjection());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final UiSettings getUiSettings() {
        try {
            if (this.zzc == null) {
                this.zzc = new UiSettings(this.zza.getUiSettings());
            }
            return this.zzc;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void moveCamera(CameraUpdate cameraUpdate) {
        try {
            Preconditions.checkNotNull(cameraUpdate, "CameraUpdate must not be null.");
            this.zza.moveCamera(cameraUpdate.zza());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean setMapStyle(MapStyleOptions mapStyleOptions) {
        try {
            return this.zza.setMapStyle(mapStyleOptions);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnCameraMoveListener(OnCameraMoveListener onCameraMoveListener) {
        try {
            if (onCameraMoveListener == null) {
                this.zza.setOnCameraMoveListener(null);
            } else {
                this.zza.setOnCameraMoveListener(new zzv(this, onCameraMoveListener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnCameraMoveStartedListener(OnCameraMoveStartedListener onCameraMoveStartedListener) {
        try {
            if (onCameraMoveStartedListener == null) {
                this.zza.setOnCameraMoveStartedListener(null);
            } else {
                this.zza.setOnCameraMoveStartedListener(new zzu(this, onCameraMoveStartedListener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setOnMapLoadedCallback(OnMapLoadedCallback onMapLoadedCallback) {
        try {
            if (onMapLoadedCallback == null) {
                this.zza.setOnMapLoadedCallback(null);
            } else {
                this.zza.setOnMapLoadedCallback(new zzj(this, onMapLoadedCallback));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnMarkerClickListener(OnMarkerClickListener onMarkerClickListener) {
        try {
            if (onMarkerClickListener == null) {
                this.zza.setOnMarkerClickListener(null);
            } else {
                this.zza.setOnMarkerClickListener(new zza(this, onMarkerClickListener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Deprecated
    public final void setOnMyLocationChangeListener(OnMyLocationChangeListener onMyLocationChangeListener) {
        try {
            if (onMyLocationChangeListener == null) {
                this.zza.setOnMyLocationChangeListener(null);
            } else {
                this.zza.setOnMyLocationChangeListener(new zzg(this, onMyLocationChangeListener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setMapType(int i) {
        try {
            this.zza.setMapType(i);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setMyLocationEnabled(boolean z) {
        try {
            this.zza.setMyLocationEnabled(z);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setPadding(int i, int i2, int i3, int i4) {
        try {
            this.zza.setPadding(i, i2, i3, i4);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate cameraUpdate, int i, CancelableCallback cancelableCallback) {
        try {
            Preconditions.checkNotNull(cameraUpdate, "CameraUpdate must not be null.");
            this.zza.animateCameraWithDurationAndCallback(cameraUpdate.zza(), i, cancelableCallback == null ? null : new zzaa(cancelableCallback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate cameraUpdate, CancelableCallback cancelableCallback) {
        try {
            Preconditions.checkNotNull(cameraUpdate, "CameraUpdate must not be null.");
            this.zza.animateCameraWithCallback(cameraUpdate.zza(), cancelableCallback == null ? null : new zzaa(cancelableCallback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}

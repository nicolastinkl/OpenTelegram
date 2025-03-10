package io.nlopez.smartlocation.location;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

/* loaded from: classes.dex */
public class LocationStore {
    private static final String PREFIX_ID = LocationStore.class.getCanonicalName() + ".KEY";
    private SharedPreferences preferences;

    public LocationStore(Context context) {
        this.preferences = context.getSharedPreferences("LOCATION_STORE", 0);
    }

    public void put(String str, Location location) {
        SharedPreferences.Editor edit = this.preferences.edit();
        edit.putString(getFieldKey(str, "PROVIDER"), location.getProvider());
        edit.putLong(getFieldKey(str, "LATITUDE"), Double.doubleToLongBits(location.getLatitude()));
        edit.putLong(getFieldKey(str, "LONGITUDE"), Double.doubleToLongBits(location.getLongitude()));
        edit.putFloat(getFieldKey(str, "ACCURACY"), location.getAccuracy());
        edit.putLong(getFieldKey(str, "ALTITUDE"), Double.doubleToLongBits(location.getAltitude()));
        edit.putFloat(getFieldKey(str, "SPEED"), location.getSpeed());
        edit.putLong(getFieldKey(str, "TIME"), location.getTime());
        edit.putFloat(getFieldKey(str, "BEARING"), location.getBearing());
        edit.apply();
    }

    private String getFieldKey(String str, String str2) {
        return PREFIX_ID + "_" + str + "_" + str2;
    }
}

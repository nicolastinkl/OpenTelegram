package io.nlopez.smartlocation.geocoding.utils;

import android.location.Address;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class LocationAddress implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: io.nlopez.smartlocation.geocoding.utils.LocationAddress.1
        @Override // android.os.Parcelable.Creator
        public LocationAddress createFromParcel(Parcel parcel) {
            return new LocationAddress(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LocationAddress[] newArray(int i) {
            return new LocationAddress[i];
        }
    };
    private Address address;
    private Location location;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LocationAddress(Address address) {
        this.address = address;
        Location location = new Location(LocationAddress.class.getCanonicalName());
        this.location = location;
        location.setLatitude(address.getLatitude());
        this.location.setLongitude(address.getLongitude());
    }

    public LocationAddress(Parcel parcel) {
        this.location = (Location) parcel.readParcelable(Location.class.getClassLoader());
        this.address = (Address) parcel.readParcelable(Address.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.location, i);
        parcel.writeParcelable(this.address, i);
    }
}

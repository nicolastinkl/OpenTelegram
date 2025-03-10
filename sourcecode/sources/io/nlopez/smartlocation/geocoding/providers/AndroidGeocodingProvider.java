package io.nlopez.smartlocation.geocoding.providers;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import io.nlopez.smartlocation.OnGeocodingListener;
import io.nlopez.smartlocation.OnReverseGeocodingListener;
import io.nlopez.smartlocation.geocoding.utils.LocationAddress;
import io.nlopez.smartlocation.utils.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class AndroidGeocodingProvider {
    private static final String BROADCAST_DIRECT_GEOCODING_ACTION = AndroidGeocodingProvider.class.getCanonicalName() + ".DIRECT_GEOCODE_ACTION";
    private static final String BROADCAST_REVERSE_GEOCODING_ACTION = AndroidGeocodingProvider.class.getCanonicalName() + ".REVERSE_GEOCODE_ACTION";
    private OnGeocodingListener geocodingListener;
    private Logger logger;
    private OnReverseGeocodingListener reverseGeocodingListener;

    public AndroidGeocodingProvider() {
        this(Locale.getDefault());
    }

    public AndroidGeocodingProvider(Locale locale) {
        new BroadcastReceiver() { // from class: io.nlopez.smartlocation.geocoding.providers.AndroidGeocodingProvider.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (AndroidGeocodingProvider.BROADCAST_DIRECT_GEOCODING_ACTION.equals(intent.getAction())) {
                    AndroidGeocodingProvider.this.logger.d("sending new direct geocoding response", new Object[0]);
                    if (AndroidGeocodingProvider.this.geocodingListener != null) {
                        AndroidGeocodingProvider.this.geocodingListener.onLocationResolved(intent.getStringExtra("name"), intent.getParcelableArrayListExtra("result"));
                    }
                }
            }
        };
        new BroadcastReceiver() { // from class: io.nlopez.smartlocation.geocoding.providers.AndroidGeocodingProvider.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (AndroidGeocodingProvider.BROADCAST_REVERSE_GEOCODING_ACTION.equals(intent.getAction())) {
                    AndroidGeocodingProvider.this.logger.d("sending new reverse geocoding response", new Object[0]);
                    if (AndroidGeocodingProvider.this.reverseGeocodingListener != null) {
                        AndroidGeocodingProvider.this.reverseGeocodingListener.onAddressResolved((Location) intent.getParcelableExtra("location"), (ArrayList) intent.getSerializableExtra("result"));
                    }
                }
            }
        };
        if (locale == null) {
            throw new RuntimeException("Locale is null");
        }
        new HashMap();
        new HashMap();
        if (!Geocoder.isPresent()) {
            throw new RuntimeException("Android Geocoder not present. Please check if Geocoder.isPresent() before invoking the search");
        }
    }

    public static class AndroidGeocodingService extends IntentService {
        private Geocoder geocoder;

        public AndroidGeocodingService() {
            super(AndroidGeocodingService.class.getSimpleName());
        }

        @Override // android.app.IntentService
        protected void onHandleIntent(Intent intent) {
            Locale locale = (Locale) intent.getSerializableExtra("locale");
            if (locale == null) {
                this.geocoder = new Geocoder(this);
            } else {
                this.geocoder = new Geocoder(this, locale);
            }
            if (intent.hasExtra("direct")) {
                HashMap hashMap = (HashMap) intent.getSerializableExtra("direct");
                for (String str : hashMap.keySet()) {
                    sendDirectGeocodingBroadcast(str, addressFromName(str, ((Integer) hashMap.get(str)).intValue()));
                }
            }
            if (intent.hasExtra("reverse")) {
                HashMap hashMap2 = (HashMap) intent.getSerializableExtra("reverse");
                for (Location location : hashMap2.keySet()) {
                    sendReverseGeocodingBroadcast(location, addressFromLocation(location, ((Integer) hashMap2.get(location)).intValue()));
                }
            }
        }

        private void sendDirectGeocodingBroadcast(String str, ArrayList<LocationAddress> arrayList) {
            Intent intent = new Intent(AndroidGeocodingProvider.BROADCAST_DIRECT_GEOCODING_ACTION);
            intent.putExtra("name", str);
            intent.putExtra("result", arrayList);
            sendBroadcast(intent);
        }

        private void sendReverseGeocodingBroadcast(Location location, ArrayList<Address> arrayList) {
            Intent intent = new Intent(AndroidGeocodingProvider.BROADCAST_REVERSE_GEOCODING_ACTION);
            intent.putExtra("location", location);
            intent.putExtra("result", arrayList);
            sendBroadcast(intent);
        }

        private ArrayList<Address> addressFromLocation(Location location, int i) {
            try {
                return new ArrayList<>(this.geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), i));
            } catch (IOException unused) {
                return new ArrayList<>();
            }
        }

        private ArrayList<LocationAddress> addressFromName(String str, int i) {
            try {
                List<Address> fromLocationName = this.geocoder.getFromLocationName(str, i);
                ArrayList<LocationAddress> arrayList = new ArrayList<>();
                Iterator<Address> it = fromLocationName.iterator();
                while (it.hasNext()) {
                    arrayList.add(new LocationAddress(it.next()));
                }
                return arrayList;
            } catch (IOException unused) {
                return new ArrayList<>();
            }
        }
    }
}

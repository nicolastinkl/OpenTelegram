package com.tencent.beacon.event.open;

import android.text.TextUtils;
import com.tencent.beacon.a.c.c;
import com.tencent.beacon.event.c.d;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class BeaconEvent {
    private String a;
    private String b;
    private EventType c;
    private boolean d;
    private Map<String, String> e;

    public static final class Builder {
        private String a;
        private String b;
        private EventType c;
        private boolean d;
        private Map<String, String> e;

        /* synthetic */ Builder(a aVar) {
            this();
        }

        public BeaconEvent build() {
            String b = d.b(this.b);
            if (TextUtils.isEmpty(this.a)) {
                this.a = c.d().f();
            }
            return new BeaconEvent(this.a, b, this.c, this.d, this.e, null);
        }

        public Builder withAppKey(String str) {
            this.a = str;
            return this;
        }

        public Builder withCode(String str) {
            this.b = str;
            return this;
        }

        public Builder withIsSucceed(boolean z) {
            this.d = z;
            return this;
        }

        public Builder withParams(Map<String, String> map) {
            if (map != null) {
                this.e.putAll(map);
            }
            return this;
        }

        public Builder withType(EventType eventType) {
            this.c = eventType;
            return this;
        }

        /* synthetic */ Builder(BeaconEvent beaconEvent, a aVar) {
            this(beaconEvent);
        }

        public Builder withParams(String str, String str2) {
            this.e.put(str, str2);
            return this;
        }

        private Builder() {
            this.c = EventType.NORMAL;
            this.d = true;
            this.e = new HashMap();
        }

        private Builder(BeaconEvent beaconEvent) {
            this.c = EventType.NORMAL;
            this.d = true;
            this.e = new HashMap();
            this.a = beaconEvent.a;
            this.b = beaconEvent.b;
            this.c = beaconEvent.c;
            this.d = beaconEvent.d;
            this.e.putAll(beaconEvent.e);
        }
    }

    /* synthetic */ BeaconEvent(String str, String str2, EventType eventType, boolean z, Map map, a aVar) {
        this(str, str2, eventType, z, map);
    }

    public static Builder builder() {
        return new Builder((a) null);
    }

    public static Builder newBuilder(BeaconEvent beaconEvent) {
        return new Builder(beaconEvent, null);
    }

    public String getAppKey() {
        return this.a;
    }

    public String getCode() {
        return this.b;
    }

    public String getLogidPrefix() {
        switch (a.a[this.c.ordinal()]) {
            case 1:
            case 2:
                return "N";
            case 3:
            case 4:
                return "I";
            case 5:
            case 6:
                return "Y";
            default:
                return "";
        }
    }

    public Map<String, String> getParams() {
        return this.e;
    }

    public EventType getType() {
        return this.c;
    }

    public boolean isSucceed() {
        return this.d;
    }

    public void setAppKey(String str) {
        this.a = str;
    }

    public void setCode(String str) {
        this.b = str;
    }

    public void setParams(Map<String, String> map) {
        this.e = map;
    }

    public void setSucceed(boolean z) {
        this.d = z;
    }

    public void setType(EventType eventType) {
        this.c = eventType;
    }

    public String toString() {
        return super.toString();
    }

    private BeaconEvent(String str, String str2, EventType eventType, boolean z, Map<String, String> map) {
        this.a = str;
        this.b = str2;
        this.c = eventType;
        this.d = z;
        this.e = map;
    }
}

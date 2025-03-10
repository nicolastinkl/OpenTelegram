package io.nlopez.smartlocation.location.config;

/* loaded from: classes.dex */
public class LocationParams {
    public static final LocationParams BEST_EFFORT;
    private LocationAccuracy accuracy;
    private float distance;
    private long interval;

    static {
        new Builder().setAccuracy(LocationAccuracy.HIGH).setDistance(0.0f).setInterval(500L).build();
        BEST_EFFORT = new Builder().setAccuracy(LocationAccuracy.MEDIUM).setDistance(150.0f).setInterval(2500L).build();
        new Builder().setAccuracy(LocationAccuracy.LOW).setDistance(500.0f).setInterval(5000L).build();
    }

    LocationParams(LocationAccuracy locationAccuracy, long j, float f) {
        this.interval = j;
        this.distance = f;
        this.accuracy = locationAccuracy;
    }

    public long getInterval() {
        return this.interval;
    }

    public float getDistance() {
        return this.distance;
    }

    public LocationAccuracy getAccuracy() {
        return this.accuracy;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocationParams)) {
            return false;
        }
        LocationParams locationParams = (LocationParams) obj;
        return Float.compare(locationParams.distance, this.distance) == 0 && this.interval == locationParams.interval && this.accuracy == locationParams.accuracy;
    }

    public int hashCode() {
        long j = this.interval;
        int i = ((int) (j ^ (j >>> 32))) * 31;
        float f = this.distance;
        return ((i + (f != 0.0f ? Float.floatToIntBits(f) : 0)) * 31) + this.accuracy.hashCode();
    }

    public static class Builder {
        private LocationAccuracy accuracy;
        private float distance;
        private long interval;

        public Builder setAccuracy(LocationAccuracy locationAccuracy) {
            this.accuracy = locationAccuracy;
            return this;
        }

        public Builder setInterval(long j) {
            this.interval = j;
            return this;
        }

        public Builder setDistance(float f) {
            this.distance = f;
            return this;
        }

        public LocationParams build() {
            return new LocationParams(this.accuracy, this.interval, this.distance);
        }
    }
}

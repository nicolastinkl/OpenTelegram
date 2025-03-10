package com.fm.openinstall;

/* loaded from: classes.dex */
public final class Configuration {
    public static final String NULL = "__NULL__";
    private boolean a;
    private String b;
    private String c;
    private boolean d;
    private String e;
    private boolean f;
    private String g;
    private String h;
    private String i;
    private boolean j;
    private boolean k;

    public static class Builder {
        private boolean a = false;
        private String b = Configuration.NULL;
        private String c = Configuration.NULL;
        private boolean d = false;
        private String e = Configuration.NULL;
        private boolean f = false;
        private String g = Configuration.NULL;
        private String h = Configuration.NULL;
        private String i = Configuration.NULL;
        private boolean j = false;
        private boolean k = false;

        @Deprecated
        public Builder adEnabled(boolean z) {
            this.a = z;
            return this;
        }

        public Builder androidId(String str) {
            this.h = str;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }

        public Builder gaid(String str) {
            this.c = str;
            return this;
        }

        public Builder imei(String str) {
            this.e = str;
            return this;
        }

        @Deprecated
        public Builder imeiDisabled() {
            this.d = true;
            return this;
        }

        public Builder macAddress(String str) {
            this.g = str;
            return this;
        }

        @Deprecated
        public Builder macDisabled() {
            this.f = true;
            return this;
        }

        public Builder oaid(String str) {
            this.b = str;
            return this;
        }

        public Builder serialNumber(String str) {
            this.i = str;
            return this;
        }

        public Builder simulatorDisabled() {
            this.j = true;
            return this;
        }

        public Builder storageDisabled() {
            this.k = true;
            return this;
        }
    }

    private Configuration() {
    }

    private Configuration(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
        this.f = builder.f;
        this.g = builder.g;
        this.h = builder.h;
        this.i = builder.i;
        this.j = builder.j;
        this.k = builder.k;
    }

    public static Configuration getDefault() {
        return new Builder().build();
    }

    public static boolean isPresent(String str) {
        return !NULL.equals(str);
    }

    public String getAndroidId() {
        return this.h;
    }

    public String getGaid() {
        return this.c;
    }

    public String getImei() {
        return this.e;
    }

    public String getMacAddress() {
        return this.g;
    }

    public String getOaid() {
        return this.b;
    }

    public String getSerialNumber() {
        return this.i;
    }

    public boolean isAdEnabled() {
        return this.a;
    }

    public boolean isImeiDisabled() {
        return this.d;
    }

    public boolean isMacDisabled() {
        return this.f;
    }

    public boolean isSimulatorDisabled() {
        return this.j;
    }

    public boolean isStorageDisabled() {
        return this.k;
    }
}

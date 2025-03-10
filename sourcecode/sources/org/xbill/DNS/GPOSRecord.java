package org.xbill.DNS;

import java.io.IOException;

/* loaded from: classes4.dex */
public class GPOSRecord extends Record {
    private byte[] altitude;
    private byte[] latitude;
    private byte[] longitude;

    GPOSRecord() {
    }

    private void validate(double d, double d2) throws IllegalArgumentException {
        if (d < -90.0d || d > 90.0d) {
            throw new IllegalArgumentException("illegal longitude " + d);
        }
        if (d2 < -180.0d || d2 > 180.0d) {
            throw new IllegalArgumentException("illegal latitude " + d2);
        }
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.longitude = dNSInput.readCountedString();
        this.latitude = dNSInput.readCountedString();
        this.altitude = dNSInput.readCountedString();
        try {
            validate(getLongitude(), getLatitude());
        } catch (IllegalArgumentException e) {
            throw new WireParseException(e.getMessage());
        }
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        return Record.byteArrayToString(this.longitude, true) + " " + Record.byteArrayToString(this.latitude, true) + " " + Record.byteArrayToString(this.altitude, true);
    }

    public String getLongitudeString() {
        return Record.byteArrayToString(this.longitude, false);
    }

    public double getLongitude() {
        return Double.parseDouble(getLongitudeString());
    }

    public String getLatitudeString() {
        return Record.byteArrayToString(this.latitude, false);
    }

    public double getLatitude() {
        return Double.parseDouble(getLatitudeString());
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeCountedString(this.longitude);
        dNSOutput.writeCountedString(this.latitude);
        dNSOutput.writeCountedString(this.altitude);
    }
}

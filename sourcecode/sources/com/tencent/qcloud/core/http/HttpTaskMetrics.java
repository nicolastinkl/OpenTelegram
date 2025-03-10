package com.tencent.qcloud.core.http;

import android.text.TextUtils;
import java.net.InetAddress;
import java.util.List;

/* loaded from: classes.dex */
public class HttpTaskMetrics {
    private long calculateMD5STookTime;
    private long calculateMD5StartTime;
    InetAddress connectAddress;
    long connectStartTimestamp;
    long connectTookTime;
    long dnsLookupTookTime;
    long dnsStartTimestamp;
    String domainName;
    private long fullTaskStartTime;
    private long fullTaskTookTime;
    private long httpTaskStartTime;
    private long httpTaskTookTime;
    private boolean isClockSkewedRetry;
    long readResponseBodyStartTimestamp;
    long readResponseBodyTookTime;
    long readResponseHeaderStartTimestamp;
    long readResponseHeaderTookTime;
    List<InetAddress> remoteAddress;
    long requestBodyByteCount;
    long responseBodyByteCount;
    private int retryCount;
    long secureConnectStartTimestamp;
    long secureConnectTookTime;
    private long signRequestStartTime;
    private long signRequestTookTime;
    long writeRequestBodyStartTimestamp;
    long writeRequestBodyTookTime;
    long writeRequestHeaderStartTimestamp;
    long writeRequestHeaderTookTime;

    private double toSeconds(long j) {
        return j / 1.0E9d;
    }

    public void onDataReady() {
    }

    void onTaskStart() {
        this.fullTaskStartTime = System.nanoTime();
    }

    void onTaskEnd() {
        this.fullTaskTookTime = System.nanoTime() - this.fullTaskStartTime;
        onDataReady();
    }

    void onHttpTaskStart() {
        this.httpTaskStartTime = System.nanoTime();
    }

    void onHttpTaskEnd() {
        this.httpTaskTookTime = System.nanoTime() - this.httpTaskStartTime;
    }

    void onCalculateMD5Start() {
        this.calculateMD5StartTime = System.nanoTime();
    }

    void onCalculateMD5End() {
        this.calculateMD5STookTime += System.nanoTime() - this.calculateMD5StartTime;
    }

    void onSignRequestStart() {
        this.signRequestStartTime = System.nanoTime();
    }

    void onSignRequestEnd() {
        this.signRequestTookTime += System.nanoTime() - this.signRequestStartTime;
    }

    public long requestBodyByteCount() {
        return this.requestBodyByteCount;
    }

    public long responseBodyByteCount() {
        return this.responseBodyByteCount;
    }

    public double httpTaskFullTime() {
        return toSeconds(this.httpTaskTookTime);
    }

    public double dnsLookupTookTime() {
        return toSeconds(this.dnsLookupTookTime);
    }

    public double connectTookTime() {
        return toSeconds(this.connectTookTime);
    }

    public double secureConnectTookTime() {
        return toSeconds(this.secureConnectTookTime);
    }

    public double calculateMD5STookTime() {
        return toSeconds(this.calculateMD5STookTime);
    }

    public double signRequestTookTime() {
        return toSeconds(this.signRequestTookTime);
    }

    public double readResponseHeaderTookTime() {
        return toSeconds(this.readResponseHeaderTookTime);
    }

    public double readResponseBodyTookTime() {
        return toSeconds(this.readResponseBodyTookTime);
    }

    public double writeRequestBodyTookTime() {
        return toSeconds(this.writeRequestBodyTookTime);
    }

    public double writeRequestHeaderTookTime() {
        return toSeconds(this.writeRequestHeaderTookTime);
    }

    public double fullTaskTookTime() {
        return toSeconds(this.fullTaskTookTime);
    }

    public List<InetAddress> getRemoteAddress() {
        return this.remoteAddress;
    }

    public InetAddress getConnectAddress() {
        return this.connectAddress;
    }

    public String getDomainName() {
        return this.domainName;
    }

    public void setDomainName(String str) {
        this.domainName = str;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(int i) {
        this.retryCount = i;
    }

    public boolean isClockSkewedRetry() {
        return this.isClockSkewedRetry;
    }

    public void setClockSkewedRetry(boolean z) {
        this.isClockSkewedRetry = z;
    }

    public static HttpTaskMetrics createMetricsWithHost(String str) {
        HttpTaskMetrics httpTaskMetrics = new HttpTaskMetrics();
        httpTaskMetrics.domainName = str;
        return httpTaskMetrics;
    }

    public void recordConnectAddress(InetAddress inetAddress) {
        if (inetAddress != null) {
            this.domainName = inetAddress.getHostName();
            this.connectAddress = inetAddress;
        }
    }

    public synchronized HttpTaskMetrics merge(HttpTaskMetrics httpTaskMetrics) {
        String str;
        if (!TextUtils.isEmpty(this.domainName) && !TextUtils.isEmpty(httpTaskMetrics.domainName) && !this.domainName.equals(httpTaskMetrics.domainName)) {
            return this;
        }
        if (TextUtils.isEmpty(this.domainName) && (str = httpTaskMetrics.domainName) != null) {
            this.domainName = str;
        }
        this.dnsLookupTookTime = Math.max(httpTaskMetrics.dnsLookupTookTime, this.dnsLookupTookTime);
        this.connectTookTime = Math.max(httpTaskMetrics.connectTookTime, this.connectTookTime);
        this.secureConnectTookTime = Math.max(httpTaskMetrics.secureConnectTookTime, this.secureConnectTookTime);
        this.writeRequestHeaderTookTime += httpTaskMetrics.writeRequestHeaderTookTime;
        this.writeRequestBodyTookTime += httpTaskMetrics.writeRequestBodyTookTime;
        this.readResponseHeaderTookTime += httpTaskMetrics.readResponseHeaderTookTime;
        this.readResponseBodyTookTime += httpTaskMetrics.readResponseBodyTookTime;
        this.requestBodyByteCount += httpTaskMetrics.requestBodyByteCount;
        this.responseBodyByteCount += httpTaskMetrics.responseBodyByteCount;
        this.fullTaskTookTime += httpTaskMetrics.fullTaskTookTime;
        this.httpTaskTookTime += httpTaskMetrics.httpTaskTookTime;
        this.calculateMD5STookTime += httpTaskMetrics.calculateMD5STookTime;
        this.signRequestTookTime += httpTaskMetrics.signRequestTookTime;
        if (httpTaskMetrics.getRemoteAddress() != null) {
            this.remoteAddress = httpTaskMetrics.getRemoteAddress();
        }
        if (httpTaskMetrics.connectAddress != null) {
            this.connectAddress = httpTaskMetrics.getConnectAddress();
        }
        this.retryCount += httpTaskMetrics.retryCount;
        if (!this.isClockSkewedRetry) {
            this.isClockSkewedRetry = httpTaskMetrics.isClockSkewedRetry;
        }
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Http Metrics: \n");
        sb.append("domain : ");
        sb.append(this.domainName);
        sb.append("\n");
        sb.append("retryCount : ");
        sb.append(this.retryCount);
        sb.append("\n");
        sb.append("isClockSkewedRetry : ");
        sb.append(this.isClockSkewedRetry);
        sb.append("\n");
        sb.append("dns : ");
        InetAddress inetAddress = this.connectAddress;
        sb.append(inetAddress != null ? inetAddress.getHostAddress() : "null");
        sb.append("\n");
        sb.append("fullTaskTookTime : ");
        sb.append(fullTaskTookTime());
        sb.append("\n");
        sb.append("calculateMD5STookTime : ");
        sb.append(calculateMD5STookTime());
        sb.append("\n");
        sb.append("signRequestTookTime : ");
        sb.append(signRequestTookTime());
        sb.append("\n");
        sb.append("dnsStartTimestamp : ");
        sb.append(this.dnsStartTimestamp);
        sb.append("\n");
        sb.append("dnsLookupTookTime : ");
        sb.append(dnsLookupTookTime());
        sb.append("\n");
        sb.append("connectStartTimestamp : ");
        sb.append(this.connectStartTimestamp);
        sb.append("\n");
        sb.append("connectTookTime : ");
        sb.append(connectTookTime());
        sb.append("\n");
        sb.append("secureConnectStartTimestamp : ");
        sb.append(this.secureConnectStartTimestamp);
        sb.append("\n");
        sb.append("secureConnectTookTime : ");
        sb.append(secureConnectTookTime());
        sb.append("\n");
        sb.append("writeRequestHeaderStartTimestamp : ");
        sb.append(this.writeRequestHeaderStartTimestamp);
        sb.append("\n");
        sb.append("writeRequestHeaderTookTime : ");
        sb.append(writeRequestHeaderTookTime());
        sb.append("\n");
        sb.append("writeRequestBodyStartTimestamp : ");
        sb.append(this.writeRequestBodyStartTimestamp);
        sb.append("\n");
        sb.append("writeRequestBodyTookTime : ");
        sb.append(writeRequestBodyTookTime());
        sb.append("\n");
        sb.append("readResponseHeaderStartTimestamp : ");
        sb.append(this.readResponseHeaderStartTimestamp);
        sb.append("\n");
        sb.append("readResponseHeaderTookTime : ");
        sb.append(readResponseHeaderTookTime());
        sb.append("\n");
        sb.append("readResponseBodyStartTimestamp : ");
        sb.append(this.readResponseBodyStartTimestamp);
        sb.append("readResponseBodyTookTime : ");
        sb.append(readResponseBodyTookTime());
        return sb.toString();
    }
}

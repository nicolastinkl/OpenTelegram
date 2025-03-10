package org.slf4j.event;

/* loaded from: classes3.dex */
public enum Level {
    ERROR(40, "ERROR"),
    WARN(30, "WARN"),
    INFO(20, "INFO"),
    DEBUG(10, "DEBUG"),
    TRACE(0, "TRACE");

    private String levelStr;

    Level(int i, String str) {
        this.levelStr = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.levelStr;
    }
}

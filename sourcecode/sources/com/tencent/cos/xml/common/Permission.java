package com.tencent.cos.xml.common;

/* loaded from: classes.dex */
public enum Permission {
    READ("READ"),
    WRITE("WRITE"),
    FULL_CONTROL("FULL_CONTROL");

    private String permission;

    Permission(String str) {
        this.permission = str;
    }

    public String getPermission() {
        return this.permission;
    }

    public static Permission fromValue(String str) {
        for (Permission permission : values()) {
            if (permission.permission.equalsIgnoreCase(str)) {
                return permission;
            }
        }
        return null;
    }
}

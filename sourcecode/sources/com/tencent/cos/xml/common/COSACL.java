package com.tencent.cos.xml.common;

/* loaded from: classes.dex */
public enum COSACL {
    PRIVATE("private"),
    PUBLIC_READ("public-read"),
    PUBLIC_READ_WRITE("public-read-write"),
    DEFAULT("default");

    private String acl;

    COSACL(String str) {
        this.acl = str;
    }

    public String getAcl() {
        return this.acl;
    }

    public static COSACL fromString(String str) {
        for (COSACL cosacl : values()) {
            if (cosacl.acl.equalsIgnoreCase(str)) {
                return cosacl;
            }
        }
        return null;
    }
}

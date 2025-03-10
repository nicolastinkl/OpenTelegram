package io.openinstall.sdk;

import java.util.HashMap;

/* loaded from: classes.dex */
public class bm {
    private final String c;
    private final int d;
    private String e;
    private boolean g;
    private final HashMap<String, Integer> a = new HashMap<>();
    private final HashMap<Integer, String> b = new HashMap<>();
    private int f = Integer.MAX_VALUE;

    public bm(String str, int i) {
        this.c = str;
        this.d = i;
    }

    private String b(String str) {
        int i = this.d;
        return i == 2 ? str.toUpperCase() : i == 3 ? str.toLowerCase() : str;
    }

    public void a(int i) {
        if (i < 0 || i > this.f) {
            throw new IllegalArgumentException(this.c + " " + i + " is out of range");
        }
    }

    public void a(int i, String str) {
        a(i);
        String b = b(str);
        this.a.put(b, Integer.valueOf(i));
        this.b.put(Integer.valueOf(i), b);
    }

    public void a(String str) {
        this.e = b(str);
    }

    public void a(boolean z) {
        this.g = z;
    }

    public void b(int i) {
        this.f = i;
    }

    public void b(int i, String str) {
        a(i);
        this.a.put(b(str), Integer.valueOf(i));
    }

    public String c(int i) {
        a(i);
        String str = this.b.get(Integer.valueOf(i));
        if (str != null) {
            return str;
        }
        String num = Integer.toString(i);
        if (this.e == null) {
            return num;
        }
        return this.e + num;
    }
}

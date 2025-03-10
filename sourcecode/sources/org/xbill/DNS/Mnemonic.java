package org.xbill.DNS;

import java.util.HashMap;

/* loaded from: classes4.dex */
class Mnemonic {
    private String description;
    private String prefix;
    private int wordcase;
    private HashMap<String, Integer> strings = new HashMap<>();
    private HashMap<Integer, String> values = new HashMap<>();
    private int max = Integer.MAX_VALUE;

    public void setNumericAllowed(boolean z) {
    }

    public Mnemonic(String str, int i) {
        this.description = str;
        this.wordcase = i;
    }

    public void setMaximum(int i) {
        this.max = i;
    }

    public void setPrefix(String str) {
        this.prefix = sanitize(str);
    }

    public void check(int i) {
        if (i < 0 || i > this.max) {
            throw new IllegalArgumentException(this.description + " " + i + "is out of range");
        }
    }

    private String sanitize(String str) {
        int i = this.wordcase;
        if (i == 2) {
            return str.toUpperCase();
        }
        return i == 3 ? str.toLowerCase() : str;
    }

    public void add(int i, String str) {
        check(i);
        String sanitize = sanitize(str);
        this.strings.put(sanitize, Integer.valueOf(i));
        this.values.put(Integer.valueOf(i), sanitize);
    }

    public void addAlias(int i, String str) {
        check(i);
        this.strings.put(sanitize(str), Integer.valueOf(i));
    }

    public String getText(int i) {
        check(i);
        String str = this.values.get(Integer.valueOf(i));
        if (str != null) {
            return str;
        }
        String num = Integer.toString(i);
        if (this.prefix == null) {
            return num;
        }
        return this.prefix + num;
    }
}

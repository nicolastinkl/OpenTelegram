package com.google.android.exoplayer2.source.dash.manifest;

import com.tencent.qimei.o.d;
import java.util.Locale;

/* loaded from: classes.dex */
public final class UrlTemplate {
    private final int identifierCount;
    private final String[] identifierFormatTags;
    private final int[] identifiers;
    private final String[] urlPieces;

    public static UrlTemplate compile(String str) {
        String[] strArr = new String[5];
        int[] iArr = new int[4];
        String[] strArr2 = new String[4];
        return new UrlTemplate(strArr, iArr, strArr2, parseTemplate(str, strArr, iArr, strArr2));
    }

    private UrlTemplate(String[] strArr, int[] iArr, String[] strArr2, int i) {
        this.urlPieces = strArr;
        this.identifiers = iArr;
        this.identifierFormatTags = strArr2;
        this.identifierCount = i;
    }

    public String buildUri(String str, long j, int i, long j2) {
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (true) {
            int i3 = this.identifierCount;
            if (i2 < i3) {
                sb.append(this.urlPieces[i2]);
                int[] iArr = this.identifiers;
                if (iArr[i2] == 1) {
                    sb.append(str);
                } else if (iArr[i2] == 2) {
                    sb.append(String.format(Locale.US, this.identifierFormatTags[i2], Long.valueOf(j)));
                } else if (iArr[i2] == 3) {
                    sb.append(String.format(Locale.US, this.identifierFormatTags[i2], Integer.valueOf(i)));
                } else if (iArr[i2] == 4) {
                    sb.append(String.format(Locale.US, this.identifierFormatTags[i2], Long.valueOf(j2)));
                }
                i2++;
            } else {
                sb.append(this.urlPieces[i3]);
                return sb.toString();
            }
        }
    }

    private static int parseTemplate(String str, String[] strArr, int[] iArr, String[] strArr2) {
        String str2;
        strArr[0] = "";
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            int indexOf = str.indexOf("$", i);
            if (indexOf == -1) {
                strArr[i2] = strArr[i2] + str.substring(i);
                i = str.length();
            } else if (indexOf != i) {
                strArr[i2] = strArr[i2] + str.substring(i, indexOf);
                i = indexOf;
            } else if (str.startsWith("$$", i)) {
                strArr[i2] = strArr[i2] + "$";
                i += 2;
            } else {
                int i3 = i + 1;
                int indexOf2 = str.indexOf("$", i3);
                String substring = str.substring(i3, indexOf2);
                if (substring.equals("RepresentationID")) {
                    iArr[i2] = 1;
                } else {
                    int indexOf3 = substring.indexOf("%0");
                    if (indexOf3 != -1) {
                        str2 = substring.substring(indexOf3);
                        if (!str2.endsWith(d.a) && !str2.endsWith("x") && !str2.endsWith("X")) {
                            str2 = str2 + d.a;
                        }
                        substring = substring.substring(0, indexOf3);
                    } else {
                        str2 = "%01d";
                    }
                    substring.hashCode();
                    switch (substring) {
                        case "Number":
                            iArr[i2] = 2;
                            break;
                        case "Time":
                            iArr[i2] = 4;
                            break;
                        case "Bandwidth":
                            iArr[i2] = 3;
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid template: " + str);
                    }
                    strArr2[i2] = str2;
                }
                i2++;
                strArr[i2] = "";
                i = indexOf2 + 1;
            }
        }
        return i2;
    }
}

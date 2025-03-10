package com.tencent.qcloud.core.util;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class QCloudHttpUtils {
    public static Map<String, List<String>> getDecodedQueryPair(URL url) {
        int i;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (url.getQuery() != null) {
            for (String str : url.getQuery().split("&")) {
                int indexOf = str.indexOf("=");
                String urlDecodeString = indexOf > 0 ? urlDecodeString(str.substring(0, indexOf)) : str;
                if (!linkedHashMap.containsKey(urlDecodeString)) {
                    linkedHashMap.put(urlDecodeString, new LinkedList());
                }
                ((List) linkedHashMap.get(urlDecodeString)).add((indexOf <= 0 || str.length() <= (i = indexOf + 1)) ? null : urlDecodeString(str.substring(i)));
            }
        }
        return linkedHashMap;
    }

    public static Map<String, List<String>> getQueryPair(URL url) {
        int i;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (url.getQuery() != null) {
            for (String str : url.getQuery().split("&")) {
                int indexOf = str.indexOf("=");
                String substring = indexOf > 0 ? str.substring(0, indexOf) : str;
                if (!linkedHashMap.containsKey(substring)) {
                    linkedHashMap.put(substring, new LinkedList());
                }
                ((List) linkedHashMap.get(substring)).add((indexOf <= 0 || str.length() <= (i = indexOf + 1)) ? null : str.substring(i));
            }
        }
        return linkedHashMap;
    }

    public static Map<String, List<String>> transformToMultiMap(Map<String, String> map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(entry.getValue());
            hashMap.put(entry.getKey(), arrayList);
        }
        return hashMap;
    }

    public static long[] parseContentRange(String str) {
        if (QCloudStringUtils.isEmpty(str)) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf(" ");
        int indexOf = str.indexOf("-");
        int indexOf2 = str.indexOf("/");
        if (lastIndexOf == -1 || indexOf == -1 || indexOf2 == -1) {
            return null;
        }
        return new long[]{Long.parseLong(str.substring(lastIndexOf + 1, indexOf)), Long.parseLong(str.substring(indexOf + 1, indexOf2)), Long.parseLong(str.substring(indexOf2 + 1))};
    }

    public static String urlEncodeString(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return str;
            }
            StringBuilder sb = new StringBuilder();
            String[] split = str.split(" ", -1);
            int length = split.length;
            for (int i = 0; i < length; i++) {
                if (i == 0 && "".equals(split[i])) {
                    sb.append("%20");
                } else {
                    if (length > 1 && i == length - 1 && "".equals(split[i])) {
                        break;
                    }
                    sb.append(URLEncoder.encode(split[i], "UTF-8"));
                    if (i != length - 1) {
                        sb.append("%20");
                    }
                }
            }
            return sb.toString().replaceAll("\\*", "%2A");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String urlDecodeString(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

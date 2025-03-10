package cn.jzvd;

import java.util.HashMap;
import java.util.LinkedHashMap;

/* loaded from: classes.dex */
public class JZDataSource {
    public int currentUrlIndex;
    public HashMap<String, String> headerMap;
    public boolean looping;
    public String title;
    public LinkedHashMap urlsMap;

    public JZDataSource(String url) {
        this.urlsMap = new LinkedHashMap();
        this.title = "";
        this.headerMap = new HashMap<>();
        this.looping = false;
        this.urlsMap.put("URL_KEY_DEFAULT", url);
        this.currentUrlIndex = 0;
    }

    public JZDataSource(String url, String title) {
        this.urlsMap = new LinkedHashMap();
        this.title = "";
        this.headerMap = new HashMap<>();
        this.looping = false;
        this.urlsMap.put("URL_KEY_DEFAULT", url);
        this.title = title;
        this.currentUrlIndex = 0;
    }

    public JZDataSource(LinkedHashMap urlsMap, String title) {
        this.urlsMap = new LinkedHashMap();
        this.title = "";
        this.headerMap = new HashMap<>();
        this.looping = false;
        this.urlsMap.clear();
        this.urlsMap.putAll(urlsMap);
        this.title = title;
        this.currentUrlIndex = 0;
    }

    public Object getCurrentUrl() {
        return getValueFromLinkedMap(this.currentUrlIndex);
    }

    public Object getCurrentKey() {
        return getKeyFromDataSource(this.currentUrlIndex);
    }

    public String getKeyFromDataSource(int index) {
        int i = 0;
        for (Object obj : this.urlsMap.keySet()) {
            if (i == index) {
                return obj.toString();
            }
            i++;
        }
        return null;
    }

    public Object getValueFromLinkedMap(int index) {
        int i = 0;
        for (Object obj : this.urlsMap.keySet()) {
            if (i == index) {
                return this.urlsMap.get(obj);
            }
            i++;
        }
        return null;
    }

    public JZDataSource cloneMe() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.putAll(this.urlsMap);
        return new JZDataSource(linkedHashMap, this.title);
    }
}

package com.tencent.cos.xml.model.tag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class ACLAccount {
    List<String> idList = new ArrayList();

    public void addAccount(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        this.idList.add(String.format("id=\"qcs::cam::uin/%s:uin/%s\"", str, str2));
    }

    public void addAccount(String str) {
        addAccount(str, str);
    }

    public String getAccount() {
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = this.idList.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append(",");
        }
        String sb2 = sb.toString();
        int lastIndexOf = sb2.lastIndexOf(",");
        if (lastIndexOf > 0) {
            return sb2.substring(0, lastIndexOf);
        }
        return null;
    }
}

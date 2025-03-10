package com.tencent.qmsp.sdk.c;

import android.os.Bundle;
import android.util.Pair;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* loaded from: classes.dex */
public class j {
    private static final byte[][] c = {new byte[]{20, 67, -74, 67, 2, 50, 117, -18}, new byte[]{51, 117, -95, 83, 39, 52, 121}, new byte[]{9, 121, -79, 101, 32, 47, 101, -28}, new byte[]{44, 116}, new byte[]{49, 105, -93, 69}, new byte[]{35, 124, -78, 71, 61}, new byte[]{53, 113, -89, 72}, new byte[]{43, 113, -66, 69}, new byte[]{51, 113, -65, 85, 43}, new byte[]{51, 117, -95}, new byte[]{20, 125, -96, 80, 96, 63, 118, -23}};
    private List<a> a;
    private int b;

    public static class a {
        int a;
        int b;
        int c;
        String d;
        String e;
    }

    public j() {
        this.b = 1;
        Pair<Integer, List<a>> a2 = a(c(), 1);
        if (a2 != null) {
            this.b = ((Integer) a2.first).intValue();
            this.a = (List) a2.second;
        }
        if (this.a == null) {
            this.a = new LinkedList();
        }
    }

    private int a(Element element) {
        try {
            NamedNodeMap attributes = element.getAttributes();
            if (attributes == null) {
                return 0;
            }
            for (int i = 0; i < attributes.getLength(); i++) {
                Node item = attributes.item(i);
                String nodeName = item.getNodeName();
                if (nodeName != null && nodeName.equalsIgnoreCase(b(1))) {
                    return Integer.parseInt(item.getNodeValue());
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Pair<Integer, List<a>> a(String str, int i) {
        byte[] a2 = new m().a(str, null, 1);
        if (a2 != null) {
            return a(a2, i);
        }
        return null;
    }

    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0093: MOVE (r1 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:56:0x0093 */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0096 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.util.Pair<java.lang.Integer, java.util.List<com.tencent.qmsp.sdk.c.j.a>> a(byte[] r9, int r10) {
        /*
            r8 = this;
            javax.xml.parsers.DocumentBuilderFactory r0 = javax.xml.parsers.DocumentBuilderFactory.newInstance()
            r1 = 0
            javax.xml.parsers.DocumentBuilder r0 = r0.newDocumentBuilder()     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            r2.<init>(r9)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            org.w3c.dom.Document r9 = r0.parse(r2)     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            org.w3c.dom.Element r9 = r9.getDocumentElement()     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            int r0 = r8.a(r9)     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            if (r0 == 0) goto L6c
            if (r0 <= r10) goto L1f
            goto L6c
        L1f:
            java.util.LinkedList r10 = new java.util.LinkedList     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            r10.<init>()     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            org.w3c.dom.NodeList r9 = r9.getChildNodes()     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            int r3 = r9.getLength()     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            r4 = 0
        L2d:
            if (r4 >= r3) goto L5a
            org.w3c.dom.Node r5 = r9.item(r4)     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            short r6 = r5.getNodeType()     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            r7 = 1
            if (r6 == r7) goto L3b
            goto L57
        L3b:
            java.lang.String r6 = r5.getNodeName()     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            if (r6 == 0) goto L57
            r7 = 2
            java.lang.String r7 = r8.b(r7)     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            boolean r6 = r6.equalsIgnoreCase(r7)     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            if (r6 != 0) goto L4d
            goto L57
        L4d:
            com.tencent.qmsp.sdk.c.j$a r5 = r8.a(r5)     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            if (r5 != 0) goto L54
            goto L57
        L54:
            r10.add(r5)     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
        L57:
            int r4 = r4 + 1
            goto L2d
        L5a:
            android.util.Pair r9 = new android.util.Pair     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            r9.<init>(r0, r10)     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            r2.close()     // Catch: java.io.IOException -> L67
            goto L6b
        L67:
            r10 = move-exception
            r10.printStackTrace()
        L6b:
            return r9
        L6c:
            android.util.Pair r9 = new android.util.Pair     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            java.lang.Integer r10 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            r9.<init>(r10, r1)     // Catch: java.lang.Exception -> L7e java.lang.Throwable -> L92
            r2.close()     // Catch: java.io.IOException -> L79
            goto L7d
        L79:
            r10 = move-exception
            r10.printStackTrace()
        L7d:
            return r9
        L7e:
            r9 = move-exception
            goto L84
        L80:
            r9 = move-exception
            goto L94
        L82:
            r9 = move-exception
            r2 = r1
        L84:
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L92
            if (r2 == 0) goto L91
            r2.close()     // Catch: java.io.IOException -> L8d
            goto L91
        L8d:
            r9 = move-exception
            r9.printStackTrace()
        L91:
            return r1
        L92:
            r9 = move-exception
            r1 = r2
        L94:
            if (r1 == 0) goto L9e
            r1.close()     // Catch: java.io.IOException -> L9a
            goto L9e
        L9a:
            r10 = move-exception
            r10.printStackTrace()
        L9e:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.c.j.a(byte[], int):android.util.Pair");
    }

    private a a(Node node) {
        try {
            NamedNodeMap attributes = node.getAttributes();
            if (attributes == null) {
                return null;
            }
            a aVar = new a();
            int length = attributes.getLength();
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                Node item = attributes.item(i2);
                if (item.getNodeType() == 2) {
                    String nodeName = item.getNodeName();
                    String nodeValue = item.getNodeValue();
                    if (nodeName != null && nodeValue != null) {
                        if (nodeName.equalsIgnoreCase(b(3))) {
                            aVar.a = Integer.parseInt(nodeValue);
                        } else if (nodeName.equalsIgnoreCase(b(4))) {
                            aVar.b = Integer.parseInt(nodeValue);
                        } else if (nodeName.equalsIgnoreCase(b(5))) {
                            aVar.c = Integer.parseInt(nodeValue);
                        } else if (nodeName.equalsIgnoreCase(b(6))) {
                            aVar.e = nodeValue;
                        } else if (nodeName.equalsIgnoreCase(b(9))) {
                            aVar.d = nodeValue;
                        }
                        i++;
                    }
                }
            }
            if (i != 5) {
                return null;
            }
            b(node);
            return aVar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean a(String str, int i, List<a> list) {
        byte[] a2 = a(i, list);
        if (a2 == null) {
            return false;
        }
        return new m().a(str, a2, null, 1);
    }

    /* JADX WARN: Not initialized variable reg: 2, insn: 0x00b3: MOVE (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:31:0x00b3 */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private byte[] a(int r8, java.util.List<com.tencent.qmsp.sdk.c.j.a> r9) {
        /*
            r7 = this;
            r0 = 0
            org.xmlpull.v1.XmlSerializer r1 = android.util.Xml.newSerializer()     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La2
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La2
            r2.<init>()     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La2
            java.lang.String r3 = "UTF-8"
            r1.setOutput(r2, r3)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            java.lang.Boolean r3 = java.lang.Boolean.TRUE     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r1.startDocument(r0, r3)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r3 = 0
            java.lang.String r4 = r7.b(r3)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r1.startTag(r0, r4)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r4 = 1
            java.lang.String r4 = r7.b(r4)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            java.lang.String r8 = java.lang.Integer.toString(r8)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r1.attribute(r0, r4, r8)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            java.util.Iterator r8 = r9.iterator()     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
        L2c:
            boolean r9 = r8.hasNext()     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            if (r9 == 0) goto L87
            java.lang.Object r9 = r8.next()     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            com.tencent.qmsp.sdk.c.j$a r9 = (com.tencent.qmsp.sdk.c.j.a) r9     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r4 = 2
            java.lang.String r5 = r7.b(r4)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r1.startTag(r0, r5)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r5 = 3
            java.lang.String r5 = r7.b(r5)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            int r6 = r9.a     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r1.attribute(r0, r5, r6)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r5 = 4
            java.lang.String r5 = r7.b(r5)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            int r6 = r9.b     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r1.attribute(r0, r5, r6)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r5 = 5
            java.lang.String r5 = r7.b(r5)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            int r6 = r9.c     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r1.attribute(r0, r5, r6)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r5 = 9
            java.lang.String r5 = r7.b(r5)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            java.lang.String r6 = r9.d     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r1.attribute(r0, r5, r6)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r5 = 6
            java.lang.String r5 = r7.b(r5)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            java.lang.String r9 = r9.e     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r1.attribute(r0, r5, r9)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            java.lang.String r9 = r7.b(r4)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r1.endTag(r0, r9)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            goto L2c
        L87:
            java.lang.String r8 = r7.b(r3)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r1.endTag(r0, r8)     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r1.endDocument()     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            byte[] r8 = r2.toByteArray()     // Catch: java.lang.Exception -> L9e java.lang.Throwable -> Lb2
            r2.close()     // Catch: java.io.IOException -> L99
            goto L9d
        L99:
            r9 = move-exception
            r9.printStackTrace()
        L9d:
            return r8
        L9e:
            r8 = move-exception
            goto La4
        La0:
            r8 = move-exception
            goto Lb4
        La2:
            r8 = move-exception
            r2 = r0
        La4:
            r8.printStackTrace()     // Catch: java.lang.Throwable -> Lb2
            if (r2 == 0) goto Lb1
            r2.close()     // Catch: java.io.IOException -> Lad
            goto Lb1
        Lad:
            r8 = move-exception
            r8.printStackTrace()
        Lb1:
            return r0
        Lb2:
            r8 = move-exception
            r0 = r2
        Lb4:
            if (r0 == 0) goto Lbe
            r0.close()     // Catch: java.io.IOException -> Lba
            goto Lbe
        Lba:
            r9 = move-exception
            r9.printStackTrace()
        Lbe:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.c.j.a(int, java.util.List):byte[]");
    }

    private Bundle b(Node node) {
        NamedNodeMap attributes;
        Node namedItem;
        String nodeValue;
        Node namedItem2;
        String nodeValue2;
        try {
            Bundle bundle = new Bundle();
            NodeList childNodes = node.getChildNodes();
            int length = childNodes.getLength();
            for (int i = 0; i < length; i++) {
                Node item = childNodes.item(i);
                if (item.getNodeType() == 1 && (attributes = item.getAttributes()) != null && (namedItem = attributes.getNamedItem(b(7))) != null && namedItem.getNodeType() == 2 && (nodeValue = namedItem.getNodeValue()) != null && (namedItem2 = attributes.getNamedItem(b(8))) != null && namedItem2.getNodeType() == 2 && (nodeValue2 = namedItem2.getNodeValue()) != null) {
                    bundle.putString(nodeValue, nodeValue2);
                }
            }
            return bundle;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String b(int i) {
        return com.tencent.qmsp.sdk.f.h.a(c[i]);
    }

    private String c() {
        return com.tencent.qmsp.sdk.a.b.a() + File.separator + b(10);
    }

    private boolean c(int i) {
        return d(i) != null;
    }

    private a d(int i) {
        for (a aVar : this.a) {
            if (aVar.a == i) {
                return aVar;
            }
        }
        return null;
    }

    public a a(int i) {
        return d(i);
    }

    public void a(int i, boolean z) {
        a d = d(i);
        if (d == null) {
            return;
        }
        this.a.remove(d);
        if (z) {
            a(c(), this.b, this.a);
        }
    }

    public boolean a() {
        return a(c(), this.b, this.a);
    }

    public boolean a(a aVar, boolean z) {
        if (c(aVar.a)) {
            return false;
        }
        this.a.add(aVar);
        if (z) {
            return a();
        }
        return true;
    }

    public List<a> b() {
        return this.a;
    }
}

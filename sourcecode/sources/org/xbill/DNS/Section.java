package org.xbill.DNS;

/* loaded from: classes4.dex */
public final class Section {
    private static String[] longSections;
    private static Mnemonic sections;
    private static String[] updateSections;

    static {
        Mnemonic mnemonic = new Mnemonic("Message Section", 3);
        sections = mnemonic;
        longSections = new String[4];
        updateSections = new String[4];
        mnemonic.setMaximum(3);
        sections.setNumericAllowed(true);
        sections.add(0, "qd");
        sections.add(1, "an");
        sections.add(2, "au");
        sections.add(3, "ad");
        String[] strArr = longSections;
        strArr[0] = "QUESTIONS";
        strArr[1] = "ANSWERS";
        strArr[2] = "AUTHORITY RECORDS";
        strArr[3] = "ADDITIONAL RECORDS";
        String[] strArr2 = updateSections;
        strArr2[0] = "ZONE";
        strArr2[1] = "PREREQUISITES";
        strArr2[2] = "UPDATE RECORDS";
        strArr2[3] = "ADDITIONAL RECORDS";
    }

    public static String string(int i) {
        return sections.getText(i);
    }

    public static String longString(int i) {
        sections.check(i);
        return longSections[i];
    }

    public static String updString(int i) {
        sections.check(i);
        return updateSections[i];
    }
}

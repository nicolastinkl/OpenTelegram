package org.telegram.ui;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class CashierInputFilter implements InputFilter {
    Pattern mPattern = Pattern.compile("([0-9]|\\.)*");

    @Override // android.text.InputFilter
    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        String charSequence2 = charSequence.toString();
        String obj = spanned.toString();
        if (TextUtils.isEmpty(charSequence2)) {
            return "";
        }
        Matcher matcher = this.mPattern.matcher(charSequence);
        if (obj.contains(".")) {
            if (!matcher.matches() || ".".equals(charSequence.toString())) {
                return "";
            }
            if (i4 - obj.indexOf(".") > 2) {
                return spanned.subSequence(i3, i4);
            }
        } else {
            if (!matcher.matches()) {
                return "";
            }
            if (".".equals(charSequence.toString()) && TextUtils.isEmpty(obj)) {
                return "";
            }
            if (!".".equals(charSequence.toString()) && "0".equals(obj)) {
                return "";
            }
        }
        if (Double.parseDouble(obj + charSequence2) > 100000.0d) {
            return spanned.subSequence(i3, i4);
        }
        return ((Object) spanned.subSequence(i3, i4)) + charSequence2;
    }
}

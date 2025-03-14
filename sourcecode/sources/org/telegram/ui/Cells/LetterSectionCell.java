package org.telegram.ui.Cells;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

/* loaded from: classes4.dex */
public class LetterSectionCell extends FrameLayout {
    private TextView textView;

    public LetterSectionCell(Context context) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(AndroidUtilities.dp(54.0f), AndroidUtilities.dp(64.0f)));
        TextView textView = new TextView(getContext());
        this.textView = textView;
        textView.setTextSize(1, 22.0f);
        this.textView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText4));
        this.textView.setGravity(17);
        addView(this.textView, LayoutHelper.createFrame(-1, -1.0f));
    }

    public void setLetter(String str) {
        this.textView.setText(str.toUpperCase());
    }

    public void setCellHeight(int i) {
        setLayoutParams(new ViewGroup.LayoutParams(AndroidUtilities.dp(54.0f), i));
    }
}

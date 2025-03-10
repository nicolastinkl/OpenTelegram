package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Typeface;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.Theme;

/* loaded from: classes4.dex */
public class OutlineEditText extends OutlineTextContainerView {
    EditTextBoldCursor editText;

    public OutlineEditText(Context context) {
        super(context);
        EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(context) { // from class: org.telegram.ui.Components.OutlineEditText.1
            @Override // org.telegram.ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
            protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
                super.onFocusChanged(z, i, rect);
                OutlineEditText.this.animateSelection((z || isFocused()) ? 1.0f : 0.0f);
            }
        };
        this.editText = editTextBoldCursor;
        editTextBoldCursor.setTextSize(1, 18.0f);
        this.editText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.editText.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.editText.setBackground(null);
        this.editText.setSingleLine(true);
        this.editText.setInputType(1);
        this.editText.setTypeface(Typeface.DEFAULT);
        this.editText.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated));
        this.editText.setCursorWidth(1.5f);
        this.editText.setPadding(AndroidUtilities.dp(15.0f), 0, AndroidUtilities.dp(15.0f), 0);
        attachEditText(this.editText);
        addView(this.editText, LayoutHelper.createFrame(-1, -2, 16));
    }

    public OutlineEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(context) { // from class: org.telegram.ui.Components.OutlineEditText.2
            @Override // org.telegram.ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
            protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
                super.onFocusChanged(z, i, rect);
                OutlineEditText.this.animateSelection((z || isFocused()) ? 1.0f : 0.0f);
            }
        };
        this.editText = editTextBoldCursor;
        editTextBoldCursor.setTextSize(1, 18.0f);
        this.editText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.editText.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.editText.setBackground(null);
        this.editText.setSingleLine(true);
        this.editText.setInputType(1);
        this.editText.setTypeface(Typeface.DEFAULT);
        this.editText.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated));
        this.editText.setCursorWidth(1.5f);
        this.editText.setPadding(AndroidUtilities.dp(15.0f), 0, AndroidUtilities.dp(15.0f), 0);
        attachEditText(this.editText);
        addView(this.editText, LayoutHelper.createFrame(-1, -2, 16));
    }

    public void setHint(String str) {
        setText(str);
    }

    public EditTextBoldCursor getEditText() {
        return this.editText;
    }

    public void setShowEye(boolean z) {
        if (z) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.mipmap.ic_eye_line_24);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.OutlineEditText.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (OutlineEditText.this.editText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                        OutlineEditText.this.editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        OutlineEditText.this.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }
            });
            addView(imageView, LayoutHelper.createFrame(24, 24.0f, 8388629, 0.0f, 0.0f, 12.0f, 0.0f));
        }
    }
}

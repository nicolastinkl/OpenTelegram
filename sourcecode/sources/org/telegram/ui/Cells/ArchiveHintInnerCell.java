package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

/* loaded from: classes4.dex */
public class ArchiveHintInnerCell extends FrameLayout {
    private TextView headerTextView;
    private ImageView imageView;
    private ImageView imageView2;
    private TextView messageTextView;

    public ArchiveHintInnerCell(Context context, int i) {
        super(context);
        ImageView imageView = new ImageView(context);
        this.imageView = imageView;
        int i2 = Theme.key_chats_nameMessage_threeLines;
        imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i2), PorterDuff.Mode.MULTIPLY));
        TextView textView = new TextView(context);
        this.headerTextView = textView;
        textView.setTextColor(Theme.getColor(i2));
        this.headerTextView.setTextSize(1, 20.0f);
        this.headerTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        this.headerTextView.setGravity(17);
        addView(this.headerTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 52.0f, 75.0f, 52.0f, 0.0f));
        TextView textView2 = new TextView(context);
        this.messageTextView = textView2;
        textView2.setTextColor(Theme.getColor(Theme.key_chats_message));
        this.messageTextView.setTextSize(1, 14.0f);
        this.messageTextView.setGravity(17);
        addView(this.messageTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 52.0f, 110.0f, 52.0f, 0.0f));
        if (i == 0) {
            addView(this.imageView, LayoutHelper.createFrame(-2, -2.0f, 49, 0.0f, 20.0f, 8.0f, 0.0f));
            ImageView imageView2 = new ImageView(context);
            this.imageView2 = imageView2;
            imageView2.setImageResource(R.drawable.chats_archive_arrow);
            this.imageView2.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_unreadCounter), PorterDuff.Mode.MULTIPLY));
            addView(this.imageView2, LayoutHelper.createFrame(-2, -2.0f, 49, 0.0f, 20.0f, 8.0f, 0.0f));
            this.headerTextView.setText(LocaleController.getString("ArchiveHintHeader1", R.string.ArchiveHintHeader1));
            this.messageTextView.setText(LocaleController.getString("ArchiveHintText1", R.string.ArchiveHintText1));
            this.imageView.setImageResource(R.drawable.chats_archive_box);
            return;
        }
        if (i == 1) {
            addView(this.imageView, LayoutHelper.createFrame(-2, -2.0f, 49, 0.0f, 18.0f, 0.0f, 0.0f));
            this.headerTextView.setText(LocaleController.getString("ArchiveHintHeader2", R.string.ArchiveHintHeader2));
            this.messageTextView.setText(LocaleController.getString("ArchiveHintText2", R.string.ArchiveHintText2));
            this.imageView.setImageResource(R.drawable.chats_archive_muted);
            return;
        }
        if (i != 2) {
            return;
        }
        addView(this.imageView, LayoutHelper.createFrame(-2, -2.0f, 49, 0.0f, 18.0f, 0.0f, 0.0f));
        this.headerTextView.setText(LocaleController.getString("ArchiveHintHeader3", R.string.ArchiveHintHeader3));
        this.messageTextView.setText(LocaleController.getString("ArchiveHintText3", R.string.ArchiveHintText3));
        this.imageView.setImageResource(R.drawable.chats_archive_pin);
    }
}

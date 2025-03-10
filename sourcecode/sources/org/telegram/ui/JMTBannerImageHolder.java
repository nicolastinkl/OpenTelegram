package org.telegram.ui;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import org.telegram.ui.Components.BackupImageView;

/* loaded from: classes3.dex */
public class JMTBannerImageHolder extends RecyclerView.ViewHolder {
    public BackupImageView imageView;

    public JMTBannerImageHolder(View view) {
        super(view);
        this.imageView = (BackupImageView) view;
    }
}

package org.telegram.ui;

import android.view.ViewGroup;
import com.youth.banner.adapter.BannerAdapter;
import java.util.List;
import org.telegram.ui.Components.BackupImageView;

/* loaded from: classes3.dex */
public abstract class JMTBannerImageAdapter<T> extends BannerAdapter<T, JMTBannerImageHolder> {
    public JMTBannerImageAdapter(List<T> list) {
        super(list);
    }

    @Override // com.youth.banner.holder.IViewHolder
    public JMTBannerImageHolder onCreateHolder(ViewGroup viewGroup, int i) {
        BackupImageView backupImageView = new BackupImageView(viewGroup.getContext());
        backupImageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        return new JMTBannerImageHolder(backupImageView);
    }
}

package org.telegram.ui;

import android.content.Context;
import android.util.AttributeSet;
import cn.jzvd.JzvdStd;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;

/* loaded from: classes3.dex */
public class JMTJzvdStdAssert extends JzvdStd {
    public JMTJzvdStdAssert(Context context) {
        super(context);
    }

    public JMTJzvdStdAssert(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // cn.jzvd.JzvdStd, cn.jzvd.Jzvd
    public int getLayoutId() {
        return R.layout.jmt_jz_layout_std;
    }

    @Override // cn.jzvd.Jzvd
    public void onPrepared() {
        this.state = 4;
        if (!this.preloading) {
            this.mediaInterface.start();
            this.preloading = false;
        }
        onStatePlaying();
    }

    @Override // cn.jzvd.JzvdStd
    public void setAllControlsVisiblity(final int i, final int i2, final int i3, final int i4, final int i5, final int i6, final int i7) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTJzvdStdAssert.1
            @Override // java.lang.Runnable
            public void run() {
                JMTJzvdStdAssert.this.topContainer.setVisibility(i);
                JMTJzvdStdAssert.this.bottomContainer.setVisibility(i2);
                JMTJzvdStdAssert.this.startButton.setVisibility(i3);
                JMTJzvdStdAssert.this.loadingProgressBar.setVisibility(i4);
                JMTJzvdStdAssert.this.posterImageView.setVisibility(i5);
                JMTJzvdStdAssert.this.bottomProgressBar.setVisibility(i6);
                JMTJzvdStdAssert.this.mRetryLayout.setVisibility(i7);
            }
        });
    }
}

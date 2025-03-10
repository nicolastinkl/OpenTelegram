package com.lxj.xpopup.interfaces;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.photoview.PhotoView;
import java.io.File;

/* loaded from: classes.dex */
public interface XPopupImageLoader {
    File getImageFile(Context context, Object uri);

    View loadImage(int position, Object uri, ImageViewerPopupView popupView, PhotoView snapshot, ProgressBar progressBar);

    void loadSnapshot(Object uri, PhotoView snapshot, ImageView srcView);
}

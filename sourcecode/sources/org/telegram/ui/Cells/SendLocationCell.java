package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocationController;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.ShareLocationDrawable;

/* loaded from: classes4.dex */
public class SendLocationCell extends FrameLayout {
    private SimpleTextView accurateTextView;
    private int currentAccount;
    private long dialogId;
    private ImageView imageView;
    private Runnable invalidateRunnable;
    private boolean live;
    private RectF rect;
    private final Theme.ResourcesProvider resourcesProvider;
    private SimpleTextView titleTextView;

    public SendLocationCell(Context context, boolean z, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        int i;
        int i2;
        this.currentAccount = UserConfig.selectedAccount;
        this.invalidateRunnable = new Runnable() { // from class: org.telegram.ui.Cells.SendLocationCell.1
            @Override // java.lang.Runnable
            public void run() {
                SendLocationCell.this.checkText();
                SendLocationCell.this.invalidate(((int) r0.rect.left) - 5, ((int) SendLocationCell.this.rect.top) - 5, ((int) SendLocationCell.this.rect.right) + 5, ((int) SendLocationCell.this.rect.bottom) + 5);
                AndroidUtilities.runOnUIThread(SendLocationCell.this.invalidateRunnable, 1000L);
            }
        };
        this.resourcesProvider = resourcesProvider;
        this.live = z;
        this.imageView = new ImageView(context);
        setBackground(Theme.AdaptiveRipple.rect());
        ImageView imageView = this.imageView;
        if (z) {
            i = Theme.key_location_sendLiveLocationBackground;
            i2 = Theme.key_location_sendLiveLocationIcon;
        } else {
            i = Theme.key_location_sendLocationBackground;
            i2 = Theme.key_location_sendLocationIcon;
        }
        imageView.setTag(Integer.valueOf(i + i2));
        Drawable createSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(42.0f), getThemedColor(z ? Theme.key_location_sendLiveLocationBackground : Theme.key_location_sendLocationBackground), getThemedColor(z ? Theme.key_location_sendLiveLocationBackground : Theme.key_location_sendLocationBackground));
        if (z) {
            this.rect = new RectF();
            ShareLocationDrawable shareLocationDrawable = new ShareLocationDrawable(context, 4);
            shareLocationDrawable.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_location_sendLiveLocationIcon), PorterDuff.Mode.MULTIPLY));
            CombinedDrawable combinedDrawable = new CombinedDrawable(createSimpleSelectorCircleDrawable, shareLocationDrawable);
            combinedDrawable.setCustomSize(AndroidUtilities.dp(42.0f), AndroidUtilities.dp(42.0f));
            this.imageView.setBackgroundDrawable(combinedDrawable);
            AndroidUtilities.runOnUIThread(this.invalidateRunnable, 1000L);
            setWillNotDraw(false);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.pin);
            drawable.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_location_sendLocationIcon), PorterDuff.Mode.MULTIPLY));
            CombinedDrawable combinedDrawable2 = new CombinedDrawable(createSimpleSelectorCircleDrawable, drawable);
            combinedDrawable2.setCustomSize(AndroidUtilities.dp(42.0f), AndroidUtilities.dp(42.0f));
            combinedDrawable2.setIconSize(AndroidUtilities.dp(24.0f), AndroidUtilities.dp(24.0f));
            this.imageView.setBackgroundDrawable(combinedDrawable2);
        }
        ImageView imageView2 = this.imageView;
        boolean z2 = LocaleController.isRTL;
        addView(imageView2, LayoutHelper.createFrame(42, 42.0f, (z2 ? 5 : 3) | 48, z2 ? 0.0f : 15.0f, 12.0f, z2 ? 15.0f : 0.0f, 0.0f));
        SimpleTextView simpleTextView = new SimpleTextView(context);
        this.titleTextView = simpleTextView;
        simpleTextView.setTextSize(16);
        this.titleTextView.setTag(Integer.valueOf(z ? Theme.key_location_sendLiveLocationText : Theme.key_location_sendLocationText));
        this.titleTextView.setTextColor(getThemedColor(z ? Theme.key_location_sendLiveLocationText : Theme.key_location_sendLocationText));
        this.titleTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        this.titleTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        SimpleTextView simpleTextView2 = this.titleTextView;
        boolean z3 = LocaleController.isRTL;
        addView(simpleTextView2, LayoutHelper.createFrame(-1, 20.0f, (z3 ? 5 : 3) | 48, z3 ? 16.0f : 73.0f, 12.0f, z3 ? 73.0f : 16.0f, 0.0f));
        SimpleTextView simpleTextView3 = new SimpleTextView(context);
        this.accurateTextView = simpleTextView3;
        simpleTextView3.setTextSize(14);
        this.accurateTextView.setTextColor(getThemedColor(Theme.key_windowBackgroundWhiteGrayText3));
        this.accurateTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        SimpleTextView simpleTextView4 = this.accurateTextView;
        boolean z4 = LocaleController.isRTL;
        addView(simpleTextView4, LayoutHelper.createFrame(-1, 20.0f, (z4 ? 5 : 3) | 48, z4 ? 16.0f : 73.0f, 37.0f, z4 ? 73.0f : 16.0f, 0.0f));
    }

    private ImageView getImageView() {
        return this.imageView;
    }

    public void setHasLocation(boolean z) {
        if (LocationController.getInstance(this.currentAccount).getSharingLocationInfo(this.dialogId) == null) {
            this.titleTextView.setAlpha(z ? 1.0f : 0.5f);
            this.accurateTextView.setAlpha(z ? 1.0f : 0.5f);
            this.imageView.setAlpha(z ? 1.0f : 0.5f);
        }
        if (this.live) {
            checkText();
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(66.0f), 1073741824));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        AndroidUtilities.cancelRunOnUIThread(this.invalidateRunnable);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.rect != null) {
            AndroidUtilities.runOnUIThread(this.invalidateRunnable, 1000L);
        }
    }

    public void setText(String str, String str2) {
        this.titleTextView.setText(str);
        this.accurateTextView.setText(str2);
    }

    public void setDialogId(long j) {
        this.dialogId = j;
        if (this.live) {
            checkText();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkText() {
        LocationController.SharingLocationInfo sharingLocationInfo = LocationController.getInstance(this.currentAccount).getSharingLocationInfo(this.dialogId);
        if (sharingLocationInfo != null) {
            String string = LocaleController.getString("StopLiveLocation", R.string.StopLiveLocation);
            int i = sharingLocationInfo.messageObject.messageOwner.edit_date;
            setText(string, LocaleController.formatLocationUpdateDate(i != 0 ? i : r0.date));
            return;
        }
        setText(LocaleController.getString("SendLiveLocation", R.string.SendLiveLocation), LocaleController.getString("SendLiveLocationInfo", R.string.SendLiveLocationInfo));
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int currentTime;
        int i;
        LocationController.SharingLocationInfo sharingLocationInfo = LocationController.getInstance(this.currentAccount).getSharingLocationInfo(this.dialogId);
        if (sharingLocationInfo != null && (i = sharingLocationInfo.stopTime) >= (currentTime = ConnectionsManager.getInstance(this.currentAccount).getCurrentTime())) {
            float abs = Math.abs(i - currentTime) / sharingLocationInfo.period;
            if (LocaleController.isRTL) {
                this.rect.set(AndroidUtilities.dp(13.0f), AndroidUtilities.dp(18.0f), AndroidUtilities.dp(43.0f), AndroidUtilities.dp(48.0f));
            } else {
                this.rect.set(getMeasuredWidth() - AndroidUtilities.dp(43.0f), AndroidUtilities.dp(18.0f), getMeasuredWidth() - AndroidUtilities.dp(13.0f), AndroidUtilities.dp(48.0f));
            }
            int themedColor = getThemedColor(Theme.key_location_liveLocationProgress);
            Theme.chat_radialProgress2Paint.setColor(themedColor);
            Theme.chat_livePaint.setColor(themedColor);
            canvas.drawArc(this.rect, -90.0f, abs * (-360.0f), false, Theme.chat_radialProgress2Paint);
            String formatLocationLeftTime = LocaleController.formatLocationLeftTime(Math.abs(sharingLocationInfo.stopTime - currentTime));
            canvas.drawText(formatLocationLeftTime, this.rect.centerX() - (Theme.chat_livePaint.measureText(formatLocationLeftTime) / 2.0f), AndroidUtilities.dp(37.0f), Theme.chat_livePaint);
        }
    }

    private int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }
}

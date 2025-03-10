package org.telegram.ui.Components;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.WallpaperCell;
import org.telegram.ui.Components.ChatAttachAlert;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.PhotoViewer;
import org.telegram.ui.WallpapersListActivity;

/* loaded from: classes4.dex */
public class ChatAttachAlertColorsLayout extends ChatAttachAlert.AttachAlertLayout {
    Adapter adapter;
    public RecyclerListView gridView;
    private int itemSize;
    private int itemsPerRow;
    GridLayoutManager layoutManager;
    Consumer<Object> wallpaperConsumer;

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    int needsActionBar() {
        return 1;
    }

    public ChatAttachAlertColorsLayout(ChatAttachAlert chatAttachAlert, Context context, Theme.ResourcesProvider resourcesProvider) {
        super(chatAttachAlert, context, resourcesProvider);
        this.itemSize = AndroidUtilities.dp(80.0f);
        this.itemsPerRow = 3;
        RecyclerListView recyclerListView = new RecyclerListView(context, resourcesProvider) { // from class: org.telegram.ui.Components.ChatAttachAlertColorsLayout.1
            @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0 || motionEvent.getY() >= ChatAttachAlertColorsLayout.this.parentAlert.scrollOffsetY[0] - AndroidUtilities.dp(80.0f)) {
                    return super.onTouchEvent(motionEvent);
                }
                return false;
            }

            @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0 || motionEvent.getY() >= ChatAttachAlertColorsLayout.this.parentAlert.scrollOffsetY[0] - AndroidUtilities.dp(80.0f)) {
                    return super.onInterceptTouchEvent(motionEvent);
                }
                return false;
            }

            @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                PhotoViewer.getInstance().checkCurrentImageVisibility();
            }
        };
        this.gridView = recyclerListView;
        Adapter adapter = new Adapter(context);
        this.adapter = adapter;
        recyclerListView.setAdapter(adapter);
        this.gridView.setClipToPadding(false);
        this.gridView.setItemAnimator(null);
        this.gridView.setLayoutAnimation(null);
        this.gridView.setVerticalScrollBarEnabled(false);
        this.gridView.setGlowColor(getThemedColor(Theme.key_dialogScrollGlow));
        addView(this.gridView, LayoutHelper.createFrame(-1, -1.0f));
        this.gridView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.Components.ChatAttachAlertColorsLayout.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                if (ChatAttachAlertColorsLayout.this.gridView.getChildCount() <= 0) {
                    return;
                }
                ChatAttachAlertColorsLayout chatAttachAlertColorsLayout = ChatAttachAlertColorsLayout.this;
                chatAttachAlertColorsLayout.parentAlert.updateLayout(chatAttachAlertColorsLayout, true, i2);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                RecyclerListView.Holder holder;
                if (i == 0) {
                    int dp = AndroidUtilities.dp(13.0f);
                    ActionBarMenuItem actionBarMenuItem = ChatAttachAlertColorsLayout.this.parentAlert.selectedMenuItem;
                    int dp2 = dp + (actionBarMenuItem != null ? AndroidUtilities.dp(actionBarMenuItem.getAlpha() * 26.0f) : 0);
                    int backgroundPaddingTop = ChatAttachAlertColorsLayout.this.parentAlert.getBackgroundPaddingTop();
                    if (((ChatAttachAlertColorsLayout.this.parentAlert.scrollOffsetY[0] - backgroundPaddingTop) - dp2) + backgroundPaddingTop >= ActionBar.getCurrentActionBarHeight() || (holder = (RecyclerListView.Holder) ChatAttachAlertColorsLayout.this.gridView.findViewHolderForAdapterPosition(0)) == null || holder.itemView.getTop() <= AndroidUtilities.dp(7.0f)) {
                        return;
                    }
                    ChatAttachAlertColorsLayout.this.gridView.smoothScrollBy(0, holder.itemView.getTop() - AndroidUtilities.dp(7.0f));
                }
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, this.itemSize) { // from class: org.telegram.ui.Components.ChatAttachAlertColorsLayout.3
            @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
                LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) { // from class: org.telegram.ui.Components.ChatAttachAlertColorsLayout.3.1
                    @Override // androidx.recyclerview.widget.LinearSmoothScroller
                    public int calculateDyToMakeVisible(View view, int i2) {
                        return super.calculateDyToMakeVisible(view, i2) - (ChatAttachAlertColorsLayout.this.gridView.getPaddingTop() - AndroidUtilities.dp(7.0f));
                    }

                    @Override // androidx.recyclerview.widget.LinearSmoothScroller
                    protected int calculateTimeForDeceleration(int i2) {
                        return super.calculateTimeForDeceleration(i2) * 2;
                    }
                };
                linearSmoothScroller.setTargetPosition(i);
                startSmoothScroll(linearSmoothScroller);
            }
        };
        this.layoutManager = gridLayoutManager;
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.Components.ChatAttachAlertColorsLayout.4
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i) {
                return ChatAttachAlertColorsLayout.this.itemSize + (i % ChatAttachAlertColorsLayout.this.itemsPerRow != ChatAttachAlertColorsLayout.this.itemsPerRow + (-1) ? AndroidUtilities.dp(5.0f) : 0);
            }
        });
        this.gridView.setLayoutManager(this.layoutManager);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    void scrollToTop() {
        this.gridView.smoothScrollToPosition(0);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    int getListTopPadding() {
        return this.gridView.getPaddingTop();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    int getCurrentItemTop() {
        if (this.gridView.getChildCount() <= 0) {
            RecyclerListView recyclerListView = this.gridView;
            recyclerListView.setTopGlowOffset(recyclerListView.getPaddingTop());
            return Integer.MAX_VALUE;
        }
        View childAt = this.gridView.getChildAt(0);
        RecyclerListView.Holder holder = (RecyclerListView.Holder) this.gridView.findContainingViewHolder(childAt);
        int top = childAt.getTop();
        int dp = AndroidUtilities.dp(7.0f);
        if (top < AndroidUtilities.dp(7.0f) || holder == null || holder.getAdapterPosition() != 0) {
            top = dp;
        }
        this.gridView.setTopGlowOffset(top);
        return top;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    int getFirstOffset() {
        return getListTopPadding() + AndroidUtilities.dp(56.0f);
    }

    @Override // android.view.View
    public void setTranslationY(float f) {
        super.setTranslationY(f);
        this.parentAlert.getSheetContainer().invalidate();
        invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onPreMeasure(int r7, int r8) {
        /*
            r6 = this;
            boolean r0 = org.telegram.messenger.AndroidUtilities.isTablet()
            r1 = 4
            if (r0 == 0) goto La
            r6.itemsPerRow = r1
            goto L18
        La:
            android.graphics.Point r0 = org.telegram.messenger.AndroidUtilities.displaySize
            int r2 = r0.x
            int r0 = r0.y
            if (r2 <= r0) goto L15
            r6.itemsPerRow = r1
            goto L18
        L15:
            r0 = 3
            r6.itemsPerRow = r0
        L18:
            android.view.ViewGroup$LayoutParams r0 = r6.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r0 = (android.widget.FrameLayout.LayoutParams) r0
            int r1 = org.telegram.ui.ActionBar.ActionBar.getCurrentActionBarHeight()
            r0.topMargin = r1
            r0 = 1094713344(0x41400000, float:12.0)
            int r0 = org.telegram.messenger.AndroidUtilities.dp(r0)
            int r7 = r7 - r0
            r0 = 1092616192(0x41200000, float:10.0)
            int r0 = org.telegram.messenger.AndroidUtilities.dp(r0)
            int r7 = r7 - r0
            int r0 = r6.itemsPerRow
            int r7 = r7 / r0
            int r0 = r6.itemSize
            if (r0 == r7) goto L40
            r6.itemSize = r7
            org.telegram.ui.Components.ChatAttachAlertColorsLayout$Adapter r0 = r6.adapter
            r0.notifyDataSetChanged()
        L40:
            androidx.recyclerview.widget.GridLayoutManager r0 = r6.layoutManager
            int r1 = r6.itemsPerRow
            int r1 = r1 * r7
            r2 = 1084227584(0x40a00000, float:5.0)
            int r3 = org.telegram.messenger.AndroidUtilities.dp(r2)
            int r4 = r6.itemsPerRow
            r5 = 1
            int r4 = r4 - r5
            int r3 = r3 * r4
            int r1 = r1 + r3
            int r1 = java.lang.Math.max(r5, r1)
            r0.setSpanCount(r1)
            org.telegram.ui.Components.ChatAttachAlertColorsLayout$Adapter r0 = r6.adapter
            int r0 = r0.getItemCount()
            int r0 = r0 - r5
            float r0 = (float) r0
            int r1 = r6.itemsPerRow
            float r1 = (float) r1
            float r0 = r0 / r1
            double r0 = (double) r0
            double r0 = java.lang.Math.ceil(r0)
            int r0 = (int) r0
            int r7 = r7 * r0
            int r0 = r0 - r5
            int r1 = org.telegram.messenger.AndroidUtilities.dp(r2)
            int r0 = r0 * r1
            int r7 = r7 + r0
            int r7 = r8 - r7
            int r0 = org.telegram.ui.ActionBar.ActionBar.getCurrentActionBarHeight()
            int r7 = r7 - r0
            r0 = 1114636288(0x42700000, float:60.0)
            int r0 = org.telegram.messenger.AndroidUtilities.dp(r0)
            int r7 = r7 - r0
            r0 = 0
            java.lang.Math.max(r0, r7)
            boolean r7 = org.telegram.messenger.AndroidUtilities.isTablet()
            if (r7 != 0) goto L9c
            android.graphics.Point r7 = org.telegram.messenger.AndroidUtilities.displaySize
            int r1 = r7.x
            int r7 = r7.y
            if (r1 <= r7) goto L9c
            float r7 = (float) r8
            r8 = 1080033280(0x40600000, float:3.5)
            float r7 = r7 / r8
            int r7 = (int) r7
            goto La0
        L9c:
            int r8 = r8 / 5
            int r7 = r8 * 2
        La0:
            r8 = 1112539136(0x42500000, float:52.0)
            int r8 = org.telegram.messenger.AndroidUtilities.dp(r8)
            int r7 = r7 - r8
            if (r7 >= 0) goto Laa
            goto Lab
        Laa:
            r0 = r7
        Lab:
            org.telegram.ui.Components.RecyclerListView r7 = r6.gridView
            int r7 = r7.getPaddingTop()
            if (r7 == r0) goto Lc8
            org.telegram.ui.Components.RecyclerListView r7 = r6.gridView
            r8 = 1086324736(0x40c00000, float:6.0)
            int r1 = org.telegram.messenger.AndroidUtilities.dp(r8)
            int r8 = org.telegram.messenger.AndroidUtilities.dp(r8)
            r2 = 1111490560(0x42400000, float:48.0)
            int r2 = org.telegram.messenger.AndroidUtilities.dp(r2)
            r7.setPadding(r1, r0, r8, r2)
        Lc8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.ChatAttachAlertColorsLayout.onPreMeasure(int, int):void");
    }

    public void setDelegate(Consumer<Object> consumer) {
        this.wallpaperConsumer = consumer;
    }

    public void updateColors(boolean z) {
        this.adapter.wallpapers.clear();
        WallpapersListActivity.fillDefaultColors(this.adapter.wallpapers, z);
        this.adapter.notifyDataSetChanged();
    }

    private class Adapter extends RecyclerListView.SelectionAdapter {
        private Context mContext;
        private final ArrayList<Object> wallpapers = new ArrayList<>();

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            return 0;
        }

        public Adapter(Context context) {
            this.mContext = context;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.wallpapers.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            WallpaperCell wallpaperCell = new WallpaperCell(this.mContext, 1) { // from class: org.telegram.ui.Components.ChatAttachAlertColorsLayout.Adapter.1
                @Override // org.telegram.ui.Cells.WallpaperCell
                protected void onWallpaperClick(Object obj, int i2) {
                    Consumer<Object> consumer = ChatAttachAlertColorsLayout.this.wallpaperConsumer;
                    if (consumer != null) {
                        consumer.accept(obj);
                    }
                }
            };
            wallpaperCell.drawStubBackground = false;
            return new RecyclerListView.Holder(wallpaperCell);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            WallpaperCell wallpaperCell = (WallpaperCell) viewHolder.itemView;
            wallpaperCell.setParams(1, false, false);
            wallpaperCell.setSize(ChatAttachAlertColorsLayout.this.itemSize);
            wallpaperCell.setWallpaper(1, 0, this.wallpapers.get(i), null, null, false);
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    void onShow(ChatAttachAlert.AttachAlertLayout attachAlertLayout) {
        try {
            this.parentAlert.actionBar.getTitleTextView().setBuildFullLayout(true);
        } catch (Exception unused) {
        }
        this.parentAlert.actionBar.setTitle(LocaleController.getString("SelectColor", R.string.SelectColor));
        this.layoutManager.scrollToPositionWithOffset(0, 0);
    }
}

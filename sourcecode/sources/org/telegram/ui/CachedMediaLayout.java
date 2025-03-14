package org.telegram.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$Peer;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_documentAttributeFilename;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarPopupWindow;
import org.telegram.ui.ActionBar.BackDrawable;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.CacheControlActivity;
import org.telegram.ui.CachedMediaLayout;
import org.telegram.ui.Cells.SharedAudioCell;
import org.telegram.ui.Cells.SharedDocumentCell;
import org.telegram.ui.Cells.SharedPhotoVideoCell2;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.AnimatedTextView;
import org.telegram.ui.Components.CheckBox2;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.ListView.AdapterWithDiffUtils;
import org.telegram.ui.Components.NestedSizeNotifierLayout;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.ViewPagerFixed;
import org.telegram.ui.PhotoViewer;
import org.telegram.ui.Storage.CacheModel;

/* loaded from: classes3.dex */
public class CachedMediaLayout extends FrameLayout implements NestedSizeNotifierLayout.ChildLayout {
    private final LinearLayout actionModeLayout;
    private final ArrayList<View> actionModeViews;
    Page[] allPages;
    private final BackDrawable backDrawable;
    private int bottomPadding;
    CacheModel cacheModel;
    private final ActionBarMenuItem clearItem;
    private final ImageView closeButton;
    Delegate delegate;
    private final View divider;
    ArrayList<Page> pages;
    BaseFragment parentFragment;
    BasePlaceProvider placeProvider;
    public final AnimatedTextView selectedMessagesCountTextView;
    private final ViewPagerFixed.TabsView tabs;
    ViewPagerFixed viewPagerFixed;

    public interface Delegate {

        /* renamed from: org.telegram.ui.CachedMediaLayout$Delegate$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            public static void $default$dismiss(Delegate delegate) {
            }
        }

        void clear();

        void clearSelection();

        void dismiss();

        void onItemSelected(CacheControlActivity.DialogFileEntities dialogFileEntities, CacheModel.FileInfo fileInfo, boolean z);
    }

    @Override // org.telegram.ui.Components.NestedSizeNotifierLayout.ChildLayout
    public boolean isAttached() {
        return true;
    }

    protected void showActionMode(boolean z) {
    }

    public CachedMediaLayout(Context context, BaseFragment baseFragment) {
        super(context);
        this.actionModeViews = new ArrayList<>();
        this.pages = new ArrayList<>();
        Page[] pageArr = new Page[5];
        this.allPages = pageArr;
        this.parentFragment = baseFragment;
        AnonymousClass1 anonymousClass1 = null;
        AnonymousClass1 anonymousClass12 = null;
        pageArr[0] = new Page(this, LocaleController.getString("FilterChats", R.string.FilterChats), 0, new DialogsAdapter(this, anonymousClass1), anonymousClass12);
        this.allPages[1] = new Page(this, LocaleController.getString("MediaTab", R.string.MediaTab), 1, new MediaAdapter(this, anonymousClass1), anonymousClass12);
        this.allPages[2] = new Page(this, LocaleController.getString("SharedFilesTab2", R.string.SharedFilesTab2), 2, new DocumentsAdapter(this, anonymousClass1), anonymousClass12);
        this.allPages[3] = new Page(this, LocaleController.getString("Music", R.string.Music), 3, new MusicAdapter(this, anonymousClass1), anonymousClass12);
        int i = 0;
        while (true) {
            Page[] pageArr2 = this.allPages;
            if (i < pageArr2.length) {
                if (pageArr2[i] != null) {
                    this.pages.add(i, pageArr2[i]);
                }
                i++;
            } else {
                ViewPagerFixed viewPagerFixed = new ViewPagerFixed(getContext());
                this.viewPagerFixed = viewPagerFixed;
                viewPagerFixed.setAllowDisallowInterceptTouch(false);
                addView(this.viewPagerFixed, LayoutHelper.createFrame(-1, -1.0f, 0, 0.0f, 48.0f, 0.0f, 0.0f));
                ViewPagerFixed.TabsView createTabsView = this.viewPagerFixed.createTabsView(true, 3);
                this.tabs = createTabsView;
                addView(createTabsView, LayoutHelper.createFrame(-1, 48.0f));
                View view = new View(getContext());
                this.divider = view;
                view.setBackgroundColor(Theme.getColor(Theme.key_divider));
                addView(view, LayoutHelper.createFrame(-1, 1.0f, 0, 0.0f, 48.0f, 0.0f, 0.0f));
                view.getLayoutParams().height = 1;
                this.viewPagerFixed.setAdapter(new AnonymousClass1(context, baseFragment));
                LinearLayout linearLayout = new LinearLayout(context);
                this.actionModeLayout = linearLayout;
                linearLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                linearLayout.setAlpha(0.0f);
                linearLayout.setClickable(true);
                addView(linearLayout, LayoutHelper.createFrame(-1, 48.0f));
                AndroidUtilities.updateViewVisibilityAnimated(linearLayout, false, 1.0f, false);
                ImageView imageView = new ImageView(context);
                this.closeButton = imageView;
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                BackDrawable backDrawable = new BackDrawable(true);
                this.backDrawable = backDrawable;
                imageView.setImageDrawable(backDrawable);
                int i2 = Theme.key_actionBarActionModeDefaultIcon;
                backDrawable.setColor(Theme.getColor(i2));
                int i3 = Theme.key_actionBarActionModeDefaultSelector;
                imageView.setBackground(Theme.createSelectorDrawable(Theme.getColor(i3), 1));
                imageView.setContentDescription(LocaleController.getString("Close", R.string.Close));
                linearLayout.addView(imageView, new LinearLayout.LayoutParams(AndroidUtilities.dp(54.0f), -1));
                this.actionModeViews.add(imageView);
                imageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.CachedMediaLayout$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        CachedMediaLayout.this.lambda$new$0(view2);
                    }
                });
                AnimatedTextView animatedTextView = new AnimatedTextView(context, true, true, true);
                this.selectedMessagesCountTextView = animatedTextView;
                animatedTextView.setTextSize(AndroidUtilities.dp(18.0f));
                animatedTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
                animatedTextView.setTextColor(Theme.getColor(i2));
                linearLayout.addView(animatedTextView, LayoutHelper.createLinear(0, -1, 1.0f, 18, 0, 0, 0));
                this.actionModeViews.add(animatedTextView);
                ActionBarMenuItem actionBarMenuItem = new ActionBarMenuItem(context, (ActionBarMenu) null, Theme.getColor(i3), Theme.getColor(i2), false);
                this.clearItem = actionBarMenuItem;
                actionBarMenuItem.setIcon(R.drawable.msg_clear);
                actionBarMenuItem.setContentDescription(LocaleController.getString("Delete", R.string.Delete));
                actionBarMenuItem.setDuplicateParentStateEnabled(false);
                linearLayout.addView(actionBarMenuItem, new LinearLayout.LayoutParams(AndroidUtilities.dp(54.0f), -1));
                this.actionModeViews.add(actionBarMenuItem);
                actionBarMenuItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.CachedMediaLayout$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        CachedMediaLayout.this.lambda$new$1(view2);
                    }
                });
                return;
            }
        }
    }

    /* renamed from: org.telegram.ui.CachedMediaLayout$1, reason: invalid class name */
    class AnonymousClass1 extends ViewPagerFixed.Adapter {
        private ActionBarPopupWindow popupWindow;
        final /* synthetic */ Context val$context;
        final /* synthetic */ BaseFragment val$parentFragment;

        @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
        public boolean hasStableId() {
            return true;
        }

        AnonymousClass1(Context context, BaseFragment baseFragment) {
            this.val$context = context;
            this.val$parentFragment = baseFragment;
        }

        @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
        public String getItemTitle(int i) {
            return CachedMediaLayout.this.pages.get(i).title;
        }

        @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
        public int getItemCount() {
            return CachedMediaLayout.this.pages.size();
        }

        @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
        public int getItemId(int i) {
            return CachedMediaLayout.this.pages.get(i).type;
        }

        @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
        public View createView(int i) {
            final RecyclerListView recyclerListView = new RecyclerListView(this.val$context);
            DefaultItemAnimator defaultItemAnimator = (DefaultItemAnimator) recyclerListView.getItemAnimator();
            defaultItemAnimator.setDelayAnimations(false);
            defaultItemAnimator.setSupportsChangeAnimations(false);
            recyclerListView.setClipToPadding(false);
            recyclerListView.setPadding(0, 0, 0, CachedMediaLayout.this.bottomPadding);
            recyclerListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.CachedMediaLayout.1.1
                @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                public void onItemClick(View view, int i2) {
                    BaseAdapter baseAdapter = (BaseAdapter) recyclerListView.getAdapter();
                    ItemInner itemInner = baseAdapter.itemInners.get(i2);
                    if (view instanceof SharedPhotoVideoCell2) {
                        CachedMediaLayout.this.openPhoto(itemInner, (MediaAdapter) baseAdapter, recyclerListView, (SharedPhotoVideoCell2) view);
                        return;
                    }
                    Delegate delegate = CachedMediaLayout.this.delegate;
                    if (delegate != null) {
                        delegate.onItemSelected(itemInner.entities, itemInner.file, false);
                    }
                }
            });
            final BaseFragment baseFragment = this.val$parentFragment;
            recyclerListView.setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListenerExtended() { // from class: org.telegram.ui.CachedMediaLayout$1$$ExternalSyntheticLambda5
                @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
                public final boolean onItemClick(View view, int i2, float f, float f2) {
                    boolean lambda$createView$5;
                    lambda$createView$5 = CachedMediaLayout.AnonymousClass1.this.lambda$createView$5(recyclerListView, baseFragment, view, i2, f, f2);
                    return lambda$createView$5;
                }

                @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
                public /* synthetic */ void onLongClickRelease() {
                    RecyclerListView.OnItemLongClickListenerExtended.CC.$default$onLongClickRelease(this);
                }

                @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
                public /* synthetic */ void onMove(float f, float f2) {
                    RecyclerListView.OnItemLongClickListenerExtended.CC.$default$onMove(this, f, f2);
                }
            });
            return recyclerListView;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$createView$5(final RecyclerListView recyclerListView, final BaseFragment baseFragment, final View view, int i, float f, float f2) {
            int i2;
            String str;
            final BaseAdapter baseAdapter = (BaseAdapter) recyclerListView.getAdapter();
            final ItemInner itemInner = baseAdapter.itemInners.get(i);
            if ((view instanceof CacheCell) || (view instanceof SharedPhotoVideoCell2)) {
                ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout = new ActionBarPopupWindow.ActionBarPopupWindowLayout(CachedMediaLayout.this.getContext());
                if (view instanceof SharedPhotoVideoCell2) {
                    ActionBarMenuItem.addItem(actionBarPopupWindowLayout, R.drawable.msg_view_file, LocaleController.getString("CacheOpenFile", R.string.CacheOpenFile), false, null).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.CachedMediaLayout$1$$ExternalSyntheticLambda4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            CachedMediaLayout.AnonymousClass1.this.lambda$createView$0(itemInner, baseAdapter, recyclerListView, view, view2);
                        }
                    });
                } else if (((CacheCell) view).container.getChildAt(0) instanceof SharedAudioCell) {
                    ActionBarMenuItem.addItem(actionBarPopupWindowLayout, R.drawable.msg_played, LocaleController.getString("PlayFile", R.string.PlayFile), false, null).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.CachedMediaLayout$1$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            CachedMediaLayout.AnonymousClass1.this.lambda$createView$1(itemInner, view, view2);
                        }
                    });
                } else {
                    ActionBarMenuItem.addItem(actionBarPopupWindowLayout, R.drawable.msg_view_file, LocaleController.getString("CacheOpenFile", R.string.CacheOpenFile), false, null).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.CachedMediaLayout$1$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            CachedMediaLayout.AnonymousClass1.this.lambda$createView$2(itemInner, view, view2);
                        }
                    });
                }
                CacheModel.FileInfo fileInfo = itemInner.file;
                if (fileInfo.dialogId != 0 && fileInfo.messageId != 0) {
                    ActionBarMenuItem.addItem(actionBarPopupWindowLayout, R.drawable.msg_viewintopic, LocaleController.getString("ViewInChat", R.string.ViewInChat), false, null).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.CachedMediaLayout$1$$ExternalSyntheticLambda3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            CachedMediaLayout.AnonymousClass1.this.lambda$createView$3(itemInner, baseFragment, view2);
                        }
                    });
                }
                int i3 = R.drawable.msg_select;
                if (CachedMediaLayout.this.cacheModel.selectedFiles.contains(itemInner.file)) {
                    i2 = R.string.Deselect;
                    str = "Deselect";
                } else {
                    i2 = R.string.Select;
                    str = "Select";
                }
                ActionBarMenuItem.addItem(actionBarPopupWindowLayout, i3, LocaleController.getString(str, i2), false, null).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.CachedMediaLayout$1$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        CachedMediaLayout.AnonymousClass1.this.lambda$createView$4(itemInner, view2);
                    }
                });
                this.popupWindow = AlertsCreator.createSimplePopup(baseFragment, actionBarPopupWindowLayout, view, (int) f, (int) f2);
                CachedMediaLayout.this.getRootView().dispatchTouchEvent(MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0));
                return true;
            }
            Delegate delegate = CachedMediaLayout.this.delegate;
            if (delegate != null) {
                delegate.onItemSelected(itemInner.entities, itemInner.file, true);
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createView$0(ItemInner itemInner, BaseAdapter baseAdapter, RecyclerListView recyclerListView, View view, View view2) {
            CachedMediaLayout.this.openPhoto(itemInner, (MediaAdapter) baseAdapter, recyclerListView, (SharedPhotoVideoCell2) view);
            ActionBarPopupWindow actionBarPopupWindow = this.popupWindow;
            if (actionBarPopupWindow != null) {
                actionBarPopupWindow.dismiss();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createView$1(ItemInner itemInner, View view, View view2) {
            CachedMediaLayout.this.openItem(itemInner.file, (CacheCell) view);
            ActionBarPopupWindow actionBarPopupWindow = this.popupWindow;
            if (actionBarPopupWindow != null) {
                actionBarPopupWindow.dismiss();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createView$2(ItemInner itemInner, View view, View view2) {
            CachedMediaLayout.this.openItem(itemInner.file, (CacheCell) view);
            ActionBarPopupWindow actionBarPopupWindow = this.popupWindow;
            if (actionBarPopupWindow != null) {
                actionBarPopupWindow.dismiss();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createView$3(ItemInner itemInner, BaseFragment baseFragment, View view) {
            Bundle bundle = new Bundle();
            long j = itemInner.file.dialogId;
            if (j > 0) {
                bundle.putLong("user_id", j);
            } else {
                bundle.putLong("chat_id", -j);
            }
            bundle.putInt("message_id", itemInner.file.messageId);
            baseFragment.presentFragment(new ChatActivity(bundle));
            CachedMediaLayout.this.delegate.dismiss();
            ActionBarPopupWindow actionBarPopupWindow = this.popupWindow;
            if (actionBarPopupWindow != null) {
                actionBarPopupWindow.dismiss();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createView$4(ItemInner itemInner, View view) {
            Delegate delegate = CachedMediaLayout.this.delegate;
            if (delegate != null) {
                delegate.onItemSelected(itemInner.entities, itemInner.file, true);
            }
            ActionBarPopupWindow actionBarPopupWindow = this.popupWindow;
            if (actionBarPopupWindow != null) {
                actionBarPopupWindow.dismiss();
            }
        }

        @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
        public void bindView(View view, int i, int i2) {
            RecyclerListView recyclerListView = (RecyclerListView) view;
            recyclerListView.setAdapter(CachedMediaLayout.this.pages.get(i).adapter);
            if (CachedMediaLayout.this.pages.get(i).type == 1) {
                recyclerListView.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
            } else {
                recyclerListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            }
            recyclerListView.setTag(Integer.valueOf(CachedMediaLayout.this.pages.get(i).type));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        this.delegate.clearSelection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        this.delegate.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openPhoto(ItemInner itemInner, MediaAdapter mediaAdapter, RecyclerListView recyclerListView, SharedPhotoVideoCell2 sharedPhotoVideoCell2) {
        PhotoViewer.getInstance().setParentActivity(this.parentFragment);
        if (this.placeProvider == null) {
            this.placeProvider = new BasePlaceProvider(this, null);
        }
        this.placeProvider.setRecyclerListView(recyclerListView);
        if (mediaAdapter.itemInners.indexOf(itemInner) >= 0) {
            PhotoViewer.getInstance().openPhotoForSelect(mediaAdapter.getPhotos(), mediaAdapter.itemInners.indexOf(itemInner), -1, false, this.placeProvider, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openItem(CacheModel.FileInfo fileInfo, CacheCell cacheCell) {
        RecyclerListView recyclerListView = (RecyclerListView) this.viewPagerFixed.getCurrentView();
        if (cacheCell.type == 2) {
            if (!(recyclerListView.getAdapter() instanceof DocumentsAdapter)) {
                return;
            }
            PhotoViewer.getInstance().setParentActivity(this.parentFragment);
            AnonymousClass1 anonymousClass1 = null;
            if (this.placeProvider == null) {
                this.placeProvider = new BasePlaceProvider(this, anonymousClass1);
            }
            this.placeProvider.setRecyclerListView(recyclerListView);
            if (fileIsMedia(fileInfo.file)) {
                ArrayList<Object> arrayList = new ArrayList<>();
                arrayList.add(new MediaController.PhotoEntry(0, 0, 0L, fileInfo.file.getPath(), 0, fileInfo.type == 1, 0, 0, 0L));
                PhotoViewer.getInstance().openPhotoForSelect(arrayList, 0, -1, false, this.placeProvider, null);
            } else {
                File file = fileInfo.file;
                AndroidUtilities.openForView(file, file.getName(), null, this.parentFragment.getParentActivity(), null);
            }
        }
        if (cacheCell.type == 3) {
            if (MediaController.getInstance().isPlayingMessage(fileInfo.messageObject)) {
                if (!MediaController.getInstance().isMessagePaused()) {
                    MediaController.getInstance().lambda$startAudioAgain$7(fileInfo.messageObject);
                    return;
                } else {
                    MediaController.getInstance().playMessage(fileInfo.messageObject);
                    return;
                }
            }
            MediaController.getInstance().playMessage(fileInfo.messageObject);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SharedPhotoVideoCell2 getCellForIndex(int i) {
        RecyclerListView listView = getListView();
        for (int i2 = 0; i2 < listView.getChildCount(); i2++) {
            View childAt = listView.getChildAt(i2);
            if (listView.getChildAdapterPosition(childAt) == i && (childAt instanceof SharedPhotoVideoCell2)) {
                return (SharedPhotoVideoCell2) childAt;
            }
        }
        return null;
    }

    public void setCacheModel(CacheModel cacheModel) {
        this.cacheModel = cacheModel;
        update();
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 1073741824));
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0118  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void update() {
        /*
            Method dump skipped, instructions count: 309
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.CachedMediaLayout.update():void");
    }

    @Override // org.telegram.ui.Components.NestedSizeNotifierLayout.ChildLayout
    public RecyclerListView getListView() {
        if (this.viewPagerFixed.getCurrentView() == null) {
            return null;
        }
        return (RecyclerListView) this.viewPagerFixed.getCurrentView();
    }

    public void updateVisibleRows() {
        for (int i = 0; i < this.viewPagerFixed.getViewPages().length; i++) {
            AndroidUtilities.updateVisibleRows((RecyclerListView) this.viewPagerFixed.getViewPages()[i]);
        }
    }

    public void setBottomPadding(int i) {
        this.bottomPadding = i;
        for (int i2 = 0; i2 < this.viewPagerFixed.getViewPages().length; i2++) {
            RecyclerListView recyclerListView = (RecyclerListView) this.viewPagerFixed.getViewPages()[i2];
            if (recyclerListView != null) {
                recyclerListView.setPadding(0, 0, 0, i);
            }
        }
    }

    private class Page {
        public final BaseAdapter adapter;
        public final String title;
        public final int type;

        /* synthetic */ Page(CachedMediaLayout cachedMediaLayout, String str, int i, BaseAdapter baseAdapter, AnonymousClass1 anonymousClass1) {
            this(cachedMediaLayout, str, i, baseAdapter);
        }

        private Page(CachedMediaLayout cachedMediaLayout, String str, int i, BaseAdapter baseAdapter) {
            this.title = str;
            this.type = i;
            this.adapter = baseAdapter;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    abstract class BaseAdapter extends AdapterWithDiffUtils {
        ArrayList<ItemInner> itemInners = new ArrayList<>();
        final int type;

        abstract void update();

        protected BaseAdapter(CachedMediaLayout cachedMediaLayout, int i) {
            this.type = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            return this.itemInners.get(i).viewType;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.itemInners.size();
        }
    }

    private class DialogsAdapter extends BaseAdapter {
        ArrayList<ItemInner> old;

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return true;
        }

        /* synthetic */ DialogsAdapter(CachedMediaLayout cachedMediaLayout, AnonymousClass1 anonymousClass1) {
            this();
        }

        private DialogsAdapter() {
            super(CachedMediaLayout.this, 0);
            this.old = new ArrayList<>();
        }

        @Override // org.telegram.ui.CachedMediaLayout.BaseAdapter
        void update() {
            this.old.clear();
            this.old.addAll(this.itemInners);
            this.itemInners.clear();
            if (CachedMediaLayout.this.cacheModel != null) {
                for (int i = 0; i < CachedMediaLayout.this.cacheModel.entities.size(); i++) {
                    ArrayList<ItemInner> arrayList = this.itemInners;
                    CachedMediaLayout cachedMediaLayout = CachedMediaLayout.this;
                    arrayList.add(new ItemInner(cachedMediaLayout, 1, cachedMediaLayout.cacheModel.entities.get(i)));
                }
            }
            setItems(this.old, this.itemInners);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CacheControlActivity.UserCell userCell = null;
            if (i == 1) {
                CacheControlActivity.UserCell userCell2 = new CacheControlActivity.UserCell(CachedMediaLayout.this.getContext(), null);
                userCell2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                userCell = userCell2;
            }
            return new RecyclerListView.Holder(userCell);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            String dialogPhotoTitle;
            if (viewHolder.getItemViewType() != 1) {
                return;
            }
            CacheControlActivity.UserCell userCell = (CacheControlActivity.UserCell) viewHolder.itemView;
            CacheControlActivity.DialogFileEntities dialogFileEntities = this.itemInners.get(i).entities;
            TLObject userOrChat = CachedMediaLayout.this.parentFragment.getMessagesController().getUserOrChat(dialogFileEntities.dialogId);
            CacheControlActivity.DialogFileEntities dialogFileEntities2 = userCell.dialogFileEntities;
            boolean z = dialogFileEntities2 != null && dialogFileEntities2.dialogId == dialogFileEntities.dialogId;
            if (dialogFileEntities.dialogId == Long.MAX_VALUE) {
                dialogPhotoTitle = LocaleController.getString("CacheOtherChats", R.string.CacheOtherChats);
                userCell.getImageView().getAvatarDrawable().setAvatarType(14);
                userCell.getImageView().setForUserOrChat(null, userCell.getImageView().getAvatarDrawable());
            } else {
                dialogPhotoTitle = DialogObject.setDialogPhotoTitle(userCell.getImageView(), userOrChat);
            }
            userCell.dialogFileEntities = dialogFileEntities;
            userCell.getImageView().setRoundRadius(AndroidUtilities.dp(((userOrChat instanceof TLRPC$Chat) && ((TLRPC$Chat) userOrChat).forum) ? 12.0f : 19.0f));
            userCell.setTextAndValue(dialogPhotoTitle, AndroidUtilities.formatFileSize(dialogFileEntities.totalSize), i < getItemCount() - 1);
            userCell.setChecked(CachedMediaLayout.this.cacheModel.isSelected(dialogFileEntities.dialogId), z);
        }
    }

    private abstract class BaseFilesAdapter extends BaseAdapter {
        ArrayList<ItemInner> oldItems;

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return true;
        }

        protected BaseFilesAdapter(int i) {
            super(CachedMediaLayout.this, i);
            this.oldItems = new ArrayList<>();
        }

        @Override // org.telegram.ui.CachedMediaLayout.BaseAdapter
        void update() {
            this.oldItems.clear();
            this.oldItems.addAll(this.itemInners);
            this.itemInners.clear();
            CacheModel cacheModel = CachedMediaLayout.this.cacheModel;
            if (cacheModel != null) {
                ArrayList<CacheModel.FileInfo> arrayList = null;
                int i = this.type;
                if (i == 1) {
                    arrayList = cacheModel.media;
                } else if (i == 2) {
                    arrayList = cacheModel.documents;
                } else if (i == 3) {
                    arrayList = cacheModel.music;
                } else if (i == 4) {
                    arrayList = cacheModel.voice;
                }
                if (arrayList != null) {
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        this.itemInners.add(new ItemInner(CachedMediaLayout.this, 2, arrayList.get(i2)));
                    }
                }
            }
            setItems(this.oldItems, this.itemInners);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ItemInner extends AdapterWithDiffUtils.Item {
        CacheControlActivity.DialogFileEntities entities;
        CacheModel.FileInfo file;

        public ItemInner(CachedMediaLayout cachedMediaLayout, int i, CacheControlActivity.DialogFileEntities dialogFileEntities) {
            super(i, true);
            this.entities = dialogFileEntities;
        }

        public ItemInner(CachedMediaLayout cachedMediaLayout, int i, CacheModel.FileInfo fileInfo) {
            super(i, true);
            this.file = fileInfo;
        }

        public boolean equals(Object obj) {
            CacheModel.FileInfo fileInfo;
            CacheModel.FileInfo fileInfo2;
            CacheControlActivity.DialogFileEntities dialogFileEntities;
            CacheControlActivity.DialogFileEntities dialogFileEntities2;
            if (this == obj) {
                return true;
            }
            if (obj != null && ItemInner.class == obj.getClass()) {
                ItemInner itemInner = (ItemInner) obj;
                int i = this.viewType;
                if (i == itemInner.viewType) {
                    if (i == 1 && (dialogFileEntities = this.entities) != null && (dialogFileEntities2 = itemInner.entities) != null) {
                        return dialogFileEntities.dialogId == dialogFileEntities2.dialogId;
                    }
                    if (i == 2 && (fileInfo = this.file) != null && (fileInfo2 = itemInner.file) != null) {
                        return Objects.equals(fileInfo.file, fileInfo2.file);
                    }
                }
            }
            return false;
        }
    }

    private class MediaAdapter extends BaseFilesAdapter {
        ArrayList<Object> photoEntries;
        private SharedPhotoVideoCell2.SharedResources sharedResources;
        CombinedDrawable thumb;

        @Override // org.telegram.ui.CachedMediaLayout.BaseFilesAdapter, org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return false;
        }

        /* synthetic */ MediaAdapter(CachedMediaLayout cachedMediaLayout, AnonymousClass1 anonymousClass1) {
            this();
        }

        private MediaAdapter() {
            super(1);
            this.photoEntries = new ArrayList<>();
        }

        @Override // org.telegram.ui.CachedMediaLayout.BaseFilesAdapter, org.telegram.ui.CachedMediaLayout.BaseAdapter
        void update() {
            super.update();
            this.photoEntries.clear();
            for (int i = 0; i < this.itemInners.size(); i++) {
                ArrayList<Object> arrayList = this.photoEntries;
                String path = this.itemInners.get(i).file.file.getPath();
                boolean z = true;
                if (this.itemInners.get(i).file.type != 1) {
                    z = false;
                }
                arrayList.add(new MediaController.PhotoEntry(0, 0, 0L, path, 0, z, 0, 0, 0L));
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if (this.sharedResources == null) {
                this.sharedResources = new SharedPhotoVideoCell2.SharedResources(viewGroup.getContext(), null);
            }
            SharedPhotoVideoCell2 sharedPhotoVideoCell2 = new SharedPhotoVideoCell2(viewGroup.getContext(), this.sharedResources, CachedMediaLayout.this.parentFragment.getCurrentAccount()) { // from class: org.telegram.ui.CachedMediaLayout.MediaAdapter.1
                @Override // org.telegram.ui.Cells.SharedPhotoVideoCell2
                /* renamed from: onCheckBoxPressed */
                public void lambda$setStyle$1() {
                    CachedMediaLayout.this.delegate.onItemSelected(null, (CacheModel.FileInfo) getTag(), true);
                }
            };
            sharedPhotoVideoCell2.setStyle(1);
            return new RecyclerListView.Holder(sharedPhotoVideoCell2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (this.thumb == null) {
                CombinedDrawable combinedDrawable = new CombinedDrawable(new ColorDrawable(Theme.getColor(Theme.key_chat_attachPhotoBackground)), Theme.chat_attachEmptyDrawable);
                this.thumb = combinedDrawable;
                combinedDrawable.setFullsize(true);
            }
            SharedPhotoVideoCell2 sharedPhotoVideoCell2 = (SharedPhotoVideoCell2) viewHolder.itemView;
            CacheModel.FileInfo fileInfo = this.itemInners.get(i).file;
            boolean z = fileInfo == sharedPhotoVideoCell2.getTag();
            sharedPhotoVideoCell2.setTag(fileInfo);
            int max = (int) Math.max(100.0f, AndroidUtilities.getRealScreenSize().x / AndroidUtilities.density);
            if (fileInfo.type == 1) {
                sharedPhotoVideoCell2.imageReceiver.setImage(ImageLocation.getForPath("vthumb://0:" + fileInfo.file.getAbsolutePath()), max + "_" + max, this.thumb, null, null, 0);
                sharedPhotoVideoCell2.setVideoText(AndroidUtilities.formatFileSize(fileInfo.size), true);
            } else {
                sharedPhotoVideoCell2.imageReceiver.setImage(ImageLocation.getForPath("thumb://0:" + fileInfo.file.getAbsolutePath()), max + "_" + max, this.thumb, null, null, 0);
                sharedPhotoVideoCell2.setVideoText(AndroidUtilities.formatFileSize(fileInfo.size), false);
            }
            sharedPhotoVideoCell2.setChecked(CachedMediaLayout.this.cacheModel.isSelected(fileInfo), z);
        }

        public ArrayList<Object> getPhotos() {
            return this.photoEntries;
        }
    }

    private class DocumentsAdapter extends BaseFilesAdapter {
        ArrayList<Object> photoEntries;

        /* synthetic */ DocumentsAdapter(CachedMediaLayout cachedMediaLayout, AnonymousClass1 anonymousClass1) {
            this();
        }

        private DocumentsAdapter() {
            super(2);
            this.photoEntries = new ArrayList<>();
        }

        @Override // org.telegram.ui.CachedMediaLayout.BaseFilesAdapter, org.telegram.ui.CachedMediaLayout.BaseAdapter
        void update() {
            super.update();
            this.photoEntries.clear();
            for (int i = 0; i < this.itemInners.size(); i++) {
                ArrayList<Object> arrayList = this.photoEntries;
                String path = this.itemInners.get(i).file.file.getPath();
                boolean z = true;
                if (this.itemInners.get(i).file.type != 1) {
                    z = false;
                }
                arrayList.add(new MediaController.PhotoEntry(0, 0, 0L, path, 0, z, 0, 0, 0L));
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CacheCell cacheCell = new CacheCell(viewGroup.getContext()) { // from class: org.telegram.ui.CachedMediaLayout.DocumentsAdapter.1
                {
                    CachedMediaLayout cachedMediaLayout = CachedMediaLayout.this;
                }

                @Override // org.telegram.ui.CachedMediaLayout.CacheCell
                public void onCheckBoxPressed() {
                    CachedMediaLayout.this.delegate.onItemSelected(null, (CacheModel.FileInfo) getTag(), true);
                }
            };
            cacheCell.type = 2;
            cacheCell.container.addView(new SharedDocumentCell(viewGroup.getContext(), 3, null));
            return new RecyclerListView.Holder(cacheCell);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            CacheCell cacheCell = (CacheCell) viewHolder.itemView;
            SharedDocumentCell sharedDocumentCell = (SharedDocumentCell) cacheCell.container.getChildAt(0);
            CacheModel.FileInfo fileInfo = this.itemInners.get(i).file;
            boolean z = fileInfo == viewHolder.itemView.getTag();
            boolean z2 = i != this.itemInners.size() - 1;
            viewHolder.itemView.setTag(fileInfo);
            sharedDocumentCell.setTextAndValueAndTypeAndThumb(fileInfo.messageType == 5 ? LocaleController.getString("AttachRound", R.string.AttachRound) : fileInfo.file.getName(), LocaleController.formatDateAudio(fileInfo.file.lastModified() / 1000, true), Utilities.getExtension(fileInfo.file.getName()), null, 0, z2);
            if (!z) {
                sharedDocumentCell.setPhoto(fileInfo.file.getPath());
            }
            sharedDocumentCell.getImageView().setRoundRadius(AndroidUtilities.dp(fileInfo.messageType == 5 ? 20.0f : 4.0f));
            cacheCell.drawDivider = z2;
            cacheCell.sizeTextView.setText(AndroidUtilities.formatFileSize(fileInfo.size));
            cacheCell.checkBox.setChecked(CachedMediaLayout.this.cacheModel.isSelected(fileInfo), z);
        }
    }

    private class MusicAdapter extends BaseFilesAdapter {
        /* synthetic */ MusicAdapter(CachedMediaLayout cachedMediaLayout, AnonymousClass1 anonymousClass1) {
            this();
        }

        private MusicAdapter() {
            super(3);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            final CacheCell cacheCell = new CacheCell(viewGroup.getContext()) { // from class: org.telegram.ui.CachedMediaLayout.MusicAdapter.1
                {
                    CachedMediaLayout cachedMediaLayout = CachedMediaLayout.this;
                }

                @Override // org.telegram.ui.CachedMediaLayout.CacheCell
                public void onCheckBoxPressed() {
                    CachedMediaLayout.this.delegate.onItemSelected(null, (CacheModel.FileInfo) getTag(), true);
                }
            };
            cacheCell.type = 3;
            SharedAudioCell sharedAudioCell = new SharedAudioCell(viewGroup.getContext(), 0, null) { // from class: org.telegram.ui.CachedMediaLayout.MusicAdapter.2
                @Override // org.telegram.ui.Cells.SharedAudioCell
                public void didPressedButton() {
                    CachedMediaLayout.this.openItem((CacheModel.FileInfo) cacheCell.getTag(), cacheCell);
                }
            };
            sharedAudioCell.setCheckForButtonPress(true);
            cacheCell.container.addView(sharedAudioCell);
            return new RecyclerListView.Holder(cacheCell);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            CacheCell cacheCell = (CacheCell) viewHolder.itemView;
            SharedAudioCell sharedAudioCell = (SharedAudioCell) cacheCell.container.getChildAt(0);
            CacheModel.FileInfo fileInfo = this.itemInners.get(i).file;
            boolean z = fileInfo == cacheCell.getTag();
            boolean z2 = i != this.itemInners.size() - 1;
            cacheCell.setTag(fileInfo);
            CachedMediaLayout.this.checkMessageObjectForAudio(fileInfo, i);
            sharedAudioCell.setMessageObject(fileInfo.messageObject, z2);
            sharedAudioCell.showName(!fileInfo.metadata.loading, z);
            cacheCell.drawDivider = z2;
            cacheCell.sizeTextView.setText(AndroidUtilities.formatFileSize(fileInfo.size));
            cacheCell.checkBox.setChecked(CachedMediaLayout.this.cacheModel.isSelected(fileInfo), z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkMessageObjectForAudio(final CacheModel.FileInfo fileInfo, int i) {
        if (fileInfo.messageObject == null) {
            TLRPC$TL_message tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.out = true;
            tLRPC$TL_message.id = i;
            tLRPC$TL_message.peer_id = new TLRPC$TL_peerUser();
            TLRPC$TL_peerUser tLRPC$TL_peerUser = new TLRPC$TL_peerUser();
            tLRPC$TL_message.from_id = tLRPC$TL_peerUser;
            TLRPC$Peer tLRPC$Peer = tLRPC$TL_message.peer_id;
            long clientUserId = UserConfig.getInstance(this.parentFragment.getCurrentAccount()).getClientUserId();
            tLRPC$TL_peerUser.user_id = clientUserId;
            tLRPC$Peer.user_id = clientUserId;
            tLRPC$TL_message.date = (int) (System.currentTimeMillis() / 1000);
            tLRPC$TL_message.message = "";
            tLRPC$TL_message.attachPath = fileInfo.file.getPath();
            TLRPC$TL_messageMediaDocument tLRPC$TL_messageMediaDocument = new TLRPC$TL_messageMediaDocument();
            tLRPC$TL_message.media = tLRPC$TL_messageMediaDocument;
            tLRPC$TL_messageMediaDocument.flags |= 3;
            tLRPC$TL_messageMediaDocument.document = new TLRPC$TL_document();
            tLRPC$TL_message.flags |= 768;
            tLRPC$TL_message.dialog_id = fileInfo.dialogId;
            String fileExtension = FileLoader.getFileExtension(fileInfo.file);
            TLRPC$Document tLRPC$Document = tLRPC$TL_message.media.document;
            tLRPC$Document.id = 0L;
            tLRPC$Document.access_hash = 0L;
            tLRPC$Document.file_reference = new byte[0];
            tLRPC$Document.date = tLRPC$TL_message.date;
            StringBuilder sb = new StringBuilder();
            sb.append("audio/");
            if (fileExtension.length() <= 0) {
                fileExtension = "mp3";
            }
            sb.append(fileExtension);
            tLRPC$Document.mime_type = sb.toString();
            TLRPC$Document tLRPC$Document2 = tLRPC$TL_message.media.document;
            tLRPC$Document2.size = fileInfo.size;
            tLRPC$Document2.dc_id = 0;
            final TLRPC$TL_documentAttributeAudio tLRPC$TL_documentAttributeAudio = new TLRPC$TL_documentAttributeAudio();
            if (fileInfo.metadata == null) {
                CacheModel.FileInfo.FileMetadata fileMetadata = new CacheModel.FileInfo.FileMetadata();
                fileInfo.metadata = fileMetadata;
                fileMetadata.loading = true;
                Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.CachedMediaLayout$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        CachedMediaLayout.this.lambda$checkMessageObjectForAudio$3(fileInfo, tLRPC$TL_documentAttributeAudio);
                    }
                });
            }
            tLRPC$TL_documentAttributeAudio.flags |= 3;
            tLRPC$TL_message.media.document.attributes.add(tLRPC$TL_documentAttributeAudio);
            TLRPC$TL_documentAttributeFilename tLRPC$TL_documentAttributeFilename = new TLRPC$TL_documentAttributeFilename();
            tLRPC$TL_documentAttributeFilename.file_name = fileInfo.file.getName();
            tLRPC$TL_message.media.document.attributes.add(tLRPC$TL_documentAttributeFilename);
            MessageObject messageObject = new MessageObject(this.parentFragment.getCurrentAccount(), tLRPC$TL_message, false, false);
            fileInfo.messageObject = messageObject;
            messageObject.mediaExists = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0039 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$checkMessageObjectForAudio$3(final org.telegram.ui.Storage.CacheModel.FileInfo r12, final org.telegram.tgnet.TLRPC$TL_documentAttributeAudio r13) {
        /*
            r11 = this;
            java.lang.String r0 = ""
            r1 = 0
            android.media.MediaMetadataRetriever r2 = new android.media.MediaMetadataRetriever     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L32
            r2.<init>()     // Catch: java.lang.Throwable -> L30 java.lang.Exception -> L32
            java.io.File r1 = r12.file     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2d
            android.net.Uri r1 = android.net.Uri.fromFile(r1)     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2d
            android.content.Context r3 = r11.getContext()     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2d
            r2.setDataSource(r3, r1)     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2d
            r1 = 7
            java.lang.String r1 = r2.extractMetadata(r1)     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2d
            r3 = 2
            java.lang.String r0 = r2.extractMetadata(r3)     // Catch: java.lang.Exception -> L25 java.lang.Throwable -> L2a
            r2.release()     // Catch: java.lang.Throwable -> L22
        L22:
            r9 = r0
            r8 = r1
            goto L3e
        L25:
            r3 = move-exception
            r10 = r2
            r2 = r1
            r1 = r10
            goto L34
        L2a:
            r12 = move-exception
            r1 = r2
            goto L4b
        L2d:
            r3 = move-exception
            r1 = r2
            goto L33
        L30:
            r12 = move-exception
            goto L4b
        L32:
            r3 = move-exception
        L33:
            r2 = r0
        L34:
            org.telegram.messenger.FileLog.e(r3)     // Catch: java.lang.Throwable -> L30
            if (r1 == 0) goto L3c
            r1.release()     // Catch: java.lang.Throwable -> L3c
        L3c:
            r9 = r0
            r8 = r2
        L3e:
            org.telegram.ui.CachedMediaLayout$$ExternalSyntheticLambda3 r0 = new org.telegram.ui.CachedMediaLayout$$ExternalSyntheticLambda3
            r4 = r0
            r5 = r11
            r6 = r12
            r7 = r13
            r4.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r0)
            return
        L4b:
            if (r1 == 0) goto L50
            r1.release()     // Catch: java.lang.Throwable -> L50
        L50:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.CachedMediaLayout.lambda$checkMessageObjectForAudio$3(org.telegram.ui.Storage.CacheModel$FileInfo, org.telegram.tgnet.TLRPC$TL_documentAttributeAudio):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkMessageObjectForAudio$2(CacheModel.FileInfo fileInfo, TLRPC$TL_documentAttributeAudio tLRPC$TL_documentAttributeAudio, String str, String str2) {
        CacheModel.FileInfo.FileMetadata fileMetadata = fileInfo.metadata;
        fileMetadata.loading = false;
        fileMetadata.title = str;
        tLRPC$TL_documentAttributeAudio.title = str;
        fileMetadata.author = str2;
        tLRPC$TL_documentAttributeAudio.performer = str2;
        updateRow(fileInfo, 3);
    }

    private void updateRow(CacheModel.FileInfo fileInfo, int i) {
        for (int i2 = 0; i2 < this.viewPagerFixed.getViewPages().length; i2++) {
            RecyclerListView recyclerListView = (RecyclerListView) this.viewPagerFixed.getViewPages()[i2];
            if (recyclerListView != null && ((BaseAdapter) recyclerListView.getAdapter()).type == i) {
                BaseAdapter baseAdapter = (BaseAdapter) recyclerListView.getAdapter();
                int i3 = 0;
                while (true) {
                    if (i3 >= baseAdapter.itemInners.size()) {
                        break;
                    }
                    if (baseAdapter.itemInners.get(i3).file == fileInfo) {
                        baseAdapter.notifyItemChanged(i3);
                        break;
                    }
                    i3++;
                }
            }
        }
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    private class BasePlaceProvider extends PhotoViewer.EmptyPhotoViewerProvider {
        RecyclerListView recyclerListView;

        private BasePlaceProvider() {
        }

        /* synthetic */ BasePlaceProvider(CachedMediaLayout cachedMediaLayout, AnonymousClass1 anonymousClass1) {
            this();
        }

        public void setRecyclerListView(RecyclerListView recyclerListView) {
            this.recyclerListView = recyclerListView;
        }

        @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public PhotoViewer.PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, TLRPC$FileLocation tLRPC$FileLocation, int i, boolean z) {
            SharedPhotoVideoCell2 cellForIndex = CachedMediaLayout.this.getCellForIndex(i);
            if (cellForIndex == null) {
                return null;
            }
            int[] iArr = new int[2];
            cellForIndex.getLocationInWindow(iArr);
            PhotoViewer.PlaceProviderObject placeProviderObject = new PhotoViewer.PlaceProviderObject();
            placeProviderObject.viewX = iArr[0];
            placeProviderObject.viewY = iArr[1];
            placeProviderObject.parentView = this.recyclerListView;
            ImageReceiver imageReceiver = cellForIndex.imageReceiver;
            placeProviderObject.imageReceiver = imageReceiver;
            placeProviderObject.thumb = imageReceiver.getBitmapSafe();
            placeProviderObject.scale = cellForIndex.getScaleX();
            return placeProviderObject;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CacheCell extends FrameLayout {
        CheckBox2 checkBox;
        FrameLayout container;
        boolean drawDivider;
        TextView sizeTextView;
        int type;

        public void onCheckBoxPressed() {
        }

        public CacheCell(CachedMediaLayout cachedMediaLayout, Context context) {
            super(context);
            CheckBox2 checkBox2 = new CheckBox2(context, 21);
            this.checkBox = checkBox2;
            checkBox2.setDrawBackgroundAsArc(14);
            this.checkBox.setColor(Theme.key_checkbox, Theme.key_radioBackground, Theme.key_checkboxCheck);
            View view = new View(getContext());
            view.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.CachedMediaLayout$CacheCell$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    CachedMediaLayout.CacheCell.this.lambda$new$0(view2);
                }
            });
            this.container = new FrameLayout(context);
            TextView textView = new TextView(context);
            this.sizeTextView = textView;
            textView.setTextSize(1, 16.0f);
            this.sizeTextView.setGravity(5);
            this.sizeTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText));
            if (LocaleController.isRTL) {
                addView(this.checkBox, LayoutHelper.createFrame(24, 24.0f, 21, 0.0f, 0.0f, 18.0f, 0.0f));
                addView(view, LayoutHelper.createFrame(40, 40.0f, 21, 0.0f, 0.0f, 0.0f, 0.0f));
                addView(this.container, LayoutHelper.createFrame(-1, -2.0f, 0, 90.0f, 0.0f, 40.0f, 0.0f));
                addView(this.sizeTextView, LayoutHelper.createFrame(69, -2.0f, 19, 0.0f, 0.0f, 0.0f, 0.0f));
                return;
            }
            addView(this.checkBox, LayoutHelper.createFrame(24, 24.0f, 19, 18.0f, 0.0f, 0.0f, 0.0f));
            addView(view, LayoutHelper.createFrame(40, 40.0f, 19, 0.0f, 0.0f, 0.0f, 0.0f));
            addView(this.container, LayoutHelper.createFrame(-1, -2.0f, 0, 48.0f, 0.0f, 90.0f, 0.0f));
            addView(this.sizeTextView, LayoutHelper.createFrame(69, -2.0f, 21, 0.0f, 0.0f, 21.0f, 0.0f));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view) {
            onCheckBoxPressed();
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            if (this.drawDivider) {
                if (LocaleController.isRTL) {
                    canvas.drawLine(0.0f, getMeasuredHeight() - 1, getMeasuredWidth() - AndroidUtilities.dp(48.0f), getMeasuredHeight() - 1, Theme.dividerPaint);
                } else {
                    canvas.drawLine(getMeasuredWidth() - AndroidUtilities.dp(90.0f), getMeasuredHeight() - 1, getMeasuredWidth(), getMeasuredHeight() - 1, Theme.dividerPaint);
                }
            }
        }
    }

    public static boolean fileIsMedia(File file) {
        String lowerCase = file.getName().toLowerCase();
        return file.getName().endsWith("mp4") || file.getName().endsWith(".jpg") || lowerCase.endsWith(".jpeg") || lowerCase.endsWith(".png") || lowerCase.endsWith(".gif");
    }
}

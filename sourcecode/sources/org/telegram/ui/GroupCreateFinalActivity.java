package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.InputFilter;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$InputFile;
import org.telegram.tgnet.TLRPC$MessageMedia;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$VideoSize;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarPopupWindow;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.GroupCreateUserCell;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.AutoDeletePopupWrapper;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.ContextProgressView;
import org.telegram.ui.Components.EditTextEmoji;
import org.telegram.ui.Components.FillLastLinearLayoutManager;
import org.telegram.ui.Components.ImageUpdater;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.ListView.AdapterWithDiffUtils;
import org.telegram.ui.Components.RLottieDrawable;
import org.telegram.ui.Components.RLottieImageView;
import org.telegram.ui.Components.RadialProgressView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.SizeNotifierFrameLayout;
import org.telegram.ui.Components.VerticalPositionAutoAnimator;
import org.telegram.ui.LocationActivity;

/* loaded from: classes3.dex */
public class GroupCreateFinalActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate, ImageUpdater.ImageUpdaterDelegate {
    private GroupCreateAdapter adapter;
    private TLRPC$FileLocation avatar;
    private AnimatorSet avatarAnimation;
    private TLRPC$FileLocation avatarBig;
    private AvatarDrawable avatarDrawable;
    private RLottieImageView avatarEditor;
    private BackupImageView avatarImage;
    private View avatarOverlay;
    private RadialProgressView avatarProgressView;
    private RLottieDrawable cameraDrawable;
    private boolean canToggleTopics;
    private int chatType;
    private boolean createAfterUpload;
    private String currentGroupCreateAddress;
    private Location currentGroupCreateLocation;
    private GroupCreateFinalActivityDelegate delegate;
    private AnimatorSet doneItemAnimation;
    private boolean donePressed;
    private EditTextEmoji editText;
    private FrameLayout editTextContainer;
    private FrameLayout floatingButtonContainer;
    private ImageView floatingButtonIcon;
    private boolean forImport;
    private ImageUpdater imageUpdater;
    private TLRPC$VideoSize inputEmojiMarkup;
    private TLRPC$InputFile inputPhoto;
    private TLRPC$InputFile inputVideo;
    private String inputVideoPath;
    private FillLastLinearLayoutManager linearLayoutManager;
    private RecyclerListView listView;
    private String nameToSet;
    ActionBarPopupWindow popupWindow;
    private ContextProgressView progressView;
    private int reqId;
    private ArrayList<Long> selectedContacts;
    private Drawable shadowDrawable;
    private int ttlPeriod;
    private double videoTimestamp;

    public interface GroupCreateFinalActivityDelegate {
        void didFailChatCreation();

        void didFinishChatCreation(GroupCreateFinalActivity groupCreateFinalActivity, long j);

        void didStartChatCreation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$createView$1(View view, MotionEvent motionEvent) {
        return true;
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public /* synthetic */ boolean canFinishFragment() {
        return ImageUpdater.ImageUpdaterDelegate.CC.$default$canFinishFragment(this);
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public /* synthetic */ void didUploadFailed() {
        ImageUpdater.ImageUpdaterDelegate.CC.$default$didUploadFailed(this);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    protected boolean hideKeyboardOnShow() {
        return false;
    }

    public GroupCreateFinalActivity(Bundle bundle) {
        super(bundle);
        this.chatType = bundle.getInt("chatType", 0);
        this.avatarDrawable = new AvatarDrawable();
        this.currentGroupCreateAddress = bundle.getString("address");
        this.currentGroupCreateLocation = (Location) bundle.getParcelable("location");
        this.forImport = bundle.getBoolean("forImport", false);
        this.nameToSet = bundle.getString("title", null);
        this.canToggleTopics = bundle.getBoolean("canToggleTopics", true);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.chatDidCreated);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.chatDidFailCreate);
        ImageUpdater imageUpdater = new ImageUpdater(true, 2, true);
        this.imageUpdater = imageUpdater;
        imageUpdater.parentFragment = this;
        imageUpdater.setDelegate(this);
        long[] longArray = getArguments().getLongArray("result");
        if (longArray != null) {
            this.selectedContacts = new ArrayList<>(longArray.length);
            for (long j : longArray) {
                this.selectedContacts.add(Long.valueOf(j));
            }
        }
        final ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.selectedContacts.size(); i++) {
            Long l = this.selectedContacts.get(i);
            if (getMessagesController().getUser(l) == null) {
                arrayList.add(l);
            }
        }
        if (!arrayList.isEmpty()) {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final ArrayList arrayList2 = new ArrayList();
            MessagesStorage.getInstance(this.currentAccount).getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    GroupCreateFinalActivity.this.lambda$onFragmentCreate$0(arrayList2, arrayList, countDownLatch);
                }
            });
            try {
                countDownLatch.await();
            } catch (Exception e) {
                FileLog.e(e);
            }
            if (arrayList.size() != arrayList2.size() || arrayList2.isEmpty()) {
                return false;
            }
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                getMessagesController().putUser((TLRPC$User) it.next(), true);
            }
        }
        this.ttlPeriod = getUserConfig().getGlobalTTl() * 60;
        return super.onFragmentCreate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFragmentCreate$0(ArrayList arrayList, ArrayList arrayList2, CountDownLatch countDownLatch) {
        arrayList.addAll(MessagesStorage.getInstance(this.currentAccount).getUsers(arrayList2));
        countDownLatch.countDown();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.chatDidCreated);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.chatDidFailCreate);
        this.imageUpdater.clear();
        if (this.reqId != 0) {
            ConnectionsManager.getInstance(this.currentAccount).cancelRequest(this.reqId, true);
        }
        EditTextEmoji editTextEmoji = this.editText;
        if (editTextEmoji != null) {
            editTextEmoji.onDestroy();
        }
        AndroidUtilities.removeAdjustResize(getParentActivity(), this.classGuid);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        EditTextEmoji editTextEmoji = this.editText;
        if (editTextEmoji != null) {
            editTextEmoji.onResume();
        }
        GroupCreateAdapter groupCreateAdapter = this.adapter;
        if (groupCreateAdapter != null) {
            groupCreateAdapter.notifyDataSetChanged();
        }
        this.imageUpdater.onResume();
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
        EditTextEmoji editTextEmoji = this.editText;
        if (editTextEmoji != null) {
            editTextEmoji.onPause();
        }
        this.imageUpdater.onPause();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void dismissCurrentDialog() {
        if (this.imageUpdater.dismissCurrentDialog(this.visibleDialog)) {
            return;
        }
        super.dismissCurrentDialog();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean dismissDialogOnPause(Dialog dialog) {
        return this.imageUpdater.dismissDialogOnPause(dialog) && super.dismissDialogOnPause(dialog);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        this.imageUpdater.onRequestPermissionsResultFragment(i, strArr, iArr);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onBackPressed() {
        EditTextEmoji editTextEmoji = this.editText;
        if (editTextEmoji == null || !editTextEmoji.isPopupShowing()) {
            return true;
        }
        this.editText.hidePopup(true);
        return false;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        int i;
        String str;
        SizeNotifierFrameLayout sizeNotifierFrameLayout;
        EditTextEmoji editTextEmoji = this.editText;
        if (editTextEmoji != null) {
            editTextEmoji.onDestroy();
        }
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("NewGroup", R.string.NewGroup));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.GroupCreateFinalActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i2) {
                if (i2 == -1) {
                    GroupCreateFinalActivity.this.finishFragment();
                }
            }
        });
        SizeNotifierFrameLayout sizeNotifierFrameLayout2 = new SizeNotifierFrameLayout(context) { // from class: org.telegram.ui.GroupCreateFinalActivity.2
            private boolean ignoreLayout;

            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i2, int i3) {
                int size = View.MeasureSpec.getSize(i2);
                int size2 = View.MeasureSpec.getSize(i3);
                setMeasuredDimension(size, size2);
                int paddingTop = size2 - getPaddingTop();
                measureChildWithMargins(((BaseFragment) GroupCreateFinalActivity.this).actionBar, i2, 0, i3, 0);
                if (measureKeyboardHeight() > AndroidUtilities.dp(20.0f) && !GroupCreateFinalActivity.this.editText.isPopupShowing()) {
                    this.ignoreLayout = true;
                    GroupCreateFinalActivity.this.editText.hideEmojiView();
                    this.ignoreLayout = false;
                }
                int childCount = getChildCount();
                for (int i4 = 0; i4 < childCount; i4++) {
                    View childAt = getChildAt(i4);
                    if (childAt != null && childAt.getVisibility() != 8 && childAt != ((BaseFragment) GroupCreateFinalActivity.this).actionBar) {
                        if (GroupCreateFinalActivity.this.editText != null && GroupCreateFinalActivity.this.editText.isPopupView(childAt)) {
                            if (AndroidUtilities.isInMultiwindow || AndroidUtilities.isTablet()) {
                                if (AndroidUtilities.isTablet()) {
                                    childAt.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.dp(AndroidUtilities.isTablet() ? 200.0f : 320.0f), (paddingTop - AndroidUtilities.statusBarHeight) + getPaddingTop()), 1073741824));
                                } else {
                                    childAt.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec((paddingTop - AndroidUtilities.statusBarHeight) + getPaddingTop(), 1073741824));
                                }
                            } else {
                                childAt.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, 1073741824));
                            }
                        } else {
                            measureChildWithMargins(childAt, i2, 0, i3, 0);
                        }
                    }
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:22:0x0072  */
            /* JADX WARN: Removed duplicated region for block: B:29:0x00a1  */
            /* JADX WARN: Removed duplicated region for block: B:33:0x00b3  */
            /* JADX WARN: Removed duplicated region for block: B:35:0x00bc  */
            /* JADX WARN: Removed duplicated region for block: B:42:0x008c  */
            @Override // org.telegram.ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            protected void onLayout(boolean r11, int r12, int r13, int r14, int r15) {
                /*
                    r10 = this;
                    int r11 = r10.getChildCount()
                    int r0 = r10.measureKeyboardHeight()
                    r1 = 1101004800(0x41a00000, float:20.0)
                    int r1 = org.telegram.messenger.AndroidUtilities.dp(r1)
                    r2 = 0
                    if (r0 > r1) goto L26
                    boolean r1 = org.telegram.messenger.AndroidUtilities.isInMultiwindow
                    if (r1 != 0) goto L26
                    boolean r1 = org.telegram.messenger.AndroidUtilities.isTablet()
                    if (r1 != 0) goto L26
                    org.telegram.ui.GroupCreateFinalActivity r1 = org.telegram.ui.GroupCreateFinalActivity.this
                    org.telegram.ui.Components.EditTextEmoji r1 = org.telegram.ui.GroupCreateFinalActivity.access$100(r1)
                    int r1 = r1.getEmojiPadding()
                    goto L27
                L26:
                    r1 = 0
                L27:
                    r10.setBottomClip(r1)
                L2a:
                    if (r2 >= r11) goto Lcf
                    android.view.View r3 = r10.getChildAt(r2)
                    int r4 = r3.getVisibility()
                    r5 = 8
                    if (r4 != r5) goto L3a
                    goto Lcb
                L3a:
                    android.view.ViewGroup$LayoutParams r4 = r3.getLayoutParams()
                    android.widget.FrameLayout$LayoutParams r4 = (android.widget.FrameLayout.LayoutParams) r4
                    int r5 = r3.getMeasuredWidth()
                    int r6 = r3.getMeasuredHeight()
                    int r7 = r4.gravity
                    r8 = -1
                    if (r7 != r8) goto L4f
                    r7 = 51
                L4f:
                    r8 = r7 & 7
                    r7 = r7 & 112(0x70, float:1.57E-43)
                    r8 = r8 & 7
                    r9 = 1
                    if (r8 == r9) goto L63
                    r9 = 5
                    if (r8 == r9) goto L5e
                    int r8 = r4.leftMargin
                    goto L6e
                L5e:
                    int r8 = r14 - r5
                    int r9 = r4.rightMargin
                    goto L6d
                L63:
                    int r8 = r14 - r12
                    int r8 = r8 - r5
                    int r8 = r8 / 2
                    int r9 = r4.leftMargin
                    int r8 = r8 + r9
                    int r9 = r4.rightMargin
                L6d:
                    int r8 = r8 - r9
                L6e:
                    r9 = 16
                    if (r7 == r9) goto L8c
                    r9 = 48
                    if (r7 == r9) goto L84
                    r9 = 80
                    if (r7 == r9) goto L7d
                    int r4 = r4.topMargin
                    goto L99
                L7d:
                    int r7 = r15 - r1
                    int r7 = r7 - r13
                    int r7 = r7 - r6
                    int r4 = r4.bottomMargin
                    goto L97
                L84:
                    int r4 = r4.topMargin
                    int r7 = r10.getPaddingTop()
                    int r4 = r4 + r7
                    goto L99
                L8c:
                    int r7 = r15 - r1
                    int r7 = r7 - r13
                    int r7 = r7 - r6
                    int r7 = r7 / 2
                    int r9 = r4.topMargin
                    int r7 = r7 + r9
                    int r4 = r4.bottomMargin
                L97:
                    int r4 = r7 - r4
                L99:
                    org.telegram.ui.GroupCreateFinalActivity r7 = org.telegram.ui.GroupCreateFinalActivity.this
                    org.telegram.ui.Components.EditTextEmoji r7 = org.telegram.ui.GroupCreateFinalActivity.access$100(r7)
                    if (r7 == 0) goto Lc6
                    org.telegram.ui.GroupCreateFinalActivity r7 = org.telegram.ui.GroupCreateFinalActivity.this
                    org.telegram.ui.Components.EditTextEmoji r7 = org.telegram.ui.GroupCreateFinalActivity.access$100(r7)
                    boolean r7 = r7.isPopupView(r3)
                    if (r7 == 0) goto Lc6
                    boolean r4 = org.telegram.messenger.AndroidUtilities.isTablet()
                    if (r4 == 0) goto Lbc
                    int r4 = r10.getMeasuredHeight()
                    int r7 = r3.getMeasuredHeight()
                    goto Lc5
                Lbc:
                    int r4 = r10.getMeasuredHeight()
                    int r4 = r4 + r0
                    int r7 = r3.getMeasuredHeight()
                Lc5:
                    int r4 = r4 - r7
                Lc6:
                    int r5 = r5 + r8
                    int r6 = r6 + r4
                    r3.layout(r8, r4, r5, r6)
                Lcb:
                    int r2 = r2 + 1
                    goto L2a
                Lcf:
                    r10.notifyHeightChanged()
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.GroupCreateFinalActivity.AnonymousClass2.onLayout(boolean, int, int, int, int):void");
            }

            @Override // android.view.View, android.view.ViewParent
            public void requestLayout() {
                if (this.ignoreLayout) {
                    return;
                }
                super.requestLayout();
            }
        };
        this.fragmentView = sizeNotifierFrameLayout2;
        sizeNotifierFrameLayout2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.fragmentView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean lambda$createView$1;
                lambda$createView$1 = GroupCreateFinalActivity.lambda$createView$1(view, motionEvent);
                return lambda$createView$1;
            }
        });
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.greydivider_top).mutate();
        LinearLayout linearLayout = new LinearLayout(context) { // from class: org.telegram.ui.GroupCreateFinalActivity.3
            @Override // android.view.ViewGroup
            protected boolean drawChild(Canvas canvas, View view, long j) {
                boolean drawChild = super.drawChild(canvas, view, j);
                if (view == GroupCreateFinalActivity.this.listView && GroupCreateFinalActivity.this.shadowDrawable != null) {
                    int measuredHeight = GroupCreateFinalActivity.this.editTextContainer.getMeasuredHeight();
                    GroupCreateFinalActivity.this.shadowDrawable.setBounds(0, measuredHeight, getMeasuredWidth(), GroupCreateFinalActivity.this.shadowDrawable.getIntrinsicHeight() + measuredHeight);
                    GroupCreateFinalActivity.this.shadowDrawable.draw(canvas);
                }
                return drawChild;
            }
        };
        linearLayout.setOrientation(1);
        sizeNotifierFrameLayout2.addView(linearLayout, LayoutHelper.createFrame(-1, -1.0f));
        FrameLayout frameLayout = new FrameLayout(context);
        this.editTextContainer = frameLayout;
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, -2));
        BackupImageView backupImageView = new BackupImageView(context) { // from class: org.telegram.ui.GroupCreateFinalActivity.4
            @Override // android.view.View
            public void invalidate() {
                if (GroupCreateFinalActivity.this.avatarOverlay != null) {
                    GroupCreateFinalActivity.this.avatarOverlay.invalidate();
                }
                super.invalidate();
            }

            @Override // android.view.View
            public void invalidate(int i2, int i3, int i4, int i5) {
                if (GroupCreateFinalActivity.this.avatarOverlay != null) {
                    GroupCreateFinalActivity.this.avatarOverlay.invalidate();
                }
                super.invalidate(i2, i3, i4, i5);
            }
        };
        this.avatarImage = backupImageView;
        backupImageView.setRoundRadius(AndroidUtilities.dp(this.chatType == 5 ? 16.0f : 32.0f));
        this.avatarDrawable.setInfo(5L, null, null);
        this.avatarImage.setImageDrawable(this.avatarDrawable);
        this.avatarImage.setContentDescription(LocaleController.getString("ChoosePhoto", R.string.ChoosePhoto));
        FrameLayout frameLayout2 = this.editTextContainer;
        BackupImageView backupImageView2 = this.avatarImage;
        boolean z = LocaleController.isRTL;
        frameLayout2.addView(backupImageView2, LayoutHelper.createFrame(64, 64.0f, (z ? 5 : 3) | 48, z ? 0.0f : 16.0f, 16.0f, z ? 16.0f : 0.0f, 16.0f));
        final Paint paint = new Paint(1);
        paint.setColor(1426063360);
        View view = new View(context) { // from class: org.telegram.ui.GroupCreateFinalActivity.5
            @Override // android.view.View
            protected void onDraw(Canvas canvas) {
                if (GroupCreateFinalActivity.this.avatarImage != null && GroupCreateFinalActivity.this.avatarProgressView.getVisibility() == 0 && GroupCreateFinalActivity.this.avatarImage.getImageReceiver().hasNotThumb()) {
                    paint.setAlpha((int) (GroupCreateFinalActivity.this.avatarImage.getImageReceiver().getCurrentAlpha() * 85.0f * GroupCreateFinalActivity.this.avatarProgressView.getAlpha()));
                    canvas.drawCircle(getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f, getMeasuredWidth() / 2.0f, paint);
                }
            }
        };
        this.avatarOverlay = view;
        FrameLayout frameLayout3 = this.editTextContainer;
        boolean z2 = LocaleController.isRTL;
        frameLayout3.addView(view, LayoutHelper.createFrame(64, 64.0f, (z2 ? 5 : 3) | 48, z2 ? 0.0f : 16.0f, 16.0f, z2 ? 16.0f : 0.0f, 16.0f));
        this.avatarOverlay.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GroupCreateFinalActivity.this.lambda$createView$4(view2);
            }
        });
        int i2 = R.raw.camera;
        this.cameraDrawable = new RLottieDrawable(i2, "" + i2, AndroidUtilities.dp(60.0f), AndroidUtilities.dp(60.0f), false, null);
        RLottieImageView rLottieImageView = new RLottieImageView(context) { // from class: org.telegram.ui.GroupCreateFinalActivity.6
            @Override // android.view.View
            public void invalidate(int i3, int i4, int i5, int i6) {
                super.invalidate(i3, i4, i5, i6);
                GroupCreateFinalActivity.this.avatarOverlay.invalidate();
            }

            @Override // android.view.View
            public void invalidate() {
                super.invalidate();
                GroupCreateFinalActivity.this.avatarOverlay.invalidate();
            }
        };
        this.avatarEditor = rLottieImageView;
        rLottieImageView.setScaleType(ImageView.ScaleType.CENTER);
        this.avatarEditor.setAnimation(this.cameraDrawable);
        this.avatarEditor.setEnabled(false);
        this.avatarEditor.setClickable(false);
        this.avatarEditor.setPadding(AndroidUtilities.dp(0.0f), 0, 0, AndroidUtilities.dp(1.0f));
        FrameLayout frameLayout4 = this.editTextContainer;
        RLottieImageView rLottieImageView2 = this.avatarEditor;
        boolean z3 = LocaleController.isRTL;
        frameLayout4.addView(rLottieImageView2, LayoutHelper.createFrame(64, 64.0f, (z3 ? 5 : 3) | 48, z3 ? 0.0f : 15.0f, 16.0f, z3 ? 15.0f : 0.0f, 16.0f));
        RadialProgressView radialProgressView = new RadialProgressView(context) { // from class: org.telegram.ui.GroupCreateFinalActivity.7
            @Override // org.telegram.ui.Components.RadialProgressView, android.view.View
            public void setAlpha(float f) {
                super.setAlpha(f);
                GroupCreateFinalActivity.this.avatarOverlay.invalidate();
            }
        };
        this.avatarProgressView = radialProgressView;
        radialProgressView.setSize(AndroidUtilities.dp(30.0f));
        this.avatarProgressView.setProgressColor(-1);
        this.avatarProgressView.setNoProgress(false);
        FrameLayout frameLayout5 = this.editTextContainer;
        RadialProgressView radialProgressView2 = this.avatarProgressView;
        boolean z4 = LocaleController.isRTL;
        frameLayout5.addView(radialProgressView2, LayoutHelper.createFrame(64, 64.0f, (z4 ? 5 : 3) | 48, z4 ? 0.0f : 16.0f, 16.0f, z4 ? 16.0f : 0.0f, 16.0f));
        showAvatarProgress(false, false);
        EditTextEmoji editTextEmoji2 = new EditTextEmoji(context, sizeNotifierFrameLayout2, this, 0, false);
        this.editText = editTextEmoji2;
        int i3 = this.chatType;
        if (i3 == 0 || i3 == 4 || i3 == 5) {
            i = R.string.EnterGroupNamePlaceholder;
            str = "EnterGroupNamePlaceholder";
        } else {
            i = R.string.EnterListName;
            str = "EnterListName";
        }
        editTextEmoji2.setHint(LocaleController.getString(str, i));
        String str2 = this.nameToSet;
        if (str2 != null) {
            this.editText.setText(str2);
            this.nameToSet = null;
        }
        this.editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        FrameLayout frameLayout6 = this.editTextContainer;
        EditTextEmoji editTextEmoji3 = this.editText;
        boolean z5 = LocaleController.isRTL;
        frameLayout6.addView(editTextEmoji3, LayoutHelper.createFrame(-1, -2.0f, 16, z5 ? 5.0f : 96.0f, 0.0f, z5 ? 96.0f : 5.0f, 0.0f));
        RecyclerListView recyclerListView = new RecyclerListView(context);
        this.listView = recyclerListView;
        this.linearLayoutManager = new FillLastLinearLayoutManager(context, 1, recyclerListView);
        RecyclerListView recyclerListView2 = this.listView;
        GroupCreateAdapter groupCreateAdapter = new GroupCreateAdapter(context);
        this.adapter = groupCreateAdapter;
        recyclerListView2.setAdapter(groupCreateAdapter);
        this.listView.setLayoutManager(this.linearLayoutManager);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setVerticalScrollbarPosition(LocaleController.isRTL ? 1 : 2);
        linearLayout.addView(this.listView, LayoutHelper.createLinear(-1, -1));
        this.listView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.GroupCreateFinalActivity.8
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i4) {
                if (i4 == 1) {
                    AndroidUtilities.hideKeyboard(GroupCreateFinalActivity.this.editText);
                }
            }
        });
        this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda8
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ boolean hasDoubleTap(View view2, int i4) {
                return RecyclerListView.OnItemClickListenerExtended.CC.$default$hasDoubleTap(this, view2, i4);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ void onDoubleTap(View view2, int i4, float f, float f2) {
                RecyclerListView.OnItemClickListenerExtended.CC.$default$onDoubleTap(this, view2, i4, f, f2);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public final void onItemClick(View view2, int i4, float f, float f2) {
                GroupCreateFinalActivity.this.lambda$createView$6(view2, i4, f, f2);
            }
        });
        this.floatingButtonContainer = new FrameLayout(context);
        Drawable createSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), Theme.getColor(Theme.key_chats_actionBackground), Theme.getColor(Theme.key_chats_actionPressedBackground));
        int i4 = Build.VERSION.SDK_INT;
        if (i4 < 21) {
            Drawable mutate = context.getResources().getDrawable(R.drawable.floating_shadow).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(-16777216, PorterDuff.Mode.MULTIPLY));
            CombinedDrawable combinedDrawable = new CombinedDrawable(mutate, createSimpleSelectorCircleDrawable, 0, 0);
            combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
            createSimpleSelectorCircleDrawable = combinedDrawable;
        }
        this.floatingButtonContainer.setBackgroundDrawable(createSimpleSelectorCircleDrawable);
        if (i4 >= 21) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            sizeNotifierFrameLayout = sizeNotifierFrameLayout2;
            stateListAnimator.addState(new int[]{android.R.attr.state_pressed}, ObjectAnimator.ofFloat(this.floatingButtonIcon, "translationZ", AndroidUtilities.dp(2.0f), AndroidUtilities.dp(4.0f)).setDuration(200L));
            stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(this.floatingButtonIcon, "translationZ", AndroidUtilities.dp(4.0f), AndroidUtilities.dp(2.0f)).setDuration(200L));
            this.floatingButtonContainer.setStateListAnimator(stateListAnimator);
            this.floatingButtonContainer.setOutlineProvider(new ViewOutlineProvider(this) { // from class: org.telegram.ui.GroupCreateFinalActivity.10
                @Override // android.view.ViewOutlineProvider
                @SuppressLint({"NewApi"})
                public void getOutline(View view2, Outline outline) {
                    outline.setOval(0, 0, AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
                }
            });
        } else {
            sizeNotifierFrameLayout = sizeNotifierFrameLayout2;
        }
        VerticalPositionAutoAnimator.attach(this.floatingButtonContainer);
        View view2 = this.floatingButtonContainer;
        int i5 = i4 >= 21 ? 56 : 60;
        float f = i4 >= 21 ? 56.0f : 60.0f;
        boolean z6 = LocaleController.isRTL;
        sizeNotifierFrameLayout.addView(view2, LayoutHelper.createFrame(i5, f, (z6 ? 3 : 5) | 80, z6 ? 14.0f : 0.0f, 0.0f, z6 ? 0.0f : 14.0f, 14.0f));
        this.floatingButtonContainer.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                GroupCreateFinalActivity.this.lambda$createView$7(view3);
            }
        });
        ImageView imageView = new ImageView(context);
        this.floatingButtonIcon = imageView;
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        this.floatingButtonIcon.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_actionIcon), PorterDuff.Mode.MULTIPLY));
        this.floatingButtonIcon.setImageResource(R.drawable.checkbig);
        this.floatingButtonIcon.setPadding(0, AndroidUtilities.dp(2.0f), 0, 0);
        this.floatingButtonContainer.setContentDescription(LocaleController.getString("Done", R.string.Done));
        this.floatingButtonContainer.addView(this.floatingButtonIcon, LayoutHelper.createFrame(i4 >= 21 ? 56 : 60, i4 >= 21 ? 56.0f : 60.0f));
        ContextProgressView contextProgressView = new ContextProgressView(context, 1);
        this.progressView = contextProgressView;
        contextProgressView.setAlpha(0.0f);
        this.progressView.setScaleX(0.1f);
        this.progressView.setScaleY(0.1f);
        this.progressView.setVisibility(4);
        this.floatingButtonContainer.addView(this.progressView, LayoutHelper.createFrame(-1, -1.0f));
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$4(View view) {
        this.imageUpdater.openMenu(this.avatar != null, new Runnable() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                GroupCreateFinalActivity.this.lambda$createView$2();
            }
        }, new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                GroupCreateFinalActivity.this.lambda$createView$3(dialogInterface);
            }
        }, 0);
        this.cameraDrawable.setCurrentFrame(0);
        this.cameraDrawable.setCustomEndFrame(43);
        this.avatarEditor.playAnimation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$2() {
        this.avatar = null;
        this.avatarBig = null;
        this.inputPhoto = null;
        this.inputVideo = null;
        this.inputVideoPath = null;
        this.inputEmojiMarkup = null;
        this.videoTimestamp = 0.0d;
        showAvatarProgress(false, true);
        this.avatarImage.setImage((ImageLocation) null, (String) null, this.avatarDrawable, (Object) null);
        this.avatarEditor.setAnimation(this.cameraDrawable);
        this.cameraDrawable.setCurrentFrame(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$3(DialogInterface dialogInterface) {
        if (!this.imageUpdater.isUploadingImage()) {
            this.cameraDrawable.setCustomEndFrame(86);
            this.avatarEditor.playAnimation();
        } else {
            this.cameraDrawable.setCurrentFrame(0, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$6(View view, int i, float f, float f2) {
        if (view instanceof TextSettingsCell) {
            if (!AndroidUtilities.isMapsInstalled(this)) {
                return;
            }
            LocationActivity locationActivity = new LocationActivity(4);
            locationActivity.setDialogId(0L);
            locationActivity.setDelegate(new LocationActivity.LocationActivityDelegate() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda9
                @Override // org.telegram.ui.LocationActivity.LocationActivityDelegate
                public final void didSelectLocation(TLRPC$MessageMedia tLRPC$MessageMedia, int i2, boolean z, int i3) {
                    GroupCreateFinalActivity.this.lambda$createView$5(tLRPC$MessageMedia, i2, z, i3);
                }
            });
            presentFragment(locationActivity);
        }
        if (!(view instanceof TextCell) || this.chatType == 5) {
            return;
        }
        ActionBarPopupWindow actionBarPopupWindow = this.popupWindow;
        if (actionBarPopupWindow == null || !actionBarPopupWindow.isShowing()) {
            AutoDeletePopupWrapper autoDeletePopupWrapper = new AutoDeletePopupWrapper(getContext(), null, new AutoDeletePopupWrapper.Callback() { // from class: org.telegram.ui.GroupCreateFinalActivity.9
                @Override // org.telegram.ui.Components.AutoDeletePopupWrapper.Callback
                public void dismiss() {
                    GroupCreateFinalActivity.this.popupWindow.dismiss();
                }

                @Override // org.telegram.ui.Components.AutoDeletePopupWrapper.Callback
                public void setAutoDeleteHistory(int i2, int i3) {
                    GroupCreateFinalActivity.this.ttlPeriod = i2;
                    AndroidUtilities.updateVisibleRows(GroupCreateFinalActivity.this.listView);
                }
            }, true, 1, null);
            autoDeletePopupWrapper.lambda$updateItems$7(this.ttlPeriod);
            ActionBarPopupWindow actionBarPopupWindow2 = new ActionBarPopupWindow(autoDeletePopupWrapper.windowLayout, -2, -2);
            this.popupWindow = actionBarPopupWindow2;
            actionBarPopupWindow2.setPauseNotifications(true);
            this.popupWindow.setDismissAnimationDuration(220);
            this.popupWindow.setOutsideTouchable(true);
            this.popupWindow.setClippingEnabled(true);
            this.popupWindow.setAnimationStyle(R.style.PopupContextAnimation);
            this.popupWindow.setFocusable(true);
            autoDeletePopupWrapper.windowLayout.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), LinearLayoutManager.INVALID_OFFSET));
            this.popupWindow.setInputMethodMode(2);
            this.popupWindow.getContentView().setFocusableInTouchMode(true);
            this.popupWindow.showAtLocation(getFragmentView(), 0, (int) (view.getX() + f), (int) (view.getY() + f2 + (autoDeletePopupWrapper.windowLayout.getMeasuredHeight() / 2.0f)));
            this.popupWindow.dimBehind();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$5(TLRPC$MessageMedia tLRPC$MessageMedia, int i, boolean z, int i2) {
        this.currentGroupCreateLocation.setLatitude(tLRPC$MessageMedia.geo.lat);
        this.currentGroupCreateLocation.setLongitude(tLRPC$MessageMedia.geo._long);
        this.currentGroupCreateAddress = tLRPC$MessageMedia.address;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$7(View view) {
        if (this.donePressed) {
            return;
        }
        if (this.editText.length() == 0) {
            Vibrator vibrator = (Vibrator) getParentActivity().getSystemService("vibrator");
            if (vibrator != null) {
                vibrator.vibrate(200L);
            }
            AndroidUtilities.shakeView(this.editText);
            return;
        }
        this.donePressed = true;
        AndroidUtilities.hideKeyboard(this.editText);
        this.editText.setEnabled(false);
        if (this.imageUpdater.isUploadingImage()) {
            this.createAfterUpload = true;
        } else {
            showEditDoneProgress(true);
            this.reqId = getMessagesController().createChat(this.editText.getText().toString(), this.selectedContacts, null, this.chatType, this.forImport, this.currentGroupCreateLocation, this.currentGroupCreateAddress, this.ttlPeriod, this);
        }
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public void onUploadProgressChanged(float f) {
        RadialProgressView radialProgressView = this.avatarProgressView;
        if (radialProgressView == null) {
            return;
        }
        radialProgressView.setProgress(f);
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public void didStartUpload(boolean z) {
        RadialProgressView radialProgressView = this.avatarProgressView;
        if (radialProgressView == null) {
            return;
        }
        radialProgressView.setProgress(0.0f);
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public void didUploadPhoto(final TLRPC$InputFile tLRPC$InputFile, final TLRPC$InputFile tLRPC$InputFile2, final double d, final String str, final TLRPC$PhotoSize tLRPC$PhotoSize, final TLRPC$PhotoSize tLRPC$PhotoSize2, boolean z, final TLRPC$VideoSize tLRPC$VideoSize) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                GroupCreateFinalActivity.this.lambda$didUploadPhoto$8(tLRPC$InputFile, tLRPC$InputFile2, tLRPC$VideoSize, str, d, tLRPC$PhotoSize2, tLRPC$PhotoSize);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didUploadPhoto$8(TLRPC$InputFile tLRPC$InputFile, TLRPC$InputFile tLRPC$InputFile2, TLRPC$VideoSize tLRPC$VideoSize, String str, double d, TLRPC$PhotoSize tLRPC$PhotoSize, TLRPC$PhotoSize tLRPC$PhotoSize2) {
        if (tLRPC$InputFile != null || tLRPC$InputFile2 != null || tLRPC$VideoSize != null) {
            this.inputPhoto = tLRPC$InputFile;
            this.inputVideo = tLRPC$InputFile2;
            this.inputEmojiMarkup = tLRPC$VideoSize;
            this.inputVideoPath = str;
            this.videoTimestamp = d;
            if (this.createAfterUpload) {
                GroupCreateFinalActivityDelegate groupCreateFinalActivityDelegate = this.delegate;
                if (groupCreateFinalActivityDelegate != null) {
                    groupCreateFinalActivityDelegate.didStartChatCreation();
                }
                getMessagesController().createChat(this.editText.getText().toString(), this.selectedContacts, null, this.chatType, this.forImport, this.currentGroupCreateLocation, this.currentGroupCreateAddress, this.ttlPeriod, this);
            }
            showAvatarProgress(false, true);
            this.avatarEditor.setImageDrawable(null);
            return;
        }
        TLRPC$FileLocation tLRPC$FileLocation = tLRPC$PhotoSize.location;
        this.avatar = tLRPC$FileLocation;
        this.avatarBig = tLRPC$PhotoSize2.location;
        this.avatarImage.setImage(ImageLocation.getForLocal(tLRPC$FileLocation), "50_50", this.avatarDrawable, (Object) null);
        showAvatarProgress(true, false);
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public String getInitialSearchString() {
        return this.editText.getText().toString();
    }

    public void setDelegate(GroupCreateFinalActivityDelegate groupCreateFinalActivityDelegate) {
        this.delegate = groupCreateFinalActivityDelegate;
    }

    private void showAvatarProgress(final boolean z, boolean z2) {
        if (this.avatarEditor == null) {
            return;
        }
        AnimatorSet animatorSet = this.avatarAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.avatarAnimation = null;
        }
        if (z2) {
            this.avatarAnimation = new AnimatorSet();
            if (z) {
                this.avatarProgressView.setVisibility(0);
                this.avatarAnimation.playTogether(ObjectAnimator.ofFloat(this.avatarEditor, (Property<RLottieImageView, Float>) View.ALPHA, 0.0f), ObjectAnimator.ofFloat(this.avatarProgressView, (Property<RadialProgressView, Float>) View.ALPHA, 1.0f));
            } else {
                this.avatarEditor.setVisibility(0);
                this.avatarAnimation.playTogether(ObjectAnimator.ofFloat(this.avatarEditor, (Property<RLottieImageView, Float>) View.ALPHA, 1.0f), ObjectAnimator.ofFloat(this.avatarProgressView, (Property<RadialProgressView, Float>) View.ALPHA, 0.0f));
            }
            this.avatarAnimation.setDuration(180L);
            this.avatarAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.GroupCreateFinalActivity.11
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (GroupCreateFinalActivity.this.avatarAnimation == null || GroupCreateFinalActivity.this.avatarEditor == null) {
                        return;
                    }
                    if (z) {
                        GroupCreateFinalActivity.this.avatarEditor.setVisibility(4);
                    } else {
                        GroupCreateFinalActivity.this.avatarProgressView.setVisibility(4);
                    }
                    GroupCreateFinalActivity.this.avatarAnimation = null;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    GroupCreateFinalActivity.this.avatarAnimation = null;
                }
            });
            this.avatarAnimation.start();
            return;
        }
        if (z) {
            this.avatarEditor.setAlpha(1.0f);
            this.avatarEditor.setVisibility(4);
            this.avatarProgressView.setAlpha(1.0f);
            this.avatarProgressView.setVisibility(0);
            return;
        }
        this.avatarEditor.setAlpha(1.0f);
        this.avatarEditor.setVisibility(0);
        this.avatarProgressView.setAlpha(0.0f);
        this.avatarProgressView.setVisibility(4);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onActivityResultFragment(int i, int i2, Intent intent) {
        this.imageUpdater.onActivityResult(i, i2, intent);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void saveSelfArgs(Bundle bundle) {
        String str;
        ImageUpdater imageUpdater = this.imageUpdater;
        if (imageUpdater != null && (str = imageUpdater.currentPicturePath) != null) {
            bundle.putString("path", str);
        }
        EditTextEmoji editTextEmoji = this.editText;
        if (editTextEmoji != null) {
            String obj = editTextEmoji.getText().toString();
            if (obj.length() != 0) {
                bundle.putString("nameTextView", obj);
            }
        }
    }

    public void restoreSelfArgs(Bundle bundle) {
        ImageUpdater imageUpdater = this.imageUpdater;
        if (imageUpdater != null) {
            imageUpdater.currentPicturePath = bundle.getString("path");
        }
        String string = bundle.getString("nameTextView");
        if (string != null) {
            EditTextEmoji editTextEmoji = this.editText;
            if (editTextEmoji != null) {
                editTextEmoji.setText(string);
            } else {
                this.nameToSet = string;
            }
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z) {
            this.editText.openKeyboard();
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.updateInterfaces) {
            if (this.listView == null) {
                return;
            }
            int intValue = ((Integer) objArr[0]).intValue();
            if ((MessagesController.UPDATE_MASK_AVATAR & intValue) == 0 && (MessagesController.UPDATE_MASK_NAME & intValue) == 0 && (MessagesController.UPDATE_MASK_STATUS & intValue) == 0) {
                return;
            }
            int childCount = this.listView.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = this.listView.getChildAt(i3);
                if (childAt instanceof GroupCreateUserCell) {
                    ((GroupCreateUserCell) childAt).update(intValue);
                }
            }
            return;
        }
        if (i == NotificationCenter.chatDidFailCreate) {
            this.reqId = 0;
            this.donePressed = false;
            showEditDoneProgress(false);
            EditTextEmoji editTextEmoji = this.editText;
            if (editTextEmoji != null) {
                editTextEmoji.setEnabled(true);
            }
            GroupCreateFinalActivityDelegate groupCreateFinalActivityDelegate = this.delegate;
            if (groupCreateFinalActivityDelegate != null) {
                groupCreateFinalActivityDelegate.didFailChatCreation();
                return;
            }
            return;
        }
        if (i == NotificationCenter.chatDidCreated) {
            this.reqId = 0;
            long longValue = ((Long) objArr[0]).longValue();
            GroupCreateFinalActivityDelegate groupCreateFinalActivityDelegate2 = this.delegate;
            if (groupCreateFinalActivityDelegate2 != null) {
                groupCreateFinalActivityDelegate2.didFinishChatCreation(this, longValue);
            } else {
                NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.closeChats, new Object[0]);
                Bundle bundle = new Bundle();
                bundle.putLong("chat_id", longValue);
                bundle.putBoolean("just_created_chat", true);
                presentFragment(new ChatActivity(bundle), true);
            }
            if (this.inputPhoto == null && this.inputVideo == null && this.inputEmojiMarkup == null) {
                return;
            }
            getMessagesController().changeChatAvatar(longValue, null, this.inputPhoto, this.inputVideo, this.inputEmojiMarkup, this.videoTimestamp, this.inputVideoPath, this.avatar, this.avatarBig, null);
        }
    }

    private void showEditDoneProgress(final boolean z) {
        if (this.floatingButtonIcon == null) {
            return;
        }
        AnimatorSet animatorSet = this.doneItemAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.doneItemAnimation = new AnimatorSet();
        if (z) {
            this.progressView.setVisibility(0);
            this.floatingButtonContainer.setEnabled(false);
            this.doneItemAnimation.playTogether(ObjectAnimator.ofFloat(this.floatingButtonIcon, "scaleX", 0.1f), ObjectAnimator.ofFloat(this.floatingButtonIcon, "scaleY", 0.1f), ObjectAnimator.ofFloat(this.floatingButtonIcon, "alpha", 0.0f), ObjectAnimator.ofFloat(this.progressView, "scaleX", 1.0f), ObjectAnimator.ofFloat(this.progressView, "scaleY", 1.0f), ObjectAnimator.ofFloat(this.progressView, "alpha", 1.0f));
        } else {
            this.floatingButtonIcon.setVisibility(0);
            this.floatingButtonContainer.setEnabled(true);
            this.doneItemAnimation.playTogether(ObjectAnimator.ofFloat(this.progressView, "scaleX", 0.1f), ObjectAnimator.ofFloat(this.progressView, "scaleY", 0.1f), ObjectAnimator.ofFloat(this.progressView, "alpha", 0.0f), ObjectAnimator.ofFloat(this.floatingButtonIcon, "scaleX", 1.0f), ObjectAnimator.ofFloat(this.floatingButtonIcon, "scaleY", 1.0f), ObjectAnimator.ofFloat(this.floatingButtonIcon, "alpha", 1.0f));
        }
        this.doneItemAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.GroupCreateFinalActivity.12
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (GroupCreateFinalActivity.this.doneItemAnimation == null || !GroupCreateFinalActivity.this.doneItemAnimation.equals(animator)) {
                    return;
                }
                if (!z) {
                    GroupCreateFinalActivity.this.progressView.setVisibility(4);
                } else {
                    GroupCreateFinalActivity.this.floatingButtonIcon.setVisibility(4);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                if (GroupCreateFinalActivity.this.doneItemAnimation == null || !GroupCreateFinalActivity.this.doneItemAnimation.equals(animator)) {
                    return;
                }
                GroupCreateFinalActivity.this.doneItemAnimation = null;
            }
        });
        this.doneItemAnimation.setDuration(150L);
        this.doneItemAnimation.start();
    }

    public class GroupCreateAdapter extends RecyclerListView.SelectionAdapter {
        private Context context;
        ArrayList<InnerItem> items = new ArrayList<>();
        private int usersStartRow;

        public GroupCreateAdapter(Context context) {
            this.context = context;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            this.items.clear();
            this.items.add(new InnerItem(this, 0));
            if (GroupCreateFinalActivity.this.chatType == 5) {
                this.items.add(new InnerItem(this, 6));
                this.items.add(new InnerItem(this, 5, LocaleController.getString("ForumToggleDescription", R.string.ForumToggleDescription)));
            }
            if (GroupCreateFinalActivity.this.currentGroupCreateAddress != null) {
                this.items.add(new InnerItem(this, 1));
                this.items.add(new InnerItem(this, 3));
                this.items.add(new InnerItem(this, 0));
            }
            if (GroupCreateFinalActivity.this.selectedContacts.size() > 0) {
                this.items.add(new InnerItem(this, 1));
                this.usersStartRow = this.items.size();
                for (int i = 0; i < GroupCreateFinalActivity.this.selectedContacts.size(); i++) {
                    this.items.add(new InnerItem(this, 2));
                }
            }
            this.items.add(new InnerItem(this, 7));
            super.notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.items.size();
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 3 || viewHolder.getItemViewType() == 4 || (viewHolder.getItemViewType() == 6 && GroupCreateFinalActivity.this.canToggleTopics);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View shadowSectionCell;
            View view;
            if (i == 0) {
                shadowSectionCell = new ShadowSectionCell(this.context);
                CombinedDrawable combinedDrawable = new CombinedDrawable(new ColorDrawable(Theme.getColor(Theme.key_windowBackgroundGray)), Theme.getThemedDrawableByKey(this.context, R.drawable.greydivider_top, Theme.key_windowBackgroundGrayShadow));
                combinedDrawable.setFullsize(true);
                shadowSectionCell.setBackgroundDrawable(combinedDrawable);
            } else {
                if (i == 1) {
                    HeaderCell headerCell = new HeaderCell(this.context);
                    headerCell.setHeight(46);
                    view = headerCell;
                } else if (i == 2) {
                    view = new GroupCreateUserCell(this.context, 0, 3, false);
                } else if (i == 4) {
                    view = new TextCell(this.context);
                } else if (i == 5) {
                    shadowSectionCell = new TextInfoPrivacyCell(this.context);
                    CombinedDrawable combinedDrawable2 = new CombinedDrawable(new ColorDrawable(Theme.getColor(Theme.key_windowBackgroundGray)), Theme.getThemedDrawableByKey(this.context, GroupCreateFinalActivity.this.selectedContacts.size() == 0 ? R.drawable.greydivider_bottom : R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    combinedDrawable2.setFullsize(true);
                    shadowSectionCell.setBackgroundDrawable(combinedDrawable2);
                } else if (i == 6) {
                    view = new TextCell(this.context, 23, false, true, GroupCreateFinalActivity.this.getResourceProvider());
                } else if (i != 7) {
                    view = new TextSettingsCell(this.context);
                } else {
                    View view2 = new View(this.context);
                    view = view2;
                    if (GroupCreateFinalActivity.this.selectedContacts.isEmpty()) {
                        view2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
                        view = view2;
                    }
                }
                return new RecyclerListView.Holder(view);
            }
            view = shadowSectionCell;
            return new RecyclerListView.Holder(view);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            String formatTTLString;
            switch (viewHolder.getItemViewType()) {
                case 1:
                    HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                    if (GroupCreateFinalActivity.this.currentGroupCreateAddress == null || i != 1) {
                        headerCell.setText(LocaleController.formatPluralString("Members", GroupCreateFinalActivity.this.selectedContacts.size(), new Object[0]));
                        break;
                    } else {
                        headerCell.setText(LocaleController.getString("AttachLocation", R.string.AttachLocation));
                        break;
                    }
                case 2:
                    GroupCreateUserCell groupCreateUserCell = (GroupCreateUserCell) viewHolder.itemView;
                    groupCreateUserCell.setObject(GroupCreateFinalActivity.this.getMessagesController().getUser((Long) GroupCreateFinalActivity.this.selectedContacts.get(i - this.usersStartRow)), null, null);
                    groupCreateUserCell.setDrawDivider(i != this.items.size() - 1);
                    break;
                case 3:
                    ((TextSettingsCell) viewHolder.itemView).setText(GroupCreateFinalActivity.this.currentGroupCreateAddress, false);
                    break;
                case 4:
                    TextCell textCell = (TextCell) viewHolder.itemView;
                    if (GroupCreateFinalActivity.this.ttlPeriod != 0) {
                        formatTTLString = LocaleController.formatTTLString(GroupCreateFinalActivity.this.ttlPeriod);
                    } else {
                        formatTTLString = LocaleController.getString("PasswordOff", R.string.PasswordOff);
                    }
                    textCell.setTextAndValueAndIcon(LocaleController.getString("AutoDeleteMessages", R.string.AutoDeleteMessages), formatTTLString, ((BaseFragment) GroupCreateFinalActivity.this).fragmentBeginToShow, R.drawable.msg_autodelete, false);
                    break;
                case 5:
                    ((TextInfoPrivacyCell) viewHolder.itemView).setText(this.items.get(i).string);
                    break;
                case 6:
                    TextCell textCell2 = (TextCell) viewHolder.itemView;
                    textCell2.setTextAndCheckAndIcon(LocaleController.getString("ChannelTopics", R.string.ChannelTopics), true, R.drawable.msg_topics, false);
                    textCell2.getCheckBox().setAlpha(0.75f);
                    break;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            return this.items.get(i).viewType;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() == 2) {
                ((GroupCreateUserCell) viewHolder.itemView).recycle();
            }
        }

        private class InnerItem extends AdapterWithDiffUtils.Item {
            String string;

            public InnerItem(GroupCreateAdapter groupCreateAdapter, int i) {
                super(i, true);
            }

            public InnerItem(GroupCreateAdapter groupCreateAdapter, int i, String str) {
                super(i, true);
                this.string = str;
            }
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        ThemeDescription.ThemeDescriptionDelegate themeDescriptionDelegate = new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda7
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                GroupCreateFinalActivity.this.lambda$getThemeDescriptions$9();
            }

            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public /* synthetic */ void onAnimationProgress(float f) {
                ThemeDescription.ThemeDescriptionDelegate.CC.$default$onAnimationProgress(this, f);
            }
        };
        arrayList.add(new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
        ActionBar actionBar = this.actionBar;
        int i = ThemeDescription.FLAG_BACKGROUND;
        int i2 = Theme.key_actionBarDefault;
        arrayList.add(new ThemeDescription(actionBar, i, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollActive));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollInactive));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollText));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));
        EditTextEmoji editTextEmoji = this.editText;
        int i3 = ThemeDescription.FLAG_TEXTCOLOR;
        int i4 = Theme.key_windowBackgroundWhiteBlackText;
        arrayList.add(new ThemeDescription(editTextEmoji, i3, null, null, null, null, i4));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_groupcreate_hintText));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_CURSORCOLOR, null, null, null, null, Theme.key_groupcreate_cursor));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGray));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlueHeader));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{GroupCreateUserCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_groupcreate_sectionText));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{GroupCreateUserCell.class}, new String[]{"statusTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlueText));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{GroupCreateUserCell.class}, new String[]{"statusTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{GroupCreateUserCell.class}, null, Theme.avatarDrawables, themeDescriptionDelegate, Theme.key_avatar_text));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundRed));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundOrange));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundViolet));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundGreen));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundCyan));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundBlue));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundPink));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i4));
        arrayList.add(new ThemeDescription(this.progressView, 0, null, null, null, null, Theme.key_contextProgressInner2));
        arrayList.add(new ThemeDescription(this.progressView, 0, null, null, null, null, Theme.key_contextProgressOuter2));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, i4));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getThemeDescriptions$9() {
        RecyclerListView recyclerListView = this.listView;
        if (recyclerListView != null) {
            int childCount = recyclerListView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.listView.getChildAt(i);
                if (childAt instanceof GroupCreateUserCell) {
                    ((GroupCreateUserCell) childAt).update(0);
                }
            }
        }
    }
}

package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Property;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.ScrollView;
import androidx.annotation.Keep;
import androidx.collection.LongSparseArray;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Dialog;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Adapters.SearchAdapterHelper;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.GroupCreateUserCell;
import org.telegram.ui.Components.AnimatedAvatarContainer;
import org.telegram.ui.Components.AnimatedTextView;
import org.telegram.ui.Components.BulletinFactory;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.GroupCreateSpan;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.UsersSelectActivity;

/* loaded from: classes3.dex */
public class UsersSelectActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate, View.OnClickListener {
    private GroupCreateAdapter adapter;
    private ArrayList<GroupCreateSpan> allSpans;
    AnimatedAvatarContainer animatedAvatarContainer;
    private int containerHeight;
    private GroupCreateSpan currentDeletingSpan;
    private FilterUsersActivityDelegate delegate;
    private EditTextBoldCursor editText;
    private EmptyTextProgressView emptyView;
    private int fieldY;
    private int filterFlags;
    private ImageView floatingButton;
    private boolean ignoreScrollEvent;
    private ArrayList<Long> initialIds;
    private boolean isInclude;
    private RecyclerListView listView;
    public boolean noChatTypes;
    private ScrollView scrollView;
    private boolean searchWas;
    private boolean searching;
    private LongSparseArray<GroupCreateSpan> selectedContacts;
    private int selectedCount;
    private SpansContainer spansContainer;
    private int ttlPeriod;
    private int type;

    public interface FilterUsersActivityDelegate {
        void didSelectChats(ArrayList<Long> arrayList, int i);
    }

    static /* synthetic */ int access$1972(UsersSelectActivity usersSelectActivity, int i) {
        int i2 = i & usersSelectActivity.filterFlags;
        usersSelectActivity.filterFlags = i2;
        return i2;
    }

    static /* synthetic */ int access$508(UsersSelectActivity usersSelectActivity) {
        int i = usersSelectActivity.selectedCount;
        usersSelectActivity.selectedCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$510(UsersSelectActivity usersSelectActivity) {
        int i = usersSelectActivity.selectedCount;
        usersSelectActivity.selectedCount = i - 1;
        return i;
    }

    public void setTtlPeriod(int i) {
        this.ttlPeriod = i;
    }

    private static class ItemDecoration extends RecyclerView.ItemDecoration {
        private boolean single;
        private int skipRows;

        private ItemDecoration() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            int width = recyclerView.getWidth();
            int childCount = recyclerView.getChildCount() - (!this.single ? 1 : 0);
            int i = 0;
            while (i < childCount) {
                View childAt = recyclerView.getChildAt(i);
                View childAt2 = i < childCount + (-1) ? recyclerView.getChildAt(i + 1) : null;
                if (recyclerView.getChildAdapterPosition(childAt) >= this.skipRows && !(childAt instanceof GraySectionCell) && !(childAt2 instanceof GraySectionCell)) {
                    float bottom = childAt.getBottom();
                    canvas.drawLine(LocaleController.isRTL ? 0.0f : AndroidUtilities.dp(72.0f), bottom, width - (LocaleController.isRTL ? AndroidUtilities.dp(72.0f) : 0), bottom, Theme.dividerPaint);
                }
                i++;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            super.getItemOffsets(rect, view, recyclerView, state);
            rect.top = 1;
        }
    }

    private class SpansContainer extends ViewGroup {
        private View addingSpan;
        private boolean animationStarted;
        private ArrayList<Animator> animators;
        private AnimatorSet currentAnimation;
        private View removingSpan;

        public SpansContainer(Context context) {
            super(context);
            this.animators = new ArrayList<>();
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            int min;
            int childCount = getChildCount();
            int size = View.MeasureSpec.getSize(i);
            int dp = size - AndroidUtilities.dp(26.0f);
            int dp2 = AndroidUtilities.dp(10.0f);
            int dp3 = AndroidUtilities.dp(10.0f);
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                if (childAt instanceof GroupCreateSpan) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(size, LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(32.0f), 1073741824));
                    if (childAt != this.removingSpan && childAt.getMeasuredWidth() + i3 > dp) {
                        dp2 += childAt.getMeasuredHeight() + AndroidUtilities.dp(8.0f);
                        i3 = 0;
                    }
                    if (childAt.getMeasuredWidth() + i4 > dp) {
                        dp3 += childAt.getMeasuredHeight() + AndroidUtilities.dp(8.0f);
                        i4 = 0;
                    }
                    int dp4 = AndroidUtilities.dp(13.0f) + i3;
                    if (!this.animationStarted) {
                        View view = this.removingSpan;
                        if (childAt == view) {
                            childAt.setTranslationX(AndroidUtilities.dp(13.0f) + i4);
                            childAt.setTranslationY(dp3);
                        } else if (view != null) {
                            float f = dp4;
                            if (childAt.getTranslationX() != f) {
                                this.animators.add(ObjectAnimator.ofFloat(childAt, (Property<View, Float>) View.TRANSLATION_X, f));
                            }
                            float f2 = dp2;
                            if (childAt.getTranslationY() != f2) {
                                this.animators.add(ObjectAnimator.ofFloat(childAt, (Property<View, Float>) View.TRANSLATION_Y, f2));
                            }
                        } else {
                            childAt.setTranslationX(dp4);
                            childAt.setTranslationY(dp2);
                        }
                    }
                    if (childAt != this.removingSpan) {
                        i3 += childAt.getMeasuredWidth() + AndroidUtilities.dp(9.0f);
                    }
                    i4 += childAt.getMeasuredWidth() + AndroidUtilities.dp(9.0f);
                }
            }
            if (AndroidUtilities.isTablet()) {
                min = AndroidUtilities.dp(372.0f) / 3;
            } else {
                Point point = AndroidUtilities.displaySize;
                min = (Math.min(point.x, point.y) - AndroidUtilities.dp(158.0f)) / 3;
            }
            if (dp - i3 < min) {
                dp2 += AndroidUtilities.dp(40.0f);
                i3 = 0;
            }
            if (dp - i4 < min) {
                dp3 += AndroidUtilities.dp(40.0f);
            }
            UsersSelectActivity.this.editText.measure(View.MeasureSpec.makeMeasureSpec(dp - i3, 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(32.0f), 1073741824));
            if (!this.animationStarted) {
                int dp5 = dp3 + AndroidUtilities.dp(42.0f);
                int dp6 = i3 + AndroidUtilities.dp(16.0f);
                UsersSelectActivity.this.fieldY = dp2;
                if (this.currentAnimation == null) {
                    UsersSelectActivity.this.containerHeight = dp5;
                    UsersSelectActivity.this.editText.setTranslationX(dp6);
                    UsersSelectActivity.this.editText.setTranslationY(UsersSelectActivity.this.fieldY);
                } else {
                    int dp7 = dp2 + AndroidUtilities.dp(42.0f);
                    if (UsersSelectActivity.this.containerHeight != dp7) {
                        this.animators.add(ObjectAnimator.ofInt(UsersSelectActivity.this, "containerHeight", dp7));
                    }
                    float f3 = dp6;
                    if (UsersSelectActivity.this.editText.getTranslationX() != f3) {
                        this.animators.add(ObjectAnimator.ofFloat(UsersSelectActivity.this.editText, (Property<EditTextBoldCursor, Float>) View.TRANSLATION_X, f3));
                    }
                    if (UsersSelectActivity.this.editText.getTranslationY() != UsersSelectActivity.this.fieldY) {
                        this.animators.add(ObjectAnimator.ofFloat(UsersSelectActivity.this.editText, (Property<EditTextBoldCursor, Float>) View.TRANSLATION_Y, UsersSelectActivity.this.fieldY));
                    }
                    UsersSelectActivity.this.editText.setAllowDrawCursor(false);
                    this.currentAnimation.playTogether(this.animators);
                    this.currentAnimation.start();
                    this.animationStarted = true;
                }
            } else if (this.currentAnimation != null && !UsersSelectActivity.this.ignoreScrollEvent && this.removingSpan == null) {
                UsersSelectActivity.this.editText.bringPointIntoView(UsersSelectActivity.this.editText.getSelectionStart());
            }
            setMeasuredDimension(size, UsersSelectActivity.this.containerHeight);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                childAt.layout(0, 0, childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
            }
        }

        public void addSpan(GroupCreateSpan groupCreateSpan, boolean z) {
            UsersSelectActivity.this.allSpans.add(groupCreateSpan);
            long uid = groupCreateSpan.getUid();
            if (uid > -9223372036854775801L) {
                UsersSelectActivity.access$508(UsersSelectActivity.this);
            }
            UsersSelectActivity.this.selectedContacts.put(uid, groupCreateSpan);
            UsersSelectActivity.this.editText.setHintVisible(false, TextUtils.isEmpty(UsersSelectActivity.this.editText.getText()));
            AnimatorSet animatorSet = this.currentAnimation;
            if (animatorSet != null && animatorSet.isRunning()) {
                this.currentAnimation.setupEndValues();
                this.currentAnimation.cancel();
            }
            this.animationStarted = false;
            if (z) {
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.currentAnimation = animatorSet2;
                animatorSet2.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.UsersSelectActivity.SpansContainer.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        SpansContainer.this.addingSpan = null;
                        SpansContainer.this.currentAnimation = null;
                        SpansContainer.this.animationStarted = false;
                        UsersSelectActivity.this.editText.setAllowDrawCursor(true);
                    }
                });
                this.currentAnimation.setDuration(150L);
                this.addingSpan = groupCreateSpan;
                this.animators.clear();
                this.animators.add(ObjectAnimator.ofFloat(this.addingSpan, (Property<View, Float>) View.SCALE_X, 0.01f, 1.0f));
                this.animators.add(ObjectAnimator.ofFloat(this.addingSpan, (Property<View, Float>) View.SCALE_Y, 0.01f, 1.0f));
                this.animators.add(ObjectAnimator.ofFloat(this.addingSpan, (Property<View, Float>) View.ALPHA, 0.0f, 1.0f));
            }
            addView(groupCreateSpan);
        }

        public void removeSpan(final GroupCreateSpan groupCreateSpan) {
            UsersSelectActivity.this.ignoreScrollEvent = true;
            long uid = groupCreateSpan.getUid();
            if (uid > -9223372036854775801L) {
                UsersSelectActivity.access$510(UsersSelectActivity.this);
            }
            UsersSelectActivity.this.selectedContacts.remove(uid);
            UsersSelectActivity.this.allSpans.remove(groupCreateSpan);
            groupCreateSpan.setOnClickListener(null);
            AnimatorSet animatorSet = this.currentAnimation;
            if (animatorSet != null) {
                animatorSet.setupEndValues();
                this.currentAnimation.cancel();
            }
            this.animationStarted = false;
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.currentAnimation = animatorSet2;
            animatorSet2.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.UsersSelectActivity.SpansContainer.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    SpansContainer.this.removeView(groupCreateSpan);
                    SpansContainer.this.removingSpan = null;
                    SpansContainer.this.currentAnimation = null;
                    SpansContainer.this.animationStarted = false;
                    UsersSelectActivity.this.editText.setAllowDrawCursor(true);
                    if (UsersSelectActivity.this.allSpans.isEmpty()) {
                        UsersSelectActivity.this.editText.setHintVisible(true, true);
                    }
                }
            });
            this.currentAnimation.setDuration(150L);
            this.removingSpan = groupCreateSpan;
            this.animators.clear();
            this.animators.add(ObjectAnimator.ofFloat(this.removingSpan, (Property<View, Float>) View.SCALE_X, 1.0f, 0.01f));
            this.animators.add(ObjectAnimator.ofFloat(this.removingSpan, (Property<View, Float>) View.SCALE_Y, 1.0f, 0.01f));
            this.animators.add(ObjectAnimator.ofFloat(this.removingSpan, (Property<View, Float>) View.ALPHA, 1.0f, 0.0f));
            requestLayout();
        }
    }

    public UsersSelectActivity(boolean z, ArrayList<Long> arrayList, int i) {
        this.selectedContacts = new LongSparseArray<>();
        this.allSpans = new ArrayList<>();
        this.isInclude = z;
        this.filterFlags = i;
        this.initialIds = arrayList;
        this.type = 0;
    }

    public UsersSelectActivity(int i) {
        this.selectedContacts = new LongSparseArray<>();
        this.allSpans = new ArrayList<>();
        this.type = i;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.contactsDidLoad);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.chatDidCreated);
        return super.onFragmentCreate();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.contactsDidLoad);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.chatDidCreated);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        GroupCreateSpan groupCreateSpan = (GroupCreateSpan) view;
        if (groupCreateSpan.isDeleting()) {
            this.currentDeletingSpan = null;
            this.spansContainer.removeSpan(groupCreateSpan);
            if (groupCreateSpan.getUid() == Long.MIN_VALUE) {
                this.filterFlags &= ~MessagesController.DIALOG_FILTER_FLAG_CONTACTS;
            } else if (groupCreateSpan.getUid() == -9223372036854775807L) {
                this.filterFlags &= ~MessagesController.DIALOG_FILTER_FLAG_NON_CONTACTS;
            } else if (groupCreateSpan.getUid() == -9223372036854775806L) {
                this.filterFlags &= ~MessagesController.DIALOG_FILTER_FLAG_GROUPS;
            } else if (groupCreateSpan.getUid() == -9223372036854775805L) {
                this.filterFlags &= ~MessagesController.DIALOG_FILTER_FLAG_CHANNELS;
            } else if (groupCreateSpan.getUid() == -9223372036854775804L) {
                this.filterFlags &= ~MessagesController.DIALOG_FILTER_FLAG_BOTS;
            } else if (groupCreateSpan.getUid() == -9223372036854775803L) {
                this.filterFlags &= ~MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_MUTED;
            } else if (groupCreateSpan.getUid() == -9223372036854775802L) {
                this.filterFlags &= ~MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_READ;
            } else if (groupCreateSpan.getUid() == -9223372036854775801L) {
                this.filterFlags &= ~MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_ARCHIVED;
            }
            updateHint();
            checkVisibleRows();
            return;
        }
        GroupCreateSpan groupCreateSpan2 = this.currentDeletingSpan;
        if (groupCreateSpan2 != null) {
            groupCreateSpan2.cancelDeleteAnimation();
        }
        this.currentDeletingSpan = groupCreateSpan;
        groupCreateSpan.startDeleteAnimation();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(final Context context) {
        Object chat;
        int i;
        String str;
        this.searching = false;
        this.searchWas = false;
        this.allSpans.clear();
        this.selectedContacts.clear();
        this.currentDeletingSpan = null;
        if (this.type == 1) {
            AnimatedAvatarContainer animatedAvatarContainer = new AnimatedAvatarContainer(getContext());
            this.animatedAvatarContainer = animatedAvatarContainer;
            ActionBar actionBar = this.actionBar;
            boolean z = LocaleController.isRTL;
            actionBar.addView(animatedAvatarContainer, LayoutHelper.createFrame(-1, -1.0f, 0, z ? 0.0f : 64.0f, 0.0f, z ? 64.0f : 0.0f, 0.0f));
            this.actionBar.setAllowOverlayTitle(false);
        }
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        int i2 = this.type;
        if (i2 == 0) {
            if (this.isInclude) {
                this.actionBar.setTitle(LocaleController.getString("FilterAlwaysShow", R.string.FilterAlwaysShow));
            } else {
                this.actionBar.setTitle(LocaleController.getString("FilterNeverShow", R.string.FilterNeverShow));
            }
        } else if (i2 == 1) {
            updateHint();
        }
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.UsersSelectActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i3) {
                if (i3 == -1) {
                    UsersSelectActivity.this.finishFragment();
                } else if (i3 == 1) {
                    UsersSelectActivity.this.onDonePressed(true);
                }
            }
        });
        ViewGroup viewGroup = new ViewGroup(context) { // from class: org.telegram.ui.UsersSelectActivity.2
            @Override // android.view.View
            protected void onMeasure(int i3, int i4) {
                int dp;
                int size = View.MeasureSpec.getSize(i3);
                int size2 = View.MeasureSpec.getSize(i4);
                setMeasuredDimension(size, size2);
                if (AndroidUtilities.isTablet() || size2 > size) {
                    dp = AndroidUtilities.dp(144.0f);
                } else {
                    dp = AndroidUtilities.dp(56.0f);
                }
                UsersSelectActivity.this.scrollView.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(dp, LinearLayoutManager.INVALID_OFFSET));
                UsersSelectActivity.this.listView.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2 - UsersSelectActivity.this.scrollView.getMeasuredHeight(), 1073741824));
                UsersSelectActivity.this.emptyView.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2 - UsersSelectActivity.this.scrollView.getMeasuredHeight(), 1073741824));
                if (UsersSelectActivity.this.floatingButton != null) {
                    int dp2 = AndroidUtilities.dp(Build.VERSION.SDK_INT < 21 ? 60.0f : 56.0f);
                    UsersSelectActivity.this.floatingButton.measure(View.MeasureSpec.makeMeasureSpec(dp2, 1073741824), View.MeasureSpec.makeMeasureSpec(dp2, 1073741824));
                }
            }

            @Override // android.view.ViewGroup, android.view.View
            protected void onLayout(boolean z2, int i3, int i4, int i5, int i6) {
                UsersSelectActivity.this.scrollView.layout(0, 0, UsersSelectActivity.this.scrollView.getMeasuredWidth(), UsersSelectActivity.this.scrollView.getMeasuredHeight());
                UsersSelectActivity.this.listView.layout(0, UsersSelectActivity.this.scrollView.getMeasuredHeight(), UsersSelectActivity.this.listView.getMeasuredWidth(), UsersSelectActivity.this.scrollView.getMeasuredHeight() + UsersSelectActivity.this.listView.getMeasuredHeight());
                UsersSelectActivity.this.emptyView.layout(0, UsersSelectActivity.this.scrollView.getMeasuredHeight(), UsersSelectActivity.this.emptyView.getMeasuredWidth(), UsersSelectActivity.this.scrollView.getMeasuredHeight() + UsersSelectActivity.this.emptyView.getMeasuredHeight());
                if (UsersSelectActivity.this.floatingButton != null) {
                    int dp = LocaleController.isRTL ? AndroidUtilities.dp(14.0f) : ((i5 - i3) - AndroidUtilities.dp(14.0f)) - UsersSelectActivity.this.floatingButton.getMeasuredWidth();
                    int dp2 = ((i6 - i4) - AndroidUtilities.dp(14.0f)) - UsersSelectActivity.this.floatingButton.getMeasuredHeight();
                    UsersSelectActivity.this.floatingButton.layout(dp, dp2, UsersSelectActivity.this.floatingButton.getMeasuredWidth() + dp, UsersSelectActivity.this.floatingButton.getMeasuredHeight() + dp2);
                }
            }

            @Override // android.view.ViewGroup
            protected boolean drawChild(Canvas canvas, View view, long j) {
                boolean drawChild = super.drawChild(canvas, view, j);
                if (view == UsersSelectActivity.this.listView || view == UsersSelectActivity.this.emptyView) {
                    ((BaseFragment) UsersSelectActivity.this).parentLayout.drawHeaderShadow(canvas, UsersSelectActivity.this.scrollView.getMeasuredHeight());
                }
                return drawChild;
            }
        };
        this.fragmentView = viewGroup;
        ViewGroup viewGroup2 = viewGroup;
        ScrollView scrollView = new ScrollView(context) { // from class: org.telegram.ui.UsersSelectActivity.3
            @Override // android.widget.ScrollView, android.view.ViewGroup, android.view.ViewParent
            public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z2) {
                if (UsersSelectActivity.this.ignoreScrollEvent) {
                    UsersSelectActivity.this.ignoreScrollEvent = false;
                    return false;
                }
                rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
                rect.top += UsersSelectActivity.this.fieldY + AndroidUtilities.dp(20.0f);
                rect.bottom += UsersSelectActivity.this.fieldY + AndroidUtilities.dp(50.0f);
                return super.requestChildRectangleOnScreen(view, rect, z2);
            }
        };
        this.scrollView = scrollView;
        scrollView.setVerticalScrollBarEnabled(false);
        AndroidUtilities.setScrollViewEdgeEffectColor(this.scrollView, Theme.getColor(Theme.key_windowBackgroundWhite));
        viewGroup2.addView(this.scrollView);
        SpansContainer spansContainer = new SpansContainer(context);
        this.spansContainer = spansContainer;
        this.scrollView.addView(spansContainer, LayoutHelper.createFrame(-1, -2.0f));
        this.spansContainer.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.UsersSelectActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UsersSelectActivity.this.lambda$createView$0(view);
            }
        });
        EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(context) { // from class: org.telegram.ui.UsersSelectActivity.4
            @Override // org.telegram.ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                if (UsersSelectActivity.this.currentDeletingSpan != null) {
                    UsersSelectActivity.this.currentDeletingSpan.cancelDeleteAnimation();
                    UsersSelectActivity.this.currentDeletingSpan = null;
                }
                if (motionEvent.getAction() == 0 && !AndroidUtilities.showKeyboard(this)) {
                    clearFocus();
                    requestFocus();
                }
                return super.onTouchEvent(motionEvent);
            }
        };
        this.editText = editTextBoldCursor;
        editTextBoldCursor.setTextSize(1, 16.0f);
        this.editText.setHintColor(Theme.getColor(Theme.key_groupcreate_hintText));
        this.editText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.editText.setCursorColor(Theme.getColor(Theme.key_groupcreate_cursor));
        this.editText.setCursorWidth(1.5f);
        this.editText.setInputType(655536);
        this.editText.setSingleLine(true);
        this.editText.setBackgroundDrawable(null);
        this.editText.setVerticalScrollBarEnabled(false);
        this.editText.setHorizontalScrollBarEnabled(false);
        this.editText.setTextIsSelectable(false);
        this.editText.setPadding(0, 0, 0, 0);
        this.editText.setImeOptions(268435462);
        this.editText.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        this.spansContainer.addView(this.editText);
        this.editText.setHintText(LocaleController.getString("SearchForPeopleAndGroups", R.string.SearchForPeopleAndGroups));
        this.editText.setCustomSelectionActionModeCallback(new ActionMode.Callback(this) { // from class: org.telegram.ui.UsersSelectActivity.5
            @Override // android.view.ActionMode.Callback
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override // android.view.ActionMode.Callback
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override // android.view.ActionMode.Callback
            public void onDestroyActionMode(ActionMode actionMode) {
            }

            @Override // android.view.ActionMode.Callback
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }
        });
        this.editText.setOnKeyListener(new View.OnKeyListener() { // from class: org.telegram.ui.UsersSelectActivity.6
            private boolean wasEmpty;

            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i3, KeyEvent keyEvent) {
                if (i3 == 67) {
                    if (keyEvent.getAction() == 0) {
                        this.wasEmpty = UsersSelectActivity.this.editText.length() == 0;
                    } else if (keyEvent.getAction() == 1 && this.wasEmpty && !UsersSelectActivity.this.allSpans.isEmpty()) {
                        GroupCreateSpan groupCreateSpan = (GroupCreateSpan) UsersSelectActivity.this.allSpans.get(UsersSelectActivity.this.allSpans.size() - 1);
                        UsersSelectActivity.this.spansContainer.removeSpan(groupCreateSpan);
                        if (groupCreateSpan.getUid() == Long.MIN_VALUE) {
                            UsersSelectActivity.access$1972(UsersSelectActivity.this, ~MessagesController.DIALOG_FILTER_FLAG_CONTACTS);
                        } else if (groupCreateSpan.getUid() == -9223372036854775807L) {
                            UsersSelectActivity.access$1972(UsersSelectActivity.this, ~MessagesController.DIALOG_FILTER_FLAG_NON_CONTACTS);
                        } else if (groupCreateSpan.getUid() == -9223372036854775806L) {
                            UsersSelectActivity.access$1972(UsersSelectActivity.this, ~MessagesController.DIALOG_FILTER_FLAG_GROUPS);
                        } else if (groupCreateSpan.getUid() == -9223372036854775805L) {
                            UsersSelectActivity.access$1972(UsersSelectActivity.this, ~MessagesController.DIALOG_FILTER_FLAG_CHANNELS);
                        } else if (groupCreateSpan.getUid() == -9223372036854775804L) {
                            UsersSelectActivity.access$1972(UsersSelectActivity.this, ~MessagesController.DIALOG_FILTER_FLAG_BOTS);
                        } else if (groupCreateSpan.getUid() == -9223372036854775803L) {
                            UsersSelectActivity.access$1972(UsersSelectActivity.this, ~MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_MUTED);
                        } else if (groupCreateSpan.getUid() == -9223372036854775802L) {
                            UsersSelectActivity.access$1972(UsersSelectActivity.this, ~MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_READ);
                        } else if (groupCreateSpan.getUid() == -9223372036854775801L) {
                            UsersSelectActivity.access$1972(UsersSelectActivity.this, ~MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_ARCHIVED);
                        }
                        UsersSelectActivity.this.updateHint();
                        UsersSelectActivity.this.checkVisibleRows();
                        return true;
                    }
                }
                return false;
            }
        });
        this.editText.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.UsersSelectActivity.7
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (UsersSelectActivity.this.editText.length() == 0) {
                    UsersSelectActivity.this.closeSearch();
                    return;
                }
                if (!UsersSelectActivity.this.adapter.searching) {
                    UsersSelectActivity.this.searching = true;
                    UsersSelectActivity.this.searchWas = true;
                    UsersSelectActivity.this.adapter.setSearching(true);
                    UsersSelectActivity.this.listView.setFastScrollVisible(false);
                    UsersSelectActivity.this.listView.setVerticalScrollBarEnabled(true);
                    UsersSelectActivity.this.emptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
                    UsersSelectActivity.this.emptyView.showProgress();
                }
                UsersSelectActivity.this.adapter.searchDialogs(UsersSelectActivity.this.editText.getText().toString());
            }
        });
        this.emptyView = new EmptyTextProgressView(context);
        if (ContactsController.getInstance(this.currentAccount).isLoadingContacts()) {
            this.emptyView.showProgress();
        } else {
            this.emptyView.showTextView();
        }
        this.emptyView.setShowAtCenter(true);
        this.emptyView.setText(LocaleController.getString("NoContacts", R.string.NoContacts));
        viewGroup2.addView(this.emptyView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, 1, false);
        RecyclerListView recyclerListView = new RecyclerListView(context);
        this.listView = recyclerListView;
        recyclerListView.setFastScrollEnabled(0);
        this.listView.setEmptyView(this.emptyView);
        RecyclerListView recyclerListView2 = this.listView;
        GroupCreateAdapter groupCreateAdapter = new GroupCreateAdapter(context);
        this.adapter = groupCreateAdapter;
        recyclerListView2.setAdapter(groupCreateAdapter);
        this.listView.setLayoutManager(linearLayoutManager);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setVerticalScrollbarPosition(LocaleController.isRTL ? 1 : 2);
        this.listView.addItemDecoration(new ItemDecoration());
        viewGroup2.addView(this.listView);
        this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.UsersSelectActivity$$ExternalSyntheticLambda3
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i3) {
                UsersSelectActivity.this.lambda$createView$1(context, view, i3);
            }
        });
        this.listView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.UsersSelectActivity.8
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i3) {
                if (i3 == 1) {
                    AndroidUtilities.hideKeyboard(UsersSelectActivity.this.editText);
                }
            }
        });
        ImageView imageView = new ImageView(context);
        this.floatingButton = imageView;
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        Drawable createSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), Theme.getColor(Theme.key_chats_actionBackground), Theme.getColor(Theme.key_chats_actionPressedBackground));
        int i3 = Build.VERSION.SDK_INT;
        if (i3 < 21) {
            Drawable mutate = context.getResources().getDrawable(R.drawable.floating_shadow).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(-16777216, PorterDuff.Mode.MULTIPLY));
            CombinedDrawable combinedDrawable = new CombinedDrawable(mutate, createSimpleSelectorCircleDrawable, 0, 0);
            combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
            createSimpleSelectorCircleDrawable = combinedDrawable;
        }
        this.floatingButton.setBackgroundDrawable(createSimpleSelectorCircleDrawable);
        this.floatingButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_actionIcon), PorterDuff.Mode.MULTIPLY));
        this.floatingButton.setImageResource(R.drawable.floating_check);
        if (i3 >= 21) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            int[] iArr = {android.R.attr.state_pressed};
            ImageView imageView2 = this.floatingButton;
            Property property = View.TRANSLATION_Z;
            stateListAnimator.addState(iArr, ObjectAnimator.ofFloat(imageView2, (Property<ImageView, Float>) property, AndroidUtilities.dp(2.0f), AndroidUtilities.dp(4.0f)).setDuration(200L));
            stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(this.floatingButton, (Property<ImageView, Float>) property, AndroidUtilities.dp(4.0f), AndroidUtilities.dp(2.0f)).setDuration(200L));
            this.floatingButton.setStateListAnimator(stateListAnimator);
            this.floatingButton.setOutlineProvider(new ViewOutlineProvider(this) { // from class: org.telegram.ui.UsersSelectActivity.9
                @Override // android.view.ViewOutlineProvider
                @SuppressLint({"NewApi"})
                public void getOutline(View view, Outline outline) {
                    outline.setOval(0, 0, AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
                }
            });
        }
        viewGroup2.addView(this.floatingButton);
        this.floatingButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.UsersSelectActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UsersSelectActivity.this.lambda$createView$2(view);
            }
        });
        this.floatingButton.setContentDescription(LocaleController.getString("Next", R.string.Next));
        int i4 = this.isInclude ? 5 : 3;
        for (int i5 = 1; i5 <= i4; i5++) {
            if (this.isInclude) {
                if (i5 == 1) {
                    i = MessagesController.DIALOG_FILTER_FLAG_CONTACTS;
                    str = "contacts";
                } else if (i5 == 2) {
                    i = MessagesController.DIALOG_FILTER_FLAG_NON_CONTACTS;
                    str = "non_contacts";
                } else if (i5 == 3) {
                    i = MessagesController.DIALOG_FILTER_FLAG_GROUPS;
                    str = "groups";
                } else if (i5 == 4) {
                    i = MessagesController.DIALOG_FILTER_FLAG_CHANNELS;
                    str = "channels";
                } else {
                    i = MessagesController.DIALOG_FILTER_FLAG_BOTS;
                    str = "bots";
                }
            } else if (i5 == 1) {
                i = MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_MUTED;
                str = "muted";
            } else if (i5 == 2) {
                i = MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_READ;
                str = "read";
            } else {
                i = MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_ARCHIVED;
                str = "archived";
            }
            if ((i & this.filterFlags) != 0) {
                GroupCreateSpan groupCreateSpan = new GroupCreateSpan(this.editText.getContext(), str);
                this.spansContainer.addSpan(groupCreateSpan, false);
                groupCreateSpan.setOnClickListener(this);
            }
        }
        ArrayList<Long> arrayList = this.initialIds;
        if (arrayList != null && !arrayList.isEmpty()) {
            int size = this.initialIds.size();
            for (int i6 = 0; i6 < size; i6++) {
                Long l = this.initialIds.get(i6);
                if (l.longValue() > 0) {
                    chat = getMessagesController().getUser(l);
                } else {
                    chat = getMessagesController().getChat(Long.valueOf(-l.longValue()));
                }
                if (chat != null) {
                    GroupCreateSpan groupCreateSpan2 = new GroupCreateSpan(this.editText.getContext(), chat);
                    this.spansContainer.addSpan(groupCreateSpan2, false);
                    groupCreateSpan2.setOnClickListener(this);
                }
            }
        }
        updateHint();
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0(View view) {
        this.editText.clearFocus();
        this.editText.requestFocus();
        AndroidUtilities.showKeyboard(this.editText);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$1(Context context, View view, int i) {
        long j;
        int i2;
        if (view instanceof GroupCreateUserCell) {
            GroupCreateUserCell groupCreateUserCell = (GroupCreateUserCell) view;
            Object object = groupCreateUserCell.getObject();
            boolean z = object instanceof String;
            if (z) {
                if (this.isInclude) {
                    if (i == 1) {
                        i2 = MessagesController.DIALOG_FILTER_FLAG_CONTACTS;
                        j = Long.MIN_VALUE;
                    } else if (i == 2) {
                        i2 = MessagesController.DIALOG_FILTER_FLAG_NON_CONTACTS;
                        j = -9223372036854775807L;
                    } else if (i == 3) {
                        i2 = MessagesController.DIALOG_FILTER_FLAG_GROUPS;
                        j = -9223372036854775806L;
                    } else if (i == 4) {
                        i2 = MessagesController.DIALOG_FILTER_FLAG_CHANNELS;
                        j = -9223372036854775805L;
                    } else {
                        i2 = MessagesController.DIALOG_FILTER_FLAG_BOTS;
                        j = -9223372036854775804L;
                    }
                } else if (i == 1) {
                    i2 = MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_MUTED;
                    j = -9223372036854775803L;
                } else if (i == 2) {
                    i2 = MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_READ;
                    j = -9223372036854775802L;
                } else {
                    i2 = MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_ARCHIVED;
                    j = -9223372036854775801L;
                }
                if (groupCreateUserCell.isChecked()) {
                    this.filterFlags = (~i2) & this.filterFlags;
                } else {
                    this.filterFlags = i2 | this.filterFlags;
                }
            } else if (object instanceof TLRPC$User) {
                j = ((TLRPC$User) object).id;
            } else {
                if (!(object instanceof TLRPC$Chat)) {
                    return;
                }
                TLRPC$Chat tLRPC$Chat = (TLRPC$Chat) object;
                j = -tLRPC$Chat.id;
                if (this.type == 1 && !ChatObject.canUserDoAdminAction(tLRPC$Chat, 13)) {
                    BulletinFactory.of(this).createErrorBulletin(LocaleController.getString("NeedAdminRightForSetAutoDeleteTimer", R.string.NeedAdminRightForSetAutoDeleteTimer)).show();
                    return;
                }
            }
            boolean z2 = this.selectedContacts.indexOfKey(j) >= 0;
            if (z2) {
                this.spansContainer.removeSpan(this.selectedContacts.get(j));
            } else if ((!z && !getUserConfig().isPremium() && this.selectedCount >= MessagesController.getInstance(this.currentAccount).dialogFiltersChatsLimitDefault) || this.selectedCount >= MessagesController.getInstance(this.currentAccount).dialogFiltersChatsLimitPremium) {
                LimitReachedBottomSheet limitReachedBottomSheet = new LimitReachedBottomSheet(this, context, 4, this.currentAccount);
                limitReachedBottomSheet.setCurrentValue(this.selectedCount);
                showDialog(limitReachedBottomSheet);
                return;
            } else {
                if (object instanceof TLRPC$User) {
                    MessagesController.getInstance(this.currentAccount).putUser((TLRPC$User) object, !this.searching);
                } else if (object instanceof TLRPC$Chat) {
                    MessagesController.getInstance(this.currentAccount).putChat((TLRPC$Chat) object, !this.searching);
                }
                GroupCreateSpan groupCreateSpan = new GroupCreateSpan(this.editText.getContext(), object);
                this.spansContainer.addSpan(groupCreateSpan, true);
                groupCreateSpan.setOnClickListener(this);
            }
            updateHint();
            if (this.searching || this.searchWas) {
                AndroidUtilities.showKeyboard(this.editText);
            } else {
                groupCreateUserCell.setChecked(!z2, true);
            }
            if (this.editText.length() > 0) {
                this.editText.setText((CharSequence) null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$2(View view) {
        onDonePressed(true);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        EditTextBoldCursor editTextBoldCursor = this.editText;
        if (editTextBoldCursor != null) {
            editTextBoldCursor.requestFocus();
        }
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.contactsDidLoad) {
            EmptyTextProgressView emptyTextProgressView = this.emptyView;
            if (emptyTextProgressView != null) {
                emptyTextProgressView.showTextView();
            }
            GroupCreateAdapter groupCreateAdapter = this.adapter;
            if (groupCreateAdapter != null) {
                groupCreateAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (i == NotificationCenter.updateInterfaces) {
            if (this.listView != null) {
                int intValue = ((Integer) objArr[0]).intValue();
                int childCount = this.listView.getChildCount();
                if ((MessagesController.UPDATE_MASK_AVATAR & intValue) == 0 && (MessagesController.UPDATE_MASK_NAME & intValue) == 0 && (MessagesController.UPDATE_MASK_STATUS & intValue) == 0) {
                    return;
                }
                for (int i3 = 0; i3 < childCount; i3++) {
                    View childAt = this.listView.getChildAt(i3);
                    if (childAt instanceof GroupCreateUserCell) {
                        ((GroupCreateUserCell) childAt).update(intValue);
                    }
                }
                return;
            }
            return;
        }
        if (i == NotificationCenter.chatDidCreated) {
            removeSelfFromStack();
        }
    }

    @Keep
    public void setContainerHeight(int i) {
        this.containerHeight = i;
        SpansContainer spansContainer = this.spansContainer;
        if (spansContainer != null) {
            spansContainer.requestLayout();
        }
    }

    @Keep
    public int getContainerHeight() {
        return this.containerHeight;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkVisibleRows() {
        long j;
        int childCount = this.listView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.listView.getChildAt(i);
            if (childAt instanceof GroupCreateUserCell) {
                GroupCreateUserCell groupCreateUserCell = (GroupCreateUserCell) childAt;
                Object object = groupCreateUserCell.getObject();
                if (object instanceof String) {
                    switch ((String) object) {
                        case "contacts":
                            j = Long.MIN_VALUE;
                            break;
                        case "non_contacts":
                            j = -9223372036854775807L;
                            break;
                        case "groups":
                            j = -9223372036854775806L;
                            break;
                        case "channels":
                            j = -9223372036854775805L;
                            break;
                        case "bots":
                            j = -9223372036854775804L;
                            break;
                        case "muted":
                            j = -9223372036854775803L;
                            break;
                        case "read":
                            j = -9223372036854775802L;
                            break;
                        case "archived":
                        default:
                            j = -9223372036854775801L;
                            break;
                    }
                } else if (object instanceof TLRPC$User) {
                    j = ((TLRPC$User) object).id;
                } else {
                    j = object instanceof TLRPC$Chat ? -((TLRPC$Chat) object).id : 0L;
                }
                if (j != 0) {
                    groupCreateUserCell.setChecked(this.selectedContacts.indexOfKey(j) >= 0, true);
                    groupCreateUserCell.setCheckBoxEnabled(true);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onDonePressed(boolean z) {
        ArrayList<Long> arrayList = new ArrayList<>();
        for (int i = 0; i < this.selectedContacts.size(); i++) {
            if (this.selectedContacts.keyAt(i) > -9223372036854775801L) {
                arrayList.add(Long.valueOf(this.selectedContacts.keyAt(i)));
            }
        }
        FilterUsersActivityDelegate filterUsersActivityDelegate = this.delegate;
        if (filterUsersActivityDelegate != null) {
            filterUsersActivityDelegate.didSelectChats(arrayList, this.filterFlags);
        }
        finishFragment();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeSearch() {
        this.searching = false;
        this.searchWas = false;
        this.adapter.setSearching(false);
        this.adapter.searchDialogs(null);
        this.listView.setFastScrollVisible(true);
        this.listView.setVerticalScrollBarEnabled(false);
        this.emptyView.setText(LocaleController.getString("NoContacts", R.string.NoContacts));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHint() {
        int i = this.type;
        if (i == 0) {
            int i2 = getUserConfig().isPremium() ? getMessagesController().dialogFiltersChatsLimitPremium : getMessagesController().dialogFiltersChatsLimitDefault;
            int i3 = this.selectedCount;
            if (i3 == 0) {
                this.actionBar.setSubtitle(LocaleController.formatString("MembersCountZero", R.string.MembersCountZero, LocaleController.formatPluralString("Chats", i2, new Object[0])));
                return;
            } else {
                this.actionBar.setSubtitle(String.format(LocaleController.getPluralString("MembersCountSelected", i3), Integer.valueOf(this.selectedCount), Integer.valueOf(i2)));
                return;
            }
        }
        if (i == 1) {
            this.actionBar.setTitle("");
            this.actionBar.setSubtitle("");
            if (this.selectedCount == 0) {
                this.animatedAvatarContainer.getTitle().setText(LocaleController.getString("SelectChats", R.string.SelectChats), true);
                if (this.ttlPeriod > 0) {
                    this.animatedAvatarContainer.getSubtitleTextView().setText(LocaleController.getString("SelectChatsForAutoDelete", R.string.SelectChatsForAutoDelete), true);
                    return;
                } else {
                    this.animatedAvatarContainer.getSubtitleTextView().setText(LocaleController.getString("SelectChatsForDisableAutoDelete", R.string.SelectChatsForDisableAutoDelete), true);
                    return;
                }
            }
            AnimatedTextView title = this.animatedAvatarContainer.getTitle();
            int i4 = this.selectedCount;
            title.setText(LocaleController.formatPluralString("Chats", i4, Integer.valueOf(i4)));
            if (this.ttlPeriod > 0) {
                this.animatedAvatarContainer.getSubtitleTextView().setText(LocaleController.getPluralString("SelectChatsForAutoDelete2", this.selectedCount));
            } else {
                this.animatedAvatarContainer.getSubtitleTextView().setText(LocaleController.getPluralString("SelectChatsForDisableAutoDelete2", this.selectedCount));
            }
        }
    }

    public void setDelegate(FilterUsersActivityDelegate filterUsersActivityDelegate) {
        this.delegate = filterUsersActivityDelegate;
    }

    public class GroupCreateAdapter extends RecyclerListView.FastScrollAdapter {
        private Context context;
        private SearchAdapterHelper searchAdapterHelper;
        private Runnable searchRunnable;
        private boolean searching;
        private final int usersStartRow;
        private ArrayList<Object> searchResult = new ArrayList<>();
        private ArrayList<CharSequence> searchResultNames = new ArrayList<>();
        private ArrayList<TLObject> contacts = new ArrayList<>();

        @Override // org.telegram.ui.Components.RecyclerListView.FastScrollAdapter
        public String getLetter(int i) {
            return null;
        }

        public GroupCreateAdapter(Context context) {
            int i;
            if (UsersSelectActivity.this.type != 0 || UsersSelectActivity.this.noChatTypes) {
                i = 0;
            } else {
                i = UsersSelectActivity.this.isInclude ? 7 : 5;
            }
            this.usersStartRow = i;
            this.context = context;
            ArrayList<TLRPC$Dialog> allDialogs = UsersSelectActivity.this.getMessagesController().getAllDialogs();
            int size = allDialogs.size();
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                TLRPC$Dialog tLRPC$Dialog = allDialogs.get(i2);
                if (!DialogObject.isEncryptedDialog(tLRPC$Dialog.id)) {
                    if (DialogObject.isUserDialog(tLRPC$Dialog.id)) {
                        TLRPC$User user = UsersSelectActivity.this.getMessagesController().getUser(Long.valueOf(tLRPC$Dialog.id));
                        if (user != null && (UsersSelectActivity.this.type != 1 || !UserObject.isUserSelf(user))) {
                            this.contacts.add(user);
                            if (UserObject.isUserSelf(user)) {
                                z = true;
                            }
                        }
                    } else {
                        TLRPC$Chat chat = UsersSelectActivity.this.getMessagesController().getChat(Long.valueOf(-tLRPC$Dialog.id));
                        if (chat != null) {
                            this.contacts.add(chat);
                        }
                    }
                }
            }
            if (!z && UsersSelectActivity.this.type != 1) {
                this.contacts.add(0, UsersSelectActivity.this.getMessagesController().getUser(Long.valueOf(UsersSelectActivity.this.getUserConfig().clientUserId)));
            }
            SearchAdapterHelper searchAdapterHelper = new SearchAdapterHelper(false);
            this.searchAdapterHelper = searchAdapterHelper;
            searchAdapterHelper.setAllowGlobalResults(false);
            this.searchAdapterHelper.setDelegate(new SearchAdapterHelper.SearchAdapterHelperDelegate() { // from class: org.telegram.ui.UsersSelectActivity$GroupCreateAdapter$$ExternalSyntheticLambda4
                @Override // org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate
                public /* synthetic */ boolean canApplySearchResults(int i3) {
                    return SearchAdapterHelper.SearchAdapterHelperDelegate.CC.$default$canApplySearchResults(this, i3);
                }

                @Override // org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate
                public /* synthetic */ LongSparseArray getExcludeCallParticipants() {
                    return SearchAdapterHelper.SearchAdapterHelperDelegate.CC.$default$getExcludeCallParticipants(this);
                }

                @Override // org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate
                public /* synthetic */ LongSparseArray getExcludeUsers() {
                    return SearchAdapterHelper.SearchAdapterHelperDelegate.CC.$default$getExcludeUsers(this);
                }

                @Override // org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate
                public final void onDataSetChanged(int i3) {
                    UsersSelectActivity.GroupCreateAdapter.this.lambda$new$0(i3);
                }

                @Override // org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate
                public /* synthetic */ void onSetHashtags(ArrayList arrayList, HashMap hashMap) {
                    SearchAdapterHelper.SearchAdapterHelperDelegate.CC.$default$onSetHashtags(this, arrayList, hashMap);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(int i) {
            if (this.searchRunnable == null && !this.searchAdapterHelper.isSearchInProgress()) {
                UsersSelectActivity.this.emptyView.showTextView();
            }
            notifyDataSetChanged();
        }

        public void setSearching(boolean z) {
            if (this.searching == z) {
                return;
            }
            this.searching = z;
            notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            if (!this.searching) {
                int i = 0;
                if (UsersSelectActivity.this.type == 0) {
                    UsersSelectActivity usersSelectActivity = UsersSelectActivity.this;
                    if (!usersSelectActivity.noChatTypes) {
                        i = usersSelectActivity.isInclude ? 7 : 5;
                    }
                }
                return i + this.contacts.size();
            }
            return this.searchResult.size() + this.searchAdapterHelper.getLocalServerSearch().size() + this.searchAdapterHelper.getGlobalSearch().size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View groupCreateUserCell;
            if (i == 1) {
                groupCreateUserCell = new GroupCreateUserCell(this.context, 1, 0, true);
            } else {
                groupCreateUserCell = new GraySectionCell(this.context);
            }
            return new RecyclerListView.Holder(groupCreateUserCell);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:35:0x01cb  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x01e5  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x02a6  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x02ba  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x02ca  */
        /* JADX WARN: Removed duplicated region for block: B:67:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:68:0x02bd  */
        /* JADX WARN: Removed duplicated region for block: B:69:0x02ac  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x0226  */
        /* JADX WARN: Removed duplicated region for block: B:81:0x01d1  */
        /* JADX WARN: Type inference failed for: r6v0 */
        /* JADX WARN: Type inference failed for: r6v1 */
        /* JADX WARN: Type inference failed for: r6v13 */
        /* JADX WARN: Type inference failed for: r6v14, types: [java.lang.StringBuilder] */
        /* JADX WARN: Type inference failed for: r6v15 */
        /* JADX WARN: Type inference failed for: r6v16 */
        /* JADX WARN: Type inference failed for: r6v17 */
        /* JADX WARN: Type inference failed for: r6v18 */
        /* JADX WARN: Type inference failed for: r6v19 */
        /* JADX WARN: Type inference failed for: r6v20 */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r18, int r19) {
            /*
                Method dump skipped, instructions count: 736
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.UsersSelectActivity.GroupCreateAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (!this.searching && UsersSelectActivity.this.type == 0) {
                UsersSelectActivity usersSelectActivity = UsersSelectActivity.this;
                if (usersSelectActivity.noChatTypes) {
                    if (i == 0) {
                        return 2;
                    }
                } else if (usersSelectActivity.isInclude) {
                    if (i == 0 || i == 6) {
                        return 2;
                    }
                } else if (i == 0 || i == 4) {
                    return 2;
                }
            }
            return 1;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.FastScrollAdapter
        public void getPositionForScrollProgress(RecyclerListView recyclerListView, float f, int[] iArr) {
            iArr[0] = (int) (getItemCount() * f);
            iArr[1] = 0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
            View view = viewHolder.itemView;
            if (view instanceof GroupCreateUserCell) {
                ((GroupCreateUserCell) view).recycle();
            }
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 1;
        }

        public void searchDialogs(final String str) {
            if (this.searchRunnable != null) {
                Utilities.searchQueue.cancelRunnable(this.searchRunnable);
                this.searchRunnable = null;
            }
            final boolean z = true;
            if (str == null) {
                this.searchResult.clear();
                this.searchResultNames.clear();
                this.searchAdapterHelper.mergeResults(null);
                this.searchAdapterHelper.queryServerSearch(null, true, true, false, false, false, 0L, false, 0, 0);
                notifyDataSetChanged();
                return;
            }
            DispatchQueue dispatchQueue = Utilities.searchQueue;
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.UsersSelectActivity$GroupCreateAdapter$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    UsersSelectActivity.GroupCreateAdapter.this.lambda$searchDialogs$3(str, z);
                }
            };
            this.searchRunnable = runnable;
            dispatchQueue.postRunnable(runnable, 300L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$searchDialogs$3(final String str, final boolean z) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.UsersSelectActivity$GroupCreateAdapter$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    UsersSelectActivity.GroupCreateAdapter.this.lambda$searchDialogs$2(str, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$searchDialogs$2(final String str, boolean z) {
            this.searchAdapterHelper.queryServerSearch(str, true, z, true, UsersSelectActivity.this.type != 1, false, 0L, false, 0, 0);
            DispatchQueue dispatchQueue = Utilities.searchQueue;
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.UsersSelectActivity$GroupCreateAdapter$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UsersSelectActivity.GroupCreateAdapter.this.lambda$searchDialogs$1(str);
                }
            };
            this.searchRunnable = runnable;
            dispatchQueue.postRunnable(runnable);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$searchDialogs$1(String str) {
            String str2;
            int i;
            String str3;
            String lowerCase = str.trim().toLowerCase();
            if (lowerCase.length() == 0) {
                updateSearchResults(new ArrayList<>(), new ArrayList<>());
                return;
            }
            String translitString = LocaleController.getInstance().getTranslitString(lowerCase);
            String str4 = null;
            if (lowerCase.equals(translitString) || translitString.length() == 0) {
                translitString = null;
            }
            char c = 0;
            char c2 = 1;
            int i2 = (translitString != null ? 1 : 0) + 1;
            String[] strArr = new String[i2];
            strArr[0] = lowerCase;
            if (translitString != null) {
                strArr[1] = translitString;
            }
            ArrayList<Object> arrayList = new ArrayList<>();
            ArrayList<CharSequence> arrayList2 = new ArrayList<>();
            int i3 = 0;
            while (i3 < this.contacts.size()) {
                TLObject tLObject = this.contacts.get(i3);
                String[] strArr2 = new String[3];
                boolean z = tLObject instanceof TLRPC$User;
                if (z) {
                    TLRPC$User tLRPC$User = (TLRPC$User) tLObject;
                    strArr2[c] = ContactsController.formatName(tLRPC$User.first_name, tLRPC$User.last_name).toLowerCase();
                    str2 = UserObject.getPublicUsername(tLRPC$User);
                    if (UserObject.isReplyUser(tLRPC$User)) {
                        strArr2[2] = LocaleController.getString("RepliesTitle", R.string.RepliesTitle).toLowerCase();
                    } else if (tLRPC$User.self) {
                        strArr2[2] = LocaleController.getString("SavedMessages", R.string.SavedMessages).toLowerCase();
                    }
                } else {
                    TLRPC$Chat tLRPC$Chat = (TLRPC$Chat) tLObject;
                    strArr2[c] = tLRPC$Chat.title.toLowerCase();
                    str2 = tLRPC$Chat.username;
                }
                strArr2[c2] = LocaleController.getInstance().getTranslitString(strArr2[c]);
                if (strArr2[c].equals(strArr2[c2])) {
                    strArr2[c2] = str4;
                }
                int i4 = 0;
                char c3 = 0;
                while (true) {
                    if (i4 >= i2) {
                        i = i2;
                        str3 = str4;
                        break;
                    }
                    String str5 = strArr[i4];
                    int i5 = 0;
                    while (i5 < 3) {
                        String str6 = strArr2[i5];
                        if (str6 != null) {
                            if (str6.startsWith(str5)) {
                                i = i2;
                            } else {
                                StringBuilder sb = new StringBuilder();
                                i = i2;
                                sb.append(" ");
                                sb.append(str5);
                                if (str6.contains(sb.toString())) {
                                }
                            }
                            c3 = 1;
                            break;
                        }
                        i = i2;
                        i5++;
                        i2 = i;
                    }
                    i = i2;
                    if (c3 == 0 && str2 != null && str2.toLowerCase().startsWith(str5)) {
                        c3 = 2;
                    }
                    if (c3 != 0) {
                        if (c3 == 1) {
                            if (z) {
                                TLRPC$User tLRPC$User2 = (TLRPC$User) tLObject;
                                arrayList2.add(AndroidUtilities.generateSearchName(tLRPC$User2.first_name, tLRPC$User2.last_name, str5));
                            } else {
                                arrayList2.add(AndroidUtilities.generateSearchName(((TLRPC$Chat) tLObject).title, null, str5));
                            }
                            str3 = null;
                        } else {
                            str3 = null;
                            arrayList2.add(AndroidUtilities.generateSearchName("@" + str2, null, "@" + str5));
                        }
                        arrayList.add(tLObject);
                    } else {
                        i4++;
                        str4 = null;
                        i2 = i;
                    }
                }
                i3++;
                str4 = str3;
                i2 = i;
                c = 0;
                c2 = 1;
            }
            updateSearchResults(arrayList, arrayList2);
        }

        private void updateSearchResults(final ArrayList<Object> arrayList, final ArrayList<CharSequence> arrayList2) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.UsersSelectActivity$GroupCreateAdapter$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    UsersSelectActivity.GroupCreateAdapter.this.lambda$updateSearchResults$4(arrayList, arrayList2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateSearchResults$4(ArrayList arrayList, ArrayList arrayList2) {
            if (this.searching) {
                this.searchRunnable = null;
                this.searchResult = arrayList;
                this.searchResultNames = arrayList2;
                this.searchAdapterHelper.mergeResults(arrayList);
                if (this.searching && !this.searchAdapterHelper.isSearchInProgress()) {
                    UsersSelectActivity.this.emptyView.showTextView();
                }
                notifyDataSetChanged();
            }
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        ThemeDescription.ThemeDescriptionDelegate themeDescriptionDelegate = new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.UsersSelectActivity$$ExternalSyntheticLambda2
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                UsersSelectActivity.this.lambda$getThemeDescriptions$3();
            }

            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public /* synthetic */ void onAnimationProgress(float f) {
                ThemeDescription.ThemeDescriptionDelegate.CC.$default$onAnimationProgress(this, f);
            }
        };
        View view = this.fragmentView;
        int i = ThemeDescription.FLAG_BACKGROUND;
        int i2 = Theme.key_windowBackgroundWhite;
        arrayList.add(new ThemeDescription(view, i, null, null, null, null, i2));
        ActionBar actionBar = this.actionBar;
        int i3 = ThemeDescription.FLAG_BACKGROUND;
        int i4 = Theme.key_actionBarDefault;
        arrayList.add(new ThemeDescription(actionBar, i3, null, null, null, null, i4));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, i4));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));
        arrayList.add(new ThemeDescription(this.scrollView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollActive));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollInactive));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollText));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));
        arrayList.add(new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder));
        arrayList.add(new ThemeDescription(this.emptyView, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_groupcreate_hintText));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_CURSORCOLOR, null, null, null, null, Theme.key_groupcreate_cursor));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{GraySectionCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_graySectionText));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{GraySectionCell.class}, null, null, null, Theme.key_graySection));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{GroupCreateUserCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_groupcreate_sectionText));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{GroupCreateUserCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_checkbox));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{GroupCreateUserCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_checkboxDisabled));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{GroupCreateUserCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_checkboxCheck));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{GroupCreateUserCell.class}, new String[]{"statusTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlueText));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{GroupCreateUserCell.class}, new String[]{"statusTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{GroupCreateUserCell.class}, null, Theme.avatarDrawables, null, Theme.key_avatar_text));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundRed));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundOrange));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundViolet));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundGreen));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundCyan));
        int i5 = Theme.key_avatar_backgroundBlue;
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, i5));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundPink));
        arrayList.add(new ThemeDescription(this.spansContainer, 0, new Class[]{GroupCreateSpan.class}, null, null, null, Theme.key_groupcreate_spanBackground));
        arrayList.add(new ThemeDescription(this.spansContainer, 0, new Class[]{GroupCreateSpan.class}, null, null, null, Theme.key_groupcreate_spanText));
        arrayList.add(new ThemeDescription(this.spansContainer, 0, new Class[]{GroupCreateSpan.class}, null, null, null, Theme.key_groupcreate_spanDelete));
        arrayList.add(new ThemeDescription(this.spansContainer, 0, new Class[]{GroupCreateSpan.class}, null, null, null, i5));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getThemeDescriptions$3() {
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

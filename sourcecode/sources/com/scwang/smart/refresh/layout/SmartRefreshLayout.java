package com.scwang.smart.refresh.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import com.scwang.smart.refresh.layout.api.RefreshComponent;
import com.scwang.smart.refresh.layout.api.RefreshContent;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.DimensionStatus;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.kernel.R$id;
import com.scwang.smart.refresh.layout.kernel.R$string;
import com.scwang.smart.refresh.layout.kernel.R$styleable;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshInitializer;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnMultiListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.listener.ScrollBoundaryDecider;
import com.scwang.smart.refresh.layout.util.SmartUtil;
import com.scwang.smart.refresh.layout.wrapper.RefreshContentWrapper;
import com.youth.banner.config.BannerConfig;

@SuppressLint({"RestrictedApi"})
/* loaded from: classes.dex */
public class SmartRefreshLayout extends ViewGroup implements RefreshLayout, NestedScrollingParent {
    protected static ViewGroup.MarginLayoutParams sDefaultMarginLP = new ViewGroup.MarginLayoutParams(-1, -1);
    protected static DefaultRefreshFooterCreator sFooterCreator;
    protected static DefaultRefreshHeaderCreator sHeaderCreator;
    protected static DefaultRefreshInitializer sRefreshInitializer;
    protected Runnable animationRunnable;
    protected int mCurrentVelocity;
    protected boolean mDisableContentWhenLoading;
    protected boolean mDisableContentWhenRefresh;
    protected char mDragDirection;
    protected float mDragRate;
    protected boolean mEnableAutoLoadMore;
    protected boolean mEnableClipFooterWhenFixedBehind;
    protected boolean mEnableClipHeaderWhenFixedBehind;
    protected boolean mEnableDisallowIntercept;
    protected boolean mEnableFooterFollowWhenNoMoreData;
    protected boolean mEnableFooterTranslationContent;
    protected boolean mEnableHeaderTranslationContent;
    protected boolean mEnableLoadMore;
    protected boolean mEnableLoadMoreWhenContentNotFull;
    protected boolean mEnableNestedScrolling;
    protected boolean mEnableOverScrollBounce;
    protected boolean mEnableOverScrollDrag;
    protected boolean mEnablePreviewInEditMode;
    protected boolean mEnablePureScrollMode;
    protected boolean mEnableRefresh;
    protected boolean mEnableScrollContentWhenLoaded;
    protected boolean mEnableScrollContentWhenRefreshed;
    protected MotionEvent mFalsifyEvent;
    protected int mFixedFooterViewId;
    protected int mFixedHeaderViewId;
    protected int mFloorDuration;
    protected int mFooterBackgroundColor;
    protected int mFooterHeight;
    protected DimensionStatus mFooterHeightStatus;
    protected int mFooterInsetStart;
    protected boolean mFooterLocked;
    protected float mFooterMaxDragRate;
    protected boolean mFooterNeedTouchEventWhenLoading;
    protected boolean mFooterNoMoreData;
    protected boolean mFooterNoMoreDataEffective;
    protected int mFooterTranslationViewId;
    protected float mFooterTriggerRate;
    protected Handler mHandler;
    protected int mHeaderBackgroundColor;
    protected int mHeaderHeight;
    protected DimensionStatus mHeaderHeightStatus;
    protected int mHeaderInsetStart;
    protected float mHeaderMaxDragRate;
    protected boolean mHeaderNeedTouchEventWhenRefreshing;
    protected int mHeaderTranslationViewId;
    protected float mHeaderTriggerRate;
    protected boolean mIsBeingDragged;
    protected RefreshKernel mKernel;
    protected long mLastOpenTime;
    protected int mLastSpinner;
    protected float mLastTouchX;
    protected float mLastTouchY;
    protected OnLoadMoreListener mLoadMoreListener;
    protected boolean mManualFooterTranslationContent;
    protected boolean mManualHeaderTranslationContent;
    protected boolean mManualLoadMore;
    protected int mMaximumVelocity;
    protected int mMinimumVelocity;
    protected NestedScrollingChildHelper mNestedChild;
    protected boolean mNestedInProgress;
    protected NestedScrollingParentHelper mNestedParent;
    protected OnMultiListener mOnMultiListener;
    protected Paint mPaint;
    protected int[] mParentOffsetInWindow;
    protected int[] mPrimaryColors;
    protected int mReboundDuration;
    protected Interpolator mReboundInterpolator;
    protected RefreshContent mRefreshContent;
    protected RefreshComponent mRefreshFooter;
    protected RefreshComponent mRefreshHeader;
    protected OnRefreshListener mRefreshListener;
    protected int mScreenHeightPixels;
    protected ScrollBoundaryDecider mScrollBoundaryDecider;
    protected Scroller mScroller;
    protected int mSpinner;
    protected RefreshState mState;
    protected boolean mSuperDispatchTouchEvent;
    protected int mTotalUnconsumed;
    protected int mTouchSlop;
    protected int mTouchSpinner;
    protected float mTouchX;
    protected float mTouchY;
    protected float mTwoLevelBottomPullUpToCloseRate;
    protected VelocityTracker mVelocityTracker;
    protected boolean mVerticalPermit;
    protected RefreshState mViceState;
    protected ValueAnimator reboundAnimator;

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public ViewGroup getLayout() {
        return this;
    }

    public SmartRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFloorDuration = 300;
        this.mReboundDuration = 300;
        this.mDragRate = 0.5f;
        this.mDragDirection = 'n';
        this.mFixedHeaderViewId = -1;
        this.mFixedFooterViewId = -1;
        this.mHeaderTranslationViewId = -1;
        this.mFooterTranslationViewId = -1;
        this.mEnableRefresh = true;
        this.mEnableLoadMore = false;
        this.mEnableClipHeaderWhenFixedBehind = true;
        this.mEnableClipFooterWhenFixedBehind = true;
        this.mEnableHeaderTranslationContent = true;
        this.mEnableFooterTranslationContent = true;
        this.mEnableFooterFollowWhenNoMoreData = false;
        this.mEnablePreviewInEditMode = true;
        this.mEnableOverScrollBounce = true;
        this.mEnableOverScrollDrag = false;
        this.mEnableAutoLoadMore = true;
        this.mEnablePureScrollMode = false;
        this.mEnableScrollContentWhenLoaded = true;
        this.mEnableScrollContentWhenRefreshed = true;
        this.mEnableLoadMoreWhenContentNotFull = true;
        this.mEnableNestedScrolling = true;
        this.mDisableContentWhenRefresh = false;
        this.mDisableContentWhenLoading = false;
        this.mFooterNoMoreData = false;
        this.mFooterNoMoreDataEffective = false;
        this.mManualLoadMore = false;
        this.mManualHeaderTranslationContent = false;
        this.mManualFooterTranslationContent = false;
        this.mParentOffsetInWindow = new int[2];
        this.mNestedChild = new NestedScrollingChildHelper(this);
        this.mNestedParent = new NestedScrollingParentHelper(this);
        DimensionStatus dimensionStatus = DimensionStatus.DefaultUnNotify;
        this.mHeaderHeightStatus = dimensionStatus;
        this.mFooterHeightStatus = dimensionStatus;
        this.mHeaderMaxDragRate = 2.5f;
        this.mFooterMaxDragRate = 2.5f;
        this.mHeaderTriggerRate = 1.0f;
        this.mFooterTriggerRate = 1.0f;
        this.mTwoLevelBottomPullUpToCloseRate = 0.16666667f;
        this.mKernel = new RefreshKernelImpl();
        RefreshState refreshState = RefreshState.None;
        this.mState = refreshState;
        this.mViceState = refreshState;
        this.mLastOpenTime = 0L;
        this.mHeaderBackgroundColor = 0;
        this.mFooterBackgroundColor = 0;
        this.mFooterLocked = false;
        this.mVerticalPermit = false;
        this.mFalsifyEvent = null;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mScroller = new Scroller(context);
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mScreenHeightPixels = context.getResources().getDisplayMetrics().heightPixels;
        this.mReboundInterpolator = new SmartUtil(SmartUtil.INTERPOLATOR_VISCOUS_FLUID);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mFooterHeight = SmartUtil.dp2px(60.0f);
        this.mHeaderHeight = SmartUtil.dp2px(100.0f);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SmartRefreshLayout);
        if (!obtainStyledAttributes.hasValue(R$styleable.SmartRefreshLayout_android_clipToPadding)) {
            super.setClipToPadding(false);
        }
        if (!obtainStyledAttributes.hasValue(R$styleable.SmartRefreshLayout_android_clipChildren)) {
            super.setClipChildren(false);
        }
        DefaultRefreshInitializer defaultRefreshInitializer = sRefreshInitializer;
        if (defaultRefreshInitializer != null) {
            defaultRefreshInitializer.initialize(context, this);
        }
        this.mDragRate = obtainStyledAttributes.getFloat(R$styleable.SmartRefreshLayout_srlDragRate, this.mDragRate);
        this.mHeaderMaxDragRate = obtainStyledAttributes.getFloat(R$styleable.SmartRefreshLayout_srlHeaderMaxDragRate, this.mHeaderMaxDragRate);
        this.mFooterMaxDragRate = obtainStyledAttributes.getFloat(R$styleable.SmartRefreshLayout_srlFooterMaxDragRate, this.mFooterMaxDragRate);
        this.mHeaderTriggerRate = obtainStyledAttributes.getFloat(R$styleable.SmartRefreshLayout_srlHeaderTriggerRate, this.mHeaderTriggerRate);
        this.mFooterTriggerRate = obtainStyledAttributes.getFloat(R$styleable.SmartRefreshLayout_srlFooterTriggerRate, this.mFooterTriggerRate);
        this.mEnableRefresh = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableRefresh, this.mEnableRefresh);
        this.mReboundDuration = obtainStyledAttributes.getInt(R$styleable.SmartRefreshLayout_srlReboundDuration, this.mReboundDuration);
        int i = R$styleable.SmartRefreshLayout_srlEnableLoadMore;
        this.mEnableLoadMore = obtainStyledAttributes.getBoolean(i, this.mEnableLoadMore);
        int i2 = R$styleable.SmartRefreshLayout_srlHeaderHeight;
        this.mHeaderHeight = obtainStyledAttributes.getDimensionPixelOffset(i2, this.mHeaderHeight);
        int i3 = R$styleable.SmartRefreshLayout_srlFooterHeight;
        this.mFooterHeight = obtainStyledAttributes.getDimensionPixelOffset(i3, this.mFooterHeight);
        this.mHeaderInsetStart = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.SmartRefreshLayout_srlHeaderInsetStart, this.mHeaderInsetStart);
        this.mFooterInsetStart = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.SmartRefreshLayout_srlFooterInsetStart, this.mFooterInsetStart);
        this.mDisableContentWhenRefresh = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlDisableContentWhenRefresh, this.mDisableContentWhenRefresh);
        this.mDisableContentWhenLoading = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlDisableContentWhenLoading, this.mDisableContentWhenLoading);
        int i4 = R$styleable.SmartRefreshLayout_srlEnableHeaderTranslationContent;
        this.mEnableHeaderTranslationContent = obtainStyledAttributes.getBoolean(i4, this.mEnableHeaderTranslationContent);
        int i5 = R$styleable.SmartRefreshLayout_srlEnableFooterTranslationContent;
        this.mEnableFooterTranslationContent = obtainStyledAttributes.getBoolean(i5, this.mEnableFooterTranslationContent);
        this.mEnablePreviewInEditMode = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnablePreviewInEditMode, this.mEnablePreviewInEditMode);
        this.mEnableAutoLoadMore = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableAutoLoadMore, this.mEnableAutoLoadMore);
        this.mEnableOverScrollBounce = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableOverScrollBounce, this.mEnableOverScrollBounce);
        this.mEnablePureScrollMode = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnablePureScrollMode, this.mEnablePureScrollMode);
        this.mEnableScrollContentWhenLoaded = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableScrollContentWhenLoaded, this.mEnableScrollContentWhenLoaded);
        this.mEnableScrollContentWhenRefreshed = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableScrollContentWhenRefreshed, this.mEnableScrollContentWhenRefreshed);
        this.mEnableLoadMoreWhenContentNotFull = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableLoadMoreWhenContentNotFull, this.mEnableLoadMoreWhenContentNotFull);
        boolean z = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableFooterFollowWhenLoadFinished, this.mEnableFooterFollowWhenNoMoreData);
        this.mEnableFooterFollowWhenNoMoreData = z;
        this.mEnableFooterFollowWhenNoMoreData = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableFooterFollowWhenNoMoreData, z);
        this.mEnableClipHeaderWhenFixedBehind = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableClipHeaderWhenFixedBehind, this.mEnableClipHeaderWhenFixedBehind);
        this.mEnableClipFooterWhenFixedBehind = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableClipFooterWhenFixedBehind, this.mEnableClipFooterWhenFixedBehind);
        this.mEnableOverScrollDrag = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableOverScrollDrag, this.mEnableOverScrollDrag);
        this.mFixedHeaderViewId = obtainStyledAttributes.getResourceId(R$styleable.SmartRefreshLayout_srlFixedHeaderViewId, this.mFixedHeaderViewId);
        this.mFixedFooterViewId = obtainStyledAttributes.getResourceId(R$styleable.SmartRefreshLayout_srlFixedFooterViewId, this.mFixedFooterViewId);
        this.mHeaderTranslationViewId = obtainStyledAttributes.getResourceId(R$styleable.SmartRefreshLayout_srlHeaderTranslationViewId, this.mHeaderTranslationViewId);
        this.mFooterTranslationViewId = obtainStyledAttributes.getResourceId(R$styleable.SmartRefreshLayout_srlFooterTranslationViewId, this.mFooterTranslationViewId);
        boolean z2 = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableNestedScrolling, this.mEnableNestedScrolling);
        this.mEnableNestedScrolling = z2;
        this.mNestedChild.setNestedScrollingEnabled(z2);
        this.mManualLoadMore = this.mManualLoadMore || obtainStyledAttributes.hasValue(i);
        this.mManualHeaderTranslationContent = this.mManualHeaderTranslationContent || obtainStyledAttributes.hasValue(i4);
        this.mManualFooterTranslationContent = this.mManualFooterTranslationContent || obtainStyledAttributes.hasValue(i5);
        this.mHeaderHeightStatus = obtainStyledAttributes.hasValue(i2) ? DimensionStatus.XmlLayoutUnNotify : this.mHeaderHeightStatus;
        this.mFooterHeightStatus = obtainStyledAttributes.hasValue(i3) ? DimensionStatus.XmlLayoutUnNotify : this.mFooterHeightStatus;
        int color = obtainStyledAttributes.getColor(R$styleable.SmartRefreshLayout_srlAccentColor, 0);
        int color2 = obtainStyledAttributes.getColor(R$styleable.SmartRefreshLayout_srlPrimaryColor, 0);
        if (color2 != 0) {
            if (color != 0) {
                this.mPrimaryColors = new int[]{color2, color};
            } else {
                this.mPrimaryColors = new int[]{color2};
            }
        } else if (color != 0) {
            this.mPrimaryColors = new int[]{0, color};
        }
        if (this.mEnablePureScrollMode && !this.mManualLoadMore && !this.mEnableLoadMore) {
            this.mEnableLoadMore = true;
        }
        obtainStyledAttributes.recycle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0052  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onFinishInflate() {
        /*
            r11 = this;
            super.onFinishInflate()
            int r0 = super.getChildCount()
            r1 = 3
            if (r0 > r1) goto L9e
            r2 = -1
            r3 = 0
            r4 = 0
            r5 = -1
            r6 = 0
        Lf:
            r7 = 2
            r8 = 1
            if (r4 >= r0) goto L33
            android.view.View r9 = super.getChildAt(r4)
            boolean r10 = com.scwang.smart.refresh.layout.util.SmartUtil.isContentView(r9)
            if (r10 == 0) goto L24
            if (r6 < r7) goto L21
            if (r4 != r8) goto L24
        L21:
            r5 = r4
            r6 = 2
            goto L30
        L24:
            boolean r7 = r9 instanceof com.scwang.smart.refresh.layout.api.RefreshComponent
            if (r7 != 0) goto L30
            if (r6 >= r8) goto L30
            if (r4 <= 0) goto L2e
            r6 = 1
            goto L2f
        L2e:
            r6 = 0
        L2f:
            r5 = r4
        L30:
            int r4 = r4 + 1
            goto Lf
        L33:
            if (r5 < 0) goto L4d
            com.scwang.smart.refresh.layout.wrapper.RefreshContentWrapper r4 = new com.scwang.smart.refresh.layout.wrapper.RefreshContentWrapper
            android.view.View r6 = super.getChildAt(r5)
            r4.<init>(r6)
            r11.mRefreshContent = r4
            if (r5 != r8) goto L48
            if (r0 != r1) goto L46
            r1 = 0
            goto L4f
        L46:
            r1 = 0
            goto L4e
        L48:
            if (r0 != r7) goto L4d
            r1 = -1
            r7 = 1
            goto L4f
        L4d:
            r1 = -1
        L4e:
            r7 = -1
        L4f:
            r4 = 0
        L50:
            if (r4 >= r0) goto L9d
            android.view.View r5 = super.getChildAt(r4)
            if (r4 == r1) goto L8b
            if (r4 == r7) goto L65
            if (r1 != r2) goto L65
            com.scwang.smart.refresh.layout.api.RefreshComponent r6 = r11.mRefreshHeader
            if (r6 != 0) goto L65
            boolean r6 = r5 instanceof com.scwang.smart.refresh.layout.api.RefreshHeader
            if (r6 == 0) goto L65
            goto L8b
        L65:
            if (r4 == r7) goto L6d
            if (r7 != r2) goto L9a
            boolean r6 = r5 instanceof com.scwang.smart.refresh.layout.api.RefreshFooter
            if (r6 == 0) goto L9a
        L6d:
            boolean r6 = r11.mEnableLoadMore
            if (r6 != 0) goto L78
            boolean r6 = r11.mManualLoadMore
            if (r6 != 0) goto L76
            goto L78
        L76:
            r6 = 0
            goto L79
        L78:
            r6 = 1
        L79:
            r11.mEnableLoadMore = r6
            boolean r6 = r5 instanceof com.scwang.smart.refresh.layout.api.RefreshFooter
            if (r6 == 0) goto L82
            com.scwang.smart.refresh.layout.api.RefreshFooter r5 = (com.scwang.smart.refresh.layout.api.RefreshFooter) r5
            goto L88
        L82:
            com.scwang.smart.refresh.layout.wrapper.RefreshFooterWrapper r6 = new com.scwang.smart.refresh.layout.wrapper.RefreshFooterWrapper
            r6.<init>(r5)
            r5 = r6
        L88:
            r11.mRefreshFooter = r5
            goto L9a
        L8b:
            boolean r6 = r5 instanceof com.scwang.smart.refresh.layout.api.RefreshHeader
            if (r6 == 0) goto L92
            com.scwang.smart.refresh.layout.api.RefreshHeader r5 = (com.scwang.smart.refresh.layout.api.RefreshHeader) r5
            goto L98
        L92:
            com.scwang.smart.refresh.layout.wrapper.RefreshHeaderWrapper r6 = new com.scwang.smart.refresh.layout.wrapper.RefreshHeaderWrapper
            r6.<init>(r5)
            r5 = r6
        L98:
            r11.mRefreshHeader = r5
        L9a:
            int r4 = r4 + 1
            goto L50
        L9d:
            return
        L9e:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "最多只支持3个子View，Most only support three sub view"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.onFinishInflate():void");
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        RefreshComponent refreshComponent;
        DefaultRefreshHeaderCreator defaultRefreshHeaderCreator;
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            if (this.mRefreshHeader == null && (defaultRefreshHeaderCreator = sHeaderCreator) != null) {
                RefreshHeader createRefreshHeader = defaultRefreshHeaderCreator.createRefreshHeader(getContext(), this);
                if (createRefreshHeader == null) {
                    throw new RuntimeException("DefaultRefreshHeaderCreator can not return null");
                }
                setRefreshHeader(createRefreshHeader);
            }
            if (this.mRefreshFooter == null) {
                DefaultRefreshFooterCreator defaultRefreshFooterCreator = sFooterCreator;
                if (defaultRefreshFooterCreator != null) {
                    RefreshFooter createRefreshFooter = defaultRefreshFooterCreator.createRefreshFooter(getContext(), this);
                    if (createRefreshFooter == null) {
                        throw new RuntimeException("DefaultRefreshFooterCreator can not return null");
                    }
                    setRefreshFooter(createRefreshFooter);
                }
            } else {
                this.mEnableLoadMore = this.mEnableLoadMore || !this.mManualLoadMore;
            }
            if (this.mRefreshContent == null) {
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = getChildAt(i);
                    RefreshComponent refreshComponent2 = this.mRefreshHeader;
                    if ((refreshComponent2 == null || childAt != refreshComponent2.getView()) && ((refreshComponent = this.mRefreshFooter) == null || childAt != refreshComponent.getView())) {
                        this.mRefreshContent = new RefreshContentWrapper(childAt);
                    }
                }
            }
            if (this.mRefreshContent == null) {
                int dp2px = SmartUtil.dp2px(20.0f);
                TextView textView = new TextView(getContext());
                textView.setTextColor(-39424);
                textView.setGravity(17);
                textView.setTextSize(20.0f);
                textView.setText(R$string.srl_content_empty);
                super.addView(textView, 0, new LayoutParams(-1, -1));
                RefreshContentWrapper refreshContentWrapper = new RefreshContentWrapper(textView);
                this.mRefreshContent = refreshContentWrapper;
                refreshContentWrapper.getView().setPadding(dp2px, dp2px, dp2px, dp2px);
            }
            View findViewById = findViewById(this.mFixedHeaderViewId);
            View findViewById2 = findViewById(this.mFixedFooterViewId);
            this.mRefreshContent.setScrollBoundaryDecider(this.mScrollBoundaryDecider);
            this.mRefreshContent.setEnableLoadMoreWhenContentNotFull(this.mEnableLoadMoreWhenContentNotFull);
            this.mRefreshContent.setUpComponent(this.mKernel, findViewById, findViewById2);
            if (this.mSpinner != 0) {
                notifyStateChanged(RefreshState.None);
                RefreshContent refreshContent = this.mRefreshContent;
                this.mSpinner = 0;
                refreshContent.moveSpinner(0, this.mHeaderTranslationViewId, this.mFooterTranslationViewId);
            }
        }
        int[] iArr = this.mPrimaryColors;
        if (iArr != null) {
            RefreshComponent refreshComponent3 = this.mRefreshHeader;
            if (refreshComponent3 != null) {
                refreshComponent3.setPrimaryColors(iArr);
            }
            RefreshComponent refreshComponent4 = this.mRefreshFooter;
            if (refreshComponent4 != null) {
                refreshComponent4.setPrimaryColors(this.mPrimaryColors);
            }
        }
        RefreshContent refreshContent2 = this.mRefreshContent;
        if (refreshContent2 != null) {
            super.bringChildToFront(refreshContent2.getView());
        }
        RefreshComponent refreshComponent5 = this.mRefreshHeader;
        if (refreshComponent5 != null && refreshComponent5.getSpinnerStyle().front) {
            super.bringChildToFront(this.mRefreshHeader.getView());
        }
        RefreshComponent refreshComponent6 = this.mRefreshFooter;
        if (refreshComponent6 == null || !refreshComponent6.getSpinnerStyle().front) {
            return;
        }
        super.bringChildToFront(this.mRefreshFooter.getView());
    }

    /* JADX WARN: Removed duplicated region for block: B:138:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x025e  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onMeasure(int r18, int r19) {
        /*
            Method dump skipped, instructions count: 859
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.onMeasure(int, int):void");
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        getPaddingBottom();
        int childCount = super.getChildCount();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = super.getChildAt(i6);
            if (childAt.getVisibility() != 8 && !"GONE".equals(childAt.getTag(R$id.srl_tag))) {
                RefreshContent refreshContent = this.mRefreshContent;
                if (refreshContent != null && refreshContent.getView() == childAt) {
                    boolean z2 = isInEditMode() && this.mEnablePreviewInEditMode && isEnableRefreshOrLoadMore(this.mEnableRefresh) && this.mRefreshHeader != null;
                    View view = this.mRefreshContent.getView();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : sDefaultMarginLP;
                    int i7 = marginLayoutParams.leftMargin + paddingLeft;
                    int i8 = marginLayoutParams.topMargin + paddingTop;
                    int measuredWidth = view.getMeasuredWidth() + i7;
                    int measuredHeight = view.getMeasuredHeight() + i8;
                    if (z2 && isEnableTranslationContent(this.mEnableHeaderTranslationContent, this.mRefreshHeader)) {
                        int i9 = this.mHeaderHeight;
                        i8 += i9;
                        measuredHeight += i9;
                    }
                    view.layout(i7, i8, measuredWidth, measuredHeight);
                }
                RefreshComponent refreshComponent = this.mRefreshHeader;
                if (refreshComponent != null && refreshComponent.getView() == childAt) {
                    boolean z3 = isInEditMode() && this.mEnablePreviewInEditMode && isEnableRefreshOrLoadMore(this.mEnableRefresh);
                    View view2 = this.mRefreshHeader.getView();
                    ViewGroup.LayoutParams layoutParams2 = view2.getLayoutParams();
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : sDefaultMarginLP;
                    int i10 = marginLayoutParams2.leftMargin;
                    int i11 = marginLayoutParams2.topMargin + this.mHeaderInsetStart;
                    int measuredWidth2 = view2.getMeasuredWidth() + i10;
                    int measuredHeight2 = view2.getMeasuredHeight() + i11;
                    if (!z3 && this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Translate) {
                        int i12 = this.mHeaderHeight;
                        i11 -= i12;
                        measuredHeight2 -= i12;
                    }
                    view2.layout(i10, i11, measuredWidth2, measuredHeight2);
                }
                RefreshComponent refreshComponent2 = this.mRefreshFooter;
                if (refreshComponent2 != null && refreshComponent2.getView() == childAt) {
                    boolean z4 = isInEditMode() && this.mEnablePreviewInEditMode && isEnableRefreshOrLoadMore(this.mEnableLoadMore);
                    View view3 = this.mRefreshFooter.getView();
                    ViewGroup.LayoutParams layoutParams3 = view3.getLayoutParams();
                    ViewGroup.MarginLayoutParams marginLayoutParams3 = layoutParams3 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams3 : sDefaultMarginLP;
                    SpinnerStyle spinnerStyle = this.mRefreshFooter.getSpinnerStyle();
                    int i13 = marginLayoutParams3.leftMargin;
                    int measuredHeight3 = (marginLayoutParams3.topMargin + getMeasuredHeight()) - this.mFooterInsetStart;
                    if (this.mFooterNoMoreData && this.mFooterNoMoreDataEffective && this.mEnableFooterFollowWhenNoMoreData && this.mRefreshContent != null && this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Translate && isEnableRefreshOrLoadMore(this.mEnableLoadMore)) {
                        View view4 = this.mRefreshContent.getView();
                        ViewGroup.LayoutParams layoutParams4 = view4.getLayoutParams();
                        measuredHeight3 = view4.getMeasuredHeight() + paddingTop + paddingTop + (layoutParams4 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams4).topMargin : 0);
                    }
                    if (spinnerStyle == SpinnerStyle.MatchLayout) {
                        measuredHeight3 = marginLayoutParams3.topMargin - this.mFooterInsetStart;
                    } else {
                        if (z4 || spinnerStyle == SpinnerStyle.FixedFront || spinnerStyle == SpinnerStyle.FixedBehind) {
                            i5 = this.mFooterHeight;
                        } else if (spinnerStyle.scale && this.mSpinner < 0) {
                            i5 = Math.max(isEnableRefreshOrLoadMore(this.mEnableLoadMore) ? -this.mSpinner : 0, 0);
                        }
                        measuredHeight3 -= i5;
                    }
                    view3.layout(i13, measuredHeight3, view3.getMeasuredWidth() + i13, view3.getMeasuredHeight() + measuredHeight3);
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mManualLoadMore = true;
        this.animationRunnable = null;
        ValueAnimator valueAnimator = this.reboundAnimator;
        if (valueAnimator != null) {
            valueAnimator.removeAllListeners();
            this.reboundAnimator.removeAllUpdateListeners();
            this.reboundAnimator.setDuration(0L);
            this.reboundAnimator.cancel();
            this.reboundAnimator = null;
        }
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (refreshComponent != null && this.mState == RefreshState.Refreshing) {
            refreshComponent.onFinish(this, false);
        }
        RefreshComponent refreshComponent2 = this.mRefreshFooter;
        if (refreshComponent2 != null && this.mState == RefreshState.Loading) {
            refreshComponent2.onFinish(this, false);
        }
        if (this.mSpinner != 0) {
            this.mKernel.moveSpinner(0, true);
        }
        RefreshState refreshState = this.mState;
        RefreshState refreshState2 = RefreshState.None;
        if (refreshState != refreshState2) {
            notifyStateChanged(refreshState2);
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        this.mFooterLocked = false;
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        Paint paint;
        Paint paint2;
        RefreshContent refreshContent = this.mRefreshContent;
        View view2 = refreshContent != null ? refreshContent.getView() : null;
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (refreshComponent != null && refreshComponent.getView() == view) {
            if (!isEnableRefreshOrLoadMore(this.mEnableRefresh) || (!this.mEnablePreviewInEditMode && isInEditMode())) {
                return true;
            }
            if (view2 != null) {
                int max = Math.max(view2.getTop() + view2.getPaddingTop() + this.mSpinner, view.getTop());
                int i = this.mHeaderBackgroundColor;
                if (i != 0 && (paint2 = this.mPaint) != null) {
                    paint2.setColor(i);
                    if (this.mRefreshHeader.getSpinnerStyle().scale) {
                        max = view.getBottom();
                    } else if (this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Translate) {
                        max = view.getBottom() + this.mSpinner;
                    }
                    canvas.drawRect(0.0f, view.getTop(), getWidth(), max, this.mPaint);
                }
                if ((this.mEnableClipHeaderWhenFixedBehind && this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind) || this.mRefreshHeader.getSpinnerStyle().scale) {
                    canvas.save();
                    canvas.clipRect(view.getLeft(), view.getTop(), view.getRight(), max);
                    boolean drawChild = super.drawChild(canvas, view, j);
                    canvas.restore();
                    return drawChild;
                }
            }
        }
        RefreshComponent refreshComponent2 = this.mRefreshFooter;
        if (refreshComponent2 != null && refreshComponent2.getView() == view) {
            if (!isEnableRefreshOrLoadMore(this.mEnableLoadMore) || (!this.mEnablePreviewInEditMode && isInEditMode())) {
                return true;
            }
            if (view2 != null) {
                int min = Math.min((view2.getBottom() - view2.getPaddingBottom()) + this.mSpinner, view.getBottom());
                int i2 = this.mFooterBackgroundColor;
                if (i2 != 0 && (paint = this.mPaint) != null) {
                    paint.setColor(i2);
                    if (this.mRefreshFooter.getSpinnerStyle().scale) {
                        min = view.getTop();
                    } else if (this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Translate) {
                        min = view.getTop() + this.mSpinner;
                    }
                    canvas.drawRect(0.0f, min, getWidth(), view.getBottom(), this.mPaint);
                }
                if ((this.mEnableClipFooterWhenFixedBehind && this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind) || this.mRefreshFooter.getSpinnerStyle().scale) {
                    canvas.save();
                    canvas.clipRect(view.getLeft(), min, view.getRight(), view.getBottom());
                    boolean drawChild2 = super.drawChild(canvas, view, j);
                    canvas.restore();
                    return drawChild2;
                }
            }
        }
        return super.drawChild(canvas, view, j);
    }

    @Override // android.view.View
    public void computeScroll() {
        this.mScroller.getCurrY();
        if (this.mScroller.computeScrollOffset()) {
            int finalY = this.mScroller.getFinalY();
            if ((finalY < 0 && ((this.mEnableRefresh || this.mEnableOverScrollDrag) && this.mRefreshContent.canRefresh())) || (finalY > 0 && ((this.mEnableLoadMore || this.mEnableOverScrollDrag) && this.mRefreshContent.canLoadMore()))) {
                if (this.mVerticalPermit) {
                    animSpinnerBounce(finalY > 0 ? -this.mScroller.getCurrVelocity() : this.mScroller.getCurrVelocity());
                }
                this.mScroller.forceFinished(true);
            } else {
                this.mVerticalPermit = true;
                invalidate();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x00c6, code lost:
    
        if (r2.isFinishing == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00ca, code lost:
    
        if (r2.isHeader == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00d8, code lost:
    
        if (r2.isFinishing == false) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00dc, code lost:
    
        if (r2.isFooter == false) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0108, code lost:
    
        if (r6 != 3) goto L233;
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean dispatchTouchEvent(android.view.MotionEvent r24) {
        /*
            Method dump skipped, instructions count: 868
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.dispatchTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        View scrollableView = this.mRefreshContent.getScrollableView();
        if ((Build.VERSION.SDK_INT >= 21 || !(scrollableView instanceof AbsListView)) && ViewCompat.isNestedScrollingEnabled(scrollableView)) {
            this.mEnableDisallowIntercept = z;
            super.requestDisallowInterceptTouchEvent(z);
        }
    }

    protected boolean startFlingIfNeed(float f) {
        if (f == 0.0f) {
            f = this.mCurrentVelocity;
        }
        if (Math.abs(f) > this.mMinimumVelocity) {
            int i = this.mSpinner;
            if (i * f < 0.0f) {
                RefreshState refreshState = this.mState;
                if (refreshState == RefreshState.Refreshing || refreshState == RefreshState.Loading || (i < 0 && this.mFooterNoMoreData)) {
                    this.animationRunnable = new FlingRunnable(f).start();
                    return true;
                }
                if (refreshState.isReleaseToOpening) {
                    return true;
                }
            }
            if ((f < 0.0f && ((this.mEnableOverScrollBounce && (this.mEnableLoadMore || this.mEnableOverScrollDrag)) || ((this.mState == RefreshState.Loading && i >= 0) || (this.mEnableAutoLoadMore && isEnableRefreshOrLoadMore(this.mEnableLoadMore))))) || (f > 0.0f && ((this.mEnableOverScrollBounce && this.mEnableRefresh) || this.mEnableOverScrollDrag || (this.mState == RefreshState.Refreshing && this.mSpinner <= 0)))) {
                this.mVerticalPermit = false;
                this.mScroller.fling(0, 0, 0, (int) (-f), 0, 0, -2147483647, Integer.MAX_VALUE);
                this.mScroller.computeScrollOffset();
                invalidate();
            }
        }
        return false;
    }

    protected boolean interceptAnimatorByAction(int i) {
        if (i == 0) {
            if (this.reboundAnimator != null) {
                RefreshState refreshState = this.mState;
                if (refreshState.isFinishing || refreshState == RefreshState.TwoLevelReleased || refreshState == RefreshState.RefreshReleased || refreshState == RefreshState.LoadReleased) {
                    return true;
                }
                if (refreshState == RefreshState.PullDownCanceled) {
                    this.mKernel.setState(RefreshState.PullDownToRefresh);
                } else if (refreshState == RefreshState.PullUpCanceled) {
                    this.mKernel.setState(RefreshState.PullUpToLoad);
                }
                this.reboundAnimator.setDuration(0L);
                this.reboundAnimator.cancel();
                this.reboundAnimator = null;
            }
            this.animationRunnable = null;
        }
        return this.reboundAnimator != null;
    }

    protected void notifyStateChanged(RefreshState refreshState) {
        RefreshState refreshState2 = this.mState;
        if (refreshState2 != refreshState) {
            this.mState = refreshState;
            this.mViceState = refreshState;
            RefreshComponent refreshComponent = this.mRefreshHeader;
            RefreshComponent refreshComponent2 = this.mRefreshFooter;
            OnMultiListener onMultiListener = this.mOnMultiListener;
            if (refreshComponent != null) {
                refreshComponent.onStateChanged(this, refreshState2, refreshState);
            }
            if (refreshComponent2 != null) {
                refreshComponent2.onStateChanged(this, refreshState2, refreshState);
            }
            if (onMultiListener != null) {
                onMultiListener.onStateChanged(this, refreshState2, refreshState);
            }
            if (refreshState == RefreshState.LoadFinish) {
                this.mFooterLocked = false;
                return;
            }
            return;
        }
        if (this.mViceState != refreshState2) {
            this.mViceState = refreshState2;
        }
    }

    protected void setStateDirectLoading(boolean z) {
        RefreshState refreshState = this.mState;
        RefreshState refreshState2 = RefreshState.Loading;
        if (refreshState != refreshState2) {
            this.mLastOpenTime = System.currentTimeMillis();
            this.mFooterLocked = true;
            notifyStateChanged(refreshState2);
            OnLoadMoreListener onLoadMoreListener = this.mLoadMoreListener;
            if (onLoadMoreListener != null) {
                if (z) {
                    onLoadMoreListener.onLoadMore(this);
                }
            } else if (this.mOnMultiListener == null) {
                finishLoadMore(2000);
            }
            RefreshComponent refreshComponent = this.mRefreshFooter;
            if (refreshComponent != null) {
                float f = this.mFooterMaxDragRate;
                if (f < 10.0f) {
                    f *= this.mFooterHeight;
                }
                refreshComponent.onStartAnimator(this, this.mFooterHeight, (int) f);
            }
            OnMultiListener onMultiListener = this.mOnMultiListener;
            if (onMultiListener == null || !(this.mRefreshFooter instanceof RefreshFooter)) {
                return;
            }
            if (z) {
                onMultiListener.onLoadMore(this);
            }
            float f2 = this.mFooterMaxDragRate;
            if (f2 < 10.0f) {
                f2 *= this.mFooterHeight;
            }
            this.mOnMultiListener.onFooterStartAnimator((RefreshFooter) this.mRefreshFooter, this.mFooterHeight, (int) f2);
        }
    }

    protected void setStateLoading(final boolean z) {
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (animator == null || animator.getDuration() != 0) {
                    SmartRefreshLayout.this.setStateDirectLoading(z);
                }
            }
        };
        notifyStateChanged(RefreshState.LoadReleased);
        ValueAnimator animSpinner = this.mKernel.animSpinner(-this.mFooterHeight);
        if (animSpinner != null) {
            animSpinner.addListener(animatorListenerAdapter);
        }
        RefreshComponent refreshComponent = this.mRefreshFooter;
        if (refreshComponent != null) {
            float f = this.mFooterMaxDragRate;
            if (f < 10.0f) {
                f *= this.mFooterHeight;
            }
            refreshComponent.onReleased(this, this.mFooterHeight, (int) f);
        }
        OnMultiListener onMultiListener = this.mOnMultiListener;
        if (onMultiListener != null) {
            RefreshComponent refreshComponent2 = this.mRefreshFooter;
            if (refreshComponent2 instanceof RefreshFooter) {
                float f2 = this.mFooterMaxDragRate;
                if (f2 < 10.0f) {
                    f2 *= this.mFooterHeight;
                }
                onMultiListener.onFooterReleased((RefreshFooter) refreshComponent2, this.mFooterHeight, (int) f2);
            }
        }
        if (animSpinner == null) {
            animatorListenerAdapter.onAnimationEnd(null);
        }
    }

    protected void setStateRefreshing(final boolean z) {
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (animator == null || animator.getDuration() != 0) {
                    SmartRefreshLayout.this.mLastOpenTime = System.currentTimeMillis();
                    SmartRefreshLayout.this.notifyStateChanged(RefreshState.Refreshing);
                    SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                    OnRefreshListener onRefreshListener = smartRefreshLayout.mRefreshListener;
                    if (onRefreshListener != null) {
                        if (z) {
                            onRefreshListener.onRefresh(smartRefreshLayout);
                        }
                    } else if (smartRefreshLayout.mOnMultiListener == null) {
                        smartRefreshLayout.finishRefresh(BannerConfig.LOOP_TIME);
                    }
                    SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                    RefreshComponent refreshComponent = smartRefreshLayout2.mRefreshHeader;
                    if (refreshComponent != null) {
                        float f = smartRefreshLayout2.mHeaderMaxDragRate;
                        if (f < 10.0f) {
                            f *= smartRefreshLayout2.mHeaderHeight;
                        }
                        refreshComponent.onStartAnimator(smartRefreshLayout2, smartRefreshLayout2.mHeaderHeight, (int) f);
                    }
                    SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                    OnMultiListener onMultiListener = smartRefreshLayout3.mOnMultiListener;
                    if (onMultiListener == null || !(smartRefreshLayout3.mRefreshHeader instanceof RefreshHeader)) {
                        return;
                    }
                    if (z) {
                        onMultiListener.onRefresh(smartRefreshLayout3);
                    }
                    SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                    float f2 = smartRefreshLayout4.mHeaderMaxDragRate;
                    if (f2 < 10.0f) {
                        f2 *= smartRefreshLayout4.mHeaderHeight;
                    }
                    smartRefreshLayout4.mOnMultiListener.onHeaderStartAnimator((RefreshHeader) smartRefreshLayout4.mRefreshHeader, smartRefreshLayout4.mHeaderHeight, (int) f2);
                }
            }
        };
        notifyStateChanged(RefreshState.RefreshReleased);
        ValueAnimator animSpinner = this.mKernel.animSpinner(this.mHeaderHeight);
        if (animSpinner != null) {
            animSpinner.addListener(animatorListenerAdapter);
        }
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (refreshComponent != null) {
            float f = this.mHeaderMaxDragRate;
            if (f < 10.0f) {
                f *= this.mHeaderHeight;
            }
            refreshComponent.onReleased(this, this.mHeaderHeight, (int) f);
        }
        OnMultiListener onMultiListener = this.mOnMultiListener;
        if (onMultiListener != null) {
            RefreshComponent refreshComponent2 = this.mRefreshHeader;
            if (refreshComponent2 instanceof RefreshHeader) {
                float f2 = this.mHeaderMaxDragRate;
                if (f2 < 10.0f) {
                    f2 *= this.mHeaderHeight;
                }
                onMultiListener.onHeaderReleased((RefreshHeader) refreshComponent2, this.mHeaderHeight, (int) f2);
            }
        }
        if (animSpinner == null) {
            animatorListenerAdapter.onAnimationEnd(null);
        }
    }

    protected void setViceState(RefreshState refreshState) {
        RefreshState refreshState2 = this.mState;
        if (refreshState2.isDragging && refreshState2.isHeader != refreshState.isHeader) {
            notifyStateChanged(RefreshState.None);
        }
        if (this.mViceState != refreshState) {
            this.mViceState = refreshState;
        }
    }

    protected boolean isEnableTranslationContent(boolean z, RefreshComponent refreshComponent) {
        return z || this.mEnablePureScrollMode || refreshComponent == null || refreshComponent.getSpinnerStyle() == SpinnerStyle.FixedBehind;
    }

    protected boolean isEnableRefreshOrLoadMore(boolean z) {
        return z && !this.mEnablePureScrollMode;
    }

    protected class FlingRunnable implements Runnable {
        int mOffset;
        float mVelocity;
        int mFrameDelay = 10;
        float mDamping = 0.98f;
        long mStartTime = 0;
        long mLastTime = AnimationUtils.currentAnimationTimeMillis();

        FlingRunnable(float f) {
            this.mVelocity = f;
            this.mOffset = SmartRefreshLayout.this.mSpinner;
        }

        /* JADX WARN: Code restructure failed: missing block: B:48:0x0057, code lost:
        
            if (r0.mSpinner > r0.mHeaderHeight) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x0049, code lost:
        
            if (r0.mSpinner >= (-r0.mFooterHeight)) goto L29;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.Runnable start() {
            /*
                r11 = this;
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.RefreshState r1 = r0.mState
                boolean r2 = r1.isFinishing
                r3 = 0
                if (r2 == 0) goto La
                return r3
            La:
                int r2 = r0.mSpinner
                if (r2 == 0) goto Lab
                boolean r1 = r1.isOpening
                if (r1 != 0) goto L26
                boolean r1 = r0.mFooterNoMoreData
                if (r1 == 0) goto L59
                boolean r1 = r0.mEnableFooterFollowWhenNoMoreData
                if (r1 == 0) goto L59
                boolean r1 = r0.mFooterNoMoreDataEffective
                if (r1 == 0) goto L59
                boolean r1 = r0.mEnableLoadMore
                boolean r0 = r0.isEnableRefreshOrLoadMore(r1)
                if (r0 == 0) goto L59
            L26:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.RefreshState r1 = r0.mState
                com.scwang.smart.refresh.layout.constant.RefreshState r2 = com.scwang.smart.refresh.layout.constant.RefreshState.Loading
                if (r1 == r2) goto L42
                boolean r1 = r0.mFooterNoMoreData
                if (r1 == 0) goto L4b
                boolean r1 = r0.mEnableFooterFollowWhenNoMoreData
                if (r1 == 0) goto L4b
                boolean r1 = r0.mFooterNoMoreDataEffective
                if (r1 == 0) goto L4b
                boolean r1 = r0.mEnableLoadMore
                boolean r0 = r0.isEnableRefreshOrLoadMore(r1)
                if (r0 == 0) goto L4b
            L42:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r1 = r0.mSpinner
                int r0 = r0.mFooterHeight
                int r0 = -r0
                if (r1 < r0) goto L59
            L4b:
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.RefreshState r1 = r0.mState
                com.scwang.smart.refresh.layout.constant.RefreshState r2 = com.scwang.smart.refresh.layout.constant.RefreshState.Refreshing
                if (r1 != r2) goto Lab
                int r1 = r0.mSpinner
                int r0 = r0.mHeaderHeight
                if (r1 <= r0) goto Lab
            L59:
                r0 = 0
                com.scwang.smart.refresh.layout.SmartRefreshLayout r1 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                int r1 = r1.mSpinner
                float r2 = r11.mVelocity
                r4 = r1
            L61:
                int r5 = r1 * r4
                if (r5 <= 0) goto Lab
                double r5 = (double) r2
                float r2 = r11.mDamping
                double r7 = (double) r2
                int r0 = r0 + 1
                int r2 = r11.mFrameDelay
                int r2 = r2 * r0
                float r2 = (float) r2
                r9 = 1092616192(0x41200000, float:10.0)
                float r2 = r2 / r9
                double r9 = (double) r2
                double r7 = java.lang.Math.pow(r7, r9)
                double r5 = r5 * r7
                float r2 = (float) r5
                int r5 = r11.mFrameDelay
                float r5 = (float) r5
                r6 = 1065353216(0x3f800000, float:1.0)
                float r5 = r5 * r6
                r7 = 1148846080(0x447a0000, float:1000.0)
                float r5 = r5 / r7
                float r5 = r5 * r2
                float r7 = java.lang.Math.abs(r5)
                int r6 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
                if (r6 >= 0) goto La7
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                com.scwang.smart.refresh.layout.constant.RefreshState r1 = r0.mState
                boolean r2 = r1.isOpening
                if (r2 == 0) goto La6
                com.scwang.smart.refresh.layout.constant.RefreshState r2 = com.scwang.smart.refresh.layout.constant.RefreshState.Refreshing
                if (r1 != r2) goto L9f
                int r5 = r0.mHeaderHeight
                if (r4 > r5) goto La6
            L9f:
                if (r1 == r2) goto Lab
                int r0 = r0.mFooterHeight
                int r0 = -r0
                if (r4 >= r0) goto Lab
            La6:
                return r3
            La7:
                float r4 = (float) r4
                float r4 = r4 + r5
                int r4 = (int) r4
                goto L61
            Lab:
                long r0 = android.view.animation.AnimationUtils.currentAnimationTimeMillis()
                r11.mStartTime = r0
                com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = com.scwang.smart.refresh.layout.SmartRefreshLayout.this
                android.os.Handler r0 = r0.mHandler
                int r1 = r11.mFrameDelay
                long r1 = (long) r1
                r0.postDelayed(r11, r1)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.FlingRunnable.start():java.lang.Runnable");
        }

        @Override // java.lang.Runnable
        public void run() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.animationRunnable != this || smartRefreshLayout.mState.isFinishing) {
                return;
            }
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            long j = currentAnimationTimeMillis - this.mLastTime;
            float pow = (float) (this.mVelocity * Math.pow(this.mDamping, (currentAnimationTimeMillis - this.mStartTime) / (1000.0f / this.mFrameDelay)));
            this.mVelocity = pow;
            float f = pow * ((j * 1.0f) / 1000.0f);
            if (Math.abs(f) > 1.0f) {
                this.mLastTime = currentAnimationTimeMillis;
                int i = (int) (this.mOffset + f);
                this.mOffset = i;
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                if (smartRefreshLayout2.mSpinner * i > 0) {
                    smartRefreshLayout2.mKernel.moveSpinner(i, true);
                    SmartRefreshLayout.this.mHandler.postDelayed(this, this.mFrameDelay);
                    return;
                }
                smartRefreshLayout2.animationRunnable = null;
                smartRefreshLayout2.mKernel.moveSpinner(0, true);
                SmartUtil.fling(SmartRefreshLayout.this.mRefreshContent.getScrollableView(), (int) (-this.mVelocity));
                SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                if (!smartRefreshLayout3.mFooterLocked || f <= 0.0f) {
                    return;
                }
                smartRefreshLayout3.mFooterLocked = false;
                return;
            }
            SmartRefreshLayout.this.animationRunnable = null;
        }
    }

    protected class BounceRunnable implements Runnable {
        int mSmoothDistance;
        float mVelocity;
        int mFrame = 0;
        int mFrameDelay = 10;
        float mOffset = 0.0f;
        long mLastTime = AnimationUtils.currentAnimationTimeMillis();

        BounceRunnable(float f, int i) {
            this.mVelocity = f;
            this.mSmoothDistance = i;
            SmartRefreshLayout.this.mHandler.postDelayed(this, this.mFrameDelay);
            if (f > 0.0f) {
                SmartRefreshLayout.this.mKernel.setState(RefreshState.PullDownToRefresh);
            } else {
                SmartRefreshLayout.this.mKernel.setState(RefreshState.PullUpToLoad);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.animationRunnable != this || smartRefreshLayout.mState.isFinishing) {
                return;
            }
            if (Math.abs(smartRefreshLayout.mSpinner) >= Math.abs(this.mSmoothDistance)) {
                if (this.mSmoothDistance != 0) {
                    double d = this.mVelocity;
                    this.mFrame = this.mFrame + 1;
                    this.mVelocity = (float) (d * Math.pow(0.44999998807907104d, r4 * 2));
                } else {
                    double d2 = this.mVelocity;
                    this.mFrame = this.mFrame + 1;
                    this.mVelocity = (float) (d2 * Math.pow(0.8500000238418579d, r4 * 2));
                }
            } else {
                double d3 = this.mVelocity;
                this.mFrame = this.mFrame + 1;
                this.mVelocity = (float) (d3 * Math.pow(0.949999988079071d, r4 * 2));
            }
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            float f = this.mVelocity * (((currentAnimationTimeMillis - this.mLastTime) * 1.0f) / 1000.0f);
            if (Math.abs(f) >= 1.0f) {
                this.mLastTime = currentAnimationTimeMillis;
                float f2 = this.mOffset + f;
                this.mOffset = f2;
                SmartRefreshLayout.this.moveSpinnerInfinitely(f2);
                SmartRefreshLayout.this.mHandler.postDelayed(this, this.mFrameDelay);
                return;
            }
            SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
            RefreshState refreshState = smartRefreshLayout2.mViceState;
            boolean z = refreshState.isDragging;
            if (z && refreshState.isHeader) {
                smartRefreshLayout2.mKernel.setState(RefreshState.PullDownCanceled);
            } else if (z && refreshState.isFooter) {
                smartRefreshLayout2.mKernel.setState(RefreshState.PullUpCanceled);
            }
            SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
            smartRefreshLayout3.animationRunnable = null;
            if (Math.abs(smartRefreshLayout3.mSpinner) >= Math.abs(this.mSmoothDistance)) {
                int min = Math.min(Math.max((int) SmartUtil.px2dp(Math.abs(SmartRefreshLayout.this.mSpinner - this.mSmoothDistance)), 30), 100) * 10;
                SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                smartRefreshLayout4.animSpinner(this.mSmoothDistance, 0, smartRefreshLayout4.mReboundInterpolator, min);
            }
        }
    }

    protected ValueAnimator animSpinner(int i, int i2, Interpolator interpolator, int i3) {
        if (this.mSpinner == i) {
            return null;
        }
        ValueAnimator valueAnimator = this.reboundAnimator;
        if (valueAnimator != null) {
            valueAnimator.setDuration(0L);
            this.reboundAnimator.cancel();
            this.reboundAnimator = null;
        }
        this.animationRunnable = null;
        ValueAnimator ofInt = ValueAnimator.ofInt(this.mSpinner, i);
        this.reboundAnimator = ofInt;
        ofInt.setDuration(i3);
        this.reboundAnimator.setInterpolator(interpolator);
        this.reboundAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                RefreshState refreshState;
                RefreshState refreshState2;
                if (animator == null || animator.getDuration() != 0) {
                    SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                    smartRefreshLayout.reboundAnimator = null;
                    if (smartRefreshLayout.mSpinner == 0 && (refreshState = smartRefreshLayout.mState) != (refreshState2 = RefreshState.None) && !refreshState.isOpening && !refreshState.isDragging) {
                        smartRefreshLayout.notifyStateChanged(refreshState2);
                        return;
                    }
                    RefreshState refreshState3 = smartRefreshLayout.mState;
                    if (refreshState3 != smartRefreshLayout.mViceState) {
                        smartRefreshLayout.setViceState(refreshState3);
                    }
                }
            }
        });
        this.reboundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SmartRefreshLayout.this.mKernel.moveSpinner(((Integer) valueAnimator2.getAnimatedValue()).intValue(), false);
            }
        });
        this.reboundAnimator.setStartDelay(i2);
        this.reboundAnimator.start();
        return this.reboundAnimator;
    }

    protected void animSpinnerBounce(float f) {
        RefreshState refreshState;
        if (this.reboundAnimator == null) {
            if (f > 0.0f && ((refreshState = this.mState) == RefreshState.Refreshing || refreshState == RefreshState.TwoLevel)) {
                this.animationRunnable = new BounceRunnable(f, this.mHeaderHeight);
                return;
            }
            if (f < 0.0f && (this.mState == RefreshState.Loading || ((this.mEnableFooterFollowWhenNoMoreData && this.mFooterNoMoreData && this.mFooterNoMoreDataEffective && isEnableRefreshOrLoadMore(this.mEnableLoadMore)) || (this.mEnableAutoLoadMore && !this.mFooterNoMoreData && isEnableRefreshOrLoadMore(this.mEnableLoadMore) && this.mState != RefreshState.Refreshing)))) {
                this.animationRunnable = new BounceRunnable(f, -this.mFooterHeight);
            } else if (this.mSpinner == 0 && this.mEnableOverScrollBounce) {
                this.animationRunnable = new BounceRunnable(f, 0);
            }
        }
    }

    protected void overSpinner() {
        RefreshState refreshState = this.mState;
        if (refreshState == RefreshState.TwoLevel) {
            if (this.mCurrentVelocity > -1000 && this.mSpinner > getHeight() / 2) {
                ValueAnimator animSpinner = this.mKernel.animSpinner(getHeight());
                if (animSpinner != null) {
                    animSpinner.setDuration(this.mFloorDuration);
                    return;
                }
                return;
            }
            if (this.mIsBeingDragged) {
                this.mKernel.finishTwoLevel();
                return;
            }
            return;
        }
        RefreshState refreshState2 = RefreshState.Loading;
        if (refreshState == refreshState2 || (this.mEnableFooterFollowWhenNoMoreData && this.mFooterNoMoreData && this.mFooterNoMoreDataEffective && this.mSpinner < 0 && isEnableRefreshOrLoadMore(this.mEnableLoadMore))) {
            int i = this.mSpinner;
            int i2 = this.mFooterHeight;
            if (i < (-i2)) {
                this.mKernel.animSpinner(-i2);
                return;
            } else {
                if (i > 0) {
                    this.mKernel.animSpinner(0);
                    return;
                }
                return;
            }
        }
        RefreshState refreshState3 = this.mState;
        RefreshState refreshState4 = RefreshState.Refreshing;
        if (refreshState3 == refreshState4) {
            int i3 = this.mSpinner;
            int i4 = this.mHeaderHeight;
            if (i3 > i4) {
                this.mKernel.animSpinner(i4);
                return;
            } else {
                if (i3 < 0) {
                    this.mKernel.animSpinner(0);
                    return;
                }
                return;
            }
        }
        if (refreshState3 == RefreshState.PullDownToRefresh) {
            this.mKernel.setState(RefreshState.PullDownCanceled);
            return;
        }
        if (refreshState3 == RefreshState.PullUpToLoad) {
            this.mKernel.setState(RefreshState.PullUpCanceled);
            return;
        }
        if (refreshState3 == RefreshState.ReleaseToRefresh) {
            this.mKernel.setState(refreshState4);
            return;
        }
        if (refreshState3 == RefreshState.ReleaseToLoad) {
            this.mKernel.setState(refreshState2);
            return;
        }
        if (refreshState3 == RefreshState.ReleaseToTwoLevel) {
            this.mKernel.setState(RefreshState.TwoLevelReleased);
            return;
        }
        if (refreshState3 == RefreshState.RefreshReleased) {
            if (this.reboundAnimator == null) {
                this.mKernel.animSpinner(this.mHeaderHeight);
            }
        } else if (refreshState3 == RefreshState.LoadReleased) {
            if (this.reboundAnimator == null) {
                this.mKernel.animSpinner(-this.mFooterHeight);
            }
        } else {
            if (refreshState3 == RefreshState.LoadFinish || this.mSpinner == 0) {
                return;
            }
            this.mKernel.animSpinner(0);
        }
    }

    protected void moveSpinnerInfinitely(float f) {
        RefreshState refreshState;
        float f2 = (!this.mNestedInProgress || this.mEnableLoadMoreWhenContentNotFull || f >= 0.0f || this.mRefreshContent.canLoadMore()) ? f : 0.0f;
        if (f2 > this.mScreenHeightPixels * 5 && getTag() == null) {
            int i = R$id.srl_tag;
            if (getTag(i) == null) {
                float f3 = this.mLastTouchY;
                int i2 = this.mScreenHeightPixels;
                if (f3 < i2 / 6.0f && this.mLastTouchX < i2 / 16.0f) {
                    Toast.makeText(getContext(), "你这么死拉，臣妾做不到啊！", 0).show();
                    setTag(i, "你这么死拉，臣妾做不到啊！");
                }
            }
        }
        RefreshState refreshState2 = this.mState;
        if (refreshState2 == RefreshState.TwoLevel && f2 > 0.0f) {
            this.mKernel.moveSpinner(Math.min((int) f2, getMeasuredHeight()), true);
        } else if (refreshState2 == RefreshState.Refreshing && f2 >= 0.0f) {
            int i3 = this.mHeaderHeight;
            if (f2 < i3) {
                this.mKernel.moveSpinner((int) f2, true);
            } else {
                float f4 = this.mHeaderMaxDragRate;
                if (f4 < 10.0f) {
                    f4 *= i3;
                }
                double d = f4 - i3;
                int max = Math.max((this.mScreenHeightPixels * 4) / 3, getHeight());
                int i4 = this.mHeaderHeight;
                double d2 = max - i4;
                double max2 = Math.max(0.0f, (f2 - i4) * this.mDragRate);
                double d3 = -max2;
                if (d2 == 0.0d) {
                    d2 = 1.0d;
                }
                this.mKernel.moveSpinner(((int) Math.min(d * (1.0d - Math.pow(100.0d, d3 / d2)), max2)) + this.mHeaderHeight, true);
            }
        } else if (f2 < 0.0f && (refreshState2 == RefreshState.Loading || ((this.mEnableFooterFollowWhenNoMoreData && this.mFooterNoMoreData && this.mFooterNoMoreDataEffective && isEnableRefreshOrLoadMore(this.mEnableLoadMore)) || (this.mEnableAutoLoadMore && !this.mFooterNoMoreData && isEnableRefreshOrLoadMore(this.mEnableLoadMore))))) {
            int i5 = this.mFooterHeight;
            if (f2 > (-i5)) {
                this.mKernel.moveSpinner((int) f2, true);
            } else {
                float f5 = this.mFooterMaxDragRate;
                if (f5 < 10.0f) {
                    f5 *= i5;
                }
                double d4 = f5 - i5;
                int max3 = Math.max((this.mScreenHeightPixels * 4) / 3, getHeight());
                int i6 = this.mFooterHeight;
                double d5 = max3 - i6;
                double d6 = -Math.min(0.0f, (i6 + f2) * this.mDragRate);
                double d7 = -d6;
                if (d5 == 0.0d) {
                    d5 = 1.0d;
                }
                this.mKernel.moveSpinner(((int) (-Math.min(d4 * (1.0d - Math.pow(100.0d, d7 / d5)), d6))) - this.mFooterHeight, true);
            }
        } else if (f2 >= 0.0f) {
            float f6 = this.mHeaderMaxDragRate;
            double d8 = f6 < 10.0f ? this.mHeaderHeight * f6 : f6;
            double max4 = Math.max(this.mScreenHeightPixels / 2, getHeight());
            double max5 = Math.max(0.0f, this.mDragRate * f2);
            double d9 = -max5;
            if (max4 == 0.0d) {
                max4 = 1.0d;
            }
            this.mKernel.moveSpinner((int) Math.min(d8 * (1.0d - Math.pow(100.0d, d9 / max4)), max5), true);
        } else {
            float f7 = this.mFooterMaxDragRate;
            double d10 = f7 < 10.0f ? this.mFooterHeight * f7 : f7;
            double max6 = Math.max(this.mScreenHeightPixels / 2, getHeight());
            double d11 = -Math.min(0.0f, this.mDragRate * f2);
            this.mKernel.moveSpinner((int) (-Math.min(d10 * (1.0d - Math.pow(100.0d, (-d11) / (max6 == 0.0d ? 1.0d : max6))), d11)), true);
        }
        if (!this.mEnableAutoLoadMore || this.mFooterNoMoreData || !isEnableRefreshOrLoadMore(this.mEnableLoadMore) || f2 >= 0.0f || (refreshState = this.mState) == RefreshState.Refreshing || refreshState == RefreshState.Loading || refreshState == RefreshState.LoadFinish) {
            return;
        }
        if (this.mDisableContentWhenLoading) {
            this.animationRunnable = null;
            this.mKernel.animSpinner(-this.mFooterHeight);
        }
        setStateDirectLoading(false);
        this.mHandler.postDelayed(new Runnable() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.5
            @Override // java.lang.Runnable
            public void run() {
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                OnLoadMoreListener onLoadMoreListener = smartRefreshLayout.mLoadMoreListener;
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore(smartRefreshLayout);
                } else if (smartRefreshLayout.mOnMultiListener == null) {
                    smartRefreshLayout.finishLoadMore(2000);
                }
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                OnMultiListener onMultiListener = smartRefreshLayout2.mOnMultiListener;
                if (onMultiListener != null) {
                    onMultiListener.onLoadMore(smartRefreshLayout2);
                }
            }
        }, this.mReboundDuration);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int backgroundColor;
        public SpinnerStyle spinnerStyle;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.backgroundColor = 0;
            this.spinnerStyle = null;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SmartRefreshLayout_Layout);
            this.backgroundColor = obtainStyledAttributes.getColor(R$styleable.SmartRefreshLayout_Layout_layout_srlBackgroundColor, this.backgroundColor);
            int i = R$styleable.SmartRefreshLayout_Layout_layout_srlSpinnerStyle;
            if (obtainStyledAttributes.hasValue(i)) {
                this.spinnerStyle = SpinnerStyle.values[obtainStyledAttributes.getInt(i, SpinnerStyle.Translate.ordinal)];
            }
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.backgroundColor = 0;
            this.spinnerStyle = null;
        }
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        return this.mNestedParent.getNestedScrollAxes();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        return (isEnabled() && isNestedScrollingEnabled() && (i & 2) != 0) && (this.mEnableOverScrollDrag || this.mEnableRefresh || this.mEnableLoadMore);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.mNestedParent.onNestedScrollAccepted(view, view2, i);
        this.mNestedChild.startNestedScroll(i & 2);
        this.mTotalUnconsumed = this.mSpinner;
        this.mNestedInProgress = true;
        interceptAnimatorByAction(0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        int i3 = this.mTotalUnconsumed;
        int i4 = 0;
        if (i2 * i3 > 0) {
            if (Math.abs(i2) > Math.abs(this.mTotalUnconsumed)) {
                int i5 = this.mTotalUnconsumed;
                this.mTotalUnconsumed = 0;
                i4 = i5;
            } else {
                this.mTotalUnconsumed -= i2;
                i4 = i2;
            }
            moveSpinnerInfinitely(this.mTotalUnconsumed);
        } else if (i2 > 0 && this.mFooterLocked) {
            int i6 = i3 - i2;
            this.mTotalUnconsumed = i6;
            moveSpinnerInfinitely(i6);
            i4 = i2;
        }
        this.mNestedChild.dispatchNestedPreScroll(i, i2 - i4, iArr, null);
        iArr[1] = iArr[1] + i4;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        ScrollBoundaryDecider scrollBoundaryDecider;
        ViewParent parent;
        ScrollBoundaryDecider scrollBoundaryDecider2;
        boolean dispatchNestedScroll = this.mNestedChild.dispatchNestedScroll(i, i2, i3, i4, this.mParentOffsetInWindow);
        int i5 = i4 + this.mParentOffsetInWindow[1];
        if ((i5 < 0 && ((this.mEnableRefresh || this.mEnableOverScrollDrag) && (this.mTotalUnconsumed != 0 || (scrollBoundaryDecider2 = this.mScrollBoundaryDecider) == null || scrollBoundaryDecider2.canRefresh(this.mRefreshContent.getView())))) || (i5 > 0 && ((this.mEnableLoadMore || this.mEnableOverScrollDrag) && (this.mTotalUnconsumed != 0 || (scrollBoundaryDecider = this.mScrollBoundaryDecider) == null || scrollBoundaryDecider.canLoadMore(this.mRefreshContent.getView()))))) {
            RefreshState refreshState = this.mViceState;
            if (refreshState == RefreshState.None || refreshState.isOpening) {
                this.mKernel.setState(i5 > 0 ? RefreshState.PullUpToLoad : RefreshState.PullDownToRefresh);
                if (!dispatchNestedScroll && (parent = getParent()) != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }
            int i6 = this.mTotalUnconsumed - i5;
            this.mTotalUnconsumed = i6;
            moveSpinnerInfinitely(i6);
        }
        if (!this.mFooterLocked || i2 >= 0) {
            return;
        }
        this.mFooterLocked = false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedPreFling(View view, float f, float f2) {
        return (this.mFooterLocked && f2 > 0.0f) || startFlingIfNeed(-f2) || this.mNestedChild.dispatchNestedPreFling(f, f2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        return this.mNestedChild.dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onStopNestedScroll(View view) {
        this.mNestedParent.onStopNestedScroll(view);
        this.mNestedInProgress = false;
        this.mTotalUnconsumed = 0;
        overSpinner();
        this.mNestedChild.stopNestedScroll();
    }

    @Override // android.view.View
    public void setNestedScrollingEnabled(boolean z) {
        this.mEnableNestedScrolling = z;
        this.mNestedChild.setNestedScrollingEnabled(z);
    }

    @Override // android.view.View
    public boolean isNestedScrollingEnabled() {
        return this.mEnableNestedScrolling && (this.mEnableOverScrollDrag || this.mEnableRefresh || this.mEnableLoadMore);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setEnableNestedScroll(boolean z) {
        setNestedScrollingEnabled(z);
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setRefreshHeader(RefreshHeader refreshHeader) {
        return setRefreshHeader(refreshHeader, 0, 0);
    }

    public RefreshLayout setRefreshHeader(RefreshHeader refreshHeader, int i, int i2) {
        RefreshComponent refreshComponent;
        RefreshComponent refreshComponent2 = this.mRefreshHeader;
        if (refreshComponent2 != null) {
            super.removeView(refreshComponent2.getView());
        }
        this.mRefreshHeader = refreshHeader;
        this.mHeaderBackgroundColor = 0;
        this.mHeaderNeedTouchEventWhenRefreshing = false;
        this.mHeaderHeightStatus = DimensionStatus.DefaultUnNotify;
        if (i == 0) {
            i = -1;
        }
        if (i2 == 0) {
            i2 = -2;
        }
        LayoutParams layoutParams = new LayoutParams(i, i2);
        ViewGroup.LayoutParams layoutParams2 = refreshHeader.getView().getLayoutParams();
        if (layoutParams2 instanceof LayoutParams) {
            layoutParams = (LayoutParams) layoutParams2;
        }
        if (this.mRefreshHeader.getSpinnerStyle().front) {
            super.addView(this.mRefreshHeader.getView(), getChildCount(), layoutParams);
        } else {
            super.addView(this.mRefreshHeader.getView(), 0, layoutParams);
        }
        int[] iArr = this.mPrimaryColors;
        if (iArr != null && (refreshComponent = this.mRefreshHeader) != null) {
            refreshComponent.setPrimaryColors(iArr);
        }
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setRefreshFooter(RefreshFooter refreshFooter) {
        return setRefreshFooter(refreshFooter, 0, 0);
    }

    public RefreshLayout setRefreshFooter(RefreshFooter refreshFooter, int i, int i2) {
        RefreshComponent refreshComponent;
        RefreshComponent refreshComponent2 = this.mRefreshFooter;
        if (refreshComponent2 != null) {
            super.removeView(refreshComponent2.getView());
        }
        this.mRefreshFooter = refreshFooter;
        this.mFooterLocked = false;
        this.mFooterBackgroundColor = 0;
        this.mFooterNoMoreDataEffective = false;
        this.mFooterNeedTouchEventWhenLoading = false;
        this.mFooterHeightStatus = DimensionStatus.DefaultUnNotify;
        this.mEnableLoadMore = !this.mManualLoadMore || this.mEnableLoadMore;
        if (i == 0) {
            i = -1;
        }
        if (i2 == 0) {
            i2 = -2;
        }
        LayoutParams layoutParams = new LayoutParams(i, i2);
        ViewGroup.LayoutParams layoutParams2 = refreshFooter.getView().getLayoutParams();
        if (layoutParams2 instanceof LayoutParams) {
            layoutParams = (LayoutParams) layoutParams2;
        }
        if (this.mRefreshFooter.getSpinnerStyle().front) {
            super.addView(this.mRefreshFooter.getView(), getChildCount(), layoutParams);
        } else {
            super.addView(this.mRefreshFooter.getView(), 0, layoutParams);
        }
        int[] iArr = this.mPrimaryColors;
        if (iArr != null && (refreshComponent = this.mRefreshFooter) != null) {
            refreshComponent.setPrimaryColors(iArr);
        }
        return this;
    }

    public RefreshFooter getRefreshFooter() {
        RefreshComponent refreshComponent = this.mRefreshFooter;
        if (refreshComponent instanceof RefreshFooter) {
            return (RefreshFooter) refreshComponent;
        }
        return null;
    }

    public RefreshHeader getRefreshHeader() {
        RefreshComponent refreshComponent = this.mRefreshHeader;
        if (refreshComponent instanceof RefreshHeader) {
            return (RefreshHeader) refreshComponent;
        }
        return null;
    }

    public RefreshState getState() {
        return this.mState;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mRefreshListener = onRefreshListener;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mLoadMoreListener = onLoadMoreListener;
        this.mEnableLoadMore = this.mEnableLoadMore || !(this.mManualLoadMore || onLoadMoreListener == null);
        return this;
    }

    public RefreshLayout setNoMoreData(boolean z) {
        RefreshState refreshState = this.mState;
        if (refreshState == RefreshState.Refreshing && z) {
            finishRefreshWithNoMoreData();
        } else if (refreshState == RefreshState.Loading && z) {
            finishLoadMoreWithNoMoreData();
        } else if (this.mFooterNoMoreData != z) {
            this.mFooterNoMoreData = z;
            RefreshComponent refreshComponent = this.mRefreshFooter;
            if (refreshComponent instanceof RefreshFooter) {
                if (((RefreshFooter) refreshComponent).setNoMoreData(z)) {
                    this.mFooterNoMoreDataEffective = true;
                    if (this.mFooterNoMoreData && this.mEnableFooterFollowWhenNoMoreData && this.mSpinner > 0 && this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Translate && isEnableRefreshOrLoadMore(this.mEnableLoadMore) && isEnableTranslationContent(this.mEnableRefresh, this.mRefreshHeader)) {
                        this.mRefreshFooter.getView().setTranslationY(this.mSpinner);
                    }
                } else {
                    this.mFooterNoMoreDataEffective = false;
                    new RuntimeException("Footer:" + this.mRefreshFooter + " NoMoreData is not supported.(不支持NoMoreData，请使用[ClassicsFooter]或者[自定义Footer并实现setNoMoreData方法且返回true])").printStackTrace();
                }
            }
        }
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout finishRefresh() {
        return finishRefresh(true);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshLayout
    public RefreshLayout finishLoadMore() {
        return finishLoadMore(true);
    }

    public RefreshLayout finishRefresh(int i) {
        return finishRefresh(i, true, Boolean.FALSE);
    }

    public RefreshLayout finishRefresh(boolean z) {
        if (z) {
            return finishRefresh(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.mLastOpenTime))), 300) << 16, true, Boolean.FALSE);
        }
        return finishRefresh(0, false, null);
    }

    public RefreshLayout finishRefresh(int i, final boolean z, final Boolean bool) {
        final int i2 = i >> 16;
        int i3 = (i << 16) >> 16;
        Runnable runnable = new Runnable() { // from class: com.scwang.smart.refresh.layout.SmartRefreshLayout.6
            int count = 0;

            @Override // java.lang.Runnable
            public void run() {
                int i4 = this.count;
                if (i4 == 0) {
                    SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                    RefreshState refreshState = smartRefreshLayout.mState;
                    RefreshState refreshState2 = RefreshState.None;
                    if (refreshState == refreshState2 && smartRefreshLayout.mViceState == RefreshState.Refreshing) {
                        smartRefreshLayout.mViceState = refreshState2;
                    } else {
                        ValueAnimator valueAnimator = smartRefreshLayout.reboundAnimator;
                        if (valueAnimator != null && refreshState.isHeader && (refreshState.isDragging || refreshState == RefreshState.RefreshReleased)) {
                            valueAnimator.setDuration(0L);
                            SmartRefreshLayout.this.reboundAnimator.cancel();
                            SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                            smartRefreshLayout2.reboundAnimator = null;
                            if (smartRefreshLayout2.mKernel.animSpinner(0) == null) {
                                SmartRefreshLayout.this.notifyStateChanged(refreshState2);
                            } else {
                                SmartRefreshLayout.this.notifyStateChanged(RefreshState.PullDownCanceled);
                            }
                        } else if (refreshState == RefreshState.Refreshing && smartRefreshLayout.mRefreshHeader != null && smartRefreshLayout.mRefreshContent != null) {
                            this.count = i4 + 1;
                            smartRefreshLayout.mHandler.postDelayed(this, i2);
                            SmartRefreshLayout.this.notifyStateChanged(RefreshState.RefreshFinish);
                            if (bool == Boolean.FALSE) {
                                SmartRefreshLayout.this.setNoMoreData(false);
                            }
                        }
                    }
                    if (bool == Boolean.TRUE) {
                        SmartRefreshLayout.this.setNoMoreData(true);
                        return;
                    }
                    return;
                }
                SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                int onFinish = smartRefreshLayout3.mRefreshHeader.onFinish(smartRefreshLayout3, z);
                SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                OnMultiListener onMultiListener = smartRefreshLayout4.mOnMultiListener;
                if (onMultiListener != null) {
                    RefreshComponent refreshComponent = smartRefreshLayout4.mRefreshHeader;
                    if (refreshComponent instanceof RefreshHeader) {
                        onMultiListener.onHeaderFinish((RefreshHeader) refreshComponent, z);
                    }
                }
                if (onFinish < Integer.MAX_VALUE) {
                    SmartRefreshLayout smartRefreshLayout5 = SmartRefreshLayout.this;
                    if (smartRefreshLayout5.mIsBeingDragged || smartRefreshLayout5.mNestedInProgress) {
                        long currentTimeMillis = System.currentTimeMillis();
                        SmartRefreshLayout smartRefreshLayout6 = SmartRefreshLayout.this;
                        if (smartRefreshLayout6.mIsBeingDragged) {
                            float f = smartRefreshLayout6.mLastTouchY;
                            smartRefreshLayout6.mTouchY = f;
                            smartRefreshLayout6.mTouchSpinner = 0;
                            smartRefreshLayout6.mIsBeingDragged = false;
                            SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(currentTimeMillis, currentTimeMillis, 0, smartRefreshLayout6.mLastTouchX, (f + smartRefreshLayout6.mSpinner) - (smartRefreshLayout6.mTouchSlop * 2), 0));
                            SmartRefreshLayout smartRefreshLayout7 = SmartRefreshLayout.this;
                            SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(currentTimeMillis, currentTimeMillis, 2, smartRefreshLayout7.mLastTouchX, smartRefreshLayout7.mLastTouchY + smartRefreshLayout7.mSpinner, 0));
                        }
                        SmartRefreshLayout smartRefreshLayout8 = SmartRefreshLayout.this;
                        if (smartRefreshLayout8.mNestedInProgress) {
                            smartRefreshLayout8.mTotalUnconsumed = 0;
                            SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(currentTimeMillis, currentTimeMillis, 1, smartRefreshLayout8.mLastTouchX, smartRefreshLayout8.mLastTouchY, 0));
                            SmartRefreshLayout smartRefreshLayout9 = SmartRefreshLayout.this;
                            smartRefreshLayout9.mNestedInProgress = false;
                            smartRefreshLayout9.mTouchSpinner = 0;
                        }
                    }
                    SmartRefreshLayout smartRefreshLayout10 = SmartRefreshLayout.this;
                    int i5 = smartRefreshLayout10.mSpinner;
                    if (i5 <= 0) {
                        if (i5 < 0) {
                            smartRefreshLayout10.animSpinner(0, onFinish, smartRefreshLayout10.mReboundInterpolator, smartRefreshLayout10.mReboundDuration);
                            return;
                        } else {
                            smartRefreshLayout10.mKernel.moveSpinner(0, false);
                            SmartRefreshLayout.this.mKernel.setState(RefreshState.None);
                            return;
                        }
                    }
                    ValueAnimator animSpinner = smartRefreshLayout10.animSpinner(0, onFinish, smartRefreshLayout10.mReboundInterpolator, smartRefreshLayout10.mReboundDuration);
                    SmartRefreshLayout smartRefreshLayout11 = SmartRefreshLayout.this;
                    ValueAnimator.AnimatorUpdateListener scrollContentWhenFinished = smartRefreshLayout11.mEnableScrollContentWhenRefreshed ? smartRefreshLayout11.mRefreshContent.scrollContentWhenFinished(smartRefreshLayout11.mSpinner) : null;
                    if (animSpinner == null || scrollContentWhenFinished == null) {
                        return;
                    }
                    animSpinner.addUpdateListener(scrollContentWhenFinished);
                }
            }
        };
        if (i3 > 0) {
            this.mHandler.postDelayed(runnable, i3);
        } else {
            runnable.run();
        }
        return this;
    }

    public RefreshLayout finishRefreshWithNoMoreData() {
        return finishRefresh(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.mLastOpenTime))), 300) << 16, true, Boolean.TRUE);
    }

    public RefreshLayout finishLoadMore(int i) {
        return finishLoadMore(i, true, false);
    }

    public RefreshLayout finishLoadMore(boolean z) {
        return finishLoadMore(z ? Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.mLastOpenTime))), 300) << 16 : 0, z, false);
    }

    /* renamed from: com.scwang.smart.refresh.layout.SmartRefreshLayout$7, reason: invalid class name */
    class AnonymousClass7 implements Runnable {
        int count = 0;
        final /* synthetic */ int val$more;
        final /* synthetic */ boolean val$noMoreData;
        final /* synthetic */ boolean val$success;

        AnonymousClass7(int i, boolean z, boolean z2) {
            this.val$more = i;
            this.val$noMoreData = z;
            this.val$success = z2;
        }

        /* JADX WARN: Code restructure failed: missing block: B:48:0x00b0, code lost:
        
            if (r6.mRefreshContent.canLoadMore() != false) goto L49;
         */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 329
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.AnonymousClass7.run():void");
        }
    }

    public RefreshLayout finishLoadMore(int i, boolean z, boolean z2) {
        int i2 = i >> 16;
        int i3 = (i << 16) >> 16;
        AnonymousClass7 anonymousClass7 = new AnonymousClass7(i2, z2, z);
        if (i3 > 0) {
            this.mHandler.postDelayed(anonymousClass7, i3);
        } else {
            anonymousClass7.run();
        }
        return this;
    }

    public RefreshLayout finishLoadMoreWithNoMoreData() {
        return finishLoadMore(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.mLastOpenTime))), 300) << 16, true, true);
    }

    public static void setDefaultRefreshHeaderCreator(DefaultRefreshHeaderCreator defaultRefreshHeaderCreator) {
        sHeaderCreator = defaultRefreshHeaderCreator;
    }

    public static void setDefaultRefreshFooterCreator(DefaultRefreshFooterCreator defaultRefreshFooterCreator) {
        sFooterCreator = defaultRefreshFooterCreator;
    }

    public static void setDefaultRefreshInitializer(DefaultRefreshInitializer defaultRefreshInitializer) {
        sRefreshInitializer = defaultRefreshInitializer;
    }

    public class RefreshKernelImpl implements RefreshKernel {
        public RefreshKernelImpl() {
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        public RefreshLayout getRefreshLayout() {
            return SmartRefreshLayout.this;
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        public RefreshKernel setState(RefreshState refreshState) {
            switch (AnonymousClass10.$SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[refreshState.ordinal()]) {
                case 1:
                    SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                    RefreshState refreshState2 = smartRefreshLayout.mState;
                    RefreshState refreshState3 = RefreshState.None;
                    if (refreshState2 != refreshState3 && smartRefreshLayout.mSpinner == 0) {
                        smartRefreshLayout.notifyStateChanged(refreshState3);
                        break;
                    } else if (smartRefreshLayout.mSpinner != 0) {
                        animSpinner(0);
                        break;
                    }
                    break;
                case 2:
                    SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout2.mState.isOpening && smartRefreshLayout2.isEnableRefreshOrLoadMore(smartRefreshLayout2.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.PullDownToRefresh);
                        break;
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.PullDownToRefresh);
                        break;
                    }
                    break;
                case 3:
                    SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                    if (smartRefreshLayout3.isEnableRefreshOrLoadMore(smartRefreshLayout3.mEnableLoadMore)) {
                        SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                        RefreshState refreshState4 = smartRefreshLayout4.mState;
                        if (!refreshState4.isOpening && !refreshState4.isFinishing && (!smartRefreshLayout4.mFooterNoMoreData || !smartRefreshLayout4.mEnableFooterFollowWhenNoMoreData || !smartRefreshLayout4.mFooterNoMoreDataEffective)) {
                            smartRefreshLayout4.notifyStateChanged(RefreshState.PullUpToLoad);
                            break;
                        }
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.PullUpToLoad);
                    break;
                case 4:
                    SmartRefreshLayout smartRefreshLayout5 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout5.mState.isOpening && smartRefreshLayout5.isEnableRefreshOrLoadMore(smartRefreshLayout5.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.PullDownCanceled);
                        setState(RefreshState.None);
                        break;
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.PullDownCanceled);
                        break;
                    }
                    break;
                case 5:
                    SmartRefreshLayout smartRefreshLayout6 = SmartRefreshLayout.this;
                    if (smartRefreshLayout6.isEnableRefreshOrLoadMore(smartRefreshLayout6.mEnableLoadMore)) {
                        SmartRefreshLayout smartRefreshLayout7 = SmartRefreshLayout.this;
                        if (!smartRefreshLayout7.mState.isOpening && (!smartRefreshLayout7.mFooterNoMoreData || !smartRefreshLayout7.mEnableFooterFollowWhenNoMoreData || !smartRefreshLayout7.mFooterNoMoreDataEffective)) {
                            smartRefreshLayout7.notifyStateChanged(RefreshState.PullUpCanceled);
                            setState(RefreshState.None);
                            break;
                        }
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.PullUpCanceled);
                    break;
                case 6:
                    SmartRefreshLayout smartRefreshLayout8 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout8.mState.isOpening && smartRefreshLayout8.isEnableRefreshOrLoadMore(smartRefreshLayout8.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.ReleaseToRefresh);
                        break;
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.ReleaseToRefresh);
                        break;
                    }
                case 7:
                    SmartRefreshLayout smartRefreshLayout9 = SmartRefreshLayout.this;
                    if (smartRefreshLayout9.isEnableRefreshOrLoadMore(smartRefreshLayout9.mEnableLoadMore)) {
                        SmartRefreshLayout smartRefreshLayout10 = SmartRefreshLayout.this;
                        RefreshState refreshState5 = smartRefreshLayout10.mState;
                        if (!refreshState5.isOpening && !refreshState5.isFinishing && (!smartRefreshLayout10.mFooterNoMoreData || !smartRefreshLayout10.mEnableFooterFollowWhenNoMoreData || !smartRefreshLayout10.mFooterNoMoreDataEffective)) {
                            smartRefreshLayout10.notifyStateChanged(RefreshState.ReleaseToLoad);
                            break;
                        }
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.ReleaseToLoad);
                    break;
                case 8:
                    SmartRefreshLayout smartRefreshLayout11 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout11.mState.isOpening && smartRefreshLayout11.isEnableRefreshOrLoadMore(smartRefreshLayout11.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.ReleaseToTwoLevel);
                        break;
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.ReleaseToTwoLevel);
                        break;
                    }
                    break;
                case 9:
                    SmartRefreshLayout smartRefreshLayout12 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout12.mState.isOpening && smartRefreshLayout12.isEnableRefreshOrLoadMore(smartRefreshLayout12.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.RefreshReleased);
                        break;
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.RefreshReleased);
                        break;
                    }
                    break;
                case 10:
                    SmartRefreshLayout smartRefreshLayout13 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout13.mState.isOpening && smartRefreshLayout13.isEnableRefreshOrLoadMore(smartRefreshLayout13.mEnableLoadMore)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.LoadReleased);
                        break;
                    } else {
                        SmartRefreshLayout.this.setViceState(RefreshState.LoadReleased);
                        break;
                    }
                    break;
                case 11:
                    SmartRefreshLayout.this.setStateRefreshing(true);
                    break;
                case 12:
                    SmartRefreshLayout.this.setStateLoading(true);
                    break;
                default:
                    SmartRefreshLayout.this.notifyStateChanged(refreshState);
                    break;
            }
            return null;
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        public RefreshKernel finishTwoLevel() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.mState == RefreshState.TwoLevel) {
                smartRefreshLayout.mKernel.setState(RefreshState.TwoLevelFinish);
                if (SmartRefreshLayout.this.mSpinner == 0) {
                    moveSpinner(0, false);
                    SmartRefreshLayout.this.notifyStateChanged(RefreshState.None);
                } else {
                    animSpinner(0).setDuration(SmartRefreshLayout.this.mFloorDuration);
                }
            }
            return this;
        }

        /* JADX WARN: Removed duplicated region for block: B:49:0x00a5  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x00ba  */
        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public com.scwang.smart.refresh.layout.api.RefreshKernel moveSpinner(int r19, boolean r20) {
            /*
                Method dump skipped, instructions count: 921
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.scwang.smart.refresh.layout.SmartRefreshLayout.RefreshKernelImpl.moveSpinner(int, boolean):com.scwang.smart.refresh.layout.api.RefreshKernel");
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        public ValueAnimator animSpinner(int i) {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            return smartRefreshLayout.animSpinner(i, 0, smartRefreshLayout.mReboundInterpolator, smartRefreshLayout.mReboundDuration);
        }

        @Override // com.scwang.smart.refresh.layout.api.RefreshKernel
        public RefreshKernel requestDrawBackgroundFor(RefreshComponent refreshComponent, int i) {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.mPaint == null && i != 0) {
                smartRefreshLayout.mPaint = new Paint();
            }
            if (refreshComponent.equals(SmartRefreshLayout.this.mRefreshHeader)) {
                SmartRefreshLayout.this.mHeaderBackgroundColor = i;
            } else if (refreshComponent.equals(SmartRefreshLayout.this.mRefreshFooter)) {
                SmartRefreshLayout.this.mFooterBackgroundColor = i;
            }
            return this;
        }
    }

    /* renamed from: com.scwang.smart.refresh.layout.SmartRefreshLayout$10, reason: invalid class name */
    static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState;

        static {
            int[] iArr = new int[RefreshState.values().length];
            $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState = iArr;
            try {
                iArr[RefreshState.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.PullDownToRefresh.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.PullUpToLoad.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.PullDownCanceled.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.PullUpCanceled.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.ReleaseToRefresh.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.ReleaseToLoad.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.ReleaseToTwoLevel.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.RefreshReleased.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.LoadReleased.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.Refreshing.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.Loading.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }
}

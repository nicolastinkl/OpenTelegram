package com.lxj.xpopup.core;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeImageTransform;
import androidx.transition.ChangeTransform;
import androidx.transition.Transition;
import androidx.transition.TransitionListenerAdapter;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.lxj.xpopup.R$id;
import com.lxj.xpopup.R$layout;
import com.lxj.xpopup.enums.PopupStatus;
import com.lxj.xpopup.interfaces.OnDragChangeListener;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxj.xpopup.photoview.PhotoView;
import com.lxj.xpopup.util.XPermission;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.BlankView;
import com.lxj.xpopup.widget.HackyViewPager;
import com.lxj.xpopup.widget.PhotoViewContainer;
import com.tencent.bugly.BuglyStrategy;
import java.util.List;

/* loaded from: classes.dex */
public class ImageViewerPopupView extends BasePopupView implements OnDragChangeListener, View.OnClickListener {
    protected ArgbEvaluator argbEvaluator;
    protected int bgColor;
    protected FrameLayout container;
    protected View customView;
    protected XPopupImageLoader imageLoader;
    protected boolean isInfinite;
    protected boolean isShowIndicator;
    protected boolean isShowPlaceholder;
    protected boolean isShowSaveBtn;
    protected HackyViewPager pager;
    protected PhotoViewContainer photoViewContainer;
    protected int placeholderColor;
    protected int placeholderRadius;
    protected int placeholderStrokeColor;
    protected BlankView placeholderView;
    protected int position;
    protected Rect rect;
    protected PhotoView snapshotView;
    protected ImageView srcView;
    protected OnSrcViewUpdateListener srcViewUpdateListener;
    protected TextView tv_pager_indicator;
    protected TextView tv_save;
    protected List<Object> urls;

    @Override // com.lxj.xpopup.core.BasePopupView
    protected final int getInnerLayoutId() {
        return R$layout._xpopup_image_viewer_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        this.tv_pager_indicator = (TextView) findViewById(R$id.tv_pager_indicator);
        this.tv_save = (TextView) findViewById(R$id.tv_save);
        this.placeholderView = (BlankView) findViewById(R$id.placeholderView);
        PhotoViewContainer photoViewContainer = (PhotoViewContainer) findViewById(R$id.photoViewContainer);
        this.photoViewContainer = photoViewContainer;
        photoViewContainer.setOnDragChangeListener(this);
        this.pager = (HackyViewPager) findViewById(R$id.pager);
        PhotoViewAdapter photoViewAdapter = new PhotoViewAdapter();
        this.pager.setAdapter(photoViewAdapter);
        this.pager.setCurrentItem(this.position);
        this.pager.setVisibility(4);
        addOrUpdateSnapshot();
        this.pager.setOffscreenPageLimit(2);
        this.pager.addOnPageChangeListener(photoViewAdapter);
        if (!this.isShowIndicator) {
            this.tv_pager_indicator.setVisibility(8);
        }
        if (!this.isShowSaveBtn) {
            this.tv_save.setVisibility(8);
        } else {
            this.tv_save.setOnClickListener(this);
        }
    }

    private void setupPlaceholder() {
        this.placeholderView.setVisibility(this.isShowPlaceholder ? 0 : 4);
        if (this.isShowPlaceholder) {
            int i = this.placeholderColor;
            if (i != -1) {
                this.placeholderView.color = i;
            }
            int i2 = this.placeholderRadius;
            if (i2 != -1) {
                this.placeholderView.radius = i2;
            }
            int i3 = this.placeholderStrokeColor;
            if (i3 != -1) {
                this.placeholderView.strokeColor = i3;
            }
            XPopupUtils.setWidthHeight(this.placeholderView, this.rect.width(), this.rect.height());
            this.placeholderView.setTranslationX(this.rect.left);
            this.placeholderView.setTranslationY(this.rect.top);
            this.placeholderView.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPagerIndicator() {
        if (this.urls.size() > 1) {
            int realPosition = getRealPosition();
            this.tv_pager_indicator.setText((realPosition + 1) + "/" + this.urls.size());
        }
        if (this.isShowSaveBtn) {
            this.tv_save.setVisibility(0);
        }
    }

    private void addOrUpdateSnapshot() {
        if (this.srcView == null) {
            return;
        }
        if (this.snapshotView == null) {
            PhotoView photoView = new PhotoView(getContext());
            this.snapshotView = photoView;
            photoView.setEnabled(false);
            this.photoViewContainer.addView(this.snapshotView);
            this.snapshotView.setScaleType(this.srcView.getScaleType());
            this.snapshotView.setTranslationX(this.rect.left);
            this.snapshotView.setTranslationY(this.rect.top);
            XPopupUtils.setWidthHeight(this.snapshotView, this.rect.width(), this.rect.height());
        }
        int realPosition = getRealPosition();
        this.snapshotView.setTag(Integer.valueOf(realPosition));
        setupPlaceholder();
        XPopupImageLoader xPopupImageLoader = this.imageLoader;
        if (xPopupImageLoader != null) {
            xPopupImageLoader.loadSnapshot(this.urls.get(realPosition), this.snapshotView, this.srcView);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doShowAnimation() {
        if (this.srcView == null) {
            this.photoViewContainer.setBackgroundColor(this.bgColor);
            this.pager.setVisibility(0);
            showPagerIndicator();
            this.photoViewContainer.isReleasing = false;
            doAfterShow();
            View view = this.customView;
            if (view != null) {
                view.setAlpha(1.0f);
                this.customView.setVisibility(0);
                return;
            }
            return;
        }
        this.photoViewContainer.isReleasing = true;
        View view2 = this.customView;
        if (view2 != null) {
            view2.setVisibility(0);
        }
        this.snapshotView.setVisibility(0);
        doAfterShow();
        this.snapshotView.post(new Runnable() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                TransitionManager.beginDelayedTransition((ViewGroup) ImageViewerPopupView.this.snapshotView.getParent(), new TransitionSet().setDuration(ImageViewerPopupView.this.getAnimationDuration()).addTransition(new ChangeBounds()).addTransition(new ChangeTransform()).addTransition(new ChangeImageTransform()).setInterpolator((TimeInterpolator) new FastOutSlowInInterpolator()).addListener((Transition.TransitionListener) new TransitionListenerAdapter() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.1.1
                    @Override // androidx.transition.Transition.TransitionListener
                    public void onTransitionEnd(Transition transition) {
                        ImageViewerPopupView.this.pager.setVisibility(0);
                        ImageViewerPopupView.this.snapshotView.setVisibility(4);
                        ImageViewerPopupView.this.showPagerIndicator();
                        ImageViewerPopupView.this.photoViewContainer.isReleasing = false;
                    }
                }));
                ImageViewerPopupView.this.snapshotView.setTranslationY(0.0f);
                ImageViewerPopupView.this.snapshotView.setTranslationX(0.0f);
                ImageViewerPopupView.this.snapshotView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
                XPopupUtils.setWidthHeight(imageViewerPopupView.snapshotView, imageViewerPopupView.photoViewContainer.getWidth(), ImageViewerPopupView.this.photoViewContainer.getHeight());
                ImageViewerPopupView imageViewerPopupView2 = ImageViewerPopupView.this;
                imageViewerPopupView2.animateShadowBg(imageViewerPopupView2.bgColor);
                View view3 = ImageViewerPopupView.this.customView;
                if (view3 != null) {
                    view3.animate().alpha(1.0f).setDuration(ImageViewerPopupView.this.getAnimationDuration()).start();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateShadowBg(final int endColor) {
        final int color = ((ColorDrawable) this.photoViewContainer.getBackground()).getColor();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
                imageViewerPopupView.photoViewContainer.setBackgroundColor(((Integer) imageViewerPopupView.argbEvaluator.evaluate(animation.getAnimatedFraction(), Integer.valueOf(color), Integer.valueOf(endColor))).intValue());
            }
        });
        ofFloat.setDuration(getAnimationDuration()).setInterpolator(new LinearInterpolator());
        ofFloat.start();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doDismissAnimation() {
        if (this.srcView == null) {
            this.photoViewContainer.setBackgroundColor(0);
            doAfterDismiss();
            this.pager.setVisibility(4);
            this.placeholderView.setVisibility(4);
            View view = this.customView;
            if (view != null) {
                view.setAlpha(0.0f);
                this.customView.setVisibility(4);
                return;
            }
            return;
        }
        this.tv_pager_indicator.setVisibility(4);
        this.tv_save.setVisibility(4);
        this.pager.setVisibility(4);
        this.photoViewContainer.isReleasing = true;
        this.snapshotView.setVisibility(0);
        this.snapshotView.post(new Runnable() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.3
            @Override // java.lang.Runnable
            public void run() {
                TransitionManager.beginDelayedTransition((ViewGroup) ImageViewerPopupView.this.snapshotView.getParent(), new TransitionSet().setDuration(ImageViewerPopupView.this.getAnimationDuration()).addTransition(new ChangeBounds()).addTransition(new ChangeTransform()).addTransition(new ChangeImageTransform()).setInterpolator((TimeInterpolator) new FastOutSlowInInterpolator()).addListener((Transition.TransitionListener) new TransitionListenerAdapter() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.3.1
                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public void onTransitionStart(Transition transition) {
                        super.onTransitionStart(transition);
                        ImageViewerPopupView.this.doAfterDismiss();
                    }

                    @Override // androidx.transition.Transition.TransitionListener
                    public void onTransitionEnd(Transition transition) {
                        ImageViewerPopupView.this.pager.setScaleX(1.0f);
                        ImageViewerPopupView.this.pager.setScaleY(1.0f);
                        ImageViewerPopupView.this.snapshotView.setScaleX(1.0f);
                        ImageViewerPopupView.this.snapshotView.setScaleY(1.0f);
                        ImageViewerPopupView.this.placeholderView.setVisibility(4);
                        ImageViewerPopupView.this.snapshotView.setTranslationX(r3.rect.left);
                        ImageViewerPopupView.this.snapshotView.setTranslationY(r3.rect.top);
                        ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
                        XPopupUtils.setWidthHeight(imageViewerPopupView.snapshotView, imageViewerPopupView.rect.width(), ImageViewerPopupView.this.rect.height());
                    }
                }));
                ImageViewerPopupView.this.snapshotView.setScaleX(1.0f);
                ImageViewerPopupView.this.snapshotView.setScaleY(1.0f);
                ImageViewerPopupView.this.snapshotView.setTranslationX(r0.rect.left);
                ImageViewerPopupView.this.snapshotView.setTranslationY(r0.rect.top);
                ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
                imageViewerPopupView.snapshotView.setScaleType(imageViewerPopupView.srcView.getScaleType());
                ImageViewerPopupView imageViewerPopupView2 = ImageViewerPopupView.this;
                XPopupUtils.setWidthHeight(imageViewerPopupView2.snapshotView, imageViewerPopupView2.rect.width(), ImageViewerPopupView.this.rect.height());
                ImageViewerPopupView.this.animateShadowBg(0);
                View view2 = ImageViewerPopupView.this.customView;
                if (view2 != null) {
                    view2.animate().alpha(0.0f).setDuration(ImageViewerPopupView.this.getAnimationDuration()).setListener(new AnimatorListenerAdapter() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.3.2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            View view3 = ImageViewerPopupView.this.customView;
                            if (view3 != null) {
                                view3.setVisibility(4);
                            }
                        }
                    }).start();
                }
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        if (this.popupStatus != PopupStatus.Show) {
            return;
        }
        this.popupStatus = PopupStatus.Dismissing;
        doDismissAnimation();
    }

    @Override // com.lxj.xpopup.interfaces.OnDragChangeListener
    public void onRelease() {
        dismiss();
    }

    @Override // com.lxj.xpopup.interfaces.OnDragChangeListener
    public void onDragChange(int dy, float scale, float fraction) {
        float f = 1.0f - fraction;
        this.tv_pager_indicator.setAlpha(f);
        View view = this.customView;
        if (view != null) {
            view.setAlpha(f);
        }
        if (this.isShowSaveBtn) {
            this.tv_save.setAlpha(f);
        }
        this.photoViewContainer.setBackgroundColor(((Integer) this.argbEvaluator.evaluate(fraction * 0.8f, Integer.valueOf(this.bgColor), 0)).intValue());
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onDismiss() {
        super.onDismiss();
        this.srcView = null;
        this.srcViewUpdateListener = null;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v == this.tv_save) {
            save();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void destroy() {
        super.destroy();
        HackyViewPager hackyViewPager = this.pager;
        hackyViewPager.removeOnPageChangeListener((PhotoViewAdapter) hackyViewPager.getAdapter());
        this.imageLoader = null;
    }

    protected int getRealPosition() {
        return this.isInfinite ? this.position % this.urls.size() : this.position;
    }

    protected void save() {
        XPermission.create(getContext(), "STORAGE").callback(new XPermission.SimpleCallback() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.4
            @Override // com.lxj.xpopup.util.XPermission.SimpleCallback
            public void onDenied() {
            }

            @Override // com.lxj.xpopup.util.XPermission.SimpleCallback
            public void onGranted() {
                Context context = ImageViewerPopupView.this.getContext();
                ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
                XPopupUtils.saveBmpToAlbum(context, imageViewerPopupView.imageLoader, imageViewerPopupView.urls.get(imageViewerPopupView.getRealPosition()));
            }
        }).request();
    }

    public class PhotoViewAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public PhotoViewAdapter() {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
            return imageViewerPopupView.isInfinite ? BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH : imageViewerPopupView.urls.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
            if (imageViewerPopupView.isInfinite) {
                position %= imageViewerPopupView.urls.size();
            }
            int i = position;
            FrameLayout buildContainer = buildContainer(container.getContext());
            ProgressBar buildProgressBar = buildProgressBar(container.getContext());
            ImageViewerPopupView imageViewerPopupView2 = ImageViewerPopupView.this;
            XPopupImageLoader xPopupImageLoader = imageViewerPopupView2.imageLoader;
            Object obj = imageViewerPopupView2.urls.get(i);
            ImageViewerPopupView imageViewerPopupView3 = ImageViewerPopupView.this;
            buildContainer.addView(xPopupImageLoader.loadImage(i, obj, imageViewerPopupView3, imageViewerPopupView3.snapshotView, buildProgressBar), new FrameLayout.LayoutParams(-1, -1));
            buildContainer.addView(buildProgressBar);
            container.addView(buildContainer);
            return buildContainer;
        }

        private FrameLayout buildContainer(Context context) {
            FrameLayout frameLayout = new FrameLayout(context);
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            return frameLayout;
        }

        private ProgressBar buildProgressBar(Context context) {
            ProgressBar progressBar = new ProgressBar(context);
            progressBar.setIndeterminate(true);
            int dp2px = XPopupUtils.dp2px(ImageViewerPopupView.this.container.getContext(), 40.0f);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dp2px, dp2px);
            layoutParams.gravity = 17;
            progressBar.setLayoutParams(layoutParams);
            progressBar.setVisibility(8);
            return progressBar;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
            imageViewerPopupView.position = i;
            imageViewerPopupView.showPagerIndicator();
            ImageViewerPopupView imageViewerPopupView2 = ImageViewerPopupView.this;
            OnSrcViewUpdateListener onSrcViewUpdateListener = imageViewerPopupView2.srcViewUpdateListener;
            if (onSrcViewUpdateListener != null) {
                onSrcViewUpdateListener.onSrcViewUpdate(imageViewerPopupView2, imageViewerPopupView2.getRealPosition());
            }
        }
    }
}

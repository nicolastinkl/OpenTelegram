package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.List;

/* loaded from: classes.dex */
interface MotionStrategy {
    AnimatorSet createAnimator();

    List<Animator.AnimatorListener> getListeners();

    MotionSpec getMotionSpec();

    void onAnimationCancel();

    void onAnimationEnd();

    void onAnimationStart(Animator animator);

    void onChange(ExtendedFloatingActionButton.OnChangedCallback onChangedCallback);

    void performNow();

    void setMotionSpec(MotionSpec motionSpec);

    boolean shouldCancel();
}

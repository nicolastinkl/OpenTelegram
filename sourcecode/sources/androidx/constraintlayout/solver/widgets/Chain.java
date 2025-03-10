package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;

/* loaded from: classes.dex */
class Chain {
    static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, int i) {
        int i2;
        int i3;
        ChainHead[] chainHeadArr;
        if (i == 0) {
            int i4 = constraintWidgetContainer.mHorizontalChainsSize;
            chainHeadArr = constraintWidgetContainer.mHorizontalChainsArray;
            i3 = i4;
            i2 = 0;
        } else {
            i2 = 2;
            i3 = constraintWidgetContainer.mVerticalChainsSize;
            chainHeadArr = constraintWidgetContainer.mVerticalChainsArray;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            ChainHead chainHead = chainHeadArr[i5];
            chainHead.define();
            applyChainConstraints(constraintWidgetContainer, linearSystem, i, i2, chainHead);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x002d, code lost:
    
        if (r8 == 2) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0040, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:309:0x003e, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:317:0x003c, code lost:
    
        if (r8 == 2) goto L25;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0258 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x04cd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x04e1  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x04ea  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x04f1  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0501  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0507 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:158:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x04ed  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x04e4  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x02b1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x039b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:234:0x03a3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:242:0x03b6  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0485  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x04ba  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01d1  */
    /* JADX WARN: Type inference failed for: r2v56, types: [androidx.constraintlayout.solver.widgets.ConstraintWidget] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v2, types: [androidx.constraintlayout.solver.widgets.ConstraintWidget] */
    /* JADX WARN: Type inference failed for: r7v32 */
    /* JADX WARN: Type inference failed for: r7v33 */
    /* JADX WARN: Type inference failed for: r7v34 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static void applyChainConstraints(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r37, androidx.constraintlayout.solver.LinearSystem r38, int r39, int r40, androidx.constraintlayout.solver.widgets.ChainHead r41) {
        /*
            Method dump skipped, instructions count: 1320
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Chain.applyChainConstraints(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer, androidx.constraintlayout.solver.LinearSystem, int, int, androidx.constraintlayout.solver.widgets.ChainHead):void");
    }
}

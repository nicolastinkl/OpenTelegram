package org.telegram.ui.Charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.Charts.data.ChartData;
import org.telegram.ui.Charts.data.StackLinearChartData;
import org.telegram.ui.Charts.view_data.StackLinearViewData;

/* loaded from: classes4.dex */
public class StackLinearChartView<T extends StackLinearViewData> extends BaseChartView<StackLinearChartData, T> {
    private float[] mapPoints;
    private Matrix matrix;
    Path ovalPath;
    boolean[] skipPoints;
    float[] startFromY;

    @Override // org.telegram.ui.Charts.BaseChartView
    public int findMaxValue(int i, int i2) {
        return 100;
    }

    @Override // org.telegram.ui.Charts.BaseChartView
    protected float getMinDistance() {
        return 0.1f;
    }

    public StackLinearChartView(Context context) {
        super(context);
        this.matrix = new Matrix();
        this.mapPoints = new float[2];
        this.ovalPath = new Path();
        this.superDraw = true;
        this.useAlphaSignature = true;
        this.drawPointOnSelection = false;
    }

    @Override // org.telegram.ui.Charts.BaseChartView
    public T createLineViewData(ChartData.Line line) {
        return (T) new StackLinearViewData(line);
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x046f  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x043f  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0459  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0463  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0455  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x03fb  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03f3  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x024c  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0218 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0259 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x03f9  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x03ff A[ADDED_TO_REGION] */
    @Override // org.telegram.ui.Charts.BaseChartView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void drawChart(android.graphics.Canvas r38) {
        /*
            Method dump skipped, instructions count: 1575
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Charts.StackLinearChartView.drawChart(android.graphics.Canvas):void");
    }

    private int quarterForPoint(float f, float f2) {
        float centerX = this.chartArea.centerX();
        float centerY = this.chartArea.centerY() + AndroidUtilities.dp(16.0f);
        if (f >= centerX && f2 <= centerY) {
            return 0;
        }
        if (f < centerX || f2 < centerY) {
            return (f >= centerX || f2 < centerY) ? 3 : 2;
        }
        return 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00e4 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0112 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0102  */
    @Override // org.telegram.ui.Charts.BaseChartView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void drawPickerChart(android.graphics.Canvas r20) {
        /*
            Method dump skipped, instructions count: 420
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Charts.StackLinearChartView.drawPickerChart(android.graphics.Canvas):void");
    }

    @Override // org.telegram.ui.Charts.BaseChartView, android.view.View
    protected void onDraw(Canvas canvas) {
        tick();
        drawChart(canvas);
        drawBottomLine(canvas);
        this.tmpN = this.horizontalLines.size();
        int i = 0;
        while (true) {
            this.tmpI = i;
            int i2 = this.tmpI;
            if (i2 < this.tmpN) {
                drawHorizontalLines(canvas, this.horizontalLines.get(i2));
                drawSignaturesToHorizontalLines(canvas, this.horizontalLines.get(this.tmpI));
                i = this.tmpI + 1;
            } else {
                drawBottomSignature(canvas);
                drawPicker(canvas);
                drawSelection(canvas);
                super.onDraw(canvas);
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0140  */
    @Override // org.telegram.ui.Charts.BaseChartView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void fillTransitionParams(org.telegram.ui.Charts.view_data.TransitionParams r18) {
        /*
            Method dump skipped, instructions count: 344
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Charts.StackLinearChartView.fillTransitionParams(org.telegram.ui.Charts.view_data.TransitionParams):void");
    }
}

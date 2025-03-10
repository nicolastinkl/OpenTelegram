package org.telegram.ui.Charts.data;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class DoubleLinearChartData extends ChartData {
    public float[] linesK;

    public DoubleLinearChartData(JSONObject jSONObject) throws JSONException {
        super(jSONObject);
    }

    @Override // org.telegram.ui.Charts.data.ChartData
    protected void measure() {
        super.measure();
        int size = this.lines.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = this.lines.get(i2).maxValue;
            if (i3 > i) {
                i = i3;
            }
        }
        this.linesK = new float[size];
        for (int i4 = 0; i4 < size; i4++) {
            if (i == this.lines.get(i4).maxValue) {
                this.linesK[i4] = 1.0f;
            } else {
                this.linesK[i4] = i / r2;
            }
        }
    }
}

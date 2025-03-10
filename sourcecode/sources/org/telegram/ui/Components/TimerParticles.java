package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.SystemClock;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Utilities;

/* loaded from: classes4.dex */
public class TimerParticles {
    private long lastAnimationTime;
    private ArrayList<Particle> particles = new ArrayList<>();
    private ArrayList<Particle> freeParticles = new ArrayList<>();

    private static class Particle {
        float alpha;
        float currentTime;
        float lifeTime;
        float velocity;
        float vx;
        float vy;
        float x;
        float y;

        private Particle() {
        }
    }

    public TimerParticles() {
        for (int i = 0; i < 40; i++) {
            this.freeParticles.add(new Particle());
        }
    }

    private void updateParticles(long j) {
        int size = this.particles.size();
        int i = 0;
        while (i < size) {
            Particle particle = this.particles.get(i);
            float f = particle.currentTime;
            float f2 = particle.lifeTime;
            if (f >= f2) {
                if (this.freeParticles.size() < 40) {
                    this.freeParticles.add(particle);
                }
                this.particles.remove(i);
                i--;
                size--;
            } else {
                particle.alpha = 1.0f - AndroidUtilities.decelerateInterpolator.getInterpolation(f / f2);
                float f3 = particle.x;
                float f4 = particle.vx;
                float f5 = particle.velocity;
                float f6 = j;
                particle.x = f3 + (((f4 * f5) * f6) / 500.0f);
                particle.y += ((particle.vy * f5) * f6) / 500.0f;
                particle.currentTime += f6;
            }
            i++;
        }
    }

    public void draw(Canvas canvas, Paint paint, RectF rectF, float f, float f2) {
        Particle particle;
        int size = this.particles.size();
        for (int i = 0; i < size; i++) {
            Particle particle2 = this.particles.get(i);
            paint.setAlpha((int) (particle2.alpha * 255.0f * f2));
            canvas.drawPoint(particle2.x, particle2.y, paint);
        }
        double d = (f - 90.0f) * 0.017453292519943295d;
        double sin = Math.sin(d);
        double d2 = -Math.cos(d);
        double width = rectF.width() / 2.0f;
        float centerX = (float) (((-d2) * width) + rectF.centerX());
        float centerY = (float) ((width * sin) + rectF.centerY());
        for (int i2 = 0; i2 < 1; i2++) {
            if (!this.freeParticles.isEmpty()) {
                particle = this.freeParticles.get(0);
                this.freeParticles.remove(0);
            } else {
                particle = new Particle();
            }
            particle.x = centerX;
            particle.y = centerY;
            double nextInt = (Utilities.random.nextInt(140) - 70) * 0.017453292519943295d;
            if (nextInt < 0.0d) {
                nextInt += 6.283185307179586d;
            }
            particle.vx = (float) ((Math.cos(nextInt) * sin) - (Math.sin(nextInt) * d2));
            particle.vy = (float) ((Math.sin(nextInt) * sin) + (Math.cos(nextInt) * d2));
            particle.alpha = 1.0f;
            particle.currentTime = 0.0f;
            particle.lifeTime = Utilities.random.nextInt(100) + 400;
            particle.velocity = (Utilities.random.nextFloat() * 4.0f) + 20.0f;
            this.particles.add(particle);
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        updateParticles(Math.min(20L, elapsedRealtime - this.lastAnimationTime));
        this.lastAnimationTime = elapsedRealtime;
    }
}

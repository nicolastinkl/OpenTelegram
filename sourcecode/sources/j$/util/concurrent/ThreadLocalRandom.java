package j$.util.concurrent;

import j$.util.AbstractC0112a;
import j$.util.function.Consumer;
import j$.util.function.f;
import j$.util.function.m;
import j$.util.function.s;
import j$.util.r;
import j$.util.stream.AbstractC0238t1;
import j$.wrappers.C$r8$wrapper$java$util$stream$IntStream$WRP;
import j$.wrappers.J0;
import j$.wrappers.L0;
import java.io.ObjectStreamField;
import java.security.AccessController;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/* loaded from: classes2.dex */
public class ThreadLocalRandom extends Random {
    private static final AtomicInteger d = new AtomicInteger();
    private static final AtomicLong e;
    private static final ThreadLocal f;
    private static final ThreadLocal g;
    long a;
    int b;
    boolean c = true;

    class a extends ThreadLocal<ThreadLocalRandom> {
        a() {
        }

        @Override // java.lang.ThreadLocal
        protected ThreadLocalRandom initialValue() {
            return new ThreadLocalRandom(null);
        }
    }

    static final class b implements r.a {
        long a;
        final long b;
        final double c;
        final double d;

        b(long j, long j2, double d, double d2) {
            this.a = j;
            this.b = j2;
            this.c = d;
            this.d = d2;
        }

        @Override // j$.util.s, j$.util.r
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public b trySplit() {
            long j = this.a;
            long j2 = (this.b + j) >>> 1;
            if (j2 <= j) {
                return null;
            }
            this.a = j2;
            return new b(j, j2, this.c, this.d);
        }

        @Override // j$.util.r.a, j$.util.r
        public /* synthetic */ boolean b(Consumer consumer) {
            return AbstractC0112a.j(this, consumer);
        }

        @Override // j$.util.r
        public int characteristics() {
            return 17728;
        }

        @Override // j$.util.s
        public void e(f fVar) {
            Objects.requireNonNull(fVar);
            long j = this.a;
            long j2 = this.b;
            if (j < j2) {
                this.a = j2;
                double d = this.c;
                double d2 = this.d;
                ThreadLocalRandom current = ThreadLocalRandom.current();
                do {
                    fVar.accept(current.c(d, d2));
                    j++;
                } while (j < j2);
            }
        }

        @Override // j$.util.r
        public long estimateSize() {
            return this.b - this.a;
        }

        @Override // j$.util.r.a, j$.util.r
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            AbstractC0112a.b(this, consumer);
        }

        @Override // j$.util.r
        public Comparator getComparator() {
            throw new IllegalStateException();
        }

        @Override // j$.util.r
        public /* synthetic */ long getExactSizeIfKnown() {
            return AbstractC0112a.e(this);
        }

        @Override // j$.util.r
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return AbstractC0112a.f(this, i);
        }

        @Override // j$.util.s
        public boolean k(f fVar) {
            Objects.requireNonNull(fVar);
            long j = this.a;
            if (j >= this.b) {
                return false;
            }
            fVar.accept(ThreadLocalRandom.current().c(this.c, this.d));
            this.a = j + 1;
            return true;
        }
    }

    static final class c implements r.b {
        long a;
        final long b;
        final int c;
        final int d;

        c(long j, long j2, int i, int i2) {
            this.a = j;
            this.b = j2;
            this.c = i;
            this.d = i2;
        }

        @Override // j$.util.s, j$.util.r
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public c trySplit() {
            long j = this.a;
            long j2 = (this.b + j) >>> 1;
            if (j2 <= j) {
                return null;
            }
            this.a = j2;
            return new c(j, j2, this.c, this.d);
        }

        @Override // j$.util.r.b, j$.util.r
        public /* synthetic */ boolean b(Consumer consumer) {
            return AbstractC0112a.k(this, consumer);
        }

        @Override // j$.util.s
        /* renamed from: c, reason: merged with bridge method [inline-methods] */
        public void e(m mVar) {
            Objects.requireNonNull(mVar);
            long j = this.a;
            long j2 = this.b;
            if (j < j2) {
                this.a = j2;
                int i = this.c;
                int i2 = this.d;
                ThreadLocalRandom current = ThreadLocalRandom.current();
                do {
                    mVar.accept(current.d(i, i2));
                    j++;
                } while (j < j2);
            }
        }

        @Override // j$.util.r
        public int characteristics() {
            return 17728;
        }

        @Override // j$.util.r
        public long estimateSize() {
            return this.b - this.a;
        }

        @Override // j$.util.r.b, j$.util.r
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            AbstractC0112a.c(this, consumer);
        }

        @Override // j$.util.s
        /* renamed from: g, reason: merged with bridge method [inline-methods] */
        public boolean k(m mVar) {
            Objects.requireNonNull(mVar);
            long j = this.a;
            if (j >= this.b) {
                return false;
            }
            mVar.accept(ThreadLocalRandom.current().d(this.c, this.d));
            this.a = j + 1;
            return true;
        }

        @Override // j$.util.r
        public Comparator getComparator() {
            throw new IllegalStateException();
        }

        @Override // j$.util.r
        public /* synthetic */ long getExactSizeIfKnown() {
            return AbstractC0112a.e(this);
        }

        @Override // j$.util.r
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return AbstractC0112a.f(this, i);
        }
    }

    static final class d implements r.c {
        long a;
        final long b;
        final long c;
        final long d;

        d(long j, long j2, long j3, long j4) {
            this.a = j;
            this.b = j2;
            this.c = j3;
            this.d = j4;
        }

        @Override // j$.util.s, j$.util.r
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public d trySplit() {
            long j = this.a;
            long j2 = (this.b + j) >>> 1;
            if (j2 <= j) {
                return null;
            }
            this.a = j2;
            return new d(j, j2, this.c, this.d);
        }

        @Override // j$.util.r.c, j$.util.r
        public /* synthetic */ boolean b(Consumer consumer) {
            return AbstractC0112a.l(this, consumer);
        }

        @Override // j$.util.r
        public int characteristics() {
            return 17728;
        }

        @Override // j$.util.s
        /* renamed from: d, reason: merged with bridge method [inline-methods] */
        public void e(s sVar) {
            Objects.requireNonNull(sVar);
            long j = this.a;
            long j2 = this.b;
            if (j < j2) {
                this.a = j2;
                long j3 = this.c;
                long j4 = this.d;
                ThreadLocalRandom current = ThreadLocalRandom.current();
                do {
                    sVar.accept(current.e(j3, j4));
                    j++;
                } while (j < j2);
            }
        }

        @Override // j$.util.r
        public long estimateSize() {
            return this.b - this.a;
        }

        @Override // j$.util.r.c, j$.util.r
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            AbstractC0112a.d(this, consumer);
        }

        @Override // j$.util.r
        public Comparator getComparator() {
            throw new IllegalStateException();
        }

        @Override // j$.util.r
        public /* synthetic */ long getExactSizeIfKnown() {
            return AbstractC0112a.e(this);
        }

        @Override // j$.util.r
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return AbstractC0112a.f(this, i);
        }

        @Override // j$.util.s
        /* renamed from: i, reason: merged with bridge method [inline-methods] */
        public boolean k(s sVar) {
            Objects.requireNonNull(sVar);
            long j = this.a;
            if (j >= this.b) {
                return false;
            }
            sVar.accept(ThreadLocalRandom.current().e(this.c, this.d));
            this.a = j + 1;
            return true;
        }
    }

    static {
        long h;
        if (((Boolean) AccessController.doPrivileged(new j$.util.concurrent.d())).booleanValue()) {
            byte[] seed = SecureRandom.getSeed(8);
            h = seed[0] & 255;
            for (int i = 1; i < 8; i++) {
                h = (h << 8) | (seed[i] & 255);
            }
        } else {
            h = h(System.nanoTime()) ^ h(System.currentTimeMillis());
        }
        e = new AtomicLong(h);
        f = new ThreadLocal();
        g = new a();
        new ObjectStreamField("rnd", Long.TYPE);
        new ObjectStreamField("initialized", Boolean.TYPE);
    }

    ThreadLocalRandom(j$.util.concurrent.d dVar) {
    }

    static final int a(int i) {
        int i2 = i ^ (i << 13);
        int i3 = i2 ^ (i2 >>> 17);
        int i4 = i3 ^ (i3 << 5);
        ((ThreadLocalRandom) g.get()).b = i4;
        return i4;
    }

    static final int b() {
        return ((ThreadLocalRandom) g.get()).b;
    }

    public static ThreadLocalRandom current() {
        ThreadLocalRandom threadLocalRandom = (ThreadLocalRandom) g.get();
        if (threadLocalRandom.b == 0) {
            f();
        }
        return threadLocalRandom;
    }

    static final void f() {
        int addAndGet = d.addAndGet(-1640531527);
        if (addAndGet == 0) {
            addAndGet = 1;
        }
        long h = h(e.getAndAdd(-4942790177534073029L));
        ThreadLocalRandom threadLocalRandom = (ThreadLocalRandom) g.get();
        threadLocalRandom.a = h;
        threadLocalRandom.b = addAndGet;
    }

    private static int g(long j) {
        long j2 = (j ^ (j >>> 33)) * (-49064778989728563L);
        return (int) (((j2 ^ (j2 >>> 33)) * (-4265267296055464877L)) >>> 32);
    }

    private static long h(long j) {
        long j2 = (j ^ (j >>> 33)) * (-49064778989728563L);
        long j3 = (j2 ^ (j2 >>> 33)) * (-4265267296055464877L);
        return j3 ^ (j3 >>> 33);
    }

    final double c(double d2, double d3) {
        double nextLong = (nextLong() >>> 11) * 1.1102230246251565E-16d;
        if (d2 >= d3) {
            return nextLong;
        }
        double d4 = ((d3 - d2) * nextLong) + d2;
        return d4 >= d3 ? Double.longBitsToDouble(Double.doubleToLongBits(d3) - 1) : d4;
    }

    final int d(int i, int i2) {
        int i3;
        int g2 = g(i());
        if (i >= i2) {
            return g2;
        }
        int i4 = i2 - i;
        int i5 = i4 - 1;
        if ((i4 & i5) == 0) {
            i3 = g2 & i5;
        } else if (i4 > 0) {
            int i6 = g2 >>> 1;
            while (true) {
                int i7 = i6 + i5;
                i3 = i6 % i4;
                if (i7 - i3 >= 0) {
                    break;
                }
                i6 = g(i()) >>> 1;
            }
        } else {
            while (true) {
                if (g2 >= i && g2 < i2) {
                    return g2;
                }
                g2 = g(i());
            }
        }
        return i3 + i;
    }

    @Override // java.util.Random
    public DoubleStream doubles() {
        return J0.k0(AbstractC0238t1.r(new b(0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0d), false));
    }

    final long e(long j, long j2) {
        long h = h(i());
        if (j >= j2) {
            return h;
        }
        long j3 = j2 - j;
        long j4 = j3 - 1;
        if ((j3 & j4) == 0) {
            return (h & j4) + j;
        }
        if (j3 > 0) {
            while (true) {
                long j5 = h >>> 1;
                long j6 = j5 + j4;
                long j7 = j5 % j3;
                if (j6 - j7 >= 0) {
                    return j7 + j;
                }
                h = h(i());
            }
        } else {
            while (true) {
                if (h >= j && h < j2) {
                    return h;
                }
                h = h(i());
            }
        }
    }

    final long i() {
        long j = this.a - 7046029254386353131L;
        this.a = j;
        return j;
    }

    @Override // java.util.Random
    public IntStream ints() {
        return C$r8$wrapper$java$util$stream$IntStream$WRP.convert(AbstractC0238t1.s(new c(0L, Long.MAX_VALUE, Integer.MAX_VALUE, 0), false));
    }

    @Override // java.util.Random
    public LongStream longs() {
        return L0.k0(AbstractC0238t1.t(new d(0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L), false));
    }

    @Override // java.util.Random
    protected int next(int i) {
        return (int) (h(i()) >>> (64 - i));
    }

    @Override // java.util.Random
    public boolean nextBoolean() {
        return g(i()) < 0;
    }

    @Override // java.util.Random
    public double nextDouble() {
        return (h(i()) >>> 11) * 1.1102230246251565E-16d;
    }

    @Override // java.util.Random
    public float nextFloat() {
        return (g(i()) >>> 8) * 5.9604645E-8f;
    }

    @Override // java.util.Random
    public double nextGaussian() {
        ThreadLocal threadLocal = f;
        Double d2 = (Double) threadLocal.get();
        if (d2 != null) {
            threadLocal.set(null);
            return d2.doubleValue();
        }
        while (true) {
            double nextDouble = (nextDouble() * 2.0d) - 1.0d;
            double nextDouble2 = (nextDouble() * 2.0d) - 1.0d;
            double d3 = (nextDouble2 * nextDouble2) + (nextDouble * nextDouble);
            if (d3 < 1.0d && d3 != 0.0d) {
                double sqrt = StrictMath.sqrt((StrictMath.log(d3) * (-2.0d)) / d3);
                f.set(new Double(nextDouble2 * sqrt));
                return nextDouble * sqrt;
            }
        }
    }

    @Override // java.util.Random
    public int nextInt() {
        return g(i());
    }

    @Override // java.util.Random
    public int nextInt(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("bound must be positive");
        }
        int g2 = g(i());
        int i2 = i - 1;
        if ((i & i2) == 0) {
            return g2 & i2;
        }
        while (true) {
            int i3 = g2 >>> 1;
            int i4 = i3 + i2;
            int i5 = i3 % i;
            if (i4 - i5 >= 0) {
                return i5;
            }
            g2 = g(i());
        }
    }

    @Override // java.util.Random
    public long nextLong() {
        return h(i());
    }

    @Override // java.util.Random
    public void setSeed(long j) {
        if (this.c) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.Random
    public DoubleStream doubles(double d2, double d3) {
        if (d2 < d3) {
            return J0.k0(AbstractC0238t1.r(new b(0L, Long.MAX_VALUE, d2, d3), false));
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    @Override // java.util.Random
    public IntStream ints(int i, int i2) {
        if (i < i2) {
            return C$r8$wrapper$java$util$stream$IntStream$WRP.convert(AbstractC0238t1.s(new c(0L, Long.MAX_VALUE, i, i2), false));
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    @Override // java.util.Random
    public LongStream longs(long j) {
        if (j >= 0) {
            return L0.k0(AbstractC0238t1.t(new d(0L, j, Long.MAX_VALUE, 0L), false));
        }
        throw new IllegalArgumentException("size must be non-negative");
    }

    @Override // java.util.Random
    public DoubleStream doubles(long j) {
        if (j >= 0) {
            return J0.k0(AbstractC0238t1.r(new b(0L, j, Double.MAX_VALUE, 0.0d), false));
        }
        throw new IllegalArgumentException("size must be non-negative");
    }

    @Override // java.util.Random
    public IntStream ints(long j) {
        if (j >= 0) {
            return C$r8$wrapper$java$util$stream$IntStream$WRP.convert(AbstractC0238t1.s(new c(0L, j, Integer.MAX_VALUE, 0), false));
        }
        throw new IllegalArgumentException("size must be non-negative");
    }

    @Override // java.util.Random
    public LongStream longs(long j, long j2) {
        if (j < j2) {
            return L0.k0(AbstractC0238t1.t(new d(0L, Long.MAX_VALUE, j, j2), false));
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    @Override // java.util.Random
    public DoubleStream doubles(long j, double d2, double d3) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        if (d2 < d3) {
            return J0.k0(AbstractC0238t1.r(new b(0L, j, d2, d3), false));
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    @Override // java.util.Random
    public IntStream ints(long j, int i, int i2) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        if (i < i2) {
            return C$r8$wrapper$java$util$stream$IntStream$WRP.convert(AbstractC0238t1.s(new c(0L, j, i, i2), false));
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    @Override // java.util.Random
    public LongStream longs(long j, long j2, long j3) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        if (j2 < j3) {
            return L0.k0(AbstractC0238t1.t(new d(0L, j, j2, j3), false));
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }
}

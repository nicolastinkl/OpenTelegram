package androidx.sharetarget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutInfoCompatSaver;
import androidx.core.graphics.drawable.IconCompat;
import androidx.sharetarget.ShortcutsInfoSerialization;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class ShortcutInfoCompatSaverImpl extends ShortcutInfoCompatSaver<ListenableFuture<Void>> {
    private static final Object GET_INSTANCE_LOCK = new Object();
    private static volatile ShortcutInfoCompatSaverImpl sInstance;
    final File mBitmapsDir;
    final ExecutorService mCacheUpdateService;
    final Context mContext;
    private final ExecutorService mDiskIoService;
    final File mTargetsXmlFile;
    final Map<String, ShortcutsInfoSerialization.ShortcutContainer> mShortcutsMap = new ArrayMap();
    final Map<String, ListenableFuture<?>> mScheduledBitmapTasks = new ArrayMap();

    @Override // androidx.core.content.pm.ShortcutInfoCompatSaver
    public /* bridge */ /* synthetic */ ListenableFuture<Void> addShortcuts(List list) {
        return addShortcuts((List<ShortcutInfoCompat>) list);
    }

    @Override // androidx.core.content.pm.ShortcutInfoCompatSaver
    public /* bridge */ /* synthetic */ ListenableFuture<Void> removeShortcuts(List list) {
        return removeShortcuts((List<String>) list);
    }

    public static ShortcutInfoCompatSaverImpl getInstance(Context context) {
        if (sInstance == null) {
            synchronized (GET_INSTANCE_LOCK) {
                if (sInstance == null) {
                    sInstance = new ShortcutInfoCompatSaverImpl(context, createExecutorService(), createExecutorService());
                }
            }
        }
        return sInstance;
    }

    static ExecutorService createExecutorService() {
        return new ThreadPoolExecutor(0, 1, 20L, TimeUnit.SECONDS, new LinkedBlockingQueue());
    }

    ShortcutInfoCompatSaverImpl(Context context, ExecutorService executorService, ExecutorService executorService2) {
        this.mContext = context.getApplicationContext();
        this.mCacheUpdateService = executorService;
        this.mDiskIoService = executorService2;
        final File file = new File(context.getFilesDir(), "ShortcutInfoCompatSaver_share_targets");
        this.mBitmapsDir = new File(file, "ShortcutInfoCompatSaver_share_targets_bitmaps");
        this.mTargetsXmlFile = new File(file, "targets.xml");
        executorService.submit(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    ShortcutInfoCompatSaverImpl.ensureDir(file);
                    ShortcutInfoCompatSaverImpl.ensureDir(ShortcutInfoCompatSaverImpl.this.mBitmapsDir);
                    ShortcutInfoCompatSaverImpl shortcutInfoCompatSaverImpl = ShortcutInfoCompatSaverImpl.this;
                    shortcutInfoCompatSaverImpl.mShortcutsMap.putAll(ShortcutsInfoSerialization.loadFromXml(shortcutInfoCompatSaverImpl.mTargetsXmlFile, shortcutInfoCompatSaverImpl.mContext));
                    ShortcutInfoCompatSaverImpl.this.deleteDanglingBitmaps(new ArrayList(ShortcutInfoCompatSaverImpl.this.mShortcutsMap.values()));
                } catch (Exception e) {
                    Log.w("ShortcutInfoCompatSaver", "ShortcutInfoCompatSaver started with an exceptions ", e);
                }
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.core.content.pm.ShortcutInfoCompatSaver
    public ListenableFuture<Void> removeShortcuts(List<String> list) {
        final ArrayList arrayList = new ArrayList(list);
        final ResolvableFuture create = ResolvableFuture.create();
        this.mCacheUpdateService.submit(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.2
            @Override // java.lang.Runnable
            public void run() {
                for (String str : arrayList) {
                    ShortcutInfoCompatSaverImpl.this.mShortcutsMap.remove(str);
                    ListenableFuture<?> remove = ShortcutInfoCompatSaverImpl.this.mScheduledBitmapTasks.remove(str);
                    if (remove != null) {
                        remove.cancel(false);
                    }
                }
                ShortcutInfoCompatSaverImpl.this.scheduleSyncCurrentState(create);
            }
        });
        return create;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.core.content.pm.ShortcutInfoCompatSaver
    public ListenableFuture<Void> removeAllShortcuts() {
        final ResolvableFuture create = ResolvableFuture.create();
        this.mCacheUpdateService.submit(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.3
            @Override // java.lang.Runnable
            public void run() {
                ShortcutInfoCompatSaverImpl.this.mShortcutsMap.clear();
                Iterator<ListenableFuture<?>> it = ShortcutInfoCompatSaverImpl.this.mScheduledBitmapTasks.values().iterator();
                while (it.hasNext()) {
                    it.next().cancel(false);
                }
                ShortcutInfoCompatSaverImpl.this.mScheduledBitmapTasks.clear();
                ShortcutInfoCompatSaverImpl.this.scheduleSyncCurrentState(create);
            }
        });
        return create;
    }

    @Override // androidx.core.content.pm.ShortcutInfoCompatSaver
    public List<ShortcutInfoCompat> getShortcuts() throws Exception {
        return (List) this.mCacheUpdateService.submit(new Callable<ArrayList<ShortcutInfoCompat>>() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.4
            @Override // java.util.concurrent.Callable
            public ArrayList<ShortcutInfoCompat> call() {
                ArrayList<ShortcutInfoCompat> arrayList = new ArrayList<>();
                Iterator<ShortcutsInfoSerialization.ShortcutContainer> it = ShortcutInfoCompatSaverImpl.this.mShortcutsMap.values().iterator();
                while (it.hasNext()) {
                    arrayList.add(new ShortcutInfoCompat.Builder(it.next().mShortcutInfo).build());
                }
                return arrayList;
            }
        }).get();
    }

    public IconCompat getShortcutIcon(final String str) throws Exception {
        Bitmap bitmap;
        final ShortcutsInfoSerialization.ShortcutContainer shortcutContainer = (ShortcutsInfoSerialization.ShortcutContainer) this.mCacheUpdateService.submit(new Callable<ShortcutsInfoSerialization.ShortcutContainer>() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public ShortcutsInfoSerialization.ShortcutContainer call() {
                return ShortcutInfoCompatSaverImpl.this.mShortcutsMap.get(str);
            }
        }).get();
        if (shortcutContainer == null) {
            return null;
        }
        if (!TextUtils.isEmpty(shortcutContainer.mResourceName)) {
            int i = 0;
            try {
                i = this.mContext.getResources().getIdentifier(shortcutContainer.mResourceName, null, null);
            } catch (Exception unused) {
            }
            if (i != 0) {
                return IconCompat.createWithResource(this.mContext, i);
            }
        }
        if (TextUtils.isEmpty(shortcutContainer.mBitmapPath) || (bitmap = (Bitmap) this.mDiskIoService.submit(new Callable<Bitmap>(this) { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.6
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Bitmap call() {
                return BitmapFactory.decodeFile(shortcutContainer.mBitmapPath);
            }
        }).get()) == null) {
            return null;
        }
        return IconCompat.createWithBitmap(bitmap);
    }

    void deleteDanglingBitmaps(List<ShortcutsInfoSerialization.ShortcutContainer> list) {
        ArrayList arrayList = new ArrayList();
        for (ShortcutsInfoSerialization.ShortcutContainer shortcutContainer : list) {
            if (!TextUtils.isEmpty(shortcutContainer.mBitmapPath)) {
                arrayList.add(shortcutContainer.mBitmapPath);
            }
        }
        for (File file : this.mBitmapsDir.listFiles()) {
            if (!arrayList.contains(file.getAbsolutePath())) {
                file.delete();
            }
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.core.content.pm.ShortcutInfoCompatSaver
    public ListenableFuture<Void> addShortcuts(List<ShortcutInfoCompat> list) {
        final ArrayList arrayList = new ArrayList(list.size());
        Iterator<ShortcutInfoCompat> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new ShortcutInfoCompat.Builder(it.next()).build());
        }
        final ResolvableFuture create = ResolvableFuture.create();
        this.mCacheUpdateService.submit(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.7
            @Override // java.lang.Runnable
            public void run() {
                for (ShortcutInfoCompat shortcutInfoCompat : arrayList) {
                    Set<String> categories = shortcutInfoCompat.getCategories();
                    if (categories != null && !categories.isEmpty()) {
                        ShortcutsInfoSerialization.ShortcutContainer containerFrom = ShortcutInfoCompatSaverImpl.this.containerFrom(shortcutInfoCompat);
                        Bitmap bitmap = containerFrom.mBitmapPath != null ? shortcutInfoCompat.getIcon().getBitmap() : null;
                        final String id = shortcutInfoCompat.getId();
                        ShortcutInfoCompatSaverImpl.this.mShortcutsMap.put(id, containerFrom);
                        if (bitmap != null) {
                            final ListenableFuture<Void> scheduleBitmapSaving = ShortcutInfoCompatSaverImpl.this.scheduleBitmapSaving(bitmap, containerFrom.mBitmapPath);
                            ListenableFuture<?> put = ShortcutInfoCompatSaverImpl.this.mScheduledBitmapTasks.put(id, scheduleBitmapSaving);
                            if (put != null) {
                                put.cancel(false);
                            }
                            scheduleBitmapSaving.addListener(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.7.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    ShortcutInfoCompatSaverImpl.this.mScheduledBitmapTasks.remove(id);
                                    if (scheduleBitmapSaving.isCancelled()) {
                                        return;
                                    }
                                    try {
                                        scheduleBitmapSaving.get();
                                    } catch (Exception e) {
                                        create.setException(e);
                                    }
                                }
                            }, ShortcutInfoCompatSaverImpl.this.mCacheUpdateService);
                        }
                    }
                }
                ShortcutInfoCompatSaverImpl.this.scheduleSyncCurrentState(create);
            }
        });
        return create;
    }

    ListenableFuture<Void> scheduleBitmapSaving(final Bitmap bitmap, final String str) {
        return submitDiskOperation(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.8
            @Override // java.lang.Runnable
            public void run() {
                ShortcutInfoCompatSaverImpl.this.saveBitmap(bitmap, str);
            }
        });
    }

    private ListenableFuture<Void> submitDiskOperation(final Runnable runnable) {
        final ResolvableFuture create = ResolvableFuture.create();
        this.mDiskIoService.submit(new Runnable(this) { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.9
            @Override // java.lang.Runnable
            public void run() {
                if (create.isCancelled()) {
                    return;
                }
                try {
                    runnable.run();
                    create.set(null);
                } catch (Exception e) {
                    create.setException(e);
                }
            }
        });
        return create;
    }

    void scheduleSyncCurrentState(final ResolvableFuture<Void> resolvableFuture) {
        final ArrayList arrayList = new ArrayList(this.mShortcutsMap.values());
        final ListenableFuture<Void> submitDiskOperation = submitDiskOperation(new Runnable() { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.10
            @Override // java.lang.Runnable
            public void run() {
                ShortcutInfoCompatSaverImpl.this.deleteDanglingBitmaps(arrayList);
                ShortcutsInfoSerialization.saveAsXml(arrayList, ShortcutInfoCompatSaverImpl.this.mTargetsXmlFile);
            }
        });
        submitDiskOperation.addListener(new Runnable(this) { // from class: androidx.sharetarget.ShortcutInfoCompatSaverImpl.11
            @Override // java.lang.Runnable
            public void run() {
                try {
                    submitDiskOperation.get();
                    resolvableFuture.set(null);
                } catch (Exception e) {
                    resolvableFuture.setException(e);
                }
            }
        }, this.mCacheUpdateService);
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0012, code lost:
    
        if (r2 != 5) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    androidx.sharetarget.ShortcutsInfoSerialization.ShortcutContainer containerFrom(androidx.core.content.pm.ShortcutInfoCompat r5) {
        /*
            r4 = this;
            androidx.core.graphics.drawable.IconCompat r0 = r5.getIcon()
            r1 = 0
            if (r0 == 0) goto L3b
            int r2 = r0.getType()
            r3 = 1
            if (r2 == r3) goto L25
            r3 = 2
            if (r2 == r3) goto L15
            r0 = 5
            if (r2 == r0) goto L25
            goto L3b
        L15:
            android.content.Context r2 = r4.mContext
            android.content.res.Resources r2 = r2.getResources()
            int r0 = r0.getResId()
            java.lang.String r0 = r2.getResourceName(r0)
            r2 = r1
            goto L3d
        L25:
            java.io.File r0 = new java.io.File
            java.io.File r2 = r4.mBitmapsDir
            java.util.UUID r3 = java.util.UUID.randomUUID()
            java.lang.String r3 = r3.toString()
            r0.<init>(r2, r3)
            java.lang.String r0 = r0.getAbsolutePath()
            r2 = r0
            r0 = r1
            goto L3d
        L3b:
            r0 = r1
            r2 = r0
        L3d:
            androidx.core.content.pm.ShortcutInfoCompat$Builder r3 = new androidx.core.content.pm.ShortcutInfoCompat$Builder
            r3.<init>(r5)
            androidx.core.content.pm.ShortcutInfoCompat$Builder r5 = r3.setIcon(r1)
            androidx.core.content.pm.ShortcutInfoCompat r5 = r5.build()
            androidx.sharetarget.ShortcutsInfoSerialization$ShortcutContainer r1 = new androidx.sharetarget.ShortcutsInfoSerialization$ShortcutContainer
            r1.<init>(r5, r0, r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.sharetarget.ShortcutInfoCompatSaverImpl.containerFrom(androidx.core.content.pm.ShortcutInfoCompat):androidx.sharetarget.ShortcutsInfoSerialization$ShortcutContainer");
    }

    void saveBitmap(Bitmap bitmap, String str) {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap is null");
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("path is empty");
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
            try {
                if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                    Log.wtf("ShortcutInfoCompatSaver", "Unable to compress bitmap");
                    throw new RuntimeException("Unable to compress bitmap for saving " + str);
                }
                fileOutputStream.close();
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | OutOfMemoryError | RuntimeException e) {
            Log.wtf("ShortcutInfoCompatSaver", "Unable to write bitmap to file", e);
            throw new RuntimeException("Unable to write bitmap to file " + str, e);
        }
    }

    static boolean ensureDir(File file) {
        if (file.exists() && !file.isDirectory() && !file.delete()) {
            return false;
        }
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }
}

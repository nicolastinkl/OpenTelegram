package com.hbisoft.hbrecorder;

import android.app.Activity;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/* loaded from: classes.dex */
class FileObserver extends android.os.FileObserver {
    private final Activity activity;
    private final int mMask;
    private List<SingleFileObserver> mObservers;
    private final String mPath;
    private final MyListener ml;

    FileObserver(String str, Activity activity, MyListener myListener) {
        super(str, 4095);
        this.mPath = str;
        this.mMask = 4095;
        this.activity = activity;
        this.ml = myListener;
    }

    @Override // android.os.FileObserver
    public void startWatching() {
        if (this.mObservers != null) {
            return;
        }
        this.mObservers = new ArrayList();
        Stack stack = new Stack();
        stack.push(this.mPath);
        while (!stack.isEmpty()) {
            String str = (String) stack.pop();
            this.mObservers.add(new SingleFileObserver(str, this.mMask));
            File[] listFiles = new File(str).listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.isDirectory() && !file.getName().equals(".") && !file.getName().equals("..")) {
                        stack.push(file.getPath());
                    }
                }
            }
        }
        Iterator<SingleFileObserver> it = this.mObservers.iterator();
        while (it.hasNext()) {
            it.next().startWatching();
        }
    }

    @Override // android.os.FileObserver
    public void stopWatching() {
        List<SingleFileObserver> list = this.mObservers;
        if (list == null) {
            return;
        }
        Iterator<SingleFileObserver> it = list.iterator();
        while (it.hasNext()) {
            it.next().stopWatching();
        }
        this.mObservers.clear();
        this.mObservers = null;
    }

    @Override // android.os.FileObserver
    public void onEvent(int i, String str) {
        if (i == 8) {
            this.activity.runOnUiThread(new Runnable() { // from class: com.hbisoft.hbrecorder.FileObserver.1
                @Override // java.lang.Runnable
                public void run() {
                    FileObserver.this.ml.callback();
                }
            });
        }
    }

    class SingleFileObserver extends android.os.FileObserver {
        final String mPath;

        SingleFileObserver(String str, int i) {
            super(str, i);
            this.mPath = str;
        }

        @Override // android.os.FileObserver
        public void onEvent(int i, String str) {
            FileObserver.this.onEvent(i, this.mPath + "/" + str);
        }
    }
}

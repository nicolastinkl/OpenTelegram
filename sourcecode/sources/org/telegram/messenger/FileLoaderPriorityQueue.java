package org.telegram.messenger;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class FileLoaderPriorityQueue {
    public static final int TYPE_LARGE = 1;
    public static final int TYPE_SMALL = 0;
    int currentAccount;
    String name;
    int type;
    private ArrayList<FileLoadOperation> allOperations = new ArrayList<>();
    private int PRIORITY_VALUE_MAX = 1048576;
    private int PRIORITY_VALUE_NORMAL = CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT;
    private int PRIORITY_VALUE_LOW = 0;

    FileLoaderPriorityQueue(int i, String str, int i2) {
        this.currentAccount = i;
        this.name = str;
        this.type = i2;
    }

    public void add(FileLoadOperation fileLoadOperation) {
        if (fileLoadOperation == null) {
            return;
        }
        int i = 0;
        int i2 = 0;
        while (i2 < this.allOperations.size()) {
            if (this.allOperations.get(i2) == fileLoadOperation) {
                this.allOperations.remove(i2);
                i2--;
            }
            i2++;
        }
        while (true) {
            if (i >= this.allOperations.size()) {
                i = -1;
                break;
            } else if (fileLoadOperation.getPriority() > this.allOperations.get(i).getPriority()) {
                break;
            } else {
                i++;
            }
        }
        if (i >= 0) {
            this.allOperations.add(i, fileLoadOperation);
        } else {
            this.allOperations.add(fileLoadOperation);
        }
    }

    public void cancel(FileLoadOperation fileLoadOperation) {
        if (fileLoadOperation != null && this.allOperations.remove(fileLoadOperation)) {
            fileLoadOperation.cancel();
        }
    }

    public void checkLoadingOperations() {
        int i = this.type == 1 ? MessagesController.getInstance(this.currentAccount).largeQueueMaxActiveOperations : MessagesController.getInstance(this.currentAccount).smallQueueMaxActiveOperations;
        boolean z = false;
        int i2 = 0;
        for (int i3 = 0; i3 < this.allOperations.size(); i3++) {
            FileLoadOperation fileLoadOperation = this.allOperations.get(i3);
            if (i3 > 0 && !z && i2 > this.PRIORITY_VALUE_LOW && fileLoadOperation.getPriority() == this.PRIORITY_VALUE_LOW) {
                z = true;
            }
            if (fileLoadOperation.preFinished) {
                i++;
            } else if (!z && i3 < i) {
                fileLoadOperation.start();
            } else if (fileLoadOperation.wasStarted()) {
                fileLoadOperation.pause();
            }
            i2 = fileLoadOperation.getPriority();
        }
    }

    public boolean remove(FileLoadOperation fileLoadOperation) {
        if (fileLoadOperation == null) {
            return false;
        }
        return this.allOperations.remove(fileLoadOperation);
    }

    public int getCount() {
        return this.allOperations.size();
    }

    public int getPosition(FileLoadOperation fileLoadOperation) {
        return this.allOperations.indexOf(fileLoadOperation);
    }
}

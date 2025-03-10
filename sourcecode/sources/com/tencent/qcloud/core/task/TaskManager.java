package com.tencent.qcloud.core.task;

import com.tencent.qcloud.core.logger.QCloudLogger;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class TaskManager {
    private static volatile TaskManager instance;
    private Map<String, QCloudTask> taskPool = new ConcurrentHashMap(30);

    public static TaskManager getInstance() {
        if (instance == null) {
            synchronized (TaskManager.class) {
                if (instance == null) {
                    instance = new TaskManager();
                }
            }
        }
        return instance;
    }

    private TaskManager() {
    }

    void add(QCloudTask qCloudTask) {
        this.taskPool.put(qCloudTask.getIdentifier(), qCloudTask);
        QCloudLogger.d("QCloudTask", "[Pool] ADD %s, %d cached", qCloudTask.getIdentifier(), Integer.valueOf(this.taskPool.size()));
    }

    void remove(QCloudTask qCloudTask) {
        if (this.taskPool.remove(qCloudTask.getIdentifier()) != null) {
            QCloudLogger.d("QCloudTask", "[Pool] REMOVE %s, %d cached", qCloudTask.getIdentifier(), Integer.valueOf(this.taskPool.size()));
        }
    }

    public QCloudTask get(String str) {
        return this.taskPool.get(str);
    }

    public List<QCloudTask> snapshot() {
        return new ArrayList(this.taskPool.values());
    }
}

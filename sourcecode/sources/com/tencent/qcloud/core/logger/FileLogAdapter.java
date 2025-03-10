package com.tencent.qcloud.core.logger;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class FileLogAdapter implements LogAdapter {
    private static FileLogAdapter instance;
    private static final byte[] object = new byte[0];
    private Handler handler;
    private File latestLogFile;
    private File logRootDir;
    private int minPriority;
    private List<FileLogItem> bufferRecord = Collections.synchronizedList(new ArrayList());
    private volatile long mBufferSize = 0;

    public static FileLogAdapter getInstance(Context context, String str) {
        return getInstance(context, str, 4);
    }

    public static FileLogAdapter getInstance(Context context, String str, int i) {
        synchronized (FileLogAdapter.class) {
            if (instance == null) {
                instance = new FileLogAdapter(context, str, i);
            }
        }
        return instance;
    }

    private FileLogAdapter(Context context, String str, int i) {
        this.minPriority = i;
        this.logRootDir = new File(context.getExternalCacheDir() + File.separator + "QCloudLogs");
        HandlerThread handlerThread = new HandlerThread("log_handlerThread", 1);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper()) { // from class: com.tencent.qcloud.core.logger.FileLogAdapter.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 == 0) {
                    FileLogAdapter.this.flush();
                    sendEmptyMessageDelayed(0, 10000L);
                } else {
                    if (i2 != 1) {
                        return;
                    }
                    FileLogAdapter.this.input();
                }
            }
        };
        this.handler = handler;
        Message obtainMessage = handler.obtainMessage();
        obtainMessage.what = 0;
        this.handler.sendMessage(obtainMessage);
    }

    @Override // com.tencent.qcloud.core.logger.LogAdapter
    public boolean isLoggable(int i, String str) {
        return i >= this.minPriority;
    }

    @Override // com.tencent.qcloud.core.logger.LogAdapter
    public synchronized void log(int i, String str, String str2, Throwable th) {
        FileLogItem fileLogItem = new FileLogItem(str, i, str2, th);
        this.bufferRecord.add(fileLogItem);
        this.mBufferSize += fileLogItem.getLength();
        this.handler.removeMessages(1);
        this.handler.sendEmptyMessageDelayed(1, 500L);
    }

    private String formatDateString(long j) {
        return new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss", Locale.getDefault()).format(Long.valueOf(j));
    }

    private boolean isSameDay(String str, long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return simpleDateFormat2.format(simpleDateFormat.parse(str)).equals(simpleDateFormat2.format(Long.valueOf(j)));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public File[] getLogFilesDesc(int i) {
        if (this.logRootDir.listFiles() == null || this.logRootDir.listFiles().length <= 0) {
            return null;
        }
        File[] listFiles = this.logRootDir.listFiles();
        Arrays.sort(listFiles, new Comparator<File>(this) { // from class: com.tencent.qcloud.core.logger.FileLogAdapter.2
            @Override // java.util.Comparator
            public int compare(File file, File file2) {
                return Long.valueOf(file2.lastModified()).compareTo(Long.valueOf(file.lastModified()));
            }
        });
        int min = Math.min(i, listFiles.length);
        File[] fileArr = new File[min];
        System.arraycopy(listFiles, 0, fileArr, 0, min);
        return fileArr;
    }

    private File getLogFile(long j) {
        File[] listFiles = this.logRootDir.listFiles();
        if (this.latestLogFile == null) {
            if (!this.logRootDir.exists() && !this.logRootDir.mkdirs()) {
                return null;
            }
            if (listFiles != null && listFiles.length > 0) {
                Arrays.sort(listFiles, new Comparator<File>(this) { // from class: com.tencent.qcloud.core.logger.FileLogAdapter.3
                    @Override // java.util.Comparator
                    public int compare(File file, File file2) {
                        return Long.valueOf(file2.lastModified()).compareTo(Long.valueOf(file.lastModified()));
                    }
                });
                this.latestLogFile = listFiles[0];
            }
        }
        File file = this.latestLogFile;
        if (file != null && file.length() < 3145728 && isSameDay(this.latestLogFile.getName().replace(".log", ""), j)) {
            return this.latestLogFile;
        }
        this.latestLogFile = new File(this.logRootDir + File.separator + formatDateString(j) + ".log");
        cleanFilesIfNecessary(listFiles);
        return this.latestLogFile;
    }

    private void cleanFilesIfNecessary(File[] fileArr) {
        if (fileArr == null || fileArr.length < 30) {
            return;
        }
        fileArr[fileArr.length - 1].delete();
    }

    private void write(List<FileLogItem> list) {
        synchronized (object) {
            if (list == null) {
                return;
            }
            FileOutputStream fileOutputStream = null;
            try {
                try {
                    File logFile = getLogFile(System.currentTimeMillis());
                    if (logFile != null) {
                        FileOutputStream fileOutputStream2 = new FileOutputStream(logFile, true);
                        for (int i = 0; i < list.size(); i++) {
                            try {
                                fileOutputStream2.write(list.get(i).toString().getBytes("UTF-8"));
                            } catch (FileNotFoundException e) {
                                e = e;
                                fileOutputStream = fileOutputStream2;
                                e.printStackTrace();
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e2) {
                                        e = e2;
                                        e.printStackTrace();
                                    }
                                }
                            } catch (IOException e3) {
                                e = e3;
                                fileOutputStream = fileOutputStream2;
                                e.printStackTrace();
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e4) {
                                        e = e4;
                                        e.printStackTrace();
                                    }
                                }
                            } catch (Throwable th) {
                                th = th;
                                fileOutputStream = fileOutputStream2;
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e5) {
                                        e5.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        }
                        fileOutputStream2.flush();
                        fileOutputStream = fileOutputStream2;
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e6) {
                            e = e6;
                            e.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e7) {
                    e = e7;
                } catch (IOException e8) {
                    e = e8;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void flush() {
        if (this.mBufferSize <= 0) {
            return;
        }
        write(this.bufferRecord);
        this.bufferRecord.clear();
        this.mBufferSize = 0L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void input() {
        if (this.mBufferSize > 32768) {
            flush();
        }
    }
}

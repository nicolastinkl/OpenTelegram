package org.xbill.DNS.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

/* loaded from: classes4.dex */
public class ResolvConfResolverConfigProvider extends BaseResolverConfigProvider {
    private int ndots = 1;

    @Override // org.xbill.DNS.config.ResolverConfigProvider
    public void initialize() {
        reset();
        if (tryParseResolveConf("/etc/resolv.conf")) {
            return;
        }
        tryParseResolveConf("sys:/etc/resolv.cfg");
    }

    private boolean tryParseResolveConf(String str) {
        Path path = Paths.get(str, new String[0]);
        if (Files.exists(path, new LinkOption[0])) {
            try {
                InputStream newInputStream = Files.newInputStream(path, new OpenOption[0]);
                try {
                    parseResolvConf(newInputStream);
                    if (newInputStream != null) {
                        newInputStream.close();
                    }
                    return true;
                } finally {
                }
            } catch (IOException unused) {
            }
        }
        return false;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    protected void parseResolvConf(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        try {
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        StringTokenizer stringTokenizer = new StringTokenizer(readLine);
                        if (stringTokenizer.hasMoreTokens()) {
                            String nextToken = stringTokenizer.nextToken();
                            char c = 65535;
                            switch (nextToken.hashCode()) {
                                case -1326197564:
                                    if (nextToken.equals("domain")) {
                                        c = 1;
                                        break;
                                    }
                                    break;
                                case -1249474914:
                                    if (nextToken.equals("options")) {
                                        c = 3;
                                        break;
                                    }
                                    break;
                                case -906336856:
                                    if (nextToken.equals("search")) {
                                        c = 2;
                                        break;
                                    }
                                    break;
                                case 154424718:
                                    if (nextToken.equals("nameserver")) {
                                        c = 0;
                                        break;
                                    }
                                    break;
                            }
                            if (c == 0) {
                                addNameserver(new InetSocketAddress(stringTokenizer.nextToken(), 53));
                            } else if (c == 1) {
                                this.searchlist.clear();
                                if (stringTokenizer.hasMoreTokens()) {
                                    addSearchPath(stringTokenizer.nextToken());
                                }
                            } else {
                                if (c == 2) {
                                    this.searchlist.clear();
                                    while (stringTokenizer.hasMoreTokens()) {
                                        addSearchPath(stringTokenizer.nextToken());
                                    }
                                } else if (c != 3) {
                                }
                                while (stringTokenizer.hasMoreTokens()) {
                                    String nextToken2 = stringTokenizer.nextToken();
                                    if (nextToken2.startsWith("ndots:")) {
                                        this.ndots = parseNdots(nextToken2.substring(6));
                                    }
                                }
                            }
                        }
                    } else {
                        bufferedReader.close();
                        inputStreamReader.close();
                        String str = System.getenv("LOCALDOMAIN");
                        if (str != null && !str.isEmpty()) {
                            this.searchlist.clear();
                            parseSearchPathList(str, " ");
                        }
                        String str2 = System.getenv("RES_OPTIONS");
                        if (str2 == null || str2.isEmpty()) {
                            return;
                        }
                        StringTokenizer stringTokenizer2 = new StringTokenizer(str2, " ");
                        while (stringTokenizer2.hasMoreTokens()) {
                            String nextToken3 = stringTokenizer2.nextToken();
                            if (nextToken3.startsWith("ndots:")) {
                                this.ndots = parseNdots(nextToken3.substring(6));
                            }
                        }
                        return;
                    }
                } finally {
                }
            }
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    inputStreamReader.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    @Override // org.xbill.DNS.config.BaseResolverConfigProvider, org.xbill.DNS.config.ResolverConfigProvider
    public int ndots() {
        return this.ndots;
    }

    @Override // org.xbill.DNS.config.BaseResolverConfigProvider, org.xbill.DNS.config.ResolverConfigProvider
    public boolean isEnabled() {
        return (System.getProperty("os.name").contains("Windows") || System.getProperty("java.specification.vendor").toLowerCase().contains("android")) ? false : true;
    }
}

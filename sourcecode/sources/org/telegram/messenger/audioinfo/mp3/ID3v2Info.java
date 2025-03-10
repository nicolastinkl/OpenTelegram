package org.telegram.messenger.audioinfo.mp3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.messenger.audioinfo.AudioInfo;

/* loaded from: classes3.dex */
public class ID3v2Info extends AudioInfo {
    static final Logger LOGGER = Logger.getLogger(ID3v2Info.class.getName());
    private byte coverPictureType;
    private final Level debugLevel;

    static class AttachedPicture {
        final byte[] imageData;
        final byte type;

        public AttachedPicture(byte b, String str, String str2, byte[] bArr) {
            this.type = b;
            this.imageData = bArr;
        }
    }

    static class CommentOrUnsynchronizedLyrics {
        final String description;
        final String text;

        public CommentOrUnsynchronizedLyrics(String str, String str2, String str3) {
            this.description = str2;
            this.text = str3;
        }
    }

    public static boolean isID3v2StartPosition(InputStream inputStream) throws IOException {
        boolean z;
        inputStream.mark(3);
        try {
            if (inputStream.read() == 73 && inputStream.read() == 68) {
                if (inputStream.read() == 51) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            inputStream.reset();
        }
    }

    public ID3v2Info(InputStream inputStream, Level level) throws IOException, ID3v2Exception {
        ID3v2DataInput data;
        long remainingLength;
        this.debugLevel = level;
        if (isID3v2StartPosition(inputStream)) {
            ID3v2TagHeader iD3v2TagHeader = new ID3v2TagHeader(inputStream);
            this.brand = "ID3";
            String.format("2.%d.%d", Integer.valueOf(iD3v2TagHeader.getVersion()), Integer.valueOf(iD3v2TagHeader.getRevision()));
            ID3v2TagBody tagBody = iD3v2TagHeader.tagBody(inputStream);
            while (true) {
                try {
                    if (tagBody.getRemainingLength() <= 10) {
                        break;
                    }
                    ID3v2FrameHeader iD3v2FrameHeader = new ID3v2FrameHeader(tagBody);
                    if (iD3v2FrameHeader.isPadding()) {
                        break;
                    }
                    if (iD3v2FrameHeader.getBodySize() > tagBody.getRemainingLength()) {
                        Logger logger = LOGGER;
                        if (logger.isLoggable(level)) {
                            logger.log(level, "ID3 frame claims to extend frames area");
                        }
                    } else if (iD3v2FrameHeader.isValid() && !iD3v2FrameHeader.isEncryption()) {
                        ID3v2FrameBody frameBody = tagBody.frameBody(iD3v2FrameHeader);
                        try {
                            try {
                                parseFrame(frameBody);
                                data = frameBody.getData();
                                remainingLength = frameBody.getRemainingLength();
                            } catch (ID3v2Exception e) {
                                if (LOGGER.isLoggable(level)) {
                                    LOGGER.log(level, String.format("ID3 exception occured in frame %s: %s", iD3v2FrameHeader.getFrameId(), e.getMessage()));
                                }
                                data = frameBody.getData();
                                remainingLength = frameBody.getRemainingLength();
                            }
                            data.skipFully(remainingLength);
                        } catch (Throwable th) {
                            frameBody.getData().skipFully(frameBody.getRemainingLength());
                            throw th;
                        }
                    } else {
                        tagBody.getData().skipFully(iD3v2FrameHeader.getBodySize());
                    }
                } catch (ID3v2Exception e2) {
                    Logger logger2 = LOGGER;
                    if (logger2.isLoggable(level)) {
                        logger2.log(level, "ID3 exception occured: " + e2.getMessage());
                    }
                }
            }
            tagBody.getData().skipFully(tagBody.getRemainingLength());
            if (iD3v2TagHeader.getFooterSize() > 0) {
                inputStream.skip(iD3v2TagHeader.getFooterSize());
            }
        }
    }

    void parseFrame(ID3v2FrameBody iD3v2FrameBody) throws IOException, ID3v2Exception {
        String str;
        byte b;
        int i;
        Logger logger = LOGGER;
        if (logger.isLoggable(this.debugLevel)) {
            logger.log(this.debugLevel, "Parsing frame: " + iD3v2FrameBody.getFrameHeader().getFrameId());
        }
        String frameId = iD3v2FrameBody.getFrameHeader().getFrameId();
        frameId.hashCode();
        switch (frameId) {
            case "COM":
            case "COMM":
                CommentOrUnsynchronizedLyrics parseCommentOrUnsynchronizedLyricsFrame = parseCommentOrUnsynchronizedLyricsFrame(iD3v2FrameBody);
                if (this.comment == null || (str = parseCommentOrUnsynchronizedLyricsFrame.description) == null || "".equals(str)) {
                    this.comment = parseCommentOrUnsynchronizedLyricsFrame.text;
                    break;
                }
                break;
            case "PIC":
            case "APIC":
                if (this.cover == null || this.coverPictureType != 3) {
                    AttachedPicture parseAttachedPictureFrame = parseAttachedPictureFrame(iD3v2FrameBody);
                    if (this.cover == null || (b = parseAttachedPictureFrame.type) == 3 || b == 0) {
                        try {
                            byte[] bArr = parseAttachedPictureFrame.imageData;
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            options.inSampleSize = 1;
                            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
                            int i2 = options.outWidth;
                            if (i2 > 800 || options.outHeight > 800) {
                                for (int max = Math.max(i2, options.outHeight); max > 800; max /= 2) {
                                    options.inSampleSize *= 2;
                                }
                            }
                            options.inJustDecodeBounds = false;
                            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
                            this.cover = decodeByteArray;
                            if (decodeByteArray != null) {
                                float max2 = Math.max(decodeByteArray.getWidth(), this.cover.getHeight()) / 120.0f;
                                if (max2 > 0.0f) {
                                    this.smallCover = Bitmap.createScaledBitmap(this.cover, (int) (r1.getWidth() / max2), (int) (this.cover.getHeight() / max2), true);
                                } else {
                                    this.smallCover = this.cover;
                                }
                                if (this.smallCover == null) {
                                    this.smallCover = this.cover;
                                }
                            }
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                        this.coverPictureType = parseAttachedPictureFrame.type;
                        break;
                    }
                }
                break;
            case "TAL":
            case "TALB":
                this.album = parseTextFrame(iD3v2FrameBody);
                break;
            case "TCM":
            case "TCOM":
                this.composer = parseTextFrame(iD3v2FrameBody);
                break;
            case "TCO":
            case "TCON":
                String parseTextFrame = parseTextFrame(iD3v2FrameBody);
                if (parseTextFrame.length() > 0) {
                    this.genre = parseTextFrame;
                    ID3v1Genre iD3v1Genre = null;
                    try {
                        if (parseTextFrame.charAt(0) == '(') {
                            int indexOf = parseTextFrame.indexOf(41);
                            if (indexOf > 1 && (iD3v1Genre = ID3v1Genre.getGenre(Integer.parseInt(parseTextFrame.substring(1, indexOf)))) == null && parseTextFrame.length() > (i = indexOf + 1)) {
                                this.genre = parseTextFrame.substring(i);
                            }
                        } else {
                            iD3v1Genre = ID3v1Genre.getGenre(Integer.parseInt(parseTextFrame));
                        }
                        if (iD3v1Genre != null) {
                            this.genre = iD3v1Genre.getDescription();
                            break;
                        }
                    } catch (NumberFormatException unused) {
                        return;
                    }
                }
                break;
            case "TCP":
            case "TCMP":
                this.compilation = "1".equals(parseTextFrame(iD3v2FrameBody));
                break;
            case "TCR":
            case "TCOP":
                this.copyright = parseTextFrame(iD3v2FrameBody);
                break;
            case "TLE":
            case "TLEN":
                String parseTextFrame2 = parseTextFrame(iD3v2FrameBody);
                try {
                    this.duration = Long.valueOf(parseTextFrame2).longValue();
                    break;
                } catch (NumberFormatException unused2) {
                    Logger logger2 = LOGGER;
                    if (logger2.isLoggable(this.debugLevel)) {
                        logger2.log(this.debugLevel, "Could not parse track duration: " + parseTextFrame2);
                        return;
                    }
                    return;
                }
            case "TP1":
            case "TPE1":
                this.artist = parseTextFrame(iD3v2FrameBody);
                break;
            case "TP2":
            case "TPE2":
                this.albumArtist = parseTextFrame(iD3v2FrameBody);
                break;
            case "TPA":
            case "TPOS":
                String parseTextFrame3 = parseTextFrame(iD3v2FrameBody);
                if (parseTextFrame3.length() > 0) {
                    int indexOf2 = parseTextFrame3.indexOf(47);
                    if (indexOf2 < 0) {
                        try {
                            this.disc = Short.valueOf(parseTextFrame3).shortValue();
                            break;
                        } catch (NumberFormatException unused3) {
                            Logger logger3 = LOGGER;
                            if (logger3.isLoggable(this.debugLevel)) {
                                logger3.log(this.debugLevel, "Could not parse disc number: " + parseTextFrame3);
                                return;
                            }
                            return;
                        }
                    } else {
                        try {
                            this.disc = Short.valueOf(parseTextFrame3.substring(0, indexOf2)).shortValue();
                        } catch (NumberFormatException unused4) {
                            Logger logger4 = LOGGER;
                            if (logger4.isLoggable(this.debugLevel)) {
                                logger4.log(this.debugLevel, "Could not parse disc number: " + parseTextFrame3);
                            }
                        }
                        try {
                            this.discs = Short.valueOf(parseTextFrame3.substring(indexOf2 + 1)).shortValue();
                            break;
                        } catch (NumberFormatException unused5) {
                            Logger logger5 = LOGGER;
                            if (logger5.isLoggable(this.debugLevel)) {
                                logger5.log(this.debugLevel, "Could not parse number of discs: " + parseTextFrame3);
                                return;
                            }
                            return;
                        }
                    }
                }
                break;
            case "TRK":
            case "TRCK":
                String parseTextFrame4 = parseTextFrame(iD3v2FrameBody);
                if (parseTextFrame4.length() > 0) {
                    int indexOf3 = parseTextFrame4.indexOf(47);
                    if (indexOf3 < 0) {
                        try {
                            this.track = Short.valueOf(parseTextFrame4).shortValue();
                            break;
                        } catch (NumberFormatException unused6) {
                            Logger logger6 = LOGGER;
                            if (logger6.isLoggable(this.debugLevel)) {
                                logger6.log(this.debugLevel, "Could not parse track number: " + parseTextFrame4);
                                return;
                            }
                            return;
                        }
                    } else {
                        try {
                            this.track = Short.valueOf(parseTextFrame4.substring(0, indexOf3)).shortValue();
                        } catch (NumberFormatException unused7) {
                            Logger logger7 = LOGGER;
                            if (logger7.isLoggable(this.debugLevel)) {
                                logger7.log(this.debugLevel, "Could not parse track number: " + parseTextFrame4);
                            }
                        }
                        try {
                            this.tracks = Short.valueOf(parseTextFrame4.substring(indexOf3 + 1)).shortValue();
                            break;
                        } catch (NumberFormatException unused8) {
                            Logger logger8 = LOGGER;
                            if (logger8.isLoggable(this.debugLevel)) {
                                logger8.log(this.debugLevel, "Could not parse number of tracks: " + parseTextFrame4);
                                return;
                            }
                            return;
                        }
                    }
                }
                break;
            case "TT1":
            case "TIT1":
                this.grouping = parseTextFrame(iD3v2FrameBody);
                break;
            case "TT2":
            case "TIT2":
                this.title = parseTextFrame(iD3v2FrameBody);
                break;
            case "TYE":
            case "TYER":
                String parseTextFrame5 = parseTextFrame(iD3v2FrameBody);
                if (parseTextFrame5.length() > 0) {
                    try {
                        this.year = Short.valueOf(parseTextFrame5).shortValue();
                        break;
                    } catch (NumberFormatException unused9) {
                        Logger logger9 = LOGGER;
                        if (logger9.isLoggable(this.debugLevel)) {
                            logger9.log(this.debugLevel, "Could not parse year: " + parseTextFrame5);
                            return;
                        }
                        return;
                    }
                }
                break;
            case "ULT":
            case "USLT":
                if (this.lyrics == null) {
                    this.lyrics = parseCommentOrUnsynchronizedLyricsFrame(iD3v2FrameBody).text;
                    break;
                }
                break;
            case "TDRC":
                String parseTextFrame6 = parseTextFrame(iD3v2FrameBody);
                if (parseTextFrame6.length() >= 4) {
                    try {
                        this.year = Short.valueOf(parseTextFrame6.substring(0, 4)).shortValue();
                        break;
                    } catch (NumberFormatException unused10) {
                        Logger logger10 = LOGGER;
                        if (logger10.isLoggable(this.debugLevel)) {
                            logger10.log(this.debugLevel, "Could not parse year from: " + parseTextFrame6);
                            return;
                        }
                        return;
                    }
                }
                break;
        }
    }

    String parseTextFrame(ID3v2FrameBody iD3v2FrameBody) throws IOException, ID3v2Exception {
        return iD3v2FrameBody.readFixedLengthString((int) iD3v2FrameBody.getRemainingLength(), iD3v2FrameBody.readEncoding());
    }

    CommentOrUnsynchronizedLyrics parseCommentOrUnsynchronizedLyricsFrame(ID3v2FrameBody iD3v2FrameBody) throws IOException, ID3v2Exception {
        ID3v2Encoding readEncoding = iD3v2FrameBody.readEncoding();
        return new CommentOrUnsynchronizedLyrics(iD3v2FrameBody.readFixedLengthString(3, ID3v2Encoding.ISO_8859_1), iD3v2FrameBody.readZeroTerminatedString(200, readEncoding), iD3v2FrameBody.readFixedLengthString((int) iD3v2FrameBody.getRemainingLength(), readEncoding));
    }

    AttachedPicture parseAttachedPictureFrame(ID3v2FrameBody iD3v2FrameBody) throws IOException, ID3v2Exception {
        String readZeroTerminatedString;
        ID3v2Encoding readEncoding = iD3v2FrameBody.readEncoding();
        if (iD3v2FrameBody.getTagHeader().getVersion() == 2) {
            String upperCase = iD3v2FrameBody.readFixedLengthString(3, ID3v2Encoding.ISO_8859_1).toUpperCase();
            upperCase.hashCode();
            readZeroTerminatedString = !upperCase.equals("JPG") ? !upperCase.equals("PNG") ? "image/unknown" : "image/png" : "image/jpeg";
        } else {
            readZeroTerminatedString = iD3v2FrameBody.readZeroTerminatedString(20, ID3v2Encoding.ISO_8859_1);
        }
        return new AttachedPicture(iD3v2FrameBody.getData().readByte(), iD3v2FrameBody.readZeroTerminatedString(200, readEncoding), readZeroTerminatedString, iD3v2FrameBody.getData().readFully((int) iD3v2FrameBody.getRemainingLength()));
    }
}

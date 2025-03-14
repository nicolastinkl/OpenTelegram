package org.telegram.messenger.audioinfo.m4a;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.audioinfo.mp3.ID3v1Genre;

/* loaded from: classes3.dex */
public class M4AInfo extends AudioInfo {
    static final Logger LOGGER = Logger.getLogger(M4AInfo.class.getName());
    private final Level debugLevel;

    public M4AInfo(InputStream inputStream) throws IOException {
        this(inputStream, Level.FINEST);
    }

    public M4AInfo(InputStream inputStream, Level level) throws IOException {
        this.debugLevel = level;
        MP4Input mP4Input = new MP4Input(inputStream);
        Logger logger = LOGGER;
        if (logger.isLoggable(level)) {
            logger.log(level, mP4Input.toString());
        }
        ftyp(mP4Input.nextChild("ftyp"));
        moov(mP4Input.nextChildUpTo("moov"));
    }

    void ftyp(MP4Atom mP4Atom) throws IOException {
        Logger logger = LOGGER;
        if (logger.isLoggable(this.debugLevel)) {
            logger.log(this.debugLevel, mP4Atom.toString());
        }
        String trim = mP4Atom.readString(4, "ISO8859_1").trim();
        this.brand = trim;
        if (trim.matches("M4V|MP4|mp42|isom")) {
            logger.warning(mP4Atom.getPath() + ": brand=" + this.brand + " (experimental)");
        } else if (!this.brand.matches("M4A|M4P")) {
            logger.warning(mP4Atom.getPath() + ": brand=" + this.brand + " (expected M4A or M4P)");
        }
        String.valueOf(mP4Atom.readInt());
    }

    void moov(MP4Atom mP4Atom) throws IOException {
        MP4Atom nextChild;
        Logger logger = LOGGER;
        if (logger.isLoggable(this.debugLevel)) {
            logger.log(this.debugLevel, mP4Atom.toString());
        }
        while (mP4Atom.hasMoreChildren()) {
            nextChild = mP4Atom.nextChild();
            String type = nextChild.getType();
            type.hashCode();
            switch (type) {
                case "mvhd":
                    mvhd(nextChild);
                    break;
                case "trak":
                    trak(nextChild);
                    break;
                case "udta":
                    udta(nextChild);
                    break;
            }
        }
    }

    void mvhd(MP4Atom mP4Atom) throws IOException {
        Logger logger = LOGGER;
        if (logger.isLoggable(this.debugLevel)) {
            logger.log(this.debugLevel, mP4Atom.toString());
        }
        byte readByte = mP4Atom.readByte();
        mP4Atom.skip(3);
        mP4Atom.skip(readByte == 1 ? 16 : 8);
        int readInt = mP4Atom.readInt();
        long readLong = readByte == 1 ? mP4Atom.readLong() : mP4Atom.readInt();
        if (this.duration == 0) {
            this.duration = (readLong * 1000) / readInt;
        } else if (logger.isLoggable(this.debugLevel)) {
            long j = (readLong * 1000) / readInt;
            if (Math.abs(this.duration - j) > 2) {
                logger.log(this.debugLevel, "mvhd: duration " + this.duration + " -> " + j);
            }
        }
        mP4Atom.readIntegerFixedPoint();
        mP4Atom.readShortFixedPoint();
    }

    void trak(MP4Atom mP4Atom) throws IOException {
        Logger logger = LOGGER;
        if (logger.isLoggable(this.debugLevel)) {
            logger.log(this.debugLevel, mP4Atom.toString());
        }
        mdia(mP4Atom.nextChildUpTo("mdia"));
    }

    void mdia(MP4Atom mP4Atom) throws IOException {
        Logger logger = LOGGER;
        if (logger.isLoggable(this.debugLevel)) {
            logger.log(this.debugLevel, mP4Atom.toString());
        }
        mdhd(mP4Atom.nextChild("mdhd"));
    }

    void mdhd(MP4Atom mP4Atom) throws IOException {
        Logger logger = LOGGER;
        if (logger.isLoggable(this.debugLevel)) {
            logger.log(this.debugLevel, mP4Atom.toString());
        }
        byte readByte = mP4Atom.readByte();
        mP4Atom.skip(3);
        mP4Atom.skip(readByte == 1 ? 16 : 8);
        int readInt = mP4Atom.readInt();
        long readLong = readByte == 1 ? mP4Atom.readLong() : mP4Atom.readInt();
        if (this.duration == 0) {
            this.duration = (readLong * 1000) / readInt;
            return;
        }
        if (logger.isLoggable(this.debugLevel)) {
            long j = (readLong * 1000) / readInt;
            if (Math.abs(this.duration - j) > 2) {
                logger.log(this.debugLevel, "mdhd: duration " + this.duration + " -> " + j);
            }
        }
    }

    void udta(MP4Atom mP4Atom) throws IOException {
        Logger logger = LOGGER;
        if (logger.isLoggable(this.debugLevel)) {
            logger.log(this.debugLevel, mP4Atom.toString());
        }
        while (mP4Atom.hasMoreChildren()) {
            MP4Atom nextChild = mP4Atom.nextChild();
            if ("meta".equals(nextChild.getType())) {
                meta(nextChild);
                return;
            }
        }
    }

    void meta(MP4Atom mP4Atom) throws IOException {
        Logger logger = LOGGER;
        if (logger.isLoggable(this.debugLevel)) {
            logger.log(this.debugLevel, mP4Atom.toString());
        }
        mP4Atom.skip(4);
        while (mP4Atom.hasMoreChildren()) {
            MP4Atom nextChild = mP4Atom.nextChild();
            if ("ilst".equals(nextChild.getType())) {
                ilst(nextChild);
                return;
            }
        }
    }

    void ilst(MP4Atom mP4Atom) throws IOException {
        Logger logger = LOGGER;
        if (logger.isLoggable(this.debugLevel)) {
            logger.log(this.debugLevel, mP4Atom.toString());
        }
        while (mP4Atom.hasMoreChildren()) {
            MP4Atom nextChild = mP4Atom.nextChild();
            Logger logger2 = LOGGER;
            if (logger2.isLoggable(this.debugLevel)) {
                logger2.log(this.debugLevel, nextChild.toString());
            }
            if (nextChild.getRemaining() == 0) {
                if (logger2.isLoggable(this.debugLevel)) {
                    logger2.log(this.debugLevel, nextChild.getPath() + ": contains no value");
                }
            } else {
                data(nextChild.nextChildUpTo("data"));
            }
        }
    }

    void data(MP4Atom mP4Atom) throws IOException {
        Logger logger = LOGGER;
        if (logger.isLoggable(this.debugLevel)) {
            logger.log(this.debugLevel, mP4Atom.toString());
        }
        mP4Atom.skip(4);
        mP4Atom.skip(4);
        String type = mP4Atom.getParent().getType();
        type.hashCode();
        switch (type) {
            case "aART":
                this.albumArtist = mP4Atom.readString("UTF-8");
                break;
            case "covr":
                try {
                    byte[] readBytes = mP4Atom.readBytes();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    options.inSampleSize = 1;
                    BitmapFactory.decodeByteArray(readBytes, 0, readBytes.length, options);
                    int i = options.outWidth;
                    if (i > 800 || options.outHeight > 800) {
                        for (int max = Math.max(i, options.outHeight); max > 800; max /= 2) {
                            options.inSampleSize *= 2;
                        }
                    }
                    options.inJustDecodeBounds = false;
                    Bitmap decodeByteArray = BitmapFactory.decodeByteArray(readBytes, 0, readBytes.length, options);
                    this.cover = decodeByteArray;
                    if (decodeByteArray != null) {
                        float max2 = Math.max(decodeByteArray.getWidth(), this.cover.getHeight()) / 120.0f;
                        if (max2 > 0.0f) {
                            this.smallCover = Bitmap.createScaledBitmap(this.cover, (int) (r0.getWidth() / max2), (int) (this.cover.getHeight() / max2), true);
                        } else {
                            this.smallCover = this.cover;
                        }
                        if (this.smallCover == null) {
                            this.smallCover = this.cover;
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                break;
            case "cpil":
                this.compilation = mP4Atom.readBoolean();
                break;
            case "cprt":
            case "©cpy":
                String str = this.copyright;
                if (str == null || str.trim().length() == 0) {
                    this.copyright = mP4Atom.readString("UTF-8");
                    break;
                }
                break;
            case "disk":
                mP4Atom.skip(2);
                this.disc = mP4Atom.readShort();
                this.discs = mP4Atom.readShort();
                break;
            case "gnre":
                String str2 = this.genre;
                if (str2 == null || str2.trim().length() == 0) {
                    if (mP4Atom.getRemaining() == 2) {
                        ID3v1Genre genre = ID3v1Genre.getGenre(mP4Atom.readShort() - 1);
                        if (genre != null) {
                            this.genre = genre.getDescription();
                            break;
                        }
                    } else {
                        this.genre = mP4Atom.readString("UTF-8");
                        break;
                    }
                }
                break;
            case "rtng":
                mP4Atom.readByte();
                break;
            case "tmpo":
                mP4Atom.readShort();
                break;
            case "trkn":
                mP4Atom.skip(2);
                this.track = mP4Atom.readShort();
                this.tracks = mP4Atom.readShort();
                break;
            case "©ART":
                this.artist = mP4Atom.readString("UTF-8");
                break;
            case "©alb":
                this.album = mP4Atom.readString("UTF-8");
                break;
            case "©cmt":
                this.comment = mP4Atom.readString("UTF-8");
                break;
            case "©com":
            case "©wrt":
                String str3 = this.composer;
                if (str3 == null || str3.trim().length() == 0) {
                    this.composer = mP4Atom.readString("UTF-8");
                    break;
                }
                break;
            case "©day":
                String trim = mP4Atom.readString("UTF-8").trim();
                if (trim.length() >= 4) {
                    try {
                        this.year = Short.valueOf(trim.substring(0, 4)).shortValue();
                        break;
                    } catch (NumberFormatException unused) {
                        return;
                    }
                }
                break;
            case "©gen":
                String str4 = this.genre;
                if (str4 == null || str4.trim().length() == 0) {
                    this.genre = mP4Atom.readString("UTF-8");
                    break;
                }
                break;
            case "©grp":
                this.grouping = mP4Atom.readString("UTF-8");
                break;
            case "©lyr":
                this.lyrics = mP4Atom.readString("UTF-8");
                break;
            case "©nam":
                this.title = mP4Atom.readString("UTF-8");
                break;
        }
    }
}

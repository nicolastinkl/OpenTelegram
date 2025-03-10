package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

/* loaded from: classes.dex */
final class OggPageHeader {
    public int bodySize;
    public long granulePosition;
    public int headerSize;
    public int pageSegmentCount;
    public int revision;
    public int type;
    public final int[] laces = new int[255];
    private final ParsableByteArray scratch = new ParsableByteArray(255);

    OggPageHeader() {
    }

    public void reset() {
        this.revision = 0;
        this.type = 0;
        this.granulePosition = 0L;
        this.pageSegmentCount = 0;
        this.headerSize = 0;
        this.bodySize = 0;
    }

    public boolean skipToNextPage(ExtractorInput extractorInput) throws IOException {
        return skipToNextPage(extractorInput, -1L);
    }

    public boolean skipToNextPage(ExtractorInput extractorInput, long j) throws IOException {
        Assertions.checkArgument(extractorInput.getPosition() == extractorInput.getPeekPosition());
        this.scratch.reset(4);
        while (true) {
            if ((j == -1 || extractorInput.getPosition() + 4 < j) && ExtractorUtil.peekFullyQuietly(extractorInput, this.scratch.getData(), 0, 4, true)) {
                this.scratch.setPosition(0);
                if (this.scratch.readUnsignedInt() == 1332176723) {
                    extractorInput.resetPeekPosition();
                    return true;
                }
                extractorInput.skipFully(1);
            }
        }
        do {
            if (j != -1 && extractorInput.getPosition() >= j) {
                break;
            }
        } while (extractorInput.skip(1) != -1);
        return false;
    }

    public boolean populate(ExtractorInput extractorInput, boolean z) throws IOException {
        reset();
        this.scratch.reset(27);
        if (!ExtractorUtil.peekFullyQuietly(extractorInput, this.scratch.getData(), 0, 27, z) || this.scratch.readUnsignedInt() != 1332176723) {
            return false;
        }
        int readUnsignedByte = this.scratch.readUnsignedByte();
        this.revision = readUnsignedByte;
        if (readUnsignedByte != 0) {
            if (z) {
                return false;
            }
            throw ParserException.createForUnsupportedContainerFeature("unsupported bit stream revision");
        }
        this.type = this.scratch.readUnsignedByte();
        this.granulePosition = this.scratch.readLittleEndianLong();
        this.scratch.readLittleEndianUnsignedInt();
        this.scratch.readLittleEndianUnsignedInt();
        this.scratch.readLittleEndianUnsignedInt();
        int readUnsignedByte2 = this.scratch.readUnsignedByte();
        this.pageSegmentCount = readUnsignedByte2;
        this.headerSize = readUnsignedByte2 + 27;
        this.scratch.reset(readUnsignedByte2);
        if (!ExtractorUtil.peekFullyQuietly(extractorInput, this.scratch.getData(), 0, this.pageSegmentCount, z)) {
            return false;
        }
        for (int i = 0; i < this.pageSegmentCount; i++) {
            this.laces[i] = this.scratch.readUnsignedByte();
            this.bodySize += this.laces[i];
        }
        return true;
    }
}

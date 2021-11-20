package dev.fxe;

import java.io.IOException;
import java.io.InputStream;

import static dev.fxe.GifReader.uInt;

@SuppressWarnings("unused")
class GifGraphicControlExtension {

    private final DisposalMethod disposalMethod;
    private final boolean userInputFlag;
    private final boolean transparencyFlag;
    private final int transparentColorIndex;
    private final long delayTime;


    GifGraphicControlExtension(InputStream inputStream) throws IOException {

        int blockSize = inputStream.read();
        int packedFields = inputStream.read();

        this.transparencyFlag = (packedFields & 0x01) == 1;
        this.userInputFlag = (packedFields & 0x02) == 0x02;
        this.disposalMethod = DisposalMethod.values()[(packedFields & 0x1C) >> 2];

        this.delayTime = uInt(inputStream.read(), inputStream.read());
        this.transparentColorIndex = inputStream.read();

        int blockTerminator = inputStream.read();
    }

    public DisposalMethod getDisposalMethod() {
        return disposalMethod;
    }

    public boolean isUserInputFlag() {
        return userInputFlag;
    }

    public boolean isTransparencyFlag() {
        return transparencyFlag;
    }

    public int getTransparentColorIndex() {
        return transparentColorIndex;
    }

    public long getDelayTimeMS() {
        return delayTime * 10;
    }

    enum DisposalMethod {
        NO_DISPOSAL,
        DO_NOT_DISPOSE,
        RESTORE_TO_BACKGROUND_COLOR,
        RESTORE_TO_PREVIOUS
    }

}

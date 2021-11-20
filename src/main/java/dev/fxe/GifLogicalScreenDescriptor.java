package dev.fxe;

import java.io.IOException;
import java.io.InputStream;

import static dev.fxe.GifReader.uInt;

class GifLogicalScreenDescriptor {

    private final int width, height;
    private final int colorResolution, sizeOfGlobalColorTable, backgroundColorIndex, pixelAspectRatio;
    private final boolean globalColorTableFlag, sortFlag;

    GifLogicalScreenDescriptor(InputStream inputStream) throws IOException {
        this.width = uInt(inputStream.read(), inputStream.read());
        this.height = uInt(inputStream.read(), inputStream.read());

        int packedFields = inputStream.read();
        this.globalColorTableFlag = packedFields >> 7 == 1;
        this.colorResolution = ((packedFields >> 6) & 6) + 1;
        this.sortFlag = ((packedFields >> 3) & 1) == 1;
        this.sizeOfGlobalColorTable = packedFields & 7;

        this.backgroundColorIndex = inputStream.read();
        this.pixelAspectRatio = inputStream.read();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getPixelAspectRatio() {
        return this.pixelAspectRatio;
    }

    public int getColorResolution() {
        return this.colorResolution;
    }

    public int getBackgroundColorIndex() {
        return this.backgroundColorIndex;
    }

    public int getSizeOfGlobalColorTable() {
        return this.sizeOfGlobalColorTable;
    }

    public boolean isGlobalColorTableFlag() {
        return this.globalColorTableFlag;
    }

    public boolean isSortFlag() {
        return this.sortFlag;
    }


}

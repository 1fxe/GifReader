package dev.fxe;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class GifReader {

    private final InputStream in;

    private int width, height;
    private int globalColorTableFlag, sortFlag, colorResolution, sizeOfGlobalColorTable, backgroundColorIndex, pixelAspectRatio;

    private int[] globalColorTable;

    public GifReader(InputStream in) throws IOException {
        this.in = in;

        this.read();
    }

    private void read() throws IOException {
        byte[] buffer = new byte[3];
        in.read(buffer);
        if (!new String(buffer).equals("GIF")) {
            throw new IllegalArgumentException("Invalid Signature");
        }
        in.read(buffer);
        if (!new String(buffer).equals("89a")) {
            throw new IllegalArgumentException("Invalid Version");
        }

        buffer = new byte[2];
        in.read(buffer);
        width = readNum(buffer);
        in.read(buffer);
        height = readNum(buffer);

        buffer = new byte[1];
        in.read(buffer);
        byte packedFields = buffer[0];
        globalColorTableFlag = ((packedFields & 0xFF) >> 7);
        colorResolution = (((packedFields & 0xFF) >> 4) & 7) + 1;
        sortFlag = ((packedFields & 0xFF) >> 3);
        sizeOfGlobalColorTable = ((packedFields & 0xFF) >> 3) & 3;

        in.read(buffer);
        backgroundColorIndex = (buffer[0] & 0xFF);
        in.read(buffer);
        pixelAspectRatio = (buffer[0] & 0xFF);

        if (globalColorTableFlag == 1) {
            buffer = new byte[3 * (int) Math.pow(2, sizeOfGlobalColorTable + 1)];
            in.read(buffer);
            globalColorTable = new int[buffer.length];
            for (int i = 0; i < buffer.length; i++) {
                globalColorTable[i] = (buffer[i] & 0xFF);
            }
        }


    }

    private int readNum(byte[] buffer) {
        return (buffer[1] & 0xFF) << 8 | (buffer[0] & 0xFF);
    }
}

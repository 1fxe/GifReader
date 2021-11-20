package dev.fxe;

import java.io.IOException;
import java.io.InputStream;

public class GifReader {

    private final GifHeader gifHeader;
    private final GifLogicalScreenDescriptor gifLogicalScreenDescriptor;
    private int[] globalColorTable;

    public GifReader(InputStream in) throws IOException {
        this.gifHeader = new GifHeader(in);
        this.gifLogicalScreenDescriptor = new GifLogicalScreenDescriptor(in);

        if (this.gifLogicalScreenDescriptor.globalColorTableFlag) {
            byte[] buffer = new byte[3 * (int) Math.pow(2, this.gifLogicalScreenDescriptor.sizeOfGlobalColorTable + 1)];
            in.read(buffer);
            globalColorTable = new int[buffer.length];
            for (int i = 0; i < buffer.length; i += 1) {
                globalColorTable[i] = uByte(buffer[i]);
            }
        }

        int q;
        while ((q = in.read()) != -1) {
            if (q == 0x21) {
                q = in.read();
                switch (q) {
                    case 0x2C: // Image Descriptor
                        break;
                    case 0xF9: // Graphic Control Extension
                        break;
                    case 0x01: // Plain Text Extension
                        break;
                    case 0xFF: // Application Extension
                        break;
                    case 0xFE: // Comment Extension
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public GifHeader getGifHeader() {
        return gifHeader;
    }

    public GifLogicalScreenDescriptor getGifLogicalScreenDescriptor() {
        return gifLogicalScreenDescriptor;
    }

    protected static int uInt(int... i) {
        return i[0] | i[1] << 8;
    }

    protected static int uByte(int b) {
        return (b & 0xFF);
    }
}

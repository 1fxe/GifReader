package dev.fxe;

import java.io.IOException;
import java.io.InputStream;

public class GifReader {

    private final GifHeader gifHeader;
    private final GifLogicalScreenDescriptor gifLogicalScreenDescriptor;
    private int[] globalColorTable;

    private GifGraphicControlExtension gifGraphicControlExtension;
    private GifApplicationExtension gifApplicationExtension;

    public GifReader(InputStream in) throws IOException {
        this.gifHeader = new GifHeader(in);
        this.gifLogicalScreenDescriptor = new GifLogicalScreenDescriptor(in);

        if (this.gifLogicalScreenDescriptor.isGlobalColorTableFlag()) {
            byte[] buffer = new byte[3 * (int) Math.pow(2, this.gifLogicalScreenDescriptor.getSizeOfGlobalColorTable() + 1)];
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
                        while (in.read() != 0x00) {}
                        break;
                    case 0xF9: // Graphic Control Extension
                        this.gifGraphicControlExtension = new GifGraphicControlExtension(in);
                        break;
                    case 0x01: // Plain Text Extension
                        while (in.read() != 0x00) {}
                        break;
                    case 0xFF: // Application Extension
                        this.gifApplicationExtension = new GifApplicationExtension(in);
                        break;
                    case 0xFE: // Comment Extension
                        while (in.read() != 0x00) {}
                        break;
                    default:
                        while (in.read() != 0x00) {}
                        break;
                }
            }
        }

        System.out.println(this.gifGraphicControlExtension.getDelayTimeMS());

    }

    public GifHeader getGifHeader() {
        return this.gifHeader;
    }

    public GifLogicalScreenDescriptor getGifLogicalScreenDescriptor() {
        return this.gifLogicalScreenDescriptor;
    }

    public GifGraphicControlExtension getGifGraphicControlExtension() {
        return this.gifGraphicControlExtension;
    }

    protected static int uInt(int... i) {
        return i[0] | i[1] << 8;
    }

    protected static int uByte(int b) {
        return (b & 0xFF);
    }
}

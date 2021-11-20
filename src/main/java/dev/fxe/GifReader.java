package dev.fxe;

import java.io.IOException;
import java.io.InputStream;

public class GifReader {

    private final InputStream in;

    private int width, height;
    private int colorResolution, sizeOfGlobalColorTable, backgroundColorIndex, pixelAspectRatio;
    boolean globalColorTableFlag;
    boolean sortFlag;


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

        // Logical Screen Descriptor
        width = uInt(in.read(), in.read());
        height = uInt(in.read(), in.read());

        int packedFields = in.read();
        globalColorTableFlag = packedFields >> 7 == 1;
        colorResolution = ((packedFields >> 6) & 6) + 1;
        sortFlag = ((packedFields >> 3) & 1) == 1;
        sizeOfGlobalColorTable = packedFields & 7;

        backgroundColorIndex = in.read();
        pixelAspectRatio = in.read();

        // Global Color Table
        if (globalColorTableFlag) {
            buffer = new byte[3 * (int) Math.pow(2, sizeOfGlobalColorTable + 1)];
            in.read(buffer);
            globalColorTable = new int[buffer.length];
            for (int i = 0; i < buffer.length; i += 1) {
                globalColorTable[i] = uByte(buffer[i]);
            }
        }

        int q;
        while ((q = in.read()) != -1)
        if (q == 0x21) {
            q = in.read();
            switch (q) {
                case 0x2C: // Image Descriptor
                    readImageDescriptor(in);
                    break;
                case 0xF9: // Graphic Control Extension
                    break;
                case 0x01: // Plain Text Extension
                    break;
                case 0xFF: // Application Extension
                    break;
                case 0xFE: // Comment Extension
                    break;
            }
        }

    }

    private void readImageDescriptor(InputStream in) throws IOException {
        int imageLeftPosition = uInt(in.read(), in.read());
        int imageTopPosition = uInt(in.read(), in.read());
        int imageWidth = uInt(in.read(), in.read());
        int imageHeight = uInt(in.read(), in.read());

        // Unpack
    }

    private int uInt(int... i) {
        return i[0] | i[1] << 8;
    }

    private int uByte(int b) {
        return (b & 0xFF);
    }
}

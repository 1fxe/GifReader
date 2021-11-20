package dev.fxe;

import java.io.IOException;
import java.io.InputStream;

class GifHeader {

    final String signature;
    final String version;

    GifHeader(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[3];
        inputStream.read(buffer);
        this.signature = new String(buffer);
        inputStream.read(buffer);
        this.version = new String(buffer);
    }

}

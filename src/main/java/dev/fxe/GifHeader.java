package dev.fxe;

import java.io.IOException;
import java.io.InputStream;

class GifHeader {

    private final String signature;
    private final String version;

    GifHeader(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[3];
        inputStream.read(buffer);
        this.signature = new String(buffer);
        inputStream.read(buffer);
        this.version = new String(buffer);
    }

    public String getSignature() {
        return signature;
    }

    public String getVersion() {
        return version;
    }
}

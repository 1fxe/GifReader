package dev.fxe;

import java.io.IOException;
import java.io.InputStream;

class GifApplicationExtension {


    private final String applicationIdentifier;
    private final String applicationAuthCode;

    GifApplicationExtension(InputStream inputStream) throws IOException {
        int blockSize = inputStream.read();

        byte[] buffer = new byte[8];
        inputStream.read(buffer);
        this.applicationIdentifier = new String(buffer);
        buffer = new byte[3];
        inputStream.read(buffer);
        this.applicationAuthCode = new String(buffer);

    }

    public String getApplicationIdentifier() {
        return applicationIdentifier;
    }

    public String getApplicationAuthCode() {
        return applicationAuthCode;
    }

}

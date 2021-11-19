package dev.fxe;

import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = Main.class.getResourceAsStream("/anime.gif");

        assert inputStream != null;
        new GifReader(inputStream);
    }

}

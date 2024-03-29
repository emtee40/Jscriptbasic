package com.scriptbasic.utility.functions.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class BinaryFileReader implements FileHandler {
    private final InputStream stream;

    public BinaryFileReader(InputStream stream) {
        this.stream = stream;
    }

    public void close() throws IOException {
        stream.close();
    }

    public byte[] read(int len) throws IOException {
        final byte[] b = new byte[len];
        int bytesRead = stream.read(b);
        if (bytesRead < len) {
            return Arrays.copyOf(b, bytesRead);
        } else {
            return b;
        }
    }
}

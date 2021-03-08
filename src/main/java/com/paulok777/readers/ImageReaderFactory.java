package com.paulok777.readers;

public class ImageReaderFactory {
    public ImageReader getReader(String source) {
        return new BmpImageReader();
    }
}

package com.paulok777.readers;

import com.paulok777.extensions.ImageExtension;

import java.io.File;

public class ImageReaderFactory {

    public ImageReader getReader(String source) {
        File sourceFile = new File(source);
        String extension = sourceFile.getName().split("\\.")[1];
        ImageExtension imageExtension = ImageExtension.valueOf(extension.toUpperCase());

        switch (imageExtension) {
            case BMP:
                return new BmpImageReader(sourceFile);
            case PPM:
                return new PpmImageReader(sourceFile);
            default:
                throw new RuntimeException();
        }
    }
}

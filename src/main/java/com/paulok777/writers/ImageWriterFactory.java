package com.paulok777.writers;

import com.paulok777.extensions.ImageExtension;

import java.io.File;

public class ImageWriterFactory {

    public ImageWriter getWriter(String goalFormat, File output) {
        ImageExtension imageExtension = ImageExtension.valueOf(goalFormat.toUpperCase());

        switch (imageExtension) {
            case BMP:
                return new BmpImageWriter(output);
            case PPM:
                return new PpmImageWriter(output);
            default:
                throw new RuntimeException();
        }
    }
}

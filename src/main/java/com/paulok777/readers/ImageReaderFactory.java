package com.paulok777.readers;

import com.paulok777.Utils;
import com.paulok777.exceptions.UnsupportedExtensionException;
import com.paulok777.extensions.ImageExtension;

import java.io.File;

public class ImageReaderFactory {

    public ImageReader getReader(String source) throws UnsupportedExtensionException {
        File sourceFile = new File(source);
        String extension = Utils.getFileExtension(sourceFile.getName());
        ImageExtension imageExtension = ImageExtension.valueOf(extension.toUpperCase());

        switch (imageExtension) {
            case BMP:
                return new BmpImageReader(sourceFile);
            case PPM:
                return new PpmImageReader(sourceFile);
            default:
                throw new UnsupportedExtensionException(String.format("Extension \"%s\" is unsupported", extension));
        }
    }
}

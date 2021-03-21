package com.paulok777.readers;

import com.paulok777.Messages;
import com.paulok777.Utils;
import com.paulok777.exceptions.UnsupportedExtensionException;
import com.paulok777.exceptions.WrongArgumentsException;
import com.paulok777.extensions.ImageExtension;

import java.io.File;

public class ImageReaderFactory {

    public ImageReader getReader(String source) throws UnsupportedExtensionException {

        File sourceFile;
        String extension;
        ImageExtension imageExtension;

        try {
            sourceFile = new File(source);
            extension = Utils.getFileExtension(sourceFile.getName());
            imageExtension = ImageExtension.valueOf(extension.toUpperCase());
        } catch (Exception e) {
            throw new WrongArgumentsException(Messages.WRONG_ARGUMENTS);
        }

        switch (imageExtension) {
            case BMP:
                return new BmpImageReader(sourceFile);
            case PPM:
                return new PpmImageReader(sourceFile);
            default:
                throw new UnsupportedExtensionException(String.format(Messages.UNSUPPORTED_EXTENSION, extension));
        }
    }
}

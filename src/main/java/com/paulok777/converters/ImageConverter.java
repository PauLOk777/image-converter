package com.paulok777.converters;

import com.paulok777.Messages;
import com.paulok777.exceptions.UnsupportedExtensionException;
import com.paulok777.formats.Image;
import com.paulok777.readers.ImageReaderFactory;
import com.paulok777.writers.ImageWriterFactory;

import java.io.File;
import java.io.IOException;

public class ImageConverter {

    private ImageReaderFactory readerFactory;
    private ImageWriterFactory writerFactory;

    public ImageConverter(ImageReaderFactory readerFactory, ImageWriterFactory writerFactory) {
        this.readerFactory = readerFactory;
        this.writerFactory = writerFactory;
    }

    public void convert(
        String source,
        String goalFormat,
        String output
    ) throws IOException, UnsupportedExtensionException {
        Image image = readerFactory.getReader(source).read();
        File outputFile = getOutputFile(goalFormat, output);
        writerFactory.getWriter(goalFormat, outputFile).write(image);
    }

    private File getOutputFile(String goalFormat, String output) throws IOException {
        File clientOutput = new File(output);
        String[] file = clientOutput.getName().split("\\.");

        if (file.length > 1 && goalFormat.toUpperCase().equals(file[1].toUpperCase())) {
            return clientOutput;
        } else if (file.length == 1) {
            return new File(output + "." + goalFormat);
        } else {
            throw new IOException(Messages.FAILED_TO_CREATE_OUTPUT);
        }
    }
}

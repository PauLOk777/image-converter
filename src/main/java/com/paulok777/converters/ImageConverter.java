package com.paulok777.converters;

import com.paulok777.Messages;
import com.paulok777.formats.Image;
import com.paulok777.readers.ImageReaderFactory;
import com.paulok777.writers.ImageWriterFactory;

import java.io.File;
import java.util.Arrays;

public class ImageConverter {

    private ImageReaderFactory readerFactory;
    private ImageWriterFactory writerFactory;

    public ImageConverter(ImageReaderFactory readerFactory, ImageWriterFactory writerFactory) {
        this.readerFactory = readerFactory;
        this.writerFactory = writerFactory;
    }

    public String convert(String source, String goalFormat, String output) {
        try {
            Image image = readerFactory.getReader(source).read();
            File outputFile = getOutputFile(goalFormat, output);
            writerFactory.getWriter(goalFormat, outputFile).write(image);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return e.getMessage();
        }

        return Messages.SUCCESS_CONVERT;
    }

    private File getOutputFile(String goalFormat, String output) {
        File clientOutput = new File(output);
        String[] file = clientOutput.getName().split("\\.");

        if (file.length > 1  && goalFormat.toUpperCase().equals(file[1].toUpperCase())) {
            return clientOutput;
        } else if (file.length == 1) {
            return new File(output + "." + goalFormat);
        } else {
            throw new RuntimeException();
        }
    }
}

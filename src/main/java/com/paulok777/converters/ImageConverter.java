package com.paulok777.converters;

import com.paulok777.readers.ImageReaderFactory;
import com.paulok777.writers.ImageWriterFactory;

public class ImageConverter implements Converter {

    private ImageReaderFactory readerFactory;
    private ImageWriterFactory writerFactory;

    public ImageConverter(ImageReaderFactory readerFactory, ImageWriterFactory writerFactory) {
        this.readerFactory = readerFactory;
        this.writerFactory = writerFactory;
    }

    public void convert(String source, String goalFormat, String output) {
        readerFactory.getReader(source).read();
        writerFactory.getWriter(goalFormat, output).write();
    }
}

package com.paulok777.writers;

public class ImageWriterFactory {
    public ImageWriter getWriter(String goalFormat, String output) {
        return new BmpImageWriter();
    }
}

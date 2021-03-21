package com.paulok777.writers;

import com.paulok777.formats.Image;

import java.io.IOException;

public interface ImageWriter {

    void write(Image image) throws IOException;
}

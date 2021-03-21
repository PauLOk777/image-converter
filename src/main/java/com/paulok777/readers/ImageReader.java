package com.paulok777.readers;

import com.paulok777.formats.Image;

import java.io.IOException;

public interface ImageReader {

    Image read() throws IOException;
}

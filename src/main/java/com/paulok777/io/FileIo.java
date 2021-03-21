package com.paulok777.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIo {
    public static void writeToOutputFile(byte[] resultData, File output) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(output))) {
            bos.write(resultData);
        }
    }

    public static byte[] getAllBytesFromSource(File source) throws IOException {
        return Files.readAllBytes(Paths.get(source.toURI()));
    }
}

package com.paulok777.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIo {
    public static void writeToOutputFile(byte[] resultData, File output) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(output))) {
            bos.write(resultData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] getAllBytesFromSource(File source) {
        try {
            return Files.readAllBytes(Paths.get(source.toURI()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}

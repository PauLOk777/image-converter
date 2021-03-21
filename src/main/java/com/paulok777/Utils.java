package com.paulok777;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Utils {

    public static byte[] longToByteArraySize4(long value) {
        byte[] arraySize8 = ByteBuffer.allocate(8).putLong(value).array();
        return Arrays.copyOfRange(arraySize8, 4, 8);
    }

    public static long byteArrayToLong(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong();
    }

    public static byte[] intToByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    public static int byteArrayToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static String getFileName(String name) {
        String[] arr = name.split("\\.");
        return String.join("", Arrays.copyOfRange(arr, 0, arr.length - 1));
    }

    public static String getFileExtension(String name) {
        try {
            return name.split("\\.")[1];
        } catch (Exception e) {
            throw new RuntimeException(Messages.WRONG_ARGUMENTS);
        }
    }
}

package com.paulok777.formats;

import com.paulok777.Utils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class BmpData {

    public static final int FILE_TYPE_START_INDEX = 0;
    public static final int FILE_SIZE_START_INDEX = 2;
    public static final int RESERVED1_START_INDEX = 6;
    public static final int RESERVED2_START_INDEX = 8;
    public static final int PIXEL_DATA_OFFSET_START_INDEX = 10;
    public static final int INFO_SIZE_START_INDEX = 14;
    public static final int WIDTH_START_INDEX = 18;
    public static final int HEIGHT_START_INDEX = 22;
    public static final int PLANES_START_INDEX = 26;
    public static final int BITS_PER_PIXEL_START_INDEX = 28;
    public static final int COMPRESSION_START_INDEX = 30;
    public static final int IMAGE_SIZE_START_INDEX = 34;
    public static final int X_PIXELS_PER_METER_START_INDEX = 38;
    public static final int Y_PIXELS_PER_METER_START_INDEX = 42;
    public static final int TOTAL_COLORS_START_INDEX = 46;
    public static final int IMPORTANT_COLORS_START_INDEX = 50;

    public static final String FILE_TYPE = "BM";
    public static final int DEFAULT_RESERVED1 = 0;
    public static final int DEFAULT_RESERVED2 = 0;
    public static final long PIXEL_DATA_OFFSET = 54L;
    public static final long INFO_SIZE = 40L;
    public static final int DEFAULT_PLANES = 1;
    public static final int DEFAULT_BITS_PER_PIXEL = 24;
    public static final long DEFAULT_COMPRESSION = 0L;
    public static final long DEFAULT_IMAGE_SIZE = 0L;
    public static final int DEFAULT_X_PIXELS_PER_METER = 0;
    public static final int DEFAULT_Y_PIXELS_PER_METER = 0;
    public static final long DEFAULT_TOTAL_COLORS = 0L;
    public static final long DEFAULT_IMPORTANT_COLORS = 0L;

    private byte[] fileType;
    private byte[] fileSize;
    private byte[] reserved1;
    private byte[] reserved2;
    private byte[] pixelDataOffset;
    private byte[] infoSize;
    private byte[] width;
    private byte[] height;
    private byte[] planes;
    private byte[] bitsPerPixel;
    private byte[] compression;
    private byte[] imageSize;
    private byte[] xPixelsPerMeter;
    private byte[] yPixelsPerMeter;
    private byte[] totalColors;
    private byte[] importantColors;

    private byte[] data;

    public BmpData() {
        fileType = FILE_TYPE.getBytes();
        reserved1 = Utils.intToByteArray(DEFAULT_RESERVED1);
        reserved2 = Utils.intToByteArray(DEFAULT_RESERVED2);
        pixelDataOffset = Utils.longToByteArraySize4(PIXEL_DATA_OFFSET);
        infoSize = Utils.longToByteArraySize4(INFO_SIZE);
        planes = Utils.intToByteArray(DEFAULT_PLANES);
        bitsPerPixel = Utils.intToByteArray(DEFAULT_BITS_PER_PIXEL);
        compression = Utils.longToByteArraySize4(DEFAULT_COMPRESSION);
        imageSize = Utils.longToByteArraySize4(DEFAULT_IMAGE_SIZE);
        xPixelsPerMeter = Utils.intToByteArray(DEFAULT_X_PIXELS_PER_METER);
        yPixelsPerMeter = Utils.intToByteArray(DEFAULT_Y_PIXELS_PER_METER);
        totalColors = Utils.longToByteArraySize4(DEFAULT_TOTAL_COLORS);
        importantColors = Utils.longToByteArraySize4(DEFAULT_IMPORTANT_COLORS);

        ArrayUtils.reverse(reserved1);
        ArrayUtils.reverse(reserved2);
        ArrayUtils.reverse(pixelDataOffset);
        ArrayUtils.reverse(infoSize);
        ArrayUtils.reverse(planes);
        ArrayUtils.reverse(bitsPerPixel);
        ArrayUtils.reverse(compression);
        ArrayUtils.reverse(imageSize);
        ArrayUtils.reverse(xPixelsPerMeter);
        ArrayUtils.reverse(yPixelsPerMeter);
        ArrayUtils.reverse(totalColors);
        ArrayUtils.reverse(importantColors);
    }

    public byte[] getFileType() {
        return fileType;
    }

    public void setFileType(byte[] fileType) {
        this.fileType = fileType;
    }

    public byte[] getFileSize() {
        return fileSize;
    }

    public void setFileSize(byte[] fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getReserved1() {
        return reserved1;
    }

    public void setReserved1(byte[] reserved1) {
        this.reserved1 = reserved1;
    }

    public byte[] getReserved2() {
        return reserved2;
    }

    public void setReserved2(byte[] reserved2) {
        this.reserved2 = reserved2;
    }

    public byte[] getPixelDataOffset() {
        return pixelDataOffset;
    }

    public void setPixelDataOffset(byte[] pixelDataOffset) {
        this.pixelDataOffset = pixelDataOffset;
    }

    public byte[] getInfoSize() {
        return infoSize;
    }

    public void setInfoSize(byte[] infoSize) {
        this.infoSize = infoSize;
    }

    public byte[] getWidth() {
        return width;
    }

    public void setWidth(byte[] width) {
        this.width = width;
    }

    public byte[] getHeight() {
        return height;
    }

    public void setHeight(byte[] height) {
        this.height = height;
    }

    public byte[] getPlanes() {
        return planes;
    }

    public void setPlanes(byte[] planes) {
        this.planes = planes;
    }

    public byte[] getBitsPerPixel() {
        return bitsPerPixel;
    }

    public void setBitsPerPixel(byte[] bitsPerPixel) {
        this.bitsPerPixel = bitsPerPixel;
    }

    public byte[] getCompression() {
        return compression;
    }

    public void setCompression(byte[] compression) {
        this.compression = compression;
    }

    public byte[] getImageSize() {
        return imageSize;
    }

    public void setImageSize(byte[] imageSize) {
        this.imageSize = imageSize;
    }

    public byte[] getXPixelsPerMeter() {
        return xPixelsPerMeter;
    }

    public void setXPixelsPerMeter(byte[] xPixelsPerMeter) {
        this.xPixelsPerMeter = xPixelsPerMeter;
    }

    public byte[] getYPixelsPerMeter() {
        return yPixelsPerMeter;
    }

    public void setYPixelsPerMeter(byte[] yPixelsPerMeter) {
        this.yPixelsPerMeter = yPixelsPerMeter;
    }

    public byte[] getTotalColors() {
        return totalColors;
    }

    public void setTotalColors(byte[] totalColors) {
        this.totalColors = totalColors;
    }

    public byte[] getImportantColors() {
        return importantColors;
    }

    public void setImportantColors(byte[] importantColors) {
        this.importantColors = importantColors;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BmpData{" +
                "width=" + Arrays.toString(width) +
                ", height=" + Arrays.toString(height) +
                ", bitsPerPixel=" + Arrays.toString(bitsPerPixel) +
                '}';
    }
}

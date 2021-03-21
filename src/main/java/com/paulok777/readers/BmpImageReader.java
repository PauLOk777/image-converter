package com.paulok777.readers;

import com.paulok777.Messages;
import com.paulok777.Utils;
import com.paulok777.exceptions.InvalidDataException;
import com.paulok777.formats.BmpData;
import com.paulok777.formats.Image;
import com.paulok777.formats.Pixel;
import com.paulok777.io.FileIo;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class BmpImageReader implements ImageReader {

    private static final byte[] ZERO_FOUR_BYTES_ARRAY = {0, 0, 0, 0};
    private static final byte[] ZERO_TWO_BYTES_ARRAY = {0, 0};

    private File source;

    public BmpImageReader(File source) {
        this.source = source;
    }

    @Override
    public Image read() throws IOException {

        BmpData bmpData = new BmpData();
        readToBmpData(bmpData);

        Image image = new Image();
        populateRawData(bmpData, image);

        return image;
    }

    private void populateRawData(BmpData source, Image target) {
        byte[] width = Arrays.copyOf(source.getWidth(), source.getWidth().length);
        ArrayUtils.reverse(width);
        target.setWidth(Utils.byteArrayToInt(width));
        byte[] height = Arrays.copyOf(source.getHeight(), source.getHeight().length);
        ArrayUtils.reverse(height);
        target.setHeight(Utils.byteArrayToInt(height));
        target.setPixels(bmpByteDataToPixels(source.getData(),
                Utils.byteArrayToInt(width), Utils.byteArrayToInt(height)));
    }

    private void readToBmpData(BmpData data) throws IOException {
        byte[] rawBytes = FileIo.getAllBytesFromSource(source);

        validateNecessaryFields(rawBytes);

        data.setFileType(Arrays.copyOfRange(
                rawBytes, BmpData.FILE_TYPE_START_INDEX, BmpData.FILE_SIZE_START_INDEX));
        data.setFileSize(Arrays.copyOfRange(
                rawBytes, BmpData.FILE_SIZE_START_INDEX, BmpData.RESERVED1_START_INDEX));
        data.setReserved1(Arrays.copyOfRange(
                rawBytes, BmpData.RESERVED1_START_INDEX, BmpData.RESERVED2_START_INDEX));
        data.setReserved2(Arrays.copyOfRange(
                rawBytes, BmpData.RESERVED2_START_INDEX, BmpData.PIXEL_DATA_OFFSET_START_INDEX));
        data.setPixelDataOffset(Arrays.copyOfRange(
                rawBytes, BmpData.PIXEL_DATA_OFFSET_START_INDEX, BmpData.INFO_SIZE_START_INDEX));
        data.setInfoSize(Arrays.copyOfRange(
                rawBytes, BmpData.INFO_SIZE_START_INDEX, BmpData.WIDTH_START_INDEX));
        data.setWidth(Arrays.copyOfRange(
                rawBytes, BmpData.WIDTH_START_INDEX, BmpData.HEIGHT_START_INDEX));
        data.setHeight(Arrays.copyOfRange(
                rawBytes, BmpData.HEIGHT_START_INDEX, BmpData.PLANES_START_INDEX));
        data.setPlanes(Arrays.copyOfRange(
                rawBytes, BmpData.PLANES_START_INDEX, BmpData.BITS_PER_PIXEL_START_INDEX));
        data.setBitsPerPixel(Arrays.copyOfRange(
                rawBytes, BmpData.BITS_PER_PIXEL_START_INDEX, BmpData.COMPRESSION_START_INDEX));
        data.setCompression(Arrays.copyOfRange(
                rawBytes, BmpData.COMPRESSION_START_INDEX, BmpData.IMAGE_SIZE_START_INDEX));
        data.setImageSize(Arrays.copyOfRange(
                rawBytes, BmpData.IMAGE_SIZE_START_INDEX, BmpData.X_PIXELS_PER_METER_START_INDEX));
        data.setXPixelsPerMeter(Arrays.copyOfRange(
                rawBytes, BmpData.X_PIXELS_PER_METER_START_INDEX, BmpData.Y_PIXELS_PER_METER_START_INDEX));
        data.setYPixelsPerMeter(Arrays.copyOfRange(
                rawBytes, BmpData.Y_PIXELS_PER_METER_START_INDEX, BmpData.TOTAL_COLORS_START_INDEX));
        data.setTotalColors(Arrays.copyOfRange(
                rawBytes, BmpData.TOTAL_COLORS_START_INDEX, BmpData.IMPORTANT_COLORS_START_INDEX));
        data.setImportantColors(Arrays.copyOfRange(
                rawBytes, BmpData.IMPORTANT_COLORS_START_INDEX, BmpData.IMPORTANT_COLORS_START_INDEX + 4));
        data.setData(Arrays.copyOfRange(rawBytes, BmpData.IMPORTANT_COLORS_START_INDEX + 4, rawBytes.length));
    }

    private void validateNecessaryFields(byte[] rawBytes) throws InvalidDataException {
        byte[] fileType = Arrays.copyOfRange(
                rawBytes, BmpData.FILE_TYPE_START_INDEX, BmpData.FILE_SIZE_START_INDEX);

        if (!BmpData.FILE_TYPE.equals(new String(fileType))) {
            throw new InvalidDataException(Messages.BMP_FILE_WRONG_FORMAT);
        }

        byte[] pixelDataOffset = Arrays.copyOfRange(
                rawBytes, BmpData.PIXEL_DATA_OFFSET_START_INDEX, BmpData.INFO_SIZE_START_INDEX);
        ArrayUtils.reverse(pixelDataOffset);
        pixelDataOffset = ArrayUtils.addAll(ZERO_FOUR_BYTES_ARRAY, pixelDataOffset);

        if (BmpData.PIXEL_DATA_OFFSET != Utils.byteArrayToLong(pixelDataOffset)) {
            throw new InvalidDataException(Messages.BMP_FILE_WRONG_FORMAT);
        }

        byte[] infoSize = Arrays.copyOfRange(
                rawBytes, BmpData.INFO_SIZE_START_INDEX, BmpData.WIDTH_START_INDEX);
        ArrayUtils.reverse(infoSize);
        infoSize = ArrayUtils.addAll(ZERO_FOUR_BYTES_ARRAY, infoSize);

        if (BmpData.INFO_SIZE != Utils.byteArrayToLong(infoSize)) {
            throw new InvalidDataException(Messages.BMP_FILE_WRONG_FORMAT);
        }

        byte[] bitsPerPixel = Arrays.copyOfRange(
                rawBytes, BmpData.BITS_PER_PIXEL_START_INDEX, BmpData.COMPRESSION_START_INDEX);
        ArrayUtils.reverse(bitsPerPixel);
        bitsPerPixel = ArrayUtils.addAll(ZERO_TWO_BYTES_ARRAY, bitsPerPixel);

        if (BmpData.DEFAULT_BITS_PER_PIXEL != Utils.byteArrayToInt(bitsPerPixel)) {
            throw new InvalidDataException(Messages.BMP_FILE_WRONG_FORMAT);
        }
    }

    private Pixel[] bmpByteDataToPixels(byte[] data, int width, int height) {
        Pixel[] pixels = new Pixel[data.length / 3];

        int countOfZeroBytesInEndOfRow = (int)((Math.ceil(width * 3 / 4.0) - (width * 3 / 4.0)) * 4);
        int counterForPixels = 0;
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                pixels[counterForPixels] = new Pixel(data[(i * width + j) * 3 + 2 + i * countOfZeroBytesInEndOfRow],
                        data[(i * width + j) * 3 + 1 + i * countOfZeroBytesInEndOfRow],
                        data[(i * width + j) * 3 + i * countOfZeroBytesInEndOfRow]);
                counterForPixels++;
            }
        }

        return pixels;
    }
}

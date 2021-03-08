package com.paulok777.writers;

import com.paulok777.Utils;
import com.paulok777.formats.BmpData;
import com.paulok777.formats.Image;
import com.paulok777.io.FileIo;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;

public class BmpImageWriter implements ImageWriter {

    private File output;

    public BmpImageWriter(File output) {
        this.output = output;
    }

    @Override
    public void write(Image image) {
        BmpData data = new BmpData();
        populateBmpData(image, data);

        byte[] resultData = getResultArray(data);
        FileIo.writeToOutputFile(resultData, output);
    }

    public void populateBmpData(Image source, BmpData target) {
        long fileSize = source.getPixels().length * 3 + BmpData.PIXEL_DATA_OFFSET;
        byte[] fileSizeInBytes = Utils.longToByteArraySize4(fileSize);
        ArrayUtils.reverse(fileSizeInBytes);
        target.setFileSize(fileSizeInBytes);
        byte[] widthInBytes = Utils.longToByteArraySize4(source.getWidth());
        ArrayUtils.reverse(widthInBytes);
        target.setWidth(widthInBytes);
        byte[] heightInBytes = Utils.longToByteArraySize4(source.getHeight());
        ArrayUtils.reverse(heightInBytes);
        target.setHeight(heightInBytes);
        target.setData(Utils.bmpImageToByteData(source));
    }

    private byte[] getResultArray(BmpData data) {
        byte[] resultData = new byte[(int) BmpData.PIXEL_DATA_OFFSET + data.getData().length];
        System.arraycopy(data.getFileType(), 0,
                resultData, BmpData.FILE_TYPE_START_INDEX, data.getFileType().length);
        System.arraycopy(data.getFileSize(), 0,
                resultData, BmpData.FILE_SIZE_START_INDEX, data.getFileSize().length);
        System.arraycopy(data.getReserved1(), 0,
                resultData, BmpData.RESERVED1_START_INDEX, data.getReserved1().length);
        System.arraycopy(data.getReserved2(), 0,
                resultData, BmpData.RESERVED2_START_INDEX, data.getReserved2().length);
        System.arraycopy(data.getPixelDataOffset(), 0,
                resultData, BmpData.PIXEL_DATA_OFFSET_START_INDEX, data.getPixelDataOffset().length);
        System.arraycopy(data.getInfoSize(), 0,
                resultData, BmpData.INFO_SIZE_START_INDEX, data.getInfoSize().length);
        System.arraycopy(data.getWidth(), 0,
                resultData, BmpData.WIDTH_START_INDEX, data.getWidth().length);
        System.arraycopy(data.getHeight(), 0,
                resultData, BmpData.HEIGHT_START_INDEX, data.getHeight().length);
        System.arraycopy(data.getPlanes(), 0,
                resultData, BmpData.PLANES_START_INDEX, data.getPlanes().length);
        System.arraycopy(data.getBitsPerPixel(), 0,
                resultData, BmpData.BITS_PER_PIXEL_START_INDEX, data.getBitsPerPixel().length);
        System.arraycopy(data.getCompression(), 0,
                resultData, BmpData.COMPRESSION_START_INDEX, data.getCompression().length);
        System.arraycopy(data.getImageSize(), 0,
                resultData, BmpData.IMAGE_SIZE_START_INDEX, data.getImageSize().length);
        System.arraycopy(data.getxPixelsPerMeter(), 0,
                resultData, BmpData.X_PIXELS_PER_METER_START_INDEX, data.getxPixelsPerMeter().length);
        System.arraycopy(data.getyPixelsPerMeter(), 0,
                resultData, BmpData.Y_PIXELS_PER_METER_START_INDEX, data.getyPixelsPerMeter().length);
        System.arraycopy(data.getTotalColors(), 0,
                resultData, BmpData.TOTAL_COLORS_START_INDEX, data.getTotalColors().length);
        System.arraycopy(data.getImportantColors(), 0,
                resultData, BmpData.IMPORTANT_COLORS_START_INDEX, data.getImportantColors().length);
        System.arraycopy(data.getData(), 0,
                resultData, BmpData.IMPORTANT_COLORS_START_INDEX + 4, data.getData().length);
        return resultData;
    }
}

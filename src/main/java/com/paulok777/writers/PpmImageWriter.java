package com.paulok777.writers;

import com.paulok777.Utils;
import com.paulok777.formats.Image;
import com.paulok777.formats.PpmData;
import com.paulok777.io.FileIo;

import java.io.File;

public class PpmImageWriter implements ImageWriter {

    private static final String NEW_LINE = "\n";
    private static final String SPACE = " ";

    private File output;

    public PpmImageWriter(File output) {
        this.output = output;
    }

    @Override
    public void write(Image image) {
        PpmData data = new PpmData();
        populatePpmData(image, data);

        byte[] resultData = getResultArray(data);
        FileIo.writeToOutputFile(resultData, output);
    }

    private void populatePpmData(Image source, PpmData target) {
        target.setWidth(source.getWidth());
        target.setHeight(source.getHeight());
        target.setData(Utils.ppmImageToByteData(source));
    }

    private byte[] getResultArray(PpmData data) {
        StringBuilder metaData = new StringBuilder();
        byte[] bytesMetaData = metaData.append(data.getMagicNumber()).append(NEW_LINE)
                .append(data.getWidth()).append(SPACE)
                .append(data.getHeight()).append(NEW_LINE)
                .append(data.getMaxColorValue()).append(NEW_LINE)
                .toString().getBytes();

        byte[] resultData = new byte[bytesMetaData.length + data.getData().length];
        System.arraycopy(bytesMetaData, 0, resultData, 0, bytesMetaData.length);
        System.arraycopy(data.getData(), 0, resultData, bytesMetaData.length, data.getData().length);
        return resultData;
    }

}

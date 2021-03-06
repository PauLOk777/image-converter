package com.paulok777.readers;

import com.paulok777.Messages;
import com.paulok777.annotations.ImageReaderImplementation;
import com.paulok777.exceptions.InvalidDataException;
import com.paulok777.formats.Image;
import com.paulok777.formats.Pixel;
import com.paulok777.formats.PpmData;
import com.paulok777.io.FileIo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ImageReaderImplementation(extension = "ppm")
public class PpmImageReader implements ImageReader {

    private static final int MAX_BITS_PER_COLOR = 255;
    private static final String LEFT_PARENTHESES = "(";
    private static final String RIGHT_PARENTHESES = ")";
    private static final String WHITESPACE_REGEX = "\\s";
    private static final String WHITESPACES_REGEX = "\\s+";
    private static final String METADATA_PARSE_REGEX =
        LEFT_PARENTHESES + PpmData.MetaData.MAGIC_NUMBER.getRegex() + RIGHT_PARENTHESES +
        WHITESPACES_REGEX + LEFT_PARENTHESES + PpmData.MetaData.WIDTH.getRegex() + RIGHT_PARENTHESES +
        WHITESPACE_REGEX + LEFT_PARENTHESES + PpmData.MetaData.HEIGHT.getRegex() + RIGHT_PARENTHESES +
        WHITESPACE_REGEX + LEFT_PARENTHESES + PpmData.MetaData.MAX_COLOR_VALUE.getRegex() +
        RIGHT_PARENTHESES + WHITESPACE_REGEX;

    private File source;

    public PpmImageReader(File source) {
        this.source = source;
    }

    @Override
    public Image read() throws IOException {

        PpmData ppmData = new PpmData();
        readToPpmData(ppmData);

        Image image = new Image();
        populateRawData(ppmData, image);

        return image;
    }

    private void populateRawData(PpmData source, Image target) {
        target.setWidth(source.getWidth());
        target.setHeight(source.getHeight());
        target.setPixels(ppmByteDataToPixels(source.getData()));
    }


    private void readToPpmData(PpmData data) throws IOException {
        byte[] rawBytes = FileIo.getAllBytesFromSource(source);
        String rawDataString = new String(rawBytes);
        Pattern pattern = Pattern.compile(METADATA_PARSE_REGEX);
        Matcher matcher = pattern.matcher(rawDataString);
        if (matcher.find()) {
            int offset = matcher.group().getBytes().length;
            String magicNumber = matcher.group(PpmData.MetaData.MAGIC_NUMBER.getOrder());
            data.setMagicNumber(magicNumber);
            data.setWidth(Integer.parseInt(matcher.group(PpmData.MetaData.WIDTH.getOrder())));
            data.setHeight(Integer.parseInt(matcher.group(PpmData.MetaData.HEIGHT.getOrder())));
            int maxColorValue = Integer.parseInt(matcher.group(PpmData.MetaData.MAX_COLOR_VALUE.getOrder()));
            data.setMaxColorValue(maxColorValue);
            data.setData(Arrays.copyOfRange(rawBytes, offset, rawBytes.length));

            validateNecessaryFields(magicNumber, maxColorValue);
        } else {
            throw new InvalidDataException();
        }
    }

    private void validateNecessaryFields(String magicNumber, int maxColorValue) throws InvalidDataException {
        if (!magicNumber.equals(PpmData.DEFAULT_MAGIC_NUMBER)) {
            throw new InvalidDataException(Messages.PPM_FILE_WRONG_FORMAT);
        }

        if (maxColorValue != MAX_BITS_PER_COLOR) {
            throw new InvalidDataException(Messages.PPM_FILE_WRONG_FORMAT);
        }
    }

    private Pixel[] ppmByteDataToPixels(byte[] data) {
        Pixel[] pixels = new Pixel[data.length / 3];

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = new Pixel(data[i * 3], data[i * 3 + 1], data[i * 3 + 2]);
        }

        return pixels;
    }
}

package com.paulok777.formats;

public class PpmData {

    public static final String DEFAULT_MAGIC_NUMBER = "P6";
    public static final int DEFAULT_MAX_COLOR_VALUE = 255;

    private String magicNumber;
    private int width;
    private int height;
    private int maxColorValue;
    private byte[] data;

    public enum MetaData {
        MAGIC_NUMBER(1, "P\\d"),
        WIDTH(2, "\\d+"),
        HEIGHT(3, "\\d+"),
        MAX_COLOR_VALUE(4, "\\d+");

        private int order;
        private String regex;

        private MetaData(int order, String regex) {
            this.order = order;
            this.regex = regex;
        }

        public int getOrder() {
            return order;
        }

        public String getRegex() {
            return regex;
        }
    }

    public PpmData() {
        magicNumber = DEFAULT_MAGIC_NUMBER;
        maxColorValue = DEFAULT_MAX_COLOR_VALUE;
    }

    public String getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(String magicNumber) {
        this.magicNumber = magicNumber;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMaxColorValue() {
        return maxColorValue;
    }

    public void setMaxColorValue(int maxColorValue) {
        this.maxColorValue = maxColorValue;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PpmData{" +
                "magicNumber='" + magicNumber + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", maxColorValue=" + maxColorValue +
                '}';
    }
}

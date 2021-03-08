package com.paulok777.formats;

public class Image {

    private int height;
    private int width;
    private Pixel[] pixels;

    public Pixel getPixel(int x, int y) {
        return pixels[y * width + x];
    }

    public void setPixel(Pixel pixel, int x, int y) {
        pixels[y * width + x] = pixel;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Pixel[] getPixels() {
        return pixels;
    }

    public void setPixels(Pixel[] pixels) {
        this.pixels = pixels;
    }

    @Override
    public String toString() {
        return "Image{" +
                "height=" + height +
                ", width=" + width +
                '}';
    }
}

package com.paulok777.writers;

import com.paulok777.Messages;
import com.paulok777.annotations.ImageWriterImplementation;
import com.paulok777.exceptions.UnsupportedExtensionException;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ImageWriterFactory {

    private final static String IMAGE_READERS_PACKAGE = "com.paulok777.writers";

    private Map<String, Class> imageWriters;

    public ImageWriterFactory() {
        Reflections reflections = new Reflections(IMAGE_READERS_PACKAGE);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ImageWriterImplementation.class);

        imageWriters = classes.stream().collect(Collectors.toMap((Class imageWriter) -> {
            ImageWriterImplementation annotation = (ImageWriterImplementation) imageWriter.getAnnotation(ImageWriterImplementation.class);
            return annotation.extension();
        }, Function.identity()));
    }

    public ImageWriter getWriter(String goalFormat, File output) throws UnsupportedExtensionException {
        String imageExtension = goalFormat.toLowerCase();

        try {
            ImageWriter imageWriter = (ImageWriter) imageWriters
                .get(imageExtension)
                .getDeclaredConstructor(File.class)
                .newInstance(output);
            return imageWriter;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new UnsupportedExtensionException(String.format(Messages.UNSUPPORTED_EXTENSION, goalFormat));
        }
    }
}


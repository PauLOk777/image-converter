package com.paulok777.readers;

import com.paulok777.Messages;
import com.paulok777.Utils;
import com.paulok777.annotations.ImageReaderImplementation;
import com.paulok777.exceptions.UnsupportedExtensionException;
import com.paulok777.exceptions.WrongArgumentsException;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ImageReaderFactory {

    private final static String IMAGE_READERS_PACKAGE = "com.paulok777.readers";

    private Map<String, Class> imageReaders;

    public ImageReaderFactory() {
        Reflections reflections = new Reflections(IMAGE_READERS_PACKAGE);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ImageReaderImplementation.class);

        imageReaders = classes.stream().collect(Collectors.toMap((Class imageReader) -> {
            ImageReaderImplementation annotation = (ImageReaderImplementation) imageReader.getAnnotation(ImageReaderImplementation.class);
            return annotation.extension();
        }, Function.identity()));
    }

    public ImageReader getReader(String source) throws UnsupportedExtensionException {

        File sourceFile;
        String extension;
        String imageExtension;

        try {
            sourceFile = new File(source);
            extension = Utils.getFileExtension(sourceFile.getName());
            imageExtension = extension.toLowerCase();
        } catch (Exception e) {
            throw new WrongArgumentsException(Messages.WRONG_ARGUMENTS);
        }

        try {
            ImageReader imageReader = (ImageReader) imageReaders
                .get(imageExtension)
                .getDeclaredConstructor(File.class)
                .newInstance(sourceFile);
            return imageReader;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new UnsupportedExtensionException(String.format(Messages.UNSUPPORTED_EXTENSION, extension));
        }
    }
}

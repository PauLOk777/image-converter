package com.paulok777;

import com.paulok777.converters.ImageConverter;
import com.paulok777.exceptions.InvalidDataException;
import com.paulok777.exceptions.UnsupportedExtensionException;
import com.paulok777.readers.ImageReaderFactory;
import com.paulok777.writers.ImageWriterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    private ImageConverter converter;

    public Controller(ImageConverter converter) {
        this.converter = converter;
    }

    protected String convertImage(Map<String, String> mapArgs)  {
        try {
            String source = mapArgs.get(Arguments.SOURCE);
            String goalFormat = mapArgs.get(Arguments.GOAL_FORMAT);
            String output = mapArgs.get(Arguments.OUTPUT) != null
                ? mapArgs.get(Arguments.OUTPUT)
                : String.format("%s.%s", Utils.getFileName(source), goalFormat);

            System.out.println(output);

            return converter.convert(
                source,
                goalFormat,
                output
            );
        } catch (UnsupportedExtensionException e) {
            System.err.println(e.getMessage());
            return e.getMessage();
        } catch (InvalidDataException e) {
            System.err.println(e.getMessage());
            return e.getMessage();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static void main(String[] args) {
        Map<String, String> mapArgs = parse(args);
        String message = new Controller(
            new ImageConverter(
                new ImageReaderFactory(),
                new ImageWriterFactory()
            )
        ).convertImage(mapArgs);
        System.out.println(message);
    }

    private static Map<String, String> parse(String[] args) {
        Map<String, String> mapArgs = new HashMap<>();

        for (String arg : args)  {
            String key = arg.substring(0, arg.indexOf('=')).toLowerCase();
            String value = arg.substring(arg.indexOf('=') + 1).toLowerCase();
            mapArgs.put(key, value);
        }

        return mapArgs;
    }
}

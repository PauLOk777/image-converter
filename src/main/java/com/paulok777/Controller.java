package com.paulok777;

import com.paulok777.converters.ImageConverter;
import com.paulok777.readers.ImageReaderFactory;
import com.paulok777.writers.ImageWriterFactory;

import java.util.HashMap;
import java.util.Map;

public class Controller {

    private ImageConverter converter;

    public Controller(ImageConverter converter) {
        this.converter = converter;
    }

    protected String convertImage(Map<String, String> mapArgs)  {
        return converter.convert(
                mapArgs.get(Arguments.SOURCE),
                mapArgs.get(Arguments.GOAL_FORMAT),
                mapArgs.get(Arguments.OUTPUT)
        );
    }

    public static void main(String[] args) {
        Map<String, String> mapArgs = parse(args);
        String message = new Controller(
                new ImageConverter(new ImageReaderFactory(), new ImageWriterFactory())).convertImage(mapArgs);
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

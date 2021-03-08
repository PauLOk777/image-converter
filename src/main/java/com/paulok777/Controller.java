package com.paulok777;

import com.paulok777.converters.Converter;
import com.paulok777.converters.ImageConverter;
import com.paulok777.readers.ImageReaderFactory;
import com.paulok777.writers.ImageWriterFactory;

import java.util.HashMap;
import java.util.Map;

public class Controller {

    private Converter converter;

    public Controller(Converter converter) {
        this.converter = converter;
    }

    protected void convert(Map<String, String> mapArgs)  {
        converter.convert(
                mapArgs.get(Arguments.SOURCE),
                mapArgs.get(Arguments.GOAL_FORMAT),
                mapArgs.get(Arguments.OUTPUT)
        );
    }

    public static void main(String[] args) {
        Map<String, String> mapArgs = parse(args);
        if (args.length < 4) {
            new Controller(new ImageConverter(new ImageReaderFactory(), new ImageWriterFactory())).convert(mapArgs);
        }
    }

    private static Map<String, String> parse(String[] args) {
        Map<String, String> mapArgs = new HashMap<>();

        for (String arg : args)  {
            String key = arg.substring(0, arg.indexOf('='));
            String value = arg.substring(arg.indexOf('=') + 1);
            mapArgs.put(key, value);
        }

        return mapArgs;
    }
}

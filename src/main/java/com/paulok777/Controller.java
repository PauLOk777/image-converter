package com.paulok777;

import com.paulok777.converters.ImageConverter;
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

    protected void convertImage(Map<String, String> mapArgs) throws IOException, UnsupportedExtensionException {
        String source = mapArgs.get(Arguments.SOURCE);
        String goalFormat = mapArgs.get(Arguments.GOAL_FORMAT);
        String output = mapArgs.get(Arguments.OUTPUT) != null
            ? mapArgs.get(Arguments.OUTPUT)
            : String.format("%s.%s", Utils.getFileName(source), goalFormat);

        converter.convert(
            source,
            goalFormat,
            output
        );
    }

    public static void main(String[] args) {
        try {
            Map<String, String> mapArgs = parse(args);
            Controller controller = new Controller(
                new ImageConverter(
                    new ImageReaderFactory(),
                    new ImageWriterFactory()
                )
            );
            controller.convertImage(mapArgs);

            System.out.println(Messages.SUCCESS_CONVERT);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
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

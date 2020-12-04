package net.jjshanks;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import net.jjshanks.error.JollyException;

public class InputReader {

    static final String DEFULAT_INPUT_DELIMITER = "[\r\n]+";

    private final String inputName;

    public InputReader(String inputName) {
        this.inputName = inputName;
    }

    public List<String> getInput() throws JollyException {
        return getInput(Function.identity(), Collectors.toList(), DEFULAT_INPUT_DELIMITER);
    }

    public <U> List<U> getInput(Function<String, U> mapper) throws JollyException {
        return getInput(mapper, Collectors.toList(), DEFULAT_INPUT_DELIMITER);
    }

    public <U> List<U> getInput(Function<String, U> mapper, Collector<U, ?, List<U>> collector, String inputDelimiter) throws JollyException {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(inputName);
            Scanner scan = new Scanner(is);
            scan.useDelimiter(inputDelimiter);
            List<String> lines = new ArrayList<>();
            while (scan.hasNext()) {
                lines.add(scan.next().trim());
            }
            scan.close();
            is.close();
            return lines.stream().map(mapper).collect(collector);
        } catch (IOException e) {
            throw new JollyException(e);
        }
    }
}
package net.jjshanks;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InputReader {

    private final String inputName;

    public InputReader(String inputName) {
        this.inputName = inputName;
    }

    public List<String> getInput() throws Exception {
        return getInput(Function.identity());
    }

    public <U> List<U> getInput(Function<String, U> function) throws Exception {
        URL url = getClass().getClassLoader().getResource(inputName);
        Path path = Paths.get(url.toURI());
        return Files.readAllLines(path, StandardCharsets.UTF_8)
            .stream()
            .map(function)
            .collect(Collectors.toList());
    }
}
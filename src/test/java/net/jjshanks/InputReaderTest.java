package net.jjshanks;

import java.util.List;
import java.util.function.Function;

import junit.framework.TestCase;
import net.jjshanks.error.JollyException;

public class InputReaderTest extends TestCase {

    public void testStringReader() throws JollyException {
        InputReader<String> ir = new InputReader<>("testInput1");
        List<String> actual = ir.getInput(Function.identity());
        List<String> expected = List.of("2", "3", "5", "7");
        assertEquals(expected, actual);
    }

    public void testFunctionReader() throws JollyException {
        InputReader<Integer> ir = new InputReader<>("testInput1");
        List<Integer> actual = ir.getInput(Integer::parseInt);
        List<Integer> expected = List.of(2, 3, 5, 7);
        assertEquals(expected, actual);
    }
}

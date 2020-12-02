package net.jjshanks;

import java.util.List;

import junit.framework.TestCase;

public class InputReaderTest extends TestCase {

    public void testStringReader() throws JollyException {
        InputReader ir = new InputReader("testInput1");
        List<String> actual = ir.getInput();
        List<String> expected = List.of("2", "3", "5", "7");
        assertEquals(expected, actual);
    }

    public void testFunctionReader() throws JollyException {
        InputReader ir = new InputReader("testInput1");
        List<Integer> actual = ir.getInput(Integer::parseInt);
        List<Integer> expected = List.of(2, 3, 5, 7);
        assertEquals(expected, actual);
    }
}

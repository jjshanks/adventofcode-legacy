package net.jjshanks;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jjshanks.error.JollyException;

public class Day2 extends AbstractProblem {

    private InputReader<String> inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day2.class);

    public static void main(String... args) throws Exception {
        new Day2().run();
    }

    Day2(String inputPath) {
        this.inputReader = new InputReader<>(inputPath);
    }

    public Day2() {
        this("input2");
    }

    
    @Override
    public void run() throws JollyException {
        part1();
        part2();
    }

    /**
     * 1 2 3
     * 4 5 6
     * 7 8 9
     */
    static Map<Integer, Map<Character, Integer>> graph1 = new HashMap<>();
    static {
        graph1.put(1, Map.of('D', 4, 'R', 2));
        graph1.put(2, Map.of('L', 1, 'D', 5, 'R', 3));
        graph1.put(3, Map.of('L', 2, 'D', 6));
        graph1.put(4, Map.of('U', 1, 'D', 7, 'R', 5));
        graph1.put(5, Map.of('U', 2, 'L', 4, 'D', 8, 'R', 6));
        graph1.put(6, Map.of('U', 3, 'L', 5, 'D', 9));
        graph1.put(7, Map.of('U', 4, 'R', 8));
        graph1.put(8, Map.of('U', 5, 'L', 7, 'R', 9));
        graph1.put(9, Map.of('U', 6, 'L', 8));
    }

    void part1() throws JollyException {
        String ans = "";
        int start = 5;
        for(String line : inputReader.getInput(Function.identity())) {
            for(char c : line.toCharArray()) {
                start = move(start, c, graph1);
            }
            ans += Integer.toString(start);
        }
        solution(ans);
    }

    void part2() throws JollyException {
        String ans = "";
        int start = 5;
        for(String line : inputReader.getInput(Function.identity())) {
            for(char c : line.toCharArray()) {
                start = move(start, c, graph2);
            }
            ans += Integer.toString(start, 14);
        }
        solution(ans.toUpperCase());
    }

    /**
     *     1
     *   2 3 4
     * 5 6 7 8 9
     *   A B C
     *     D
     */
    static Map<Integer, Map<Character, Integer>> graph2 = new HashMap<>();
    static {
        graph2.put(1, Map.of('D', 3));
        graph2.put(2, Map.of('D', 6, 'R', 3));
        graph2.put(3, Map.of('U', 1, 'L', 2, 'D', 7, 'R', 4));
        graph2.put(4, Map.of('L', 3, 'D', 8));
        graph2.put(5, Map.of('R', 6));
        graph2.put(6, Map.of('U', 2, 'L', 5, 'D', 10, 'R', 7));
        graph2.put(7, Map.of('U', 3, 'L', 6, 'D', 11, 'R', 8));
        graph2.put(8, Map.of('U', 4, 'L', 7, 'D', 12, 'R', 9));
        graph2.put(9, Map.of('L', 8));
        graph2.put(10, Map.of('U', 6, 'R', 11));
        graph2.put(11, Map.of('U', 7, 'L', 10, 'D', 13, 'R', 12));
        graph2.put(12, Map.of('U', 8, 'L', 11));
        graph2.put(13, Map.of('U', 11));
    }

    private int move(int start, char c, Map<Integer, Map<Character, Integer>> graph) {
        Map<Character, Integer> x = graph.get(start);
        if(x.containsKey(c)) {
            return x.get(c);
        }
        return start;
    }
}

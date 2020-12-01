package net.jjshanks;

import java.util.*;

public class Day1Part2 extends AbstractProblem {
    
    private InputReader inputReader;

    public static void main(String... args) throws Exception {
        new Day1Part2().Run();
    }

    public Day1Part2() {
        this.inputReader = new InputReader("input1");
    }

    public void Run() throws Exception {
        List<Integer> values = inputReader.getInput(Integer::parseInt);
        Set<Integer> seen = new HashSet<>();
        Map<Integer, List<Integer>> canidates = new HashMap<>();
        for(Integer value : values) {
            if(seen.size() > 1) {
                if(canidates.containsKey(value)) {
                    List<Integer> others = canidates.get(value);
                    solution(value * others.get(0) * others.get(1));
                    break;
                }
                for(Integer x : seen) {
                    List<Integer> others = new ArrayList<>();
                    others.add(x);
                    others.add(value);
                    canidates.put(2020 - x - value, others);
                }
            }
            seen.add(value);
        }
    }
}

package net.jjshanks;

import java.util.*;

public class Day1Part1 extends AbstractProblem {
    
    private InputReader inputReader;
 
    public static void main(String... args) throws Exception {
        new Day1Part1().Run();
    }

    public Day1Part1() {
        this.inputReader = new InputReader("input1");
    }

    public void Run() throws Exception {
        List<Integer> values = inputReader.getInput(Integer::parseInt);
        Set<Integer> seen = new HashSet<>();
        for(Integer value : values) {
            Integer lookingFor = 2020 - value;
            if(seen.contains(lookingFor)) {
                solution(lookingFor * value);
                break;
            }
            seen.add(value);
        }
    }
}

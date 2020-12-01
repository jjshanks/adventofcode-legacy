package net.jjshanks;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day1Part1 extends AbstractProblem {
    
    private InputReader inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day1Part1.class);

    public static void main(String... args) throws Exception {
        new Day1Part1().run();
    }

    public Day1Part1() {
        this.inputReader = new InputReader("input1");
    }

    public void run() throws Exception {
        List<Integer> values = inputReader.getInput(Integer::parseInt);
        Set<Integer> seen = new HashSet<>();
        for(Integer value : values) {
            LOG.trace("Evaulating input {}", value);
            Integer lookingFor = 2020 - value;
            LOG.trace("Looking for {}", lookingFor);
            if(seen.contains(lookingFor)) {
                LOG.debug("Found a solution key for {} with {}", value, lookingFor);
                solution(lookingFor * value);
                break;
            }
            LOG.trace("Adding {} as a seen value", value);
            seen.add(value);
        }
    }
}

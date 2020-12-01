package net.jjshanks;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day1Part2 extends AbstractProblem {
    
    private InputReader inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day1Part2.class);

    public static void main(String... args) throws Exception {
        new Day1Part2().run();
    }

    public Day1Part2() {
        this.inputReader = new InputReader("input1");
    }

    public void run() throws Exception {
        List<Integer> values = inputReader.getInput(Integer::parseInt);
        Set<Integer> seen = new HashSet<>();
        Map<Integer, List<Integer>> canidates = new HashMap<>();
        for(Integer value : values) {
            LOG.trace("Evaulating input {}", value);
            if(seen.size() > 1) {
                if(canidates.containsKey(value)) {
                    List<Integer> others = canidates.get(value);
                    LOG.debug("Found a solution key for {} with {}", value, others);
                    solution(value * others.get(0) * others.get(1));
                    break;
                }
                for(Integer x : seen) {
                    List<Integer> others = new ArrayList<>();
                    others.add(x);
                    others.add(value);
                    Integer key = 2020 - x - value;
                    if(key < 0) {
                        LOG.trace("Ignoring key {} with values {}", key, others);
                        continue;
                    }
                    LOG.debug("Tracking key {} with values {}", key, others);
                    canidates.put(key, others);
                }
            }
            LOG.trace("Adding {} as a seen value", value);
            seen.add(value);
        }
    }
}

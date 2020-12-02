package net.jjshanks.day1;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jjshanks.AbstractProblem;
import net.jjshanks.InputReader;
import net.jjshanks.error.JollyException;

public class Day1Part1 extends AbstractProblem {

    private InputReader inputReader;

    static final Logger LOG = LoggerFactory.getLogger(Day1Part1.class);

    public static void main(String... args) throws Exception {
        new Day1Part1().run();
    }

    Day1Part1(String inputPath) {
        this.inputReader = new InputReader(inputPath);
    }

    public Day1Part1() {
        this("input1");
    }

    public void run() throws JollyException {
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
